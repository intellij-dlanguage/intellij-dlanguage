package net.masterthought.dlanguage.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import ddt.dtool.parser.DeeParser;
import ddt.dtool.parser.DeeParserResult;
import ddt.dtool.parser.common.LexElement;
import net.masterthought.dlanguage.lexer.DeeElementTypeCache;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Parser for D source, wrapper for Descent parser.
 * <p/>
 * This is a wrapper for the eclipse DDT parser instead of writing another D parser from scratch
 * The approach taken was to use the DDT parser to build up a AST hierarchy and grab the start and end positions
 * for each node. Then use the intellij PsiBuilder to mark and complete tokens at the specific points determined
 * by the DDT parser.
 * <p/>
 * This approach may seem crazy - but after several days of research I could not find a way to create the Psi structure
 * without going through the Psi Builder loop. This approach seems to work but I'm very open to suggestion for a better
 * way. (I'm working on a BNF grammar to use the grammar kit to generate the parser - but that is a big task for me and
 * something I will maybe get around to in the future.
 */
public class DParser implements PsiParser {

    public DParser() {
    }

    @NotNull
    @Override
    public ASTNode parse(final IElementType root, final PsiBuilder builder) {
        builder.setDebugMode(true);

        // In error cases intellij creates less tokens than the DeeParser
        // This ensures that we can limit the end position of nodes to the max intellij offset
        Integer maxIdeaOffset = maxIdeaTokenOffset(builder);

        PsiBuilder.Marker rootMarker = builder.mark();
        try {
            parseContent(builder, maxIdeaOffset);
        } catch (Exception e1) {
            rootMarker.rollbackTo();
            PsiBuilder.Marker newRoot = builder.mark();
            final PsiBuilder.Marker errorMark = builder.mark();
            while (!builder.eof()) {
                builder.advanceLexer();
            }
            errorMark.error(e1.getMessage());
            newRoot.done(root);
            return builder.getTreeBuilt();
        }
        return chewEverything(rootMarker, root, builder);
    }

    private Integer maxIdeaTokenOffset(PsiBuilder builder) {
        Integer max = 0;
        PsiBuilder.Marker collectorMarker = builder.mark();
        while (!builder.eof()) {
            max = builder.getCurrentOffset();
            builder.advanceLexer();
        }
        collectorMarker.rollbackTo();

        return max;
    }

    private static ASTNode chewEverything(PsiBuilder.Marker marker, IElementType e, PsiBuilder builder) {
        while (!builder.eof()) {
            builder.advanceLexer();
        }
        marker.done(e);
        return builder.getTreeBuilt();
    }

    // Data container to hold the data about each DeeParser node - e.g. the start and end positions and the Psi Marker.
    class DMarker {

        private IElementType elementType;
        private int start;
        private int finish;
        private PsiBuilder.Marker marker = null;
        private String content;
        Boolean doneStatus = false;
        Boolean startedStatus = false;

        public DMarker(IElementType elementType, int start, int finish, String content) {
            this.elementType = elementType;
            this.start = start;
            this.finish = finish;
            this.content = content;
        }

        public IElementType elementType() {
            return elementType;
        }

        public int start() {
            return start;
        }

        public int finish() {
            return finish;
        }

        public void marker(PsiBuilder.Marker marker) {
            this.marker = marker;
        }

        public PsiBuilder.Marker marker() {
            return marker;
        }

        public String content() {
            return content;
        }

        public void setDoneStatus(Boolean status) {
            this.doneStatus = status;
        }

        public Boolean isDone() {
            return doneStatus;
        }

        public void setStartedStatus(Boolean status) {
            this.startedStatus = status;
        }

        public Boolean isStarted() {
            return startedStatus;
        }
    }

    // Create a list of nodes with sart and end positions so that we can iteratie it and mark and complete with PsiBuilder
    private List<DMarker> buildDMarkerStructure(ddt.melnorme.lang.tooling.ast_actual.ASTNode deeNode, int endPosition) {
        List<DMarker> dMarkers = Lists.newArrayList();
        DMarkerparseDeeStructure(dMarkers, deeNode, endPosition);
        return dMarkers;
    }

    // Recursive method that builds the start and end positions for each node based on the DeeParser result
    private DMarker DMarkerparseDeeStructure(List<DMarker> dMarkers, ddt.melnorme.lang.tooling.ast_actual.ASTNode deeNode, int endPosition) {

        IElementType elementType = DeeElementTypeCache.valueOf(deeNode.getNodeType().name());
        int startOffSet = deeNode.getStartPos();
        int endOffSet = deeNode.getEndPos();
        String content = deeNode.toStringAsCode();

        // dont set an end position greater than what intellij thinks is the last position
        if (endOffSet >= endPosition) {
            endOffSet = endPosition;
        }

        // items at the end of the file get lost so this allows us to parse up till just before the eof
        if (endOffSet >= endPosition) {
            endOffSet = endOffSet - 1;
        }

        for (ddt.melnorme.lang.tooling.ast_actual.ASTNode child : deeNode.getChildren()) {
            DMarkerparseDeeStructure(dMarkers, child, endPosition);
        }

        DMarker dMarker = new DMarker(elementType, startOffSet, endOffSet, content);
        dMarkers.add(dMarker);
        return dMarker;
    }

    // Comparators used for sorting by order
    public class DMarkerComparator implements Comparator<DMarker> {

        public int compare(DMarker obj1, DMarker obj2) {
            return obj1.start() - obj2.start();
        }

    }

    public class EndersComparator implements Comparator<DMarker> {

        public int compare(DMarker obj1, DMarker obj2) {
            return obj1.start() - obj2.start();
        }

    }

    public class StartersComparator implements Comparator<DMarker> {

        public int compare(DMarker obj1, DMarker obj2) {
            return obj1.finish() - obj2.finish();
        }

    }

    // Do all the complicated stuff to matchup the DeeParse with the PsiBuilder
    private void parseContent(PsiBuilder builder, Integer maxIdeaOffset) {
        String basePath = builder.getProject().getBasePath();
        DeeParserResult.ParsedModule parsedModule = DeeParser.parseSource(builder.getOriginalText().toString(), Paths.get(basePath));
        int tokenListSize = parsedModule.tokenList.size();
        LexElement lastElement = parsedModule.tokenList.get(tokenListSize - 1);
        int endPosition = lastElement.getEndPos();
        ddt.melnorme.lang.tooling.ast_actual.ASTNode deeNode = parsedModule.node;

        // if the DeeParser end position exceeds what intellij thinks is the end position use the intellij one instead
        if (endPosition > maxIdeaOffset) {
            endPosition = maxIdeaOffset;
        }

        List<DMarker> dMarkers = buildDMarkerStructure(deeNode, endPosition);

        // remove duplicates in terms of exactly the same start and end positions.
        List<String> seen = Lists.newArrayList();
        List<DMarker> dupes = Lists.newArrayList();
        for (DMarker m : dMarkers) {
            String val = String.valueOf(m.start()) + String.valueOf(m.finish());
            if (!seen.contains(val)) {
                seen.add(val);
                dupes.add(m);
            }
        }

        // remove the top level MODULE - DeeParser creates a top level module containing everything in the file
        List<DMarker> modifiedMarkers = Lists.newArrayList();
        for (DMarker m : dupes) {
            if (!m.elementType().toString().equals("MODULE")) {
                modifiedMarkers.add(m);
            }
        }

        Collections.sort(modifiedMarkers, new DMarkerComparator());

        // Build a map that contains each position where there is either a drop marker or a complete marker.
        // This allows us to grab the actions we need to complete at each offset - a list of either
        // start: drop markers or finish: complete previously dropped markers.
        Map<Integer, Map<String, List<DMarker>>> structure = Maps.newHashMap();
        // start
        for (DMarker marker : modifiedMarkers) {
            Map<String, List<DMarker>> item = structure.get(marker.start());
            if (item == null) {
                item = Maps.newHashMap();
            }
            List<DMarker> startList = item.get("start");
            if (startList == null) {
                startList = Lists.newArrayList();
            }
            startList.add(marker);
            item.put("start", startList);
            structure.put(marker.start(), item);
        }
        // end
        for (DMarker marker : modifiedMarkers) {
            Map<String, List<DMarker>> item = structure.get(marker.finish());
            if (item == null) {
                item = Maps.newHashMap();
            }
            List<DMarker> endList = item.get("finish");
            if (endList == null) {
                endList = Lists.newArrayList();
            }
            endList.add(marker);
            item.put("finish", endList);
            structure.put(marker.finish(), item);
        }

        // Loop until eof reached
        while (!builder.eof()) {

            int currentPosition = builder.getCurrentOffset();

            // Find the actions to do at the current offset
            Map<String, List<DMarker>> s = structure.get(currentPosition);
            if (s != null) {

                // ends - do these first in order of the highest start offset
                List<DMarker> enders = s.get("finish");
                if (enders != null) {
                    Collections.sort(enders, Collections.reverseOrder(new EndersComparator()));
                    for (DMarker m : enders) {
                        if (!m.isDone()) {
                            m.marker().done(m.elementType());
                            m.setDoneStatus(true);
                        }
                    }
                }

                // starts - do these after the ends are done and in order of the lowest end offset
                List<DMarker> starters = s.get("start");
                if (starters != null) {
                    Collections.sort(starters, Collections.reverseOrder(new StartersComparator()));
                    for (DMarker m : starters) {
                        if (!m.isStarted()) {
                            m.marker(builder.mark());
                            m.setStartedStatus(true);
                        }
                    }
                }


            }

            builder.advanceLexer();
        }

        //        // This is helpful for debugging issues with the DeeParser to PSi Builder integration
        //        List<Map<String,Integer>> deeTokens = Lists.newArrayList();
        //               for(LexElement e : parsedModule.tokenList){
        //                   Map map = Maps.newHashMap();
        //                   map.put(e.toString(), e.getStartPos());
        //                   deeTokens.add(map);
        //               }
        //               List<Map<String,Integer>> ideaTokens = Lists.newArrayList();
        //
        //               while(!builder.eof()){
        //                   String ideaContent = builder.getTokenText();
        //                   int position = builder.getCurrentOffset();
        //                   Map map = Maps.newHashMap();
        //                   map.put(ideaContent,position);
        //                   ideaTokens.add(map);
        //                   builder.advanceLexer();
        //               }
        //
        //               System.out.println("done");

    }
}
