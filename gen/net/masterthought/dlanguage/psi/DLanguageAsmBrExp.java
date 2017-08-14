package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmBrExp extends PsiElement {
    @Nullable
    DLanguageAsmExp getAsmExp();

    @Nullable
    DLanguageAsmUnaExp getAsmUnaExp();

    @Nullable
    DLanguageAsmBrExp getAsmBrExp();

    @Nullable
    PsiElement getOP_BRACKET_RIGHT();

    @Nullable
    PsiElement getOP_BRACKET_LEFT();

}
