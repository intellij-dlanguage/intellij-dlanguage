package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageStructDeclaration;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;

import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getDeclarations;

/**
 * Created by francis on 2/28/2017.
 */
public interface StructContainer extends Container {

    Class structClass = DLanguageStructDeclaration.class;

    default <T extends DNamedElement> List<T> getStructDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, this, structClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageStructDeclaration> getPublicStructs(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getStructDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageStructDeclaration> getProtectedStructs(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getStructDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageStructDeclaration> getPrivateStructs(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getStructDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }


}
