package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateSingleArgument extends PsiElement {
    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    DLanguageType getType();

    @Nullable
    PsiElement getKW_SUPER();

    @Nullable
    PsiElement getKW_THIS();

    @Nullable
    PsiElement getOP_DOLLAR();

    @Nullable
    PsiElement getKW_TRUE();

    @Nullable
    PsiElement getKW_FALSE();

    @Nullable
    PsiElement getKW___DATE__();

    @Nullable
    PsiElement getKW___EOF__();

    @Nullable
    PsiElement getKW___FILE__();

    @Nullable
    PsiElement getKW___FILE_FULL_PATH__();

    @Nullable
    PsiElement getKW___FUNCTION__();

    @Nullable
    PsiElement getKW___GSHARED();

    @Nullable
    PsiElement getKW___LINE__();

    @Nullable
    PsiElement getKW___MODULE__();

    @Nullable
    PsiElement getKW___PARAMETERS();

    @Nullable
    PsiElement getKW___PRETTY_FUNCTION__();

    @Nullable
    PsiElement getKW___TIME__();

    @Nullable
    PsiElement getKW___TIMESTAMP__();

    @Nullable
    PsiElement getKW___TRAITS();

    @Nullable
    PsiElement getKW___VECTOR();

    @Nullable
    PsiElement getKW___VENDOR__();

    @Nullable
    PsiElement getKW___VERSION__();

    @Nullable
    PsiElement getINTEGER_LITERAL();

    @Nullable
    PsiElement getFLOAT_LITERAL();

    @Nullable
    PsiElement getDOUBLE_QUOTED_STRING();

    @Nullable
    PsiElement getCHARACTER_LITERAL();

}
