package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageConstructor;
import net.masterthought.dlanguage.psi.DLanguageSharedStaticConstructor;
import net.masterthought.dlanguage.psi.DLanguageStaticConstructor;
import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;

import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getDeclarations;

/**
 * Created by francis on 3/8/2017.
 */
public interface ConstructorContainer extends Container {
    Class staticsharedConstructorClass = DLanguageSharedStaticConstructor.class;
    Class staticConstructorClass = DLanguageStaticConstructor.class;
    Class constructorClass = DLanguageConstructor.class;

    default List<DLanguageConstructor> getConstructorDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, this, constructorClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageStaticConstructor> getStaticConstructorDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, this, staticConstructorClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageSharedStaticConstructor> getStaticSharedConstructorDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, this, staticsharedConstructorClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageConstructor> getPublicConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageConstructor> getProtectedConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageConstructor> getPrivateConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageStaticConstructor> getPublicStaticConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getStaticConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageStaticConstructor> getProtectedStaticConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getStaticConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageStaticConstructor> getPrivateStaticConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getStaticConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageSharedStaticConstructor> getPublicStaticSharedConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getStaticSharedConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageSharedStaticConstructor> getProtectedStaticSharedConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getStaticSharedConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageSharedStaticConstructor> getPrivateStaticSharedConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getStaticSharedConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

}
