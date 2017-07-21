package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageSwitchStatement extends PsiElement {
    @Nullable
    public PsiElement getKW_SWITCH();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public DLanguageExpression getExpression();

    @Nullable
    public DLanguageStatement getStatement();
}
