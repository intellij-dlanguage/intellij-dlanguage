package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageEqualExpression extends PsiElement {

    @NotNull
    List<DLanguageShiftExpression> getShiftExpressions();

    @Nullable
    PsiElement getOP_EQ_EQ();

    @Nullable
    PsiElement getOP_NOT_EQ();

}
