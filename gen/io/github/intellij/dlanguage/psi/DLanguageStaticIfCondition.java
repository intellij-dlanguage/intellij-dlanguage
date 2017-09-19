package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageStaticIfCondition extends PsiElement {
    @Nullable
    DLanguageStaticIfCondition getStaticIfCondition();

    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

}
