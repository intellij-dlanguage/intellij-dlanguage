package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateValueParameterDefault extends PsiElement {

    @Nullable
    public PsiElement getOP_EQ();

    @Nullable
    public DLanguageAssignExpression getAssignExpression();

    @Nullable
    public PsiElement getKW___FILE__();

    @Nullable
    public PsiElement getKW___FUNCTION__();

    @Nullable
    public PsiElement getKW___LINE__();

    @Nullable
    public PsiElement getKW___MODULE__();

    @Nullable
    public PsiElement getKW___PRETTY_FUNCTION__();

}
