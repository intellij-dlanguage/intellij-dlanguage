package net.masterthought.dlanguage.parser;

import com.google.common.collect.Sets;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.psi.tree.IElementType;
import net.masterthought.dlanguage.psi.DLanguageTokenType;
import net.masterthought.dlanguage.psi.DLanguageTypes;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.intellij.lang.parser.GeneratedParserUtilBase.enter_section_;
import static com.intellij.lang.parser.GeneratedParserUtilBase.exit_section_;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * D DLangParser.
 * <p>
 * It == sometimes useful to sub-class DLangParser to skip over things that are not
 * interesting. For example, DCD skips over function bodies when caching symbols
 * from imported files.
 */
@SuppressWarnings({"WeakerAccess", "SameParameterValue", "ConstantConditions"})
class DLangParser {
    // This list MUST BE MAINTAINED IN SORTED ORDER.
    static final String[] REGISTER_NAMES = {
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
    static HashMap<String, Token.IdType> tokenTypeIndex = new HashMap<>();

    static {
        tokenTypeIndex.put("scriptLine", new Token.IdType(DLanguageTypes.SHEBANG));
        tokenTypeIndex.put("identifier", new Token.IdType(DLanguageTypes.ID));
        tokenTypeIndex.put("__DATE__", new Token.IdType(DLanguageTypes.KW___DATE__));//todo
        tokenTypeIndex.put("__EOF__", new Token.IdType(DLanguageTypes.KW___EOF__));
        tokenTypeIndex.put("__FILE__", new Token.IdType(DLanguageTypes.KW___FILE__));
        tokenTypeIndex.put("__FILE_FULL_PATH__", new Token.IdType(DLanguageTypes.KW___FILE_FULL_PATH__));
        tokenTypeIndex.put("__FUNCTION__", new Token.IdType(DLanguageTypes.KW___FUNCTION__));
        tokenTypeIndex.put("__gshared", new Token.IdType(DLanguageTypes.KW___GSHARED));
        tokenTypeIndex.put("__LINE__", new Token.IdType(DLanguageTypes.KW___LINE__));
        tokenTypeIndex.put("__MODULE__", new Token.IdType(DLanguageTypes.KW___MODULE__));
        tokenTypeIndex.put("__parameters", new Token.IdType(DLanguageTypes.KW___PARAMETERS));
        tokenTypeIndex.put("__PRETTY_FUNCTION__", new Token.IdType(DLanguageTypes.KW___PRETTY_FUNCTION__));
        tokenTypeIndex.put("__TIME__", new Token.IdType(DLanguageTypes.KW___TIME__));
        tokenTypeIndex.put("__TIMESTAMP__", new Token.IdType(DLanguageTypes.KW___TIMESTAMP__));
        tokenTypeIndex.put("__traits", new Token.IdType(DLanguageTypes.KW___TRAITS));
        tokenTypeIndex.put("__vector", new Token.IdType(DLanguageTypes.KW___VECTOR));
        tokenTypeIndex.put("__VENDOR__", new Token.IdType(DLanguageTypes.KW___VENDOR__));
        tokenTypeIndex.put("__VERSION__", new Token.IdType(DLanguageTypes.KW___VERSION__));
        tokenTypeIndex.put("", new Token.IdType(DLanguageTypes.SPECIAL_EMPTY_TOKEN));
        tokenTypeIndex.put("floatLiteral", new Token.IdType(DLanguageTypes.FLOAT_LITERAL));
        tokenTypeIndex.put("doubleLiteral", new Token.IdType(DLanguageTypes.FLOAT_LITERAL));//todo
        tokenTypeIndex.put("idoubleLiteral", new Token.IdType(DLanguageTypes.FLOAT_LITERAL));//todo
        tokenTypeIndex.put("ifloatLiteral", new Token.IdType(DLanguageTypes.FLOAT_LITERAL));//todo
        tokenTypeIndex.put("realLiteral", new Token.IdType(DLanguageTypes.FLOAT_LITERAL));//todo
        tokenTypeIndex.put("irealLiteral", new Token.IdType(DLanguageTypes.FLOAT_LITERAL));//todo
        tokenTypeIndex.put("intLiteral", new Token.IdType(DLanguageTypes.INTEGER_LITERAL));
        tokenTypeIndex.put("uintLiteral", new Token.IdType(DLanguageTypes.INTEGER_LITERAL));
        tokenTypeIndex.put("longLiteral", new Token.IdType(DLanguageTypes.INTEGER_LITERAL));
        tokenTypeIndex.put("ulongLiteral", new Token.IdType(DLanguageTypes.INTEGER_LITERAL));
        tokenTypeIndex.put("characterLiteral", new Token.IdType(DLanguageTypes.CHARACTER_LITERAL));
        tokenTypeIndex.put("stringLiteral", new Token.IdType(DLanguageTypes.DOUBLE_QUOTED_STRING));
        tokenTypeIndex.put("dstringLiteral", new Token.IdType(DLanguageTypes.DOUBLE_QUOTED_STRING));//todo create an actual lexer entry for this
        tokenTypeIndex.put("wstringLiteral", new Token.IdType(DLanguageTypes.WYSIWYG_STRING));//todo create an actual lexer entry for this
        tokenTypeIndex.put("typedef", new Token.IdType(DLanguageTypes.ID));//todo create an actual lexer entry for this
    }

    public Token.IdType[] Protections = {tok("export"), tok("package"),
        tok("private"), tok("public"), tok("protected")};
    @NotNull
    PsiBuilder builder;
    /**
     * Current error count
     */
    int errorCount;
    /**
     * Current warning count
     */
    int warningCount;
    /**
     * Name used when reporting warnings and errors
     */
    String fileName;
    /**
     * Tokens to parse
     */
    Token[] tokens;
    /**
     * Allocator used for creating AST nodes
     */
    @Deprecated
    RollbackAllocator allocator;
    int suppressedErrorCount;
    @Deprecated
    int MAX_ERRORS = 50000;
    Map<Integer, Boolean> cachedAAChecks = new HashMap<>();
    int suppressMessages;
    int index;

    public DLangParser(@NotNull PsiBuilder builder) {
        this.errorCount = 0;
        this.warningCount = 0;
        this.fileName = "totally irrelevant";
        this.tokens = getTokens(builder);
        this.allocator = new RollbackAllocator();
        this.suppressedErrorCount = 0;
        this.suppressMessages = 0;
        this.index = 0;
        this.builder = builder;
    }

    private static Token.IdType tok(String tok) {
        if (tokenTypeIndex.get(tok) != null) {
            return tokenTypeIndex.get(tok);
        }
        final IElementType[] matchingTypes = IElementType.enumerate((IElementType type) -> {
            if (type instanceof DLanguageTokenType) {
                return ((DLanguageTokenType) type).getDebugName().equals(tok);
            }
            return false;
        });
        if (matchingTypes.length != 1) {
            throw new IllegalArgumentException("string:" + tok);
        }
        final Token.IdType result = new Token.IdType(matchingTypes[0]);
        tokenTypeIndex.put(tok, result);
        return result;
    }

    private Token[] getTokens(PsiBuilder builder) {
        //todo switch to consume/rollback strategy
        final Marker tokenRollBackMark = builder.mark();
        ArrayList<IElementType> tokens = new ArrayList<>();
        while (true) {
            if (builder.eof()) {
                break;
            }
            tokens.add(builder.getTokenType());
            builder.advanceLexer();
        }
        tokenRollBackMark.rollbackTo();
        Token[] tokenArray = new Token[tokens.size()];
        int i = 0;
        for (IElementType token : tokens) {
            tokenArray[i] = new Token(new Token.IdType(token));
            i++;
        }

        return tokenArray;
    }

    public void cleanup(@NotNull Marker... markers) {
        for (@NotNull Marker marker : markers) {
            exit_section_(builder, marker, STATIC_IF_CONDITION, false);//the static if could be anything. todo make an actual dummy type for it
        }
    }

    /**
     * Parses an AddExpression.
     * converted
     * $(GRAMMAR $(RULEDEF addExpression):
     * $(RULE mulExpression)
     * | $(RULE addExpression) $(LPAREN)$(LITERAL '+') | $(LITERAL'-') | $(LITERAL'~')$(RPAREN) $(RULE mulExpression)
     * ;)
     */
    boolean parseAddExpression() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker section = enter_section_(builder);
        boolean result = parseLeftAssocBinaryExpression("AddExpression", "MulExpression", tok("+"), tok("-"), tok("~"));
        exit_section_(builder, section, ADD_EXPRESSION_, result);
        return result;
    }

    /**
     * Parses an AliasDeclaration.
     * converted
     * $(GRAMMAR $(RULEDEF aliasDeclaration):
     * $(LITERAL 'alias') $(RULE aliasInitializer) $(LPAREN)$(LITERAL ',') $(RULE aliasInitializer)$(RPAREN)* $(LITERAL ';')
     * | $(LITERAL 'alias') $(RULE storageClass)* $(RULE type) $(RULE identifierList) $(LITERAL ';')
     * | $(LITERAL 'alias') $(RULE storageClass)* $(RULE type) $(RULE identifier) $(LITERAL '(') $(RULE parameters) $(LITERAL ')') $(memberFunctionAttribute)* $(LITERAL ';')
     * ;)
     */
    boolean parseAliasDeclaration() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
        if (!tokenCheck("alias")) {
            cleanup(m);
            return false;
        }
//            node.comment = comment;
//            comment = null;

        if (startsWith(tok("identifier"), tok("=")) || startsWith(tok("identifier"), tok("("))) {
            do {
                if (!parseAliasInitializer()) {
                    cleanup(m);
                    return false;
                }
                if (currentIs(tok(","))) {
                    advance();
                } else {
                    break;
                }
            }
            while (moreTokens());
            if (expect(tok(";")) == null) {
                cleanup(m);
                return false;
            }
        } else {
            while (moreTokens() && isStorageClass()) {
                if (!parseStorageClass()) {
                    cleanup(m);
                    return false;
                }
            }
            if (!parseNodeQ("node.type", "Type")) {
                cleanup(m);
                return false;
            }
            if (!parseNodeQ("node.identifierList", "IdentifierList")) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok("("))) {
                if (!parseNodeQ("node.parameters", "Parameters")) {
                    cleanup(m);
                    return false;
                }
                final Marker memberFunctionAttributes = enter_section_(builder);
                while (moreTokens() && currentIsMemberFunctionAttribute()) {
                    if (!parseMemberFunctionAttribute()) {
                        cleanup(m, memberFunctionAttributes);
                        return false;
                    }
                }
                if (expect(tok(";")) == null) {
                    cleanup(m);
                    return false;
                }
                exit_section_(builder, memberFunctionAttributes, MEMBER_FUNCTION_ATTRIBUTES, true);

            }
        }
        if (expect(tok(";")) == null) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, ALIAS_DECLARATION, true);
        return true;
    }

    private boolean isFunction() {
        if (currentIsOneOf(tok("function"), tok("delegate"), tok("{")))
            return true;
        if (startsWith(tok("identifier"), tok("=>")))
            return true;
        if (currentIs(tok("("))) {
            Token t = peekPastParens();
            if (t != null) {
                if (t.type.equals(tok("=>")) || t.type.equals(tok("{")) || isMemberFunctionAttribute(t.type))
                    return true;
            }
        }
        return false;
    }

    /**
     * Parses an AliasInitializer.
     * <p>
     * $(GRAMMAR $(RULEDEF aliasInitializer):
     * $(LITERAL Identifier) $(RULE templateParameters)? $(LITERAL '=') $(RULE storageClass)* $(RULE type)
     * | $(LITERAL Identifier) $(RULE templateParameters)? $(LITERAL '=') $(RULE functionLiteralExpression)
     * ;)
     */
    boolean parseAliasInitializer() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
        if (!tokenCheck("identifier")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("("))) {
            if (!parseNodeQ("node.templateParameters", "TemplateParameters")) {
                cleanup(m);
                return false;
            }
        }
        if (!tokenCheck("=")) {
            cleanup(m);
            return false;
        }
        if (isFunction()) {
            if (!parseNodeQ("node.functionLiteralExpression", "FunctionLiteralExpression")) {
                cleanup(m);
                return false;
            }
        } else {
            final Marker storageClassesMarker = enter_section_(builder);
            while (moreTokens() && isStorageClass()) {
                if (!parseStorageClass()) {
                    cleanup(m, storageClassesMarker);
                    return false;
                }
            }
            exit_section_(builder, storageClassesMarker, STORAGE_CLASSES, true);//todo get rid of these
            if (!parseNodeQ("node.type", "Type")) {
                cleanup(m);
                return false;
            }
        }
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AliasThisDeclaration,false);
        if (!tokenCheck("alias")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("identifier")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("this")) {
            cleanup(m);
            return false;
        }
        if (expect(tok(";")) == null) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, ALIAS_THIS, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AlignAttribute,false);
        expect(tok("align"));//todo note expect has an error side affect
        if (currentIs(tok("("))) {
            if (!tokenCheck("(")) {
                cleanup(m);
                return false;
            }
            if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                cleanup(m);
                return false;
            }
            if (!tokenCheck(")")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, ALIGN_ATTRIBUTE, true);
        return true;
    }

    /**
     * Parses an AndAndExpression.
     * <p>
     * $(GRAMMAR $(RULEDEF andAndExpression):
     * $(RULE orExpression)
     * | $(RULE andAndExpression) $(LITERAL '&&') $(RULE orExpression)
     * ;)
     */
    boolean parseAndAndExpression() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
        final boolean result = parseLeftAssocBinaryExpression("AndAndExpression", "OrExpression", tok("&&"));
        exit_section_(builder, m, AND_EXPRESSION_, result);//todo new types needed.
        return result;
    }

    /**
     * Parses an AndExpression.
     * <p>
     * $(GRAMMAR $(RULEDEF andExpression):
     * $(RULE cmpExpression)
     * | $(RULE andExpression) $(LITERAL '&') $(RULE cmpExpression)
     * ;)
     */
    boolean parseAndExpression() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
        final boolean result = parseLeftAssocBinaryExpression("AndExpression", "CmpExpression", tok("&"));
        exit_section_(builder, m, AND_EXPRESSION_, result);
        return result;
    }

    /**
     * Parses an ArgumentList.
     * <p>
     * $(GRAMMAR $(RULEDEF argumentList):
     * $(RULE assignExpression) ($(LITERAL ',') $(RULE assignExpression)?)*
     * ;)
     */
    boolean parseArgumentList() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        if (!moreTokens()) {
            error("argument list expected instead of EOF");
            return false;
        }
        final Marker argumentList = enter_section_(builder);
        boolean node = parseCommaSeparatedRule("ArgumentList", "AssignExpression");
        if (!node) {
            cleanup(argumentList);
            return false;
        }
        exit_section_(builder, argumentList, ARGUMENT_LIST, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
//        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Arguments,false);
        if (!tokenCheck("(")) {/*cleanup(m);*/
            return false;
        }
        if (!currentIs(tok(")")))
            if (!parseNodeQ("node.argumentList", "ArgumentList")) {/*cleanup(m)*/
                return false;
            }
        if (!tokenCheck(")")) {/*cleanup(m);*/
            return false;
        }
//            exit_section_(builder,m,ArGUM,true);//todo needs a type
        return true;
    }

    /**
     * Parses an ArrayInitializer.
     * <p>
     * $(GRAMMAR $(RULEDEF arrayInitializer):
     * $(LITERAL '[') $(LITERAL ']')
     * | $(LITERAL '[') $(RULE arrayMemberInitialization) ($(LITERAL ',') $(RULE arrayMemberInitialization)?)* $(LITERAL ']')
     * ;)
     */
    boolean parseArrayInitializer() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ArrayInitializer,false);
        final Marker arrayInit = enter_section_(builder);
        Token open = expect(tok("["));
        if (open == null) {
            cleanup(arrayInit);
            return false;
        }
        while (moreTokens()) {
            if (currentIs(tok("]")))
                break;
            if (!parseArrayMemberInitialization()) {
                cleanup(arrayInit);
                return false;
            }
            if (currentIs(tok(",")))
                advance();
            else
                break;
        }
        Token close = expect(tok("]"));
        if (close == null) {
            cleanup(arrayInit);
            return false;
        }
//            node.endLocation = close.index;
        exit_section_(builder, arrayInit, ARRAY_INITIALIZER, true);
        return true;
    }

    /**
     * Parses an ArrayLiteral.
     * <p>
     * $(GRAMMAR $(RULEDEF arrayLiteral):
     * $(LITERAL '[') $(RULE argumentList)? $(LITERAL ']')
     * ;)
     */
    boolean parseArrayLiteral() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ArrayLiteral,false);
        if (!tokenCheck("[")) {
            cleanup(m);
            return false;
        }
        if (!currentIs(tok("]"))) {
            if (!parseNodeQ("node.argumentList", "ArgumentList")) {
                cleanup(m);
                return false;
            }
        }
        if (!tokenCheck("]")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, ARRAY_LITERAL, true);
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
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ArrayMemberInitialization,false);
        if (current().type.equals(tok("["))) {
            Bookmark b = setBookmark();
            skipBrackets();
            if (currentIs(tok(":"))) {
                if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                    cleanup(m);
                    return false;
                }
                advance(); // :
                if (!parseNodeQ("node.nonVoidInitializer", "NonVoidInitializer")) {
                    cleanup(m);
                    return false;
                }
                exit_section_(builder, m, ARRAY_MEMBER_INITIALIZATION, true);
                return true;
            } else {
                goToBookmark(b);
            }
        }
        if (current().type.equals(tok("{"))) {
            if (!parseNodeQ("node.nonVoidInitializer", "NonVoidInitializer")) {
                cleanup(m);
                return false;
            }
            exit_section_(builder, m, ARRAY_MEMBER_INITIALIZATION, true);
            return true;
        } else {
            boolean assignExpression = parseAssignExpression();
            if (!assignExpression) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok(":"))) {
                advance();
                if (!parseNodeQ("node.nonVoidInitializer", "NonVoidInitializer")) {
                    cleanup(m);
                    return false;
                }
            }
            exit_section_(builder, m, ARRAY_MEMBER_INITIALIZATION, true);
            return true;
        }

    }

    /**
     * Parses an AsmAddExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmAddExp):
     * $(RULE asmMulExp)
     * | $(RULE asmAddExp) ($(LITERAL '+') | $(LITERAL '-')) $(RULE asmMulExp)
     * ;)
     */
    boolean parseAsmAddExp() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_(builder);
        final boolean result = parseLeftAssocBinaryExpression("AsmAddExp", "AsmMulExp", tok("+"), tok("-"));
        exit_section_(builder, m, ASM_ADD_EXP, result);
        return result;
    }

    /**
     * Parses an AsmAndExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmAndExp):
     * $(RULE asmEqualExp)
     * | $(RULE asmAndExp) $(LITERAL '&') $(RULE asmEqualExp)
     * ;)
     */
    boolean parseAsmAndExp() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_(builder);
        final boolean result = parseLeftAssocBinaryExpression("AsmAndExp", "AsmEqualExp", tok("&"));
        exit_section_(builder, m, ASM_AND_EXP, result);
        return result;
    }

    /**
     * Parses an AsmBrExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmBrExp):
     * $(RULE asmUnaExp)
     * | $(RULE asmBrExp)? $(LITERAL '[') $(RULE asmExp) $(LITERAL ']')
     * ;)
     */
    boolean parseAsmBrExp() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            AsmBrExp node = allocator.make!AsmBrExp();
        final Marker m = enter_section_(builder);
        if (currentIs(tok("["))) {
            advance(); // [
            if (!parseNodeQ("node.asmExp", "AsmExp")) {
                cleanup(m);
                return false;
            }
            if (!tokenCheck("]")) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok("["))) {
                while (currentIs(tok("["))) {
                    advance(); // [
                    if (!parseNodeQ("node.asmExp", "AsmExp")) {
                        cleanup(m);
                        return false;
                    }
                    if (!tokenCheck("]")) {
                        cleanup(m);
                        return false;
                    }
                }
            }
        } else {
            if (!parseNodeQ("node.asmUnaExp", "AsmUnaExp")) {
                cleanup(m);
                return false;
            }
            while (currentIs(tok("["))) {
                advance(); // [
                if (!parseNodeQ("node.asmExp", "AsmExp")) {
                    cleanup(m);
                    return false;
                }
                if (!tokenCheck("]")) {
                    cleanup(m);
                    return false;
                }
            }
        }
        exit_section_(builder, m, DLanguageTypes.ASM_BR_EXP, true);
        return true;
    }

    /**
     * Parses an AsmEqualExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmEqualExp):
     * $(RULE asmRelExp)
     * | $(RULE asmEqualExp) ('==' | '!=') $(RULE asmRelExp)
     * ;)
     */
    boolean parseAsmEqualExp() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_(builder);
        final boolean result = parseLeftAssocBinaryExpression("AsmEqualExp", "AsmRelExp", tok("=="), tok("!="));
        exit_section_(builder, m, ASM_EQUAL_EXP, result);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            AsmExp node = allocator.make!AsmExp;
        final Marker m = enter_section_(builder);
        if (!parseNodeQ("node.left", "AsmLogOrExp")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("?"))) {
            advance();
            if (!parseNodeQ("node.middle", "AsmExp")) {
                cleanup(m);
                return false;
            }
            if (!tokenCheck(":")) {
                cleanup(m);
                return false;
            }
            if (!parseNodeQ("node.right", "AsmExp")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, ASM_EXP, true);
        return true;
    }

    /**
     * Parses an AsmInstruction
     * <p>
     * $(GRAMMAR $(RULEDEF asmInstruction):
     * $(LITERAL Identifier)
     * | $(LITERAL 'align') $(LITERAL IntegerLiteral)
     * | $(LITERAL 'align') $(LITERAL Identifier)
     * | $(LITERAL Identifier) $(LITERAL ':') $(RULE asmInstruction)
     * | $(LITERAL Identifier) $(RULE operands)
     * | $(LITERAL 'in') $(RULE operands)
     * | $(LITERAL 'out') $(RULE operands)
     * | $(LITERAL 'int') $(RULE operands)
     * ;)
     */
    boolean parseAsmInstruction() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
//            AsmInstruction node = allocator.make!AsmInstruction;
        final Marker m = enter_section_(builder);
        if (currentIs(tok("align"))) {
            advance(); // align
//                node.hasAlign = true;
            if (currentIsOneOf(tok("intLiteral"), tok("identifier"))) {
                advance();
//                    node.identifierOrIntegerOrOpcode = advance();
            } else {
                error("Identifier or integer literal expected.");
                //exits normally
            }
        } else if (currentIsOneOf(tok("identifier"), tok("in"), tok("out"), tok("int"))) {
//                node.identifierOrIntegerOrOpcode = advance();
            final Token t = advance();
            if (t.type.equals(tok("identifier")) && currentIs(tok(":"))) {
                advance(); // :
                if (!parseNodeQ("node.asmInstruction", "AsmInstruction")) {
                    cleanup(m);
                    return false;
                }
            } else if (!currentIs(tok(";")))
                if (!parseNodeQ("node.operands", "Operands")) {
                    cleanup(m);
                    return false;
                }
        }
        exit_section_(builder, m, ASM_INSTRUCTION, true);
        return true;
    }

    /**
     * Parses an AsmLogAndExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmLogAndExp):
     * $(RULE asmOrExp)
     * $(RULE asmLogAndExp) $(LITERAL '&&') $(RULE asmOrExp)
     * ;)
     */
    boolean parseAsmLogAndExp() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_(builder);
        final boolean result = parseLeftAssocBinaryExpression("AsmLogAndExp", "AsmOrExp", tok("&&"));
        exit_section_(builder, m, ASM_LOG_AND_EXP, result);
        return result;
    }

    /**
     * Parses an AsmLogOrExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmLogOrExp):
     * $(RULE asmLogAndExp)
     * | $(RULE asmLogOrExp) '||' $(RULE asmLogAndExp)
     * ;)
     */
    boolean parseAsmLogOrExp() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_(builder);
        final boolean result = parseLeftAssocBinaryExpression("AsmLogOrExp", "AsmLogAndExp", tok("||"));
        exit_section_(builder, m, ASM_LOG_OR_EXP, result);
        return result;
    }

//        template simpleParseItems(items ...)
//        {
//            static if (items.length > 1)
//            enum simpleParseItems = simpleParseItem!(items[0]) ~ "\n"
//            ~ simpleParseItems!(items[1 .. $]);
//        else static if (items.length == 1)
//            enum simpleParseItems = simpleParseItem!(items[0]);
//        else
//            enum simpleParseItems = "const";
//        }

//        template simpleParseItem(alias item)
//        {
//            static if (is (typeof(item) == string))
//            enum simpleParseItem = "if ((node." ~ item[0 .. item.countUntil("|")]
//            ~ " = " ~ item[item.countUntil("|") + 1 .. $] ~ "()) is null) { return null; }";
//        else
//            enum simpleParseItem = "if (expect(" ~ item.stringof ~ ") is null) { return null; }";
//        }

    /**
     * Parses an AsmMulExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmMulExp):
     * $(RULE asmBrExp)
     * | $(RULE asmMulExp) ($(LITERAL '*') | $(LITERAL '/') | $(LITERAL '%')) $(RULE asmBrExp)
     * ;)
     */
    boolean parseAsmMulExp() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_(builder);
        final boolean result = parseLeftAssocBinaryExpression("AsmMulExp", "AsmBrExp", tok("*"), tok("/"), tok("%"));
        exit_section_(builder, m, ASM_MUL_EXP, result);
        return result;
    }

    /**
     * Parses an AsmOrExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmOrExp):
     * $(RULE asmXorExp)
     * | $(RULE asmOrExp) $(LITERAL '|') $(RULE asmXorExp)
     * ;)
     */
    boolean parseAsmOrExp() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_(builder);
        final boolean result = parseLeftAssocBinaryExpression("AsmOrExp", "AsmXorExp", tok("|"));
        exit_section_(builder, m, ASM_OR_EXP, result);
        return result;
    }

    void trace(String ignored) {
    }

    /**
     * Parses an AsmPrimaryExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmPrimaryExp):
     * $(LITERAL IntegerLiteral)
     * | $(LITERAL FloatLiteral)
     * | $(LITERAL StringLiteral)
     * | $(RULE register)
     * | $(RULE register : AsmExp)
     * | $(RULE identifierChain)
     * | $(LITERAL '$')
     * ;)
     */
    boolean parseAsmPrimaryExp() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
