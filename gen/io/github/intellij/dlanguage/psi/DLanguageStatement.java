package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageStatement extends PsiElement {
    @Nullable
    DLanguageDefaultStatement getDefaultStatement();

    @Nullable
    DLanguageStatementNoCaseNoDefault getStatementNoCaseNoDefault();

    @Nullable
    DLanguageCaseStatement getCaseStatement();

    @Nullable
    DLanguageCaseRangeStatement getCaseRangeStatement();
}
