package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageReturnStatement extends PsiElement {
    @Nullable
    public PsiElement getKW_RETURN();

    @Nullable
    public DLanguageExpression getExpression();

    @Nullable
    public PsiElement getOP_SCOLON();

}
