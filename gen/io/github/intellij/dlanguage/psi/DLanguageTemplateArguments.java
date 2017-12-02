package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateArguments extends PsiElement {

    @Nullable
    public DLanguageTemplateArgumentList getTemplateArgumentList();

    @Nullable
    public DLanguageTemplateSingleArgument getTemplateSingleArgument();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public PsiElement getOP_NOT();

}
