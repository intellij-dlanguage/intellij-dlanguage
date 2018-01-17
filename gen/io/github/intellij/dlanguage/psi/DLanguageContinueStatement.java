package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.jetbrains.annotations.Nullable;


public interface DLanguageContinueStatement extends PsiElement {

    @Nullable
    public PsiElement getKW_CONTINUE();

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_SCOLON();

}
