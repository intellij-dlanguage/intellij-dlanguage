package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageWithStatement extends PsiElement {

    @Nullable
    public PsiElement getKW_WITH();

    @Nullable
    public DLanguageExpression getExpression();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public DLanguageStatementNoCaseNoDefault getStatementNoCaseNoDefault();
}
