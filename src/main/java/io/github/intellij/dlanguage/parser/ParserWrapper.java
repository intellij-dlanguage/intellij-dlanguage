package io.github.intellij.dlanguage.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 7/2/2017.
 */
public class ParserWrapper implements PsiParser {

    @NotNull
    @Override
    public ASTNode parse(@NotNull final IElementType root, @NotNull final PsiBuilder builder) {
        final DLangParser parser = new DLangParser(builder);
        PsiBuilder.Marker m = builder.mark();
        parser.parseModule();
        m.done(root);
        return builder.getTreeBuilt();
    }
}
