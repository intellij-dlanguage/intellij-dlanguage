package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageGotoStatement extends PsiElement {
    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    DLanguageExpression getExpression();

    @Nullable
    PsiElement getKW_DEFAULT();

    @Nullable
    PsiElement getKW_CASE();

    @Nullable
    PsiElement getKW_GOTO();

    @Nullable
    PsiElement getOP_SCOLON();

}
