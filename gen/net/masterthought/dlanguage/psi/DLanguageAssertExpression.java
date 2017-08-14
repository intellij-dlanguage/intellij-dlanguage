package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageAssertExpression extends PsiElement {
    @Nullable
    PsiElement getKW_ASSERT();

    @Nullable
    PsiElement getOP_BRACES_LEFT();

    @Nullable
    PsiElement getOP_BRACES_RIGHT();

    @NotNull
    List<DLanguageAssignExpression> getAssignExpressions();

    @NotNull
    List<PsiElement> getOP_COMMAs();

}
