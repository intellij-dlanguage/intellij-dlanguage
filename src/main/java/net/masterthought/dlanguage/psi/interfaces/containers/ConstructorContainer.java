package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageConstructor;
import net.masterthought.dlanguage.psi.DLanguageFuncDeclaration;
import net.masterthought.dlanguage.psi.DLanguageSharedStaticConstructor;
import net.masterthought.dlanguage.psi.DLanguageStaticConstructor;
import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;

import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getDeclarations;

/**
 * Created by francis on 3/8/2017.
 */
public interface ConstructorContainer extends Container {
    Class staticsharedConstructor = DLanguageSharedStaticConstructor.class;
    Class staticConstructor = DLanguageStaticConstructor.class;
    Class constructor = DLanguageConstructor.class;

    default List<DLanguageFuncDeclaration> getConstructorDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, constructor, this.getClass(), includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageFuncDeclaration> getStaticConstructorDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, staticConstructor, this.getClass(), includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageFuncDeclaration> getStaticSharedConstructorDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, staticsharedConstructor, this.getClass(), includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageFuncDeclaration> getPublicConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageFuncDeclaration> getProtectedConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageFuncDeclaration> getPrivateConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageFuncDeclaration> getPublicStaticConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getStaticConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageFuncDeclaration> getProtectedStaticConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getStaticConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageFuncDeclaration> getPrivateStaticConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getStaticConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageFuncDeclaration> getPublicStaticSharedConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getStaticSharedConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageFuncDeclaration> getProtectedStaticSharedConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getStaticSharedConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageFuncDeclaration> getPrivateStaticSharedConstructors(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getStaticSharedConstructorDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

}
