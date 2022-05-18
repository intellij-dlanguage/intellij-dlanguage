package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

public interface DLanguageInOutContractExpression extends PsiElement {

    @Nullable
    DLanguageInContractExpression getInContractExpression();

    @Nullable
    DLanguageOutContractExpression getOutContractExpression();
}
