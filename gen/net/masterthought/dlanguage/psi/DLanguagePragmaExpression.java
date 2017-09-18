package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguagePragmaExpression extends PsiElement {
    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    DLanguageArgumentList getArgumentList();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_COMMA();

    @Nullable
    PsiElement getKW_PRAGMA();

}
