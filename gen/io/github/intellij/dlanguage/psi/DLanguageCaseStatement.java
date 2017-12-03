
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageCaseStatement extends PsiElement {

    @Nullable
    PsiElement getKW_CASE();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    DLanguageArgumentList getArgumentList();

    @Nullable
    DLanguageDeclarationsAndStatements getDeclarationsAndStatements();
}
