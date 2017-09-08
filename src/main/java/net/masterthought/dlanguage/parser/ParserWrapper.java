package net.masterthought.dlanguage.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LightPsiParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 7/2/2017.
 */
public class ParserWrapper implements LightPsiParser, PsiParser {

    @Override
    public void parseLight(final IElementType t, PsiBuilder b) {
        boolean r = false;
        b = adapt_builder_(t, b, this, null);
        final PsiBuilder.Marker m = enter_section_(b, 0, _COLLAPSE_, null);
        final DLangParser parser = new DLangParser(b);
        if (t == FUNCTION_DECLARATION) {
            r = parser.parseFunctionDeclaration();
        } else if (t == INTERFACE_OR_CLASS) {
            r = parser.parseInterfaceOrClass();
        } else if (t == TEMPLATE_DECLARATION) {
            r = parser.parseTemplateDeclaration();
        } else if (t == CONSTRUCTOR) {
            r = parser.parseConstructor();
        } else if (t == DESTRUCTOR) {
            r = parser.parseDestructor();
        } else if (t == STRUCT_DECLARATION) {
            r = parser.parseStructDeclaration();
        } else if (t == ALIAS_INITIALIZER) {
            r = parser.parseAliasInitializer();
        } else if (t == MODULE_DECLARATION) {
            r = parser.parseModuleDeclaration();
        } else if (t == DECLARATOR) {
            r = parser.parseDeclarator();
        } else if (t == LABELED_STATEMENT) {
            r = parser.parseLabeledStatement();
        } else if (t == SHARED_STATIC_CONSTRUCTOR) {
            r = parser.parseSharedStaticConstructor();
        } else if (t == SHARED_STATIC_DESTRUCTOR) {
            r = parser.parseSharedStaticDestructor();
        } else if (t == STATIC_CONSTRUCTOR) {
            r = parser.parseStaticConstructor();
        } else if (t == STATIC_DESTRUCTOR) {
            r = parser.parseStaticDestructor();
        } else if (t == AUTO_DECLARATION_PART) {
            r = parser.parseAutoDeclarationPart();
        } else if (t == ENUM_DECLARATION) {
            r = parser.parseEnumDeclaration();
        } else if (t == UNION_DECLARATION) {
            r = parser.parseUnionDeclaration();
        } else if (t == SINGLE_IMPORT) {
            r = parser.parseSingleImport();
        } else if (t == UNITTEST) {
            r = parser.parseUnittest();
        } else if (t == CATCH) {
            r = parser.parseCatch();
        } else if (t == IF_CONDITION) {
            r = false;
        } else if (t == FOREACH_TYPE) {
            r = parser.parseForeachType();
        } else if (t == PARAMETER) {
            r = parser.parseParameter();
        } else if (t == TEMPLATE_PARAMETER) {
            r = parser.parseTemplateParameter();
        } else if (t == EPONYMOUS_TEMPLATE_DECLARATION) {
            r = parser.parseEponymousTemplateDeclaration();
        } else if (t == ENUM_MEMBER) {
            r = parser.parseEnumMember();
        } else if (t == ALIAS_DECLARATION) {
            r = parser.parseAliasDeclaration();
        } else if (t == ALIAS_THIS_DECLARATION) {
            r = parser.parseAliasThisDeclaration();
        } else if (t == ALIGN_ATTRIBUTE) {
            r = parser.parseAlignAttribute();
        } else if (t == AND_AND_EXPRESSION) {
            r = parser.parseAndAndExpression();
        } else if (t == AND_EXPRESSION) {
            r = parser.parseAndExpression();
        } else if (t == ANONYMOUS_ENUM_DECLARATION) {
            r = parser.parseAnonymousEnumDeclaration();
        } else if (t == ARGUMENT_LIST) {
            r = false;
        } else if (t == ARGUMENTS) {
            r = parser.parseArguments();
        } else if (t == ARRAY_INITIALIZER) {
            r = parser.parseArrayInitializer();
        } else if (t == ARRAY_LITERAL) {
            r = parser.parseArrayLiteral();
        } else if (t == ARRAY_MEMBER_INITIALIZATION) {
            r = parser.parseArrayMemberInitialization();
        } else if (t == ASM_ADD_EXP) {
            r = parser.parseAsmAddExp();
        } else if (t == ASM_AND_EXP) {
            r = parser.parseAsmAndExp();
        } else if (t == ASM_BR_EXP) {
            r = parser.parseAsmBrExp();
        } else if (t == ASM_EQUAL_EXP) {
            r = parser.parseAsmEqualExp();
        } else if (t == ASM_EXP) {
            r = parser.parseAsmExp();
        } else if (t == ASM_INSTRUCTION) {
            r = parser.parseAsmInstruction();
        } else if (t == ASM_LOG_AND_EXP) {
            r = parser.parseAsmLogAndExp();
        } else if (t == ASM_LOG_OR_EXP) {
            r = parser.parseAsmLogOrExp();
        } else if (t == ASM_MUL_EXP) {
            r = parser.parseAsmMulExp();
        } else if (t == ASM_OR_EXP) {
            r = parser.parseAsmOrExp();
        } else if (t == ASM_PRIMARY_EXP) {
            r = parser.parseAsmPrimaryExp();
        } else if (t == ASM_REL_EXP) {
            r = parser.parseAsmRelExp();
        } else if (t == ASM_SHIFT_EXP) {
            r = parser.parseAsmShiftExp();
        } else if (t == ASM_STATEMENT) {
            r = parser.parseAsmStatement();
        } else if (t == ASM_TYPE_PREFIX) {
            r = parser.parseAsmTypePrefix();
        } else if (t == ASM_UNA_EXP) {
            r = parser.parseAsmUnaExp();
        } else if (t == ASM_XOR_EXP) {
            r = parser.parseAsmXorExp();
        } else if (t == ASSERT_EXPRESSION) {
            r = parser.parseAssertExpression();
        } else if (t == ASSIGN_EXPRESSION) {
            r = parser.parseAssignExpression();
        } else if (t == ASSOC_ARRAY_LITERAL) {
            r = parser.parseAssocArrayLiteral();
        } else if (t == AT_ATTRIBUTE) {
            r = parser.parseAtAttribute();
        } else if (t == ATTRIBUTE) {
            r = parser.parseAttribute();
        } else if (t == ATTRIBUTE_DECLARATION) {
            r = parser.parseAttributeDeclaration();
        } else if (t == AUTO_DECLARATION) {
            r = parser.parseAutoDeclaration();
        } else if (t == BASE_CLASS) {
            r = parser.parseBaseClass();
        } else if (t == BASE_CLASS_LIST) {
            r = parser.parseBaseClassList();
        } else if (t == BLOCK_STATEMENT) {
            r = parser.parseBlockStatement();
        } else if (t == BODY_STATEMENT) {
            r = parser.parseBodyStatement();
        } else if (t == BREAK_STATEMENT) {
            r = parser.parseBreakStatement();
        } else if (t == CASE_RANGE_STATEMENT) {
            r = parser.parseCaseRangeStatement();
        } else if (t == CASE_STATEMENT) {
            r = parser.parseCaseStatement();
        } else if (t == CAST_EXPRESSION) {
            r = parser.parseCastExpression();
        } else if (t == CAST_QUALIFIER) {
            r = parser.parseCastQualifier();
        } else if (t == CATCHES) {
            r = parser.parseCatches();
        } else if (t == CMP_EXPRESSION) {
            r = parser.parseCmpExpression();
        } else if (t == COMPILE_CONDITION) {
            r = parser.parseCompileCondition();
        } else if (t == CONDITIONAL_DECLARATION) {
            r = false;
        } else if (t == CONDITIONAL_STATEMENT) {
            r = parser.parseConditionalStatement();
        } else if (t == CONSTRAINT) {
            r = parser.parseConstraint();
        } else if (t == CONTINUE_STATEMENT) {
            r = parser.parseContinueStatement();
        } else if (t == CLASS_DECLARATION) {
            r = parser.parseClassDeclaration();
        } else if (t == DEBUG_CONDITION) {
            r = parser.parseDebugCondition();
        } else if (t == DEBUG_SPECIFICATION) {
            r = parser.parseDebugSpecification();
        } else if (t == DECLARATION) {
            r = parser.parseDeclaration();
        } else if (t == DECLARATION_OR_STATEMENT) {
            r = parser.parseDeclarationOrStatement();
        } else if (t == DECLARATIONS_AND_STATEMENTS) {
            r = false;
        } else if (t == DEFAULT_STATEMENT) {
            r = parser.parseDefaultStatement();
        } else if (t == DELETE_EXPRESSION) {
            r = parser.parseDeleteExpression();
        } else if (t == DEPRECATED) {
            r = parser.parseDeprecated();
        } else if (t == DO_STATEMENT) {
            r = parser.parseDoStatement();
        } else if (t == ENUM_BODY) {
            r = parser.parseEnumBody();
        } else if (t == EQUAL_EXPRESSION) {
            r = parser.parseEqualExpression();
        } else if (t == EXPRESSION) {
            r = parser.parseExpression();
        } else if (t == EXPRESSION_STATEMENT) {
            r = false;
        } else if (t == FINAL_SWITCH_STATEMENT) {
            r = parser.parseFinalSwitchStatement();
        } else if (t == FINALLY) {
            r = parser.parseFinally();
        } else if (t == FOR_STATEMENT) {
            r = parser.parseForStatement();
        } else if (t == FOREACH_STATEMENT) {
            r = parser.parseForeachStatement();
        } else if (t == FOREACH_TYPE_LIST) {
            r = false;
        } else if (t == FUNCTION_ATTRIBUTE) {
            r = false;
        } else if (t == FUNCTION_BODY) {
            r = parser.parseFunctionBody();
        } else if (t == FUNCTION_CALL_EXPRESSION) {
            r = parser.parseFunctionCallExpression();
        } else if (t == FUNCTION_LITERAL_EXPRESSION) {
            r = parser.parseFunctionLiteralExpression();
        } else if (t == GOTO_STATEMENT) {
            r = parser.parseGotoStatement();
        } else if (t == IDENTIFIER_CHAIN) {
            r = parser.parseIdentifierChain();
        } else if (t == IDENTIFIER_LIST) {
            r = parser.parseIdentifierList();
        } else if (t == IDENTIFIER_OR_TEMPLATE_CHAIN) {
            r = parser.parseIdentifierOrTemplateChain();
        } else if (t == IDENTIFIER_OR_TEMPLATE_INSTANCE) {
            r = parser.parseIdentifierOrTemplateInstance();
        } else if (t == IDENTITY_EXPRESSION) {
            r = parser.parseIdentityExpression();
        } else if (t == IF_STATEMENT) {
            r = parser.parseIfStatement();
        } else if (t == IMPORT_BIND) {
            r = parser.parseImportBind();
        } else if (t == IMPORT_BINDINGS) {
            r = false;
        } else if (t == IMPORT_DECLARATION) {
            r = parser.parseImportDeclaration();
        } else if (t == IMPORT_EXPRESSION) {
            r = parser.parseImportExpression();
        } else if (t == IN_EXPRESSION) {
            r = parser.parseInExpression();
        } else if (t == IN_STATEMENT) {
            r = parser.parseInStatement();
        } else if (t == INDEX) {
            r = parser.parseIndex();
        } else if (t == INDEX_EXPRESSION) {
            r = parser.parseIndexExpression();
        } else if (t == INITIALIZER) {
            r = parser.parseInitializer();
        } else if (t == INTERFACE_DECLARATION) {
            r = parser.parseInterfaceDeclaration();
        } else if (t == INVARIANT) {
            r = parser.parseInvariant();
        } else if (t == IS_EXPRESSION) {
            r = parser.parseIsExpression();
        } else if (t == KEY_VALUE_PAIR) {
            r = parser.parseKeyValuePair();
        } else if (t == KEY_VALUE_PAIRS) {
            r = parser.parseKeyValuePairs();
        } else if (t == LAST_CATCH) {
            r = parser.parseLastCatch();
        } else if (t == LINKAGE_ATTRIBUTE) {
            r = parser.parseLinkageAttribute();
        } else if (t == MEMBER_FUNCTION_ATTRIBUTE) {
            r = parser.parseMemberFunctionAttribute();
        } else if (t == MIXIN_DECLARATION) {
            r = parser.parseMixinDeclaration();
        } else if (t == MIXIN_EXPRESSION) {
            r = parser.parseMixinExpression();
        } else if (t == MIXIN_TEMPLATE_DECLARATION) {
            r = parser.parseMixinTemplateDeclaration();
        } else if (t == MIXIN_TEMPLATE_NAME) {
            r = parser.parseMixinTemplateName();
        } else if (t == MUL_EXPRESSION) {
            r = parser.parseMulExpression();
        } else if (t == NEW_ANON_CLASS_EXPRESSION) {
            r = parser.parseNewAnonClassExpression();
        } else if (t == NEW_EXPRESSION) {
            r = parser.parseNewExpression();
        } else if (t == NON_VOID_INITIALIZER) {
            r = parser.parseNonVoidInitializer();
        } else if (t == OPERANDS) {
            r = parser.parseOperands();
        } else if (t == OR_EXPRESSION) {
            r = parser.parseOrExpression();
        } else if (t == OR_OR_EXPRESSION) {
            r = parser.parseOrOrExpression();
        } else if (t == OUT_STATEMENT) {
            r = parser.parseOutStatement();
        } else if (t == PARAMETERS) {
            r = parser.parseParameters();
        } else if (t == POSTBLIT) {
            r = parser.parsePostblit();
        } else if (t == POW_EXPRESSION) {
            r = parser.parsePowExpression();
        } else if (t == PRAGMA_DECLARATION) {
            r = parser.parsePragmaDeclaration();
        } else if (t == PRAGMA_EXPRESSION) {
            r = parser.parsePragmaExpression();
        } else if (t == PRIMARY_EXPRESSION) {
            r = parser.parsePrimaryExpression();
        } else if (t == REGISTER) {
            r = parser.parseRegister();
        } else if (t == REL_EXPRESSION) {
            r = parser.parseRelExpression();
        } else if (t == RETURN_STATEMENT) {
            r = parser.parseReturnStatement();
        } else if (t == SCOPE_GUARD_STATEMENT) {
            r = parser.parseScopeGuardStatement();
        } else if (t == SHIFT_EXPRESSION) {
            r = parser.parseShiftExpression();
        } else if (t == STATEMENT) {
            r = parser.parseStatement();
        } else if (t == STATEMENT_NO_CASE_NO_DEFAULT) {
            r = parser.parseStatementNoCaseNoDefault();
        } else if (t == STATIC_ASSERT_DECLARATION) {
            r = parser.parseStaticAssertDeclaration();
        } else if (t == STATIC_ASSERT_STATEMENT) {
            r = parser.parseStaticAssertStatement();
        } else if (t == STATIC_IF_CONDITION) {
            r = parser.parseStaticIfCondition();
        } else if (t == STORAGE_CLASS) {
            r = parser.parseStorageClass();
        } else if (t == STRUCT_BODY) {
            r = parser.parseStructBody();
        } else if (t == STRUCT_INITIALIZER) {
            r = parser.parseStructInitializer();
        } else if (t == STRUCT_MEMBER_INITIALIZER) {
            r = parser.parseStructMemberInitializer();
        } else if (t == STRUCT_MEMBER_INITIALIZERS) {
            r = parser.parseStructMemberInitializers();
        } else if (t == STRING_LIT) {
            r = false;
        } else if (t == SWITCH_STATEMENT) {
            r = parser.parseSwitchStatement();
        } else if (t == SYMBOL) {
            r = parser.parseSymbol();
        } else if (t == SYNCHRONIZED_STATEMENT) {
            r = parser.parseSynchronizedStatement();
        } else if (t == TEMPLATE_ALIAS_PARAMETER) {
            r = parser.parseTemplateAliasParameter();
        } else if (t == TEMPLATE_ARGUMENT) {
            r = parser.parseTemplateArgument();
        } else if (t == TEMPLATE_ARGUMENT_LIST) {
            r = parser.parseTemplateArgumentList();
        } else if (t == TEMPLATE_ARGUMENTS) {
            r = parser.parseTemplateArguments();
        } else if (t == TEMPLATE_INSTANCE) {
            r = parser.parseTemplateInstance();
        } else if (t == TEMPLATE_MIXIN_EXPRESSION) {
            r = parser.parseTemplateMixinExpression();
        } else if (t == TEMPLATE_PARAMETER_LIST) {
            r = parser.parseTemplateParameterList();
        } else if (t == TEMPLATE_PARAMETERS) {
            r = parser.parseTemplateParameters();
        } else if (t == TEMPLATE_SINGLE_ARGUMENT) {
            r = parser.parseTemplateSingleArgument();
        } else if (t == TEMPLATE_THIS_PARAMETER) {
            r = parser.parseTemplateThisParameter();
        } else if (t == TEMPLATE_TUPLE_PARAMETER) {
            r = parser.parseTemplateTupleParameter();
        } else if (t == TEMPLATE_TYPE_PARAMETER) {
            r = parser.parseTemplateTypeParameter();
        } else if (t == TEMPLATE_VALUE_PARAMETER) {
            r = parser.parseTemplateValueParameter();
        } else if (t == TEMPLATE_VALUE_PARAMETER_DEFAULT) {
            r = parser.parseTemplateValueParameterDefault();
        } else if (t == TERNARY_EXPRESSION) {
            r = parser.parseTernaryExpression();
        } else if (t == THROW_STATEMENT) {
            r = parser.parseThrowStatement();
        } else if (t == TRAITS_EXPRESSION) {
            r = parser.parseTraitsExpression();
        } else if (t == TRY_STATEMENT) {
            r = parser.parseTryStatement();
        } else if (t == TYPE) {
            r = false;
        } else if (t == TYPE_2) {
            r = parser.parseType2();
        } else if (t == TYPE_SPECIALIZATION) {
            r = parser.parseTypeSpecialization();
        } else if (t == TYPE_SUFFIX) {
            r = parser.parseTypeSuffix();
        } else if (t == TYPEID_EXPRESSION) {
            r = parser.parseTypeidExpression();
        } else if (t == TYPEOF_EXPRESSION) {
            r = parser.parseTypeofExpression();
        } else if (t == UNARY_EXPRESSION) {
            r = parser.parseUnaryExpression();
        } else if (t == VARIABLE_DECLARATION) {
            r = parser.parseVariableDeclaration();
        } else if (t == VECTOR) {
            r = parser.parseVector();
        } else if (t == VERSION_CONDITION) {
            r = parser.parseVersionCondition();
        } else if (t == VERSION_SPECIFICATION) {
            r = parser.parseVersionSpecification();
        } else if (t == WHILE_STATEMENT) {
            r = parser.parseWhileStatement();
        } else if (t == WITH_STATEMENT) {
            r = parser.parseWithStatement();
        } else if (t == XOR_EXPRESSION) {
            r = parser.parseXorExpression();
        } else if (t == ADD_EXPRESSION) {
            r = parser.parseAddExpression();
        } else {
            r = parser.parseModule();
        }
        exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
    }


    @NotNull
    @Override
    public ASTNode parse(@NotNull final IElementType root, @NotNull final PsiBuilder builder) {
        parseLight(root, builder);
        return builder.getTreeBuilt();
    }
}
