package io.github.intellij.dlanguage.psi.impl;

import com.intellij.psi.tree.IElementType;
import io.github.intellij.dlanguage.stubs.types.*;
import org.jetbrains.annotations.NotNull;

public class DElementTypeFactory {
    private DElementTypeFactory() {
    }

    @NotNull
    public static IElementType factory(@NotNull final String name) {
        switch (name) {
            case "IDENTIFIER":
                return new IdentifierStubElementType(name);
            case "FUNCTION_DECLARATION":
                return new FunctionDeclarationStubElementType(name);
            case "INTERFACE_OR_CLASS":
                return new InterfaceOrClassStubElementType(name);
            case "TEMPLATE_DECLARATION":
                return new DlangTemplateDeclarationStubElementType(name);
            case "CONSTRUCTOR":
                return new ConstructorStubElementType(name);
            case "DESTRUCTOR":
                return new DestructorStubElementType(name);
            case "STRUCT_DECLARATION":
                return new StructDeclarationStubElementType(name);
            case "ALIAS_INITIALIZER":
                return new AliasInitializerStubElementType(name);
            case "MODULE_DECLARATION":
                return new ModuleDeclarationStubElementType(name);
            case "DECLARATOR":
                return new DeclaratorStubElementType(name);
            case "DECLARATOR_IDENTIFIER":
                return new DeclaratorIdentifierStubElementType(name);
            case "LABELED_STATEMENT":
                return new LabeledStatementStubElementType(name);
            case "SHARED_STATIC_CONSTRUCTOR":
                return new SharedStaticConstructorStubElementType(name);
            case "SHARED_STATIC_DESTRUCTOR":
                return new SharedStaticDestructorStubElementType(name);
            case "STATIC_CONSTRUCTOR":
                return new StaticConstructorStubElementType(name);
            case "STATIC_DESTRUCTOR":
                return new StaticDestructorStubElementType(name);
            case "AUTO_DECLARATION_PART":
                return new AutoDeclarationPartStubElementType(name);
            case "ENUM_DECLARATION":
                return new EnumDeclarationStubElementType(name);
            case "UNION_DECLARATION":
                return new DlangUnionDeclarationStubElementType(name);
            case "SINGLE_IMPORT":
                return new SingleImportStubElementType(name);
            case "UNITTEST":
                return new UnittestStubElementType(name);
            case "CATCH":
                return new CatchStubElementType(name);
            case "IF_CONDITION":
                return new DlangIfConditionStubElementType(name);
            case "FOREACH_TYPE":
                return new DlangForeachTypeStubElementType(name);
            case "PARAMETER":
                return new DlangParameterStubElementType(name);
            case "TEMPLATE_PARAMETER":
                return new DlangTemplateParameterStubElementType(name);
            case "EPONYMOUS_TEMPLATE_DECLARATION":
                return new DlangEponymousTemplateDeclarationStubElementType(name);
            case "ENUM_MEMBER":
                return new DlangEnumMemberStubElementType(name);
            case "NAMED_IMPORT_BIND":
                return new DLanguageNamedImportBindStubElementType(name);
            case "VERSION_SPECIFICATION":
                return new VersionSpecificationElementType(name);
        }

        throw new RuntimeException("Unknown element type: " + name);
    }
}
