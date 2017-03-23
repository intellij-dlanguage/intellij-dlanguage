package net.masterthought.dlanguage.psi.references.placeholders;

import net.masterthought.dlanguage.psi.DLanguageStructDeclaration;

/**
 * Created by francis on 3/20/2017.
 */
public class StructMembersPlaceHolder implements PlaceHolder {
    private DLanguageStructDeclaration element;

    public StructMembersPlaceHolder(DLanguageStructDeclaration element) {

        this.element = element;
    }

    public DLanguageStructDeclaration getElement() {
        return element;
    }
}
