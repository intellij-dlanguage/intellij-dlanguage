package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAttribute extends PsiElement {

    @Nullable
    public DLanguagePragmaExpression getPragmaExpression();

    @Nullable
    public PsiElement getKW_SYNCHRONIZED();

    @Nullable
    public PsiElement getKW_ABSTRACT();

    @Nullable
    public PsiElement getKW_AUTO();

    @Nullable
    public PsiElement getKW_ENUM();

    @Nullable
    public PsiElement getKW_EXTERN();

    @Nullable
    public PsiElement getKW_FINAL();

    @Nullable
    public PsiElement getKW_INOUT();

    @Nullable
    public PsiElement getKW_NOTHROW();

    @Nullable
    public PsiElement getKW_OVERRIDE();

    @Nullable
    public PsiElement getKW_PURE();

    @Nullable
    public PsiElement getKW_REF();

    @Nullable
    public PsiElement getKW___GSHARED();

    @Nullable
    public PsiElement getKW_SCOPE();

    @Nullable
    public PsiElement getKW_STATIC();

    @Nullable
    public PsiElement getKW_EXPORT();

    @Nullable
    public PsiElement getKW_PRIVATE();

    @Nullable
    public PsiElement getKW_PROTECTED();

    @Nullable
    public PsiElement getKW_PUBLIC();

    @Nullable
    public DLanguageAlignAttribute getAlignAttribute();

    @Nullable
    public DLanguageDeprecated getDeprecated();

    @Nullable
    public DLanguageAtAttribute getAtAttribute();

    @Nullable
    public PsiElement getKW_PACKAGE();

    @Nullable
    public DLanguageIdentifierChain getIdentifierChain();

    @Nullable
    public DLanguageLinkageAttribute getLinkageAttribute();

    @Nullable
    public PsiElement getKW_CONST();

}
