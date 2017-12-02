package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateMixinDeclaration extends PsiElement {

    @Nullable
    public PsiElement getKW_MIXIN();

    @Nullable
    public DLanguageTemplateDeclaration getTemplateDeclaration();
}
