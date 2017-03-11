package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageEnumDeclaration;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;

import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getDeclarations;

/**
 * Created by francis on 3/10/2017.
 */
public interface EnumContainer extends Container {
    Class enumClass = DLanguageEnumDeclaration.class;

    default <T extends DNamedElement> List<T> getEnumDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, this, enumClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageEnumDeclaration> getPublicEnumes(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getEnumDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageEnumDeclaration> getProtectedEnumes(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getEnumDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageEnumDeclaration> getPrivateEnumes(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getEnumDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

}
