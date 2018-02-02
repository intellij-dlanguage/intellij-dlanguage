package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.jetbrains.annotations.Nullable;


public interface DLanguagePragmaExpression extends PsiElement {

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public DLanguageArgumentList getArgumentList();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_COMMA();

    @Nullable
    public PsiElement getKW_PRAGMA();

}
