package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateParameters extends PsiElement {
    @Nullable
    DLanguageTemplateParameterList getTemplateParameterList();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

}
