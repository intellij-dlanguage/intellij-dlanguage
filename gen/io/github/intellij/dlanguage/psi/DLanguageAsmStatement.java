package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmStatement extends PsiElement {

    @Nullable
    public PsiElement getKW_ASM();

    @Nullable
    public PsiElement getOP_BRACES_LEFT();

    @Nullable
    public PsiElement getOP_BRACES_RIGHT();

    @NotNull
    public List<DLanguageAsmInstruction> getAsmInstructions();
}
