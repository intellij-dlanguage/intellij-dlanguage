package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageUnionDeclaration;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;

import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getDeclarations;

/**
 * Created by francis on 3/10/2017.
 */
public interface UnionContainer extends Container {
    Class unionClass = DLanguageUnionDeclaration.class;

    default <T extends DNamedElement> List<T> getUnionDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, this, unionClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageUnionDeclaration> getPublicUniones(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getUnionDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageUnionDeclaration> getProtectedUniones(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getUnionDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageUnionDeclaration> getPrivateUniones(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getUnionDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

}