//            AsmPrimaryExp node = allocator.make!AsmPrimaryExp();
        final Marker m = enter_section_(builder);
        Token.IdType i = current().type;
        if (i.equals(tok("doubleLiteral")) || i.equals(tok("floatLiteral")) || i.equals(tok("intLiteral")) || i.equals(tok("longLiteral")) || i.equals(tok("stringLiteral")) || i.equals(tok("$"))) {
            advance();
        } else if (i.equals(tok("identifier"))) {
            if ((Sets.newHashSet(Arrays.asList(REGISTER_NAMES))).contains(current().text)) {
                trace("Found register");
                if (!parseRegister()) {
                    cleanup(m);
                    return false;
                }
                if (current().type.equals(tok(":"))) {
                    advance();
                    if (!parseNodeQ("node.segmentOverrideSuffix", "AsmExp")) {
                        cleanup(m);
                        return false;
                    }
                }
            } else {
                if (!parseNodeQ("node.identifierChain", "IdentifierChain")) {
                    cleanup(m);
                    return false;
                }
            }
        } else {
            error("Float literal, integer literal, $, or identifier expected.");
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, ASM_PRIMARY_EXP, true);
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
        final Marker m = enter_section_(builder);
        final boolean result = parseLeftAssocBinaryExpression("AsmRelExp", "AsmShiftExp", tok("<"), tok("<="), tok(">"), tok(">="));
        exit_section_(builder, m, ASM_REL_EXP, result);
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
        final Marker m = enter_section_(builder);
        final boolean result = parseLeftAssocBinaryExpression("AsmShiftExp", "AsmAddExp", tok("<<"), tok(">>"), tok(">>>"));
        exit_section_(builder, m, ASM_SHIFT_EXP, result);
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
        final Marker m = enter_section_(builder);
        advance(); // asm
        while (isAttribute()) {
            if (!parseFunctionAttribute(true)) {
                error("Function attribute or '{' expected");
                //should there be a cleanup here or would that suppress the error?
                cleanup(m);
                return false;
            }
        }
        advance(); // {
//            final Marker statementList = enter_section_(builder);
        while (moreTokens() && !currentIs(tok("}"))) {
            //todo the libdparse source does some stuff with the rollback allocator here that I don't understand
            parseAsmInstruction();
//
//                else
            expect(tok(";"));
        }
//            exit_section_(builder,statementList,);//todo needs type
        expect(tok("}"));
        exit_section_(builder, m, ASM_STATEMENT, true);
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
     * $(LITERAL Identifier) $(LITERAL Identifier)?
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
        final Marker m = enter_section_(builder);
        Token.IdType i = current().type;
        if (i.equals(tok("identifier")) || i.equals(tok("byte")) || i.equals(tok("short")) || i.equals(tok("int")) || i.equals(tok("float")) || i.equals(tok("double")) || i.equals(tok("real"))) {
            Token t = advance();
            if (t.type.equals(tok("identifier")))
                switch (t.text) {
                    case "near":
                    case "far":
                    case "word":
                    case "dword":
                    case "qword": {
                        break;
                    }
                    default: {
                        error("ASM type node expected");
                        exit_section_(builder, m, ASM_TYPE_PREFIX, true);
                        return false;
                    }
                }
            if (currentIs(tok("identifier")) && current().text.equals("ptr")) {
                advance();
            }
            exit_section_(builder, m, ASM_TYPE_PREFIX, true);
            return true;
        } else {
            error("Expected an identifier, 'byte', 'short', 'int', 'float', 'double', or 'real'");
            cleanup(m);
            return false;
        }
    }

    /**
     * Parses an AsmUnaExp
     * <p>
     * $(GRAMMAR $(RULEDEF asmUnaExp):
     * $(RULE asmTypePrefix) $(RULE asmExp)
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
        final Marker m = enter_section_(builder);
        Token.IdType i = current().type;
        if (i.equals(tok("+")) || i.equals(tok("-")) || i.equals(tok("!")) || i.equals(tok("~"))) {
            advance();
            if (!parseNodeQ("node.asmUnaExp", "AsmUnaExp")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("byte")) || i.equals(tok("short")) || i.equals(tok("int")) || i.equals(tok("float")) || i.equals(tok("double")) || i.equals(tok("real"))) {
            if (!parseNodeQ("node.asmTypePrefix", "AsmTypePrefix")) {
                cleanup(m);
                return false;
            }
            if (!parseNodeQ("node.asmExp", "AsmExp")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("identifier"))) {
            switch (current().text) {
                case "offsetof":
                case "seg": {
                    advance();
                    if (!parseNodeQ("node.asmExp", "AsmExp")) {
                        cleanup(m);
                        return false;
                    }
                    break;
                }
                case "near":
                case "far":
                case "word":
                case "dword":
                case "qword": {
                    if (!parseNodeQ("node.asmTypePrefix", "AsmTypePrefix")) {
                        cleanup(m);
                        return false;
                    }
                    if (!parseNodeQ("node.asmExp", "AsmExp")) {
                        cleanup(m);
                        return false;
                    }
                    break;
                }
                default: {
                    if (!parseNodeQ("node.asmPrimaryExp", "AsmPrimaryExp")) {
                        cleanup(m);
                        return false;
                    }
                    break;
                }
            }

        } else {
            if (!parseNodeQ("node.asmPrimaryExp", "AsmPrimaryExp")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, ASM_UNA_EXP, true);
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
        final Marker m = enter_section_(builder);
        final boolean result = parseLeftAssocBinaryExpression("AsmXorExp", "AsmAndExp", tok("^"));
        exit_section_(builder, m, ASM_XOR_EXP, result);
        return result;
    }

    /**
     * Parses an AssertExpression
     * <p>
     * $(GRAMMAR $(RULEDEF assertExpression):
     * $(LITERAL 'assert') $(LITERAL '$(LPAREN)') $(RULE assignExpression) ($(LITERAL ',') $(RULE assignExpression))? $(LITERAL ',')? $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseAssertExpression() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AssertExpression,false);
//            node.line = current().line;
//            node.column = current().column;
        final Marker m = enter_section_(builder);
        advance(); // "assert"
        if (!tokenCheck("(")) {
            return false;
        }
        if (!parseNodeQ("node.assertion", "AssignExpression")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok(","))) {
            advance();
            if (currentIs(tok(")"))) {
                advance();
                return true;
            }
            if (!parseNodeQ("node.message", "AssignExpression")) {
                cleanup(m);
                return false;
            }
        }
        if (currentIs(tok(",")))
            advance();
        if (!tokenCheck(")")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, ASSERT_EXPRESSION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_(builder);
        if (!moreTokens()) {
            error("Assign expression expected instead of EOF");
            return false;
        }
        boolean ternary = parseTernaryExpression();
        if (!ternary) {
            cleanup(m);
            return false;
        }
        if (currentIsOneOf(tok("="), tok(">>>="), tok(">>="), tok("<<="), tok("+="), tok("-="), tok("*="), tok("%="), tok("&="), tok("/="), tok("|="), tok("^^="), tok("^="), tok("~="))) {
//                Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AssignExpression,false);
//                node.line = current().line;
//                node.column = current().column;
//                node.ternaryExpression = ternary;
//                node.operator = advance().type;
            if (!parseNodeQ("node.expression", "AssignExpression")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, ASSIGN_EXPRESSION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_(builder);
        final boolean result = simpleParse("AssocArrayLiteral", tok("["), "keyValuePairs|parseKeyValuePairs", tok("]"));
        exit_section_(builder, m, ASSOC_ARRAY_LITERAL, result);
        return result;
    }

    /**
     * moove and document these
     *
     * @param nodeType
     * @param parts
     * @return
     */
    private boolean simpleParse(String nodeType, Object... parts) {
        //open marker for type
        final Marker m = enter_section_(builder);
        final boolean result = simpleParseItems(parts);
        exit_section_(builder, m, ParserPreliminaryJavaWriteUp.nodeTypeToIElementType(nodeType), result);
        return result;
    }

    boolean simpleParseItems(Object... items) {
        for (Object item : items) {
            if (item instanceof Token.IdType) {
                if (!simpleParseItemsSingle((Token.IdType) item)) {
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

    boolean simpleParseItemsSingle(String item) {
        final int i = item.indexOf("|");
        final String first = item.substring(0, i);//unneeded, libdparse uses for building it's ast, but we don't need to
        final String second = item.substring(i + 1);
        final String name = second.replace("parse", "");
        return parseName(name);

    }

    boolean simpleParseItemsSingle(Token.IdType item) {
        return expect(item) != null;
    }

    /**
     * Parses an AtAttribute
     * <p>
     * $(GRAMMAR $(RULEDEF atAttribute):
     * $(LITERAL '@') $(LITERAL Identifier)
     * | $(LITERAL '@') $(LITERAL Identifier) $(LITERAL '$(LPAREN)') $(RULE argumentList)? $(LITERAL '$(RPAREN)')
     * | $(LITERAL '@') $(LITERAL '$(LPAREN)') $(RULE argumentList) $(LITERAL '$(RPAREN)')
     * | $(LITERAL '@') $(RULE TemplateInstance)
     * ;)
     */
    boolean parseAtAttribute() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AtAttribute,false);
        Token start = expect(tok("@"));
        if (start == null) {
            cleanup(m);
            return false;
        }
        if (!moreTokens()) {
            error("\"(\", or identifier expected");
            return false;
        }
//            node.startLocation = start.index;
        Token.IdType i = current().type;
        if (i.equals(tok("identifier"))) {
            if (peekIs(tok("!"))) {
                if (!parseNodeQ("node.templateInstance", "TemplateInstance")) {
                    cleanup(m);
                    return false;
                }
            } else
                advance();
            if (currentIs(tok("("))) {
                advance(); // (
                if (!currentIs(tok(")"))) {
                    if (!parseNodeQ("node.argumentList", "ArgumentList")) {
                        cleanup(m);
                        return false;
                    }
                }
                expect(tok(")"));
            }
        } else if (i.equals(tok("("))) {
            advance();
            if (!parseNodeQ("node.argumentList", "ArgumentList")) {
                cleanup(m);
                return false;
            }
            expect(tok(")"));
        } else {
            error("\"(\", or identifier expected");
            return false;
        }
        exit_section_(builder, m, ATTRIBUTE, true);//todo elementType
        return true;
    }

    /**
     * Parses an Attribute
     * <p>
     * $(GRAMMAR $(RULEDEF attribute):
     * | $(RULE pragmaExpression)
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
     * | $(LITERAL '')
     * | $(LITERAL 'immutable')
     * | $(LITERAL 'inout')
     * | $(LITERAL 'shared')
     * | $(LITERAL '__gshared')
     * | $(LITERAL '')
     * | $(LITERAL '')
     * | $(LITERAL 'ref')
     * ;)
     */
    boolean parseAttribute() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Attribute,false);
        Token.IdType i = current().type;
        if (i.equals(tok("pragma"))) {
            if (!parseNodeQ("node.pragmaExpression", "PragmaExpression")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("deprecated"))) {
            if (!parseNodeQ("node.deprecated_", "Deprecated")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("align"))) {
            if (!parseNodeQ("node.alignAttribute", "AlignAttribute")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("@"))) {
            if (!parseNodeQ("node.atAttribute", "AtAttribute")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("package"))) {
            advance();
            if (currentIs(tok("("))) {
                expect(tok("("));
                if (!parseNodeQ("node.identifierChain", "IdentifierChain")) {
                    cleanup(m);
                    return false;
                }
                expect(tok(")"));
            }
        } else if (i.equals(tok("extern"))) {
            if (peekIs(tok("("))) {
                if (!parseNodeQ("node.linkageAttribute", "LinkageAttribute")) {
                    cleanup(m);
                    return false;
                }
                exit_section_(builder, m, ATTRIBUTE, true);
                return true;
            }


            advance();
        } else if (i.equals(tok("private")) || i.equals(tok("protected")) || i.equals(tok("public")) || i.equals(tok("export")) || i.equals(tok("static")) || i.equals(tok("abstract")) || i.equals(tok("final")) || i.equals(tok("override")) || i.equals(tok("synchronized")) || i.equals(tok("auto")) || i.equals(tok("scope")) || i.equals(tok("const")) || i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("shared")) || i.equals(tok("__gshared")) || i.equals(tok("const")) || i.equals(tok("const")) || i.equals(tok("ref"))) {
            advance();
        } else {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, ATTRIBUTE, true);
        return true;
    }

    /**
     * Parses an AttributeDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF attributeDeclaration):
     * $(RULE attribute) $(LITERAL ':')
     * ;)
     */
    boolean parseAttributeDeclaration()//(Attribute attribute = null)
    {
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AttributeDeclaration,false);
//            node.line = current().line;
        final boolean result = parseAttribute();
        expect(tok(":"));
        exit_section_(builder, m, ATTRIBUTE_SPECIFIER, result);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AutoDeclaration,false);
//            node.comment = comment;
//            comment = null;
        StackBuffer storageClasses;
        while (isStorageClass()) {
            if (parseStorageClass()) {
                cleanup(m);
                return false;
            }
        }
        do {
            if (!parseAutoDeclarationPart()) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok(",")))
                advance();
            else
                break;
        } while (moreTokens());
        final Token semi = expect(tok(";"));
        if (semi == null) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, AUTO_DECLARATION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            auto part = allocator.make!AutoDeclarationPart;
        final Marker m = enter_section_(builder);
        Token i = expect(tok("identifier"));//todo
        if (i == null) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("("))) {
            if (!parseNodeQ("part.templateParameters", "TemplateParameters")) {
                cleanup(m);
                return false;
            }
        }
        if (!tokenCheck("=")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("part.initializer", "Initializer")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, AUTO_DECLARATION_X, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.BlockStatement,false);
        Token openBrace = expect(tok("{"));
        if (!nullCheck("openBrace")) {
            cleanup(m);
            return false;
        }
//            node.startLocation = openBrace.index;
        if (!currentIs(tok("}"))) {
            if (!parseNodeQ("node.declarationsAndStatements", "DeclarationsAndStatements")) {
                cleanup(m);
                return false;
            }
        }
        Token closeBrace = expect(tok("}"));
        if (closeBrace != null) {
//                node.endLocation = closeBrace.index;
        } else {
            trace("Could not find end of block statement.");
//                node.endLocation = Number.max;
        }
        exit_section_(builder, m, BLOCK_STATEMENT, true);
        return true;
    }

    /**
     * Parses a BodyStatement
     * <p>
     * $(GRAMMAR $(RULEDEF bodyStatement):
     * $(LITERAL 'body') $(RULE blockStatement)
     * ;)
     */
    boolean parseBodyStatement() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_(builder);
        final boolean result = simpleParse("BodyStatement", tok("body"), "blockStatement|parseBlockStatement");
        exit_section_(builder, m, BODY_STATEMENT, result);
        return result;
    }

    /**
     * Parses a BreakStatement
     * <p>
     * $(GRAMMAR $(RULEDEF breakStatement):
     * $(LITERAL 'break') $(LITERAL Identifier)? $(LITERAL ';')
     * ;)
     */
    boolean parseBreakStatement() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
        expect(tok("break"));
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.BreakStatement,false);
        Token.IdType i = current().type;
        if (i.equals(tok("identifier"))) {
            advance();
            if (!tokenCheck(";")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok(";"))) {
            advance();
        } else {
            error("Identifier or semicolon expected following \"break\"");
            return false;//todo cleanup?
        }
        exit_section_(builder, m, BREAK_STATEMENT, true);
        return true;
    }

    /**
     * todo document and move
     *
     * @param type
     * @return
     */
    public boolean isProtection(Token.IdType type) {

        for (Token.IdType t : Protections) {
            if (t.equals(type)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Parses a BaseClass
     * <p>
     * $(GRAMMAR $(RULEDEF baseClass):
     * $(RULE type2)
     * ;)
     */
    boolean parseBaseClass() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.BaseClass,false);
        if (isProtection(current().type)) {
            warn("Use of base class protection == deprecated.");
            advance();
        }
        if (!parseType2()) {
            cleanup(m);
            return false;
        }
//            exit_section_(builder,m,"vghbj0,",true);//todo needs type
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
        final boolean result = parseCommaSeparatedRule("BaseClassList", "BaseClass");
        exit_section_(builder, m, BASE_CLASS_LIST, result);
        return result;
    }

    /**
     * Parses an BuiltinType
     * <p>
     * $(GRAMMAR $(RULEDEF builtinType):
     * $(LITERAL 'boolean')
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
    Token.IdType parseBuiltinType() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        return advance().type;
    }

    /**
     * Parses a CaseRangeStatement
     * <p>
     * $(GRAMMAR $(RULEDEF caseRangeStatement):
     * $(LITERAL 'case') $(RULE assignExpression) $(LITERAL ':') $(LITERAL '...') $(LITERAL 'case') $(RULE assignExpression) $(LITERAL ':') $(RULE declarationsAndStatements)
     * ;)
     */
    boolean parseCaseRangeStatement() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CaseRangeStatement,false);
//            assert (low != null);
//            node.low = low;
        if (!tokenCheck(":")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("..")) {
            cleanup(m);
            return false;
        }
        expect(tok("case"));
        if (!parseNodeQ("node.high", "AssignExpression")) {
            cleanup(m);
            return false;
        }
        Token colon = expect(tok(":"));
        if (colon == null)
            return false;
        if (!parseNodeQ("node.declarationsAndStatements", "DeclarationsAndStatements")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, CASE_RANGE_STATEMENT, true);
        return true;
    }

    /**
     * Parses an CaseStatement
     * <p>
     * $(GRAMMAR $(RULEDEF caseStatement):
     * $(LITERAL 'case') $(RULE argumentList) $(LITERAL ':') $(RULE declarationsAndStatements)
     * ;)
     */
    boolean parseCaseStatement() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CaseStatement,false);
//            node.argumentList = argumentList;
        Token colon = expect(tok(":"));
        if (colon == null) {
            cleanup(m);
            return false;
        }
//            node.colonLocation = colon.index;
        if (!nullCheck("node.declarationsAndStatements = parseDeclarationsAndStatements(false)")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, CASE_STATEMENT, true);
        return true;
    }

    /**
     * Parses a CastExpression
     * <p>
     * $(GRAMMAR $(RULEDEF castExpression):
     * $(LITERAL 'cast') $(LITERAL '$(LPAREN)') ($(RULE type) | $(RULE castQualifier))? $(LITERAL '$(RPAREN)') $(RULE unaryExpression)
     * ;)
     */
    boolean parseCastExpression() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CastExpression,false);
        expect(tok("cast"));
        if (!tokenCheck("(")) {
            cleanup(m);
            return false;
        }
        if (!currentIs(tok(")"))) {
            if (isCastQualifier()) {
                if (!parseNodeQ("node.castQualifier", "CastQualifier")) {
                    cleanup(m);
                    return false;
                }
            } else {
                if (!parseNodeQ("node.type", "Type")) {
                    cleanup(m);
                    return false;
                }
            }
        }
        if (!tokenCheck(")")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.unaryExpression", "UnaryExpression")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, CAST_EXPRESSION, true);
        return true;
    }

    /**
     * Parses a CastQualifier
     * <p>
     * $(GRAMMAR $(RULEDEF castQualifier):
     * $(LITERAL '')
     * | $(LITERAL '') $(LITERAL 'shared')
     * | $(LITERAL 'immutable')
     * | $(LITERAL 'inout')
     * | $(LITERAL 'inout') $(LITERAL 'shared')
     * | $(LITERAL 'shared')
     * | $(LITERAL 'shared') $(LITERAL '')
     * | $(LITERAL 'shared') $(LITERAL 'inout')
     * ;)
     */
    boolean parseCastQualifier() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CastQualifier,false);
        Token.IdType i = current().type;
        if (i.equals(tok("inout")) || i.equals(tok("const"))) {
            advance();
            if (currentIs(tok("shared")))
                advance();
        } else if (i.equals(tok("shared"))) {
            advance();
            if (currentIsOneOf(tok("const"), tok("inout")))
                advance();
        } else if (i.equals(tok("immutable"))) {
            advance();
        } else {
            error(", immutable, inout, or shared expected");
            return false;
        }
//            exit_section_(builder,m,CAST_QUALIFIIER,true);//todo
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Catch,false);
        expect(tok("catch"));
        if (!tokenCheck("(")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.type", "Type")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("identifier"))) {
            advance();
        }
        if (!tokenCheck(")")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, CATCH, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Catches,false);
        while (moreTokens()) {
            if (!currentIs(tok("catch")))
                break;
            if (peekIs(tok("("))) {
                if (!parseCatch()) {
                    cleanup(m);
                    return false;
                }
            } else {
                if (!parseNodeQ("node.lastCatch", "LastCatch")) {
                    cleanup(m);
                    return false;
                }
                break;
            }
        }
        exit_section_(builder, m, CATCHES, true);
        return true;
    }

    /**
     * Parses a ClassDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF classDeclaration):
     * $(LITERAL 'class') $(LITERAL Identifier) $(LITERAL ';')
     * | $(LITERAL 'class') $(LITERAL Identifier) ($(LITERAL ':') $(RULE baseClassList))? $(RULE structBody)
     * | $(LITERAL 'class') $(LITERAL Identifier) $(RULE templateParameters) $(RULE raint)? ($(RULE structBody) | $(LITERAL ';'))
     * | $(LITERAL 'class') $(LITERAL Identifier) $(RULE templateParameters) $(RULE raint)? ($(LITERAL ':') $(RULE baseClassList))? $(RULE structBody)
     * | $(LITERAL 'class') $(LITERAL Identifier) $(RULE templateParameters) ($(LITERAL ':') $(RULE baseClassList))? $(RULE raint)? $(RULE structBody)
     * ;)
     */
    boolean parseClassDeclaration() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ClassDeclaration,false);
        expect(tok("class"));
        final boolean result = parseInterfaceOrClass();
        exit_section_(builder, m, CLASS_DECLARATION, result);
        return result;
    }

    /**
     * Parses a CmpExpression
     * <p>
     * $(GRAMMAR $(RULEDEF cmpExpression):
     * $(RULE shiftExpression)
     * | $(RULE equalExpression)
     * | $(RULE identityExpression)
     * | $(RULE relExpression)
     * | $(RULE inExpression)
     * ;)
     */
    boolean parseCmpExpression() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_(builder);
        boolean shift = parseShiftExpression();
        if (!shift) {
            cleanup(m);
            return false;
        }
        if (!moreTokens()) {
            exit_section_(builder, m, DLanguageTypes.CONDITIONAL_EXPRESSION_, true);//todo
            return shift;
        }
        Token.IdType i = current().type;
        if (i.equals(tok("is"))) {
            if (!parseIdentityExpression()) {
                cleanup(m);
                return false;
            }
            exit_section_(builder, m, DLanguageTypes.CONDITIONAL_EXPRESSION_, true);//todo
            return true;
        } else if (i.equals(tok("in"))) {
            if (!parseInExpression()) {
                cleanup(m);
                return false;
            }
            exit_section_(builder, m, DLanguageTypes.CONDITIONAL_EXPRESSION_, true);//todo
            return true;
        } else if (i.equals(tok("!"))) {
            if (peekIs(tok("is")))
                if (!parseIdentityExpression()) {
                    cleanup(m);
                    return false;
                } else if (peekIs(tok("in")))
                    if (!parseInExpression()) {
                        cleanup(m);
                        return false;
                    }
            exit_section_(builder, m, DLanguageTypes.CONDITIONAL_EXPRESSION_, true);//todo
            return true;
        } else if (i.equals(tok("<")) || i.equals(tok("<=")) || i.equals(tok(">")) || i.equals(tok(">=")) || i.equals(tok("!<>=")) || i.equals(tok("!<>")) || i.equals(tok("<>")) || i.equals(tok("<>=")) || i.equals(tok("!>")) || i.equals(tok("!>=")) || i.equals(tok("!<")) || i.equals(tok("!<="))) {
            if (!parseRelExpression()) {
                cleanup(m);
                return false;
            }
            exit_section_(builder, m, DLanguageTypes.CONDITIONAL_EXPRESSION_, true);//todo
            return true;
        } else if (i.equals(tok("==")) || i.equals(tok("!="))) {
            if (!parseEqualExpression()) {
                cleanup(m);
                return false;
            }
            exit_section_(builder, m, DLanguageTypes.CONDITIONAL_EXPRESSION_, true);//todo TYPE
            return true;
        } else {
            exit_section_(builder, m, DLanguageTypes.CONDITIONAL_EXPRESSION_, true);//todo
            return true;
        }
    }

    /**
     * Parses a CompileCondition
     * <p>
     * $(GRAMMAR $(RULEDEF compileCondition):
     * $(RULE versionCondition)
     * | $(RULE debugCondition)
     * | $(RULE staticIfCondition)
     * ;)
     */
    boolean parseCompileCondition() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CompileCondition,false);
        Token.IdType i = current().type;
        if (i.equals(tok("version"))) {
            if (!parseNodeQ("node.versionCondition", "VersionCondition")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("debug"))) {
            if (!parseNodeQ("node.debugCondition", "DebugCondition")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("static"))) {
            if (!parseNodeQ("node.staticIfCondition", "StaticIfCondition")) {
                cleanup(m);
                return false;
            }
        } else {
            error("\"version\", \"debug\", or \"static\" expected");
            return false;
        }
        exit_section_(builder, m, CONDITIONAL_DECLARATION, true);//todo types
        return true;
    }

    /**
     * Parses a ConditionalDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF conditionalDeclaration):
     * $(RULE compileCondition) $(RULE declaration)
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
    boolean parseConditionalDeclaration(boolean strict) {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ConditionalDeclaration,false);
        if (!parseNodeQ("node.compileCondition", "CompileCondition")) {
            cleanup(m);
            return false;
        }

        StackBuffer trueDeclarations;
        if (currentIs(tok(":")) || currentIs(tok("{"))) {
            boolean brace = advance().type.equals(tok("{"));
            while (moreTokens() && !currentIs(tok("}")) && !currentIs(tok("else"))) {
                Bookmark b = setBookmark();
//                    c = allocator.setCheckpoint();
                if (parseDeclaration(strict, true)) {
                    abandonBookmark(b);
                } else {
                    goToBookmark(b);
//                        allocator.rollback(c);
                    cleanup(m);
                    return false;
                }
            }
            if (brace)
                if (!tokenCheck("}")) {
                    cleanup(m);
                    return false;
                }
        } else if (!parseDeclaration(strict, true)) {
            return false;
        }

        if (currentIs(tok("else"))) {
            advance();
        } else {
            exit_section_(builder, m, CONDITIONAL_DECLARATION, true);
            return true;
        }

        StackBuffer falseDeclarations;
        if (currentIs(tok(":")) || currentIs(tok("{"))) {
            boolean brace = currentIs(tok("{"));
            advance();
            while (moreTokens() && !currentIs(tok("}")))
                if (!parseDeclaration(strict, true)) {
                    cleanup(m);
                    return false;
                }
            if (brace)
                if (!tokenCheck("}")) {
                    return false;
                }
        } else {
            if (!parseDeclaration(strict, true)) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, CONDITIONAL_DECLARATION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ConditionalStatement,false);
        if (!parseNodeQ("node.compileCondition", "CompileCondition")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.trueStatement", "DeclarationOrStatement")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("else"))) {
            advance();
            if (!parseNodeQ("node.falseStatement", "DeclarationOrStatement")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, CONDITIONAL_STATEMENT, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Constraint,false);
        Token ifToken = expect(tok("if"));
        if (ifToken == null) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("(")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.expression", "Expression")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck(")")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, CONSTRAINT, true);
        return true;
    }

    /**
     * Parses a Constructor
     * <p>
     * $(GRAMMAR $(RULEDEF ructor):
     * $(LITERAL 'this') $(RULE templateParameters)? $(RULE parameters) $(RULE memberFunctionAttribute)* $(RULE raint)? ($(RULE functionBody) | $(LITERAL ';'))
     * ;)
     */
    boolean parseConstructor() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Constructor node = allocator.make!Constructor;
