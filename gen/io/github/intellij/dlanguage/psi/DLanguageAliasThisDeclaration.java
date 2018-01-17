package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAliasThisDeclaration extends PsiElement {

    @Nullable
    public PsiElement getKW_ALIAS();

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public PsiElement getKW_THIS();

    @Nullable
    public PsiElement getOP_SCOLON();

}
