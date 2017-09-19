package io.github.intellij.dlanguage.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LightPsiParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import io.github.intellij.dlanguage.psi.DlangTypes;
import org.jetbrains.annotations.NotNull;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static io.github.intellij.dlanguage.psi.DlangTypes.*;

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
        if (t == DlangTypes.FUNCTION_DECLARATION) {
            r = parser.parseFunctionDeclaration();
        } else if (t == DlangTypes.INTERFACE_OR_CLASS) {
            r = parser.parseInterfaceOrClass();
        } else if (t == DlangTypes.TEMPLATE_DECLARATION) {
            r = parser.parseTemplateDeclaration();
        } else if (t == DlangTypes.CONSTRUCTOR) {
            r = parser.parseConstructor();
        } else if (t == DlangTypes.DESTRUCTOR) {
            r = parser.parseDestructor();
        } else if (t == DlangTypes.STRUCT_DECLARATION) {
            r = parser.parseStructDeclaration();
        } else if (t == DlangTypes.ALIAS_INITIALIZER) {
            r = parser.parseAliasInitializer();
        } else if (t == DlangTypes.MODULE_DECLARATION) {
            r = parser.parseModuleDeclaration();
        } else if (t == DlangTypes.DECLARATOR) {
            r = parser.parseDeclarator();
        } else if (t == DlangTypes.LABELED_STATEMENT) {
            r = parser.parseLabeledStatement();
        } else if (t == DlangTypes.SHARED_STATIC_CONSTRUCTOR) {
            r = parser.parseSharedStaticConstructor();
        } else if (t == DlangTypes.SHARED_STATIC_DESTRUCTOR) {
            r = parser.parseSharedStaticDestructor();
        } else if (t == DlangTypes.STATIC_CONSTRUCTOR) {
            r = parser.parseStaticConstructor();
        } else if (t == DlangTypes.STATIC_DESTRUCTOR) {
            r = parser.parseStaticDestructor();
        } else if (t == DlangTypes.AUTO_DECLARATION_PART) {
            r = parser.parseAutoDeclarationPart();
        } else if (t == DlangTypes.ENUM_DECLARATION) {
            r = parser.parseEnumDeclaration();
        } else if (t == DlangTypes.UNION_DECLARATION) {
            r = parser.parseUnionDeclaration();
        } else if (t == DlangTypes.SINGLE_IMPORT) {
            r = parser.parseSingleImport();
        } else if (t == DlangTypes.UNITTEST) {
            r = parser.parseUnittest();
        } else if (t == DlangTypes.CATCH) {
            r = parser.parseCatch();
        } else if (t == DlangTypes.IF_CONDITION) {
            r = false;
        } else if (t == DlangTypes.FOREACH_TYPE) {
            r = parser.parseForeachType();
        } else if (t == DlangTypes.PARAMETER) {
            r = parser.parseParameter();
        } else if (t == DlangTypes.TEMPLATE_PARAMETER) {
            r = parser.parseTemplateParameter();
        } else if (t == DlangTypes.EPONYMOUS_TEMPLATE_DECLARATION) {
            r = parser.parseEponymousTemplateDeclaration();
        } else if (t == DlangTypes.ENUM_MEMBER) {
            r = parser.parseEnumMember();
        } else if (t == DlangTypes.ALIAS_DECLARATION) {
            r = parser.parseAliasDeclaration();
        } else if (t == DlangTypes.ALIAS_THIS_DECLARATION) {
            r = parser.parseAliasThisDeclaration();
        } else if (t == DlangTypes.ALIGN_ATTRIBUTE) {
            r = parser.parseAlignAttribute();
        } else if (t == DlangTypes.AND_AND_EXPRESSION) {
            r = parser.parseAndAndExpression();
        } else if (t == DlangTypes.AND_EXPRESSION) {
            r = parser.parseAndExpression();
        } else if (t == DlangTypes.ANONYMOUS_ENUM_DECLARATION) {
            r = parser.parseAnonymousEnumDeclaration();
        } else if (t == DlangTypes.ARGUMENT_LIST) {
            r = false;
        } else if (t == DlangTypes.ARGUMENTS) {
            r = parser.parseArguments();
        } else if (t == DlangTypes.ARRAY_INITIALIZER) {
            r = parser.parseArrayInitializer();
        } else if (t == DlangTypes.ARRAY_LITERAL) {
            r = parser.parseArrayLiteral();
        } else if (t == DlangTypes.ARRAY_MEMBER_INITIALIZATION) {
            r = parser.parseArrayMemberInitialization();
        } else if (t == DlangTypes.ASM_ADD_EXP) {
            r = parser.parseAsmAddExp();
        } else if (t == DlangTypes.ASM_AND_EXP) {
            r = parser.parseAsmAndExp();
        } else if (t == DlangTypes.ASM_BR_EXP) {
            r = parser.parseAsmBrExp();
        } else if (t == DlangTypes.ASM_EQUAL_EXP) {
            r = parser.parseAsmEqualExp();
        } else if (t == DlangTypes.ASM_EXP) {
            r = parser.parseAsmExp();
        } else if (t == DlangTypes.ASM_INSTRUCTION) {
            r = parser.parseAsmInstruction();
        } else if (t == DlangTypes.ASM_LOG_AND_EXP) {
            r = parser.parseAsmLogAndExp();
        } else if (t == DlangTypes.ASM_LOG_OR_EXP) {
            r = parser.parseAsmLogOrExp();
        } else if (t == DlangTypes.ASM_MUL_EXP) {
            r = parser.parseAsmMulExp();
        } else if (t == DlangTypes.ASM_OR_EXP) {
            r = parser.parseAsmOrExp();
        } else if (t == DlangTypes.ASM_PRIMARY_EXP) {
            r = parser.parseAsmPrimaryExp();
        } else if (t == DlangTypes.ASM_REL_EXP) {
            r = parser.parseAsmRelExp();
        } else if (t == DlangTypes.ASM_SHIFT_EXP) {
            r = parser.parseAsmShiftExp();
        } else if (t == DlangTypes.ASM_STATEMENT) {
            r = parser.parseAsmStatement();
        } else if (t == DlangTypes.ASM_TYPE_PREFIX) {
            r = parser.parseAsmTypePrefix();
        } else if (t == DlangTypes.ASM_UNA_EXP) {
            r = parser.parseAsmUnaExp();
        } else if (t == DlangTypes.ASM_XOR_EXP) {
            r = parser.parseAsmXorExp();
        } else if (t == DlangTypes.ASSERT_EXPRESSION) {
            r = parser.parseAssertExpression();
        } else if (t == DlangTypes.ASSIGN_EXPRESSION) {
            r = parser.parseAssignExpression();
        } else if (t == DlangTypes.ASSOC_ARRAY_LITERAL) {
            r = parser.parseAssocArrayLiteral();
        } else if (t == DlangTypes.AT_ATTRIBUTE) {
            r = parser.parseAtAttribute();
        } else if (t == DlangTypes.ATTRIBUTE) {
            r = parser.parseAttribute();
        } else if (t == DlangTypes.ATTRIBUTE_DECLARATION) {
            r = parser.parseAttributeDeclaration();
        } else if (t == DlangTypes.AUTO_DECLARATION) {
            r = parser.parseAutoDeclaration();
        } else if (t == DlangTypes.BASE_CLASS) {
            r = parser.parseBaseClass();
        } else if (t == DlangTypes.BASE_CLASS_LIST) {
            r = parser.parseBaseClassList();
        } else if (t == DlangTypes.BLOCK_STATEMENT) {
            r = parser.parseBlockStatement();
        } else if (t == DlangTypes.BODY_STATEMENT) {
            r = parser.parseBodyStatement();
        } else if (t == DlangTypes.BREAK_STATEMENT) {
            r = parser.parseBreakStatement();
        } else if (t == DlangTypes.CASE_RANGE_STATEMENT) {
            r = parser.parseCaseRangeStatement();
        } else if (t == DlangTypes.CASE_STATEMENT) {
            r = parser.parseCaseStatement();
        } else if (t == DlangTypes.CAST_EXPRESSION) {
            r = parser.parseCastExpression();
        } else if (t == DlangTypes.CAST_QUALIFIER) {
            r = parser.parseCastQualifier();
        } else if (t == DlangTypes.CATCHES) {
            r = parser.parseCatches();
        } else if (t == DlangTypes.CMP_EXPRESSION) {
            r = parser.parseCmpExpression();
        } else if (t == DlangTypes.COMPILE_CONDITION) {
            r = parser.parseCompileCondition();
        } else if (t == DlangTypes.CONDITIONAL_DECLARATION) {
            r = false;
        } else if (t == DlangTypes.CONDITIONAL_STATEMENT) {
            r = parser.parseConditionalStatement();
        } else if (t == DlangTypes.CONSTRAINT) {
            r = parser.parseConstraint();
        } else if (t == DlangTypes.CONTINUE_STATEMENT) {
            r = parser.parseContinueStatement();
        } else if (t == DlangTypes.CLASS_DECLARATION) {
            r = parser.parseClassDeclaration();
        } else if (t == DlangTypes.DEBUG_CONDITION) {
            r = parser.parseDebugCondition();
        } else if (t == DlangTypes.DEBUG_SPECIFICATION) {
            r = parser.parseDebugSpecification();
        } else if (t == DlangTypes.DECLARATION) {
            r = parser.parseDeclaration();
        } else if (t == DlangTypes.DECLARATION_OR_STATEMENT) {
            r = parser.parseDeclarationOrStatement();
        } else if (t == DlangTypes.DECLARATIONS_AND_STATEMENTS) {
            r = false;
        } else if (t == DlangTypes.DEFAULT_STATEMENT) {
            r = parser.parseDefaultStatement();
        } else if (t == DlangTypes.DELETE_EXPRESSION) {
            r = parser.parseDeleteExpression();
        } else if (t == DlangTypes.DEPRECATED) {
            r = parser.parseDeprecated();
        } else if (t == DlangTypes.DO_STATEMENT) {
            r = parser.parseDoStatement();
        } else if (t == DlangTypes.ENUM_BODY) {
            r = parser.parseEnumBody();
        } else if (t == DlangTypes.EQUAL_EXPRESSION) {
            r = parser.parseEqualExpression();
        } else if (t == DlangTypes.EXPRESSION) {
            r = parser.parseExpression();
        } else if (t == DlangTypes.EXPRESSION_STATEMENT) {
            r = false;
        } else if (t == DlangTypes.FINAL_SWITCH_STATEMENT) {
            r = parser.parseFinalSwitchStatement();
        } else if (t == DlangTypes.FINALLY) {
            r = parser.parseFinally();
        } else if (t == DlangTypes.FOR_STATEMENT) {
            r = parser.parseForStatement();
        } else if (t == DlangTypes.FOREACH_STATEMENT) {
            r = parser.parseForeachStatement();
        } else if (t == DlangTypes.FOREACH_TYPE_LIST) {
            r = false;
        } else if (t == DlangTypes.FUNCTION_ATTRIBUTE) {
            r = false;
        } else if (t == DlangTypes.FUNCTION_BODY) {
            r = parser.parseFunctionBody();
        } else if (t == DlangTypes.FUNCTION_CALL_EXPRESSION) {
            r = parser.parseFunctionCallExpression();
        } else if (t == DlangTypes.FUNCTION_LITERAL_EXPRESSION) {
            r = parser.parseFunctionLiteralExpression();
        } else if (t == DlangTypes.GOTO_STATEMENT) {
            r = parser.parseGotoStatement();
        } else if (t == DlangTypes.IDENTIFIER_CHAIN) {
            r = parser.parseIdentifierChain();
        } else if (t == DlangTypes.IDENTIFIER_LIST) {
            r = parser.parseIdentifierList();
        } else if (t == DlangTypes.IDENTIFIER_OR_TEMPLATE_CHAIN) {
            r = parser.parseIdentifierOrTemplateChain();
        } else if (t == DlangTypes.IDENTIFIER_OR_TEMPLATE_INSTANCE) {
            r = parser.parseIdentifierOrTemplateInstance();
        } else if (t == DlangTypes.IDENTITY_EXPRESSION) {
            r = parser.parseIdentityExpression();
        } else if (t == DlangTypes.IF_STATEMENT) {
            r = parser.parseIfStatement();
        } else if (t == DlangTypes.IMPORT_BIND) {
            r = parser.parseImportBind();
        } else if (t == DlangTypes.IMPORT_BINDINGS) {
            r = false;
        } else if (t == DlangTypes.IMPORT_DECLARATION) {
            r = parser.parseImportDeclaration();
        } else if (t == DlangTypes.IMPORT_EXPRESSION) {
            r = parser.parseImportExpression();
        } else if (t == DlangTypes.IN_EXPRESSION) {
            r = parser.parseInExpression();
        } else if (t == DlangTypes.IN_STATEMENT) {
            r = parser.parseInStatement();
        } else if (t == DlangTypes.INDEX) {
            r = parser.parseIndex();
        } else if (t == DlangTypes.INDEX_EXPRESSION) {
            r = parser.parseIndexExpression();
        } else if (t == DlangTypes.INITIALIZER) {
            r = parser.parseInitializer();
        } else if (t == DlangTypes.INTERFACE_DECLARATION) {
            r = parser.parseInterfaceDeclaration();
        } else if (t == DlangTypes.INVARIANT) {
            r = parser.parseInvariant();
        } else if (t == DlangTypes.IS_EXPRESSION) {
            r = parser.parseIsExpression();
        } else if (t == DlangTypes.KEY_VALUE_PAIR) {
            r = parser.parseKeyValuePair();
        } else if (t == DlangTypes.KEY_VALUE_PAIRS) {
            r = parser.parseKeyValuePairs();
        } else if (t == DlangTypes.LAST_CATCH) {
            r = parser.parseLastCatch();
        } else if (t == DlangTypes.LINKAGE_ATTRIBUTE) {
            r = parser.parseLinkageAttribute();
        } else if (t == DlangTypes.MEMBER_FUNCTION_ATTRIBUTE) {
            r = parser.parseMemberFunctionAttribute();
        } else if (t == DlangTypes.MIXIN_DECLARATION) {
            r = parser.parseMixinDeclaration();
        } else if (t == DlangTypes.MIXIN_EXPRESSION) {
            r = parser.parseMixinExpression();
        } else if (t == DlangTypes.MIXIN_TEMPLATE_DECLARATION) {
            r = parser.parseMixinTemplateDeclaration();
        } else if (t == DlangTypes.MIXIN_TEMPLATE_NAME) {
            r = parser.parseMixinTemplateName();
        } else if (t == DlangTypes.MUL_EXPRESSION) {
            r = parser.parseMulExpression();
        } else if (t == DlangTypes.NEW_ANON_CLASS_EXPRESSION) {
            r = parser.parseNewAnonClassExpression();
        } else if (t == DlangTypes.NEW_EXPRESSION) {
            r = parser.parseNewExpression();
        } else if (t == DlangTypes.NON_VOID_INITIALIZER) {
            r = parser.parseNonVoidInitializer();
        } else if (t == DlangTypes.OPERANDS) {
            r = parser.parseOperands();
        } else if (t == DlangTypes.OR_EXPRESSION) {
            r = parser.parseOrExpression();
        } else if (t == DlangTypes.OR_OR_EXPRESSION) {
            r = parser.parseOrOrExpression();
        } else if (t == DlangTypes.OUT_STATEMENT) {
            r = parser.parseOutStatement();
        } else if (t == DlangTypes.PARAMETERS) {
            r = parser.parseParameters();
        } else if (t == DlangTypes.POSTBLIT) {
            r = parser.parsePostblit();
        } else if (t == DlangTypes.POW_EXPRESSION) {
            r = parser.parsePowExpression();
        } else if (t == DlangTypes.PRAGMA_DECLARATION) {
            r = parser.parsePragmaDeclaration();
        } else if (t == DlangTypes.PRAGMA_EXPRESSION) {
            r = parser.parsePragmaExpression();
        } else if (t == DlangTypes.PRIMARY_EXPRESSION) {
            r = parser.parsePrimaryExpression();
        } else if (t == DlangTypes.REGISTER) {
            r = parser.parseRegister();
        } else if (t == DlangTypes.REL_EXPRESSION) {
            r = parser.parseRelExpression();
        } else if (t == DlangTypes.RETURN_STATEMENT) {
            r = parser.parseReturnStatement();
        } else if (t == DlangTypes.SCOPE_GUARD_STATEMENT) {
            r = parser.parseScopeGuardStatement();
        } else if (t == DlangTypes.SHIFT_EXPRESSION) {
            r = parser.parseShiftExpression();
        } else if (t == DlangTypes.STATEMENT) {
            r = parser.parseStatement();
        } else if (t == DlangTypes.STATEMENT_NO_CASE_NO_DEFAULT) {
            r = parser.parseStatementNoCaseNoDefault();
        } else if (t == DlangTypes.STATIC_ASSERT_DECLARATION) {
            r = parser.parseStaticAssertDeclaration();
        } else if (t == DlangTypes.STATIC_ASSERT_STATEMENT) {
            r = parser.parseStaticAssertStatement();
        } else if (t == DlangTypes.STATIC_IF_CONDITION) {
            r = parser.parseStaticIfCondition();
        } else if (t == DlangTypes.STORAGE_CLASS) {
            r = parser.parseStorageClass();
        } else if (t == DlangTypes.STRUCT_BODY) {
            r = parser.parseStructBody();
        } else if (t == DlangTypes.STRUCT_INITIALIZER) {
            r = parser.parseStructInitializer();
        } else if (t == DlangTypes.STRUCT_MEMBER_INITIALIZER) {
            r = parser.parseStructMemberInitializer();
        } else if (t == DlangTypes.STRUCT_MEMBER_INITIALIZERS) {
            r = parser.parseStructMemberInitializers();
        } else if (t == DlangTypes.STRING_LIT) {
            r = false;
        } else if (t == DlangTypes.SWITCH_STATEMENT) {
            r = parser.parseSwitchStatement();
        } else if (t == DlangTypes.SYMBOL) {
            r = parser.parseSymbol();
        } else if (t == DlangTypes.SYNCHRONIZED_STATEMENT) {
            r = parser.parseSynchronizedStatement();
        } else if (t == DlangTypes.TEMPLATE_ALIAS_PARAMETER) {
            r = parser.parseTemplateAliasParameter();
        } else if (t == DlangTypes.TEMPLATE_ARGUMENT) {
            r = parser.parseTemplateArgument();
        } else if (t == DlangTypes.TEMPLATE_ARGUMENT_LIST) {
            r = parser.parseTemplateArgumentList();
        } else if (t == DlangTypes.TEMPLATE_ARGUMENTS) {
            r = parser.parseTemplateArguments();
        } else if (t == DlangTypes.TEMPLATE_INSTANCE) {
            r = parser.parseTemplateInstance();
        } else if (t == DlangTypes.TEMPLATE_MIXIN_EXPRESSION) {
            r = parser.parseTemplateMixinExpression();
        } else if (t == DlangTypes.TEMPLATE_PARAMETER_LIST) {
            r = parser.parseTemplateParameterList();
        } else if (t == DlangTypes.TEMPLATE_PARAMETERS) {
            r = parser.parseTemplateParameters();
        } else if (t == DlangTypes.TEMPLATE_SINGLE_ARGUMENT) {
            r = parser.parseTemplateSingleArgument();
        } else if (t == DlangTypes.TEMPLATE_THIS_PARAMETER) {
            r = parser.parseTemplateThisParameter();
        } else if (t == DlangTypes.TEMPLATE_TUPLE_PARAMETER) {
            r = parser.parseTemplateTupleParameter();
        } else if (t == DlangTypes.TEMPLATE_TYPE_PARAMETER) {
            r = parser.parseTemplateTypeParameter();
        } else if (t == DlangTypes.TEMPLATE_VALUE_PARAMETER) {
            r = parser.parseTemplateValueParameter();
        } else if (t == DlangTypes.TEMPLATE_VALUE_PARAMETER_DEFAULT) {
            r = parser.parseTemplateValueParameterDefault();
        } else if (t == DlangTypes.TERNARY_EXPRESSION) {
            r = parser.parseTernaryExpression();
        } else if (t == DlangTypes.THROW_STATEMENT) {
            r = parser.parseThrowStatement();
        } else if (t == DlangTypes.TRAITS_EXPRESSION) {
            r = parser.parseTraitsExpression();
        } else if (t == DlangTypes.TRY_STATEMENT) {
            r = parser.parseTryStatement();
        } else if (t == DlangTypes.TYPE) {
            r = false;
        } else if (t == DlangTypes.TYPE_2) {
            r = parser.parseType2();
        } else if (t == DlangTypes.TYPE_SPECIALIZATION) {
            r = parser.parseTypeSpecialization();
        } else if (t == DlangTypes.TYPE_SUFFIX) {
            r = parser.parseTypeSuffix();
        } else if (t == DlangTypes.TYPEID_EXPRESSION) {
            r = parser.parseTypeidExpression();
        } else if (t == DlangTypes.TYPEOF_EXPRESSION) {
            r = parser.parseTypeofExpression();
        } else if (t == DlangTypes.UNARY_EXPRESSION) {
            r = parser.parseUnaryExpression();
        } else if (t == DlangTypes.VARIABLE_DECLARATION) {
            r = parser.parseVariableDeclaration();
        } else if (t == DlangTypes.VECTOR) {
            r = parser.parseVector();
        } else if (t == DlangTypes.VERSION_CONDITION) {
            r = parser.parseVersionCondition();
        } else if (t == DlangTypes.VERSION_SPECIFICATION) {
            r = parser.parseVersionSpecification();
        } else if (t == DlangTypes.WHILE_STATEMENT) {
            r = parser.parseWhileStatement();
        } else if (t == DlangTypes.WITH_STATEMENT) {
            r = parser.parseWithStatement();
        } else if (t == DlangTypes.XOR_EXPRESSION) {
            r = parser.parseXorExpression();
        } else if (t == DlangTypes.ADD_EXPRESSION) {
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
