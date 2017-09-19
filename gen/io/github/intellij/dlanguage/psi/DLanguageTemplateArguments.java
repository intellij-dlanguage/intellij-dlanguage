package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateArguments extends PsiElement {
    @Nullable
    DLanguageTemplateArgumentList getTemplateArgumentList();

    @Nullable
    DLanguageTemplateSingleArgument getTemplateSingleArgument();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_NOT();

}
