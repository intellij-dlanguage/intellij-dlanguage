package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageBreakStatement extends PsiElement {
    @Nullable
    public PsiElement getKW_BREAK();

    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_SCOLON();

}
