package net.masterthought.dlanguage.psi.references.placeholders;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.DLanguageTemplateMixinDeclaration;

/**
 * Created by francis on 3/20/2017.
 */
public class TemplateMixinMembersPlaceHolder implements PlaceHolder {
    private DLanguageTemplateMixinDeclaration element;

    public TemplateMixinMembersPlaceHolder(DLanguageTemplateMixinDeclaration element) {
        this.element = element;
    }

    @Override
    public PsiElement getElement() {
        return element;
    }
}
