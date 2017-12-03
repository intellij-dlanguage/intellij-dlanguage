
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDefaultStatement extends PsiElement {

    @Nullable
    PsiElement getKW_DEFAULT();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    DLanguageDeclarationsAndStatements getDeclarationsAndStatements();
}
