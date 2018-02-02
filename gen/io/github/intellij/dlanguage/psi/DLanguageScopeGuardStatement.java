
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.jetbrains.annotations.Nullable;


public interface DLanguageScopeGuardStatement extends PsiElement {

    @Nullable
    PsiElement getKW_SCOPE();

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    DLanguageStatementNoCaseNoDefault getStatementNoCaseNoDefault();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

}
