package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.jetbrains.annotations.Nullable;


public interface DLanguageLinkageAttribute extends PsiElement {

    @Nullable
    public DLanguageIdentifierChain getIdentifierChain();

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public PsiElement getOP_PLUS_PLUS();

    @Nullable
    public PsiElement getKW_EXTERN();

    @Nullable
    public PsiElement getOP_COMMA();

}