//            node.comment = comment;
//            comment = null;
        final Marker m = enter_section_(builder);
        Token t = expect(tok("this"));
        if (t == null) {
            cleanup(m);
            return false;
        }
//            node.location = t.index;
//            node.line = t.line;
//            node.column = t.column;
        Token p = peekPastParens();
        boolean isTemplate = false;
        if (p != null && p.type.equals(tok("("))) {
            isTemplate = true;
            if (!parseNodeQ("node.templateParameters", "TemplateParameters")) {
                cleanup(m);
                return false;
            }
        }
        if (!parseNodeQ("node.parameters", "Parameters")) {
            cleanup(m);
            return false;
        }

        while (moreTokens() && currentIsMemberFunctionAttribute())
            if (!parseMemberFunctionAttribute()) {
                cleanup(m);
                return false;
            }

        if (isTemplate && currentIs(tok("if")))
            if (!parseNodeQ("node.raint", "Constraint")) {
                cleanup(m);
                return false;
            }
        if (currentIs(tok(";")))
            advance();
        else if (!parseNodeQ("node.functionBody", "FunctionBody")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, CONSTRUCTOR, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ContinueStatement,false);
        if (!tokenCheck("continue")) {
            cleanup(m);
            return false;
        }
        Token.IdType i = current().type;
        if (i.equals(tok("identifier"))) {
            advance();
            if (!tokenCheck(";")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok(";"))) {
            advance();
        } else {
            error("Identifier or semicolon expected following \"continue\"");
            return false;
        }
        exit_section_(builder, m, CONTINUE_STATEMENT, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DebugCondition,false);
        Token d = expect(tok("debug"));
        if (d == null) {
            cleanup(m);
            return false;
        }
//            node.debugIndex = d.index;

        if (currentIs(tok("("))) {
            advance();
            if (currentIsOneOf(tok("intLiteral"), tok("identifier"))) {
                advance();
            } else {
                error("Integer literal or identifier expected");
                return false;
            }
            if (!tokenCheck(")")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, DEBUG_CONDITION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DebugSpecification,false);
        if (!tokenCheck("debug")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("=")) {
            cleanup(m);
            return false;
        }
        if (currentIsOneOf(tok("identifier"), tok("intLiteral"))) {
            advance();
        } else {
            error("Integer literal or identifier expected");
            return false;
        }
        if (!tokenCheck(";")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, DEBUG_SPECIFICATION, true);
        return true;
    }

    /**
     * Parses a Declaration
     * <p>
     * Params: strict = if true, do not return partial AST nodes on errors.
     * <p>
     * $(GRAMMAR $(RULEDEF declaration):
     * $(RULE attribute)* $(RULE declaration2)
     * | $(RULE attribute)+ $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
     * ;
     * $(RULEDEF declaration2):
     * $(RULE aliasDeclaration)
     * | $(RULE aliasThisDeclaration)
     * | $(RULE anonymousEnumDeclaration)
     * | $(RULE attributeDeclaration)
     * | $(RULE classDeclaration)
     * | $(RULE conditionalDeclaration)
     * | $(RULE ructor)
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
        return parseDeclaration(false, false);
    }

    boolean parseDeclaration(boolean strict) {
        return parseDeclaration(strict, false);
    }

    boolean parseDeclaration(boolean strict, boolean mustBeDeclaration) {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Declaration,false);
        if (!moreTokens()) {
            error("declaration expected instead of EOF");
            exit_section_(builder, m, DECLARATION, true);
            return false;
        }
//            if (current().comment != null)
//            comment = current().comment;
//            Number autoStorageClassStart = Number.max;
        boolean nodeAttributes = false;
        DecType isAuto;
        do {
            final Pair<DecType, Integer> pair = isAutoDeclaration();
            int autoStorageClassStart = pair.second;
            isAuto = pair.first;
            if (isAuto != DecType.other && index == autoStorageClassStart)
                break;
            if (!isAttribute())
                break;
//                c = allocator.setCheckpoint();
            boolean attr = parseAttribute();
            if (!attr) {
//                    allocator.rollback(c);
                break;
            }
            if (currentIs(tok(":"))) {
                if (!parseAttributeDeclaration()) {
                    cleanup(m);
                    return false;
                }
                return true;
            } else
                nodeAttributes = true;
        } while (moreTokens());

        if (!moreTokens()) {
            error("declaration expected instead of EOF");
            exit_section_(builder, m, DECLARATION, true);
            return false;
        }

        if (isAuto == DecType.autoVar) {
            if (!parseVariableDeclaration(true, true)) {
                cleanup(m);
                return false;
            }
            return true;
        } else if (isAuto == DecType.autoFun) {
            if (!parseFunctionDeclaration(true, true)) {
                cleanup(m);
                return false;
            }
            return true;
        }

        final Token.IdType idType = current().type;
        {
            if (idType.equals(tok("asm")) || idType.equals(tok("break")) || idType.equals(tok("case")) || idType.equals(tok("continue")) || idType.equals(tok("default")) || idType.equals(tok("do")) || idType.equals(tok("for")) || idType.equals(tok("foreach")) || idType.equals(tok("foreach_reverse")) || idType.equals(tok("goto")) || idType.equals(tok("if")) || idType.equals(tok("return")) || idType.equals(tok("switch")) || idType.equals(tok("throw")) || idType.equals(tok("try")) || idType.equals(tok("while")) || idType.equals(tok("assert"))) {
                error("Declaration expected");
                exit_section_(builder, m, DECLARATION, true);
                return false;
            } else if (idType.equals(tok(";"))) {
                // http://d.magic.com/issues/show_bug.cgi?id=4559
                warn("Empty declaration");
                advance();
            } else if (idType.equals(tok("{"))) {
                if (!nodeAttributes) {
                    error("declaration expected instead of '{'");
                    exit_section_(builder, m, DECLARATION, true);
                    return false;
                }
                advance();
                while (moreTokens() && !currentIs(tok("}"))) {
//                        auto c = allocator.setCheckpoint();
                    if (!parseDeclaration(strict)) {
//                            allocator.rollback(c);
                        cleanup(m);
                        return false;
                    }
                }
//                    ownArray(node.declarations, declarations);
                if (!tokenCheck("}")) {
                    cleanup(m);
                    return false;
                }
            } else if (idType.equals(tok("alias"))) {
                if (startsWith(tok("alias"), tok("identifier"), tok("this"))) {
                    if (!parseNodeQ("node.aliasThisDeclaration", "AliasThisDeclaration")) {
                        cleanup(m);
                        return false;
                    }
                } else if (!parseNodeQ("node.aliasDeclaration", "AliasDeclaration")) {
                    cleanup(m);
                    return false;
                }
            } else if (idType.equals(tok("class"))) {
                if (!parseNodeQ("node.classDeclaration", "ClassDeclaration")) {
                    cleanup(m);
                    return false;
                }
            } else if (idType.equals(tok("this"))) {
                if (!mustBeDeclaration && peekIs(tok("("))) {
                    // Do not parse as a declaration if we could parse as a function call.
                    Bookmark b = setBookmark();
                    Token past = peekPastParens();
                    goToBookmark(b);
                    if (past != null && past.type == tok(";"))
                        return false;
                }
                if (startsWith(tok("this"), tok("("), tok("this"), tok(")"))) {
                    if (!parseNodeQ("node.postblit", "Postblit")) {
                        cleanup(m);
                        return false;
                    }
                } else if (!parseNodeQ("node.constructorructor", "Constructor")) {
                    cleanup(m);
                    return false;
                }
            } else if (idType.equals(tok("~"))) {
                if (!parseNodeQ("node.destructor", "Destructor")) {
                    cleanup(m);
                    return false;
                }
            } else if (idType.equals(tok("enum"))) {
                Bookmark b = setBookmark();
                advance(); // enum
                if (currentIsOneOf(tok(":"), tok("{"))) {
                    goToBookmark(b);
                    if (!parseNodeQ("node.anonymousEnumDeclaration", "AnonymousEnumDeclaration")) {
                        cleanup(m);
                        return false;
                    }
                } else if (currentIs(tok("identifier"))) {
                    advance();
                    if (currentIs(tok("("))) {
                        skipParens(); // ()
                        if (currentIs(tok("(")))
                            skipParens();
                        if (!currentIs(tok("="))) {
                            goToBookmark(b);
                            boolean nodeFunctionDeclaration = parseFunctionDeclaration(true, true);
                            if (!nodeFunctionDeclaration) {
                                cleanup(m);
                                return false;
                            }
                        } else {
                            goToBookmark(b);
                            if (!parseNodeQ("node.eponymousTemplateDeclaration", "EponymousTemplateDeclaration")) {
                                cleanup(m);
                                return false;
                            }
                        }
                    } else if (currentIsOneOf(tok(":"), tok("{"), tok(";"))) {
                        goToBookmark(b);
                        if (!parseNodeQ("node.enumDeclaration", "EnumDeclaration")) {
                            cleanup(m);
                            return false;
                        }
                    } else {
                        boolean eq = currentIs(tok("="));
                        goToBookmark(b);
                        if (!parseVariableDeclaration(true, eq)) {
                            cleanup(m);
                            return false;
                        }
                    }
                } else {
                    boolean s = isStorageClass();
                    goToBookmark(b);
                    if (!parseVariableDeclaration(true, s)) {
                        cleanup(m);
                        return false;
                    }
                }
            } else if (idType.equals(tok("import"))) {
                if (!parseNodeQ("node.importDeclaration", "ImportDeclaration")) {
                    cleanup(m);
                    return false;
                }
            } else if (idType.equals(tok("interface"))) {
                if (!parseNodeQ("node.interfaceDeclaration", "InterfaceDeclaration")) {
                    cleanup(m);
                    return false;
                }
            } else if (idType.equals(tok("mixin"))) {
                if (peekIs(tok("template"))) {
                    if (!parseNodeQ("node.mixinTemplateDeclaration", "MixinTemplateDeclaration")) {
                        cleanup(m);
                        return false;
                    }
                } else {
                    Bookmark b = setBookmark();
                    advance();
                    if (currentIs(tok("("))) {
                        Token t = peekPastParens();
                        if (t != null && t.type.equals(tok(";"))) {
                            goToBookmark(b);
                            if (!parseNodeQ("node.mixinDeclaration", "MixinDeclaration")) {
                                cleanup(m);
                                return false;
                            }
                        } else {
                            goToBookmark(b);
                            error("Declaration expected");
                            exit_section_(builder, m, DECLARATION, true);
                            return false;
                        }
                    } else {
                        goToBookmark(b);
                        if (!parseNodeQ("node.mixinDeclaration", "MixinDeclaration")) {
                            cleanup(m);
                            return false;
                        }
                    }
                }
            } else if (idType.equals(tok("pragma"))) {
                if (!parseNodeQ("node.pragmaDeclaration", "PragmaDeclaration")) {
                    cleanup(m);
                    return false;
                }
            } else if (idType.equals(tok("shared"))) {
                if (startsWith(tok("shared"), tok("static"), tok("this"))) {
                    if (!parseNodeQ("node.sharedStaticConstructor", "SharedStaticConstructor")) {
                        cleanup(m);
                        return false;
                    }
                } else if (startsWith(tok("shared"), tok("static"), tok("~"))) {
                    if (!parseNodeQ("node.sharedStaticDestructor", "SharedStaticDestructor")) {
                        cleanup(m);
                        return false;
                    }
                } else {
                    if (!type(m)) {
                        cleanup(m);
                        return false;
                    }
                }
            } else if (idType.equals(tok("static"))) {
                if (peekIs(tok("this"))) {
                    if (!parseNodeQ("node.staticConstructor", "StaticConstructor")) {
                        cleanup(m);
                        return false;
                    }
                } else if (peekIs(tok("~"))) {
                    if (!parseNodeQ("node.staticDestructor", "StaticDestructor")) {
                        cleanup(m);
                        return false;
                    }
                } else if (peekIs(tok("if"))) {
                    if (!parseConditionalDeclaration(strict)) {
                        cleanup(m);
                        return false;
                    }
                } else if (peekIs(tok("assert"))) {
                    if (!parseNodeQ("node.staticAssertDeclaration", "StaticAssertDeclaration")) {
                        cleanup(m);
                        return false;
                    }
                } else {
                    if (!type(m)) {
                        cleanup(m);
                        return false;
                    }
                }

            } else if (idType.equals(tok("struct"))) {
                if (!parseNodeQ("node.structDeclaration", "StructDeclaration")) {
                    cleanup(m);
                    return false;
                }
            } else if (idType.equals(tok("template"))) {
                if (!parseNodeQ("node.templateDeclaration", "TemplateDeclaration")) {
                    cleanup(m);
                    return false;
                }
            } else if (idType.equals(tok("union"))) {
                if (!parseNodeQ("node.unionDeclaration", "UnionDeclaration")) {
                    cleanup(m);
                    return false;
                }
            } else if (idType.equals(tok("invariant"))) {
                if (!parseNodeQ("node.invariant_", "Invariant")) {
                    cleanup(m);
                    return false;
                }
            } else if (idType.equals(tok("unittest"))) {
                if (!parseNodeQ("node.unittest_", "Unittest")) {
                    cleanup(m);
                    return false;
                }
            } else if (idType.equals(tok("identifier")) || idType.equals(tok(".")) || idType.equals(tok("const")) || idType.equals(tok("immutable")) || idType.equals(tok("inout")) || idType.equals(tok("scope")) || idType.equals(tok("typeof")) || idType.equals(tok("__vector")) || idType.equals(tok("int")) || idType.equals(tok("bool")) || idType.equals(tok("byte")) || idType.equals(tok("cdouble")) || idType.equals(tok("cent")) || idType.equals(tok("cfloat")) || idType.equals(tok("char")) || idType.equals(tok("creal")) || idType.equals(tok("dchar")) || idType.equals(tok("double")) || idType.equals(tok("float")) || idType.equals(tok("idouble")) || idType.equals(tok("ifloat")) || idType.equals(tok("ireal")) || idType.equals(tok("long")) || idType.equals(tok("real")) || idType.equals(tok("short")) || idType.equals(tok("ubyte")) || idType.equals(tok("ucent")) || idType.equals(tok("uint")) || idType.equals(tok("ulong")) || idType.equals(tok("ushort")) || idType.equals(tok("void")) || idType.equals(tok("wchar"))) {
                if (!type(m)) {
//                    cleanup(m);
                    return false;//no cleanup needed already done in type
                }
            } else if (idType.equals(tok("version"))) {
                if (peekIs(tok("("))) {
                    if (!parseConditionalDeclaration(strict)) {
                        cleanup(m);
                        return false;
                    }
                } else if (peekIs(tok("="))) {
                    if (!parseNodeQ("node.versionSpecification", "VersionSpecification")) {
                        cleanup(m);
                        return false;
                    }
                } else {
                    error("\"=\" or \"(\" expected following \"version\"");
                    exit_section_(builder, m, DECLARATION, true);
                    return false;
                }
            } else if (idType.equals(tok("debug"))) {
                if (peekIs(tok("="))) {
                    if (!parseNodeQ("node.debugSpecification", "DebugSpecification")) {
                        cleanup(m);
                        return false;
                    }
                } else if (!parseConditionalDeclaration(strict)) {
                    cleanup(m);
                    return false;
                }
            } else {
                error("Declaration expected");
                exit_section_(builder, m, DECLARATION, true);
                return false;
            }
        }
        exit_section_(builder, m, DECLARATION, true);
        return true;
    }

    private boolean type(@NotNull Marker m) {
        boolean t = parseType();
        if ((!t) || !currentIs(tok("identifier")))
            return false;
        if (peekIs(tok("("))) {
            if (!parseFunctionDeclaration(!t, false)) {
                cleanup(m);
                return false;
            }
        } else if (!parseVariableDeclaration(!t, false)) {
            cleanup(m);
            return false;
        }
        return true;
    }

    /**
     * Parses DeclarationsAndStatements
     * <p>
     * $(GRAMMAR $(RULEDEF declarationsAndStatements):
     * $(RULE declarationOrStatement)+
     * ;)
     */
    boolean parseDeclarationsAndStatements() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final boolean includeCases = true;
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DeclarationsAndStatements,false);
//        StackBuffer declarationsAndStatements;
        while (!currentIsOneOf(tok("}"), tok("else")) && moreTokens() && suppressedErrorCount <= MAX_ERRORS) {
            if (currentIs(tok("case")) && !includeCases) {
                break;
            }
            if (currentIs(tok("while"))) {
                Bookmark b = setBookmark();
                advance();
                if (currentIs(tok("("))) {
                    Token p = peekPastParens();
                    if (p != null && p.type.equals(tok(";"))) {
                        goToBookmark(b);
                        break;
                    }
                    goToBookmark(b);
                }
            }
//                c = allocator.setCheckpoint();
            if (!parseDeclarationOrStatement()) {
//                    allocator.rollback(c);
                if (suppressMessages > 0) {
                    cleanup(m);
                    return false;
                }
            }
        }
//            ownArray(node.declarationsAndStatements, declarationsAndStatements);
        exit_section_(builder, m, DECLARATION_STATEMENT, true);//todo types
        return true;
    }

    /**
     * Parses a DeclarationOrStatement
     * <p>
     * $(GRAMMAR $(RULEDEF declarationOrStatement):
     * $(RULE declaration)
     * | $(RULE statement)
     * ;)
     */
    boolean parseDeclarationOrStatement() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DeclarationOrStatement,false);
        // "Any ambiguities in the grammar between Statements and
        // Declarations are resolved by the declarations taking precedence."
        Bookmark b = setBookmark();
//            c = allocator.setCheckpoint();
        boolean d = parseDeclaration(true, false);
        if (!d) {
//                allocator.rollback(c);
            goToBookmark(b);
            if (!parseNodeQ("node.statement", "Statement")) {
                cleanup(m);
                return false;
            }
        } else {
            // TODO: Make this more efficient. Right now we parse the declaration
            // twice, once with errors and warnings ignored, and once with them
            // printed. Maybe store messages to then be abandoned or written later?
//                allocator.rollback(c);
            goToBookmark(b);
            parseDeclaration(true, true);
        }
        exit_section_(builder, m, DECLARATION_STATEMENT, true);//todo type
        return true;
    }

    /**
     * Parses a Declarator
     * <p>
     * $(GRAMMAR $(RULEDEF declarator):
     * $(LITERAL Identifier)
     * | $(LITERAL Identifier) $(LITERAL '=') $(RULE initializer)
     * | $(LITERAL Identifier) $(RULE templateParameters) $(LITERAL '=') $(RULE initializer)
     * ;)
     */
    boolean parseDeclarator() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Declarator,false);
        Token id = expect(tok("identifier"));
        if (id == null) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("["))) // dmd doesn't accept pointer after identifier
        {
            warn("C-style array declaration.");
            while (moreTokens() && currentIs(tok("[")))
                if (!parseTypeSuffix()) {
                    cleanup(m);
                    return false;
                }
        }
        if (currentIs(tok("("))) {
            if (!parseTemplateParameters()) {
                cleanup(m);
                return false;
            }
            if (!tokenCheck("=")) {
                cleanup(m);
                return false;
            }
            if (!parseInitializer()) {
                cleanup(m);
                return false;
            }
        } else if (currentIs(tok("="))) {
            advance();
            if (!parseNodeQ("node.initializer", "Initializer")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, DECLARATOR, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DefaultStatement,false);
        if (!tokenCheck("default")) {
            cleanup(m);
            return false;
        }
        Token colon = expect(tok(":"));
        if (colon == null) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.declarationsAndStatements", "DeclarationsAndStatements")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, DEFAULT_STATEMENT, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DeleteExpression,false);
//            node.line = current().line;
//            node.column = current().column;
        if (!tokenCheck("delete")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.unaryExpression", "UnaryExpression")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, DELETE_EXPRESSION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Deprecated,false);
        if (!tokenCheck("deprecated")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("("))) {
            advance();
            if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                cleanup(m);
                return false;
            }
            if (!tokenCheck(")")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, DEPRECATED_ATTRIBUTE, true);//todo element type
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Destructor,false);
//            node.comment = comment;
//            comment = null;
        if (!tokenCheck("~")) {
            cleanup(m);
            return false;
        }
        if (!moreTokens()) {
            error("'this' expected");
            exit_section_(builder, m, DESTRUCTOR, true);
            return false;
        }
//            node.index = current().index;
//            node.line = current().line;
//            node.column = current().column;
        if (!tokenCheck("this")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("(")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck(")")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok(";")))
            advance();
        else {
            while (moreTokens() && currentIsMemberFunctionAttribute())
                if (!parseMemberFunctionAttribute()) {
                    cleanup(m);
                    return false;
                }
//                ownArray(node.memberFunctionAttributes, memberFunctionAttributes);
            if (!parseNodeQ("node.functionBody", "FunctionBody")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, DESTRUCTOR, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DoStatement,false);
        if (!tokenCheck("do")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.statementNoCaseNoDefault", "StatementNoCaseNoDefault")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("while")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("(")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.expression", "Expression")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck(")")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck(";")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, DO_STATEMENT, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            EnumBody node = allocator.make!EnumBody;
        final Marker m = enter_section_(builder);
        Token open = expect(tok("{"));
        if (open == null) {
            cleanup(m);
            return false;
        }
//            node.startLocation = open.index;
//            StackBuffer enumMembers;
//        DummyClasses.EnumMember last;
        while (moreTokens()) {
            if (currentIs(tok("identifier"))) {
//                    auto c = allocator.setCheckpoint();
                boolean e = parseEnumMember();
//                    if (!e)
//                        allocator.rollback(c);//todo
//                    else
//                        last = e;
                if (currentIs(tok(","))) {
//                        if (last != null && last.comment == null)
//                        last.comment = current().trailingComment;
                    advance();
                    if (!currentIs(tok("}")))
                        continue;
                }
                if (currentIs(tok("}"))) {
//                        if (last != null && last.comment == null)
//                        last.comment = tokens[index - 1].trailingComment;
                    break;
                } else {
                    error("',' or '}' expected");
                    if (currentIs(tok("}")))
                        break;
                }
            } else
                error("Enum member expected");
        }
//            ownArray(node.enumMembers, enumMembers);
        Token close = expect(tok("}"));
//            if (close != null)
//            node.endLocation = close.index;
        exit_section_(builder, m, ENUM_BODY, true);
        return true;
    }

    boolean parseAnonymousEnumMember() {
        return parseAnonymousEnumMember(false);
    }

    /**
     * $(GRAMMAR $(RULEDEF anonymousEnumMember):
     * $(RULE type) $(LITERAL identifier) $(LITERAL '=') $(RULE assignExpression)
     * | $(LITERAL identifier) $(LITERAL '=') $(RULE assignExpression)
     * | $(LITERAL identifier)
     * ;)
     */
    boolean parseAnonymousEnumMember(boolean typeAllowed) {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AnonymousEnumMember,false);

        if (currentIs(tok("identifier")) && peekIsOneOf(tok(","), tok("="), tok("}"))) {
//                node.comment = current().comment;
            if (!tokenCheck("node.name", "identifier")) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok("="))) {
                advance(); // =
                if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                    cleanup(m);
                    return false;
                }
            }
        } else if (typeAllowed) {
//            node.comment = current().comment;
            if (!parseNodeQ("node.type", "Type")) {
                cleanup(m);
                return false;
            }
            if (tokenCheck("node.name", "identifier")) {
                cleanup(m);
                return false;
            }
            if (!tokenCheck("=")) {
                cleanup(m);
                return false;
            }
            if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                cleanup(m);
                return false;
            }
        } else {
            error("Cannot specify anonymous enum member type if anonymous enum has a base type.");
            exit_section_(builder, m, ANONYMOUS_ENUM_DECLARATION, true);
            return false;
        }
        exit_section_(builder, m, ENUM_MEMBER, true);
        return true;
    }

    /**
     * $(GRAMMAR $(RULEDEF anonymousEnumDeclaration):
     * $(LITERAL 'enum') ($(LITERAL ':') $(RULE type))? $(LITERAL '{') $(RULE anonymousEnumMember)+ $(LITERAL '}')
     * ;)
     */
    boolean parseAnonymousEnumDeclaration() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AnonymousEnumDeclaration,false);
        if (!tokenCheck("enum")) {
            cleanup(m);
            return false;
        }
        boolean hasBaseType = currentIs(tok(":"));
        if (hasBaseType) {
            advance();
            if (!parseNodeQ("node.baseType", "Type")) {
                cleanup(m);
                return false;
            }
        }
        if (!tokenCheck("{")) {
            cleanup(m);
            return false;
        }
        while (moreTokens()) {
            if (currentIs(tok(","))) {
//                    if (last != null && last.comment == null)
//                    last.comment = current().trailingComment;
                advance();
                continue;
            } else if (currentIs(tok("}"))) {
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
//            ownArray(node.members, members);
        if (!tokenCheck("}")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, ANONYMOUS_ENUM_DECLARATION, true);
        return true;
    }

    /**
     * Parses an EnumDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF enumDeclaration):
     * $(LITERAL 'enum') $(LITERAL Identifier) ($(LITERAL ':') $(RULE type))? $(LITERAL ';')
     * | $(LITERAL 'enum') $(LITERAL Identifier) ($(LITERAL ':') $(RULE type))? $(RULE enumBody)
     * ;)
     */
    boolean parseEnumDeclaration() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.EnumDeclaration,false);
        if (!tokenCheck("enum")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("node.name", "identifier")) {
            cleanup(m);
            return false;
        }
//            node.comment = comment;
//            comment = null;
        if (currentIs(tok(";"))) {
            advance();
            return false;
        }
        if (currentIs(tok(":"))) {
            advance(); // skip ':'
            if (!parseNodeQ("node.type", "Type")) {
                cleanup(m);
                return false;
            }
        }
        if (!parseNodeQ("node.enumBody", "EnumBody")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, ENUM_DECLARATION, true);
        return true;
    }

    /**
     * Parses an EnumMember
     * <p>
     * $(GRAMMAR $(RULEDEF enumMember):
     * $(LITERAL Identifier)
     * | $(LITERAL Identifier) $(LITERAL '=') $(RULE assignExpression)
     * ;)
     */
    boolean parseEnumMember() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.EnumMember,false);
//            node.comment = current().comment;
        if (!tokenCheck("node.name", "identifier")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("="))) {
            advance();
            if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, ENUM_MEMBER, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.EponymousTemplateDeclaration,false);
        advance(); // enum
        Token ident = expect(tok("identifier"));
        if (!nullCheck("ident")) {
            cleanup(m);
            return false;
        }
