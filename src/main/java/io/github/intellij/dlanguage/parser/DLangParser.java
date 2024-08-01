package io.github.intellij.dlanguage.parser;

import com.google.common.collect.Sets;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;

class DLangParser {

    // Please keep this list in order
    private static final HashSet<String> REGISTER_NAMES = new HashSet<>(Arrays.asList(
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
        "YMM3", "YMM4", "YMM5", "YMM6", "YMM7", "YMM8", "YMM9"));

    private static final IElementType[] stringLiteralsArray = new IElementType[] {
        ALTERNATE_WYSIWYG_STRING,
        DOUBLE_QUOTED_STRING,
        WYSIWYG_STRING,
        DELIMITED_STRING,
        TOKEN_STRING
    };

    private static final Set<IElementType> literals = Sets.newHashSet(
        KW_THIS,
        KW_SUPER,
        KW_NULL,
        KW_TRUE,
        KW_FALSE,
        INTEGER_LITERAL,  // represent int, long, uint and ulong values
        FLOAT_LITERAL, // represent float, double, ifloat, idouble, real and ireal values
        CHARACTER_LITERAL,
        ALTERNATE_WYSIWYG_STRING,
        DOUBLE_QUOTED_STRING,
        WYSIWYG_STRING,
        DELIMITED_STRING,
        TOKEN_STRING
    );

