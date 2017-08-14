package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAndExpression extends PsiElement {
    @Nullable
    DLanguageAndExpression getAndExpression();

    @Nullable
    DLanguageCmpExpression getCmpExpression();

    @Nullable
    PsiElement getOP_AND();

}
