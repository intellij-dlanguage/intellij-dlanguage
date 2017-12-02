package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageSynchronizedStatement extends PsiElement {

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    DLanguageExpression getExpression();

    @Nullable
    DLanguageStatementNoCaseNoDefault getStatementNoCaseNoDefault();

    @Nullable
    PsiElement getKW_SYNCHRONIZED();

}
