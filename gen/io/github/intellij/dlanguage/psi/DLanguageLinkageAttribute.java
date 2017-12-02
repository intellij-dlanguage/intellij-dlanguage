package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageLinkageAttribute extends PsiElement {

    @Nullable
    DLanguageIdentifierChain getIdentifierChain();

    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_PLUS_PLUS();

    @Nullable
    PsiElement getKW_EXTERN();

    @Nullable
    PsiElement getOP_COMMA();

}
