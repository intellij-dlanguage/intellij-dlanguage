
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.jetbrains.annotations.Nullable;


public interface DLanguageBreakStatement extends PsiElement {

    @Nullable
    PsiElement getKW_BREAK();

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_SCOLON();

}
