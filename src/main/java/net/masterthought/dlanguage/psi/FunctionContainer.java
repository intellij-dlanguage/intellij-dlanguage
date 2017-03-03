package net.masterthought.dlanguage.psi;

import java.util.List;

/**
 * Created by franc on 2/28/2017.
 */
public interface FunctionContainer {
    List<DLanguageFuncDeclaration> getFunctionDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations);

    default List<DLanguageFuncDeclaration> getPropertyFunctions(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        // todo implement functions in func declaration which allow for autofiltering of property functions
    }

    default List<DLanguageFuncDeclaration> getPublicFunctions(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        //todo
    }

    default List<DLanguageFuncDeclaration> getProtectedFunctions(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        //todo
    }

    default List<DLanguageFuncDeclaration> getPrivateFunctions(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        //todo
    }
}
