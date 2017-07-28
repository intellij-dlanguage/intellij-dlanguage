package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageMixinTemplateName extends PsiElement {
    @Nullable
    public DLanguageTypeofExpression getTypeofExpression();

    @Nullable
    public DLanguageIdentifierOrTemplateChain getIdentifierOrTemplateChain();

    @Nullable
    public DLanguageSymbol getSymbol();

    @Nullable
    public PsiElement getOP_DOT();

}