    private static final Set<IElementType> basicTypes = Sets.newHashSet(
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

    private static final Set<IElementType> Protections = Sets.newHashSet(
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

    DLangParser(@NotNull final PsiBuilder builder) {
        this.builder = builder;
    }

    private void cleanup(@NotNull final Marker marker, final IElementType element) {
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
    Marker parseAddExpression() {
        Marker m = parseMulExpression();
        if (m == null)
            return null;
        while (currentIsOneOf(OP_PLUS, OP_MINUS, OP_TILDA)) {
            m = m.precede();
            builder.advanceLexer();
            if (parseMulExpression() == null) {
                cleanup(m, ADD_EXPRESSION);
                return null;
            }
            m.done(ADD_EXPRESSION);
        }
        return m;
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
    boolean parseAliasDeclaration(Marker m) {
        if (builder.getTokenType() != KW_ALIAS)
            return false;
        builder.advanceLexer();
        if (startsWith(ID, OP_EQ) || startsWith(ID, OP_PAR_LEFT)) {
            do {
                if (!parseAliasInitializer()) {
                    m.done(ALIAS_DECLARATION);
                    return true;
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
                    m.done(ALIAS_DECLARATION);
                    return true;
                }
            }
            if (!parseType()) {
                m.done(ALIAS_DECLARATION);
                return true;
            }
            if (!parseDeclaratorIdentifierList()) {
                m.done(ALIAS_DECLARATION);
                return true;
            }
            if (currentIs(OP_PAR_LEFT)) {
                if (!parseParameters()) {
                    m.done(ALIAS_DECLARATION);
                    return true;
                }
                while (moreTokens() && currentIsMemberFunctionAttribute()) {
                    if (!parseMemberFunctionAttribute()) {
                        m.done(ALIAS_DECLARATION);
                        return true;
                    }
                }
            }
        }
        expect(OP_SCOLON);
        m.done(ALIAS_DECLARATION);
        return true;
    }

    private boolean isFunction() {
        if (currentIsOneOf(KW_FUNCTION, KW_DELEGATE, OP_BRACES_LEFT))
            return true;
        if (startsWith(ID, OP_LAMBDA_ARROW))
            return true;
        Marker bookmark = builder.mark();
        if (currentIs(OP_PAR_LEFT) || currentIs(KW_REF) && peekIs(OP_PAR_LEFT)) {
            if (currentIs(KW_REF))
                advance();
            final IElementType t = peekPastParens();
            if (t != null) {
                if (t == OP_LAMBDA_ARROW || t == OP_BRACES_LEFT
                    || isMemberFunctionAttribute(t)) {
                    bookmark.rollbackTo();
                    return true;
                }
            }
        }
        bookmark.rollbackTo();
        return false;
    }

    /**
     * Parses an AliasAssign.
     * <p>
     * $(GRAMMAR $(RULEDEF aliasAssign):
     *   $(LITERAL Identifier) $(LITERAL '=') $(RULE type)
     */
    boolean parseAliasAssign(Marker m)
    {
        if (builder.getTokenType() != ID)
            return false;
        final Marker bookmark = builder.mark();
        advance();
        if (!tokenCheck(OP_EQ)) {
            bookmark.rollbackTo();
            return false;
        }
        if (!parseType()) {
            bookmark.rollbackTo();
            return false;
        }
        if (builder.getTokenType() != OP_SCOLON) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        builder.advanceLexer();
        m.done(ALIAS_ASSIGN);
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
        final Marker m = builder.mark();
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
            if (parseFunctionLiteralExpression() == null) {
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
            if (!parseType()) {
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
     * | $(LITERAL 'alias') $(LITERAL 'this') $(LITERAL Identifier) $(LITERAL ';')
     * ;)
     */
    boolean parseAliasThisDeclaration(Marker m) {
        if (builder.getTokenType() != KW_ALIAS)
            return false;
        final Marker bookmark = builder.mark();
        builder.advanceLexer();
        if (builder.getTokenType() == ID) {
            if (!tokenCheck(ID)) {
                bookmark.rollbackTo();
                return false;
            }
            if (!tokenCheck(KW_THIS)) {
                bookmark.rollbackTo();
                return false;
            }
            expect(OP_SCOLON);
        } else if (builder.getTokenType() == KW_THIS) {
            builder.advanceLexer();
            expect(OP_EQ);
            expect(ID);
            expect(OP_SCOLON);
        } else {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        m.done(ALIAS_THIS_DECLARATION);
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
        final Marker m = builder.mark();
        expect(KW_ALIGN);
        if (currentIs(OP_PAR_LEFT)) {
            if (!tokenCheck(OP_PAR_LEFT)) {
                cleanup(m, ALIGN_ATTRIBUTE);
                return false;
            }
            if (parseAssignExpression() == null) {
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
    Marker parseAndAndExpression() {
        Marker m = parseOrExpression();
        if (m == null)
            return null;
        while (currentIs(OP_BOOL_AND)) {
            m = m.precede();
            builder.advanceLexer();
            if (parseOrExpression() == null) {
                cleanup(m, AND_AND_EXPRESSION);
                return null;
            }
            m.done(AND_AND_EXPRESSION);
        }
        return m;
    }

    /**
     * Parses an AndExpression.
     * <p>
     * $(GRAMMAR $(RULEDEF andExpression):
     *   $(RULE cmpExpression)
     * | $(RULE andExpression) $(LITERAL '&') $(RULE cmpExpression)
     * ;)
     */
    Marker parseAndExpression() {
        Marker m = parseCmpExpression();
        if (m == null)
            return null;
        while (currentIs(OP_AND)) {
            m = m.precede();
            builder.advanceLexer();
            if (parseCmpExpression() == null) {
                cleanup(m, AND_EXPRESSION);
                return null;
            }
            m.done(AND_EXPRESSION);
        }
        return m;
    }

    /**
     * Parses an ArgumentList.
     * <p>
     * $(GRAMMAR $(RULEDEF argumentList):
     * $(RULE assignExpression) ($(LITERAL ',') $(RULE assignExpression)?)*
     * ;)
     */
    private boolean parseArgumentList() {
        if (currentIs(OP_PAR_RIGHT))
            return true;
        Marker m = builder.mark();
        while (!currentIs(OP_PAR_RIGHT)) {
            if (parseAssignExpression() == null) {
                    cleanup(m, ARGUMENT_LIST);
                    return false;
            }
            if (currentIs(OP_PAR_RIGHT))
                break;
            if (!currentIs(OP_COMMA)) {
                break;
            }
            advance();
        }
        m.done(ARGUMENT_LIST);
        return true;
    }

    /**
     * Parses Arguments.
     * <p>
     * $(GRAMMAR $(RULEDEF arguments):
     * $(LITERAL '$(LPAREN)') $(RULE argumentList)? $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseArguments() {
        final Marker m = builder.mark();
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, ARGUMENTS);
            return false;
        }
        if (!currentIs(OP_PAR_RIGHT))
            if (!parseArgumentList()) {
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
        final Marker arrayInit = builder.mark();
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
    Marker parseArrayLiteral() {
        final Marker m = builder.mark();
        final IElementType open = expect(OP_BRACKET_LEFT);
        if (open == null) {
            cleanup(m, ARRAY_LITERAL);
            return null;
        }
        if (!currentIs(OP_BRACKET_RIGHT)) {
            if (!parseArrayInitializer()) {
                cleanup(m, ARRAY_LITERAL);
                return null;
            }
        }
        final IElementType close = expect(OP_BRACKET_RIGHT);
        if (close == null) {
            cleanup(m, ARRAY_INITIALIZER);
            return null;
        }
        exit_section_modified(builder, m, ARRAY_LITERAL, true);
        return m;
    }

    /**
     * Parses an ArrayMemberInitialization.
     * <p>
     * $(GRAMMAR $(RULEDEF arrayMemberInitialization):
     * ($(RULE assignExpression) $(LITERAL ':'))? $(RULE nonVoidInitializer)
     * ;)
     */
    boolean parseArrayMemberInitialization() {
        final Marker m = builder.mark();
        if (currentIs(OP_BRACKET_LEFT)) {
            Marker bookmark = builder.mark();
            skipBrackets();
            if (currentIs(OP_COLON)) {
                bookmark.rollbackTo();
                if (parseAssignExpression() == null) {
                    cleanup(m, ARRAY_MEMBER_INITIALIZATION);
                    return false;
                }
                advance(); // :
                if (!parseNonVoidInitializer()) {
                    cleanup(m, ARRAY_MEMBER_INITIALIZATION);
                    return false;
                }
                exit_section_modified(builder, m, ARRAY_MEMBER_INITIALIZATION, true);
                return true;
            } else {
                bookmark.rollbackTo();
            }
        }
        if (currentIs(OP_BRACES_LEFT)) {
            if (!parseNonVoidInitializer()) {
                cleanup(m, ARRAY_MEMBER_INITIALIZATION);
                return false;
            }
        } else {
            final boolean assignExpression = parseAssignExpression() != null;
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
        }
        exit_section_modified(builder, m, ARRAY_MEMBER_INITIALIZATION, true);
        return true;

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
        final Marker m = builder.mark();
        final boolean node;
        node = parseAsmMulExp();
        if (!node) {
            m.drop();
            return false;
        }
        while (currentIsOneOf(OP_PLUS, OP_MINUS)) {
            advance();
            if (!parseAsmMulExp()) {
                cleanup(m, ASM_ADD_EXP);
                return false;
            }
        }
        exit_section_modified(builder, m, ASM_ADD_EXP, true);
        return true;
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
        final Marker m = builder.mark();
        final boolean node;
        node = parseAsmEqualExp();
        if (!node) {
            m.drop();
            return false;
        }
        while (currentIs(OP_AND)) {
            advance();
            if (!parseAsmEqualExp()) {
                cleanup(m, ASM_AND_EXP);
                return false;
            }
        }
        exit_section_modified(builder, m, ASM_AND_EXP, true);
        return true;
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
        final Marker m = builder.mark();
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
        final Marker m = builder.mark();
        final boolean node;
        node = parseAsmRelExp();
        if (!node) {
            m.drop();
            return false;
        }
        while (currentIsOneOf(OP_EQ_EQ, OP_NOT_EQ)) {
            advance();
            if (!parseAsmRelExp()) {
                cleanup(m, ASM_EQUAL_EXP);
                return false;
            }
        }
        exit_section_modified(builder, m, ASM_EQUAL_EXP, true);
        return true;
    }

    /**
     * Parses an AsmExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmExp):
     * $(RULE asmLogOrExp) ($(LITERAL '?') $(RULE asmExp) $(LITERAL ':') $(RULE asmExp))?
     * ;)
     */
    boolean parseAsmExp() {
        final Marker m = builder.mark();
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
        final Marker m = builder.mark();
        if (currentIs(OP_SCOLON)) {
            builder.advanceLexer();
            m.done(ASM_INSTRUCTION);
            return true;
        }
        if (currentIs(KW_ALIGN)) {
            advance(); // align
            if (currentIsOneOf(INTEGER_LITERAL, ID)) {
                if (!currentIs(OP_SCOLON)) {
                    builder.error("`;` expected.");
                    m.done(ASM_INSTRUCTION);
                    return false;
                }
                builder.advanceLexer();
            } else {
                error("Identifier or integer literal expected.");
                m.done(ASM_INSTRUCTION);
                return false;
            }
        } else if (currentIsOneOf(ID, KW_IN, KW_OUT, KW_INT)) {
            final IElementType t = advance();
            if (t == ID && currentIs(OP_COLON)) {
                advance(); // :
                if (currentIs(OP_SCOLON)) {
                    builder.advanceLexer();
                    m.done(ASM_INSTRUCTION);
                    return true;
                }
                if (!parseAsmInstruction()) {
                    m.done(ASM_INSTRUCTION);
                    return false;
                }
            } else if (!currentIs(OP_SCOLON)) {
                if (!parseOperands()) {
                    m.done(ASM_INSTRUCTION);
                    return false;
                }
            }
            builder.advanceLexer();
        } else {
            m.rollbackTo();
            return false;
        }
        m.done(ASM_INSTRUCTION);
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
        final Marker m = builder.mark();
        final boolean node;
        node = parseAsmOrExp();
        if (!node) {
            m.drop();
            return false;
        }
        while (currentIs(OP_BOOL_AND)) {
            advance();
            if (!parseAsmOrExp()) {
                cleanup(m, ASM_LOG_AND_EXP);
                return false;
            }
        }
        exit_section_modified(builder, m, ASM_LOG_AND_EXP, true);
        return true;
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
        final Marker m = builder.mark();
        final boolean node;
        node = parseAsmLogAndExp();
        if (!node) {
            m.drop();
            return false;
        }
        while (currentIs(OP_BOOL_OR)) {
            advance();
            if (!parseAsmLogAndExp()) {
                cleanup(m, ASM_LOG_OR_EXP);
                return false;
            }
        }
        exit_section_modified(builder, m, ASM_LOG_OR_EXP, true);
        return true;
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
        final Marker m = builder.mark();
        final boolean node;
        node = parseAsmBrExp();
        if (!node) {
            m.drop();
            return false;
        }
        while (currentIsOneOf(OP_ASTERISK, OP_DIV, OP_MOD)) {
            advance();
            if (!parseAsmBrExp()) {
                cleanup(m, ASM_MUL_EXP);
                return false;
            }
        }
        exit_section_modified(builder, m, ASM_MUL_EXP, true);
        return true;
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
        final Marker m = builder.mark();
        final boolean node;
        node = parseAsmXorExp();
        if (!node) {
            m.drop();
            return false;
        }
        while (currentIs(OP_OR)) {
            advance();
            if (!parseAsmXorExp()) {
                cleanup(m, ASM_OR_EXP);
                return false;
            }
        }
        exit_section_modified(builder, m, ASM_OR_EXP, true);
        return true;
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
        final Marker m = builder.mark();
        final IElementType i = current();
        if (i == FLOAT_LITERAL || i == INTEGER_LITERAL || i == DOUBLE_QUOTED_STRING || i == OP_DOLLAR || i == KW_THIS) {
            advance();
        } else if (i == ID) {
            if (REGISTER_NAMES.contains(builder.getTokenText())) {
                if (!parseRegister()) {
                    cleanup(m, ASM_PRIMARY_EXP);
                    return false;
                }
                if (current() == OP_COLON) {
                    advance();
                    if (!parseAsmExp()) {
                        cleanup(m, ASM_PRIMARY_EXP);
                        return false;
                    }
                }
            } else {
                if (parseIdentifierChain() == null) {
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
        final Marker m = builder.mark();
        final boolean node;
        node = parseAsmShiftExp();
        if (!node) {
            m.drop();
            return false;
        }
        while (currentIsOneOf(OP_LESS, OP_LESS_EQ, OP_GT, OP_GT_EQ)) {
            advance();
            if (!parseAsmShiftExp()) {
                cleanup(m, ASM_REL_EXP);
                return false;
            }
        }
        exit_section_modified(builder, m, ASM_REL_EXP, true);
        return true;
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
        final Marker m = builder.mark();
        final boolean node;
        node = parseAsmAddExp();
        if (!node) {
            m.drop();
            return false;
        }
        while (currentIsOneOf(OP_SH_LEFT, OP_SH_RIGHT, OP_USH_RIGHT)) {
            advance();
            if (!parseAsmAddExp()) {
                cleanup(m, ASM_SHIFT_EXP);
                return false;
            }
        }
        exit_section_modified(builder, m, ASM_SHIFT_EXP, true);
        return true;
    }

    /**
     * Parses an AsmStatement
     * <p>
     * $(GRAMMAR $(RULEDEF asmStatement):
     * $(LITERAL 'asm') $(RULE functionAttributes)? $(LITERAL '{') $(RULE asmInstruction)+ $(LITERAL '}')
     * ;)
     */
    boolean parseAsmStatement() {
        final Marker m = builder.mark();
        advance(); // asm
        while (isAttribute()) {
            if (!parseFunctionAttribute()) {
                error("Function attribute or '{' expected");
                cleanup(m, ASM_STATEMENT);
                return false;
            }
        }
        expect(OP_BRACES_LEFT);
        while (moreTokens() && !currentIs(OP_BRACES_RIGHT)) {
            if (!parseAsmInstruction()) {
                // TODO: here libdparse handle gcc asm instructions
                while(builder.getTokenType() != OP_BRACES_RIGHT) {
                    builder.advanceLexer();
                }
                break;
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
        final Marker m = builder.mark();
        final IElementType i = current();
        if (i == ID || i == KW_BYTE || i == KW_SHORT || i == KW_INT || i == KW_FLOAT || i == KW_DOUBLE || i == KW_REAL) {
            final String tokenText = builder.getTokenText();
            final IElementType t = advance();
            if (t == ID)
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
        final Marker m = builder.mark();
        final IElementType i = current();
        if (i == OP_PLUS || i == OP_MINUS || i == OP_NOT || i == OP_TILDA) {
            advance();
            if (!parseAsmUnaExp()) {
                cleanup(m, ASM_UNA_EXP);
                return false;
            }
        } else if (i == KW_BYTE || i == KW_SHORT || i == KW_INT || i == KW_FLOAT || i == KW_DOUBLE || i == KW_REAL) {
            if (!typePrefix(m)) return false;
        } else if (i == ID) {
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
        final Marker m = builder.mark();
        final boolean node;
        node = parseAsmAndExp();
        if (!node) {
            m.drop();
            return false;
        }
        while (currentIs(OP_XOR)) {
            advance();
            if (!parseAsmAndExp()) {
                cleanup(m, ASM_XOR_EXP);
                return false;
            }
        }
        exit_section_modified(builder, m, ASM_XOR_EXP, true);
        return true;
    }

    /**
     * Parses an AssertArguments
     *
     * $(GRAMMAR $(RULEDEF assertArguments):
     *     $(RULE assignExpression) ($(LITERAL ',') ($(RULE assignExpression))* $(LITERAL ',')?
     *     ;)
     */
    public boolean parseAssertArguments() {
        final Marker m = builder.mark();
        if (parseAssignExpression() == null) {
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
            if (parseAssignExpression() == null) {
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
    Marker parseAssertExpression() {
        final Marker m = builder.mark();
        advance(); // "assert"
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, ASSERT_EXPRESSION);
            return null;
        }
        if (!parseAssertArguments()) {
            cleanup(m, ASSERT_EXPRESSION);
            return null;
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, ASSERT_EXPRESSION);
            return null;
        }
        exit_section_modified(builder, m, ASSERT_EXPRESSION, true);
        return m;
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
    Marker parseAssignExpression() {
        Marker ternary = parseTernaryExpression();
        if (ternary == null) {
            return null;
        }
        if (currentIsOneOf(OP_EQ, OP_USH_RIGHT_EQ, OP_SH_RIGHT_EQ, OP_SH_LEFT_EQ, OP_PLUS_EQ, OP_MINUS_EQ, OP_MUL_EQ, OP_MOD_EQ, OP_AND_EQ, OP_DIV_EQ, OP_OR_EQ, OP_POW_EQ, OP_XOR_EQ, OP_TILDA_EQ)) {
            Marker m = ternary.precede();
            advance();
            if (parseAssignExpression() == null) {
                cleanup(m, ASSIGN_EXPRESSION);
                return m;
            }
            exit_section_modified(builder, m, ASSIGN_EXPRESSION, true);
            return m;
        }
        return ternary;
    }

    /**
     * Parses an AssocArrayLiteral
     * <p>
     * $(GRAMMAR $(RULEDEF assocArrayLiteral):
     * $(LITERAL '[') $(RULE keyValuePairs) $(LITERAL ']')
     * ;)
     */
    Marker parseAssocArrayLiteral() {
        final Marker m = builder.mark();
        if (expect(OP_BRACKET_LEFT) == null) {
            cleanup(m, ASSOC_ARRAY_LITERAL);
            return null;
        }
        if(!parseKeyValuePairs()) {
            cleanup(m, ASSOC_ARRAY_LITERAL);
            return null;
        }
        if (expect(OP_BRACKET_RIGHT) == null) {
            cleanup(m, ASSOC_ARRAY_LITERAL);
            return null;
        }
        exit_section_modified(builder, m, ASSOC_ARRAY_LITERAL, true);
        return m;
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
        final Marker m = builder.mark();
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
        if (i == ID) {
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
                    if (!parseArgumentList()) {
                        cleanup(m, AT_ATTRIBUTE);
                        return false;
                    }
                }
                expect(OP_PAR_RIGHT);
            }
        } else if (i == OP_PAR_LEFT) {
            advance();
            if (!parseArgumentList()) {
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
        final Marker m = builder.mark();
        final IElementType i = current();
        if (i == KW_PRAGMA) {
            if (!parsePragmaExpression()) {
                cleanup(m, ATTRIBUTE);
                return false;
            }
        } else if (i == KW_DEPRECATED) {
            if (!parseDeprecated()) {
                cleanup(m, ATTRIBUTE);
                return false;
            }
        } else if (i == KW_ALIGN) {
            if (!parseAlignAttribute()) {
                cleanup(m, ATTRIBUTE);
                return false;
            }
        } else if (i == OP_AT) {
            if (!parseAtAttribute()) {
                cleanup(m, ATTRIBUTE);
                return false;
            }
        } else if (i == KW_PACKAGE) {
            advance();
            if (currentIs(OP_PAR_LEFT)) {
                expect(OP_PAR_LEFT);
                if (parseIdentifierChain() == null) {
                    cleanup(m, ATTRIBUTE);
                    return false;
                }
                expect(OP_PAR_RIGHT);
            }
        } else if (i == KW_EXTERN) {
            if (peekIs(OP_PAR_LEFT)) {
                if (!parseLinkageAttribute()) {
                    cleanup(m, ATTRIBUTE);
                    return false;
                }
            } else
                advance();
        } else if (i == KW_CONST || i == KW_IMMUTABLE || i == KW_INOUT || i == KW_SHARED) {
            if (peekIs(OP_PAR_LEFT)) {
                m.rollbackTo();
                return false;
            }
            builder.advanceLexer();
            if(builder.getTokenType() == ID) {
                m.rollbackTo();
                return false;
            }
        } else if (i == KW_PRIVATE || i == KW_PROTECTED || i == KW_PUBLIC || i == KW_EXPORT || i == KW_STATIC || i == KW_ABSTRACT || i == KW_FINAL || i == KW_OVERRIDE || i == KW_SYNCHRONIZED || i == KW_AUTO || i == KW_SCOPE || i == KW___GSHARED || i == KW_NOTHROW || i == KW_PURE || i == KW_REF || i == KW_THROW) {
            boolean isStorageClass = isStorageClass();
            advance();
            if(isStorageClass && builder.getTokenType() == ID) {
                m.rollbackTo();
                return false;
            }
        } else {
            m.drop();
            return false;
        }
        exit_section_modified(builder, m, ATTRIBUTE, true);
        return true;
    }

    void parseAutoAssignments() {
        do {
            if (!parseAutoDeclarationPart()) {
                return;
            }
            if (currentIs(OP_COMMA))
                advance();
            else
                break;
        } while (moreTokens());
    }

    /**
     * Parses an AutoDeclarationPart
     * <p>
     * $(GRAMMAR $(RULEDEF autoDeclarationPart):
     * $(LITERAL Identifier) $(RULE templateParameters)? $(LITERAL '=') $(RULE initializer)
     * ;)
     */
    boolean parseAutoDeclarationPart() {
        final Marker m = builder.mark();
        final IElementType i = expect(ID);
        if (i == null) {
            cleanup(m, AUTO_ASSIGNMENT);
            return false;
        }
        if (currentIs(OP_PAR_LEFT)) {
            if (!parseTemplateParameters()) {
                cleanup(m, AUTO_ASSIGNMENT);
                return false;
            }
        }
        if (!tokenCheck(OP_EQ)) {
            cleanup(m, AUTO_ASSIGNMENT);
            return false;
        }
        if (!parseInitializer()) {
            cleanup(m, AUTO_ASSIGNMENT);
            return false;
        }
        exit_section_modified(builder, m, AUTO_ASSIGNMENT, true);
        return true;
    }

    /**
     * Parses a BlockStatement
     * <p>
     * $(GRAMMAR $(RULEDEF blockStatement):
     * $(LITERAL '{') $(RULE statement)* $(LITERAL '}')
     * ;)
     */
    boolean parseBlockStatement() {
        final Marker m = builder.mark();
        final IElementType openBrace = expect(OP_BRACES_LEFT);
        if (openBrace == null) {
            m.done(BLOCK_STATEMENT);
            return false;
        }
        while (!builder.eof() && builder.getTokenType() != OP_BRACES_RIGHT) {
            if (!parseStatement()) {
                Marker recovery = builder.mark();
                int parentLevel = 0;
                while (!builder.eof()) {
                    if (builder.getTokenType() == OP_BRACES_RIGHT) {
                        parentLevel--;
                        if (parentLevel < 0) {
                            break;
                        }
                    } else if (builder.getTokenType() == OP_BRACES_LEFT) {
                        parentLevel++;
                    } else if (builder.getTokenType() == OP_SCOLON) {
                        if (parentLevel <= 0) {
                            break;
                        }
                    }
                    builder.advanceLexer();
                }
                if (builder.getTokenType() == OP_SCOLON)
                    builder.advanceLexer();
                recovery.error("Unable to parse this statement");
            }
        }
        expect(OP_BRACES_RIGHT);
        m.done(BLOCK_STATEMENT);
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
        final Marker m = builder.mark();
        expect(KW_BREAK);
        if (!moreTokens()) {
            cleanup(m, BREAK_STATEMENT);
            return false;
        }
        final IElementType i = current();
        if (i == ID) {
            advance();
            if (!tokenCheck(OP_SCOLON)) {
                cleanup(m, BREAK_STATEMENT);
                return false;
            }
        } else if (i == OP_SCOLON) {
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
        final Marker m = builder.mark();
        if (!moreTokens()) {
            cleanup(m, BASE_CLASS);
            return false;
        }
        if (isProtection(current())) {
            advance();
        }
        if (!parseBasicType()) {
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
        final Marker m = builder.mark();
        while (moreTokens()) {
            if (!parseBaseClass()) {
                cleanup(m, BASE_CLASS_LIST);
                return false;
            }
            if (currentIs(OP_COMMA)) {
                advance();
            } else
                break;
        }
        exit_section_modified(builder, m, BASE_CLASS_LIST, true);
        return true;
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
        final Marker marker = builder.mark();
        final IElementType type = advance();
        marker.done(BUILTIN_TYPE);
        return type;
    }

    /**
     * Parses a CaseRangeStatement
     * <p>
     * $(GRAMMAR $(RULEDEF caseRangeStatement):
     * $(LITERAL 'case') $(RULE assignExpression) $(LITERAL ':') $(LITERAL '...') $(LITERAL 'case') $(RULE assignExpression) $(LITERAL ':') $(RULE scopeStatementList)?
     * ;)
     */
    boolean parseCaseRangeStatement(Marker m) {
        if (!tokenCheck(OP_COLON)) {
            cleanup(m, CASE_RANGE_STATEMENT);
            return false;
        }
        if (!tokenCheck(OP_DDOT)) {
            cleanup(m, CASE_RANGE_STATEMENT);
            return false;
        }
        expect(KW_CASE);
        if (parseAssignExpression() == null) {
            cleanup(m, CASE_RANGE_STATEMENT);
            return false;
        }
        final IElementType colon = expect(OP_COLON);
        if (colon == null) {
            cleanup(m, CASE_RANGE_STATEMENT);
            return false;
        }
        if (!parseScopeStatementList()) {
            cleanup(m, CASE_RANGE_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, CASE_RANGE_STATEMENT, true);
        return true;
    }

    boolean parseCaseRangeStatement() {
        Marker m = builder.mark();
        return parseCaseRangeStatement(m);
    }

    /**
     * Parses an CaseStatement
     * <p>
     * $(GRAMMAR $(RULEDEF caseStatement):
     * $(LITERAL 'case') $(RULE argumentList) $(LITERAL ':') $(RULE scopeArgumentList)?
     * ;)
     */
    boolean parseCaseStatement(Marker m) {
        final IElementType colon = expect(OP_COLON);
        if (colon == null) {
            cleanup(m, CASE_STATEMENT);
            return false;
        }
        if (!parseScopeStatementList()) {
            cleanup(m, CASE_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, CASE_STATEMENT, true);
        return true;
    }

    /**
     * Parses a CastExpression
     * <p>
     * $(GRAMMAR $(RULEDEF castExpression):
     * $(LITERAL 'cast') $(LITERAL '$(LPAREN)') ($(RULE type) | $(RULE castQualifier))? $(LITERAL '$(RPAREN)') $(RULE unaryExpression)
     * ;)
     */
    Marker parseCastExpression() {
        final Marker m = builder.mark();
        expect(KW_CAST);
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, CAST_EXPRESSION);
            return null;
        }
        if (!currentIs(OP_PAR_RIGHT)) {
            if (isCastQualifier()) {
                if (!parseCastQualifier()) {
                    cleanup(m, CAST_EXPRESSION);
                    return null;
                }
            } else {
                if (!parseType()) {
                    cleanup(m, CAST_EXPRESSION);
                    return null;
                }
            }
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, CAST_EXPRESSION);
            return null;
        }
        if (parseUnaryExpression() == null) {
            cleanup(m, CAST_EXPRESSION);
            return null;
        }
        exit_section_modified(builder, m, CAST_EXPRESSION, true);
        return m;
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
        final Marker marker = builder.mark();
        final IElementType i = current();
        if (i == KW_INOUT || i == KW_CONST) {
            advance();
            if (currentIs(KW_SHARED))
                advance();
        } else if (i == KW_SHARED) {
            advance();
            if (currentIsOneOf(KW_CONST, KW_INOUT))
                advance();
        } else if (i == KW_IMMUTABLE) {
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
        final Marker m = builder.mark();
        expect(KW_CATCH);
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, CATCH);
            return false;
        }
        if (!parseType()) {
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
        if (!parseNoScopeNonEmptyStatement()) {
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
        final Marker m = builder.mark();
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
    boolean parseClassDeclaration(Marker m) {
        if (builder.getTokenType() != KW_CLASS)
            return false;
        builder.advanceLexer();
        parseInterfaceOrClass();
        m.done(CLASS_DECLARATION);
        return true;
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
    Marker parseCmpExpression() {
        final Marker shift = parseShiftExpression();
        if (shift == null) {
            return null;
        }
        final IElementType i = current();
        if (i == KW_IS) {
            return parseIdentityExpression(shift.precede());
        } else if (i == KW_IN) {
            return parseInExpression(shift.precede());
        } else if (i == OP_NOT) {
            if (peekIs(KW_IS)) {
                return parseIdentityExpression(shift.precede());
            } else if (peekIs(KW_IN))
                return parseInExpression(shift.precede());
        } else if (i == OP_LESS || i == OP_LESS_EQ || i == OP_GT || i == OP_GT_EQ || i == OP_NOT_GR || i == OP_NOT_GR_EQ || i == OP_NOT_LESS || i == OP_NOT_LESS_EQ) {
            return parseRelExpression(shift.precede());
        } else if (i == OP_EQ_EQ || i == OP_NOT_EQ) {
            return parseEqualExpression(shift.precede());
        }
        return shift;
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
        final Marker m = builder.mark();
        final IElementType i = current();
        if (i == KW_VERSION) {
            if (!parseVersionCondition()) {
                cleanup(m, COMPILE_CONDITION);
                return false;
            }
        } else if (i == KW_DEBUG) {
            if (!parseDebugCondition()) {
                cleanup(m, COMPILE_CONDITION);
                return false;
            }
        } else if (i == KW_STATIC) {
            if (!parseStaticIfCondition()) {
                cleanup(m, COMPILE_CONDITION);
                return false;
            }
        } else {
            m.drop();
            return false;
        }
        exit_section_modified(builder, m, COMPILE_CONDITION, true);
        return true;
    }

    void parseDeclDefsWithRecoveryUpToParentScope() {
        // Assume that the open brace is already passed
        while (!builder.eof() && builder.getTokenType() != OP_BRACES_RIGHT) {
            parseDeclDefWithRecovery();
        }
    }

    void parseDeclDefWithRecovery() {
        Marker bookmark = builder.mark();
        if (!parseDeclDef()) {
            bookmark.rollbackTo();
            Marker recovery = builder.mark();
            int braces_level = 0;
            while(!builder.eof()) {
                if (builder.getTokenType() == OP_BRACES_RIGHT) {
                    braces_level--;
                    if (braces_level < 0)
                        break;
                } else if (builder.getTokenType() == OP_BRACES_LEFT) {
                    braces_level++;
                }
                if (builder.getTokenType() == OP_SCOLON) {
                    if (braces_level <= 0)
                        break;
                }
                builder.advanceLexer();
            }
            if (builder.getTokenType() == OP_SCOLON)
                builder.advanceLexer();
            recovery.error("Unable to parse this declaration");
        } else {
            bookmark.drop();
        }
    }

    /**
     * Parses a ConditionalDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF conditionalDeclaration):
     *   $(RULE compileCondition) $(RULE declarationBlock)
     * | $(RULE compileCondition) $(RULE declarationBlock) $(LITERAL 'else') $(RULE declarationBlock)
     * | $(RULE compileCondition) $(RULE declarationBlock) $(LITERAL 'else') $(LITERAL ':') $(RULE declDefs)*
     * | $(RULE compileCondition) $(LITERAL ':') $(RULE declDefs)+
     * ;)
     */
    boolean parseConditionalDeclaration(Marker m) {
        final Marker bookmark = builder.mark();
        if (!parseCompileCondition()) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        if (currentIs(OP_COLON)) {
            builder.advanceLexer();
            parseDeclDefsWithRecoveryUpToParentScope();
            m.done(CONDITIONAL_DECLARATION);
            return true;
        }
        if (!parseDeclarationBlock()) {
            m.done(CONDITIONAL_DECLARATION);
            return true;
        }

        if (builder.getTokenType() != KW_ELSE) {
            m.done(CONDITIONAL_DECLARATION);
            return true;
        }
        builder.advanceLexer();

        if (builder.getTokenType() == OP_COLON) {
            builder.advanceLexer();
            parseDeclDefsWithRecoveryUpToParentScope();
        } else {
            parseDeclarationBlock();
        }
        m.done(CONDITIONAL_DECLARATION);
        return true;
    }

    /**
     * Parses a ConditionalStatement
     * <p>
     * $(GRAMMAR $(RULEDEF conditionalStatement):
     * $(RULE compileCondition) $(RULE noScopeNonEmptyStatement) ($(LITERAL 'else') $(RULE noScopeNonEmptyStatement))?
     * ;)
     */
    boolean parseConditionalStatement() {
        final Marker m = builder.mark();
        if (!parseCompileCondition()) {
            cleanup(m, CONDITIONAL_STATEMENT);
            return false;
        }
        if (!parseNoScopeNonEmptyStatement()) {
            cleanup(m, CONDITIONAL_STATEMENT);
            return false;
        }
        if (currentIs(KW_ELSE)) {
            advance();
            if (!parseNoScopeNonEmptyStatement()) {
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
        final Marker m = builder.mark();
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
    boolean parseConstructor(Marker m) {
        if (builder.getTokenType() != KW_THIS)
            return false;
        builder.advanceLexer();
        final IElementType p = peekPastParens();
        boolean isTemplate = false;
        if (p == OP_PAR_LEFT) {
            isTemplate = true;
            if (!parseTemplateParameters()) {
                m.done(CONSTRUCTOR);
                return true;
            }
        }
        if (!parseParameters()) {
            m.done(CONSTRUCTOR);
            return true;
        }
        while (moreTokens() && currentIsMemberFunctionAttribute())
            if (!parseMemberFunctionAttribute()) {
                m.done(CONSTRUCTOR);
                return true;
            }
        if (isTemplate && currentIs(KW_IF))
            if (!parseConstraint()) {
                m.done(CONSTRUCTOR);
                return true;
            }
        if (currentIs(OP_SCOLON))
            advance();
        else if (!parseFunctionBody()) {
            m.done(CONSTRUCTOR);
            return true;
        }
        m.done(CONSTRUCTOR);
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
        final Marker m = builder.mark();
        if (!tokenCheck(KW_CONTINUE)) {
            cleanup(m, CONTINUE_STATEMENT);
            return false;
        }
        if (!moreTokens()) {
            cleanup(m, CONTINUE_STATEMENT);
            return false;
        }
        final IElementType i = current();
        if (i == ID) {
            advance();
            if (!tokenCheck(OP_SCOLON)) {
                cleanup(m, CONTINUE_STATEMENT);
                return false;
            }
        } else if (i == OP_SCOLON) {
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
        final Marker m = builder.mark();
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
    boolean parseDebugSpecification(Marker m) {
        if (builder.getTokenType() != KW_DEBUG)
            return false;
        final Marker bookmark = builder.mark();
        builder.advanceLexer();
        if (!tokenCheck(OP_EQ)) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        if (currentIsOneOf(ID, INTEGER_LITERAL)) { // Note: that using integer for version is deprecated since 2.101.0
            advance();
        } else {
            error("Integer literal or identifier expected");
            m.done(DEBUG_SPECIFICATION);
            return true;
        }
        tokenCheck(OP_SCOLON);
        m.done(DEBUG_SPECIFICATION);
        return true;
    }

    /**
     * Parses a DeclDef
     * $(GRAMMAR $(RULEDEF declDef):
     * | $(RULE attributeSpecifier)
     * | $(RULE declaration)
     * | $(RULE constructor)
     * | $(RULE destructor)
     * | $(RULE postblit)
     * | $(RULE invariant)
     * | $(RULE unittest)
     * | $(RULE aliasThisDeclaration)
     * | $(RULE staticConstructor)
     * | $(RULE staticDestructor)
     * | $(RULE sharedStaticConstructor)
     * | $(RULE sharedStaticDestructor)
     * | $(RULE conditionalDeclaration)
     * | $(RULE debugSpecification)
     * | $(RULE versionSpecification)
     * | $(RULE mixinDeclaration)
     * | $(RULE emptyDeclaration)
     * ;)
     */
    boolean parseDeclDef() {
        Marker m = builder.mark();
        boolean result = parseDeclDef(m);
        if (!result)
            m.drop();
        return result;
    }

    boolean parseDeclDef(Marker m) {
        m.setCustomEdgeTokenBinders(LeadingDocCommentBinder.INSTANCE, TrailingDocCommentBinder.INSTANCE);
        boolean result = false;
        result = result || parseDestructor(m);
        result = result || parsePostblit(m);
        result = result || parseInvariant(m);
        result = result || parseUnittest(m);
        result = result || parseDebugSpecification(m);
        result = result || parseVersionSpecification(m);
        result = result || parseAliasThisDeclaration(m);
        result = result || parseConstructor(m);
        result = result || parseStaticConstructor(m);
        result = result || parseStaticDestructor(m);
        result = result || parseSharedStaticConstructor(m);
        result = result || parseSharedStaticDestructor(m);
        result = result || parseConditionalDeclaration(m);
        result = result || parseMixinDeclaration(m);
        result = result || parseDeclaration(m);
        result = result || parseAttributeSpecifier(m);
        result = result || parseEmptyDeclaration(m);
        return result;
    }

    boolean parseAttributeSpecifier(Marker marker) {
        Marker bookmark = builder.mark();
        boolean hasAttribute = false;
        while(!builder.eof()) {
            if (!parseAttribute()) {
                break;
            }
            hasAttribute = true;
        }
        if (!hasAttribute) {
            bookmark.rollbackTo();
            return false;
        }
        if (currentIs(OP_COLON)) {
            bookmark.drop();
            advance();
            marker.done(ATTRIBUTE_SPECIFIER);
            return true;
        }
        if (!parseDeclarationBlockWithAttribute(marker)) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        // Marker done by parseDeclarationBlock
        return true;
    }

    boolean parseDeclarationBlockWithAttribute(Marker marker) {
        // single statement
        if (!currentIs(OP_BRACES_LEFT)) {
            return parseDeclDef(marker);
        }
        // Multiple statements
        parseDeclarationBlockWithBlock();
        marker.done(ATTRIBUTE_SPECIFIER);
        return true;
    }

    boolean parseDeclarationBlock() {
        // single statement
        if (!currentIs(OP_BRACES_LEFT)) {
            return parseDeclDef();
        }
        // Multiple statements
        parseDeclarationBlockWithBlock();
        return true;
    }

    private void parseDeclarationBlockWithBlock() {
        assert builder.getTokenType() == OP_BRACES_LEFT;
        // Multiple statements
        Marker marker = builder.mark();
        builder.advanceLexer();
        parseDeclDefsWithRecoveryUpToParentScope();
        expect(OP_BRACES_RIGHT);
        marker.done(DECLARATION_BLOCK);
    }

    boolean parseEmptyDeclaration(Marker marker) {
        if (builder.getTokenType() != OP_SCOLON)
            return false;
        builder.advanceLexer();
        marker.done(EMPTY_DECLARATION);
        return true;
    }

    /**
     * Parses a Declaration
     * $(GRAMMAR $(RULEDEF declaration):
     *   $(RULE functionDeclaration)
     * | $(RULE variableDeclaration)
     * | $(RULE aliasDeclaration)
     * | $(RULE aliasAssign)
     * | $(RULE aggregateDeclaration)
     * | $(RULE enumDeclaration)
     * | $(RULE importDeclaration)
     * | $(RULE conditionalDeclaration)
     * | $(RULE staticForeachDeclaration)
     * | $(RULE staticAssertDeclaration)
     * | $(RULE templateDeclaration)
     * | $(RULE templateMixinDeclaration)
     * | $(RULE templateMixin)
     * ;)
     */
    boolean parseDeclaration(Marker m) {
        m.setCustomEdgeTokenBinders(LeadingDocCommentBinder.INSTANCE, TrailingDocCommentBinder.INSTANCE);
        boolean result = parseAliasDeclaration(m);
        result = result || parseAliasAssign(m);
        result = result || parseAggregateDeclaration(m);
        result = result || parseEnumDeclaration(m);
        result = result || parseImportDeclaration(m);
        result = result || parseConditionalDeclaration(m);
        result = result || parseStaticForeachDeclaration(m);
        result = result || parseStaticAssertDeclaration(m);
        result = result || parseTemplateDeclaration(m);
        result = result || parseTemplateMixinDeclaration(m);
        result = result || parseTemplateMixin(m);
        result = result || parseFunctionDeclaration(m);
        result = result || parseVariableDeclaration(m);
        return result;
    }

    boolean parseAggregateDeclaration(Marker m) {
        boolean result = parseClassDeclaration(m);
        result = result || parseInterfaceDeclaration(m);
        result = result || parseStructDeclaration(m);
        result = result || parseUnionDeclaration(m);
        return result;
    }

    private void exit_section_modified(final PsiBuilder builder, final Marker m, final IElementType type, final boolean b) {
        m.done(type);
    }

    /**
     * Parses an Identifier initializer
     * <p>
     * $(GRAMMAR $(RULEDEF declarator):
     *   $(LITERAL Identifier)
     * | $(LITERAL Identifier) $(LITERAL '=') $(RULE initializer)
     * | $(LITERAL Identifier) $(RULE templateParameters) $(LITERAL '=') $(RULE initializer)
     * ;)
     */
    boolean parseIdentifierInitializer() {
        if (builder.getTokenType() != ID) {
            return false;
        }
        final Marker m = builder.mark();
        advance();
        if (currentIs(OP_BRACKET_LEFT)) // dmd doesn't accept pointer after identifier
        {
            while (moreTokens() && currentIs(OP_BRACKET_LEFT))
                if (!parseTypeSuffix()) {
                    cleanup(m, IDENTIFIER_INITIALIZER);
                    return false;
                }
        }
        if (currentIs(OP_PAR_LEFT)) {
            if (!parseTemplateParameters()) {
                cleanup(m, IDENTIFIER_INITIALIZER);
                return false;
            }
            if (!tokenCheck(OP_EQ)) {
                cleanup(m, IDENTIFIER_INITIALIZER);
                return false;
            }
            if (!parseInitializer()) {
                cleanup(m, IDENTIFIER_INITIALIZER);
                return true;
            }
        } else if (currentIs(OP_EQ)) {
            advance();
            if (!parseInitializer()) {
                cleanup(m, IDENTIFIER_INITIALIZER);
                return true;
            }
        }
        exit_section_modified(builder, m, IDENTIFIER_INITIALIZER, true);
        return true;
    }

    /**
     * Parses a DeclaratorIdentifierList
     * <p>
     * $(GRAMMAR $(RULEDEF declaratorIdentifierList):
     *     $(LITERAL Identifier) ($(LITERAL ',') $(LITERAL Identifier))*
     *     ;)
     */
    boolean parseDeclaratorIdentifierList() {
        while (moreTokens()) {
            Marker m = builder.mark();
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
     * $(LITERAL 'default') $(LITERAL ':') $(RULE scopeStatementList)?
     * ;)
     */
    boolean parseDefaultStatement() {
        final Marker m = builder.mark();
        if (!tokenCheck(KW_DEFAULT)) {
            cleanup(m, DEFAULT_STATEMENT);
            return false;
        }
        final IElementType colon = expect(OP_COLON);
        if (colon == null) {
            cleanup(m, DEFAULT_STATEMENT);
            return false;
        }
        if (!parseScopeStatementList()) {
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
    Marker parseDeleteExpression() {
        final Marker m = builder.mark();
        if (!tokenCheck(KW_DELETE)) {
            cleanup(m, DELETE_EXPRESSION);
            return null;
        }
        if (parseUnaryExpression() == null) {
            cleanup(m, DELETE_EXPRESSION);
            return null;
        }
        exit_section_modified(builder, m, DELETE_EXPRESSION, true);
        return m;
    }

    /**
     * Parses a Deprecated attribute
     * <p>
     * $(GRAMMAR $(RULEDEF deprecated):
     * $(LITERAL 'deprecated') ($(LITERAL '$(LPAREN)') $(LITERAL StringLiteral)+ $(LITERAL '$(RPAREN)'))?
     * ;)
     */
    boolean parseDeprecated() {
        final Marker m = builder.mark();
        if (!tokenCheck(KW_DEPRECATED)) {
            cleanup(m, DEPRECATED);
            return false;
        }
        if (currentIs(OP_PAR_LEFT)) {
            advance();
            if (parseAssignExpression() == null) {
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
    boolean parseDestructor(Marker m) {
        if (!currentIs(OP_TILDA))
            return false;
        builder.advanceLexer();
        if (!moreTokens()) {
            error("`this` expected");
            m.done(DESTRUCTOR);
            return true;
        }
        if (!tokenCheck(KW_THIS)) {
            m.done(DESTRUCTOR);
            return true;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            m.done(DESTRUCTOR);
            return true;
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            m.done(DESTRUCTOR);
            return true;
        }
        if (currentIs(OP_SCOLON))
            advance();
        else {
            while (moreTokens() && currentIsMemberFunctionAttribute())
                if (!parseMemberFunctionAttribute()) {
                    m.done(DESTRUCTOR);
                    return true;
                }
            if (!parseFunctionBody()) {
                m.done(DESTRUCTOR);
                return true;
            }
        }
        m.done(DESTRUCTOR);
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
        final Marker m = builder.mark();
        if (!tokenCheck(KW_DO)) {
            cleanup(m, DO_STATEMENT);
            return false;
        }
        if (!moreTokens()) {
            cleanup(m, DO_STATEMENT);
            return false;
        }
        if (!parseNonEmptyStatementNoCaseNoDefault()) {
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
        if (builder.getTokenType() != OP_BRACES_LEFT) {
            return false;
        }
        final Marker m = builder.mark();
        builder.advanceLexer();
        while (moreTokens()) {
            if (currentIsOneOf(ID, OP_AT, KW_DEPRECATED)) {
                parseEnumMember();
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
        expect(OP_BRACES_RIGHT);
        exit_section_modified(builder, m, ENUM_BODY, true);
        return true;
    }

    /**
     * $(GRAMMAR $(RULEDEF enumMember):
     *   $(RULE enumMemberAttributes) $(RULE type)? $(LITERAL identifier) ($(LITERAL '=') $(RULE assignExpression))?
     * ;)
     */
    boolean parseEnumMember(final boolean typeAllowed) {
        final Marker m = builder.mark();
        // TODO parseEnumMemberAttributes
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
            if (!parseType()) {
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
        if (parseAssignExpression() == null) {
            cleanup(m, ENUM_MEMBER);
            return false;
        }
        return true;
    }

    boolean parseAnonymousEnumDeclaration() {
        boolean hasBaseType = false;
        if (currentIs(OP_COLON)) {
            advance();
            if (!parseType()) {
                return true;
            }
            hasBaseType = true;
        }
        if (builder.getTokenType() != OP_BRACES_LEFT) {
            return false;
        }
        builder.advanceLexer();
        while (moreTokens()) {
            if (currentIs(OP_COMMA)) {
                advance();
            } else if (currentIs(OP_BRACES_RIGHT)) {
                break;
            } else {
                parseEnumMember(!hasBaseType);
            }
        }
        if (!tokenCheck(OP_BRACES_RIGHT)) {
            return true;
        }
        return true;
    }

    /**
     * Parses an EnumDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF enumDeclaration):
     *   $(LITERAL 'enum') $(LITERAL Identifier) ($(LITERAL ':') $(RULE type)) $(LITERAL ';')
     * | $(LITERAL 'enum') $(LITERAL Identifier) ($(LITERAL ':') $(RULE type)) $(RULE enumBody)
     * $(GRAMMAR $(RULEDEF anonymousEnumDeclaration):
     * $(LITERAL 'enum') ($(LITERAL ':') $(RULE type))? $(LITERAL '{') $(RULE anonymousEnumMember)+ $(LITERAL '}')
     * ;)
     */
    boolean parseEnumDeclaration(Marker m) {
        if (builder.getTokenType() != KW_ENUM)
            return false;
        final Marker bookmark = builder.mark();
        builder.advanceLexer();
        if (builder.getTokenType() != ID) {
            if (!parseAnonymousEnumDeclaration()) {
                bookmark.rollbackTo();
                return false;
            }
            bookmark.drop();
            m.done(ANONYMOUS_ENUM_DECLARATION);
            return true;
        }
        advance();
        if (currentIs(OP_COLON)) {
            advance(); // skip ':'
            if (!parseType()) {
                bookmark.drop();
                m.done(ENUM_DECLARATION);
                return true;
            }
        }
        if (currentIs(OP_SCOLON)) {
            advance();
            bookmark.drop();
            m.done(ENUM_DECLARATION);
            return true;
        }
        if (!parseEnumBody()) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        m.done(ENUM_DECLARATION);
        return true;
    }

    /**
     * Parses an EnumMemberAttribute
     * <p>
     * $(GRAMMAR $(RULEDEF enumMemberAttribute):
     *       $(RULE atAttribute)
     *     | $(RULE deprecated)
     *     ;)
     */
    public boolean parseEnumMemberAttribute() {
        final Marker m = builder.mark();
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
        final Marker m = builder.mark();
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
            if (parseAssignExpression() == null) {
                cleanup(m, ENUM_MEMBER);
                return false;
            }
        }
        exit_section_modified(builder, m, ENUM_MEMBER, true);
        return true;
    }

    /**
     * Parses an EqualExpression
     * <p>
     * $(GRAMMAR $(RULEDEF equalExpression):
     * $(RULE shiftExpression) ($(LITERAL '==') | $(LITERAL '!=')) $(RULE shiftExpression)
     * ;)
     */
    Marker parseEqualExpression(Marker m) {
        if (m == null) {
            m = builder.mark();
            if (parseShiftExpression() == null) {
                cleanup(m, EQUAL_EXPRESSION);
                return null;
            }
        }
        if (currentIsOneOf(OP_EQ_EQ, OP_NOT_EQ)) {
            advance();
        }
        if (parseShiftExpression() == null) {
            cleanup(m, EQUAL_EXPRESSION);
            return null;
        }
        exit_section_modified(builder, m, EQUAL_EXPRESSION, true);
        return m;
    }

    /**
     * Parses an Expression
     * <p>
     * $(GRAMMAR $(RULEDEF expression):
     * $(RULE commaExpression)
     * ;)
     */
    boolean parseExpression() {
        return parseCommaExpression() != null;
    }

    /**
     * Parses an Expression
     * <p>
     * $(GRAMMAR $(RULEDEF expression):
     * $(RULE assignExpression)
     * | $(RULE commaExpression) ($(LITERAL ',') $(RULE assignExpression))
     * ;)
     */
    Marker parseCommaExpression() {
        Marker assign = parseAssignExpression();
        if (assign == null) {
            return null;
        }
        if (currentIs(OP_COMMA)) {
            Marker m = assign.precede();
            advance();
            if (!currentIsOneOf(OP_PAR_RIGHT, OP_BRACES_RIGHT, OP_BRACKET_RIGHT)) {
                if (parseCommaExpression() == null) {
                    cleanup(m, COMMA_EXPRESSION);
                    return m;
                }
            }
            m.done(COMMA_EXPRESSION);
            return m;
        }
        return assign;
    }

    /**
     * Parses an ExpressionStatement
     * <p>
     * $(GRAMMAR $(RULEDEF expressionStatement):
     * $(RULE expression) $(LITERAL ';')
     * ;)
     */
    boolean parseExpressionStatement() {
        final Marker m = builder.mark();
        final boolean b = parseExpression();
        if (!b) {
                m.rollbackTo();
                return false;
            }
        if (expect(OP_SCOLON) == null) {
            // To have enter key properly work in unfinished expression statement
            m.setCustomEdgeTokenBinders(null, TrailingSpaceBinder.INSTANCE);
        }
        m.done(EXPRESSION_STATEMENT);
        return true;
    }

    boolean parseDeclarationStatement() {
        Marker marker = builder.mark();
        // First try without storage class to have storage class attached to the declaration that can have some
        // It produces a nicer tree
        if (parseDeclaration(builder.mark())) {
            marker.done(DECLARATION_STATEMENT);
            return true;
        }
        // Declaration does not contains storage class
        marker.rollbackTo();
        marker = builder.mark();
        while (!builder.eof()) {
            if(!parseStorageClass())
                break;
        }
        if (!parseDeclaration(builder.mark())) {
            marker.rollbackTo();
            return false;
        }
        marker.done(DECLARATION_STATEMENT);
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
        final Marker m = builder.mark();
        if (expect(KW_FINAL) == null) {
            cleanup(m, FINAL_SWITCH_STATEMENT);
            return false;
        }
        if(!parseSwitchStatement()) {
            cleanup(m, FINAL_SWITCH_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, FINAL_SWITCH_STATEMENT, true);
        return true;
    }

    /**
     * Parses a Finally statement
     * <p>
     * $(GRAMMAR $(RULEDEF finallyStatement):
     * $(LITERAL 'finally') $(RULE noScopeNonEmptyStatement)
     * ;)
     */
    boolean parseFinally() {
        final Marker m = builder.mark();
        if (!tokenCheck(KW_FINALLY)) {
            cleanup(m, FINALLY);
            return false;
        }
        if (!parseNoScopeNonEmptyStatement()) {
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
     * $(LITERAL 'for') $(LITERAL '$(LPAREN)') ($LITERAL ';') | $(RULE noScopeNonEmptyStatement)) $(RULE expression)? $(LITERAL ';') $(RULE expression)? $(LITERAL '$(RPAREN)') $(RULE scopeStatement)
     * ;)
     */
    boolean parseForStatement() {
        final Marker m = builder.mark();
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
        else if (!parseNoScopeNonEmptyStatement()) {
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
        if (!parseScopeStatement()) {
            cleanup(m, FOR_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, FOR_STATEMENT, true);
        return true;
    }


    /**
     * Parses a StaticForeachDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF staticForeachDeclaration):
     *       $(LITERAL 'static') ($(LITERAL 'foreach') | $(LITERAL 'foreach_reverse')) $(LITERAL '$(LPAREN)') $(RULE foreachTypeList) $(LITERAL ';') $(RULE expression) $(LITERAL '$(RPAREN)') ($(RULE declaration) | $(LITERAL '{') $(RULE declaration)* $(LITERAL '}'))
     *     | $(LITERAL 'static') ($(LITERAL 'foreach') | $(LITERAL 'foreach_reverse')) $(LITERAL '$(LPAREN)') $(RULE foreachType) $(LITERAL ';') $(RULE expression) $(LITERAL '..') $(RULE expression) $(LITERAL '$(RPAREN)') ($(RULE declaration) | $(LITERAL '{') $(RULE declaration)* $(LITERAL '}'))
     *     ;)
     */
    boolean parseStaticForeachDeclaration(Marker m)
    {
        if (builder.getTokenType() != KW_STATIC)
            return false;
        final Marker bookmark = builder.mark();
        builder.advanceLexer();
        if (!currentIsOneOf(KW_FOREACH, KW_FOREACH_REVERSE)) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        parseForeach(STATIC_FOREACH_DECLARATION, true);
        exit_section_modified(builder, m, STATIC_FOREACH_DECLARATION, true);
        return true;
    }

    /**
     * Parses a ForeachStatement
     * <p>
     * $(GRAMMAR $(RULEDEF foreachStatement):
     * ($(LITERAL 'foreach') | $(LITERAL 'foreach_reverse')) $(LITERAL '$(LPAREN)') $(RULE foreachTypeList) $(LITERAL ';') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE NoScopeNonEmptyStatement)
     * | ($(LITERAL 'foreach') | $(LITERAL 'foreach_reverse')) $(LITERAL '$(LPAREN)') $(RULE foreachType) $(LITERAL ';') $(RULE expression) $(LITERAL '..') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE NoScopeNonEmptyStatement)
     * ;)
     */
    boolean parseForeachStatement() {
        return parseForeach(FOREACH_STATEMENT, false);
    }

    boolean parseStaticForeachStatement() {
        Marker m = builder.mark();
        if (expect(KW_STATIC) == null) {
            cleanup(m, ASSOC_ARRAY_LITERAL);
            return false;
        }
        if(!parseForeachStatement()) {
            cleanup(m, ASSOC_ARRAY_LITERAL);
            return false;
        }
        m.done(ASSOC_ARRAY_LITERAL);
        return true;
    }

    boolean parseForeach(IElementType elementType, boolean declOnly) {
        final Marker m = builder.mark();
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
        final int types = parseForeachTypeList();
        if (types < 0) {
            cleanup(m, elementType);
            return false;
        }
        final boolean canBeRange = types == 1;
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
            error("Statement expected");
            cleanup(m, elementType);
            return true;
        }
        if (declOnly) {
            if (currentIs(OP_BRACES_LEFT)) {
                advance();
                parseDeclDefsWithRecoveryUpToParentScope();
                if (!tokenCheck(OP_BRACES_RIGHT)) {
                    cleanup(m, elementType);
                    return false;
                }
            }
            else if (!parseDeclDef()) {
                cleanup(m, elementType);
                return false;
            }
        }
        else {
            if (!parseNoScopeNonEmptyStatement()) {
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
        final Marker m = builder.mark();
        while (moreTokens()) {
            if (currentIs(KW_SCOPE)) {
                advance();
            } else if (currentIs(KW_REF)) {
                advance();
            } else if (currentIs(KW_ALIAS)) {
                advance();
            } else if (currentIs(KW_ENUM)) {
                advance();
            } else if (!parseTypeConstructor()) {
                break;
            }
        }
        if (currentIs(ID) && peekIsOneOf(OP_COMMA, OP_SCOLON)) {
            advance();
            exit_section_modified(builder, m, FOREACH_TYPE, true);
            return true;
        }
        if (!parseType()) {
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
    int parseForeachTypeList() {
        final Marker marker = builder.mark();
        int count = 0;
        while (moreTokens()) {
            if (!parseForeachType()) {
                cleanup(marker, FOREACH_TYPE_LIST);
                return -1;
            }
            count++;
            if (currentIs(OP_COMMA)) {
                advance();
            } else
                break;
        }
        exit_section_modified(builder, marker, FOREACH_TYPE_LIST, true);
        return count;
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
    boolean parseFunctionAttribute() {
        final Marker m = builder.mark();
        final IElementType i = current();
        if (i == OP_AT) {
            if (!parseAtAttribute()) {
                cleanup(m, FUNCTION_ATTRIBUTE);
                return false;
            }
        } else if (i == KW_PURE || i == KW_NOTHROW) {
            advance();
        } else {
            error("@attribute, `pure`, or `nothrow` expected");
            exit_section_modified(builder, m, FUNCTION_ATTRIBUTE, true);
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
        Marker bookmark = builder.mark();
        if (parseMissingFunctionBody()) {
            bookmark.drop();
        } else {
            bookmark.rollbackTo();
            bookmark = builder.mark();
            if (parseShortenedFunctionBody()) {
                bookmark.drop();
            } else {
                bookmark.rollbackTo();
                return parseSpecifiedFunctionBody();
            }
        }
        return true;
    }

    boolean parseFunctionContract() {
        return parseFunctionContract(true);
    }

    /**
     * Parses a FunctionContract
     * <p>
     * $(GRAMMAR $(RULEDEF functionContract):
     *       $(RULE inOutContractExpression)
     *     | $(RULE inOutStatement)
     *     ;)
     */
    boolean parseFunctionContract(boolean allowStatement) {
        final Marker m = builder.mark();
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

    /**
     * Parses a FunctionDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF functionDeclaration):
     *   ($(RULE storageClass)+ | $(RULE _type)) $(LITERAL Identifier) $(RULE parameters) $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ';'))
     * | ($(RULE storageClass)+ | $(RULE _type)) $(LITERAL Identifier) $(RULE templateParameters) $(RULE parameters) $(RULE memberFunctionAttribute)* $(RULE constraint)? ($(RULE functionBody) | $(LITERAL ';'))
     * ;)
     */
    boolean parseFunctionDeclaration(Marker m) {
        Marker bookmark = builder.mark();
        boolean hasStorageClass = false;
        while (!builder.eof()) {
            if (!parseStorageClass())
                break;
            hasStorageClass = true;
        }
        if (hasStorageClass && currentIs(ID) && peekIs(OP_PAR_LEFT)) {
            // it’s an auto function declaration
            advance();
            if (!parseFunctionDeclaratorSuffix()) {
                bookmark.rollbackTo();
                return false;
            }
            if (!parseFunctionBody()) {
                bookmark.rollbackTo();
                return false;
            }
            bookmark.drop();
            m.done(FUNCTION_DECLARATION);
            return true;
        }
        if (!parseBasicType()) {
            bookmark.rollbackTo();
            return false;
        }
        if (!parseFunctionDeclarator()) {
            bookmark.rollbackTo();
            return false;
        }
        if (!parseFunctionBody()) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        m.done(FUNCTION_DECLARATION);
        return true;
    }

    boolean parseFunctionDeclarator() {
        parseTypeSuffixes();
        if (!tokenCheck(ID)) {
            return false;
        }
        return parseFunctionDeclaratorSuffix();
    }

    boolean parseFunctionDeclaratorSuffix() {
        if (!currentIs(OP_PAR_LEFT)) {
            error("`(` expected");
            return false;
        }
        final IElementType p = peekPastParens();
        final boolean isTemplate = p == OP_PAR_LEFT;
        if (isTemplate) {
            if (!parseTemplateParameters()) {
                return false;
            }
        }
        if (!parseParameters()) {
            return false;
        }
        while (moreTokens() && currentIsMemberFunctionAttribute())
            if (!parseMemberFunctionAttribute()) {
                return false;
            }
        if (isTemplate && currentIs(KW_IF)) {
            return parseConstraint();
        }
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
    Marker parseFunctionLiteralExpression() {
        final Marker m = builder.mark();
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
                if (!parseType()) {
                    cleanup(m, FUNCTION_LITERAL_EXPRESSION);
                    return null;
                }
        }
        if (startsWith(ID, OP_LAMBDA_ARROW)) {
            advance();
            advance(); // =>
            if (parseAssignExpression() == null) {
                cleanup(m, FUNCTION_LITERAL_EXPRESSION);
                return null;
            }
            exit_section_modified(builder, m, FUNCTION_LITERAL_EXPRESSION, true);
            return m;
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
                return null;
            }
            while (currentIsMemberFunctionAttribute()) {
                if (!parseMemberFunctionAttribute()) {
                    break;
                }
            }
        }
        if (currentIs(OP_LAMBDA_ARROW)) {
            advance();
            if (parseAssignExpression() == null) {
                cleanup(m, FUNCTION_LITERAL_EXPRESSION);
                return null;
            }
        } else if (!parseSpecifiedFunctionBody()) {
            cleanup(m, FUNCTION_LITERAL_EXPRESSION);
            return null;
        }
        exit_section_modified(builder, m, FUNCTION_LITERAL_EXPRESSION, true);
        return m;
    }

    /**
     * Parses a GotoStatement
     * <p>
     * $(GRAMMAR $(RULEDEF gotoStatement):
     * $(LITERAL 'goto') ($(LITERAL Identifier) | $(LITERAL 'default') | $(LITERAL 'case') $(RULE expression)?) $(LITERAL ';')
     * ;)
     */
    boolean parseGotoStatement() {
        final Marker m = builder.mark();
        if (!tokenCheck(KW_GOTO)) {
            cleanup(m, GOTO_STATEMENT);
            return false;
        }
        final IElementType i = current();
        if (i == ID || i == KW_DEFAULT) {
            advance();
        } else if (i == KW_CASE) {
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
    Marker parseIdentifierChain() {
        if (builder.getTokenType() != ID)
            return null;
        Marker m = builder.mark();
        builder.advanceLexer();
        m.done(IDENTIFIER_CHAIN);
        while (builder.getTokenType() == OP_DOT) {
            m = m.precede();
            builder.advanceLexer();
            expect(ID);
            m.done(IDENTIFIER_CHAIN);
        }
        return m;
    }

    /**
     * Parses a QualifiedIdentifier.
     * <p>
     * $(GRAMMAR $(RULEDEF qualifiedIdentifier):
     *       $(RULE identifierOrTemplateInstance)
     *     | $(RULE identifierOrTemplateInstance) $(LITERAL '.') $(RULE qualifiedIdentifier)
     *     | $(RULE identifier) $(LITERAL '[') $(RULE assignExpression) $(LITERAL ']')
     *     | $(RULE identifier) $(LITERAL '[') $(RULE assignExpression) $(LITERAL ']') $(LITERAL '.') $(RULE qualifiedIdentifier)
     *     ;)
     */
    public boolean parseQualifiedIdentifier() {
        if (builder.getTokenType() != ID) {
            builder.error("Identifier expected");
            return false;
        }
        Marker m = builder.mark();
        while(!builder.eof()) {
            Marker bookmark = builder.mark();
            if (parseTemplateInstance()) {
                bookmark.drop();
                if (currentIs(OP_DOT)) {
                    advance();
                    continue;
                }
                m.done(QUALIFIED_IDENTIFIER);
                return true;
            } else {
                bookmark.rollbackTo();
                advance(); // Identifier
                if (currentIs(OP_BRACKET_LEFT)) {
                    // dyn arrays -> type suffixes
                    if (peekIs(OP_BRACKET_RIGHT)) {
                        break;
                    }
                    bookmark = builder.mark();
                    advance();
                    // here we can have a type (AA key)
                    if (parseAssignExpression() == null) {
                        bookmark.rollbackTo();
                        break;
                    }
                    // indexer followed by ".." -> sliceExp -> type suffix
                    else if (currentIs(OP_DDOT)) {
                        bookmark.rollbackTo();
                        break;
                    }
                    // otherwise either the index of a type list or a dim
                    bookmark.drop();
                    expect(OP_BRACKET_RIGHT);
                }
            }
            if (builder.getTokenType() != OP_DOT) {
                break;
            }
            advance();
            m.done(QUALIFIED_IDENTIFIER);
            m = m.precede();
        }
        m.done(QUALIFIED_IDENTIFIER);
        return true;
    }


    /**
     * Parses an MixinQualifiedIdentifier
     * <p>
     * $(GRAMMAR $(RULEDEF MixinQualifiedIdentifier):
     * $(RULE identifier)
     * | $(RULE identifier) ($(LITERAL '.') $(RULE MixinQualifiedIdentifier)
     * | $(RULE TemplateInstance) ($(LITERAL '.') $(RULE MixinQualifiedIdentifier)
     * ;)
     */
    boolean parseMixinQualifiedIdentifier() {
        if(!currentIs(ID)) {
            builder.error("Identifier expected");
            return false;
        }
        Marker m = builder.mark();
        while (moreTokens()) {
            Marker bookmark = builder.mark();
            if (parseTemplateInstance()) {
                if (builder.getTokenType() != OP_DOT) {
                    // Template arguments not part of the name, just take the id
                    bookmark.rollbackTo();
                    assert builder.getTokenType() == ID;
                    advance();
                } else {
                    bookmark.drop();
                }
            } else {
                bookmark.rollbackTo();
                assert builder.getTokenType() == ID;
                advance();
            }
            if (builder.getTokenType() != OP_DOT) {
                break;
            }
            builder.advanceLexer();
            if (builder.getTokenType() != ID) {
                break;
            }
            m.done(MIXIN_QUALIFIED_IDENTIFIER);
            m = m.precede();
        }
        m.done(MIXIN_QUALIFIED_IDENTIFIER);
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
        if (peekIs(OP_NOT) && !startsWith(ID, OP_NOT, KW_IS) && !startsWith(ID, OP_NOT, KW_IN)) {
            return parseTemplateInstance();
        } else {
            final IElementType ident = expect(ID);
            return ident != null;
        }
    }

    /**
     * Parses an IdentityExpression
     * <p>
     * $(GRAMMAR $(RULEDEF identityExpression):
     * $(RULE shiftExpression) ($(LITERAL 'is') | ($(LITERAL '!') $(LITERAL 'is'))) $(RULE shiftExpression)
     * ;)
     */
    Marker parseIdentityExpression(Marker m)
    {
        if (m == null) {
            m = builder.mark();
            if (parseShiftExpression() == null) {
                cleanup(m, IDENTITY_EXPRESSION);
                return null;
            }
        }
        if (currentIs(OP_NOT)) {
            advance();
        }
        if (!tokenCheck(KW_IS)) {
            cleanup(m, IDENTITY_EXPRESSION);
            return null;
        }
        if (parseShiftExpression() == null) {
            cleanup(m, IDENTITY_EXPRESSION);
            return null;
        }
        exit_section_modified(builder, m, IDENTITY_EXPRESSION, true);
        return m;
    }

    /**
     * Parses an IfStatement
     * <p>
     * $(GRAMMAR $(RULEDEF ifStatement):
     * $(LITERAL 'if') $(LITERAL '$(LPAREN)') $(RULE ifCondition) $(LITERAL '$(RPAREN)') $(RULE ifStatement) ($(LITERAL 'else') $(RULE elseStatement))?
     * ;)
     */
    boolean parseIfStatement() {
        final Marker m = builder.mark();
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
            return true;
        }
        if (!parseThenStatement()) {
            cleanup(m, IF_STATEMENT);
            return false;
        }
        if (currentIs(KW_ELSE)) {
            advance();
            if (!parseElseStatement()) {
                cleanup(m, IF_STATEMENT);
                return false;
            }
        }
        exit_section_modified(builder, m, IF_STATEMENT, true);
        return true;
    }


    /**
     * Parse IfCondition
     * <p>
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
            final Marker ifCondition = builder.mark();
            advance();
            final IElementType i = expect(ID);
            if (i != null)
                expect(OP_EQ);
            if (!parseExpression()) {
                cleanup(ifCondition, IF_CONDITION);
                return false;
            }
            ifCondition.done(IF_CONDITION);
        } else {
            if (builder.eof()) {
                return false;
            }
            // consume for TypeCtors = identifier
            if (isTypeCtor(current())) {
                Marker before_advance = builder.mark();
                while (isTypeCtor(current())) {
                    before_advance.drop();
                    before_advance = builder.mark();
                    advance();
                }
                // goes back for TypeCtor(Type) = identifier
                if (currentIs(OP_PAR_LEFT)) {
                    before_advance.rollbackTo();
                } else {
                    before_advance.drop();
                }
            }
            Marker bookmark = builder.mark();
            final boolean type = parseType();
            if (!type || !currentIs(ID) || !peekIs(OP_EQ)) {
                bookmark.rollbackTo();
                return parseExpression();
            } else {
                bookmark.drop();
                final Marker ifCondition = builder.mark();
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
                ifCondition.done(IF_CONDITION);
            }
        }
        return true;
    }

    boolean parseThenStatement() {
        return parseScopeStatement();
    }

    boolean parseElseStatement() {
        return parseScopeStatement();
    }


    /**
     * Parses an ImportBind
     * <p>
     * $(GRAMMAR $(RULEDEF importBind):
     * $(LITERAL Identifier) ($(LITERAL '=') $(LITERAL Identifier))?
     * ;)
     */
    boolean parseImportBind() {
        final Marker m = builder.mark();
        boolean isNamedBind = false;
        Marker bookmark = builder.mark();
        IElementType ident = expect(ID);
        if (ident == null) {
            bookmark.drop();
            cleanup(m, IMPORT_BIND);
            return false;
        }
        if (currentIs(OP_EQ)) {
            isNamedBind = true;
            advance();
            final IElementType id = expect(ID);
            if (id == null) {
                bookmark.drop();
                cleanup(m, IMPORT_BIND);
                return false;
            }
        }
        bookmark.rollbackTo();
        if (isNamedBind) {
            final Marker namedImportBind = builder.mark();
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
    boolean parseImportBindings() {
        final Marker m = builder.mark();
        builder.advanceLexer();
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
     *   $(LITERAL 'static')? $(LITERAL 'import') $(RULE import) ($(LITERAL ',') $(RULE import))* ($(LITERAL ',') $(RULE importBindings))? $(LITERAL ';')
     * | $(LITERAL 'static')? $(LITERAL 'import') $(RULE importBindings) $(LITERAL ';')
     * ;)
     */
    boolean parseImportDeclaration(Marker m) {
        final Marker bookmark = builder.mark();
        boolean isStatic = false;
        if (builder.getTokenType() == KW_STATIC) {
            builder.advanceLexer();
            isStatic = true;
        }
        if (!tokenCheck(KW_IMPORT)) {
            bookmark.rollbackTo();
            return false;
        }
        if (builder.getTokenType() == OP_PAR_LEFT) {
            if (isStatic) {
                builder.error("Expected identifier instead of '(");
                bookmark.drop();
                m.done(IMPORT_DECLARATION);
                return true;
            }
            // It’s an import expression
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        final boolean si = parseSingleImport();
        if (!si) {
            m.done(IMPORT_DECLARATION);
            return true;
        }
        if (currentIs(OP_COLON))
            parseImportBindings();
        else {
            if (currentIs(OP_COMMA)) {
                advance();
                while (moreTokens()) {
                    if (!parseSingleImport()) {
                        m.done(IMPORT_DECLARATION);
                        return true;
                    }
                    if (currentIs(OP_COLON)) {
                        parseImportBindings();
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
            m.done(IMPORT_DECLARATION);
            return true;
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
    Marker parseImportExpression() {
        final Marker marker = builder.mark();
        if (expect(KW_IMPORT) == null) {
            cleanup(marker, IMPORT_EXPRESSION);
            return null;
        }
        if (expect(OP_PAR_LEFT) == null) {
            cleanup(marker, IMPORT_EXPRESSION);
            return null;
        }
        if(parseAssignExpression() == null) {
            cleanup(marker, IMPORT_EXPRESSION);
            return null;
        }
        if (expect(OP_PAR_RIGHT) == null) {
            cleanup(marker, IMPORT_EXPRESSION);
            return null;
        }
        exit_section_modified(builder, marker, IMPORT_EXPRESSION, true);
        return marker;
    }

    /**
     * Parses an InContractExpression
     * <p>
     * $(GRAMMAR $(RULEDEF inContractExpression):
     *     $(LITERAL 'in') $(LITERAL '$(LPAREN)') $(RULE assertArguments) $(LITERAL '$(RPAREN)')
     *     ;)
     */
    public boolean parseInContractExpression() {
        final Marker m = builder.mark();
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

    /**
     * Parses an InExpression
     * <p>
     * $(GRAMMAR $(RULEDEF inExpression):
     * $(RULE shiftExpression) ($(LITERAL 'in') | ($(LITERAL '!') $(LITERAL 'in'))) $(RULE shiftExpression)
     * ;)
     */
    Marker parseInExpression(Marker m)
    {
        if (m == null) {
            m = builder.mark();
            if (parseShiftExpression() == null) {
                cleanup(m, IN_EXPRESSION);
                return null;
            }
        }
        if (currentIs(OP_NOT)) {
            advance();
        }
        if (!tokenCheck(KW_IN)) {
            cleanup(m, IN_EXPRESSION);
            return null;
        }
        if (parseShiftExpression() == null) {
            cleanup(m, IN_EXPRESSION);
            return null;
        }
        exit_section_modified(builder, m, IN_EXPRESSION, true);
        return m;
    }

    /**
     * Parses an InOutContractExpression
     * <p>
     * $(GRAMMAR $(RULEDEF inOutContractExpression):
     *       $(RULE inContractExpression)
     *     | $(RULE outContractExpression)
     *     ;)
     */
    public boolean parseInOutContractExpression() {
        final Marker m = builder.mark();
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
     * <p>
     * $(GRAMMAR $(RULEDEF inOutStatement):
     *       $(RULE inStatement)
     *     | $(RULE outStatement)
     *     ;)
     */
    public boolean parseInOutStatement() {
        final Marker m = builder.mark();
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
        final Marker m = builder.mark();
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
        final Marker m = builder.mark();
        if (currentIs(KW_VOID))
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
    boolean parseInterfaceDeclaration(Marker m) {
        if (builder.getTokenType() != KW_INTERFACE)
            return false;
        builder.advanceLexer();
        parseInterfaceOrClass();
        m.done(INTERFACE_DECLARATION);
        return true;
    }

    /**
     * Parses an Invariant
     * <p>
     * $(GRAMMAR $(RULEDEF invariant):
     *   $(LITERAL 'invariant') ($(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)'))? $(RULE blockStatement)
     * | $(LITERAL 'invariant') $(LITERAL '$(LPAREN)') $(RULE assertArguments) $(LITERAL '$(RPAREN)') $(LITERAL ';')
     * ;)
     */
    boolean parseInvariant(Marker m) {
        if (builder.getTokenType() != KW_INVARIANT)
            return false;
        builder.advanceLexer();
        if (currentIs(OP_PAR_LEFT)) {
            builder.advanceLexer();
            if (builder.getTokenType() != OP_PAR_RIGHT) {
                if (!parseAssertArguments()) {
                    m.done(INVARIANT);
                    return true;
                }
                if (!tokenCheck(OP_PAR_RIGHT)) {
                    m.done(INVARIANT);
                    return true;
                }
                if (!tokenCheck(OP_SCOLON)) {
                    m.done(INVARIANT);
                    return true;
                }
                m.done(INVARIANT);
                return true;
            }
            builder.advanceLexer();
        }
        parseBlockStatement();
        m.done(INVARIANT);
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
    Marker parseIsExpression() {
        final Marker m = builder.mark();
        if (!tokenCheck(KW_IS)) {
            cleanup(m, IS_EXPRESSION);
            return null;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, IS_EXPRESSION);
            return null;
        }
        if (!parseType()) {
            cleanup(m, IS_EXPRESSION);
            return null;
        }
        if (currentIs(ID))
            advance();
        if (currentIsOneOf(OP_EQ_EQ, OP_COLON)) {
            advance();
            if (!parseTypeSpecialization()) {
                cleanup(m, IS_EXPRESSION);
                return null;
            }
            if (currentIs(OP_COMMA)) {
                advance();
                if (!parseTemplateParameterList()) {
                    cleanup(m, IS_EXPRESSION);
                    return null;
                }
            }
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, IS_EXPRESSION);
            return null;
        }
        exit_section_modified(builder, m, IS_EXPRESSION, true);
        return m;
    }

    /**
     * Parses a KeyValuePair
     * <p>
     * $(GRAMMAR $(RULEDEF keyValuePair):
     * $(RULE assignExpression) $(LITERAL ':') $(RULE assignExpression)
     * ;)
     */
    boolean parseKeyValuePair() {
        final Marker m = builder.mark();
        if (parseAssignExpression() == null) {
            cleanup(m, KEY_VALUE_PAIR);
            return false;
        }
        if (!tokenCheck(OP_COLON)) {
            cleanup(m, KEY_VALUE_PAIR);
            return false;
        }
        if (parseAssignExpression() == null) {
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
        final Marker m = builder.mark();
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
     * $(LITERAL Identifier) $(LITERAL ':') $(RULE Statement)?
     * ;)
     */
    boolean parseLabeledStatement() {
        final Marker m = builder.mark();
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, LABELED_STATEMENT);
            return false;
        }
        expect(OP_COLON);
        if (!currentIs(OP_BRACES_RIGHT))
            if (!parseStatement()) {
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
        final Marker m = builder.mark();
        final IElementType t = expect(KW_CATCH);
        if (t == null) {
            cleanup(m, LAST_CATCH);
            return false;
        }
        if (!parseNonEmptyStatementNoCaseNoDefault()) {
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
     * | $(LITERAL 'extern') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '++') ($(LITERAL ',') $(RULE QualifiedIdentifier) | $(RULE namespaceList) | $(LITERAL 'struct') | $(LITERAL 'class'))? $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseLinkageAttribute() {
        final Marker m = builder.mark();
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
                    if (!parseQualifiedIdentifier()) {
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
     *   $(RULE functionAttribute)
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
        final Marker m = builder.mark();
        if (!moreTokens()) {
            cleanup(m, MEMBER_FUNCTION_ATTRIBUTE);
            return false;
        }
        final IElementType i = current();
        if (i == OP_AT) {
            if (!parseAtAttribute()) {
                cleanup(m, MEMBER_FUNCTION_ATTRIBUTE);
                return false;
            }
        } else if (i == KW_IMMUTABLE || i == KW_INOUT || i == KW_SHARED || i == KW_CONST || i == KW_PURE || i == KW_NOTHROW || i == KW_RETURN || i == KW_SCOPE || i == KW_THROW) {
            advance();
        } else {
            error("Member function attribute expected");
        }
        exit_section_modified(builder, m, MEMBER_FUNCTION_ATTRIBUTE, true);
        return true;
    }

    /**
     * Parses a MissingFunctionBody
     * <p>
     * $(GRAMMAR $(RULEDEF missingFunctionBody):
     *       $(LITERAL ';')
     *     | $(RULE functionContract)* $(LITERAL ';')
     *     ;)
     */
    public boolean parseMissingFunctionBody() {
        final Marker m = builder.mark();
        boolean haveContract = false;
        boolean lastIsOutContractExpression = false;
        while (currentIsOneOf(KW_IN, KW_OUT)) {
            boolean isOut = currentIs(KW_OUT);
            Marker bookmark = builder.mark();
            if (parseFunctionContract(false)) {
                lastIsOutContractExpression = isOut;
            } else {
                lastIsOutContractExpression = false;
                bookmark.rollbackTo();
                if (parseFunctionContract())
                    haveContract = true;
            }
            bookmark.drop();
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
     * $(LITERAL 'mixin') $(LITERAL '$(LPAREN)') $(RULE argumentList) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseMixinDeclaration(Marker m) {
        if (builder.getTokenType() != KW_MIXIN)
            return false;
        final Marker bookmark = builder.mark();
        builder.advanceLexer();
        if (builder.getTokenType() != OP_PAR_LEFT) {
            bookmark.rollbackTo();
            return false;
        }
        builder.advanceLexer();
        if (!parseArgumentList()) {
            m.done(MIXIN_DECLARATION);
            return true;
        }
        bookmark.drop();
        expect(OP_PAR_RIGHT);
        expect(OP_SCOLON);
        m.done(MIXIN_DECLARATION);
        return true;
    }

    /**
     * Parses a MixinType
     * <p>
     * $(GRAMMAR $(RULEDEF mixinType):
     * $(LITERAL 'mixin') $(LITERAL '$(LPAREN)') $(RULE argumentList) $(LITERAL '$(RPAREN)')
     * ;)
     */
    Marker parseMixinType() {
        final Marker m = builder.mark();
        expect(KW_MIXIN);
        if (builder.getTokenType() != OP_PAR_LEFT) {
            m.rollbackTo();
            return null;
        }
        builder.advanceLexer();
        if (!parseArgumentList()) {
            cleanup(m, MIXIN_TYPE);
            return null;
        }
        expect(OP_PAR_RIGHT);
        exit_section_modified(builder, m, MIXIN_TYPE, true);
        return m;
    }

    /**
     * Parses a MixinTemplateDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF mixinTemplateDeclaration):
     * $(LITERAL 'mixin') $(RULE templateDeclaration)
     * ;)
     */
    boolean parseTemplateMixinDeclaration(Marker m) {
        if (builder.getTokenType() != KW_MIXIN) {
            return false;
        }
        final Marker bookmark = builder.mark();
        builder.advanceLexer();
        if (builder.getTokenType() != KW_TEMPLATE) {
            bookmark.rollbackTo();
            return false;
        }
        if (!parseTemplateDeclarationCommon()) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        m.done(TEMPLATE_MIXIN_DECLARATION);
        return true;
    }

    /**
     * Parses a MixinTemplateName
     * <p>
     * $(GRAMMAR $(RULEDEF mixinTemplateName):
     * $(LITERAL '.')? $(RULE mixinQualifiedIdentifier)
     * | $(RULE typeofExpression) $(LITERAL '.') $(RULE mixinQualifiedIdentifier)
     * ;)
     */
    boolean parseMixinTemplateName() {
        final Marker m = builder.mark();
        if (currentIs(KW_TYPEOF)) {
            if (parseTypeofExpression() != null) {
                cleanup(m, MIXIN_TEMPLATE_NAME);
                return false;
            }
            expect(OP_DOT);
            if (!parseMixinQualifiedIdentifier()) {
                cleanup(m, MIXIN_TEMPLATE_NAME);
                return false;
            }
        } else {
            if (currentIs(OP_DOT))
                builder.advanceLexer();
            if (!parseMixinQualifiedIdentifier()) {
                cleanup(m, MIXIN_TEMPLATE_NAME);
                return false;
            }
        }
        m.done(MIXIN_TEMPLATE_NAME);
        return true;
    }

    /**
     * Parses a Module
     * <p>
     * $(GRAMMAR $(RULEDEF module):
     * $(RULE moduleDeclaration)? $(RULE declaration)*
     * ;)
     */
    void parseModule() {
        if (currentIs(SHEBANG)) {
            advance();
        }
        Marker bookmark = builder.mark();
        while (currentIs(OP_AT) || currentIs(KW_DEPRECATED)) {
            parseAttribute();
        }
        boolean isModule = currentIs(KW_MODULE);
        bookmark.rollbackTo();
        if (isModule) {
            parseModuleDeclaration();
        }
        while (!builder.eof()) {
            // To prevent infinite loop if top level } are present
            if (builder.getTokenType() == OP_BRACES_RIGHT) {
                Marker error = builder.mark();
                builder.advanceLexer();
                error.error("Unexpected '}'");
            }
            parseDeclDefWithRecovery();
        }
    }

    /**
     * Parses a ModuleDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF moduleDeclaration):
     * $(RULE atAttribute)* $(RULE deprecated)? $(RULE atAttribute)* $(LITERAL 'module') $(RULE identifierChain) $(LITERAL ';')
     * ;)
     */
    void parseModuleDeclaration() {
        final Marker m = builder.mark();
        m.setCustomEdgeTokenBinders(LeadingDocCommentBinder.INSTANCE, TrailingDocCommentBinder.INSTANCE);
        while (currentIs(OP_AT)) {
            parseAttribute();
        }
        if (currentIs(KW_DEPRECATED))
            if (!parseDeprecated()) {
                m.done(MODULE_DECLARATION);
                return;
            }
        while (currentIs(OP_AT)) {
            parseAttribute();
        }
        final IElementType start = expect(KW_MODULE);
        if (start == null) {
            m.done(MODULE_DECLARATION);
            return;
        }
        if (parseIdentifierChain() == null) {
            m.done(MODULE_DECLARATION);
            return;
        }
        expect(OP_SCOLON);
        m.done(MODULE_DECLARATION);
    }

    /**
     * Parses a MulExpression.
     * <p>
     * $(GRAMMAR $(RULEDEF mulExpression):
     *   $(RULE powExpression)
     * | $(RULE mulExpression) ($(LITERAL '*') | $(LITERAL '/') | $(LITERAL '%')) $(RULE powExpression)
     * ;)
     */
    Marker parseMulExpression() {
        Marker m = parseUnaryExpression();
        if (m == null)
            return null;
        while (currentIsOneOf(OP_ASTERISK, OP_DIV, OP_MOD)) {
            m = m.precede();
            builder.advanceLexer();
            if (parseUnaryExpression() == null) {
                cleanup(m, MUL_EXPRESSION);
                return null;
            }
            m.done(MUL_EXPRESSION);
        }
        return m;
    }


    /**
     * Parses a NamespaceList.
     * <p>
     * $(GRAMMAR $(RULEDEF namespaceList):
     *     $(RULE ternaryExpression) ($(LITERAL ',') $(RULE ternaryExpression)?)* $(LITERAL ',')?
     *     ;)
     */
    public boolean parseNamespaceList() {
        final Marker marker = builder.mark();
        while (moreTokens()) {
            if (parseTernaryExpression() == null) {
                marker.drop();
                return false;
            }
            if (currentIs(OP_COMMA)) {
                advance();
                if (currentIsOneOf(OP_PAR_RIGHT))
                    break;
            } else
                break;
        }
        exit_section_modified(builder, marker, NAMESPACE_LIST, true);
        return true;
    }

    /**
     * Parses a NewAnonClassExpression
     * <p>
     * $(GRAMMAR $(RULEDEF newAnonClassExpression):
     * $(LITERAL 'new') $(LITERAL 'class') $(RULE arguments)? $(RULE baseClassList)? $(RULE structBody)
     * ;)
     */
    boolean parseNewAnonClassExpression() {
        final Marker m = builder.mark();
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
    Marker parseNewExpression() {
        final Marker m = builder.mark();
        if (peekIs(KW_CLASS)) {
            if (!parseNewAnonClassExpression()) {
                cleanup(m, NEW_EXPRESSION);
                return null;
            }
        } else {
            expect(KW_NEW);
            if (!parseType(true)) {
                cleanup(m, NEW_EXPRESSION);
                return null;
            }
            if (currentIs(OP_BRACKET_LEFT)) {
                advance();
                if (parseAssignExpression() == null) {
                    cleanup(m, NEW_EXPRESSION);
                    return null;
                }
                expect(OP_BRACKET_RIGHT);
            } else if (currentIs(OP_PAR_LEFT))
                if (!parseArguments()) {
                    cleanup(m, NEW_EXPRESSION);
                    return null;
                }
        }
        exit_section_modified(builder, m, NEW_EXPRESSION, true);
        return m;
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
        if (currentIs(OP_BRACES_LEFT)) {
            final IElementType b = peekPastBraces();
            if (b == OP_PAR_LEFT) {
                return parseAssignExpression() != null;
            }
            Marker bookmark = builder.mark();
            final boolean initializer = parseStructInitializer();
            if (initializer) {
                bookmark.drop();
                return true;
            }
            bookmark.rollbackTo();
        } else if (currentIs(OP_BRACKET_LEFT)) {
            boolean isAA = false;
            Marker bookmark = builder.mark();
            advance();
            if (currentIs(OP_BRACKET_LEFT)) {
                advance();
                IElementType c = peekPastBrackets();
                isAA = c == OP_COLON;
            }
            bookmark.rollbackTo();
            final IElementType b = peekPastBrackets();
            if (!isAA && (b == OP_COMMA
                || b == OP_PAR_RIGHT
                || b == OP_BRACKET_RIGHT
                || b == OP_BRACES_RIGHT
                || b == OP_SCOLON)) {
                return parseArrayLiteral() != null;
            }
        }
        return parseAssignExpression() != null;
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
        final Marker marker = builder.mark();
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
    Marker parseOrExpression() {
        Marker m = parseXorExpression();
        if (m == null)
            return null;
        while (currentIs(OP_OR)) {
            m = m.precede();
            builder.advanceLexer();
            if (parseXorExpression() == null) {
                cleanup(m, OR_EXPRESSION);
                return null;
            }
            m.done(OR_EXPRESSION);
        }
        return m;
    }

    /**
     * Parses an OrOrExpression
     * <p>
     * $(GRAMMAR $(RULEDEF orOrExpression):
     *   $(RULE andAndExpression)
     * | $(RULE orOrExpression) $(LITERAL '||') $(RULE andAndExpression)
     * ;)
     */
    Marker parseOrOrExpression() {
        Marker m = parseAndAndExpression();
        if (m == null)
            return null;
        while (currentIs(OP_BOOL_OR)) {
            m = m.precede();
            builder.advanceLexer();
            if (parseAndAndExpression() == null) {
                cleanup(m, OR_OR_EXPRESSION);
                return null;
            }
            m.done(OR_OR_EXPRESSION);
        }
        return m;
    }

    /**
     * Parses an OutContractExpression
     * <p>
     * $(GRAMMAR $(RULEDEF outContractExpression):
     *     $(LITERAL 'out') $(LITERAL '$(LPAREN)') $(LITERAL Identifier)? $(LITERAL ';') $(RULE assertArguments) $(LITERAL '$(RPAREN)')
     *     ;)
     */
    public boolean parseOutContractExpression() {
        final Marker m = builder.mark();
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
        final Marker m = builder.mark();
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
        final Marker m = builder.mark();
        while (!builder.eof()) {
            if (!parseParameterAttribute())
                break;
        }
        // Parsed the attributes of the variadic attributes.
        // Abort and defer to parseVariadicArgumentsAttributes
        if (currentIs(OP_TRIPLEDOT)) {
            cleanup(m, PARAMETER);
            return false;
        }

        if (!parseType()) {
            cleanup(m, PARAMETER);
            return false;
        }
        if (currentIs(ID)) {
            advance();
            if (currentIs(OP_TRIPLEDOT)) {
                advance();
            } else if (currentIs(OP_EQ)) {
                advance();
                if (parseAssignExpression() == null) {
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
            if (parseAssignExpression() == null) {
                cleanup(m, PARAMETER);
                return false;
            }
        }
        exit_section_modified(builder, m, PARAMETER, true);
        return true;
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
    boolean parseParameterAttribute() {
        final IElementType i = builder.getTokenType();
        if (i == OP_AT) {
            if (!parseAtAttribute()) {
                error("Parameter attribute expected");
                return false;
            }
            return true;
        } else if (i == KW_IMMUTABLE || i == KW_SHARED || i == KW_CONST || i == KW_INOUT) {
            if (peekIs(OP_PAR_LEFT))
                return false;
            else
                builder.advanceLexer();
        } else if (i == KW_FINAL || i == KW_IN || i == KW_LAZY || i == KW_OUT || i == KW_REF || i == KW_SCOPE || i == KW_AUTO || i == KW_RETURN) {
            builder.advanceLexer();
        } else {
            return false;
        }
        return true;
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
        final Marker m = builder.mark();
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
            Marker bookmark = builder.mark();
            if (!(parseParameter())) {
                // parseParameter fails for C-style variadics, they are parsed below
                if (!currentIs(OP_TRIPLEDOT)) {
                    bookmark.drop();
                    cleanup(m, PARAMETERS);
                    return false;
                }
                // Reset to the beginning of the current parameters
                bookmark.rollbackTo();
                if (!parseVariadicArgumentsAttributes()) {
                    cleanup(m, PARAMETERS);
                    return false;
                }
                if (!tokenCheck(OP_TRIPLEDOT)) {
                    cleanup(m, PARAMETERS);
                    return false;
                }
            } else {
                bookmark.drop();
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
     * <p>
     * $(GRAMMAR $(RULEDEF variadicArgumentsAttributes):
     *       $(RULE variadicArgumentsAttribute)+
     *     ;)
     */
    public boolean parseVariadicArgumentsAttributes() {
        final Marker m = builder.mark();
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
     * <p>
     * $(GRAMMAR $(RULEDEF variadicArgumentsAttribute):
     *       $(LITERAL 'const')
     *     | $(LITERAL 'immutable')
     *     | $(LITERAL 'scope')
     *     | $(LITERAL 'shared')
     *     | $(LITERAL 'return')
     *     ;)
     */
    public boolean parseVariadicArgumentsAttribute() {
        final Marker m = builder.mark();
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
    boolean parsePostblit(Marker m) {
        if (builder.getTokenType() != KW_THIS)
            return false;
        final Marker bookmark = builder.mark();
        advance();
        if (builder.getTokenType() != OP_PAR_LEFT) {
            bookmark.rollbackTo();
            return false;
        }
        advance();
        if (builder.getTokenType() != KW_THIS) {
            // probably a constructor
            bookmark.rollbackTo();
            return false;
        }
        advance();
        if (builder.getTokenType() != OP_PAR_RIGHT) {
            // probably a template taking this as template argument
            bookmark.rollbackTo();
            return false;
        }
        advance();
        bookmark.drop();
        while (currentIsMemberFunctionAttribute())
            if (!parseMemberFunctionAttribute()) {
                m.done(POSTBLIT);
                return true;
            }
        if (currentIs(OP_SCOLON))
            advance();
        else if (!parseFunctionBody()) {
            m.done(POSTBLIT);
            return true;
        }
        m.done(POSTBLIT);
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
    Marker parsePowExpression() {
        Marker m = parsePostfixExpression();
        if (m == null) {
            return null;
        }
        if (builder.getTokenType() == OP_POW) {
            Marker marker = m.precede();
            builder.advanceLexer();
            if (parseUnaryExpression() == null) {
                cleanup(marker, POW_EXPRESSION);
                return null;
            }
            marker.done(POW_EXPRESSION);
            return marker;
        }
        return m;
    }

    /**
     * Parses a PragmaExpression
     * <p>
     * $(GRAMMAR $(RULEDEF pragmaExpression):
     * $(RULE 'pragma') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) ($(LITERAL ',') $(RULE argumentList))? $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parsePragmaExpression() {
        final Marker m = builder.mark();
        expect(KW_PRAGMA);
        expect(OP_PAR_LEFT);
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, PRAGMA_EXPRESSION);
            return false;
        }
        if (currentIs(OP_COMMA)) {
            advance();
            if (!parseArgumentList()) {
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
        final Marker m = builder.mark();
        if (!parsePragmaExpression()) {
            cleanup(m, PRAGMA_STATEMENT);
            return false;
        }
        if (current() == OP_BRACES_LEFT) {
            if (!parseBlockStatement()) {
                cleanup(m, PRAGMA_STATEMENT);
                return false;
            }
        } else if (current() == OP_SCOLON) {
            advance();
        } else {
            if (!parseNonEmptyStatement()) {
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
    Marker parsePrimaryExpression() {
        final IElementType i = current();

        // Special keyword
        if (i == KW___FILE__ || i == KW___FILE_FULL_PATH__ || i == KW___MODULE__ || i == KW___LINE__ || i == KW___FUNCTION__ || i == KW___PRETTY_FUNCTION__) {
            Marker m = builder.mark();
            advance();
            exit_section_modified(builder, m, MAGIC_LITERAL_EXPRESSION, true);
            return m;
        }

        // Special token
        if (i == KW___DATE__ || i == KW___TIME__ || i == KW___TIMESTAMP__ || i == KW___VENDOR__ || i == KW___VERSION__) {
            Marker m = builder.mark();
            advance();
            exit_section_modified(builder, m, MAGIC_LITERAL_EXPRESSION, true);
            return m;
        }

        if (i == ID && peekIs(OP_LAMBDA_ARROW)) {
            return parseFunctionLiteralExpression();
        }

        // [.] (Identifier|TemplateInstance)
        if (i == OP_DOT || i == ID) {
            return primaryExpressionIdentifierCase();
        }

        if (i == OP_DOLLAR) {
            Marker m = builder.mark();
            builder.advanceLexer();
            m.done(DOLLAR_EXPRESSION);
            return m;
        }

        // LiteralExpression
        if (isLiteral(i)) {
            Marker m = builder.mark();
            if (currentIsOneOf(stringLiteralsArray)) {
                do {
                    advance();
                } while (currentIsOneOf(stringLiteralsArray));
            } else {
                advance();
            }
            exit_section_modified(builder, m, LITERAL_EXPRESSION, true);
            return m;
        }
        if (i == OP_BRACKET_LEFT) {
            // ArrayLiteral | AssocArrayLiteral
            if (isAssociativeArrayLiteral()) {
                return parseAssocArrayLiteral();
            } else {
                return parseArrayLiteral();
            }
        }
        // Function Literal
        Marker bookmark = builder.mark();
        Marker fnctlMarker = parseFunctionLiteralExpression();
        if (fnctlMarker != null) {
            bookmark.drop();
            return fnctlMarker;
        }
        bookmark.rollbackTo();

        // AssertExpression
        if (i == KW_ASSERT) {
            return parseAssertExpression();
        }

        // MixinExpression
        if (i == KW_MIXIN) {
            return parseMixinType();
        }

        // ImportExpression
        if (i == KW_IMPORT) {
            return parseImportExpression();
        }

        // NewExpression
        if (i == KW_NEW) {
            return parseNewExpression();
        }

        // FundamentalType . Identifier | FundamentalType ( NamedArgumentList )
        if (isBasicType(i)) {
            Marker m = builder.mark();
            advance();
            if (currentIs(OP_DOT)) {
                advance();
                expect(ID);
            } else if (currentIs(OP_PAR_LEFT)) {
                if (!parseArguments()) {
                    cleanup(m, FUNDAMENTAL_TYPE_CONSTRUCT_EXPRESSION);
                    return null;
                }
                m.done(FUNDAMENTAL_TYPE_CONSTRUCT_EXPRESSION);
                return m;
            } else {
                builder.error("`.` or `(` expected");
                m.drop();
                return null;
            }
            exit_section_modified(builder, m, FUNDAMENTAL_TYPE_PROPERTY_EXPRESSION, true);
            return m;
        }

        // TypeCtor? ...
        if (i == KW_IMMUTABLE || i == KW_CONST || i == KW_INOUT || i == KW_SHARED) {
            Marker m = builder.mark();
            bookmark = builder.mark();
            advance();
            if (expect(OP_PAR_LEFT) == null) {
                bookmark.rollbackTo();
                m.drop();
                return null;
            }
            if (!parseType()) {
                bookmark.rollbackTo();
                m.drop();
                return null;
            }
            bookmark.drop();
            expect(OP_PAR_RIGHT);
            if (currentIs(OP_DOT)) {
                // ( type ) . id
                advance();
                if (currentIs(ID)) {
                    advance();
                    exit_section_modified(builder, m, TYPE_PROPERTY_EXPRESSION, true);
                    return m;
                }
            } else if (builder.getTokenType() != OP_PAR_LEFT) {
                builder.error("`.` or `(` expected");
                m.done(TYPE_CONSTRUCT_EXPRESSION);
                return null;
            }
            // (type) (args)
            builder.advanceLexer();
            parseArgumentList();
            expect(OP_PAR_RIGHT);
            m.done(TYPE_CONSTRUCT_EXPRESSION);
            return m;
        }

        if (i == OP_PAR_LEFT) {
            Marker m = builder.mark();
            advance();
            bookmark = builder.mark();
            if (parseType()) {
                if (expect(OP_PAR_RIGHT) != null) {
                    if (builder.getTokenType() == OP_DOT) {
                        // ( type ) . id
                        builder.advanceLexer();
                        expect(ID);
                        if (builder.getTokenType() != OP_NOT && builder.getTokenType() != OP_PAR_LEFT && builder.getTokenType() != OP_DDOT) {
                            bookmark.drop();
                            exit_section_modified(builder, m, TYPE_PROPERTY_EXPRESSION, true);
                            return m;
                        }
                        // then it’s a parenthesised expression followed by reference expression
                    } else if (builder.getTokenType() == OP_PAR_LEFT) {
                        // (type) (args)
                        bookmark.drop();
                        builder.advanceLexer();
                        parseArgumentList();
                        expect(OP_PAR_RIGHT);
                        m.done(TYPE_CONSTRUCT_EXPRESSION);
                        return m;
                    }
                }
            }
            bookmark.rollbackTo();

            // ( expression )
            if (!parseExpression()) {
                cleanup(m, PARENTHESISED_EXPRESSION);
                return null;
            }
            expect(OP_PAR_RIGHT);
            exit_section_modified(builder, m, PARENTHESISED_EXPRESSION, true);
            return m;
        }

        if (i == KW_TYPEOF) {
            return parseTypeofExpression();
        }

        if (i == KW_TYPEID) {
            return parseTypeidExpression();
        }

        if (i == KW_IS) {
            return parseIsExpression();
        }

        if (i == KW___TRAITS) {
            return parseTraitsExpression();
        }

        return null;
    }

    private boolean isLiteral(final IElementType i) {
        return literals.contains(i);
    }

    private boolean isBasicType(final IElementType i) {
        return basicTypes.contains(i);
    }

    private Marker primaryExpressionIdentifierCase() {
        Marker m = builder.mark();
        if (builder.getTokenType() == OP_DOT) {
            builder.advanceLexer();
        }
        boolean result = parseIdentifierOrTemplateInstance();
        m.done(REFERENCE_EXPRESSION);
        return result ? m : null;
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
        final Marker m = builder.mark();
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
    Marker parseRelExpression(Marker m)
    {
        if (m == null) {
            m = builder.mark();
            if (parseShiftExpression() == null) {
                cleanup(m, IDENTITY_EXPRESSION);
                return null;
            }
        }
        if (!currentIsOneOf(OP_LESS, OP_LESS_EQ, OP_GT, OP_GT_EQ)) {
            cleanup(m, IDENTITY_EXPRESSION);
            return null;
        }
        advance();
        if (parseShiftExpression() == null) {
            cleanup(m, IDENTITY_EXPRESSION);
            return null;
        }
        exit_section_modified(builder, m, REL_EXPRESSION, true);
        return m;
    }

    /**
     * Parses a ReturnStatement
     * <p>
     * $(GRAMMAR $(RULEDEF returnStatement):
     * $(LITERAL 'return') $(RULE expression)? $(LITERAL ';')
     * ;)
     */
    boolean parseReturnStatement() {
        final Marker m = builder.mark();
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
        final Marker m = builder.mark();
        expect(KW_SCOPE);
        expect(OP_PAR_LEFT);
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, SCOPE_GUARD_STATEMENT);
            return false;
        }
        expect(OP_PAR_RIGHT);
        if (!parseNonEmptyStatementNoCaseNoDefault()) {
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
    boolean parseSharedStaticConstructor(Marker m) {
        if (builder.getTokenType() != KW_SHARED)
            return false;
        final Marker bookmark = builder.mark();
        builder.advanceLexer();
        if (builder.getTokenType() != KW_STATIC) {
            bookmark.rollbackTo();
            return false;
        }
        builder.advanceLexer();
        final boolean b = parseStaticCtorDtorCommon();
        if (!b) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        m.done(SHARED_STATIC_CONSTRUCTOR);
        return true;
    }

    /**
     * Parses a SharedStaticDestructor
     * <p>
     * $(GRAMMAR $(RULEDEF sharedStaticDestructor):
     * $(LITERAL 'shared') $(LITERAL 'static') $(LITERAL '~') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE MemberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ";"))
     * ;)
     */
    boolean parseSharedStaticDestructor(Marker m) {
        if (builder.getTokenType() != KW_SHARED)
            return false;
        final Marker bookmark = builder.mark();
        builder.advanceLexer();
        if (builder.getTokenType() != KW_STATIC) {
            bookmark.rollbackTo();
            return false;
        }
        if (!tokenCheck(OP_TILDA)) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        parseStaticCtorDtorCommon();
        m.done(SHARED_STATIC_DESTRUCTOR);
        return true;
    }

    /**
     * Parses a ShiftExpression
     * <p>
     * $(GRAMMAR $(RULEDEF shiftExpression):
     * $(RULE addExpression)
     * | $(RULE shiftExpression) ($(LITERAL '<<') | $(LITERAL '>>') | $(LITERAL '>>>')) $(RULE addExpression)
     * ;)
     */
    Marker parseShiftExpression() {
        Marker m = parseAddExpression();
        if (m == null)
            return null;
        while (currentIsOneOf(OP_SH_LEFT, OP_SH_RIGHT, OP_USH_RIGHT)) {
            m = m.precede();
            builder.advanceLexer();
            if (parseAddExpression() == null) {
                cleanup(m, SHIFT_EXPRESSION);
                return null;
            }
            m.done(SHIFT_EXPRESSION);
        }
        return m;
    }

    /**
     * Parses a SingleImport
     * <p>
     * $(GRAMMAR $(RULEDEF import):
     * ($(LITERAL Identifier) $(LITERAL '='))? $(RULE identifierChain)
     * ;)
     */
    boolean parseSingleImport() {
        final Marker m = builder.mark();
        if (startsWith(ID, OP_EQ)) {
            advance(); // identifier
            advance(); // =
        }
        if (parseIdentifierChain() == null) {
            cleanup(m, SINGLE_IMPORT);
            return false;
        }
        exit_section_modified(builder, m, SINGLE_IMPORT, true);
        return true;
    }

    /**
     * Parses a ShortenedFunctionBody
     * <p>
     * $(GRAMMAR $(RULEDEF shortenedFunctionBody):
     *      $(RULE inOutContractExpression)* $(LITERAL '=>') $(RULE expression) $(LITERAL ';')
     *     ;)
     */
    public boolean parseShortenedFunctionBody() {
        final Marker m = builder.mark();
        while (currentIsOneOf(KW_IN, KW_OUT)) {
            parseFunctionContract(false);
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
     * <p>
     * $(GRAMMAR $(RULEDEF specifiedFunctionBody):
     *       $(LITERAL 'do')? $(RULE blockStatement)
     *     | $(RULE functionContract)* $(RULE inOutContractExpression) $(LITERAL 'do')? $(RULE blockStatement)
     *     | $(RULE functionContract)* $(RULE inOutStatement) $(LITERAL 'do') $(RULE blockStatement)
     *     ;)
     */
    public boolean parseSpecifiedFunctionBody() {
        final Marker m = builder.mark();
        boolean requireDo = false;
        while (currentIsOneOf(KW_IN, KW_OUT)) {
            Marker bookmark = builder.mark();
            if (parseFunctionContract(false)) {
                requireDo = false;
            }
            else {
                bookmark.rollbackTo();
                if (parseFunctionContract()) {
                    requireDo = true;
                }
            }
            bookmark.drop();
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
     * Parse a Statement
     * $(GRAMMAR Statement):
     * $(RULE emptyStatement)
     * | $(RULE scopeBlockStatement)
     * | $(RULE nonEmptyStatement)
     */
    boolean parseStatement() {
        IElementType i = builder.getTokenType();
        if (i == OP_SCOLON) {
            return parseEmptyStatement();
        } else if (i  == OP_BRACES_LEFT) {
            return parseScopeBlockStatement();
        } else {
            return parseNonEmptyStatement();
        }
    }

    /**
     * Parses a Non Empty Statement
     * <p>
     * $(GRAMMAR $(RULEDEF nonEmptyStatement):
     * $(RULE statementNoCaseNoDefault)
     * | $(RULE caseStatement)
     * | $(RULE caseRangeStatement)
     * | $(RULE defaultStatement)
     * ;)
     */
    boolean parseNonEmptyStatement() {
        if (!moreTokens()) {
            error("Expected statement instead of EOF");
            return false;
        }
        final IElementType i = current();
        if (i == KW_CASE) {
            final Marker m_case = builder.mark();
            advance();
            final boolean argumentList = parseArgumentList();
            if (!argumentList) {
                m_case.drop();
                return false;
            }
            if (startsWith(OP_COLON, OP_DDOT)) {
                return parseCaseRangeStatement(m_case);
            } else {
                return parseCaseStatement(m_case);
            }
        } else if (i == KW_DEFAULT) {
            return parseDefaultStatement();
        } else {
            return parseNonEmptyStatementNoCaseNoDefault();
        }
    }

    /**
     * Parses a StatementNoCaseNoDefault
     * <p>
     * $(GRAMMAR $(RULEDEF statementNoCaseNoDefault):
     *   $(RULE labeledStatement)
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
     * | $(Rule importDeclation)
     * ;)
     */
    boolean parseNonEmptyStatementNoCaseNoDefault() {
        if (!moreTokens()) {
            error("Expected statement instead of EOF");
            return false;
        }
        final IElementType i = current();
        if (i == OP_BRACES_LEFT) {
            return parseBlockStatement();
        } else if (i == KW_IF) {
            return parseIfStatement();
        } else if (i == KW_WHILE) {
            return parseWhileStatement();
        } else if (i == KW_DO) {
            return parseDoStatement();
        } else if (i == KW_FOR) {
            return parseForStatement();
        } else if (i == KW_FOREACH || i == KW_FOREACH_REVERSE) {
            return parseForeachStatement();
        } else if (i == KW_SWITCH) {
            return parseSwitchStatement();
        } else if (i == KW_CONTINUE) {
            return parseContinueStatement();
        } else if (i == KW_BREAK) {
            return parseBreakStatement();
        } else if (i == KW_RETURN) {
            return parseReturnStatement();
        } else if (i == KW_GOTO) {
            return parseGotoStatement();
        } else if (i == KW_WITH) {
            return parseWithStatement();
        } else if (i == KW_SYNCHRONIZED) {
            return parseSynchronizedStatement();
        } else if (i == KW_TRY) {
            return parseTryStatement();
        } else if (i == KW_SCOPE) {
            if (peekIs(OP_PAR_LEFT))
                return parseScopeGuardStatement();
            else
                return parseDeclarationStatement();
        } else if (i == KW_ASM) {
            return parseAsmStatement();
        } else if (i == KW_PRAGMA) {
            return parsePragmaStatement();
        } else if (i == KW_FINAL && peekIs(KW_SWITCH)) {
            return parseFinalSwitchStatement();
        } else if (i == KW_DEBUG) {
            if (peekIs(OP_EQ)) {
                return parseDeclarationStatement();
            }
            return parseConditionalStatement();
        } else if (i == KW_VERSION) {
            if (peekIs(OP_EQ)) {
                // TODO for better error reporting `version = X;` in statement is an error
                //  the parsing should print a pretty error, specifying that they must be done at module level
                return false;
            }
            return parseConditionalStatement();
        } else if (i == KW_STATIC) {
            IElementType next = builder.lookAhead(1);
            if (next == KW_IF) {
                return parseConditionalStatement();
            } else if ( next == KW_ASSERT) {
                return parseStaticAssertStatement();
            } else if (next == KW_FOREACH || next == KW_FOREACH_REVERSE) {
                return parseStaticForeachStatement();
            } else if (next == KW_IMPORT) {
                return parseImportDeclaration(builder.mark());
            }
            else {
                Marker m = builder.mark();
                if (parseDeclarationStatement()) {
                    m.drop();
                    return true;
                }
                builder.advanceLexer();
                error("'if', 'assert', 'foreach', 'foreach_reverse' or 'import' expected.");
                m.rollbackTo();
                return false;
            }
        } else if (i == ID && peekIs(OP_COLON)) {
            return parseLabeledStatement();
        } else if (i == KW_IMPORT && !peekIs(OP_PAR_LEFT)) {
            return parseImportDeclaration(builder.mark());
        } else {
            Marker marker = builder.mark();
            if (currentIs(ID) && builder.lookAhead(1) == OP_EQ) {
                // there can’t be alias reassignment at this level
                marker.drop();
                return parseExpressionStatement();
            }
            if (parseDeclarationStatement()) {
                marker.drop();
                return true;
            } else {
                marker.rollbackTo();
                return parseExpressionStatement();
            }
        }
    }

    boolean parseEmptyStatement() {
        Marker marker = builder.mark();
        if(expect(OP_SCOLON) == null) {
            marker.drop();
            return false;
        }
        marker.done(EMPTY_STATEMENT);
        return true;
    }

    boolean parseScopeBlockStatement() {
        return parseBlockStatement();
    }

    boolean parseScopeStatementList() {
        while (!builder.eof() ) {
            if(!parseStatementNoCaseNoDefault()) {
                break;
            }
        }
        return true;
    }

    boolean parseStatementNoCaseNoDefault() {
        IElementType i = builder.getTokenType();
        if (i == OP_SCOLON) {
            return parseEmptyStatement();
        } else if (i == OP_BRACES_LEFT) {
            return parseScopeBlockStatement();
        } else {
            return parseNonEmptyStatementNoCaseNoDefault();
        }
    }

    boolean parseNoScopeNonEmptyStatement() {
        if (builder.getTokenType() == OP_BRACES_LEFT) {
            return parseBlockStatement();
        } else {
            return parseNonEmptyStatement();
        }
    }

    boolean parseScopeStatement() {
        if (builder.getTokenType() == OP_BRACES_LEFT) {
            return parseScopeBlockStatement();
        } else {
            return parseNonEmptyStatement();
        }
    }

    /**
     * Parses a StaticAssertDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF staticAssertDeclaration):
     * $(RULE staticAssertStatement)
     * ;)
     */
    boolean parseStaticAssertDeclaration(Marker marker) {
        if (builder.getTokenType() != KW_STATIC) {
            return false;
        }
        final Marker bookmark = builder.mark();
        builder.advanceLexer();
        if (builder.getTokenType() != KW_ASSERT) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        if(parseAssertExpression() == null) {
            marker.done(STATIC_ASSERT_DECLARATION);
            return true;
        }
        expect(OP_SCOLON);
        marker.done(STATIC_ASSERT_DECLARATION);
        return true;
    }

    /**
     * Parses a StaticAssertStatement
     * <p>
     * $(GRAMMAR $(RULEDEF staticAssertStatement):
     * $(LITERAL 'static') $(RULE assertExpression) $(LITERAL ';')
     * ;)
     */
    boolean parseStaticAssertStatement() {
        final Marker marker = builder.mark();
        if (expect(KW_STATIC) == null) {
            cleanup(marker, STATIC_ASSERT_STATEMENT);
            return false;
        }
        if(parseAssertExpression() == null) {
            cleanup(marker, STATIC_ASSERT_STATEMENT);
            return false;
        }
        if (expect(OP_SCOLON) == null) {
            cleanup(marker, STATIC_ASSERT_STATEMENT);
            return false;
        }
        exit_section_modified(builder, marker, STATIC_ASSERT_STATEMENT, true);
        return true;
    }

    /**
     * Parses a StaticConstructor
     * <p>
     * $(GRAMMAR $(RULEDEF staticConstructor):
     * $(LITERAL 'static') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ";"))
     * ;)
     */
    boolean parseStaticConstructor(Marker m) {
        if (builder.getTokenType() != KW_STATIC)
            return false;
        final Marker bookmark = builder.mark();
        builder.advanceLexer();
        if (!parseStaticCtorDtorCommon()) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        m.done(STATIC_CONSTRUCTOR);
        return true;
    }

    /**
     * Parses a StaticDestructor
     * <p>
     * $(GRAMMAR $(RULEDEF staticDestructor):
     * $(LITERAL 'static') $(LITERAL '~') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ";"))
     * ;)
     */
    boolean parseStaticDestructor(Marker m) {
        if (builder.getTokenType() != KW_STATIC)
            return false;
        final Marker bookmark = builder.mark();
        builder.advanceLexer();
        if (!tokenCheck(OP_TILDA)) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        parseStaticCtorDtorCommon();
        m.done(STATIC_DESTRUCTOR);
        return true;
    }

    /**
     * Parses an StaticIfCondition
     * <p>
     * $(GRAMMAR $(RULEDEF staticIfCondition):
     * $(LITERAL 'static') $(LITERAL 'if') $(LITERAL '$(LPAREN)') $(RULE assignExpression) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseStaticIfCondition() {
        final Marker marker = builder.mark();
        if (expect(KW_STATIC) == null) {
            cleanup(marker, STATIC_IF_CONDITION);
            return false;
        }
        if (expect(KW_IF) == null) {
            cleanup(marker, STATIC_IF_CONDITION);
            return false;
        }
        if (expect(OP_PAR_LEFT) == null) {
            cleanup(marker, STATIC_IF_CONDITION);
            return false;
        }
        if(parseAssignExpression() == null) {
            cleanup(marker, STATIC_IF_CONDITION);
            return false;
        }
        if (expect(OP_PAR_RIGHT) == null) {
            cleanup(marker, STATIC_IF_CONDITION);
            return false;
        }
        exit_section_modified(builder, marker, STATIC_IF_CONDITION, true);
        return true;
    }

    /**
     * Parses a StorageClass
     * <p>
     * $(GRAMMAR $(RULEDEF storageClass):
     * $(RULE linkageAttribute)
     * | $(RULE alignAttribute)
     * | $(RULE atAttribute)
     * | $(RULE typeConstructor)
     * | $(LITERAL 'deprecated')
     * | $(LITERAL 'enum')
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
     * ;)
     */
    boolean parseStorageClass() {
        final Marker m = builder.mark();
        final IElementType i = current();
        if (i == OP_AT) {
            if (!parseAtAttribute()) {
                cleanup(m, STORAGE_CLASS);
                return false;
            }
        } else if (i == KW_DEPRECATED) {
            builder.advanceLexer();
            if (builder.getTokenType() == OP_PAR_LEFT) {
                m.rollbackTo();
                return false;
            }
        } else if (i == KW_ALIGN) {
            if (!parseAlignAttribute()) {
                cleanup(m, STORAGE_CLASS);
                return false;
            }
        } else if (i == KW_EXTERN) {
            if (peekIs(OP_PAR_LEFT)) {
                if (!parseLinkageAttribute()) {
                    cleanup(m, STORAGE_CLASS);
                    return false;
                }
                exit_section_modified(builder, m, STORAGE_CLASS, true);
                return true;
            } else
                advance();
        } else if (i == KW_CONST || i == KW_IMMUTABLE || i == KW_INOUT || i == KW_SHARED || i == KW_ABSTRACT || i == KW_AUTO || i == KW_ENUM || i == KW_FINAL || i == KW_NOTHROW || i == KW_OVERRIDE || i == KW_PURE || i == KW_REF || i == KW___GSHARED || i == KW_SCOPE || i == KW_STATIC || i == KW_SYNCHRONIZED) {
            advance();
            if (builder.getTokenType() == OP_PAR_LEFT) {
                m.rollbackTo();
                return false;
            }
        } else {
            m.rollbackTo();
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
        final Marker m = builder.mark();
        expect(OP_BRACES_LEFT);
        while (!builder.eof() && builder.getTokenType() != OP_BRACES_RIGHT) {
            if (!parseDeclDef()) {
                Marker recovery = builder.mark();
                while (!builder.eof() && builder.getTokenType() != OP_BRACES_RIGHT && builder.getTokenType() != OP_SCOLON)
                    builder.advanceLexer();
                if (builder.getTokenType() == OP_SCOLON)
                    builder.advanceLexer();
                recovery.error("Unable to parse this declaration");
            }
        }
        expect(OP_BRACES_RIGHT);
        m.done(STRUCT_BODY);
        return true;
    }

    /**
     * Parses a StructDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF structDeclaration):
     * $(LITERAL 'struct') $(LITERAL Identifier)? ($(RULE templateParameters) $(RULE raint)? $(RULE structBody) | ($(RULE structBody) | $(LITERAL ';')))
     * ;)
     */
    boolean parseStructDeclaration(Marker m) {
        if (builder.getTokenType() != KW_STRUCT)
            return false;
        builder.advanceLexer();
        if (currentIs(ID))
            advance();
        if (currentIs(OP_PAR_LEFT)) {
            if (!parseTemplateParameters()) {
                m.done(STRUCT_DECLARATION);
                return true;
            }
            if (currentIs(KW_IF))
                if (!parseConstraint()) {
                    m.done(STRUCT_DECLARATION);
                    return true;
                }
        }
        if (currentIs(OP_BRACES_LEFT)) {
            if (!parseStructBody()) {
                m.done(STRUCT_DECLARATION);
                return true;
            }
        } else if (currentIs(OP_SCOLON))
            advance();
        else {
            error("Template Parameters, Struct Body, or Semicolon expected");
        }
        m.done(STRUCT_DECLARATION);
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
        final Marker m = builder.mark();
        expect(OP_BRACES_LEFT);
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
        final Marker m = builder.mark();
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
        final Marker m = builder.mark();
        do {
            parseStructMemberInitializer();
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
        final Marker m = builder.mark();
        expect(KW_SWITCH);
        expect(OP_PAR_LEFT);
        if (!parseExpression()) {
            cleanup(m, SWITCH_STATEMENT);
            return false;
        }
        expect(OP_PAR_RIGHT);
        if (!parseNonEmptyStatement()) {
            cleanup(m, SWITCH_STATEMENT);
            return false;
        }
        exit_section_modified(builder, m, SWITCH_STATEMENT, true);
        return true;
    }

    /**
     * Parses a SynchronizedStatement
     * <p>
     * $(GRAMMAR $(RULEDEF synchronizedStatement):
     * $(LITERAL 'synchronized') ($(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)'))? $(RULE scopeStatement)
     * ;)
     */
    boolean parseSynchronizedStatement() {
        final Marker m = builder.mark();
        expect(KW_SYNCHRONIZED);
        if (currentIs(OP_PAR_LEFT)) {
            expect(OP_PAR_LEFT);
            if (!parseExpression()) {
                cleanup(m, SYNCHRONIZED_STATEMENT);
                return false;
            }
            expect(OP_PAR_RIGHT);
        }
        if (!parseScopeStatement()) {
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
        final Marker m = builder.mark();
        expect(KW_ALIAS);
        if (currentIs(ID) && !peekIs(OP_DOT)) {
            if (peekIsOneOf(OP_COMMA, OP_PAR_RIGHT, OP_EQ, OP_COLON))
                advance();
            else {
                if (!parseType()) {
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
            if (!parseType()) {
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
                if (!parseType()) {
                    cleanup(m, TEMPLATE_ALIAS_PARAMETER);
                    return false;
                }
            } else if (parseAssignExpression() == null) {
                cleanup(m, TEMPLATE_ALIAS_PARAMETER);
                return false;
            }
        }
        if (currentIs(OP_EQ)) {
            advance();
            if (isType()) {
                if (!parseType()) {
                    cleanup(m, TEMPLATE_ALIAS_PARAMETER);
                    return false;
                }
            } else if (parseAssignExpression() == null) {
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
        final Marker m = builder.mark();
        int startIndex = builder.getCurrentOffset();
        boolean p = cachedTypedChecks.containsKey(startIndex);
        if (p) {
            if (cachedTypedChecks.get(startIndex)) {
                parseType();
            }
            else {
                if (parseAssignExpression() == null) {
                    cleanup(m, TEMPLATE_ARGUMENT);
                    return false;
                }
            }
        }
        else {
            Marker bookmark = builder.mark();
            final boolean t = parseType();
            if (t && currentIsOneOf(OP_COMMA, OP_PAR_RIGHT)) {
                cachedTypedChecks.put(startIndex, true);
                bookmark.drop();
            } else {
                cachedTypedChecks.put(startIndex, false);
                bookmark.rollbackTo();
                if (parseAssignExpression() == null) {
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
        final Marker marker = builder.mark();
        while (moreTokens()) {
            if (!parseTemplateArgument()) {
                cleanup(marker, TEMPLATE_ARGUMENT_LIST);
                return false;
            }
            if (currentIs(OP_COMMA)) {
                advance();
                if (currentIsOneOf(OP_PAR_RIGHT))
                    break;
            } else
                break;
        }
        exit_section_modified(builder, marker, TEMPLATE_ARGUMENT_LIST, true);
        return true;
    }

    /**
     * Parses TemplateArguments
     * <p>
     * $(GRAMMAR $(RULEDEF templateArguments):
     * $(LITERAL '!') ($(LITERAL '$(LPAREN)') $(RULE templateArgumentList)? $(LITERAL '$(RPAREN)')) | $(RULE templateSingleArgument)
     * ;)
     */
    boolean parseTemplateArguments() {
        final Marker m = builder.mark();
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
    boolean parseTemplateDeclaration(Marker m) {
        if (builder.getTokenType() != KW_TEMPLATE)
            return false;
        final Marker bookmark = builder.mark();
        if (!parseTemplateDeclarationCommon()) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        m.done(TEMPLATE_DECLARATION);
        return true;
    }

    private boolean parseTemplateDeclarationCommon() {
        assert builder.getTokenType() == KW_TEMPLATE;
        builder.advanceLexer();
        if (builder.getTokenType() != ID) {
            return false;
        }
        advance();
        if (!parseTemplateParameters()) {
            return true;
        }
        if (currentIs(KW_IF))
            if (!parseConstraint()) {
                return true;
            }
        final IElementType start = expect(OP_BRACES_LEFT);
        if (start == null) {
            return true;
        }
        while (!builder.eof() && builder.getTokenType() != OP_BRACES_RIGHT) {
            if (!parseDeclDef()) {
                Marker recovery = builder.mark();
                while (!builder.eof() && builder.getTokenType() != OP_BRACES_RIGHT && builder.getTokenType() != OP_SCOLON)
                    builder.advanceLexer();
                if (builder.getTokenType() == OP_SCOLON)
                    builder.advanceLexer();
                recovery.error("Unable to parse this declaration");
            }
        }
        expect(OP_BRACES_RIGHT);
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
        final Marker m = builder.mark();
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, TEMPLATE_INSTANCE);
            return false;
        }
        if (!currentIs(OP_NOT)) {
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
     * Parses a TemplateMixin
     * <p>
     * $(GRAMMAR $(RULEDEF templateMixinExpression):
     * $(LITERAL 'mixin') $(RULE mixinTemplateName) $(RULE templateArguments)? $(LITERAL Identifier)?
     * ;)
     */
    boolean parseTemplateMixin(Marker m) {
        if (builder.getTokenType() != KW_MIXIN)
            return false;
        final Marker bookmark = builder.mark();
        builder.advanceLexer();
        if (!parseMixinTemplateName()) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        if (currentIs(OP_NOT)) {
            if (!parseTemplateArguments()) {
                m.done(TEMPLATE_MIXIN);
                return true;
            }
        }
        if (currentIs(ID))
            advance();
        expect(OP_SCOLON);
        m.done(TEMPLATE_MIXIN);
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
        final Marker m = builder.mark();
        final IElementType i = current();
        if (i == KW_ALIAS) {
            if (!parseTemplateAliasParameter()) {
                cleanup(m, TEMPLATE_PARAMETER);
                return false;
            }
        } else if (i == ID) {
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
        } else if (i == KW_THIS) {
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
        final Marker marker = builder.mark();
        while (moreTokens()) {
            if (!parseTemplateParameter()) {
                cleanup(marker, TEMPLATE_PARAMETER_LIST);
                return false;
            }
            if (currentIs(OP_COMMA)) {
                advance();
                if (currentIsOneOf(OP_PAR_RIGHT, OP_BRACES_RIGHT, OP_BRACKET_RIGHT))
                    break;
            } else
                break;
        }
        exit_section_modified(builder, marker, TEMPLATE_PARAMETER_LIST, true);
        return true;
    }

    /**
     * Parses TemplateParameters
     * <p>
     * $(GRAMMAR $(RULEDEF templateParameters):
     * $(LITERAL '$(LPAREN)') $(RULE templateParameterList)? $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseTemplateParameters() {
        final Marker m = builder.mark();
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
        final Marker m = builder.mark();
        if (!moreTokens()) {
            error("template argument expected instead of EOF");
            exit_section_modified(builder, m, TEMPLATE_SINGLE_ARGUMENT, true);
            return false;
        }
        final IElementType i = current();
        if (i == KW_THIS || i == ID || isLiteral(i)) {
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
        final Marker m = builder.mark();
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
        final Marker m = builder.mark();
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
        final Marker m = builder.mark();
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, TEMPLATE_TYPE_PARAMETER);
            return false;
        }
        if (currentIs(OP_COLON)) {
            advance();
            if (!parseType()) {
                cleanup(m, TEMPLATE_TYPE_PARAMETER);
                return false;
            }
        }
        if (currentIs(OP_EQ)) {
            advance();
            if (!parseType()) {
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
        final Marker m = builder.mark();
        if (!parseType()) {
            cleanup(m, TEMPLATE_VALUE_PARAMETER);
            return false;
        }
        if (!tokenCheck(ID)) {
            cleanup(m, TEMPLATE_VALUE_PARAMETER);
            return false;
        }
        if (currentIs(OP_COLON)) {
            advance();
            if (parseAssignExpression() == null) {
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
        final Marker m = builder.mark();
        expect(OP_EQ);
        final IElementType i = current();
        if (isMagicKeywordLiteral(i)) {
            advance();
        } else {
            if (parseAssignExpression() == null) {
                cleanup(m, TEMPLATE_VALUE_PARAMETER_DEFAULT);
                return false;
            }

        }
        exit_section_modified(builder, m, TEMPLATE_VALUE_PARAMETER_DEFAULT, true);
        return true;
    }

    boolean isMagicKeywordLiteral(IElementType i) {
        return i == KW___FILE__ || i == KW___FILE_FULL_PATH__ || i == KW___MODULE__ || i == KW___LINE__ || i == KW___FUNCTION__ || i == KW___PRETTY_FUNCTION__;
    }

    /**
     * Parses a TernaryExpression (called ConditionalExpression in specs)
     * <p>
     * $(GRAMMAR $(RULEDEF ternaryExpression):
     * $(RULE orOrExpression) ($(LITERAL '?') $(RULE expression) $(LITERAL ':') $(RULE ternaryExpression))?
     * ;)
     */
    Marker parseTernaryExpression() {
        Marker orOrExpression = parseOrOrExpression();
        if (orOrExpression == null) {
            return null;
        }
        if (currentIs(OP_QUEST)) {
            final Marker m = orOrExpression.precede();
            advance();
            if (!parseExpression()) {
                cleanup(m, TERNARY_EXPRESSION);
                return null;
            }
            final IElementType colon = expect(OP_COLON);
            if (colon == null) {
                cleanup(m, TERNARY_EXPRESSION);
                return null;
            }
            if (parseTernaryExpression() == null) {
                cleanup(m, TERNARY_EXPRESSION);
                return null;
            }
            exit_section_modified(builder, m, TERNARY_EXPRESSION, true);
            return m;
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
    Marker parseThrowExpression() {
        final Marker m = builder.mark();
        expect(KW_THROW);
        if (parseAssignExpression() == null) {
            cleanup(m, THROW_EXPRESSION);
            return null;
        }
        exit_section_modified(builder, m, THROW_EXPRESSION, true);
        return m;
    }

    /**
     * Parses an TraitsExpression
     * <p>
     * $(GRAMMAR $(RULEDEF traitsExpression):
     * $(LITERAL '___traits') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL ',') $(RULE TemplateArgumentList) $(LITERAL '$(RPAREN)')
     * ;)
     */
    Marker parseTraitsExpression() {
        final Marker m = builder.mark();
        if (!tokenCheck(KW___TRAITS)) {
            cleanup(m, TRAITS_EXPRESSION);
            return null;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            cleanup(m, TRAITS_EXPRESSION);
            return null;
        }
        final IElementType ident = expect(ID);
        if (ident == null) {
            cleanup(m, TRAITS_EXPRESSION);
            return null;
        }
        if (currentIs(OP_COMMA)) {
            advance();
            if (!(parseTemplateArgumentList())) {
                cleanup(m, TRAITS_EXPRESSION);
                return null;
            }
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            cleanup(m, TRAITS_EXPRESSION);
            return null;
        }
        exit_section_modified(builder, m, TRAITS_EXPRESSION, true);
        return m;
    }

    /**
     * Parses a TryStatement
     * <p>
     * $(GRAMMAR $(RULEDEF tryStatement):
     * $(LITERAL 'try') $(RULE scopeStatement) ($(RULE catches) | $(RULE catches) $(RULE finally) | $(RULE finally))
     * ;)
     */
    boolean parseTryStatement() {
        final Marker m = builder.mark();
        expect(KW_TRY);
        if (!parseScopeStatement()) {
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

    boolean parseType() {
        return parseType(false);
    }

    /**
     * Parses a Type
     * <p>
     * $(GRAMMAR $(RULEDEF type):
     * $(RULE typeConstructors)? $(RULE type2) $(RULE typeSuffix)*
     * ;)
     */
    boolean parseType(boolean inNewExpression) {
        final Marker m = builder.mark();
        if (!moreTokens()) {
            error("type expected");
            exit_section_modified(builder, m, TYPE, true);
            return false;
        }
        final IElementType i = current();
        if (isTypeCtor(i)) {
            if (!peekIs(OP_PAR_LEFT))
                if (!parseTypeConstructors()) {
                    cleanup(m, TYPE);
                    return false;
                }
        }
        if (!parseBasicType()) {
            cleanup(m, TYPE);
            return false;
        }
        while (moreTokens()) {
            final IElementType i1 = current();

            if (i1 == OP_BRACKET_LEFT) {
                Marker bookmark = builder.mark();
                if(inNewExpression) {
                    builder.advanceLexer();
                    if (parseAssignExpression() != null && builder.getTokenType() == OP_BRACES_RIGHT) {
                        builder.advanceLexer();
                        if (builder.getTokenType() != OP_BRACKET_LEFT && builder.getTokenType() != OP_ASTERISK
                            && builder.getTokenType() != KW_DELEGATE && builder.getTokenType() != KW_FUNCTION
                            && builder.getTokenType() != OP_PAR_LEFT) {
                            // This one is not a type prefix but part of the parent newExpression
                            bookmark.rollbackTo();
                            break;
                        }
                    }
                }
                bookmark.rollbackTo();
                if (!parseTypeSuffix()) {
                    cleanup(m, TYPE);
                    return false;
                }
            } else if (i1 == OP_ASTERISK || i1 == KW_DELEGATE || i1 == KW_FUNCTION) {
                if (!parseTypeSuffix()) {
                    cleanup(m, TYPE);
                    return false;
                }
            } else {
                break;
            }
        }
        exit_section_modified(builder, m, TYPE, true);
        return true;
    }

    void parseTypeSuffixes() {
        while (!builder.eof()) {
            if (!parseTypeSuffix())
                break;
        }
    }

    /**
     * Parses a BasicType
     * <p>
     * $(GRAMMAR $(RULEDEF basicType):
     *   $(RULE builtinType)
     * | $(RULE qualifiedIdentifier)
     * | $(LITERAL 'super') $(LITERAL '.') $(RULE qualifiedIdentifier)
     * | $(LITERAL 'this') $(LITERAL '.') $(RULE qualifiedIdentifier)
     * | $(RULE typeofExpression) ($(LITERAL '.') $(RULE qualifiedIdentifier))?
     * | $(RULE typeConstructor) $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL '$(RPAREN)')
     * | $(RULE traitsExpression)
     * | $(RULE vector)
     * | $(RULE mixinExpression)
     * ;)
     */
    boolean parseBasicType() {
        final Marker m = builder.mark();
        if (!moreTokens()) {
            error("basic type expected instead of EOF");
            exit_section_modified(builder, m, BASIC_TYPE, true);
            return false;
        }
        if (builder.getTokenType() == OP_DOT) {
            builder.advanceLexer();
        }
        final IElementType i = builder.getTokenType();
        if (i == ID) {
            if (!parseQualifiedIdentifier()) {
                cleanup(m, BASIC_TYPE);
                return false;
            }
        } else if (isBasicType(i)) {
            parseBuiltinType();
        } else if (i == KW_SUPER || i == KW_THIS) {
            // note: super can be removed but `this` can be an alias to an instance.
            advance();
            if (builder.getTokenType() != OP_DOT) {
                cleanup(m, BASIC_TYPE);
                return false;
            }
            advance();
            if (!parseQualifiedIdentifier()) {
                cleanup(m, BASIC_TYPE);
                return false;
            }
        } else if (i == KW___TRAITS) {
            if (parseTraitsExpression() != null) {
                cleanup(m, BASIC_TYPE);
                return false;
            }
        } else if (i == KW_TYPEOF) {
            if (parseTypeofExpression() == null) {
                cleanup(m, BASIC_TYPE);
                return false;
            }
            if (currentIs(OP_DOT)) {
                advance();
                if (!parseQualifiedIdentifier()) {
                    cleanup(m, BASIC_TYPE);
                    return false;
                }
            }
        } else if (i == KW_MIXIN) {
            if (parseMixinType() != null) {
                cleanup(m, BASIC_TYPE);
                return false;
            }
        } else if (i == KW_CONST || i == KW_IMMUTABLE || i == KW_INOUT || i == KW_SHARED) {
            advance();
            if (!tokenCheck(OP_PAR_LEFT)) {
                cleanup(m, BASIC_TYPE);
                return false;
            }
            if (!(parseType())) {
                cleanup(m, BASIC_TYPE);
                return false;
            }
            if (!tokenCheck(OP_PAR_RIGHT)) {
                cleanup(m, BASIC_TYPE);
                return false;
            }
        } else if (i == KW___VECTOR) {
            if (!(parseVector())) {
                cleanup(m, BASIC_TYPE);
                return false;
            }
        } else {
            error("Basic type, type constructor, symbol, `typeof`, `__traits`, `__vector` or `mixin` expected");
            exit_section_modified(builder, m, BASIC_TYPE, true);
            return false;
        }
        exit_section_modified(builder, m, BASIC_TYPE, true);
        return true;
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
    boolean parseTypeConstructor() {
        final IElementType i = current();
        if (i == KW_CONST || i == KW_IMMUTABLE || i == KW_INOUT || i == KW_SHARED) {
            if (!peekIs(OP_PAR_LEFT)) {
                advance();
                return true;
            }
        }
        return false;
    }

    /**
     * Parses TypeConstructors
     * <p>
     * $(GRAMMAR $(RULEDEF typeConstructors):
     * $(RULE typeConstructor)+
     * ;)
     */
    boolean parseTypeConstructors() {
        boolean containsConstructors = false;
        while (moreTokens()) {
            if(!parseTypeConstructor())
                break;
            containsConstructors = true;
        }
        return containsConstructors;
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
        final Marker m = builder.mark();
        final IElementType i = current();
        if (i == KW_STRUCT || i == KW_UNION || i == KW_CLASS || i == KW_INTERFACE || i == KW___VECTOR || i == KW_ENUM || i == KW_FUNCTION || i == KW_DELEGATE || i == KW_SUPER || i == KW_CONST || i == KW_IMMUTABLE || i == KW_INOUT || i == KW_SHARED || i == KW_RETURN || i == KW___PARAMETERS || i == KW_MODULE || i == KW_PACKAGE) {
            if (peekIsOneOf(OP_PAR_RIGHT, OP_COMMA)) {
                advance();
                exit_section_modified(builder, m, TYPE_SPECIALIZATION, true);
                return true;
            }
            if (!parseType()) {
                cleanup(m, TYPE_SPECIALIZATION);
                return false;
            }
        } else if (!parseType()) {
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
        final Marker m = builder.mark();
        final IElementType i = current();
        if (i == OP_ASTERISK) {
            advance();
            exit_section_modified(builder, m, TYPE_SUFFIX, true);
            return true;
        } else if (i == OP_BRACKET_LEFT) {
            advance();
            if (currentIs(OP_BRACKET_RIGHT)) {
                advance();
                exit_section_modified(builder, m, TYPE_SUFFIX, true);
                return true;
            }
            Marker bookmark = builder.mark();
            final boolean type = parseType();
            if (type && currentIs(OP_BRACKET_RIGHT)) {
                bookmark.drop();
            } else {
                bookmark.rollbackTo();
                if (parseAssignExpression() == null) {
                    cleanup(m, TYPE_SUFFIX);
                    return false;
                }
                if (currentIs(OP_DDOT)) {
                    advance();
                    if (parseAssignExpression() == null) {
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
        } else if (i == KW_DELEGATE || i == KW_FUNCTION) {
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
            m.drop();
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
    Marker parseTypeidExpression() {
        final Marker m = builder.mark();
        expect(KW_TYPEID);
        expect(OP_PAR_LEFT);
        Marker bookmark = builder.mark();
        final boolean t = parseType();
        if (!t || !currentIs(OP_PAR_RIGHT)) {
            bookmark.rollbackTo();
            if (!parseExpression()) {
                cleanup(m, TYPEID_EXPRESSION);
                return null;
            }
        } else {
            bookmark.drop();
        }
        expect(OP_PAR_RIGHT);
        exit_section_modified(builder, m, TYPEID_EXPRESSION, true);
        return m;
    }

    /**
     * Parses a TypeofExpression
     * <p>
     * $(GRAMMAR $(RULEDEF typeofExpression):
     * $(LITERAL 'typeof') $(LITERAL '$(LPAREN)') ($(RULE expression) | $(LITERAL 'return')) $(LITERAL '$(RPAREN)')
     * ;)
     */
    Marker parseTypeofExpression() {
        final Marker m = builder.mark();
        expect(KW_TYPEOF);
        expect(OP_PAR_LEFT);
        if (currentIs(KW_RETURN))
            advance();
        else if (!parseExpression()) {
            cleanup(m, TYPEOF_EXPRESSION);
            return null;
        }
        expect(OP_PAR_RIGHT);
        exit_section_modified(builder, m, TYPEOF_EXPRESSION, true);
        return m;
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
    Marker parseUnaryExpression() {
        final IElementType i = current();
        if (i == OP_AND || i == OP_NOT || i == OP_ASTERISK || i == OP_PLUS || i == OP_MINUS || i == OP_TILDA || i == OP_PLUS_PLUS || i == OP_MINUS_MINUS) {
            Marker m = builder.mark();
            advance();
            if (parseUnaryExpression() == null) {
                cleanup(m, UNARY_EXPRESSION);
                return null;
            }
            exit_section_modified(builder, m, UNARY_EXPRESSION, true);
            return m;
        } else if (i == KW_DELETE) {
            return parseDeleteExpression();
        } else if (i == KW_CAST) {
            return parseCastExpression();
        } else if (i == KW_THROW) {
            return parseThrowExpression();
        }
        return parsePowExpression();
    }

    Marker parsePostfixExpression_terminal() {
        Marker m = parsePrimaryExpression();
        if (m != null)
            return m;
        // TypeCtor
        final IElementType i = current();
        if (isTypeCtor(i)) {
            m = builder.mark();
            builder.advanceLexer();
            parseBasicType();
            if (expect(OP_PAR_LEFT) == null) {
                m.rollbackTo();
                return null;
            }
            parseArgumentList();
            expect(OP_PAR_RIGHT);
            m.done(POSTFIX_EXPRESSION);
            return m;
        }
        return null;
    }

    Marker parsePostfixExpression_0(Marker m) {
        if (currentIs(OP_DOT)) {
            Marker marker = m.precede();
            builder.advanceLexer();
            Marker bookmark = builder.mark();
            if (currentIs(KW_NEW)) {
                bookmark.drop();
                parseNewExpression();
            }
            else if (parseTemplateInstance()) {
                bookmark.drop();
            } else {
                bookmark.rollbackTo();
                if (builder.getTokenType() == ID) {
                    advance();
                } else {
                    builder.error("Identifier, template instance or new expression expected");
                }
            }
            marker.done(REFERENCE_EXPRESSION);
            return marker;
        }
        if (currentIsOneOf(OP_PLUS_PLUS, OP_MINUS_MINUS)) {
            Marker marker = m.precede();
            builder.advanceLexer();
            marker.done(POSTFIX_EXPRESSION);
            return marker;
        }
        if (currentIs(OP_PAR_LEFT)) {
            Marker marker = m.precede();
            builder.advanceLexer();
            parseArgumentList();
            expect(OP_PAR_RIGHT);
            marker.done(FUNCTION_CALL_EXPRESSION);
            return marker;
        }
        if (currentIs(OP_BRACKET_LEFT)) {
            Marker marker = m.precede();
            parseSliceOrIndexExpression();
            marker.done(ARRAY_ACCESS_EXPRESSION);
            return marker;
        }
        return null;
    }

    void parseSliceOrIndexExpression() {
        Marker m = builder.mark();
        advance();
        if (currentIs(OP_BRACKET_RIGHT)) {
            advance();
            m.done(INDEX_EXPRESSION);
            return;
        }
        if (parseAssignExpression() == null) {
            m.drop();
            expect(OP_BRACKET_RIGHT);
            return;
        }
        while (!builder.eof() && builder.getTokenType() == OP_COMMA) {
            advance();
            if (builder.getTokenType() != OP_BRACKET_RIGHT) {
                parseAssignExpression();
            }
        }
        if (builder.getTokenType() == OP_BRACKET_RIGHT) {
            advance();
            m.done(INDEX_EXPRESSION);
            return;
        }
        while (!builder.eof() && (builder.getTokenType() == OP_COMMA || builder.getTokenType() == OP_DDOT)) {
            advance();
            parseAssignExpression();
        }
        expect(OP_BRACKET_RIGHT);
        m.done(INDEX_EXPRESSION);
    }

    Marker parsePostfixExpression() {
        Marker m = parsePostfixExpression_terminal();
        if (m == null)
            return m;
        while (true) {
            Marker marker = parsePostfixExpression_0(m);
            if (marker == null)
                break;
            m = marker;
        }
        return m;
    }

    /**
     * Parses an UnionDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF unionDeclaration):
     *   $(LITERAL 'union') $(LITERAL Identifier) ($(RULE templateParameters) $(RULE constraint)?)? ($(RULE structBody) | $(LITERAL ';'))
     * | $(LITERAL 'union') $(RULE structBody)
     * ;)
     */
    boolean parseUnionDeclaration(Marker m) {
        if (builder.getTokenType() != KW_UNION)
            return false;
        builder.advanceLexer();
        if (currentIs(ID)) {
            advance();
            if (currentIs(OP_PAR_LEFT)) {
                if (!parseTemplateParameters()) {
                    m.done(UNION_DECLARATION);
                    return true;
                }
                if (currentIs(KW_IF))
                    if (!parseConstraint()) {
                        m.done(UNION_DECLARATION);
                        return true;
                    }
            }
        }
        if (currentIs(OP_SCOLON))
            advance();
        else if (!parseStructBody()) {
            m.done(UNION_DECLARATION);
            return true;
        }
        m.done(UNION_DECLARATION);
        return true;
    }

    /**
     * Parses a Unittest
     * <p>
     * $(GRAMMAR $(RULEDEF unittest):
     * $(LITERAL 'unittest') $(RULE blockStatement)
     * ;)
     */
    boolean parseUnittest(Marker marker) {
        if (builder.getTokenType() != KW_UNITTEST)
            return false;
        builder.advanceLexer();
        parseBlockStatement();
        marker.done(UNITTEST);
        return true;
    }

    /**
     * Parses a VariableDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF variableDeclaration):
     *   $(RULE storageClass)* $(RULE type) $(RULE declarator) ($(LITERAL ',') $(RULE declarator))* $(LITERAL ';')
     * | $(RULE autoDeclaration)
     * $(GRAMMAR $(RULEDEF autoDeclaration):
     * $(RULE storageClass)+  $(RULE autoDeclarationPart) ($(LITERAL ',') $(RULE autoDeclarationPart))* $(LITERAL ';')
     * ;)
     */
    boolean parseVariableDeclaration(Marker m)
    {
        Marker bookmark = builder.mark();
        boolean hasStorageClass = false;
        while (!builder.eof()) {
            if (!parseStorageClass())
                break;
            hasStorageClass = true;
        }
        Marker bookmark2 = builder.mark();
        boolean parsedVariable = parseNonAutoVariableDeclaration();
        if (!parsedVariable && hasStorageClass) {
            bookmark2.rollbackTo();
            // Then it’s an auto declaration
            parseAutoAssignments();
            if (builder.getTokenType() != OP_SCOLON) {
                bookmark.rollbackTo();
                return false;
            }
            bookmark.drop();
            builder.advanceLexer();
            m.done(AUTO_DECLARATION);
            return true;
        } else {
            bookmark2.drop();
        }
        if (!parsedVariable) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        expect(OP_SCOLON);
        m.done(VARIABLE_DECLARATION);
        return true;
    }

    private boolean parseNonAutoVariableDeclaration() {
        if (!parseBasicType()) {
            return false;
        }
        parseTypeSuffixes();
        boolean hasDeclarator = false;
        while (!builder.eof()) {
            if(!parseIdentifierInitializer())
                break;
            hasDeclarator = true;
            if (currentIs(OP_COMMA))
                advance();
            else
                break;
        }
        return hasDeclarator;
    }

    /**
     * Parses a Vector
     * <p>
     * $(GRAMMAR $(RULEDEF vector):
     * $(LITERAL '___vector') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseVector() {
        final Marker marker = builder.mark();
        if (expect(KW___VECTOR) == null) {
            cleanup(marker, VECTOR);
            return false;
        }
        if (expect(OP_PAR_LEFT) == null) {
            cleanup(marker, VECTOR);
            return false;
        }
        if(!parseType()) {
            cleanup(marker, VECTOR);
            return false;
        }
        if (expect(OP_PAR_RIGHT) == null) {
            cleanup(marker, VECTOR);
            return false;
        }
        exit_section_modified(builder, marker, VECTOR, true);
        return true;
    }

    /**
     * Parses a VersionCondition
     * <p>
     * $(GRAMMAR $(RULEDEF versionCondition):
     * $(LITERAL 'version') $(LITERAL '$(LPAREN)') ($(LITERAL IntegerLiteral) | $(LITERAL Identifier) | $(LITERAL 'unittest') | $(LITERAL 'assert')) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseVersionCondition() {
        final Marker m = builder.mark();
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
    boolean parseVersionSpecification(Marker m) {
        if (builder.getTokenType() != KW_VERSION)
            return false;
        final Marker bookmark = builder.mark();
        builder.advanceLexer();
        if (!tokenCheck(OP_EQ)) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.drop();
        if (!currentIsOneOf(ID, INTEGER_LITERAL)) { // Note: that using integer for version is deprecated since 2.101.0
            error("Identifier or integer literal expected");
            m.done(VERSION_SPECIFICATION);
            return true;
        }
        advance();
        expect(OP_SCOLON);
        m.done(VERSION_SPECIFICATION);
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
        final Marker m = builder.mark();
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
            error("Statement expected");
            exit_section_modified(builder, m, WHILE_STATEMENT, true);
            return true; // this line makes DCD better
        }
        if (!parseScopeStatement()) {
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
        final Marker marker = builder.mark();
        if (expect(KW_WITH) == null) {
            cleanup(marker, WITH_STATEMENT);
            return false;
        }
        if (expect(OP_PAR_LEFT) == null) {
            cleanup(marker, WITH_STATEMENT);
            return false;
        }
        if(!parseExpression()) {
            cleanup(marker, WITH_STATEMENT);
            return false;
        }
        if (expect(OP_PAR_RIGHT) == null) {
            cleanup(marker, WITH_STATEMENT);
            return false;
        }
        if(!parseScopeStatement()) {
            cleanup(marker, WITH_STATEMENT);
            return false;
        }
        exit_section_modified(builder, marker, WITH_STATEMENT, true);
        return true;
    }

    /**
     * Parses an XorExpression
     * <p>
     * $(GRAMMAR $(RULEDEF xorExpression):
     * $(RULE andExpression)
     * | $(RULE xorExpression) $(LITERAL '^') $(RULE andExpression)
     * ;)
     */
    Marker parseXorExpression() {
        Marker marker = parseAndExpression();
        if (marker == null)
            return null;
        while (currentIs(OP_XOR)) {
            marker = marker.precede();
            builder.advanceLexer();
            if (parseAndExpression() == null) {
                cleanup(marker, XOR_EXPRESSION);
                return null;
            }
            marker.done(XOR_EXPRESSION);
        }
        return marker;
    }

    /**
     * Returns: true if there are more tokens
     */
    boolean moreTokens() {
        return !builder.eof();
    }

    boolean isCastQualifier() {
        final IElementType i = current();
        if (i == KW_CONST) {
            if (peekIs(OP_PAR_RIGHT))
                return true;
            return startsWith(KW_CONST, KW_SHARED, OP_PAR_RIGHT);
        } else if (i == KW_IMMUTABLE) {
            return peekIs(OP_PAR_RIGHT);
        } else if (i == KW_INOUT) {
            if (peekIs(OP_PAR_RIGHT))
                return true;
            return startsWith(KW_INOUT, KW_SHARED, OP_PAR_RIGHT);
        } else if (i == KW_SHARED) {
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
        if (cachedAAChecks.containsKey(builder.getCurrentOffset()))
            return cachedAAChecks.get(builder.getCurrentOffset());
        int currentIndex = builder.getCurrentOffset();
        Marker bookmark = builder.mark();
        advance();
        final boolean result = !currentIs(OP_BRACKET_RIGHT) && parseExpression() && currentIs(OP_COLON);
        cachedAAChecks.put(currentIndex, result);
        bookmark.rollbackTo();
        return result;
    }

    /// Only use this in template parameter parsing
    boolean isType() {
        if (!moreTokens()) return false;
        Marker bookmark = builder.mark();
        if (!parseType()) {
            bookmark.rollbackTo();
            return false;
        }
        bookmark.rollbackTo();
        return currentIsOneOf(OP_COMMA, OP_PAR_RIGHT, OP_EQ);
    }

    private boolean isStorageClass() {
        if (!moreTokens()) return false;
        final IElementType i = current();
        if (i == KW_CONST || i == KW_IMMUTABLE || i == KW_INOUT || i == KW_SHARED) {
            return !peekIs(OP_PAR_LEFT);
        } else
            return i == OP_AT || i == KW_DEPRECATED || i == KW_ABSTRACT || i == KW_ALIGN || i == KW_AUTO || i == KW_ENUM || i == KW_EXTERN || i == KW_FINAL || i == KW_NOTHROW || i == KW_OVERRIDE || i == KW_PURE || i == KW_REF || i == KW___GSHARED || i == KW_SCOPE || i == KW_STATIC || i == KW_SYNCHRONIZED;
    }

    boolean isAttribute() {
        if (!moreTokens()) return false;
        final IElementType i = current();
        if (i == KW_CONST || i == KW_IMMUTABLE || i == KW_INOUT || i == KW_SCOPE) {
            return !peekIs(OP_PAR_LEFT);
        } else if (i == KW_STATIC) {
            return !peekIsOneOf(KW_ASSERT, KW_THIS, KW_IF, OP_TILDA, KW_FOREACH, KW_FOREACH_REVERSE);
        } else if (i == KW_SHARED) {
            return !(startsWith(KW_SHARED, KW_STATIC, KW_THIS) || startsWith(KW_SHARED, KW_STATIC, OP_TILDA) || peekIs(OP_PAR_LEFT));
        } else if (i == KW_PRAGMA) {
            Marker bookmark = builder.mark();
            advance();
            final IElementType past = peekPastParens();
            if (past == null || past == OP_SCOLON) {
                bookmark.rollbackTo();
                return false;
            }
            bookmark.rollbackTo();
            return true;
        } else
            return i == KW_DEPRECATED || i == KW_PRIVATE || i == KW_PACKAGE || i == KW_PROTECTED || i == KW_PUBLIC || i == KW_EXPORT || i == KW_FINAL || i == KW_SYNCHRONIZED || i == KW_OVERRIDE || i == KW_ABSTRACT || i == KW_AUTO || i == KW___GSHARED || i == KW_PURE || i == KW_NOTHROW || i == OP_AT || i == KW_REF || i == KW_EXTERN || i == KW_ALIGN;
    }

    private boolean isMemberFunctionAttribute(final IElementType t) {
        return t == KW_CONST || t == KW_IMMUTABLE || t == KW_INOUT || t == KW_SHARED || t == OP_AT || t == KW_PURE || t == KW_NOTHROW || t == KW_RETURN || t == KW_SCOPE;
    }

    private boolean isTypeCtor(final IElementType t) {
        return t == KW_CONST || t == KW_IMMUTABLE || t == KW_INOUT || t == KW_SHARED;
    }

    private boolean currentIsMemberFunctionAttribute() {
        return moreTokens() && isMemberFunctionAttribute(current());
    }

    private void error(final String message) {
        builder.error(message);
        while (moreTokens()) {
            if (currentIsOneOf(OP_SCOLON, OP_BRACES_RIGHT,
                OP_PAR_RIGHT, OP_BRACKET_RIGHT)) {
                advance();
                break;
            } else
                advance();
        }
    }

    private void skip(final IElementType o, final IElementType c)
    {
        assert (currentIs(o));
        advance();
        int depth = 1;
        while (moreTokens()) {
            if (builder.getTokenType() == c) {
                advance();
                depth--;
                if (depth <= 0)
                    return;
            } else if (builder.getTokenType() == o) {
                depth++;
                advance();
            } else {
                advance();
            }
        }
    }

    private void skipBrackets() {
        skip(OP_BRACKET_LEFT, OP_BRACKET_RIGHT);
    }

    private IElementType peekPast(final IElementType o, final IElementType c)
    {
        if (builder.eof())
            return null;
        int depth = 1;
        var marker = builder.mark();
        builder.advanceLexer();
        while (builder.getTokenType() != null) {
            if (builder.getTokenType() == o) {
                ++depth;
            } else if (builder.getTokenType() == c) {
                --depth;
            }
            builder.advanceLexer();
            if (depth <= 0)
                break;
        }
        var token = builder.getTokenType();
        marker.rollbackTo();
        return depth == 0 ? token : null;
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
        return builder.lookAhead(offset) == t;
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
        final IElementType needle = builder.lookAhead(1);
        if (needle == null) return false;
        for (final IElementType type : types) {
            if (type == needle) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a token of the specified type if it was the next token, otherwise
     * calls the error function and returns null. Advances the lexer by one token.
     */
    private IElementType expect(final IElementType tok) {
        if (!builder.eof() && builder.getTokenType() == tok) {
            return advance();
        } else {
            final String tokenString = tok.getDebugName();
            final String token = (!builder.eof() ? (builder.getTokenType().toString()) : "EOF");
            builder.error("Expected " + tokenString + " instead of " + token);
            return null;
        }
    }

    /**
     * Returns: the _current token
     */
    @Nullable
    private IElementType current() {
        return builder.getTokenType();
    }

    /**
     * Advances to the next token and returns the current token
     */
    private IElementType advance() {
        IElementType token = builder.getTokenType();
        builder.advanceLexer();
        return token;
    }

    /**
     * Returns: true if the current token has the given type
     */
    private boolean currentIs(final IElementType type) {
        return !builder.eof() && builder.getTokenType() == type;
    }

    /**
     * Returns: true if the current token is one of the given types
     */
    private boolean currentIsOneOf(final IElementType... types) {
        if (builder.eof()) return false;

        final IElementType curr = current();

        if(curr != null && types != null) {
            return Arrays.asList(types).contains(curr);
        }
        return false;
    }

    private boolean startsWith(final IElementType... types) {
        for (int i = 0; i < types.length; ++i) {
            IElementType token = builder.lookAhead(i);
            if (token == null || token != types[i])
                return false;
        }
        return true;
    }

    private boolean tokenCheck(final IElementType tok) {
        return expect(tok) != null;
    }

    private boolean parseStaticCtorDtorCommon() {
        if (!tokenCheck(KW_THIS)) {
            return false;
        }
        if (!tokenCheck(OP_PAR_LEFT)) {
            return true;
        }
        if (!tokenCheck(OP_PAR_RIGHT)) {
            return true;
        }
        while (moreTokens() && !currentIsOneOf(OP_BRACES_LEFT, KW_IN, KW_OUT,
            KW_DO, OP_SCOLON)) {
            if (!(parseMemberFunctionAttribute())) {
                return true;
            }
        }
        if (currentIs(OP_SCOLON))
            advance();
        else {
            return parseFunctionBody();
        }
        return true;
    }

    void parseInterfaceOrClass() {
        final IElementType ident = expect(ID);
        if (ident == null) {
            return;
        }
        if (currentIs(OP_SCOLON)) {
            advance();
            return;
        }
        if (currentIs(OP_BRACES_LEFT)) {
            parseStructBody();
            return;
        }
        if (currentIs(OP_PAR_LEFT)) {
            if (!parseTemplateParameters()) {
                return;
            }
            if (currentIs(OP_SCOLON)) {
                advance();
                return;
            }
            constraint(false);
            return;
        }
        if (currentIs(OP_COLON)) {
            baseClassList();
            return;
        }
        parseStructBody();
    }

    private boolean emptyBody() {
        advance();
        return true;
    }

    private boolean baseClassList() {
        advance(); // :
        if (!parseBaseClassList()) {
            return false;
        }
        if (currentIs(KW_IF)) {
            return constraint(true);
        }
        return parseStructBody();
    }

    private boolean constraint(final boolean baseClassListQ) {
        if (currentIs(KW_IF)) {
            if (!parseConstraint()) {
                return false;
            }
        }
        if (baseClassListQ) {
            if (currentIs(OP_BRACES_LEFT)) {
                return parseStructBody();
            } else if (currentIs(OP_SCOLON)) {
                return emptyBody();
            } else {
                error("Struct body or ';' expected");
                return false;
            }
        }
        if (currentIs(OP_COLON)) {
            return baseClassList();
        }
        if (currentIs(KW_IF)) {
            return constraint(false);
        }
        if (currentIs(OP_SCOLON)) {
            return emptyBody();
        }
        if (currentIs(OP_COLON)) {
            return baseClassList();
        }
        return parseStructBody();
    }

}
