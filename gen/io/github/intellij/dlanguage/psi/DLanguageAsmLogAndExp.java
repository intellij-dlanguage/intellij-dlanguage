
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmLogAndExp extends PsiElement {

    @Nullable
    DLanguageAsmLogAndExp getAsmLogAndExp();

    @Nullable
    DLanguageAsmOrExp getAsmOrExp();

    @Nullable
    PsiElement getOP_BOOL_AND();

}
