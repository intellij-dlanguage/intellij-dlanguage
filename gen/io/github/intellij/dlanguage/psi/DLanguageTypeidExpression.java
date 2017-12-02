package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTypeidExpression extends PsiElement {

    @Nullable
    public DLanguageExpression getExpression();

    @Nullable
    public PsiElement getKW_TYPEID();

    @Nullable
    public DLanguageType getType();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

}
