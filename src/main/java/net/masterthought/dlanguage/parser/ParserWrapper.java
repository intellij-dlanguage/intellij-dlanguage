package net.masterthought.dlanguage.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LightPsiParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;

/**
 * Created by francis on 7/2/2017.
 */
public class ParserWrapper implements LightPsiParser, PsiParser {
    @Override
    public void parseLight(IElementType t, PsiBuilder b) {
        boolean r = false;
        b = adapt_builder_(t, b, this, null);
        PsiBuilder.Marker m = enter_section_(b, 0, _COLLAPSE_, null);
        if (false) {
            //todo re-add incremental re-parse
        } else {
            final DLangParser dLangParser = new DLangParser(b);
            r = dLangParser.parseModule();
        }
        exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
    }


    @NotNull
    @Override
    public ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
        parseLight(root, builder);
        return builder.getTreeBuilt();
    }
}
