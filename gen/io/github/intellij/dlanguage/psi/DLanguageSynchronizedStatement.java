package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageSynchronizedStatement extends PsiElement {

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public DLanguageExpression getExpression();

    @Nullable
    public DLanguageStatementNoCaseNoDefault getStatementNoCaseNoDefault();

    @Nullable
    public PsiElement getKW_SYNCHRONIZED();

}
