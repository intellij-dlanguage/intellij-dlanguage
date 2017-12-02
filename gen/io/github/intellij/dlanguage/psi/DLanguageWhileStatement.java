package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageWhileStatement extends PsiElement {

    @Nullable
    public PsiElement getKW_WHILE();

    @Nullable
    public DLanguageExpression getExpression();

    @Nullable
    public DLanguageDeclarationOrStatement getDeclarationOrStatement();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

}
