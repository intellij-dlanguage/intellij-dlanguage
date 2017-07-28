package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageContinueStatement extends PsiElement {
    @Nullable
    public PsiElement getKW_CONTINUE();

    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_SCOLON();

}
