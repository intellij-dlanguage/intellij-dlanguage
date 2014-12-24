package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import ddt.dtool.parser.DeeTokens;
import net.masterthought.dlanguage.lexer.DeeElementTypeCache;
import net.masterthought.dlanguage.lexer.DeeTokenLookUp;
import net.masterthought.dlanguage.psi.impl.*;

public interface DElementTypes {

    // element types supported by DeeParser
    IElementType NULL = DeeElementTypeCache.valueOf("NULL");
    IElementType SYMBOL = DeeElementTypeCache.valueOf("SYMBOL");
    IElementType MODULE = DeeElementTypeCache.valueOf("MODULE");
    IElementType DECLARATION_MODULE = DeeElementTypeCache.valueOf("DECLARATION_MODULE");
    IElementType DECLARATION_IMPORT = DeeElementTypeCache.valueOf("DECLARATION_IMPORT");
    IElementType IMPORT_CONTENT = DeeElementTypeCache.valueOf("IMPORT_CONTENT");
    IElementType IMPORT_ALIAS = DeeElementTypeCache.valueOf("IMPORT_ALIAS");
    IElementType IMPORT_SELECTIVE = DeeElementTypeCache.valueOf("IMPORT_SELECTIVE");
    IElementType IMPORT_SELECTIVE_ALIAS = DeeElementTypeCache.valueOf("IMPORT_SELECTIVE_ALIAS");
    IElementType DECL_LIST = DeeElementTypeCache.valueOf("DECL_LIST");
    IElementType DECL_BLOCK = DeeElementTypeCache.valueOf("DECL_BLOCK");
    IElementType DECLARATION_EMTPY = DeeElementTypeCache.valueOf("DECLARATION_EMTPY");
    IElementType MISSING_DECLARATION = DeeElementTypeCache.valueOf("MISSING_DECLARATION");
    IElementType INVALID_SYNTAX = DeeElementTypeCache.valueOf("INVALID_SYNTAX");
    IElementType INCOMPLETE_DECLARATOR = DeeElementTypeCache.valueOf("INCOMPLETE_DECLARATOR");
    IElementType REF_IMPORT_SELECTION = DeeElementTypeCache.valueOf("REF_IMPORT_SELECTION");
    IElementType REF_MODULE = DeeElementTypeCache.valueOf("REF_MODULE");
    IElementType REF_IDENTIFIER = DeeElementTypeCache.valueOf("REF_IDENTIFIER");
    IElementType REF_QUALIFIED = DeeElementTypeCache.valueOf("REF_QUALIFIED");
    IElementType REF_MODULE_QUALIFIED = DeeElementTypeCache.valueOf("REF_MODULE_QUALIFIED");
    IElementType REF_PRIMITIVE = DeeElementTypeCache.valueOf("REF_PRIMITIVE");
    IElementType REF_TYPE_DYN_ARRAY = DeeElementTypeCache.valueOf("REF_TYPE_DYN_ARRAY");
    IElementType REF_TYPE_POINTER = DeeElementTypeCache.valueOf("REF_TYPE_POINTER");
    IElementType REF_INDEXING = DeeElementTypeCache.valueOf("REF_INDEXING");
    IElementType REF_SLICE = DeeElementTypeCache.valueOf("REF_SLICE");
    IElementType REF_TYPE_FUNCTION = DeeElementTypeCache.valueOf("REF_TYPE_FUNCTION");
    IElementType REF_TEMPLATE_INSTANCE = DeeElementTypeCache.valueOf("REF_TEMPLATE_INSTANCE");
    IElementType REF_TYPEOF = DeeElementTypeCache.valueOf("REF_TYPEOF");
    IElementType REF_MODIFIER = DeeElementTypeCache.valueOf("REF_MODIFIER");
    IElementType REF_AUTO = DeeElementTypeCache.valueOf("REF_AUTO");
    IElementType MISSING_EXPRESSION = DeeElementTypeCache.valueOf("MISSING_EXPRESSION");
    IElementType EXP_REF_RETURN = DeeElementTypeCache.valueOf("EXP_REF_RETURN");
    IElementType EXP_THIS = DeeElementTypeCache.valueOf("EXP_THIS");
    IElementType EXP_SUPER = DeeElementTypeCache.valueOf("EXP_SUPER");
    IElementType EXP_NULL = DeeElementTypeCache.valueOf("EXP_NULL");
    IElementType EXP_ARRAY_LENGTH = DeeElementTypeCache.valueOf("EXP_ARRAY_LENGTH");
    IElementType EXP_LITERAL_BOOL = DeeElementTypeCache.valueOf("EXP_LITERAL_BOOL");
    IElementType EXP_LITERAL_INTEGER = DeeElementTypeCache.valueOf("EXP_LITERAL_INTEGER");
    IElementType EXP_LITERAL_STRING = DeeElementTypeCache.valueOf("EXP_LITERAL_STRING");
    IElementType EXP_LITERAL_CHAR = DeeElementTypeCache.valueOf("EXP_LITERAL_CHAR");
    IElementType EXP_LITERAL_FLOAT = DeeElementTypeCache.valueOf("EXP_LITERAL_FLOAT");
    IElementType EXP_LITERAL_ARRAY = DeeElementTypeCache.valueOf("EXP_LITERAL_ARRAY");
    IElementType EXP_LITERAL_MAPARRAY = DeeElementTypeCache.valueOf("EXP_LITERAL_MAPARRAY");
    IElementType MAPARRAY_ENTRY = DeeElementTypeCache.valueOf("MAPARRAY_ENTRY");
    IElementType EXP_FUNCTION_LITERAL = DeeElementTypeCache.valueOf("EXP_FUNCTION_LITERAL");
    IElementType EXP_SIMPLE_LAMBDA = DeeElementTypeCache.valueOf("EXP_SIMPLE_LAMBDA");
    IElementType SIMPLE_LAMBDA_DEFUNIT = DeeElementTypeCache.valueOf("SIMPLE_LAMBDA_DEFUNIT");
    IElementType EXP_REFERENCE = DeeElementTypeCache.valueOf("EXP_REFERENCE");
    IElementType EXP_PARENTHESES = DeeElementTypeCache.valueOf("EXP_PARENTHESES");
    IElementType EXP_ASSERT = DeeElementTypeCache.valueOf("EXP_ASSERT");
    IElementType EXP_MIXIN_STRING = DeeElementTypeCache.valueOf("EXP_MIXIN_STRING");
    IElementType EXP_IMPORT_STRING = DeeElementTypeCache.valueOf("EXP_IMPORT_STRING");
    IElementType EXP_TYPEID = DeeElementTypeCache.valueOf("EXP_TYPEID");
    IElementType EXP_INDEX = DeeElementTypeCache.valueOf("EXP_INDEX");
    IElementType EXP_CALL = DeeElementTypeCache.valueOf("EXP_CALL");
    IElementType EXP_PREFIX = DeeElementTypeCache.valueOf("EXP_PREFIX");
    IElementType EXP_NEW = DeeElementTypeCache.valueOf("EXP_NEW");
    IElementType EXP_NEW_ANON_CLASS = DeeElementTypeCache.valueOf("EXP_NEW_ANON_CLASS");
    IElementType EXP_CAST = DeeElementTypeCache.valueOf("EXP_CAST");
    IElementType EXP_CAST_QUAL = DeeElementTypeCache.valueOf("EXP_CAST_QUAL");
    IElementType EXP_POSTFIX_OP = DeeElementTypeCache.valueOf("EXP_POSTFIX_OP");
    IElementType EXP_INFIX = DeeElementTypeCache.valueOf("EXP_INFIX");
    IElementType EXP_CONDITIONAL = DeeElementTypeCache.valueOf("EXP_CONDITIONAL");
    IElementType EXP_IS = DeeElementTypeCache.valueOf("EXP_IS");
    IElementType STATIC_IF_EXP_IS = DeeElementTypeCache.valueOf("STATIC_IF_EXP_IS");
    IElementType STATIC_IF_EXP_IS_DEF_UNIT = DeeElementTypeCache.valueOf("STATIC_IF_EXP_IS_DEF_UNIT");
    IElementType EXP_TRAITS = DeeElementTypeCache.valueOf("EXP_TRAITS");
    IElementType DECLARATION_ATTRIB = DeeElementTypeCache.valueOf("DECLARATION_ATTRIB");
    IElementType ATTRIB_LINKAGE = DeeElementTypeCache.valueOf("ATTRIB_LINKAGE");
    IElementType ATTRIB_CPP_LINKAGE = DeeElementTypeCache.valueOf("ATTRIB_CPP_LINKAGE");
    IElementType ATTRIB_ALIGN = DeeElementTypeCache.valueOf("ATTRIB_ALIGN");
    IElementType ATTRIB_PRAGMA = DeeElementTypeCache.valueOf("ATTRIB_PRAGMA");
    IElementType ATTRIB_PROTECTION = DeeElementTypeCache.valueOf("ATTRIB_PROTECTION");
    IElementType ATTRIB_BASIC = DeeElementTypeCache.valueOf("ATTRIB_BASIC");
    IElementType ATTRIB_AT_KEYWORD = DeeElementTypeCache.valueOf("ATTRIB_AT_KEYWORD");
    IElementType ATTRIB_CUSTOM = DeeElementTypeCache.valueOf("ATTRIB_CUSTOM");
    IElementType DECLARATION_MIXIN_STRING = DeeElementTypeCache.valueOf("DECLARATION_MIXIN_STRING");
    IElementType DECLARATION_MIXIN = DeeElementTypeCache.valueOf("DECLARATION_MIXIN");
    IElementType DECLARATION_ALIAS_THIS = DeeElementTypeCache.valueOf("DECLARATION_ALIAS_THIS");
    IElementType DECLARATION_INVARIANT = DeeElementTypeCache.valueOf("DECLARATION_INVARIANT");
    IElementType DECLARATION_UNITEST = DeeElementTypeCache.valueOf("DECLARATION_UNITEST");
    IElementType DECLARATION_ALLOCATOR_FUNCTION = DeeElementTypeCache.valueOf("DECLARATION_ALLOCATOR_FUNCTION");
    IElementType DECLARATION_SPECIAL_FUNCTION = DeeElementTypeCache.valueOf("DECLARATION_SPECIAL_FUNCTION");
    IElementType DECLARATION_DEBUG_VERSION_SPEC = DeeElementTypeCache.valueOf("DECLARATION_DEBUG_VERSION_SPEC");
    IElementType DECLARATION_DEBUG_VERSION = DeeElementTypeCache.valueOf("DECLARATION_DEBUG_VERSION");
    IElementType DECLARATION_STATIC_IF = DeeElementTypeCache.valueOf("DECLARATION_STATIC_IF");
    IElementType DECLARATION_STATIC_ASSERT = DeeElementTypeCache.valueOf("DECLARATION_STATIC_ASSERT");
    IElementType DEFINITION_VARIABLE = DeeElementTypeCache.valueOf("DEFINITION_VARIABLE");
    IElementType DEFINITION_VAR_FRAGMENT = DeeElementTypeCache.valueOf("DEFINITION_VAR_FRAGMENT");
    IElementType DEFINITION_AUTO_VARIABLE = DeeElementTypeCache.valueOf("DEFINITION_AUTO_VARIABLE");
    IElementType CSTYLE_ROOT_REF = DeeElementTypeCache.valueOf("CSTYLE_ROOT_REF");
    IElementType INITIALIZER_VOID = DeeElementTypeCache.valueOf("INITIALIZER_VOID");
    IElementType INITIALIZER_ARRAY = DeeElementTypeCache.valueOf("INITIALIZER_ARRAY");
    IElementType ARRAY_INIT_ENTRY = DeeElementTypeCache.valueOf("ARRAY_INIT_ENTRY");
    IElementType INITIALIZER_STRUCT = DeeElementTypeCache.valueOf("INITIALIZER_STRUCT");
    IElementType STRUCT_INIT_ENTRY = DeeElementTypeCache.valueOf("STRUCT_INIT_ENTRY");

//    IElementType DEFINITION_FUNCTION = DeeElementTypeCache.valueOf("DEFINITION_FUNCTION");
    IElementType DEFINITION_FUNCTION = DElementTypeFactory.factory("DEFINITION_FUNCTION");


