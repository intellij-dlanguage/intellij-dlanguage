package net.masterthought.dlanguage.parser;

import com.intellij.psi.tree.IElementType;
import net.masterthought.dlanguage.psi.DLanguageTypes;

/**
 * Created by francis on 6/28/2017.
 */
public class ParserPreliminaryJavaWriteUp {

    static IElementType nodeTypeToIElementType(String nodeType) {
        switch (nodeType) {
            case "AliasThisDeclaration":
                return DLanguageTypes.ALIAS_THIS;
            case "AlignAttribute":
                return DLanguageTypes.ALIGN_ATTRIBUTE;
            case "ArgumentList":
                return DLanguageTypes.ARGUMENT_LIST;
//            case "Arguments":
//                return DLanguageTypes.ARGUMEN;
            case "ArrayInitializer":
                return DLanguageTypes.ARRAY_INITIALIZER;
            case "ArrayLiteral":
                return DLanguageTypes.ARRAY_LITERAL;
            case "ArrayMemberInitialization":
                return DLanguageTypes.ARRAY_MEMBER_INITIALIZATION;
            case "AsmInstruction":
                return DLanguageTypes.ASM_INSTRUCTION;
            case "AsmPrimaryExp":
                return DLanguageTypes.ASM_PRIMARY_EXP;
            case "AsmStatement":
                return DLanguageTypes.ASM_STATEMENT;
            case "AsmTypePrefix":
                return DLanguageTypes.ASM_TYPE_PREFIX;
            case "AssocArrayLiteral":
                return DLanguageTypes.ASSOC_ARRAY_LITERAL;
//            case "AtAttribute":
//                return DLanguageTypes.ATTRIBUTE;
            case "Attribute":
                return DLanguageTypes.ATTRIBUTE;
            case "AttributeDeclaration":
                return DLanguageTypes.ATTRIBUTE_SPECIFIER;
            case "AutoDeclaration":
                return DLanguageTypes.AUTO_DECLARATION;
            case "AutoDeclarationPart":
                return DLanguageTypes.AUTO_DECLARATION_X;
            case "BlockStatement":
                return DLanguageTypes.BLOCK_STATEMENT;
            case "BodyStatement":
                return DLanguageTypes.BODY_STATEMENT;
            case "BreakStatement":
                return DLanguageTypes.BREAK_STATEMENT;
//            case "BaseClass":
//                return DLanguageTypes.BASE_CLASS;
            case "BaseClassList":
                return DLanguageTypes.BASE_CLASS_LIST;
//                case "CaseRangeStatement":
//                    return DLanguageTypes.CASERANGE_STATEMENT;
//                case "CaseStatement":
//                    return DLanguageTypes.CASE_STATEMENT;
            case "CastExpression":
                return DLanguageTypes.CAST_EXPRESSION;
//            case "CastQualifier":
//                return DLanguageTypes.CAST_QUALIFIER;
            case "Catch":
                return DLanguageTypes.CATCH;
            case "Catches":
                return DLanguageTypes.CATCHES;
            case "ClassDeclaration":
                return DLanguageTypes.CLASS_DECLARATION;
//            case "CompileCondition":
//                return DLanguageTypes.COMPILE_CONDITION;
//                case "ConditionalDeclaration":
//                    return DLanguageTypes.CONDITIONAL_DECLARATION;
            case "ConditionalStatement":
                return DLanguageTypes.CONDITIONAL_STATEMENT;
            case "Constraint":
                return DLanguageTypes.CONSTRAINT;
            case "Constructor":
                return DLanguageTypes.CONSTRUCTOR;
            case "ContinueStatement":
                return DLanguageTypes.CONTINUE_STATEMENT;
            case "DebugCondition":
                return DLanguageTypes.DEBUG_CONDITION;
            case "DebugSpecification":
                return DLanguageTypes.DEBUG_SPECIFICATION;
            case "Declaration":
                return DLanguageTypes.DECLARATION;
//            case "DeclarationsAndStatements":
//                return DLanguageTypes.DECLARATIONSAND_STATEMENTS;
//            case "DeclarationOrStatement":
//                return DLanguageTypes.DECLARATIONOR_STATEMENT;
            case "Declarator":
                return DLanguageTypes.DECLARATOR;
            case "DefaultStatement":
                return DLanguageTypes.DEFAULT_STATEMENT;
            case "Deprecated":
                return DLanguageTypes.DEPRECATED_ATTRIBUTE;
            case "Destructor":
                return DLanguageTypes.DESTRUCTOR;
            case "DoStatement":
                return DLanguageTypes.DO_STATEMENT;
            case "EnumBody":
                return DLanguageTypes.ENUM_BODY;
            case "AnonymousEnumMember":
                return DLanguageTypes.ANONYMOUS_ENUM_DECLARATION;
            case "AnonymousEnumDeclaration":
                return DLanguageTypes.ANONYMOUS_ENUM_DECLARATION;
            case "EnumDeclaration":
                return DLanguageTypes.ENUM_DECLARATION;
            case "EnumMember":
                return DLanguageTypes.ENUM_MEMBER;
//            case "EponymousTemplateDeclaration":
//                return DLanguageTypes.EPONYMOUSTEMPLATE_DECLARATION;
//                case "ExpressionStatement":
//                    return DLanguageTypes.EXPRESSION_STATEMENT;
            case "FinalSwitchStatement":
                return DLanguageTypes.FINAL_SWITCH_STATEMENT;
            case "Finally":
                return DLanguageTypes.FINALLY_STATEMENT;
            case "ForStatement":
                return DLanguageTypes.FOR_STATEMENT;
            case "ForeachStatement":
                return DLanguageTypes.FOREACH_STATEMENT;
            case "ForeachType":
                return DLanguageTypes.FOREACH_TYPE;
            case "ForeachTypeList":
                return DLanguageTypes.FOREACH_TYPE_LIST;
//                case "FunctionAttribute":
//                    return DLanguageTypes.FUNCTIONATTRIBUTE;
            case "FunctionBody":
                return DLanguageTypes.FUNCTION_BODY;
//                case "FunctionDeclaration":
//                    return DLanguageTypes.FUNCTION_DECLARATION;
            case "GotoStatement":
                return DLanguageTypes.GOTO_STATEMENT;
//            case "IdentifierChain":
//                return DLanguageTypes.IDENTIFIERCHAIN;
            case "IdentifierList":
                return DLanguageTypes.IDENTIFIER_LIST;
//            case "IdentifierOrTemplateChain":
//                return DLanguageTypes.IDENTIFIERORTEMPLATECHAIN;
//            case "IdentifierOrTemplateInstance":
//                return DLanguageTypes.IDENTIFIERORTEMPLATEINSTANCE;
            case "IfStatement":
                return DLanguageTypes.IF_STATEMENT;
            case "ImportBind":
                return DLanguageTypes.IMPORT_BIND;
//                case "ImportBindings":
//                    return DLanguageTypes.IMPORTBINDINGS;
            case "ImportDeclaration":
                return DLanguageTypes.IMPORT_DECLARATION;
            case "InStatement":
                return DLanguageTypes.IN_STATEMENT;
            case "Initializer":
                return DLanguageTypes.INITIALIZER;
            case "InterfaceDeclaration":
                return DLanguageTypes.INTERFACE_DECLARATION;
            case "Invariant":
                return DLanguageTypes.INVARIANT;
            case "KeyValuePair":
                return DLanguageTypes.KEY_VALUE_PAIR;
            case "KeyValuePairs":
                return DLanguageTypes.KEY_VALUE_PAIRS;
            case "LabeledStatement":
                return DLanguageTypes.LABELED_STATEMENT;
            case "LastCatch":
                return DLanguageTypes.LAST_CATCH;
            case "LinkageAttribute":
                return DLanguageTypes.LINKAGE_ATTRIBUTE;
            case "MemberFunctionAttribute":
                return DLanguageTypes.MEMBER_FUNCTION_ATTRIBUTE;
            case "MixinDeclaration":
                return DLanguageTypes.MIXIN_DECLARATION;
            case "MixinTemplateDeclaration":
                return DLanguageTypes.TEMPLATE_MIXIN_DECLARATION;
//            case "MixinTemplateName":
//                return DLanguageTypes.MIXINTEMPLATENAME;
            case "Module":
                return DLanguageTypes.MODULE_FULLY_QUALIFIED_NAME;
            case "ModuleDeclaration":
                return DLanguageTypes.MODULE_DECLARATION;
            case "NonVoidInitializer":
                return DLanguageTypes.NON_VOID_INITIALIZER;
            case "Operands":
                return DLanguageTypes.OPERANDS;
            case "OutStatement":
                return DLanguageTypes.OUT_STATEMENT;
            case "Parameter":
                return DLanguageTypes.PARAMETER;
            case "Parameters":
                return DLanguageTypes.PARAMETERS;
            case "Postblit":
                return DLanguageTypes.POSTBLIT;
            case "PragmaDeclaration":
                return DLanguageTypes.PRAGMA;
            case "Register":
                return DLanguageTypes.REGISTER;
            case "ReturnStatement":
                return DLanguageTypes.RETURN_STATEMENT;
            case "ScopeGuardStatement":
                return DLanguageTypes.SCOPE_GUARD_STATEMENT;
            case "SharedStaticConstructor":
                return DLanguageTypes.SHARED_STATIC_CONSTRUCTOR;
            case "SharedStaticDestructor":
                return DLanguageTypes.SHARED_STATIC_DESTRUCTOR;
            case "SingleImport":
                return DLanguageTypes.IMPORT;
            case "Statement":
                return DLanguageTypes.STATEMENT;
            case "StatementNoCaseNoDefault":
                return DLanguageTypes.STATEMENT_NO_CASE_NO_DEFAULT;
            case "StaticAssertDeclaration":
                return DLanguageTypes.STATIC_ASSERT;
            case "StaticAssertStatement":
                return DLanguageTypes.STATIC_ASSERT;
            case "StaticConstructor":
                return DLanguageTypes.STATIC_CONSTRUCTOR;
            case "StaticDestructor":
                return DLanguageTypes.STATIC_DESTRUCTOR;
            case "StaticIfCondition":
                return DLanguageTypes.STATIC_IF_CONDITION;
            case "StorageClass":
                return DLanguageTypes.STORAGE_CLASS;
//            case "StructBody":
//                return DLanguageTypes.STRUCT_BODY;
            case "StructDeclaration":
                return DLanguageTypes.STRUCT_DECLARATION;
            case "StructInitializer":
                return DLanguageTypes.STRUCT_INITIALIZER;
            case "StructMemberInitializer":
                return DLanguageTypes.STRUCT_MEMBER_INITIALIZER;
            case "StructMemberInitializers":
                return DLanguageTypes.STRUCT_MEMBER_INITIALIZERS;
            case "SwitchStatement":
                return DLanguageTypes.SWITCH_STATEMENT;
            case "Symbol":
                return DLanguageTypes.SYMBOL;
            case "SynchronizedStatement":
                return DLanguageTypes.SYNCHRONIZED_STATEMENT;
            case "TemplateAliasParameter":
                return DLanguageTypes.TEMPLATE_ALIAS_PARAMETER;
            case "TemplateArgument":
                return DLanguageTypes.TEMPLATE_ARGUMENT;
            case "TemplateArgumentList":
                return DLanguageTypes.TEMPLATE_ARGUMENT_LIST;
            case "TemplateArguments":
                return DLanguageTypes.TEMPLATE_ARGUMENTS;
            case "TemplateDeclaration":
                return DLanguageTypes.TEMPLATE_DECLARATION;
            case "TemplateInstance":
                return DLanguageTypes.TEMPLATE_INSTANCE;
            case "TemplateParameter":
                return DLanguageTypes.TEMPLATE_PARAMETER;
            case "TemplateParameterList":
                return DLanguageTypes.TEMPLATE_PARAMETER_LIST;
            case "TemplateParameters":
                return DLanguageTypes.TEMPLATE_PARAMETERS;
            case "TemplateSingleArgument":
                return DLanguageTypes.TEMPLATE_SINGLE_ARGUMENT;
            case "TemplateThisParameter":
                return DLanguageTypes.TEMPLATE_THIS_PARAMETER;
            case "TemplateTupleParameter":
                return DLanguageTypes.TEMPLATE_TUPLE_PARAMETER;
            case "TemplateTypeParameter":
                return DLanguageTypes.TEMPLATE_TYPE_PARAMETER;
            case "TemplateValueParameter":
                return DLanguageTypes.TEMPLATE_VALUE_PARAMETER;
//            case "TemplateValueParameterDefault":
//                return DLanguageTypes.TEMPLATE_VALUE_PARAMETER_DEFAULT;
            case "ThrowStatement":
                return DLanguageTypes.THROW_STATEMENT;
            case "TryStatement":
                return DLanguageTypes.TRY_STATEMENT;
            case "Type":
                return DLanguageTypes.TYPE;
//            case "Type2":
//                return DLanguageTypes.TYPE2;
            case "TypeSpecialization":
                return DLanguageTypes.TYPE_SPECIALIZATION;
//            case "TypeSuffix":
//                return DLanguageTypes.TYPE_SUFFIX;
            case "UnionDeclaration":
                return DLanguageTypes.UNION_DECLARATION;
            case "Unittest":
                return DLanguageTypes.UNIT_TESTING;
//            case "VariableDeclaration":
//                return DLanguageTypes.VARIABLE_DECLARATION;
            case "Vector":
                return DLanguageTypes.TYPE_VECTOR;
            case "VersionCondition":
                return DLanguageTypes.VERSION_CONDITION;
            case "VersionSpecification":
                return DLanguageTypes.VERSION_SPECIFICATION;
            case "WhileStatement":
                return DLanguageTypes.WHILE_STATEMENT;
            case "WithStatement":
                return DLanguageTypes.WITH_STATEMENT;
            case "AddExpression":
                return DLanguageTypes.ADD_EXPRESSION_;
//            case "AndAndExpression":
//                return DLanguageTypes.AND_AND_EXPRESSION_;
//            case "AndExpression":
//                return DLanguageTypes.AND_EXPRESSION_;
            case "AsmAddExp":
                return DLanguageTypes.ASM_ADD_EXP;
            case "AsmAndExp":
                return DLanguageTypes.ASM_AND_EXP;
            case "AsmBrExp":
                return DLanguageTypes.ASM_BR_EXP;
            case "AsmExp":
                return DLanguageTypes.ASM_EXP;
            case "AsmEqualExp":
                return DLanguageTypes.ASM_EQUAL_EXP;
            case "AsmLogAndExp":
                return DLanguageTypes.ASM_LOG_AND_EXP;
            case "AsmLogOrExp":
                return DLanguageTypes.ASM_LOG_OR_EXP;
            case "AsmMulExp":
                return DLanguageTypes.ASM_MUL_EXP;
            case "AsmOrExp":
                return DLanguageTypes.ASM_OR_EXP;
            case "AsmRelExp":
                return DLanguageTypes.ASM_REL_EXP;
            case "AsmUnaExp":
                return DLanguageTypes.ASM_UNA_EXP;
            case "AsmShiftExp":
                return DLanguageTypes.ASM_SHIFT_EXP;
            case "AsmXorExp":
                return DLanguageTypes.ASM_XOR_EXP;
            case "AssertExpression":
                return DLanguageTypes.ASSERT_EXPRESSION;
            case "AssignExpression":
                return DLanguageTypes.ASSIGN_EXPRESSION;
//            case "CmpExpression":
//                return DLanguageTypes.CMP_EXPRESSION;
            case "DeleteExpression":
                return DLanguageTypes.DELETE_EXPRESSION;
//                case "EqualExpression":
//                    return DLanguageTypes.EQUALEXPRESSION;
//            case "Expression":
//                return DLanguageTypes.EXPRESSION;
//            case "FunctionCallExpression":
//                return DLanguageTypes.FUNCTIONCALLEXPRESSION;
            case "FunctionLiteralExpression":
                return DLanguageTypes.FUNCTION_LITERAL;
            case "IdentityExpression":
                return DLanguageTypes.IDENTITY_EXPRESSION;
            case "ImportExpression":
                return DLanguageTypes.IMPORT_EXPRESSION;
            case "IndexExpression":
                return DLanguageTypes.INDEX_EXPRESSION;
//                case "InExpression":
//                    return DLanguageTypes.INEXPRESSION;
            case "IsExpression":
                return DLanguageTypes.IS_EXPRESSION;
            case "MixinExpression":
                return DLanguageTypes.MIXIN_EXPRESSION;
            case "MulExpression":
                return DLanguageTypes.MUL_EXPRESSION_;
            case "NewAnonClassExpression":
                return DLanguageTypes.NEW_ANON_CLASS_EXPRESSION;
            case "NewExpression":
                return DLanguageTypes.NEW_EXPRESSION;
            case "OrExpression":
//                return DLanguageTypes.OR_EXPRESSION_;
//            case "OrOrExpression":
//                return DLanguageTypes.OROR_EXPRESSION_;
//            case "PowExpression":
                return DLanguageTypes.POW_EXPRESSION_;
//            case "PragmaExpression":
//                return DLanguageTypes.PRAGMA_STATEMENT;
            case "PrimaryExpression":
                return DLanguageTypes.PRIMARY_EXPRESSION;
            case "RelExpression":
                return DLanguageTypes.REL_EXPRESSION;
            case "ShiftExpression":
                return DLanguageTypes.SHIFT_EXPRESSION_;
//            case "Index":
//                return DLanguageTypes.INDEX;
            case "TemplateMixinExpression":
                return DLanguageTypes.TEMPLATE_MIXIN;
//            case "TernaryExpression":
//                return DLanguageTypes.TERNARY_EXPRESSION;
            case "TraitsExpression":
                return DLanguageTypes.TRAITS_EXPRESSION;
            case "TypeidExpression":
                return DLanguageTypes.TYPEID_EXPRESSION;
            case "TypeofExpression":
                return DLanguageTypes.TYPEOF;
//            case "UnaryExpression":
//                return DLanguageTypes.UNARY_EXPRESSION;
            case "XorExpression":
                return DLanguageTypes.XOR_EXPRESSION_;
//            case "AliasInitializer":
//                return DLanguageTypes.ALIAS__INITIALIZER;
            default:
                throw new IllegalArgumentException("unrecognized thing to parse:" + nodeType);
        }
    }

}
