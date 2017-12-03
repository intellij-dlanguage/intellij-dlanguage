package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


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
