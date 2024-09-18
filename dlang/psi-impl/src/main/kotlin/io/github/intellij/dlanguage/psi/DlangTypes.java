package io.github.intellij.dlanguage.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.ILazyParseableElementType;
import io.github.intellij.dlanguage.documentation.DlangDocCommentType;
import io.github.intellij.dlanguage.psi.impl.*;
import io.github.intellij.dlanguage.psi.impl.named.*;

public interface DlangTypes {
    IElementType FUNCTION_DECLARATION = DElementTypeFactory.factory("FUNCTION_DECLARATION");
    IElementType CLASS_DECLARATION =DElementTypeFactory.factory("CLASS_DECLARATION");
    IElementType INTERFACE_DECLARATION = DElementTypeFactory.factory("INTERFACE_DECLARATION");
    IElementType TEMPLATE_DECLARATION = DElementTypeFactory.factory("TEMPLATE_DECLARATION");
    IElementType CONSTRUCTOR = DElementTypeFactory.factory("CONSTRUCTOR");
    IElementType DESTRUCTOR = DElementTypeFactory.factory("DESTRUCTOR");
    IElementType STRUCT_DECLARATION = DElementTypeFactory.factory("STRUCT_DECLARATION");
    IElementType ALIAS_INITIALIZER = DElementTypeFactory.factory("ALIAS_INITIALIZER");
    IElementType MODULE_DECLARATION = DElementTypeFactory.factory("MODULE_DECLARATION");
    IElementType IDENTIFIER_INITIALIZER = DElementTypeFactory.factory("IDENTIFIER_INITIALIZER");
    IElementType LABELED_STATEMENT = DElementTypeFactory.factory("LABELED_STATEMENT");
    IElementType SHARED_STATIC_CONSTRUCTOR = DElementTypeFactory.factory("SHARED_STATIC_CONSTRUCTOR");
    IElementType SHARED_STATIC_DESTRUCTOR = DElementTypeFactory.factory("SHARED_STATIC_DESTRUCTOR");
    IElementType STATIC_CONSTRUCTOR = DElementTypeFactory.factory("STATIC_CONSTRUCTOR");
    IElementType STATIC_DESTRUCTOR = DElementTypeFactory.factory("STATIC_DESTRUCTOR");
    IElementType AUTO_ASSIGNMENT = DElementTypeFactory.factory("AUTO_ASSIGNMENT");
    IElementType ENUM_DECLARATION = DElementTypeFactory.factory("ENUM_DECLARATION");
    IElementType UNION_DECLARATION = DElementTypeFactory.factory("UNION_DECLARATION");
    IElementType SINGLE_IMPORT = DElementTypeFactory.factory("SINGLE_IMPORT");
    IElementType UNITTEST = DElementTypeFactory.factory("UNITTEST");
    IElementType CATCH = DElementTypeFactory.factory("CATCH");
    IElementType IF_CONDITION = DElementTypeFactory.factory("IF_CONDITION");
    IElementType FOREACH_TYPE = DElementTypeFactory.factory("FOREACH_TYPE");
    IElementType PARAMETER = DElementTypeFactory.factory("PARAMETER");
    IElementType TEMPLATE_ALIAS_PARAMETER = DElementTypeFactory.factory("TEMPLATE_ALIAS_PARAMETER");
    IElementType TEMPLATE_TUPLE_PARAMETER = DElementTypeFactory.factory("TEMPLATE_TUPLE_PARAMETER");
    IElementType TEMPLATE_TYPE_PARAMETER = DElementTypeFactory.factory("TEMPLATE_TYPE_PARAMETER");
    IElementType TEMPLATE_VALUE_PARAMETER = DElementTypeFactory.factory("TEMPLATE_VALUE_PARAMETER");
    IElementType ENUM_MEMBER = DElementTypeFactory.factory("ENUM_MEMBER");
    IElementType NAMED_IMPORT_BIND = DElementTypeFactory.factory("NAMED_IMPORT_BIND");
    IElementType VERSION_SPECIFICATION = DElementTypeFactory.factory("VERSION_SPECIFICATION");
    IElementType DECLARATOR_IDENTIFIER = DElementTypeFactory.factory("DECLARATOR_IDENTIFIER");
    IElementType IMPORT_DECLARATION = DElementTypeFactory.factory("IMPORT_DECLARATION");

    ILazyParseableElementType BLOCK_STATEMENT = new CodeBlockElementType("BLOCK_STATEMENT");

