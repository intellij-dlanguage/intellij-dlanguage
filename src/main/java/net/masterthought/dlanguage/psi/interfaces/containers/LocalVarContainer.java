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
public interface LocalVarContainer extends Container {
    Class autoDeclarationClass = DLanguageAutoDeclarationY.class;
    Class declaratorInitializer = DLanguageDeclaratorInitializer.class;

    default List<VariableDeclaration> getLocalVariableDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        List<VariableDeclaration> res = new ArrayList<>();
        res.addAll(getLocalAutoVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
        res.addAll(getLocalNonAutoDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
        return res;
    }

    default List<DLanguageAutoDeclarationY> getLocalAutoVariableDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, autoDeclarationClass, this.getClass(), includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<DLanguageDeclaratorInitializer> getLocalNonAutoDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, declaratorInitializer, this.getClass(), includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default List<VariableDeclaration> getLocalPublicVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getPublicElements(getLocalVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<VariableDeclaration> getLocalProtectedVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getProtectedElements(getLocalVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<VariableDeclaration> getLocalPrivateVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getPrivateElements(getLocalVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageAutoDeclarationY> getLocalPublicAutoVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getPublicElements(getLocalAutoVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageAutoDeclarationY> getLocalProtectedAutoVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getProtectedElements(getLocalAutoVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageAutoDeclarationY> getLocalPrivateAutoVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getPrivateElements(getLocalAutoVariableDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageDeclaratorInitializer> getLocalPublicNonAutoVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getPublicElements(getLocalNonAutoDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageDeclaratorInitializer> getLocalProtectedNonAutoVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getProtectedElements(getLocalNonAutoDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageDeclaratorInitializer> getLocalPrivateNonAutoVariables(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getPrivateElements(getLocalNonAutoDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

}