    IElementType FUNCTION_PARAMETER = DeeElementTypeCache.valueOf("FUNCTION_PARAMETER");
    IElementType NAMELESS_PARAMETER = DeeElementTypeCache.valueOf("NAMELESS_PARAMETER");
    IElementType VAR_ARGS_PARAMETER = DeeElementTypeCache.valueOf("VAR_ARGS_PARAMETER");
    IElementType FUNCTION_BODY = DeeElementTypeCache.valueOf("FUNCTION_BODY");
    IElementType IN_OUT_FUNCTION_BODY = DeeElementTypeCache.valueOf("IN_OUT_FUNCTION_BODY");
    IElementType FUNCTION_BODY_OUT_BLOCK = DeeElementTypeCache.valueOf("FUNCTION_BODY_OUT_BLOCK");
    IElementType DEFINITION_CONSTRUCTOR = DeeElementTypeCache.valueOf("DEFINITION_CONSTRUCTOR");
    IElementType DEFINITION_STRUCT = DeeElementTypeCache.valueOf("DEFINITION_STRUCT");
    IElementType DEFINITION_UNION = DeeElementTypeCache.valueOf("DEFINITION_UNION");
    IElementType DEFINITION_CLASS = DeeElementTypeCache.valueOf("DEFINITION_CLASS");
    IElementType DEFINITION_INTERFACE = DeeElementTypeCache.valueOf("DEFINITION_INTERFACE");
    IElementType DEFINITION_TEMPLATE = DeeElementTypeCache.valueOf("DEFINITION_TEMPLATE");
    IElementType TEMPLATE_TYPE_PARAM = DeeElementTypeCache.valueOf("TEMPLATE_TYPE_PARAM");
    IElementType TEMPLATE_VALUE_PARAM = DeeElementTypeCache.valueOf("TEMPLATE_VALUE_PARAM");
    IElementType TEMPLATE_ALIAS_PARAM = DeeElementTypeCache.valueOf("TEMPLATE_ALIAS_PARAM");
    IElementType TEMPLATE_TUPLE_PARAM = DeeElementTypeCache.valueOf("TEMPLATE_TUPLE_PARAM");
    IElementType TEMPLATE_THIS_PARAM = DeeElementTypeCache.valueOf("TEMPLATE_THIS_PARAM");
    IElementType DEFINITION_MIXIN_INSTANCE = DeeElementTypeCache.valueOf("DEFINITION_MIXIN_INSTANCE");
    IElementType DEFINITION_ENUM = DeeElementTypeCache.valueOf("DEFINITION_ENUM");
    IElementType DECLARATION_ENUM = DeeElementTypeCache.valueOf("DECLARATION_ENUM");
    IElementType ENUM_BODY = DeeElementTypeCache.valueOf("ENUM_BODY");
    IElementType ENUM_MEMBER = DeeElementTypeCache.valueOf("ENUM_MEMBER");
    IElementType DEFINITION_ENUM_VAR = DeeElementTypeCache.valueOf("DEFINITION_ENUM_VAR");
    IElementType DEFINITION_ENUM_VAR_FRAGMENT = DeeElementTypeCache.valueOf("DEFINITION_ENUM_VAR_FRAGMENT");
    IElementType DEFINITION_ALIAS = DeeElementTypeCache.valueOf("DEFINITION_ALIAS");
    IElementType DEFINITION_ALIAS_FRAGMENT = DeeElementTypeCache.valueOf("DEFINITION_ALIAS_FRAGMENT");
    IElementType DEFINITION_ALIAS_VAR_DECL = DeeElementTypeCache.valueOf("DEFINITION_ALIAS_VAR_DECL");
    IElementType DEFINITION_ALIAS_FUNCTION_DECL = DeeElementTypeCache.valueOf("DEFINITION_ALIAS_FUNCTION_DECL");
    IElementType ALIAS_VAR_DECL_FRAGMENT = DeeElementTypeCache.valueOf("ALIAS_VAR_DECL_FRAGMENT");
    IElementType TEMPLATE_TYPE_PARAM__INSTANCE = DeeElementTypeCache.valueOf("TEMPLATE_TYPE_PARAM__INSTANCE");
    IElementType TEMPLATE_VALUE_PARAM__INSTANCE = DeeElementTypeCache.valueOf("TEMPLATE_VALUE_PARAM__INSTANCE");
    IElementType TEMPLATE_ALIAS_PARAM__INSTANCE = DeeElementTypeCache.valueOf("TEMPLATE_ALIAS_PARAM__INSTANCE");
    IElementType BLOCK_STATEMENT = DeeElementTypeCache.valueOf("BLOCK_STATEMENT");
    IElementType BLOCK_STATEMENT_UNSCOPED = DeeElementTypeCache.valueOf("BLOCK_STATEMENT_UNSCOPED");
    IElementType EMPTY_STATEMENT = DeeElementTypeCache.valueOf("EMPTY_STATEMENT");
    IElementType SCOPED_STATEMENT_LIST = DeeElementTypeCache.valueOf("SCOPED_STATEMENT_LIST");
    IElementType STATEMENT_EXPRESSION = DeeElementTypeCache.valueOf("STATEMENT_EXPRESSION");
    IElementType STATEMENT_LABEL = DeeElementTypeCache.valueOf("STATEMENT_LABEL");
    IElementType STATEMENT_IF = DeeElementTypeCache.valueOf("STATEMENT_IF");
    IElementType STATEMENT_IF_VAR = DeeElementTypeCache.valueOf("STATEMENT_IF_VAR");
    IElementType VARIABLE_DEF_WITH_INIT = DeeElementTypeCache.valueOf("VARIABLE_DEF_WITH_INIT");
    IElementType STATEMENT_WHILE = DeeElementTypeCache.valueOf("STATEMENT_WHILE");
    IElementType STATEMENT_DO_WHILE = DeeElementTypeCache.valueOf("STATEMENT_DO_WHILE");
    IElementType STATEMENT_FOR = DeeElementTypeCache.valueOf("STATEMENT_FOR");
    IElementType STATEMENT_FOREACH = DeeElementTypeCache.valueOf("STATEMENT_FOREACH");
    IElementType FOREACH_VARIABLE_DEF = DeeElementTypeCache.valueOf("FOREACH_VARIABLE_DEF");
    IElementType STATEMENT_SWITCH = DeeElementTypeCache.valueOf("STATEMENT_SWITCH");
    IElementType STATEMENT_CASE = DeeElementTypeCache.valueOf("STATEMENT_CASE");
    IElementType STATEMENT_CASE_RANGE = DeeElementTypeCache.valueOf("STATEMENT_CASE_RANGE");
    IElementType STATEMENT_DEFAULT = DeeElementTypeCache.valueOf("STATEMENT_DEFAULT");
    IElementType STATEMENT_CONTINUE = DeeElementTypeCache.valueOf("STATEMENT_CONTINUE");
    IElementType STATEMENT_BREAK = DeeElementTypeCache.valueOf("STATEMENT_BREAK");
    IElementType STATEMENT_RETURN = DeeElementTypeCache.valueOf("STATEMENT_RETURN");
    IElementType STATEMENT_GOTO = DeeElementTypeCache.valueOf("STATEMENT_GOTO");
    IElementType STATEMENT_GOTO_DEFAULT = DeeElementTypeCache.valueOf("STATEMENT_GOTO_DEFAULT");
    IElementType STATEMENT_GOTO_CASE = DeeElementTypeCache.valueOf("STATEMENT_GOTO_CASE");
    IElementType STATEMENT_THROW = DeeElementTypeCache.valueOf("STATEMENT_THROW");
    IElementType STATEMENT_SYNCHRONIZED = DeeElementTypeCache.valueOf("STATEMENT_SYNCHRONIZED");
    IElementType STATEMENT_WITH = DeeElementTypeCache.valueOf("STATEMENT_WITH");
    IElementType STATEMENT_ASM = DeeElementTypeCache.valueOf("STATEMENT_ASM");
    IElementType STATEMENT_SCOPE = DeeElementTypeCache.valueOf("STATEMENT_SCOPE");
    IElementType STATEMENT_TRY = DeeElementTypeCache.valueOf("STATEMENT_TRY");
    IElementType TRY_CATCH_CLAUSE = DeeElementTypeCache.valueOf("TRY_CATCH_CLAUSE");
    IElementType SIMPLE_VARIABLE_DEF = DeeElementTypeCache.valueOf("SIMPLE_VARIABLE_DEF");

