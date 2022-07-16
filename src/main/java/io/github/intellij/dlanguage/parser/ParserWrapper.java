package io.github.intellij.dlanguage.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LightPsiParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import io.github.intellij.dlanguage.psi.DlangTypes;
import org.jetbrains.annotations.NotNull;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;

/**
 * Created by francis on 7/2/2017.
 */
public class ParserWrapper implements LightPsiParser, PsiParser {

    @Override
    public void parseLight(final IElementType type, PsiBuilder builder) {
        boolean result;
        builder = adapt_builder_(type, builder, this, null);
        final PsiBuilder.Marker marker = enter_section_(builder, 0, _COLLAPSE_, null);
        final DLangParser parser = new DLangParser(builder);
        if (type == DlangTypes.FUNCTION_DECLARATION) {
            result = parser.parseFunctionDeclaration();
        } else if (type == DlangTypes.INTERFACE_OR_CLASS) {
            result = parser.parseInterfaceOrClass();
        } else if (type == DlangTypes.TEMPLATE_DECLARATION) {
            result = parser.parseTemplateDeclaration();
        } else if (type == DlangTypes.CONSTRUCTOR) {
            result = parser.parseConstructor();
        } else if (type == DlangTypes.DESTRUCTOR) {
            result = parser.parseDestructor();
        } else if (type == DlangTypes.STRUCT_DECLARATION) {
            result = parser.parseStructDeclaration();
        } else if (type == DlangTypes.ALIAS_INITIALIZER) {
            result = parser.parseAliasInitializer();
        } else if (type == DlangTypes.MODULE_DECLARATION) {
            result = parser.parseModuleDeclaration();
        } else if (type == DlangTypes.DECLARATOR) {
            result = parser.parseDeclarator();
        } else if (type == DlangTypes.LABELED_STATEMENT) {
            result = parser.parseLabeledStatement();
        } else if (type == DlangTypes.SHARED_STATIC_CONSTRUCTOR) {
            result = parser.parseSharedStaticConstructor();
        } else if (type == DlangTypes.SHARED_STATIC_DESTRUCTOR) {
            result = parser.parseSharedStaticDestructor();
        } else if (type == DlangTypes.STATIC_CONSTRUCTOR) {
            result = parser.parseStaticConstructor();
        } else if (type == DlangTypes.STATIC_DESTRUCTOR) {
            result = parser.parseStaticDestructor();
        } else if (type == DlangTypes.AUTO_DECLARATION_PART) {
            result = parser.parseAutoDeclarationPart();
        } else if (type == DlangTypes.ENUM_DECLARATION) {
            result = parser.parseEnumDeclaration();
        } else if (type == DlangTypes.UNION_DECLARATION) {
            result = parser.parseUnionDeclaration();
        } else if (type == DlangTypes.SINGLE_IMPORT) {
            result = parser.parseSingleImport();
        } else if (type == DlangTypes.UNITTEST) {
            result = parser.parseUnittest();
        } else if (type == DlangTypes.CATCH) {
            result = parser.parseCatch();
        } else if (type == DlangTypes.IF_CONDITION) {
            result = parser.parseIfCondition();
        } else if (type == DlangTypes.FOREACH_TYPE) {
            result = parser.parseForeachType();
        } else if (type == DlangTypes.PARAMETER) {
            result = parser.parseParameter();
        } else if (type == DlangTypes.TEMPLATE_PARAMETER) {
            result = parser.parseTemplateParameter();
        } else if (type == DlangTypes.EPONYMOUS_TEMPLATE_DECLARATION) {
            result = parser.parseEponymousTemplateDeclaration();
        } else if (type == DlangTypes.ENUM_MEMBER) {
            result = parser.parseEnumMember();
        } else if (type == DlangTypes.ENUM_MEMBER_ATTRIBUTE) {
            result = parser.parseEnumMemberAttribute();
        } else if (type == DlangTypes.ALIAS_DECLARATION) {
            result = parser.parseAliasDeclaration();
        } else if (type == DlangTypes.ALIAS_THIS_DECLARATION) {
            result = parser.parseAliasThisDeclaration();
        } else if (type == DlangTypes.ALIGN_ATTRIBUTE) {
            result = parser.parseAlignAttribute();
        } else if (type == DlangTypes.AND_AND_EXPRESSION) {
            result = parser.parseAndAndExpression();
        } else if (type == DlangTypes.AND_EXPRESSION) {
            result = parser.parseAndExpression();
        } else if (type == DlangTypes.ANONYMOUS_ENUM_DECLARATION) {
            result = parser.parseAnonymousEnumDeclaration();
        } else if (type == DlangTypes.ARGUMENT_LIST) {
            result = false;
        } else if (type == DlangTypes.ARGUMENTS) {
            result = parser.parseArguments();
        } else if (type == DlangTypes.ARRAY_INITIALIZER) {
            result = parser.parseArrayInitializer();
        } else if (type == DlangTypes.ARRAY_LITERAL) {
            result = parser.parseArrayLiteral();
        } else if (type == DlangTypes.ARRAY_MEMBER_INITIALIZATION) {
            result = parser.parseArrayMemberInitialization();
        } else if (type == DlangTypes.ASM_ADD_EXP) {
            result = parser.parseAsmAddExp();
        } else if (type == DlangTypes.ASM_AND_EXP) {
            result = parser.parseAsmAndExp();
        } else if (type == DlangTypes.ASM_BR_EXP) {
            result = parser.parseAsmBrExp();
        } else if (type == DlangTypes.ASM_EQUAL_EXP) {
            result = parser.parseAsmEqualExp();
        } else if (type == DlangTypes.ASM_EXP) {
            result = parser.parseAsmExp();
        } else if (type == DlangTypes.ASM_INSTRUCTION) {
            result = parser.parseAsmInstruction();
        } else if (type == DlangTypes.ASM_LOG_AND_EXP) {
            result = parser.parseAsmLogAndExp();
        } else if (type == DlangTypes.ASM_LOG_OR_EXP) {
            result = parser.parseAsmLogOrExp();
        } else if (type == DlangTypes.ASM_MUL_EXP) {
            result = parser.parseAsmMulExp();
        } else if (type == DlangTypes.ASM_OR_EXP) {
            result = parser.parseAsmOrExp();
        } else if (type == DlangTypes.ASM_PRIMARY_EXP) {
            result = parser.parseAsmPrimaryExp();
        } else if (type == DlangTypes.ASM_REL_EXP) {
            result = parser.parseAsmRelExp();
        } else if (type == DlangTypes.ASM_SHIFT_EXP) {
            result = parser.parseAsmShiftExp();
        } else if (type == DlangTypes.ASM_STATEMENT) {
            result = parser.parseAsmStatement();
        } else if (type == DlangTypes.ASM_TYPE_PREFIX) {
            result = parser.parseAsmTypePrefix();
        } else if (type == DlangTypes.ASM_UNA_EXP) {
            result = parser.parseAsmUnaExp();
        } else if (type == DlangTypes.ASM_XOR_EXP) {
            result = parser.parseAsmXorExp();
        } else if (type == DlangTypes.ASSERT_ARGUMENTS) {
            result = parser.parseAssertArguments();
        } else if (type == DlangTypes.ASSERT_EXPRESSION) {
            result = parser.parseAssertExpression();
        } else if (type == DlangTypes.ASSIGN_EXPRESSION) {
            result = parser.parseAssignExpression();
        } else if (type == DlangTypes.ASSOC_ARRAY_LITERAL) {
            result = parser.parseAssocArrayLiteral();
        } else if (type == DlangTypes.AT_ATTRIBUTE) {
            result = parser.parseAtAttribute();
        } else if (type == DlangTypes.ATTRIBUTE) {
            result = parser.parseAttribute();
        } else if (type == DlangTypes.ATTRIBUTE_DECLARATION) {
            result = parser.parseAttributeDeclaration();
        } else if (type == DlangTypes.AUTO_DECLARATION) {
            result = parser.parseAutoDeclaration();
        } else if (type == DlangTypes.BASE_CLASS) {
            result = parser.parseBaseClass();
        } else if (type == DlangTypes.BASE_CLASS_LIST) {
            result = parser.parseBaseClassList();
        } else if (type == DlangTypes.BLOCK_STATEMENT) {
            result = parser.parseBlockStatement();
        } else if (type == DlangTypes.BREAK_STATEMENT) {
            result = parser.parseBreakStatement();
        } else if (type == DlangTypes.CASE_RANGE_STATEMENT) {
            result = parser.parseCaseRangeStatement();
        } else if (type == DlangTypes.CASE_STATEMENT) {
            result = parser.parseCaseStatement();
        } else if (type == DlangTypes.CAST_EXPRESSION) {
            result = parser.parseCastExpression();
        } else if (type == DlangTypes.CAST_QUALIFIER) {
            result = parser.parseCastQualifier();
        } else if (type == DlangTypes.CATCHES) {
            result = parser.parseCatches();
        } else if (type == DlangTypes.CMP_EXPRESSION) {
            result = parser.parseCmpExpression();
        } else if (type == DlangTypes.COMPILE_CONDITION) {
            result = parser.parseCompileCondition();
        } else if (type == DlangTypes.CONDITIONAL_DECLARATION) {
            result = false;
        } else if (type == DlangTypes.CONDITIONAL_STATEMENT) {
            result = parser.parseConditionalStatement();
        } else if (type == DlangTypes.CONSTRAINT) {
            result = parser.parseConstraint();
        } else if (type == DlangTypes.CONTINUE_STATEMENT) {
            result = parser.parseContinueStatement();
        } else if (type == DlangTypes.CLASS_DECLARATION) {
            result = parser.parseClassDeclaration();
        } else if (type == DlangTypes.DEBUG_CONDITION) {
            result = parser.parseDebugCondition();
        } else if (type == DlangTypes.DEBUG_SPECIFICATION) {
            result = parser.parseDebugSpecification();
        } else if (type == DlangTypes.DECLARATION) {
            result = parser.parseDeclaration();
        } else if (type == DlangTypes.DECLARATION_OR_STATEMENT) {
            result = parser.parseDeclarationOrStatement();
        } else if (type == DlangTypes.DECLARATIONS_AND_STATEMENTS) {
            result = false;
        } else if (type == DlangTypes.DECLARATOR_IDENTIFIER_LIST) {
            result = parser.parseDeclaratorIdentifierList();
        } else if (type == DlangTypes.DEFAULT_STATEMENT) {
            result = parser.parseDefaultStatement();
        } else if (type == DlangTypes.DELETE_EXPRESSION) {
            result = parser.parseDeleteExpression();
        } else if (type == DlangTypes.DEPRECATED) {
            result = parser.parseDeprecated();
        } else if (type == DlangTypes.DO_STATEMENT) {
            result = parser.parseDoStatement();
        } else if (type == DlangTypes.ENUM_BODY) {
            result = parser.parseEnumBody();
        } else if (type == DlangTypes.EQUAL_EXPRESSION) {
            result = parser.parseEqualExpression();
        } else if (type == DlangTypes.EXPRESSION) {
            result = parser.parseExpression();
        } else if (type == DlangTypes.EXPRESSION_STATEMENT) {
            result = false;
        } else if (type == DlangTypes.FINAL_SWITCH_STATEMENT) {
            result = parser.parseFinalSwitchStatement();
        } else if (type == DlangTypes.FINALLY) {
            result = parser.parseFinally();
        } else if (type == DlangTypes.FOR_STATEMENT) {
            result = parser.parseForStatement();
        } else if (type == DlangTypes.FOREACH_STATEMENT) {
            result = parser.parseForeachStatement();
        } else if (type == DlangTypes.FOREACH_TYPE_LIST) {
            result = false;
        } else if (type == DlangTypes.FUNCTION_ATTRIBUTE) {
            result = false;
        } else if (type == DlangTypes.FUNCTION_BODY) {
            result = parser.parseFunctionBody();
        } else if (type == DlangTypes.FUNCTION_CALL_EXPRESSION) {
            result = parser.parseFunctionCallExpression();
        } else if (type == DlangTypes.FUNCTION_CONTRACT) {
            result = parser.parseFunctionContract();
        } else if (type == DlangTypes.FUNCTION_LITERAL_EXPRESSION) {
            result = parser.parseFunctionLiteralExpression();
        } else if (type == DlangTypes.GOTO_STATEMENT) {
            result = parser.parseGotoStatement();
        } else if (type == DlangTypes.IDENTIFIER_CHAIN) {
            result = parser.parseIdentifierChain();
        } else if (type == DlangTypes.IDENTIFIER_OR_TEMPLATE_CHAIN) {
            result = parser.parseIdentifierOrTemplateChain();
        } else if (type == DlangTypes.IDENTIFIER_OR_TEMPLATE_INSTANCE) {
            result = parser.parseIdentifierOrTemplateInstance();
        } else if (type == DlangTypes.IDENTITY_EXPRESSION) {
            result = parser.parseIdentityExpression();
        } else if (type == DlangTypes.IF_STATEMENT) {
            result = parser.parseIfStatement();
        } else if (type == DlangTypes.IMPORT_BIND) {
            result = parser.parseImportBind();
        } else if (type == DlangTypes.IMPORT_BINDINGS) {
            result = false;
        } else if (type == DlangTypes.IMPORT_DECLARATION) {
            result = parser.parseImportDeclaration();
        } else if (type == DlangTypes.IMPORT_EXPRESSION) {
            result = parser.parseImportExpression();
        } else if (type == DlangTypes.IN_CONTRACT_EXPRESSION) {
            result = parser.parseInContractExpression();
        } else if (type == DlangTypes.IN_EXPRESSION) {
            result = parser.parseInExpression();
        } else if (type == DlangTypes.IN_OUT_CONTRACT_EXPRESSION) {
            result = parser.parseInOutContractExpression();
        } else if (type == DlangTypes.IN_OUT_STATEMENT) {
            result = parser.parseInOutStatement();
        } else if (type == DlangTypes.IN_STATEMENT) {
            result = parser.parseInStatement();
        } else if (type == DlangTypes.INDEX) {
            result = parser.parseIndex();
        } else if (type == DlangTypes.INDEX_EXPRESSION) {
            result = parser.parseIndexExpression();
        } else if (type == DlangTypes.INITIALIZER) {
            result = parser.parseInitializer();
        } else if (type == DlangTypes.INTERFACE_DECLARATION) {
            result = parser.parseInterfaceDeclaration();
        } else if (type == DlangTypes.INVARIANT) {
            result = parser.parseInvariant();
        } else if (type == DlangTypes.IS_EXPRESSION) {
            result = parser.parseIsExpression();
        } else if (type == DlangTypes.KEY_VALUE_PAIR) {
            result = parser.parseKeyValuePair();
        } else if (type == DlangTypes.KEY_VALUE_PAIRS) {
            result = parser.parseKeyValuePairs();
        } else if (type == DlangTypes.LAST_CATCH) {
            result = parser.parseLastCatch();
        } else if (type == DlangTypes.LINKAGE_ATTRIBUTE) {
            result = parser.parseLinkageAttribute();
        } else if (type == DlangTypes.MEMBER_FUNCTION_ATTRIBUTE) {
            result = parser.parseMemberFunctionAttribute();
        } else if (type == DlangTypes.MIXIN_DECLARATION) {
            result = parser.parseMixinDeclaration();
        } else if (type == DlangTypes.MIXIN_EXPRESSION) {
            result = parser.parseMixinExpression();
        } else if (type == DlangTypes.MISSING_FUNCTION_BODY) {
            result = parser.parseMissingFunctionBody();
        } else if (type == DlangTypes.MIXIN_TEMPLATE_DECLARATION) {
            result = parser.parseMixinTemplateDeclaration();
        } else if (type == DlangTypes.MIXIN_TEMPLATE_NAME) {
            result = parser.parseMixinTemplateName();
        } else if (type == DlangTypes.MUL_EXPRESSION) {
            result = parser.parseMulExpression();
        } else if (type == DlangTypes.NAMESPACE_LIST) {
            result = parser.parseNamespaceList();
        } else if (type == DlangTypes.NEW_ANON_CLASS_EXPRESSION) {
            result = parser.parseNewAnonClassExpression();
        } else if (type == DlangTypes.NEW_EXPRESSION) {
            result = parser.parseNewExpression();
        } else if (type == DlangTypes.NON_VOID_INITIALIZER) {
            result = parser.parseNonVoidInitializer();
        } else if (type == DlangTypes.OPERANDS) {
            result = parser.parseOperands();
        } else if (type == DlangTypes.OR_EXPRESSION) {
            result = parser.parseOrExpression();
        } else if (type == DlangTypes.OR_OR_EXPRESSION) {
            result = parser.parseOrOrExpression();
        } else if (type == DlangTypes.OUT_CONTRACT_EXPRESSION) {
            result = parser.parseOutContractExpression();
        } else if (type == DlangTypes.OUT_STATEMENT) {
            result = parser.parseOutStatement();
        } else if (type == DlangTypes.PARAMETERS) {
            result = parser.parseParameters();
        } else if (type == DlangTypes.POSTBLIT) {
            result = parser.parsePostblit();
        } else if (type == DlangTypes.POW_EXPRESSION) {
            result = parser.parsePowExpression();
        } else if (type == DlangTypes.PRAGMA_DECLARATION) {
            result = parser.parsePragmaDeclaration();
        } else if (type == DlangTypes.PRAGMA_EXPRESSION) {
            result = parser.parsePragmaExpression();
        } else if (type == DlangTypes.PRAGMA_STATEMENT) {
            result = parser.parsePragmaStatement();
        } else if (type == DlangTypes.PRIMARY_EXPRESSION) {
            result = parser.parsePrimaryExpression();
        } else if (type == DlangTypes.REGISTER) {
            result = parser.parseRegister();
        } else if (type == DlangTypes.REL_EXPRESSION) {
            result = parser.parseRelExpression();
        } else if (type == DlangTypes.RETURN_STATEMENT) {
            result = parser.parseReturnStatement();
        } else if (type == DlangTypes.SCOPE_GUARD_STATEMENT) {
            result = parser.parseScopeGuardStatement();
        } else if (type == DlangTypes.SHIFT_EXPRESSION) {
            result = parser.parseShiftExpression();
        } else if (type == DlangTypes.SHORTENED_FUNCTION_BODY) {
            result = parser.parseShortenedFunctionBody();
        } else if (type == DlangTypes.SPECIFIED_FUNCTION_BODY) {
            result = parser.parseSpecifiedFunctionBody();
        } else if (type == DlangTypes.STATEMENT) {
            result = parser.parseStatement();
        } else if (type == DlangTypes.STATEMENT_NO_CASE_NO_DEFAULT) {
            result = parser.parseStatementNoCaseNoDefault();
        } else if (type == DlangTypes.STATIC_ASSERT_DECLARATION) {
            result = parser.parseStaticAssertDeclaration();
        } else if (type == DlangTypes.STATIC_ASSERT_STATEMENT) {
            result = parser.parseStaticAssertStatement();
        } else if (type == DlangTypes.STATIC_IF_CONDITION) {
            result = parser.parseStaticIfCondition();
        } else if (type == DlangTypes.STORAGE_CLASS) {
            result = parser.parseStorageClass();
        } else if (type == DlangTypes.STRUCT_BODY) {
            result = parser.parseStructBody();
        } else if (type == DlangTypes.STRUCT_INITIALIZER) {
            result = parser.parseStructInitializer();
        } else if (type == DlangTypes.STRUCT_MEMBER_INITIALIZER) {
            result = parser.parseStructMemberInitializer();
        } else if (type == DlangTypes.STRUCT_MEMBER_INITIALIZERS) {
            result = parser.parseStructMemberInitializers();
        } else if (type == DlangTypes.SWITCH_STATEMENT) {
            result = parser.parseSwitchStatement();
        } else if (type == DlangTypes.SYMBOL) {
            result = parser.parseSymbol();
        } else if (type == DlangTypes.SYNCHRONIZED_STATEMENT) {
            result = parser.parseSynchronizedStatement();
        } else if (type == DlangTypes.TEMPLATE_ALIAS_PARAMETER) {
            result = parser.parseTemplateAliasParameter();
        } else if (type == DlangTypes.TEMPLATE_ARGUMENT) {
            result = parser.parseTemplateArgument();
        } else if (type == DlangTypes.TEMPLATE_ARGUMENT_LIST) {
            result = parser.parseTemplateArgumentList();
        } else if (type == DlangTypes.TEMPLATE_ARGUMENTS) {
            result = parser.parseTemplateArguments();
        } else if (type == DlangTypes.TEMPLATE_INSTANCE) {
            result = parser.parseTemplateInstance();
        } else if (type == DlangTypes.TEMPLATE_MIXIN_EXPRESSION) {
            result = parser.parseTemplateMixinExpression();
        } else if (type == DlangTypes.TEMPLATE_PARAMETER_LIST) {
            result = parser.parseTemplateParameterList();
        } else if (type == DlangTypes.TEMPLATE_PARAMETERS) {
            result = parser.parseTemplateParameters();
        } else if (type == DlangTypes.TEMPLATE_SINGLE_ARGUMENT) {
            result = parser.parseTemplateSingleArgument();
        } else if (type == DlangTypes.TEMPLATE_THIS_PARAMETER) {
            result = parser.parseTemplateThisParameter();
        } else if (type == DlangTypes.TEMPLATE_TUPLE_PARAMETER) {
            result = parser.parseTemplateTupleParameter();
        } else if (type == DlangTypes.TEMPLATE_TYPE_PARAMETER) {
            result = parser.parseTemplateTypeParameter();
        } else if (type == DlangTypes.TEMPLATE_VALUE_PARAMETER) {
            result = parser.parseTemplateValueParameter();
        } else if (type == DlangTypes.TEMPLATE_VALUE_PARAMETER_DEFAULT) {
            result = parser.parseTemplateValueParameterDefault();
        } else if (type == DlangTypes.TERNARY_EXPRESSION) {
            result = parser.parseTernaryExpression();
        } else if (type == DlangTypes.THROW_EXPRESSION) {
            result = parser.parseThrowExpression();
        } else if (type == DlangTypes.TRAITS_EXPRESSION) {
            result = parser.parseTraitsExpression();
        } else if (type == DlangTypes.TRY_STATEMENT) {
            result = parser.parseTryStatement();
        } else if (type == DlangTypes.TYPE) {
            result = false;
        } else if (type == DlangTypes.TYPE_2) {
            result = parser.parseType2();
        } else if (type == DlangTypes.TYPE_IDENTIFIER_PART) {
            result = parser.parseTypeIdentifierPart();
        } else if (type == DlangTypes.TYPE_SPECIALIZATION) {
            result = parser.parseTypeSpecialization();
        } else if (type == DlangTypes.TYPE_SUFFIX) {
            result = parser.parseTypeSuffix();
        } else if (type == DlangTypes.TYPEID_EXPRESSION) {
            result = parser.parseTypeidExpression();
        } else if (type == DlangTypes.TYPEOF_EXPRESSION) {
            result = parser.parseTypeofExpression();
        } else if (type == DlangTypes.UNARY_EXPRESSION) {
            result = parser.parseUnaryExpression();
        } else if (type == DlangTypes.VARIABLE_DECLARATION) {
            result = parser.parseVariableDeclaration();
        } else if (type == DlangTypes.VARIADIC_ARGUMENTS_ATTRIBUTE) {
            result = parser.parseVariadicArgumentsAttribute();
        } else if (type == DlangTypes.VARIADIC_ARGUMENTS_ATTRIBUTES) {
            result = parser.parseVariadicArgumentsAttributes();
        } else if (type == DlangTypes.VECTOR) {
            result = parser.parseVector();
        } else if (type == DlangTypes.VERSION_CONDITION) {
            result = parser.parseVersionCondition();
        } else if (type == DlangTypes.VERSION_SPECIFICATION) {
            result = parser.parseVersionSpecification();
        } else if (type == DlangTypes.WHILE_STATEMENT) {
            result = parser.parseWhileStatement();
        } else if (type == DlangTypes.WITH_STATEMENT) {
            result = parser.parseWithStatement();
        } else if (type == DlangTypes.XOR_EXPRESSION) {
            result = parser.parseXorExpression();
        } else if (type == DlangTypes.ADD_EXPRESSION) {
            result = parser.parseAddExpression();
        } else {
            result = parser.parseModule();
        }
        exit_section_(builder, 0, marker, type, result, true, TRUE_CONDITION);
    }


    @NotNull
    @Override
    public ASTNode parse(@NotNull final IElementType root, @NotNull final PsiBuilder builder) {
        parseLight(root, builder);
        return builder.getTreeBuilt();
    }
}
