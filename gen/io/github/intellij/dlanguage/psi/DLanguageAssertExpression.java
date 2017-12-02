package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAssertExpression extends PsiElement {

    @Nullable
    public PsiElement getKW_ASSERT();

    @Nullable
    public PsiElement getOP_BRACES_LEFT();

    @Nullable
    public PsiElement getOP_BRACES_RIGHT();

    @NotNull
    public List<DLanguageAssignExpression> getAssignExpressions();

    @NotNull
    public List<PsiElement> getOP_COMMAs();

}
