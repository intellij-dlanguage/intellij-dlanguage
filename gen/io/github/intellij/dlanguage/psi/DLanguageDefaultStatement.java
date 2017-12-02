package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDefaultStatement extends PsiElement {

    @Nullable
    public PsiElement getKW_DEFAULT();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public DLanguageDeclarationsAndStatements getDeclarationsAndStatements();
}