    // Token Types supported by DeeLexer
    IElementType EOF = DeeTokenLookUp.valueOf(DeeTokens.EOF);
    IElementType INVALID_TOKEN = DeeTokenLookUp.valueOf(DeeTokens.INVALID_TOKEN);
    IElementType LINE_END = DeeTokenLookUp.valueOf(DeeTokens.LINE_END);
    IElementType WHITESPACE = DeeTokenLookUp.valueOf(DeeTokens.WHITESPACE);
    IElementType GROUP_COMMENT = DeeTokenLookUp.valueOf(DeeTokens.GROUP_COMMENT);
    IElementType COMMENT_MULTI = DeeTokenLookUp.valueOf(DeeTokens.COMMENT_MULTI);
    IElementType COMMENT_NESTED = DeeTokenLookUp.valueOf(DeeTokens.COMMENT_NESTED);
    IElementType COMMENT_LINE = DeeTokenLookUp.valueOf(DeeTokens.COMMENT_LINE);
    IElementType DOCCOMMENT_MULTI = DeeTokenLookUp.valueOf(DeeTokens.DOCCOMMENT_MULTI);
    IElementType DOCCOMMENT_NESTED = DeeTokenLookUp.valueOf(DeeTokens.DOCCOMMENT_NESTED);
    IElementType DOCCOMMENT_LINE = DeeTokenLookUp.valueOf(DeeTokens.DOCCOMMENT_LINE);
    IElementType SCRIPT_LINE_INTRO = DeeTokenLookUp.valueOf(DeeTokens.SCRIPT_LINE_INTRO);
    IElementType SPECIAL_TOKEN_LINE = DeeTokenLookUp.valueOf(DeeTokens.SPECIAL_TOKEN_LINE);
    IElementType IDENTIFIER = DeeTokenLookUp.valueOf(DeeTokens.IDENTIFIER);
    IElementType GROUP_STRING = DeeTokenLookUp.valueOf(DeeTokens.GROUP_STRING);
    IElementType STRING_WYSIWYG = DeeTokenLookUp.valueOf(DeeTokens.STRING_WYSIWYG);
    IElementType STRING_DQ = DeeTokenLookUp.valueOf(DeeTokens.STRING_DQ);
    IElementType STRING_HEX = DeeTokenLookUp.valueOf(DeeTokens.STRING_HEX);
    IElementType STRING_DELIM = DeeTokenLookUp.valueOf(DeeTokens.STRING_DELIM);
    IElementType STRING_TOKENS = DeeTokenLookUp.valueOf(DeeTokens.STRING_TOKENS);
    IElementType CHARACTER = DeeTokenLookUp.valueOf(DeeTokens.CHARACTER);
    IElementType GROUP_INTEGER = DeeTokenLookUp.valueOf(DeeTokens.GROUP_INTEGER);
    IElementType INTEGER_DECIMAL = DeeTokenLookUp.valueOf(DeeTokens.INTEGER_DECIMAL);
    IElementType INTEGER_BINARY = DeeTokenLookUp.valueOf(DeeTokens.INTEGER_BINARY);
    IElementType INTEGER_OCTAL = DeeTokenLookUp.valueOf(DeeTokens.INTEGER_OCTAL);
    IElementType INTEGER_HEX = DeeTokenLookUp.valueOf(DeeTokens.INTEGER_HEX);
    IElementType GROUP_FLOAT = DeeTokenLookUp.valueOf(DeeTokens.GROUP_FLOAT);
    IElementType FLOAT_DECIMAL = DeeTokenLookUp.valueOf(DeeTokens.FLOAT_DECIMAL);
    IElementType FLOAT_HEX = DeeTokenLookUp.valueOf(DeeTokens.FLOAT_HEX);
    IElementType OPEN_PARENS = DeeTokenLookUp.valueOf(DeeTokens.OPEN_PARENS);
    IElementType CLOSE_PARENS = DeeTokenLookUp.valueOf(DeeTokens.CLOSE_PARENS);
    IElementType OPEN_BRACE = DeeTokenLookUp.valueOf(DeeTokens.OPEN_BRACE);
    IElementType CLOSE_BRACE = DeeTokenLookUp.valueOf(DeeTokens.CLOSE_BRACE);
    IElementType OPEN_BRACKET = DeeTokenLookUp.valueOf(DeeTokens.OPEN_BRACKET);
    IElementType CLOSE_BRACKET = DeeTokenLookUp.valueOf(DeeTokens.CLOSE_BRACKET);
    IElementType SEMICOLON = DeeTokenLookUp.valueOf(DeeTokens.SEMICOLON);
    IElementType COLON = DeeTokenLookUp.valueOf(DeeTokens.COLON);
    IElementType QUESTION = DeeTokenLookUp.valueOf(DeeTokens.QUESTION);
    IElementType COMMA = DeeTokenLookUp.valueOf(DeeTokens.COMMA);
    IElementType DOLLAR = DeeTokenLookUp.valueOf(DeeTokens.DOLLAR);
    IElementType AT = DeeTokenLookUp.valueOf(DeeTokens.AT);
    IElementType DOT = DeeTokenLookUp.valueOf(DeeTokens.DOT);
    IElementType DOUBLE_DOT = DeeTokenLookUp.valueOf(DeeTokens.DOUBLE_DOT);
    IElementType TRIPLE_DOT = DeeTokenLookUp.valueOf(DeeTokens.TRIPLE_DOT);
    IElementType DECREMENT = DeeTokenLookUp.valueOf(DeeTokens.DECREMENT);
    IElementType INCREMENT = DeeTokenLookUp.valueOf(DeeTokens.INCREMENT);
    IElementType MINUS = DeeTokenLookUp.valueOf(DeeTokens.MINUS);
    IElementType MINUS_ASSIGN = DeeTokenLookUp.valueOf(DeeTokens.MINUS_ASSIGN);
    IElementType PLUS = DeeTokenLookUp.valueOf(DeeTokens.PLUS);
    IElementType PLUS_ASSIGN = DeeTokenLookUp.valueOf(DeeTokens.PLUS_ASSIGN);
    IElementType DIV = DeeTokenLookUp.valueOf(DeeTokens.DIV);
    IElementType DIV_ASSIGN = DeeTokenLookUp.valueOf(DeeTokens.DIV_ASSIGN);
    IElementType STAR = DeeTokenLookUp.valueOf(DeeTokens.STAR);
    IElementType MULT_ASSIGN = DeeTokenLookUp.valueOf(DeeTokens.MULT_ASSIGN);
    IElementType MOD = DeeTokenLookUp.valueOf(DeeTokens.MOD);
    IElementType MOD_ASSIGN = DeeTokenLookUp.valueOf(DeeTokens.MOD_ASSIGN);
    IElementType POW = DeeTokenLookUp.valueOf(DeeTokens.POW);
    IElementType POW_ASSIGN = DeeTokenLookUp.valueOf(DeeTokens.POW_ASSIGN);
    IElementType AND = DeeTokenLookUp.valueOf(DeeTokens.AND);
    IElementType AND_ASSIGN = DeeTokenLookUp.valueOf(DeeTokens.AND_ASSIGN);
    IElementType OR = DeeTokenLookUp.valueOf(DeeTokens.OR);
    IElementType OR_ASSIGN = DeeTokenLookUp.valueOf(DeeTokens.OR_ASSIGN);
    IElementType XOR = DeeTokenLookUp.valueOf(DeeTokens.XOR);
    IElementType XOR_ASSIGN = DeeTokenLookUp.valueOf(DeeTokens.XOR_ASSIGN);
    IElementType CONCAT = DeeTokenLookUp.valueOf(DeeTokens.CONCAT);
    IElementType CONCAT_ASSIGN = DeeTokenLookUp.valueOf(DeeTokens.CONCAT_ASSIGN);
    IElementType LOGICAL_AND = DeeTokenLookUp.valueOf(DeeTokens.LOGICAL_AND);
    IElementType LOGICAL_OR = DeeTokenLookUp.valueOf(DeeTokens.LOGICAL_OR);
    IElementType LAMBDA = DeeTokenLookUp.valueOf(DeeTokens.LAMBDA);
    IElementType ASSIGN = DeeTokenLookUp.valueOf(DeeTokens.ASSIGN);
    IElementType EQUALS = DeeTokenLookUp.valueOf(DeeTokens.EQUALS);
    IElementType NOT = DeeTokenLookUp.valueOf(DeeTokens.NOT);
    IElementType NOT_EQUAL = DeeTokenLookUp.valueOf(DeeTokens.NOT_EQUAL);
    IElementType LESS_THAN = DeeTokenLookUp.valueOf(DeeTokens.LESS_THAN);
    IElementType LESS_EQUAL = DeeTokenLookUp.valueOf(DeeTokens.LESS_EQUAL);
    IElementType GREATER_THAN = DeeTokenLookUp.valueOf(DeeTokens.GREATER_THAN);
    IElementType GREATER_EQUAL = DeeTokenLookUp.valueOf(DeeTokens.GREATER_EQUAL);
    IElementType LESS_GREATER = DeeTokenLookUp.valueOf(DeeTokens.LESS_GREATER);
    IElementType LESS_GREATER_EQUAL = DeeTokenLookUp.valueOf(DeeTokens.LESS_GREATER_EQUAL);
    IElementType UNORDERED_E = DeeTokenLookUp.valueOf(DeeTokens.UNORDERED_E);
    IElementType UNORDERED = DeeTokenLookUp.valueOf(DeeTokens.UNORDERED);
    IElementType UNORDERED_GE = DeeTokenLookUp.valueOf(DeeTokens.UNORDERED_GE);
    IElementType UNORDERED_G = DeeTokenLookUp.valueOf(DeeTokens.UNORDERED_G);
    IElementType UNORDERED_LE = DeeTokenLookUp.valueOf(DeeTokens.UNORDERED_LE);
    IElementType UNORDERED_L = DeeTokenLookUp.valueOf(DeeTokens.UNORDERED_L);
    IElementType LEFT_SHIFT = DeeTokenLookUp.valueOf(DeeTokens.LEFT_SHIFT);
    IElementType LEFT_SHIFT_ASSIGN = DeeTokenLookUp.valueOf(DeeTokens.LEFT_SHIFT_ASSIGN);
    IElementType RIGHT_SHIFT = DeeTokenLookUp.valueOf(DeeTokens.RIGHT_SHIFT);
    IElementType RIGHT_SHIFT_ASSIGN = DeeTokenLookUp.valueOf(DeeTokens.RIGHT_SHIFT_ASSIGN);
    IElementType TRIPLE_RSHIFT = DeeTokenLookUp.valueOf(DeeTokens.TRIPLE_RSHIFT);
    IElementType TRIPLE_RSHIFT_ASSIGN = DeeTokenLookUp.valueOf(DeeTokens.TRIPLE_RSHIFT_ASSIGN);
    IElementType GROUP_PRIMITIVE_KW = DeeTokenLookUp.valueOf(DeeTokens.GROUP_PRIMITIVE_KW);
    IElementType KW_BOOL = DeeTokenLookUp.valueOf(DeeTokens.KW_BOOL);
    IElementType KW_VOID = DeeTokenLookUp.valueOf(DeeTokens.KW_VOID);
    IElementType KW_BYTE = DeeTokenLookUp.valueOf(DeeTokens.KW_BYTE);
    IElementType KW_UBYTE = DeeTokenLookUp.valueOf(DeeTokens.KW_UBYTE);
    IElementType KW_SHORT = DeeTokenLookUp.valueOf(DeeTokens.KW_SHORT);
    IElementType KW_USHORT = DeeTokenLookUp.valueOf(DeeTokens.KW_USHORT);
    IElementType KW_INT = DeeTokenLookUp.valueOf(DeeTokens.KW_INT);
    IElementType KW_UINT = DeeTokenLookUp.valueOf(DeeTokens.KW_UINT);
    IElementType KW_LONG = DeeTokenLookUp.valueOf(DeeTokens.KW_LONG);
    IElementType KW_ULONG = DeeTokenLookUp.valueOf(DeeTokens.KW_ULONG);
    IElementType KW_CENT = DeeTokenLookUp.valueOf(DeeTokens.KW_CENT);
    IElementType KW_UCENT = DeeTokenLookUp.valueOf(DeeTokens.KW_UCENT);
    IElementType KW_CHAR = DeeTokenLookUp.valueOf(DeeTokens.KW_CHAR);
    IElementType KW_WCHAR = DeeTokenLookUp.valueOf(DeeTokens.KW_WCHAR);
    IElementType KW_DCHAR = DeeTokenLookUp.valueOf(DeeTokens.KW_DCHAR);
    IElementType KW_FLOAT = DeeTokenLookUp.valueOf(DeeTokens.KW_FLOAT);
    IElementType KW_DOUBLE = DeeTokenLookUp.valueOf(DeeTokens.KW_DOUBLE);
    IElementType KW_REAL = DeeTokenLookUp.valueOf(DeeTokens.KW_REAL);
    IElementType KW_IFLOAT = DeeTokenLookUp.valueOf(DeeTokens.KW_IFLOAT);
    IElementType KW_IDOUBLE = DeeTokenLookUp.valueOf(DeeTokens.KW_IDOUBLE);
    IElementType KW_IREAL = DeeTokenLookUp.valueOf(DeeTokens.KW_IREAL);
    IElementType KW_CFLOAT = DeeTokenLookUp.valueOf(DeeTokens.KW_CFLOAT);
    IElementType KW_CDOUBLE = DeeTokenLookUp.valueOf(DeeTokens.KW_CDOUBLE);
    IElementType KW_CREAL = DeeTokenLookUp.valueOf(DeeTokens.KW_CREAL);
    IElementType GROUP_PROTECTION_KW = DeeTokenLookUp.valueOf(DeeTokens.GROUP_PROTECTION_KW);
    IElementType KW_PRIVATE = DeeTokenLookUp.valueOf(DeeTokens.KW_PRIVATE);
    IElementType KW_PACKAGE = DeeTokenLookUp.valueOf(DeeTokens.KW_PACKAGE);
    IElementType KW_PROTECTED = DeeTokenLookUp.valueOf(DeeTokens.KW_PROTECTED);
    IElementType KW_PUBLIC = DeeTokenLookUp.valueOf(DeeTokens.KW_PUBLIC);
    IElementType KW_EXPORT = DeeTokenLookUp.valueOf(DeeTokens.KW_EXPORT);
    IElementType GROUP_ATTRIBUTE_KW = DeeTokenLookUp.valueOf(DeeTokens.GROUP_ATTRIBUTE_KW);
    IElementType KW_ABSTRACT = DeeTokenLookUp.valueOf(DeeTokens.KW_ABSTRACT);
    IElementType KW_DEPRECATED = DeeTokenLookUp.valueOf(DeeTokens.KW_DEPRECATED);
    IElementType KW_FINAL = DeeTokenLookUp.valueOf(DeeTokens.KW_FINAL);
    IElementType KW_NOTHROW = DeeTokenLookUp.valueOf(DeeTokens.KW_NOTHROW);
    IElementType KW_OVERRIDE = DeeTokenLookUp.valueOf(DeeTokens.KW_OVERRIDE);
    IElementType KW_PURE = DeeTokenLookUp.valueOf(DeeTokens.KW_PURE);
    IElementType KW_SCOPE = DeeTokenLookUp.valueOf(DeeTokens.KW_SCOPE);
    IElementType KW_STATIC = DeeTokenLookUp.valueOf(DeeTokens.KW_STATIC);
    IElementType KW_SYNCHRONIZED = DeeTokenLookUp.valueOf(DeeTokens.KW_SYNCHRONIZED);
    IElementType KW_REF = DeeTokenLookUp.valueOf(DeeTokens.KW_REF);
    IElementType KW_CONST = DeeTokenLookUp.valueOf(DeeTokens.KW_CONST);
    IElementType KW_IMMUTABLE = DeeTokenLookUp.valueOf(DeeTokens.KW_IMMUTABLE);
    IElementType KW_INOUT = DeeTokenLookUp.valueOf(DeeTokens.KW_INOUT);
    IElementType KW_SHARED = DeeTokenLookUp.valueOf(DeeTokens.KW_SHARED);
    IElementType KW_AUTO = DeeTokenLookUp.valueOf(DeeTokens.KW_AUTO);
    IElementType KW_ALIAS = DeeTokenLookUp.valueOf(DeeTokens.KW_ALIAS);
    IElementType KW_ALIGN = DeeTokenLookUp.valueOf(DeeTokens.KW_ALIGN);
    IElementType KW_ASM = DeeTokenLookUp.valueOf(DeeTokens.KW_ASM);
    IElementType KW_ASSERT = DeeTokenLookUp.valueOf(DeeTokens.KW_ASSERT);
    IElementType KW_BODY = DeeTokenLookUp.valueOf(DeeTokens.KW_BODY);
    IElementType KW_BREAK = DeeTokenLookUp.valueOf(DeeTokens.KW_BREAK);
    IElementType KW_CASE = DeeTokenLookUp.valueOf(DeeTokens.KW_CASE);
    IElementType KW_CAST = DeeTokenLookUp.valueOf(DeeTokens.KW_CAST);
    IElementType KW_CATCH = DeeTokenLookUp.valueOf(DeeTokens.KW_CATCH);
    IElementType KW_CLASS = DeeTokenLookUp.valueOf(DeeTokens.KW_CLASS);
    IElementType KW_CONTINUE = DeeTokenLookUp.valueOf(DeeTokens.KW_CONTINUE);
    IElementType KW_DEBUG = DeeTokenLookUp.valueOf(DeeTokens.KW_DEBUG);
    IElementType KW_DEFAULT = DeeTokenLookUp.valueOf(DeeTokens.KW_DEFAULT);
    IElementType KW_DELEGATE = DeeTokenLookUp.valueOf(DeeTokens.KW_DELEGATE);
    IElementType KW_DELETE = DeeTokenLookUp.valueOf(DeeTokens.KW_DELETE);
    IElementType KW_DO = DeeTokenLookUp.valueOf(DeeTokens.KW_DO);
    IElementType KW_ELSE = DeeTokenLookUp.valueOf(DeeTokens.KW_ELSE);
    IElementType KW_ENUM = DeeTokenLookUp.valueOf(DeeTokens.KW_ENUM);
    IElementType KW_EXTERN = DeeTokenLookUp.valueOf(DeeTokens.KW_EXTERN);
    IElementType KW_FALSE = DeeTokenLookUp.valueOf(DeeTokens.KW_FALSE);
    IElementType KW_FINALLY = DeeTokenLookUp.valueOf(DeeTokens.KW_FINALLY);
    IElementType KW_FOR = DeeTokenLookUp.valueOf(DeeTokens.KW_FOR);
    IElementType KW_FOREACH = DeeTokenLookUp.valueOf(DeeTokens.KW_FOREACH);
    IElementType KW_FOREACH_REVERSE = DeeTokenLookUp.valueOf(DeeTokens.KW_FOREACH_REVERSE);
    IElementType KW_FUNCTION = DeeTokenLookUp.valueOf(DeeTokens.KW_FUNCTION);
    IElementType KW_GOTO = DeeTokenLookUp.valueOf(DeeTokens.KW_GOTO);
    IElementType KW_IF = DeeTokenLookUp.valueOf(DeeTokens.KW_IF);
    IElementType KW_IMPORT = DeeTokenLookUp.valueOf(DeeTokens.KW_IMPORT);
    IElementType KW_IN = DeeTokenLookUp.valueOf(DeeTokens.KW_IN);
    IElementType KW_INTERFACE = DeeTokenLookUp.valueOf(DeeTokens.KW_INTERFACE);
    IElementType KW_INVARIANT = DeeTokenLookUp.valueOf(DeeTokens.KW_INVARIANT);
    IElementType KW_IS = DeeTokenLookUp.valueOf(DeeTokens.KW_IS);
    IElementType KW_LAZY = DeeTokenLookUp.valueOf(DeeTokens.KW_LAZY);
    IElementType KW_MACRO = DeeTokenLookUp.valueOf(DeeTokens.KW_MACRO);
    IElementType KW_MIXIN = DeeTokenLookUp.valueOf(DeeTokens.KW_MIXIN);
    IElementType KW_MODULE = DeeTokenLookUp.valueOf(DeeTokens.KW_MODULE);
    IElementType KW_NEW = DeeTokenLookUp.valueOf(DeeTokens.KW_NEW);
    IElementType KW_NULL = DeeTokenLookUp.valueOf(DeeTokens.KW_NULL);
    IElementType KW_OUT = DeeTokenLookUp.valueOf(DeeTokens.KW_OUT);
    IElementType KW_PRAGMA = DeeTokenLookUp.valueOf(DeeTokens.KW_PRAGMA);
    IElementType KW_RETURN = DeeTokenLookUp.valueOf(DeeTokens.KW_RETURN);
    IElementType KW_STRUCT = DeeTokenLookUp.valueOf(DeeTokens.KW_STRUCT);
    IElementType KW_SUPER = DeeTokenLookUp.valueOf(DeeTokens.KW_SUPER);
    IElementType KW_SWITCH = DeeTokenLookUp.valueOf(DeeTokens.KW_SWITCH);
    IElementType KW_TEMPLATE = DeeTokenLookUp.valueOf(DeeTokens.KW_TEMPLATE);
    IElementType KW_THIS = DeeTokenLookUp.valueOf(DeeTokens.KW_THIS);
    IElementType KW_THROW = DeeTokenLookUp.valueOf(DeeTokens.KW_THROW);
    IElementType KW_TRUE = DeeTokenLookUp.valueOf(DeeTokens.KW_TRUE);
    IElementType KW_TRY = DeeTokenLookUp.valueOf(DeeTokens.KW_TRY);
    IElementType KW_TYPEDEF = DeeTokenLookUp.valueOf(DeeTokens.KW_TYPEDEF);
    IElementType KW_TYPEID = DeeTokenLookUp.valueOf(DeeTokens.KW_TYPEID);
    IElementType KW_TYPEOF = DeeTokenLookUp.valueOf(DeeTokens.KW_TYPEOF);
    IElementType KW_UNION = DeeTokenLookUp.valueOf(DeeTokens.KW_UNION);
    IElementType KW_UNITTEST = DeeTokenLookUp.valueOf(DeeTokens.KW_UNITTEST);
    IElementType KW_VERSION = DeeTokenLookUp.valueOf(DeeTokens.KW_VERSION);
    IElementType KW_VOLATILE = DeeTokenLookUp.valueOf(DeeTokens.KW_VOLATILE);
    IElementType KW_WHILE = DeeTokenLookUp.valueOf(DeeTokens.KW_WHILE);
    IElementType KW_WITH = DeeTokenLookUp.valueOf(DeeTokens.KW_WITH);
    IElementType KW___TRAITS = DeeTokenLookUp.valueOf(DeeTokens.KW___TRAITS);
    IElementType KW___GSHARED = DeeTokenLookUp.valueOf(DeeTokens.KW___GSHARED);
    IElementType KW___THREAD = DeeTokenLookUp.valueOf(DeeTokens.KW___THREAD);
    IElementType KW___VECTOR = DeeTokenLookUp.valueOf(DeeTokens.KW___VECTOR);
    IElementType KW___FILE__ = DeeTokenLookUp.valueOf(DeeTokens.KW___FILE__);
    IElementType KW___LINE__ = DeeTokenLookUp.valueOf(DeeTokens.KW___LINE__);
    IElementType KW___MODULE__ = DeeTokenLookUp.valueOf(DeeTokens.KW___MODULE__);
    IElementType KW___FUNCTION__ = DeeTokenLookUp.valueOf(DeeTokens.KW___FUNCTION__);
    IElementType KW___PRETTY_FUNCTION__ = DeeTokenLookUp.valueOf(DeeTokens.KW___PRETTY_FUNCTION__);
    IElementType KW___DATE__ = DeeTokenLookUp.valueOf(DeeTokens.KW___DATE__);
    IElementType KW___TIME__ = DeeTokenLookUp.valueOf(DeeTokens.KW___TIME__);
    IElementType KW___TIMESTAMP__ = DeeTokenLookUp.valueOf(DeeTokens.KW___TIMESTAMP__);
    IElementType KW___VENDOR__ = DeeTokenLookUp.valueOf(DeeTokens.KW___VENDOR__);
    IElementType KW___VERSION__ = DeeTokenLookUp.valueOf(DeeTokens.KW___VERSION__);


