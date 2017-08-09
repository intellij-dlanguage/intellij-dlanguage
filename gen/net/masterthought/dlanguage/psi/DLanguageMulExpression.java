package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageMulExpression extends PsiElement {
    @Nullable
    DLanguageMulExpression getMulExpression();

    @Nullable
    DLanguagePowExpression getPowExpression();

    @Nullable
    PsiElement getOP_MOD();

    @Nullable
    PsiElement getOP_DIV();

    @Nullable
    PsiElement getOP_ASTERISK();

}
