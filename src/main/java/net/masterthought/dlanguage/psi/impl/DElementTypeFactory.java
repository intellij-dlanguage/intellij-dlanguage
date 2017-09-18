package net.masterthought.dlanguage.psi.impl;

import com.intellij.psi.tree.IElementType;
import net.masterthought.dlanguage.stubs.types.*;
import org.jetbrains.annotations.NotNull;

public class DElementTypeFactory {
    private DElementTypeFactory() {
    }

    @NotNull
    public static IElementType factory(@NotNull final String name) {
        if (name.equals("IDENTIFIER")) return new IdentifierStubElementType(name);
        if (name.equals("FUNCTION_DECLARATION")) return new FunctionDeclarationStubElementType(name);
        if (name.equals("INTERFACE_OR_CLASS")) return new InterfaceOrClassStubElementType(name);
        if (name.equals("TEMPLATE_DECLARATION")) return new DlangTemplateDeclarationStubElementType(name);
        if (name.equals("CONSTRUCTOR")) return new ConstructorStubElementType(name);
        if (name.equals("DESTRUCTOR")) return new DestructorStubElementType(name);
        if (name.equals("STRUCT_DECLARATION")) return new StructDeclarationStubElementType(name);
        if (name.equals("ALIAS_INITIALIZER")) return new AliasInitializerStubElementType(name);
        if (name.equals("MODULE_DECLARATION")) return new ModuleDeclarationStubElementType(name);
        if (name.equals("DECLARATOR")) return new DeclaratorStubElementType(name);
        if (name.equals("LABELED_STATEMENT")) return new LabeledStatementStubElementType(name);
        if (name.equals("SHARED_STATIC_CONSTRUCTOR")) return new SharedStaticConstructorStubElementType(name);
        if (name.equals("SHARED_STATIC_DESTRUCTOR")) return new SharedStaticDestructorStubElementType(name);
        if (name.equals("STATIC_CONSTRUCTOR")) return new StaticConstructorStubElementType(name);
        if (name.equals("STATIC_DESTRUCTOR")) return new StaticDestructorStubElementType(name);
        if (name.equals("AUTO_DECLARATION_PART")) return new AutoDeclarationPartStubElementType(name);
        if (name.equals("ENUM_DECLARATION")) return new EnumDeclarationStubElementType(name);
        if (name.equals("UNION_DECLARATION")) return new DlangUnionDeclarationStubElementType(name);
        if (name.equals("SINGLE_IMPORT")) return new SingleImportStubElementType(name);
        if (name.equals("UNITTEST")) return new UnittestStubElementType(name);
        if (name.equals("CATCH")) return new CatchStubElementType(name);
        if (name.equals("IF_CONDITION")) return new DLanguageIfConditionStubElementType(name);
        if (name.equals("FOREACH_TYPE")) return new DLanguageForeachTypeStubElementType(name);
        if (name.equals("PARAMETER")) return new DLanguageParameterStubElementType(name);
        if (name.equals("TEMPLATE_PARAMETER")) return new DLanguageTemplateParameterStubElementType(name);
        if (name.equals("EPONYMOUS_TEMPLATE_DECLARATION"))
            return new DLanguageEponymousTemplateDeclarationStubElementType(name);
        if (name.equals("ENUM_MEMBER")) return new DlangEnumMemberStubElementType(name);
        if (name.equals("NAMED_IMPORT_BIND")) return new DLanguageNamedImportBindStubElementType(name);
        throw new RuntimeException("Unknown element type: " + name);
    }
}
