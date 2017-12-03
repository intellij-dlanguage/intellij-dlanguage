
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageInExpression extends PsiElement {

    @NotNull
    List<DLanguageShiftExpression> getShiftExpressions();

    @Nullable
    PsiElement getKW_IN();

    @Nullable
    PsiElement getOP_NOT();

}
