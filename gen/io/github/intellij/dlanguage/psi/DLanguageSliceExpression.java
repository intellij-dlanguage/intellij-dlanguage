package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageSliceExpression extends PsiElement {

    @Nullable
    DLanguageUnaryExpression getUnaryExpression();

    @NotNull
    List<DLanguageAssignExpression> getAssignExpressions();

    @Nullable
    PsiElement getOP_BRACKET_LEFT();

    @Nullable
    PsiElement getOP_BRACKET_RIGHT();

    @Nullable
    PsiElement getOP_DDOT();

}
