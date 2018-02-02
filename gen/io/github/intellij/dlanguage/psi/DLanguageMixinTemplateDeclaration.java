package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangTemplateDeclaration;
import org.jetbrains.annotations.Nullable;


public interface DLanguageMixinTemplateDeclaration extends PsiElement {

    @Nullable
    DlangTemplateDeclaration getTemplateDeclaration();

    @Nullable
    PsiElement getKW_MIXIN();

}
