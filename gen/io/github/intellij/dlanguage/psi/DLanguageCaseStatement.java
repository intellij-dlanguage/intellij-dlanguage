package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageCaseStatement extends PsiElement {

    @Nullable
    public PsiElement getKW_CASE();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public DLanguageArgumentList getArgumentList();

    @Nullable
    public DLanguageDeclarationsAndStatements getDeclarationsAndStatements();
}
