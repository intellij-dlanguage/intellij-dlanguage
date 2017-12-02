package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageWhileStatement extends PsiElement {

    @Nullable
    PsiElement getKW_WHILE();

    @Nullable
    DLanguageExpression getExpression();

    @Nullable
    DLanguageDeclarationOrStatement getDeclarationOrStatement();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

}
