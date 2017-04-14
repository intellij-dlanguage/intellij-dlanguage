package net.masterthought.dlanguage.psi.references.placeholders;

import net.masterthought.dlanguage.psi.DLanguageClassDeclaration;

/**
 * Created by francis on 3/20/2017.
 */
public class ClassMembersPlaceHolder implements PlaceHolder {
    private DLanguageClassDeclaration element;

    public ClassMembersPlaceHolder(DLanguageClassDeclaration element) {

        this.element = element;
    }

    public DLanguageClassDeclaration getElement() {
        return element;
    }
}
