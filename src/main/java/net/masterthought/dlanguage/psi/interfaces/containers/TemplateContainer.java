package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageTemplateDeclaration;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;

import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getDeclarations;

/**
 * Created by francis on 2/28/2017.
 *
 */
public interface TemplateContainer extends Container {
    Class templateClass = DLanguageTemplateDeclaration.class;

    default <T extends DNamedElement> List<T> getTemplateDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, this, templateClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageTemplateDeclaration> getPublicTemplates(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getTemplateDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageTemplateDeclaration> getProtectedTemplates(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getTemplateDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageTemplateDeclaration> getPrivateTemplates(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getTemplateDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

}
