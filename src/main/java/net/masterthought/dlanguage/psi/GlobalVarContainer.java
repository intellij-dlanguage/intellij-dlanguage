package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;

/**
 * Created by francis on 2/28/2017.
 */
public interface GlobalVarContainer {
    List<VariableDeclaration> getVariableDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations);

    default List<VariableDeclaration> getPublicVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<VariableDeclaration> getProtectedVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<VariableDeclaration> getPrivateVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

}