    DlangElementType ALIAS_ASSIGN = new DlangElementType("ALIAS_ASSIGN");
    DlangElementType ALIAS_DECLARATION = new DlangElementType("ALIAS_DECLARATION");
    DlangElementType ALIAS_THIS_DECLARATION = new DlangElementType("ALIAS_THIS_DECLARATION");
    DlangElementType ALIGN_ATTRIBUTE = new DlangElementType("ALIGN_ATTRIBUTE");
    DlangElementType AND_AND_EXPRESSION = new DlangElementType("AND_AND_EXPRESSION");
    DlangElementType AND_EXPRESSION = new DlangElementType("AND_EXPRESSION");
    DlangElementType ANONYMOUS_ENUM_DECLARATION = new DlangElementType("ANONYMOUS_ENUM_DECLARATION");
    DlangElementType ARGUMENT_LIST = new DlangElementType("ARGUMENT_LIST");
    DlangElementType ARGUMENTS = new DlangElementType("ARGUMENTS");
    DlangElementType ARRAY_ACCESS_EXPRESSION = new DlangElementType("ARRAY_ACCESS_EXPRESSION");
    DlangElementType ARRAY_INITIALIZER = new DlangElementType("ARRAY_INITIALIZER");
    DlangElementType ARRAY_LITERAL = new DlangElementType("ARRAY_LITERAL");
    DlangElementType ARRAY_MEMBER_INITIALIZATION = new DlangElementType("ARRAY_MEMBER_INITIALIZATION");
    DlangElementType ASM_ADD_EXP = new DlangElementType("ASM_ADD_EXP");
    DlangElementType ASM_AND_EXP = new DlangElementType("ASM_AND_EXP");
    DlangElementType ASM_BR_EXP = new DlangElementType("ASM_BR_EXP");
    DlangElementType ASM_EQUAL_EXP = new DlangElementType("ASM_EQUAL_EXP");
    DlangElementType ASM_EXP = new DlangElementType("ASM_EXP");
    DlangElementType ASM_INSTRUCTION = new DlangElementType("ASM_INSTRUCTION");
    DlangElementType ASM_LOG_AND_EXP = new DlangElementType("ASM_LOG_AND_EXP");
    DlangElementType ASM_LOG_OR_EXP = new DlangElementType("ASM_LOG_OR_EXP");
    DlangElementType ASM_MUL_EXP = new DlangElementType("ASM_MUL_EXP");
    DlangElementType ASM_OR_EXP = new DlangElementType("ASM_OR_EXP");
    DlangElementType ASM_PRIMARY_EXP = new DlangElementType("ASM_PRIMARY_EXP");
    DlangElementType ASM_REL_EXP = new DlangElementType("ASM_REL_EXP");
    DlangElementType ASM_SHIFT_EXP = new DlangElementType("ASM_SHIFT_EXP");
    DlangElementType ASM_STATEMENT = new DlangElementType("ASM_STATEMENT");
    DlangElementType ASM_TYPE_PREFIX = new DlangElementType("ASM_TYPE_PREFIX");
    DlangElementType ASM_UNA_EXP = new DlangElementType("ASM_UNA_EXP");
    DlangElementType ASM_XOR_EXP = new DlangElementType("ASM_XOR_EXP");
    DlangElementType ASSERT_ARGUMENTS = new DlangElementType("ASSERT_ARGUMENTS");
    DlangElementType ASSERT_EXPRESSION = new DlangElementType("ASSERT_EXPRESSION");
    DlangElementType ASSIGN_EXPRESSION = new DlangElementType("ASSIGN_EXPRESSION");
    DlangElementType ASSOC_ARRAY_LITERAL = new DlangElementType("ASSOC_ARRAY_LITERAL");
    DlangElementType AT_ATTRIBUTE = new DlangElementType("AT_ATTRIBUTE");
    DlangElementType ATTRIBUTE = new DlangElementType("ATTRIBUTE");
    DlangElementType ATTRIBUTE_SPECIFIER = new DlangElementType("ATTRIBUTE_SPECIFIER");
    DlangElementType AUTO_DECLARATION = new DlangElementType("AUTO_DECLARATION");
    DlangElementType BASE_CLASS = new DlangElementType("BASE_CLASS");
    DlangElementType BASE_CLASS_LIST = new DlangElementType("BASE_CLASS_LIST");
    DlangElementType BASIC_TYPE = new DlangElementType("BASIC_TYPE");
    DlangElementType BREAK_STATEMENT = new DlangElementType("BREAK_STATEMENT");
    DlangElementType CASE_RANGE_STATEMENT = new DlangElementType("CASE_RANGE_STATEMENT");
    DlangElementType CASE_STATEMENT = new DlangElementType("CASE_STATEMENT");
    DlangElementType CAST_EXPRESSION = new DlangElementType("CAST_EXPRESSION");
    DlangElementType CAST_QUALIFIER = new DlangElementType("CAST_QUALIFIER");
    DlangElementType CATCHES = new DlangElementType("CATCHES");
    DlangElementType COMMA_EXPRESSION = new DlangElementType("COMMA_EXPRESSION");
    DlangElementType COMPILE_CONDITION = new DlangElementType("COMPILE_CONDITION");
    DlangElementType CONDITIONAL_DECLARATION = new DlangElementType("CONDITIONAL_DECLARATION");
    DlangElementType CONDITIONAL_STATEMENT = new DlangElementType("CONDITIONAL_STATEMENT");
    DlangElementType CONSTRAINT = new DlangElementType("CONSTRAINT");
    DlangElementType CONTINUE_STATEMENT = new DlangElementType("CONTINUE_STATEMENT");
    DlangElementType DEBUG_CONDITION = new DlangElementType("DEBUG_CONDITION");
    DlangElementType DEBUG_SPECIFICATION = new DlangElementType("DEBUG_SPECIFICATION");
    DlangElementType DECLARATION_BLOCK = new DlangElementType("DECLARATION_BLOCK");
    DlangElementType DECLARATION_STATEMENT = new DlangElementType("DECLARATION_STATEMENT");
    DlangElementType DEFAULT_STATEMENT = new DlangElementType("DEFAULT_STATEMENT");
    DlangElementType DELETE_EXPRESSION = new DlangElementType("DELETE_EXPRESSION");
    DlangElementType DEPRECATED = new DlangElementType("DEPRECATED");
    DlangElementType DO_STATEMENT = new DlangElementType("DO_STATEMENT");
    DlangElementType DOLLAR_EXPRESSION = new DlangElementType("DOLLAR_EXPRESSION");
    DlangElementType EMPTY_DECLARATION = new DlangElementType("EMPTY_DECLARATION");
    DlangElementType EMPTY_STATEMENT = new DlangElementType("EMPTY_STATEMENT");
    DlangElementType ENUM_BODY = new DlangElementType("ENUM_BODY");
    DlangElementType ENUM_MEMBER_ATTRIBUTE = new DlangElementType("ENUM_MEMBER_ATTRIBUTE");
    DlangElementType EQUAL_EXPRESSION = new DlangElementType("EQUAL_EXPRESSION");
    DlangElementType EXPRESSION_STATEMENT = new DlangElementType("EXPRESSION_STATEMENT");
    DlangElementType FINAL_SWITCH_STATEMENT = new DlangElementType("FINAL_SWITCH_STATEMENT");
    DlangElementType FINALLY = new DlangElementType("FINALLY");
    DlangElementType FOR_STATEMENT = new DlangElementType("FOR_STATEMENT");
    DlangElementType FOREACH_STATEMENT = new DlangElementType("FOREACH_STATEMENT");
    DlangElementType FOREACH_TYPE_LIST = new DlangElementType("FOREACH_TYPE_LIST");
    DlangElementType FUNCTION_ATTRIBUTE = new DlangElementType("FUNCTION_ATTRIBUTE");
    DlangElementType FUNCTION_CALL_EXPRESSION = new DlangElementType("FUNCTION_CALL_EXPRESSION");
    DlangElementType FUNCTION_CONTRACT = new DlangElementType("FUNCTION_CONTRACT");
    DlangElementType FUNCTION_LITERAL_EXPRESSION = new DlangElementType("FUNCTION_LITERAL_EXPRESSION");
    DlangElementType FUNDAMENTAL_TYPE_CONSTRUCT_EXPRESSION = new DlangElementType("FUNDAMENTAL_TYPE_CONSTRUCT_EXPRESSION");
    DlangElementType FUNDAMENTAL_TYPE_PROPERTY_EXPRESSION = new DlangElementType("FUNDAMENTAL_TYPE_PROPERTY_EXPRESSION");
    DlangElementType GOTO_STATEMENT = new DlangElementType("GOTO_STATEMENT");
    DlangElementType IDENTIFIER_CHAIN = new DlangElementType("IDENTIFIER_CHAIN");
    DlangElementType IDENTITY_EXPRESSION = new DlangElementType("IDENTITY_EXPRESSION");
    DlangElementType IF_STATEMENT = new DlangElementType("IF_STATEMENT");
    DlangElementType IMPORT_BIND = new DlangElementType("IMPORT_BIND");
    DlangElementType IMPORT_BINDINGS = new DlangElementType("IMPORT_BINDINGS");
    DlangElementType IMPORT_EXPRESSION = new DlangElementType("IMPORT_EXPRESSION");
    DlangElementType IN_CONTRACT_EXPRESSION = new DlangElementType("IN_CONTRACT_EXPRESSION");
    DlangElementType IN_EXPRESSION = new DlangElementType("IN_EXPRESSION");
    DlangElementType IN_OUT_CONTRACT_EXPRESSION = new DlangElementType("IN_OUT_CONTRACT_EXPRESSION");
    DlangElementType IN_OUT_STATEMENT = new DlangElementType("IN_OUT_STATEMENT");
    DlangElementType IN_STATEMENT = new DlangElementType("IN_STATEMENT");
    DlangElementType INDEX_EXPRESSION = new DlangElementType("INDEX_EXPRESSION");
    DlangElementType INITIALIZER = new DlangElementType("INITIALIZER");
    DlangElementType INVARIANT = new DlangElementType("INVARIANT");
    DlangElementType IS_EXPRESSION = new DlangElementType("IS_EXPRESSION");
    DlangElementType KEY_VALUE_PAIR = new DlangElementType("KEY_VALUE_PAIR");
    DlangElementType KEY_VALUE_PAIRS = new DlangElementType("KEY_VALUE_PAIRS");
    DlangElementType LAST_CATCH = new DlangElementType("LAST_CATCH");
    DlangElementType LINKAGE_ATTRIBUTE = new DlangElementType("LINKAGE_ATTRIBUTE");
    DlangElementType LITERAL_EXPRESSION = new DlangElementType("LITERAL_EXPRESSION");
    DlangElementType MAGIC_LITERAL_EXPRESSION = new DlangElementType("MAGIC_LITERAL_EXPRESSION");
    DlangElementType MEMBER_FUNCTION_ATTRIBUTE = new DlangElementType("MEMBER_FUNCTION_ATTRIBUTE");
    DlangElementType MISSING_FUNCTION_BODY = new DlangElementType("MISSING_FUNCTION_BODY");
    DlangElementType MIXIN_DECLARATION = new DlangElementType("MIXIN_DECLARATION");
    DlangElementType MIXIN_TYPE = new DlangElementType("MIXIN_TYPE");
    DlangElementType MIXIN_QUALIFIED_IDENTIFIER = new DlangElementType("MIXIN_QUALIFIED_IDENTIFIER");
    DlangElementType MIXIN_TEMPLATE_NAME = new DlangElementType("MIXIN_TEMPLATE_NAME");
    DlangElementType MUL_EXPRESSION = new DlangElementType("MUL_EXPRESSION");
    DlangElementType NAMESPACE_LIST = new DlangElementType("NAMESPACE_LIST");
    DlangElementType NEW_ANON_CLASS_EXPRESSION = new DlangElementType("NEW_ANON_CLASS_EXPRESSION");
    DlangElementType NEW_EXPRESSION = new DlangElementType("NEW_EXPRESSION");
    DlangElementType OPERANDS = new DlangElementType("OPERANDS");
    DlangElementType OR_EXPRESSION = new DlangElementType("OR_EXPRESSION");
    DlangElementType OR_OR_EXPRESSION = new DlangElementType("OR_OR_EXPRESSION");
    DlangElementType OUT_CONTRACT_EXPRESSION = new DlangElementType("OUT_CONTRACT_EXPRESSION");
    DlangElementType OUT_STATEMENT = new DlangElementType("OUT_STATEMENT");
    DlangElementType PARAMETERS = new DlangElementType("PARAMETERS");
    DlangElementType PARENTHESISED_EXPRESSION = new DlangElementType("PARENTHESISED_EXPRESSION");
    DlangElementType POSTBLIT = new DlangElementType("POSTBLIT");
    DlangElementType POSTFIX_EXPRESSION = new DlangElementType("POSTFIX_EXPRESSION");
    DlangElementType POW_EXPRESSION = new DlangElementType("POW_EXPRESSION");
    DlangElementType PRAGMA_EXPRESSION = new DlangElementType("PRAGMA_EXPRESSION");
    DlangElementType PRAGMA_STATEMENT = new DlangElementType("PRAGMA_STATEMENT");
    DlangElementType QUALIFIED_IDENTIFIER = new DlangElementType("QUALIFIED_IDENTIFIER");
    DlangElementType REFERENCE_EXPRESSION = new DlangElementType("REFERENCE_EXPRESSION");
    DlangElementType REGISTER = new DlangElementType("REGISTER");
    DlangElementType REL_EXPRESSION = new DlangElementType("REL_EXPRESSION");
    DlangElementType RETURN_STATEMENT = new DlangElementType("RETURN_STATEMENT");
    DlangElementType SCOPE_GUARD_STATEMENT = new DlangElementType("SCOPE_GUARD_STATEMENT");
    DlangElementType SHIFT_EXPRESSION = new DlangElementType("SHIFT_EXPRESSION");
    DlangElementType SHORTENED_FUNCTION_BODY = new DlangElementType("SHORTENED_FUNCTION_BODY");
    DlangElementType SPECIFIED_FUNCTION_BODY = new DlangElementType("SPECIFIED_FUNCTION_BODY");
    DlangElementType STATIC_ASSERT_DECLARATION = new DlangElementType("STATIC_ASSERT_DECLARATION");
    DlangElementType STATIC_ASSERT_STATEMENT = new DlangElementType("STATIC_ASSERT_STATEMENT");
    DlangElementType STATIC_IF_CONDITION = new DlangElementType("STATIC_IF_CONDITION");
    DlangElementType STORAGE_CLASS = new DlangElementType("STORAGE_CLASS");
    DlangElementType STRUCT_BODY = new DlangElementType("STRUCT_BODY");
    DlangElementType STRUCT_INITIALIZER = new DlangElementType("STRUCT_INITIALIZER");
    DlangElementType STRUCT_MEMBER_INITIALIZER = new DlangElementType("STRUCT_MEMBER_INITIALIZER");
    DlangElementType STRUCT_MEMBER_INITIALIZERS = new DlangElementType("STRUCT_MEMBER_INITIALIZERS");
    DlangElementType SWITCH_STATEMENT = new DlangElementType("SWITCH_STATEMENT");
    DlangElementType SYNCHRONIZED_STATEMENT = new DlangElementType("SYNCHRONIZED_STATEMENT");
    DlangElementType TEMPLATE_ARGUMENT = new DlangElementType("TEMPLATE_ARGUMENT");
    DlangElementType TEMPLATE_ARGUMENT_LIST = new DlangElementType("TEMPLATE_ARGUMENT_LIST");
    DlangElementType TEMPLATE_ARGUMENTS = new DlangElementType("TEMPLATE_ARGUMENTS");
    DlangElementType TEMPLATE_INSTANCE = new DlangElementType("TEMPLATE_INSTANCE");
    DlangElementType TEMPLATE_MIXIN = new DlangElementType("TEMPLATE_MIXIN");
    DlangElementType TEMPLATE_MIXIN_DECLARATION = new DlangElementType("TEMPLATE_MIXIN_DECLARATION");
    DlangElementType TEMPLATE_PARAMETER_LIST = new DlangElementType("TEMPLATE_PARAMETER_LIST");
    DlangElementType TEMPLATE_PARAMETERS = new DlangElementType("TEMPLATE_PARAMETERS");
    DlangElementType TEMPLATE_SINGLE_ARGUMENT = new DlangElementType("TEMPLATE_SINGLE_ARGUMENT");
    DlangElementType TEMPLATE_THIS_PARAMETER = new DlangElementType("TEMPLATE_THIS_PARAMETER");
    DlangElementType TEMPLATE_VALUE_PARAMETER_DEFAULT = new DlangElementType("TEMPLATE_VALUE_PARAMETER_DEFAULT");
    DlangElementType TERNARY_EXPRESSION = new DlangElementType("TERNARY_EXPRESSION");
    DlangElementType THROW_EXPRESSION = new DlangElementType("THROW_EXPRESSION");
    DlangElementType TRAITS_EXPRESSION = new DlangElementType("TRAITS_EXPRESSION");
    DlangElementType TRY_STATEMENT = new DlangElementType("TRY_STATEMENT");
    DlangElementType TYPE = new DlangElementType("TYPE");
    DlangElementType TYPE_CONSTRUCT_EXPRESSION = new DlangElementType("TYPE_CONSTRUCT_EXPRESSION");
    DlangElementType TYPE_PROPERTY_EXPRESSION = new DlangElementType("TYPE_PROPERTY_EXPRESSION");
    DlangElementType TYPE_SPECIALIZATION = new DlangElementType("TYPE_SPECIALIZATION");
    DlangElementType TYPE_SUFFIX = new DlangElementType("TYPE_SUFFIX");
    DlangElementType TYPEID_EXPRESSION = new DlangElementType("TYPEID_EXPRESSION");
    DlangElementType TYPEOF_EXPRESSION = new DlangElementType("TYPEOF_EXPRESSION");
    DlangElementType UNARY_EXPRESSION = new DlangElementType("UNARY_EXPRESSION");
    DlangElementType VARIABLE_DECLARATION = new DlangElementType("VARIABLE_DECLARATION");
    DlangElementType VARIADIC_ARGUMENTS_ATTRIBUTE = new DlangElementType("VARIADIC_ARGUMENTS_ATTRUBUTE");
    DlangElementType VARIADIC_ARGUMENTS_ATTRIBUTES = new DlangElementType("VARIADIC_ARGUMENTS_ATTRUBUTES");
    DlangElementType VECTOR = new DlangElementType("VECTOR");
    DlangElementType VERSION_CONDITION = new DlangElementType("VERSION_CONDITION");
    DlangElementType WHILE_STATEMENT = new DlangElementType("WHILE_STATEMENT");
    DlangElementType WITH_STATEMENT = new DlangElementType("WITH_STATEMENT");
    DlangElementType XOR_EXPRESSION = new DlangElementType("XOR_EXPRESSION");
    DlangElementType ADD_EXPRESSION = new DlangElementType("ADD_EXPRESSION");
    DlangElementType BUILTIN_TYPE = new DlangElementType("BUILTIN_TYPE");
    DlangElementType STATIC_FOREACH_DECLARATION = new DlangElementType("STATIC_FOREACH_DECLARATION");
    DlangElementType STATIC_FOREACH_STATEMENT = new DlangElementType("STATIC_FOREACH_STATEMENT");

