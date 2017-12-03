package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageStorageClass extends PsiElement {

    @Nullable
    DLanguageAtAttribute getAtAttribute();

    @Nullable
    DLanguageDeprecated getDeprecated();

    @Nullable
    DLanguageAlignAttribute getAlignAttribute();

    @Nullable
    DLanguageLinkageAttribute getLinkageAttribute();

    @Nullable
    PsiElement getKW_SYNCHRONIZED();

    @Nullable
    DLanguageTypeConstructor getTypeConstructor();

    @Nullable
    PsiElement getKW_ABSTRACT();

    @Nullable
    PsiElement getKW_CONST();

    @Nullable
    PsiElement getKW_IMMUTABLE();

    @Nullable
    PsiElement getKW_AUTO();

    @Nullable
    PsiElement getKW_ENUM();

    @Nullable
    PsiElement getKW_EXTERN();

    @Nullable
    PsiElement getKW_FINAL();

    @Nullable
    PsiElement getKW_INOUT();

    @Nullable
    PsiElement getKW_NOTHROW();

    @Nullable
    PsiElement getKW_OVERRIDE();

    @Nullable
    PsiElement getKW_PURE();

    @Nullable
    PsiElement getKW_REF();

    @Nullable
    PsiElement getKW___GSHARED();

    @Nullable
    PsiElement getKW_SCOPE();

    @Nullable
    PsiElement getKW_STATIC();

}
