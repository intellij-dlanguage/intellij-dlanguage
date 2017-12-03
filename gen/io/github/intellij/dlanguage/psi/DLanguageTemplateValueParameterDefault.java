package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateValueParameterDefault extends PsiElement {

    @Nullable
    PsiElement getOP_EQ();

    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    PsiElement getKW___FILE__();

    @Nullable
    PsiElement getKW___FUNCTION__();

    @Nullable
    PsiElement getKW___LINE__();

    @Nullable
    PsiElement getKW___MODULE__();

    @Nullable
    PsiElement getKW___PRETTY_FUNCTION__();

}
