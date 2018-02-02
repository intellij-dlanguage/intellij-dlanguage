package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmUnaExp extends PsiElement {

    @Nullable
    public DLanguageAsmUnaExp getAsmUnaExp();

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public DLanguageAsmExp getAsmExp();

    @Nullable
    public PsiElement getOP_PLUS();

    @Nullable
    public PsiElement getOP_MINUS();

    @Nullable
    public PsiElement getOP_NOT();

    @Nullable
    public PsiElement getOP_TILDA();

    @Nullable
    public DLanguageAsmPrimaryExp getAsmPrimaryExp();
}
