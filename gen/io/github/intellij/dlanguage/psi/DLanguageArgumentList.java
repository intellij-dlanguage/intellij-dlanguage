package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageArgumentList extends PsiElement {

    @NotNull
    public List<DLanguageAssignExpression> getAssignExpressions();

    @NotNull
    public List<PsiElement> getOP_COMMAs();

}