    IElementType LINE_DOC = new DlangDocCommentType("LINE_DOC");
    IElementType BLOCK_DOC = new DlangDocCommentType("BLOCK_DOC");
    IElementType NESTING_BLOCK_DOC = new DlangDocCommentType("NESTING_BLOCK_DOC");
    DlangTokenType ALTERNATE_WYSIWYG_STRING = new DlangTokenType("ALTERNATE_WYSIWYG_STRING");
    DlangTokenType BLOCK_COMMENT = new DlangTokenType("BLOCK_COMMENT");
    DlangTokenType CHARACTER_LITERAL = new DlangTokenType("CHARACTER_LITERAL");
    DlangTokenType DELIMITED_STRING = new DlangTokenType("DELIMITED_STRING");
    DlangTokenType DOUBLE_QUOTED_STRING = new DlangTokenType("DOUBLE_QUOTED_STRING");
    DlangTokenType FLOAT_LITERAL = new DlangTokenType("FLOAT_LITERAL");
    DlangTokenType ID = new DlangTokenType("ID");
    DlangTokenType INTEGER_LITERAL = new DlangTokenType("INTEGER_LITERAL");
    DlangTokenType KW_ABSTRACT = new DlangTokenType("abstract");
    DlangTokenType KW_ALIAS = new DlangTokenType("alias");
    DlangTokenType KW_ALIGN = new DlangTokenType("align");
    DlangTokenType KW_ASM = new DlangTokenType("asm");
    DlangTokenType KW_ASSERT = new DlangTokenType("assert");
    DlangTokenType KW_AUTO = new DlangTokenType("auto");
    DlangTokenType KW_BOOL = new DlangTokenType("bool");
    DlangTokenType KW_BREAK = new DlangTokenType("break");
    DlangTokenType KW_BYTE = new DlangTokenType("byte");
    DlangTokenType KW_CASE = new DlangTokenType("case");
    DlangTokenType KW_CAST = new DlangTokenType("cast");
    DlangTokenType KW_CATCH = new DlangTokenType("catch");
    DlangTokenType KW_CENT = new DlangTokenType("cent");
    DlangTokenType KW_UCENT = new DlangTokenType("ucent");
    DlangTokenType KW_CDOUBLE = new DlangTokenType("cdouble");
    DlangTokenType KW_CFLOAT = new DlangTokenType("cfloat");
    DlangTokenType KW_CHAR = new DlangTokenType("char");
    DlangTokenType KW_CLASS = new DlangTokenType("class");
    DlangTokenType KW_CONST = new DlangTokenType("const");
    DlangTokenType KW_CONTINUE = new DlangTokenType("continue");
    DlangTokenType KW_CREAL = new DlangTokenType("creal");
    DlangTokenType KW_DCHAR = new DlangTokenType("dchar");
    DlangTokenType KW_DEBUG = new DlangTokenType("debug");
    DlangTokenType KW_DEFAULT = new DlangTokenType("default");
    DlangTokenType KW_DELEGATE = new DlangTokenType("delegate");
    DlangTokenType KW_DELETE = new DlangTokenType("delete");
    DlangTokenType KW_DEPRECATED = new DlangTokenType("deprecated");
    DlangTokenType KW_DO = new DlangTokenType("do");
    DlangTokenType KW_DOUBLE = new DlangTokenType("double");
    DlangTokenType KW_ELSE = new DlangTokenType("else");
    DlangTokenType KW_ENUM = new DlangTokenType("enum");
    DlangTokenType KW_EXPORT = new DlangTokenType("export");
    DlangTokenType KW_EXTERN = new DlangTokenType("extern");
    DlangTokenType KW_FALSE = new DlangTokenType("false");
    DlangTokenType KW_FINAL = new DlangTokenType("final");
    DlangTokenType KW_FINALLY = new DlangTokenType("finally");
    DlangTokenType KW_FLOAT = new DlangTokenType("float");
    DlangTokenType KW_FOR = new DlangTokenType("for");
    DlangTokenType KW_FOREACH = new DlangTokenType("foreach");
    DlangTokenType KW_FOREACH_REVERSE = new DlangTokenType("foreach_reverse");
    DlangTokenType KW_FUNCTION = new DlangTokenType("function");
    DlangTokenType KW_GOTO = new DlangTokenType("goto");
    DlangTokenType KW_IDOUBLE = new DlangTokenType("idouble");
    DlangTokenType KW_IF = new DlangTokenType("if");
    DlangTokenType KW_IFLOAT = new DlangTokenType("ifloat");
    DlangTokenType KW_IMMUTABLE = new DlangTokenType("immutable");
    DlangTokenType KW_IMPORT = new DlangTokenType("import");
    DlangTokenType KW_IN = new DlangTokenType("in");
    DlangTokenType KW_INOUT = new DlangTokenType("inout");
    DlangTokenType KW_INT = new DlangTokenType("int");
    DlangTokenType KW_INTERFACE = new DlangTokenType("interface");
    DlangTokenType KW_INVARIANT = new DlangTokenType("invariant");
    DlangTokenType KW_IREAL = new DlangTokenType("ireal");
    DlangTokenType KW_IS = new DlangTokenType("is");
    DlangTokenType KW_LAZY = new DlangTokenType("lazy");
    DlangTokenType KW_LONG = new DlangTokenType("long");
    DlangTokenType KW_MIXIN = new DlangTokenType("mixin");
    DlangTokenType KW_MODULE = new DlangTokenType("module");
    DlangTokenType KW_NEW = new DlangTokenType("new");
    DlangTokenType KW_NOTHROW = new DlangTokenType("nothrow");
    DlangTokenType KW_NULL = new DlangTokenType("null");
    DlangTokenType KW_OUT = new DlangTokenType("out");
    DlangTokenType KW_OVERRIDE = new DlangTokenType("override");
    DlangTokenType KW_PACKAGE = new DlangTokenType("package");
    DlangTokenType KW_PRAGMA = new DlangTokenType("pragma");
    DlangTokenType KW_PRIVATE = new DlangTokenType("private");
    DlangTokenType KW_PROTECTED = new DlangTokenType("protected");
    DlangTokenType KW_PUBLIC = new DlangTokenType("public");
    DlangTokenType KW_PURE = new DlangTokenType("pure");
    DlangTokenType KW_REAL = new DlangTokenType("real");
    DlangTokenType KW_REF = new DlangTokenType("ref");
    DlangTokenType KW_RETURN = new DlangTokenType("return");
    DlangTokenType KW_SCOPE = new DlangTokenType("scope");
    DlangTokenType KW_SHARED = new DlangTokenType("shared");
    DlangTokenType KW_SHORT = new DlangTokenType("short");
    DlangTokenType KW_STATIC = new DlangTokenType("static");
    DlangTokenType KW_STRUCT = new DlangTokenType("struct");
    DlangTokenType KW_SUPER = new DlangTokenType("super");
    DlangTokenType KW_SWITCH = new DlangTokenType("switch");
    DlangTokenType KW_SYNCHRONIZED = new DlangTokenType("synchronized");
    DlangTokenType KW_TEMPLATE = new DlangTokenType("template");
    DlangTokenType KW_THIS = new DlangTokenType("this");
    DlangTokenType KW_THROW = new DlangTokenType("throw");
    DlangTokenType KW_TRUE = new DlangTokenType("true");
    DlangTokenType KW_TRY = new DlangTokenType("try");
    DlangTokenType KW_TYPEID = new DlangTokenType("typeid");
    DlangTokenType KW_TYPEOF = new DlangTokenType("typeof");
    DlangTokenType KW_UBYTE = new DlangTokenType("ubyte");
    DlangTokenType KW_UINT = new DlangTokenType("uint");
    DlangTokenType KW_ULONG = new DlangTokenType("ulong");
    DlangTokenType KW_UNION = new DlangTokenType("union");
    DlangTokenType KW_UNITTEST = new DlangTokenType("unittest");
    DlangTokenType KW_USHORT = new DlangTokenType("ushort");
    DlangTokenType KW_VERSION = new DlangTokenType("version");
    DlangTokenType KW_VOID = new DlangTokenType("void");
    DlangTokenType KW_WCHAR = new DlangTokenType("wchar");
    DlangTokenType KW_WHILE = new DlangTokenType("while");
    DlangTokenType KW_WITH = new DlangTokenType("with");
    DlangTokenType LINE_COMMENT = new DlangTokenType("LINE_COMMENT");
    DlangTokenType NESTING_BLOCK_COMMENT = new DlangTokenType("NESTING_BLOCK_COMMENT");
    DlangTokenType OP_AND = new DlangTokenType("&");
    DlangTokenType OP_AND_EQ = new DlangTokenType("&=");
    DlangTokenType OP_ASTERISK = new DlangTokenType("*");
    DlangTokenType OP_AT = new DlangTokenType("@");
    DlangTokenType OP_BOOL_AND = new DlangTokenType("&&");
    DlangTokenType OP_BOOL_OR = new DlangTokenType("||");
    DlangTokenType OP_BRACES_LEFT = new DlangTokenType("{");
    DlangTokenType OP_BRACES_RIGHT = new DlangTokenType("}");
    DlangTokenType OP_BRACKET_LEFT = new DlangTokenType("[");
    DlangTokenType OP_BRACKET_RIGHT = new DlangTokenType("]");
    DlangTokenType OP_COLON = new DlangTokenType(":");
    DlangTokenType OP_COMMA = new DlangTokenType(",");
    DlangTokenType OP_DDOT = new DlangTokenType("..");
    DlangTokenType OP_DIV = new DlangTokenType("/");
    DlangTokenType OP_DIV_EQ = new DlangTokenType("/=");
    DlangTokenType OP_DOLLAR = new DlangTokenType("$");
    DlangTokenType OP_DOT = new DlangTokenType(".");
    DlangTokenType OP_EQ = new DlangTokenType("=");
    DlangTokenType OP_EQ_EQ = new DlangTokenType("==");
    DlangTokenType OP_GT = new DlangTokenType(">");
    DlangTokenType OP_GT_EQ = new DlangTokenType(">=");
    DlangTokenType OP_LAMBDA_ARROW = new DlangTokenType("=>");
    DlangTokenType OP_LESS = new DlangTokenType("<");
    DlangTokenType OP_LESS_EQ = new DlangTokenType("<=");
    DlangTokenType OP_MINUS = new DlangTokenType("-");
    DlangTokenType OP_MINUS_EQ = new DlangTokenType("-=");
    DlangTokenType OP_MINUS_MINUS = new DlangTokenType("--");
    DlangTokenType OP_MOD = new DlangTokenType("%");
    DlangTokenType OP_MOD_EQ = new DlangTokenType("%=");
    DlangTokenType OP_MUL_EQ = new DlangTokenType("*=");
    DlangTokenType OP_NOT = new DlangTokenType("!");
    DlangTokenType OP_NOT_EQ = new DlangTokenType("!=");
    DlangTokenType OP_NOT_GR = new DlangTokenType("!>");
    DlangTokenType OP_NOT_GR_EQ = new DlangTokenType("!>=");
    DlangTokenType OP_NOT_LESS = new DlangTokenType("!<");
    DlangTokenType OP_NOT_LESS_EQ = new DlangTokenType("!<=");
    DlangTokenType OP_OR = new DlangTokenType("|");
    DlangTokenType OP_OR_EQ = new DlangTokenType("|=");
    DlangTokenType OP_PAR_LEFT = new DlangTokenType("(");
    DlangTokenType OP_PAR_RIGHT = new DlangTokenType(")");
    DlangTokenType OP_PLUS = new DlangTokenType("+");
    DlangTokenType OP_PLUS_EQ = new DlangTokenType("+=");
    DlangTokenType OP_PLUS_PLUS = new DlangTokenType("++");
    DlangTokenType OP_POW = new DlangTokenType("^^");
    DlangTokenType OP_POW_EQ = new DlangTokenType("^^=");
    DlangTokenType OP_QUEST = new DlangTokenType("?");
    DlangTokenType OP_SCOLON = new DlangTokenType(";");
    DlangTokenType OP_SH_LEFT = new DlangTokenType("<<");
    DlangTokenType OP_SH_LEFT_EQ = new DlangTokenType("<<=");
    DlangTokenType OP_SH_RIGHT = new DlangTokenType(">>");
    DlangTokenType OP_SH_RIGHT_EQ = new DlangTokenType(">>=");
    DlangTokenType OP_TILDA = new DlangTokenType("~");
    DlangTokenType OP_TILDA_EQ = new DlangTokenType("~=");
    DlangTokenType OP_TRIPLEDOT = new DlangTokenType("...");
    DlangTokenType OP_USH_RIGHT = new DlangTokenType(">>>");
    DlangTokenType OP_USH_RIGHT_EQ = new DlangTokenType(">>>=");
    DlangTokenType OP_XOR = new DlangTokenType("^");
    DlangTokenType OP_XOR_EQ = new DlangTokenType("^=");
    DlangTokenType SHEBANG = new DlangTokenType("shebang");
    DlangTokenType TOKEN_STRING = new DlangTokenType("TOKEN_STRING");
    DlangTokenType WYSIWYG_STRING = new DlangTokenType("WYSIWYG_STRING");
    DlangTokenType KW___DATE__ = new DlangTokenType("__DATE__");
    DlangTokenType KW___EOF__ = new DlangTokenType("__EOF__");
    DlangTokenType KW___FILE__ = new DlangTokenType("__FILE__");
    DlangTokenType KW___FILE_FULL_PATH__ = new DlangTokenType("__FILE_FULL_PATH__");
    DlangTokenType KW___FUNCTION__ = new DlangTokenType("__FUNCTION__");
    DlangTokenType KW___GSHARED = new DlangTokenType("__gshared");
    DlangTokenType KW___LINE__ = new DlangTokenType("__LINE__");
    DlangTokenType KW___MODULE__ = new DlangTokenType("__MODULE__");
    DlangTokenType KW___PARAMETERS = new DlangTokenType("__parameters");
    DlangTokenType KW___PRETTY_FUNCTION__ = new DlangTokenType("__PRETTY_FUNCTION__");
    DlangTokenType KW___TIME__ = new DlangTokenType("__TIME__");
    DlangTokenType KW___TIMESTAMP__ = new DlangTokenType("__TIMESTAMP__");
    DlangTokenType KW___TRAITS = new DlangTokenType("__traits");
    DlangTokenType KW___VECTOR = new DlangTokenType("__vector");
    DlangTokenType KW___VENDOR__ = new DlangTokenType("__VENDOR__");
    DlangTokenType KW___VERSION__ = new DlangTokenType("__VERSION__");

