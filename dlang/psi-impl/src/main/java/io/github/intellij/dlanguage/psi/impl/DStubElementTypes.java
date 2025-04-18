package io.github.intellij.dlanguage.psi.impl;

import com.intellij.psi.tree.IElementType;
import io.github.intellij.dlanguage.stubs.types.*;

public interface DStubElementTypes {
    IElementType FUNCTION_DECLARATION = new FunctionDeclarationStubElementType("FUNCTION_DECLARATION");
    IElementType CLASS_DECLARATION = new ClassDeclarationStubElementType("CLASS_DECLARATION");
    IElementType INTERFACE_DECLARATION = new InterfaceDeclarationStubElementType("INTERFACE_DECLARATION");
    IElementType TEMPLATE_DECLARATION = new DlangTemplateDeclarationStubElementType("TEMPLATE_DECLARATION");
    IElementType CONSTRUCTOR = new ConstructorStubElementType("CONSTRUCTOR");
    IElementType DESTRUCTOR = new DestructorStubElementType("DESTRUCTOR");
    IElementType STRUCT_DECLARATION = new StructDeclarationStubElementType("STRUCT_DECLARATION");
    IElementType ALIAS_INITIALIZER = new AliasInitializerStubElementType("ALIAS_INITIALIZER");
    IElementType MODULE_DECLARATION = new ModuleDeclarationStubElementType("MODULE_DECLARATION");
    IElementType IDENTIFIER_INITIALIZER = new IdentifierInitializerStubElementType("IDENTIFIER_INITIALIZER");
    IElementType DECLARATOR_IDENTIFIER = new DeclaratorIdentifierStubElementType("DECLARATOR_IDENTIFIER");
    IElementType LABELED_STATEMENT = new LabeledStatementStubElementType("LABELED_STATEMENT");
    IElementType SHARED_STATIC_CONSTRUCTOR = new SharedStaticConstructorStubElementType("SHARED_STATIC_CONSTRUCTOR");
    IElementType SHARED_STATIC_DESTRUCTOR = new SharedStaticDestructorStubElementType("SHARED_STATIC_DESTRUCTOR");
    IElementType STATIC_CONSTRUCTOR = new StaticConstructorStubElementType("STATIC_CONSTRUCTOR");
    IElementType STATIC_DESTRUCTOR = new StaticDestructorStubElementType("STATIC_DESTRUCTOR");
    IElementType AUTO_ASSIGNMENT = new AutoAssignmentStubElementType("AUTO_ASSIGNMENT");
    IElementType ENUM_DECLARATION = new EnumDeclarationStubElementType("ENUM_DECLARATION");
    IElementType UNION_DECLARATION = new DlangUnionDeclarationStubElementType("UNION_DECLARATION");
    IElementType IMPORT_DECLARATION = new DLanguageImportDeclarationStubElementType("IMPORT_DECLARATION");
    IElementType SINGLE_IMPORT = new SingleImportStubElementType("SINGLE_IMPORT");
    IElementType UNITTEST = new UnittestStubElementType("UNITTEST");
    IElementType CATCH = new CatchStubElementType("CATCH");
    IElementType IF_CONDITION = new DlangIfConditionStubElementType("IF_CONDITION");
    IElementType FOREACH_TYPE = new DlangForeachTypeStubElementType("FOREACH_TYPE");
    IElementType PARAMETER = new DlangParameterStubElementType("PARAMETER");
    IElementType TEMPLATE_ALIAS_PARAMETER = new DlangTemplateAliasParameterStubElementType("TEMPLATE_ALIAS_PARAMETER");
    IElementType TEMPLATE_TUPLE_PARAMETER = new DlangTemplateTupleParameterStubElementType("TEMPLATE_TUPLE_PARAMETER");
    IElementType TEMPLATE_TYPE_PARAMETER = new DlangTemplateTypeParameterStubElementType("TEMPLATE_TYPE_PARAMETER");
    IElementType TEMPLATE_VALUE_PARAMETER = new DlangTemplateValueParameterStubElementType("TEMPLATE_VALUE_PARAMETER");
    IElementType ENUM_MEMBER = new DlangEnumMemberStubElementType("ENUM_MEMBER");
    IElementType NAMED_IMPORT_BIND = new DLanguageNamedImportBindStubElementType("NAMED_IMPORT_BIND");
    IElementType VERSION_SPECIFICATION = new VersionSpecificationElementType("VERSION_SPECIFICATION");
}
