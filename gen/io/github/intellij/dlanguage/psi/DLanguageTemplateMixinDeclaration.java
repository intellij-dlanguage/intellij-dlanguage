package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangTemplateDeclaration;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateMixinDeclaration extends PsiElement {

    @Nullable
    PsiElement getKW_MIXIN();

    @Nullable
    DlangTemplateDeclaration getTemplateDeclaration();
}
