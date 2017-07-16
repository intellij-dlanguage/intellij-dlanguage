package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageSliceExpression extends PsiElement {
    @Nullable
    public DLanguageUnaryExpression getUnaryExpression();

    @NotNull
    public List<DLanguageAssignExpression> getAssignExpressions();

    @Nullable
    public PsiElement getOP_BRACKET_LEFT();

    @Nullable
    public PsiElement getOP_BRACKET_RIGHT();

    @Nullable
    public PsiElement getOP_DDOT();

}
