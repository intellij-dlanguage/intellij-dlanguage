package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageOutStatement extends PsiElement {
    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public DLanguageBlockStatement getBlockStatement();

    @Nullable
    public PsiElement getKW_OUT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

}
