package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageInterfaceDeclaration;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;

import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getDeclarations;

/**
 * Created by francis on 3/10/2017.
 */
public interface InterfaceContainer extends Container {
    Class interfaceClass = DLanguageInterfaceDeclaration.class;

    default <T extends DNamedElement> List<T> getInterfaceDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, interfaceClass, this.getClass(), includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageInterfaceDeclaration> getPublicInterfacees(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getInterfaceDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageInterfaceDeclaration> getProtectedInterfacees(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getInterfaceDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageInterfaceDeclaration> getPrivateInterfacees(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getInterfaceDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }


}
