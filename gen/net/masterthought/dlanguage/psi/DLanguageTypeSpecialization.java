package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTypeSpecialization extends PsiElement {
    @Nullable
    DLanguageType getType();

    @Nullable
    PsiElement getKW___PARAMETERS();

    @Nullable
    PsiElement getKW_STRUCT();

    @Nullable
    PsiElement getKW_UNION();

    @Nullable
    PsiElement getKW_CLASS();

    @Nullable
    PsiElement getKW_INTERFACE();

    @Nullable
    PsiElement getKW_ENUM();

    @Nullable
    PsiElement getKW_FUNCTION();

    @Nullable
    PsiElement getKW_DELEGATE();

    @Nullable
    PsiElement getKW_SUPER();

    @Nullable
    PsiElement getKW_CONST();

    @Nullable
    PsiElement getKW_IMMUTABLE();

    @Nullable
    PsiElement getKW_INOUT();

    @Nullable
    PsiElement getKW_SHARED();

    @Nullable
    PsiElement getKW_RETURN();

}
