package net.masterthought.dlanguage.psi.references.placeholders;

import net.masterthought.dlanguage.psi.DLanguageInterfaceDeclaration;

/**
 * Created by francis on 3/20/2017.
 */
public class InterfaceMembersPlaceHolder implements PlaceHolder {

    private DLanguageInterfaceDeclaration element;

    public InterfaceMembersPlaceHolder(DLanguageInterfaceDeclaration element) {

        this.element = element;
    }

    public DLanguageInterfaceDeclaration getElement() {
        return element;
    }
}
