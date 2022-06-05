package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

public interface DLanguageFunctionContract extends PsiElement {

    @Nullable
    PsiElement getOP_BRACES_LEFT();

    @Nullable
    DLanguageInOutStatement getInOutStatement();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    DLanguageInOutContractExpression getInOutContractExpression();
}
