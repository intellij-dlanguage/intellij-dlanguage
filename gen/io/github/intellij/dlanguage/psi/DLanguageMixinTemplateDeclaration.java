package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageMixinTemplateDeclaration extends PsiElement {

    @Nullable
    DlangTemplateDeclaration getTemplateDeclaration();

    @Nullable
    PsiElement getKW_MIXIN();

}
