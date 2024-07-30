#!/usr/bin/rdmd
/**
Script to generate the psi implementations. Generated files are located at the current location.
Note: This script is written to be executed in gen/io/github/intellij/dlanguage/psi/ directory
 */

//todo add stub children finder, to prevent loading of psi tree

import std.stdio;
import std.format;
import std.algorithm;

enum defaultParentClassName = "ASTWrapperPsiElement";

string[][string] types_children;

string[][string] stub_children;

string[][string] types_extra_interfaces;

/// If an entry appear here, then the element will inherit from it, otherwise it will inherit from ASTWrapperPsiElement
string[string] types_mixins;

/**
 * Contain the name of Psi elements that are named elements
 */
string[] named_children = [
    "AliasInitializer",
    "AutoAssignment",
    "Catch",
    "ClassDeclaration",
    "Constructor",
    "DeclaratorIdentifier",
    "Destructor",
    "EnumDeclaration",
    "EnumMember",
    "ForeachType",
    "FunctionDeclaration",
    "IdentifierInitializer",
    "IfCondition",
    "InterfaceDeclaration",
    "LabeledStatement",
    "ModuleDeclaration",
    "NamedImportBind",
    "Parameter",
    "SingleImport",
    "StructDeclaration",
    "TemplateDeclaration",
    "TemplateParameter",
    "UnionDeclaration",
    "VersionSpecification",
];

// TODO try to remove this array and generate all stub implementation
string[] generate_stubImpl = [
    "SharedStaticConstructor",
    "SharedStaticDestructor",
    "StaticConstructor",
    "StaticDestructor",
];

/*
Associative array which contains Name of psi element as key and if it has a processDeclaration as value
*/
bool[string] has_processDeclaration;

/*
Associative array of how elements must be renamed, key is original element, value is how it must be renamed
*/
string[string] renameMap;

