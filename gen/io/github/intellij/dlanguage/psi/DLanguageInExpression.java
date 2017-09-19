package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageInExpression extends PsiElement {
    @NotNull
    List<DLanguageShiftExpression> getShiftExpressions();

    @Nullable
    PsiElement getKW_IN();

    @Nullable
    PsiElement getOP_NOT();

}
