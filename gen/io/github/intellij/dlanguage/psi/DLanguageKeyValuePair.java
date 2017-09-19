package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageKeyValuePair extends PsiElement {
    @NotNull
    List<DLanguageAssignExpression> getAssignExpressions();

    @Nullable
    PsiElement getOP_COLON();

}
