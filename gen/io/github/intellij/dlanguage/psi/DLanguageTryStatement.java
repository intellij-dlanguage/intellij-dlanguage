
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTryStatement extends PsiElement {

    @Nullable
    PsiElement getKW_TRY();

    @Nullable
    DLanguageDeclarationOrStatement getDeclarationOrStatement();

    @Nullable
    DLanguageCatches getCatches();

    @Nullable
    DLanguageFinally getFinally();
}
