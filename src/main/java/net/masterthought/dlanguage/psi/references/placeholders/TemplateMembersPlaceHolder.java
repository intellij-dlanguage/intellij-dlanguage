package net.masterthought.dlanguage.psi.references.placeholders;

import net.masterthought.dlanguage.psi.DLanguageTemplateDeclaration;

/**
 * Created by francis on 3/20/2017.
 */
public class TemplateMembersPlaceHolder implements PlaceHolder {
    private DLanguageTemplateDeclaration element;

    public TemplateMembersPlaceHolder(DLanguageTemplateDeclaration element) {

        this.element = element;
    }

    public DLanguageTemplateDeclaration getElement() {
        return element;
    }
}
