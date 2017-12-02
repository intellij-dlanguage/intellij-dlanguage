package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageLastCatch extends PsiElement {

    @Nullable
    PsiElement getKW_CATCH();

    @Nullable
    DLanguageStatementNoCaseNoDefault getStatementNoCaseNoDefault();
}
