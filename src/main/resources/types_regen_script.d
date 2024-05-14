#!/usr/bin/rdmd
/**
Script to generate the psi implementations. Generated files are located at the current location.
Note: This script is written to be executed in gen/io/github/intellij/dlanguage/psi/ directory
 */

//todo add stub children finder, to prevent loading of psi tree

import std.stdio;
import std.format;
import std.algorithm;


string[][string] types_children;

string[][string] stub_children;

/**
 * Contain the name of Psi elements that are named elements
 */
string[] named_children = [
    "AliasInitializer",
    "AutoDeclarationPart",
    "Catch",
    "Constructor",
    "Declarator",
    "DeclaratorIdentifier",
    "Destructor",
    "EnumDeclaration",
    "EnumMember",
    "EponymousTemplateDeclaration",
    "ForeachType",
    "FunctionDeclaration",
    "Identifier",
    "IfCondition",
    "InterfaceOrClass",
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
    types_children["AddExpression"] = ["AddExpression", "MulExpression","OP_TILDA", "OP_PLUS" , "OP_MINUS"]; //todo insert from MulExpression
    types_children["AliasAssign"] = ["Type","OP_EQ"];
    types_children["AliasDeclaration"] = ["DeclaratorIdentifier*","OP_COMMA","StorageClass*","KW_ALIAS", "Type", "OP_SCOLON", "AliasInitializer*"];
    types_children["AliasThisDeclaration"] = ["KW_ALIAS", "Identifier", "KW_THIS", "OP_SCOLON"];
    types_children["AlignAttribute"] = ["KW_ALIGN", "AssignExpression", "OP_PAR_RIGHT", "OP_PAR_LEFT"];
    types_children["AndAndExpression"] = ["AndAndExpression", "OrExpression", "OP_BOOL_AND"];
    types_children["AndExpression"] = ["AndExpression", "CmpExpression", "OP_AND"];
    types_children["AnonymousEnumDeclaration"] = ["AssignExpression","OP_COLON","KW_ENUM","OP_PAR_LEFT","OP_PAR_RIGHT","Type","EnumMember*"];
    types_children["AnonymousEnumMember"] = [];
    types_children["ArgumentList"] = ["AssignExpression*", "OP_COMMA*"];
    types_children["Arguments"] = ["ArgumentList","OP_PAR_RIGHT", "OP_PAR_LEFT"];
    types_children["ArrayInitializer"] = ["OP_COMMA*", "ArrayMemberInitialization*"];
    types_children["ArrayLiteral"] = ["ArrayInitializer", "OP_BRACKET_RIGHT", "OP_BRACKET_LEFT"];
    types_children["ArrayMemberInitialization"] = ["AssignExpression", "OP_COLON", "NonVoidInitializer", "OP_BRACES_LEFT"];
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
    types_children["AsmTypePrefix"] = ["Identifier*"];
    types_children["AsmUnaExp"] = ["AsmUnaExp","Identifier","AsmExp","OP_PLUS","OP_MINUS","OP_NOT","OP_TILDA","AsmPrimaryExp"];
    types_children["AsmXorExp"] = [/*"AsmPrimaryExp", "AsmTypePrefix", "AsmExp",*/ "AsmXorExp", "AsmAndExp", "OP_XOR"];
    types_children["AssertArguments"] = ["AssignExpression*", "OP_COMMA"];
    types_children["AssertExpression"] = ["KW_ASSERT","OP_BRACES_LEFT","OP_BRACES_RIGHT" ,"AssignExpression*", "OP_COMMA*"];
    types_children["AssignExpression"] = ["TernaryExpression","AssignExpression","OP_AND_EQ","OP_DIV_EQ","OP_EQ","OP_EQ_EQ","OP_GT_EQ","OP_LESS_EQ","OP_LESS_GR_EQ","OP_MINUS_EQ","OP_MOD_EQ","OP_MUL_EQ","OP_NOT_EQ","OP_NOT_GR_EQ","OP_NOT_LESS_EQ","OP_OR_EQ","OP_PLUS_EQ","OP_POW_EQ","OP_SH_LEFT_EQ","OP_SH_RIGHT_EQ","OP_TILDA_EQ","OP_UNORD_EQ","OP_USH_RIGHT_EQ","OP_XOR_EQ"];
    types_children["AssocArrayLiteral"] = ["KeyValuePairs","OP_BRACKET_RIGHT","OP_BRACKET_LEFT"];
    types_children["AtAttribute"] = ["OP_AT","OP_PAR_LEFT","OP_PAR_RIGHT","Identifier", "ArgumentList","FunctionCallExpression"];
    types_children["Attribute"] = ["PragmaExpression", "KW_SYNCHRONIZED","KW_ABSTRACT","KW_AUTO","KW_ENUM","KW_EXTERN","KW_FINAL","KW_INOUT","KW_NOTHROW","KW_THROW","KW_OVERRIDE","KW_PURE","KW_REF","KW___GSHARED","KW_SCOPE","KW_STATIC","KW_EXPORT", "KW_PRIVATE","KW_PROTECTED","KW_PUBLIC","AlignAttribute","Deprecated","AtAttribute","KW_PACKAGE","IdentifierChain","LinkageAttribute","KW_CONST"];
    types_children["AttributeDeclaration"] = ["OP_COLON","Attribute"];
    types_children["AutoDeclaration"] = ["StorageClass*","OP_COMMA*","OP_SCOLON","AutoDeclarationPart*"];
    types_children["BlockStatement"] = ["DeclarationsAndStatements","OP_BRACES_RIGHT","OP_BRACES_LEFT"];
    types_children["BreakStatement"] = ["KW_BREAK","Identifier","OP_SCOLON"];
    types_children["BaseClass"] = ["TypeofExpression","OP_DOT","IdentifierOrTemplateChain"];
    types_children["BaseClassList"] = ["BaseClass*","OP_COMMA*"];
    types_children["BuiltinType"] = [/*todo add the types*/];  // UNUSED currently
    types_children["CaseRangeStatement"] = ["KW_CASE*","OP_TRIPLEDOT","OP_COLON*","AssignExpression", "DeclarationsAndStatements"];
    types_children["CaseStatement"] = ["KW_CASE","OP_COLON","ArgumentList","DeclarationsAndStatements"];
    types_children["CastExpression"] = ["CastQualifier", "Type","UnaryExpression","KW_CAST","OP_PAR_LEFT","OP_PAR_RIGHT"];
    types_children["CastQualifier"] = ["KW_IMMUTABLE","KW_CONST","KW_SHARED","KW_INOUT"];
    types_children["Catches"] = ["LastCatch","Catch"];
    types_children["ClassDeclaration"] = ["KW_CLASS","InterfaceOrClass"/*"Identifier","OP_SCOLON","OP_COLON","StructBody","TemplateParameters","Constraint","BaseClassList"*/];
    types_children["CmpExpression"] = ["ShiftExpression","EqualExpression","IdentityExpression","RelExpression","InExpression"];
    types_children["CompileCondition"] = [ "VersionCondition",  "DebugCondition",  "StaticIfCondition"];
    types_children["ConditionalDeclaration"] = ["CompileCondition", "Declaration*","OP_COLON","KW_ELSE","OP_BRACES_RIGHT","OP_BRACES_LEFT"];
    types_children["ConditionalStatement"] = ["CompileCondition", "DeclarationOrStatement*", "KW_ELSE","OP_BRACES_RIGHT","OP_BRACES_LEFT"];
    types_children["Constraint"] = ["KW_IF","Expression", "OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_children["ContinueStatement"] = ["KW_CONTINUE", "Identifier", "OP_SCOLON"];
    types_children["DebugCondition"] = ["KW_DEBUG","INTEGER_LITERAL", "Identifier", "OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_children["DebugSpecification"] = ["KW_DEBUG","OP_EQ","Identifier", "INTEGER_LITERAL", "OP_SCOLON"];
    types_children["Declaration"] = ["AliasThisDeclaration","AliasDeclaration","ClassDeclaration","ConditionalDeclaration"/*,"Postblit"*/,"Constructor","Destructor","AnonymousEnumDeclaration","EponymousTemplateDeclaration","EnumDeclaration","ImportDeclaration","InterfaceDeclaration","MixinTemplateDeclaration","MixinDeclaration","PragmaDeclaration",  "SharedStaticConstructor", "SharedStaticDestructor","StaticConstructor","StaticDestructor","StaticAssertDeclaration","StructDeclaration","TemplateDeclaration","UnionDeclaration","Invariant","Unittest","VersionSpecification","DebugSpecification","Attribute*","Declaration*","OP_BRACES_RIGHT","OP_BRACES_LEFT","FunctionDeclaration","VariableDeclaration","AttributeDeclaration"];
    types_children["DeclarationOrStatement"] = ["Statement","Declaration"];
    types_children["DeclarationsAndStatements"] = ["DeclarationOrStatement*"];
    types_children["DefaultStatement"] = ["KW_DEFAULT","OP_COLON","DeclarationsAndStatements"];
    types_children["DeleteExpression"] = ["KW_DELETE","UnaryExpression"];
    types_children["DeleteStatement"] = ["KW_DELETE"];
    types_children["Deprecated"] = ["AssignExpression","Deprecated","OP_PAR_RIGHT","OP_PAR_LEFT"];
    //stub_children ["Destructor"] = ["FunctionBody","OP_SCOLON","OP_PAR_RIGHT","OP_PAR_LEFT","KW_THIS","OP_TILDA","MemberFunctionAttribute*"];
    types_children["DoStatement"] = ["KW_DO","KW_WHILE","StatementNoCaseNoDefault","Expression","OP_SCOLON","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_children["EnumBody"] = [ "Identifier","OP_BRACES_RIGHT", "OP_BRACES_LEFT", "EnumMember*","OP_COMMA*","OP_SCOLON"];
    types_children["EnumMemberAttribute"] = ["Attribute","Deprecated"];
    stub_children ["EponymousTemplateDeclaration"] = ["Identifier", "TemplateParameters", "OP_EQ", "Type", "OP_SCOLON", "KW_ENUM", "KW_ALIAS"];
    types_children["EqualExpression"] = ["ShiftExpression*","OP_EQ_EQ","OP_NOT_EQ"];
    types_children["Expression"] = ["AssignExpression*","OP_COMMA*"];
    types_children["ExpressionStatement"] = ["Expression","OP_SCOLON"];
    types_children["Finally"] = ["KW_FINALLY", "DeclarationOrStatement"];
    types_children["FinalSwitchStatement"] = ["KW_FINAL", "SwitchStatement"];
    types_children["ForStatement"] = ["DeclarationOrStatement*", "Expression*","OP_BRACES_RIGHT","OP_BRACES_LEFT","KW_FOR","OP_SCOLON"];
    types_children["ForeachStatement"] = ["KW_FOREACH","KW_FOREACH_REVERSE","DeclarationOrStatement", "Expression*","OP_BRACES_RIGHT","OP_BRACES_LEFT","OP_DDOT","ForeachType","ForeachTypeList","OP_SCOLON"];
    types_children["ForeachTypeList"] = ["ForeachType*","OP_COMMA*"];
    types_children["FunctionAttribute"] = ["AtAttribute","KW_PURE","KW_NOTHROW"];
    types_children["FunctionBody"] = ["SpecifiedFunctionBody","MissingFunctionBody","ShortenedFunctionBody"];
    types_children["FunctionCallExpression"] = ["Type", "Arguments", "UnaryExpression",  "TemplateArguments"];
    types_children["FunctionContract"] = ["OP_BRACES_LEFT", "InOutStatement", "OP_PAR_LEFT", "InOutContractExpression"];
    types_children["FunctionLiteralExpression"] = ["Type","KW_FUNCTION","KW_DELEGATE","KW_REF","Parameters","FunctionAttribute*","SpecifiedFunctionBody","Identifier"];
    types_children["GotoStatement"] = ["Identifier","Expression","KW_DEFAULT","KW_CASE","KW_GOTO","OP_SCOLON"];
    types_children["IdentifierChain"] = ["Identifier*", "OP_DOT*"];
    types_children["IdentifierOrTemplateChain"] = ["OP_DOT*","IdentifierOrTemplateInstance*"];
    types_children["IdentifierOrTemplateInstance"] = ["Identifier","TemplateInstance"];
    types_children["IdentityExpression"] = ["ShiftExpression*","KW_IS","OP_NOT"];
    stub_children ["IfCondition"] = ["Identifier",  "Expression", "KW_AUTO", "KW_SCOPE","Type", "OP_EQ"];
    types_children["IfStatement"] = [/*"Identifier","Expression",*/"DeclarationOrStatement*","KW_ELSE","KW_IF","OP_PAR_LEFT","OP_PAR_RIGHT"/*,"KW_AUTO","Type","OP_EQ"*/,"IfCondition"];
    types_children["ImportBind"] = ["Identifier","NamedImportBind"];
    types_children["ImportBindings"] = ["OP_COMMA*","ImportBind*","SingleImport","OP_COLON"];
    types_children["ImportDeclaration"] = ["KW_IMPORT","SingleImport*","ImportBindings","OP_COMMA*","OP_SCOLON"];
    types_children["ImportExpression"] = ["ImportExpression","AssignExpression","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_children["Index"] = ["AssignExpression"];
    types_children["IndexExpression"] = ["OP_BRACKET_LEFT","OP_BRACKET_RIGHT","ArgumentList","UnaryExpression"];
    types_children["InExpression"] = ["ShiftExpression*","KW_IN","OP_NOT"];
    types_children["Initializer"] = ["KW_VOID","NonVoidInitializer"];
    types_children["InContractExpression"] = ["KW_IN","OP_PAR_LEFT", "AssertArguments", "OP_PAR_RIGHT"];
    types_children["InOutContractExpression"] = ["InContractExpression", "OutContractExpression"];
    types_children["InOutStatement"] = ["InStatement", "OutStatement"];
    types_children["InStatement"] = ["KW_IN","BlockStatement"];
    types_children["Initializer"] = ["KW_VOID","NonVoidInitializer"];
    types_children["InterfaceDeclaration"] = ["KW_INTERFACE","InterfaceOrClass"];
    types_children["Invariant"] = ["BlockStatement","KW_INVARIANT","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_children["IsExpression"] = ["OP_PAR_RIGHT","OP_PAR_LEFT","Type","Identifier","TypeSpecialization","TemplateParameterList","OP_COMMA","OP_COLON","OP_EQ","KW_IS"];//todo technically this should be named
    types_children["KeyValuePair"] = ["AssignExpression*","OP_COLON"];
    types_children["KeyValuePairs"] = ["OP_COMMA*","KeyValuePair*"];
    stub_children ["LabeledStatement"] = ["Identifier", "OP_COLON","DeclarationOrStatement"];
    types_children["LambdaExpression"] = ["Identifier","KW_FUNCTION","KW_DELEGATE","OP_LAMBDA_ARROW","AssignExpression","Parameters","FunctionAttribute*"];
    types_children["LastCatch"] = ["KW_CATCH","StatementNoCaseNoDefault"];
    types_children["LinkageAttribute"] = ["IdentifierChain","Identifier","OP_PAR_RIGHT","OP_PAR_LEFT","OP_PLUS_PLUS","KW_EXTERN","OP_COMMA"];
    types_children["MemberFunctionAttribute"] = [ "FunctionAttribute","KW_IMMUTABLE","KW_INOUT","KW_SHARED","KW_CONST"];
    types_children["MissingFunctionBody"] = ["FunctionContract*","OP_SCOLON"];
    types_children["MixinDeclaration"] = ["TemplateMixinExpression","MixinExpression","OP_SCOLON"];
    types_children["MixinExpression"] = ["ArgumentList","OP_PAR_RIGHT","OP_PAR_LEFT","KW_MIXIN"];
    types_children["MixinTemplateDeclaration"] = ["TemplateDeclaration","KW_MIXIN"];
    types_children["MixinTemplateName"] = ["TypeofExpression", "IdentifierOrTemplateChain", "Symbol","OP_DOT"];
    types_children["MulExpression"] = ["MulExpression","PowExpression","OP_MOD","OP_DIV","OP_ASTERISK"];
    stub_children ["NamedImportBind"] = ["Identifier",  "OP_EQ"];
    types_children["NamespaceList"] = ["OP_COMMA",  "TernaryExpression*"];
    types_children["NewAnonClassExpression"] = ["KW_NEW","KW_CLASS","Arguments","BaseClassList", "StructBody"];
    types_children["NewExpression"] = ["KW_NEW","NewAnonClassExpression","Type", "AssignExpression", "Arguments","OP_BRACKET_LEFT","OP_BRACKET_RIGHT"];
    types_children["NonVoidInitializer"] = ["AssignExpression", "ArrayLiteral", "StructInitializer"];
    types_children["Operands"] = ["OP_COMMA","Operands","AsmExp"];
    types_children["OrExpression"] = ["OrExpression", "XorExpression", "OP_OR"];
    types_children["OrOrExpression"] = ["AndAndExpression", "OrOrExpression", "OP_BOOL_OR"];
    types_children["OutContractExpression"] = ["KW_OUT", "OP_PAR_LEFT", "OP_SCOLON", "AssertArguments", "OP_PAR_RIGHT"];
    types_children["OutStatement"] = ["Identifier","BlockStatement","KW_OUT","OP_PAR_LEFT","OP_PAR_RIGHT"];
    types_children["ParameterAttribute"] = ["KW_FINAL","KW_IN","KW_LAZY","KW_OUT","KW_REF","KW_SCOPE","KW_AUTO","TypeConstructor","AtAttribute"];
    types_children["Parameters"] = ["OP_COMMA*","OP_TRIPLEDOT","Parameter*","OP_PAR_LEFT","OP_PAR_RIGHT"];
    types_children["Postblit"] = ["FunctionBody","OP_SCOLON","KW_THIS*","OP_PAR_LEFT","OP_PAR_RIGHT","MemberFunctionAttribute"];
    types_children["PowExpression"] = ["PowExpression","UnaryExpression","OP_POW"];
    types_children["PragmaDeclaration"] = ["PragmaExpression", "OP_SCOLON"];
    types_children["PragmaExpression"] = ["Identifier","ArgumentList","OP_PAR_LEFT","OP_PAR_RIGHT","OP_COMMA","KW_PRAGMA"];
    types_children["PragmaStatement"] = ["PragmaExpression", "BlockStatement", "OP_SCOLON", "Statement"];
    types_children["PrimaryExpression"] = ["Type","Arguments","FunctionLiteralExpression","TypeofExpression",
        "TypeidExpression","Vector","AssocArrayLiteral","ArrayLiteral","Expression","OP_PAR_LEFT","OP_PAR_RIGHT","IsExpression","LambdaExpression","TraitsExpression","MixinExpression","ImportExpression","KW_SUPER","KW_THIS","OP_DOLLAR","KW_TRUE","KW_FALSE","KW_REF","KW___DATE__","KW___EOF__","KW___FILE__","KW___FILE_FULL_PATH__","KW___FUNCTION__","KW___GSHARED","KW___LINE__","KW___MODULE__","KW___PARAMETERS","KW___PRETTY_FUNCTION__","KW___TIME__","KW___TIMESTAMP__","KW___TRAITS","KW___VECTOR","KW___VENDOR__","KW___VERSION__","INTEGER_LITERAL","FLOAT_LITERAL","DOUBLE_QUOTED_STRING*","DELIMITED_STRING*","CHARACTER_LITERAL","IdentifierOrTemplateInstance","OP_DOT","TypeConstructor"/*todo add basicTypes*/];
    types_children["Register"] = ["Identifier","INTEGER_LITERAL", "OP_PAR_RIGHT", "OP_PAR_LEFT"];
    types_children["RelExpression"] = ["RelExpression", "ShiftExpression","OP_GT","OP_GT_EQ","OP_LESS","OP_LESS_EQ","OP_LESS_GR","OP_LESS_GR_EQ","OP_NOT_GR","OP_NOT_GR_EQ","OP_NOT_LESS","OP_NOT_LESS_EQ","OP_UNORD","OP_UNORD_EQ"];
    types_children["ReturnStatement"] = ["KW_RETURN","Expression","OP_SCOLON"];
    types_children["ScopeGuardStatement"] = ["KW_SCOPE","Identifier","StatementNoCaseNoDefault","OP_PAR_LEFT","OP_PAR_RIGHT"];
    stub_children ["SharedStaticConstructor"] = ["KW_STATIC","KW_SHARED","KW_THIS","OP_PAR_LEFT","OP_PAR_RIGHT","FunctionBody"];
    stub_children ["SharedStaticDestructor"] = ["OP_TILDA","KW_STATIC","KW_SHARED","KW_THIS","OP_PAR_LEFT","OP_PAR_RIGHT","FunctionBody"];
    types_children["ShiftExpression"] = ["ShiftExpression","AddExpression","OP_SH_RIGHT","OP_SH_LEFT","OP_USH_RIGHT"];
    types_children["SliceExpression"] = ["UnaryExpression","AssignExpression*","OP_BRACKET_LEFT","OP_BRACKET_RIGHT","OP_DDOT"];
    types_children["ShortenedFunctionBody"] = ["FunctionContract*", "OP_LAMBDA_ARROW", "Expression", "OP_SCOLON"];
    types_children["SpecifiedFunctionBody"] = ["FunctionContract*", "KW_DO", "BlockStatement"];
    types_children["Statement"] = ["DefaultStatement","StatementNoCaseNoDefault","CaseStatement","CaseRangeStatement"];
    types_children["StatementNoCaseNoDefault"] = ["LabeledStatement","BlockStatement","IfStatement","WhileStatement","DoStatement","ForStatement","ForeachStatement","SwitchStatement","FinalSwitchStatement","ContinueStatement","BreakStatement","ReturnStatement","GotoStatement","WithStatement","SynchronizedStatement","TryStatement","ScopeGuardStatement","PragmaStatement","AsmStatement","DebugSpecification", "ConditionalStatement", "VersionSpecification","StaticAssertStatement","ExpressionStatement"];
    types_children["StaticAssertDeclaration"] = ["StaticAssertStatement"];
    types_children["StaticAssertStatement"] = ["KW_STATIC","AssertExpression","OP_SCOLON"];
    types_children["StaticCtorDtorCommon"] = ["FunctionBody"];
    stub_children ["StaticConstructor"] = ["KW_STATIC","KW_THIS","OP_PAR_LEFT","OP_PAR_RIGHT","FunctionBody"];
    stub_children ["StaticDestructor"] = ["OP_TILDA","KW_STATIC","KW_THIS","OP_PAR_LEFT","OP_PAR_RIGHT","FunctionBody"];
    types_children["StaticForeachDeclaration"] = ["KW_FOREACH", "KW_FOREACH_REVERSE", "Declaration", "Expression*", "OP_BRACES_RIGHT", "OP_BRACES_LEFT", "OP_DDOT", "ForeachType", "ForeachTypeList", "OP_SCOLON", "KW_STATIC"];
    types_children["StaticForeachStatement"] = ["KW_STATIC", "ForeachStatement"];
    types_children["StaticIfCondition"] = ["StaticIfCondition","AssignExpression","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_children["StorageClass"] = ["AtAttribute","Deprecated","AlignAttribute","LinkageAttribute",   "KW_SYNCHRONIZED","TypeConstructor","KW_ABSTRACT","KW_CONST","KW_IMMUTABLE","KW_AUTO","KW_ENUM","KW_EXTERN","KW_FINAL","KW_INOUT","KW_NOTHROW","KW_OVERRIDE","KW_PURE","KW_REF","KW___GSHARED","KW_SCOPE","KW_STATIC","KW_THROW"];
    types_children["StructBody"] = ["OP_BRACES_RIGHT","OP_BRACES_LEFT","Declaration*"];
//    named_children["StructDeclaration"] = [ "KW_STRUCT","Identifier",  "TemplateParameters",  "Constraint", "StructBody", "OP_SCOLON"];
    types_children["StructInitializer"] = ["StructMemberInitializers*","OP_BRACES_RIGHT","OP_BRACES_LEFT"];
    types_children["StructMemberInitializer"] = ["Identifier","OP_COLON","NonVoidInitializer"];
    types_children["StructMemberInitializers"] = ["StructMemberInitializer*","OP_COMMA*"];
//    types_children["String"] = ["DOUBLE_QUOTED_STRING*"];
    types_children["SwitchStatement"] = ["KW_SWITCH","OP_PAR_RIGHT","OP_PAR_LEFT","Expression","Statement"];
    types_children["Symbol"] = ["OP_DOT","IdentifierOrTemplateChain"];
    types_children["SynchronizedStatement"] = ["OP_PAR_RIGHT","OP_PAR_LEFT","Expression","StatementNoCaseNoDefault","KW_SYNCHRONIZED"];
    types_children["TemplateAliasParameter"] = ["KW_ALIAS","Identifier","Type*","AssignExpression*","OP_COLON","OP_EQ"];
    types_children["TemplateArgument"] = ["Type","AssignExpression"];
    types_children["TemplateArgumentList"] = ["OP_COMMA*","TemplateArgument*"];
    types_children["TemplateArguments"] = ["TemplateArgumentList","TemplateSingleArgument","OP_PAR_RIGHT","OP_PAR_LEFT","OP_NOT"];
//    named_children["TemplateDeclaration"] = ["KW_TEMPLATE","Identifier","TemplateParameters","Constraint","OP_BRACES_RIGHT","OP_BRACES_LEFT","Declaration*","EponymousTemplateDeclaration"];
    types_children["TemplateInstance"] = ["Identifier", "TemplateArguments"];
    types_children["TemplateMixinExpression"] = ["KW_MIXIN","MixinTemplateName","TemplateArguments","Identifier"];
    types_children["TemplateMixinDeclaration"] = ["KW_MIXIN","TemplateDeclaration"];
    stub_children["TemplateParameter"] = ["TemplateAliasParameter","TemplateTupleParameter","TemplateTypeParameter","TemplateThisParameter","TemplateValueParameter"];
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
    types_children["TernaryExpression"] = ["OP_QUEST","OP_COLON","OrOrExpression","Expression","TernaryExpression"];
    types_children["ThrowExpression"] = ["KW_THROW","AssignExpression","OP_SCOLON"];
    types_children["TraitsExpression"] = ["KW___TRAITS","TemplateArgumentList","Identifier","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_children["TryStatement"] = ["KW_TRY","DeclarationOrStatement","Catches","Finally"];
    types_children["Type"] = ["Attribute","Type_2","TypeSuffix*"];
    types_children["Type_2"] = ["Type","TypeofExpression","TypeConstructor","TraitsExpression","MixinExpression","Vector","BuiltinType","IdentifierOrTemplateChain","TypeIdentifierPart","OP_DOT","OP_PAR_RIGHT","OP_PAR_LEFT","KW_SUPER","KW_THIS","KW_CONST","KW_IMMUTABLE","KW_INOUT","KW_SHARED"];
    types_children["TypeConstructor"] = ["KW_CONST","KW_IMMUTABLE","KW_INOUT","KW_SHARED","KW_SCOPE"];
    types_children["TypeConstructors"] = ["TypeConstructor*"];
    types_children["TypeIdentifierPart"] = ["OP_DOT", "IdentifierOrTemplateInstance", "OP_BRACKET_LEFT", "OP_BRACKET_RIGHT", "AssignExpression", "OP_DDOT", "TypeIdentifierPart"];
    types_children["TypeidExpression"] = ["Expression","KW_TYPEID","Type","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_children["TypeofExpression"] = ["Expression","KW_TYPEOF","OP_PAR_RIGHT","OP_PAR_LEFT","KW_RETURN"];
    types_children["TypeSpecialization"] = ["Type","KW___PARAMETERS","KW_STRUCT","KW_UNION","KW_CLASS","KW_INTERFACE","KW_ENUM","KW___VECTOR","KW_FUNCTION","KW_DELEGATE","KW_SUPER","KW_CONST","KW_IMMUTABLE","KW_INOUT","KW_SHARED","KW_RETURN","KW_MODULE","KW_PACKAGE"];
    types_children["TypeSuffix"] = ["AssignExpression*","OP_TRIPLEDOT","KW_FUNCTION","KW_DELEGATE","OP_ASTERISK","MemberFunctionAttribute*","Parameters","OP_BRACKET_LEFT","OP_BRACKET_RIGHT"];
    types_children["UnaryExpression"] = ["PrimaryExpression","FunctionCallExpression","ThrowExpression","UnaryExpression","NewExpression","DeleteExpression","CastExpression","AssertExpression","IdentifierOrTemplateInstance","OP_PAR_RIGHT","OP_PAR_LEFT","SliceExpression","IndexExpression","Type","OP_DOT","OP_AND","OP_ASTERISK","OP_MINUS","OP_MINUS_MINUS","OP_NOT","OP_PLUS","OP_PLUS_PLUS","OP_TILDA"];
//    named_children["UnionDeclaration"] = ["Identifier","TemplateParameters","Constraint","StructBody","OP_SCOLON"];
    stub_children ["Unittest"] = ["Unittest",  "BlockStatement"];
    types_children["VariableDeclaration"] = ["AutoDeclaration",  "OP_SCOLON","Type","Declarator*","OP_COMMA*","OP_EQ","FunctionBody","StorageClass*"];
    types_children["VariadicArgumentsAttribute"] = ["KW_CONST", "KW_IMMUTABLE", "KW_SHARED", "KW_SCOPE", "KW_RETURN"];
    types_children["VariadicArgumentsAttributes"] = ["OP_TRIPLEDOT*", "VariadicArgumentsAttribute*"];
    types_children["Vector"] = ["KW___VECTOR", "Type", "OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_children["VersionCondition"] = ["KW_VERSION","KW_UNITTEST","KW_ASSERT","INTEGER_LITERAL","Identifier","OP_PAR_RIGHT","OP_PAR_LEFT"];
    //types_children["VersionSpecification"] = ["KW_VERSION","OP_EQ","Identifier","OP_PAR_RIGHT","OP_PAR_LEFT","INTEGER_LITERAL","OP_SCOLON"];
    types_children["WhileStatement"] = ["KW_WHILE","IfCondition","DeclarationOrStatement","OP_PAR_RIGHT","OP_PAR_LEFT"];
    types_children["WithStatement"] = ["KW_WITH","Expression","OP_PAR_RIGHT","OP_PAR_LEFT","StatementNoCaseNoDefault"];
    types_children["XorExpression"] = ["XorExpression", "AndExpression", "OP_XOR"];



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
    has_processDeclaration["AutoDeclarationPart"] = false;
    has_processDeclaration["BlockStatement"] = false;
    has_processDeclaration["BreakStatement"] = false;
    has_processDeclaration["BaseClass"] = false;
    has_processDeclaration["BaseClassList"] = false;
    has_processDeclaration["BuiltinType"] = false;
    has_processDeclaration["CaseRangeStatement"] = false;
    has_processDeclaration["CaseStatement"] = false;
    has_processDeclaration["CastExpression"] = false;
    has_processDeclaration["CastQualifier"] = false;
    has_processDeclaration["Catch"] = true;
    has_processDeclaration["Catches"] = false;
    has_processDeclaration["ClassDeclaration"] = false;
    has_processDeclaration["CmpExpression"] = false;
    has_processDeclaration["CompileCondition"] = false;
    has_processDeclaration["ConditionalDeclaration"] = true;
    has_processDeclaration["ConditionalStatement"] = false;
    has_processDeclaration["Constraint"] = false;
    has_processDeclaration["Constructor"] = true;
    has_processDeclaration["ContinueStatement"] = false;
    has_processDeclaration["DebugCondition"] = false;
    has_processDeclaration["DebugSpecification"] = false;
    has_processDeclaration["Declaration"] = true;
    has_processDeclaration["DeclarationOrStatement"] = false;
    has_processDeclaration["DeclarationsAndStatements"] = true;
    has_processDeclaration["Declarator"] = false;
    has_processDeclaration["DefaultStatement"] = false;
    has_processDeclaration["DeleteExpression"] = false;
    has_processDeclaration["DeleteStatement"] = false;
    has_processDeclaration["Deprecated"] = false;
    has_processDeclaration["Destructor"] = false;
    has_processDeclaration["DoStatement"] = false;
    has_processDeclaration["EnumBody"] = false;
//    has_processDeclaration["EnumDeclaration"] = true;
    has_processDeclaration["EnumMember"] = false;
    has_processDeclaration["EnumMemberAttribute"] = false;
    has_processDeclaration["EponymousTemplateDeclaration"] = true;
    has_processDeclaration["EqualExpression"] = false;
    has_processDeclaration["Expression"] = false;
    has_processDeclaration["ExpressionStatement"] = false;
    has_processDeclaration["Finally"] = false;
    has_processDeclaration["FinalSwitchStatement"] = false;
    has_processDeclaration["ForStatement"] = true;
    has_processDeclaration["ForeachStatement"] = true;
    has_processDeclaration["ForeachTypeList"] = false;
    has_processDeclaration["ForeachType"] = false;
    has_processDeclaration["FunctionAttribute"] = false;
    has_processDeclaration["FunctionBody"] = false;
    has_processDeclaration["FunctionCallExpression"] = false;
    has_processDeclaration["FunctionContract"] = false;
    has_processDeclaration["FunctionDeclaration"] = true;
    has_processDeclaration["FunctionLiteralExpression"] = true;
    has_processDeclaration["GotoStatement"] = false;
    has_processDeclaration["IdentifierChain"] = false;
    has_processDeclaration["IdentifierList"] = false;
    has_processDeclaration["IdentifierOrTemplateChain"] = false;
    has_processDeclaration["IdentifierOrTemplateInstance"] = false;
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
    has_processDeclaration["InterfaceDeclaration"] = false;
//    has_processDeclaration["InterfaceOrClass"] = true;
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
    has_processDeclaration["MixinExpression"] = false;
    has_processDeclaration["MixinTemplateDeclaration"] = true;
    has_processDeclaration["MixinTemplateName"] = false;
    //has_processDeclaration["ModuleDeclaration"] = false;
    has_processDeclaration["MulExpression"] = false;
    has_processDeclaration["NamedImportBind"] = false;
    has_processDeclaration["NewAnonClassExpression"] = false;
    has_processDeclaration["NewExpression"] = false;
    has_processDeclaration["NonVoidInitializer"] = false;
    has_processDeclaration["Operands"] = false;
    has_processDeclaration["OrExpression"] = false;
    has_processDeclaration["OrOrExpression"] = false;
    has_processDeclaration["OutStatement"] = true;
    has_processDeclaration["Parameter"] = false;
    has_processDeclaration["ParameterAttribute"] = false;
    has_processDeclaration["Parameters"] = false;
    has_processDeclaration["Postblit"] = false;
    has_processDeclaration["PowExpression"] = false;
    has_processDeclaration["PragmaDeclaration"] = false;
    has_processDeclaration["PragmaExpression"] = false;
    has_processDeclaration["PrimaryExpression"] = false;
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
    has_processDeclaration["Statement"] = false;
    has_processDeclaration["StatementNoCaseNoDefault"] = false;
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
    has_processDeclaration["Symbol"] = false;
    has_processDeclaration["SynchronizedStatement"] = false;
    has_processDeclaration["TemplateAliasParameter"] = false;
    has_processDeclaration["TemplateArgument"] = false;
    has_processDeclaration["TemplateArgumentList"] = false;
    has_processDeclaration["TemplateArguments"] = false;
//    has_processDeclaration["TemplateDeclaration"] = true;
    has_processDeclaration["TemplateInstance"] = false;
    has_processDeclaration["TemplateMixinExpression"] = false;
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
    has_processDeclaration["Type_2"] = false;
    has_processDeclaration["TypeConstructor"] = false;
    has_processDeclaration["TypeConstructors"] = false;
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
}

enum psiDlangImportTemplate = "import io.github.intellij.dlanguage.psi.DLanguage%s;";
enum psiDlangNamedImportTemplate = "import io.github.intellij.dlanguage.psi.named.DLanguage%s;";

static immutable string[] defaultInterfaceImports = [
"import com.intellij.psi.PsiElement;",
"import org.jetbrains.annotations.Nullable;",
];

static immutable string[] defaultImplImports = [
    "import com.intellij.extapi.psi.ASTWrapperPsiElement;",
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


public class %s extends ASTWrapperPsiElement implements
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


public interface %s extends PsiElement `;
    foreach(string interface_; interfaces) {
        base ~= (", " ~ interface_);
    }
    base ~= "{\n";
    return base;
}

bool isToken(string toget) {
    return toget.canFind("OP") || toget.canFind("KW") || toget.canFind("LITERAL") || toget.canFind("STRING") || toget.canFind("ID");
}

string[] getExtraImport(string[][string] children, string key) {
    string[] normalImports;
    if(children[key].canFind!(a => (a.canFind("*"))))
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

string getNonTokenImport(string toget) {
    import std.array;
    string import_;
    if (toget.canFind("*")) {
        toget = toget.replace("*", "");
        if (toget == "Identifier")
            import_ = psiDlangNamedImportTemplate.format(toget);
        else if ((named_children.canFind(toget) && toget !in stub_children))
            import_ = "import io.github.intellij.dlanguage.psi.named.DLanguage%s;".format(toget);
        else
            import_ = psiDlangImportTemplate.format(toget);
    } else {
        if (toget == "Identifier" || (named_children.canFind(toget) && toget !in stub_children))
            import_ = psiDlangNamedImportTemplate.format(toget);
        else
            import_ = psiDlangImportTemplate.format(toget);
    }
    return import_;
}

void getImplImportsElements(string[][string] children, string key, ref string[] staticImports, ref string[] normalImports) {
    import std.array;
    // By default import the base type
    normalImports ~= psiDlangImportTemplate.format(key);
    foreach(string toget; children[key]) {
        if (isToken(toget)) {
            toget = toget.replace("*", "");
            // tokens are imported statically
            staticImports ~= "import static io.github.intellij.dlanguage.psi.DlangTypes.%s;".format(toget);
        } else {
            normalImports ~= getNonTokenImport(toget);
        }
    }
    normalImports ~= getExtraImport(children, key);
}

string formatImplImports(const string[] staticImports, const string[] normalImports) {
    import std.array;
    string importString = staticImports.dup.sort.uniq.join("\n");
    if (staticImports.length > 0)
        importString ~= "\n\n";
    importString ~= normalImports.dup.sort.uniq.join("\n");
    return importString;
}

string getImplImports(string[][string] children, string key) {
    string[] normalImports = defaultImplImports.dup;
    string[] staticImports;
    getImplImportsElements(children, key, staticImports, normalImports);
    return formatImplImports(staticImports, normalImports);
}

string getStubImplImports(string[][string] children, string key) {
    string[] normalImports = defaultStubImplImports.dup;
    string[] staticImports;
    auto stubImport = key == "Unittest" ? "import io.github.intellij.dlanguage.stubs.interfaces.Dlang%sStub;".format(key) : "import io.github.intellij.dlanguage.stubs.Dlang%sStub;".format(key);
    normalImports ~= stubImport;
    getImplImportsElements(children, key, staticImports, normalImports);
    return formatImplImports(staticImports, normalImports);
}

string getterMethod(string toget) {
    import std.string;
    if (toget.canFind("*")) {
        toget = toget.replace("*", "");
        if (isToken(toget)) {
            return `
    @NotNull
    public List<PsiElement> get%ss() {
        return findChildrenByType(%s);
    }
`.format(toget,toget);
        }
        else{
            return `
    @NotNull
    public List<DLanguage%s> get%ss() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguage%s.class);
    }`.format(toget,toget,toget);
        }
    }
    if(isToken(toget)){
        return `
    @Nullable
    public PsiElement get%s() {
        return findChildByType(%s);
    }
`.format(toget,toget);
    }
    else{
        if(toget != "Identifier")
            return `
    @Nullable
    public DLanguage%s get%s() {
        return PsiTreeUtil.getChildOfType(this, DLanguage%s.class);
    }
`.format(toget,toget,toget);
        else
            return `
    @Nullable
    public Dlang%s get%s() {
        return PsiTreeUtil.getChildOfType(this, Dlang%s.class);
    }
`.format(toget,toget,toget);
    }
}

string[] getInterfaceImportElements(string[][string] children, string key) {
    string[] normalImports = defaultInterfaceImports.dup;
    foreach(string toget; children[key]){
        if (isToken(toget))
            continue;
        normalImports ~= getNonTokenImport(toget);
    }
    if(children[key].canFind!(a => (a.canFind("*")))) {
        normalImports ~= ["import java.util.List;", "import org.jetbrains.annotations.NotNull;" ];
    }
    normalImports ~= getExtraImport(children, key);
    return normalImports;
}

string getInterfaceImports(string[][string] children, string key) {
    import std.array;
    auto imports = getInterfaceImportElements(children, key);
    return imports.sort.uniq.join("\n");
}

string getStubInterfaceImports(string[][string] children, string key) {
    import std.array;
    auto imports = getInterfaceImportElements(children, key);
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

string getStubImplImports(string key) {
    import std.array;
    // By default import the base type
    string[] staticImports = string[].init;
    string[] normalImports = defaultStubImplImports.dup;
    normalImports ~= psiDlangImportTemplate.format(key);
    foreach(string toget; types_children[key]){
        if (isToken(toget)) {
            continue;
        }
        normalImports ~= getNonTokenImport(toget);
    }
    normalImports ~= getExtraImport(types_children, key);
    string importString = staticImports.sort.uniq.join("\n");
    if (staticImports.length > 0)
        importString ~= "\n\n";
    importString ~= normalImports.sort.uniq.join("\n");
    return importString;
}

string getterMethodInterface(string toget){
    import std.string;
    if(toget.canFind("*")){
        toget = toget.replace("*","");
        if(isToken(toget)){
            return `
    @NotNull
    List<PsiElement> get%ss();
`.format(toget);
        }
        else{
            return `
    @NotNull
    List<DLanguage%s> get%ss();
`.format(toget,toget);
        }
    }

    if(isToken(toget)){
        return `
    @Nullable
    PsiElement get%s();
`.format(toget);

    }
    else{
        if(toget != "Identifier")
            return `
    @Nullable
    DLanguage%s get%s();
`.format(toget,toget);
        else
            return `
    @Nullable
    Dlang%s get%s();
`.format(toget,toget);
    }
}



int main(string[] args){
    import std.file;
    import std.path;
    if (!exists("impl"))
        mkdir("impl");
    foreach(string key;types_children.keys){
        import std.algorithm;
        import std.string;
        string interfaceClassName = "DLanguage" ~ key;
        string implClassName = "DLanguage" ~ key ~ "Impl";
        string implFile = implFileTemplate.format(getImplImports(types_children, key), implClassName, interfaceClassName, implClassName, key);
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
        string interfaceFile = interfaceFileTemplate.format(getInterfaceImports(types_children, key), interfaceClassName);
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
        string interfaceFile = interfaceFileTemplate(interfaces).format(getStubInterfaceImports(stub_children, key), interfaceClassName);
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
