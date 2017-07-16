package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAliasThisDeclaration extends PsiElement {
    @Nullable
    public PsiElement getKW_ALIAS();

    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public PsiElement getKW_THIS();

    @Nullable
    public PsiElement getOP_SCOLON();

}
