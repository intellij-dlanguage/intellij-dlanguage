package net.masterthought.dlanguage.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.tree.IElementType;
import ddt.dtool.parser.DeeParser;
import net.masterthought.dlanguage.DLanguage;
import net.masterthought.dlanguage.psi.DLanguageTokenType;
import net.masterthought.dlanguage.psi.interfaces.DElementTypes2;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Paths;
import java.util.*;

/**
 * Parser for D source, wrapper for Descent parser.
 */
public class DParser4 implements PsiParser {

    public DParser4() {
    }


    @NotNull
    @Override
    public ASTNode parse(final IElementType root, final PsiBuilder builder) {
        builder.setDebugMode(true);
        PsiBuilder.Marker rootMarker = builder.mark();

        parseContent(builder, rootMarker);

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

    private HashMap<Integer, DMarker> createStartMap(List<DMarker> dMarkers) {
        HashMap<Integer, DMarker> startMap = Maps.newHashMap();
        for (DMarker d : dMarkers) {
            startMap.put(d.start(), d);
        }
        return startMap;
    }

    private HashMap<Integer, DMarker> createEndMap(List<DMarker> dMarkers) {
        HashMap<Integer, DMarker> startMap = Maps.newHashMap();
        for (DMarker d : dMarkers) {
            startMap.put(d.finish(), d);
        }
        return startMap;
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

    public class IntegerComparator implements Comparator<Integer> {

        public int compare(Integer obj1, Integer obj2) {
            return (obj1 - obj2);
        }

    }

    public class DMarkerComparator implements Comparator<DMarker> {

           public int compare(DMarker obj1, DMarker obj2) {
               return obj1.start().compareTo(obj2);
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

    private void parseContent(PsiBuilder builder, PsiBuilder.Marker rootMarker) {
        String basePath = builder.getProject().getBasePath();
        ddt.melnorme.lang.tooling.ast_actual.ASTNode deeNode = DeeParser.parseSource(builder.getOriginalText().toString(), Paths.get(basePath)).node;

        List<DMarker> dMarkers = buildDMarkerStructure(deeNode);


        Map<Integer, List<Integer>> startItems = Maps.newHashMap();
        Map<Integer, List<Integer>> endItems = Maps.newHashMap();
        for (int x = 1; x <= dMarkers.size(); x = x + 1) {
            DMarker marker = dMarkers.get(x - 1);
            List<Integer> itemList = startItems.get(marker.start());
            if (itemList == null) {
                itemList = Lists.newArrayList();
            }
            itemList.add(x - 1);
            startItems.put(marker.start(), itemList);

            // end items
            List<Integer> eitemList = endItems.get(marker.finish());
            if (eitemList == null) {
                eitemList = Lists.newArrayList();
            }
            eitemList.add(x - 1);
            endItems.put(marker.finish(), eitemList);

        }

//        List sortedKeys=new ArrayList(structure.keySet());
//        Collections.sort(sortedKeys);

        IntegerComparator comparator = new IntegerComparator();

        Collections.sort(dMarkers,comparator);

        while (!builder.eof()) {


            for(DMarker marker : dMarkers){

            }


            // close
//            int curr = builder.getCurrentOffset();
//
//            for (Integer key : startItems.keySet()) {
//                List<Integer> startItemList = startItems.get(key);
//                if(startItemList != null) {
//                    Collections.sort(startItemList,comparator);
//                    for (Integer m : startItemList) {
//                        DMarker marker = dMarkers.get(m);
//                        if (!marker.isStarted()) {
//                            if (builder.getCurrentOffset() + 1 >= marker.start()) {
//                                marker.marker(builder.mark());
//                                marker.setStartedStatus(true);
//                            }
//                        }
//                    }
//                }
//            }
//
//            for (Integer key : endItems.keySet()) {
//                List<Integer> endItemList = endItems.get(key);
//                if(endItemList != null) {
//                    Collections.sort(endItemList,Collections.reverseOrder(comparator));
//                    for (Integer m : endItemList) {
//                        DMarker marker = dMarkers.get(m);
//                        if (!marker.isDone()) {
//                            if (builder.getCurrentOffset() + 1 >= marker.finish()) {
//                                marker.marker().done(marker.elementType());
//                                marker.setDoneStatus(true);
//                            }
//                        }
//                    }
//                }
//            }
//
//            builder.advanceLexer();

        }

        Boolean started = allStarted(dMarkers);
        Boolean done = allDone(dMarkers);

        System.out.println("yay!");

//        DMarkerComparator comparator = new DMarkerComparator();
//        Collections.sort(dMarkers, comparator);

//        int totalSize = dMarkers.size();
//        List<Integer> indexList = Lists.newArrayList();
//
//        for (int x = 1; x <= totalSize; x = x + 1) {
//            indexList.add(x);
//        }
//        for (int x = totalSize; x >= 1; x = x - 1) {
//            indexList.add(x);
//        }
//
//        while (!builder.eof()) {
//            for(DMarker marker : dMarkers){
//
//            }
//
//
////          builder.advanceLexer();
//
//        }

//            for (int x = 1; x <= builder.getOriginalText().length(); x = x + 1) {
//               for(DMarker marker : dMarkers){
//                  if(x == marker.start()){
//                      marker.marker(builder.mark());
//                  }
//                   builder.advanceLexer();
//               }
//            }


//            for (int x = 1; x <= totalSize; x = x + 1) {
//                DMarker startMarker = dMarkers.get(x-1);
//                DMarker endMarker = dMarkers.get(totalSize-1);
//                if (builder.getCurrentOffset() == startMarker.start()) {
//                    startMarker.marker(builder.mark());
//                }
//
//                int fromEnd = builder.getOriginalText().length() - builder.getCurrentOffset();
//                if (fromEnd == endMarker.finish()) {
//                    endMarker.marker().done(endMarker.elementType());
//                }
//            }

//            builder.advanceLexer();
//        }

//
//        for (int x = 1; x <= totalSize; x = x + 1) {
//            DMarker startMarker = dMarkers.get(x-1);
//            DMarker endMarkeer = dMarkers.get(totalSize-1);
//            while (!builder.eof()) {
//                if (builder.getCurrentOffset() == startMarker.start()) {
//                    startMarker.marker(builder.mark());
//                    startMarker.setStartedStatus(true);
////                    rootMarker.rollbackTo();
////                    break;
//                }
//                if (builder.getCurrentOffset() == endMarkeer.finish()) {
//                    endMarkeer.marker().done(endMarkeer.elementType());
//                    endMarkeer.setDoneStatus(true);
////                    rootMarker.rollbackTo();
////                    break;
//                }
//                builder.advanceLexer();
//            }
//
//        }


//        while (!builder.eof()) {
//
//            for (DMarker marker : dMarkers) {
//
//                if(!marker.isDone()) {
//                    if (builder.getCurrentOffset() == marker.start()) {
//                        marker.marker(builder.mark());
//                        marker.setStartedStatus(true);
//                        rootMarker.rollbackTo();
//                        break;
//                    }
//                }
//                builder.advanceLexer();
//            }
//
//            if (allStarted(dMarkers)) {
//                Collections.sort(dMarkers, Collections.reverseOrder(comparator));
//
//                for (DMarker marker : dMarkers) {
//                    if (builder.getCurrentOffset() == marker.finish()) {
//                        marker.marker().done(marker.elementType());
//                        marker.setDoneStatus(true);
//                        rootMarker.rollbackTo();
//                        break;
//                    }
//                    builder.advanceLexer();
//                }
//            }
//
//        }

//        Collections.sort(dMarkers, Collections.reverseOrder(comparator));
//
//        for (DMarker marker : dMarkers) {
//
//              while (!builder.eof()) {
//
//                  if (builder.getCurrentOffset() == marker.finish()) {
//                      marker.marker().done(marker.elementType());
//                      marker.setDoneStatus(true);
//                      rootMarker.rollbackTo();
//                      break;
//                  }
//                  builder.advanceLexer();
//              }
//
//          }


//        while (!builder.eof()) {
//            for (DMarker marker : dMarkers) {
//                if (builder.getCurrentOffset() == marker.start() || builder.getCurrentOffset() == marker.finish()) {
//                    if(!marker.isStarted()) {
//                        marker.marker(builder.mark());
//                        marker.setStartedStatus(true);
//                    } else {
//                        if(!marker.isDone()) {
//                            marker.marker().done(marker.elementType());
//                            marker.setDoneStatus(true);
//                        }
//                    }
//                }
//            }
//            builder.advanceLexer();
//        }


//        DMarker lastMatchedMarker = null;

//        while (!builder.eof()) {
//
//            for (DMarker marker : dMarkers) {
//
//                if (!marker.isStarted()) {
//
//                    if (builder.getCurrentOffset() == marker.start()) {
//                        if (lastMatchedMarker != null && lastMatchedMarker.start() != marker.start()) {
//                            builder.advanceLexer();
//                        }
//                        marker.marker(builder.mark());
//                        marker.setStartedStatus(true);
//                        lastMatchedMarker = marker;
//                    }
//                } else {
//                    if (builder.getCurrentOffset() == marker.finish()) {
//                        if (lastMatchedMarker != null && lastMatchedMarker.finish() != marker.finish()) {
//                            builder.advanceLexer();
//                        }
//                        marker.marker().done(marker.elementType());
//                        marker.setDoneStatus(true);
//                        lastMatchedMarker = marker;
//                    }
//                }
//                builder.advanceLexer();
//            }
//
//        }


    }
}
