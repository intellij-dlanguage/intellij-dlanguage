
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageExpression extends PsiElement {

    @NotNull
    List<DLanguageAssignExpression> getAssignExpressions();

    @NotNull
    List<PsiElement> getOP_COMMAs();

}
