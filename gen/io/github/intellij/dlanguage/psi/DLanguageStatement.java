package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageStatement extends PsiElement {

    @Nullable
    public DLanguageDefaultStatement getDefaultStatement();

    @Nullable
    public DLanguageStatementNoCaseNoDefault getStatementNoCaseNoDefault();

    @Nullable
    public DLanguageCaseStatement getCaseStatement();

    @Nullable
    public DLanguageCaseRangeStatement getCaseRangeStatement();
}
