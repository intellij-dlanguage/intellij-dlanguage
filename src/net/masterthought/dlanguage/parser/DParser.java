package net.masterthought.dlanguage.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * Parser for D source, wrapper for Descent parser.
 */
public class DParser implements PsiParser {

    public DParser() {
    }


    @NotNull
    @Override
    public ASTNode parse(final IElementType root, final PsiBuilder builder) {
        builder.setDebugMode(ApplicationManager.getApplication().isUnitTestMode());
        PsiBuilder.Marker rootMarker = builder.mark();

        parseItem(builder);

        rootMarker.done(root);
        ASTNode ret = builder.getTreeBuilt();
        System.out.println(ret);
        return ret;
    }

    private void parseItem(PsiBuilder builder) {
        //PsiBuilder.Marker section = builder.mark();
        PsiBuilder.Marker item;
        IElementType tokenType = null;

        while (!builder.eof()) {
            tokenType = builder.getTokenType();

            if (tokenType == null) {
                //item.drop();
                break;
            }

            item = builder.mark();

            System.out.println(tokenType);
            System.out.println(builder.getTokenText());
            System.out.println(builder.getCurrentOffset());

            //builder.advanceLexer();
            builder.advanceLexer();
            item.collapse(tokenType);
            //builder.advanceLexer();
        }

        //section.done(ElementTypes.UNKNOWN);
    }
}
