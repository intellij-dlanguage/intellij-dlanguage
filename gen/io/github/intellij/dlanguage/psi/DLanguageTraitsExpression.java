package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTraitsExpression extends PsiElement {
    @Nullable
    PsiElement getKW___TRAITS();

    @Nullable
    DLanguageTemplateArgumentList getTemplateArgumentList();

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

}