//            node.name = *ident;
        if (!parseNodeQ("node.templateParameters", "TemplateParameters")) {
            cleanup(m);
            return false;
        }
        expect(tok("="));
        if (!parseAssignExpression())
            if (!parseNodeQ("node.type", "Type")) {
                cleanup(m);
                return false;
            }
        expect(tok(";"));
        exit_section_(builder, m, VAR_FUNC_DECLARATION, true);//todo check type
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
    boolean parseEqualExpression(boolean parseShift) {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.EqualExpression,false);
        if (parseShift) {
            boolean shift = parseShiftExpression();
            if (!shift) {
                cleanup(m);
                return false;
            }
        }
        if (currentIsOneOf(tok("=="), tok("!="))) {
            advance();
        }
        if (!parseNodeQ("node.right", "ShiftExpression")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, EQUAL_EXPRESSION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_(builder);
        if (suppressedErrorCount > MAX_ERRORS)
            return false;
        if (!moreTokens()) {
            error("Expected expression instead of EOF");
            exit_section_(builder, m, EXPRESSION_STATEMENT, true);//todo types
            return false;
        }
        final boolean result = parseCommaSeparatedRule("Expression", "AssignExpression");
        exit_section_(builder, m, EXPRESSION_STATEMENT, result);//todo types
        return result;
    }

    /**
     * Parses an ExpressionStatement
     * <p>
     * $(GRAMMAR $(RULEDEF expressionStatement):
     * $(RULE expression) $(LITERAL ';')
     * ;)
     */
    boolean parseExpressionStatement(boolean parseExpression) {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ExpressionStatement,false);
        if (parseExpression) {
            final boolean b = parseExpression();
            if (!b) {
                cleanup(m);
                return false;
            }
        }
        if (expect(tok(";")) == null) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, EXPRESSION_STATEMENT, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker m = enter_section_(builder);
        final boolean result = simpleParse("FinalSwitchStatement", tok("final"), "switchStatement|parseSwitchStatement");
        exit_section_(builder, m, FINAL_SWITCH_STATEMENT, result);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Finally,false);
        if (!tokenCheck("finally")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, FINALLY_STATEMENT, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ForStatement,false);
        if (!tokenCheck("for")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("(")) {
            cleanup(m);
            return false;
        }

        if (currentIs(tok(";")))
            advance();
        else if (!parseNodeQ("node.initialization", "DeclarationOrStatement")) {
            cleanup(m);
            return false;
        }

        if (currentIs(tok(";")))
            advance();
        else {
            if (!parseNodeQ("node.test", "Expression")) {
                cleanup(m);
                return false;
            }
            expect(tok(";"));
        }

        if (!currentIs(tok(")")))
            if (!parseNodeQ("node.increment", "Expression")) {
                cleanup(m);
                return false;
            }

        if (!tokenCheck(")")) {
            return false;
        }

        // Intentionally return an incomplete parse tree so that DCD will work
        // more correctly.
        if (currentIs(tok("}"))) {
            error("Statement expected");
            exit_section_(builder, m, FOR_STATEMENT, true);
            return true;
        }

        if (!parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, FOR_STATEMENT, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            ForeachStatement node = allocator.make!ForeachStatement;
        final Marker m = enter_section_(builder);
        if (currentIsOneOf(tok("foreach"), tok("foreach_reverse"))) {
            advance();
        } else {
            error("\"foreach\" or \"foreach_reverse\" expected");
            exit_section_(builder, m, FOREACH_STATEMENT, true);
            return false;
        }
        if (!tokenCheck("(")) {
            cleanup(m);
            return false;
        }
        boolean feType = parseForeachTypeList();
        if (!feType) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck(";")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.low", "Expression")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok(".."))) {
//                if (!canBeRange)
//                {
//                    error("Cannot have more than one foreach variable for a foreach range statement");
//                    return null;
//                }
            advance();
            if (!parseNodeQ("node.high", "Expression")) {
                cleanup(m);
                return false;
            }
        }
        if (!tokenCheck(")")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("}"))) {
            error("Statement expected"/*, false*/);
            exit_section_(builder, m, FOREACH_STATEMENT, true);
            return true; // this line makes DCD better
        }
        if (!parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, FOREACH_STATEMENT, true);
        return true;
    }

    /**
     * Parses a ForeachType
     * <p>
     * $(GRAMMAR $(RULEDEF foreachType):
     * $(LITERAL 'ref')? $(RULE typeConstructors)? $(RULE type)? $(LITERAL Identifier)
     * | $(RULE typeConstructors)? $(LITERAL 'ref')? $(RULE type)? $(LITERAL Identifier)
     * ;)
     */
    boolean parseForeachType() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ForeachType,false);
        if (currentIs(tok("ref"))) {
            advance();
        }
        if (currentIsOneOf(tok("const"), tok("immutable"),
            tok("inout"), tok("shared")) && !peekIs(tok("("))) {
            trace("\033[01;36mType constructor");
            if (!parseNodeQ("node.typeConstructors", "TypeConstructors")) {
                cleanup(m);
                return false;
            }
        }
        if (currentIs(tok("ref"))) {
            advance();
        }
        if (currentIs(tok("identifier")) && peekIsOneOf(tok(","), tok(";"))) {
            advance();
            exit_section_(builder, m, FOREACH_TYPE, true);
            return true;
        }
        if (!parseNodeQ("node.type", "Type")) {
            cleanup(m);
            return false;
        }
        Token ident = expect(tok("identifier"));
        if (ident == null) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, FOREACH_TYPE, true);
        return true;
    }

    /**
     * Parses a ForeachTypeList
     * <p>
     * $(GRAMMAR $(RULEDEF foreachTypeList):
     * $(RULE foreachType) ($(LITERAL ',') $(RULE foreachType))*
     * ;)
     */
    boolean parseForeachTypeList() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean b = parseCommaSeparatedRule("ForeachTypeList", "ForeachType");
        exit_section_(builder, marker, FOREACH_TYPE_LIST, b);
        return b;
    }

    /**
     * Parses a FunctionAttribute
     * <p>
     * $(GRAMMAR $(RULEDEF functionAttribute):
     * $(RULE atAttribute)
     * | $(LITERAL '')
     * | $(LITERAL '')
     * ;)
     */
    boolean parseFunctionAttribute(boolean validate) {
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.FunctionAttribute,false);
        Token.IdType i = current().type;
        if (i.equals(tok("@"))) {
            if (!parseNodeQ("node.atAttribute", "AtAttribute")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("const")) || i.equals(tok("const"))) {
            advance();
        } else {
            if (validate) {
                error("@attribute, \"\", or \"\" expected");
                exit_section_(builder, m, FUNCTION_ATTRIBUTE, true);
            }
            return false;
        }
        exit_section_(builder, m, FUNCTION_ATTRIBUTE, true);
        return true;
    }

    /**
     * Parses a FunctionBody
     * <p>
     * $(GRAMMAR $(RULEDEF functionBody):
     * $(RULE blockStatement)
     * | ($(RULE inStatement) | $(RULE outStatement) | $(RULE outStatement) $(RULE inStatement) | $(RULE inStatement) $(RULE outStatement))? $(RULE bodyStatement)?
     * ;)
     */
    boolean parseFunctionBody() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.FunctionBody,false);
        if (currentIs(tok(";"))) {
            advance();
            exit_section_(builder, m, FUNCTION_BODY, true);
            return true;
        } else if (currentIs(tok("{"))) {
            if (!parseNodeQ("node.blockStatement", "BlockStatement")) {
                cleanup(m);
                return false;
            }
        } else if (currentIsOneOf(tok("in"), tok("out"), tok("body"))) {
            if (currentIs(tok("in"))) {
                if (!parseNodeQ("node.inStatement", "InStatement")) {
                    cleanup(m);
                    return false;
                }
                if (currentIs(tok("out")))
                    if (!parseNodeQ("node.outStatement", "OutStatement")) {
                        cleanup(m);
                        return false;
                    }
            } else if (currentIs(tok("out"))) {
                if (!parseNodeQ("node.outStatement", "OutStatement")) {
                    cleanup(m);
                    return false;
                }
                if (currentIs(tok("in")))
                    if (!parseNodeQ("node.inStatement", "InStatement")) {
                        cleanup(m);
                        return false;
                    }
            }
            // Allow function bodies without body statements because this is
            // valid inside of interfaces.
            if (currentIs(tok("body")))
                if (!parseNodeQ("node.bodyStatement", "BodyStatement")) {
                    cleanup(m);
                    return false;
                }
        } else {
            error("'in', 'out', 'body', or block statement expected");
            exit_section_(builder, m, FUNCTION_BODY, true);
            return false;
        }
        exit_section_(builder, m, FUNCTION_BODY, true);
        return true;
    }

    boolean parseFunctionCallExpression() {
        return parseFunctionCallExpression(true);
    }

    /**
     * Parses a FunctionCallExpression
     * <p>
     * $(GRAMMAR $(RULEDEF functionCallExpression):
     * $(RULE symbol) $(RULE arguments)
     * | $(RULE unaryExpression) $(RULE arguments)
     * | $(RULE type) $(RULE arguments)
     * ;)
     */
    boolean parseFunctionCallExpression(boolean parseUnary)//(UnaryExpression unary = null)
    {
//            mixin(traceEnterAndExit!(__FUNCTION__));


        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.FunctionCallExpression,false);
        Token.IdType i = current().type;
        if (i.equals(tok("const")) || i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("shared")) || i.equals(tok("scope")) || i.equals(tok("const")) || i.equals(tok("const"))) {
            if (!parseNodeQ("node.type", "Type")) {
                cleanup(m/*todo*/);
                return false;
            }
            if (!parseNodeQ("node.arguments", "Arguments")) {
                cleanup(m/*todo*/);
                return false;
            }
        } else {
            if (!parseUnary) {
            } else if (!parseNodeQ("node.unaryExpression", "UnaryExpression")) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok("!")))
                if (!parseNodeQ("node.templateArguments", "TemplateArguments")) {
                    cleanup(m);
                    return false;
                }
            if (!parseUnary)
                if (!parseNodeQ("node.arguments", "Arguments")) {
                    cleanup(m);
                    return false;
                }
        }
        exit_section_(builder, m, null, true);//todo type
        return true;
    }

    boolean parseFunctionDeclaration() {
        return parseFunctionDeclaration(true, false);
    }

    /**
     * Parses a FunctionDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF functionDeclaration):
     * ($(RULE storageClass)+ | $(RULE _type)) $(LITERAL Identifier) $(RULE parameters) $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ';'))
     * | ($(RULE storageClass)+ | $(RULE _type)) $(LITERAL Identifier) $(RULE templateParameters) $(RULE parameters) $(RULE memberFunctionAttribute)* $(RULE raint)? ($(RULE functionBody) | $(LITERAL ';'))
     * ;)
     */
    boolean parseFunctionDeclaration(boolean parseType, boolean isAuto)//(Type type = null,Attribute[] attributes = null)
    {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.FunctionDeclaration,false);
//            node.comment = comment;
//            comment = null;
//            StackBuffer memberFunctionAttributes;
//            node.attributes = attributes;
        if (isAuto) {
//                StackBuffer storageClasses;
            while (isStorageClass())
                if (!parseStorageClass()) {
                    cleanup(m);
                    return false;
                }
//                ownArray(node.storageClasses, storageClasses);

//                for( a; node.attributes)
//                {
//                    if (a.attribute == tok("auto"))
//                    node.hasAuto = true;
//                else if (a.attribute == tok("ref"))
//                    node.hasRef = true;
//                else
//                    continue;
//                }
        } else {
            while (moreTokens() && currentIsMemberFunctionAttribute())
                if (!parseMemberFunctionAttribute()) {
                    cleanup(m);
                    return false;
                }
            if (parseType) {
                if (!parseNodeQ("node.returnType", "Type")) {
                    cleanup(m);
                    return false;
                }
            }
//                else
//                node.returnType = type;
        }

        if (!tokenCheck("node.name", "identifier")) {
            cleanup(m);
            return false;
        }
        if (!currentIs(tok("("))) {
            error("'(' expected");
            exit_section_(builder, m, FUNC_DECLARATION, true);
//                cleanup(m);//todo
            return false;
        }
        Token p = peekPastParens();
        boolean isTemplate = p != null && p.type.equals(tok("("));

        if (isTemplate) {
            if (!parseNodeQ("node.templateParameters", "TemplateParameters")) {
                cleanup(m);
                return false;
            }
        }

        if (!parseNodeQ("node.parameters", "Parameters")) {
            cleanup(m);
            return false;
        }

        while (moreTokens() && currentIsMemberFunctionAttribute())
            if (!parseMemberFunctionAttribute()) {
                cleanup(m);
                return false;
            }

        if (isTemplate && currentIs(tok("if"))) {
            if (!parseNodeQ("node.raint", "Constraint")) {
                cleanup(m);
                return false;
            }
        }

        if (currentIs(tok(";")))
            advance();
        else {
            if (!parseNodeQ("node.functionBody", "FunctionBody")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, FUNC_DECLARATION, true);
        return true;
    }

    /**
     * Parses a FunctionLiteralExpression
     * <p>
     * $(GRAMMAR $(RULEDEF functionLiteralExpression):
     * | $(LITERAL 'delegate') $(RULE type)? ($(RULE parameters) $(RULE functionAttribute)*)? $(RULE functionBody)
     * | $(LITERAL 'function') $(RULE type)? ($(RULE parameters) $(RULE functionAttribute)*)? $(RULE functionBody)
     * | $(RULE parameters) $(RULE functionAttribute)* $(RULE functionBody)
     * | $(RULE functionBody)
     * | $(LITERAL Identifier) $(LITERAL '=>') $(RULE assignExpression)
     * | $(LITERAL 'function') $(RULE type)? $(RULE parameters) $(RULE functionAttribute)* $(LITERAL '=>') $(RULE assignExpression)
     * | $(LITERAL 'delegate') $(RULE type)? $(RULE parameters) $(RULE functionAttribute)* $(LITERAL '=>') $(RULE assignExpression)
     * | $(RULE parameters) $(RULE functionAttribute)* $(LITERAL '=>') $(RULE assignExpression)
     * ;)
     */
    boolean parseFunctionLiteralExpression() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.FunctionLiteralExpression,false);
//            node.line = current().line;
//            node.column = current().column;
        if (currentIsOneOf(tok("function"), tok("delegate"))) {
            advance();
            if (!currentIsOneOf(tok("("), tok("in"), tok("body"),
                tok("out"), tok("{"), tok("=>")))
                if (!parseNodeQ("node.returnType", "Type")) {
                    cleanup(m);
                    return false;
                }
        }
        if (startsWith(tok("identifier"), tok("=>"))) {
            advance();
            advance(); // =>
            if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                cleanup(m);
                return false;
            }
            exit_section_(builder, m, FUNCTION_LITERAL, true);//todo type
            return true;
        } else if (currentIs(tok("("))) {
            if (!parseNodeQ("node.parameters", "Parameters")) {
                cleanup(m);
                return false;
            }
            while (currentIsMemberFunctionAttribute()) {
//                    auto c =/ allocator.setCheckpoint();
                if (!parseMemberFunctionAttribute()) {
//                        allocator.rollback(c);
                    break;
                }
            }
//                ownArray(node.memberFunctionAttributes, memberFunctionAttributes);
        }
        if (currentIs(tok("=>"))) {
            advance();
            if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                cleanup(m);
                return false;
            }
        } else if (!parseNodeQ("node.functionBody", "FunctionBody")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, FUNCTION_LITERAL, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.GotoStatement,false);
        if (!tokenCheck("goto")) {
            cleanup(m);
            return false;
        }
        Token.IdType i = current().type;
        if (i.equals(tok("identifier")) || i.equals(tok("default"))) {
            advance();
        } else if (i.equals(tok("case"))) {
            advance();
            if (!currentIs(tok(";")))
                if (!parseNodeQ("node.expression", "Expression")) {
                    cleanup(m/*todo*/);
                    return false;
                }
        } else {
            error("Identifier, \"default\", or \"case\" expected");
            exit_section_(builder, m, GOTO_STATEMENT, true);
            return false;//todo cleanup?
        }
        if (!tokenCheck(";")) {
            return false;
        }
        exit_section_(builder, m, GOTO_STATEMENT, true);
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
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IdentifierChain,false);
        while (moreTokens()) {
            Token ident = expect(tok("identifier"));
            if (ident == null) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok("."))) {
                advance();
                continue;
            } else
                break;
        }
        exit_section_(builder, m, IDENTIFIER_LIST, true);//todo type
        return true;
    }

    /**
     * Parses an IdentifierList
     * <p>
     * $(GRAMMAR $(RULEDEF identifierList):
     * $(LITERAL Identifier) ($(LITERAL ',') $(LITERAL Identifier))*
     * ;)
     */
    boolean parseIdentifierList() {
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IdentifierList,false);
        while (moreTokens()) {
            Token ident = expect(tok("identifier"));
            if (ident == null) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok(","))) {
                advance();
                continue;
            } else
                break;
        }
        exit_section_(builder, m, IDENTIFIER_LIST, true);
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
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IdentifierOrTemplateChain,false);
        int identifiersOrTemplateInstancesLength = 0;
        while (moreTokens()) {
//                auto c = allocator.setCheckpoint();
            if (!parseIdentifierOrTemplateInstance()) {
                identifiersOrTemplateInstancesLength++;
//                    allocator.rollback(c);
                if (identifiersOrTemplateInstancesLength == 0) {
                    cleanup(m);
                    return false;
                } else
                    break;
            }
            if (!currentIs(tok(".")))
                break;
            else
                advance();
        }
        exit_section_(builder, m, IDENTIFIER_LIST/*todo type*/, true);
        return true;
    }

    /**
     * Parses an IdentifierOrTemplateInstance
     * <p>
     * $(GRAMMAR $(RULEDEF identifierOrTemplateInstance):
     * $(LITERAL Identifier)
     * | $(RULE templateInstance)
     * ;)
     */
    boolean parseIdentifierOrTemplateInstance() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IdentifierOrTemplateInstance,false);
        if (peekIs(tok("!")) && !startsWith(tok("identifier"), tok("!"), tok("is")) && !startsWith(tok("identifier"), tok("!"), tok("in"))) {
            if (!parseNodeQ("node.templateInstance", "TemplateInstance")) {
                cleanup(m);
                return false;
            }
        } else {
            Token ident = expect(tok("identifier"));
            if (ident == null) {
                cleanup(m);
                return false;
            }
//                node.identifier = ident;
        }
        exit_section_(builder, m, IDENTIFIER_LIST/*todo type*/, true);
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
    boolean parseIdentityExpression(boolean parseShift)//(ExpressionNode shift = null)
    {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IdentityExpression,false);
        if (parseShift) {
            if (!parseShiftExpression()) {
                cleanup(m);
                return false;
            }
        }
        if (currentIs(tok("!"))) {
            advance();
        }
        if (!tokenCheck("is")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.right", "ShiftExpression")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, IDENTITY_EXPRESSION, true);
        return true;
    }

    /**
     * Parses an IfStatement
     * <p>
     * $(GRAMMAR $(RULEDEF ifStatement):
     * $(LITERAL 'if') $(LITERAL '$(LPAREN)') $(RULE ifCondition) $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement) ($(LITERAL 'else') $(RULE declarationOrStatement))?
     * $(RULEDEF ifCondition):
     * $(LITERAL 'auto') $(LITERAL Identifier) $(LITERAL '=') $(RULE expression)
     * | $(RULE typeConstructors) $(LITERAL Identifier) $(LITERAL '=') $(RULE expression)
     * | $(RULE type) $(LITERAL Identifier) $(LITERAL '=') $(RULE expression)
     * | $(RULE expression)
     * ;)
     */
    boolean parseIfStatement() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IfStatement,false);
//            node.line = current().line;
//            node.column = current().column;
        if (!tokenCheck("if")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("(")) {
            cleanup(m);
            return false;
        }

        if (currentIs(tok("auto"))) {
            advance();
            Token i = expect(tok("identifier"));
            if (i != null)
                expect(tok("="));
            if (!parseNodeQ("node.expression", "Expression")) {
                cleanup(m);
                return false;
            }
        } else {
            // consume for TypeCtors = identifier
            if (isTypeCtor(current().type)) {
                Bookmark before_advance = null;
                while (isTypeCtor(current().type)) {
                    if (before_advance != null) {
                        abandonBookmark(before_advance);
                    }
                    before_advance = setBookmark();
                    advance();
                }
                // goes back for TypeCtor(Type) = identifier
                if (currentIs(tok("("))) {
//                    builder.
                    goToBookmark(before_advance);
                }
            }

            Bookmark b = setBookmark();
//                c = allocator.setCheckpoint();
            boolean type = parseType();
            if (!type || !currentIs(tok("identifier"))
                || !peekIs(tok("="))) {
//                    allocator.rollback(c);
                goToBookmark(b);
                if (!parseNodeQ("node.expression", "Expression")) {
                    cleanup(m);
                    return false;
                }
            } else {
                abandonBookmark(b);
//                mixin(tokenCheck("node.identifier", "identifier"));//todo?
                if (!tokenCheck("=")) {
                    cleanup(m);
                    return false;
                }
                if (!parseNodeQ("node.expression", "Expression")) {
                    cleanup(m);
                    return false;
                }
            }
        }

        if (!tokenCheck(")")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("}"))) {
            error("Statement expected"/*, false*/);//todo
            exit_section_(builder, m, IF_STATEMENT, true);
            return true; // this line makes DCD better
        }
        if (!parseNodeQ("node.thenStatement", "DeclarationOrStatement")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("else"))) {
            advance();
            if (!parseNodeQ("node.elseStatement", "DeclarationOrStatement")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, IF_STATEMENT, true);
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
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ImportBind,false);
        Token ident = expect(tok("identifier"));
        if (ident == null) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("="))) {
            advance();
            Token id = expect(tok("identifier"));
            if (id == null) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, IMPORT_BIND, true);
        return true;
    }

    /**
     * Parses ImportBindings
     * <p>
     * $(GRAMMAR $(RULEDEF importBindings):
     * $(RULE singleImport) $(LITERAL ':') $(RULE importBind) ($(LITERAL ',') $(RULE importBind))*
     * ;)
     */
    boolean parseImportBindings(boolean parseSingleImport) {
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ImportBindings,false);
        if (parseSingleImport) {
            if (!parseSingleImport()) {
                cleanup(m);
                return false;
            }
        }
        if (!tokenCheck(":")) {
            cleanup(m);
            return false;
        }
        while (moreTokens()) {
//                c = allocator.setCheckpoint();
            if (!parseImportBind()) {
                if (currentIs(tok(",")))
                    advance();
                else
                    break;
            } else {
//                    allocator.rollback(c);
                break;
            }
        }
        exit_section_(builder, m, IMPORT_BIND_LIST, true);//todo type
        return true;
    }

    /**
     * Parses an ImportDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF importDeclaration):
     * $(LITERAL 'import') $(RULE singleImport) ($(LITERAL ',') $(RULE singleImport))* ($(LITERAL ',') $(RULE importBindings))? $(LITERAL ';')
     * | $(LITERAL 'import') $(RULE importBindings) $(LITERAL ';')
     * ;)
     */
    boolean parseImportDeclaration() {
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ImportDeclaration,false);
//            node.startIndex = current().index;
        if (!tokenCheck("import")) {
            return false;
        }
        boolean si = parseSingleImport();
        if (!si)
            return false;
        if (currentIs(tok(":")))
            parseImportBindings(!si);//todo I think this should be negated check
        else {
            if (currentIs(tok(","))) {
                advance();
                while (moreTokens()) {
                    boolean single = parseSingleImport();
                    if (!single) {
                        cleanup(m);
                        return false;
                    }
                    if (currentIs(tok(":"))) {
                        parseImportBindings(!single);//todo negate?
                        break;
                    } else {
                        if (currentIs(tok(",")))
                            advance();
                        else
                            break;
                    }
                }
            }
//                ownArray(node.singleImports, singleImports);
        }
//            node.endIndex = (moreTokens() ? current() : previous()).index + 1;
        if (!tokenCheck(";")) {
            return false;
        }
        exit_section_(builder, m, IMPORT_DECLARATION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean b = simpleParse("ImportExpression", tok("import"), tok("("),
            "assignExpression|parseAssignExpression", tok(")"));
        exit_section_(builder, marker, IMPORT_EXPRESSION, b);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Index(),false);
        if (!parseNodeQ("node.low", "AssignExpression")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok(".."))) {
            advance();
            if (!parseNodeQ("node.high", "AssignExpression")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, INDEX_EXPRESSION, true);//todo type
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
    boolean parseIndexExpression(boolean parseUnary)//(UnaryExpression unaryExpression = null)
    {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IndexExpression,false);
        if (parseUnary) {
            if (!parseUnaryExpression()) {
                cleanup(m);
                return false;
            }
        }
        if (!tokenCheck("[")) {
            cleanup(m);
            return false;
        }
        while (true) {
            if (!moreTokens()) {
                error("Expected unary expression instead of EOF");
                exit_section_(builder, m, INDEX_EXPRESSION, true);
                return false;
            }
            if (currentIs(tok("]")))
                break;
            if (!(parseIndex())) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok(",")))
                advance();
            else
                break;
        }
        advance(); // ]
        exit_section_(builder, m, INDEX_EXPRESSION, true);
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
    boolean parseInExpression(boolean parseShift)//(ExpressionNode shift = null)
    {
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.InExpression,false);
        if (parseShift) {
            if (!parseShiftExpression()) {
                cleanup(m);
                return false;
            }
        }
        if (currentIs(tok("!"))) {
//                node.negated = true;
            advance();
        }
        if (!tokenCheck("in")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.right", "ShiftExpression")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, IN_EXPRESSION, true);
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
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.InStatement,false);
        Token i = expect(tok("in"));
        if (i == null) {
            cleanup(m);
            return false;
        }
