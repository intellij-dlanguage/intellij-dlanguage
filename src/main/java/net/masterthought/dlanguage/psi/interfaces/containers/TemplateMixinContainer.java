package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageTemplateMixinDeclaration;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;

import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getDeclarations;

/**
 * Created by francis on 2/28/2017.
 */
public interface TemplateMixinContainer extends Container {
    Class templateMixinClass = DLanguageTemplateMixinDeclaration.class;


    default <T extends DNamedElement> List<T> getTemplateMixinDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, this, templateMixinClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageTemplateMixinDeclaration> getPublicTemplateMixins(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getTemplateMixinDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageTemplateMixinDeclaration> getProtectedTemplateMixins(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getTemplateMixinDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageTemplateMixinDeclaration> getPrivateTemplateMixins(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getTemplateMixinDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }
}
