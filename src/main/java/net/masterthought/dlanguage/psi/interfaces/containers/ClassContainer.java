package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageClassDeclaration;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;

import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getDeclarations;

/**
 * Created by francis on 2/28/2017.
 */
public interface ClassContainer extends Container {

    Class classClass = DLanguageClassDeclaration.class;//sorry about the confusing name. The class object for class declarations.

    default <T extends DNamedElement> List<T> getClassDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, classClass, this.getClass(), includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageClassDeclaration> getPublicClasses(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getClassDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageClassDeclaration> getProtectedClasses(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getClassDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageClassDeclaration> getPrivateClasses(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getClassDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }
}
