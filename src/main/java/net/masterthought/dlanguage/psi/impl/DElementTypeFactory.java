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
        if (name.equals("MODULE_DECLARATION")) return new DLanguageModuleDeclarationStubElementType(name);
        if (name.equals("INTERFACE_DECLARATION")) return new DLanguageInterfaceDeclarationStubElementType(name);
        if (name.equals("VAR_DECLARATIONS")) return new DLanguageVarDeclaratorStubElementType(name);
        if (name.equals("LABELED_STATEMENT")) return new DLanguageLabeledStatementStubElementType(name);
        if (name.equals("TEMPLATE_MIXIN_DECLARATION"))
            return new DLanguageTemplateMixinDeclarationStubElementType(name);
        if (name.equals("SHARED_STATIC_CONSTRUCTOR")) return new DLanguageSharedStaticConstructorStubElementType(name);
        if (name.equals("SHARED_STATIC_DESTRUCTOR")) return new DLanguageSharedStaticDestructorStubElementType(name);
        if (name.equals("STATIC_CONSTRUCTOR")) return new DLanguageStaticConstructorStubElementType(name);
        if (name.equals("STATIC_DESTRUCTOR")) return new DLanguageStaticDestructorStubElementType(name);
            throw new RuntimeException("Unknown element type: " + name);
    }
}