//            node.inTokenLocation = i.index;
        if (!parseNodeQ("node.blockStatement", "BlockStatement")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, IN_STATEMENT, true);
        return true;
    }

    /**
     * Parses an Initializer
     * <p>
     * $(GRAMMAR $(RULEDEF initializer):
     * $(LITERAL 'void')
     * | $(RULE nonVoidInitializer)
     * ;)
     */
    boolean parseInitializer() {
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Initializer,false);
        if (currentIs(tok("void")) && peekIsOneOf(tok(","), tok(";")))
            advance();
        else if (!parseNodeQ("node.nonVoidInitializer", "NonVoidInitializer")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, INITIALIZER, true);
        return true;
    }

    /**
     * Parses an InterfaceDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF interfaceDeclaration):
     * $(LITERAL 'interface') $(LITERAL Identifier) $(LITERAL ';')
     * | $(LITERAL 'interface') $(LITERAL Identifier) ($(LITERAL ':') $(RULE baseClassList))? $(RULE structBody)
     * | $(LITERAL 'interface') $(LITERAL Identifier) $(RULE templateParameters) $(RULE raint)? ($(LITERAL ':') $(RULE baseClassList))? $(RULE structBody)
     * | $(LITERAL 'interface') $(LITERAL Identifier) $(RULE templateParameters) ($(LITERAL ':') $(RULE baseClassList))? $(RULE raint)? $(RULE structBody)
     * ;)
     */
    boolean parseInterfaceDeclaration() {
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.InterfaceDeclaration,false);
        expect(tok("interface"));
        final boolean b = parseInterfaceOrClass();
        exit_section_(builder, m, INTERFACE_DECLARATION, b);
        return b;
    }

    /**
     * Parses an Invariant
     * <p>
     * $(GRAMMAR $(RULEDEF invariant):
     * $(LITERAL 'invariant') ($(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)'))? $(RULE blockStatement)
     * ;)
     */
    boolean parseInvariant() {
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Invariant,false);
//            node.index = current().index;
//            node.line = current().line;
        if (!tokenCheck("invariant")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("("))) {
            advance();
            if (!tokenCheck(")")) {
                cleanup(m);
                return false;
            }
        }
        if (!parseNodeQ("node.blockStatement", "BlockStatement")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, INVARIANT, true);
        return true;
    }

    /**
     * Parses an IsExpression
     * <p>
     * $(GRAMMAR $(RULEDEF isExpression):
     * $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL '$(RPAREN)')
     * $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL ':') $(RULE typeSpecialization) $(LITERAL '$(RPAREN)')
     * $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL '=') $(RULE typeSpecialization) $(LITERAL '$(RPAREN)')
     * $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL ':') $(RULE typeSpecialization) $(LITERAL ',') $(RULE templateParameterList) $(LITERAL '$(RPAREN)')
     * $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL '=') $(RULE typeSpecialization) $(LITERAL ',') $(RULE templateParameterList) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseIsExpression() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IsExpression,false);
        if (!tokenCheck("is")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("(")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.type", "Type")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("identifier")))
            advance();
        if (currentIsOneOf(tok("=="), tok(":"))) {
            advance();
            if (!parseNodeQ("node.typeSpecialization", "TypeSpecialization")) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok(","))) {
                advance();
                if (!parseNodeQ("node.templateParameterList", "TemplateParameterList")) {
                    cleanup(m);
                    return false;
                }
            }
        }
        if (!tokenCheck(")")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, IS_EXPRESSION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.KeyValuePair,false);
        if (!parseNodeQ("node.key", "AssignExpression")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck(":")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.value", "AssignExpression")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, KEY_VALUE_PAIR, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.KeyValuePairs,false);
        while (moreTokens()) {
            if (!parseKeyValuePair()) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok(","))) {
                advance();
                if (currentIs(tok("]")))
                    break;
            } else
                break;
        }
        exit_section_(builder, m, KEY_VALUE_PAIRS, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.LabeledStatement,false);
        Token ident = expect(tok("identifier"));
        if (ident == null) {
            cleanup(m);
            return false;
        }
        expect(tok(":"));
        if (!currentIs(tok("}")))
            if (!parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement")) {
                cleanup(m);
                return false;
            }
        exit_section_(builder, m, LABELED_STATEMENT, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.LastCatch,false);
        Token t = expect(tok("catch"));
        if (t == null) {
            cleanup(m);
            return false;
        }
//            node.line = t.line;
//            node.column = t.column;
        if (!parseNodeQ("node.statementNoCaseNoDefault", "StatementNoCaseNoDefault")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, LAST_CATCH, true);
        return true;
    }

    /**
     * Parses a LinkageAttribute
     * <p>
     * $(GRAMMAR $(RULEDEF linkageAttribute):
     * $(LITERAL 'extern') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '$(RPAREN)')
     * $(LITERAL 'extern') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '-') $(LITERAL Identifier) $(LITERAL '$(RPAREN)')
     * | $(LITERAL 'extern') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '++') ($(LITERAL ',') $(RULE identifierChain) | $(LITERAL 'struct') | $(LITERAL 'class'))? $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseLinkageAttribute() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.LinkageAttribute,false);
        if (!tokenCheck("extern")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("(")) {
            cleanup(m);
            return false;
        }
        Token ident = expect(tok("identifier"));
        if (ident == null) {
            cleanup(m);
            return false;
        }
//            node.identifier = *ident;
        if (currentIs(tok("++"))) {
            advance();
//                node.hasPlusPlus = true;
            if (currentIs(tok(","))) {
                advance();
                if (currentIsOneOf(tok("struct"), tok("class")))
                    advance();
                else if (!parseNodeQ("node.identifierChain", "IdentifierChain")) {
                    cleanup(m);
                    return false;
                }
            }
        } else if (currentIs(tok("-"))) {
            advance();
            if (!tokenCheck("identifier")) {
                cleanup(m);
                return false;
            }
        }
        expect(tok(")"));
        exit_section_(builder, m, LINKAGE_ATTRIBUTE, true);
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
     * | $(LITERAL '')
     * | $(LITERAL 'return')
     * | $(LITERAL 'scope')
     * ;)
     */
    boolean parseMemberFunctionAttribute() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.MemberFunctionAttribute,false);
        Token.IdType i = current().type;
        if (i.equals(tok("@"))) {
            if (!parseNodeQ("node.atAttribute", "AtAttribute")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("shared")) || i.equals(tok("const")) || i.equals(tok("const")) || i.equals(tok("const")) || i.equals(tok("return")) || i.equals(tok("scope"))) {
            advance();
        } else {
            error("Member function attribute expected");//todo notify libdparse of spelling mistae in original source
        }
        exit_section_(builder, m, MEMBER_FUNCTION_ATTRIBUTE, true);
        return true;
    }

    /**
     * Parses a MixinDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF mixinDeclaration):
     * $(RULE mixinExpression) $(LITERAL ';')
     * | $(RULE templateMixinExpression) $(LITERAL ';')
     * ;)
     */
    boolean parseMixinDeclaration() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.MixinDeclaration,false);
        if (peekIsOneOf(tok("identifier"), tok("typeof"), tok("."))) {
            if (!parseNodeQ("node.templateMixinExpression", "TemplateMixinExpression")) {
                cleanup(m);
                return false;
            }
        } else if (peekIs(tok("("))) {
            if (!parseNodeQ("node.mixinExpression", "MixinExpression")) {
                cleanup(m);
                return false;
            }
        } else {
            error("\" (\" or identifier expected");
            exit_section_(builder, m, MIXIN_DECLARATION, true);
            return false;
        }
        expect(tok(";"));
        exit_section_(builder, m, MIXIN_DECLARATION, true);
        return true;
    }

    /**
     * Parses a MixinExpression
     * <p>
     * $(GRAMMAR $(RULEDEF mixinExpression):
     * $(LITERAL 'mixin') $(LITERAL '$(LPAREN)') $(RULE assignExpression) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseMixinExpression() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.MixinExpression,false);
        expect(tok("mixin"));
        expect(tok("("));
        if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
            cleanup(m);
            return false;
        }
        expect(tok(")"));
        exit_section_(builder, m, MIXIN_EXPRESSION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.MixinTemplateDeclaration,false);
        if (!tokenCheck("mixin")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.templateDeclaration", "TemplateDeclaration")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, TEMPLATE_MIXIN_DECLARATION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.MixinTemplateName,false);
        if (currentIs(tok("typeof"))) {
            if (!parseNodeQ("node.typeofExpression", "TypeofExpression")) {
                cleanup(m);
                return false;
            }
            expect(tok("."));
            if (!parseNodeQ("node.identifierOrTemplateChain", "IdentifierOrTemplateChain")) {
                cleanup(m);
                return false;
            }
        } else if (!parseNodeQ("node.symbol", "Symbol")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, MIXIN_TEMPLATE_NAME, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Module m = allocator.make!Module;
//        final Marker marker = enter_section_(builder);
        if (currentIs(tok("scriptLine"))) {
            advance();
        }
        boolean isDeprecatedModule = false;
        if (currentIs(tok("deprecated"))) {
            Bookmark b = setBookmark();
            advance();
            if (currentIs(tok("(")))
                skipParens();
            isDeprecatedModule = currentIs(tok("module"));
            goToBookmark(b);
        }
        if (currentIs(tok("module")) || isDeprecatedModule) {
//                c = allocator.setCheckpoint();
            parseModuleDeclaration();
//                allocator.rollback(c);
        }
        while (moreTokens()) {
//                c = allocator.setCheckpoint();
            parseDeclaration(true, true);
//                    allocator.rollback(c);
        }
//            ownArray(m.declarations, declarations);
//            assert(retVal != null);
//            return m;
        return true;
    }

    /**
     * Parses a ModuleDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF moduleDeclaration):
     * $(RULE deprecated)? $(LITERAL 'module') $(RULE identifierChain) $(LITERAL ';')
     * ;)
     */
    boolean parseModuleDeclaration() {
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ModuleDeclaration,false);
        if (currentIs(tok("deprecated")))
            if (!parseNodeQ("node.deprecated_", "Deprecated")) {
                cleanup(m);
                return false;
            }
        Token start = expect(tok("module"));
        if (start == null) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.moduleName", "IdentifierChain")) {
            cleanup(m);
            return false;
        }
//            node.comment = start.comment;
//            if (node.comment == null)
//                node.comment = start.trailingComment;
//            comment = null;
        Token end = expect(tok(";"));
//            node.startLocation = start.index;
//            if (end != null)
//                node.endLocation = end.index;
        exit_section_(builder, m, MODULE_DECLARATION, true);
        return true;
    }

    /**
     * Parses a MulExpression.
     * <p>
     * $(GRAMMAR $(RULEDEF mulExpression):
     * $(RULE powExpression)
     * | $(RULE mulExpression) ($(LITERAL '*') | $(LITERAL '/') | $(LITERAL '%')) $(RULE powExpression)
     * ;)
     */
    boolean parseMulExpression() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean b = parseLeftAssocBinaryExpression("MulExpression", "PowExpression",
            tok("*"), tok("/"), tok("%"));
        exit_section_(builder, marker, MUL_EXPRESSION_, b);
        return b;
    }

    /**
     * Parses a NewAnonClassExpression
     * <p>
     * $(GRAMMAR $(RULEDEF newAnonClassExpression):
     * $(LITERAL 'new') $(RULE arguments)? $(LITERAL 'class') $(RULE arguments)? $(RULE baseClassList)? $(RULE structBody)
     * ;)
     */
    boolean parseNewAnonClassExpression() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.NewAnonClassExpression,false);
        expect(tok("new"));
        if (currentIs(tok("(")))
            if (!parseNodeQ("node.allocatorArguments", "Arguments")) {
                cleanup(m);
                return false;
            }
        expect(tok("class"));
        if (currentIs(tok("(")))
            if (!parseNodeQ("node.ructorArguments", "Arguments")) {
                cleanup(m);
                return false;
            }
        if (!currentIs(tok("{")))
            if (!parseNodeQ("node.baseClassList", "BaseClassList")) {
                cleanup(m);
                return false;
            }
        if (!parseNodeQ("node.structBody", "StructBody")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, NEW_ANON_CLASS_EXPRESSION, true);
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
        // Parse ambiguity.
        // auto a = new int[10];
        //              ^^^^^^^
        // auto a = new int[10];
        //              ^^^****
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.NewExpression,false);
        if (peekIsOneOf(tok("class"), tok("("))) {
            if (!parseNodeQ("node.newAnonClassExpression", "NewAnonClassExpression")) {
                cleanup(m);
                return false;
            }
        } else {
            expect(tok("new"));
            if (!parseNodeQ("node.type", "Type")) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok("["))) {
                advance();
                if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                    cleanup(m);
                    return false;
                }
                expect(tok("]"));
            } else if (currentIs(tok("(")))
                if (!parseNodeQ("node.arguments", "Arguments")) {
                    cleanup(m);
                    return false;
                }
        }
        exit_section_(builder, m, NEW_EXPRESSION_WITH_ARGS, true);
        return true;
    }

    /**
     * Parses a NonVoidInitializer
     * <p>
     * $(GRAMMAR $(RULEDEF nonVoidInitializer):
     * $(RULE assignExpression)
     * | $(RULE arrayInitializer)
     * | $(RULE structInitializer)
     * ;)
     */
    boolean parseNonVoidInitializer() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.NonVoidInitializer,false);
        boolean assignExpressionParsed = false;
        boolean arrayInitializerParsed = false;
        boolean structInitializerParsed = false;
        if (currentIs(tok("{"))) {
            Token b = peekPastBraces();
            if (b != null && (b.type.equals(tok("(")))) {
                if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                    cleanup(m);
                    return false;
                }
            } else {
                assignExpressionParsed = true;
                assert (currentIs(tok("{")));
                Bookmark bookmark = setBookmark();
                boolean initializer = parseStructInitializer();
                if (initializer) {
//                            node.structInitializer = initializer;
                    abandonBookmark(bookmark);
                    structInitializerParsed = true;
                } else {
                    goToBookmark(bookmark);
                    if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                        cleanup(m);
                        return false;
                    }
                    assignExpressionParsed = true;
                }
            }
        } else if (currentIs(tok("["))) {
            Token b = peekPastBrackets();
            if (b != null && (b.type.equals(tok(","))
                || b.type.equals(tok(")"))
                || b.type.equals(tok("]"))
                || b.type.equals(tok("}"))
                || b.type.equals(tok(";")))) {
                if (!parseNodeQ("node.arrayInitializer", "ArrayInitializer")) {
                    cleanup(m);
                    return false;
                }
                arrayInitializerParsed = true;
            } else if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                cleanup(m);
                return false;
            } else {
                assignExpressionParsed = true;
            }
        } else if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
            cleanup(m);
            return false;
        } else {
            assignExpressionParsed = true;
        }
        if (!assignExpressionParsed && !structInitializerParsed && !arrayInitializerParsed) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, NON_VOID_INITIALIZER, true);
        return true;
    }

    /**
     * Parses Operands
     * <p>
     * $(GRAMMAR $(RULEDEF operands):
     * $(RULE asmExp)
     * | $(RULE asmExp) $(LITERAL ',') $(RULE operands)
     * ;)
     */
    boolean parseOperands() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Operands node = allocator.make!Operands;
        final Marker marker = enter_section_(builder);
//            StackBuffer expressions;
        while (true) {
            if (!(parseAsmExp())) {
                cleanup(marker);
                return false;
            }
            if (currentIs(tok(",")))
                advance();
            else
                break;
        }
//            ownArray(node.operands, expressions);
        exit_section_(builder, marker, OPERANDS, true);
        return true;
    }

    /**
     * Parses an OrExpression
     * <p>
     * $(GRAMMAR $(RULEDEF orExpression):
     * $(RULE xorExpression)
     * | $(RULE orExpression) $(LITERAL '|') $(RULE xorExpression)
     * ;)
     */
    boolean parseOrExpression() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean b = parseLeftAssocBinaryExpression("OrExpression", "XorExpression",
            tok("|"));
        exit_section_(builder, marker, AND_EXPRESSION_, b);//todo type
        return b;
    }

    /**
     * Parses an OrOrExpression
     * <p>
     * $(GRAMMAR $(RULEDEF orOrExpression):
     * $(RULE andAndExpression)
     * | $(RULE orOrExpression) $(LITERAL '||') $(RULE andAndExpression)
     * ;)
     */
    boolean parseOrOrExpression() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean b = parseLeftAssocBinaryExpression("OrOrExpression", "AndAndExpression",
            tok("||"));
        exit_section_(builder, marker, OR_OR_EXPRESSION, b);
        return b;
    }

    /**
     * Parses an OutStatement
     * <p>
     * $(GRAMMAR $(RULEDEF outStatement):
     * $(LITERAL 'out') ($(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '$(RPAREN)'))? $(RULE blockStatement)
     * ;)
     */
    boolean parseOutStatement() {
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.OutStatement,false);
        Token o = expect(tok("out"));
        if (o == null) {
            cleanup(m);
            return false;
        }
//            node.outTokenLocation = o.index;
        if (currentIs(tok("("))) {
            advance();
            Token ident = expect(tok("identifier"));
            if (ident == null) {
                cleanup(m);
                return false;
            }
//                node.parameter = *ident;
            expect(tok(")"));
        }
        if (!parseNodeQ("node.blockStatement", "BlockStatement")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, OUT_STATEMENT, true);
        return true;
    }

    /**
     * Parses a Parameter
     * <p>
     * $(GRAMMAR $(RULEDEF parameter):
     * $(RULE parameterAttribute)* $(RULE type)
     * $(RULE parameterAttribute)* $(RULE type) $(LITERAL Identifier)? $(LITERAL '...')
     * $(RULE parameterAttribute)* $(RULE type) $(LITERAL Identifier)? ($(LITERAL '=') $(RULE assignExpression))?
     * ;)
     */
    boolean parseParameter() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Parameter,false);
        while (moreTokens()) {
            Token.IdType type = parseParameterAttribute();//todo check args
            if (type.equals(tok("")))
                break;
        }
        if (!parseNodeQ("node.type", "Type")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("identifier"))) {
            advance();
            if (currentIs(tok("..."))) {
                advance();
//                    node.vararg = true;
            } else if (currentIs(tok("="))) {
                advance();
                if (!parseNodeQ("node.default_", "AssignExpression")) {
                    cleanup(m);
                    return false;
                }
            } else if (currentIs(tok("["))) {
                while (moreTokens() && currentIs(tok("[")))
                    if (!(parseTypeSuffix())) {
                        cleanup(m);
                        return false;
                    }
            }
        } else if (currentIs(tok("..."))) {
//                node.vararg = true;
            advance();
        } else if (currentIs(tok("="))) {
            advance();
            if (!parseNodeQ("node.default_", "AssignExpression")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, PARAMETER, true);
        return true;
    }

    /**
     * Parses a ParameterAttribute
     * <p>
     * $(GRAMMAR $(RULEDEF parameterAttribute):
     * $(RULE typeConstructor)
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
    Token.IdType parseParameterAttribute() {
        final boolean validate = false;
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Token.IdType i = current().type;
        if (i.equals(tok("immutable")) || i.equals(tok("shared")) || i.equals(tok("const")) || i.equals(tok("inout"))) {
            if (peekIs(tok("(")))
                return tok("");
        }
        if (i.equals(tok("final")) || i.equals(tok("in")) || i.equals(tok("lazy")) || i.equals(tok("out")) || i.equals(tok("ref")) || i.equals(tok("scope")) || i.equals(tok("auto")) || i.equals(tok("return"))) {
            return advance().type;
        } else {
            if (validate) {
                error("Parameter attribute expected");
            }
            return tok("");
        }
    }

    /**
     * Parses Parameters
     * <p>
     * $(GRAMMAR $(RULEDEF parameters):
     * $(LITERAL '$(LPAREN)') $(RULE parameter) ($(LITERAL ',') $(RULE parameter))* ($(LITERAL ',') $(LITERAL '...'))? $(LITERAL '$(RPAREN)')
     * | $(LITERAL '$(LPAREN)') $(LITERAL '...') $(LITERAL '$(RPAREN)')
     * | $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseParameters() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Parameters,false);
        if (!tokenCheck("(")) {
            cleanup(m);
            return false;
        }

        if (currentIs(tok(")"))) {
            advance(); // )
            exit_section_(builder, m, PARAMETERS, true);
            return true;
        }
        if (currentIs(tok("..."))) {
            advance();
//                node.hasVarargs = true;
            if (!tokenCheck(")")) {
                cleanup(m);
                return false;
            }
            exit_section_(builder, m, PARAMETERS, true);
            return true;
        }
        StackBuffer parameters;
        while (moreTokens()) {
            if (currentIs(tok("..."))) {
                advance();
//                    node.hasVarargs = true;
                break;
            }
            if (currentIs(tok(")")))
                break;
            if (!(parseParameter())) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok(",")))
                advance();
            else
                break;
        }
//            ownArray(node.parameters, parameters);
        if (!tokenCheck(")")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, PARAMETERS, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Postblit,false);
//            node.line = current().line;
//            node.column = current().column;
//            node.location = current().index;
        index += 4;//todo make sure stuff is consumed appropriately
        builder.advanceLexer();
        builder.advanceLexer();
        builder.advanceLexer();
        builder.advanceLexer();
        StackBuffer memberFunctionAttributes;
        while (currentIsMemberFunctionAttribute())
            if (!parseMemberFunctionAttribute()) {
                cleanup(m);
                return false;
            }
//            ownArray(node.memberFunctionAttributes, memberFunctionAttributes);
        if (currentIs(tok(";")))
            advance();
        else if (!parseNodeQ("node.functionBody", "FunctionBody")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, POSTBLIT, true);
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
        final Marker marker = enter_section_(builder);
        final boolean b = parseLeftAssocBinaryExpression("PowExpression", "UnaryExpression",
            tok("^^"));
        exit_section_(builder, marker, POW_EXPRESSION_, b);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean res = simpleParse("PragmaDeclaration", "pragmaExpression|parsePragmaExpression", tok(";"));
        exit_section_(builder, marker, PRAGMA_STATEMENT, true);//todo type
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
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.PragmaExpression,false);
        expect(tok("pragma"));
        expect(tok("("));
        Token ident = expect(tok("identifier"));
        if (ident == null) {
            cleanup(m);
            return false;
        }
//            node.identifier = *ident;
        if (currentIs(tok(","))) {
            advance();
            if (!parseNodeQ("node.argumentList", "ArgumentList")) {
                cleanup(m);
                return false;
            }
        }
        expect(tok(")"));
        exit_section_(builder, m, PRAGMA, true);//todo type
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.PrimaryExpression,false);
        if (!moreTokens()) {
            error("Expected primary statement instead of EOF");
            exit_section_(builder, m, PRIMARY_EXPRESSION, true);
            return false;
        }
        if (current().type.equals(tok("."))) {
            advance();
        }
        Token.IdType i = current().type;
        if (i.equals(tok("identifier"))) {
            if (peekIs(tok("=>"))) {
                if (!parseNodeQ("node.functionLiteralExpression", "FunctionLiteralExpression")) {
                    cleanup(m);
                    return false;
                }
            } else if (!parseNodeQ("node.identifierOrTemplateInstance", "IdentifierOrTemplateInstance")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("immutable")) || i.equals(tok("const")) || i.equals(tok("inout")) || i.equals(tok("shared"))) {
            advance();
            expect(tok("("));
            if (!parseNodeQ("node.type", "Type")) {
                cleanup(m);
                return false;
            }
            expect(tok(")"));
            expect(tok("."));
            Token ident = expect(tok("identifier"));
        } else if (i.equals(tok("int")) || i.equals(tok("bool")) || i.equals(tok("byte")) || i.equals(tok("cdouble")) || i.equals(tok("cent")) || i.equals(tok("cfloat")) || i.equals(tok("char")) || i.equals(tok("creal")) || i.equals(tok("dchar")) || i.equals(tok("double")) || i.equals(tok("float")) || i.equals(tok("idouble")) || i.equals(tok("ifloat")) || i.equals(tok("ireal")) || i.equals(tok("long")) || i.equals(tok("real")) || i.equals(tok("short")) || i.equals(tok("ubyte")) || i.equals(tok("ucent")) || i.equals(tok("uint")) || i.equals(tok("ulong")) || i.equals(tok("ushort")) || i.equals(tok("void")) || i.equals(tok("wchar"))) {
            advance();
            if (currentIs(tok("."))) {
                advance();
                Token t = expect(tok("identifier"));
//                        if (t != null)
//                            node.primary = *t;
            } else if (currentIs(tok("(")))
                if (!parseNodeQ("node.arguments", "Arguments")) {
                    cleanup(m);
                    return false;
                }
        } else if (i.equals(tok("function")) || i.equals(tok("delegate")) || i.equals(tok("{")) || i.equals(tok("in")) || i.equals(tok("out")) || i.equals(tok("body"))) {
            if (!parseNodeQ("node.functionLiteralExpression", "FunctionLiteralExpression")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("typeof"))) {
            if (!parseNodeQ("node.typeofExpression", "TypeofExpression")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("typeid"))) {
            if (!parseNodeQ("node.typeidExpression", "TypeidExpression")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("__vector"))) {
            if (!parseNodeQ("node.vector", "Vector")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("["))) {
            if (isAssociativeArrayLiteral()) {
                if (!parseNodeQ("node.assocArrayLiteral", "AssocArrayLiteral")) {
                    cleanup(m);
                    return false;
                }
            } else if (!parseNodeQ("node.arrayLiteral", "ArrayLiteral")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("("))) {
            Bookmark b = setBookmark();
            skipParens();
            while (isAttribute())
                parseAttribute();
            if (currentIsOneOf(tok("=>"), tok("{"))) {
                goToBookmark(b);
                if (!parseNodeQ("node.functionLiteralExpression", "FunctionLiteralExpression")) {
                    cleanup(m);
                    return false;
                }
            } else {
                goToBookmark(b);
                advance();
                if (!parseNodeQ("node.expression", "Expression")) {
                    cleanup(m);
                    return false;
                }
                if (!tokenCheck(")")) {
                    return false;
                }
            }
        } else if (i.equals(tok("is"))) {
            if (!parseNodeQ("node.isExpression", "IsExpression")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("__traits"))) {
            if (!parseNodeQ("node.traitsExpression", "TraitsExpression")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("mixin"))) {
            if (!parseNodeQ("node.mixinExpression", "MixinExpression")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("import"))) {
            if (!parseNodeQ("node.importExpression", "ImportExpression")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("this")) || i.equals(tok("super")) || i.equals(tok("dstringLiteral")) || i.equals(tok("stringLiteral")) || i.equals(tok("wstringLiteral")) || i.equals(tok("characterLiteral")) || i.equals(tok("true")) || i.equals(tok("false")) || i.equals(tok("null")) || i.equals(tok("$")) || i.equals(tok("doubleLiteral")) || i.equals(tok("floatLiteral")) || i.equals(tok("idoubleLiteral")) || i.equals(tok("ifloatLiteral")) || i.equals(tok("intLiteral")) || i.equals(tok("longLiteral")) || i.equals(tok("realLiteral")) || i.equals(tok("irealLiteral")) || i.equals(tok("uintLiteral")) || i.equals(tok("ulongLiteral")) || i.equals(tok("__DATE__")) || i.equals(tok("__TIME__")) || i.equals(tok("__TIMESTAMP__")) || i.equals(tok("__VENDOR__")) || i.equals(tok("__VERSION__")) || i.equals(tok("__FILE__")) || i.equals(tok("__FILE_FULL_PATH__")) || i.equals(tok("__LINE__")) || i.equals(tok("__MODULE__")) || i.equals(tok("__FUNCTION__")) || i.equals(tok("__PRETTY_FUNCTION__"))) {
            if (currentIsOneOf(tok("stringLiteral"), tok("wstringLiteral"), tok("dstringLiteral"))) {
                advance();
                boolean alreadyWarned = false;
                while (currentIsOneOf(tok("stringLiteral"), tok("wstringLiteral"),
                    tok("dstringLiteral"))) {
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
            exit_section_(builder, m, PRIMARY_EXPRESSION, true);
            return false;
        }
        exit_section_(builder, m, PRIMARY_EXPRESSION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Register,false);
        Token ident = expect(tok("identifier"));
        if (ident == null) {
            cleanup(m);
            return false;
        }
//            node.identifier = *ident;
        if (currentIs(tok("("))) {
            advance();
            Token intLit = expect(tok("intLiteral"));
            if (intLit == null) {
                cleanup(m);
                return false;
            }
//                node.intLiteral = *intLit;
            expect(tok(")"));
        }
        exit_section_(builder, m, REGISTER, true);
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
    boolean parseRelExpression(boolean parseShift)//(ExpressionNode shift)
    {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean b = parseLeftAssocBinaryExpression("RelExpression", "ShiftExpression", parseShift, tok("<"), tok("<="), tok(">"), tok(">="), tok("!<>="), tok("!<>"), tok("<>"), tok("<>="), tok("!>"), tok("!>="), tok("!>="), tok("!<"), tok("!<="));
        exit_section_(builder, marker, REL_EXPRESSION, b);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ReturnStatement,false);
        Token start = expect(tok("return"));
        if (start == null) {
            cleanup(m);
            return false;
        }
//            node.startLocation = start.index;
        if (!currentIs(tok(";")))
            if (!parseNodeQ("node.expression", "Expression")) {
                cleanup(m);
                return false;
            }
        Token semicolon = expect(tok(";"));
        if (semicolon == null) {
            cleanup(m);
            return false;
        }
//            node.endLocation = semicolon.index;
        exit_section_(builder, m, RETURN_STATEMENT, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ScopeGuardStatement,false);
        expect(tok("scope"));
        expect(tok("("));
        Token ident = expect(tok("identifier"));
        if (ident == null) {
            cleanup(m);
            return false;
        }
//            node.identifier = *ident;
        expect(tok(")"));
        if (!parseNodeQ("node.statementNoCaseNoDefault", "StatementNoCaseNoDefault")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, SCOPE_GUARD_STATEMENT, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.SharedStaticConstructor,false);
//            node.location = current().index;
        if (!tokenCheck("shared")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("static")) {
            cleanup(m);
            return false;
        }
        final boolean b = parseStaticCtorDtorCommon();
        exit_section_(builder, m, SHARED_STATIC_CONSTRUCTOR, b);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.SharedStaticDestructor,false);
//            node.location = current().index;
        if (!tokenCheck("shared")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("static")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("~")) {
            cleanup(m);
            return false;
        }
        final boolean b = parseStaticCtorDtorCommon();
        exit_section_(builder, m, SHARED_STATIC_DESTRUCTOR, b);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean b = parseLeftAssocBinaryExpression("ShiftExpression", "AddExpression", tok("<<"), tok(">>"), tok(">>>"));
        exit_section_(builder, marker, SHIFT_EXPRESSION_, b);
        return b;
    }

    /**
     * Parses a SingleImport
     * <p>
     * $(GRAMMAR $(RULEDEF singleImport):
     * ($(LITERAL Identifier) $(LITERAL '='))? $(RULE identifierChain)
     * ;)
     */
    boolean parseSingleImport() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.SingleImport,false);
        if (startsWith(tok("identifier"), tok("="))) {
            advance(); // identifier
            advance(); // =
        }
        if (!parseNodeQ("node.identifierChain", "IdentifierChain")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, IMPORT, true);//todo type
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Statement,false);
        if (!moreTokens()) {
            error("Expected statement instead of EOF");
            exit_section_(builder, m, STATEMENT, true);
            return false;
        }
        Token.IdType i = current().type;
        if (i.equals(tok("case"))) {
            advance();
            boolean argumentList = parseArgumentList();
            if (!argumentList) {
                cleanup(m);
                return false;
            }
            if (startsWith(tok(":"), tok("..")))
                parseCaseRangeStatement();
            else
                parseCaseStatement();
        } else if (i.equals(tok("default"))) {
            if (!parseNodeQ("node.defaultStatement", "DefaultStatement")) {
                cleanup(m);
                return false;
            }
        } else {
            if (!parseNodeQ("node.statementNoCaseNoDefault", "StatementNoCaseNoDefault")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, STATEMENT, true);
        return true;
    }

    /**
     * Parses a StatementNoCaseNoDefault
     * <p>
     * $(GRAMMAR $(RULEDEF statementNoCaseNoDefault):
     * $(RULE labeledStatement)
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
     * | $(RULE throwStatement)
     * | $(RULE scopeGuardStatement)
     * | $(RULE asmStatement)
     * | $(RULE conditionalStatement)
     * | $(RULE staticAssertStatement)
     * | $(RULE versionSpecification)
     * | $(RULE debugSpecification)
     * | $(RULE expressionStatement)
     * ;)
     */
    boolean parseStatementNoCaseNoDefault() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StatementNoCaseNoDefault,false);
