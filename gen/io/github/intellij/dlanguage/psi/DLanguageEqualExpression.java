package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageEqualExpression extends PsiElement {

    @NotNull
    public List<DLanguageShiftExpression> getShiftExpressions();

    @Nullable
    public PsiElement getOP_EQ_EQ();

    @Nullable
    public PsiElement getOP_NOT_EQ();

}
