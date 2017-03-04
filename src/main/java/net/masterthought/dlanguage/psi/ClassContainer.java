package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;

/**
 * Created by francis on 2/28/2017.
 */
public interface ClassContainer {
    List<DLanguageClassDeclaration> getClassDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations);

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
