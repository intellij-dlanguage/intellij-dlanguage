package net.masterthought.dlanguage.psi.impl;

import com.intellij.psi.tree.IElementType;
import net.masterthought.dlanguage.stubs.types.*;
import org.jetbrains.annotations.NotNull;

public class DElementTypeFactory {
    private DElementTypeFactory(){}

    public static IElementType factory(@NotNull String name){
        if (name.equals("IDENTIFIER")) return new DLanguageIdentifierStubElementType(name);
        if (name.equals("FUNC_DECLARATION")) return new DLanguageFuncDeclarationStubElementType(name);
        if (name.equals("CLASS_DECLARATION")) return new DLanguageClassDeclarationStubElementType(name);
        if (name.equals("TEMPLATE_DECLARATION")) return new DLanguageTemplateDeclarationStubElementType(name);
        if (name.equals("CONSTRUCTOR")) return new DLanguageConstructorStubElementType(name);
        if (name.equals("DESTRUCTOR")) return new DLanguageDestructorStubElementType(name);
        if (name.equals("STRUCT_DECLARATION")) return new DLanguageStructDeclarationStubElementType(name);
        if (name.equals("ALIAS_DECLARATION")) return new DLanguageAliasDeclarationStubElementType(name);
        throw new RuntimeException("Unknown element type: " + name);
    }
}
