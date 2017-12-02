package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTypeofExpression extends PsiElement {

    @Nullable
    public DLanguageExpression getExpression();

    @Nullable
    public PsiElement getKW_TYPEOF();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public PsiElement getKW_RETURN();

}
