package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateThisParameter extends PsiElement {

    @Nullable
    public PsiElement getKW_THIS();

    @Nullable
    public DLanguageTemplateTypeParameter getTemplateTypeParameter();
}
