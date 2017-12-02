package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageMulExpression extends PsiElement {

    @Nullable
    public DLanguageMulExpression getMulExpression();

    @Nullable
    public DLanguagePowExpression getPowExpression();

    @Nullable
    public PsiElement getOP_MOD();

    @Nullable
    public PsiElement getOP_DIV();

    @Nullable
    public PsiElement getOP_ASTERISK();

}
