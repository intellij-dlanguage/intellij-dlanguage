package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAddExpression extends PsiElement {

    @Nullable
    public DLanguageAddExpression getAddExpression();

    @Nullable
    public DLanguageMulExpression getMulExpression();

    @Nullable
    public PsiElement getOP_TILDA();

    @Nullable
    public PsiElement getOP_PLUS();

    @Nullable
    public PsiElement getOP_MINUS();

}
