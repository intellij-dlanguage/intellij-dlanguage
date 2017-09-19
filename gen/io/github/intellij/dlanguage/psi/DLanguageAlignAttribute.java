package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAlignAttribute extends PsiElement {
    @Nullable
    PsiElement getKW_ALIGN();

    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

}
