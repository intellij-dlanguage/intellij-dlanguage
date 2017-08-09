package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageMixinTemplateDeclaration extends PsiElement {
    @Nullable
    DLanguageTemplateDeclaration getTemplateDeclaration();

    @Nullable
    PsiElement getKW_MIXIN();

}