    class Factory {
        public static PsiElement createElement(ASTNode node) {
            IElementType type = node.getElementType();

            if (type == DECLARATION_MODULE) {
                return new DDeclarationModuleImpl(node);
            } else if (type == NULL) {
                return new DNullImpl(node);
            } else if (type == SYMBOL) {
                return new DSymbolImpl(node);
            } else if (type == MODULE) {
                return new DModuleImpl(node);
            } else if (type == DECLARATION_IMPORT) {
                return new DDeclarationImportImpl(node);
            } else if (type == IMPORT_CONTENT) {
                return new DImportContentImpl(node);
            } else if (type == IMPORT_ALIAS) {
                return new DImportAliasImpl(node);
            } else if (type == IMPORT_SELECTIVE) {
                return new DImportSelectiveImpl(node);
            } else if (type == IMPORT_SELECTIVE_ALIAS) {
                return new DImportSelectiveAliasImpl(node);
            } else if (type == DECL_LIST) {
                return new DDeclListImpl(node);
            } else if (type == DECL_BLOCK) {
                return new DDeclBlockImpl(node);
            } else if (type == DECLARATION_EMTPY) {
                return new DDeclarationEmtpyImpl(node);
            } else if (type == MISSING_DECLARATION) {
                return new DMissingDeclarationImpl(node);
            } else if (type == INVALID_SYNTAX) {
                return new DInvalidSyntaxImpl(node);
            } else if (type == INCOMPLETE_DECLARATOR) {
                return new DIncompleteDeclaratorImpl(node);
            } else if (type == REF_IMPORT_SELECTION) {
                return new DRefImportSelectionImpl(node);
            } else if (type == REF_MODULE) {
                return new DRefModuleImpl(node);
            } else if (type == REF_IDENTIFIER) {
                return new DRefIdentifierImpl(node);
            } else if (type == REF_QUALIFIED) {
                return new DRefQualifiedImpl(node);
            } else if (type == REF_MODULE_QUALIFIED) {
                return new DRefModuleQualifiedImpl(node);
            } else if (type == REF_PRIMITIVE) {
                return new DRefPrimitiveImpl(node);
            } else if (type == REF_TYPE_DYN_ARRAY) {
                return new DRefTypeDynArrayImpl(node);
            } else if (type == REF_TYPE_POINTER) {
                return new DRefTypePointerImpl(node);
            } else if (type == REF_INDEXING) {
                return new DRefIndexingImpl(node);
            } else if (type == REF_SLICE) {
                return new DRefSliceImpl(node);
            } else if (type == REF_TYPE_FUNCTION) {
                return new DRefTypeFunctionImpl(node);
            } else if (type == REF_TEMPLATE_INSTANCE) {
                return new DRefTemplateInstanceImpl(node);
            } else if (type == REF_TYPEOF) {
                return new DRefTypeofImpl(node);
            } else if (type == REF_MODIFIER) {
                return new DRefModifierImpl(node);
            } else if (type == REF_AUTO) {
                return new DRefAutoImpl(node);
            } else if (type == MISSING_EXPRESSION) {
                return new DMissingExpressionImpl(node);
            } else if (type == EXP_REF_RETURN) {
                return new DExpRefReturnImpl(node);
            } else if (type == EXP_THIS) {
                return new DExpThisImpl(node);
            } else if (type == EXP_SUPER) {
                return new DExpSuperImpl(node);
            } else if (type == EXP_NULL) {
                return new DExpNullImpl(node);
            } else if (type == EXP_ARRAY_LENGTH) {
                return new DExpArrayLengthImpl(node);
            } else if (type == EXP_LITERAL_BOOL) {
                return new DExpLiteralBoolImpl(node);
            } else if (type == EXP_LITERAL_INTEGER) {
                return new DExpLiteralIntegerImpl(node);
            } else if (type == EXP_LITERAL_STRING) {
                return new DExpLiteralStringImpl(node);
            } else if (type == EXP_LITERAL_CHAR) {
                return new DExpLiteralCharImpl(node);
            } else if (type == EXP_LITERAL_FLOAT) {
                return new DExpLiteralFloatImpl(node);
            } else if (type == EXP_LITERAL_ARRAY) {
                return new DExpLiteralArrayImpl(node);
            } else if (type == EXP_LITERAL_MAPARRAY) {
                return new DExpLiteralMaparrayImpl(node);
            } else if (type == MAPARRAY_ENTRY) {
                return new DMaparrayEntryImpl(node);
            } else if (type == EXP_FUNCTION_LITERAL) {
                return new DExpFunctionLiteralImpl(node);
            } else if (type == EXP_SIMPLE_LAMBDA) {
                return new DExpSimpleLambdaImpl(node);
            } else if (type == SIMPLE_LAMBDA_DEFUNIT) {
                return new DSimpleLambdaDefunitImpl(node);
            } else if (type == EXP_REFERENCE) {
                return new DExpReferenceImpl(node);
            } else if (type == EXP_PARENTHESES) {
                return new DExpParenthesesImpl(node);
            } else if (type == EXP_ASSERT) {
                return new DExpAssertImpl(node);
            } else if (type == EXP_MIXIN_STRING) {
                return new DExpMixinStringImpl(node);
            } else if (type == EXP_IMPORT_STRING) {
                return new DExpImportStringImpl(node);
            } else if (type == EXP_TYPEID) {
                return new DExpTypeidImpl(node);
            } else if (type == EXP_INDEX) {
                return new DExpIndexImpl(node);
            } else if (type == EXP_CALL) {
                return new DExpCallImpl(node);
            } else if (type == EXP_PREFIX) {
                return new DExpPrefixImpl(node);
            } else if (type == EXP_NEW) {
                return new DExpNewImpl(node);
            } else if (type == EXP_NEW_ANON_CLASS) {
                return new DExpNewAnonClassImpl(node);
            } else if (type == EXP_CAST) {
                return new DExpCastImpl(node);
            } else if (type == EXP_CAST_QUAL) {
                return new DExpCastQualImpl(node);
            } else if (type == EXP_POSTFIX_OP) {
                return new DExpPostfixOpImpl(node);
            } else if (type == EXP_INFIX) {
                return new DExpInfixImpl(node);
            } else if (type == EXP_CONDITIONAL) {
                return new DExpConditionalImpl(node);
            } else if (type == EXP_IS) {
                return new DExpIsImpl(node);
            } else if (type == STATIC_IF_EXP_IS) {
                return new DStaticIfExpIsImpl(node);
            } else if (type == STATIC_IF_EXP_IS_DEF_UNIT) {
                return new DStaticIfExpIsDefUnitImpl(node);
            } else if (type == EXP_TRAITS) {
                return new DExpTraitsImpl(node);
            } else if (type == DECLARATION_ATTRIB) {
                return new DDeclarationAttribImpl(node);
            } else if (type == ATTRIB_LINKAGE) {
                return new DAttribLinkageImpl(node);
            } else if (type == ATTRIB_CPP_LINKAGE) {
                return new DAttribCppLinkageImpl(node);
            } else if (type == ATTRIB_ALIGN) {
                return new DAttribAlignImpl(node);
            } else if (type == ATTRIB_PRAGMA) {
                return new DAttribPragmaImpl(node);
            } else if (type == ATTRIB_PROTECTION) {
                return new DAttribProtectionImpl(node);
            } else if (type == ATTRIB_BASIC) {
                return new DAttribBasicImpl(node);
            } else if (type == ATTRIB_AT_KEYWORD) {
                return new DAttribAtKeywordImpl(node);
            } else if (type == ATTRIB_CUSTOM) {
                return new DAttribCustomImpl(node);
            } else if (type == DECLARATION_MIXIN_STRING) {
                return new DDeclarationMixinStringImpl(node);
            } else if (type == DECLARATION_MIXIN) {
                return new DDeclarationMixinImpl(node);
            } else if (type == DECLARATION_ALIAS_THIS) {
                return new DDeclarationAliasThisImpl(node);
            } else if (type == DECLARATION_INVARIANT) {
                return new DDeclarationInvariantImpl(node);
            } else if (type == DECLARATION_UNITEST) {
                return new DDeclarationUnitestImpl(node);
            } else if (type == DECLARATION_ALLOCATOR_FUNCTION) {
                return new DDeclarationAllocatorFunctionImpl(node);
            } else if (type == DECLARATION_SPECIAL_FUNCTION) {
                return new DDeclarationSpecialFunctionImpl(node);
            } else if (type == DECLARATION_DEBUG_VERSION_SPEC) {
                return new DDeclarationDebugVersionSpecImpl(node);
            } else if (type == DECLARATION_DEBUG_VERSION) {
                return new DDeclarationDebugVersionImpl(node);
            } else if (type == DECLARATION_STATIC_IF) {
                return new DDeclarationStaticIfImpl(node);
            } else if (type == DECLARATION_STATIC_ASSERT) {
                return new DDeclarationStaticAssertImpl(node);
            } else if (type == DEFINITION_VARIABLE) {
                return new DDefinitionVariableImpl(node);
            } else if (type == DEFINITION_VAR_FRAGMENT) {
                return new DDefinitionVarFragmentImpl(node);
            } else if (type == DEFINITION_AUTO_VARIABLE) {
                return new DDefinitionAutoVariableImpl(node);
            } else if (type == CSTYLE_ROOT_REF) {
                return new DCstyleRootRefImpl(node);
            } else if (type == INITIALIZER_VOID) {
                return new DInitializerVoidImpl(node);
            } else if (type == INITIALIZER_ARRAY) {
                return new DInitializerArrayImpl(node);
            } else if (type == ARRAY_INIT_ENTRY) {
                return new DArrayInitEntryImpl(node);
            } else if (type == INITIALIZER_STRUCT) {
                return new DInitializerStructImpl(node);
            } else if (type == STRUCT_INIT_ENTRY) {
                return new DStructInitEntryImpl(node);
            } else if (type == DEFINITION_FUNCTION) {
                return new DDefinitionFunctionImpl(node);
            } else if (type == FUNCTION_PARAMETER) {
                return new DFunctionParameterImpl(node);
            } else if (type == NAMELESS_PARAMETER) {
                return new DNamelessParameterImpl(node);
            } else if (type == VAR_ARGS_PARAMETER) {
                return new DVarArgsParameterImpl(node);
            } else if (type == FUNCTION_BODY) {
                return new DFunctionBodyImpl(node);
            } else if (type == IN_OUT_FUNCTION_BODY) {
                return new DInOutFunctionBodyImpl(node);
            } else if (type == FUNCTION_BODY_OUT_BLOCK) {
                return new DFunctionBodyOutBlockImpl(node);
            } else if (type == DEFINITION_CONSTRUCTOR) {
                return new DDefinitionConstructorImpl(node);
            } else if (type == DEFINITION_STRUCT) {
                return new DDefinitionStructImpl(node);
            } else if (type == DEFINITION_UNION) {
                return new DDefinitionUnionImpl(node);
            } else if (type == DEFINITION_CLASS) {
                return new DDefinitionClassImpl(node);
            } else if (type == DEFINITION_INTERFACE) {
                return new DDefinitionInterfaceImpl(node);
            } else if (type == DEFINITION_TEMPLATE) {
                return new DDefinitionTemplateImpl(node);
            } else if (type == TEMPLATE_TYPE_PARAM) {
                return new DTemplateTypeParamImpl(node);
            } else if (type == TEMPLATE_VALUE_PARAM) {
                return new DTemplateValueParamImpl(node);
            } else if (type == TEMPLATE_ALIAS_PARAM) {
                return new DTemplateAliasParamImpl(node);
            } else if (type == TEMPLATE_TUPLE_PARAM) {
                return new DTemplateTupleParamImpl(node);
            } else if (type == TEMPLATE_THIS_PARAM) {
                return new DTemplateThisParamImpl(node);
            } else if (type == DEFINITION_MIXIN_INSTANCE) {
                return new DDefinitionMixinInstanceImpl(node);
            } else if (type == DEFINITION_ENUM) {
                return new DDefinitionEnumImpl(node);
            } else if (type == DECLARATION_ENUM) {
                return new DDeclarationEnumImpl(node);
            } else if (type == ENUM_BODY) {
                return new DEnumBodyImpl(node);
            } else if (type == ENUM_MEMBER) {
                return new DEnumMemberImpl(node);
            } else if (type == DEFINITION_ENUM_VAR) {
                return new DDefinitionEnumVarImpl(node);
            } else if (type == DEFINITION_ENUM_VAR_FRAGMENT) {
                return new DDefinitionEnumVarFragmentImpl(node);
            } else if (type == DEFINITION_ALIAS) {
                return new DDefinitionAliasImpl(node);
            } else if (type == DEFINITION_ALIAS_FRAGMENT) {
                return new DDefinitionAliasFragmentImpl(node);
            } else if (type == DEFINITION_ALIAS_VAR_DECL) {
                return new DDefinitionAliasVarDeclImpl(node);
            } else if (type == DEFINITION_ALIAS_FUNCTION_DECL) {
                return new DDefinitionAliasFunctionDeclImpl(node);
            } else if (type == ALIAS_VAR_DECL_FRAGMENT) {
                return new DAliasVarDeclFragmentImpl(node);
            } else if (type == TEMPLATE_TYPE_PARAM__INSTANCE) {
                return new DTemplateTypeParamInstanceImpl(node);
            } else if (type == TEMPLATE_VALUE_PARAM__INSTANCE) {
                return new DTemplateValueParamInstanceImpl(node);
            } else if (type == TEMPLATE_ALIAS_PARAM__INSTANCE) {
                return new DTemplateAliasParamInstanceImpl(node);
            } else if (type == BLOCK_STATEMENT) {
                return new DBlockStatementImpl(node);
            } else if (type == BLOCK_STATEMENT_UNSCOPED) {
                return new DBlockStatementUnscopedImpl(node);
            } else if (type == EMPTY_STATEMENT) {
                return new DEmptyStatementImpl(node);
            } else if (type == SCOPED_STATEMENT_LIST) {
                return new DScopedStatementListImpl(node);
            } else if (type == STATEMENT_EXPRESSION) {
                return new DStatementExpressionImpl(node);
            } else if (type == STATEMENT_LABEL) {
                return new DStatementLabelImpl(node);
            } else if (type == STATEMENT_IF) {
                return new DStatementIfImpl(node);
            } else if (type == STATEMENT_IF_VAR) {
                return new DStatementIfVarImpl(node);
            } else if (type == VARIABLE_DEF_WITH_INIT) {
                return new DVariableDefWithInitImpl(node);
            } else if (type == STATEMENT_WHILE) {
                return new DStatementWhileImpl(node);
            } else if (type == STATEMENT_DO_WHILE) {
                return new DStatementDoWhileImpl(node);
            } else if (type == STATEMENT_FOR) {
                return new DStatementForImpl(node);
            } else if (type == STATEMENT_FOREACH) {
                return new DStatementForeachImpl(node);
            } else if (type == FOREACH_VARIABLE_DEF) {
                return new DForeachVariableDefImpl(node);
            } else if (type == STATEMENT_SWITCH) {
                return new DStatementSwitchImpl(node);
            } else if (type == STATEMENT_CASE) {
                return new DStatementCaseImpl(node);
            } else if (type == STATEMENT_CASE_RANGE) {
                return new DStatementCaseRangeImpl(node);
            } else if (type == STATEMENT_DEFAULT) {
                return new DStatementDefaultImpl(node);
            } else if (type == STATEMENT_CONTINUE) {
                return new DStatementContinueImpl(node);
            } else if (type == STATEMENT_BREAK) {
                return new DStatementBreakImpl(node);
            } else if (type == STATEMENT_RETURN) {
                return new DStatementReturnImpl(node);
            } else if (type == STATEMENT_GOTO) {
                return new DStatementGotoImpl(node);
            } else if (type == STATEMENT_GOTO_DEFAULT) {
                return new DStatementGotoDefaultImpl(node);
            } else if (type == STATEMENT_GOTO_CASE) {
                return new DStatementGotoCaseImpl(node);
            } else if (type == STATEMENT_THROW) {
                return new DStatementThrowImpl(node);
            } else if (type == STATEMENT_SYNCHRONIZED) {
                return new DStatementSynchronizedImpl(node);
            } else if (type == STATEMENT_WITH) {
                return new DStatementWithImpl(node);
            } else if (type == STATEMENT_ASM) {
                return new DStatementAsmImpl(node);
            } else if (type == STATEMENT_SCOPE) {
                return new DStatementScopeImpl(node);
            } else if (type == STATEMENT_TRY) {
                return new DStatementTryImpl(node);
            } else if (type == TRY_CATCH_CLAUSE) {
                return new DTryCatchClauseImpl(node);
            } else if (type == SIMPLE_VARIABLE_DEF) {
                return new DSimpleVariableDefImpl(node);
            } else {
                return new DCompositeElementType(node);
            }

        }
    }
}
