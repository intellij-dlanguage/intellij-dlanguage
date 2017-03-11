package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageAliasDeclaration;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;

import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getDeclarations;

/**
 * Created by francis on 3/4/2017.
 */
public interface AliasContainer extends Container {

    Class aliasClass = DLanguageAliasDeclaration.class;

    default <T extends DNamedElement> List<T> getAliasDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, this, aliasClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }


    default List<DLanguageAliasDeclaration> getPrivateAliasDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getAliasDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageAliasDeclaration> getPublicAliasDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getAliasDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageAliasDeclaration> getProtectedAliasDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getAliasDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }
}
