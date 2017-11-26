package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAttribute extends PsiElement {
    @Nullable
    DLanguagePragmaExpression getPragmaExpression();

    @Nullable
    PsiElement getKW_SYNCHRONIZED();

    @Nullable
    PsiElement getKW_ABSTRACT();

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

    @Nullable
    PsiElement getKW_EXPORT();

    @Nullable
    PsiElement getKW_CONST();

    @Nullable
    PsiElement getKW_PRIVATE();

    @Nullable
    PsiElement getKW_PROTECTED();

    @Nullable
    PsiElement getKW_PUBLIC();

    @Nullable
    DLanguageAlignAttribute getAlignAttribute();

    @Nullable
    DLanguageDeprecated getDeprecated();

    @Nullable
    DLanguageAtAttribute getAtAttribute();

    @Nullable
    PsiElement getKW_PACKAGE();

    @Nullable
    DLanguageIdentifierChain getIdentifierChain();

    @Nullable
    DLanguageLinkageAttribute getLinkageAttribute();
}