    // Highlighting specific
    DlangElementType VALID_NAMED_CHARACTER_ENTITY = new DlangElementType("VALID_NAMED_CHARACTER_ENTITY");

    class Factory {
        public static PsiElement createElement(final ASTNode node) {
            final IElementType type = node.getElementType();
            if (type == CONSTRUCTOR) {
                return new DLanguageConstructorImpl(node);
            } else if (type == DESTRUCTOR) {
                return new DLanguageDestructorImpl(node);
            } else if (type == ENUM_DECLARATION) {
                return new DlangEnumDeclarationImpl(node);
            } else if (type == ENUM_MEMBER) {
                return new DLanguageEnumMemberImpl(node);
            } else if (type == ENUM_MEMBER_ATTRIBUTE) {
                return new DLanguageEnumMemberAttributeImpl(node);
            } else if (type == FOREACH_TYPE) {
                return new DLanguageForeachTypeImpl(node);
            } else if (type == LABELED_STATEMENT) {
                return new DLanguageLabeledStatementImpl(node);
            } else if (type == MODULE_DECLARATION) {
                return new DlangModuleDeclarationImpl(node);
            } else if (type == PARAMETER) {
                return new DLanguageParameterImpl(node);
            } else if (type == SHARED_STATIC_CONSTRUCTOR) {
                return new DLanguageSharedStaticConstructorImpl(node);
            } else if (type == SHARED_STATIC_DESTRUCTOR) {
                return new DLanguageSharedStaticDestructorImpl(node);
            } else if (type == STATIC_CONSTRUCTOR) {
                return new DLanguageStaticConstructorImpl(node);
            } else if (type == STATIC_DESTRUCTOR) {
                return new DLanguageStaticDestructorImpl(node);
            } else if (type == STRUCT_DECLARATION) {
                return new DlangStructDeclarationImpl(node);
            } else if (type == TEMPLATE_DECLARATION) {
                return new DlangTemplateDeclarationImpl(node);
            } else if (type == UNION_DECLARATION) {
                return new DlangUnionDeclarationImpl(node);
            } else if (type == ALIAS_DECLARATION) {
                return new DLanguageAliasDeclarationImpl(node);
            } else if (type == ALIAS_INITIALIZER) {
                return new DLanguageAliasInitializerImpl(node);
            } else if (type == ALIAS_THIS_DECLARATION) {
                return new DLanguageAliasThisDeclarationImpl(node);
            } else if (type == ALIGN_ATTRIBUTE) {
                return new DLanguageAlignAttributeImpl(node);
            } else if (type == AND_AND_EXPRESSION) {
                return new DLanguageAndAndExpressionImpl(node);
            } else if (type == AND_EXPRESSION) {
                return new DLanguageAndExpressionImpl(node);
            } else if (type == ANONYMOUS_ENUM_DECLARATION) {
                return new DLanguageAnonymousEnumDeclarationImpl(node);
            } else if (type == ARGUMENT_LIST) {
                return new DLanguageArgumentListImpl(node);
            } else if (type == ARGUMENTS) {
                return new DLanguageArgumentsImpl(node);
            } else if (type == ARRAY_ACCESS_EXPRESSION) {
                return new DLanguageArrayAccessExpressionImpl(node);
            } else if (type == ARRAY_INITIALIZER) {
                return new DLanguageArrayInitializerImpl(node);
            } else if (type == ARRAY_LITERAL) {
                return new DLanguageArrayLiteralImpl(node);
            } else if (type == ARRAY_MEMBER_INITIALIZATION) {
                return new DLanguageArrayMemberInitializationImpl(node);
            } else if (type == ASM_ADD_EXP) {
                return new DLanguageAsmAddExpImpl(node);
            } else if (type == ASM_AND_EXP) {
                return new DLanguageAsmAndExpImpl(node);
            } else if (type == ASM_BR_EXP) {
                return new DLanguageAsmBrExpImpl(node);
            } else if (type == ASM_EQUAL_EXP) {
                return new DLanguageAsmEqualExpImpl(node);
            } else if (type == ASM_EXP) {
                return new DLanguageAsmExpImpl(node);
            } else if (type == ASM_INSTRUCTION) {
                return new DLanguageAsmInstructionImpl(node);
            } else if (type == ASM_LOG_AND_EXP) {
                return new DLanguageAsmLogAndExpImpl(node);
            } else if (type == ASM_LOG_OR_EXP) {
                return new DLanguageAsmLogOrExpImpl(node);
            } else if (type == ASM_MUL_EXP) {
                return new DLanguageAsmMulExpImpl(node);
            } else if (type == ASM_OR_EXP) {
                return new DLanguageAsmOrExpImpl(node);
            } else if (type == ASM_PRIMARY_EXP) {
                return new DLanguageAsmPrimaryExpImpl(node);
            } else if (type == ASM_REL_EXP) {
                return new DLanguageAsmRelExpImpl(node);
            } else if (type == ASM_SHIFT_EXP) {
                return new DLanguageAsmShiftExpImpl(node);
            } else if (type == ASM_STATEMENT) {
                return new DLanguageAsmStatementImpl(node);
            } else if (type == ASM_TYPE_PREFIX) {
                return new DLanguageAsmTypePrefixImpl(node);
            } else if (type == ASM_UNA_EXP) {
                return new DLanguageAsmUnaExpImpl(node);
            } else if (type == ASM_XOR_EXP) {
                return new DLanguageAsmXorExpImpl(node);
            } else if (type == ASSERT_ARGUMENTS) {
                return new DLanguageAssertArgumentsImpl(node);
            } else if (type == ASSERT_EXPRESSION) {
                return new DLanguageAssertExpressionImpl(node);
            } else if (type == ASSIGN_EXPRESSION) {
                return new DLanguageAssignExpressionImpl(node);
            } else if (type == ASSOC_ARRAY_LITERAL) {
                return new DLanguageAssocArrayLiteralImpl(node);
            } else if (type == AT_ATTRIBUTE) {
                return new DLanguageAtAttributeImpl(node);
            } else if (type == ATTRIBUTE) {
                return new DLanguageAttributeImpl(node);
            } else if (type == ATTRIBUTE_SPECIFIER) {
                return new DLanguageAttributeSpecifierImpl(node);
            } else if (type == AUTO_DECLARATION) {
                return new DLanguageAutoDeclarationImpl(node);
            } else if (type == AUTO_ASSIGNMENT) {
                return new DLanguageAutoAssignmentImpl(node);
            } else if (type == BASE_CLASS) {
                return new DLanguageBaseClassImpl(node);
            } else if (type == BASE_CLASS_LIST) {
                return new DLanguageBaseClassListImpl(node);
            } else if (type == BASIC_TYPE) {
                return new DLanguageBasicTypeImpl(node);
            //} else if (type == BLOCK_STATEMENT) {
            //    return new DLanguageBlockStatementImpl(node);
            } else if (type == BREAK_STATEMENT) {
                return new DLanguageBreakStatementImpl(node);
            } else if (type == CASE_RANGE_STATEMENT) {
                return new DLanguageCaseRangeStatementImpl(node);
            } else if (type == CASE_STATEMENT) {
                return new DLanguageCaseStatementImpl(node);
            } else if (type == CAST_EXPRESSION) {
                return new DLanguageCastExpressionImpl(node);
            } else if (type == CAST_QUALIFIER) {
                return new DLanguageCastQualifierImpl(node);
            } else if (type == CATCH) {
                return new DLanguageCatchImpl(node);
            } else if (type == CATCHES) {
                return new DLanguageCatchesImpl(node);
            } else if (type == COMMA_EXPRESSION) {
                return new DLanguageCommaExpressionImpl(node);
            } else if (type == COMPILE_CONDITION) {
                return new DLanguageCompileConditionImpl(node);
            } else if (type == CONDITIONAL_DECLARATION) {
                return new DLanguageConditionalDeclarationImpl(node);
            } else if (type == CONDITIONAL_STATEMENT) {
                return new DLanguageConditionalStatementImpl(node);
            } else if (type == CONSTRAINT) {
                return new DLanguageConstraintImpl(node);
            } else if (type == CONTINUE_STATEMENT) {
                return new DLanguageContinueStatementImpl(node);
            } else if (type == DEBUG_CONDITION) {
                return new DLanguageDebugConditionImpl(node);
            } else if (type == DEBUG_SPECIFICATION) {
                return new DLanguageDebugSpecificationImpl(node);
            } else if (type == DECLARATION_BLOCK) {
                return new DLanguageDeclarationBlockImpl(node);
            } else if (type == DECLARATION_STATEMENT) {
                return new DLanguageDeclarationStatementImpl(node);
            } else if (type == IDENTIFIER_INITIALIZER) {
                return new DLanguageIdentifierInitializerImpl(node);
            } else if (type == DEFAULT_STATEMENT) {
                return new DLanguageDefaultStatementImpl(node);
            } else if (type == DELETE_EXPRESSION) {
                return new DLanguageDeleteExpressionImpl(node);
            } else if (type == DEPRECATED) {
                return new DLanguageDeprecatedImpl(node);
            } else if (type == DO_STATEMENT) {
                return new DLanguageDoStatementImpl(node);
            } else if (type == DOLLAR_EXPRESSION) {
                return new DLanguageDollarExpressionImpl(node);
            } else if (type == EMPTY_DECLARATION) {
                return new DLanguageEmptyDeclarationImpl(node);
            } else if (type == EMPTY_STATEMENT) {
                return new DLanguageEmptyStatementImpl(node);
            } else if (type == ENUM_BODY) {
                return new DLanguageEnumBodyImpl(node);
            } else if (type == EQUAL_EXPRESSION) {
                return new DLanguageEqualExpressionImpl(node);
            } else if (type == EXPRESSION_STATEMENT) {
                return new DLanguageExpressionStatementImpl(node);
            } else if (type == FINAL_SWITCH_STATEMENT) {
                return new DLanguageFinalSwitchStatementImpl(node);
            } else if (type == FINALLY) {
                return new DLanguageFinallyImpl(node);
            } else if (type == FOR_STATEMENT) {
                return new DLanguageForStatementImpl(node);
            } else if (type == FOREACH_STATEMENT) {
                return new DLanguageForeachStatementImpl(node);
            } else if (type == FOREACH_TYPE_LIST) {
                return new DLanguageForeachTypeListImpl(node);
            } else if (type == FUNCTION_ATTRIBUTE) {
                return new DLanguageFunctionAttributeImpl(node);
            } else if (type == FUNCTION_CALL_EXPRESSION) {
                return new DLanguageFunctionCallExpressionImpl(node);
            } else if (type == FUNCTION_CONTRACT) {
                return new DLanguageFunctionContractImpl(node);
            } else if (type == FUNCTION_DECLARATION) {
                return new DLanguageFunctionDeclarationImpl(node);
            } else if (type == FUNCTION_LITERAL_EXPRESSION) {
                return new DLanguageFunctionLiteralExpressionImpl(node);
            } else if (type == FUNDAMENTAL_TYPE_CONSTRUCT_EXPRESSION) {
                return new DLanguageFundamentalTypeConstructExpressionImpl(node);
            } else if (type == FUNDAMENTAL_TYPE_PROPERTY_EXPRESSION) {
                return new DLanguageFundamentalTypePropertyExpressionImpl(node);
            } else if (type == GOTO_STATEMENT) {
                return new DLanguageGotoStatementImpl(node);
            } else if (type == IDENTIFIER_CHAIN) {
                return new DLanguageIdentifierChainImpl(node);
            } else if (type == DECLARATOR_IDENTIFIER) {
                return new DlangDeclaratorIdentifierImpl(node);
            } else if (type == MIXIN_QUALIFIED_IDENTIFIER) {
                return new DLanguageMixinQualifiedIdentifierImpl(node);
            } else if (type == IDENTITY_EXPRESSION) {
                return new DLanguageIdentityExpressionImpl(node);
            } else if (type == IF_STATEMENT) {
                return new DLanguageIfStatementImpl(node);
            } else if (type == IMPORT_BIND) {
                return new DLanguageImportBindImpl(node);
            } else if (type == IMPORT_BINDINGS) {
                return new DLanguageImportBindingsImpl(node);
            } else if (type == IMPORT_DECLARATION) {
                return new DLanguageImportDeclarationImpl(node);
            } else if (type == IMPORT_EXPRESSION) {
                return new DLanguageImportExpressionImpl(node);
            } else if (type == IN_CONTRACT_EXPRESSION) {
                return new DLanguageInContractExpressionImpl(node);
            } else if (type == IN_EXPRESSION) {
                return new DLanguageInExpressionImpl(node);
            } else if (type == IN_OUT_CONTRACT_EXPRESSION) {
                return new DLanguageInOutContractExpressionImpl(node);
            } else if (type == IN_OUT_STATEMENT) {
                return new DLanguageInOutStatementImpl(node);
            } else if (type == IN_STATEMENT) {
                return new DLanguageInStatementImpl(node);
            } else if (type == INDEX_EXPRESSION) {
                return new DLanguageIndexExpressionImpl(node);
            } else if (type == INITIALIZER) {
                return new DLanguageInitializerImpl(node);
            } else if (type == INVARIANT) {
                return new DLanguageInvariantImpl(node);
            } else if (type == IS_EXPRESSION) {
                return new DLanguageIsExpressionImpl(node);
            } else if (type == KEY_VALUE_PAIR) {
                return new DLanguageKeyValuePairImpl(node);
            } else if (type == KEY_VALUE_PAIRS) {
                return new DLanguageKeyValuePairsImpl(node);
            } else if (type == LAST_CATCH) {
                return new DLanguageLastCatchImpl(node);
            } else if (type == LINKAGE_ATTRIBUTE) {
                return new DLanguageLinkageAttributeImpl(node);
            } else if (type == LITERAL_EXPRESSION) {
                return new DLanguageLiteralExpressionImpl(node);
            } else if (type == MAGIC_LITERAL_EXPRESSION) {
                return new DLanguageMagicLiteralExpressionImpl(node);
            } else if (type == MEMBER_FUNCTION_ATTRIBUTE) {
                return new DLanguageMemberFunctionAttributeImpl(node);
            } else if (type == MISSING_FUNCTION_BODY) {
                return new DLanguageMissingFunctionBodyImpl(node);
            } else if (type == MIXIN_DECLARATION) {
                return new DLanguageMixinDeclarationImpl(node);
            } else if (type == MIXIN_TYPE) {
                return new DLanguageMixinTypeImpl(node);
            } else if (type == MIXIN_TEMPLATE_NAME) {
                return new DLanguageMixinTemplateNameImpl(node);
            } else if (type == MUL_EXPRESSION) {
                return new DLanguageMulExpressionImpl(node);
            } else if (type == NAMESPACE_LIST) {
                return new DLanguageNamespaceListImpl(node);
            } else if (type == NEW_ANON_CLASS_EXPRESSION) {
                return new DLanguageNewAnonClassExpressionImpl(node);
            } else if (type == NEW_EXPRESSION) {
                return new DLanguageNewExpressionImpl(node);
            } else if (type == OPERANDS) {
                return new DLanguageOperandsImpl(node);
            } else if (type == OR_EXPRESSION) {
                return new DLanguageOrExpressionImpl(node);
            } else if (type == OR_OR_EXPRESSION) {
                return new DLanguageOrOrExpressionImpl(node);
            } else if (type == OUT_CONTRACT_EXPRESSION) {
                return new DLanguageOutContractExpressionImpl(node);
            } else if (type == OUT_STATEMENT) {
                return new DLanguageOutStatementImpl(node);
            } else if (type == PARAMETERS) {
                return new DLanguageParametersImpl(node);
            } else if (type == PARENTHESISED_EXPRESSION) {
                return new DLanguageParenthesisedExpressionImpl(node);
            } else if (type == POSTBLIT) {
                return new DLanguagePostblitImpl(node);
            } else if (type == POSTFIX_EXPRESSION) {
                return new DLanguagePostfixExpressionImpl(node);
            } else if (type == POW_EXPRESSION) {
                return new DLanguagePowExpressionImpl(node);
            } else if (type == PRAGMA_EXPRESSION) {
                return new DLanguagePragmaExpressionImpl(node);
            } else if (type == PRAGMA_STATEMENT) {
                return new DLanguagePragmaStatementImpl(node);
            } else if (type == REFERENCE_EXPRESSION) {
                return new DLanguageReferenceExpressionImpl(node);
            } else if (type == REGISTER) {
                return new DLanguageRegisterImpl(node);
            } else if (type == REL_EXPRESSION) {
                return new DLanguageRelExpressionImpl(node);
            } else if (type == RETURN_STATEMENT) {
                return new DLanguageReturnStatementImpl(node);
            } else if (type == SCOPE_GUARD_STATEMENT) {
                return new DLanguageScopeGuardStatementImpl(node);
            } else if (type == SHIFT_EXPRESSION) {
                return new DLanguageShiftExpressionImpl(node);
            } else if (type == SHORTENED_FUNCTION_BODY) {
                return new DLanguageShortenedFunctionBodyImpl(node);
            } else if (type == SPECIFIED_FUNCTION_BODY) {
                return new DLanguageSpecifiedFunctionBodyImpl(node);
            } else if (type == SINGLE_IMPORT) {
                return new DlangSingleImportImpl(node);
            } else if (type == STATIC_ASSERT_DECLARATION) {
                return new DLanguageStaticAssertDeclarationImpl(node);
            } else if (type == STATIC_ASSERT_STATEMENT) {
                return new DLanguageStaticAssertStatementImpl(node);
            } else if (type == STATIC_IF_CONDITION) {
                return new DLanguageStaticIfConditionImpl(node);
            } else if (type == STORAGE_CLASS) {
                return new DLanguageStorageClassImpl(node);
            } else if (type == STRUCT_BODY) {
                return new DLanguageStructBodyImpl(node);
            } else if (type == STRUCT_INITIALIZER) {
                return new DLanguageStructInitializerImpl(node);
            } else if (type == STRUCT_MEMBER_INITIALIZER) {
                return new DLanguageStructMemberInitializerImpl(node);
            } else if (type == STRUCT_MEMBER_INITIALIZERS) {
                return new DLanguageStructMemberInitializersImpl(node);
            } else if (type == SWITCH_STATEMENT) {
                return new DLanguageSwitchStatementImpl(node);
            } else if (type == SYNCHRONIZED_STATEMENT) {
                return new DLanguageSynchronizedStatementImpl(node);
            } else if (type == TEMPLATE_ALIAS_PARAMETER) {
                return new DLanguageTemplateAliasParameterImpl(node);
            } else if (type == TEMPLATE_ARGUMENT) {
                return new DLanguageTemplateArgumentImpl(node);
            } else if (type == TEMPLATE_ARGUMENT_LIST) {
                return new DLanguageTemplateArgumentListImpl(node);
            } else if (type == TEMPLATE_ARGUMENTS) {
                return new DLanguageTemplateArgumentsImpl(node);
            } else if (type == TEMPLATE_INSTANCE) {
                return new DLanguageTemplateInstanceImpl(node);
            } else if (type == TEMPLATE_MIXIN) {
                return new DLanguageTemplateMixinImpl(node);
            } else if (type == TEMPLATE_MIXIN_DECLARATION) {
                return new DLanguageTemplateMixinDeclarationImpl(node);
            } else if (type == TEMPLATE_PARAMETER_LIST) {
                return new DLanguageTemplateParameterListImpl(node);
            } else if (type == TEMPLATE_PARAMETERS) {
                return new DLanguageTemplateParametersImpl(node);
            } else if (type == TEMPLATE_SINGLE_ARGUMENT) {
                return new DLanguageTemplateSingleArgumentImpl(node);
            } else if (type == TEMPLATE_THIS_PARAMETER) {
                return new DLanguageTemplateThisParameterImpl(node);
            } else if (type == TEMPLATE_TUPLE_PARAMETER) {
                return new DLanguageTemplateTupleParameterImpl(node);
            } else if (type == TEMPLATE_TYPE_PARAMETER) {
                return new DLanguageTemplateTypeParameterImpl(node);
            } else if (type == TEMPLATE_VALUE_PARAMETER) {
                return new DLanguageTemplateValueParameterImpl(node);
            } else if (type == TEMPLATE_VALUE_PARAMETER_DEFAULT) {
                return new DLanguageTemplateValueParameterDefaultImpl(node);
            } else if (type == TERNARY_EXPRESSION) {
                return new DLanguageTernaryExpressionImpl(node);
            } else if (type == THROW_EXPRESSION) {
                return new DLanguageThrowExpressionImpl(node);
            } else if (type == TRAITS_EXPRESSION) {
                return new DLanguageTraitsExpressionImpl(node);
            } else if (type == TRY_STATEMENT) {
                return new DLanguageTryStatementImpl(node);
            } else if (type == TYPE) {
                return new DLanguageTypeImpl(node);
            } else if (type == TYPE_CONSTRUCT_EXPRESSION) {
                return new DLanguageTypeConstructExpressionImpl(node);
            } else if (type == QUALIFIED_IDENTIFIER) {
                return new DLanguageQualifiedIdentifierImpl(node);
            } else if (type == TYPE_PROPERTY_EXPRESSION) {
                return new DLanguageTypePropertyExpressionImpl(node);
            } else if (type == TYPE_SPECIALIZATION) {
                return new DLanguageTypeSpecializationImpl(node);
            } else if (type == TYPE_SUFFIX) {
                return new DLanguageTypeSuffixImpl(node);
            } else if (type == TYPEID_EXPRESSION) {
                return new DLanguageTypeidExpressionImpl(node);
            } else if (type == TYPEOF_EXPRESSION) {
                return new DLanguageTypeofExpressionImpl(node);
            } else if (type == UNARY_EXPRESSION) {
                return new DLanguageUnaryExpressionImpl(node);
            } else if (type == VARIABLE_DECLARATION) {
                return new DLanguageSpecifiedVariableDeclarationImpl(node);
            } else if (type == VARIADIC_ARGUMENTS_ATTRIBUTE) {
                return new DLanguageVariadicArgumentsAttributeImpl(node);
            } else if (type == VARIADIC_ARGUMENTS_ATTRIBUTES) {
                return new DLanguageVariadicArgumentsAttributesImpl(node);
            } else if (type == VECTOR) {
                return new DLanguageVectorImpl(node);
            } else if (type == VERSION_CONDITION) {
                return new DLanguageVersionConditionImpl(node);
            } else if (type == VERSION_SPECIFICATION) {
                return new DlangVersionSpecificationImpl(node);
            } else if (type == WHILE_STATEMENT) {
                return new DLanguageWhileStatementImpl(node);
            } else if (type == WITH_STATEMENT) {
                return new DLanguageWithStatementImpl(node);
            } else if (type == XOR_EXPRESSION) {
                return new DLanguageXorExpressionImpl(node);
            } else if (type == ADD_EXPRESSION) {
                return new DLanguageAddExpressionImpl(node);
            } else if (type == CLASS_DECLARATION) {
                return new DlangClassDeclarationImpl(node);
            } else if (type == UNITTEST) {
                return new DlangUnittestImpl(node);
            } else if (type == INTERFACE_DECLARATION) {
                return new DlangInterfaceDeclarationImpl(node);
            } else if (type == IF_CONDITION) {
                return new DLanguageIfConditionImpl(node);
            } else if (type == BUILTIN_TYPE) {
                return new DLanguageBuiltinTypeImpl(node);
            } else if (type == NAMED_IMPORT_BIND) {
                return new DLanguageNamedImportBindImpl(node);
            } else if (type == STATIC_FOREACH_STATEMENT) {
                return new DLanguageStaticForeachStatementImpl(node);
            } else if (type == STATIC_FOREACH_DECLARATION) {
                return new DLanguageStaticForeachDeclarationImpl(node);
            } else if (type == ALIAS_ASSIGN) {
                return new DLanguageAliasAssignImpl(node);
            }

            throw new AssertionError("Unknown element type: " + type);
        }
    }
}
