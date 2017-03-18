package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageInterfaceDeclaration;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.Mixin;
import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;

import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getDeclarations;

/**
 * Created by francis on 3/17/2017.
 */
public interface MixinContainer extends Container {
    Class mixinClass = Mixin.class;

    default <T extends DNamedElement> List<T> getMixins(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, this, mixinClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageInterfaceDeclaration> getPublicMixins(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getMixins(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageInterfaceDeclaration> getProtectedMixins(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getMixins(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageInterfaceDeclaration> getPrivateMixins(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getMixins(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }
}
