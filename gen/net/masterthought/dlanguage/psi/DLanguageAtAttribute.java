package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAtAttribute extends PsiElement {
    @Nullable
    public PsiElement getOP_AT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public DLanguageArgumentList getArgumentList();

    @Nullable
    public DLanguageFunctionCallExpression getFunctionCallExpression();
}