//        current();
        Token.IdType i = current().type;
        if (i.equals(tok("{"))) {
            if (!parseNodeQ("node.blockStatement", "BlockStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("if"))) {
            if (!parseNodeQ("node.ifStatement", "IfStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("while"))) {
            if (!parseNodeQ("node.whileStatement", "WhileStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("do"))) {
            if (!parseNodeQ("node.doStatement", "DoStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("for"))) {
            if (!parseNodeQ("node.forStatement", "ForStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("foreach")) || i.equals(tok("foreach_reverse"))) {
            if (!parseNodeQ("node.foreachStatement", "ForeachStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("switch"))) {
            if (!parseNodeQ("node.switchStatement", "SwitchStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("continue"))) {
            if (!parseNodeQ("node.continueStatement", "ContinueStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("break"))) {
            if (!parseNodeQ("node.breakStatement", "BreakStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("return"))) {
            if (!parseNodeQ("node.returnStatement", "ReturnStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("goto"))) {
            if (!parseNodeQ("node.gotoStatement", "GotoStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("with"))) {
            if (!parseNodeQ("node.withStatement", "WithStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("synchronized"))) {
            if (!parseNodeQ("node.synchronizedStatement", "SynchronizedStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("try"))) {
            if (!parseNodeQ("node.tryStatement", "TryStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("throw"))) {
            if (!parseNodeQ("node.throwStatement", "ThrowStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("scope"))) {
            if (!parseNodeQ("node.scopeGuardStatement", "ScopeGuardStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("asm"))) {
            if (!parseNodeQ("node.asmStatement", "AsmStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("final"))) {
            if (peekIs(tok("switch"))) {

                if (!parseNodeQ("node.finalSwitchStatement", "FinalSwitchStatement")) {
                    cleanup(m);
                    return false;
                }
                exit_section_(builder, m, STATEMENT_NO_CASE_NO_DEFAULT, true);
                return true;
            } else {
                error("\"switch\" expected");
                exit_section_(builder, m, STATEMENT_NO_CASE_NO_DEFAULT, true);
                return false;
            }
        } else if (i.equals(tok("debug"))) {
            if (peekIs(tok("="))) {
                if (!parseNodeQ("node.debugSpecification", "DebugSpecification")) {
                    cleanup(m);
                    return false;
                }
            } else if (!parseNodeQ("node.conditionalStatement", "ConditionalStatement")) {
                    cleanup(m);
                    return false;
                }
        } else if (i.equals(tok("version"))) {
            if (peekIs(tok("="))) {
                if (!parseNodeQ("node.versionSpecification", "VersionSpecification")) {
                    cleanup(m);
                    return false;
                }
            } else if (!parseNodeQ("node.conditionalStatement", "ConditionalStatement")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("static"))) {
            if (peekIs(tok("if"))) {
                if (!parseNodeQ("node.conditionalStatement", "ConditionalStatement")) {
                    cleanup(m);
                    return false;
                }
            } else if (peekIs(tok("assert"))) {
                if (!parseNodeQ("node.staticAssertStatement", "StaticAssertStatement")) {
                    cleanup(m);
                    return false;
                }
            } else {
                error("'if' or 'assert' expected.");
                exit_section_(builder, m, STATEMENT_NO_CASE_NO_DEFAULT, true);
                return false;
            }
        } else if (i.equals(tok("identifier"))) {
            if (peekIs(tok(":"))) {
                if (!parseNodeQ("node.labeledStatement", "LabeledStatement")) {
                    cleanup(m);
                    return false;
                }
                exit_section_(builder, m, STATEMENT_NO_CASE_NO_DEFAULT, true);
                return true;
            }
            if (!parseNodeQ("node.expressionStatement", "ExpressionStatement")) {
                cleanup(m);
                return false;
            }
        } else {
            if (!parseNodeQ("node.expressionStatement", "ExpressionStatement")) {
                cleanup(m);
                return false;
            }
        }
//            node.endLocation = tokens[index - 1].index;
        exit_section_(builder, m, STATEMENT_NO_CASE_NO_DEFAULT, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean b = simpleParse("StaticAssertDeclaration",
            "staticAssertStatement|parseStaticAssertStatement");
        exit_section_(builder, marker, STATIC_ASSERT, b);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean b = simpleParse("StaticAssertStatement",
            tok("static"), "assertExpression|parseAssertExpression", tok(";"));
        exit_section_(builder, marker, STATIC_ASSERT, b);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StaticConstructor,false);
//            node.location = current().index;
        if (!tokenCheck("static")) {
            cleanup(m);
            return false;
        }
        final boolean b = parseStaticCtorDtorCommon();
        exit_section_(builder, m, STATIC_CONSTRUCTOR, b);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StaticDestructor,false);
//            node.location = current().index;
        if (!tokenCheck("static")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("~")) {
            cleanup(m);
            return false;
        }
        final boolean b = parseStaticCtorDtorCommon();
        exit_section_(builder, m, STATIC_DESTRUCTOR, b);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean b = simpleParse("StaticIfCondition", tok("static"), tok("if"), tok("("),
            "assignExpression|parseAssignExpression", tok(")"));
        exit_section_(builder, marker, STATIC_IF_CONDITION, b);
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
     * | $(LITERAL '___gshared')
     * | $(LITERAL 'scope')
     * | $(LITERAL 'static')
     * | $(LITERAL 'synchronized')
     * ;)
     */
    boolean parseStorageClass() {
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StorageClass,false);
        Token.IdType i = current().type;
        if (i.equals(tok("@"))) {
            if (!parseNodeQ("node.atAttribute", "AtAttribute")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("deprecated"))) {
            if (!parseNodeQ("node.deprecated_", "Deprecated")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("align"))) {
            if (!parseNodeQ("node.alignAttribute", "AlignAttribute")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("extern"))) {
            if (peekIs(tok("("))) {
                if (!parseNodeQ("node.linkageAttribute", "LinkageAttribute")) {
                    cleanup(m);
                    return false;
                }
                exit_section_(builder, m, STORAGE_CLASS, true);
                return true;
            }
            advance();
        } else if (i.equals(tok("const")) || i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("shared")) || i.equals(tok("abstract")) || i.equals(tok("auto")) || i.equals(tok("enum")) || i.equals(tok("final")) || i.equals(tok("const")) || i.equals(tok("override")) || i.equals(tok("const")) || i.equals(tok("ref")) || i.equals(tok("__gshared")) || i.equals(tok("scope")) || i.equals(tok("static")) || i.equals(tok("synchronized"))) {
            advance();
        } else {
            error("Storage class expected");
            exit_section_(builder, m, STORAGE_CLASS, true);
            return false;
        }
        exit_section_(builder, m, STORAGE_CLASS, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StructBody,false);
        Token start = expect(tok("{"));
//            if (start != null) node.startLocation = start.index;
        StackBuffer declarations;
        while (!currentIs(tok("}")) && moreTokens()) {
//                c = allocator.setCheckpoint();
            parseDeclaration(true, true);
//                    allocator.rollback(c);
        }
//            ownArray(node.declarations, declarations);
        Token end = expect(tok("}"));
//            if (end != null) node.endLocation = end.index;
        exit_section_(builder, m, AGGREGATE_BODY, true);//todo type
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StructDeclaration,false);
        Token t = expect(tok("struct"));
        if (currentIs(tok("identifier")))
            advance();
//            else {
//                node.name.line = t.line;
//                node.name.column = t.column;
//            }
//            node.comment = comment;
//            comment = null;

        if (currentIs(tok("("))) {
            if (!parseNodeQ("node.templateParameters", "TemplateParameters")) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok("if")))
                if (!parseNodeQ("node.raint", "Constraint")) {
                    cleanup(m);
                    return false;
                }
            if (!parseNodeQ("node.structBody", "StructBody")) {
                cleanup(m);
                return false;
            }
        } else if (currentIs(tok("{"))) {
            if (!parseNodeQ("node.structBody", "StructBody")) {
                cleanup(m);
                return false;
            }
        } else if (currentIs(tok(";")))
            advance();
        else {
            error("Template Parameters, Struct Body, or Semicolon expected");
            exit_section_(builder, m, STRUCT_DECLARATION, true);
            return false;
        }
        exit_section_(builder, m, STRUCT_DECLARATION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StructInitializer,false);
        Token a = expect(tok("{"));
//            node.startLocation = a.index;
        if (currentIs(tok("}"))) {
//                node.endLocation = current().index;
            advance();
        } else {
            if (!parseNodeQ("node.structMemberInitializers", "StructMemberInitializers")) {
                cleanup(m);
                return false;
            }
            Token e = expect(tok("}"));
            if (e == null) {
                cleanup(m);
                return false;
            }
//                node.endLocation = e.index;
        }
        exit_section_(builder, m, STRUCT_INITIALIZER, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StructMemberInitializer,false);
        if (startsWith(tok("identifier"), tok(":"))) {
            /*node.identifier = *//*tokens[*/
            index++/*]*/;//todo make sure consumption is done correctly
            index++;
            builder.advanceLexer();
            builder.advanceLexer();
        }
        if (!parseNodeQ("node.nonVoidInitializer", "NonVoidInitializer")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, STRUCT_MEMBER_INITIALIZER, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StructMemberInitializers,false);
        do {
//                auto c = allocator.setCheckpoint();
            parseStructMemberInitializer();
//                    allocator.rollback(c);
            if (currentIs(tok(",")))
                advance();
            else
                break;
        } while (moreTokens() && !currentIs(tok("}")));
//            ownArray(node.structMemberInitializers, structMemberInitializers);
        exit_section_(builder, m, STRUCT_MEMBER_INITIALIZERS, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.SwitchStatement,false);
        expect(tok("switch"));
        expect(tok("("));
        if (!parseNodeQ("node.expression", "Expression")) {
            cleanup(m);
            return false;
        }
        expect(tok(")"));
        if (!parseNodeQ("node.statement", "Statement")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, SWITCH_STATEMENT, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Symbol,false);
        if (currentIs(tok("."))) {
//                node.dot = true;
            advance();
        }
        if (!parseNodeQ("node.identifierOrTemplateChain", "IdentifierOrTemplateChain")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, SYMBOL, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.SynchronizedStatement,false);
        expect(tok("synchronized"));
        if (currentIs(tok("("))) {
            expect(tok("("));
            if (!parseNodeQ("node.expression", "Expression")) {
                cleanup(m);
                return false;
            }
            expect(tok(")"));
        }
        if (!parseNodeQ("node.statementNoCaseNoDefault", "StatementNoCaseNoDefault")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, SYNCHRONIZED_STATEMENT, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateAliasParameter,false);
        expect(tok("alias"));
        if (currentIs(tok("identifier")) && !peekIs(tok("."))) {
            if (peekIsOneOf(tok(","), tok(")"), tok("="), tok(":")))
                advance();
            else {
                if (!parseNodeQ("node.type", "Type")) {
                    cleanup(m);
                    return false;
                }
                Token ident = expect(tok("identifier"));
                if (ident == null) {
                    cleanup(m);
                    return false;
                }
            }
        } else {
            if (!parseNodeQ("node.type", "Type")) {
                cleanup(m);
                return false;
            }
            Token ident = expect(tok("identifier"));
            if (ident == null) {
                cleanup(m);
                return false;
            }
//                node.identifier = *ident;
        }

        if (currentIs(tok(":"))) {
            advance();
            if (isType()) {
                if (!parseNodeQ("node.colonType", "Type")) {
                    cleanup(m);
                    return false;
                }
            } else if (!parseNodeQ("node.colonExpression", "AssignExpression")) {
                cleanup(m);
                return false;
            }
        }
        if (currentIs(tok("="))) {
            advance();
            if (isType()) {
                if (!parseNodeQ("node.assignType", "Type")) {
                    cleanup(m);
                    return false;
                }
            } else if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, TEMPLATE_ALIAS_PARAMETER, true);
        return true;
    }

    /**
     * Parses a TemplateArgument
     * <p>
     * $(GRAMMAR $(RULEDEF templateArgument):
     * $(RULE type)
     * | $(RULE assignExpression)
     * ;)
     */
    boolean parseTemplateArgument() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        if (suppressedErrorCount > MAX_ERRORS) return false;
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateArgument,false);
        Bookmark b = setBookmark();
        boolean t = parseType();
        if (t && currentIsOneOf(tok(","), tok(")"))) {
            abandonBookmark(b);
//                node.type = t;
        } else {
            goToBookmark(b);
            if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, TEMPLATE_ARGUMENT, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean b = parseCommaSeparatedRule("TemplateArgumentList", "TemplateArgument");
        exit_section_(builder, marker, TEMPLATE_ARGUMENT_LIST, b);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        if (suppressedErrorCount > MAX_ERRORS) return false;
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateArguments,false);
        expect(tok("!"));
        if (currentIs(tok("("))) {
            advance();
            if (!currentIs(tok(")")))
                if (!parseNodeQ("node.templateArgumentList", "TemplateArgumentList")) {
                    cleanup(m);
                    return false;
                }
            if (!tokenCheck(")")) {
                cleanup(m);
                return false;
            }
        } else if (!parseNodeQ("node.templateSingleArgument", "TemplateSingleArgument")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, TEMPLATE_ARGUMENTS, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateDeclaration,false);
//            node.comment = comment;
//            comment = null;
        expect(tok("template"));
        Token ident = expect(tok("identifier"));
        if (ident == null) {
            cleanup(m);
            return false;
        }
//            node.name = *ident;
        if (!parseNodeQ("node.templateParameters", "TemplateParameters")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("if")))
            if (!parseNodeQ("node.constraint", "Constraint")) {
                cleanup(m);
                return false;
            }
        Token start = expect(tok("{"));
        if (start == null) {
            cleanup(m);
            return false;
        }
//            node.startLocation = start.index;
//            StackBuffer declarations;
        while (moreTokens() && !currentIs(tok("}"))) {
//                c = allocator.setCheckpoint();
            parseDeclaration(true, true);
//                    allocator.rollback(c);
        }
//            ownArray(node.declarations, declarations);
        Token end = expect(tok("}"));
//            if (end != null) node.endLocation = end.index;
        exit_section_(builder, m, TEMPLATE_DECLARATION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        if (suppressedErrorCount > MAX_ERRORS) return false;
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateInstance,false);
        Token ident = expect(tok("identifier"));
        if (ident == null) {
            cleanup(m);
            return false;
        }
//            node.identifier = *ident;
        if (!parseNodeQ("node.templateArguments", "TemplateArguments")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, TEMPLATE_INSTANCE, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateMixinExpression,false);
        if (!tokenCheck("mixin")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.mixinTemplateName", "MixinTemplateName")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("!")))
            if (!parseNodeQ("node.templateArguments", "TemplateArguments")) {
                cleanup(m);
                return false;
            }
        if (currentIs(tok("identifier")))
            advance();
        exit_section_(builder, m, MIXIN_EXPRESSION, true);//todo type
        return true;
    }

    /**
     * Parses a TemplateParameter
     * <p>
     * $(GRAMMAR $(RULEDEF templateParameter):
     * $(RULE templateTypeParameter)
     * | $(RULE templateValueParameter)
     * | $(RULE templateAliasParameter)
     * | $(RULE templateTupleParameter)
     * | $(RULE templateThisParameter)
     * ;)
     */
    boolean parseTemplateParameter() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateParameter,false);
        Token.IdType i = current().type;
        if (i.equals(tok("alias"))) {
            if (!parseNodeQ("node.templateAliasParameter", "TemplateAliasParameter")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("identifier"))) {
            if (peekIs(tok("..."))) {
                if (!parseNodeQ("node.templateTupleParameter", "TemplateTupleParameter")) {
                    cleanup(m);
                    return false;
                }
            } else if (peekIsOneOf(tok(":"), tok("="), tok(","), tok(")"))) {
                if (!parseNodeQ("node.templateTypeParameter", "TemplateTypeParameter")) {
                    cleanup(m);
                    return false;
                }
            } else if (!parseNodeQ("node.templateValueParameter", "TemplateValueParameter")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("this"))) {
            if (!parseNodeQ("node.templateThisParameter", "TemplateThisParameter")) {
                cleanup(m);
                return false;
            }
        } else {
            if (!parseNodeQ("node.templateValueParameter", "TemplateValueParameter")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, TEMPLATE_PARAMETER, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean b = parseCommaSeparatedRule("TemplateParameterList", "TemplateParameter");
        exit_section_(builder, marker, TEMPLATE_PARAMETER_LIST, b);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateParameters,false);
        if (!tokenCheck("(")) {
            cleanup(m);
            return false;
        }
        if (!currentIs(tok(")")))
            if (!parseNodeQ("node.templateParameterList", "TemplateParameterList")) {
                cleanup(m);
                return false;
            }
        if (!tokenCheck(")")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, TEMPLATE_PARAMETERS, true);
        return true;
    }

    /**
     * Parses a TemplateSingleArgument
     * <p>
     * $(GRAMMAR $(RULEDEF templateSingleArgument):
     * $(RULE builtinType)
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
     * | $(LITERAL '___TIME__')
     * | $(LITERAL '___TIMESTAMP__')
     * | $(LITERAL '___VENDOR__')
     * | $(LITERAL '___VERSION__')
     * | $(LITERAL '___FILE__')
     * | $(LITERAL '___LINE__')
     * | $(LITERAL '___MODULE__')
     * | $(LITERAL '___FUNCTION__')
     * | $(LITERAL '___PRETTY_FUNCTION__')
     * ;)
     */
    boolean parseTemplateSingleArgument() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateSingleArgument,false);
        if (!moreTokens()) {
            error("template argument expected instead of EOF");
            exit_section_(builder, m, TEMPLATE_SINGLE_ARGUMENT, true);
            return false;
        }
        Token.IdType i = current().type;
        if (i.equals(tok("this")) || i.equals(tok("identifier")) || i.equals(tok("characterLiteral")) || i.equals(tok("true")) || i.equals(tok("false")) || i.equals(tok("null")) || i.equals(tok("$")) || i.equals(tok("dstringLiteral")) || i.equals(tok("stringLiteral")) || i.equals(tok("wstringLiteral")) || i.equals(tok("doubleLiteral")) || i.equals(tok("floatLiteral")) || i.equals(tok("idoubleLiteral")) || i.equals(tok("ifloatLiteral")) || i.equals(tok("intLiteral")) || i.equals(tok("longLiteral")) || i.equals(tok("realLiteral")) || i.equals(tok("irealLiteral")) || i.equals(tok("uintLiteral")) || i.equals(tok("ulongLiteral")) || i.equals(tok("__DATE__")) || i.equals(tok("__TIME__")) || i.equals(tok("__TIMESTAMP__")) || i.equals(tok("__VENDOR__")) || i.equals(tok("__VERSION__")) || i.equals(tok("__FILE__")) || i.equals(tok("__FILE_FULL_PATH__")) || i.equals(tok("__LINE__")) || i.equals(tok("__MODULE__")) || i.equals(tok("__FUNCTION__")) || i.equals(tok("__PRETTY_FUNCTION__")) || i.equals(tok("int")) || i.equals(tok("bool")) || i.equals(tok("byte")) || i.equals(tok("cdouble")) || i.equals(tok("cent")) || i.equals(tok("cfloat")) || i.equals(tok("char")) || i.equals(tok("creal")) || i.equals(tok("dchar")) || i.equals(tok("double")) || i.equals(tok("float")) || i.equals(tok("idouble")) || i.equals(tok("ifloat")) || i.equals(tok("ireal")) || i.equals(tok("long")) || i.equals(tok("real")) || i.equals(tok("short")) || i.equals(tok("ubyte")) || i.equals(tok("ucent")) || i.equals(tok("uint")) || i.equals(tok("ulong")) || i.equals(tok("ushort")) || i.equals(tok("void")) || i.equals(tok("wchar"))) {
            advance();
        } else {
            error("Invalid template argument. (Try enclosing in parenthesis?)");
            exit_section_(builder, m, TEMPLATE_SINGLE_ARGUMENT, true);
            return false;
        }
        exit_section_(builder, m, TEMPLATE_SINGLE_ARGUMENT, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateThisParameter,false);
        expect(tok("this"));
        if (!parseNodeQ("node.templateTypeParameter", "TemplateTypeParameter")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, TEMPLATE_THIS_PARAMETER, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateTupleParameter,false);
        Token i = expect(tok("identifier"));
        if (i == null) {
            cleanup(m);
            return false;
        }
//            node.identifier = *i;
        if (!tokenCheck("...")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, TEMPLATE_TUPLE_PARAMETER, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateTypeParameter,false);
        Token ident = expect(tok("identifier"));
        if (ident == null) {
            cleanup(m);
            return false;
        }
//            node.identifier = *ident;
        if (currentIs(tok(":"))) {
            advance();
            if (!parseNodeQ("node.colonType", "Type")) {
                cleanup(m);
                return false;
            }
        }
        if (currentIs(tok("="))) {
            advance();
            if (!parseNodeQ("node.assignType", "Type")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, TEMPLATE_TYPE_PARAMETER, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateValueParameter,false);
        if (!parseNodeQ("node.type", "Type")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("node.identifier", "identifier")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok(":"))) {
            advance();
            if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                cleanup(m);
                return false;
            }
        }
        if (currentIs(tok("=")))
            if (!parseNodeQ("node.templateValueParameterDefault", "TemplateValueParameterDefault")) {
                cleanup(m);
                return false;
            }
        exit_section_(builder, m, TEMPLATE_VALUE_PARAMETER, true);
        return true;
    }

    /**
     * Parses a TemplateValueParameterDefault
     * <p>
     * $(GRAMMAR $(RULEDEF templateValueParameterDefault):
     * $(LITERAL '=') ($(LITERAL '___FILE__') | $(LITERAL '___MODULE__') | $(LITERAL '___LINE__') | $(LITERAL '___FUNCTION__') | $(LITERAL '___PRETTY_FUNCTION__') | $(RULE assignExpression))
     * ;)
     */
    boolean parseTemplateValueParameterDefault() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateValueParameterDefault,false);
        expect(tok("="));
        Token.IdType i = current().type;
        if (i.equals(tok("__FILE__")) || i.equals(tok("__MODULE__")) || i.equals(tok("__LINE__")) || i.equals(tok("__FUNCTION__")) || i.equals(tok("__PRETTY_FUNCTION__"))) {
            advance();

        } else {
            if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                cleanup(m);
                return false;
            }

        }
        exit_section_(builder, m, TEMPLATE_VALUE_PARAMETER, true);
        return true;
    }

    /**
     * Parses a TernaryExpression
     * <p>
     * $(GRAMMAR $(RULEDEF ternaryExpression):
     * $(RULE orOrExpression) ($(LITERAL '?') $(RULE expression) $(LITERAL ':') $(RULE ternaryExpression))?
     * ;)
     */
    boolean parseTernaryExpression() {
//            mixin(traceEnterAndExit!(__FUNCTION__));

        boolean orOrExpression = parseOrOrExpression();
        if (!orOrExpression) {
            return false;//no cleanup needed
        }
        if (currentIs(tok("?"))) {
//                TernaryExpression node = allocator.make!TernaryExpression;
//                node.orOrExpression = orOrExpression;
            final Marker m = enter_section_(builder);
            advance();
            if (!parseNodeQ("node.expression", "Expression")) {
                cleanup(m);
                return false;
            }
            Token colon = expect(tok(":"));
            if (colon == null) {
                cleanup(m);
                return false;
            }
//                node.colon = *colon;
            if (!parseNodeQ("node.ternaryExpression", "TernaryExpression")) {
                cleanup(m);
                return false;
            }
            exit_section_(builder, m, CONDITIONAL_EXPRESSION_, true);
            return true;
        }
//            exit_section_(builder,m,,true);
        return orOrExpression;
    }

    /**
     * Parses a ThrowStatement
     * <p>
     * $(GRAMMAR $(RULEDEF throwStatement):
     * $(LITERAL 'throw') $(RULE expression) $(LITERAL ';')
     * ;)
     */
    boolean parseThrowStatement() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ThrowStatement,false);
        expect(tok("throw"));
        if (!parseNodeQ("node.expression", "Expression")) {
            cleanup(m);
            return false;
        }
        expect(tok(";"));
        exit_section_(builder, m, THROW_STATEMENT, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TraitsExpression,false);
        if (!tokenCheck("__traits")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("(")) {
            cleanup(m);
            return false;
        }
        Token ident = expect(tok("identifier"));
        if (ident == null) {
            cleanup(m);
            return false;
        }
//            node.identifier = *ident;
        if (currentIs(tok(","))) {
            advance();
            if (!(parseTemplateArgumentList())) {
                cleanup(m);
                return false;
            }
        }
        if (!tokenCheck(")")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, TRAITS_EXPRESSION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TryStatement,false);
        expect(tok("try"));
        if (!parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement")) {
            cleanup(m);
            return false;
        }
        if (currentIs(tok("catch")))
            if (!parseNodeQ("node.catches", "Catches")) {
                cleanup(m);
                return false;
            }
        if (currentIs(tok("finally")))
            if (!parseNodeQ("node.finally_", "Finally")) {
                cleanup(m);
                return false;
            }
        exit_section_(builder, m, TRY_STATEMENT, true);
        return true;
    }

    /**
     * Parses a Type
     * <p>
     * $(GRAMMAR $(RULEDEF type):
     * $(RULE typeConstructors)? $(RULE type2) $(RULE typeSuffix)*
     * ;)
     */
    boolean parseType() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Type,false);
        if (!moreTokens()) {
            error("type expected");
            exit_section_(builder, m, TYPE, true);
            return false;
        }
        Token.IdType i = current().type;
        if (i.equals(tok("const")) || i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("shared"))) {
            if (!peekIs(tok("(")))
                if (!parseNodeQ("node.typeConstructors", "TypeConstructors")) {
                    cleanup(m);
                    return false;
                }
        }
        if (!parseNodeQ("node.type2", "Type2")) {
            cleanup(m);
            return false;
        }
//            StackBuffer typeSuffixes;
        while (moreTokens()) {
            Token.IdType i1 = current().type;
            if (i1.equals(tok("["))) {
                Bookmark newBookmark = setBookmark();
                if (parseTypeSuffix())
                    abandonBookmark(newBookmark);
                else {
//                        allocator.rollback(c);
                    goToBookmark(newBookmark);
                    break;
                }
            } else if (i1.equals(tok("*")) || i1.equals(tok("delegate")) || i1.equals(tok("function"))) {
                if (!parseTypeSuffix()) {
                    return false;
                }
            } else {
                break;
            }
        }
//            ownArray(node.typeSuffixes, typeSuffixes);
        exit_section_(builder, m, TYPE, true);
        return true;
    }

    /**
     * Parses a Type2
     * <p>
     * $(GRAMMAR $(RULEDEF type2):
     * $(RULE builtinType)
     * | $(RULE symbol)
     * | $(LITERAL 'super') $(LITERAL '.') $(RULE identifierOrTemplateChain)
     * | $(LITERAL 'this') $(LITERAL '.') $(RULE identifierOrTemplateChain)
     * | $(RULE typeofExpression) ($(LITERAL '.') $(RULE identifierOrTemplateChain))?
     * | $(RULE typeConstructor) $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL '$(RPAREN)')
     * | $(RULE vector)
     * ;)
     */
    boolean parseType2() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Type2,false);
        if (!moreTokens()) {
            error("type2 expected instead of EOF");
            exit_section_(builder, m, TYPE, true);
            return false;
        }
        Token.IdType i = current().type;
        if (i.equals(tok("identifier")) || i.equals(tok("."))) {
            if (!parseNodeQ("node.symbol", "Symbol")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("int")) || i.equals(tok("bool")) || i.equals(tok("byte")) || i.equals(tok("cdouble")) || i.equals(tok("cent")) || i.equals(tok("cfloat")) || i.equals(tok("char")) || i.equals(tok("creal")) || i.equals(tok("dchar")) || i.equals(tok("double")) || i.equals(tok("float")) || i.equals(tok("idouble")) || i.equals(tok("ifloat")) || i.equals(tok("ireal")) || i.equals(tok("long")) || i.equals(tok("real")) || i.equals(tok("short")) || i.equals(tok("ubyte")) || i.equals(tok("ucent")) || i.equals(tok("uint")) || i.equals(tok("ulong")) || i.equals(tok("ushort")) || i.equals(tok("void")) || i.equals(tok("wchar"))) {
            parseBuiltinType();
        } else if (i.equals(tok("super")) || i.equals(tok("this"))) {
            advance();
            if (!tokenCheck(".")) {
                cleanup(m);
                return false;
            }
            if (!parseNodeQ("node.identifierOrTemplateChain", "IdentifierOrTemplateChain")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("typeof"))) {
            if (!parseTypeofExpression()) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok("."))) {
                advance();
                if (!parseNodeQ("node.identifierOrTemplateChain", "IdentifierOrTemplateChain")) {
                    cleanup(m);
                    return false;
                }
            }
        } else if (i.equals(tok("const")) || i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("shared"))) {
            advance();
            if (!tokenCheck("(")) {
                cleanup(m);
                return false;
            }
            if (!(parseType())) {
                cleanup(m);
                return false;
            }
            if (!tokenCheck(")")) {
                cleanup(m);
                return false;
            }
        } else if (i.equals(tok("__vector"))) {
            if (!(parseVector())) {
                cleanup(m);
                return false;
            }
        } else {
            error("Basic type, type constructor, symbol, or typeof expected");
            exit_section_(builder, m, TYPE, true);
            return false;
        }
        exit_section_(builder, m, TYPE, true);//todo element type
        return true;
    }

    /**
     * Parses a TypeConstructor
     * <p>
     * $(GRAMMAR $(RULEDEF typeConstructor):
     * $(LITERAL '')
     * | $(LITERAL 'immutable')
     * | $(LITERAL 'inout')
     * | $(LITERAL 'shared')
     * ;)
     */
    Token.IdType parseTypeConstructor(boolean validate) {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Token.IdType i = current().type;
        if (i.equals(tok("const")) || i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("shared"))) {
            if (!peekIs(tok("(")))
                return advance().type;
            if (validate) {
                error("\"\", \"immutable\", \"inout\", or \"shared\" expected");
            }
            return tok("");
        } else {
            if (validate) {
                error("\"\", \"immutable\", \"inout\", or \"shared\" expected");
            }
            return tok("");
        }
    }

    /**
     * Parses TypeConstructors
     * <p>
     * $(GRAMMAR $(RULEDEF typeConstructors):
     * $(RULE typeConstructor)+
     * ;)
     */
    Token.IdType[] parseTypeConstructors() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        List<Token.IdType> r = new LinkedList<>();
        while (moreTokens()) {
            Token.IdType type = parseTypeConstructor(false);
            if (type.equals(tok("")))
                break;
            else
                r.add(type);
        }
        return (Token.IdType[]) r.toArray();
    }

    /**
     * Parses a TypeSpecialization
     * <p>
     * $(GRAMMAR $(RULEDEF typeSpecialization):
     * $(RULE type)
     * | $(LITERAL 'struct')
     * | $(LITERAL 'union')
     * | $(LITERAL 'class')
     * | $(LITERAL 'interface')
     * | $(LITERAL 'enum')
     * | $(LITERAL 'function')
     * | $(LITERAL 'delegate')
     * | $(LITERAL 'super')
     * | $(LITERAL '')
     * | $(LITERAL 'immutable')
     * | $(LITERAL 'inout')
     * | $(LITERAL 'shared')
     * | $(LITERAL 'return')
     * | $(LITERAL 'typedef')
     * | $(LITERAL '___parameters')
     * ;)
     */
    boolean parseTypeSpecialization() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TypeSpecialization,false);
        Token.IdType i = current().type;
        if (i.equals(tok("struct")) || i.equals(tok("union")) || i.equals(tok("class")) || i.equals(tok("interface")) || i.equals(tok("enum")) || i.equals(tok("function")) || i.equals(tok("delegate")) || i.equals(tok("super")) || i.equals(tok("return")) || i.equals(tok("typedef")) || i.equals(tok("__parameters")) || i.equals(tok("const")) || i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("shared"))) {
            if (peekIsOneOf(tok(")"), tok(","))) {
                advance();
                exit_section_(builder, m, TYPE_SPECIALIZATION, true);
                return true;
            }
            if (!parseNodeQ("node.type", "Type")) {
                cleanup(m);
                return false;
            }
        }
        if (!parseNodeQ("node.type", "Type")) {
            cleanup(m);
            return false;
        }

        exit_section_(builder, m, TYPE_SPECIALIZATION, true);
        return true;
    }

    /**
     * Parses a TypeSuffix
     * <p>
     * $(GRAMMAR $(RULEDEF typeSuffix):
     * $(LITERAL '*')
     * | $(LITERAL '[') $(RULE type)? $(LITERAL ']')
     * | $(LITERAL '[') $(RULE assignExpression) $(LITERAL ']')
     * | $(LITERAL '[') $(RULE assignExpression) $(LITERAL '..')  $(RULE assignExpression) $(LITERAL ']')
     * | ($(LITERAL 'delegate') | $(LITERAL 'function')) $(RULE parameters) $(RULE memberFunctionAttribute)*
     * ;)
     */
    boolean parseTypeSuffix() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TypeSuffix,false);
        Token.IdType i = current().type;
        if (i.equals(tok("*"))) {
            advance();
            exit_section_(builder, m, TYPE_CTOR, true);//todo type
            return true;
        } else if (i.equals(tok("["))) {
            advance();
            if (currentIs(tok("]"))) {
                advance();
                exit_section_(builder, m, TYPE_CTOR, true);//todo type
                return true;
            }
            Bookmark bookmark = setBookmark();
            boolean type = parseType();
            if (type && currentIs(tok("]"))) {
                abandonBookmark(bookmark);
//                        node.type = type;
            } else {
                goToBookmark(bookmark);
                if (!parseNodeQ("node.low", "AssignExpression")) {
                    cleanup(m);
                    return false;
                }
//                        if (!node.low) {
//                            cleanup(m);
//                            return false;
//                        }
                if (currentIs(tok(".."))) {
                    advance();
                    if (!parseNodeQ("node.high", "AssignExpression")) {
                        cleanup(m);
                        return false;
                    }
//                            if (!node.high) {
//                                cleanup(m/);
//                                return false;
//                            }
                }
            }
            if (!tokenCheck("]")) {
                return false;
            }
            exit_section_(builder, m, TYPE_CTOR, true);
            return true;
        } else if (i.equals(tok("delegate")) || i.equals(tok("function"))) {
            advance();
            if (!parseNodeQ("node.parameters", "Parameters")) {
                cleanup(m);
                return false;
            }
            while (currentIsMemberFunctionAttribute())
                if (!parseMemberFunctionAttribute()) {
                    cleanup(m);
                    return false;
                }
            exit_section_(builder, m, TYPE_CTOR, true);
            return true;
        } else {
            error("\"*\", \"[\", \"delegate\", or \"function\" expected.");
            exit_section_(builder, m, TYPE_CTOR, true);//TODO TYPE
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TypeidExpression,false);
        expect(tok("typeid"));
        expect(tok("("));
        Bookmark b = setBookmark();
        boolean t = parseType();
        if (!t || !currentIs(tok(")"))) {
            goToBookmark(b);
            if (!parseNodeQ("node.expression", "Expression")) {
                cleanup(m);
                return false;
            }
//                if(!node.expression) {
//                    cleanup(m/*todo*/);
//                    return false;
//                }
        } else {
            abandonBookmark(b);
//                node.type = t;
        }
        expect(tok(")"));
        exit_section_(builder, m, TYPEID_EXPRESSION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TypeofExpression,false);
        expect(tok("typeof"));
        expect(tok("("));
        if (currentIs(tok("return")))
            advance();
        else if (!parseNodeQ("node.expression", "Expression")) {
            cleanup(m);
            return false;
        }
        expect(tok(")"));
        exit_section_(builder, m, TYPEOF, true);
        return true;
    }

    private boolean unaryExpressionSwitchDefault(Marker m) {
        if (!parseNodeQ("node.primaryExpression", "PrimaryExpression")) {
            cleanup(m);
            return false;
        }
        return true;
    }

//        /**
//         * Function that == called when a warning or error == encountered.
//         * The parameters are the file name, line number, column number,
//         * and the error or warning message.
//         */
//        void function(String, Number, Number, String, boolean) messageFunction;

    /**
     * Parses a UnaryExpression
     * <p>
     * $(GRAMMAR $(RULEDEF unaryExpression):
     * $(RULE primaryExpression)
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        if (!moreTokens())
            return false;
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.UnaryExpression,false);
        boolean fallThrough = false;
        Token.IdType i = current().type;
        if (i.equals(tok("const")) || i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("shared"))) {
            Bookmark b = setBookmark();
            if (peekIs(tok("("))) {
                fallThrough = true;
                advance();
                Token past = peekPastParens();
                if (past != null && past.type.equals(tok("."))) {
                    goToBookmark(b);
                    if (!unaryExpressionSwitchDefault(m)) {
                        return false;//no cleanup needed
                    }
                    fallThrough = false;
                }
            }
            goToBookmark(b);
            if (fallThrough) {
                if (i.equals(tok("scope")) || i.equals(tok("const")) || i.equals(tok("const"))) {
                    if (!parseNodeQ("node.functionCallExpression", "FunctionCallExpression")) {
                        cleanup(m);
                        return false;
                    }
                }
            }
        } else {
            if (i.equals(tok("scope")) || i.equals(tok("const")) || i.equals(tok("const"))) {
                if (!parseNodeQ("node.functionCallExpression", "FunctionCallExpression")) {
                    cleanup(m);
                    return false;
                }
            } else if (i.equals(tok("&")) || i.equals(tok("!")) || i.equals(tok("*")) || i.equals(tok("+")) || i.equals(tok("-")) || i.equals(tok("~")) || i.equals(tok("++")) || i.equals(tok("--"))) {
                advance();
                if (!parseNodeQ("node.unaryExpression", "UnaryExpression")) {
                    cleanup(m);
                    return false;
                }
            } else if (i.equals(tok("new"))) {
                if (!parseNodeQ("node.newExpression", "NewExpression")) {
                    cleanup(m);
                    return false;
                }
            } else if (i.equals(tok("delete"))) {
                if (!parseNodeQ("node.deleteExpression", "DeleteExpression")) {
                    cleanup(m);
                    return false;
                }
            } else if (i.equals(tok("cast"))) {
                if (!parseNodeQ("node.castExpression", "CastExpression")) {
                    cleanup(m);
                    return false;
                }
            } else if (i.equals(tok("assert"))) {
                if (!parseNodeQ("node.assertExpression", "AssertExpression")) {
                    cleanup(m);
                    return false;
                }
            } else if (i.equals(tok("("))) {
                Bookmark b = setBookmark();
                skipParens();
                if (startsWith(tok("."), tok("identifier"))) {
                    // go back to the (
                    goToBookmark(b);
                    Bookmark b2 = setBookmark();
                    advance();
                    boolean t = parseType();
                    if (!t || !currentIs(tok(")"))) {
                        goToBookmark(b);
                        if (!unaryExpressionSwitchDefault(m)) {
                            return false;//no cleanup needed
                        }
                    } else {
                        abandonBookmark(b2);
//                        node.type = t;
                        advance(); // )
                        advance(); // .
                        if (!parseNodeQ("node.identifierOrTemplateInstance", "IdentifierOrTemplateInstance")) {
                            cleanup(m);
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
        }

        loop:
        while (moreTokens()) {
            Token.IdType i1 = current().type;
            if (i1.equals(tok("!"))) {
                if (peekIs(tok("("))) {
                    final Bookmark b = setBookmark();
                    Token p = peekPastParens();
                    boolean jump = (currentIs(tok("(")) && p != null && p.type.equals(tok("("))) || peekIs(tok("("));
                    goToBookmark(b);
                    if (jump) {
                        final Marker newUnary = enter_section_(builder);
                        if (!parseFunctionCallExpression()) {
                            cleanup(m, newUnary);
                            return false;
                        }
                        exit_section_(builder, newUnary, DLanguageTypes.UNARY_EXPRESSION, true);
                    } else
                        break loop;
                } else
                    break loop;
                final Marker newUnary = enter_section_(builder);
                if (!parseFunctionCallExpression()) {
                    cleanup(m, newUnary);
                    return false;
                }
                exit_section_(builder, newUnary, DLanguageTypes.UNARY_EXPRESSION, true);
            } else if (i1.equals(tok("("))) {
                final Marker newUnary = enter_section_(builder);
                if (!parseFunctionCallExpression()) {
                    cleanup(m, newUnary);
                    return false;
                }
                exit_section_(builder, newUnary, DLanguageTypes.UNARY_EXPRESSION, true);
            } else if (i1.equals(tok("++")) || i1.equals(tok("--"))) {
                advance();
            } else if (i1.equals(tok("["))) {
                parseIndexExpression();
            } else if (i1.equals(tok("."))) {
                advance();
                if (currentIs(tok("new"))) {
                    if (!parseNodeQ("node.newExpression", "NewExpression")) {
                        cleanup(m);
                        return false;
                    }
                } else
                    parseIdentifierOrTemplateInstance();
            } else {
                break loop;
            }
        }
        exit_section_(builder, m, UNARY_EXPRESSION, true);
        return true;
    }

    /**
     * Parses an UnionDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF unionDeclaration):
     * $(LITERAL 'union') $(LITERAL Identifier) $(RULE templateParameters) $(RULE raint)? $(RULE structBody)
     * | $(LITERAL 'union') $(LITERAL Identifier) ($(RULE structBody) | $(LITERAL ';'))
     * | $(LITERAL 'union') $(RULE structBody)
     * ;)
     */
    boolean parseUnionDeclaration() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.UnionDeclaration,false);
