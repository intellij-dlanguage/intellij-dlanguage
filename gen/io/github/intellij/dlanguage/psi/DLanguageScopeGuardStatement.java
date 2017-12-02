package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageScopeGuardStatement extends PsiElement {

    @Nullable
    public PsiElement getKW_SCOPE();

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public DLanguageStatementNoCaseNoDefault getStatementNoCaseNoDefault();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

}
