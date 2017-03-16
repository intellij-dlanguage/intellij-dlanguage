package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageFuncDeclaration;
import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;


/**
 * Created by franc on 2/28/2017.
 */
public interface FunctionContainer extends Container {

    Class functionClass = DLanguageFuncDeclaration.class;

    default List<DLanguageFuncDeclaration> getFunctionDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return ContainerUtil.getDeclarations(this, this, functionClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageFuncDeclaration> getPropertyFunctions(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getElementsWithAtProperty(getFunctionDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageFuncDeclaration> getPublicFunctions(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getFunctionDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageFuncDeclaration> getProtectedFunctions(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getFunctionDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageFuncDeclaration> getPrivateFunctions(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getFunctionDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

}