//            node.comment = comment;
//            comment = null;
        // grab line number even if it's anonymous
        Token t = expect(tok("union"));
        if (currentIs(tok("identifier"))) {
            advance();
            if (currentIs(tok("("))) {
                if (!parseNodeQ("node.templateParameters", "TemplateParameters")) {
                    cleanup(m);
                    return false;
                }
                if (currentIs(tok("if")))
                    if (!parseNodeQ("node.raint", "Constraint")) {
                        cleanup(m);
                        return false;
                    }
                if (!parseNodeQ("node.structBody", "StructBody")) {
                    cleanup(m);
                    return false;
                }
            } else if (currentIs(tok(";")))
                advance();
            else if (!parseNodeQ("node.structBody", "StructBody")) {
                cleanup(m);
                return false;
            }
        } else {
//                node.name.line = t.line;
//                node.name.column = t.column;
//                semiOrStructBody:
            if (currentIs(tok(";")))
                advance();
            else if (!parseNodeQ("node.structBody", "StructBody")) {
                cleanup(m);
                return false;
            }
        }
        exit_section_(builder, m, UNION_DECLARATION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean b = simpleParse("Unittest", tok("unittest"), "blockStatement|parseBlockStatement");
        exit_section_(builder, marker, UNIT_TESTING, b);
        return b;
    }

    boolean parseVariableDeclaration() {
        return parseVariableDeclaration(true, false);
    }

    /**
     * Parses a VariableDeclaration
     * <p>
     * $(GRAMMAR $(RULEDEF variableDeclaration):
     * $(RULE storageClass)* $(RULE type) $(RULE declarator) ($(LITERAL ',') $(RULE declarator))* $(LITERAL ';')
     * | $(RULE storageClass)* $(RULE type) $(LITERAL identifier) $(LITERAL '=') $(RULE functionBody) $(LITERAL ';')
     * | $(RULE autoDeclaration)
     * ;)
     */
    boolean parseVariableDeclaration(boolean parseType, boolean isAuto)//(Type type = null )
    {
//            mixin (traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.VariableDeclaration,false);

        if (isAuto) {
            if (!parseNodeQ("node.autoDeclaration", "AutoDeclaration")) {
                cleanup(m);
                return false;
            }
//                node.comment = node.autoDeclaration.comment;
            exit_section_(builder, m, VAR_DECLARATIONS, true);//todo type
            return true;
        }

        StackBuffer storageClasses;
        while (isStorageClass())
            if (!parseStorageClass()) {
                cleanup(m);
                return false;
            }
//            ownArray(node.storageClasses, storageClasses);

        if (parseType) {
            parseType();
        }
//            node.comment = comment;
//            comment = null;

        // TODO: handle function bodies correctly

        while (true) {
            boolean declarator = parseDeclarator();
            if (!declarator) {
                cleanup(m);
                return false;
            } else {
//                    last = declarator;
            }
            if (moreTokens() && currentIs(tok(","))) {
//                    if (node.comment != null)
//                    declarator.comment = node.comment ~ "\n" ~ current().trailingComment;
//                else
//                    declarator.comment = current().trailingComment;
                advance();
            } else
                break;
        }
//            ownArray(node.declarators, declarators);
        Token semicolon = expect(tok(";"));
        if (semicolon == null) {
            cleanup(m);
            return false;
        }
//            if (node.comment != null)
//            {
//                if (semicolon.trailingComment == null)
//                last.comment = node.comment;
//            else
//                last.comment = node.comment ~ "\n" ~ semicolon.trailingComment;
//            }
//        else
//            last.comment = semicolon.trailingComment;
        exit_section_(builder, m, VAR_DECLARATIONS, true);//todo type
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean b = simpleParse("Vector", tok("__vector"), tok("("), "type|parseType", tok(")"));
        exit_section_(builder, marker, TYPE_VECTOR, b);
        return b;
    }

//        boolean hasMagicDelimiter(alias L, alias T)
//        {
////            mixin(traceEnterAndExit!(__FUNCTION__));
//            i = index;
//            scope(exit) index = i;
//            assert (currentIs(L));
//            advance();
//            while (moreTokens()) switch (current().type)
//            {
//                case tok("{"): skipBraces(); break;
//                case tok("("): skipParens(); break;
//                case tok("["): skipBrackets(); break;
//                case tok("]"): case tok("}"): return false;
//                case T: exit_section_(builder,m,/*todo*/,true);
// return true;
//                default: advance(); break;
//            }
//            return false;
//        }

    /**
     * Parses a VersionCondition
     * <p>
     * $(GRAMMAR $(RULEDEF versionCondition):
     * $(LITERAL 'version') $(LITERAL '$(LPAREN)') ($(LITERAL IntegerLiteral) | $(LITERAL Identifier) | $(LITERAL 'unittest') | $(LITERAL 'assert')) $(LITERAL '$(RPAREN)')
     * ;)
     */
    boolean parseVersionCondition() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.VersionCondition,false);
        Token v = expect(tok("version"));
        if (v == null) {
            cleanup(m);
            return false;
        }
//            node.versionIndex = v.index;
        if (!tokenCheck("(")) {
            return false;
        }
        if (currentIsOneOf(tok("intLiteral"), tok("identifier"), tok("unittest"), tok("assert")))
            advance();
        else {
            error("Expected an integer literal, an identifier, \"assert\", or \"unittest\"");
            exit_section_(builder, m, VERSION_CONDITION, true);
            return false;
        }
        expect(tok(")"));
        exit_section_(builder, m, VERSION_CONDITION, true);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.VersionSpecification,false);
        if (!tokenCheck("version")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck("=")) {
            cleanup(m);
            return false;
        }
        if (!currentIsOneOf(tok("identifier"), tok("intLiteral"))) {
            error("Identifier or integer literal expected");
            exit_section_(builder, m, VERSION_SPECIFICATION, true);
            return false;
        }
        advance();
        expect(tok(";"));
        exit_section_(builder, m, VERSION_SPECIFICATION, true);
        return true;
    }

    /**
     * Parses a WhileStatement
     * <p>
     * $(GRAMMAR $(RULEDEF whileStatement):
     * $(LITERAL 'while') $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement)
     * ;)
     */
    boolean parseWhileStatement() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.WhileStatement,false);
        if (!tokenCheck("while")) {
            cleanup(m);
            return false;
        }
//            node.startIndex = current().index;
        if (!tokenCheck("(")) {
            cleanup(m);
            return false;
        }
        if (!parseNodeQ("node.expression", "Expression")) {
            cleanup(m);
            return false;
        }
        if (!tokenCheck(")")) {
            return false;
        }
        if (currentIs(tok("}"))) {
            error("Statement expected"/*, false*/);//todo
            exit_section_(builder, m, WHILE_STATEMENT, true);
            return true; // this line makes DCD better
        }
        if (!parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement")) {
            cleanup(m);
            return false;
        }
        exit_section_(builder, m, WHILE_STATEMENT, true);
        return true;
    }

    /**
     * Parses a WithStatement
     * <p>
     * $(GRAMMAR $(RULEDEF withStatement):
     * $(LITERAL 'with') $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE statementNoCaseNoDefault)
     * ;)
     */
    boolean parseWithStatement() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean b = simpleParse("WithStatement", tok("with"), tok("("), "expression|parseExpression", tok(")"), "statementNoCaseNoDefault|parseStatementNoCaseNoDefault");
        exit_section_(builder, marker, WITH_STATEMENT, b);
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        final Marker marker = enter_section_(builder);
        final boolean b = parseLeftAssocBinaryExpression("XorExpression", "AndExpression",
            tok("^"));
        exit_section_(builder, marker, XOR_EXPRESSION_, b);
        return b;
    }

    void setTokens(Token[] tokens) {
        this.tokens = tokens;
    }

    /**
     * Returns: true if there are more tokens
     */
    boolean moreTokens() {
        return index < tokens.length;
    }

    boolean isCastQualifier() {
        final Marker m = enter_section_(builder);
        Token.IdType i = current().type;
        if (i.equals(tok("const"))) {
            if (peekIs(tok(")")))
                return true;
            return startsWith(tok("const"), tok("shared"), tok(")"));
        } else if (i.equals(tok("immutable"))) {
            return peekIs(tok(")"));
        } else if (i.equals(tok("inout"))) {
            if (peekIs(tok(")")))
                return true;
            return startsWith(tok("inout"), tok("shared"), tok(")"));
        } else if (i.equals(tok("shared"))) {
            if (peekIs(tok(")")))
                return true;
            if (startsWith(tok("shared"), tok("const"), tok(")")))
                return true;
            return startsWith(tok("shared"), tok("inout"), tok(")"));
        } else {
            return false;
        }
    }

    boolean isAssociativeArrayLiteral() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
        if (cachedAAChecks.keySet().contains(index))//todo check this translation is correct
            return true;
