package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface DLanguageExpression extends PsiElement {
    @NotNull
    List<DLanguageAssignExpression> getAssignExpressions();

    @NotNull
    List<PsiElement> getOP_COMMAs();

}
