package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageIdentityExpression extends PsiElement {
    @NotNull
    List<DLanguageShiftExpression> getShiftExpressions();

    @Nullable
    PsiElement getKW_IS();

    @Nullable
    PsiElement getOP_NOT();

}