//            size_t currentIndex = current.index;
        Bookmark b = setBookmark();
        advance();
        boolean result = !currentIs(tok("]")) && parseExpression() && currentIs(tok(":"));
        cachedAAChecks.put(index, result);
        goToBookmark(b);
        return result;
    }

    Pair<DecType, Integer> isAutoDeclaration() {
        int beginIndex = Integer.MAX_VALUE;
        Bookmark b = setBookmark();
//            goToBookmark(b);// on scope exit
        loop:
        while (moreTokens()) {
            Token.IdType i = current().type;
            if (i.equals(tok("pragma"))) {
                beginIndex = Integer.MAX_VALUE;
                advance();
                if (currentIs(tok("("))) {
                    skipParens();
                    break;
                } else {
                    goToBookmark(b);
                    return new Pair<>(DecType.other, beginIndex);
                }
            } else if (i.equals(tok("package")) || i.equals(tok("private")) || i.equals(tok("protected")) || i.equals(tok("public"))) {
                beginIndex = Integer.MAX_VALUE;
                advance();
            } else if (i.equals(tok("@"))) {
                beginIndex = Math.min(beginIndex, index);
                advance();
                if (currentIs(tok("(")))
                    skipParens();
                else if (currentIs(tok("identifier"))) {
                    advance();
                    if (currentIs(tok("!"))) {
                        advance();
                        if (currentIs(tok("(")))
                            skipParens();
                        else
                            advance();
                    }
                    if (currentIs(tok("(")))
                        skipParens();
                } else {
                    goToBookmark(b);
                    return new Pair<>(DecType.other, beginIndex);
                }

            } else if (i.equals(tok("deprecated")) || i.equals(tok("align")) || i.equals(tok("extern"))) {
                beginIndex = Math.min(beginIndex, index);
                advance();
                if (currentIs(tok("(")))
                    skipParens();
            } else if (i.equals(tok("const")) || i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("synchronized"))) {
                if (peekIs(tok("("))) {
                    goToBookmark(b);
                    return new Pair<>(DecType.other, beginIndex);
                } else {
                    beginIndex = Math.min(beginIndex, index);
                    advance();
                    break;
                }
            } else if (i.equals(tok("auto")) || i.equals(tok("enum")) || i.equals(tok("export")) || i.equals(tok("final")) || i.equals(tok("__gshared")) || i.equals(tok("const")) || i.equals(tok("override")) || i.equals(tok("const")) || i.equals(tok("ref")) || i.equals(tok("scope")) || i.equals(tok("shared")) || i.equals(tok("static"))) {
                beginIndex = Math.min(beginIndex, index);
                advance();
            } else {
                break loop;
            }
        }
        if (index <= b.intValue())//todo
        {
            goToBookmark(b);
            return new Pair<>(DecType.other, beginIndex);
        }
        if (startsWith(tok("identifier"), tok("="))) {
            goToBookmark(b);
            return new Pair<>(DecType.autoVar, beginIndex);
        }
        if (startsWith(tok("identifier"), tok("("))) {
            advance();
            Token past = peekPastParens();
            if (past == null) {
                goToBookmark(b);
                return new Pair<>(DecType.other, beginIndex);
            } else if (past.type.equals(tok("="))) {
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
//            mixin(traceEnterAndExit!(__FUNCTION__));
        if (!moreTokens()) return false;
        Token.IdType i = current().type;
        if (i.equals(tok("final"))) {
            return !peekIs(tok("switch"));
        }
        if (i.equals(tok("debug"))) {
            if (peekIs(tok(":")))
                return true;
            if (peekIs(tok("=")))
                return true;
            if (peekIs(tok("("))) {
                //default
                Bookmark b = setBookmark();
                final boolean res = parseDeclaration(true, true);
                goToBookmark(b);
                return res;
            }
            return false;
        }
        if (i.equals(tok("version"))) {
            if (peekIs(tok("=")))
                return true;
            if (peekIs(tok("("))) {
                //default
                Bookmark b = setBookmark();
                final boolean res = parseDeclaration(true, true);
                goToBookmark(b);
                return res;
            }
            return false;
        }
        if (i.equals(tok("synchronized"))) {
            if (peekIs(tok("(")))
                return false;
            else {
                //default
                Bookmark b = setBookmark();
                final boolean res = parseDeclaration(true, true);
                goToBookmark(b);
                return res;
            }
        }
        if (i.equals(tok("static"))) {
            if (peekIs(tok("if")))
                return false;
            return !peekIs(tok("("));
        }
        if (i.equals(tok("scope"))) {
            return !peekIs(tok("("));
        }
        if (i.equals(tok("@")) || i.equals(tok("abstract")) || i.equals(tok("alias")) || i.equals(tok("align")) || i.equals(tok("auto")) || i.equals(tok("class")) || i.equals(tok("deprecated")) || i.equals(tok("enum")) || i.equals(tok("export")) || i.equals(tok("extern")) || i.equals(tok("__gshared")) || i.equals(tok("interface")) || i.equals(tok("const")) || i.equals(tok("override")) || i.equals(tok("package")) || i.equals(tok("private")) || i.equals(tok("protected")) || i.equals(tok("public")) || i.equals(tok("const")) || i.equals(tok("ref")) || i.equals(tok("struct")) || i.equals(tok("union")) || i.equals(tok("unittest"))) {
            return true;
        }
        if (i.equals(tok("int")) || i.equals(tok("bool")) || i.equals(tok("byte")) || i.equals(tok("cdouble")) || i.equals(tok("cent")) || i.equals(tok("cfloat")) || i.equals(tok("char")) || i.equals(tok("creal")) || i.equals(tok("dchar")) || i.equals(tok("double")) || i.equals(tok("float")) || i.equals(tok("idouble")) || i.equals(tok("ifloat")) || i.equals(tok("ireal")) || i.equals(tok("long")) || i.equals(tok("real")) || i.equals(tok("short")) || i.equals(tok("ubyte")) || i.equals(tok("ucent")) || i.equals(tok("uint")) || i.equals(tok("ulong")) || i.equals(tok("ushort")) || i.equals(tok("void")) || i.equals(tok("wchar"))) {
            return !peekIsOneOf(tok("."), tok("("));
        }
        if (i.equals(tok("asm")) || i.equals(tok("break")) || i.equals(tok("case")) || i.equals(tok("continue")) || i.equals(tok("default")) || i.equals(tok("do")) || i.equals(tok("for")) || i.equals(tok("foreach")) || i.equals(tok("foreach_reverse")) || i.equals(tok("goto")) || i.equals(tok("if")) || i.equals(tok("return")) || i.equals(tok("switch")) || i.equals(tok("throw")) || i.equals(tok("try")) || i.equals(tok("while")) || i.equals(tok("{")) || i.equals(tok("assert"))) {
            return false;
        } else {
            Bookmark b = setBookmark();
            final boolean res = parseDeclaration(true, true);
            goToBookmark(b);
            return res;
        }
    }

    /// Only use this in template parameter parsing
    boolean isType() {
        if (!moreTokens()) return false;
        Bookmark b = setBookmark();
//            auto c = allocator.setCheckpoint();
//            scope (exit) allocator.rollback(c);
        if (!parseType()) {
            goToBookmark(b);
            return false;
        }
        goToBookmark(b);
        return currentIsOneOf(tok(","), tok(")"), tok("="));
    }

    boolean isStorageClass() {
        if (!moreTokens()) return false;
        Token.IdType i = current().type;
        if (i.equals(tok("const")) || i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("shared"))) {
            return !peekIs(tok("("));
        } else
            return i.equals(tok("@")) || i.equals(tok("deprecated")) || i.equals(tok("abstract")) || i.equals(tok("align")) || i.equals(tok("auto")) || i.equals(tok("enum")) || i.equals(tok("extern")) || i.equals(tok("final")) || i.equals(tok("const")) || i.equals(tok("override")) || i.equals(tok("const")) || i.equals(tok("ref")) || i.equals(tok("__gshared")) || i.equals(tok("scope")) || i.equals(tok("static")) || i.equals(tok("synchronized"));
    }

    boolean isAttribute() {
        if (!moreTokens()) return false;
        Token.IdType i = current().type;
        if (i.equals(tok("const")) || i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("scope"))) {
            return !peekIs(tok("("));
        } else if (i.equals(tok("static"))) {
            return !peekIsOneOf(tok("assert"), tok("this"), tok("if"), tok("~"));
        } else if (i.equals(tok("shared"))) {
            return !(startsWith(tok("shared"), tok("static"), tok("this")) || startsWith(tok("shared"), tok("static"), tok("~")) || peekIs(tok("(")));
        } else if (i.equals(tok("pragma"))) {
            Bookmark b = setBookmark();
            advance();
            Token past = peekPastParens();
            if (past == null || past.type.equals(tok(";"))) {
                goToBookmark(b);
                return false;
            }
            goToBookmark(b);
            return true;
        } else
            return i.equals(tok("deprecated")) || i.equals(tok("private")) || i.equals(tok("package")) || i.equals(tok("protected")) || i.equals(tok("public")) || i.equals(tok("export")) || i.equals(tok("final")) || i.equals(tok("synchronized")) || i.equals(tok("override")) || i.equals(tok("abstract")) || i.equals(tok("auto")) || i.equals(tok("__gshared")) || i.equals(tok("const")) || i.equals(tok("const")) || i.equals(tok("@")) || i.equals(tok("ref")) || i.equals(tok("extern")) || i.equals(tok("align"));
    }

    boolean isMemberFunctionAttribute(Token.IdType t) {
        return t.equals(tok("const")) || t.equals(tok("immutable")) || t.equals(tok("inout")) || t.equals(tok("shared")) || t.equals(tok("@")) || t.equals(tok("const")) || t.equals(tok("const")) || t.equals(tok("return")) || t.equals(tok("scope"));
    }

    boolean isTypeCtor(Token.IdType t) {
        return t.equals(tok("const")) || t.equals(tok("immutable")) || t.equals(tok("inout")) || t.equals(tok("shared"));
    }

    boolean currentIsMemberFunctionAttribute() {
        return moreTokens() && isMemberFunctionAttribute(current().type);
    }

    boolean parseLeftAssocBinaryExpression(String ExpressionType, String ExpressionPartType, Token.IdType... operators) {
        return parseLeftAssocBinaryExpression(ExpressionType, ExpressionPartType, false, operators);
    }

    boolean parseLeftAssocBinaryExpression(String ExpressionType, String ExpressionPartType, boolean part, Token.IdType... operators)//(alias ExpressionType, alias ExpressionPartType, Operators ...)(ExpressionNode part = null)
    {
        boolean node = (!part) ? parseName(ExpressionPartType) : part;
        if (!node)
            return false;
        while (currentIsOneOf(operators)) {
//                auto n = allocator.make!ExpressionType;
            advance();
            if (!parseNodeQ("n.right", ExpressionPartType)) {
                return false;
            }
        }
        return true;
    }

    //todo idk why though, probably the true/false args
    boolean parseCommaSeparatedRule(String listType, String itemType)//(alias ListType, alias ItemType,)
    {
//            final boolean setLineAndColumn = false;
//        Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ListType,false);
//            if (setLineAndColumn)
//            {
////                node.line = current().line;
////                node.column = current().column;
//            }
//            final Marker m = enter_section_(builder);
        while (moreTokens()) {
            if (!parseName(itemType)) {
//                    cleanup(m);
                return false;
            }
            if (currentIs(tok(","))) {
                advance();
                if (currentIsOneOf(tok(")"), tok("}"), tok("]")))
                    break;
                else
                    continue;
            } else
                break;
        }
//            exit_section_(builder,m,,true);
        return true;
    }

    void warn(String message) {
        if (suppressMessages > 0)
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

    void error(String message) {
        if (suppressMessages == 0) {
            ++errorCount;
//                auto column = index < tokens.length ? tokens[index].column : tokens[$ - 1].column;
//                auto line = index < tokens.length ? tokens[index].line : tokens[$ - 1].line;
//                if (messageFunction == null)
//                stderr.writefln("%s(%d:%d)[error]: %s", fileName, line, column, message);
//            else
//                messageFunction(fileName, line, column, message, true);
            builder.error(message);
        } else
            ++suppressedErrorCount;
        while (moreTokens()) {
            if (currentIsOneOf(tok(";"), tok("}"),
                tok(")"), tok("]"))) {
                advance();
                break;
            } else
                advance();
        }
    }

    void skip(Token.IdType o, Token.IdType c)//(alias O, alias C)
    {
        assert (currentIs(o));
        advance();
        int depth = 1;
        while (moreTokens()) {
            if (tokens[index].type.equals(c)) {
                advance();
                depth--;
                if (depth <= 0)
                    return;
            } else if (tokens[index].type.equals(o)) {
                depth++;
                advance();
            } else {
                advance();
            }
        }
    }

    void skipBraces() {
        skip(tok("{"), tok("}"));
    }

    void skipParens() {
        skip(tok("("), tok(")"));
    }

    void skipBrackets() {
        skip(tok("["), tok("]"));
    }

    Token peek() {
        return index + 1 < tokens.length ? tokens[index + 1] : null;
    }

    Token peekPast(Token.IdType o, Token.IdType c)//(alias O, alias C)
    {
        if (index >= tokens.length)
            return null;
        int depth = 1;
        int i = index;
        ++i;
        while (i < tokens.length) {
            if (tokens[i].type.equals(o)) {
                ++depth;
                ++i;
            } else if (tokens[i].type.equals(c)) {
                --depth;
                ++i;
                if (depth <= 0)
                    break;
            } else
                ++i;
        }
        return i >= tokens.length ? null : depth == 0 ? tokens[i] : null;
    }

    Token peekPastParens() {
        return peekPast(tok("("), tok(")"));
    }

    Token peekPastBrackets() {
        return peekPast(tok("["), tok("]"));
    }

    Token peekPastBraces() {
        return peekPast(tok("{"), tok("}"));
    }

    boolean peekIs(Token.IdType t) {
        return index + 1 < tokens.length && tokens[index + 1].type.equals(t);
    }

    boolean peekIsOneOf(Token.IdType... types) {
        if (index + 1 >= tokens.length) return false;
        final Token.IdType needle = tokens[index + 1].type;
        for (Token.IdType type : types) {
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
    Token expect(Token.IdType type) {
        if (index < tokens.length && tokens[index].type.equals(type)) {
//            assert (builder.getTokenType().equals(tokens[index].type.type));
            if (!builder.getTokenType().equals(tokens[index].type.type)) {
                throw new AssertionError();
            }
            builder.advanceLexer();
            return tokens[index++];
        } else {
            //technically these == work but .equals would make me feel more comfortable
            String tokenString = type.toString();
            boolean shouldNotAdvance = index < tokens.length && (tokens[index].type.equals(tok(")")) || tokens[index].type.equals(tok(";")) || tokens[index].type.equals(tok("}")));
            String token = (index < tokens.length ? (tokens[index].text == null ? str(tokens[index].type) : tokens[index].text) : "EOF");
            error("Expected " + tokenString + " instead of " + token/*,!shouldNotAdvance*/);//todo type
            return null;
        }
    }

    public String str(Object o) {
        return o.toString();
    }

    /**
     * Returns: the _current token
     */
    Token current() {
        return tokens[index];
    }

    /**
     * Returns: the _previous token
     */
    Token previous() {
        return tokens[index - 1];
    }

    /**
     * Advances to the next token and returns the current token
     */
    Token advance() {
        builder.advanceLexer();
        return tokens[index++];
    }

    /**
     * Returns: true if the current token has the given type
     */
    boolean currentIs(Token.IdType type) {
        return index < tokens.length && tokens[index].type.equals(type);
    }

    /**
     * Returns: true if the current token is one of the given types
     */
    boolean currentIsOneOf(Token.IdType... types) {
        if (index >= tokens.length)
            return false;
        for (Token.IdType type : types) {
            if (type.equals(current().type)) {
                return true;
            }
        }
        return false;

//        return canFind(types, current().type);
    }

//        template simpleParse//(NodeType, parts ...)
//        {
//            if (__traits(hasMember, NodeType, "comment"))
//            enum nodeComm = "node.comment = comment;\n"
//            ~ "comment = null;\n";
//        else enum nodeComm = "const";
//
//            if (__traits(hasMember, NodeType, "line"))
//            enum nodeLine = "node.line = current().line;\n";
//        else enum nodeLine = "const";
//
//            if (__traits(hasMember, NodeType, "column"))
//            enum nodeColumn = "node.column = current().column;\n";
//        else enum nodeColumn = "const";
//
//            if (__traits(hasMember, NodeType, "location"))
//            enum nodeLoc = "node.location = current().index;\n";
//        else enum nodeLoc = "const";
//
////            enum simpleParse = "Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes." ~ NodeType.Stringof ~ ",false);\n"
//            ~ nodeComm ~ nodeLine ~ nodeColumn ~ nodeLoc
//            ~ simpleParseItems!(parts)
//            ~ "\nreturn true;\n";
//        }
//
//        template simpleParseItems//(items ...)
//        {
//            if (items.length > 1)
//            enum simpleParseItems = simpleParseItem!(items[0]) ~ "\n"
//            ~ simpleParseItems!(items[1 .. $]);
//        else if (items.length == 1)
//            enum simpleParseItems = simpleParseItem!(items[0]);
//        else
//            enum simpleParseItems = "const";
//        }
//
//        template simpleParseItem//(alias item)
//        {
//            if (is (typeof(item) == String))
//            enum simpleParseItem = "if ((node." ~ item[0 .. item.countUntil("|")]
//            ~ " = " ~ item[item.countUntil("|") + 1 .. $] ~ "()) == null) { return null; }";
//        else
//            enum simpleParseItem = "if (expect(" ~ item.Stringof ~ ") == null) { return null; }";
//        }

//        template traceEnterAndExit(String fun)
//        {
//            enum traceEnterAndExit = "version (dparse_verbose) { _traceDepth++; trace("const"
//            ~ "\033[01;32m" ~ fun ~ "\033[0m"); }"
//            ~ "version (dparse_verbose) scope(exit) { trace("const"
//                ~ "\033[01;31m" ~ fun ~ "\033[0m"); _traceDepth--; }";
//            }

//            version (dparse_verbose)
//            {
//        import std.stdio : stderr;
//            void trace(String message)
//            {
//                if (suppressMessages > 0)
//                    return;
//                auto depth = format("%4d ", _traceDepth);
//                if (index < tokens.length)
//                    stderr.writeln(depth, message, "(", current().line, ":", current().column, ")");
//                else
//                    stderr.writeln(depth, message, "(EOF:0)");
//            }
//    }
//    else
//            {
//                void trace(lazy String) {}
//            }

    boolean startsWith(Token.IdType... types) {
        if (index + types.length >= tokens.length)
            return false;
        for (int i = 0; (i < types.length) && ((index + i) < tokens.length); ++i) {
            if (tokens[index + i].type != types[i])
                return false;
        }
        return true;
    }

    Bookmark setBookmark() {
////        mixin(traceEnterAndExit!(__FUNCTION__));
        ++suppressMessages;
        final Marker m = enter_section_(builder);
        return new Bookmark(index, m);
    }

    void abandonBookmark(Bookmark bookmark) {
        --suppressMessages;
        if (suppressMessages == 0)
            suppressedErrorCount = 0;
        if (!bookmark.dropped) {
            bookmark.m.drop();
            bookmark.dropped = true;
        }
    }

    void goToBookmark(Bookmark bookmark) {
        --suppressMessages;
        if (suppressMessages == 0)
            suppressedErrorCount = 0;
        index = bookmark.num;
        assert !bookmark.dropped;
        bookmark.m.rollbackTo();
        bookmark.dropped = true;
    }

    boolean parseNodeQ(String VarName, String NodeName) {
        if (!parseName(NodeName)) {
            return false;
        }
        //we can ignore VarName, since that is aside effect not strictly necessary for parsing
        return true;
//            enum parseNodeQ = "{ if ((" ~ VarName ~ " = parse" ~ NodeName ~ "()) == null) return null; }";//potentiall huge implmentatation in java todo
    }

    boolean nullCheck(String exp) {
        return exp != null;
    }

    boolean tokenCheck(String Tok) {
        return expect(tok(Tok)) != null;
    }

    boolean tokenCheck(String Exp, String Tok) {
        final Token t = expect(tok(Tok));
        if (t == null) {
            return false;
        } else {
            //do nothing. Side-effects not relevant for purely parsing
        }
//            enum tokenCheck = "{auto t = expect(tok("const" ~ Tok ~ "const"));"
//            ~ "if (t == null) { return null;}"
//            ~ "else {" ~ Exp ~ " = *t; }}";
        return true;
    }

    boolean parseStaticCtorDtorCommon() {
//            node.line = current().line;
//            node.column = current().column;
        if (!tokenCheck("this")) {
            return false;
        }
        if (!tokenCheck("(")) {
            return false;
        }
        if (!tokenCheck(")")) {
            return false;
        }
        StackBuffer attributes;
        while (moreTokens() && !currentIsOneOf(tok("{"), tok("in"), tok("out"), tok("body"), tok(";"))) {
            if (!(parseMemberFunctionAttribute())) {
                return false;
            }
        }
//            ownArray(node.memberFunctionAttributes, attributes);
        if (currentIs(tok(";")))
            advance();
        else if (!parseNodeQ("node.functionBody", "FunctionBody")) {
            return false;
        }
        return true;
    }

    boolean parseInterfaceOrClass() {
        Token ident = expect(tok("identifier"));
        if (ident == null) {
            return false;
        }
//            node.name = *ident;
//            node.comment = comment;
//            comment = null;
        if (currentIs(tok(";"))) {
            return emptyBody();
        }
        if (currentIs(tok("{"))) {
            return structBody();
        }
        if (currentIs(tok("("))) {
            if (!parseNodeQ("node.templateParameters", "TemplateParameters")) {
                return false;
            }
            if (currentIs(tok(";"))) {
                return emptyBody();
            }
            return constraint(false);
        }
        if (currentIs(tok(":"))) {
            return baseClassList();
        }
        return structBody();

    }

    private boolean emptyBody() {
        advance();
        return true;
    }

    private boolean structBody() {
        return parseNodeQ("node.structBody", "StructBody");
    }

    private boolean baseClassList() {
        advance(); // :
        if (!parseBaseClassList()) {
            return false;
        }
        if (currentIs(tok("if"))) {
            return constraint(true);
        }
        return structBody();
    }

    private boolean constraint(boolean baseClassListQ) {
        if (currentIs(tok("if"))) {
            if (!parseNodeQ("node.raint", "Constraint")) {
                return false;
            }
        }
        if (baseClassListQ) {
            if (currentIs(tok("{"))) {
                return structBody();
            } else if (currentIs(tok(";"))) {
                return emptyBody();
            } else {
                error("Struct body or ';' expected");
                return false;
            }
        }
        if (currentIs(tok(":"))) {
            return baseClassList();
        }
        if (currentIs(tok("if"))) {
            return constraint(baseClassListQ);
        }
        if (currentIs(tok(";"))) {
            return emptyBody();
        }
        if (currentIs(tok(":"))) {
            return baseClassList();
        }
        return structBody();
    }

    private boolean parseName(String NodeName) {
        switch (NodeName) {
            case "AliasThisDeclaration":
                return parseAliasThisDeclaration();
            case "AlignAttribute":
                return parseAlignAttribute();
            case "ArgumentList":
                return parseArgumentList();
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
            case "BodyStatement":
                return parseBodyStatement();
            case "BreakStatement":
                return parseBreakStatement();
            case "BaseClass":
                return parseBaseClass();
            case "BaseClassList":
                return parseBaseClassList();
//                case "CaseRangeStatement":
//                    return parseCaseRangeStatement();
//                case "CaseStatement":
//                    return parseCaseStatement();
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
//                case "ConditionalDeclaration":
//                    return parseConditionalDeclaration();
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
//                case "ExpressionStatement":
//                    return parseExpressionStatement();
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
                return parseForeachTypeList();
//                case "FunctionAttribute":
//                    return parseFunctionAttribute();
            case "FunctionBody":
                return parseFunctionBody();
//                case "FunctionDeclaration":
//                    return parseFunctionDeclaration();
            case "GotoStatement":
                return parseGotoStatement();
            case "IdentifierChain":
                return parseIdentifierChain();
            case "IdentifierList":
                return parseIdentifierList();
            case "IdentifierOrTemplateChain":
                return parseIdentifierOrTemplateChain();
            case "IdentifierOrTemplateInstance":
                return parseIdentifierOrTemplateInstance();
            case "IfStatement":
                return parseIfStatement();
            case "ImportBind":
                return parseImportBind();
//                case "ImportBindings":
//                    return parseImportBindings();
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
            case "ThrowStatement":
                return parseThrowStatement();
            case "TryStatement":
                return parseTryStatement();
            case "Type":
                return parseType();
            case "Type2":
                return parseType2();
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
//                case "EqualExpression":
//                    return parseEqualExpression();
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
//                case "InExpression":
//                    return parseInExpression();
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
        First first;
        Second second;

        Pair(First first, Second second) {
            this.first = first;
            this.second = second;
        }
    }

    class Bookmark extends Number {
        final int num;
        final Marker m;
        private boolean dropped = false;

        public Bookmark(int num, Marker m) {
            this.num = num;
            this.m = m;
        }

        @Override
        public int intValue() {
            return num;
        }

        @Override
        public long longValue() {
            return (long) num;
        }

        @Override
        public float floatValue() {
            return (float) num;
        }

        @Override
        public double doubleValue() {
            return num;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            boolean abandoned = false;
            assert dropped;
//            try {
//                m.drop();//todo check if already dropped
//            } catch (Throwable e) {
//                abandoned = true;
//                //exveption means that the bookmark has likely already been abandoned
//            }
//            if (!abandoned) {
//                throw new IllegalStateException("bookmark not abandoned" + this.toString());
//            }
        }

        @Override
        public String toString() {
            return "Bookmark{" +
                "num=" + num +
                ", m=" + m +
                '}';
        }
    }

}
