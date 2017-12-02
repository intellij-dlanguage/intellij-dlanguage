package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmExp extends PsiElement {

    @Nullable
    public DLanguageAsmLogOrExp getAsmLogOrExp();

    @Nullable
    public DLanguageAsmExp getAsmExp();

    @Nullable
    public PsiElement getOP_QUEST();

    @Nullable
    public PsiElement getOP_COLON();

}
