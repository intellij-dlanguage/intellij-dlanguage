package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageTemplateDeclaration;

/**
 * Created by francis on 2/28/2017.
 *
 */
public interface TemplateContainer extends Container {
    Class templateClass = DLanguageTemplateDeclaration.class;

}
