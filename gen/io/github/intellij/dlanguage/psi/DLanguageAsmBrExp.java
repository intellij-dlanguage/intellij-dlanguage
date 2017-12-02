package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmBrExp extends PsiElement {

    @Nullable
    public DLanguageAsmExp getAsmExp();

    @Nullable
    public DLanguageAsmUnaExp getAsmUnaExp();

    @Nullable
    public DLanguageAsmBrExp getAsmBrExp();

    @Nullable
    public PsiElement getOP_BRACKET_RIGHT();

    @Nullable
    public PsiElement getOP_BRACKET_LEFT();

}
