package io.github.intellij.dlanguage.parser;

import com.google.common.collect.Sets;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.psi.tree.IElementType;
import kotlin.jvm.internal.Ref;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.intellij.lang.parser.GeneratedParserUtilBase.enter_section_;
import static com.intellij.lang.parser.GeneratedParserUtilBase.exit_section_;
import static io.github.intellij.dlanguage.psi.DlangTypes.*;

/**
 * This parser is very closely based on libdparse, so that we can get bug fixes and new features from libdparse.
 * This means that some of the code can be a little bit messy because of this.
 */
class DLangParser {

    // This list MUST BE MAINTAINED IN SORTED ORDER.
    private static final String[] REGISTER_NAMES = {
        "AH", "AL", "AX", "BH", "BL", "BP", "BPL", "BX", "CH", "CL", "CR0", "CR2",
        "CR3", "CR4", "CS", "CX", "DH", "DI", "DIL", "DL", "DR0", "DR1", "DR2",
        "DR3", "DR6", "DR7", "DS", "DX", "EAX", "EBP", "EBX", "ECX", "EDI", "EDX",
        "ES", "ESI", "ESP", "FS", "GS", "MM0", "MM1", "MM2", "MM3", "MM4", "MM5",
        "MM6", "MM7", "R10", "R10B", "R10D", "R10W", "R11", "R11B", "R11D", "R11W",
        "R12", "R12B", "R12D", "R12W", "R13", "R13B", "R13D", "R13W", "R14", "R14B",
        "R14D", "R14W", "R15", "R15B", "R15D", "R15W", "R8", "R8B", "R8D", "R8W",
        "R9", "R9B", "R9D", "R9W", "RAX", "RBP", "RBX", "RCX", "RDI", "RDX", "RSI",
        "RSP", "SI", "SIL", "SP", "SPL", "SS", "ST", "TR3", "TR4", "TR5", "TR6",
        "TR7", "XMM0", "XMM1", "XMM10", "XMM11", "XMM12", "XMM13", "XMM14", "XMM15",
        "XMM2", "XMM3", "XMM4", "XMM5", "XMM6", "XMM7", "XMM8", "XMM9", "YMM0",
        "YMM1", "YMM10", "YMM11", "YMM12", "YMM13", "YMM14", "YMM15", "YMM2",
        "YMM3", "YMM4", "YMM5", "YMM6", "YMM7", "YMM8", "YMM9"
    };

    @Deprecated
    final int MAX_ERRORS = 200;

    private static final IElementType[] stringLiteralsArray = new IElementType[] {
        ALTERNATE_WYSIWYG_STRING,
        DOUBLE_QUOTED_STRING,
        WYSIWYG_STRING,
        DELIMITED_STRING,
        TOKEN_STRING
    };

    private final Set<IElementType> literals = Sets.newHashSet(
        ALTERNATE_WYSIWYG_STRING,
        DOUBLE_QUOTED_STRING,
        WYSIWYG_STRING,
        DELIMITED_STRING,
        TOKEN_STRING,
        CHARACTER_LITERAL,
        KW_TRUE,
        KW_FALSE,
        KW_NULL,
        OP_DOLLAR,
        FLOAT_LITERAL, // represent float, double, ifloat, idouble, real and ireal values
        INTEGER_LITERAL,  // represent int, long, uint and ulong values
        KW___DATE__,
        KW___TIME__,
        KW___TIMESTAMP__,
        KW___VENDOR__,
        KW___VERSION__,
        KW___FILE__,
        KW___FILE_FULL_PATH__,
        KW___LINE__,
        KW___MODULE__,
        KW___FUNCTION__,
        KW___PRETTY_FUNCTION__
    );

    private final Set<IElementType> basicTypes = Sets.newHashSet(
        KW_INT,
        KW_BOOL,
        KW_BYTE,
        KW_CDOUBLE,
        KW_CENT,
        KW_CFLOAT,
        KW_CHAR,
        KW_CREAL,
        KW_DCHAR,
        KW_DOUBLE,
        KW_FLOAT,
        KW_IDOUBLE,
        KW_IFLOAT,
        KW_IREAL,
        KW_LONG,
        KW_REAL,
        KW_SHORT,
        KW_UBYTE,
        KW_UCENT,
        KW_UINT,
        KW_ULONG,
        KW_USHORT,
        KW_VOID,
        KW_WCHAR
    );

    private final Set<IElementType> Protections = Sets.newHashSet(
        KW_EXPORT,
        KW_PACKAGE,
        KW_PRIVATE,
        KW_PUBLIC,
        KW_PROTECTED
    );

    @NotNull
    private final PsiBuilder builder;
    private final Map<Integer, Boolean> cachedAAChecks = new HashMap<>();
    private final Map<Integer, Boolean> cachedTypedChecks = new HashMap<>();

    // private final HashMap<Marker, Integer> beginnings = new HashMap<>();//todo this maybe useful in the future but commented out for now
    // private Bookmark debugBookmark = null;//used to be able to eval expressions while debugging and then rollback side effects

    private int errorCount;
    private int warningCount;
    private IElementType[] tokens;
    private ArrayList<Integer> suppressMessages;
    private int index;


    private int suppressedErrorCount() {
        return suppressMessages.isEmpty() ? 0 : suppressMessages.get(suppressMessages.size()-1);
    }

    DLangParser(@NotNull final PsiBuilder builder) {
        this.errorCount = 0;
        this.warningCount = 0;
        this.tokens = getTokens(builder);
        this.suppressMessages = new ArrayList<>();
        this.index = 0;
        this.builder = builder;
    }

    private static IElementType nodeTypeToIElementType(final String nodeType) {
        switch (nodeType) {
            case "AlignAttribute":
                return ALIGN_ATTRIBUTE;
            case "ArgumentList":
                return ARGUMENT_LIST;
            case "ArrayInitializer":
                return ARRAY_INITIALIZER;
            case "ArrayLiteral":
                return ARRAY_LITERAL;
            case "ArrayMemberInitialization":
                return ARRAY_MEMBER_INITIALIZATION;
            case "AsmInstruction":
                return ASM_INSTRUCTION;
            case "AsmPrimaryExp":
                return ASM_PRIMARY_EXP;
            case "AsmStatement":
                return ASM_STATEMENT;
            case "AsmTypePrefix":
                return ASM_TYPE_PREFIX;
            case "AssocArrayLiteral":
                return ASSOC_ARRAY_LITERAL;
            case "Attribute":
                return ATTRIBUTE;
            case "AutoDeclaration":
                return AUTO_DECLARATION;
            case "BlockStatement":
                return BLOCK_STATEMENT;
            case "BodyStatement":
                return BODY_STATEMENT;
            case "BreakStatement":
                return BREAK_STATEMENT;
            case "BaseClassList":
                return BASE_CLASS_LIST;
            case "CastExpression":
                return CAST_EXPRESSION;
            case "Catch":
                return CATCH;
            case "Catches":
                return CATCHES;
            case "ConditionalStatement":
                return CONDITIONAL_STATEMENT;
            case "Constraint":
                return CONSTRAINT;
            case "Constructor":
                return CONSTRUCTOR;
            case "ContinueStatement":
                return CONTINUE_STATEMENT;
            case "DebugCondition":
                return DEBUG_CONDITION;
            case "DebugSpecification":
                return DEBUG_SPECIFICATION;
            case "Declaration":
                return DECLARATION;
            case "Declarator":
                return DECLARATOR;
            case "DeclaratorIdentifierList":
                return DECLARATOR_IDENTIFIER;
            case "DefaultStatement":
                return DEFAULT_STATEMENT;
            case "Destructor":
                return DESTRUCTOR;
            case "DoStatement":
                return DO_STATEMENT;
            case "EnumBody":
                return ENUM_BODY;
            case "AnonymousEnumMember":
                return ANONYMOUS_ENUM_DECLARATION;
            case "AnonymousEnumDeclaration":
                return ANONYMOUS_ENUM_DECLARATION;
            case "EnumDeclaration":
                return ENUM_DECLARATION;
            case "EnumMember":
                return ENUM_MEMBER;
            case "FinalSwitchStatement":
                return FINAL_SWITCH_STATEMENT;
            case "ForStatement":
                return FOR_STATEMENT;
            case "ForeachStatement":
                return FOREACH_STATEMENT;
            case "ForeachType":
                return FOREACH_TYPE;
            case "ForeachTypeList":
                return FOREACH_TYPE_LIST;
            case "FunctionBody":
                return FUNCTION_BODY;
            case "GotoStatement":
                return GOTO_STATEMENT;
            case "IfStatement":
                return IF_STATEMENT;
            case "ImportBind":
                return IMPORT_BIND;
            case "ImportDeclaration":
                return IMPORT_DECLARATION;
            case "InStatement":
                return IN_STATEMENT;
            case "Initializer":
                return INITIALIZER;
            case "Invariant":
                return INVARIANT;
            case "KeyValuePair":
                return KEY_VALUE_PAIR;
            case "KeyValuePairs":
                return KEY_VALUE_PAIRS;
            case "LabeledStatement":
                return LABELED_STATEMENT;
            case "LastCatch":
                return LAST_CATCH;
            case "LinkageAttribute":
                return LINKAGE_ATTRIBUTE;
            case "MemberFunctionAttribute":
                return MEMBER_FUNCTION_ATTRIBUTE;
            case "MixinDeclaration":
                return MIXIN_DECLARATION;
            case "ModuleDeclaration":
                return MODULE_DECLARATION;
            case "NonVoidInitializer":
                return NON_VOID_INITIALIZER;
            case "Operands":
                return OPERANDS;
            case "OutStatement":
                return OUT_STATEMENT;
            case "Parameter":
                return PARAMETER;
            case "Parameters":
                return PARAMETERS;
            case "Postblit":
                return POSTBLIT;
            case "Register":
                return REGISTER;
            case "ReturnStatement":
                return RETURN_STATEMENT;
            case "ScopeGuardStatement":
                return SCOPE_GUARD_STATEMENT;
            case "SharedStaticConstructor":
                return SHARED_STATIC_CONSTRUCTOR;
            case "SharedStaticDestructor":
                return SHARED_STATIC_DESTRUCTOR;
            case "Statement":
                return STATEMENT;
            case "StatementNoCaseNoDefault":
                return STATEMENT_NO_CASE_NO_DEFAULT;
            case "StaticConstructor":
                return STATIC_CONSTRUCTOR;
            case "StaticDestructor":
                return STATIC_DESTRUCTOR;
            case "StaticIfCondition":
                return STATIC_IF_CONDITION;
            case "StorageClass":
                return STORAGE_CLASS;
            case "StructDeclaration":
                return STRUCT_DECLARATION;
            case "StructInitializer":
                return STRUCT_INITIALIZER;
            case "StructMemberInitializer":
                return STRUCT_MEMBER_INITIALIZER;
            case "StructMemberInitializers":
                return STRUCT_MEMBER_INITIALIZERS;
            case "SwitchStatement":
                return SWITCH_STATEMENT;
            case "Symbol":
                return SYMBOL;
            case "SynchronizedStatement":
                return SYNCHRONIZED_STATEMENT;
            case "TemplateAliasParameter":
                return TEMPLATE_ALIAS_PARAMETER;
            case "TemplateArgument":
                return TEMPLATE_ARGUMENT;
            case "TemplateArgumentList":
                return TEMPLATE_ARGUMENT_LIST;
            case "TemplateArguments":
                return TEMPLATE_ARGUMENTS;
            case "TemplateDeclaration":
                return TEMPLATE_DECLARATION;
            case "TemplateInstance":
                return TEMPLATE_INSTANCE;
            case "TemplateParameter":
                return TEMPLATE_PARAMETER;
            case "TemplateParameterList":
                return TEMPLATE_PARAMETER_LIST;
            case "TemplateParameters":
                return TEMPLATE_PARAMETERS;
            case "TemplateSingleArgument":
                return TEMPLATE_SINGLE_ARGUMENT;
            case "TemplateThisParameter":
                return TEMPLATE_THIS_PARAMETER;
            case "TemplateTupleParameter":
                return TEMPLATE_TUPLE_PARAMETER;
            case "TemplateTypeParameter":
                return TEMPLATE_TYPE_PARAMETER;
            case "TemplateValueParameter":
                return TEMPLATE_VALUE_PARAMETER;
            case "ThrowExpression":
                return THROW_EXPRESSION;
            case "TryStatement":
                return TRY_STATEMENT;
            case "Type":
                return TYPE;
            case "TypeIdentifierPart":
                return TYPE_IDENTIFIER_PART;
            case "TypeSpecialization":
                return TYPE_SPECIALIZATION;
            case "UnionDeclaration":
                return UNION_DECLARATION;
            case "VersionCondition":
                return VERSION_CONDITION;
            case "VersionSpecification":
                return VERSION_SPECIFICATION;
            case "WhileStatement":
                return WHILE_STATEMENT;
            case "WithStatement":
                return WITH_STATEMENT;
            case "AsmAddExp":
                return ASM_ADD_EXP;
            case "AsmAndExp":
                return ASM_AND_EXP;
            case "AsmBrExp":
                return ASM_BR_EXP;
            case "AsmExp":
                return ASM_EXP;
            case "AsmEqualExp":
                return ASM_EQUAL_EXP;
            case "AsmLogAndExp":
                return ASM_LOG_AND_EXP;
            case "AsmLogOrExp":
                return ASM_LOG_OR_EXP;
            case "AsmMulExp":
                return ASM_MUL_EXP;
            case "AsmOrExp":
                return ASM_OR_EXP;
            case "AsmRelExp":
                return ASM_REL_EXP;
            case "AsmUnaExp":
                return ASM_UNA_EXP;
            case "AsmShiftExp":
                return ASM_SHIFT_EXP;
            case "AsmXorExp":
                return ASM_XOR_EXP;
            case "AssertExpression":
                return ASSERT_EXPRESSION;
            case "AssignExpression":
                return ASSIGN_EXPRESSION;
            case "DeleteExpression":
                return DELETE_EXPRESSION;
            case "IdentityExpression":
                return IDENTITY_EXPRESSION;
            case "ImportExpression":
                return IMPORT_EXPRESSION;
            case "IndexExpression":
                return INDEX_EXPRESSION;
            case "IsExpression":
                return IS_EXPRESSION;
            case "MixinExpression":
                return MIXIN_EXPRESSION;
            case "NewAnonClassExpression":
                return NEW_ANON_CLASS_EXPRESSION;
            case "NewExpression":
                return NEW_EXPRESSION;
            case "OrExpression":
            case "PrimaryExpression":
                return PRIMARY_EXPRESSION;
            case "PragmaDeclaration":
                return PRAGMA_DECLARATION;
            case "RelExpression":
                return REL_EXPRESSION;
            case "StaticAssertStatement":
                return STATIC_ASSERT_STATEMENT;
            case "StaticAssertDeclaration":
                return STATIC_ASSERT_DECLARATION;
            case "TraitsExpression":
                return TRAITS_EXPRESSION;
            case "TypeidExpression":
                return TYPEID_EXPRESSION;
            case "Unittest":
                return UNITTEST;
            case "Vector":
                return VECTOR;
            case "StaticForeachStatement":
                return STATIC_FOREACH_STATEMENT;
            case "StaticForeachDeclaration":
                return STATIC_FOREACH_DECLARATION;
            case "AliasAssign":
                return ALIAS_ASSIGN;
            default:
                throw new IllegalArgumentException("unrecognized thing to parse:" + nodeType);
        }
    }

    private IElementType[] getTokens(@NotNull final PsiBuilder builder) {
        final Marker tokenRollBackMark = builder.mark();
        final ArrayList<IElementType> tokens = new ArrayList<>();

        do {
            @Nullable final IElementType tokenType = builder.getTokenType();

            if(tokenType != null) {
                tokens.add(tokenType);
            }

            builder.advanceLexer();
        } while (!builder.eof());

        tokenRollBackMark.rollbackTo();

        return tokens.toArray(new IElementType[0]);
    }

    private void cleanup(@NotNull final Marker marker, final IElementType element) {
////        index = beginnings.get(marker);
////        beginnings.remove(marker);
        exit_section_modified(builder, marker, element, true);
    }

    /**
     * Parses an AddExpression.
     * converted
     * $(GRAMMAR $(RULEDEF addExpression):
     *   $(RULE mulExpression)
     * | $(RULE addExpression) $(LPAREN)$(LITERAL '+') | $(LITERAL'-') | $(LITERAL'~')$(RPAREN) $(RULE mulExpression)
     * ;)
     */
    boolean parseAddExpression() {
        final Marker section = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean result = parseLeftAssocBinaryExpression(toParseExpression, "AddExpression", "MulExpression", OP_PLUS, OP_MINUS, OP_TILDA);
        if (toParseExpression.element) {
            exit_section_modified(builder, section, ADD_EXPRESSION, result);
        } else {
            section.drop();
//            beginnings.remove(section);
        }
        return result;
    }

    /**
     * Parses an AliasDeclaration.
     * converted
     * $(GRAMMAR $(RULEDEF aliasDeclaration):
     *   $(LITERAL 'alias') $(RULE aliasInitializer) $(LPAREN)$(LITERAL ',') $(RULE aliasInitializer)$(RPAREN)* $(LITERAL ';')
     * | $(LITERAL 'alias') $(RULE storageClass)* $(RULE type) $(RULE declaratorIdentifierList) $(LITERAL ';')
     * | $(LITERAL 'alias') $(RULE storageClass)* $(RULE type) $(RULE identifier) $(LITERAL '(') $(RULE parameters) $(LITERAL ')') $(memberFunctionAttribute)* $(LITERAL ';')
     * ;)
     */
    boolean parseAliasDeclaration() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_ALIAS)) {
            cleanup(m, ALIAS_DECLARATION);
            return false;
        }
        if (startsWith(ID, OP_EQ) || startsWith(ID, OP_PAR_LEFT)) {
            do {
                if (!parseAliasInitializer()) {
                    cleanup(m, ALIAS_DECLARATION);
                    return false;
                }
                if (currentIs(OP_COMMA)) {
                    advance();
                } else {
                    break;
                }
            }
            while (moreTokens());
        } else {
            while (moreTokens() && isStorageClass()) {
                if (!parseStorageClass()) {
                    cleanup(m, ALIAS_DECLARATION);
                    return false;
                }
            }
            if (!parseType().first) {
                cleanup(m, ALIAS_DECLARATION);
                return false;
            }
            if (!parseDeclaratorIdentifierList()) {
                cleanup(m, ALIAS_DECLARATION);
                return false;
            }
            if (currentIs(OP_PAR_LEFT)) {
                if (!parseParameters()) {
                    cleanup(m, ALIAS_DECLARATION);
                    return false;
                }
                while (moreTokens() && currentIsMemberFunctionAttribute()) {
                    if (!parseMemberFunctionAttribute()) {
                        cleanup(m, ALIAS_DECLARATION);
                        return false;
                    }
                }
            }
        }
        if (expect(OP_SCOLON) == null) {
            cleanup(m, ALIAS_DECLARATION);
            return false;
        }
        exit_section_modified(builder, m, ALIAS_DECLARATION, true);
        return true;
    }

    private boolean isFunction() {
        if (currentIsOneOf(KW_FUNCTION, KW_DELEGATE, OP_BRACES_LEFT))
            return true;
        if (startsWith(ID, OP_LAMBDA_ARROW))
            return true;
        Bookmark b = setBookmark();
        if (currentIs(OP_PAR_LEFT) || currentIs(KW_REF) && peekIs(OP_PAR_LEFT)) {
            if (currentIs(KW_REF))
                advance();
            final IElementType t = peekPastParens();
            if (t != null) {
                if (t.equals(OP_LAMBDA_ARROW) || t.equals(OP_BRACES_LEFT)
                    || isMemberFunctionAttribute(t)) {
                    goToBookmark(b);
                    return true;
                }

            }
        }
        goToBookmark(b);
        return false;
    }

    /**
     * Parses an AliasAssign.
     * <p>
     * $(GRAMMAR $(RULEDEF aliasAssign):
     *   $(LITERAL Identifier) $(LITERAL '=') $(RULE type)
     */
    boolean parseAliasAssign()
    {
        final Marker m = enter_section_modified(builder);
        //mixin(traceEnterAndExit!(__FUNCTION__));
        if (!tokenCheck(ID)) {
            cleanup(m, ALIAS_ASSIGN);
            return false;
        }
        if (!tokenCheck(OP_EQ)) {
            cleanup(m, ALIAS_ASSIGN);
            return false;
        }
        if (!parseType().first) {
            cleanup(m, ALIAS_ASSIGN);
            return false;
        }
        final IElementType semi = expect(OP_SCOLON);
        if (semi == null) {
            cleanup(m, ALIAS_ASSIGN);
            return false;
        }
        exit_section_modified(builder, m, ALIAS_ASSIGN, true);
        return true;
    }

    /**
     * Parses an AliasInitializer.
     * <p>
     * $(GRAMMAR $(RULEDEF aliasInitializer):
     *   $(LITERAL Identifier) $(RULE templateParameters)? $(LITERAL '=') $(RULE storageClass)* $(RULE type)
     * | $(LITERAL Identifier) $(RULE templateParameters)? $(LITERAL '=') $(RULE storageClass)* $(RULE type) $(RULE parameters) $(RULE memberFunctionAttribute)*
     * | $(LITERAL Identifier) $(RULE templateParameters)? $(LITERAL '=') $(RULE functionLiteralExpression)
     * ;)
     */
    boolean parseAliasInitializer() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(ID)) {
            cleanup(m, ALIAS_INITIALIZER);
            return false;
        }
        if (currentIs(OP_PAR_LEFT)) {
            if (!parseTemplateParameters()) {
                cleanup(m, ALIAS_INITIALIZER);
                return false;
            }
        }
        if (!tokenCheck(OP_EQ)) {
            cleanup(m, ALIAS_INITIALIZER);
            return false;
        }
        if (isFunction()) {
            if (!parseFunctionLiteralExpression()) {
                cleanup(m, ALIAS_INITIALIZER);
                return false;
            }
        } else {
            while (moreTokens() && isStorageClass()) {
                if (!parseStorageClass()) {
                    cleanup(m, ALIAS_INITIALIZER);
                    return false;
                }
            }
            if (!parseType().first) {
                cleanup(m, ALIAS_INITIALIZER);
                return false;
            }
            if (currentIs(OP_PAR_LEFT)) {
                if (!parseParameters()) {
                    cleanup(m, ALIAS_INITIALIZER);
                    return false;
                }
                while (moreTokens() && currentIsMemberFunctionAttribute())
                    if (!parseMemberFunctionAttribute()) {
                        cleanup(m, ALIAS_INITIALIZER);
                        return false;
                    }
            }
        }
        exit_section_modified(builder, m, ALIAS_INITIALIZER, true);
        return true;
    }

    /**
     * Parses an AliasThisDeclaration.
     * <p>
     * $(GRAMMAR $(RULEDEF aliasThisDeclaration):
     * $(LITERAL 'alias') $(LITERAL Identifier) $(LITERAL 'this') $(LITERAL ';')
     * ;)
     */
    boolean parseAliasThisDeclaration() {
        final Marker m = enter_section_modified(builder);
//			Runnable cleanup =() ->  exit_section_modified(builder,m,DlangTypes.AliasThisDeclaration,false);
        if (!tokenCheck(KW_ALIAS)) {
            cleanup(m, ALIAS_THIS_DECLARATION);
            return false;
        }
        if (!tokenCheck(ID)) {
            cleanup(m, ALIAS_THIS_DECLARATION);
            return false;
        }
        if (!tokenCheck(KW_THIS)) {
            cleanup(m, ALIAS_THIS_DECLARATION);
            return false;
        }
        if (expect(OP_SCOLON) == null) {
            cleanup(m, ALIAS_THIS_DECLARATION);
            return false;
        }
        exit_section_modified(builder, m, ALIAS_THIS_DECLARATION, true);
        return true;
    }

    /**
     * Parses an AlignAttribute.
     * <p>
     * $(GRAMMAR $(RULEDEF alignAttribute):
     * $(LITERAL 'align') ($(LITERAL '$(LPAREN)') $(RULE AssignExpression) $(LITERAL '$(RPAREN)'))?
     * ;)
     */
    boolean parseAlignAttribute() {
        final Marker m = enter_section_modified(builder);
        expect(KW_ALIGN);
        if (currentIs(OP_PAR_LEFT)) {
            if (!tokenCheck(OP_PAR_LEFT)) {
                cleanup(m, ALIGN_ATTRIBUTE);
                return false;
            }
            if (!parseAssignExpression()) {
                cleanup(m, ALIGN_ATTRIBUTE);
                return false;
            }
            if (!tokenCheck(OP_PAR_RIGHT)) {
                cleanup(m, ALIGN_ATTRIBUTE);
                return false;
            }
        }
        exit_section_modified(builder, m, ALIGN_ATTRIBUTE, true);
        return true;
    }

    /**
     * Parses an AndAndExpression.
     * <p>
     * $(GRAMMAR $(RULEDEF andAndExpression):
     *   $(RULE orExpression)
     * | $(RULE andAndExpression) $(LITERAL '&&') $(RULE orExpression)
     * ;)
     */
    boolean parseAndAndExpression() {
        final Marker m = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean result = parseLeftAssocBinaryExpression(toParseExpression, "AndAndExpression", "OrExpression", OP_BOOL_AND);
        if (toParseExpression.element) {
            exit_section_modified(builder, m, AND_AND_EXPRESSION, result);
        } else {
            m.drop();
//            beginnings.remove(m);
        }
        return result;
    }

    /**
     * Parses an AndExpression.
     * <p>
     * $(GRAMMAR $(RULEDEF andExpression):
     *   $(RULE cmpExpression)
     * | $(RULE andExpression) $(LITERAL '&') $(RULE cmpExpression)
     * ;)
     */
    boolean parseAndExpression() {
        final Marker m = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean result = parseLeftAssocBinaryExpression(toParseExpression, "AndExpression", "CmpExpression", OP_AND);
        if (!toParseExpression.element) {
            m.drop();
//            beginnings.remove(m);
            return result;
        }
        exit_section_modified(builder, m, AND_EXPRESSION, result);
        return result;
    }

    /**
     * Parses an ArgumentList.
     * <p>
     * $(GRAMMAR $(RULEDEF argumentList):
     * $(RULE assignExpression) ($(LITERAL ',') $(RULE assignExpression)?)*
     * ;)
     */
    private Pair<Boolean, Integer> parseArgumentList() {
        if (!moreTokens()) {
            error("argument list expected instead of EOF");
            return new Pair<>(false, -1);
        }
        final Marker argumentList = enter_section_modified(builder);
        final Ref.IntRef length = new Ref.IntRef();
        length.element = 0;
        final boolean node = parseCommaSeparatedRule(length, "ArgumentList", "AssignExpression", true);
        if (!node) {
            cleanup(argumentList, ARGUMENT_LIST);
            return new Pair<>(false, -1);
        }
        exit_section_modified(builder, argumentList, ARGUMENT_LIST, true);
        return new Pair<>(true, length.element);
    }

    /**
     * Parses Arguments.
     * <p>
     * $(GRAMMAR $(RULEDEF arguments):
     * $(LITERAL '$(LPAREN)') $(RULE argumentList)? $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseArguments() {
        final Marker m = enter_section_(builder);
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, ARGUMENTS);
            return false;
        }
        if (!currentIs(OP_PAR_RIGHT))
            if (!parseArgumentList().first) {
                cleanup(m, ARGUMENTS);
                return false;
            }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, ARGUMENTS);
            return false;
        }
        exit_section_modified(builder, m, ARGUMENTS, true);
        return true;
    }

    /**
     * Parses an ArrayInitializer.
     * <p>
     * $(GRAMMAR $(RULEDEF arrayInitializer):
     * ($(RULE arrayMemberInitialization) ($(LITERAL ',') $(RULE arrayMemberInitialization)?)*)?
     * ;)
     */
    boolean parseArrayInitializer() {
//        Marker m = enter_section_(builder);
        final Marker arrayInit = enter_section_modified(builder);
        while (moreTokens()) {
            if (currentIs(OP_BRACKET_RIGHT))
                break;
            if (!parseArrayMemberInitialization()) {
                cleanup(arrayInit, ARRAY_INITIALIZER);
                return false;
            }
            if (currentIs(OP_COMMA))
                advance();
            else
                break;
        }
        exit_section_modified(builder, arrayInit, ARRAY_INITIALIZER, true);
        return true;
    }

    /**
     * Parses an ArrayLiteral.
     * <p>
     * $(GRAMMAR $(RULEDEF arrayLiteral):
     * $(LITERAL '[') $(RULE ArrayMemberInitiolizations)? $(LITERAL ']')
     * ;)
     */
    boolean parseArrayLiteral() {
        final Marker m = enter_section_modified(builder);
//			Runnable cleanup =() ->  exit_section_modified(builder,m,DlangTypes.ArrayLiteral,false);
        final IElementType open = expect(OP_BRACKET_LEFT);
        if (open == null) {
            cleanup(m, ARRAY_LITERAL);
            return false;
        }
        if (!parseArrayInitializer()) {
            cleanup(m, ARRAY_LITERAL);
            return false;
        }
        final IElementType close = expect(OP_BRACKET_RIGHT);
        if (close == null) {
            cleanup(m, ARRAY_INITIALIZER);
            return false;
        }
        exit_section_modified(builder, m, ARRAY_LITERAL, true);
        return true;
    }

    /**
     * Parses an ArrayMemberInitialization.
     * <p>
     * $(GRAMMAR $(RULEDEF arrayMemberInitialization):
     * ($(RULE assignExpression) $(LITERAL ':'))? $(RULE nonVoidInitializer)
     * ;)
     */
    boolean parseArrayMemberInitialization() {
//            mixin(traceEnterAndExit!(__FUNCTION__))
        final Marker m = enter_section_modified(builder);
//			Runnable cleanup =() ->  exit_section_modified(builder,m,DlangTypes.ArrayMemberInitialization,false);
        if (currentIs(OP_BRACKET_LEFT)) {
            final Bookmark b = setBookmark();
            skipBrackets();
            if (currentIs(OP_COLON)) {
                goToBookmark(b);
                if (!parseAssignExpression()) {
                    abandonBookmark(b);
                    cleanup(m, ARRAY_MEMBER_INITIALIZATION);
                    return false;
                }
                advance(); // :
                if (!parseNonVoidInitializer()) {
                    abandonBookmark(b);
                    cleanup(m, ARRAY_MEMBER_INITIALIZATION);
                    return false;
                }
                abandonBookmark(b);
                exit_section_modified(builder, m, ARRAY_MEMBER_INITIALIZATION, true);
                return true;
            } else {
                goToBookmark(b);
            }
        }
        if (currentIs(OP_BRACES_LEFT)) {
            if (!parseNonVoidInitializer()) {
                cleanup(m, ARRAY_MEMBER_INITIALIZATION);
                return false;
            }
            exit_section_modified(builder, m, ARRAY_MEMBER_INITIALIZATION, true);
            return true;
        } else {
            final boolean assignExpression = parseAssignExpression();
            if (!assignExpression) {
                cleanup(m, ARRAY_MEMBER_INITIALIZATION);
                return false;
            }
            if (currentIs(OP_COLON)) {
                advance();
                if (!parseNonVoidInitializer()) {
                    cleanup(m, ARRAY_MEMBER_INITIALIZATION);
                    return false;
                }
            }
            exit_section_modified(builder, m, ARRAY_MEMBER_INITIALIZATION, true);
            return true;
        }

    }

    /**
     * Parses an AsmAddExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmAddExp):
     *   $(RULE asmMulExp)
     * | $(RULE asmAddExp) ($(LITERAL '+') | $(LITERAL '-')) $(RULE asmMulExp)
     * ;)
     */
    boolean parseAsmAddExp() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean result = parseLeftAssocBinaryExpression(toParseExpression, "AsmAddExp", "AsmMulExp", OP_PLUS, OP_MINUS);
        if (!toParseExpression.element) {
            m.drop();
//            beginnings.remove(m);
            return result;
        }
        exit_section_modified(builder, m, ASM_ADD_EXP, result);
        return result;
    }

    /**
     * Parses an AsmAndExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmAndExp):
     *   $(RULE asmEqualExp)
     * | $(RULE asmAndExp) $(LITERAL '&') $(RULE asmEqualExp)
     * ;)
     */
    boolean parseAsmAndExp() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean result = parseLeftAssocBinaryExpression(toParseExpression, "AsmAndExp", "AsmEqualExp", OP_AND);
        if (!toParseExpression.element) {
            m.drop();
//            beginnings.remove(m);
            return result;
        }
        exit_section_modified(builder, m, ASM_AND_EXP, result);
        return result;
    }

    /**
     * Parses an AsmBrExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmBrExp):
     *   $(RULE asmUnaExp)
     * | $(RULE asmBrExp)? $(LITERAL '[') $(RULE asmExp) $(LITERAL ']')
     * ;)
     */
    boolean parseAsmBrExp() {
//            AsmBrExp node = allocator.make!AsmBrExp();
        final Marker m = enter_section_modified(builder);
        if (!moreTokens())
        {
            error("Found end-of-file when expecting an AsmBrExp"/*, false*/);
            cleanup(m, ASM_BR_EXP);
            return false;
        }
        if (currentIs(OP_BRACKET_LEFT)) {
            advance(); // [
            if (!parseAsmExp()) {
                cleanup(m, ASM_BR_EXP);
                return false;
            }
            if (!tokenCheck(OP_BRACKET_RIGHT)) {
                cleanup(m, ASM_BR_EXP);
                return false;
            }
            if (currentIs(OP_BRACKET_LEFT)) {
                while (currentIs(OP_BRACKET_LEFT)) {
                    advance(); // [
                    if (!parseAsmExp()) {
                        cleanup(m, ASM_BR_EXP);
                        return false;
                    }
                    if (!tokenCheck(OP_BRACKET_RIGHT)) {
                        cleanup(m, ASM_BR_EXP);
                        return false;
                    }
                }
            }
        } else {
            if (!parseAsmUnaExp()) {
                cleanup(m, ASM_BR_EXP);
                return false;
            }
            while (currentIs(OP_BRACKET_LEFT)) {
                advance(); // [
                if (!parseAsmExp()) {
                    cleanup(m, ASM_BR_EXP);
                    return false;
                }
                if (!tokenCheck(OP_BRACKET_RIGHT)) {
                    cleanup(m, ASM_BR_EXP);
                    return false;
                }
            }
        }
        exit_section_modified(builder, m, ASM_BR_EXP, true);
        return true;
    }

    /**
     * Parses an AsmEqualExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmEqualExp):
     *   $(RULE asmRelExp)
     * | $(RULE asmEqualExp) ('==' | '!=') $(RULE asmRelExp)
     * ;)
     */
    boolean parseAsmEqualExp() {
        final Marker m = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean result = parseLeftAssocBinaryExpression(toParseExpression, "AsmEqualExp", "AsmRelExp", OP_EQ_EQ, OP_NOT_EQ);
        if (!toParseExpression.element) {
            m.drop();
//            beginnings.remove(m);
            return result;
        }
        exit_section_modified(builder, m, ASM_EQUAL_EXP, result);
        return result;
    }

    /**
     * Parses an AsmExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmExp):
     * $(RULE asmLogOrExp) ($(LITERAL '?') $(RULE asmExp) $(LITERAL ':') $(RULE asmExp))?
     * ;)
     */
    boolean parseAsmExp() {
//            AsmExp node = allocator.make!AsmExp;
        final Marker m = enter_section_modified(builder);
        if (!parseAsmLogOrExp()) {
            cleanup(m, ASM_EXP);
            return false;
        }
        if (currentIs(OP_QUEST)) {
            advance();
            if (!parseAsmExp()) {
                cleanup(m, ASM_EXP);
                return false;
            }
            if (!tokenCheck(OP_COLON)) {
                cleanup(m, ASM_EXP);
                return false;
            }
            if (!parseAsmExp()) {
                cleanup(m, ASM_EXP);
                return false;
            }
        }
        exit_section_modified(builder, m, ASM_EXP, true);
        return true;
    }

    /**
     * Parses an AsmInstruction
     * <p>
     * $(GRAMMAR $(RULEDEF asmInstruction):
     *   $(LITERAL Identifier)
     * | $(LITERAL 'align') $(LITERAL IntegerLiteral)
     * | $(LITERAL 'align') $(LITERAL Identifier)
     * | $(LITERAL Identifier) $(LITERAL ':') $(RULE asmInstruction)
     * | $(LITERAL Identifier) $(RULE operands)
     * | $(LITERAL 'in') $(RULE operands)
     * | $(LITERAL 'out') $(RULE operands)
     * | $(LITERAL 'int') $(RULE operands)
     * | $(LITERAL ';')
     * ;)
     */
    boolean parseAsmInstruction() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
//            AsmInstruction node = allocator.make!AsmInstruction;
        final Marker m = enter_section_modified(builder);
        if (currentIs(OP_SCOLON)) {
            warn("Empty asm instruction");
            exit_section_modified(builder, m, ASM_INSTRUCTION, true);
            return true;
        }
        if (currentIs(KW_ALIGN)) {
            advance(); // align
            if (currentIsOneOf(INTEGER_LITERAL, ID)) {
                if (!currentIs(OP_SCOLON))
                {
                    error("`;` expected.");
                    if (moreTokens())
                        advance();
                    cleanup(m, ASM_INSTRUCTION);
                    return false;
                }
            } else {
                error("Identifier or integer literal expected.");
                cleanup(m, ASM_INSTRUCTION);
                return false;
            }
        } else if (currentIsOneOf(ID, KW_IN, KW_OUT, KW_INT)) {
            final IElementType t = advance();
            if (t.equals(ID) && currentIs(OP_COLON)) {
                advance(); // :
                if (currentIs(OP_SCOLON)) {
                    exit_section_modified(builder, m, ASM_INSTRUCTION, true);
                    return true;
                }
                if (!parseAsmInstruction()) {
                    cleanup(m, ASM_INSTRUCTION);
                    return false;
                }
            } else if (!currentIs(OP_SCOLON))
                if (!parseOperands()) {
                    cleanup(m, ASM_INSTRUCTION);
                    return false;
                }
        }
        exit_section_modified(builder, m, ASM_INSTRUCTION, true);
        return true;
    }

    /**
     * Parses an AsmLogAndExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmLogAndExp):
     *   $(RULE asmOrExp)
     * | $(RULE asmLogAndExp) $(LITERAL '&&') $(RULE asmOrExp)
     * ;)
     */
    boolean parseAsmLogAndExp() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean result = parseLeftAssocBinaryExpression(toParseExpression, "AsmLogAndExp", "AsmOrExp", OP_BOOL_AND);
        if (!toParseExpression.element) {
            m.drop();
//            beginnings.remove(m);
            return result;
        }
        exit_section_modified(builder, m, ASM_LOG_AND_EXP, result);
        return result;
    }

    /**
     * Parses an AsmLogOrExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmLogOrExp):
     *   $(RULE asmLogAndExp)
     * | $(RULE asmLogOrExp) '||' $(RULE asmLogAndExp)
     * ;)
     */
    boolean parseAsmLogOrExp() {
        final Marker m = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean result = parseLeftAssocBinaryExpression(toParseExpression, "AsmLogOrExp", "AsmLogAndExp", OP_BOOL_OR);
        if (!toParseExpression.element) {
            m.drop();
//            beginnings.remove(m);
            return result;
        }
        exit_section_modified(builder, m, ASM_LOG_OR_EXP, result);
        return result;
    }

    /**
     * Parses an AsmMulExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmMulExp):
     *   $(RULE asmBrExp)
     * | $(RULE asmMulExp) ($(LITERAL '*') | $(LITERAL '/') | $(LITERAL '%')) $(RULE asmBrExp)
     * ;)
     */
    boolean parseAsmMulExp() {
        final Marker m = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean result = parseLeftAssocBinaryExpression(toParseExpression, "AsmMulExp", "AsmBrExp", OP_ASTERISK, OP_DIV, OP_MOD);
        if (!toParseExpression.element) {
            m.drop();
//            beginnings.remove(m);
            return result;
        }
        exit_section_modified(builder, m, ASM_MUL_EXP, result);
        return result;
    }

    /**
     * Parses an AsmOrExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmOrExp):
     *   $(RULE asmXorExp)
     * | $(RULE asmOrExp) $(LITERAL '|') $(RULE asmXorExp)
     * ;)
     */
    boolean parseAsmOrExp() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean result = parseLeftAssocBinaryExpression(toParseExpression, "AsmOrExp", "AsmXorExp", OP_OR);
        if (!toParseExpression.element) {
            m.drop();
//            beginnings.remove(m);
            return result;
        }
        exit_section_modified(builder, m, ASM_OR_EXP, result);
        return result;
    }

    /**
     * Parses an AsmPrimaryExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmPrimaryExp):
     *   $(LITERAL IntegerLiteral)
     * | $(LITERAL FloatLiteral)
     * | $(LITERAL StringLiteral)
     * | $(RULE register)
     * | $(RULE register : AsmExp)
     * | $(RULE identifierChain)
     * | $(LITERAL '$')
     * | $(LITERAL 'this')
     * | $(LITERAL '__LOCAL_SIZE')
     * ;)
     */
    boolean parseAsmPrimaryExp() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
//            AsmPrimaryExp node = allocator.make!AsmPrimaryExp();
        final Marker m = enter_section_modified(builder);
        final IElementType i = current();
        if (i.equals(FLOAT_LITERAL) || i.equals(INTEGER_LITERAL) || i.equals(DOUBLE_QUOTED_STRING) || i.equals(OP_DOLLAR) || i.equals(KW_THIS)) {
            advance();
        } else if (i.equals(ID)) {
            if ((Sets.newHashSet(Arrays.asList(REGISTER_NAMES))).contains(currentText())) {
//                trace("Found register");
                if (!parseRegister()) {
                    cleanup(m, ASM_PRIMARY_EXP);
                    return false;
                }
                if (current().equals(OP_COLON)) {
                    advance();
                    if (!parseAsmExp()) {
                        cleanup(m, ASM_PRIMARY_EXP);
                        return false;
                    }
                }
            } else {
                if (!parseIdentifierChain()) {
                    cleanup(m, ASM_PRIMARY_EXP);
                    return false;
                }
            }
        } else {
            error("Float literal, integer literal, `$`, `this` or identifier expected.");
            cleanup(m, ASM_PRIMARY_EXP);
            return false;
        }
        exit_section_modified(builder, m, ASM_PRIMARY_EXP, true);
        return true;
    }

    /**
     * Parses an AsmRelExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmRelExp):
     * $(RULE asmShiftExp)
     * | $(RULE asmRelExp) (($(LITERAL '<') | $(LITERAL '<=') | $(LITERAL '>') | $(LITERAL '>=')) $(RULE asmShiftExp))?
     * ;)
     */
    boolean parseAsmRelExp() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean result = parseLeftAssocBinaryExpression(toParseExpression, "AsmRelExp", "AsmShiftExp", OP_LESS, OP_LESS_EQ, OP_GT, OP_GT_EQ);//todo refactor this to make this shorter
        if (!toParseExpression.element) {
            m.drop();
//            beginnings.remove(m);
            return result;
        }
        exit_section_modified(builder, m, ASM_REL_EXP, result);
        return result;
    }

    /**
     * Parses an AsmShiftExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmShiftExp):
     * $(RULE asmAddExp)
     * $(RULE asmShiftExp) ($(LITERAL '<<') | $(LITERAL '>>') | $(LITERAL '>>>')) $(RULE asmAddExp)
     * ;)
     */
    boolean parseAsmShiftExp() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean result = parseLeftAssocBinaryExpression(toParseExpression, "AsmShiftExp", "AsmAddExp", OP_SH_LEFT, OP_SH_RIGHT, OP_USH_RIGHT);
        if (!toParseExpression.element) {
            m.drop();
//            beginnings.remove(m);
            return result;
        }
        exit_section_modified(builder, m, ASM_SHIFT_EXP, result);
        return result;
    }

    /**
     * Parses an AsmStatement
     * <p>
     * $(GRAMMAR $(RULEDEF asmStatement):
     * $(LITERAL 'asm') $(RULE functionAttributes)? $(LITERAL '{') $(RULE asmInstruction)+ $(LITERAL '}')
     * ;)
     */
    boolean parseAsmStatement() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
//            AsmStatement node = allocator.make!AsmStatement;
        final Marker m = enter_section_modified(builder);
        advance(); // asm
        while (isAttribute()) {
            if (!parseFunctionAttribute(true)) {
                error("Function attribute or '{' expected");
                cleanup(m, ASM_STATEMENT);
                return false;
            }
        }
        expect(OP_BRACES_LEFT);
        while (moreTokens() && !currentIs(OP_BRACES_RIGHT)) {
            if (!parseAsmInstruction()) {
                // TODO: here libdparse handle gcc asm instructions
                cleanup(m, ASM_STATEMENT);
                return false;
            } else {
                expect(OP_SCOLON);
            }
        }
        expect(OP_BRACES_RIGHT);
        exit_section_modified(builder, m, ASM_STATEMENT, true);
        return true;
    }

    /**
     * Parses an AsmTypePrefix
     * <p>
     * Note that in the following grammar definition the first identifier must
     * be "near", "far", "word", "dword", or "qword". The second identifier must
     * be "ptr".
     * <p>
     * $(GRAMMAR $(RULEDEF asmTypePrefix):
     *   $(LITERAL Identifier) $(LITERAL Identifier)?
     * | $(LITERAL 'byte') $(LITERAL Identifier)?
     * | $(LITERAL 'short') $(LITERAL Identifier)?
     * | $(LITERAL 'int') $(LITERAL Identifier)?
     * | $(LITERAL 'float') $(LITERAL Identifier)?
     * | $(LITERAL 'double') $(LITERAL Identifier)?
     * | $(LITERAL 'real') $(LITERAL Identifier)?
     * ;)
     */
    boolean parseAsmTypePrefix() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_modified(builder);
        final IElementType i = current();
        if (i.equals(ID) || i.equals(KW_BYTE) || i.equals(KW_SHORT) || i.equals(KW_INT) || i.equals(KW_FLOAT) || i.equals(KW_DOUBLE) || i.equals(KW_REAL)) {
            final String tokenText = builder.getTokenText();
            final IElementType t = advance();
            if (t.equals(ID))
                switch (tokenText) {
                    case "near":
                    case "far":
                    case "word":
                    case "dword":
                    case "qword": {
                        break;
                    }
                    default: {
                        error("ASM type node expected");
                        exit_section_modified(builder, m, ASM_TYPE_PREFIX, true);//todo
                        return false;
                    }
                }
            if (currentIs(ID) && builder.getTokenText().equals("ptr")) {
                advance();
            }
            exit_section_modified(builder, m, ASM_TYPE_PREFIX, true);
            return true;
        } else {
            error("Expected an identifier, 'byte', 'short', 'int', 'float', 'double', or 'real'");
            cleanup(m, ASM_TYPE_PREFIX);
            return false;
        }
    }

    /**
     * Parses an AsmUnaExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmUnaExp):
     *   $(RULE asmTypePrefix) $(RULE asmExp)
     * | $(LITERAL Identifier) $(RULE asmExp)
     * | $(LITERAL '+') $(RULE asmUnaExp)
     * | $(LITERAL '-') $(RULE asmUnaExp)
     * | $(LITERAL '!') $(RULE asmUnaExp)
     * | $(LITERAL '~') $(RULE asmUnaExp)
     * | $(RULE asmPrimaryExp)
     * ;)
     */
    boolean parseAsmUnaExp() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
//            AsmUnaExp node = allocator.make!AsmUnaExp();
        final Marker m = enter_section_modified(builder);
        final IElementType i = current();
        if (i.equals(OP_PLUS) || i.equals(OP_MINUS) || i.equals(OP_NOT) || i.equals(OP_TILDA)) {
            advance();
            if (!parseAsmUnaExp()) {
                cleanup(m, ASM_UNA_EXP);
                return false;
            }
        } else if (i.equals(KW_BYTE) || i.equals(KW_SHORT) || i.equals(KW_INT) || i.equals(KW_FLOAT) || i.equals(KW_DOUBLE) || i.equals(KW_REAL)) {
            if (!typePrefix(m)) return false;
        } else if (i.equals(ID)) {
            switch (builder.getTokenText()) {
                case "offsetof":
                case "seg": {
                    advance();
                    if (!parseAsmExp()) {
                        cleanup(m, ASM_UNA_EXP);
                        return false;
                    }
                    break;
                }
                case "near":
                case "far":
                case "word":
                case "dword":
                case "qword": {
                    if (!typePrefix(m)) return false;
                    break;
                }
                default: {
                    if (!outerDefault(m)) return false;
                    break;
                }
            }

        } else {
            if (!outerDefault(m)) return false;
        }
        exit_section_modified(builder, m, ASM_UNA_EXP, true);
        return true;
    }

    private boolean outerDefault(final Marker m) {
        if (!parseAsmPrimaryExp()) {
            cleanup(m, ASM_UNA_EXP);
            return false;
        }
        return true;
    }

    private boolean typePrefix(final Marker m) {
        if (!parseAsmTypePrefix()) {
            cleanup(m, ASM_UNA_EXP);
            return false;
        }
        if (!parseAsmExp()) {
            cleanup(m, ASM_UNA_EXP);
            return false;
        }
        return true;
    }

    /**
     * Parses an AsmXorExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmXorExp):
     * $(RULE asmAndExp)
     * | $(RULE asmXorExp) $(LITERAL '^') $(RULE asmAndExp)
     * ;)
     */
    boolean parseAsmXorExp() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean result = parseLeftAssocBinaryExpression(toParseExpression, "AsmXorExp", "AsmAndExp", OP_XOR);
        if (!toParseExpression.element) {
            m.drop();
//            beginnings.remove(m);
            return result;
        }
        exit_section_modified(builder, m, ASM_XOR_EXP, result);
        return result;
    }

    /**
     * Parses an AssertArguments
     *
     * $(GRAMMAR $(RULEDEF assertArguments):
     *     $(RULE assignExpression) ($(LITERAL ',') ($(RULE assignExpression))* $(LITERAL ',')?
     *     ;)
     */
    public boolean parseAssertArguments() {
        final Marker m = enter_section_modified(builder);
        if (!parseAssignExpression()) {
            cleanup(m, ASSERT_ARGUMENTS);
            return false;
        }
        if (currentIs(OP_COMMA)) {
            advance();
        }
        if (currentIs(OP_PAR_RIGHT)) {
            exit_section_modified(builder, m, ASSERT_ARGUMENTS, true);
            return true;
        }
        while (!currentIs(OP_PAR_RIGHT)) {
            if (!parseAssignExpression()) {
                cleanup(m, ASSERT_ARGUMENTS);
                return false;
            }
            if (currentIs(OP_COMMA)) {
                advance();
            }
        }
        exit_section_modified(builder, m, ASSERT_ARGUMENTS, true);
        return true;
    }

    /**
     * Parses an AssertExpression
     * <p>
     * $(GRAMMAR $(RULEDEF assertExpression):
     * $(LITERAL 'assert') $(LITERAL '$(LPAREN)') $(RULE assertArguments) ($(LITERAL ',') $(RULE assignExpression))? $(LITERAL ',')? $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseAssertExpression() {
//            Marker m = enter_section_(builder);
        final Marker m = enter_section_modified(builder);
        advance(); // "assert"
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, ASSERT_EXPRESSION);
            return false;
        }
        if (!parseAssertArguments()) {
            cleanup(m, ASSERT_EXPRESSION);
            return false;
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, ASSERT_EXPRESSION);
            return false;
        }
        exit_section_modified(builder, m, ASSERT_EXPRESSION, true);
        return true;
    }

    /**
     * Parses an AssignExpression
     * <p>
     * $(GRAMMAR $(RULEDEF assignExpression):
     * $(RULE ternaryExpression) ($(RULE assignOperator) $(RULE assignExpression))?
     * ;
     * $(RULEDEF assignOperator):
     * $(LITERAL '=')
     * | $(LITERAL '>>>=')
     * | $(LITERAL '>>=')
     * | $(LITERAL '<<=')
     * | $(LITERAL '+=')
     * | $(LITERAL '-=')
     * | $(LITERAL '*=')
     * | $(LITERAL '%=')
     * | $(LITERAL '&=')
     * | $(LITERAL '/=')
     * | $(LITERAL '|=')
     * | $(LITERAL '^^=')
     * | $(LITERAL '^=')
     * | $(LITERAL '~=')
     * ;)
     */
    boolean parseAssignExpression() {
        final Marker m = enter_section_modified(builder);
        if (!moreTokens()) {
            error("Assign expression expected instead of EOF");
            exit_section_modified(builder, m, ASSIGN_EXPRESSION, true);
            return false;
        }
        final boolean ternary = parseTernaryExpression();
        if (!ternary) {
            cleanup(m, ASSIGN_EXPRESSION);
            return false;
        }
        if (currentIsOneOf(OP_EQ, OP_USH_RIGHT_EQ, OP_SH_RIGHT_EQ, OP_SH_LEFT_EQ, OP_PLUS_EQ, OP_MINUS_EQ, OP_MUL_EQ, OP_MOD_EQ, OP_AND_EQ, OP_DIV_EQ, OP_OR_EQ, OP_POW_EQ, OP_XOR_EQ, OP_TILDA_EQ)) {
            advance();
            if (!parseAssignExpression()) {
                cleanup(m, ASSIGN_EXPRESSION);
                return false;
            }
            exit_section_modified(builder, m, ASSIGN_EXPRESSION, true);
            return true;
        }
        exit_section_modified(builder, m, ASSIGN_EXPRESSION, true);
        return true;
    }

    /**
     * Parses an AssocArrayLiteral
     * <p>
     * $(GRAMMAR $(RULEDEF assocArrayLiteral):
     * $(LITERAL '[') $(RULE keyValuePairs) $(LITERAL ']')
     * ;)
     */
    boolean parseAssocArrayLiteral() {
        final Marker m = enter_section_modified(builder);
        final boolean result = simpleParse("AssocArrayLiteral", OP_BRACKET_LEFT, "keyValuePairs|parseKeyValuePairs", OP_BRACKET_RIGHT);// todo refactor to simplify this
        exit_section_modified(builder, m, ASSOC_ARRAY_LITERAL, result);
        return result;
    }

    private boolean simpleParse(final String nodeType, final Object... parts) {
        //open marker for type
        final Marker m = enter_section_modified(builder);
        final boolean result = simpleParseItems(parts);
        exit_section_modified(builder, m, nodeTypeToIElementType(nodeType), result);
        return result;
    }

    private boolean simpleParseItems(final Object... items) {
        for (final Object item : items) {
            if (item instanceof IElementType) {
                if (!simpleParseItemsSingle((IElementType) item)) {
                    return false;
                }
            } else if (item instanceof String) {
                if (!simpleParseItemsSingle((String) item)) {
                    return false;
                }
            } else {
                throw new IllegalArgumentException();
            }

        }
        return true;


    }

    private boolean simpleParseItemsSingle(final String item) {
        final int i = item.indexOf("|");
        final String first = item.substring(0, i);//unneeded, libdparse uses for building it's ast, but we don't need to
        final String second = item.substring(i + 1);
        final String name = second.replace("parse", "");
        return parseName(name);

    }

    private boolean simpleParseItemsSingle(final IElementType item) {
        return expect(item) != null;
    }

    /**
     * Parses an AtAttribute
     * <p>
     * $(GRAMMAR $(RULEDEF atAttribute):
     *   $(LITERAL '@') $(LITERAL Identifier)
     * | $(LITERAL '@') $(LITERAL Identifier) $(LITERAL '$(LPAREN)') $(RULE argumentList)? $(LITERAL '$(RPAREN)')
     * | $(LITERAL '@') $(LITERAL '$(LPAREN)') $(RULE argumentList) $(LITERAL '$(RPAREN)')
     * | $(LITERAL '@') $(RULE TemplateInstance)
     * ;)
     */
    boolean parseAtAttribute() {
        final Marker m = enter_section_modified(builder);
        final IElementType start = expect(OP_AT);
        if (start == null) {
            cleanup(m, AT_ATTRIBUTE);
            return false;
        }
        if (!moreTokens()) {
            error("`(`, or identifier expected");
            exit_section_modified(builder, m, AT_ATTRIBUTE, true);
            return false;
        }
        final IElementType i = current();
        if (i.equals(ID)) {
            if (peekIs(OP_NOT)) {
                if (!parseTemplateInstance()) {
                    cleanup(m, AT_ATTRIBUTE);
                    return false;
                }
            } else
                advance();
            if (currentIs(OP_PAR_LEFT)) {
                advance(); // (
                if (!currentIs(OP_PAR_RIGHT)) {
                    if (!parseArgumentList().first) {
                        cleanup(m, AT_ATTRIBUTE);
                        return false;
                    }
                }
                expect(OP_PAR_RIGHT);
            }
        } else if (i.equals(OP_PAR_LEFT)) {
            advance();
            if (!parseArgumentList().first) {
                cleanup(m, AT_ATTRIBUTE);
                return false;
            }
            expect(OP_PAR_RIGHT);
        } else {
            error("`(`, or identifier expected");
            exit_section_modified(builder, m, AT_ATTRIBUTE, true);
            return false;
        }
        exit_section_modified(builder, m, AT_ATTRIBUTE, true);
        return true;
    }

    /**
     * Parses an Attribute
     * <p>
     * $(GRAMMAR $(RULEDEF attribute):
     *   $(RULE pragmaExpression)
     * | $(RULE alignAttribute)
     * | $(RULE deprecated)
     * | $(RULE atAttribute)
     * | $(RULE linkageAttribute)
     * | $(LITERAL 'export')
     * | $(LITERAL 'package') ($(LITERAL "(") $(RULE identifierChain) $(LITERAL ")"))?
     * | $(LITERAL 'private')
     * | $(LITERAL 'protected')
     * | $(LITERAL 'public')
     * | $(LITERAL 'static')
     * | $(LITERAL 'extern')
     * | $(LITERAL 'abstract')
     * | $(LITERAL 'final')
     * | $(LITERAL 'override')
     * | $(LITERAL 'synchronized')
     * | $(LITERAL 'auto')
     * | $(LITERAL 'scope')
     * | $(LITERAL 'const')
     * | $(LITERAL 'immutable')
     * | $(LITERAL 'inout')
     * | $(LITERAL 'shared')
     * | $(LITERAL '__gshared')
     * | $(LITERAL 'nothrow')
     * | $(LITERAL 'pure')
     * | $(LITERAL 'ref')
     * | $(LITERAL 'throw')
     * ;)
     */
    boolean parseAttribute() {
        final Marker m = enter_section_modified(builder);
        final IElementType i = current();
        if (i.equals(KW_PRAGMA)) {
            if (!parsePragmaExpression()) {
                cleanup(m, ATTRIBUTE);
                return false;
            }
        } else if (i.equals(KW_DEPRECATED)) {
            if (!parseDeprecated()) {
                cleanup(m, ATTRIBUTE);
                return false;
            }
        } else if (i.equals(KW_ALIGN)) {
            if (!parseAlignAttribute()) {
                cleanup(m, ATTRIBUTE);
                return false;
            }
        } else if (i.equals(OP_AT)) {
            if (!parseAtAttribute()) {
                cleanup(m, ATTRIBUTE);
                return false;
            }
        } else if (i.equals(KW_PACKAGE)) {
            advance();
            if (currentIs(OP_PAR_LEFT)) {
                expect(OP_PAR_LEFT);
                if (!parseIdentifierChain()) {
                    cleanup(m, ATTRIBUTE);
                    return false;
                }
                expect(OP_PAR_RIGHT);
            }
        } else if (i.equals(KW_EXTERN)) {
            if (peekIs(OP_PAR_LEFT)) {
                if (!parseLinkageAttribute()) {
                    cleanup(m, ATTRIBUTE);
                    return false;
                }
            } else
                advance();
        } else if (i.equals(KW_PRIVATE) || i.equals(KW_PROTECTED) || i.equals(KW_PUBLIC) || i.equals(KW_EXPORT) || i.equals(KW_STATIC) || i.equals(KW_ABSTRACT) || i.equals(KW_FINAL) || i.equals(KW_OVERRIDE) || i.equals(KW_SYNCHRONIZED) || i.equals(KW_AUTO) || i.equals(KW_SCOPE) || i.equals(KW_CONST) || i.equals(KW_IMMUTABLE) || i.equals(KW_INOUT) || i.equals(KW_SHARED) || i.equals(KW___GSHARED) || i.equals(KW_NOTHROW) || i.equals(KW_PURE) || i.equals(KW_REF) || i.equals(KW_THROW)) {
            advance();
        } else {
            cleanup(m, ATTRIBUTE);
            return false;
        }
        exit_section_modified(builder, m, ATTRIBUTE, true);
        return true;
    }

    boolean parseAttributeDeclaration() {
        return parseAttributeDeclaration(true);
    }

    /**
     * Parses an AttributeDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF attributeDeclaration):
     * $(RULE attribute) $(LITERAL ':')
     * ;)
     */
    private boolean parseAttributeDeclaration(final boolean parseAttribute)//(Attribute attribute = null)
    {
        final Marker m = enter_section_modified(builder);
        if (parseAttribute) {
            parseAttribute();
        }
        expect(OP_COLON);
        exit_section_modified(builder, m, ATTRIBUTE_DECLARATION, true);
        return true;
    }

    /**
     * Parses an AutoDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF autoDeclaration):
     * $(RULE storageClass)+  $(RULE autoDeclarationPart) ($(LITERAL ',') $(RULE autoDeclarationPart))* $(LITERAL ';')
     * ;)
     */
    boolean parseAutoDeclaration() {
        final Marker m = enter_section_modified(builder);
        while (isStorageClass()) {
            if (!parseStorageClass()) {
                cleanup(m, AUTO_DECLARATION);
                return false;
            }
        }
        do {
            if (!parseAutoDeclarationPart()) {
                cleanup(m, AUTO_DECLARATION);
                return false;
            }
            if (currentIs(OP_COMMA))
                advance();
            else
                break;
        } while (moreTokens());
        final IElementType semi = expect(OP_SCOLON);
        if (semi == null) {
            cleanup(m, AUTO_DECLARATION);
            return false;
        }
        exit_section_modified(builder, m, AUTO_DECLARATION, true);
        return true;
    }

    /**
     * Parses an AutoDeclarationPart
     * <p>
     * $(GRAMMAR $(RULEDEF autoDeclarationPart):
     * $(LITERAL Identifier) $(RULE templateParameters)? $(LITERAL '=') $(RULE initializer)
     * ;)
     */
    boolean parseAutoDeclarationPart() {
//            auto part = allocator.make!AutoDeclarationPart;
        final Marker m = enter_section_modified(builder);
        final IElementType i = expect(ID);
        if (i == null) {
            cleanup(m, AUTO_DECLARATION_PART);
            return false;
        }
        if (currentIs(OP_PAR_LEFT)) {
            if (!parseTemplateParameters()) {
                cleanup(m, AUTO_DECLARATION_PART);
                return false;
            }
        }
        if (!tokenCheck(OP_EQ)) {
            cleanup(m, AUTO_DECLARATION_PART);
            return false;
        }
        if (!parseInitializer()) {
            cleanup(m, AUTO_DECLARATION_PART);
            return false;
        }
        exit_section_modified(builder, m, AUTO_DECLARATION_PART, true);
        return true;
    }

    /**
     * Parses a BlockStatement
     * <p>
     * $(GRAMMAR $(RULEDEF blockStatement):
     * $(LITERAL '{') $(RULE declarationsAndStatements)? $(LITERAL '}')
     * ;)
     */
    boolean parseBlockStatement() {
        final Marker m = enter_section_modified(builder);
        final IElementType openBrace = expect(OP_BRACES_LEFT);
        if (openBrace == null) {
            cleanup(m, BLOCK_STATEMENT);
            return false;
        }
        if (!currentIs(OP_BRACES_RIGHT)) {
            if (!parseDeclarationsAndStatements()) {
                cleanup(m, BLOCK_STATEMENT);
                return false;
            }
        }
        final IElementType closeBrace = expect(OP_BRACES_RIGHT);
        exit_section_modified(builder, m, BLOCK_STATEMENT, true);
        return true;
    }

    /**
     * Parses a BreakStatement
     * <p>
     * $(GRAMMAR $(RULEDEF breakStatement):
     * $(LITERAL 'break') $(LITERAL Identifier)? $(LITERAL ';')
     * ;)
     */
    boolean parseBreakStatement() {
        final Marker m = enter_section_modified(builder);
        expect(KW_BREAK);
        if (!moreTokens()) {
            cleanup(m, BREAK_STATEMENT);
            return false;
        }
        final IElementType i = current();
        if (i.equals(ID)) {
            advance();
            if (!tokenCheck(OP_SCOLON)) {
                cleanup(m, BREAK_STATEMENT);
                return false;
            }
        } else if (i.equals(OP_SCOLON)) {
            advance();
        } else {
            error("Identifier or semicolon expected following `break`");
            exit_section_modified(builder, m, BREAK_STATEMENT, true);
            return false;
        }
        exit_section_modified(builder, m, BREAK_STATEMENT, true);
        return true;
    }

    private boolean isProtection(final IElementType type) {
        return Protections.contains(type);
    }

    /**
     * Parses a BaseClass
     * <p>
     * $(GRAMMAR $(RULEDEF baseClass):
     * $(RULE type2)
     * ;)
     */
    boolean parseBaseClass() {
        final Marker m = enter_section_modified(builder);
        if (!moreTokens()) {
            cleanup(m, BASE_CLASS);
            return false;
        }
        if (isProtection(current())) {
            warn("Use of base class protection is deprecated.");
            advance();
        }
        if (!parseType2()) {
            cleanup(m, BASE_CLASS);
            return false;
        }
        exit_section_modified(builder, m, BASE_CLASS, true);
        return true;
    }

    /**
     * Parses a BaseClassList
     * <p>
     * $(GRAMMAR $(RULEDEF baseClassList):
     * $(RULE baseClass) ($(LITERAL ',') $(RULE baseClass))*
     * ;)
     */
    boolean parseBaseClassList() {
        final Marker m = enter_section_modified(builder);
        final boolean result = parseCommaSeparatedRule("BaseClassList", "BaseClass");
        exit_section_modified(builder, m, BASE_CLASS_LIST, result);
        return result;
    }

    /**
     * Parses an BuiltinType
     * <p>
     * $(GRAMMAR $(RULEDEF builtinType):
     *   $(LITERAL 'bool')
     * | $(LITERAL 'byte')
     * | $(LITERAL 'ubyte')
     * | $(LITERAL 'short')
     * | $(LITERAL 'ushort')
     * | $(LITERAL 'int')
     * | $(LITERAL 'int')
     * | $(LITERAL 'long')
     * | $(LITERAL 'ulong')
     * | $(LITERAL 'char')
     * | $(LITERAL 'wchar')
     * | $(LITERAL 'dchar')
     * | $(LITERAL 'float')
     * | $(LITERAL 'double')
     * | $(LITERAL 'real')
     * | $(LITERAL 'ifloat')
     * | $(LITERAL 'idouble')
     * | $(LITERAL 'ireal')
     * | $(LITERAL 'cfloat')
     * | $(LITERAL 'cdouble')
     * | $(LITERAL 'creal')
     * | $(LITERAL 'void')
     * ;)
     */
    private IElementType parseBuiltinType() {
        assert isBasicType(current());
        final Marker marker = enter_section_(builder);
        final IElementType type = advance();
        exit_section_(builder, marker, BUILTIN_TYPE, true);
        return type;
    }

    /**
     * Parses a CaseRangeStatement
     * <p>
     * $(GRAMMAR $(RULEDEF caseRangeStatement):
     * $(LITERAL 'case') $(RULE assignExpression) $(LITERAL ':') $(LITERAL '...') $(LITERAL 'case') $(RULE assignExpression) $(LITERAL ':') $(RULE declarationsAndStatements)
     * ;)
     */
    boolean parseCaseRangeStatement(Marker m) {
//            assert (low != null);
//            node.low = low;
        if (!tokenCheck(OP_COLON)) {
            cleanup(m, CASE_RANGE_STATEMENT);
            return false;
        }
        if (!tokenCheck(OP_DDOT)) {
            cleanup(m, CASE_RANGE_STATEMENT);
            return false;
        }
        expect(KW_CASE);
        if (!parseAssignExpression()) {
            cleanup(m, CASE_RANGE_STATEMENT);
            return false;
        }
        final IElementType colon = expect(OP_COLON);
        if (colon == null) {
            cleanup(m, CASE_RANGE_STATEMENT);
            return false;
        }
        if (!parseDeclarationsAndStatements(false)) {
            cleanup(m, CASE_RANGE_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, CASE_RANGE_STATEMENT, true);
        return true;
    }

    boolean parseCaseRangeStatement() {
        Marker m = enter_section_modified(builder);
        return parseCaseRangeStatement(m);
    }

    /**
     * Parses an CaseStatement
     * <p>
     * $(GRAMMAR $(RULEDEF caseStatement):
     * $(LITERAL 'case') $(RULE argumentList) $(LITERAL ':') $(RULE declarationsAndStatements)
     * ;)
     */
    boolean parseCaseStatement(Marker m) {
        final IElementType colon = expect(OP_COLON);
        if (colon == null) {
            cleanup(m, CASE_STATEMENT);
            return false;
        }
        if (!parseDeclarationsAndStatements(false)) {
            cleanup(m, CASE_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, CASE_STATEMENT, true);
        return true;
    }

    boolean parseCaseStatement() {
        final Marker m = enter_section_modified(builder);
        return parseCaseStatement(m);
    }

    private boolean parseDeclarationsAndStatements() {
        return parseDeclarationsAndStatements(true);
    }

    /**
     * Parses a CastExpression
     * <p>
     * $(GRAMMAR $(RULEDEF castExpression):
     * $(LITERAL 'cast') $(LITERAL '$(LPAREN)') ($(RULE type) | $(RULE castQualifier))? $(LITERAL '$(RPAREN)') $(RULE unaryExpression)
     * ;)
     */
    boolean parseCastExpression() {
        final Marker m = enter_section_modified(builder);
        expect(KW_CAST);
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, CAST_EXPRESSION);
            return false;
        }
        if (!currentIs(OP_PAR_RIGHT)) {
            if (isCastQualifier()) {
                if (!parseCastQualifier()) {
                    cleanup(m, CAST_EXPRESSION);
                    return false;
                }
            } else {
                if (!parseType().first) {
                    cleanup(m, CAST_EXPRESSION);
                    return false;
                }
            }
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, CAST_EXPRESSION);
            return false;
        }
        if (!parseUnaryExpression()) {
            cleanup(m, CAST_EXPRESSION);
            return false;
        }
        exit_section_modified(builder, m, CAST_EXPRESSION, true);
        return true;
    }

    /**
     * Parses a CastQualifier
     * <p>
     * $(GRAMMAR $(RULEDEF castQualifier):
     *   $(LITERAL 'const')
     * | $(LITERAL 'const') $(LITERAL 'shared')
     * | $(LITERAL 'immutable')
     * | $(LITERAL 'inout')
     * | $(LITERAL 'inout') $(LITERAL 'shared')
     * | $(LITERAL 'shared')
     * | $(LITERAL 'shared') $(LITERAL 'const')
     * | $(LITERAL 'shared') $(LITERAL 'inout')
     * ;)
     */
    boolean parseCastQualifier() {
        if (!moreTokens()) {
            return false;
        }
        final Marker marker = enter_section_(builder);
        final IElementType i = current();
        if (i.equals(KW_INOUT) || i.equals(KW_CONST)) {
            advance();
            if (currentIs(KW_SHARED))
                advance();
        } else if (i.equals(KW_SHARED)) {
            advance();
            if (currentIsOneOf(KW_CONST, KW_INOUT))
                advance();
        } else if (i.equals(KW_IMMUTABLE)) {
            advance();
        } else {
            error("`const`, `immutable`, `inout`, or `shared` expected");
            return false;
        }
        exit_section_modified(builder, marker, CAST_QUALIFIER, true);
        return true;
    }

    /**
     * Parses a Catch
     * <p>
     * $(GRAMMAR $(RULEDEF catch):
     * $(LITERAL 'catch') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL Identifier)? $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement)
     * ;)
     */
    boolean parseCatch() {
        final Marker m = enter_section_modified(builder);
        expect(KW_CATCH);
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, CATCH);
            return false;
        }
        if (!parseType().first) {
            cleanup(m, CATCH);
            return false;
        }
        if (currentIs(ID)) {
            advance();
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, CATCH);
            return false;
        }
        if (!parseDeclarationOrStatement()) {
            cleanup(m, CATCH);
            return false;
        }
        exit_section_modified(builder, m, CATCH, true);
        return true;
    }

    /**
     * Parses a Catches
     * <p>
     * $(GRAMMAR $(RULEDEF catches):
     * $(RULE catch)+
     * | $(RULE catch)* $(RULE lastCatch)
     * ;)
     */
    boolean parseCatches() {
        final Marker m = enter_section_modified(builder);
        while (moreTokens()) {
            if (!currentIs(KW_CATCH))
                break;
            if (peekIs(OP_PAR_LEFT)) {
                if (!parseCatch()) {
                    cleanup(m, CATCHES);
                    return false;
                }
            } else {
                if (!parseLastCatch()) {
                    cleanup(m, CATCHES);
                    return false;
                }
                break;
            }
        }
        exit_section_modified(builder, m, CATCHES, true);
        return true;
    }

    /**
     * Parses a ClassDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF classDeclaration):
     *   $(LITERAL 'class') $(LITERAL Identifier) $(LITERAL ';')
     * | $(LITERAL 'class') $(LITERAL Identifier) ($(LITERAL ':') $(RULE baseClassList))? $(RULE structBody)
     * | $(LITERAL 'class') $(LITERAL Identifier) $(RULE templateParameters) $(RULE consraint)? ($(RULE structBody) | $(LITERAL ';'))
     * | $(LITERAL 'class') $(LITERAL Identifier) $(RULE templateParameters) $(RULE consraint)? ($(LITERAL ':') $(RULE baseClassList))? $(RULE structBody)
     * | $(LITERAL 'class') $(LITERAL Identifier) $(RULE templateParameters) ($(LITERAL ':') $(RULE baseClassList))? $(RULE raint)? $(RULE structBody)
     * ;)
     */
    boolean parseClassDeclaration() {
        final Marker m = enter_section_modified(builder);
        expect(KW_CLASS);
        final boolean result = parseInterfaceOrClass();
        exit_section_modified(builder, m, CLASS_DECLARATION, result);
        return result;
    }

    /**
     * Parses a CmpExpression
     * <p>
     * $(GRAMMAR $(RULEDEF cmpExpression):
     *   $(RULE shiftExpression)
     * | $(RULE equalExpression)
     * | $(RULE identityExpression)
     * | $(RULE relExpression)
     * | $(RULE inExpression)
     * ;)
     */
    boolean parseCmpExpression() {
        final Marker m = enter_section_modified(builder);
        final boolean shift = parseShiftExpression();
        if (!shift) {
            cleanup(m, CMP_EXPRESSION);
            return false;
        }
        if (!moreTokens()) {
            exit_section_modified(builder, m, CMP_EXPRESSION, true);
            return shift;
        }
        final IElementType i = current();
        if (i.equals(KW_IS)) {
            if (!parseIdentityExpression(false)) {
                cleanup(m, CMP_EXPRESSION);
                return false;
            }
            exit_section_modified(builder, m, CMP_EXPRESSION, true);
            return true;
        } else if (i.equals(KW_IN)) {
            if (!parseInExpression(false)) {
                cleanup(m, CMP_EXPRESSION);
                return false;
            }
            exit_section_modified(builder, m, CMP_EXPRESSION, true);
            return true;
        } else if (i.equals(OP_NOT)) {
            if (peekIs(KW_IS)) {
                if (!parseIdentityExpression(false)) {
                    cleanup(m, CMP_EXPRESSION);
                    return false;
                }
            } else if (peekIs(KW_IN))
                if (!parseInExpression(false)) {
                    cleanup(m, CMP_EXPRESSION);
                    return false;
                }
            exit_section_modified(builder, m, CMP_EXPRESSION, true);
            return true;
        } else if (i.equals(OP_LESS) || i.equals(OP_LESS_EQ) || i.equals(OP_GT) || i.equals(OP_GT_EQ) || i.equals(OP_UNORD_EQ) || i.equals(OP_UNORD) || i.equals(OP_LESS_GR) || i.equals(OP_LESS_GR_EQ) || i.equals(OP_NOT_GR) || i.equals(OP_NOT_GR_EQ) || i.equals(OP_NOT_LESS) || i.equals(OP_NOT_LESS_EQ)) {
            if (!parseRelExpression(false)) {
                cleanup(m, CMP_EXPRESSION);
                return false;
            }
            exit_section_modified(builder, m, CMP_EXPRESSION, true);
            return true;
        } else if (i.equals(OP_EQ_EQ) || i.equals(OP_NOT_EQ)) {
            if (!parseEqualExpression(false)) {
                cleanup(m, CMP_EXPRESSION);
                return false;
            }
            exit_section_modified(builder, m, CMP_EXPRESSION, true);
            return true;
        } else {
            exit_section_modified(builder, m, CMP_EXPRESSION, true);
            return true;
        }
    }

    private Marker enter_section_modified(final PsiBuilder builder) {
        final Marker marker = enter_section_(builder);
//        beginnings.put(marker, index);
        return marker;
    }

    /**
     * Parses a CompileCondition
     * <p>
     * $(GRAMMAR $(RULEDEF compileCondition):
     *   $(RULE versionCondition)
     * | $(RULE debugCondition)
     * | $(RULE staticIfCondition)
     * ;)
     */
    boolean parseCompileCondition() {
        final Marker m = enter_section_modified(builder);
        if (!moreTokens()) {
            cleanup(m, COMPILE_CONDITION);
            return false;
        }
        final IElementType i = current();
        if (i.equals(KW_VERSION)) {
            if (!parseVersionCondition()) {
                cleanup(m, COMPILE_CONDITION);
                return false;
            }
        } else if (i.equals(KW_DEBUG)) {
            if (!parseDebugCondition()) {
                cleanup(m, COMPILE_CONDITION);
                return false;
            }
        } else if (i.equals(KW_STATIC)) {
            if (!parseStaticIfCondition()) {
                cleanup(m, COMPILE_CONDITION);
                return false;
            }
        } else {
            error("`version`, `debug`, or `static` expected");
            exit_section_modified(builder, m, COMPILE_CONDITION, true);
            return false;
        }
        exit_section_modified(builder, m, COMPILE_CONDITION, true);
        return true;
    }

    /**
     * Parses a ConditionalDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF conditionalDeclaration):
     *   $(RULE compileCondition) $(RULE declaration)
     * | $(RULE compileCondition) $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
     * | $(RULE compileCondition) $(LITERAL ':') $(RULE declaration)+
     * | $(RULE compileCondition) $(RULE declaration) $(LITERAL 'else') $(LITERAL ':') $(RULE declaration)*
     * | $(RULE compileCondition) $(RULE declaration) $(LITERAL 'else') $(RULE declaration)
     * | $(RULE compileCondition) $(RULE declaration) $(LITERAL 'else') $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
     * | $(RULE compileCondition) $(LITERAL '{') $(RULE declaration)* $(LITERAL '}') $(LITERAL 'else') $(RULE declaration)
     * | $(RULE compileCondition) $(LITERAL '{') $(RULE declaration)* $(LITERAL '}') $(LITERAL 'else') $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
     * | $(RULE compileCondition) $(LITERAL '{') $(RULE declaration)* $(LITERAL '}') $(LITERAL 'else') $(LITERAL ':') $(RULE declaration)*
     * | $(RULE compileCondition) $(LITERAL ':') $(RULE declaration)+ $(LITERAL 'else') $(RULE declaration)
     * | $(RULE compileCondition) $(LITERAL ':') $(RULE declaration)+ $(LITERAL 'else') $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
     * | $(RULE compileCondition) $(LITERAL ':') $(RULE declaration)+ $(LITERAL 'else') $(LITERAL ':') $(RULE declaration)*
     * ;)
     */
    boolean parseConditionalDeclaration(final boolean strict, boolean inTemplateDeclaration) {
        final Marker m = enter_section_modified(builder);
        if (!parseCompileCondition()) {
            cleanup(m, CONDITIONAL_DECLARATION);
            return false;
        }
        if (currentIs(OP_COLON) || currentIs(OP_BRACES_LEFT)) {
            final boolean brace = advance().equals(OP_BRACES_LEFT);
            while (moreTokens() && !currentIs(OP_BRACES_RIGHT) && !currentIs(KW_ELSE)) {
                final Bookmark b = setBookmark();
//                    c = allocator.setCheckpoint();
                if (parseDeclaration(strict, true, inTemplateDeclaration)) {
                    abandonBookmark(b);
                } else {
                    goToBookmark(b);
//                        allocator.rollback(c);
                    cleanup(m, CONDITIONAL_DECLARATION);
                    return false;
                }
            }
            if (brace)
                if (!tokenCheck(OP_BRACES_RIGHT)) {
                    cleanup(m, CONDITIONAL_DECLARATION);
                    return false;
                }
        } else if (!parseDeclaration(strict, true, inTemplateDeclaration)) {
            cleanup(m, CONDITIONAL_DECLARATION);
            return false;
        }
        if (currentIs(KW_ELSE)) {
            advance();
        } else {
            exit_section_modified(builder, m, CONDITIONAL_DECLARATION, true);
            return true;
        }
        if (currentIs(OP_COLON) || currentIs(OP_BRACES_LEFT)) {
            final boolean brace = currentIs(OP_BRACES_LEFT);
            advance();
            while (moreTokens() && !currentIs(OP_BRACES_RIGHT))
                if (!parseDeclaration(strict, true, inTemplateDeclaration)) {
                    cleanup(m, CONDITIONAL_DECLARATION);
                    return false;
                }
            if (brace)
                if (!tokenCheck(OP_BRACES_RIGHT)) {
                    cleanup(m, CONDITIONAL_DECLARATION);
                    return false;
                }
        } else {
            if (!parseDeclaration(strict, true, inTemplateDeclaration)) {
                cleanup(m, CONDITIONAL_DECLARATION);
                return false;
            }
        }
        exit_section_modified(builder, m, CONDITIONAL_DECLARATION, true);
        return true;
    }

    /**
     * Parses a ConditionalStatement
     * <p>
     * $(GRAMMAR $(RULEDEF conditionalStatement):
     * $(RULE compileCondition) $(RULE declarationOrStatement) ($(LITERAL 'else') $(RULE declarationOrStatement))?
     * ;)
     */
    boolean parseConditionalStatement() {
        final Marker m = enter_section_modified(builder);
        if (!parseCompileCondition()) {
            cleanup(m, CONDITIONAL_STATEMENT);
            return false;
        }
        if (!parseDeclarationOrStatement()) {
            cleanup(m, CONDITIONAL_STATEMENT);
            return false;
        }
        if (currentIs(KW_ELSE)) {
            advance();
            if (!parseDeclarationOrStatement()) {
                cleanup(m, CONDITIONAL_STATEMENT);
                return false;
            }
        }
        exit_section_modified(builder, m, CONDITIONAL_STATEMENT, true);
        return true;
    }

    /**
     * Parses a Constraint
     * <p>
     * $(GRAMMAR $(RULEDEF raint):
     * $(LITERAL 'if') $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseConstraint() {
        final Marker m = enter_section_modified(builder);
        final IElementType ifToken = expect(KW_IF);
        if (ifToken == null) {
            cleanup(m, CONSTRAINT);
            return false;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, CONSTRAINT);
            return false;
        }
        if (!parseExpression()) {
            cleanup(m, CONSTRAINT);
            return false;
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, CONSTRAINT);
            return false;
        }
        exit_section_modified(builder, m, CONSTRAINT, true);
        return true;
    }

    /**
     * Parses a Constructor
     * <p>
     * $(GRAMMAR $(RULEDEF ructor):
     * $(LITERAL 'this') $(RULE templateParameters)? $(RULE parameters) $(RULE memberFunctionAttribute)* $(RULE constraint)? ($(RULE functionBody) | $(LITERAL ';'))
     * ;)
     */
    boolean parseConstructor() {
//            Constructor node = allocator.make!Constructor;
        final Marker m = enter_section_modified(builder);
        final IElementType t = expect(KW_THIS);
        if (t == null) {
            cleanup(m, CONSTRUCTOR);
            return false;
        }
        final IElementType p = peekPastParens();
        boolean isTemplate = false;
        if (p != null && p.equals(OP_PAR_LEFT)) {
            isTemplate = true;
            if (!parseTemplateParameters()) {
                cleanup(m, CONSTRUCTOR);
                return false;
            }
        }
        if (!parseParameters()) {
            cleanup(m, CONSTRUCTOR);
            return false;
        }
        while (moreTokens() && currentIsMemberFunctionAttribute())
            if (!parseMemberFunctionAttribute()) {
                cleanup(m, CONSTRUCTOR);
                return false;
            }
        if (isTemplate && currentIs(KW_IF))
            if (!parseConstraint()) {
                cleanup(m, CONSTRUCTOR);
                return false;
            }
        if (currentIs(OP_SCOLON))
            advance();
        else if (!parseFunctionBody()) {
            cleanup(m, CONSTRUCTOR);
            return false;
        }
        exit_section_modified(builder, m, CONSTRUCTOR, true);
        return true;
    }

    /**
     * Parses an ContinueStatement
     * <p>
     * $(GRAMMAR $(RULEDEF continueStatement):
     * $(LITERAL 'continue') $(LITERAL Identifier)? $(LITERAL ';')
     * ;)
     */
    boolean parseContinueStatement() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_CONTINUE)) {
            cleanup(m, CONTINUE_STATEMENT);
            return false;
        }
        if (!moreTokens()) {
            cleanup(m, CONTINUE_STATEMENT);
            return false;
        }
        final IElementType i = current();
        if (i.equals(ID)) {
            advance();
            if (!tokenCheck(OP_SCOLON)) {
                cleanup(m, CONTINUE_STATEMENT);
                return false;
            }
        } else if (i.equals(OP_SCOLON)) {
            advance();
        } else {
            error("Identifier or semicolon expected following \"continue\"");
            exit_section_modified(builder, m, CONTINUE_STATEMENT, true);
            return false;
        }
        exit_section_modified(builder, m, CONTINUE_STATEMENT, true);
        return true;
    }

    /**
     * Parses a DebugCondition
     * <p>
     * $(GRAMMAR $(RULEDEF debugCondition):
     * $(LITERAL 'debug') ($(LITERAL '$(LPAREN)') ($(LITERAL IntegerLiteral) | $(LITERAL Identifier)) $(LITERAL '$(RPAREN)'))?
     * ;)
     */
    boolean parseDebugCondition() {
        final Marker m = enter_section_modified(builder);
        final IElementType d = expect(KW_DEBUG);
        if (d == null) {
            cleanup(m, DEBUG_CONDITION);
            return false;
        }
        if (currentIs(OP_PAR_LEFT)) {
            advance();
            if (currentIsOneOf(INTEGER_LITERAL, ID)) {
                advance();
            } else {
                error("Integer literal or identifier expected");
                exit_section_modified(builder, m, DEBUG_CONDITION, true);
                return false;
            }
            if (!tokenCheck(OP_PAR_RIGHT)) {
                cleanup(m, DEBUG_CONDITION);
                return false;
            }
        }
        exit_section_modified(builder, m, DEBUG_CONDITION, true);
        return true;
    }

    /**
     * Parses a DebugSpecification
     * <p>
     * $(GRAMMAR $(RULEDEF debugSpecification):
     * $(LITERAL 'debug') $(LITERAL '=') ($(LITERAL Identifier) | $(LITERAL IntegerLiteral)) $(LITERAL ';')
     * ;)
     */
    boolean parseDebugSpecification() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_DEBUG)) {
            cleanup(m, DEBUG_SPECIFICATION);
            return false;
        }
        if (!tokenCheck(OP_EQ)) {
            cleanup(m, DEBUG_SPECIFICATION);
            return false;
        }
        if (currentIsOneOf(ID, INTEGER_LITERAL)) {
            advance();
        } else {
            error("Integer literal or identifier expected");
            exit_section_modified(builder, m, DEBUG_SPECIFICATION, true);
            return false;
        }
        if (!tokenCheck(OP_SCOLON)) {
            cleanup(m, DEBUG_SPECIFICATION);
            return false;
        }
        exit_section_modified(builder, m, DEBUG_SPECIFICATION, true);
        return true;
    }

    /**
     * Parses a Declaration
     * <p>
     * Params:
     *   strict = if true, do not return partial AST nodes on errors.
     *   mustBeDeclaration = do not parse as a declaration if it could be parsed as a function call
     *   inTemplateDeclaration = if this function is called from a templated context
     * <p>
     * $(GRAMMAR $(RULEDEF declaration):
     *   $(RULE attribute)* $(RULE declaration2)
     * | $(RULE attribute)+ $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
     * ;
     * $(RULEDEF declaration2):
     *   $(RULE aliasDeclaration)
     * | $(RULE aliasThisDeclaration)
     * | $(RULE anonymousEnumDeclaration)
     * | $(RULE attributeDeclaration)
     * | $(RULE classDeclaration)
     * | $(RULE conditionalDeclaration)
     * | $(RULE constructor)
     * | $(RULE debugSpecification)
     * | $(RULE destructor)
     * | $(RULE enumDeclaration)
     * | $(RULE eponymousTemplateDeclaration)
     * | $(RULE functionDeclaration)
     * | $(RULE importDeclaration)
     * | $(RULE interfaceDeclaration)
     * | $(RULE invariant)
     * | $(RULE mixinDeclaration)
     * | $(RULE mixinTemplateDeclaration)
     * | $(RULE pragmaDeclaration)
     * | $(RULE sharedStaticConstructor)
     * | $(RULE sharedStaticDestructor)
     * | $(RULE staticAssertDeclaration)
     * | $(RULE staticForeachDeclaration)
     * | $(RULE staticConstructor)
     * | $(RULE staticDestructor)
     * | $(RULE structDeclaration)
     * | $(RULE templateDeclaration)
     * | $(RULE unionDeclaration)
     * | $(RULE unittest)
     * | $(RULE variableDeclaration)
     * | $(RULE versionSpecification)
     * ;)
     */
    boolean parseDeclaration() {
        return parseDeclaration(false, false, false);
    }

    boolean parseDeclaration(final boolean strict) {
        return parseDeclaration(strict, false, false);
    }

    boolean parseDeclaration(final boolean strict, final boolean mustBeDeclaration) {
        return parseDeclaration(strict, mustBeDeclaration, false);
    }

    boolean parseDeclaration(final boolean strict, final boolean mustBeDeclaration, boolean inTemplateDeclaration) {
        final Marker m = enter_section_modified(builder);
        if (!moreTokens()) {
            error("declaration expected instead of EOF");
            exit_section_modified(builder, m, DECLARATION, true);
            return false;
        }
        boolean nodeAttributes = false;
        DecType isAuto;
        do {
            final Pair<DecType, Integer> pair = isAutoDeclaration();
            final int autoStorageClassStart = pair.second;
            isAuto = pair.first;
            if (isAuto != DecType.other && index == autoStorageClassStart)
                break;
            if (!isAttribute())
                break;
//                c = allocator.setCheckpoint();
            final boolean attr = parseAttribute();
            if (!attr) {
//                    allocator.rollback(c);
                break;
            }
            if (currentIs(OP_COLON)) {
                if (!parseAttributeDeclaration(false)) {
                    cleanup(m, DECLARATION);
                    return false;
                }
                exit_section_modified(builder, m, DECLARATION, true);
                return true;
            } else
                nodeAttributes = true;
        } while (moreTokens());

        if (!moreTokens()) {
            error("declaration expected instead of EOF");
            exit_section_modified(builder, m, DECLARATION, true);
            return false;
        }

        if (!currentIs(KW_ENUM)) {  // #165: handle enums separatly b/c of EponymousTemplateDeclaration
            if (isAuto == DecType.autoVar) {
                if (!parseVariableDeclaration(null, true)) {
                    cleanup(m, DECLARATION);
                    return false;
                }
                exit_section_modified(builder, m, DECLARATION, true);
                return true;
            } else if (isAuto == DecType.autoFun) {
                if (!parseFunctionDeclaration(null, true)) {
                    cleanup(m, DECLARATION);
                    return false;
                }
                exit_section_modified(builder, m, DECLARATION, true);
                return true;
            }
        }

        final IElementType idType = current();
        {
            if (idType.equals(KW_ASM) || idType.equals(KW_BREAK) || idType.equals(KW_CASE) || idType.equals(KW_CONTINUE) || idType.equals(KW_DEFAULT) || idType.equals(KW_DO) || idType.equals(KW_FOR) || idType.equals(KW_FOREACH) || idType.equals(KW_FOREACH_REVERSE) || idType.equals(KW_GOTO) || idType.equals(KW_IF) || idType.equals(KW_RETURN) || idType.equals(KW_SWITCH) || idType.equals(KW_THROW) || idType.equals(KW_TRY) || idType.equals(KW_WHILE) || idType.equals(KW_ASSERT)) {
                return declarationDefault(m);
            } else if (idType.equals(OP_SCOLON)) {
                // http://d.magic.com/issues/show_bug.cgi?id=4559
                warn("Empty declaration");
                advance();
            } else if (idType.equals(OP_BRACES_LEFT)) {
                if (!nodeAttributes) {
                    error("declaration expected instead of `{`");
                    exit_section_modified(builder, m, DECLARATION, true);
                    return false;
                }
                advance();
                while (moreTokens() && !currentIs(OP_BRACES_RIGHT)) {
//                        auto c = allocator.setCheckpoint();
                    if (!parseDeclaration(strict, false, inTemplateDeclaration)) {
//                            allocator.rollback(c);
                        cleanup(m, DECLARATION);
                        return false;
                    }
                }
                if (!tokenCheck(OP_BRACES_RIGHT)) {
                    cleanup(m, DECLARATION);
                    return false;
                }
            } else if (idType.equals(KW_ALIAS)) {
                if (startsWith(KW_ALIAS, ID, KW_THIS)) {
                    if (!parseAliasThisDeclaration()) {
                        cleanup(m, DECLARATION);
                        return false;
                    }
                } else if (!parseAliasDeclaration()) {
                    cleanup(m, DECLARATION);
                    return false;
                }
            } else if (idType.equals(KW_CLASS)) {
                if (!parseClassDeclaration()) {
                    cleanup(m, DECLARATION);
                    return false;
                }
            } else if (idType.equals(KW_THIS)) {
                if (!mustBeDeclaration && peekIs(OP_PAR_LEFT)) {
                    // Do not parse as a declaration if we could parse as a function call.
                    final Bookmark b = setBookmark();
                    final IElementType past = peekPastParens();
                    goToBookmark(b);
                    if (past != null && past.equals(OP_SCOLON)) {
                        cleanup(m, DECLARATION);
                        return false;
                    }
                }
                if (startsWith(KW_THIS, OP_PAR_LEFT, KW_THIS, OP_PAR_RIGHT)) {
                    if (!parsePostblit()) {
                        cleanup(m, DECLARATION);
                        return false;
                    }
                } else if (!parseConstructor()) {
                    cleanup(m, DECLARATION);
                    return false;
                }
            } else if (idType.equals(OP_TILDA)) {
                if (!parseDestructor()) {
                    cleanup(m, DECLARATION);
                    return false;
                }
            } else if (idType.equals(KW_ENUM)) {
                final Bookmark b = setBookmark();
                advance(); // enum
                if (currentIsOneOf(OP_COLON, OP_BRACES_LEFT)) {
                    goToBookmark(b);
                    if (!parseAnonymousEnumDeclaration()) {
                        cleanup(m, DECLARATION);
                        return false;
                    }
                } else if (currentIs(ID)) {
                    advance();
                    if (currentIs(OP_PAR_LEFT)) {
                        skipParens(); // ()
                        if (currentIs(OP_PAR_LEFT))
                            skipParens();
                        if (!currentIs(OP_EQ)) {
                            goToBookmark(b);
                            final boolean nodeFunctionDeclaration = parseFunctionDeclaration(null, true);
                            if (!nodeFunctionDeclaration) {
                                cleanup(m, DECLARATION);
                                return false;
                            }
                        } else {
                            goToBookmark(b);
                            if (!parseEponymousTemplateDeclaration()) {
                                cleanup(m, DECLARATION);
                                return false;
                            }
                        }
                    } else if (currentIsOneOf(OP_COLON, OP_BRACES_LEFT, OP_SCOLON)) {
                        goToBookmark(b);
                        if (!parseEnumDeclaration()) {
                            cleanup(m, DECLARATION);
                            return false;
                        }
                    } else {
                        final boolean eq = currentIs(OP_EQ);
                        goToBookmark(b);
                        if (!parseVariableDeclaration(null, eq)) {
                            cleanup(m, DECLARATION);
                            return false;
                        }
                    }
                } else {
                    final boolean s = isStorageClass();
                    goToBookmark(b);
                    if (!parseVariableDeclaration(null, s)) {
                        cleanup(m, DECLARATION);
                        return false;
                    }
                }
            } else if (idType.equals(KW_IMPORT)) {
                if (!parseImportDeclaration()) {
                    cleanup(m, DECLARATION);
                    return false;
                }
            } else if (idType.equals(KW_INTERFACE)) {
                if (!parseInterfaceDeclaration()) {
                    cleanup(m, DECLARATION);
                    return false;
                }
            } else if (idType.equals(KW_MIXIN)) {
                if (peekIs(KW_TEMPLATE)) {
                    if (!parseMixinTemplateDeclaration()) {
                        cleanup(m, DECLARATION);
                        return false;
                    }
                } else {
                    final Bookmark b = setBookmark();
                    advance();
                    if (currentIs(OP_PAR_LEFT)) {
                        final IElementType t = peekPastParens();
                        if (t != null && t.equals(OP_SCOLON)) {
                            goToBookmark(b);
                            if (!parseMixinDeclaration()) {
                                cleanup(m, DECLARATION);
                                return false;
                            }
                        } else {
                            goToBookmark(b);
                            return declarationDefault(m);
                        }
                    } else {
                        goToBookmark(b);
                        if (!parseMixinDeclaration()) {
                            cleanup(m, DECLARATION);
                            return false;
                        }
                    }
                }
            } else if (idType.equals(KW_PRAGMA)) {
                if (!parsePragmaDeclaration()) {
                    cleanup(m, DECLARATION);
                    return false;
                }
            } else if (idType.equals(KW_SHARED)) {
                if (startsWith(KW_SHARED, KW_STATIC, KW_THIS)) {
                    if (!parseSharedStaticConstructor()) {
                        cleanup(m, DECLARATION);
                        return false;
                    }
                } else if (startsWith(KW_SHARED, KW_STATIC, OP_TILDA)) {
                    if (!parseSharedStaticDestructor()) {
                        cleanup(m, DECLARATION);
                        return false;
                    }
                } else {
                    if (!type(m)) {
//                        cleanup(m);//already done
                        return false;
                    }
                }
            } else if (idType.equals(KW_STATIC)) {
                if (peekIs(KW_THIS)) {
                    if (!parseStaticConstructor()) {
                        cleanup(m, DECLARATION);
                        return false;
                    }
                } else if (peekIs(OP_TILDA)) {
                    if (!parseStaticDestructor()) {
                        cleanup(m, DECLARATION);
                        return false;
                    }
                } else if (peekIs(KW_IF)) {
                    if (!parseConditionalDeclaration(strict, inTemplateDeclaration)) {
                        cleanup(m, DECLARATION);
                        return false;
                    }
                } else if (peekIs(KW_ASSERT)) {
                    if (!parseStaticAssertDeclaration()) {
                        cleanup(m, DECLARATION);
                        return false;
                    }
                } else if (peekIs(KW_FOREACH) || peekIs(KW_FOREACH_REVERSE)) {
                    if (!parseStaticForeachDeclaration(inTemplateDeclaration)) {
                        cleanup(m, DECLARATION);
                        return false;
                    }
                } else {
                    if (!type(m)) {
//                        cleanup(m);//already done by type
                        return false;
                    }
                }

            } else if (idType.equals(KW_STRUCT)) {
                if (!parseStructDeclaration()) {
                    cleanup(m, DECLARATION);
                    return false;
                }
            } else if (idType.equals(KW_TEMPLATE)) {
                if (!parseTemplateDeclaration()) {
                    cleanup(m, DECLARATION);
                    return false;
                }
            } else if (idType.equals(KW_UNION)) {
                if (!parseUnionDeclaration()) {
                    cleanup(m, DECLARATION);
                    return false;
                }
            } else if (idType.equals(KW_INVARIANT)) {
                if (!parseInvariant()) {
                    cleanup(m, DECLARATION);
                    return false;
                }
            } else if (idType.equals(KW_UNITTEST)) {
                if (!parseUnittest()) {
                    cleanup(m, DECLARATION);
                    return false;
                }
            } else if (idType.equals(ID) && inTemplateDeclaration && peekIs(OP_EQ)) {
                if (!parseAliasAssign()) {
                    cleanup(m, DECLARATION);
                    return false;
                }
            } else if (idType.equals(ID) || idType.equals(OP_DOT) || idType.equals(KW_CONST) || idType.equals(KW_IMMUTABLE) || idType.equals(KW_INOUT) || idType.equals(KW_SCOPE) || idType.equals(KW_TYPEOF) || idType.equals(KW___VECTOR) || idType.equals(KW___TRAITS) || idType.equals(KW_INT) || idType.equals(KW_BOOL) || idType.equals(KW_BYTE) || idType.equals(KW_CDOUBLE) || idType.equals(KW_CENT) || idType.equals(KW_CFLOAT) || idType.equals(KW_CHAR) || idType.equals(KW_CREAL) || idType.equals(KW_DCHAR) || idType.equals(KW_DOUBLE) || idType.equals(KW_FLOAT) || idType.equals(KW_IDOUBLE) || idType.equals(KW_IFLOAT) || idType.equals(KW_IREAL) || idType.equals(KW_LONG) || idType.equals(KW_REAL) || idType.equals(KW_SHORT) || idType.equals(KW_UBYTE) || idType.equals(KW_UCENT) || idType.equals(KW_UINT) || idType.equals(KW_ULONG) || idType.equals(KW_USHORT) || idType.equals(KW_VOID) || idType.equals(KW_WCHAR)) {
                if (!type(m)) {
//                    cleanup(m);
                    return false;//no cleanup needed already done in type
                }
            } else if (idType.equals(KW_VERSION)) {
                if (peekIs(OP_PAR_LEFT)) {
                    if (!parseConditionalDeclaration(strict, inTemplateDeclaration)) {
                        cleanup(m, DECLARATION);
                        return false;
                    }
                } else if (peekIs(OP_EQ)) {
                    if (!parseVersionSpecification()) {
                        cleanup(m, DECLARATION);
                        return false;
                    }
                } else {
                    error("`=` or `(` expected following `version`");
                    exit_section_modified(builder, m, DECLARATION, true);
                    return false;
                }
            } else if (idType.equals(KW_DEBUG)) {
                if (peekIs(OP_EQ)) {
                    if (!parseDebugSpecification()) {
                        cleanup(m, DECLARATION);
                        return false;
                    }
                } else if (!parseConditionalDeclaration(strict, inTemplateDeclaration)) {
                    cleanup(m, DECLARATION);
                    return false;
                }
            } else {
                return declarationDefault(m);
            }
        }
        exit_section_modified(builder, m, DECLARATION, true);
        return true;
    }

    private boolean declarationDefault(final Marker m) {
        error("Declaration expected");
        exit_section_modified(builder, m, DECLARATION, true);
        return false;
    }

    private boolean type(@NotNull final Marker m) {
        final Pair<Boolean, Marker> t = parseType();
        if ((!t.first) || !currentIs(ID)) {
            if (t.first)
                error("no identifier for declarator");
            cleanup(m, DECLARATION);
            return false;
        }
        Bookmark b = setBookmark();
        b.m.done(DECLARATION); // must `done()` marker here to keep be able to allow use t.second.precede()
        if (!parseVariableDeclaration(t.second, false)) {
            goToBookmark(b);
            if (!parseFunctionDeclaration(t.second, false)) {
                cleanup(m, DECLARATION);
                return false;
            }
        } else {
            abandonBookmark(b);
        }
        /*if (peekIs(OP_PAR_LEFT)) {
           if (!parseFunctionDeclaration(t.second, false)) {
               cleanup(m, DECLARATION);
               return false;
           }
        } else if (!parseVariableDeclaration(t.second, false)) {
                cleanup(m, DECLARATION);
                return false;
        }*/
        return true;
    }

    /**
     * Parses DeclarationsAndStatements
     * <p>
     * $(GRAMMAR $(RULEDEF declarationsAndStatements):
     * $(RULE declarationOrStatement)+
     * ;)
     */
    boolean parseDeclarationsAndStatements(final boolean includeCases) {
        final Marker m = enter_section_modified(builder);
        while (!currentIsOneOf(OP_BRACES_RIGHT, KW_ELSE) && moreTokens() && suppressedErrorCount() <= MAX_ERRORS) {
            if (currentIsOneOf(KW_CASE, KW_DEFAULT) && !includeCases) {
                break;
            }
            if (currentIs(KW_WHILE)) {
                final Bookmark b = setBookmark();
                advance();
                if (currentIs(OP_PAR_LEFT)) {
                    final IElementType p = peekPastParens();
                    if (p != null && p.equals(OP_SCOLON)) {
                        goToBookmark(b);
                        break;
                    }
                    goToBookmark(b);
                } else
                    abandonBookmark(b);
            }
//                c = allocator.setCheckpoint();
            if (!parseDeclarationOrStatement()) {
//                    allocator.rollback(c);

                if (!suppressMessages.isEmpty()) {
                    cleanup(m, DECLARATIONS_AND_STATEMENTS);
                    return false;
                }
            }
        }
        exit_section_modified(builder, m, DECLARATIONS_AND_STATEMENTS, true);
        return true;
    }

    private void exit_section_modified(final PsiBuilder builder, final Marker m, final IElementType type, final boolean b) {
        //there is no incorrect parsing aka, markers should only be dropped in case of bookmarks
//        beginnings.remove(m);
        if (type.equals(DECLARATION) || type.equals(MODULE_DECLARATION)) {
            // Attach documentations to their declarations
            m.setCustomEdgeTokenBinders(LeadingDocCommentBinder.INSTANCE, TrailingDocCommentBinder.INSTANCE);
        }
        exit_section_(builder, m, type, true);

    }

    /**
     * Parses a DeclarationOrStatement
     * <p>
     * $(GRAMMAR $(RULEDEF declarationOrStatement):
     *   $(RULE declaration)
     * | $(RULE statement)
     * ;)
     */
    boolean parseDeclarationOrStatement() {
        final Marker m = enter_section_modified(builder);
        // "Any ambiguities in the grammar between Statements and
        // Declarations are resolved by the declarations taking precedence."
        final Bookmark b = setBookmark();
//            c = allocator.setCheckpoint();
        final boolean d = parseDeclaration(true, false);
        if (!d) {
//                allocator.rollback(c);
            goToBookmark(b);
            if (!parseStatement()) {
                cleanup(m, DECLARATION_OR_STATEMENT);
                return false;
            }
        } else {
            // original comment from libdparse:
            // : Make this more efficient. Right now we parse the declaration
            // twice, once with errors and warnings ignored, and once with them
            // printed. Maybe store messages to then be abandoned or written later?
//                allocator.rollback(c);
            goToBookmark(b);
            parseDeclaration(true, true);
        }
        exit_section_modified(builder, m, DECLARATION_OR_STATEMENT, true);
        return true;
    }

    /**
     * Parses a Declarator
     * <p>
     * $(GRAMMAR $(RULEDEF declarator):
     *   $(LITERAL Identifier)
     * | $(LITERAL Identifier) $(LITERAL '=') $(RULE initializer)
     * | $(LITERAL Identifier) $(RULE templateParameters) $(LITERAL '=') $(RULE initializer)
     * ;)
     */
    boolean parseDeclarator() {
        final Marker m = enter_section_modified(builder);
        final IElementType id = expect(ID);
        if (id == null) {
            cleanup(m, DECLARATOR);
            return false;
        }
        if (currentIs(OP_BRACKET_LEFT)) // dmd doesn't accept pointer after identifier
        {
            warn("C-style array declaration.");
            while (moreTokens() && currentIs(OP_BRACKET_LEFT))
                if (!parseTypeSuffix()) {
                    cleanup(m, DECLARATOR);
                    return false;
                }
        }
        if (currentIs(OP_PAR_LEFT)) {
            if (!parseTemplateParameters()) {
                cleanup(m, DECLARATOR);
                return false;
            }
            if (!tokenCheck(OP_EQ)) {
                cleanup(m, DECLARATOR);
                return false;
            }
            if (!parseInitializer()) {
                cleanup(m, DECLARATOR);
                return false;
            }
        } else if (currentIs(OP_EQ)) {
            advance();
            if (!parseInitializer()) {
                cleanup(m, DECLARATOR);
                return false;
            }
        }
        exit_section_modified(builder, m, DECLARATOR, true);
        return true;
    }

    /**
     * Parses a DeclaratorIdentifierList
     *
     * $(GRAMMAR $(RULEDEF declaratorIdentifierList):
     *     $(LITERAL Identifier) ($(LITERAL ',') $(LITERAL Identifier))*
     *     ;)
     */
    boolean parseDeclaratorIdentifierList() {
        while (moreTokens()) {
            Marker m = enter_section_modified(builder);
            final IElementType ident = expect(ID);
            if (ident == null) {
                cleanup(m, DECLARATOR_IDENTIFIER);
                return false;
            }
            if (currentIs(OP_COMMA)) {
                advance();
            } else {
                exit_section_modified(builder, m, DECLARATOR_IDENTIFIER, true);
                break;
            }
            exit_section_modified(builder, m, DECLARATOR_IDENTIFIER, true);
        }
        return true;
    }

    /**
     * Parses a DefaultStatement
     * <p>
     * $(GRAMMAR $(RULEDEF defaultStatement):
     * $(LITERAL 'default') $(LITERAL ':') $(RULE declarationsAndStatements)
     * ;)
     */
    boolean parseDefaultStatement() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_DEFAULT)) {
            cleanup(m, DEFAULT_STATEMENT);
            return false;
        }
        final IElementType colon = expect(OP_COLON);
        if (colon == null) {
            cleanup(m, DEFAULT_STATEMENT);
            return false;
        }
        if (!parseDeclarationsAndStatements()) {
            cleanup(m, DEFAULT_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, DEFAULT_STATEMENT, true);
        return true;
    }

    /**
     * Parses a DeleteExpression
     * <p>
     * $(GRAMMAR $(RULEDEF deleteExpression):
     * $(LITERAL 'delete') $(RULE unaryExpression)
     * ;)
     */
    boolean parseDeleteExpression() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_DELETE)) {
            cleanup(m, DELETE_EXPRESSION);
            return false;
        }
        if (!parseUnaryExpression()) {
            cleanup(m, DELETE_EXPRESSION);
            return false;
        }
        exit_section_modified(builder, m, DELETE_EXPRESSION, true);
        return true;
    }

    /**
     * Parses a Deprecated attribute
     * <p>
     * $(GRAMMAR $(RULEDEF deprecated):
     * $(LITERAL 'deprecated') ($(LITERAL '$(LPAREN)') $(LITERAL StringLiteral)+ $(LITERAL '$(RPAREN)'))?
     * ;)
     */
    boolean parseDeprecated() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_DEPRECATED)) {
            cleanup(m, DEPRECATED);
            return false;
        }
        if (currentIs(OP_PAR_LEFT)) {
            advance();
            if (!parseAssignExpression()) {
                cleanup(m, DEPRECATED);
                return false;
            }
            if (!tokenCheck(OP_PAR_RIGHT)) {
                cleanup(m, DEPRECATED);
                return false;
            }
        }
        exit_section_modified(builder, m, DEPRECATED, true);
        return true;
    }

    /**
     * Parses a Destructor
     * <p>
     * $(GRAMMAR $(RULEDEF destructor):
     * $(LITERAL '~') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ';'))
     * ;)
     */
    boolean parseDestructor() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(OP_TILDA)) {
            cleanup(m, DESTRUCTOR);
            return false;
        }
        if (!moreTokens()) {
            error("`this` expected");
            exit_section_modified(builder, m, DESTRUCTOR, true);
            return false;
        }
        if (!tokenCheck(KW_THIS)) {
            cleanup(m, DESTRUCTOR);
            return false;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, DESTRUCTOR);
            return false;
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, DESTRUCTOR);
            return false;
        }
        if (currentIs(OP_SCOLON))
            advance();
        else {
            while (moreTokens() && currentIsMemberFunctionAttribute())
                if (!parseMemberFunctionAttribute()) {
                    cleanup(m, DESTRUCTOR);
                    return false;
                }
            if (!parseFunctionBody()) {
                cleanup(m, DESTRUCTOR);
                return false;
            }
        }
        exit_section_modified(builder, m, DESTRUCTOR, true);
        return true;
    }

    /**
     * Parses a DoStatement
     * <p>
     * $(GRAMMAR $(RULEDEF doStatement):
     * $(LITERAL 'do') $(RULE statementNoCaseNoDefault) $(LITERAL 'while') $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)') $(LITERAL ';')
     * ;)
     */
    boolean parseDoStatement() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_DO)) {
            cleanup(m, DO_STATEMENT);
            return false;
        }
        if (!moreTokens()) {
            cleanup(m, DO_STATEMENT);
            return false;
        }
        if (!parseStatementNoCaseNoDefault()) {
            cleanup(m, DO_STATEMENT);
            return false;
        }
        if (!tokenCheck(KW_WHILE)) {
            cleanup(m, DO_STATEMENT);
            return false;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, DO_STATEMENT);
            return false;
        }
        if (!parseExpression()) {
            cleanup(m, DO_STATEMENT);
            return false;
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, DO_STATEMENT);
            return false;
        }
        if (!tokenCheck(OP_SCOLON)) {
            cleanup(m, DO_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, DO_STATEMENT, true);
        return true;
    }

    /**
     * Parses an EnumBody
     * <p>
     * $(GRAMMAR $(RULEDEF enumBody):
     * $(LITERAL '{') $(RULE enumMember) ($(LITERAL ',') $(RULE enumMember)?)* $(LITERAL '}')
     * ;)
     */
    boolean parseEnumBody() {
//            EnumBody node = allocator.make!EnumBody;
        final Marker m = enter_section_modified(builder);
        final IElementType open = expect(OP_BRACES_LEFT);
        if (open == null) {
            cleanup(m, ENUM_BODY);
            return false;
        }
        while (moreTokens()) {
            if (currentIsOneOf(ID, OP_AT, KW_DEPRECATED)) {
//                    auto c = allocator.setCheckpoint();
                final boolean e = parseEnumMember();
//                    if (!e)
//                        allocator.rollback(c);
//                    else
//                        last = e;
                if (currentIs(OP_COMMA)) {
                    advance();
                    if (!currentIs(OP_BRACES_RIGHT))
                        continue;
                }
                if (currentIs(OP_BRACES_RIGHT)) {
                    break;
                } else {
                    error("`,` or `}` expected");
                    if (currentIs(OP_BRACES_RIGHT))
                        break;
                }
            } else
                error("Enum member expected");
        }
        final IElementType close = expect(OP_BRACES_RIGHT);
        exit_section_modified(builder, m, ENUM_BODY, true);
        return true;
    }

    boolean parseAnonymousEnumMember() {
        return parseAnonymousEnumMember(false);
    }

    /**
     * $(GRAMMAR $(RULEDEF anonymousEnumMember):
     *   $(RULE type) $(LITERAL identifier) $(LITERAL '=') $(RULE assignExpression)
     * | $(LITERAL identifier) $(LITERAL '=') $(RULE assignExpression)
     * | $(LITERAL identifier)
     * ;)
     */
    boolean parseAnonymousEnumMember(final boolean typeAllowed) {
        final Marker m = enter_section_modified(builder);
        if (currentIs(ID) && peekIsOneOf(OP_COMMA, OP_EQ, OP_BRACES_RIGHT)) {
            if (!tokenCheck(ID)) {
                cleanup(m, ENUM_MEMBER);
                return false;
            }
            if (currentIs(OP_EQ)) {
                advance(); // =
                if (!assignAnonEnumMember(m)) return false;
            }
        } else if (typeAllowed) {
            if (!parseType().first) {
                cleanup(m, ENUM_MEMBER);
                return false;
            }
            if (!tokenCheck(ID)) {
                cleanup(m, ENUM_MEMBER);
                return false;
            }
            if (!tokenCheck(OP_EQ)) {
                cleanup(m, ENUM_MEMBER);
                return false;
            }
            if (!assignAnonEnumMember(m)) return false;
        } else {
            error("Cannot specify anonymous enum member type if anonymous enum has a base type.");
            exit_section_modified(builder, m, ENUM_MEMBER, true);
            return false;
        }
        exit_section_modified(builder, m, ENUM_MEMBER, true);
        return true;
    }

    private boolean assignAnonEnumMember(final Marker m) {
        if (!parseAssignExpression()) {
            cleanup(m, ENUM_MEMBER);
            return false;
        }
        return true;
    }

    /**
     * $(GRAMMAR $(RULEDEF anonymousEnumDeclaration):
     * $(LITERAL 'enum') ($(LITERAL ':') $(RULE type))? $(LITERAL '{') $(RULE anonymousEnumMember)+ $(LITERAL '}')
     * ;)
     */
    boolean parseAnonymousEnumDeclaration() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_ENUM)) {
            cleanup(m, ANONYMOUS_ENUM_DECLARATION);
            return false;
        }
        final boolean hasBaseType = currentIs(OP_COLON);
        if (hasBaseType) {
            advance();
            if (!parseType().first) {
                cleanup(m, ANONYMOUS_ENUM_DECLARATION);
                return false;
            }
        }
        if (!tokenCheck(OP_BRACES_LEFT)) {
            cleanup(m, ANONYMOUS_ENUM_DECLARATION);
            return false;
        }
        while (moreTokens()) {
            if (currentIs(OP_COMMA)) {
//                    if (last != null && last.comment == null)
//                    last.comment = current().trailingComment;
                advance();
            } else if (currentIs(OP_BRACES_RIGHT)) {
//                    if (last != null && last.comment == null)
//                    last.comment = tokens[index - 1].trailingComment;
                break;
            } else {
//                    c = allocator.setCheckpoint();
                parseAnonymousEnumMember(!hasBaseType);
//                    if (!e)
//                        allocator.rollback(c);
//                    else
//                        last = e;
            }
        }
        if (!tokenCheck(OP_BRACES_RIGHT)) {
            cleanup(m, ANONYMOUS_ENUM_DECLARATION);
            return false;
        }
        exit_section_modified(builder, m, ANONYMOUS_ENUM_DECLARATION, true);
        return true;
    }

    /**
     * Parses an EnumDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF enumDeclaration):
     *   $(LITERAL 'enum') $(LITERAL Identifier) ($(LITERAL ':') $(RULE type))? $(LITERAL ';')
     * | $(LITERAL 'enum') $(LITERAL Identifier) ($(LITERAL ':') $(RULE type))? $(RULE enumBody)
     * ;)
     */
    boolean parseEnumDeclaration() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_ENUM)) {
            cleanup(m, ENUM_DECLARATION);
            return false;
        }
        if (!tokenCheck(ID)) {
            cleanup(m, ENUM_DECLARATION);
            return false;
        }
        if (currentIs(OP_COLON)) {
            advance(); // skip ':'
            if (!parseType().first) {
                cleanup(m, ENUM_DECLARATION);
                return false;
            }
        }
        if (currentIs(OP_SCOLON)) {
            advance();
            exit_section_(builder, m, ENUM_DECLARATION, true);
            return true;
        }
        if (!parseEnumBody()) {
            cleanup(m, ENUM_DECLARATION);
            return false;
        }
        exit_section_modified(builder, m, ENUM_DECLARATION, true);
        return true;
    }

    /**
     * Parses an EnumMemberAttribute
     *
     * $(GRAMMAR $(RULEDEF enumMemberAttribute):
     *       $(RULE atAttribute)
     *     | $(RULE deprecated)
     *     ;)
     */
    public boolean parseEnumMemberAttribute() {
        final Marker m = enter_section_modified(builder);
        if (currentIs(OP_AT)) {
            if (!parseAtAttribute()) {
                cleanup(m, ENUM_MEMBER_ATTRIBUTE);
                return false;
            }
        } else if (currentIs(KW_DEPRECATED)) {
            if (!parseDeprecated()) {
                cleanup(m, ENUM_MEMBER_ATTRIBUTE);
                return false;
            }
        } else {
            m.drop(); // drop instead of cleanup otherwise an empty EnumMemberAttribute will be created
            return false;
        }
        exit_section_modified(builder, m, ENUM_MEMBER_ATTRIBUTE, true);
        return true;
    }

    /**
     * Parses an EnumMember
     * <p>
     * $(GRAMMAR $(RULEDEF enumMember):
     * ($(RULE enumMemberAttribute))* $(LITERAL Identifier) $(LITERAL '=') $(RULE assignExpression)
     * ;)
     */
    boolean parseEnumMember() {
        final Marker m = enter_section_modified(builder);
        while (moreTokens()) {
            if (!parseEnumMemberAttribute()) {
                break;
            }
        }
        if (!tokenCheck(ID)) {
            cleanup(m, ENUM_MEMBER);
            return false;
        }
        if (currentIs(OP_EQ)) {
            advance();
            if (!parseAssignExpression()) {
                cleanup(m, ENUM_MEMBER);
                return false;
            }
        }
        exit_section_modified(builder, m, ENUM_MEMBER, true);
        return true;
    }

    /**
     * Parses an EponymousTemplateDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF eponymousTemplateDeclaration):
     * $(LITERAL 'enum') $(LITERAL Identifier) $(RULE templateParameters) $(LITERAL '=') $(RULE assignExpression) $(LITERAL ';')
     * ;)
     */
    boolean parseEponymousTemplateDeclaration() {
        final Marker m = enter_section_modified(builder);
        advance(); // enum
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, EPONYMOUS_TEMPLATE_DECLARATION);
            return false;
        }
        if (!parseTemplateParameters()) {
            cleanup(m, EPONYMOUS_TEMPLATE_DECLARATION);
            return false;
        }
        expect(OP_EQ);
        if (!parseAssignExpression())
            if (!parseType().first) {
                cleanup(m, EPONYMOUS_TEMPLATE_DECLARATION);
                return false;
            }
        expect(OP_SCOLON);
        exit_section_modified(builder, m, EPONYMOUS_TEMPLATE_DECLARATION, true);
        return true;
    }

    boolean parseEqualExpression() {
        return parseEqualExpression(true);
    }

    /**
     * Parses an EqualExpression
     * <p>
     * $(GRAMMAR $(RULEDEF equalExpression):
     * $(RULE shiftExpression) ($(LITERAL '==') | $(LITERAL '!=')) $(RULE shiftExpression)
     * ;)
     */
    boolean parseEqualExpression(final boolean parseShift) {
        final Marker m = enter_section_modified(builder);
        if (parseShift) {
            final boolean shift = parseShiftExpression();
            if (!shift) {
                cleanup(m, EQUAL_EXPRESSION);
                return false;
            }
        }
        if (currentIsOneOf(OP_EQ_EQ, OP_NOT_EQ)) {
            advance();
        }
        if (!parseShiftExpression()) {
            cleanup(m, EQUAL_EXPRESSION);
            return false;
        }
        exit_section_modified(builder, m, EQUAL_EXPRESSION, true);
        return true;
    }

    /**
     * Parses an Expression
     * <p>
     * $(GRAMMAR $(RULEDEF expression):
     * $(RULE assignExpression) ($(LITERAL ',') $(RULE assignExpression))*
     * ;)
     */
    boolean parseExpression() {
        final Marker m = enter_section_modified(builder);
        if (suppressedErrorCount() > MAX_ERRORS) {
            cleanup(m, EXPRESSION);
            return false;
        }
        if (!moreTokens()) {
            error("Expected expression instead of EOF");
            exit_section_modified(builder, m, EXPRESSION, true);
            return false;
        }
        final boolean result = parseCommaSeparatedRule("Expression", "AssignExpression", true);
        exit_section_modified(builder, m, EXPRESSION, result);
        return result;
    }

    /**
     * Parses an ExpressionStatement
     * <p>
     * $(GRAMMAR $(RULEDEF expressionStatement):
     * $(RULE expression) $(LITERAL ';')
     * ;)
     */
    boolean parseExpressionStatement(final boolean parseExpression) {
        final Marker m = enter_section_modified(builder);
        if (parseExpression) {
            final boolean b = parseExpression();
            if (!b) {
                cleanup(m, EXPRESSION_STATEMENT);
                return false;
            }
        }
        if (expect(OP_SCOLON) == null) {
            cleanup(m, EXPRESSION_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, EXPRESSION_STATEMENT, true);
        return true;
    }

    /**
     * Parses a FinalSwitchStatement
     * <p>
     * $(GRAMMAR $(RULEDEF finalSwitchStatement):
     * $(LITERAL 'final') $(RULE switchStatement)
     * ;)
     */
    boolean parseFinalSwitchStatement() {
        final Marker m = enter_section_modified(builder);
        final boolean result = simpleParse("FinalSwitchStatement", KW_FINAL, "switchStatement|parseSwitchStatement");
        exit_section_modified(builder, m, FINAL_SWITCH_STATEMENT, result);
        return result;
    }

    /**
     * Parses a Finally
     * <p>
     * $(GRAMMAR $(RULEDEF finally):
     * $(LITERAL 'finally') $(RULE declarationOrStatement)
     * ;)
     */
    boolean parseFinally() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_FINALLY)) {
            cleanup(m, FINALLY);
            return false;
        }
        if (!parseDeclarationOrStatement()) {
            cleanup(m, FINALLY);
            return false;
        }
        exit_section_modified(builder, m, FINALLY, true);
        return true;
    }

    /**
     * Parses a ForStatement
     * <p>
     * $(GRAMMAR $(RULEDEF forStatement):
     * $(LITERAL 'for') $(LITERAL '$(LPAREN)') ($(RULE declaration) | $(RULE statementNoCaseNoDefault)) $(RULE expression)? $(LITERAL ';') $(RULE expression)? $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement)
     * ;)
     */
    boolean parseForStatement() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_FOR)) {
            cleanup(m, FOR_STATEMENT);
            return false;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, FOR_STATEMENT);
            return false;
        }
        if (currentIs(OP_SCOLON))
            advance();
        else if (!parseDeclarationOrStatement()) {
            cleanup(m, FOR_STATEMENT);
            return false;
        }
        if (currentIs(OP_SCOLON))
            advance();
        else {
            if (!parseExpression()) {
                cleanup(m, FOR_STATEMENT);
                return false;
            }
            expect(OP_SCOLON);
        }
        if (!currentIs(OP_PAR_RIGHT))
            if (!parseExpression()) {
                cleanup(m, FOR_STATEMENT);
                return false;
            }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, FOR_STATEMENT);
            return false;
        }
        // Intentionally return an incomplete parse tree so that DCD will work
        // more correctly.
        if (currentIs(OP_BRACES_RIGHT)) {
            error("Statement expected");
            exit_section_modified(builder, m, FOR_STATEMENT, true);
            return true;
        }

        if (!parseDeclarationOrStatement()) {
            cleanup(m, FOR_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, FOR_STATEMENT, true);
        return true;
    }


    /**
     * Parses a StaticForeachDeclaration
     *
     * $(GRAMMAR $(RULEDEF staticForeachDeclaration):
     *       $(LITERAL 'static') ($(LITERAL 'foreach') | $(LITERAL 'foreach_reverse')) $(LITERAL '$(LPAREN)') $(RULE foreachTypeList) $(LITERAL ';') $(RULE expression) $(LITERAL '$(RPAREN)') ($(RULE declaration) | $(LITERAL '{') $(RULE declaration)* $(LITERAL '}'))
     *     | $(LITERAL 'static') ($(LITERAL 'foreach') | $(LITERAL 'foreach_reverse')) $(LITERAL '$(LPAREN)') $(RULE foreachType) $(LITERAL ';') $(RULE expression) $(LITERAL '..') $(RULE expression) $(LITERAL '$(RPAREN)') ($(RULE declaration) | $(LITERAL '{') $(RULE declaration)* $(LITERAL '}'))
     *     ;)
     */
    boolean parseStaticForeachDeclaration(final boolean inTemplateDeclaration)
    {
        //mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_STATIC)) {
            cleanup(m, STATIC_FOREACH_DECLARATION);
            return false;
        }
        if (!parseForeach(STATIC_FOREACH_DECLARATION, true, inTemplateDeclaration)) {
            cleanup(m, STATIC_FOREACH_DECLARATION);
            return false;
        }
        exit_section_modified(builder, m, STATIC_FOREACH_DECLARATION, true);
        return true;
    }

    /**
     * Parses a ForeachStatement
     * <p>
     * $(GRAMMAR $(RULEDEF foreachStatement):
     * ($(LITERAL 'foreach') | $(LITERAL 'foreach_reverse')) $(LITERAL '$(LPAREN)') $(RULE foreachTypeList) $(LITERAL ';') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement)
     * | ($(LITERAL 'foreach') | $(LITERAL 'foreach_reverse')) $(LITERAL '$(LPAREN)') $(RULE foreachType) $(LITERAL ';') $(RULE expression) $(LITERAL '..') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement)
     * ;)
     */
    boolean parseForeachStatement() {
        //mixin(traceEnterAndExit!(__FUNCTION__));
        return parseForeach(FOREACH_STATEMENT, false);
    }

    boolean parseStaticForeachStatement() {
        return simpleParse("StaticForeachStatement", KW_STATIC,
            "foreachStatement|parseForeachStatement");
    }

    boolean parseForeach(IElementType elementType, final boolean declOnly) {
        return parseForeach(elementType, declOnly, false);
    }

    boolean parseForeach(IElementType elementType, boolean declOnly, boolean inTemplateDeclaration) {
//            ForeachStatement node = allocator.make!ForeachStatement;
        final Marker m = enter_section_modified(builder);
        if (currentIsOneOf(KW_FOREACH, KW_FOREACH_REVERSE)) {
            advance();
        } else {
            error("`foreach` or `foreach_reverse` expected");
            cleanup(m, elementType);
            return false;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, elementType);
            return false;
        }
        final Pair<Boolean, Integer> booleanLengthPair = parseForeachTypeList();
        final boolean feType = booleanLengthPair.first;
        if (!feType) {
            cleanup(m, elementType);
            return false;
        }
        final boolean canBeRange = booleanLengthPair.second == 1;
        if (!tokenCheck(OP_SCOLON)) {
            cleanup(m, elementType);
            return false;
        }
        if (!parseExpression()) {
            cleanup(m, elementType);
            return false;
        }
        if (currentIs(OP_DDOT)) {
            if (!canBeRange) {
                error("Cannot have more than one foreach variable for a foreach range statement");
                cleanup(m, elementType);
                return false;

            }
            advance();
            if (!parseExpression()) {
                cleanup(m, elementType);
                return false;
            }
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, elementType);
            return false;
        }
        if (currentIs(OP_BRACES_RIGHT)) {
            error("Statement expected"/*, false*/);
            cleanup(m, elementType);
            return true; // this line makes DCD better
        }
        if (declOnly) {
            //node.style = currentIs(OP_BRACES_LEFT) ? DeclarationListStyle.block : DeclarationListStyle.single;
            if (currentIs(OP_BRACES_LEFT)) {
                advance();
                while (moreTokens() && !currentIs(OP_BRACES_RIGHT)) {
                    Bookmark b = setBookmark();
                    //immutable c = allocator.setCheckpoint();
                    if (parseDeclaration(true, true, inTemplateDeclaration)) {
                        abandonBookmark(b);
                    } else {
                        goToBookmark(b);
                        cleanup(m, elementType);
                        //allocator.rollback(c);
                        return false;
                    }
                }
                if (!tokenCheck(OP_BRACES_RIGHT)) {
                    cleanup(m, elementType);
                    return false;
                }
            }
            else if (!parseDeclaration(true, true, inTemplateDeclaration)) {
                cleanup(m, elementType);
                return false;
            }
        }
        else {
            if (!parseDeclarationOrStatement()) {
                cleanup(m, elementType);
                return false;
            }
        }
        exit_section_modified(builder, m, elementType, true);
        return true;
    }

    /**
     * Parses a ForeachType
     * <p>
     * $(GRAMMAR $(RULEDEF foreachType):
     * ($(LITERAL 'scope') | $(LITERAL 'ref') | $(LITERAL 'alias') | $(LITERAL 'enum') | $(RULE typeConstructor))* $(RULE type)? $(LITERAL Identifier)
     * ;)
     */
    boolean parseForeachType() {
        final Marker m = enter_section_modified(builder);
        while (moreTokens()) {
            if (currentIs(KW_SCOPE)) {
                advance();
            } else if (currentIs(KW_REF)) {
                advance();
            } else if (currentIs(KW_ALIAS)) {
                advance();
            } else if (currentIs(KW_ENUM)) {
                advance();
            } else if (parseTypeConstructor(false) != null) {
                //trace ("\033[01;36mType constructor");
            } else {
                break;
            }
        }
        if (currentIs(ID) && peekIsOneOf(OP_COMMA, OP_SCOLON)) {
            advance();
            exit_section_modified(builder, m, FOREACH_TYPE, true);
            return true;
        }
        if (!parseType().first) {
            cleanup(m, FOREACH_TYPE);
            return false;
        }
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, FOREACH_TYPE);
            return false;
        }
        exit_section_modified(builder, m, FOREACH_TYPE, true);
        return true;
    }

    /**
     * Parses a ForeachTypeList
     * <p>
     * $(GRAMMAR $(RULEDEF foreachTypeList):
     * $(RULE foreachType) ($(LITERAL ',') $(RULE foreachType))*
     * ;)
     */
    Pair<Boolean, Integer> parseForeachTypeList() {
        final Marker marker = enter_section_modified(builder);
        final Ref.IntRef foreachTypeRefLength = new Ref.IntRef();
        foreachTypeRefLength.element = 0;
        final boolean b = parseCommaSeparatedRule(foreachTypeRefLength, "ForeachTypeList", "ForeachType");
        exit_section_modified(builder, marker, FOREACH_TYPE_LIST, b);
        return new Pair<Boolean, Integer>(b, foreachTypeRefLength.element);
    }

    /**
     * Parses a FunctionAttribute
     * <p>
     * $(GRAMMAR $(RULEDEF functionAttribute):
     *   $(RULE atAttribute)
     * | $(LITERAL 'pure')
     * | $(LITERAL 'nothrow')
     * ;)
     */
    boolean parseFunctionAttribute(final boolean validate) {
        final Marker m = enter_section_modified(builder);
        final IElementType i = current();
        if (i.equals(OP_AT)) {
            if (!parseAtAttribute()) {
                cleanup(m, FUNCTION_ATTRIBUTE);
                return false;
            }
        } else if (i.equals(KW_PURE) || i.equals(KW_NOTHROW)) {
            advance();
        } else {
            if (validate) {
                error("@attribute, `pure`, or `nothrow` expected");
                exit_section_modified(builder, m, FUNCTION_ATTRIBUTE, true);
                return false;
            }
            cleanup(m, FUNCTION_ATTRIBUTE);
            return false;
        }
        exit_section_modified(builder, m, FUNCTION_ATTRIBUTE, true);
        return true;
    }

    /**
     * Parses a FunctionBody
     * <p>
     * $(GRAMMAR $(RULEDEF functionBody):
     *   $(RULE specifiedFunctionBody)
     * | $(RULE missingFunctionBody)
     * | $(RULE shortenedFunctionBody)
     * ;)
     */
    boolean parseFunctionBody() {
        final Marker m = enter_section_modified(builder);
        Bookmark b = setBookmark();
        if (parseMissingFunctionBody()) {
            abandonBookmark(b);
        } else {
            goToBookmark(b);
            b = setBookmark();
            if (parseShortenedFunctionBody()) {
                abandonBookmark(b);
            } else {
                goToBookmark(b);
                if (!parseSpecifiedFunctionBody()) {
                    cleanup(m, FUNCTION_BODY);
                    return false;
                }
            }
        }
        exit_section_modified(builder, m, FUNCTION_BODY, true);
        return true;
    }

    boolean parseFunctionCallExpression() {
        final Pair<Boolean, Marker> booleanMarkerPair = parseFunctionCallExpression(null);
        return booleanMarkerPair.first;
    }

    /**
     * Parses a FunctionCallExpression
     * <p>
     * $(GRAMMAR $(RULEDEF functionCallExpression):
     *   $(RULE symbol) $(RULE arguments)
     * | $(RULE unaryExpression) $(RULE arguments)
     * | $(RULE type) $(RULE arguments)
     * ;)
     */
    private Pair<Boolean, Marker> parseFunctionCallExpression(final Marker unary)//(UnaryExpression unary = null)
    {
        final Marker m;
        if (unary != null) {
            m = unary.precede();
        } else {
            m = enter_section_modified(builder);
        }
        final IElementType i = current();
        if (i.equals(KW_CONST) || i.equals(KW_IMMUTABLE) || i.equals(KW_INOUT) || i.equals(KW_SHARED) || i.equals(KW_SCOPE) || i.equals(KW_PURE) || i.equals(KW_NOTHROW)) {
            if (!parseType().first) {
                cleanup(m, FUNCTION_CALL_EXPRESSION);
                return new Pair<>(false, m);
            }
            if (!parseArguments()) {
                cleanup(m, FUNCTION_CALL_EXPRESSION);
                return new Pair<>(false, m);
            }
        } else {
            if (unary != null) {
            } else if (!parseUnaryExpression()) {
                cleanup(m, FUNCTION_CALL_EXPRESSION);
                return new Pair<>(false, m);
            }
            if (currentIs(OP_NOT))
                if (!parseTemplateArguments()) {
                    cleanup(m, FUNCTION_CALL_EXPRESSION);
                    return new Pair<>(false, m);
                }
            if (unary != null)
                if (!parseArguments()) {
                    cleanup(m, FUNCTION_CALL_EXPRESSION);
                    return new Pair<>(false, m);
                }
        }
        exit_section_modified(builder, m, FUNCTION_CALL_EXPRESSION, true);
        return new Pair<>(true, m);
    }


    boolean parseFunctionContract() {
        return parseFunctionContract(true);
    }

    /**
     * Parses a FunctionContract
     *
     * $(GRAMMAR $(RULEDEF functionContract):
     *       $(RULE inOutContractExpression)
     *     | $(RULE inOutStatement)
     *     ;)
     */
    boolean parseFunctionContract(boolean allowStatement) {
        final Marker m = enter_section_modified(builder);
        if (allowStatement && (peekIs(OP_BRACES_LEFT) || (currentIs(KW_OUT) && peekAre(OP_PAR_LEFT, ID, OP_PAR_RIGHT)))) {
            if (!parseInOutStatement()) {
                cleanup(m, FUNCTION_CONTRACT);
                return false;
            }
        } else if (peekIs(OP_PAR_LEFT)) {
            if (!parseInOutContractExpression()) {
                cleanup(m, FUNCTION_CONTRACT);
                return false;
            }
        } else {
            error(allowStatement
                ? "`{` or `(` expected"
                : "`(` expected");
            cleanup(m, FUNCTION_CONTRACT);
            return false;
        }
        exit_section_modified(builder, m, FUNCTION_CONTRACT, true);
        return true;
    }

    boolean parseFunctionDeclaration() {
        return parseFunctionDeclaration(null, false);
    }

    /**
     * Parses a FunctionDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF functionDeclaration):
     *   ($(RULE storageClass)+ | $(RULE _type)) $(LITERAL Identifier) $(RULE parameters) $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ';'))
     * | ($(RULE storageClass)+ | $(RULE _type)) $(LITERAL Identifier) $(RULE templateParameters) $(RULE parameters) $(RULE memberFunctionAttribute)* $(RULE constraint)? ($(RULE functionBody) | $(LITERAL ';'))
     * ;)
     */
    boolean parseFunctionDeclaration(final Marker type, final boolean isAuto)//(Type type = null,Attribute[] attributes = null)
    {
        Marker m = null;
        if (type == null) {
            m = enter_section_modified(builder);
        } else
            m = type.precede();
        if (isAuto) {
            while (isStorageClass())
                if (!parseStorageClass()) {
                    cleanup(m, FUNCTION_DECLARATION);
                    return false;
                }
//                for( a; node.attributes)
//                {
//                    if (a.attribute == KW_AUTO)
//                    node.hasAuto = true;
//                else if (a.attribute == KW_REF)
//                    node.hasRef = true;
//                else
//                    continue;
//                }
        } else {
            while (moreTokens() && currentIsMemberFunctionAttribute())
                if (!parseMemberFunctionAttribute()) {
                    cleanup(m, FUNCTION_DECLARATION);
                    return false;
                }
            if (type == null) {
                if (!parseType().first) {
                    cleanup(m, FUNCTION_DECLARATION);
                    return false;
                }
            }
        }
        if (!tokenCheck(ID)) {
            cleanup(m, FUNCTION_DECLARATION);
            return false;
        }
        if (!currentIs(OP_PAR_LEFT)) {
            error("`(` expected");
            exit_section_modified(builder, m, FUNCTION_DECLARATION, true);
            return false;
        }
        final IElementType p = peekPastParens();
        final boolean isTemplate = p != null && p.equals(OP_PAR_LEFT);
        if (isTemplate) {
            if (!parseTemplateParameters()) {
                cleanup(m, FUNCTION_DECLARATION);
                return false;
            }
        }
        if (!parseParameters()) {
            cleanup(m, FUNCTION_DECLARATION);
            return false;
        }
        while (moreTokens() && currentIsMemberFunctionAttribute())
            if (!parseMemberFunctionAttribute()) {
                cleanup(m, FUNCTION_DECLARATION);
                return false;
            }
        if (isTemplate && currentIs(KW_IF)) {
            if (!parseConstraint()) {
                cleanup(m, FUNCTION_DECLARATION);
                return false;
            }
        }
        if (!parseFunctionBody()) {
            cleanup(m, FUNCTION_DECLARATION);
            return false;
        }
        exit_section_modified(builder, m, FUNCTION_DECLARATION, true);
        return true;
    }

    /**
     * Parses a FunctionLiteralExpression
     * <p>
     * $(GRAMMAR $(RULEDEF functionLiteralExpression):
     * | $(LITERAL 'delegate') $($(LITERAL 'auto')? LITERAL 'ref')? $(RULE type)? ($(RULE parameters) $(RULE functionAttribute)*)? $(RULE specifiedFunctionBody)
     * | $(LITERAL 'function') $($(LITERAL 'auto')? LITERAL 'ref')? $(RULE type)? ($(RULE parameters) $(RULE functionAttribute)*)? $(RULE specifiedFunctionBody)
     * | $($(LITERAL 'auto')? LITERAL 'ref')? $(RULE parameters) $(RULE functionAttribute)* $(RULE specifiedFunctionBody)
     * | $(RULE specifiedFunctionBody)
     * | $(LITERAL Identifier) $(LITERAL '=>') $(RULE assignExpression)
     * | $(LITERAL 'function') $($(LITERAL 'auto')? LITERAL 'ref')? $(RULE type)? $(RULE parameters) $(RULE functionAttribute)* $(LITERAL '=>') $(RULE assignExpression)
     * | $(LITERAL 'delegate') $($(LITERAL 'auto')? LITERAL 'ref')? $(RULE type)? $(RULE parameters) $(RULE functionAttribute)* $(LITERAL '=>') $(RULE assignExpression)
     * | $($(LITERAL 'auto')? LITERAL 'ref')? $(RULE parameters) $(RULE functionAttribute)* $(LITERAL '=>') $(RULE assignExpression)
     * ;)
     */
    boolean parseFunctionLiteralExpression() {
        final Marker m = enter_section_modified(builder);
        if (currentIsOneOf(KW_FUNCTION, KW_DELEGATE)) {
            advance();
            if (currentIs(KW_AUTO)) {
                advance();
                expect(KW_REF);
            }
            else if (currentIs(KW_REF)) {
                advance();
            }
            if (!currentIsOneOf(OP_PAR_LEFT, KW_IN, KW_DO,
                KW_OUT, OP_BRACES_LEFT, OP_LAMBDA_ARROW))
                if (!parseType().first) {
                    cleanup(m, FUNCTION_LITERAL_EXPRESSION);
                    return false;
                }
        }
        if (startsWith(ID, OP_LAMBDA_ARROW)) {
            advance();
            advance(); // =>
            if (!parseAssignExpression()) {
                cleanup(m, FUNCTION_LITERAL_EXPRESSION);
                return false;
            }
            exit_section_modified(builder, m, FUNCTION_LITERAL_EXPRESSION, true);
            return true;
        } else if (currentIs(OP_PAR_LEFT)
            || currentIs(KW_REF) && peekIs(OP_PAR_LEFT)
            || currentIs(KW_AUTO) && peekAre(KW_REF, OP_PAR_LEFT)) {
            if (currentIs(KW_AUTO)) {
                advance();
                expect(KW_REF);
            }
            else if (currentIs(KW_REF)) {
                advance();
            }
            if (!parseParameters()) {
                cleanup(m, FUNCTION_LITERAL_EXPRESSION);
                return false;
            }
            while (currentIsMemberFunctionAttribute()) {
//                    auto c =/ allocator.setCheckpoint();
                if (!parseMemberFunctionAttribute()) {
//                        allocator.rollback(c);
                    break;
                }
            }
        }
        if (currentIs(OP_LAMBDA_ARROW)) {
            advance();
            if (!parseAssignExpression()) {
                cleanup(m, FUNCTION_LITERAL_EXPRESSION);
                return false;
            }
        } else if (!parseSpecifiedFunctionBody()) {
            cleanup(m, FUNCTION_LITERAL_EXPRESSION);
            return false;
        }
        exit_section_modified(builder, m, FUNCTION_LITERAL_EXPRESSION, true);
        return true;
    }

    /**
     * Parses a GotoStatement
     * <p>
     * $(GRAMMAR $(RULEDEF gotoStatement):
     * $(LITERAL 'goto') ($(LITERAL Identifier) | $(LITERAL 'default') | $(LITERAL 'case') $(RULE expression)?) $(LITERAL ';')
     * ;)
     */
    boolean parseGotoStatement() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_GOTO)) {
            cleanup(m, GOTO_STATEMENT);
            return false;
        }
        if (!moreTokens()) {
            error("Expected something after goto instead of EOF");
            cleanup(m, GOTO_STATEMENT);
            return false;
        }
        final IElementType i = current();
        if (i.equals(ID) || i.equals(KW_DEFAULT)) {
            advance();
        } else if (i.equals(KW_CASE)) {
            advance();
            if (!currentIs(OP_SCOLON))
                if (!parseExpression()) {
                    cleanup(m, GOTO_STATEMENT);
                    return false;
                }
        } else {
            error("Identifier, `default`, or `case` expected");
            exit_section_modified(builder, m, GOTO_STATEMENT, true);
            return false;
        }
        if (!tokenCheck(OP_SCOLON)) {
            cleanup(m, GOTO_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, GOTO_STATEMENT, true);
        return true;
    }

    /**
     * Parses an IdentifierChain
     * <p>
     * $(GRAMMAR $(RULEDEF identifierChain):
     * $(LITERAL Identifier) ($(LITERAL '.') $(LITERAL Identifier))*
     * ;)
     */
    boolean parseIdentifierChain() {
        final Marker m = enter_section_modified(builder);
        while (moreTokens()) {
            final IElementType ident = expect(ID);
            if (ident == null) {
                cleanup(m, IDENTIFIER_CHAIN);
                return false;
            }
            if (currentIs(OP_DOT)) {
                advance();
            } else
                break;
        }
        exit_section_modified(builder, m, IDENTIFIER_CHAIN, true);
        return true;
    }

    /**
     * Parses a TypeIdentifierPart.
     *
     * $(GRAMMAR $(RULEDEF typeIdentifierPart):
     *       $(RULE identifierOrTemplateInstance)
     *     | $(RULE identifierOrTemplateInstance) $(LITERAL '.') $(RULE typeIdentifierPart)
     *     | $(RULE identifierOrTemplateInstance) $(LITERAL '[') $(RULE assignExpression) $(LITERAL ']')
     *     | $(RULE identifierOrTemplateInstance) $(LITERAL '[') $(RULE assignExpression) $(LITERAL ']') $(LITERAL '.') $(RULE typeIdentifierPart)
     *     ;)
     */
    public boolean parseTypeIdentifierPart() {
        final Marker m = enter_section_modified(builder);
        if (currentIs(OP_DOT)) {
            advance();
        }
        if (!parseIdentifierOrTemplateInstance()) {
            cleanup(m, TYPE_IDENTIFIER_PART);
            return false;
        }
        if (currentIs(OP_BRACKET_LEFT)) {
            // dyn arrays -> type suffixes
            if (peekIs(OP_BRACKET_RIGHT)) {
                exit_section_modified(builder, m, TYPE_IDENTIFIER_PART, true);
                return true;
            }
            Bookmark b = setBookmark();
            advance();
            // here we can have a type (AA key)
            if (!parseAssignExpression()) {
                goToBookmark(b);
                exit_section_modified(builder, m, TYPE_IDENTIFIER_PART, true);
                return true;
            }
            // indexer followed by ".." -> sliceExp -> type suffix
            else if (currentIs(OP_DDOT)) {
                goToBookmark(b);
                exit_section_modified(builder, m, TYPE_IDENTIFIER_PART, true);
                return true;
            }
            // otherwise either the index of a type list or a dim
            abandonBookmark(b);
            expect(OP_BRACKET_RIGHT);
            if (!currentIs(OP_DOT)) {
                exit_section_modified(builder, m, TYPE_IDENTIFIER_PART, true);
                return true;
            }
        }
        if (currentIs(OP_DOT)) {
            advance();
            if (!parseTypeIdentifierPart()) {
                cleanup(m, TYPE_IDENTIFIER_PART);
                return false;
            }
        }
        exit_section_modified(builder, m, TYPE_IDENTIFIER_PART, true);
        return true;
    }


    /**
     * Parses an IdentifierOrTemplateChain
     * <p>
     * $(GRAMMAR $(RULEDEF identifierOrTemplateChain):
     * $(RULE identifierOrTemplateInstance) ($(LITERAL '.') $(RULE identifierOrTemplateInstance))*
     * ;)
     */
    boolean parseIdentifierOrTemplateChain() {
        final Marker m = enter_section_modified(builder);
        int identifiersOrTemplateInstancesLength = 0;
        while (moreTokens()) {
            if (!parseIdentifierOrTemplateInstance()) {
                // TODO handle
                identifiersOrTemplateInstancesLength++;
                if (identifiersOrTemplateInstancesLength == 0) {
                    cleanup(m, IDENTIFIER_OR_TEMPLATE_CHAIN);
                    return false;
                } else
                    break;
            }
            if (!currentIs(OP_DOT))
                break;
            else
                advance();
        }
        exit_section_modified(builder, m, IDENTIFIER_OR_TEMPLATE_CHAIN, true);
        return true;
    }

    /**
     * Parses an IdentifierOrTemplateInstance
     * <p>
     * $(GRAMMAR $(RULEDEF identifierOrTemplateInstance):
     *   $(LITERAL Identifier)
     * | $(RULE templateInstance)
     * ;)
     */
    boolean parseIdentifierOrTemplateInstance() {
        final Marker m = enter_section_modified(builder);
        if (peekIs(OP_NOT) && !startsWith(ID, OP_NOT, KW_IS) && !startsWith(ID, OP_NOT, KW_IN)) {
            if (!parseTemplateInstance()) {
                cleanup(m, IDENTIFIER_OR_TEMPLATE_INSTANCE);
                return false;
            }
        } else {
            final IElementType ident = expect(ID);
            if (ident == null) {
                cleanup(m, IDENTIFIER_OR_TEMPLATE_INSTANCE);
                return false;
            }
        }
        exit_section_modified(builder, m, IDENTIFIER_OR_TEMPLATE_INSTANCE, true);
        return true;
    }

    boolean parseIdentityExpression() {
        return parseIdentityExpression(true);
    }

    /**
     * Parses an IdentityExpression
     * <p>
     * $(GRAMMAR $(RULEDEF identityExpression):
     * $(RULE shiftExpression) ($(LITERAL 'is') | ($(LITERAL '!') $(LITERAL 'is'))) $(RULE shiftExpression)
     * ;)
     */
    boolean parseIdentityExpression(final boolean parseShift)//(ExpressionNode shift = null)
    {
        final Marker m = enter_section_modified(builder);
        if (parseShift) {
            if (!parseShiftExpression()) {
                cleanup(m, IDENTITY_EXPRESSION);
                return false;
            }
        }
        if (currentIs(OP_NOT)) {
            advance();
        }
        if (!tokenCheck(KW_IS)) {
            cleanup(m, IDENTITY_EXPRESSION);
            return false;
        }
        if (!parseShiftExpression()) {
            cleanup(m, IDENTITY_EXPRESSION);
            return false;
        }
        exit_section_modified(builder, m, IDENTITY_EXPRESSION, true);
        return true;
    }

    /**
     * Parses an IfStatement
     * <p>
     * $(GRAMMAR $(RULEDEF ifStatement):
     * $(LITERAL 'if') $(LITERAL '$(LPAREN)') $(RULE ifCondition) $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement) ($(LITERAL 'else') $(RULE declarationOrStatement))?
     * ;)
     */
    boolean parseIfStatement() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_IF)) {
            cleanup(m, IF_STATEMENT);
            return false;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, IF_STATEMENT);
            return false;
        }
        if (!parseIfCondition()) {
            cleanup(m, IF_STATEMENT);
            return false;
        }

        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, IF_STATEMENT);
            return false;
        }
        if (currentIs(OP_BRACES_RIGHT)) {
            error("Statement expected");
            exit_section_modified(builder, m, IF_STATEMENT, true);
            return true; // this line makes DCD better
        }
        if (!parseDeclarationOrStatement()) {
            cleanup(m, IF_STATEMENT);
            return false;
        }
        if (currentIs(KW_ELSE)) {
            advance();
            if (!parseDeclarationOrStatement()) {
                cleanup(m, IF_STATEMENT);
                return false;
            }
        }
        exit_section_modified(builder, m, IF_STATEMENT, true);
        return true;
    }


    /**
     * Parse IfCondition
     *
     * $(RULEDEF ifCondition):
     *   $(LITERAL 'auto') $(LITERAL Identifier) $(LITERAL '=') $(RULE expression)
     *   $(LITERAL 'scope') $(LITERAL Identifier) $(LITERAL '=') $(RULE expression)
     * | $(RULE typeConstructors) $(LITERAL Identifier) $(LITERAL '=') $(RULE expression)
     * | $(RULE type) $(LITERAL Identifier) $(LITERAL '=') $(RULE expression)
     * | $(RULE expression)
     * ;)
     */
    public boolean parseIfCondition() {
        // ex. case:
        //    `if (auto identifier = exp)`
        //    `if (scope identifier = exp)`
        if (currentIsOneOf(KW_AUTO, KW_SCOPE)) {
            final Marker ifCondition = enter_section_(builder);
            advance();
            final IElementType i = expect(ID);
            if (i != null)
                expect(OP_EQ);
            if (!parseExpression()) {
                cleanup(ifCondition, IF_CONDITION);
                return false;
            }
            exit_section_(builder, ifCondition, IF_CONDITION, true);
        } else {
            if (!moreTokens()) {
                return false;
            }
            // consume for TypeCtors = identifier
            if (isTypeCtor(current())) {
                Bookmark before_advance = null;
                while (isTypeCtor(current())) {
                    if (before_advance != null) {
                        abandonBookmark(before_advance);
                    }
                    before_advance = setBookmark();
                    advance();
                }
                // goes back for TypeCtor(Type) = identifier
                if (currentIs(OP_PAR_LEFT)) {
                    goToBookmark(before_advance);
                } else {
                    abandonBookmark(before_advance);
                }
            }
            final Bookmark b = setBookmark();
            final boolean type = parseType().first;
            if (!type || !currentIs(ID)
                || !peekIs(OP_EQ)) {
                goToBookmark(b);
                if (!parseExpression()) {
                    return false;
                }
            } else {
                abandonBookmark(b);
                final Marker ifCondition = enter_section_(builder);
                if (!tokenCheck(ID)) {
                    cleanup(ifCondition, IF_CONDITION);
                    return false;
                }
                if (!tokenCheck(OP_EQ)) {
                    cleanup(ifCondition, IF_CONDITION);
                    return false;
                }
                if (!parseExpression()) {
                    cleanup(ifCondition, IF_CONDITION);
                    return false;
                }
                exit_section_(builder, ifCondition, IF_CONDITION, true);
            }
        }
        return true;
    }


    /**
     * Parses an ImportBind
     * <p>
     * $(GRAMMAR $(RULEDEF importBind):
     * $(LITERAL Identifier) ($(LITERAL '=') $(LITERAL Identifier))?
     * ;)
     */
    boolean parseImportBind() {
        final Marker m = enter_section_modified(builder);
        boolean isNamedBind = false;
        final Bookmark bookmark = setBookmark();
        IElementType ident = expect(ID);
        if (ident == null) {
            abandonBookmark(bookmark);
            cleanup(m, IMPORT_BIND);
            return false;
        }
        if (currentIs(OP_EQ)) {
            isNamedBind = true;
            advance();
            final IElementType id = expect(ID);
            if (id == null) {
                abandonBookmark(bookmark);
                cleanup(m, IMPORT_BIND);
                return false;
            }
        }
        goToBookmark(bookmark);
        if (isNamedBind) {
            final Marker namedImportBind = enter_section_modified(builder);
            ident = expect(ID);
            if (ident == null) {
                cleanup(m, IMPORT_BIND);
                return false;
            }
            exit_section_modified(builder, namedImportBind, NAMED_IMPORT_BIND, true);
            if (currentIs(OP_EQ)) {
                advance();
                final IElementType id = expect(ID);
                if (id == null) {
                    cleanup(m, IMPORT_BIND);
                    return false;
                }
            }
        } else {
            ident = expect(ID);
            if (ident == null) {
                cleanup(m, IMPORT_BIND);
                return false;
            }
        }
        exit_section_modified(builder, m, IMPORT_BIND, true);
        return true;
    }

    /**
     * Parses ImportBindings
     * <p>
     * $(GRAMMAR $(RULEDEF importBindings):
     * $(RULE import) $(LITERAL ':') $(RULE importBind) ($(LITERAL ',') $(RULE importBind))*
     * ;)
     */
    boolean parseImportBindings(final boolean parseSingleImport) {
        final Marker m = enter_section_modified(builder);
        if (parseSingleImport) {
            if (!parseSingleImport()) {
                cleanup(m, IMPORT_BINDINGS);
                return false;
            }
        }
        if (!tokenCheck(OP_COLON)) {
            cleanup(m, IMPORT_BINDINGS);
            return false;
        }
        while (moreTokens()) {
            if (parseImportBind()) {
                if (currentIs(OP_COMMA))
                    advance();
                else
                    break;
            } else {
                break;
            }
        }
        exit_section_modified(builder, m, IMPORT_BINDINGS, true);
        return true;
    }

    /**
     * Parses an ImportDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF importDeclaration):
     * $(LITERAL 'import') $(RULE import) ($(LITERAL ',') $(RULE import))* ($(LITERAL ',') $(RULE importBindings))? $(LITERAL ';')
     * | $(LITERAL 'import') $(RULE importBindings) $(LITERAL ';')
     * ;)
     */
    boolean parseImportDeclaration() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_IMPORT)) {
            cleanup(m, IMPORT_DECLARATION);
            return false;
        }
        final boolean si = parseSingleImport();
        if (!si) {
            cleanup(m, IMPORT_DECLARATION);
            return false;
        }
        if (currentIs(OP_COLON))
            parseImportBindings(!si);
        else {
            if (currentIs(OP_COMMA)) {
                advance();
                while (moreTokens()) {
                    final boolean single = parseSingleImport();
                    if (!single) {
                        cleanup(m, IMPORT_DECLARATION);
                        return false;
                    }
                    if (currentIs(OP_COLON)) {
                        parseImportBindings(!single);
                        break;
                    } else {
                        if (currentIs(OP_COMMA))
                            advance();
                        else
                            break;
                    }
                }
            }
        }
        if (!tokenCheck(OP_SCOLON)) {
            cleanup(m, IMPORT_DECLARATION);
            return false;
        }
        exit_section_modified(builder, m, IMPORT_DECLARATION, true);
        return true;
    }

    /**
     * Parses an ImportExpression
     * <p>
     * $(GRAMMAR $(RULEDEF importExpression):
     * $(LITERAL 'import') $(LITERAL '$(LPAREN)') $(RULE assignExpression) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseImportExpression() {
        final Marker marker = enter_section_modified(builder);
        final boolean b = simpleParse("ImportExpression", KW_IMPORT, OP_PAR_LEFT,
            "assignExpression|parseAssignExpression", OP_PAR_RIGHT);
        exit_section_modified(builder, marker, IMPORT_EXPRESSION, b);
        return b;
    }

    /**
     * Parses an Index
     * <p>
     * $(GRAMMAR $(RULEDEF index):
     * $(RULE assignExpression) ($(LITERAL '..') $(RULE assignExpression))?
     * ;
     * )
     */
    boolean parseIndex() {
        final Marker m = enter_section_modified(builder);
        if (!parseAssignExpression()) {
            cleanup(m, INDEX);
            return false;
        }
        if (currentIs(OP_DDOT)) {
            advance();
            if (!parseAssignExpression()) {
                cleanup(m, INDEX);
                return false;
            }
        }
        exit_section_modified(builder, m, INDEX, true);
        return true;
    }

    boolean parseIndexExpression() {
        return parseIndexExpression(true);
    }

    /**
     * Parses an IndexExpression
     * <p>
     * $(GRAMMAR $(RULEDEF indexExpression):
     * $(RULE unaryExpression) $(LITERAL '[') $(LITERAL ']')
     * | $(RULE unaryExpression) $(LITERAL '[') $(RULE index) ($(LITERAL ',') $(RULE index))* $(LITERAL ']')
     * ;
     * )
     */
    boolean parseIndexExpression(final boolean parseUnary)//(UnaryExpression unaryExpression = null)
    {
        final Marker m = enter_section_modified(builder);
        if (parseUnary) {
            if (!parseUnaryExpression()) {
                cleanup(m, INDEX_EXPRESSION);
                return false;
            }
        }
        if (!tokenCheck(OP_BRACKET_LEFT)) {
            cleanup(m, INDEX_EXPRESSION);
            return false;
        }
        while (true) {
            if (!moreTokens()) {
                error("Expected unary expression instead of EOF");
                exit_section_modified(builder, m, INDEX_EXPRESSION, true);
                return false;
            }
            if (currentIs(OP_BRACKET_RIGHT))
                break;
            if (!(parseIndex())) {
                cleanup(m, INDEX_EXPRESSION);
                return false;
            }
            if (currentIs(OP_COMMA))
                advance();
            else
                break;
        }
        if (!tokenCheck(OP_BRACKET_RIGHT)) {
            cleanup(m, INDEX_EXPRESSION);
            return false;
        }
        exit_section_modified(builder, m, INDEX_EXPRESSION, true);
        return true;
    }

    /**
     * Parses an InContractExpression
     *
     * $(GRAMMAR $(RULEDEF inContractExpression):
     *     $(LITERAL 'in') $(LITERAL '$(LPAREN)') $(RULE assertArguments) $(LITERAL '$(RPAREN)')
     *     ;)
     */
    public boolean parseInContractExpression() {
        final Marker m = enter_section_modified(builder);
        if (expect(KW_IN) == null) {
            cleanup(m, IN_CONTRACT_EXPRESSION);
            return false;
        }
        if (expect(OP_PAR_LEFT) == null) {
            cleanup(m, IN_CONTRACT_EXPRESSION);
            return false;
        }
        if (!parseAssertArguments()) {
            cleanup(m, IN_CONTRACT_EXPRESSION);
            return false;
        }
        if (expect(OP_PAR_RIGHT) == null) {
            cleanup(m, IN_CONTRACT_EXPRESSION);
            return false;
        }
        exit_section_modified(builder, m, IN_CONTRACT_EXPRESSION, true);
        return true;
    }

    boolean parseInExpression() {
        return parseInExpression(true);
    }

    /**
     * Parses an InExpression
     * <p>
     * $(GRAMMAR $(RULEDEF inExpression):
     * $(RULE shiftExpression) ($(LITERAL 'in') | ($(LITERAL '!') $(LITERAL 'in'))) $(RULE shiftExpression)
     * ;)
     */
    boolean parseInExpression(final boolean parseShift)//(ExpressionNode shift = null)
    {
        final Marker m = enter_section_modified(builder);
        if (parseShift) {
            if (!parseShiftExpression()) {
                cleanup(m, IN_EXPRESSION);
                return false;
            }
        }
        if (currentIs(OP_NOT)) {
            advance();
        }
        if (!tokenCheck(KW_IN)) {
            cleanup(m, IN_EXPRESSION);
            return false;
        }
        if (!parseShiftExpression()) {
            cleanup(m, IN_EXPRESSION);
            return false;
        }
        exit_section_modified(builder, m, IN_EXPRESSION, true);
        return true;
    }

    /**
     * Parses an InOutContractExpression
     *
     * $(GRAMMAR $(RULEDEF inOutContractExpression):
     *       $(RULE inContractExpression)
     *     | $(RULE outContractExpression)
     *     ;)
     */
    public boolean parseInOutContractExpression() {
        final Marker m = enter_section_modified(builder);
        if (currentIs(KW_IN)) {
            if (!parseInContractExpression()) {
                cleanup(m, IN_OUT_CONTRACT_EXPRESSION);
                return false;
            }
        } else if (!parseOutContractExpression()) {
            cleanup(m, IN_OUT_CONTRACT_EXPRESSION);
            return false;
        } else {
            cleanup(m, IN_OUT_CONTRACT_EXPRESSION);
            return false;
        }
        exit_section_modified(builder, m, IN_OUT_CONTRACT_EXPRESSION, true);
        return true;
    }

    /**
     * Parses an InOutStatement
     *
     * $(GRAMMAR $(RULEDEF inOutStatement):
     *       $(RULE inStatement)
     *     | $(RULE outStatement)
     *     ;)
     */
    public boolean parseInOutStatement() {
        final Marker m = enter_section_modified(builder);
        if (currentIs(KW_IN)) {
            if (!parseInStatement()) {
                cleanup(m, IN_OUT_STATEMENT);
                return false;
            }
        } else if (currentIs(KW_OUT)) {
            if (!parseOutStatement()) {
                cleanup(m, IN_OUT_STATEMENT);
                return false;
            }
        } else {
            cleanup(m, IN_OUT_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, IN_OUT_STATEMENT, true);
        return true;
    }

    /**
     * Parses an InStatement
     * <p>
     * $(GRAMMAR $(RULEDEF inStatement):
     * $(LITERAL 'in') $(RULE blockStatement)
     * ;)
     */
    boolean parseInStatement() {
        final Marker m = enter_section_modified(builder);
        final IElementType i = expect(KW_IN);
        if (i == null) {
            cleanup(m, IN_STATEMENT);
            return false;
        }
        if (!parseBlockStatement()) {
            cleanup(m, IN_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, IN_STATEMENT, true);
        return true;
    }

    /**
     * Parses an Initializer
     * <p>
     * $(GRAMMAR $(RULEDEF initializer):
     *   $(LITERAL 'void')
     * | $(RULE nonVoidInitializer)
     * ;)
     */
    boolean parseInitializer() {
        final Marker m = enter_section_modified(builder);
        if (currentIs(KW_VOID) && peekIsOneOf(OP_COMMA, OP_SCOLON))
            advance();
        else if (!parseNonVoidInitializer()) {
            cleanup(m, INITIALIZER);
            return false;
        }
        exit_section_modified(builder, m, INITIALIZER, true);
        return true;
    }

    /**
     * Parses an InterfaceDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF interfaceDeclaration):
     *   $(LITERAL 'interface') $(LITERAL Identifier) $(LITERAL ';')
     * | $(LITERAL 'interface') $(LITERAL Identifier) ($(LITERAL ':') $(RULE baseClassList))? $(RULE structBody)
     * | $(LITERAL 'interface') $(LITERAL Identifier) $(RULE templateParameters) $(RULE raint)? ($(LITERAL ':') $(RULE baseClassList))? $(RULE structBody)
     * | $(LITERAL 'interface') $(LITERAL Identifier) $(RULE templateParameters) ($(LITERAL ':') $(RULE baseClassList))? $(RULE raint)? $(RULE structBody)
     * ;)
     */
    boolean parseInterfaceDeclaration() {
        final Marker m = enter_section_modified(builder);
        expect(KW_INTERFACE);
        final boolean b = parseInterfaceOrClass();
        exit_section_modified(builder, m, INTERFACE_DECLARATION, b);
        return b;
    }

    /**
     * Parses an Invariant
     * <p>
     * $(GRAMMAR $(RULEDEF invariant):
     *   $(LITERAL 'invariant') ($(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)'))? $(RULE blockStatement)
     * | $(LITERAL 'invariant') $(LITERAL '$(LPAREN)') $(RULE assertArguments) $(LITERAL '$(RPAREN)') $(LITERAL ';')
     * ;)
     */
    boolean parseInvariant() {
        final Marker m = enter_section_modified(builder);
        boolean mustHaveBlock = false;
        if (!tokenCheck(KW_INVARIANT)) {
            cleanup(m, INVARIANT);
            return false;
        }
        if (currentIs(OP_PAR_LEFT) && peekIs(OP_PAR_RIGHT)) {
            mustHaveBlock = true;
            advance();
            advance();
        }
        if (currentIs(OP_BRACES_LEFT)) {
            if (currentIs(OP_PAR_LEFT)) {
                advance();
                if (!tokenCheck(OP_PAR_RIGHT)) {
                    cleanup(m, INVARIANT);
                    return false;
                }
            }
            if (!parseBlockStatement()) {
                cleanup(m, INVARIANT);
                return false;
            }
        } else if (!mustHaveBlock && currentIs(OP_PAR_LEFT)) {
            advance();
            if (!parseAssertArguments()) {
                cleanup(m, INVARIANT);
                return false;
            }
            if (!tokenCheck(OP_PAR_RIGHT)) {
                cleanup(m, INVARIANT);
                return false;
            }
            if (!tokenCheck(OP_SCOLON)) {
                cleanup(m, INVARIANT);
                return false;
            }
        } else {
            cleanup(m, INVARIANT);
            return false;
        }
        exit_section_modified(builder, m, INVARIANT, true);
        return true;
    }

    /**
     * Parses an IsExpression
     * <p>
     * $(GRAMMAR $(RULEDEF isExpression):
     *   $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL '$(RPAREN)')
     * | $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL ':') $(RULE typeSpecialization) $(LITERAL '$(RPAREN)')
     * | $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL '=') $(RULE typeSpecialization) $(LITERAL '$(RPAREN)')
     * | $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL ':') $(RULE typeSpecialization) $(LITERAL ',') $(RULE templateParameterList) $(LITERAL '$(RPAREN)')
     * | $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL '=') $(RULE typeSpecialization) $(LITERAL ',') $(RULE templateParameterList) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseIsExpression() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_IS)) {
            cleanup(m, IS_EXPRESSION);
            return false;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, IS_EXPRESSION);
            return false;
        }
        if (!parseType().first) {
            cleanup(m, IS_EXPRESSION);
            return false;
        }
        if (currentIs(ID))
            advance();
        if (currentIsOneOf(OP_EQ_EQ, OP_COLON)) {
            advance();
            if (!parseTypeSpecialization()) {
                cleanup(m, IS_EXPRESSION);
                return false;
            }
            if (currentIs(OP_COMMA)) {
                advance();
                if (!parseTemplateParameterList()) {
                    cleanup(m, IS_EXPRESSION);
                    return false;
                }
            }
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, IS_EXPRESSION);
            return false;
        }
        exit_section_modified(builder, m, IS_EXPRESSION, true);
        return true;
    }

    /**
     * Parses a KeyValuePair
     * <p>
     * $(GRAMMAR $(RULEDEF keyValuePair):
     * $(RULE assignExpression) $(LITERAL ':') $(RULE assignExpression)
     * ;)
     */
    boolean parseKeyValuePair() {
        final Marker m = enter_section_modified(builder);
        if (!parseAssignExpression()) {
            cleanup(m, KEY_VALUE_PAIR);
            return false;
        }
        if (!tokenCheck(OP_COLON)) {
            cleanup(m, KEY_VALUE_PAIR);
            return false;
        }
        if (!parseAssignExpression()) {
            cleanup(m, KEY_VALUE_PAIR);
            return false;
        }
        exit_section_modified(builder, m, KEY_VALUE_PAIR, true);
        return true;
    }

    /**
     * Parses KeyValuePairs
     * <p>
     * $(GRAMMAR $(RULEDEF keyValuePairs):
     * $(RULE keyValuePair) ($(LITERAL ',') $(RULE keyValuePair))* $(LITERAL ',')?
     * ;)
     */
    boolean parseKeyValuePairs() {
        final Marker m = enter_section_modified(builder);
        while (moreTokens()) {
            if (!parseKeyValuePair()) {
                cleanup(m, KEY_VALUE_PAIRS);
                return false;
            }
            if (currentIs(OP_COMMA)) {
                advance();
                if (currentIs(OP_BRACKET_RIGHT))
                    break;
            } else
                break;
        }
        exit_section_modified(builder, m, KEY_VALUE_PAIRS, true);
        return true;
    }

    /**
     * Parses a LabeledStatement
     * <p>
     * $(GRAMMAR $(RULEDEF labeledStatement):
     * $(LITERAL Identifier) $(LITERAL ':') $(RULE declarationOrStatement)?
     * ;)
     */
    boolean parseLabeledStatement() {
        final Marker m = enter_section_modified(builder);
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, LABELED_STATEMENT);
            return false;
        }
        expect(OP_COLON);
        if (!currentIs(OP_BRACES_RIGHT))
            if (!parseDeclarationOrStatement()) {
                cleanup(m, LABELED_STATEMENT);
                return false;
            }
        exit_section_modified(builder, m, LABELED_STATEMENT, true);
        return true;
    }

    /**
     * Parses a LastCatch
     * <p>
     * $(GRAMMAR $(RULEDEF lastCatch):
     * $(LITERAL 'catch') $(RULE statementNoCaseNoDefault)
     * ;)
     */
    boolean parseLastCatch() {
        final Marker m = enter_section_modified(builder);
        final IElementType t = expect(KW_CATCH);
        if (t == null) {
            cleanup(m, LAST_CATCH);
            return false;
        }
        if (!parseStatementNoCaseNoDefault()) {
            cleanup(m, LAST_CATCH);
            return false;
        }
        exit_section_modified(builder, m, LAST_CATCH, true);
        return true;
    }

    /**
     * Parses a LinkageAttribute
     * <p>
     * $(GRAMMAR $(RULEDEF linkageAttribute):
     *   $(LITERAL 'extern') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '$(RPAREN)')
     * | $(LITERAL 'extern') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '-') $(LITERAL Identifier) $(LITERAL '$(RPAREN)')
     * | $(LITERAL 'extern') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '++') ($(LITERAL ',') $(RULE typeIdentifierPart) | $(RULE namespaceList) | $(LITERAL 'struct') | $(LITERAL 'class'))? $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseLinkageAttribute() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_EXTERN)) {
            cleanup(m, LINKAGE_ATTRIBUTE);
            return false;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, LINKAGE_ATTRIBUTE);
            return false;
        }
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, LINKAGE_ATTRIBUTE);
            return false;
        }
        if (currentIs(OP_PLUS_PLUS)) {
            advance();
            if (currentIs(OP_COMMA)) {
                advance();
                if (currentIsOneOf(KW_STRUCT, KW_CLASS))
                    advance();
                else if (currentIs(ID)) {
                    if (!parseTypeIdentifierPart()) {
                        cleanup(m, LINKAGE_ATTRIBUTE);
                        return false;
                    }
                } else {
                    if (!parseNamespaceList()) {
                        cleanup(m, LINKAGE_ATTRIBUTE);
                        return false;
                    }
                }
            }
        } else if (currentIs(OP_MINUS)) {
            advance();
            if (!tokenCheck(ID)) {
                cleanup(m, LINKAGE_ATTRIBUTE);
                return false;
            }
        }
        expect(OP_PAR_RIGHT);
        exit_section_modified(builder, m, LINKAGE_ATTRIBUTE, true);
        return true;
    }

    /**
     * Parses a MemberFunctionAttribute
     * <p>
     * $(GRAMMAR $(RULEDEF memberFunctionAttribute):
     * $(RULE functionAttribute)
     * | $(LITERAL 'immutable')
     * | $(LITERAL 'inout')
     * | $(LITERAL 'shared')
     * | $(LITERAL 'const')
     * | $(LITERAL 'return')
     * | $(LITERAL 'scope')
     * | $(LITERAL 'throw')
     * ;)
     */
    boolean parseMemberFunctionAttribute() {
        final Marker m = enter_section_modified(builder);
        if (!moreTokens()) {
            cleanup(m, MEMBER_FUNCTION_ATTRIBUTE);
            return false;
        }
        final IElementType i = current();
        if (i.equals(OP_AT)) {
            if (!parseAtAttribute()) {
                cleanup(m, MEMBER_FUNCTION_ATTRIBUTE);
                return false;
            }
        } else if (i.equals(KW_IMMUTABLE) || i.equals(KW_INOUT) || i.equals(KW_SHARED) || i.equals(KW_CONST) || i.equals(KW_PURE) || i.equals(KW_NOTHROW) || i.equals(KW_RETURN) || i.equals(KW_SCOPE) || i.equals(KW_THROW)) {
            advance();
        } else {
            error("Member function attribute expected");
        }
        exit_section_modified(builder, m, MEMBER_FUNCTION_ATTRIBUTE, true);
        return true;
    }

    /**
     * Parses a MissingFunctionBody
     *
     * $(GRAMMAR $(RULEDEF missingFunctionBody):
     *       $(LITERAL ';')
     *     | $(RULE functionContract)* $(LITERAL ';')
     *     ;)
     */
    public boolean parseMissingFunctionBody() {
        final Marker m = enter_section_modified(builder);
        boolean haveContract = false;
        boolean lastIsOutContractExpression = false;
        while (currentIsOneOf(KW_IN, KW_OUT)) {
            boolean isOut = currentIs(KW_OUT);
            Bookmark b = setBookmark();
            if (parseFunctionContract(false)) {
                lastIsOutContractExpression = isOut;
            } else {
                lastIsOutContractExpression = false;
                goToBookmark(b);
                if (parseFunctionContract())
                    haveContract = true;
            }
            abandonBookmark(b);
        }
        if (!haveContract || lastIsOutContractExpression) {
            if (expect(OP_SCOLON) == null) {
                cleanup(m, MISSING_FUNCTION_BODY);
                return false;
            }
        } else if (moreTokens() && currentIsOneOf(KW_DO, OP_LAMBDA_ARROW)) {
            cleanup(m, MISSING_FUNCTION_BODY);
            return false;
        }
        exit_section_modified(builder, m, MISSING_FUNCTION_BODY, true);
        return true;
    }

    /**
     * Parses a MixinDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF mixinDeclaration):
     *   $(RULE mixinExpression) $(LITERAL ';')
     * | $(RULE templateMixinExpression) $(LITERAL ';')
     * ;)
     */
    boolean parseMixinDeclaration() {
        final Marker m = enter_section_modified(builder);
        if (peekIsOneOf(ID, KW_TYPEOF, OP_DOT)) {
            if (!parseTemplateMixinExpression()) {
                cleanup(m, MIXIN_DECLARATION);
                return false;
            }
        } else if (peekIs(OP_PAR_LEFT)) {
            if (!parseMixinExpression()) {
                cleanup(m, MIXIN_DECLARATION);
                return false;
            }
        } else {
            error("`(` or identifier expected");
            exit_section_modified(builder, m, MIXIN_DECLARATION, true);
            return false;
        }
        expect(OP_SCOLON);
        exit_section_modified(builder, m, MIXIN_DECLARATION, true);
        return true;
    }

    /**
     * Parses a MixinExpression
     * <p>
     * $(GRAMMAR $(RULEDEF mixinExpression):
     * $(LITERAL 'mixin') $(LITERAL '$(LPAREN)') $(RULE argumentList) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseMixinExpression() {
        final Marker m = enter_section_modified(builder);
        expect(KW_MIXIN);
        expect(OP_PAR_LEFT);
        if (!parseArgumentList().first) {
            cleanup(m, MIXIN_EXPRESSION);
            return false;
        }
        expect(OP_PAR_RIGHT);
        exit_section_modified(builder, m, MIXIN_EXPRESSION, true);
        return true;
    }

    /**
     * Parses a MixinTemplateDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF mixinTemplateDeclaration):
     * $(LITERAL 'mixin') $(RULE templateDeclaration)
     * ;)
     */
    boolean parseMixinTemplateDeclaration() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_MIXIN)) {
            cleanup(m, MIXIN_TEMPLATE_DECLARATION);
            return false;
        }
        if (!parseTemplateDeclaration()) {
            cleanup(m, MIXIN_TEMPLATE_DECLARATION);
            return false;
        }
        exit_section_modified(builder, m, MIXIN_TEMPLATE_DECLARATION, true);
        return true;
    }

    /**
     * Parses a MixinTemplateName
     * <p>
     * $(GRAMMAR $(RULEDEF mixinTemplateName):
     * $(RULE symbol)
     * | $(RULE typeofExpression) $(LITERAL '.') $(RULE identifierOrTemplateChain)
     * ;)
     */
    boolean parseMixinTemplateName() {
        final Marker m = enter_section_modified(builder);
        if (currentIs(KW_TYPEOF)) {
            if (!parseTypeofExpression()) {
                cleanup(m, MIXIN_TEMPLATE_NAME);
                return false;
            }
            expect(OP_DOT);
            if (!parseIdentifierOrTemplateChain()) {
                cleanup(m, MIXIN_TEMPLATE_NAME);
                return false;
            }
        } else if (!parseSymbol()) {
            cleanup(m, MIXIN_TEMPLATE_NAME);
            return false;
        }
        exit_section_modified(builder, m, MIXIN_TEMPLATE_NAME, true);
        return true;
    }

    /**
     * Parses a Module
     * <p>
     * $(GRAMMAR $(RULEDEF module):
     * $(RULE moduleDeclaration)? $(RULE declaration)*
     * ;)
     */
    boolean parseModule() {
        if (currentIs(SHEBANG)) {
            advance();
        }
        boolean isDeprecatedModule = false;
        final Bookmark b = setBookmark();
        while (currentIs(OP_AT) || currentIs(KW_DEPRECATED)) {
            parseAttribute();
        }
        boolean isModule = currentIs(KW_MODULE);
        goToBookmark(b);
        if (isModule) {
//                c = allocator.setCheckpoint();
            parseModuleDeclaration();
//                allocator.rollback(c);
        }
        while (moreTokens()) {
//                c = allocator.setCheckpoint();
            parseDeclaration(true, true);
//                    allocator.rollback(c);
        }
        return true;
    }

    /**
     * Parses a ModuleDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF moduleDeclaration):
     * $(RULE atAttribute)* $(RULE deprecated)? $(RULE atAttribute)* $(LITERAL 'module') $(RULE identifierChain) $(LITERAL ';')
     * ;)
     */
    boolean parseModuleDeclaration() {
        final Marker m = enter_section_modified(builder);
        while (currentIs(OP_AT)) {
            parseAttribute();
        }
        if (currentIs(KW_DEPRECATED))
            if (!parseDeprecated()) {
                cleanup(m, MODULE_DECLARATION);
                return false;
            }
        while (currentIs(OP_AT)) {
            parseAttribute();
        }
        final IElementType start = expect(KW_MODULE);
        if (start == null) {
            cleanup(m, MODULE_DECLARATION);
            return false;
        }
        if (!parseIdentifierChain()) {
            cleanup(m, MODULE_DECLARATION);
            return false;
        }
        final IElementType end = expect(OP_SCOLON);
        exit_section_modified(builder, m, MODULE_DECLARATION, true);
        return true;
    }

    /**
     * Parses a MulExpression.
     * <p>
     * $(GRAMMAR $(RULEDEF mulExpression):
     *   $(RULE powExpression)
     * | $(RULE mulExpression) ($(LITERAL '*') | $(LITERAL '/') | $(LITERAL '%')) $(RULE powExpression)
     * ;)
     */
    boolean parseMulExpression() {
        final Marker marker = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean b = parseLeftAssocBinaryExpression(toParseExpression, "MulExpression", "PowExpression",
            OP_ASTERISK, OP_DIV, OP_MOD);
        if (!toParseExpression.element) {
            marker.drop();
//            beginnings.remove(marker);
            return b;
        }
        exit_section_modified(builder, marker, MUL_EXPRESSION, b);
        return b;
    }

    /**
     * Parses a NamespaceList.
     *
     * $(GRAMMAR $(RULEDEF namespaceList):
     *     $(RULE ternaryExpression) ($(LITERAL ',') $(RULE ternaryExpression)?)* $(LITERAL ',')?
     *     ;)
     */
    public boolean parseNamespaceList() {
        final Marker marker = enter_section_modified(builder);
        final boolean b = parseCommaSeparatedRule("NamespaceList", "TernaryExpression", true);
        exit_section_modified(builder, marker, NAMESPACE_LIST, b);
        return b;
    }

    /**
     * Parses a NewAnonClassExpression
     * <p>
     * $(GRAMMAR $(RULEDEF newAnonClassExpression):
     * $(LITERAL 'new') $(LITERAL 'class') $(RULE arguments)? $(RULE baseClassList)? $(RULE structBody)
     * ;)
     */
    boolean parseNewAnonClassExpression() {
        final Marker m = enter_section_modified(builder);
        expect(KW_NEW);
        expect(KW_CLASS);
        if (currentIs(OP_PAR_LEFT))
            if (!parseArguments()) {
                cleanup(m, NEW_ANON_CLASS_EXPRESSION);
                return false;
            }
        if (!currentIs(OP_BRACES_LEFT))
            if (!parseBaseClassList()) {
                cleanup(m, NEW_ANON_CLASS_EXPRESSION);
                return false;
            }
        if (!parseStructBody()) {
            cleanup(m, NEW_ANON_CLASS_EXPRESSION);
            return false;
        }
        exit_section_modified(builder, m, NEW_ANON_CLASS_EXPRESSION, true);
        return true;
    }

    /**
     * Parses a NewExpression
     * <p>
     * $(GRAMMAR $(RULEDEF newExpression):
     * $(LITERAL 'new') $(RULE type) (($(LITERAL '[') $(RULE assignExpression) $(LITERAL ']')) | $(RULE arguments))?
     * | $(RULE newAnonClassExpression)
     * ;)
     */
    boolean parseNewExpression() {
        final Marker m = enter_section_modified(builder);
        if (peekIs(KW_CLASS)) {
            if (!parseNewAnonClassExpression()) {
                cleanup(m, NEW_EXPRESSION);
                return false;
            }
        } else {
            expect(KW_NEW);
            if (!parseType().first) {
                cleanup(m, NEW_EXPRESSION);
                return false;
            }
            if (currentIs(OP_BRACKET_LEFT)) {
                advance();
                if (!parseAssignExpression()) {
                    cleanup(m, NEW_EXPRESSION);
                    return false;
                }
                expect(OP_BRACKET_RIGHT);
            } else if (currentIs(OP_PAR_LEFT))
                if (!parseArguments()) {
                    cleanup(m, NEW_EXPRESSION);
                    return false;
                }
        }
        exit_section_modified(builder, m, NEW_EXPRESSION, true);
        return true;
    }

    /**
     * Parses a NonVoidInitializer
     * <p>
     * $(GRAMMAR $(RULEDEF nonVoidInitializer):
     *   $(RULE assignExpression)
     * | $(RULE arrayLiteral)
     * | $(RULE structInitializer)
     * ;)
     */
    boolean parseNonVoidInitializer() {
        final Marker m = enter_section_modified(builder);
        boolean assignExpressionParsed = false;
        boolean arrayInitializerParsed = false;
        boolean structInitializerParsed = false;
        if (currentIs(OP_BRACES_LEFT)) {
            final IElementType b = peekPastBraces();
            if (b != null && (b.equals(OP_PAR_LEFT))) {
                if (!parseAssignExpression()) {
                    cleanup(m, NON_VOID_INITIALIZER);
                    return false;
                }
                assignExpressionParsed = true;
            } else {
                assignExpressionParsed = true;
                assert (currentIs(OP_BRACES_LEFT));
                final Bookmark bookmark = setBookmark();
                final boolean initializer = parseStructInitializer();
                if (initializer) {
                    structInitializerParsed = true;
                    abandonBookmark(bookmark);
                } else {
                    goToBookmark(bookmark);
                    if (!parseAssignExpression()) {
                        cleanup(m, NON_VOID_INITIALIZER);
                        return false;
                    }
                    assignExpressionParsed = true;
                }
            }
        } else if (currentIs(OP_BRACKET_LEFT)) {
            boolean isAA = false;
            Bookmark bk = setBookmark();
            advance();
            if (currentIs(OP_BRACKET_LEFT)) {
                advance();
                IElementType c = peekPastBrackets();
                isAA = c != null && c.equals(OP_COLON);
            }
            goToBookmark(bk);
            final IElementType b = peekPastBrackets();
            if (!isAA && b != null && (b.equals(OP_COMMA)
                || b.equals(OP_PAR_RIGHT)
                || b.equals(OP_BRACKET_RIGHT)
                || b.equals(OP_BRACES_RIGHT)
                || b.equals(OP_SCOLON))) {
                if (!parseArrayLiteral()) {
                    cleanup(m, NON_VOID_INITIALIZER);
                    return false;
                }
                arrayInitializerParsed = true;
            } else if (!parseAssignExpression()) {
                cleanup(m, NON_VOID_INITIALIZER);
                return false;
            } else {
                assignExpressionParsed = true;
            }
        } else if (!parseAssignExpression()) {
            cleanup(m, NON_VOID_INITIALIZER);
            return false;
        } else {
            assignExpressionParsed = true;
        }
        if (!assignExpressionParsed && !structInitializerParsed && !arrayInitializerParsed) {
            cleanup(m, NON_VOID_INITIALIZER);
            return false;
        }
        exit_section_modified(builder, m, NON_VOID_INITIALIZER, true);
        return true;
    }

    /**
     * Parses Operands
     * <p>
     * $(GRAMMAR $(RULEDEF operands):
     *   $(RULE asmExp)
     * | $(RULE asmExp) $(LITERAL ',') $(RULE operands)
     * ;)
     */
    boolean parseOperands() {
        final Marker marker = enter_section_modified(builder);
        while (true) {
            if (!(parseAsmExp())) {
                cleanup(marker, OPERANDS);
                return false;
            }
            if (currentIs(OP_COMMA))
                advance();
            else
                break;
        }
        exit_section_modified(builder, marker, OPERANDS, true);
        return true;
    }

    /**
     * Parses an OrExpression
     * <p>
     * $(GRAMMAR $(RULEDEF orExpression):
     *   $(RULE xorExpression)
     * | $(RULE orExpression) $(LITERAL '|') $(RULE xorExpression)
     * ;)
     */
    boolean parseOrExpression() {
        final Marker marker = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean b = parseLeftAssocBinaryExpression(toParseExpression, "OrExpression", "XorExpression",
            OP_OR);
        if (!toParseExpression.element) {
            marker.drop();
//            beginnings.remove(marker);
            return b;
        }
        exit_section_modified(builder, marker, OR_EXPRESSION, b);
        return b;
    }

    /**
     * Parses an OrOrExpression
     * <p>
     * $(GRAMMAR $(RULEDEF orOrExpression):
     *   $(RULE andAndExpression)
     * | $(RULE orOrExpression) $(LITERAL '||') $(RULE andAndExpression)
     * ;)
     */
    boolean parseOrOrExpression() {
        //todo move all this stuff into parseLeftAssocBinary
        final IElementType type = OR_OR_EXPRESSION;
        final String expressionPartType = "AndAndExpression";
        final String expressionType = "OrOrExpression";
        final IElementType[] tokens = {OP_BOOL_OR};
        final Marker marker = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean b = parseLeftAssocBinaryExpression(toParseExpression, expressionType, expressionPartType,
            tokens);
        if (!toParseExpression.element) {
            marker.drop();
//            beginnings.remove(marker);
            return b;
        }
        exit_section_modified(builder, marker, type, b);
        return b;
    }

    /**
     * Parses an OutContractExpression
     *
     * $(GRAMMAR $(RULEDEF outContractExpression):
     *     $(LITERAL 'out') $(LITERAL '$(LPAREN)') $(LITERAL Identifier)? $(LITERAL ';') $(RULE assertArguments) $(LITERAL '$(RPAREN)')
     *     ;)
     */
    public boolean parseOutContractExpression() {
        final Marker m = enter_section_modified(builder);
        final IElementType o = expect(KW_OUT);
        if (o == null) {
            cleanup(m, OUT_CONTRACT_EXPRESSION);
            return false;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, OUT_CONTRACT_EXPRESSION);
            return false;
        }
        if (currentIs(ID)) {
            advance();
        }
        if (!tokenCheck(OP_SCOLON)) {
            cleanup(m, OUT_CONTRACT_EXPRESSION);
            return false;
        }
        if (!parseAssertArguments()) {
            cleanup(m, OUT_CONTRACT_EXPRESSION);
            return false;
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, OUT_CONTRACT_EXPRESSION);
            return false;
        }
        exit_section_modified(builder, m, OUT_CONTRACT_EXPRESSION, true);
        return true;
    }

    /**
     * Parses an OutStatement
     * <p>
     * $(GRAMMAR $(RULEDEF outStatement):
     * $(LITERAL 'out') ($(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '$(RPAREN)'))? $(RULE blockStatement)
     * ;)
     */
    boolean parseOutStatement() {
        final Marker m = enter_section_modified(builder);
        final IElementType o = expect(KW_OUT);
        if (o == null) {
            cleanup(m, OUT_STATEMENT);
            return false;
        }
        if (currentIs(OP_PAR_LEFT)) {
            advance();
            final IElementType ident = expect(ID);
            if (ident == null) {
                cleanup(m, OUT_STATEMENT);
                return false;
            }
            expect(OP_PAR_RIGHT);
        }
        if (!parseBlockStatement()) {
            cleanup(m, OUT_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, OUT_STATEMENT, true);
        return true;
    }

    /**
     * Parses a Parameter
     * <p>
     * $(GRAMMAR $(RULEDEF parameter):
     *   $(RULE parameterAttribute)* $(RULE type)
     * | $(RULE parameterAttribute)* $(RULE type) $(LITERAL Identifier)? $(LITERAL '...')
     * | $(RULE parameterAttribute)* $(RULE type) $(LITERAL Identifier)? ($(LITERAL '=') $(RULE assignExpression))?
     * ;)
     */
    boolean parseParameter() {
        final Marker m = enter_section_modified(builder);
        while (moreTokens()) {
            final IElementType type = parseParameterAttribute(false);
            if (type == null)
                break;
        }
        // Parsed the attributes of the variadic attributes.
        // Abort and defer to parseVariadicArgumentsAttributes
        if (currentIs(OP_TRIPLEDOT)) {
            cleanup(m, PARAMETER);
            return false;
        }

        if (!parseType().first) {
            cleanup(m, PARAMETER);
            return false;
        }
        if (currentIs(ID)) {
            advance();
            if (currentIs(OP_TRIPLEDOT)) {
                advance();
            } else if (currentIs(OP_EQ)) {
                advance();
                if (!parseAssignExpression()) {
                    cleanup(m, PARAMETER);
                    return false;
                }
                if (currentIs(OP_TRIPLEDOT)) {
                    advance();
                }
            } else if (currentIs(OP_BRACKET_LEFT)) {
                while (moreTokens() && currentIs(OP_BRACKET_LEFT))
                    if (!(parseTypeSuffix())) {
                        cleanup(m, PARAMETER);
                        return false;
                    }
            }
        } else if (currentIs(OP_TRIPLEDOT)) {
            advance();
        } else if (currentIs(OP_EQ)) {
            advance();
            if (!parseAssignExpression()) {
                cleanup(m, PARAMETER);
                return false;
            }
        }
        exit_section_modified(builder, m, PARAMETER, true);
        return true;
    }

    IElementType parseParameterAttribute() {
        return parseParameterAttribute(false);
    }

    /**
     * Parses a ParameterAttribute
     * <p>
     * $(GRAMMAR $(RULEDEF parameterAttribute):
     *   $(RULE atAttribute)
     * | $(RULE typeConstructor)
     * | $(LITERAL 'final')
     * | $(LITERAL 'in')
     * | $(LITERAL 'lazy')
     * | $(LITERAL 'out')
     * | $(LITERAL 'ref')
     * | $(LITERAL 'scope')
     * | $(LITERAL 'auto')
     * | $(LITERAL 'return')
     * ;)
     */
    IElementType parseParameterAttribute(final boolean validate) {
        final IElementType i = current();
        if (i.equals(OP_AT)) {
            if (!parseAtAttribute()) {
                error("Parameter attribute expected");
                return null;
            }
            return current(); // Hack because libdparse return the aa node
        } else if (i.equals(KW_IMMUTABLE) || i.equals(KW_SHARED) || i.equals(KW_CONST) || i.equals(KW_INOUT)) {
            if (peekIs(OP_PAR_LEFT))
                return null;
            else
                return advance();
        } else if (i.equals(KW_FINAL) || i.equals(KW_IN) || i.equals(KW_LAZY) || i.equals(KW_OUT) || i.equals(KW_REF) || i.equals(KW_SCOPE) || i.equals(KW_AUTO) || i.equals(KW_RETURN)) {
            return advance();
        } else {
            if (validate) {
                error("Parameter attribute expected");
            }
            return null;
        }
    }

    /**
     * Parses Parameters
     * <p>
     * $(GRAMMAR $(RULEDEF parameters):
     *   $(LITERAL '$(LPAREN)') $(RULE parameter) ($(LITERAL ',') $(RULE parameter))* ($(LITERAL ',') $(LITERAL '...'))? $(LITERAL '$(RPAREN)')
     * | $(LITERAL '$(LPAREN)') $(LITERAL '...') $(LITERAL '$(RPAREN)')
     * | $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseParameters() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, PARAMETERS);
            return false;
        }

        if (currentIs(OP_PAR_RIGHT)) {
            advance(); // )
            exit_section_modified(builder, m, PARAMETERS, true);
            return true;
        }
        if (currentIs(OP_TRIPLEDOT)) {
            advance();
            if (!tokenCheck(OP_PAR_RIGHT)) {
                cleanup(m, PARAMETERS);
                return false;
            }
            exit_section_modified(builder, m, PARAMETERS, true);
            return true;
        }
        while (moreTokens()) {
            if (currentIs(OP_TRIPLEDOT)) {
                advance();
                break;
            }
            if (currentIs(OP_PAR_RIGHT))
                break;
            // Save starting point to deal with attributed variadics, e.g.
            // int printf(in char* format, scope const ...);
            Bookmark b = setBookmark();
            if (!(parseParameter())) {
                // parseParameter fails for C-style variadics, they are parsed below
                if (!currentIs(OP_TRIPLEDOT)) {
                    abandonBookmark(b);
                    cleanup(m, PARAMETERS);
                    return false;
                }
                // Reset to the beginning of the current parameters
                goToBookmark(b);
                if (!parseVariadicArgumentsAttributes()) {
                    cleanup(m, PARAMETERS);
                    return false;
                }
                if (!tokenCheck(OP_TRIPLEDOT)) {
                    cleanup(m, PARAMETERS);
                    return false;
                }
            } else {
                abandonBookmark(b);
            }
            if (currentIs(OP_COMMA))
                advance();
            else
                break;
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, PARAMETERS);
            return false;
        }
        exit_section_modified(builder, m, PARAMETERS, true);
        return true;
    }

    /**
     * Parses attributes of C-style variadic parameters.
     *
     * $(GRAMMAR $(RULEDEF variadicArgumentsAttributes):
     *       $(RULE variadicArgumentsAttribute)+
     *     ;)
     */
    public boolean parseVariadicArgumentsAttributes() {
        final Marker m = enter_section_modified(builder);
        while (moreTokens() && !currentIs(OP_TRIPLEDOT)) {
            if (!parseVariadicArgumentsAttribute()) {
                cleanup(m, VARIADIC_ARGUMENTS_ATTRIBUTES);
                return false;
            }
        }
        exit_section_modified(builder, m, VARIADIC_ARGUMENTS_ATTRIBUTES, true);
        return true;
    }

    /**
     * Parses an attribute of C-style variadic parameters.
     *
     * $(GRAMMAR $(RULEDEF variadicArgumentsAttribute):
     *       $(LITERAL 'const')
     *     | $(LITERAL 'immutable')
     *     | $(LITERAL 'scope')
     *     | $(LITERAL 'shared')
     *     | $(LITERAL 'return')
     *     ;)
     */
    public boolean parseVariadicArgumentsAttribute() {
        final Marker m = enter_section_modified(builder);
        if (!currentIsOneOf(KW_CONST, KW_IMMUTABLE, KW_SHARED, KW_SCOPE, KW_RETURN)) {
            error("`const`, `immutable`, `shared`, `scope` or `return` expected");
            cleanup(m, VARIADIC_ARGUMENTS_ATTRIBUTE);
            return false;
        }
        advance();
        exit_section_modified(builder, m, VARIADIC_ARGUMENTS_ATTRIBUTE, true);
        return true;
    }

    /**
     * Parses a Postblit
     * <p>
     * $(GRAMMAR $(RULEDEF postblit):
     * $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL 'this') $(LITERAL '$(RPAREN)') $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ';'))
     * ;)
     */
    boolean parsePostblit() {
        final Marker m = enter_section_modified(builder);
        advance();
        advance();
        advance();
        advance();
        while (currentIsMemberFunctionAttribute())
            if (!parseMemberFunctionAttribute()) {
                cleanup(m, POSTBLIT);
                return false;
            }
        if (currentIs(OP_SCOLON))
            advance();
        else if (!parseFunctionBody()) {
            cleanup(m, POSTBLIT);
            return false;
        }
        exit_section_modified(builder, m, POSTBLIT, true);
        return true;
    }

    /**
     * Parses a PowExpression
     * <p>
     * $(GRAMMAR $(RULEDEF powExpression):
     * $(RULE unaryExpression)
     * | $(RULE powExpression) $(LITERAL '^^') $(RULE unaryExpression)
     * ;)
     */
    boolean parsePowExpression() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean b = parseLeftAssocBinaryExpression(toParseExpression, "PowExpression", "UnaryExpression",
            OP_POW);
        if (!toParseExpression.element) {
            marker.drop();
//            beginnings.remove(marker);
            return b;
        }
        exit_section_modified(builder, marker, POW_EXPRESSION, b);
        return b;
    }

    /**
     * Parses a PragmaDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF pragmaDeclaration):
     * $(RULE pragmaExpression) $(LITERAL ';')
     * ;)
     */
    boolean parsePragmaDeclaration() {
        final Marker marker = enter_section_modified(builder);
        final boolean res = simpleParse("PragmaDeclaration", "pragmaExpression|parsePragmaExpression", OP_SCOLON);
        exit_section_modified(builder, marker, PRAGMA_DECLARATION, true);
        return res;
    }

    /**
     * Parses a PragmaExpression
     * <p>
     * $(GRAMMAR $(RULEDEF pragmaExpression):
     * $(RULE 'pragma') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) ($(LITERAL ',') $(RULE argumentList))? $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parsePragmaExpression() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_modified(builder);
        expect(KW_PRAGMA);
        expect(OP_PAR_LEFT);
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, PRAGMA_EXPRESSION);
            return false;
        }
        if (currentIs(OP_COMMA)) {
            advance();
            if (!parseArgumentList().first) {
                cleanup(m, PRAGMA_EXPRESSION);
                return false;
            }
        }
        expect(OP_PAR_RIGHT);
        exit_section_modified(builder, m, PRAGMA_EXPRESSION, true);
        return true;
    }

    /**
     * Parses a PragmaStatement
     *
     * $(GRAMMAR $(RULEDEF pragmaStatement):
     *       $(RULE pragmaExpression) $(RULE statement)
     *     | $(RULE pragmaExpression) $(RULE blockStatement)
     *     | $(RULE pragmaExpression) $(LITERAL ';')
     *     ;)
     */
    public boolean parsePragmaStatement() {
        final Marker m = enter_section_modified(builder);
        if (!parsePragmaExpression()) {
            cleanup(m, PRAGMA_STATEMENT);
            return false;
        }
        if (current().equals(OP_BRACES_LEFT)) {
            if (!parseBlockStatement()) {
                cleanup(m, PRAGMA_STATEMENT);
                return false;
            }
        } else if (current().equals(OP_SCOLON)) {
            advance();
        } else {
            if (!parseStatement()) {
                cleanup(m, PRAGMA_STATEMENT);
                return false;
            }
        }
        exit_section_modified(builder, m, PRAGMA_STATEMENT, true);
        return true;
    }

    /**
     * Parses a PrimaryExpression
     * <p>
     * $(GRAMMAR $(RULEDEF primaryExpression):
     * $(RULE identifierOrTemplateInstance)
     * | $(LITERAL '.') $(RULE identifierOrTemplateInstance)
     * | $(RULE typeConstructor) $(LITERAL '$(LPAREN)') $(RULE basicType) $(LITERAL '$(RPAREN)') $(LITERAL '.') $(LITERAL Identifier)
     * | $(RULE basicType) $(LITERAL '.') $(LITERAL Identifier)
     * | $(RULE basicType) $(RULE arguments)
     * | $(RULE typeofExpression)
     * | $(RULE typeidExpression)
     * | $(RULE vector)
     * | $(RULE arrayLiteral)
     * | $(RULE assocArrayLiteral)
     * | $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)')
     * | $(RULE isExpression)
     * | $(RULE functionLiteralExpression)
     * | $(RULE traitsExpression)
     * | $(RULE mixinExpression)
     * | $(RULE importExpression)
     * | $(LITERAL '$')
     * | $(LITERAL 'this')
     * | $(LITERAL 'super')
     * | $(LITERAL '_null')
     * | $(LITERAL '_true')
     * | $(LITERAL '_false')
     * | $(LITERAL '___DATE__')
     * | $(LITERAL '___TIME__')
     * | $(LITERAL '___TIMESTAMP__')
     * | $(LITERAL '___VENDOR__')
     * | $(LITERAL '___VERSION__')
     * | $(LITERAL '___FILE__')
     * | $(LITERAL '___LINE__')
     * | $(LITERAL '___MODULE__')
     * | $(LITERAL '___FUNCTION__')
     * | $(LITERAL '___PRETTY_FUNCTION__')
     * | $(LITERAL IntegerLiteral)
     * | $(LITERAL FloatLiteral)
     * | $(LITERAL StringLiteral)+
     * | $(LITERAL CharacterLiteral)
     * ;)
     */
    boolean parsePrimaryExpression() {
        final Marker m = enter_section_modified(builder);
        if (!moreTokens()) {
            error("Expected primary statement instead of EOF");
            exit_section_modified(builder, m, PRIMARY_EXPRESSION, true);
            return false;
        }
        final IElementType i = current();
        if (i.equals(OP_DOT)) {
            advance();
            if (!primaryExpressionIdentifierCase(m)) return false;
        } else if (i.equals(ID)) {
            if (!primaryExpressionIdentifierCase(m)) return false;
        } else if (i.equals(KW_IMMUTABLE) || i.equals(KW_CONST) || i.equals(KW_INOUT) || i.equals(KW_SHARED)) {
            advance();
            expect(OP_PAR_LEFT);
            if (!parseType().first) {
                cleanup(m, PRIMARY_EXPRESSION);
                return false;
            }
            expect(OP_PAR_RIGHT);
            expect(OP_DOT);
            final IElementType ident = expect(ID);
        } else if (isBasicType(i)) {
            advance();
            if (currentIs(OP_DOT)) {
                advance();
                final IElementType t = expect(ID);
            } else if (currentIs(OP_PAR_LEFT))
                if (!parseArguments()) {
                    cleanup(m, PRIMARY_EXPRESSION);
                    return false;
                }
        } else if (i.equals(KW_FUNCTION) || i.equals(KW_DELEGATE) || i.equals(OP_BRACES_LEFT) || i
            .equals(KW_IN) || i.equals(KW_OUT) || i
            .equals(KW_DO)) {
            if (!parseFunctionLiteralExpression()) {
                cleanup(m, PRIMARY_EXPRESSION);
                return false;
            }
        } else if (i.equals(KW_TYPEOF)) {
            if (!parseTypeofExpression()) {
                cleanup(m, PRIMARY_EXPRESSION);
                return false;
            }
        } else if (i.equals(KW_TYPEID)) {
            if (!parseTypeidExpression()) {
                cleanup(m, PRIMARY_EXPRESSION);
                return false;
            }
        } else if (i.equals(KW___VECTOR)) {
            if (!parseVector()) {
                cleanup(m, PRIMARY_EXPRESSION);
                return false;
            }
        } else if (i.equals(OP_BRACKET_LEFT)) {
            if (isAssociativeArrayLiteral()) {
                if (!parseAssocArrayLiteral()) {
                    cleanup(m, PRIMARY_EXPRESSION);
                    return false;
                }
            } else if (!parseArrayLiteral()) {
                cleanup(m, PRIMARY_EXPRESSION);
                return false;
            }
        } else if (i.equals(KW_AUTO)) {
            if (peekAre(KW_REF, OP_PAR_LEFT)) {
                if (!parseFunctionLiteralExpression()) {
                    cleanup(m, PRIMARY_EXPRESSION);
                    return false;
                }
            } else {
                // goto default
                error("Primary expression expected");
                exit_section_modified(builder, m, PRIMARY_EXPRESSION, true);
                return false;
            }
        } else if (i.equals(KW_REF)) {
            if (peekIs(OP_PAR_LEFT)) {
                if (!parseFunctionLiteralExpression()) {
                    cleanup(m, PRIMARY_EXPRESSION);
                    return false;
                }
            } else {
                // goto default
                error("Primary expression expected");
                exit_section_modified(builder, m, PRIMARY_EXPRESSION, true);
                return false;
            }
        } else if (i.equals(OP_PAR_LEFT)) {
            final Bookmark b = setBookmark();
            skipParens();
            while (isAttribute())
                parseAttribute();
            if (currentIsOneOf(OP_LAMBDA_ARROW, OP_BRACES_LEFT)) {
                goToBookmark(b);
                if (!parseFunctionLiteralExpression()) {
                    cleanup(m, PRIMARY_EXPRESSION);
                    return false;
                }
            } else {
                goToBookmark(b);
                advance();
                if (!parseExpression()) {
                    cleanup(m, PRIMARY_EXPRESSION);
                    return false;
                }
                if (!tokenCheck(OP_PAR_RIGHT)) {
                    cleanup(m, PRIMARY_EXPRESSION);
                    return false;
                }
            }
        } else if (i.equals(KW_IS)) {
            if (!parseIsExpression()) {
                cleanup(m, PRIMARY_EXPRESSION);
                return false;
            }
        } else if (i.equals(KW___TRAITS)) {
            if (!parseTraitsExpression()) {
                cleanup(m, PRIMARY_EXPRESSION);
                return false;
            }
        } else if (i.equals(KW_MIXIN)) {
            if (!parseMixinExpression()) {
                cleanup(m, PRIMARY_EXPRESSION);
                return false;
            }
        } else if (i.equals(KW_IMPORT)) {
            if (!parseImportExpression()) {
                cleanup(m, PRIMARY_EXPRESSION);
                return false;
            }
        } else if (i.equals(KW_THIS) || i.equals(KW_SUPER) || isLiteral(i)) {
            if (currentIsOneOf(stringLiteralsArray)) {
                advance();
                boolean alreadyWarned = false;
                while (currentIsOneOf(stringLiteralsArray)) {
                    if (!alreadyWarned) {
                        warn("Implicit concatenation of String literals");
                        alreadyWarned = true;
                    }
                    advance();
                }
            } else
                advance();
        } else {
            error("Primary expression expected");
            exit_section_modified(builder, m, PRIMARY_EXPRESSION, true);
            return false;
        }
        exit_section_modified(builder, m, PRIMARY_EXPRESSION, true);
        return true;
    }

    private boolean isLiteral(final IElementType i) {
        return literals.contains(i);
    }

    private boolean isBasicType(final IElementType i) {
        return basicTypes.contains(i);
    }

    private boolean primaryExpressionIdentifierCase(final Marker m) {
        if (peekIs(OP_LAMBDA_ARROW)) {
            if (!parseFunctionLiteralExpression()) {
                cleanup(m, PRIMARY_EXPRESSION);
                return false;
            }
        } else if (!parseIdentifierOrTemplateInstance()) {
            cleanup(m, PRIMARY_EXPRESSION);
            return false;
        }
        return true;
    }

    /**
     * Parses a Register
     * <p>
     * $(GRAMMAR $(RULEDEF register):
     * $(LITERAL Identifier)
     * | $(LITERAL Identifier) $(LITERAL '$(LPAREN)') $(LITERAL IntegerLiteral) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseRegister() {
        final Marker m = enter_section_modified(builder);
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, REGISTER);
            return false;
        }
        if (currentIs(OP_PAR_LEFT)) {
            advance();
            final IElementType intLit = expect(INTEGER_LITERAL);
            if (intLit == null) {
                cleanup(m, REGISTER);
                return false;
            }
            expect(OP_PAR_RIGHT);
        }
        exit_section_modified(builder, m, REGISTER, true);
        return true;
    }

    boolean parseRelExpression() {
        return parseRelExpression(true);
    }

    /**
     * Parses a RelExpression
     * <p>
     * $(GRAMMAR $(RULEDEF relExpression):
     * $(RULE shiftExpression)
     * | $(RULE relExpression) $(RULE relOperator) $(RULE shiftExpression)
     * ;
     * $(RULEDEF relOperator):
     * $(LITERAL '<')
     * | $(LITERAL '<=')
     * | $(LITERAL '>')
     * | $(LITERAL '>=')
     * | $(LITERAL '!<>=')
     * | $(LITERAL '!<>')
     * | $(LITERAL '<>')
     * | $(LITERAL '<>=')
     * | $(LITERAL '!>')
     * | $(LITERAL '!>=')
     * | $(LITERAL '!<')
     * | $(LITERAL '!<=')
     * ;)
     */
    boolean parseRelExpression(final boolean parseShift)//(ExpressionNode shift)
    {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean b = parseLeftAssocBinaryExpression(toParseExpression, "RelExpression", "ShiftExpression", !parseShift, OP_LESS, OP_LESS_EQ, OP_GT, OP_GT_EQ, OP_UNORD_EQ, OP_UNORD, OP_LESS_GR, OP_LESS_GR_EQ, OP_NOT_GR, OP_NOT_GR_EQ, OP_NOT_LESS, OP_NOT_LESS_EQ);
        if (!toParseExpression.element) {
            marker.drop();
//            beginnings.remove(marker);
            return b;
        }
        exit_section_modified(builder, marker, REL_EXPRESSION, b);
        return b;
    }

    /**
     * Parses a ReturnStatement
     * <p>
     * $(GRAMMAR $(RULEDEF returnStatement):
     * $(LITERAL 'return') $(RULE expression)? $(LITERAL ';')
     * ;)
     */
    boolean parseReturnStatement() {
        final Marker m = enter_section_modified(builder);
        final IElementType start = expect(KW_RETURN);
        if (start == null) {
            cleanup(m, RETURN_STATEMENT);
            return false;
        }
        if (!currentIs(OP_SCOLON))
            if (!parseExpression()) {
                cleanup(m, RETURN_STATEMENT);
                return false;
            }
        final IElementType semicolon = expect(OP_SCOLON);
        if (semicolon == null) {
            cleanup(m, RETURN_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, RETURN_STATEMENT, true);
        return true;
    }

    /**
     * Parses a ScopeGuardStatement
     * <p>
     * $(GRAMMAR $(RULEDEF scopeGuardStatement):
     * $(LITERAL 'scope') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '$(RPAREN)') $(RULE statementNoCaseNoDefault)
     * ;)
     */
    boolean parseScopeGuardStatement() {
        final Marker m = enter_section_modified(builder);
        expect(KW_SCOPE);
        expect(OP_PAR_LEFT);
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, SCOPE_GUARD_STATEMENT);
            return false;
        }
        expect(OP_PAR_RIGHT);
        if (!parseStatementNoCaseNoDefault()) {
            cleanup(m, SCOPE_GUARD_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, SCOPE_GUARD_STATEMENT, true);
        return true;
    }

    /**
     * Parses a SharedStaticConstructor
     * <p>
     * $(GRAMMAR $(RULEDEF sharedStaticConstructor):
     * $(LITERAL 'shared') $(LITERAL 'static') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE MemberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ";"))
     * ;)
     */
    boolean parseSharedStaticConstructor() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_SHARED)) {
            cleanup(m, SHARED_STATIC_CONSTRUCTOR);
            return false;
        }
        if (!tokenCheck(KW_STATIC)) {
            cleanup(m, SHARED_STATIC_CONSTRUCTOR);
            return false;
        }
        final boolean b = parseStaticCtorDtorCommon();
        exit_section_modified(builder, m, SHARED_STATIC_CONSTRUCTOR, b);
        return b;
    }

    /**
     * Parses a SharedStaticDestructor
     * <p>
     * $(GRAMMAR $(RULEDEF sharedStaticDestructor):
     * $(LITERAL 'shared') $(LITERAL 'static') $(LITERAL '~') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE MemberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ";"))
     * ;)
     */
    boolean parseSharedStaticDestructor() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_SHARED)) {
            cleanup(m, SHARED_STATIC_DESTRUCTOR);
            return false;
        }
        if (!tokenCheck(KW_STATIC)) {
            cleanup(m, SHARED_STATIC_DESTRUCTOR);
            return false;
        }
        if (!tokenCheck(OP_TILDA)) {
            cleanup(m, SHARED_STATIC_DESTRUCTOR);
            return false;
        }
        final boolean b = parseStaticCtorDtorCommon();
        exit_section_modified(builder, m, SHARED_STATIC_DESTRUCTOR, b);
        return b;
    }

    /**
     * Parses a ShiftExpression
     * <p>
     * $(GRAMMAR $(RULEDEF shiftExpression):
     * $(RULE addExpression)
     * | $(RULE shiftExpression) ($(LITERAL '<<') | $(LITERAL '>>') | $(LITERAL '>>>')) $(RULE addExpression)
     * ;)
     */
    boolean parseShiftExpression() {
        final Marker marker = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean b = parseLeftAssocBinaryExpression(toParseExpression, "ShiftExpression", "AddExpression", OP_SH_LEFT, OP_SH_RIGHT, OP_USH_RIGHT);
        if (!toParseExpression.element) {
            marker.drop();
//            beginnings.remove(marker);
            return b;
        }
        exit_section_modified(builder, marker, SHIFT_EXPRESSION, b);
        return b;
    }

    /**
     * Parses a SingleImport
     * <p>
     * $(GRAMMAR $(RULEDEF import):
     * ($(LITERAL Identifier) $(LITERAL '='))? $(RULE identifierChain)
     * ;)
     */
    boolean parseSingleImport() {
        final Marker m = enter_section_modified(builder);
        if (startsWith(ID, OP_EQ)) {
            advance(); // identifier
            advance(); // =
        }
        if (!parseIdentifierChain()) {
            cleanup(m, SINGLE_IMPORT);
            return false;
        }
        exit_section_modified(builder, m, SINGLE_IMPORT, true);
        return true;
    }

    /**
     * Parses a ShortenedFunctionBody
     *
     * $(GRAMMAR $(RULEDEF shortenedFunctionBody):
     *      $(RULE inOutContractExpression)* $(LITERAL '=>') $(RULE expression) $(LITERAL ';')
     *     ;)
     */
    public boolean parseShortenedFunctionBody() {
        final Marker m = enter_section_modified(builder);
        boolean contract = false;
        while (currentIsOneOf(KW_IN, KW_OUT)) {
            if (parseFunctionContract(false)) {
                contract = true;
            }
        }
        if (!tokenCheck(OP_LAMBDA_ARROW)) {
            cleanup(m, SHORTENED_FUNCTION_BODY);
            return false;
        }
        if (!parseExpression()) {
            cleanup(m, SHORTENED_FUNCTION_BODY);
            return false;
        }
        if (!tokenCheck(OP_SCOLON)) {
            cleanup(m, SHORTENED_FUNCTION_BODY);
            return false;
        }
        exit_section_modified(builder, m, SHORTENED_FUNCTION_BODY, true);
        return true;
    }

    /**
     * Parses a SpecifiedFunctionBody
     *
     * $(GRAMMAR $(RULEDEF specifiedFunctionBody):
     *       $(LITERAL 'do')? $(RULE blockStatement)
     *     | $(RULE functionContract)* $(RULE inOutContractExpression) $(LITERAL 'do')? $(RULE blockStatement)
     *     | $(RULE functionContract)* $(RULE inOutStatement) $(LITERAL 'do') $(RULE blockStatement)
     *     ;)
     */
    public boolean parseSpecifiedFunctionBody() {
        final Marker m = enter_section_modified(builder);
        boolean requireDo = false;
        while (currentIsOneOf(KW_IN, KW_OUT)) {
            Bookmark b = setBookmark();
            if (parseFunctionContract(false)) {
                requireDo = false;
            }
            else {
                goToBookmark(b);
                if (parseFunctionContract()) {
                    requireDo = true;
                }
            }
            abandonBookmark(b);
        }
        if (currentIs(KW_DO) || currentIs(ID)) {
            advance();
            requireDo = false;
        }
        if (requireDo) {
            error("`do` expected after InStatement or OutStatement");
            cleanup(m, SPECIFIED_FUNCTION_BODY);
            return false;
        }
        if (!parseBlockStatement()) {
            cleanup(m, SPECIFIED_FUNCTION_BODY);
            return false;
        }
        exit_section_modified(builder, m, SPECIFIED_FUNCTION_BODY, true);
        return true;
    }

    /**
     * Parses a Statement
     * <p>
     * $(GRAMMAR $(RULEDEF statement):
     * $(RULE statementNoCaseNoDefault)
     * | $(RULE caseStatement)
     * | $(RULE caseRangeStatement)
     * | $(RULE defaultStatement)
     * ;)
     */
    boolean parseStatement() {
        final Marker m = enter_section_modified(builder);
        if (!moreTokens()) {
            error("Expected statement instead of EOF");
            exit_section_modified(builder, m, STATEMENT, true);
            return false;
        }
        final IElementType i = current();
        if (i.equals(KW_CASE)) {
            final Marker m_case = enter_section_modified(builder);
            advance();
            final Pair<Boolean, Integer> argumentListRetVal = parseArgumentList();
            final boolean argumentList = argumentListRetVal.first;
            if (!argumentList) {
                m_case.drop();
                cleanup(m, STATEMENT);
                return false;
            }
            if (argumentListRetVal.second == 1 && startsWith(OP_COLON, OP_DDOT)) {
                if (!parseCaseRangeStatement(m_case)) {
                    cleanup(m, STATEMENT);
                    return false;
                }
            } else {
                if (!parseCaseStatement(m_case)) {
                    cleanup(m, STATEMENT);
                    return false;
                }
            }
        } else if (i.equals(KW_DEFAULT)) {
            if (!parseDefaultStatement()) {
                cleanup(m, STATEMENT);
                return false;
            }
        } else {
            if (!parseStatementNoCaseNoDefault()) {
                cleanup(m, STATEMENT);
                return false;
            }
        }
        exit_section_modified(builder, m, STATEMENT, true);
        return true;
    }

    /**
     * Parses a StatementNoCaseNoDefault
     * <p>
     * $(GRAMMAR $(RULEDEF statementNoCaseNoDefault):
     *   $(RULE labeledStatement)
     * | $(RULE blockStatement)
     * | $(RULE ifStatement)
     * | $(RULE whileStatement)
     * | $(RULE doStatement)
     * | $(RULE forStatement)
     * | $(RULE foreachStatement)
     * | $(RULE switchStatement)
     * | $(RULE finalSwitchStatement)
     * | $(RULE continueStatement)
     * | $(RULE breakStatement)
     * | $(RULE returnStatement)
     * | $(RULE gotoStatement)
     * | $(RULE withStatement)
     * | $(RULE synchronizedStatement)
     * | $(RULE tryStatement)
     * | $(RULE scopeGuardStatement)
     * | $(RULE pragmaStatement)
     * | $(RULE asmStatement)
     * | $(RULE conditionalStatement)
     * | $(RULE staticAssertStatement)
     * | $(RULE versionSpecification)
     * | $(RULE debugSpecification)
     * | $(RULE expressionStatement)
     * ;)
     */
    boolean parseStatementNoCaseNoDefault() {
        final Marker m = enter_section_modified(builder);
        if (!moreTokens()) {
            error("Expected statement instead of EOF");
            exit_section_modified(builder, m, STATEMENT_NO_CASE_NO_DEFAULT, true);
            return false;
        }
        final IElementType i = current();
        if (i.equals(OP_BRACES_LEFT)) {
            if (!parseBlockStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_IF)) {
            if (!parseIfStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_WHILE)) {
            if (!parseWhileStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_DO)) {
            if (!parseDoStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_FOR)) {
            if (!parseForStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_FOREACH) || i.equals(KW_FOREACH_REVERSE)) {
            if (!parseForeachStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_SWITCH)) {
            if (!parseSwitchStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_CONTINUE)) {
            if (!parseContinueStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_BREAK)) {
            if (!parseBreakStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_RETURN)) {
            if (!parseReturnStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_GOTO)) {
            if (!parseGotoStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_WITH)) {
            if (!parseWithStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_SYNCHRONIZED)) {
            if (!parseSynchronizedStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_TRY)) {
            if (!parseTryStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_SCOPE)) {
            if (!parseScopeGuardStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_ASM)) {
            if (!parseAsmStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_PRAGMA)) {
            if (!parsePragmaStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_FINAL)) {
            if (peekIs(KW_SWITCH)) {

                if (!parseFinalSwitchStatement()) {
                    cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                    return false;
                }
                exit_section_modified(builder, m, STATEMENT_NO_CASE_NO_DEFAULT, true);
                return true;
            } else {
                error("`switch` expected");
                exit_section_modified(builder, m, STATEMENT_NO_CASE_NO_DEFAULT, true);
                return false;
            }
        } else if (i.equals(KW_DEBUG)) {
            if (peekIs(OP_EQ)) {
                if (!parseDebugSpecification()) {
                    cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                    return false;
                }
            } else if (!parseConditionalStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_VERSION)) {
            if (peekIs(OP_EQ)) {
                if (!parseVersionSpecification()) {
                    cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                    return false;
                }
            } else if (!parseConditionalStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else if (i.equals(KW_STATIC)) {
            if (peekIs(KW_IF)) {
                if (!parseConditionalStatement()) {
                    cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                    return false;
                }
            } else if (peekIs(KW_ASSERT)) {
                if (!parseStaticAssertStatement()) {
                    cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                    return false;
                }
            } else if (peekIs(KW_FOREACH) || peekIs(KW_FOREACH_REVERSE)) {
                if (!parseStaticForeachStatement()) {
                    cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                    return false;
                }
            } else {
                error("'if' or 'assert' expected.");
                exit_section_modified(builder, m, STATEMENT_NO_CASE_NO_DEFAULT, true);
                return false;
            }
        } else if (i.equals(ID)) {
            if (peekIs(OP_COLON)) {
                if (!parseLabeledStatement()) {
                    cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                    return false;
                }
                exit_section_modified(builder, m, STATEMENT_NO_CASE_NO_DEFAULT, true);
                return true;
            }
            if (!parseExpressionStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        } else {
            if (!parseExpressionStatement()) {
                cleanup(m, STATEMENT_NO_CASE_NO_DEFAULT);
                return false;
            }
        }
//            node.endLocation = tokens[index - 1].index;
        exit_section_modified(builder, m, STATEMENT_NO_CASE_NO_DEFAULT, true);
        return true;
    }

    /**
     * Parses a StaticAssertDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF staticAssertDeclaration):
     * $(RULE staticAssertStatement)
     * ;)
     */
    boolean parseStaticAssertDeclaration() {
        final Marker marker = enter_section_modified(builder);
        final boolean b = simpleParse("StaticAssertDeclaration",
            "staticAssertStatement|parseStaticAssertStatement");
        exit_section_modified(builder, marker, STATIC_ASSERT_DECLARATION, b);
        return b;
    }

    /**
     * Parses a StaticAssertStatement
     * <p>
     * $(GRAMMAR $(RULEDEF staticAssertStatement):
     * $(LITERAL 'static') $(RULE assertExpression) $(LITERAL ';')
     * ;)
     */
    boolean parseStaticAssertStatement() {
        final Marker marker = enter_section_modified(builder);
        final boolean b = simpleParse("StaticAssertStatement",
            KW_STATIC, "assertExpression|parseAssertExpression", OP_SCOLON);
        exit_section_modified(builder, marker, STATIC_ASSERT_STATEMENT, b);
        return b;
    }

    /**
     * Parses a StaticConstructor
     * <p>
     * $(GRAMMAR $(RULEDEF staticConstructor):
     * $(LITERAL 'static') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ";"))
     * ;)
     */
    boolean parseStaticConstructor() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_STATIC)) {
            cleanup(m, STATIC_CONSTRUCTOR);
            return false;
        }
        final boolean b = parseStaticCtorDtorCommon();
        exit_section_modified(builder, m, STATIC_CONSTRUCTOR, b);
        return b;
    }

    /**
     * Parses a StaticDestructor
     * <p>
     * $(GRAMMAR $(RULEDEF staticDestructor):
     * $(LITERAL 'static') $(LITERAL '~') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ";"))
     * ;)
     */
    boolean parseStaticDestructor() {
        final Marker m = enter_section_modified(builder);
//			Runnable cleanup =() ->  exit_section_modified(builder,m,DlangTypes.StaticDestructor,false);
//            node.location = current().index;
        if (!tokenCheck(KW_STATIC)) {
            cleanup(m, STATIC_DESTRUCTOR);
            return false;
        }
        if (!tokenCheck(OP_TILDA)) {
            cleanup(m, STATIC_DESTRUCTOR);
            return false;
        }
        final boolean b = parseStaticCtorDtorCommon();
        exit_section_modified(builder, m, STATIC_DESTRUCTOR, b);
        return b;
    }

    /**
     * Parses an StaticIfCondition
     * <p>
     * $(GRAMMAR $(RULEDEF staticIfCondition):
     * $(LITERAL 'static') $(LITERAL 'if') $(LITERAL '$(LPAREN)') $(RULE assignExpression) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseStaticIfCondition() {
        final Marker marker = enter_section_modified(builder);
        final boolean b = simpleParse("StaticIfCondition", KW_STATIC, KW_IF, OP_PAR_LEFT,
            "assignExpression|parseAssignExpression", OP_PAR_RIGHT);
        exit_section_modified(builder, marker, STATIC_IF_CONDITION, b);
        return b;
    }

    /**
     * Parses a StorageClass
     * <p>
     * $(GRAMMAR $(RULEDEF storageClass):
     * $(RULE alignAttribute)
     * | $(RULE linkageAttribute)
     * | $(RULE atAttribute)
     * | $(RULE typeConstructor)
     * | $(RULE deprecated)
     * | $(LITERAL 'abstract')
     * | $(LITERAL 'auto')
     * | $(LITERAL 'enum')
     * | $(LITERAL 'extern')
     * | $(LITERAL 'final')
     * | $(LITERAL '')
     * | $(LITERAL 'override')
     * | $(LITERAL '')
     * | $(LITERAL 'ref')
     * | $(LITERAL '__gshared')
     * | $(LITERAL 'scope')
     * | $(LITERAL 'static')
     * | $(LITERAL 'synchronized')
     * | $(LITERAL 'throw')
     * ;)
     */
    boolean parseStorageClass() {
        final Marker m = enter_section_modified(builder);
        final IElementType i = current();
        if (i.equals(OP_AT)) {
            if (!parseAtAttribute()) {
                cleanup(m, STORAGE_CLASS);
                return false;
            }
        } else if (i.equals(KW_DEPRECATED)) {
            if (!parseDeprecated()) {
                cleanup(m, STORAGE_CLASS);
                return false;
            }
        } else if (i.equals(KW_ALIGN)) {
            if (!parseAlignAttribute()) {
                cleanup(m, STORAGE_CLASS);
                return false;
            }
        } else if (i.equals(KW_EXTERN)) {
            if (peekIs(OP_PAR_LEFT)) {
                if (!parseLinkageAttribute()) {
                    cleanup(m, STORAGE_CLASS);
                    return false;
                }
                exit_section_modified(builder, m, STORAGE_CLASS, true);
                return true;
            } else
                advance();
        } else if (i.equals(KW_CONST) || i.equals(KW_IMMUTABLE) || i.equals(KW_INOUT) || i.equals(KW_SHARED) || i.equals(KW_ABSTRACT) || i.equals(KW_AUTO) || i.equals(KW_ENUM) || i.equals(KW_FINAL) || i.equals(KW_NOTHROW) || i.equals(KW_OVERRIDE) || i.equals(KW_PURE) || i.equals(KW_REF) || i.equals(KW___GSHARED) || i.equals(KW_SCOPE) || i.equals(KW_STATIC) || i.equals(KW_SYNCHRONIZED) || i.equals(KW_THROW)) {
            advance();
        } else {
            error("Storage class expected");
            exit_section_modified(builder, m, STORAGE_CLASS, true);
            return false;
        }
        exit_section_modified(builder, m, STORAGE_CLASS, true);
        return true;
    }

    /**
     * Parses a StructBody
     * <p>
     * $(GRAMMAR $(RULEDEF structBody):
     * $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
     * ;)
     */
    boolean parseStructBody() {
        final Marker m = enter_section_modified(builder);
        final IElementType start = expect(OP_BRACES_LEFT);
        while (!currentIs(OP_BRACES_RIGHT) && moreTokens()) {
            parseDeclaration(true, true);
        }
        final IElementType end = expect(OP_BRACES_RIGHT);
        exit_section_modified(builder, m, STRUCT_BODY, true);
        return true;
    }

    /**
     * Parses a StructDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF structDeclaration):
     * $(LITERAL 'struct') $(LITERAL Identifier)? ($(RULE templateParameters) $(RULE raint)? $(RULE structBody) | ($(RULE structBody) | $(LITERAL ';')))
     * ;)
     */
    boolean parseStructDeclaration() {
        final Marker m = enter_section_modified(builder);
        final IElementType t = expect(KW_STRUCT);
        if (currentIs(ID))
            advance();
        if (currentIs(OP_PAR_LEFT)) {
            if (!parseTemplateParameters()) {
                cleanup(m, STRUCT_DECLARATION);
                return false;
            }
            if (currentIs(KW_IF))
                if (!parseConstraint()) {
                    cleanup(m, STRUCT_DECLARATION);
                    return false;
                }
        }
        if (currentIs(OP_BRACES_LEFT)) {
            if (!parseStructBody()) {
                cleanup(m, STRUCT_DECLARATION);
                return false;
            }
        } else if (currentIs(OP_SCOLON))
            advance();
        else {
            error("Template Parameters, Struct Body, or Semicolon expected");
            exit_section_modified(builder, m, STRUCT_DECLARATION, true);
            return false;
        }
        exit_section_modified(builder, m, STRUCT_DECLARATION, true);
        return true;
    }

    /**
     * Parses an StructInitializer
     * <p>
     * $(GRAMMAR $(RULEDEF structInitializer):
     * $(LITERAL '{') $(RULE structMemberInitializers)? $(LITERAL '}')
     * ;)
     */
    boolean parseStructInitializer() {
        final Marker m = enter_section_modified(builder);
        final IElementType a = expect(OP_BRACES_LEFT);
        if (currentIs(OP_BRACES_RIGHT)) {
            advance();
        } else {
            if (!parseStructMemberInitializers()) {
                cleanup(m, STRUCT_INITIALIZER);
                return false;
            }
            final IElementType e = expect(OP_BRACES_RIGHT);
            if (e == null) {
                cleanup(m, STRUCT_INITIALIZER);
                return false;
            }
        }
        exit_section_modified(builder, m, STRUCT_INITIALIZER, true);
        return true;
    }

    /**
     * Parses a StructMemberInitializer
     * <p>
     * $(GRAMMAR $(RULEDEF structMemberInitializer):
     * ($(LITERAL Identifier) $(LITERAL ':'))? $(RULE nonVoidInitializer)
     * ;)
     */
    boolean parseStructMemberInitializer() {
        final Marker m = enter_section_modified(builder);
        if (startsWith(ID, OP_COLON)) {
            advance();
            advance();
        }
        if (!parseNonVoidInitializer()) {
            cleanup(m, STRUCT_MEMBER_INITIALIZER);
            return false;
        }
        exit_section_modified(builder, m, STRUCT_MEMBER_INITIALIZER, true);
        return true;
    }

    /**
     * Parses StructMemberInitializers
     * <p>
     * $(GRAMMAR $(RULEDEF structMemberInitializers):
     * $(RULE structMemberInitializer) ($(LITERAL ',') $(RULE structMemberInitializer)?)*
     * ;)
     */
    boolean parseStructMemberInitializers() {
        final Marker m = enter_section_modified(builder);
        do {
//                auto c = allocator.setCheckpoint();
            parseStructMemberInitializer();
//                    allocator.rollback(c);
            if (currentIs(OP_COMMA))
                advance();
            else
                break;
        } while (moreTokens() && !currentIs(OP_BRACES_RIGHT));
        exit_section_modified(builder, m, STRUCT_MEMBER_INITIALIZERS, true);
        return true;
    }

    /**
     * Parses a SwitchStatement
     * <p>
     * $(GRAMMAR $(RULEDEF switchStatement):
     * $(LITERAL 'switch') $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE statement)
     * ;)
     */
    boolean parseSwitchStatement() {
        final Marker m = enter_section_modified(builder);
        expect(KW_SWITCH);
        expect(OP_PAR_LEFT);
        if (!parseExpression()) {
            cleanup(m, SWITCH_STATEMENT);
            return false;
        }
        expect(OP_PAR_RIGHT);
        if (!parseStatement()) {
            cleanup(m, SWITCH_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, SWITCH_STATEMENT, true);
        return true;
    }

    /**
     * Parses a Symbol
     * <p>
     * $(GRAMMAR $(RULEDEF symbol):
     * $(LITERAL '.')? $(RULE identifierOrTemplateChain)
     * ;)
     */
    boolean parseSymbol() {
        final Marker m = enter_section_modified(builder);
        if (currentIs(OP_DOT)) {
            advance();
        }
        if (!parseIdentifierOrTemplateChain()) {
            cleanup(m, SYMBOL);
            return false;
        }
        exit_section_modified(builder, m, SYMBOL, true);
        return true;
    }

    /**
     * Parses a SynchronizedStatement
     * <p>
     * $(GRAMMAR $(RULEDEF synchronizedStatement):
     * $(LITERAL 'synchronized') ($(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)'))? $(RULE statementNoCaseNoDefault)
     * ;)
     */
    boolean parseSynchronizedStatement() {
        final Marker m = enter_section_modified(builder);
        expect(KW_SYNCHRONIZED);
        if (currentIs(OP_PAR_LEFT)) {
            expect(OP_PAR_LEFT);
            if (!parseExpression()) {
                cleanup(m, SYNCHRONIZED_STATEMENT);
                return false;
            }
            expect(OP_PAR_RIGHT);
        }
        if (!parseStatementNoCaseNoDefault()) {
            cleanup(m, SYNCHRONIZED_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, SYNCHRONIZED_STATEMENT, true);
        return true;
    }

    /**
     * Parses a TemplateAliasParameter
     * <p>
     * $(GRAMMAR $(RULEDEF templateAliasParameter):
     * $(LITERAL 'alias') $(RULE type)? $(LITERAL Identifier) ($(LITERAL ':') ($(RULE type) | $(RULE assignExpression)))? ($(LITERAL '=') ($(RULE type) | $(RULE assignExpression)))?
     * ;)
     */
    boolean parseTemplateAliasParameter() {
        final Marker m = enter_section_modified(builder);
        expect(KW_ALIAS);
        if (currentIs(ID) && !peekIs(OP_DOT)) {
            if (peekIsOneOf(OP_COMMA, OP_PAR_RIGHT, OP_EQ, OP_COLON))
                advance();
            else {
                if (!parseType().first) {
                    cleanup(m, TEMPLATE_ALIAS_PARAMETER);
                    return false;
                }
                final IElementType ident = expect(ID);
                if (ident == null) {
                    cleanup(m, TEMPLATE_ALIAS_PARAMETER);
                    return false;
                }
            }
        } else {
            if (!parseType().first) {
                cleanup(m, TEMPLATE_ALIAS_PARAMETER);
                return false;
            }
            final IElementType ident = expect(ID);
            if (ident == null) {
                cleanup(m, TEMPLATE_ALIAS_PARAMETER);
                return false;
            }
        }

        if (currentIs(OP_COLON)) {
            advance();
            if (isType()) {
                if (!parseType().first) {
                    cleanup(m, TEMPLATE_ALIAS_PARAMETER);
                    return false;
                }
            } else if (!parseAssignExpression()) {
                cleanup(m, TEMPLATE_ALIAS_PARAMETER);
                return false;
            }
        }
        if (currentIs(OP_EQ)) {
            advance();
            if (isType()) {
                if (!parseType().first) {
                    cleanup(m, TEMPLATE_ALIAS_PARAMETER);
                    return false;
                }
            } else if (!parseAssignExpression()) {
                cleanup(m, TEMPLATE_ALIAS_PARAMETER);
                return false;
            }
        }
        exit_section_modified(builder, m, TEMPLATE_ALIAS_PARAMETER, true);
        return true;
    }

    /**
     * Parses a TemplateArgument
     * <p>
     * $(GRAMMAR $(RULEDEF templateArgument):
     *   $(RULE type)
     * | $(RULE assignExpression)
     * ;)
     */
    boolean parseTemplateArgument() {
        final Marker m = enter_section_modified(builder);
        int startIndex = index;
        boolean p = cachedTypedChecks.containsKey(index);
        if (p) {
            if (cachedTypedChecks.get(index)) {
                parseType();
            }
            else {
                if (!parseAssignExpression()) {
                    cleanup(m, TEMPLATE_ARGUMENT);
                    return false;
                }
            }
        }
        else {
            final Bookmark b = setBookmark();
            final boolean t = parseType().first;
            if (t && currentIsOneOf(OP_COMMA, OP_PAR_RIGHT)) {
                cachedTypedChecks.put(startIndex, true);
                abandonBookmark(b);
            } else {
                cachedTypedChecks.put(startIndex, false);
                goToBookmark(b);
                if (!parseAssignExpression()) {
                    cleanup(m, TEMPLATE_ARGUMENT);
                    return false;
                }
            }
        }
        exit_section_modified(builder, m, TEMPLATE_ARGUMENT, true);
        return true;
    }

    /**
     * Parses a TemplateArgumentList
     * <p>
     * $(GRAMMAR $(RULEDEF templateArgumentList):
     * $(RULE templateArgument) ($(LITERAL ',') $(RULE templateArgument)?)*
     * ;)
     */
    boolean parseTemplateArgumentList() {
        final Marker marker = enter_section_modified(builder);
        final boolean b = parseCommaSeparatedRule("TemplateArgumentList", "TemplateArgument", true);
        exit_section_modified(builder, marker, TEMPLATE_ARGUMENT_LIST, b);
        return b;
    }

    /**
     * Parses TemplateArguments
     * <p>
     * $(GRAMMAR $(RULEDEF templateArguments):
     * $(LITERAL '!') ($(LITERAL '$(LPAREN)') $(RULE templateArgumentList)? $(LITERAL '$(RPAREN)')) | $(RULE templateSingleArgument)
     * ;)
     */
    boolean parseTemplateArguments() {
        final Marker m = enter_section_modified(builder);
        expect(OP_NOT);
        if (currentIs(OP_PAR_LEFT)) {
            advance();
            if (!currentIs(OP_PAR_RIGHT))
                if (!parseTemplateArgumentList()) {
                    cleanup(m, TEMPLATE_ARGUMENTS);
                    return false;
                }
            if (!tokenCheck(OP_PAR_RIGHT)) {
                cleanup(m, TEMPLATE_ARGUMENTS);
                return false;
            }
        } else if (!parseTemplateSingleArgument()) {
            cleanup(m, TEMPLATE_ARGUMENTS);
            return false;
        }
        exit_section_modified(builder, m, TEMPLATE_ARGUMENTS, true);
        return true;
    }

    /**
     * Parses a TemplateDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF templateDeclaration):
     * $(LITERAL 'template') $(LITERAL Identifier) $(RULE templateParameters) $(RULE raint)? $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
     * ;)
     */
    boolean parseTemplateDeclaration() {
        final Marker m = enter_section_modified(builder);
        expect(KW_TEMPLATE);
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, TEMPLATE_DECLARATION);
            return false;
        }
        if (!parseTemplateParameters()) {
            cleanup(m, TEMPLATE_DECLARATION);
            return false;
        }
        if (currentIs(KW_IF))
            if (!parseConstraint()) {
                cleanup(m, TEMPLATE_DECLARATION);
                return false;
            }
        final IElementType start = expect(OP_BRACES_LEFT);
        if (start == null) {
            cleanup(m, TEMPLATE_DECLARATION);
            return false;
        }
        while (moreTokens() && !currentIs(OP_BRACES_RIGHT)) {
//                c = allocator.setCheckpoint();
            parseDeclaration(true, true, true);
//                    allocator.rollback(c);
        }
        final IElementType end = expect(OP_BRACES_RIGHT);
        exit_section_modified(builder, m, TEMPLATE_DECLARATION, true);
        return true;
    }

    /**
     * Parses a TemplateInstance
     * <p>
     * $(GRAMMAR $(RULEDEF templateInstance):
     * $(LITERAL Identifier) $(RULE templateArguments)
     * ;)
     */
    boolean parseTemplateInstance() {
        final Marker m = enter_section_modified(builder);
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, TEMPLATE_INSTANCE);
            return false;
        }
        if (!parseTemplateArguments()) {
            cleanup(m, TEMPLATE_INSTANCE);
            return false;
        }
        exit_section_modified(builder, m, TEMPLATE_INSTANCE, true);
        return true;
    }

    /**
     * Parses a TemplateMixinExpression
     * <p>
     * $(GRAMMAR $(RULEDEF templateMixinExpression):
     * $(LITERAL 'mixin') $(RULE mixinTemplateName) $(RULE templateArguments)? $(LITERAL Identifier)?
     * ;)
     */
    boolean parseTemplateMixinExpression() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_MIXIN)) {
            cleanup(m, TEMPLATE_MIXIN_EXPRESSION);
            return false;
        }
        if (!parseMixinTemplateName()) {
            cleanup(m, TEMPLATE_MIXIN_EXPRESSION);
            return false;
        }
        if (currentIs(OP_NOT))
            if (!parseTemplateArguments()) {
                cleanup(m, TEMPLATE_MIXIN_EXPRESSION);
                return false;
            }
        if (currentIs(ID))
            advance();
        exit_section_modified(builder, m, TEMPLATE_MIXIN_EXPRESSION, true);
        return true;
    }

    /**
     * Parses a TemplateParameter
     * <p>
     * $(GRAMMAR $(RULEDEF templateParameter):
     *   $(RULE templateTypeParameter)
     * | $(RULE templateValueParameter)
     * | $(RULE templateAliasParameter)
     * | $(RULE templateTupleParameter)
     * | $(RULE templateThisParameter)
     * ;)
     */
    boolean parseTemplateParameter() {
        final Marker m = enter_section_modified(builder);
        final IElementType i = current();
        if (i.equals(KW_ALIAS)) {
            if (!parseTemplateAliasParameter()) {
                cleanup(m, TEMPLATE_PARAMETER);
                return false;
            }
        } else if (i.equals(ID)) {
            if (peekIs(OP_TRIPLEDOT)) {
                if (!parseTemplateTupleParameter()) {
                    cleanup(m, TEMPLATE_PARAMETER);
                    return false;
                }
            } else if (peekIsOneOf(OP_COLON, OP_EQ, OP_COMMA, OP_PAR_RIGHT)) {
                if (!parseTemplateTypeParameter()) {
                    cleanup(m, TEMPLATE_PARAMETER);
                    return false;
                }
            } else if (!parseTemplateValueParameter()) {
                cleanup(m, TEMPLATE_PARAMETER);
                return false;
            }
        } else if (i.equals(KW_THIS)) {
            if (!parseTemplateThisParameter()) {
                cleanup(m, TEMPLATE_PARAMETER);
                return false;
            }
        } else {
            if (!parseTemplateValueParameter()) {
                cleanup(m, TEMPLATE_PARAMETER);
                return false;
            }
        }
        exit_section_modified(builder, m, TEMPLATE_PARAMETER, true);
        return true;
    }

    /**
     * Parses a TemplateParameterList
     * <p>
     * $(GRAMMAR $(RULEDEF templateParameterList):
     * $(RULE templateParameter) ($(LITERAL ',') $(RULE templateParameter)?)* $(LITERAL ',')?
     * ;)
     */
    boolean parseTemplateParameterList() {
        final Marker marker = enter_section_modified(builder);
        final boolean b = parseCommaSeparatedRule("TemplateParameterList", "TemplateParameter", true);
        exit_section_modified(builder, marker, TEMPLATE_PARAMETER_LIST, b);
        return b;
    }

    /**
     * Parses TemplateParameters
     * <p>
     * $(GRAMMAR $(RULEDEF templateParameters):
     * $(LITERAL '$(LPAREN)') $(RULE templateParameterList)? $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseTemplateParameters() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, TEMPLATE_PARAMETERS);
            return false;
        }
        if (!currentIs(OP_PAR_RIGHT))
            if (!parseTemplateParameterList()) {
                cleanup(m, TEMPLATE_PARAMETERS);
                return false;
            }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, TEMPLATE_PARAMETERS);
            return false;
        }
        exit_section_modified(builder, m, TEMPLATE_PARAMETERS, true);
        return true;
    }

    /**
     * Parses a TemplateSingleArgument
     * <p>
     * $(GRAMMAR $(RULEDEF templateSingleArgument):
     *   $(RULE builtinType)
     * | $(LITERAL Identifier)
     * | $(LITERAL CharacterLiteral)
     * | $(LITERAL StringLiteral)
     * | $(LITERAL IntegerLiteral)
     * | $(LITERAL FloatLiteral)
     * | $(LITERAL '_true')
     * | $(LITERAL '_false')
     * | $(LITERAL '_null')
     * | $(LITERAL 'this')
     * | $(LITERAL '___DATE__')
     * | $(LITERAL '___FILE__')
     * | $(LITERAL '___FILE_FULL_PATH__')
     * | $(LITERAL '___FUNCTION__')
     * | $(LITERAL '___LINE__')
     * | $(LITERAL '___MODULE__')
     * | $(LITERAL '___PRETTY_FUNCTION__')
     * | $(LITERAL '___TIME__')
     * | $(LITERAL '___TIMESTAMP__')
     * | $(LITERAL '___VENDOR__')
     * | $(LITERAL '___VERSION__')
     * ;)
     */
    boolean parseTemplateSingleArgument() {
        final Marker m = enter_section_modified(builder);
        if (!moreTokens()) {
            error("template argument expected instead of EOF");
            exit_section_modified(builder, m, TEMPLATE_SINGLE_ARGUMENT, true);
            return false;
        }
        final IElementType i = current();
        if (i.equals(KW_THIS) || i.equals(ID) || isLiteral(i)) {
            advance();
        } else if (isBasicType(i)) {
            parseBuiltinType();
        } else {
            error("Invalid template argument. (Try enclosing in parenthesis?)");
            exit_section_modified(builder, m, TEMPLATE_SINGLE_ARGUMENT, true);
            return false;
        }
        exit_section_modified(builder, m, TEMPLATE_SINGLE_ARGUMENT, true);
        return true;
    }

    /**
     * Parses a TemplateThisParameter
     * <p>
     * $(GRAMMAR $(RULEDEF templateThisParameter):
     * $(LITERAL 'this') $(RULE templateTypeParameter)
     * ;)
     */
    boolean parseTemplateThisParameter() {
        final Marker m = enter_section_modified(builder);
        expect(KW_THIS);
        if (!parseTemplateTypeParameter()) {
            cleanup(m, TEMPLATE_THIS_PARAMETER);
            return false;
        }
        exit_section_modified(builder, m, TEMPLATE_THIS_PARAMETER, true);
        return true;
    }

    /**
     * Parses an TemplateTupleParameter
     * <p>
     * $(GRAMMAR $(RULEDEF templateTupleParameter):
     * $(LITERAL Identifier) $(LITERAL '...')
     * ;)
     */
    boolean parseTemplateTupleParameter() {
        final Marker m = enter_section_modified(builder);
        final IElementType i = expect(ID);
        if (i == null) {
            cleanup(m, TEMPLATE_TUPLE_PARAMETER);
            return false;
        }
        if (!tokenCheck(OP_TRIPLEDOT)) {
            cleanup(m, TEMPLATE_TUPLE_PARAMETER);
            return false;
        }
        exit_section_modified(builder, m, TEMPLATE_TUPLE_PARAMETER, true);
        return true;
    }

    /**
     * Parses a TemplateTypeParameter
     * <p>
     * $(GRAMMAR $(RULEDEF templateTypeParameter):
     * $(LITERAL Identifier) ($(LITERAL ':') $(RULE type))? ($(LITERAL '=') $(RULE type))?
     * ;)
     */
    boolean parseTemplateTypeParameter() {
        final Marker m = enter_section_modified(builder);
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, TEMPLATE_TYPE_PARAMETER);
            return false;
        }
        if (currentIs(OP_COLON)) {
            advance();
            if (!parseType().first) {
                cleanup(m, TEMPLATE_TYPE_PARAMETER);
                return false;
            }
        }
        if (currentIs(OP_EQ)) {
            advance();
            if (!parseType().first) {
                cleanup(m, TEMPLATE_TYPE_PARAMETER);
                return false;
            }
        }
        exit_section_modified(builder, m, TEMPLATE_TYPE_PARAMETER, true);
        return true;
    }

    /**
     * Parses a TemplateValueParameter
     * <p>
     * $(GRAMMAR $(RULEDEF templateValueParameter):
     * $(RULE type) $(LITERAL Identifier) ($(LITERAL ':') $(RULE assignExpression))? $(RULE templateValueParameterDefault)?
     * ;)
     */
    boolean parseTemplateValueParameter() {
        final Marker m = enter_section_modified(builder);
        if (!parseType().first) {
            cleanup(m, TEMPLATE_VALUE_PARAMETER);
            return false;
        }
        if (!tokenCheck(ID)) {
            cleanup(m, TEMPLATE_VALUE_PARAMETER);
            return false;
        }
        if (currentIs(OP_COLON)) {
            advance();
            if (!parseAssignExpression()) {
                cleanup(m, TEMPLATE_VALUE_PARAMETER);
                return false;
            }
        }
        if (currentIs(OP_EQ))
            if (!parseTemplateValueParameterDefault()) {
                cleanup(m, TEMPLATE_VALUE_PARAMETER);
                return false;
            }
        exit_section_modified(builder, m, TEMPLATE_VALUE_PARAMETER, true);
        return true;
    }

    /**
     * Parses a TemplateValueParameterDefault
     * <p>
     * $(GRAMMAR $(RULEDEF templateValueParameterDefault):
     *   $(LITERAL '=') $(LITERAL '___DATE__')
     * | $(LITERAL '=') $(LITERAL '___FILE__')
     * | $(LITERAL '=') $(LITERAL '___FILE_FULL_PATH__')
     * | $(LITERAL '=') $(LITERAL '___FUNCTION__')
     * | $(LITERAL '=') $(LITERAL '___LINE__')
     * | $(LITERAL '=') $(LITERAL '___MODULE__')
     * | $(LITERAL '=') $(LITERAL '___PRETTY_FUNCTION__')
     * | $(LITERAL '=') $(LITERAL '___TIME__')
     * | $(LITERAL '=') $(LITERAL '___TIMESTAMP__')
     * | $(LITERAL '=') $(LITERAL '___VENDOR__')
     * | $(LITERAL '=') $(LITERAL '___VERSION__')
     * | $(LITERAL '=') $(RULE assignExpression))
     * ;)
     */
    boolean parseTemplateValueParameterDefault() {
        final Marker m = enter_section_modified(builder);
        expect(OP_EQ);
        final IElementType i = current();
        if (i.equals(KW___FILE__) || i.equals(KW___FILE_FULL_PATH__) || i.equals(KW___MODULE__) || i.equals(KW___LINE__) || i.equals(KW___FUNCTION__) || i.equals(KW___PRETTY_FUNCTION__)) {
            advance();
        } else {
            if (!parseAssignExpression()) {
                cleanup(m, TEMPLATE_VALUE_PARAMETER_DEFAULT);
                return false;
            }

        }
        exit_section_modified(builder, m, TEMPLATE_VALUE_PARAMETER_DEFAULT, true);
        return true;
    }

    /**
     * Parses a TernaryExpression (called ConditionalExpression in specs)
     * <p>
     * $(GRAMMAR $(RULEDEF ternaryExpression):
     * $(RULE orOrExpression) ($(LITERAL '?') $(RULE expression) $(LITERAL ':') $(RULE ternaryExpression))?
     * ;)
     */
    boolean parseTernaryExpression() {
        final boolean orOrExpression = parseOrOrExpression();
        if (!orOrExpression) {
            return false;//no cleanup needed
        }
        if (currentIs(OP_QUEST)) {
            final Marker m = enter_section_modified(builder);
            advance();
            if (!parseExpression()) {
                cleanup(m, TERNARY_EXPRESSION);
                return false;
            }
            final IElementType colon = expect(OP_COLON);
            if (colon == null) {
                cleanup(m, TERNARY_EXPRESSION);
                return false;
            }
            if (!parseTernaryExpression()) {
                cleanup(m, TERNARY_EXPRESSION);
                return false;
            }
            exit_section_modified(builder, m, TERNARY_EXPRESSION, true);
            return true;
        }
        return orOrExpression;
    }

    /**
     * Parses a ThrowExpression
     * <p>
     * $(GRAMMAR $(RULEDEF throwExpression):
     * $(LITERAL 'throw') $(RULE assignExpression)
     * ;)
     */
    boolean parseThrowExpression() {
        final Marker m = enter_section_modified(builder);
        expect(KW_THROW);
        if (!parseAssignExpression()) {
            cleanup(m, THROW_EXPRESSION);
            return false;
        }
        exit_section_modified(builder, m, THROW_EXPRESSION, true);
        return true;
    }

    /**
     * Parses an TraitsExpression
     * <p>
     * $(GRAMMAR $(RULEDEF traitsExpression):
     * $(LITERAL '___traits') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL ',') $(RULE TemplateArgumentList) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseTraitsExpression() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW___TRAITS)) {
            cleanup(m, TRAITS_EXPRESSION);
            return false;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, TRAITS_EXPRESSION);
            return false;
        }
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, TRAITS_EXPRESSION);
            return false;
        }
        if (currentIs(OP_COMMA)) {
            advance();
            if (!(parseTemplateArgumentList())) {
                cleanup(m, TRAITS_EXPRESSION);
                return false;
            }
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, TRAITS_EXPRESSION);
            return false;
        }
        exit_section_modified(builder, m, TRAITS_EXPRESSION, true);
        return true;
    }

    /**
     * Parses a TryStatement
     * <p>
     * $(GRAMMAR $(RULEDEF tryStatement):
     * $(LITERAL 'try') $(RULE declarationOrStatement) ($(RULE catches) | $(RULE catches) $(RULE finally) | $(RULE finally))
     * ;)
     */
    boolean parseTryStatement() {
        final Marker m = enter_section_modified(builder);
        expect(KW_TRY);
        if (!parseDeclarationOrStatement()) {
            cleanup(m, TRY_STATEMENT);
            return false;
        }
        if (currentIs(KW_CATCH))
            if (!parseCatches()) {
                cleanup(m, TRY_STATEMENT);
                return false;
            }
        if (currentIs(KW_FINALLY))
            if (!parseFinally()) {
                cleanup(m, TRY_STATEMENT);
                return false;
            }
        exit_section_modified(builder, m, TRY_STATEMENT, true);
        return true;
    }

    /**
     * Parses a Type
     * <p>
     * $(GRAMMAR $(RULEDEF type):
     * $(RULE typeConstructors)? $(RULE type2) $(RULE typeSuffix)*
     * ;)
     */
    Pair<Boolean, Marker> parseType() {
        final Marker m = enter_section_modified(builder);
        if (!moreTokens()) {
            error("type expected");
            exit_section_modified(builder, m, TYPE, true);
            return new Pair<>(false, m);
        }
        final IElementType i = current();
        if (isTypeCtor(i)) {
            if (!peekIs(OP_PAR_LEFT))
                if (parseTypeConstructors() == null) {
                    cleanup(m, TYPE);
                    return new Pair<>(false, m);
                }
        }
        if (!parseNodeQ("Type2")) {
            cleanup(m, TYPE);
            return new Pair<>(false, m);
        }
        while (moreTokens()) {
            final IElementType i1 = current();

            if (i1.equals(OP_BRACKET_LEFT)) {
                // Allow this to fail because of the madness that is the
                // newExpression rule. Something starting with '[' may be arguments
                // to the newExpression instead of part of the type
                final Bookmark newBookmark = setBookmark();
                if (parseTypeSuffix())
                    abandonBookmark(newBookmark);
                else {
//                        allocator.rollback(c);
                    goToBookmark(newBookmark);
                    break;
                }
            } else if (i1.equals(OP_ASTERISK) || i1.equals(KW_DELEGATE) || i1.equals(KW_FUNCTION)) {
                if (!parseTypeSuffix()) {
                    cleanup(m, TYPE);
                    return new Pair<>(false, m);
                }
            } else {
                break;
            }
        }
        exit_section_modified(builder, m, TYPE, true);
        return new Pair<>(true, m);
    }

    /**
     * Parses a Type2
     * <p>
     * $(GRAMMAR $(RULEDEF type2):
     *   $(RULE builtinType)
     * | $(RULE typeIdentifierPart)
     * | $(LITERAL 'super') $(LITERAL '.') $(RULE typeIdentifierPart)
     * | $(LITERAL 'this') $(LITERAL '.') $(RULE typeIdentifierPart)
     * | $(RULE typeofExpression) ($(LITERAL '.') $(RULE typeIdentifierPart))?
     * | $(RULE typeConstructor) $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL '$(RPAREN)')
     * | $(RULE traitsExpression)
     * | $(RULE vector)
     * | $(RULE mixinExpression)
     * ;)
     */
    boolean parseType2() {
        final Marker m = enter_section_modified(builder);
        if (!moreTokens()) {
            error("type2 expected instead of EOF");
            exit_section_modified(builder, m, TYPE_2, true);
            return false;
        }
        final IElementType i = current();
        if (i.equals(ID) || i.equals(OP_DOT)) {
            if (!parseTypeIdentifierPart()) {
                cleanup(m, TYPE_2);
                return false;
            }
        } else if (isBasicType(i)) {
            parseBuiltinType();
        } else if (i.equals(KW_SUPER) || i.equals(KW_THIS)) {
            // note: super can be removed but `this` can be an alias to an instance.
            advance();
            if (!tokenCheck(OP_DOT)) {
               cleanup(m, TYPE_2);
               return false;
            }
            advance();
            if (!parseTypeIdentifierPart()) {
                cleanup(m, TYPE_2);
                return false;
            }
        } else if (i.equals(KW___TRAITS)) {
            if (!parseTraitsExpression()) {
                cleanup(m, TYPE_2);
                return false;
            }
        } else if (i.equals(KW_TYPEOF)) {
            if (!parseTypeofExpression()) {
                cleanup(m, TYPE_2);
                return false;
            }
            if (currentIs(OP_DOT)) {
                advance();
                if (!parseTypeIdentifierPart()) {
                    cleanup(m, TYPE_2);
                    return false;
                }
            }
        } else if (i.equals(KW_MIXIN)) {
            if (!parseMixinExpression()) {
                cleanup(m, TYPE_2);
                return false;
            }
        } else if (i.equals(KW_CONST) || i.equals(KW_IMMUTABLE) || i.equals(KW_INOUT) || i.equals(KW_SHARED)) {
            advance();
            if (!tokenCheck(OP_PAR_LEFT)) {
                cleanup(m, TYPE_2);
                return false;
            }
            if (!(parseType().first)) {
                cleanup(m, TYPE_2);
                return false;
            }
            if (!tokenCheck(OP_PAR_RIGHT)) {
                cleanup(m, TYPE_2);
                return false;
            }
        } else if (i.equals(KW___VECTOR)) {
            if (!(parseVector())) {
                cleanup(m, TYPE_2);
                return false;
            }
        } else {
            error("Basic type, type constructor, symbol, `typeof`, `__traits`, `__vector` or `mixin` expected");
            exit_section_modified(builder, m, TYPE_2, true);
            return false;
        }
        exit_section_modified(builder, m, TYPE_2, true);
        return true;
    }

    IElementType parseTypeConstructor() {
        return parseTypeConstructor(true);
    }

    /**
     * Parses a TypeConstructor
     * <p>
     * $(GRAMMAR $(RULEDEF typeConstructor):
     *   $(LITERAL 'const')
     * | $(LITERAL 'immutable')
     * | $(LITERAL 'inout')
     * | $(LITERAL 'shared')
     * ;)
     */
    IElementType parseTypeConstructor(final boolean validate) {
        final IElementType i = current();
        if (i.equals(KW_CONST) || i.equals(KW_IMMUTABLE) || i.equals(KW_INOUT) || i.equals(KW_SHARED)) {
            if (!peekIs(OP_PAR_LEFT))
                return advance();
            if (validate) {
                error("`const`, `immutable`, `inout`, or `shared` expected");
            }
            return null;
        } else {
            if (validate) {
                error("`const`, `immutable`, `inout`, or `shared` expected");
            }
            return null;
        }
    }

    /**
     * Parses TypeConstructors
     * <p>
     * $(GRAMMAR $(RULEDEF typeConstructors):
     * $(RULE typeConstructor)+
     * ;)
     */
    IElementType[] parseTypeConstructors() {
        final List<IElementType> r = new LinkedList<>();
        while (moreTokens()) {
            final IElementType type = parseTypeConstructor(false);
            if (type == null)
                break;
            else
                r.add(type);
        }
        if (r.isEmpty())
            return null;
        final IElementType[] res = new IElementType[r.size()];
        r.toArray(res);
        return res;
    }

    /**
     * Parses a TypeSpecialization
     * <p>
     * $(GRAMMAR $(RULEDEF typeSpecialization):
     *   $(RULE type)
     * | $(LITERAL 'struct')
     * | $(LITERAL 'union')
     * | $(LITERAL 'class')
     * | $(LITERAL 'interface')
     * | $(LITERAL 'enum')
     * | $(LITERAL '__vector')
     * | $(LITERAL 'function')
     * | $(LITERAL 'delegate')
     * | $(LITERAL 'super')
     * | $(LITERAL 'const')
     * | $(LITERAL 'immutable')
     * | $(LITERAL 'inout')
     * | $(LITERAL 'shared')
     * | $(LITERAL 'return')
     * | $(LITERAL 'typedef')
     * | $(LITERAL '__parameters')
     * | $(LITERAL 'module')
     * | $(LITERAL 'package')
     * ;)
     */
    boolean parseTypeSpecialization() {
        final Marker m = enter_section_modified(builder);
        final IElementType i = current();
        if (i.equals(KW_STRUCT) || i.equals(KW_UNION) || i.equals(KW_CLASS) || i.equals(KW_INTERFACE) || i.equals(KW___VECTOR) || i.equals(KW_ENUM) || i.equals(KW_FUNCTION) || i.equals(KW_DELEGATE) || i.equals(KW_SUPER) || i.equals(KW_CONST) || i.equals(KW_IMMUTABLE) || i.equals(KW_INOUT) || i.equals(KW_SHARED) || i.equals(KW_RETURN) || i.equals(KW___PARAMETERS) || i.equals(KW_MODULE) || i.equals(KW_PACKAGE)) {
            if (peekIsOneOf(OP_PAR_RIGHT, OP_COMMA)) {
                advance();
                exit_section_modified(builder, m, TYPE_SPECIALIZATION, true);
                return true;
            }
            if (!parseType().first) {
                cleanup(m, TYPE_SPECIALIZATION);
                return false;
            }
        } else if (!parseType().first) {
            cleanup(m, TYPE_SPECIALIZATION);
            return false;
        }
        exit_section_modified(builder, m, TYPE_SPECIALIZATION, true);
        return true;
    }

    /**
     * Parses a TypeSuffix
     * <p>
     * $(GRAMMAR $(RULEDEF typeSuffix):
     *   $(LITERAL '*')
     * | $(LITERAL '[') $(RULE type)? $(LITERAL ']')
     * | $(LITERAL '[') $(RULE assignExpression) $(LITERAL ']')
     * | $(LITERAL '[') $(RULE assignExpression) $(LITERAL '..')  $(RULE assignExpression) $(LITERAL ']')
     * | ($(LITERAL 'delegate') | $(LITERAL 'function')) $(RULE parameters) $(RULE memberFunctionAttribute)*
     * ;)
     */
    boolean parseTypeSuffix() {
        final Marker m = enter_section_modified(builder);
        final IElementType i = current();
        if (i.equals(OP_ASTERISK)) {
            advance();
            exit_section_modified(builder, m, TYPE_SUFFIX, true);
            return true;
        } else if (i.equals(OP_BRACKET_LEFT)) {
            advance();
            if (currentIs(OP_BRACKET_RIGHT)) {
                advance();
                exit_section_modified(builder, m, TYPE_SUFFIX, true);
                return true;
            }
            final Bookmark bookmark = setBookmark();
            final boolean type = parseType().first;
            if (type && currentIs(OP_BRACKET_RIGHT)) {
                abandonBookmark(bookmark);
            } else {
                goToBookmark(bookmark);
                if (!parseAssignExpression()) {
                    cleanup(m, TYPE_SUFFIX);
                    return false;
                }
                if (currentIs(OP_DDOT)) {
                    advance();
                    if (!parseAssignExpression()) {
                        cleanup(m, TYPE_SUFFIX);
                        return false;
                    }
                }
            }
            if (!tokenCheck(OP_BRACKET_RIGHT)) {
                cleanup(m, TYPE_SUFFIX);
                return false;
            }
            exit_section_modified(builder, m, TYPE_SUFFIX, true);
            return true;
        } else if (i.equals(KW_DELEGATE) || i.equals(KW_FUNCTION)) {
            advance();
            if (!parseParameters()) {
                cleanup(m, TYPE_SUFFIX);
                return false;
            }
            while (currentIsMemberFunctionAttribute())
                if (!parseMemberFunctionAttribute()) {
                    cleanup(m, TYPE_SUFFIX);
                    return false;
                }
            exit_section_modified(builder, m, TYPE_SUFFIX, true);
            return true;
        } else {
            error("`*`, `[`, `delegate`, or `function` expected.");
            exit_section_modified(builder, m, TYPE_SUFFIX, true);
            return false;
        }
    }

    /**
     * Parses a TypeidExpression
     * <p>
     * $(GRAMMAR $(RULEDEF typeidExpression):
     * $(LITERAL 'typeid') $(LITERAL '$(LPAREN)') ($(RULE type) | $(RULE expression)) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseTypeidExpression() {
        final Marker m = enter_section_modified(builder);
        expect(KW_TYPEID);
        expect(OP_PAR_LEFT);
        final Bookmark b = setBookmark();
        final boolean t = parseType().first;
        if (!t || !currentIs(OP_PAR_RIGHT)) {
            goToBookmark(b);
            if (!parseExpression()) {
                cleanup(m, TYPEID_EXPRESSION);
                return false;
            }
        } else {
            abandonBookmark(b);
        }
        expect(OP_PAR_RIGHT);
        exit_section_modified(builder, m, TYPEID_EXPRESSION, true);
        return true;
    }

    /**
     * Parses a TypeofExpression
     * <p>
     * $(GRAMMAR $(RULEDEF typeofExpression):
     * $(LITERAL 'typeof') $(LITERAL '$(LPAREN)') ($(RULE expression) | $(LITERAL 'return')) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseTypeofExpression() {
        final Marker m = enter_section_modified(builder);
        expect(KW_TYPEOF);
        expect(OP_PAR_LEFT);
        if (currentIs(KW_RETURN))
            advance();
        else if (!parseExpression()) {
            cleanup(m, TYPEOF_EXPRESSION);
            return false;
        }
        expect(OP_PAR_RIGHT);
        exit_section_modified(builder, m, TYPEOF_EXPRESSION, true);
        return true;
    }

    private boolean unaryExpressionSwitchDefault(final Marker m) {
        if (!parsePrimaryExpression()) {
            cleanup(m, UNARY_EXPRESSION);
            return false;
        }
        return true;
    }

    /**
     * Parses a UnaryExpression
     * <p>
     * $(GRAMMAR $(RULEDEF unaryExpression):
     *   $(RULE primaryExpression)
     * | $(LITERAL '&') $(RULE unaryExpression)
     * | $(LITERAL '!') $(RULE unaryExpression)
     * | $(LITERAL '*') $(RULE unaryExpression)
     * | $(LITERAL '+') $(RULE unaryExpression)
     * | $(LITERAL '-') $(RULE unaryExpression)
     * | $(LITERAL '~') $(RULE unaryExpression)
     * | $(LITERAL '++') $(RULE unaryExpression)
     * | $(LITERAL '--') $(RULE unaryExpression)
     * | $(RULE newExpression)
     * | $(RULE deleteExpression)
     * | $(RULE castExpression)
     * | $(RULE assertExpression)
     * | $(RULE throwExpression)
     * | $(RULE functionCallExpression)
     * | $(RULE indexExpression)
     * | $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL '$(RPAREN)') $(LITERAL '.') $(RULE identifierOrTemplateInstance)
     * | $(RULE unaryExpression) $(LITERAL '.') $(RULE newExpression)
     * | $(RULE unaryExpression) $(LITERAL '.') $(RULE identifierOrTemplateInstance)
     * | $(RULE unaryExpression) $(LITERAL '--')
     * | $(RULE unaryExpression) $(LITERAL '++')
     * ;)
     */
    boolean parseUnaryExpression() {
        if (!moreTokens())
            return false;
        Marker m = enter_section_modified(builder);
        boolean fallThrough = false;
        final IElementType i = current();
        if (i.equals(KW_CONST) || i.equals(KW_IMMUTABLE) || i.equals(KW_INOUT) || i.equals(KW_SHARED)) {
            final Bookmark b = setBookmark();
            fallThrough = true;
            if (peekIs(OP_PAR_LEFT)) {
                advance();
                final IElementType past = peekPastParens();
                if (past != null && past.equals(OP_DOT)) {
                    goToBookmark(b);
                    if (!unaryExpressionSwitchDefault(m)) {
                        return false;//no cleanup needed
                    }
                    fallThrough = false;
                }
            }
            if (fallThrough) {
                goToBookmark(b);
                if (!parseFunctionCallExpression()) {
                    cleanup(m, UNARY_EXPRESSION);
                    return false;
                }
            }
        } else if (i.equals(KW_SCOPE) || i.equals(KW_PURE) || i.equals(KW_NOTHROW)) {
            if (!parseFunctionCallExpression()) {
                cleanup(m, UNARY_EXPRESSION);
                return false;
            }
        } else if (i.equals(OP_AND) || i.equals(OP_NOT) || i.equals(OP_ASTERISK) || i.equals(OP_PLUS) || i.equals(OP_MINUS) || i.equals(OP_TILDA) || i.equals(OP_PLUS_PLUS) || i.equals(OP_MINUS_MINUS)) {
            advance();
            if (!parseUnaryExpression()) {
                cleanup(m, UNARY_EXPRESSION);
                return false;
            }
        } else if (i.equals(KW_NEW)) {
            if (!parseNewExpression()) {
                cleanup(m, UNARY_EXPRESSION);
                return false;
            }
        } else if (i.equals(KW_DELETE)) {
            if (!parseDeleteExpression()) {
                cleanup(m, UNARY_EXPRESSION);
                return false;
            }
        } else if (i.equals(KW_CAST)) {
            if (!parseCastExpression()) {
                cleanup(m, UNARY_EXPRESSION);
                return false;
            }
        } else if (i.equals(KW_ASSERT)) {
            if (!parseAssertExpression()) {
                cleanup(m, UNARY_EXPRESSION);
                return false;
            }
        } else if (i.equals(KW_THROW)) {
            if (!parseThrowExpression()) {
                cleanup(m, UNARY_EXPRESSION);
                return false;
            }
        } else if (i.equals(OP_PAR_LEFT)) {
            final Bookmark b = setBookmark();
            skipParens();
            if (startsWith(OP_DOT, ID)) {
                // go back to the (
                goToBookmark(b);
                final Bookmark b2 = setBookmark();
                advance();
                final boolean t = parseType().first;
                if (!t || !currentIs(OP_PAR_RIGHT)) {
                    goToBookmark(b);//todo investigate the possible going to the same bookmark twice. for some reason if I prevent going to the same bookmark twice I get lots of index out of bounds from idea-core
                    if (!unaryExpressionSwitchDefault(m)) {
                        abandonBookmark(b2);
                        return false;//no cleanup needed
                    }
                } else {
                    abandonBookmark(b2);
                    advance(); // )
                    advance(); // .
                    if (!parseIdentifierOrTemplateInstance()) {
                        cleanup(m, UNARY_EXPRESSION);
                        return false;
                    }
                }
            } else {
                // not (type).identifier, so treat as primary expression
                goToBookmark(b);
                if (!unaryExpressionSwitchDefault(m)) {
                    return false;//no cleanup needed
                }
            }
        } else {
            if (!unaryExpressionSwitchDefault(m)) {
                return false;//no cleanup needed
            }
        }

        while (moreTokens()) {
            final IElementType i1 = current();
            if (i1.equals(OP_NOT)) {
                if (peekIs(OP_PAR_LEFT)) {
                    final Bookmark b = setBookmark();
                    final IElementType p = peekPastParens();
                    final boolean jump = (currentIs(OP_PAR_LEFT) && p != null && p.equals(OP_PAR_LEFT)) || peekIs(OP_PAR_LEFT);
                    goToBookmark(b);
                    if (jump) {
                        //todo fix this duplication
                        exit_section_modified(builder, m, UNARY_EXPRESSION, true);
                        final Pair<Boolean, Marker> booleanMarkerPair = parseFunctionCallExpression(m);
                        m = booleanMarkerPair.second.precede();
                        if (!booleanMarkerPair.first) {
                            cleanup(m, UNARY_EXPRESSION);
                            return false;
                        }
                    } else
                        break;
                } else
                    break;
            } else if (i1.equals(OP_PAR_LEFT)) {
                //todo fix this duplication
                exit_section_modified(builder, m, UNARY_EXPRESSION, true);
                final Pair<Boolean, Marker> booleanMarkerPair = parseFunctionCallExpression(m);
                m = booleanMarkerPair.second.precede();
                if (!booleanMarkerPair.first) {
                    cleanup(m, UNARY_EXPRESSION);
                    return false;
                }
            } else if (i1.equals(OP_PLUS_PLUS) || i1.equals(OP_MINUS_MINUS)) {
                advance();
            } else if (i1.equals(OP_BRACKET_LEFT)) {
                parseIndexExpression(false);
            } else if (i1.equals(OP_DOT)) {
                advance();
                if (currentIs(KW_NEW)) {
                    if (!parseNewExpression()) {
                        cleanup(m, UNARY_EXPRESSION);
                        return false;
                    }
                } else
                    parseIdentifierOrTemplateInstance();
            } else {
                break;
            }
        }
        exit_section_modified(builder, m, UNARY_EXPRESSION, true);
        return true;
    }

    /**
     * Parses an UnionDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF unionDeclaration):
     *   $(LITERAL 'union') $(LITERAL Identifier) ($(RULE templateParameters) $(RULE constraint)?)? ($(RULE structBody) | $(LITERAL ';'))
     * | $(LITERAL 'union') $(RULE structBody)
     * ;)
     */
    boolean parseUnionDeclaration() {
        final Marker m = enter_section_modified(builder);
        final IElementType t = expect(KW_UNION);
        if (currentIs(ID)) {
            advance();
            if (currentIs(OP_PAR_LEFT)) {
                if (!parseTemplateParameters()) {
                    cleanup(m, UNION_DECLARATION);
                    return false;
                }
                if (currentIs(KW_IF))
                    if (!parseConstraint()) {
                        cleanup(m, UNION_DECLARATION);
                        return false;
                    }
            }
            if (currentIs(OP_SCOLON))
                advance();
            else if (!parseStructBody()) {
                cleanup(m, UNION_DECLARATION);
                return false;
            }
        } else {
            if (currentIs(OP_SCOLON))
                advance();
            else if (!parseStructBody()) {
                cleanup(m, UNION_DECLARATION);
                return false;
            }
        }
        exit_section_modified(builder, m, UNION_DECLARATION, true);
        return true;
    }

    /**
     * Parses a Unittest
     * <p>
     * $(GRAMMAR $(RULEDEF unittest):
     * $(LITERAL 'unittest') $(RULE blockStatement)
     * ;)
     */
    boolean parseUnittest() {
        final Marker marker = enter_section_modified(builder);
        final boolean b = simpleParse("Unittest", KW_UNITTEST, "blockStatement|parseBlockStatement");
        exit_section_modified(builder, marker, UNITTEST, b);
        return b;
    }

    boolean parseVariableDeclaration() {
        return parseVariableDeclaration(null, false);
    }

    /**
     * Parses a VariableDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF variableDeclaration):
     *   $(RULE storageClass)* $(RULE type) $(RULE declarator) ($(LITERAL ',') $(RULE declarator))* $(LITERAL ';')
     * | $(RULE autoDeclaration)
     * ;)
     */
    boolean parseVariableDeclaration(final Marker type, final boolean isAuto)//(Type type = null )
    {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker m;
        if (type == null) {
            m = enter_section_modified(builder);
        } else {
            m = type.precede();
        }
        if (isAuto) {
            if (!parseAutoDeclaration()) {
                if (type != null)
                    m.drop();
                else
                    cleanup(m, VARIABLE_DECLARATION);
                return false;
            }
            exit_section_modified(builder, m, VARIABLE_DECLARATION, true);
            return true;
        }
        while (isStorageClass())
            if (!parseStorageClass()) {
                if (type != null)
                    m.drop();
                else
                    cleanup(m, VARIABLE_DECLARATION);
                return false;
            }
        if (type == null) {
            parseType();
        }
        //original comment from libdparse:
        //  handle function bodies correctly
        while (true) {
            final boolean declarator = parseDeclarator();
            if (!declarator) {
                if (type != null)
                    m.drop();
                else
                    cleanup(m, VARIABLE_DECLARATION);
                return false;
            }
            if (moreTokens() && currentIs(OP_COMMA)) {
                advance();
            } else
                break;
        }
        final IElementType semicolon = expect(OP_SCOLON);
        if (semicolon == null) {
            if (type != null)
                m.drop();
            else
                cleanup(m, VARIABLE_DECLARATION);
            return false;
        }
        exit_section_modified(builder, m, VARIABLE_DECLARATION, true);
        return true;
    }

    /**
     * Parses a Vector
     * <p>
     * $(GRAMMAR $(RULEDEF vector):
     * $(LITERAL '___vector') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseVector() {
        final Marker marker = enter_section_modified(builder);
        final boolean b = simpleParse("Vector", KW___VECTOR, OP_PAR_LEFT, "type|parseType", OP_PAR_RIGHT);
        exit_section_modified(builder, marker, VECTOR, b);
        return b;
    }

    /**
     * Parses a VersionCondition
     * <p>
     * $(GRAMMAR $(RULEDEF versionCondition):
     * $(LITERAL 'version') $(LITERAL '$(LPAREN)') ($(LITERAL IntegerLiteral) | $(LITERAL Identifier) | $(LITERAL 'unittest') | $(LITERAL 'assert')) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseVersionCondition() {
        final Marker m = enter_section_modified(builder);
        final IElementType v = expect(KW_VERSION);
        if (v == null) {
            cleanup(m, VERSION_CONDITION);
            return false;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, VERSION_CONDITION);
            return false;
        }
        if (currentIsOneOf(INTEGER_LITERAL, ID, KW_UNITTEST, KW_ASSERT))
            advance();
        else {
            error("Expected an integer literal, an identifier, `assert`, or `unittest`");
            exit_section_modified(builder, m, VERSION_CONDITION, true);
            return false;
        }
        expect(OP_PAR_RIGHT);
        exit_section_modified(builder, m, VERSION_CONDITION, true);
        return true;
    }

    /**
     * Parses a VersionSpecification
     * <p>
     * $(GRAMMAR $(RULEDEF versionSpecification):
     * $(LITERAL 'version') $(LITERAL '=') ($(LITERAL Identifier) | $(LITERAL IntegerLiteral)) $(LITERAL ';')
     * ;)
     */
    boolean parseVersionSpecification() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_VERSION)) {
            cleanup(m, VERSION_SPECIFICATION);
            return false;
        }
        if (!tokenCheck(OP_EQ)) {
            cleanup(m, VERSION_SPECIFICATION);
            return false;
        }
        if (!currentIsOneOf(ID, INTEGER_LITERAL)) {
            error("Identifier or integer literal expected");
            exit_section_modified(builder, m, VERSION_SPECIFICATION, true);
            return false;
        }
        advance();
        expect(OP_SCOLON);
        exit_section_modified(builder, m, VERSION_SPECIFICATION, true);
        return true;
    }

    /**
     * Parses a WhileStatement
     * <p>
     * $(GRAMMAR $(RULEDEF whileStatement):
     * $(LITERAL 'while') $(LITERAL '$(LPAREN)') $(RULE ifCondition) $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement)
     * ;)
     */
    boolean parseWhileStatement() {
        final Marker m = enter_section_modified(builder);
        if (!tokenCheck(KW_WHILE)) {
            cleanup(m, WHILE_STATEMENT);
            return false;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, WHILE_STATEMENT);
            return false;
        }
        if (!parseIfCondition()) {
            cleanup(m, WHILE_STATEMENT);
            return false;
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, WHILE_STATEMENT);
            return false;
        }
        if (currentIs(OP_BRACES_RIGHT)) {
            error("Statement expected"/*, false*/);
            exit_section_modified(builder, m, WHILE_STATEMENT, true);
            return true; // this line makes DCD better
        }
        if (!parseDeclarationOrStatement()) {
            cleanup(m, WHILE_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, WHILE_STATEMENT, true);
        return true;
    }

    /**
     * Parses a WithStatement
     * <p>
     * $(GRAMMAR $(RULEDEF withStatement):
     * $(LITERAL 'with') $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement)
     * ;)
     */
    boolean parseWithStatement() {
        final Marker marker = enter_section_modified(builder);
        final boolean b = simpleParse("WithStatement", KW_WITH, OP_PAR_LEFT, "expression|parseExpression", OP_PAR_RIGHT, "declarationOrStatement|parseDeclarationOrStatement");
        exit_section_modified(builder, marker, WITH_STATEMENT, b);
        return b;
    }

    /**
     * Parses an XorExpression
     * <p>
     * $(GRAMMAR $(RULEDEF xorExpression):
     * $(RULE andExpression)
     * | $(RULE xorExpression) $(LITERAL '^') $(RULE andExpression)
     * ;)
     */
    boolean parseXorExpression() {
        final Marker marker = enter_section_modified(builder);
        final Ref.BooleanRef toParseExpression = new Ref.BooleanRef();
        toParseExpression.element = false;
        final boolean b = parseLeftAssocBinaryExpression(toParseExpression, "XorExpression", "AndExpression",
            OP_XOR);
        if (!toParseExpression.element) {
            marker.drop();
//            beginnings.remove(marker);
            return b;
        }
        exit_section_modified(builder, marker, XOR_EXPRESSION, b);
        return b;
    }

    void setTokens(final IElementType[] tokens) {
        this.tokens = tokens;
    }

    /**
     * Returns: true if there are more tokens
     */
    boolean moreTokens() {
        return index < tokens.length;
    }

    boolean isCastQualifier() {
        final IElementType i = current();
        if (i.equals(KW_CONST)) {
            if (peekIs(OP_PAR_RIGHT))
                return true;
            return startsWith(KW_CONST, KW_SHARED, OP_PAR_RIGHT);
        } else if (i.equals(KW_IMMUTABLE)) {
            return peekIs(OP_PAR_RIGHT);
        } else if (i.equals(KW_INOUT)) {
            if (peekIs(OP_PAR_RIGHT))
                return true;
            return startsWith(KW_INOUT, KW_SHARED, OP_PAR_RIGHT);
        } else if (i.equals(KW_SHARED)) {
            if (peekIs(OP_PAR_RIGHT))
                return true;
            if (startsWith(KW_SHARED, KW_CONST, OP_PAR_RIGHT))
                return true;
            return startsWith(KW_SHARED, KW_INOUT, OP_PAR_RIGHT);
        } else {
            return false;
        }
    }

    boolean isAssociativeArrayLiteral() {
        if (cachedAAChecks.containsKey(index))
            return cachedAAChecks.get(index);
        int currentIndex = index;
        final Bookmark b = setBookmark();
        advance();
        final boolean result = !currentIs(OP_BRACKET_RIGHT) && parseExpression() && currentIs(OP_COLON);
        cachedAAChecks.put(currentIndex, result);
        goToBookmark(b);
        return result;
    }

    Pair<DecType, Integer> isAutoDeclaration() {
        int beginIndex = Integer.MAX_VALUE;
        final Bookmark b = setBookmark();
//            goToBookmark(b);// on scope exit
        loop:
        while (moreTokens()) {
            final IElementType i = current();
            if (i.equals(KW_PRAGMA)) {
                beginIndex = Integer.MAX_VALUE;
                advance();
                if (currentIs(OP_PAR_LEFT)) {
                    skipParens();
                    break;
                } else {
                    goToBookmark(b);
                    return new Pair<>(DecType.other, beginIndex);
                }
            } else if (i.equals(KW_PACKAGE) || i.equals(KW_PRIVATE) || i.equals(KW_PROTECTED) || i.equals(KW_PUBLIC)) {
                beginIndex = Integer.MAX_VALUE;
                advance();
            } else if (i.equals(OP_AT)) {
                beginIndex = Math.min(beginIndex, index);
                advance();
                if (currentIs(OP_PAR_LEFT))
                    skipParens();
                else if (currentIs(ID)) {
                    advance();
                    if (currentIs(OP_NOT)) {
                        advance();
                        if (currentIs(OP_PAR_LEFT))
                            skipParens();
                        else
                            advance();
                    }
                    if (currentIs(OP_PAR_LEFT))
                        skipParens();
                } else {
                    goToBookmark(b);
                    return new Pair<>(DecType.other, beginIndex);
                }
            } else if (i.equals(KW_DEPRECATED) || i.equals(KW_ALIGN) || i.equals(KW_EXTERN)) {
                beginIndex = Math.min(beginIndex, index);
                advance();
                if (currentIs(OP_PAR_LEFT))
                    skipParens();
            } else if (i.equals(KW_CONST) || i.equals(KW_IMMUTABLE) || i.equals(KW_INOUT) || i.equals(KW_SYNCHRONIZED)) {
                if (peekIs(OP_PAR_LEFT)) {
                    goToBookmark(b);
                    return new Pair<>(DecType.other, beginIndex);
                } else {
                    beginIndex = Math.min(beginIndex, index);
                    advance();
                    break;
                }
            } else if (i.equals(KW_AUTO) || i.equals(KW_ENUM) || i.equals(KW_EXPORT) || i.equals(KW_FINAL) || i.equals(KW___GSHARED) || i.equals(KW_NOTHROW) || i.equals(KW_OVERRIDE) || i.equals(KW_PURE) || i.equals(KW_REF) || i.equals(KW_SCOPE) || i.equals(KW_SHARED) || i.equals(KW_STATIC)) {
                beginIndex = Math.min(beginIndex, index);
                advance();
            } else {
                break loop;
            }
        }
        if (index <= b.intValue()) {
            goToBookmark(b);
            return new Pair<>(DecType.other, beginIndex);
        }
        if (startsWith(ID, OP_EQ)) {
            goToBookmark(b);
            return new Pair<>(DecType.autoVar, beginIndex);
        }
        if (startsWith(ID, OP_PAR_LEFT)) {
            advance();
            final IElementType past = peekPastParens();
            if (past == null) {
                goToBookmark(b);
                return new Pair<>(DecType.other, beginIndex);
            } else if (past.equals(OP_EQ)) {
                goToBookmark(b);
                return new Pair<>(DecType.autoVar, beginIndex);
            } else {
                goToBookmark(b);
                return new Pair<>(DecType.autoFun, beginIndex);
            }
        }
        goToBookmark(b);
        return new Pair<>(DecType.other, beginIndex);
    }

    boolean isDeclaration() {
        if (!moreTokens()) return false;
        final IElementType i = current();
        if (i.equals(KW_FINAL)) {
            return !peekIs(KW_SWITCH);
        }
        if (i.equals(KW_DEBUG)) {
            if (peekIs(OP_COLON))
                return true;
            if (peekIs(OP_EQ))
                return true;
            if (peekIs(OP_PAR_LEFT)) {
                //default
                return isDeclarationDefault();
            }
            return false;
        }
        if (i.equals(KW_VERSION)) {
            if (peekIs(OP_EQ))
                return true;
            if (peekIs(OP_PAR_LEFT)) {
                //default
                return isDeclarationDefault();
            }
            return false;
        }
        if (i.equals(KW_SYNCHRONIZED)) {
            if (peekIs(OP_PAR_LEFT))
                return false;
            else {
                //default
                return isDeclarationDefault();
            }
        }
        if (i.equals(KW_STATIC)) {
            if (peekIs(KW_IF))
                return false;
            if (peekIs(KW_FOREACH) || peekIs(KW_FOREACH_REVERSE))
                return isDeclarationDefault();
            return !peekIs(OP_PAR_LEFT);
        }
        if (i.equals(KW_SCOPE)) {
            return !peekIs(OP_PAR_LEFT);
        }
        if (i.equals(OP_AT) || i.equals(KW_ABSTRACT) || i.equals(KW_ALIAS) || i.equals(KW_ALIGN) || i.equals(KW_AUTO) || i.equals(KW_CLASS) || i.equals(KW_DEPRECATED) || i.equals(KW_ENUM) || i.equals(KW_EXPORT) || i.equals(KW_EXTERN) || i.equals(KW___GSHARED) || i.equals(KW_INTERFACE) || i.equals(KW_NOTHROW) || i.equals(KW_OVERRIDE) || i.equals(KW_PACKAGE) || i.equals(KW_PRIVATE) || i.equals(KW_PROTECTED) || i.equals(KW_PUBLIC) || i.equals(KW_PURE) || i.equals(KW_REF) || i.equals(KW_STRUCT) || i.equals(KW_UNION) || i.equals(KW_UNITTEST)) {
            return true;
        }
        if (isBasicType(i)) {
            return !peekIsOneOf(OP_DOT, OP_PAR_LEFT);
        }
        if (i.equals(KW_ASM) || i.equals(KW_BREAK) || i.equals(KW_CASE) || i.equals(KW_CONTINUE) || i.equals(KW_DEFAULT) || i.equals(KW_DO) || i.equals(KW_FOR) || i.equals(KW_FOREACH) || i.equals(KW_FOREACH_REVERSE) || i.equals(KW_GOTO) || i.equals(KW_IF) || i.equals(KW_RETURN) || i.equals(KW_SWITCH) || i.equals(KW_THROW) || i.equals(KW_TRY) || i.equals(KW_WHILE) || i.equals(OP_BRACES_LEFT) || i.equals(KW_ASSERT)) {
            return false;
        } else {
            return isDeclarationDefault();
        }
    }

    private boolean isDeclarationDefault() {
        final Bookmark b = setBookmark();
        final boolean res = parseDeclaration(true, true);
        goToBookmark(b);
        return res;
    }

    /// Only use this in template parameter parsing
    boolean isType() {
        if (!moreTokens()) return false;
        final Bookmark b = setBookmark();
        if (!parseType().first) {
            goToBookmark(b);
            return false;
        }
        goToBookmark(b);
        return currentIsOneOf(OP_COMMA, OP_PAR_RIGHT, OP_EQ);
    }

    private boolean isStorageClass() {
        if (!moreTokens()) return false;
        final IElementType i = current();
        if (i.equals(KW_CONST) || i.equals(KW_IMMUTABLE) || i.equals(KW_INOUT) || i.equals(KW_SHARED)) {
            return !peekIs(OP_PAR_LEFT);
        } else
            return i.equals(OP_AT) || i.equals(KW_DEPRECATED) || i.equals(KW_ABSTRACT) || i.equals(KW_ALIGN) || i.equals(KW_AUTO) || i.equals(KW_ENUM) || i.equals(KW_EXTERN) || i.equals(KW_FINAL) || i.equals(KW_NOTHROW) || i.equals(KW_OVERRIDE) || i.equals(KW_PURE) || i.equals(KW_REF) || i.equals(KW___GSHARED) || i.equals(KW_SCOPE) || i.equals(KW_STATIC) || i.equals(KW_SYNCHRONIZED);
    }

    boolean isAttribute() {
        if (!moreTokens()) return false;
        final IElementType i = current();
        if (i.equals(KW_CONST) || i.equals(KW_IMMUTABLE) || i.equals(KW_INOUT) || i.equals(KW_SCOPE)) {
            return !peekIs(OP_PAR_LEFT);
        } else if (i.equals(KW_STATIC)) {
            return !peekIsOneOf(KW_ASSERT, KW_THIS, KW_IF, OP_TILDA, KW_FOREACH, KW_FOREACH_REVERSE);
        } else if (i.equals(KW_SHARED)) {
            return !(startsWith(KW_SHARED, KW_STATIC, KW_THIS) || startsWith(KW_SHARED, KW_STATIC, OP_TILDA) || peekIs(OP_PAR_LEFT));
        } else if (i.equals(KW_PRAGMA)) {
            final Bookmark b = setBookmark();
            advance();
            final IElementType past = peekPastParens();
            if (past == null || past.equals(OP_SCOLON)) {
                goToBookmark(b);
                return false;
            }
            goToBookmark(b);
            return true;
        } else
            return i.equals(KW_DEPRECATED) || i.equals(KW_PRIVATE) || i.equals(KW_PACKAGE) || i.equals(KW_PROTECTED) || i.equals(KW_PUBLIC) || i.equals(KW_EXPORT) || i.equals(KW_FINAL) || i.equals(KW_SYNCHRONIZED) || i.equals(KW_OVERRIDE) || i.equals(KW_ABSTRACT) || i.equals(KW_AUTO) || i.equals(KW___GSHARED) || i.equals(KW_PURE) || i.equals(KW_NOTHROW) || i.equals(OP_AT) || i.equals(KW_REF) || i.equals(KW_EXTERN) || i.equals(KW_ALIGN);
    }

    private boolean isMemberFunctionAttribute(final IElementType t) {
        return t.equals(KW_CONST) || t.equals(KW_IMMUTABLE) || t.equals(KW_INOUT) || t.equals(KW_SHARED) || t.equals(OP_AT) || t.equals(KW_PURE) || t.equals(KW_NOTHROW) || t.equals(KW_RETURN) || t.equals(KW_SCOPE);
    }

    private boolean isTypeCtor(final IElementType t) {
        return t.equals(KW_CONST) || t.equals(KW_IMMUTABLE) || t.equals(KW_INOUT) || t.equals(KW_SHARED);
    }

    private boolean currentIsMemberFunctionAttribute() {
        return moreTokens() && isMemberFunctionAttribute(current());
    }

    private boolean parseLeftAssocBinaryExpression(final Ref.BooleanRef operatorWasMatched,
        final String ExpressionType, final String ExpressionPartType,
        @NotNull final IElementType... operators) {
        return parseLeftAssocBinaryExpression(operatorWasMatched, ExpressionType, ExpressionPartType, false, operators);
    }

    private boolean parseLeftAssocBinaryExpression(final Ref.BooleanRef operatorWasMatched,
        final String ExpressionType, final String ExpressionPartType, final boolean part,
        @NotNull final IElementType... operators)//(alias ExpressionType, alias ExpressionPartType, Operators ...)(ExpressionNode part = null)
    {
        operatorWasMatched.element = false;
        final boolean node;
        if (!part) {
            node = parseName(ExpressionPartType);
            if (!node)
                return false;
        }
        while (currentIsOneOf(operators)) {
            operatorWasMatched.element = true;
//                auto n = allocator.make!ExpressionType;
            advance();
            if (!parseNodeQ(ExpressionPartType)) {
                return false;
            }
        }
        return true;
    }

    private boolean parseCommaSeparatedRule(final String listType, final String itemType) {
        return parseCommaSeparatedRule(listType, itemType, false);
    }

    private boolean parseCommaSeparatedRule(final String listType, final String itemType, boolean allowTrailingComma) {
        return parseCommaSeparatedRule(new Ref.IntRef(), listType, itemType, allowTrailingComma);
    }

    private boolean parseCommaSeparatedRule(final Ref.IntRef foreachTypeRefLength, final String listType, final String itemType) {
        return parseCommaSeparatedRule(foreachTypeRefLength, listType, itemType, false);
    }

    private boolean parseCommaSeparatedRule(final Ref.IntRef foreachTypeRefLength, final String listType, final String itemType, boolean allowTrailingComma)//(alias ListType, alias ItemType,)
    {
//            final boolean setLineAndColumn = false;
//        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_modified(builder,m,DlangTypes.ListType,false);
//            if (setLineAndColumn)
//            {
////                node.line = current().line;
////                node.column = current().column;
//            }
//            final Marker m = enter_section_(builder);
        foreachTypeRefLength.element = 0;
        while (moreTokens()) {
            if (!parseName(itemType)) {
//                    cleanup(m);
                return false;
            }
            foreachTypeRefLength.element++;
            if (currentIs(OP_COMMA)) {
                advance();
                if (allowTrailingComma && currentIsOneOf(OP_PAR_RIGHT, OP_BRACES_RIGHT, OP_BRACKET_RIGHT))
                    break;
                else
                    continue;
            } else
                break;
        }
//            exit_section_modified(builder,m,,true);
        return true;
    }

    private void warn(final String message) {
        if (!suppressMessages.isEmpty())
            return;
        ++warningCount;
        //do nothing, potential add as an error.
//            auto column = index < tokens.length ? tokens[index].column : 0;
//            auto line = index < tokens.length ? tokens[index].line : 0;
//            if (messageFunction == null)
//            stderr.writefln("%s(%d:%d)[warn]: %s", fileName, line, column, message);
//        else
//            messageFunction(fileName, line, column, message, false);
    }

    private void error(final String message) {
        if (suppressMessages.isEmpty()) {
            ++errorCount;
//                auto column = index < tokens.length ? tokens[index].column : tokens[$ - 1].column;
//                auto line = index < tokens.length ? tokens[index].line : tokens[$ - 1].line;
//                if (messageFunction == null)
//                stderr.writefln("%s(%d:%d)[error]: %s", fileName, line, column, message);
//            else
//                messageFunction(fileName, line, column, message, true);
            builder.error(message);
        } else
            suppressMessages.set(suppressMessages.size() - 1, suppressMessages.get(suppressMessages.size() - 1) + 1);
        while (moreTokens()) {
            if (currentIsOneOf(OP_SCOLON, OP_BRACES_RIGHT,
                OP_PAR_RIGHT, OP_BRACKET_RIGHT)) {
                advance();
                break;
            } else
                advance();
        }
    }

    private void skip(final IElementType o, final IElementType c)//(alias O, alias C)
    {
        assert (currentIs(o));
        advance();
        int depth = 1;
        while (moreTokens()) {
            if (tokens[index].equals(c)) {
                advance();
                depth--;
                if (depth <= 0)
                    return;
            } else if (tokens[index].equals(o)) {
                depth++;
                advance();
            } else {
                advance();
            }
        }
    }

    void skipBraces() {
        skip(OP_BRACES_LEFT, OP_BRACES_RIGHT);
    }

    private void skipParens() {
        skip(OP_PAR_LEFT, OP_PAR_RIGHT);
    }

    private void skipBrackets() {
        skip(OP_BRACKET_LEFT, OP_BRACKET_RIGHT);
    }

    IElementType peek() {
        return index + 1 < tokens.length ? tokens[index + 1] : null;
    }

    private IElementType peekPast(final IElementType o, final IElementType c)//(alias O, alias C)
    {
        if (index >= tokens.length)
            return null;
        int depth = 1;
        int i = index;
        ++i;
        while (i < tokens.length) {
            if (tokens[i].equals(o)) {
                ++depth;
                ++i;
            } else if (tokens[i].equals(c)) {
                --depth;
                ++i;
                if (depth <= 0)
                    break;
            } else
                ++i;
        }
        return i >= tokens.length ? null : depth == 0 ? tokens[i] : null;
    }

    private IElementType peekPastParens() {
        return peekPast(OP_PAR_LEFT, OP_PAR_RIGHT);
    }

    private IElementType peekPastBrackets() {
        return peekPast(OP_BRACKET_LEFT, OP_BRACKET_RIGHT);
    }

    private IElementType peekPastBraces() {
        return peekPast(OP_BRACES_LEFT, OP_BRACES_RIGHT);
    }

    /**
     * Returns: `true` if there is a next token and that token has the type `t`.
     */
    private boolean peekIs(final IElementType t) {
        return peekNIs(t, 1);
    }

    /**
     * Returns: `true` if the token `offset` tokens ahead exists and is of type `t`.
     */
    private boolean peekNIs(final IElementType t, int offset)
    {
        return index + offset < tokens.length && tokens[index + offset].equals(t);
    }

    /**
     * Returns: `true` if there are at least `types.length` tokens following the
     * current one and they have types matching the corresponding elements of
     * `types`.
     */
    private boolean peekAre(final IElementType... types) {
        int i = 0;
        for (IElementType type : types) {
            if (!peekNIs(type, i + 1))
                return false;
            i++;
        }
        return true;
    }

    /**
     * Returns: `true` if there is a next token and its type is one of the given
     * `types`.
     */
    private boolean peekIsOneOf(final IElementType... types) {
        if (index + 1 >= tokens.length) return false;
        final IElementType needle = tokens[index + 1];
        for (final IElementType type : types) {
            if (type.equals(needle)) {
                return true;
            }
        }
        return false;

//        return canFind(types, );
    }

    /**
     * Returns a token of the specified type if it was the next token, otherwise
     * calls the error function and returns null. Advances the lexer by one token.
     */
    private IElementType expect(final IElementType tok) {
        if (index < tokens.length && tokens[index].equals(tok)) {
//            assert (builder.getTokenType().equals(tokens[index].type.type));
            if (!builder.getTokenType().equals(tokens[index])) {
                throw new AssertionError();
            }
            Marker m = null;
            if (currentIs(ID)) {
                m = enter_section_(builder);
            }
            builder.advanceLexer();
            index++;
            if (m != null) {
                exit_section_(builder, m, IDENTIFIER, true);
            }
            return tokens[index - 1];
        } else {
            final String tokenString = tok.getDebugName();
//            final boolean shouldNotAdvance = index < tokens.length && (tokens[index].type.equals(OP_PAR_RIGHT) || tokens[index].type.equals(OP_SCOLON) || tokens[index].type.equals(OP_BRACES_RIGHT));
            final String token = (index < tokens.length ? (tokens[index].toString()) : "EOF");
            error("Expected " + tokenString + " instead of " + token/*,!shouldNotAdvance*/);
            return null;
        }
    }

    /**
     * Returns: the _current token
     */
    @Nullable
    private IElementType current() {
        return index >= 0 && index < tokens.length ? tokens[index] : null;
    }

    @Nullable
    private String currentText() {
        return index >= 0 && index < tokens.length ? builder.getTokenText() : null;
    }

    /**
     * Returns: the _previous token
     */
    private IElementType previous() {
        return tokens[index - 1];
    }

    /**
     * Advances to the next token and returns the current token
     */
    private IElementType advance() {
        if (builder.getTokenType() != null && !builder.getTokenType().equals(tokens[index])) {
            throw new AssertionError(
                String.format("token type '%s' does not match %s",
                    builder.getTokenType(),
                    tokens[index]
                )
            );
        }
        Marker identifierMarker = null;
        if (currentIs(ID)) {
            identifierMarker = enter_section_(builder);
        }
        builder.advanceLexer();
        index++;
        if (identifierMarker != null) {
            exit_section_(builder, identifierMarker, IDENTIFIER, true);
        }
        return tokens[index - 1];
    }

    /**
     * Returns: true if the current token has the given type
     */
    private boolean currentIs(final IElementType type) {
        return index < tokens.length && tokens[index].equals(type);
    }

    /**
     * Returns: true if the current token is one of the given types
     */
    private boolean currentIsOneOf(final IElementType... types) {
        if (index >= tokens.length) return false;

        final IElementType curr = current();

        if(curr != null && types != null) {
            return Arrays.stream(types)
                .anyMatch(t -> t.equals(curr));
        }

        return false;

//        return canFind(types, current().type);
    }

    private boolean startsWith(final IElementType... types) {
        if (index + types.length >= tokens.length)
            return false;
        for (int i = 0; (i < types.length) && ((index + i) < tokens.length); ++i) {
            if (!tokens[index + i].equals(types[i]))
                return false;
        }
        return true;
    }

    private Bookmark setBookmark() {
        suppressMessages.add(suppressedErrorCount());
        final Marker m = enter_section_modified(builder);
        return new Bookmark(index, m);
    }

    private void abandonBookmark(final Bookmark bookmark) {
        if (!suppressMessages.isEmpty())
            suppressMessages.remove(suppressMessages.size() - 1);
        if (!bookmark.dropped) {
            bookmark.m.drop();
            bookmark.dropped = true;
        }
    }

    private void goToBookmark(final Bookmark bookmark) {
        if (!suppressMessages.isEmpty())
            suppressMessages.remove(suppressMessages.size() - 1);
        index = bookmark.num;
//        assert !bookmark.dropped;
        bookmark.m.rollbackTo();
        bookmark.dropped = true;
    }

    private boolean parseNodeQ(final String NodeName) {
        return parseName(NodeName);
    }

    private boolean tokenCheck(final IElementType tok) {
        return expect(tok) != null;
    }

    private boolean parseStaticCtorDtorCommon() {
//            node.line = current().line;
//            node.column = current().column;
        if (!tokenCheck(KW_THIS)) {
            return false;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            return false;
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            return false;
        }
        while (moreTokens() && !currentIsOneOf(OP_BRACES_LEFT, KW_IN, KW_OUT,
            KW_DO, OP_SCOLON)) {
            if (!(parseMemberFunctionAttribute())) {
                return false;
            }
        }
        if (currentIs(OP_SCOLON))
            advance();
        else {
            return parseFunctionBody();
        }
        return true;
    }

    boolean parseInterfaceOrClass() {
        final Marker m = enter_section_(builder);
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, INTERFACE_OR_CLASS);
            return false;
        }
        if (currentIs(OP_SCOLON)) {
            return emptyBody(m);
        }
        if (currentIs(OP_BRACES_LEFT)) {
            return structBody(m);
        }
        if (currentIs(OP_PAR_LEFT)) {
            if (!parseTemplateParameters()) {
                cleanup(m, INTERFACE_OR_CLASS);
                return false;
            }
            if (currentIs(OP_SCOLON)) {
                return emptyBody(m);
            }
            return constraint(m, false);
        }
        if (currentIs(OP_COLON)) {
            return baseClassList(m);
        }
        return structBody(m);

    }

    private boolean emptyBody(final Marker m) {
        advance();
        exit_section_(builder, m, INTERFACE_OR_CLASS, true);
        return true;
    }

    private boolean structBody(final Marker m) {
        final boolean res = parseStructBody();
        if (res) {
            exit_section_(builder, m, INTERFACE_OR_CLASS, true);
        } else
            cleanup(m, INTERFACE_OR_CLASS);
        return res;
    }

    private boolean baseClassList(final Marker m) {
        advance(); // :
        if (!parseBaseClassList()) {
            cleanup(m, INTERFACE_OR_CLASS);
            return false;
        }
        if (currentIs(KW_IF)) {
            return constraint(m, true);
        }
        return structBody(m);
    }

    private boolean constraint(final Marker m, final boolean baseClassListQ) {
        if (currentIs(KW_IF)) {
            if (!parseConstraint()) {
                cleanup(m, INTERFACE_OR_CLASS);
                return false;
            }
        }
        if (baseClassListQ) {
            if (currentIs(OP_BRACES_LEFT)) {
                return structBody(m);
            } else if (currentIs(OP_SCOLON)) {
                return emptyBody(m);
            } else {
                error("Struct body or ';' expected");
                cleanup(m, INTERFACE_OR_CLASS);
                return false;
            }
        }
        if (currentIs(OP_COLON)) {
            return baseClassList(m);
        }
        if (currentIs(KW_IF)) {
            return constraint(m, baseClassListQ);
        }
        if (currentIs(OP_SCOLON)) {
            return emptyBody(m);
        }
        if (currentIs(OP_COLON)) {
            return baseClassList(m);
        }
        return structBody(m);
    }

    private boolean parseName(final String NodeName) {
        switch (NodeName) {
            case "AliasThisDeclaration":
                return parseAliasThisDeclaration();
            case "AlignAttribute":
                return parseAlignAttribute();
            case "ArgumentList":
                return parseArgumentList().first;
            case "Arguments":
                return parseArguments();
            case "ArrayInitializer":
                return parseArrayInitializer();
            case "ArrayLiteral":
                return parseArrayLiteral();
            case "ArrayMemberInitialization":
                return parseArrayMemberInitialization();
            case "AsmInstruction":
                return parseAsmInstruction();
            case "AsmPrimaryExp":
                return parseAsmPrimaryExp();
            case "AsmStatement":
                return parseAsmStatement();
            case "AsmTypePrefix":
                return parseAsmTypePrefix();
            case "AssocArrayLiteral":
                return parseAssocArrayLiteral();
            case "AtAttribute":
                return parseAtAttribute();
            case "Attribute":
                return parseAttribute();
            case "AttributeDeclaration":
                return parseAttributeDeclaration();
            case "AutoDeclaration":
                return parseAutoDeclaration();
            case "AutoDeclarationPart":
                return parseAutoDeclarationPart();
            case "BlockStatement":
                return parseBlockStatement();
            case "BreakStatement":
                return parseBreakStatement();
            case "BaseClass":
                return parseBaseClass();
            case "BaseClassList":
                return parseBaseClassList();
            case "CastExpression":
                return parseCastExpression();
            case "CastQualifier":
                return parseCastQualifier();
            case "Catch":
                return parseCatch();
            case "Catches":
                return parseCatches();
            case "ClassDeclaration":
                return parseClassDeclaration();
            case "CompileCondition":
                return parseCompileCondition();
            case "ConditionalStatement":
                return parseConditionalStatement();
            case "Constraint":
                return parseConstraint();
            case "Constructor":
                return parseConstructor();
            case "ContinueStatement":
                return parseContinueStatement();
            case "DebugCondition":
                return parseDebugCondition();
            case "DebugSpecification":
                return parseDebugSpecification();
            case "Declaration":
                return parseDeclaration();
            case "DeclarationsAndStatements":
                return parseDeclarationsAndStatements();
            case "DeclarationOrStatement":
                return parseDeclarationOrStatement();
            case "Declarator":
                return parseDeclarator();
            case "DeclaratorIdentifierList":
                return parseDeclaratorIdentifierList();
            case "DefaultStatement":
                return parseDefaultStatement();
            case "Deprecated":
                return parseDeprecated();
            case "Destructor":
                return parseDestructor();
            case "DoStatement":
                return parseDoStatement();
            case "EnumBody":
                return parseEnumBody();
            case "AnonymousEnumMember":
                return parseAnonymousEnumMember();
            case "AnonymousEnumDeclaration":
                return parseAnonymousEnumDeclaration();
            case "EnumDeclaration":
                return parseEnumDeclaration();
            case "EnumMember":
                return parseEnumMember();
            case "EponymousTemplateDeclaration":
                return parseEponymousTemplateDeclaration();
            case "FinalSwitchStatement":
                return parseFinalSwitchStatement();
            case "Finally":
                return parseFinally();
            case "ForStatement":
                return parseForStatement();
            case "ForeachStatement":
                return parseForeachStatement();
            case "ForeachType":
                return parseForeachType();
            case "ForeachTypeList":
                return parseForeachTypeList().first;
            case "FunctionBody":
                return parseFunctionBody();
            case "GotoStatement":
                return parseGotoStatement();
            case "IdentifierChain":
                return parseIdentifierChain();
            case "IdentifierOrTemplateChain":
                return parseIdentifierOrTemplateChain();
            case "IdentifierOrTemplateInstance":
                return parseIdentifierOrTemplateInstance();
            case "IfStatement":
                return parseIfStatement();
            case "ImportBind":
                return parseImportBind();
            case "ImportDeclaration":
                return parseImportDeclaration();
            case "InStatement":
                return parseInStatement();
            case "Initializer":
                return parseInitializer();
            case "InterfaceDeclaration":
                return parseInterfaceDeclaration();
            case "Invariant":
                return parseInvariant();
            case "KeyValuePair":
                return parseKeyValuePair();
            case "KeyValuePairs":
                return parseKeyValuePairs();
            case "LabeledStatement":
                return parseLabeledStatement();
            case "LastCatch":
                return parseLastCatch();
            case "LinkageAttribute":
                return parseLinkageAttribute();
            case "MemberFunctionAttribute":
                return parseMemberFunctionAttribute();
            case "MixinDeclaration":
                return parseMixinDeclaration();
            case "MixinTemplateDeclaration":
                return parseMixinTemplateDeclaration();
            case "MixinTemplateName":
                return parseMixinTemplateName();
            case "Module":
                return parseModule();
            case "ModuleDeclaration":
                return parseModuleDeclaration();
            case "NonVoidInitializer":
                return parseNonVoidInitializer();
            case "Operands":
                return parseOperands();
            case "OutStatement":
                return parseOutStatement();
            case "Parameter":
                return parseParameter();
            case "Parameters":
                return parseParameters();
            case "Postblit":
                return parsePostblit();
            case "PragmaDeclaration":
                return parsePragmaDeclaration();
            case "Register":
                return parseRegister();
            case "ReturnStatement":
                return parseReturnStatement();
            case "ScopeGuardStatement":
                return parseScopeGuardStatement();
            case "SharedStaticConstructor":
                return parseSharedStaticConstructor();
            case "SharedStaticDestructor":
                return parseSharedStaticDestructor();
            case "SingleImport":
                return parseSingleImport();
            case "Statement":
                return parseStatement();
            case "StatementNoCaseNoDefault":
                return parseStatementNoCaseNoDefault();
            case "StaticAssertDeclaration":
                return parseStaticAssertDeclaration();
            case "StaticAssertStatement":
                return parseStaticAssertStatement();
            case "StaticConstructor":
                return parseStaticConstructor();
            case "StaticDestructor":
                return parseStaticDestructor();
            case "StaticIfCondition":
                return parseStaticIfCondition();
            case "StorageClass":
                return parseStorageClass();
            case "StructBody":
                return parseStructBody();
            case "StructDeclaration":
                return parseStructDeclaration();
            case "StructInitializer":
                return parseStructInitializer();
            case "StructMemberInitializer":
                return parseStructMemberInitializer();
            case "StructMemberInitializers":
                return parseStructMemberInitializers();
            case "SwitchStatement":
                return parseSwitchStatement();
            case "Symbol":
                return parseSymbol();
            case "SynchronizedStatement":
                return parseSynchronizedStatement();
            case "TemplateAliasParameter":
                return parseTemplateAliasParameter();
            case "TemplateArgument":
                return parseTemplateArgument();
            case "TemplateArgumentList":
                return parseTemplateArgumentList();
            case "TemplateArguments":
                return parseTemplateArguments();
            case "TemplateDeclaration":
                return parseTemplateDeclaration();
            case "TemplateInstance":
                return parseTemplateInstance();
            case "TemplateParameter":
                return parseTemplateParameter();
            case "TemplateParameterList":
                return parseTemplateParameterList();
            case "TemplateParameters":
                return parseTemplateParameters();
            case "TemplateSingleArgument":
                return parseTemplateSingleArgument();
            case "TemplateThisParameter":
                return parseTemplateThisParameter();
            case "TemplateTupleParameter":
                return parseTemplateTupleParameter();
            case "TemplateTypeParameter":
                return parseTemplateTypeParameter();
            case "TemplateValueParameter":
                return parseTemplateValueParameter();
            case "TemplateValueParameterDefault":
                return parseTemplateValueParameterDefault();
            case "ThrowExpression":
                return parseThrowExpression();
            case "TryStatement":
                return parseTryStatement();
            case "Type":
                return parseType().first;
            case "Type2":
                return parseType2();
            case "TypeIdentifierChain":
                return parseIdentifierChain();
            case "TypeSpecialization":
                return parseTypeSpecialization();
            case "TypeSuffix":
                return parseTypeSuffix();
            case "UnionDeclaration":
                return parseUnionDeclaration();
            case "Unittest":
                return parseUnittest();
            case "VariableDeclaration":
                return parseVariableDeclaration();
            case "Vector":
                return parseVector();
            case "VersionCondition":
                return parseVersionCondition();
            case "VersionSpecification":
                return parseVersionSpecification();
            case "WhileStatement":
                return parseWhileStatement();
            case "WithStatement":
                return parseWithStatement();
            case "AddExpression":
                return parseAddExpression();
            case "AndAndExpression":
                return parseAndAndExpression();
            case "AndExpression":
                return parseAndExpression();
            case "AsmAddExp":
                return parseAsmAddExp();
            case "AsmAndExp":
                return parseAsmAndExp();
            case "AsmBrExp":
                return parseAsmBrExp();
            case "AsmExp":
                return parseAsmExp();
            case "AsmEqualExp":
                return parseAsmEqualExp();
            case "AsmLogAndExp":
                return parseAsmLogAndExp();
            case "AsmLogOrExp":
                return parseAsmLogOrExp();
            case "AsmMulExp":
                return parseAsmMulExp();
            case "AsmOrExp":
                return parseAsmOrExp();
            case "AsmRelExp":
                return parseAsmRelExp();
            case "AsmUnaExp":
                return parseAsmUnaExp();
            case "AsmShiftExp":
                return parseAsmShiftExp();
            case "AsmXorExp":
                return parseAsmXorExp();
            case "AssertExpression":
                return parseAssertExpression();
            case "AssignExpression":
                return parseAssignExpression();
            case "CmpExpression":
                return parseCmpExpression();
            case "DeleteExpression":
                return parseDeleteExpression();
            case "Expression":
                return parseExpression();
            case "FunctionCallExpression":
                return parseFunctionCallExpression();
            case "FunctionLiteralExpression":
                return parseFunctionLiteralExpression();
            case "IdentityExpression":
                return parseIdentityExpression();
            case "ImportExpression":
                return parseImportExpression();
            case "IndexExpression":
                return parseIndexExpression();
            case "IsExpression":
                return parseIsExpression();
            case "MixinExpression":
                return parseMixinExpression();
            case "MulExpression":
                return parseMulExpression();
            case "NewAnonClassExpression":
                return parseNewAnonClassExpression();
            case "NewExpression":
                return parseNewExpression();
            case "OrExpression":
                return parseOrExpression();
            case "OrOrExpression":
                return parseOrOrExpression();
            case "PowExpression":
                return parsePowExpression();
            case "PragmaExpression":
                return parsePragmaExpression();
            case "PrimaryExpression":
                return parsePrimaryExpression();
            case "RelExpression":
                return parseRelExpression();
            case "ShiftExpression":
                return parseShiftExpression();
            case "Index":
                return parseIndex();
            case "TemplateMixinExpression":
                return parseTemplateMixinExpression();
            case "TernaryExpression":
                return parseTernaryExpression();
            case "TraitsExpression":
                return parseTraitsExpression();
            case "TypeidExpression":
                return parseTypeidExpression();
            case "TypeofExpression":
                return parseTypeofExpression();
            case "UnaryExpression":
                return parseUnaryExpression();
            case "XorExpression":
                return parseXorExpression();
            case "AliasInitializer":
                return parseAliasInitializer();
            case "ExpressionStatement":
                return parseExpressionStatement();
            case "TypeConstructors":
                return parseTypeConstructors() != null;
            case "AliasDeclaration":
                return parseAliasDeclaration();
            default:
                throw new IllegalArgumentException("unrecognized thing to parse:" + NodeName);
        }
    }

    private boolean parseExpressionStatement() {
        return parseExpressionStatement(true);
    }

    enum DecType {
        autoVar,
        autoFun,
        other
    }

    class Pair<First, Second> {
        final First first;
        final Second second;

        Pair(final First first, final Second second) {
            this.first = first;
            this.second = second;
        }
    }

    class Bookmark {
        final int num;
        final Marker m;
        private boolean dropped = false;

        Bookmark(final int num, final Marker m) {
            this.num = num;
            this.m = m;
        }

        int intValue() {
            return num;
        }
    }

}
