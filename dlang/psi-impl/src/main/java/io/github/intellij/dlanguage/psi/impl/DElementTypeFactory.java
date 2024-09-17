package io.github.intellij.dlanguage.psi.impl;

import com.intellij.psi.tree.IElementType;
import io.github.intellij.dlanguage.stubs.types.*;
import org.jetbrains.annotations.NotNull;

public class DElementTypeFactory {
    private DElementTypeFactory() {
    }

    @NotNull
    public static IElementType factory(@NotNull final String name) {
        return switch (name) {
            case "FUNCTION_DECLARATION" -> new FunctionDeclarationStubElementType(name);
            case "CLASS_DECLARATION" -> new ClassDeclarationStubElementType(name);
            case "INTERFACE_DECLARATION" -> new InterfaceDeclarationStubElementType(name);
            case "TEMPLATE_DECLARATION" -> new DlangTemplateDeclarationStubElementType(name);
            case "CONSTRUCTOR" -> new ConstructorStubElementType(name);
            case "DESTRUCTOR" -> new DestructorStubElementType(name);
            case "STRUCT_DECLARATION" -> new StructDeclarationStubElementType(name);
            case "ALIAS_INITIALIZER" -> new AliasInitializerStubElementType(name);
            case "MODULE_DECLARATION" -> new ModuleDeclarationStubElementType(name);
            case "IDENTIFIER_INITIALIZER" -> new IdentifierInitializerStubElementType(name);
            case "DECLARATOR_IDENTIFIER" -> new DeclaratorIdentifierStubElementType(name);
            case "LABELED_STATEMENT" -> new LabeledStatementStubElementType(name);
            case "SHARED_STATIC_CONSTRUCTOR" -> new SharedStaticConstructorStubElementType(name);
            case "SHARED_STATIC_DESTRUCTOR" -> new SharedStaticDestructorStubElementType(name);
            case "STATIC_CONSTRUCTOR" -> new StaticConstructorStubElementType(name);
            case "STATIC_DESTRUCTOR" -> new StaticDestructorStubElementType(name);
            case "AUTO_ASSIGNMENT" -> new AutoAssignmentStubElementType(name);
            case "ENUM_DECLARATION" -> new EnumDeclarationStubElementType(name);
            case "UNION_DECLARATION" -> new DlangUnionDeclarationStubElementType(name);
            case "IMPORT_DECLARATION" -> new DLanguageImportDeclarationStubElementType(name);
            case "SINGLE_IMPORT" -> new SingleImportStubElementType(name);
            case "UNITTEST" -> new UnittestStubElementType(name);
            case "CATCH" -> new CatchStubElementType(name);
            case "IF_CONDITION" -> new DlangIfConditionStubElementType(name);
            case "FOREACH_TYPE" -> new DlangForeachTypeStubElementType(name);
            case "PARAMETER" -> new DlangParameterStubElementType(name);
            case "TEMPLATE_ALIAS_PARAMETER" -> new DlangTemplateAliasParameterStubElementType(name);
            case "TEMPLATE_TUPLE_PARAMETER" -> new DlangTemplateTupleParameterStubElementType(name);
            case "TEMPLATE_TYPE_PARAMETER" -> new DlangTemplateTypeParameterStubElementType(name);
            case "TEMPLATE_VALUE_PARAMETER" -> new DlangTemplateValueParameterStubElementType(name);
            case "ENUM_MEMBER" -> new DlangEnumMemberStubElementType(name);
            case "NAMED_IMPORT_BIND" -> new DLanguageNamedImportBindStubElementType(name);
            case "VERSION_SPECIFICATION" -> new VersionSpecificationElementType(name);
            default -> throw new RuntimeException("Unknown element type: " + name);
        };

    }
}
