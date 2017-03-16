package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageImport;

import java.util.List;

import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getDeclarations;

/**
 * Created by francis on 3/15/2017.
 */
public interface ImportContainer extends Container {
    Class importClass = DLanguageImport.class;//sorry about the confusing name. The class object for class declarations.

    default <T extends DLanguageImport> List<T> getImportDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, this, importClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }
}
