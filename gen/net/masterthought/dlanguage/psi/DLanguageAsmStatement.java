package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageAsmStatement extends PsiElement {
    @Nullable
    PsiElement getKW_ASM();

    @Nullable
    PsiElement getOP_BRACES_LEFT();

    @Nullable
    PsiElement getOP_BRACES_RIGHT();

    @NotNull
    List<DLanguageAsmInstruction> getAsmInstructions();
}
