package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;

/**
 * Created by francis on 2/28/2017.
 */
public interface StructContainer {
    List<DLanguageStructDeclaration> getStructDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations);

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
