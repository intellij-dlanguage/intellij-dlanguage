package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageOperands extends PsiElement {

    @Nullable
    public PsiElement getOP_COMMA();

    @Nullable
    public DLanguageOperands getOperands();

    @Nullable
    public DLanguageAsmExp getAsmExp();
}
