package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageAutoDeclarationY;
import net.masterthought.dlanguage.psi.DLanguageDeclaratorInitializer;
import net.masterthought.dlanguage.psi.interfaces.VariableDeclaration;

import java.util.ArrayList;
import java.util.List;

import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getDeclarations;
import static net.masterthought.dlanguage.utils.DUtil.*;

/**
 * Created by francis on 2/28/2017.
 */
public interface GlobalVarContainer extends Container {
    Class globalVarClass = VariableDeclaration.class;

    Class autoDeclarationClass = DLanguageAutoDeclarationY.class;

    Class declaratorInitializer = DLanguageDeclaratorInitializer.class;

    default List<VariableDeclaration> getGlobalVariableDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        List<VariableDeclaration> res = new ArrayList<>();
        res.addAll(getGlobalAutoVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
        res.addAll(getGlobalNonAutoDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
        return res;
    }

    default List<DLanguageAutoDeclarationY> getGlobalAutoVariableDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, this, autoDeclarationClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageDeclaratorInitializer> getGlobalNonAutoDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, this, declaratorInitializer, includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }


    default List<VariableDeclaration> getGlobalPublicVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getPublicElements(getGlobalVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<VariableDeclaration> getGlobalProtectedVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getProtectedElements(getGlobalVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<VariableDeclaration> getGlobalPrivateVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getPrivateElements(getGlobalVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageAutoDeclarationY> getGlobalPublicAutoVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getPublicElements(getGlobalAutoVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageAutoDeclarationY> getGlobalProtectedAutoVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getProtectedElements(getGlobalAutoVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageAutoDeclarationY> getGlobalPrivateAutoVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getPrivateElements(getGlobalAutoVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageDeclaratorInitializer> getGlobalPublicNonAutoVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getPublicElements(getGlobalNonAutoDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageDeclaratorInitializer> getGlobalProtectedNonAutoVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getProtectedElements(getGlobalNonAutoDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageDeclaratorInitializer> getGlobalPrivateNonAutoVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getPrivateElements(getGlobalNonAutoDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

}