static this() {
    types_children["AddExpression"] = ["Expression*", "OP_TILDA", "OP_PLUS" , "OP_MINUS"];
    types_extra_interfaces["AddExpression"] = ["Expression"];
    types_children["AliasAssign"] = ["Type","OP_EQ"];
    types_extra_interfaces["AliasAssign"] = ["Declaration"];
    types_children["AliasDeclaration"] = ["DeclaratorIdentifier*","OP_COMMA","StorageClass*","KW_ALIAS", "Type", "OP_SCOLON", "AliasInitializer*"];
    types_extra_interfaces["AliasDeclaration"] = ["Declaration"];
    types_children["AliasThisDeclaration"] = ["KW_ALIAS", "Identifier", "KW_THIS", "OP_SCOLON"];
    types_extra_interfaces["AliasThisDeclaration"] = ["Declaration"];
    types_children["AlignAttribute"] = ["KW_ALIGN", "AssignExpression", "OP_PAR_RIGHT", "OP_PAR_LEFT"];
    types_children["AndAndExpression"] = ["Expression*", "OP_BOOL_AND"];
    types_extra_interfaces["AndAndExpression"] = ["Expression"];
    types_children["AndExpression"] = ["Expression*", "OP_AND"];
    types_extra_interfaces["AndExpression"] = ["Expression"];
    types_children["AnonymousEnumDeclaration"] = ["Expression","OP_COLON","KW_ENUM","OP_PAR_LEFT","OP_PAR_RIGHT","Type","EnumMember*"];
    types_extra_interfaces["AnonymousEnumDeclaration"] = ["Declaration"];
    types_children["AnonymousEnumMember"] = [];
    types_children["ArgumentList"] = ["Expression*", "OP_COMMA*"];
    types_children["Arguments"] = ["ArgumentList","OP_PAR_RIGHT", "OP_PAR_LEFT"];
    types_children["ArrayAccessExpression"] = ["Expression", "IndexExpression"];
    types_extra_interfaces["ArrayAccessExpression"] = ["Expression"];
    types_children["ArrayInitializer"] = ["OP_COMMA*", "ArrayMemberInitialization*"];
    types_children["ArrayLiteral"] = ["ArrayInitializer", "OP_BRACKET_RIGHT", "OP_BRACKET_LEFT"];
    types_children["ArrayMemberInitialization"] = ["Expression", "OP_COLON", "ArrayLiteral", "StructInitializer", "OP_BRACES_LEFT"];
    types_children["AsmAddExp"] = ["AsmAddExp", "AsmMulExp",  "OP_MINUS", "OP_PLUS"];
    types_children["AsmAndExp"] = ["AsmAndExp", "AsmEqualExp", "OP_AND"];
    types_children["AsmBrExp"] = ["AsmExp", "AsmUnaExp", "AsmBrExp", "OP_BRACKET_RIGHT", "OP_BRACKET_LEFT"];
    types_children["AsmEqualExp"] = ["AsmEqualExp", "AsmRelExp",  "OP_NOT_EQ", "OP_EQ_EQ"];
    types_children["AsmExp"] = ["AsmLogOrExp",  "AsmExp", "OP_QUEST", "OP_COLON"];
    types_children["AsmInstruction"] = [ "INTEGER_LITERAL","Identifier","AsmInstruction","Operands","KW_ALIGN", "OP_COLON", "OP_SCOLON"];
    types_children["AsmLogAndExp"] = ["AsmLogAndExp", "AsmOrExp", "OP_BOOL_AND"];
    types_children["AsmLogOrExp"] = ["AsmLogOrExp", "AsmLogAndExp", "OP_BOOL_OR"];
    types_children["AsmMulExp"] = ["AsmMulExp", "AsmBrExp","OP_MOD","OP_DIV","OP_ASTERISK"];
    types_children["AsmOrExp"] = ["AsmOrExp", "AsmXorExp", "OP_OR"];
    types_children["AsmPrimaryExp"] = ["FLOAT_LITERAL", "INTEGER_LITERAL", "Register",  "AsmExp", "IdentifierChain","OP_DOLLAR","KW_THIS"];
    types_children["AsmRelExp"] = ["AsmRelExp", "AsmShiftExp","OP_GT_EQ","OP_GT","OP_LESS","OP_LESS_EQ"];
    types_children["AsmShiftExp"] = ["AsmShiftExp", "AsmAddExp","OP_SH_LEFT","OP_SH_RIGHT","OP_USH_RIGHT"];
    types_children["AsmStatement"] = ["KW_ASM", "OP_BRACES_LEFT", "OP_BRACES_RIGHT", "AsmInstruction*"];
    types_extra_interfaces["AsmStatement"] = ["Statement"];
    types_children["AsmTypePrefix"] = ["Identifier*"];
    types_children["AsmUnaExp"] = ["AsmUnaExp","Identifier","AsmExp","OP_PLUS","OP_MINUS","OP_NOT","OP_TILDA","AsmPrimaryExp"];
    types_children["AsmXorExp"] = [/*"AsmPrimaryExp", "AsmTypePrefix", "AsmExp",*/ "AsmXorExp", "AsmAndExp", "OP_XOR"];
    types_children["AssertArguments"] = ["AssignExpression*", "OP_COMMA"];
    types_children["AssertExpression"] = ["KW_ASSERT","OP_BRACES_LEFT","OP_BRACES_RIGHT" ,"Expression*", "OP_COMMA*"];
    types_extra_interfaces["AssertExpression"] = ["Expression"];
    types_children["AssignExpression"] = ["Expression*","OP_AND_EQ","OP_DIV_EQ","OP_EQ","OP_EQ_EQ","OP_GT_EQ","OP_LESS_EQ","OP_MINUS_EQ","OP_MOD_EQ","OP_MUL_EQ","OP_NOT_EQ","OP_NOT_GR_EQ","OP_NOT_LESS_EQ","OP_OR_EQ","OP_PLUS_EQ","OP_POW_EQ","OP_SH_LEFT_EQ","OP_SH_RIGHT_EQ","OP_TILDA_EQ","OP_USH_RIGHT_EQ","OP_XOR_EQ"];
    types_extra_interfaces["AndExpression"] = ["Expression"];
    types_children["AssocArrayLiteral"] = ["KeyValuePairs","OP_BRACKET_RIGHT","OP_BRACKET_LEFT"];
    types_children["AtAttribute"] = ["OP_AT","OP_PAR_LEFT","OP_PAR_RIGHT","Identifier", "ArgumentList","FunctionCallExpression"];
    types_children["Attribute"] = ["PragmaExpression", "KW_SYNCHRONIZED","KW_ABSTRACT","KW_AUTO","KW_ENUM","KW_EXTERN","KW_FINAL","KW_INOUT","KW_NOTHROW","KW_THROW","KW_OVERRIDE","KW_PURE","KW_REF","KW___GSHARED","KW_SCOPE","KW_STATIC","KW_EXPORT", "KW_PRIVATE","KW_PROTECTED","KW_PUBLIC","AlignAttribute","Deprecated","AtAttribute","KW_PACKAGE","IdentifierChain","LinkageAttribute","KW_CONST"];
    types_children["AttributeSpecifier"] = ["OP_COLON","Attribute","DeclarationBlock"];
    types_extra_interfaces["AttributeSpecifier"] = ["Declaration"];
    types_children["AutoDeclaration"] = ["StorageClass*","OP_COMMA*","OP_SCOLON","AutoAssignment*"];
    types_extra_interfaces["AutoDeclaration"] = ["VariableDeclaration"];
    types_children["BlockStatement"] = ["Statement*","OP_BRACES_RIGHT","OP_BRACES_LEFT"];
    types_extra_interfaces["BlockStatement"] = ["Statement"];
    types_children["BreakStatement"] = ["KW_BREAK","Identifier","OP_SCOLON"];
    types_children["BaseClass"] = ["TypeofExpression","OP_DOT","BasicType"];
    types_children["BaseClassList"] = ["BaseClass*","OP_COMMA*"];
    types_children["BasicType"] = ["Type","TypeofExpression","TypeConstructor","MixinType","Expression*","Vector","BuiltinType","QualifiedIdentifier","OP_DOT","OP_PAR_RIGHT","OP_PAR_LEFT","KW_SUPER","KW_THIS","KW_CONST","KW_IMMUTABLE","KW_INOUT","KW_SHARED"];
    types_children["BuiltinType"] = [/*todo add the types*/];  // UNUSED currently
    types_children["CaseRangeStatement"] = ["KW_CASE*","OP_TRIPLEDOT","OP_COLON*","AssignExpression", "Statement*"];
    types_extra_interfaces["CaseRangeStatement"] = ["Statement"];
    types_children["CaseStatement"] = ["KW_CASE","OP_COLON","ArgumentList","Statement*"];
    types_extra_interfaces["CaseRangeStatement"] = ["Statement"];
    types_children["CastExpression"] = ["CastQualifier", "Type","Expression","KW_CAST","OP_PAR_LEFT","OP_PAR_RIGHT"];
    types_extra_interfaces["CastExpression"] = ["Expression"];
    types_children["CastQualifier"] = ["KW_IMMUTABLE","KW_CONST","KW_SHARED","KW_INOUT"];
    types_children["Catches"] = ["LastCatch","Catch"];
    types_children["CommaExpression"] = ["Expression*", "OP_COMMA"];
    types_extra_interfaces["CommaExpression"] = ["Expression"];
    types_children["CompileCondition"] = [ "VersionCondition",  "DebugCondition",  "StaticIfCondition"];
    types_children["ConditionalDeclaration"] = ["CompileCondition", "Declaration*","OP_COLON","KW_ELSE","OP_BRACES_RIGHT","OP_BRACES_LEFT"];
    types_extra_interfaces["ConditionalDeclaration"] = ["Declaration"];
    types_children["ConditionalStatement"] = ["CompileCondition", "Statement*", "KW_ELSE","OP_BRACES_RIGHT","OP_BRACES_LEFT"];
    types_extra_interfaces["ConditionalStatement"] = ["Statement"];
    types_children["Constraint"] = ["KW_IF","Expression*","OP_COMMA*", "OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_children["ContinueStatement"] = ["KW_CONTINUE", "Identifier", "OP_SCOLON"];
    types_children["DebugCondition"] = ["KW_DEBUG","INTEGER_LITERAL", "Identifier", "OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_children["DebugSpecification"] = ["KW_DEBUG","OP_EQ","Identifier", "INTEGER_LITERAL", "OP_SCOLON"];
    types_extra_interfaces["DebugSpecification"] = ["Declaration"];
    types_children["DeclarationBlock"] = ["OP_BRACES_LEFT", "Declaration*", "OP_BRACES_RIGHT"];
    types_children["DeclarationStatement"] = ["Declaration"];
    types_extra_interfaces["DeclarationStatement"] = ["Statement"];
    types_children["DefaultStatement"] = ["KW_DEFAULT","OP_COLON","Statement*"];
    types_extra_interfaces["DefaultStatement"] = ["Statement"];
    types_children["DeleteExpression"] = ["KW_DELETE","Expression"];
    types_extra_interfaces["DeleteExpression"] = ["Expression"];
    types_children["DeleteStatement"] = ["KW_DELETE"];
    types_children["Deprecated"] = ["AssignExpression","Deprecated","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_children["DollarExpression"] = ["OP_DOLLAR"];
    types_extra_interfaces["DollarExpression"] = ["Expression"];
    types_children["DoStatement"] = ["KW_DO","KW_WHILE","LabeledStatement","BlockStatement","IfStatement","WhileStatement","DoStatement","ForStatement","ForeachStatement","SwitchStatement","FinalSwitchStatement","ContinueStatement","BreakStatement","ReturnStatement","GotoStatement","WithStatement","SynchronizedStatement","TryStatement","ScopeGuardStatement","PragmaStatement","AsmStatement","DebugSpecification", "ConditionalStatement", "VersionSpecification","StaticAssertStatement","ExpressionStatement","Expression*","OP_COMMA*","OP_SCOLON","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_extra_interfaces["DoStatement"] = ["Statement"];
    types_children["EmptyDeclaration"] = ["OP_SCOLON"];
    types_extra_interfaces["EmptyDeclaration"] = ["Declaration"];
    types_children["EmptyStatement"] = ["OP_SCOLON"];
    types_extra_interfaces["EmptyStatement"] = ["Statement"];
    types_children["EnumBody"] = [ "Identifier","OP_BRACES_RIGHT", "OP_BRACES_LEFT", "EnumMember*","OP_COMMA*","OP_SCOLON"];
    types_children["EnumMemberAttribute"] = ["Attribute","Deprecated"];
    types_children["EqualExpression"] = ["Expression*","OP_EQ_EQ","OP_NOT_EQ"];
    types_extra_interfaces["EqualExpression"] = ["Expression"];
    types_children["ExpressionStatement"] = ["Expression*","OP_COMMA*","OP_SCOLON"];
    types_extra_interfaces["ExpressionStatement"] = ["Statement"];
    types_children["Finally"] = ["KW_FINALLY", "Statement"];
    types_children["FinalSwitchStatement"] = ["KW_FINAL", "SwitchStatement"];
    types_extra_interfaces["FinalSwitchStatement"] = ["Statement"];
    types_children["FundamentalTypeConstructExpression"] = ["BasicType", "OP_PAR_LEFT", "ArgumentList", "OP_PAR_RIGHT"];
    types_extra_interfaces["FundamentalTypeConstructExpression"] = ["Expression"];
    types_children["FundamentalTypePropertyExpression"] = ["BasicType", "OP_DOT", "Identifier"];
    types_extra_interfaces["FundamentalTypePropertyExpression"] = ["Expression"];
    types_children["ForStatement"] = ["Statement*", "AssignExpression*","OP_BRACES_RIGHT","OP_BRACES_LEFT","KW_FOR","OP_SCOLON"];
    types_extra_interfaces["ForStatement"] = ["Statement"];
    types_children["ForeachStatement"] = ["KW_FOREACH","KW_FOREACH_REVERSE","Statement", "Expression","OP_BRACES_RIGHT","OP_BRACES_LEFT","OP_DDOT","ForeachType","ForeachTypeList","OP_SCOLON"];
    types_extra_interfaces["ForeachStatement"] = ["Statement"];
    types_children["ForeachTypeList"] = ["ForeachType*","OP_COMMA*"];
    types_children["FunctionAttribute"] = ["AtAttribute","KW_PURE","KW_NOTHROW"];
    types_children["FunctionCallExpression"] = ["Type", "Arguments", "Expression", "TemplateArguments"];
    types_extra_interfaces["FunctionCallExpression"] = ["Statement"];
    types_children["FunctionContract"] = ["OP_BRACES_LEFT", "InOutStatement", "OP_PAR_LEFT", "InOutContractExpression"];
    types_children["FunctionLiteralExpression"] = ["Type","KW_FUNCTION","KW_DELEGATE","KW_REF","Parameters","FunctionAttribute*","SpecifiedFunctionBody","Identifier"];
    types_extra_interfaces["FunctionLiteralExpression"] = ["Expression"];
    types_children["GotoStatement"] = ["Identifier","AssignExpression*","OP_COMMA*","KW_DEFAULT","KW_CASE","KW_GOTO","OP_SCOLON"];
    types_extra_interfaces["GotoStatement"] = ["Statement"];
    types_children["IdentifierChain"] = ["Identifier", "OP_DOT", "IdentifierChain"];
    types_mixins["IdentifierChain"] = "DLanguageIdentifierChainImplMixin";
    types_children["IdentityExpression"] = ["Expression*","KW_IS","OP_NOT"];
    types_extra_interfaces["IdentityExpression"] = ["Expression"];
    stub_children ["IfCondition"] = ["Identifier",  "AssignExpression*","OP_COMMA*", "KW_AUTO", "KW_SCOPE","Type", "OP_EQ"];
    types_children["IfStatement"] = [/*"Identifier","AssignExpression*","OP_COMMA*",*/"Statement","KW_ELSE","KW_IF","OP_PAR_LEFT","OP_PAR_RIGHT"/*,"KW_AUTO","Type","OP_EQ"*/,"IfCondition"];
    types_extra_interfaces["IfStatement"] = ["Statement"];
    types_children["ImportBind"] = ["Identifier","NamedImportBind"];
    types_mixins["ImportBind"] = "DLanguageImportBindImplMixin";
    types_children["ImportBindings"] = ["OP_COMMA*","ImportBind*","SingleImport","OP_COLON"];
    types_children["ImportDeclaration"] = ["KW_IMPORT","SingleImport*","ImportBindings","OP_COMMA*","OP_SCOLON"];
    types_extra_interfaces["ImportDeclaration"] = ["Statement", "Declaration"];
    types_children["ImportExpression"] = ["ImportExpression","AssignExpression","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_extra_interfaces["ImportExpression"] = ["Expression"];
    types_children["IndexExpression"] = ["OP_BRACKET_LEFT","OP_BRACKET_RIGHT","ArgumentList","Expression"];
    types_extra_interfaces["IndexExpression"] = ["Expression"];
    types_children["InExpression"] = ["ShiftExpression*","KW_IN","OP_NOT"];
    types_children["Initializer"] = ["KW_VOID","AssignExpression", "ArrayLiteral", "StructInitializer"];
    types_children["InContractExpression"] = ["KW_IN","OP_PAR_LEFT", "AssertArguments", "OP_PAR_RIGHT"];
    types_children["InOutContractExpression"] = ["InContractExpression", "OutContractExpression"];
    types_children["InOutStatement"] = ["InStatement", "OutStatement"];
    types_children["InStatement"] = ["KW_IN","BlockStatement"];
    types_children["Initializer"] = ["KW_VOID","AssignExpression", "ArrayLiteral", "StructInitializer"];
    types_children["Invariant"] = ["BlockStatement","KW_INVARIANT","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_extra_interfaces["Invariant"] = ["Declaration"];
    types_children["IsExpression"] = ["OP_PAR_RIGHT","OP_PAR_LEFT","Type","Identifier","TypeSpecialization","TemplateParameterList","OP_COMMA","OP_COLON","OP_EQ","KW_IS"];
    types_extra_interfaces["IsExpression"] = ["Expression"];
    types_children["KeyValuePair"] = ["AssignExpression*","OP_COLON"];
    types_children["KeyValuePairs"] = ["OP_COMMA*","KeyValuePair*"];
    stub_children ["LabeledStatement"] = ["Identifier", "OP_COLON","Statement"];
    types_extra_interfaces["LabeledStatement"] = ["Statement"];
    types_children["LambdaExpression"] = ["Identifier","KW_FUNCTION","KW_DELEGATE","OP_LAMBDA_ARROW","Expression","Parameters","FunctionAttribute*"];
    types_extra_interfaces["LambdaExpression"] = ["Expression"];
    types_children["LastCatch"] = ["KW_CATCH","LabeledStatement","BlockStatement","IfStatement","WhileStatement","DoStatement","ForStatement","ForeachStatement","SwitchStatement","FinalSwitchStatement","ContinueStatement","BreakStatement","ReturnStatement","GotoStatement","WithStatement","SynchronizedStatement","TryStatement","ScopeGuardStatement","PragmaStatement","AsmStatement","DebugSpecification", "ConditionalStatement", "VersionSpecification","StaticAssertStatement","ExpressionStatement"];
    types_children["LinkageAttribute"] = ["IdentifierChain","Identifier","OP_PAR_RIGHT","OP_PAR_LEFT","OP_PLUS_PLUS","KW_EXTERN","OP_COMMA"];
    types_children["LiteralExpression"] = ["KW_THIS", "KW_SUPER", "KW_NULL", "KW_TRUE", "KW_FALSE", "INTEGER_LITERAL", "FLOAT_LITERAL", "CHARACTER_LITERAL", "ALTERNATE_WYSIWYG_STRING*", "DOUBLE_QUOTED_STRING*", "WYSIWYG_STRING*", "DELIMITED_STRING*", "TOKEN_STRING*"];
    types_extra_interfaces["LiteralExpression"] = ["Expression"];
    types_children["MagicLiteralExpression"] = ["KW___FILE__", "KW___FILE_FULL_PATH__", "KW___MODULE__", "KW___LINE__", "KW___FUNCTION__", "KW___PRETTY_FUNCTION__"];
    types_extra_interfaces["MagicLiteralExpression"] = ["Expression"];
    types_children["MemberFunctionAttribute"] = [ "FunctionAttribute","KW_IMMUTABLE","KW_INOUT","KW_SHARED","KW_CONST"];
    types_children["MissingFunctionBody"] = ["FunctionContract*","OP_SCOLON"];
    types_extra_interfaces["MissingFunctionBody"] = ["FunctionBody"];
    types_children["MixinDeclaration"] = ["KW_MIXIN", "OP_PAR_LEFT", "ArgumentList", "OP_PAR_RIGHT", "OP_SCOLON"];
    types_extra_interfaces["MixinDeclaration"] = ["Declaration"];
    types_children["MixinQualifiedIdentifier"] = ["Identifier", "TemplateInstance", "OP_DOT", "MixinQualifiedIdentifier"];
    types_children["MixinTemplateDeclaration"] = ["TemplateDeclaration","KW_MIXIN"];
    types_children["MixinTemplateName"] = ["TypeofExpression", "MixinQualifiedIdentifier", "OP_DOT"];
    types_children["MixinType"] = ["ArgumentList","OP_PAR_RIGHT","OP_PAR_LEFT","KW_MIXIN"];
    types_children["MulExpression"] = ["Expression*","OP_MOD","OP_DIV","OP_ASTERISK"];
    types_extra_interfaces["MulExpression"] = ["Expression"];
    stub_children ["NamedImportBind"] = ["Identifier",  "OP_EQ"];
    types_children["NamespaceList"] = ["OP_COMMA",  "Expression*"];
    types_children["NewAnonClassExpression"] = ["KW_NEW","KW_CLASS","Arguments","BaseClassList", "StructBody"];
    types_children["NewExpression"] = ["KW_NEW","NewAnonClassExpression","Type", "Expression", "Arguments","OP_BRACKET_LEFT","OP_BRACKET_RIGHT"];
    types_extra_interfaces["NewExpression"] = ["Expression"];
    types_children["Operands"] = ["OP_COMMA","Operands","AsmExp"];
    types_children["OrExpression"] = ["Expression*", "OP_OR"];
    types_extra_interfaces["OrExpression"] = ["Expression"];
    types_children["OrOrExpression"] = ["Expression*", "OP_BOOL_OR"];
    types_extra_interfaces["OrOrExpression"] = ["Expression"];
    types_children["OutContractExpression"] = ["KW_OUT", "OP_PAR_LEFT", "OP_SCOLON", "AssertArguments", "OP_PAR_RIGHT"];
    types_children["OutStatement"] = ["Identifier","BlockStatement","KW_OUT","OP_PAR_LEFT","OP_PAR_RIGHT"];
    types_children["ParameterAttribute"] = ["KW_FINAL","KW_IN","KW_LAZY","KW_OUT","KW_REF","KW_SCOPE","KW_AUTO","TypeConstructor","AtAttribute"];
    types_children["ParenthesisedExpression"] = ["OP_PAR_LEFT", "Expression", "OP_PAR_RIGHT"];
    types_extra_interfaces["ParenthesisedExpression"] = ["Expression"];
    types_children["Parameters"] = ["OP_COMMA*","OP_TRIPLEDOT","Parameter*","OP_PAR_LEFT","OP_PAR_RIGHT"];
    types_children["Postblit"] = ["FunctionBody","OP_SCOLON","KW_THIS*","OP_PAR_LEFT","OP_PAR_RIGHT","MemberFunctionAttribute"];
    types_extra_interfaces["Postblit"] = ["Declaration"];
    types_children["PostfixExpression"] = ["Expression", "OP_PLUS_PLUS", "OP_MINUS_MINUS"];
    types_extra_interfaces["PostfixExpression"] = ["Expression"];
    types_children["PowExpression"] = ["Expression*","OP_POW"];
    types_extra_interfaces["PowExpression"] = ["Expression"];
    types_children["PragmaExpression"] = ["Identifier","ArgumentList","OP_PAR_LEFT","OP_PAR_RIGHT","OP_COMMA","KW_PRAGMA"];
    types_extra_interfaces["PragmaExpression"] = ["Expression"];
    types_children["PragmaStatement"] = ["PragmaExpression", "OP_SCOLON", "DefaultStatement","LabeledStatement","BlockStatement","IfStatement","WhileStatement","DoStatement","ForStatement","ForeachStatement","SwitchStatement","FinalSwitchStatement","ContinueStatement","BreakStatement","ReturnStatement","GotoStatement","WithStatement","SynchronizedStatement","TryStatement","ScopeGuardStatement","PragmaStatement","AsmStatement","DebugSpecification", "ConditionalStatement", "VersionSpecification","StaticAssertStatement","ExpressionStatement","CaseStatement","CaseRangeStatement"];
    types_children["QualifiedIdentifier"] = ["Identifier", "TemplateInstance", "OP_BRACKET_LEFT", "OP_BRACKET_RIGHT", "Expression", "OP_DOT", "QualifiedIdentifier"];
    types_mixins["QualifiedIdentifier"] = "DLanguageQualifiedIdentifierImplMixin";
    types_children["ReferenceExpression"] = ["OP_DOT", "Identifier", "TemplateInstance", "ReferenceExpression"];
    types_extra_interfaces["ReferenceExpression"] = ["Expression"];
    types_mixins["ReferenceExpression"] = "DLanguageReferenceExpressionImplMixin";
    types_children["Register"] = ["Identifier","INTEGER_LITERAL", "OP_PAR_RIGHT", "OP_PAR_LEFT"];
    types_children["RelExpression"] = ["Expression*", "OP_GT","OP_GT_EQ","OP_LESS","OP_LESS_EQ","OP_NOT_GR","OP_NOT_GR_EQ","OP_NOT_LESS","OP_NOT_LESS_EQ"];
    types_extra_interfaces["RelExpression"] = ["Expression"];
    types_children["ReturnStatement"] = ["KW_RETURN","Expression*","OP_COMMA*","OP_SCOLON"];
    types_extra_interfaces["ReturnStatement"] = ["Statement"];
    types_children["ScopeGuardStatement"] = ["KW_SCOPE","Identifier","LabeledStatement","BlockStatement","IfStatement","WhileStatement","DoStatement","ForStatement","ForeachStatement","SwitchStatement","FinalSwitchStatement","ContinueStatement","BreakStatement","ReturnStatement","GotoStatement","WithStatement","SynchronizedStatement","TryStatement","ScopeGuardStatement","PragmaStatement","AsmStatement","DebugSpecification", "ConditionalStatement", "VersionSpecification","StaticAssertStatement","ExpressionStatement","OP_PAR_LEFT","OP_PAR_RIGHT"];
    types_extra_interfaces["ScopeGuardStatement"] = ["Statement"];
    stub_children ["SharedStaticConstructor"] = ["KW_STATIC","KW_SHARED","KW_THIS","OP_PAR_LEFT","OP_PAR_RIGHT","FunctionBody"];
    types_extra_interfaces["SharedStaticConstructor"] = ["Declaration"];
    stub_children ["SharedStaticDestructor"] = ["OP_TILDA","KW_STATIC","KW_SHARED","KW_THIS","OP_PAR_LEFT","OP_PAR_RIGHT","FunctionBody"];
    types_extra_interfaces["SharedStaticDestructor"] = ["Declaration"];
    types_children["ShiftExpression"] = ["Expression*","OP_SH_RIGHT","OP_SH_LEFT","OP_USH_RIGHT"];
    types_extra_interfaces["ShiftExpression"] = ["Expression"];
    types_children["SliceExpression"] = ["Expression*","OP_BRACKET_LEFT","OP_BRACKET_RIGHT","OP_DDOT"];
    types_extra_interfaces["SliceExpression"] = ["Expression"];
    types_children["ShortenedFunctionBody"] = ["FunctionContract*", "OP_LAMBDA_ARROW", "AssignExpression", "OP_SCOLON"];
    types_extra_interfaces["ShortenedFunctionBody"] = ["FunctionBody"];
    types_children["SpecifiedFunctionBody"] = ["FunctionContract*", "KW_DO", "BlockStatement"];
    types_extra_interfaces["SpecifiedFunctionBody"] = ["FunctionBody"];
    types_children["SpecifiedVariableDeclaration"] = ["OP_SCOLON", "BasicType", "TypeSuffix*", "IdentifierInitializer*","OP_COMMA*","OP_EQ","FunctionBody","StorageClass*"];
    types_extra_interfaces["SpecifiedVariableDeclaration"] = ["VariableDeclaration"];
    types_children["StaticAssertDeclaration"] = ["StaticAssertStatement"];
    types_extra_interfaces["StaticAssertDeclaration"] = ["Declaration"];
    types_children["StaticAssertStatement"] = ["KW_STATIC","AssertExpression","OP_SCOLON"];
    types_children["StaticCtorDtorCommon"] = ["FunctionBody"];
    stub_children ["StaticConstructor"] = ["KW_STATIC","KW_THIS","OP_PAR_LEFT","OP_PAR_RIGHT","FunctionBody"];
    types_extra_interfaces["StaticConstructor"] = ["Declaration"];
    stub_children ["StaticDestructor"] = ["OP_TILDA","KW_STATIC","KW_THIS","OP_PAR_LEFT","OP_PAR_RIGHT","FunctionBody"];
    types_extra_interfaces["StaticDestructor"] = ["Declaration"];
    types_children["StaticForeachDeclaration"] = ["KW_FOREACH", "KW_FOREACH_REVERSE", "Declaration", "Expression*", "OP_BRACES_RIGHT", "OP_BRACES_LEFT", "OP_DDOT", "ForeachType", "ForeachTypeList", "OP_SCOLON", "KW_STATIC"];
    types_extra_interfaces["StaticForeachDeclaration"] = ["Declaration"];
    types_children["StaticForeachStatement"] = ["KW_STATIC", "ForeachStatement"];
    types_extra_interfaces["StaticForeachStatement"] = ["Statement"];
    types_children["StaticIfCondition"] = ["StaticIfCondition","Expression","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_children["StorageClass"] = ["AtAttribute","Deprecated","AlignAttribute","LinkageAttribute",   "KW_SYNCHRONIZED","TypeConstructor","KW_ABSTRACT","KW_CONST","KW_IMMUTABLE","KW_AUTO","KW_ENUM","KW_EXTERN","KW_FINAL","KW_INOUT","KW_NOTHROW","KW_OVERRIDE","KW_PURE","KW_REF","KW___GSHARED","KW_SCOPE","KW_STATIC","KW_THROW"];
    types_children["StructBody"] = ["OP_BRACES_RIGHT","OP_BRACES_LEFT","Declaration*"];
    types_children["StructInitializer"] = ["StructMemberInitializers*","OP_BRACES_RIGHT","OP_BRACES_LEFT"];
    types_children["StructMemberInitializer"] = ["Identifier","OP_COLON","Expression", "ArrayLiteral", "StructInitializer"];
    types_children["StructMemberInitializers"] = ["StructMemberInitializer*","OP_COMMA*"];
    types_children["SwitchStatement"] = ["KW_SWITCH","OP_PAR_RIGHT","OP_PAR_LEFT","AssignExpression*","OP_COMMA*","DefaultStatement","LabeledStatement","BlockStatement","IfStatement","WhileStatement","DoStatement","ForStatement","ForeachStatement","SwitchStatement","FinalSwitchStatement","ContinueStatement","BreakStatement","ReturnStatement","GotoStatement","WithStatement","SynchronizedStatement","TryStatement","ScopeGuardStatement","PragmaStatement","AsmStatement","DebugSpecification", "ConditionalStatement", "VersionSpecification","StaticAssertStatement","ExpressionStatement","CaseStatement","CaseRangeStatement"];
    types_extra_interfaces["SwitchStatement"] = ["Statement"];
    types_children["SynchronizedStatement"] = ["OP_PAR_RIGHT","OP_PAR_LEFT","AssignExpression*","OP_COMMA*","LabeledStatement","BlockStatement","IfStatement","WhileStatement","DoStatement","ForStatement","ForeachStatement","SwitchStatement","FinalSwitchStatement","ContinueStatement","BreakStatement","ReturnStatement","GotoStatement","WithStatement","SynchronizedStatement","TryStatement","ScopeGuardStatement","PragmaStatement","AsmStatement","DebugSpecification", "ConditionalStatement", "VersionSpecification","StaticAssertStatement","ExpressionStatement","KW_SYNCHRONIZED"];
    types_extra_interfaces["SynchronizedStatement"] = ["Statement"];
    types_children["TemplateAliasParameter"] = ["KW_ALIAS","Identifier","Type*","AssignExpression*","OP_COLON","OP_EQ"];
    types_children["TemplateArgument"] = ["Type","AssignExpression"];
    types_children["TemplateArgumentList"] = ["OP_COMMA*","TemplateArgument*"];
    types_children["TemplateArguments"] = ["TemplateArgumentList","TemplateSingleArgument","OP_PAR_RIGHT","OP_PAR_LEFT","OP_NOT"];
    types_children["TemplateInstance"] = ["Identifier", "TemplateArguments"];
    types_children["TemplateMixin"] = ["KW_MIXIN","MixinTemplateName","TemplateArguments","Identifier"];
    types_extra_interfaces["TemplateMixin"] = ["Declaration"];
    types_children["TemplateMixinDeclaration"] = ["KW_MIXIN","KW_TEMPLATE", "Identifier", "TemplateParameters", "KW_IF", "OP_BRACES_LEFT", "Declaration*", "OP_BRACES_RIGHT"];
    types_extra_interfaces["TemplateMixinDeclaration"] = ["Declaration"];
    types_children["TemplateMixinExpression"] = ["KW_MIXIN","MixinTemplateName","TemplateArguments","Identifier"];
    types_extra_interfaces["TemplateMixinExpression"] = ["Expression"];
    stub_children ["TemplateParameter"] = ["TemplateAliasParameter","TemplateTupleParameter","TemplateTypeParameter","TemplateThisParameter","TemplateValueParameter"];
    types_children["TemplateParameterList"] = ["TemplateParameter*","OP_COMMA*"];
    types_children["TemplateParameters"] = ["TemplateParameterList","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_children["TemplateSingleArgument"] = ["Identifier","BuiltinType","KW_SUPER","KW_THIS","OP_DOLLAR","KW_TRUE","KW_FALSE","KW_NULL",
                                                "KW___DATE__","KW___EOF__","KW___FILE__","KW___FILE_FULL_PATH__","KW___FUNCTION__","KW___GSHARED","KW___LINE__","KW___MODULE__","KW___PARAMETERS","KW___PRETTY_FUNCTION__","KW___TIME__","KW___TIMESTAMP__","KW___TRAITS","KW___VECTOR","KW___VENDOR__","KW___VERSION__",
                                                "INTEGER_LITERAL","FLOAT_LITERAL","DOUBLE_QUOTED_STRING","CHARACTER_LITERAL"];
    types_children["TemplateThisParameter"] = ["KW_THIS","TemplateTypeParameter"];
    types_children["TemplateTupleParameter"] = ["Identifier","OP_TRIPLEDOT"];
    types_children["TemplateTypeParameter"] = ["Identifier","Type*","OP_COLON","OP_EQ"];
    types_children["TemplateValueParameter"] = ["Type","Identifier","OP_COLON","AssignExpression",  "TemplateValueParameterDefault"];
    types_children["TemplateValueParameterDefault"] = ["OP_EQ","AssignExpression","KW___FILE__","KW___FUNCTION__","KW___LINE__","KW___MODULE__","KW___PRETTY_FUNCTION__"];
    types_children["TernaryExpression"] = ["OP_QUEST","OP_COLON","Expression*"];
    types_extra_interfaces["TernaryExpression"] = ["Expression"];
    types_children["ThrowExpression"] = ["KW_THROW","Expression","OP_SCOLON"];
    types_extra_interfaces["ThrowExpression"] = ["Expression"];
    types_children["TraitsExpression"] = ["KW___TRAITS","TemplateArgumentList","Identifier","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_extra_interfaces["TraitsExpression"] = ["Expression"];
    types_children["TryStatement"] = ["KW_TRY","Statement","Catches","Finally"];
    types_extra_interfaces["TryStatement"] = ["Statement"];
    types_children["Type"] = ["TypeConstructor*","BasicType","TypeSuffix*"];
    types_children["TypeConstructExpression"] = ["KW_IMMUTABLE", "KW_CONST", "KW_INOUT", "KW_SHARED", "Type", "OP_PAR_LEFT", "ArgumentList", "OP_PAR_RIGHT"];
    types_extra_interfaces["TypeConstructExpression"] = ["Expression"];
    types_children["TypeConstructor"] = ["KW_CONST","KW_IMMUTABLE","KW_INOUT","KW_SHARED","KW_SCOPE"];
    types_children["TypePropertyExpression"] = ["KW_IMMUTABLE", "KW_CONST", "KW_INOUT", "KW_SHARED", "Type", "OP_DOT", "Identifier"];
    types_children["TypeidExpression"] = ["Expression","KW_TYPEID","Type","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_extra_interfaces["TypeidExpression"] = ["Expression"];
    types_children["TypeofExpression"] = ["Expression","KW_TYPEOF","OP_PAR_RIGHT","OP_PAR_LEFT","KW_RETURN"];
    types_extra_interfaces["TypeofExpression"] = ["Expression"];
    types_children["TypeSpecialization"] = ["Type","KW___PARAMETERS","KW_STRUCT","KW_UNION","KW_CLASS","KW_INTERFACE","KW_ENUM","KW___VECTOR","KW_FUNCTION","KW_DELEGATE","KW_SUPER","KW_CONST","KW_IMMUTABLE","KW_INOUT","KW_SHARED","KW_RETURN","KW_MODULE","KW_PACKAGE"];
    types_children["TypeSuffix"] = ["Expression*","OP_TRIPLEDOT","KW_FUNCTION","KW_DELEGATE","OP_ASTERISK","MemberFunctionAttribute*","Parameters","OP_BRACKET_LEFT","OP_BRACKET_RIGHT"];
    types_children["UnaryExpression"] = ["Expression","Identifier", "TemplateInstance","OP_PAR_RIGHT","OP_PAR_LEFT","Type","OP_DOT","OP_AND","OP_ASTERISK","OP_MINUS","OP_MINUS_MINUS","OP_NOT","OP_PLUS","OP_PLUS_PLUS","OP_TILDA"];
    types_extra_interfaces["UnaryExpression"] = ["Expression"];
    stub_children ["Unittest"] = ["Unittest",  "BlockStatement"];
    types_extra_interfaces["Unittest"] = ["Declaration"];
    types_children["VariadicArgumentsAttribute"] = ["KW_CONST", "KW_IMMUTABLE", "KW_SHARED", "KW_SCOPE", "KW_RETURN"];
    types_children["VariadicArgumentsAttributes"] = ["OP_TRIPLEDOT*", "VariadicArgumentsAttribute*"];
    types_children["Vector"] = ["KW___VECTOR", "Type", "OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_children["VersionCondition"] = ["KW_VERSION","KW_UNITTEST","KW_ASSERT","INTEGER_LITERAL","Identifier","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_extra_interfaces["VersionCondition"] = ["Declaration"];
    types_children["WhileStatement"] = ["KW_WHILE","IfCondition","Statement","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_extra_interfaces["WhileStatement"] = ["Statement"];
    types_children["WithStatement"] = ["KW_WITH","AssignExpression*","OP_COMMA*","OP_PAR_RIGHT","OP_PAR_LEFT","LabeledStatement","BlockStatement","IfStatement","WhileStatement","DoStatement","ForStatement","ForeachStatement","SwitchStatement","FinalSwitchStatement","ContinueStatement","BreakStatement","ReturnStatement","GotoStatement","WithStatement","SynchronizedStatement","TryStatement","ScopeGuardStatement","PragmaStatement","AsmStatement","DebugSpecification", "ConditionalStatement", "VersionSpecification","StaticAssertStatement","ExpressionStatement"];
    types_extra_interfaces["WithStatement"] = ["Statement"];
    types_children["XorExpression"] = ["Expression*", "OP_XOR"];
    types_extra_interfaces["XorExpression"] = ["Expression"];



    has_processDeclaration["AddExpression"] = false;
    has_processDeclaration["AliasAssign"] = false;
    has_processDeclaration["AliasDeclaration"] = false;
    has_processDeclaration["AliasInitializer"] = false;
    has_processDeclaration["AliasThisDeclaration"] = false;
    has_processDeclaration["AlignAttribute"] = false;
    has_processDeclaration["AndAndExpression"] = false;
    has_processDeclaration["AndExpression"] = false;
    has_processDeclaration["AnonymousEnumDeclaration"] = false;
    has_processDeclaration["AnonymousEnumMember"] = false;
    has_processDeclaration["ArgumentList"] = false;
    has_processDeclaration["Arguments"] = false;
    has_processDeclaration["ArrayAccessExpression"] = false;
    has_processDeclaration["ArrayInitializer"] = false;
    has_processDeclaration["ArrayLiteral"] = false;
    has_processDeclaration["ArrayMemberInitialization"] = false;
    has_processDeclaration["AsmAddExp"] = false;
    has_processDeclaration["AsmAndExp"] = false;
    has_processDeclaration["AsmBrExp"] = false;
    has_processDeclaration["AsmEqualExp"] = false;
    has_processDeclaration["AsmExp"] = false;
    has_processDeclaration["AsmInstruction"] = false;
    has_processDeclaration["AsmLogAndExp"] = false;
    has_processDeclaration["AsmLogOrExp"] = false;
    has_processDeclaration["AsmMulExp"] = false;
    has_processDeclaration["AsmOrExp"] = false;
    has_processDeclaration["AsmPrimaryExp"] = false;
    has_processDeclaration["AsmRelExp"] = false;
    has_processDeclaration["AsmShiftExp"] = false;
    has_processDeclaration["AsmStatement"] = false;
    has_processDeclaration["AsmTypePrefix"] = false;
    has_processDeclaration["AsmUnaExp"] = false;
    has_processDeclaration["AsmXorExp"] = false;
    has_processDeclaration["AssertArguments"] = false;
    has_processDeclaration["AssertExpression"] = false;
    has_processDeclaration["AssignExpression"] = false;
    has_processDeclaration["AssocArrayLiteral"] = false;
    has_processDeclaration["AtAttribute"] = false;
    has_processDeclaration["Attribute"] = false;
    has_processDeclaration["AttributeDeclaration"] = false;
    has_processDeclaration["AutoDeclaration"] = false;
    has_processDeclaration["AutoAssignment"] = false;
    has_processDeclaration["BlockStatement"] = false;
    has_processDeclaration["BreakStatement"] = false;
    has_processDeclaration["BaseClass"] = false;
    has_processDeclaration["BaseClassList"] = false;
    has_processDeclaration["BasicType"] = false;
    has_processDeclaration["BuiltinType"] = false;
    has_processDeclaration["CaseRangeStatement"] = false;
    has_processDeclaration["CaseStatement"] = false;
    has_processDeclaration["CastExpression"] = false;
    has_processDeclaration["CastQualifier"] = false;
    has_processDeclaration["Catch"] = true;
    has_processDeclaration["Catches"] = false;
    //has_processDeclaration["ClassDeclaration"] = true;
    has_processDeclaration["CompileCondition"] = false;
    has_processDeclaration["ConditionalDeclaration"] = true;
    has_processDeclaration["ConditionalStatement"] = false;
    has_processDeclaration["Constraint"] = false;
    has_processDeclaration["Constructor"] = true;
    has_processDeclaration["ContinueStatement"] = false;
    has_processDeclaration["DebugCondition"] = false;
    has_processDeclaration["DebugSpecification"] = false;
    has_processDeclaration["Declaration"] = true;
    has_processDeclaration["DefaultStatement"] = false;
    has_processDeclaration["DeleteExpression"] = false;
    has_processDeclaration["DeleteStatement"] = false;
    has_processDeclaration["Deprecated"] = false;
    has_processDeclaration["Destructor"] = false;
    has_processDeclaration["DoStatement"] = false;
    has_processDeclaration["EmptyStatement"] = false;
    has_processDeclaration["EnumBody"] = false;
//    has_processDeclaration["EnumDeclaration"] = true;
    has_processDeclaration["EnumMember"] = false;
    has_processDeclaration["EnumMemberAttribute"] = false;
    has_processDeclaration["EqualExpression"] = false;
    has_processDeclaration["ExpressionStatement"] = false;
    has_processDeclaration["Finally"] = false;
    has_processDeclaration["FinalSwitchStatement"] = false;
    has_processDeclaration["ForStatement"] = true;
    has_processDeclaration["ForeachStatement"] = true;
    has_processDeclaration["ForeachTypeList"] = false;
    has_processDeclaration["ForeachType"] = false;
    has_processDeclaration["FunctionAttribute"] = false;
    has_processDeclaration["FunctionCallExpression"] = false;
    has_processDeclaration["FunctionContract"] = false;
    has_processDeclaration["FunctionDeclaration"] = true;
    has_processDeclaration["FunctionLiteralExpression"] = true;
    has_processDeclaration["GotoStatement"] = false;
    has_processDeclaration["IdentifierChain"] = false;
    has_processDeclaration["IdentifierInitializer"] = false;
    has_processDeclaration["IdentifierList"] = false;
    has_processDeclaration["IdentityExpression"] = false;
    has_processDeclaration["IfStatement"] = true;
    has_processDeclaration["IfCondition"] = false;
    has_processDeclaration["ImportBind"] = false;
    has_processDeclaration["ImportBindings"] = false;
    has_processDeclaration["ImportDeclaration"] = true;
    has_processDeclaration["ImportExpression"] = false;
    has_processDeclaration["Index"] = false;
    has_processDeclaration["IndexExpression"] = false;
    has_processDeclaration["InExpression"] = false;
    has_processDeclaration["Initializer"] = false;
    has_processDeclaration["InContractExpression"] = false;
    has_processDeclaration["InStatement"] = false;
    has_processDeclaration["Initializer"] = false;
    //has_processDeclaration["InterfaceDeclaration"] = true;
    has_processDeclaration["Invariant"] = false;
    has_processDeclaration["IsExpression"] = true;
    has_processDeclaration["KeyValuePair"] = false;
    has_processDeclaration["KeyValuePairs"] = false;
    has_processDeclaration["LambdaExpression"] = true;
    has_processDeclaration["LabeledStatement"] = false;
    has_processDeclaration["LastCatch"] = false;
    has_processDeclaration["LinkageAttribute"] = false;
    has_processDeclaration["MemberFunctionAttribute"] = false;
    has_processDeclaration["MixinDeclaration"] = false;
    has_processDeclaration["MixinQualifiedIdentifier"] = false;
    has_processDeclaration["MixinTemplateDeclaration"] = true;
    has_processDeclaration["MixinTemplateName"] = false;
    //has_processDeclaration["ModuleDeclaration"] = false;
    has_processDeclaration["MulExpression"] = false;
    has_processDeclaration["NamedImportBind"] = false;
    has_processDeclaration["NewAnonClassExpression"] = false;
    has_processDeclaration["NewExpression"] = false;
    has_processDeclaration["Operands"] = false;
    has_processDeclaration["OrExpression"] = false;
    has_processDeclaration["OrOrExpression"] = false;
    has_processDeclaration["OutStatement"] = true;
    has_processDeclaration["Parameter"] = false;
    has_processDeclaration["ParameterAttribute"] = false;
    has_processDeclaration["Parameters"] = false;
    has_processDeclaration["Postblit"] = false;
    has_processDeclaration["PowExpression"] = false;
    has_processDeclaration["PragmaExpression"] = false;
    has_processDeclaration["QualifiedIdentifier"] = false;
    has_processDeclaration["Register"] = false;
    has_processDeclaration["RelExpression"] = false;
    has_processDeclaration["ReturnStatement"] = false;
    has_processDeclaration["ScopeGuardStatement"] = false;
    has_processDeclaration["SharedStaticConstructor"] = false;
    has_processDeclaration["SharedStaticDestructor"] = false;
    has_processDeclaration["ShiftExpression"] = false;
//    has_processDeclaration["SingleImport"] = false;
    has_processDeclaration["SliceExpression"] = false;
    has_processDeclaration["UnaryExpression"] = false;
    has_processDeclaration["StaticAssertDeclaration"] = false;
    has_processDeclaration["StaticAssertStatement"] = false;
    has_processDeclaration["StaticConstructor"] = false;
    has_processDeclaration["StaticDestructor"] = false;
    has_processDeclaration["StaticCtorDtorCommon"] = false;
    has_processDeclaration["StaticForeachDeclaration"] = true;
    has_processDeclaration["StaticIfCondition"] = false;
    has_processDeclaration["StorageClass"] = false;
    has_processDeclaration["StructBody"] = true;
//    has_processDeclaration["StructDeclaration"] = true;
    has_processDeclaration["StructInitializer"] = false;
    has_processDeclaration["StructMemberInitializer"] = false;
    has_processDeclaration["StructMemberInitializers"] = false;
    has_processDeclaration["String"] = false;
    has_processDeclaration["SwitchStatement"] = false;
    has_processDeclaration["SynchronizedStatement"] = false;
    has_processDeclaration["TemplateAliasParameter"] = false;
    has_processDeclaration["TemplateArgument"] = false;
    has_processDeclaration["TemplateArgumentList"] = false;
    has_processDeclaration["TemplateArguments"] = false;
//    has_processDeclaration["TemplateDeclaration"] = true;
    has_processDeclaration["TemplateInstance"] = false;
    has_processDeclaration["TemplateMixinDeclaration"] = true;
    has_processDeclaration["TemplateParameter"] = false;
    has_processDeclaration["TemplateParameterList"] = false;
    has_processDeclaration["TemplateParameters"] = false;
    has_processDeclaration["TemplateSingleArgument"] = false;
    has_processDeclaration["TemplateThisParameter"] = false;
    has_processDeclaration["TemplateTupleParameter"] = false;
    has_processDeclaration["TemplateTypeParameter"] = false;
    has_processDeclaration["TemplateValueParameter"] = false;
    has_processDeclaration["TemplateValueParameterDefault"] = false;
    has_processDeclaration["TernaryExpression"] = false;
    has_processDeclaration["ThrowStatement"] = false;
    has_processDeclaration["TraitsExpression"] = false;
    has_processDeclaration["TryStatement"] = false;
    has_processDeclaration["Type"] = false;
    has_processDeclaration["TypeConstructor"] = false;
    has_processDeclaration["TypeidExpression"] = false;
    has_processDeclaration["TypeofExpression"] = false;
    has_processDeclaration["TypeSpecialization"] = false;
    has_processDeclaration["TypeSuffix"] = false;
    has_processDeclaration["UnaryExpression"] = false;
//    has_processDeclaration["UnionDeclaration"] = true;
    has_processDeclaration["Unittest"] = false;
    has_processDeclaration["VariableDeclaration"] = false;
    has_processDeclaration["Vector"] = false;
    has_processDeclaration["VersionCondition"] = false;
    //has_processDeclaration["VersionSpecification"] = false;
    has_processDeclaration["WhileStatement"] = true;
    has_processDeclaration["WithStatement"] = false;
    has_processDeclaration["XorExpression"] = false;

    foreach (string key; named_children) {
        // Stub elements are not renamed
        if (key in stub_children)
            continue;
        renameMap["DLanguage" ~ key] = "Dlang" ~ key;
    }
    renameMap["DLanguageIdentifierInitializer"] = "DLanguageIdentifierInitializer";
    renameMap["DLanguageAutoAssignment"] = "DLanguageAutoAssignment";
}

enum psiDlangImportTemplate = "import io.github.intellij.dlanguage.psi.DLanguage%s;";
enum psiDlangExtraInterfaceImportTemplate = "import io.github.intellij.dlanguage.psi.interfaces.%s;";
enum psiDlangNamedImportTemplate = "import io.github.intellij.dlanguage.psi.named.DLanguage%s;";

static immutable string[] defaultInterfaceImports = [
"import com.intellij.psi.PsiElement;",
"import org.jetbrains.annotations.Nullable;",
];

static immutable string[] defaultImplImports = [
    "import com.intellij.lang.ASTNode;",
    "import com.intellij.psi.PsiElement;",
    "import com.intellij.psi.PsiElementVisitor;",
    "import com.intellij.psi.util.PsiTreeUtil;",
    "import io.github.intellij.dlanguage.psi.DlangVisitor;",
    "import org.jetbrains.annotations.NotNull;",
    "import org.jetbrains.annotations.Nullable;",
];

static immutable string[] defaultStubImplImports = [
    "import com.intellij.lang.ASTNode;",
    "import com.intellij.psi.PsiElement;",
    "import com.intellij.psi.PsiElementVisitor;",
    "import com.intellij.psi.stubs.IStubElementType;",
    "import com.intellij.psi.util.PsiTreeUtil;",
    "import io.github.intellij.dlanguage.psi.DlangTypes;",
    "import io.github.intellij.dlanguage.psi.DlangVisitor;",
    "import org.jetbrains.annotations.NotNull;",
    "import org.jetbrains.annotations.Nullable;",
];

immutable string implFileTemplate =
`package io.github.intellij.dlanguage.psi.impl;

%s


public class %s extends %s implements
    %s {

    public %s(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visit%s(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }
`;

string implStubFileTemplate =
`package io.github.intellij.dlanguage.psi.impl;

%s


public class %s extends
    DStubbedPsiElementBase<%s> implements %s {

    public %s(ASTNode node) {
        super(node);
    }

    public %s(%s stub,
        IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visit%s(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }
`;

string interfaceFileTemplate(string[] interfaces = []) {

    string base =
`package io.github.intellij.dlanguage.psi;

%s


public interface %s extends PsiElement`;
    foreach(string interface_; interfaces) {
        base ~= (", " ~ interface_);
    }
    base ~= " {\n";
    return base;
}

bool isToken(string toget) {
    import std.array : replace;
    toget = toget.replace("*", "");
    return toget.canFind("OP") || toget.canFind("KW") || toget.canFind("LITERAL") || toget.canFind("STRING") || toget == "Identifier";
}

bool isExtraInterface(string toget) {
    import std.algorithm : canFind;
    return toget !in types_children && toget !in stub_children && !named_children.canFind(toget);
}

string[] getExtraImport(string[] elements, string key) {
    string[] normalImports;
    if(elements.canFind!(a => (a.canFind("*"))))
        normalImports ~= "import java.util.List;";
    import std.array;
    import std.algorithm;
    if(key in has_processDeclaration && has_processDeclaration[key])
        normalImports ~= [ "import com.intellij.psi.ResolveState;",
        "import com.intellij.psi.scope.PsiScopeProcessor;",
        "import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;",
        "import org.jetbrains.annotations.NotNull;",
        ];
    return normalImports;
}

string getNonTokenImport(string toget, string key) {
    import std.array;
    string import_;
    if (toget.canFind("*")) {
        toget = toget.replace("*", "");
        if ((named_children.canFind(toget) && toget !in stub_children))
            import_ = "import io.github.intellij.dlanguage.psi.named.DLanguage%s;".format(toget);
        else if (isExtraInterface(toget))
            import_ = psiDlangExtraInterfaceImportTemplate.format(toget);
        else
            import_ = psiDlangImportTemplate.format(toget);
    } else {
        if (named_children.canFind(toget) && toget !in stub_children)
            import_ = psiDlangNamedImportTemplate.format(toget);
        else if (isExtraInterface(toget))
            import_ = psiDlangExtraInterfaceImportTemplate.format(toget);
        else
            import_ = psiDlangImportTemplate.format(toget);
    }
    return import_;
}

void getImplImportsElements(string[] elements, string key, ref string[] staticImports, ref string[] normalImports) {
    import std.array;
    // By default import the base type
    normalImports ~= psiDlangImportTemplate.format(key);
    foreach(string toget; elements) {
        if (isToken(toget)) {
            toget = toget.replace("*", "");
            // tokens are imported statically
            if (toget == "Identifier")
                toget = "ID";
            staticImports ~= "import static io.github.intellij.dlanguage.psi.DlangTypes.%s;".format(toget);
        } else {
            normalImports ~= getNonTokenImport(toget, key);
        }
    }
    normalImports ~= getExtraImport(elements, key);
}

string formatImplImports(const string[] staticImports, const string[] normalImports) {
    import std.array;
    string importString = staticImports.dup.sort.uniq.join("\n");
    if (staticImports.length > 0)
        importString ~= "\n\n";
    importString ~= normalImports.dup.sort.uniq.join("\n");
    return importString;
}

string getImplImports(string[] elements, string key, string parentClassName = null) {
    string[] normalImports = defaultImplImports.dup;
    string[] staticImports;
    if (parentClassName != null && parentClassName != defaultParentClassName) {
        normalImports ~= "import io.github.intellij.dlanguage.psi.ext." ~ parentClassName ~ ";";
    } else {
        normalImports ~= "import com.intellij.extapi.psi.ASTWrapperPsiElement;";
    }
    getImplImportsElements(elements, key, staticImports, normalImports);
    return formatImplImports(staticImports, normalImports);
}

string getStubImplImports(string[][string] elements, string key) {
    string[] normalImports = defaultStubImplImports.dup;
    string[] staticImports;
    auto stubImport = key == "Unittest" ? "import io.github.intellij.dlanguage.stubs.interfaces.Dlang%sStub;".format(key) : "import io.github.intellij.dlanguage.stubs.Dlang%sStub;".format(key);
    normalImports ~= stubImport;
    getImplImportsElements(elements[key], key, staticImports, normalImports);
    return formatImplImports(staticImports, normalImports);
}

string getterMethod(string toget) {
    import std.string;
    bool hasMultiple = toget.canFind("*");
    toget = toget.replace("*", "");

    string type;
    if (isToken(toget))
        type = "PsiElement";
    else if (isExtraInterface(toget))
        type = toget;
    else
        type = "DLanguage" ~ toget;

    if (hasMultiple) {
        if (isToken(toget)) {
            auto typeToGet = toget;
            if (toget == "Identifier")
                typeToGet = "ID";
            return `
    @NotNull
    public List<PsiElement> get%ss() {
        return findChildrenByType(%s);
    }
`.format(toget,typeToGet);
        }
        else {
            return `
    @NotNull
    public List<%s> get%ss() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, %s.class);
    }
`.format(type,toget,type);
        }
    }

    if (isToken(toget)) {
        auto typeToGet = toget;
        if (toget == "Identifier")
            typeToGet = "ID";
        return `
    @Nullable
    public PsiElement get%s() {
        return findChildByType(%s);
    }
`.format(toget,typeToGet);
    }
    else {
        return `
    @Nullable
    public %s get%s() {
        return PsiTreeUtil.getChildOfType(this, %s.class);
    }
`.format(type,toget,type);
    }
}

string[] getInterfaceImportElements(string[] elements, string key) {
    string[] normalImports = defaultInterfaceImports.dup;
    foreach(string toget; elements){
        if (isToken(toget))
            continue;
        normalImports ~= getNonTokenImport(toget, key);
    }
    if(elements.canFind!(a => (a.canFind("*")))) {
        normalImports ~= ["import java.util.List;", "import org.jetbrains.annotations.NotNull;" ];
    }
    normalImports ~= getExtraImport(elements, key);
    return normalImports;
}

string getInterfaceImports(string[] elements, string key) {
    import std.array;
    auto imports = getInterfaceImportElements(elements, key);
    return imports.sort.uniq.join("\n");
}

string getStubInterfaceImports(string[] elements, string key) {
    import std.array;
    auto imports = getInterfaceImportElements(elements, key);
    // unittest is a special case where it’s stored under another path
    auto stubImport = key == "Unittest" ? "import io.github.intellij.dlanguage.stubs.interfaces.Dlang%sStub;".format(key) : "import io.github.intellij.dlanguage.stubs.Dlang%sStub;".format(key);
    imports ~= [
        "import com.intellij.psi.StubBasedPsiElement;",
        "import io.github.intellij.dlanguage.psi.interfaces.DCompositeElement;",
        stubImport
    ];
    //if (named_children.canFind(key))
        imports ~= "import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;";
    return imports.sort.uniq.join("\n");
}

string getStubImplImports(string[] elements, string key) {
    import std.array;
    // By default import the base type
    string[] staticImports;
    string[] normalImports = defaultStubImplImports.dup;
    normalImports ~= psiDlangImportTemplate.format(key);
    foreach(string toget; elements){
        if (isToken(toget)) {
            continue;
        }
        normalImports ~= getNonTokenImport(toget, key);
    }
    normalImports ~= getExtraImport(elements, key);
    string importString = staticImports.sort.uniq.join("\n");
    if (staticImports.length > 0)
        importString ~= "\n\n";
    importString ~= normalImports.sort.uniq.join("\n");
    return importString;
}

string getterMethodInterface(string toget) {
    import std.string;
    string type;
    bool hasMultiple = toget.canFind("*");
    toget = toget.replace("*","");

    if (isToken(toget))
        type = "PsiElement";
    else if (isExtraInterface(toget))
        type = toget;
    else
        type = "DLanguage" ~ toget;

    if (hasMultiple) {
        return `
    @NotNull
    List<%s> get%ss();
`.format(type, toget);
    }
    return `
    @Nullable
    %s get%s();
`.format(type, toget);
}



int main(string[] args) {
    import std.file;
    import std.path;
    import std.array;
    if (!exists("impl"))
        mkdir("impl");
    foreach(string key;types_children.keys){
        import std.algorithm;
        import std.string;
        string interfaceClassName = "DLanguage" ~ key;
        string implClassName = "DLanguage" ~ key ~ "Impl";
        string parentClassName = key in types_mixins ? types_mixins[key] : defaultParentClassName;
        string implFile = implFileTemplate.format(getImplImports(types_children[key], key, parentClassName), implClassName, parentClassName, interfaceClassName, implClassName, key);
        foreach(string toget; types_children[key]){
            implFile ~= getterMethod(toget);
        }
        if(key in has_processDeclaration && has_processDeclaration[key]){
            implFile ~= `
    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor,
                                       @NotNull ResolveState state,
                                       PsiElement lastParent,
                                       @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this,processor,state,lastParent,place);
    }
`;
        }
        implFile ~= "\n}\n";
        auto extraInterfaces = key in types_extra_interfaces ? types_extra_interfaces[key] : [];
        string interfaceFile = interfaceFileTemplate(extraInterfaces).format(getInterfaceImports((types_children[key] ~ extraInterfaces).uniq.array, key), interfaceClassName);
        foreach(string toget; types_children[key]){
            interfaceFile ~= getterMethodInterface(toget);
        }
        interfaceFile ~= "\n}\n";
        File f = File(chainPath("impl",(implClassName ~ ".java").renameMapApply()),"w");
        f.write(implFile.renameMapApply());
        f.close();
        f = File((interfaceClassName ~".java").renameMapApply(),"w");
        f.write(interfaceFile.renameMapApply());
        f.close();
    }

    foreach(string key; stub_children.keys) {
        import std.algorithm;
        import std.array;
        import std.string;
        string interfaceClassName = "DLanguage" ~ key;
        string[] interfaces = ["DCompositeElement","StubBasedPsiElement<Dlang%sStub>".format(key)];
        if (named_children.canFind(key))
            interfaces.insertInPlace(1, "DNamedElement");
        auto extraInterfaces = key in types_extra_interfaces ? types_extra_interfaces[key] : [];
        string interfaceFile = interfaceFileTemplate(interfaces ~ extraInterfaces).format(getStubInterfaceImports((stub_children[key] ~ extraInterfaces).uniq.array, key), interfaceClassName);
        foreach (string toget; stub_children[key]) {
            interfaceFile ~= getterMethodInterface(toget);
        }
        if(key in has_processDeclaration && has_processDeclaration[key]) {
            interfaceFile ~= `
    @Override
    default public boolean processDeclarations(@NotNull PsiScopeProcessor processor,
                                               @NotNull ResolveState state,
                                               PsiElement lastParent,
                                               @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this,processor,state,lastParent,place);
    }
`;
        }
        interfaceFile ~= "\n}\n";
        File f = File((interfaceClassName ~".java"),"w");
        f.write(interfaceFile.renameMapApply());
        f.close();
        if (generate_stubImpl.canFind(key)) {
            string implClassName = "DLanguage" ~ key ~ "Impl";
            string stubPsiElementClassName = "Dlang" ~ key ~ "Stub";
            string implFile = implStubFileTemplate.format(getStubImplImports(stub_children, key), implClassName, stubPsiElementClassName, interfaceClassName, implClassName, implClassName, stubPsiElementClassName, key);
            foreach (string toget; stub_children[key]) {
                implFile ~= getterMethod(toget);
            }
            implFile ~= "\n}\n";
            f = File(chainPath("impl",(implClassName ~ ".java").renameMapApply()),"w");
            f.write(implFile.renameMapApply());
            f.close();
        }
    }
    return 0;
}


string renameMapApply(string thing) {
    import std.regex;
    foreach(reg; renameMap.keys) {
        auto re = regex(reg ~ "\\ ");
        thing = thing.replaceAll(re, renameMap[reg] ~ " ");
        re = regex(reg ~ "\\.");
        thing = thing.replaceAll(re, renameMap[reg]~".");
        re = regex(reg ~ ">");
        thing = thing.replaceAll(re, renameMap[reg]~">");
        re = regex(reg ~ "\\{");
        thing = thing.replaceAll(re, renameMap[reg]~"{");
        re = regex(reg ~ "Stub\\ ");
        thing = thing.replaceAll(re, renameMap[reg] ~ "Stub ");
        re = regex(reg ~ "Stub\\.");
        thing = thing.replaceAll(re, renameMap[reg]~"Stub.");
        re = regex(reg ~ "Stub>");
        thing = thing.replaceAll(re, renameMap[reg]~"Stub>");
        re = regex(reg ~ ";");
        thing = thing.replaceAll(re,renameMap[reg]~";");
    }
    return thing;
}
