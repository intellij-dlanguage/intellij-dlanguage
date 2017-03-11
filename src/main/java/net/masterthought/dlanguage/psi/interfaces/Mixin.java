package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.DLanguageTemplateDeclaration;
import net.masterthought.dlanguage.psi.DLanguageTemplateMixinDeclaration;

/**
 * Created by francis on 3/9/2017.
 */
public interface Mixin extends PsiElement {
    DLanguageTemplateDeclaration getTemplateDeclaration();

    DLanguageTemplateMixinDeclaration getTemplateMixinDeclaration();
}
