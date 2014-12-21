package net.masterthought.dlanguage.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import ddt.dtool.parser.DeeParser;
import net.masterthought.dlanguage.DLanguage;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Paths;
import java.util.*;

/**
 * Parser for D source, wrapper for Descent parser.
 */
public class DParser implements PsiParser {

    public DParser() {
    }


    @NotNull
    @Override
    public ASTNode parse(final IElementType root, final PsiBuilder builder) {
        builder.setDebugMode(true);
        PsiBuilder.Marker rootMarker = builder.mark();

        parseContent(builder);

        rootMarker.done(root);
        ASTNode ret = builder.getTreeBuilt();
//        System.out.println(ret);
        return ret;


    }

//    deeNode.getModuleNode().getStartPos()
//    deeNode.getModuleNode().getEndPos()
//    deeNode.hasChildren()
//    deeNode.getChildren()

// use for all instead of above
//    deeNode.getChildren()[1].getStartPos()

// ((Module) deeNode).getChildren()[3].getNodeType().name   e.g. DEFINITION_CLASS

// builder.getCurrentOffset()

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



    private List<DMarker> buildDMarkerStructure(ddt.melnorme.lang.tooling.ast_actual.ASTNode deeNode) {
        List<DMarker> dMarkers = Lists.newArrayList();
        DMarkerparseDeeStructure(dMarkers, deeNode);
        return dMarkers;
    }

    private DMarker DMarkerparseDeeStructure(List<DMarker> dMarkers, ddt.melnorme.lang.tooling.ast_actual.ASTNode deeNode) {

        IElementType elementType = new IElementType(deeNode.getNodeType().name(), DLanguage.INSTANCE);
        int startOffSet = deeNode.getStartPos();
        int endOffSet = deeNode.getEndPos();
        String content = deeNode.toStringAsCode();

        for (ddt.melnorme.lang.tooling.ast_actual.ASTNode child : deeNode.getChildren()) {
            DMarkerparseDeeStructure(dMarkers, child);
        }

        DMarker dMarker = new DMarker(elementType, startOffSet, endOffSet, content);
        dMarkers.add(dMarker);
        return dMarker;
    }

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


    private Boolean allStarted(List<DMarker> markers) {
        List<Boolean> results = Lists.newArrayList();
        for (DMarker m : markers) {
            results.add(m.isStarted());
        }
        return !results.contains(false);
    }

    private Boolean allDone(List<DMarker> markers) {
        List<Boolean> results = Lists.newArrayList();
        for (DMarker m : markers) {
            results.add(m.isDone());
        }
        return !results.contains(false);
    }


    private void parseContent(PsiBuilder builder) {
        String basePath = builder.getProject().getBasePath();
        ddt.melnorme.lang.tooling.ast_actual.ASTNode deeNode = DeeParser.parseSource(builder.getOriginalText().toString(), Paths.get(basePath)).node;

        List<DMarker> dMarkers = buildDMarkerStructure(deeNode);


        List<String> seen = Lists.newArrayList();
        List<DMarker> modifiedMarkers = Lists.newArrayList();
        for (DMarker m : dMarkers) {
            String val = String.valueOf(m.start()) + String.valueOf(m.finish());
            if (!seen.contains(val)) {
                seen.add(val);
                modifiedMarkers.add(m);
            }
        }

        Collections.sort(modifiedMarkers, new DMarkerComparator());

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

        List<Integer> sortedStructure = new ArrayList(structure.keySet());
        Collections.sort(sortedStructure);

        int counter = 0;
        int next_position;
        while (!builder.eof()) {

            for (Integer position : sortedStructure) {
                if(counter+1 < sortedStructure.size()) {
                    next_position = sortedStructure.get(counter + 1);
                } else {
                    next_position = builder.getOriginalText().toString().length();
                }
                if (position >= builder.getCurrentOffset() && builder.getCurrentOffset() < next_position) {
                    Map<String, List<DMarker>> s = structure.get(position);

                    // ends
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

                    // starts
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
                counter++;
            }
            builder.advanceLexer();
        }

        Boolean started = allStarted(modifiedMarkers);
        Boolean done = allDone(modifiedMarkers);

        System.out.println("yay!");

    }
}
