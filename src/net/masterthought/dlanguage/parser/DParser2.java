package net.masterthought.dlanguage.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import net.masterthought.dlanguage.psi.DLanguageTokenType;
import net.masterthought.dlanguage.psi.interfaces.DElementTypes2;
import org.jetbrains.annotations.NotNull;

/**
 * Parser for D source, wrapper for Descent parser.
 */
public class DParser2 implements PsiParser {

    public DParser2() {
    }


    @NotNull
    @Override
    public ASTNode parse(final IElementType root, final PsiBuilder builder) {
        builder.setDebugMode(true);
        PsiBuilder.Marker rootMarker = builder.mark();

        parseModuleDeclaration(builder);

        rootMarker.done(root);
        ASTNode ret = builder.getTreeBuilt();
//        System.out.println(ret);
        return ret;



    }

    private void parseModuleDeclaration(PsiBuilder builder) {
        PsiBuilder.Marker marker = null;
        IElementType tokenType;
        while (!builder.eof()) {
            tokenType = builder.getTokenType();

            if (tokenType == null) {
                //item.drop();
                break;
            }

            if (tokenType == DLanguageTokenType.KW_MODULE) {
                marker = builder.mark();
            }
            if (tokenType == DLanguageTokenType.LINE_END) {
                if (marker != null) {
                    marker.done(DElementTypes2.MODULE);
                    marker = null;
                }
            }
            builder.advanceLexer();
        }
    }

//    private static ASTNode chewEverything(PsiBuilder.Marker marker, IElementType e, PsiBuilder builder) {
//        while (!builder.eof()) {
//            builder.advanceLexer();
//        }
//        marker.done(e);
//        ASTNode result = builder.getTreeBuilt();
//        // System.out.println("Psifile:" + builder.getTreeBuilt().getPsi().getContainingFile().getName());
//        return result;
//    }

//    private void parseItem(PsiBuilder builder) {
//        //PsiBuilder.Marker section = builder.mark();
//        PsiBuilder.Marker item;
//        IElementType tokenType = null;
//
//        while (!builder.eof()) {
//            tokenType = builder.getTokenType();
//
//            if (tokenType == null) {
//                //item.drop();
//                break;
//            }
//
//            item = builder.mark();
//
////            System.out.println(tokenType);
////            System.out.println(builder.getTokenText());
////            System.out.println(builder.getCurrentOffset());
//
//            //builder.advanceLexer();
//            builder.advanceLexer();
//            item.collapse(tokenType);
//            //builder.advanceLexer();
//        }
//
//        //section.done(ElementTypes.UNKNOWN);
//    }
}
