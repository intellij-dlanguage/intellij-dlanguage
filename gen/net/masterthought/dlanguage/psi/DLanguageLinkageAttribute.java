package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageLinkageAttribute extends PsiElement {
    @Nullable
    public DLanguageIdentifierChain getIdentifierChain();

    @Nullable
    public DLanguageIdentifier getIdentifier();

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
