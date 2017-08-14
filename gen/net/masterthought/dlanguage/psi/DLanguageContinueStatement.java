package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageContinueStatement extends PsiElement {
    @Nullable
    PsiElement getKW_CONTINUE();

    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_SCOLON();

}
