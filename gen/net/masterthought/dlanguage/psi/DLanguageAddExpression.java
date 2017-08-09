package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAddExpression extends PsiElement {
    @Nullable
    DLanguageAddExpression getAddExpression();

    @Nullable
    DLanguageMulExpression getMulExpression();

    @Nullable
    PsiElement getOP_TILDA();

    @Nullable
    PsiElement getOP_PLUS();

    @Nullable
    PsiElement getOP_MINUS();

}
