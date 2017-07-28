package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageMixinDeclaration extends PsiElement {
    @Nullable
    public DLanguageTemplateMixinExpression getTemplateMixinExpression();

    @Nullable
    public DLanguageMixinExpression getMixinExpression();

    @Nullable
    public PsiElement getOP_SCOLON();

}
