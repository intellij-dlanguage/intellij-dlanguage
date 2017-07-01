package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.psi.tree.IElementType;
import hidden.edu.emory.mathcs.backport.java.util.Arrays;
import net.masterthought.dlanguage.parser.Token.IdType;
import net.masterthought.dlanguage.psi.DLanguageTypes;
import org.fest.util.Sets;

import java.util.HashMap;

import static com.intellij.lang.parser.GeneratedParserUtilBase.enter_section_;
import static com.intellij.lang.parser.GeneratedParserUtilBase.exit_section_;
import static net.masterthought.dlanguage.parser.DummyClasses.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 6/28/2017.
 */
public class ParserPreliminaryJavaWriteUp {

    /**
     * Params:
     *     tokens = the tokens parsed by dparse.lexer
     *     fileName = the name of the file being parsed
     *     messageFunction = a function to call on error or warning messages.
     *         The parameters are the file name, line number, column number,
     *         the error or warning message, and a booleanean (true means error, false
     *         means warning).
     * Returns: the parsed module
     */
    boolean parseModule(Token[] tokens, String fileName, RollbackAllocator allocator)
    {
        Parser parser = new Parser();
//        parser.fileName = fileName;
        parser.tokens = tokens;
//        parser.messageFunction = messageFunction;
//        parser.allocator = allocator;
        boolean mod = parser.parseModule();
//        if (warningCount != null)
//        *warningCount = parser.warningCount;
//        if (errorCount != null)
//        *errorCount = parser.errorCount;
        return mod;
    }

    void mixin(Object... args){
        //todo
    }

    /**
     * D Parser.
     *
     * It == sometimes useful to sub-class Parser to skip over things that are not
     * interesting. For example, DCD skips over function bodies when caching symbols
     * from imported files.
     */
    class Parser
    {


        public void cleanup(Marker... markers){
            for (Marker marker : markers) {
                exit_section_(builder,marker, STATIC_IF_CONDITION,false);//the static if could be anything. todo make an actual dummy type for it
            }
        }

        /**
         * Parses an AddExpression.
         *  converted
         * $(GRAMMAR $(RULEDEF addExpression):
         *       $(RULE mulExpression)
         *     | $(RULE addExpression) $(LPAREN)$(LITERAL '+') | $(LITERAL'-') | $(LITERAL'~')$(RPAREN) $(RULE mulExpression)
         *     ;)
         */
        boolean parseAddExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            final Marker section = enter_section_(builder);
            boolean result = parseLeftAssocBinaryExpression("AddExpression", "MulExpression",tok("+"), tok("-"), tok("~"));
            exit_section_(builder,section, ADD_EXPRESSION_,result);
            return result;
        }

        /**
         * Parses an AliasDeclaration.
         * converted
         * $(GRAMMAR $(RULEDEF aliasDeclaration):
         *       $(LITERAL 'alias') $(RULE aliasInitializer) $(LPAREN)$(LITERAL ',') $(RULE aliasInitializer)$(RPAREN)* $(LITERAL ';')
         *     | $(LITERAL 'alias') $(RULE storageClass)* $(RULE type) $(RULE identifierList) $(LITERAL ';')
         *     | $(LITERAL 'alias') $(RULE storageClass)* $(RULE type) $(RULE identifier) $(LITERAL '(') $(RULE parameters) $(LITERAL ')') $(memberFunctionAttribute)* $(LITERAL ';')
         *     ;)
         */
        boolean parseAliasDeclaration()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
            if(!tokenCheck("alias")){
                cleanup(m);
                return false;
            }
//            node.comment = comment;
//            comment = null;

            if (startsWith(tok("identifier"), tok("=")) || startsWith(tok("identifier"), tok("("))) {
                final Marker aliasInitializers = enter_section_(builder);
                do {
                    if (!parseAliasInitializer()) {
                        cleanup(m, aliasInitializers);
                        return false;
                    }
                    if (currentIs(tok(","))) {
                        advance();
                    } else {
                        break;
                    }
                }
                while (moreTokens());
                exit_section_(builder, aliasInitializers, ALIAS_DECLARATION_Y, true);
            } else {
                final Marker storageClassesMarker = enter_section_(builder);
                while (moreTokens() && isStorageClass()) {
                    if (!parseStorageClass()) {
                        cleanup(m, storageClassesMarker);
                        return false;
                    }
                }
                exit_section_(builder, storageClassesMarker, STORAGE_CLASSES, true);
                if (!parseNodeQ("node.type", "Type")) {
                    cleanup(m);
                    return false;
                }
                if (!parseNodeQ("node.identifierList", "IdentifierList")) {
                    cleanup(m);
                    return false;
                }
                if (currentIs(tok("("))) {
                    mixin(parseNodeQ("node.parameters", "Parameters"));
                    final Marker memberFunctionAttributes = enter_section_(builder);
                    while (moreTokens() && currentIsMemberFunctionAttribute()) {
                        if (!parseMemberFunctionAttribute()) {
                            cleanup(m, memberFunctionAttributes);
                            return false;
                        }
                    }
                    exit_section_(builder, memberFunctionAttributes, MEMBER_FUNCTION_ATTRIBUTES, true);

                }
            }
            return true;
        }


        private boolean isFunction()
        {
            if (currentIsOneOf(tok("function"), tok("delegate"), tok("{")))
                return true;
            if (startsWith(tok("identifier"), tok("=>")))
                return true;
            if (currentIs(tok("(")))
            {
                Token t = peekPastParens();
                if (t != null)
                {
                    if (t.type == tok("=>") || t.type == tok("{")
                        || isMemberFunctionAttribute(t.type))
                        return true;
                }
            }
            return false;
        }

        /**
         * Parses an AliasInitializer.
         *
         * $(GRAMMAR $(RULEDEF aliasInitializer):
         *       $(LITERAL Identifier) $(RULE templateParameters)? $(LITERAL '=') $(RULE storageClass)* $(RULE type)
         *     | $(LITERAL Identifier) $(RULE templateParameters)? $(LITERAL '=') $(RULE functionLiteralExpression)
         *     ;)
         */
        boolean parseAliasInitializer() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
            if (!tokenCheck( "identifier")) {//todo check that name
                cleanup(m);
                return false;
            }
            if (currentIs(tok("(")))
                if (!parseNodeQ("node.templateParameters", "TemplateParameters")) {
                    cleanup(m);
                    return false;
                }
            if (!tokenCheck("=")) {
                cleanup(m);
                return false;
            }
            if (isFunction())
                if (!parseNodeQ("node.functionLiteralExpression", "FunctionLiteralExpression")) {
                    cleanup(m);
                    return false;
                } else {
                    final Marker storageClassesMarker = enter_section_(builder);
                    while (moreTokens() && isStorageClass()) {
                        if (!parseStorageClass()) {
                            cleanup(m, storageClassesMarker);
                            return false;
                        }
                    }
                    exit_section_(builder, storageClassesMarker, STORAGE_CLASSES, true);
                    if (!parseNodeQ("node.type", "Type")) {
                        cleanup(m);
                        return false;
                    }
                }
            return true;
        }

        /**
         * Parses an AliasThisDeclaration.
         *
         * $(GRAMMAR $(RULEDEF aliasThisDeclaration):
         *     $(LITERAL 'alias') $(LITERAL Identifier) $(LITERAL 'this') $(LITERAL ';')
         *     ;)
         */
        boolean parseAliasThisDeclaration() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AliasThisDeclaration,false);
            if (!tokenCheck("alias")) {
                cleanup(m);
                return false;
            }
            if(!tokenCheck("identifier")){//todo check that
                cleanup(m);
                return false;
            }
            if (!tokenCheck("this")) {
                cleanup(m);
                return false;
            }
            exit_section_(builder, m, ALIAS_THIS, true);
            return true;
        }

        /**
         * Parses an AlignAttribute.
         *
         * $(GRAMMAR $(RULEDEF alignAttribute):
         *     $(LITERAL 'align') ($(LITERAL '$(LPAREN)') $(RULE AssignExpression) $(LITERAL '$(RPAREN)'))?
         *     ;)
         */
        boolean parseAlignAttribute()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AlignAttribute,false);
            expect(tok("align"));
            if (currentIs(tok("(")))
            {
                if (!tokenCheck("(")) {
                    cleanup(m);
                    return false;
                }
                if(!parseNodeQ("node.assignExpression", "AssignExpression")){
                    cleanup(m);
                    return false;
                }
                if (!tokenCheck(")")) {
                    cleanup(m);
                    return false;
                }
            }
            exit_section_(builder,m,ALIGN_ATTRIBUTE,true);
            return true;
        }

        /**
         * Parses an AndAndExpression.
         *
         * $(GRAMMAR $(RULEDEF andAndExpression):
         *       $(RULE orExpression)
         *     | $(RULE andAndExpression) $(LITERAL '&&') $(RULE orExpression)
         *     ;)
         */
        boolean parseAndAndExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
            final boolean result = parseLeftAssocBinaryExpression("AndAndExpression", "OrExpression", tok("&&"));
            exit_section_(builder,m,
                AND_EXXPRESSION_,result);//todo new types needed.
            return result;
        }

        /**
         * Parses an AndExpression.
         *
         * $(GRAMMAR $(RULEDEF andExpression):
         *       $(RULE cmpExpression)
         *     | $(RULE andExpression) $(LITERAL '&') $(RULE cmpExpression)
         *     ;)
         */
        boolean parseAndExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
            final boolean result = parseLeftAssocBinaryExpression("AndExpression", "CmpExpression", tok("&"));
            exit_section_(builder,m, AND_EXXPRESSION_,result);
            return result;
        }

        /**
         * Parses an ArgumentList.
         *
         * $(GRAMMAR $(RULEDEF argumentList):
         *     $(RULE assignExpression) ($(LITERAL ',') $(RULE assignExpression)?)*
         *     ;)
         */
        boolean parseArgumentList()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            if (!moreTokens())
            {
                error("argument list expected instead of EOF");
                return false;
            }
            final Marker argumentList = enter_section_(builder);
            boolean node = parseCommaSeparatedRule("ArgumentList", "AssignExpression");
            if(!node){
                cleanup(argumentList);
                return false;
            }
//            if (moreTokens()) node.endLocation = current().index;// figure out what this  is about
            exit_section_(builder,argumentList,ARGUMENT_LIST,true);
            return true;
        }

        /**
         * Parses Arguments.
         *
         * $(GRAMMAR $(RULEDEF arguments):
         *     $(LITERAL '$(LPAREN)') $(RULE argumentList)? $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseArguments()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Arguments,false);
            if(!tokenCheck("(")){/*cleanup(m);*/return false;}
            if (!currentIs(tok(")")))
            if(!parseNodeQ("node.argumentList", "ArgumentList")){/*cleanup(m)*/;return false;}
            if(!tokenCheck(")")){/*cleanup(m);*/return false;}
//            exit_section_(builder,m,ArGUM,true);//todo needs a type
            return true;
        }

        /**
         * Parses an ArrayInitializer.
         *
         * $(GRAMMAR $(RULEDEF arrayInitializer):
         *       $(LITERAL '[') $(LITERAL ']')
         *     | $(LITERAL '[') $(RULE arrayMemberInitialization) ($(LITERAL ',') $(RULE arrayMemberInitialization)?)* $(LITERAL ']')
         *     ;)
         */
        boolean parseArrayInitializer()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ArrayInitializer,false);
            final Marker arrayInit = enter_section_(builder);
            Token open = expect(tok("["));
            if(open == null){
                cleanup(arrayInit);
                return false;
            }
            while (moreTokens())
            {
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
            if(close == null){
                cleanup(arrayInit);
                return false;
            }
//            node.endLocation = close.index;
            exit_section_(builder,arrayInit,ARRAY_INITIALIZER,true);
            return true;
        }

        /**
         * Parses an ArrayLiteral.
         *
         * $(GRAMMAR $(RULEDEF arrayLiteral):
         *     $(LITERAL '[') $(RULE argumentList)? $(LITERAL ']')
         *     ;)
         */
        boolean parseArrayLiteral()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ArrayLiteral,false);
            if (!tokenCheck("[")) {
                cleanup(m);
                return false;
            }
            if (!currentIs(tok("]")))
                if (!parseNodeQ("node.argumentList", "ArgumentList")) {
                    cleanup(m);
                    return false;
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
         *
         * $(GRAMMAR $(RULEDEF arrayMemberInitialization):
         *     ($(RULE assignExpression) $(LITERAL ':'))? $(RULE nonVoidInitializer)
         *     ;)
         */
        boolean parseArrayMemberInitialization() {
//            mixin(traceEnterAndExit!(__FUNCTION__))
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ArrayMemberInitialization,false);
            if (current().type == tok("[")) {
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
            if (current().type == tok("{")) {
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
         *
         * $(GRAMMAR $(RULEDEF asmAddExp):
         *       $(RULE asmMulExp)
         *     | $(RULE asmAddExp) ($(LITERAL '+') | $(LITERAL '-')) $(RULE asmMulExp)
         *     ;)
         */
        boolean parseAsmAddExp()
        {
//            mixin (traceEnterAndExit!(__FUNCTION__));
            final Marker m = enter_section_(builder);
            final boolean result = parseLeftAssocBinaryExpression("AsmAddExp", "AsmMulExp", tok("+"), tok("-"));
            exit_section_(builder,m,ASM_ADD_EXP,result);
            return result;
        }

        /**
         * Parses an AsmAndExp
         *
         * $(GRAMMAR $(RULEDEF asmAndExp):
         *       $(RULE asmEqualExp)
         *     | $(RULE asmAndExp) $(LITERAL '&') $(RULE asmEqualExp)
         *     ;)
         */
        boolean parseAsmAndExp()
        {
//            mixin (traceEnterAndExit!(__FUNCTION__));
            final Marker m = enter_section_(builder);
            final boolean result = parseLeftAssocBinaryExpression("AsmAndExp", "AsmEqualExp", tok("&"));
            exit_section_(builder,m,ASM_AND_EXP,result);
            return result;
        }

        /**
         * Parses an AsmBrExp
         *
         * $(GRAMMAR $(RULEDEF asmBrExp):
         *       $(RULE asmUnaExp)
         *     | $(RULE asmBrExp)? $(LITERAL '[') $(RULE asmExp) $(LITERAL ']')
         *     ;)
         */
        boolean parseAsmBrExp()
        {
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
                    while (currentIs(tok("[")))
                    {
                        advance(); // [
                        if(!parseNodeQ("node.asmExp", "AsmExp")){
                            cleanup(m);
                            return false;
                        }
                        if (!tokenCheck("]")) {
                            cleanup(m);
                            return false;
                        }
                    }
                }
            }
            else
            {
                if(!parseNodeQ("node.asmUnaExp", "AsmUnaExp")){
                    cleanup(m);
                    return false;
                }
                while (currentIs(tok("[")))
                {
                    advance(); // [
                    if(!parseNodeQ("node.asmExp", "AsmExp")){
                        cleanup(m);
                        return false;
                    }
                    if (!tokenCheck("]")) {
                        cleanup(m);
                        return false;
                    }
                }
            }
            exit_section_(builder,m,DLanguageTypes.ASM_BR_EXP,true);
            return true;
        }

        /**
         * Parses an AsmEqualExp
         *
         * $(GRAMMAR $(RULEDEF asmEqualExp):
         *       $(RULE asmRelExp)
         *     | $(RULE asmEqualExp) ('==' | '!=') $(RULE asmRelExp)
         *     ;)
         */
        boolean parseAsmEqualExp()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            final Marker m = enter_section_(builder);
            final boolean result = parseLeftAssocBinaryExpression("AsmEqualExp", "AsmRelExp", tok("=="), tok("!="));
            exit_section_(builder,m,ASM_EQUAL_EXP,result);
            return result;
        }

        /**
         * Parses an AsmExp
         *
         * $(GRAMMAR $(RULEDEF asmExp):
         *     $(RULE asmLogOrExp) ($(LITERAL '?') $(RULE asmExp) $(LITERAL ':') $(RULE asmExp))?
         *     ;)
         */
        boolean parseAsmExp()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            AsmExp node = allocator.make!AsmExp;
            final Marker m = enter_section_(builder);
            if(!parseNodeQ("node.left", "AsmLogOrExp")){
                cleanup(m);
                return false;
            }
            if (currentIs(tok("?")))
            {
                advance();
                if(!parseNodeQ("node.middle", "AsmExp")){
                    cleanup(m);
                    return false;
                }
                if(!tokenCheck(":")){
                    cleanup(m);
                    return false;
                }
                if(!parseNodeQ("node.right", "AsmExp")){
                    cleanup(m);
                    return false;
                }
            }
            exit_section_(builder,m,ASM_EXP,true);
            return true;
        }

        /**
         * Parses an AsmInstruction
         *
         * $(GRAMMAR $(RULEDEF asmInstruction):
         *       $(LITERAL Identifier)
         *     | $(LITERAL 'align') $(LITERAL IntegerLiteral)
         *     | $(LITERAL 'align') $(LITERAL Identifier)
         *     | $(LITERAL Identifier) $(LITERAL ':') $(RULE asmInstruction)
         *     | $(LITERAL Identifier) $(RULE operands)
         *     | $(LITERAL 'in') $(RULE operands)
         *     | $(LITERAL 'out') $(RULE operands)
         *     | $(LITERAL 'int') $(RULE operands)
         *     ;)
         */
        boolean parseAsmInstruction() {
//            mixin (traceEnterAndExit!(__FUNCTION__));
//            AsmInstruction node = allocator.make!AsmInstruction;
            final Marker m = enter_section_(builder);
            if (currentIs(tok("align"))) {
                advance(); // align
//                node.hasAlign = true;
                if (currentIsOneOf(tok("intLiteral"), tok("identifier"))) {//todo check identifier
                    advance();
//                    node.identifierOrIntegerOrOpcode = advance();
                } else {
                    error("Identifier or integer literal expected.");
                }
            } else if (currentIsOneOf(tok("identifier"), tok("in"), tok("out"), tok("int"))) {
//                node.identifierOrIntegerOrOpcode = advance();
                final Token t = advance();
                if (t.type == tok("identifier") && currentIs(tok(":"))) {
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
         *
         * $(GRAMMAR $(RULEDEF asmLogAndExp):
         *     $(RULE asmOrExp)
         *     $(RULE asmLogAndExp) $(LITERAL '&&') $(RULE asmOrExp)
         *     ;)
         */
        boolean parseAsmLogAndExp()
        {
//            mixin (traceEnterAndExit!(__FUNCTION__));
            final Marker m = enter_section_(builder);
            final boolean result = parseLeftAssocBinaryExpression("AsmLogAndExp", "AsmOrExp", tok("&&"));
            exit_section_(builder,m,ASM_LOG_AND_EXP,result);
            return result;
        }

        /**
         * Parses an AsmLogOrExp
         *
         * $(GRAMMAR $(RULEDEF asmLogOrExp):
         *       $(RULE asmLogAndExp)
         *     | $(RULE asmLogOrExp) '||' $(RULE asmLogAndExp)
         *     ;)
         */
        boolean parseAsmLogOrExp()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            final Marker m = enter_section_(builder);
            final boolean result = parseLeftAssocBinaryExpression("AsmLogOrExp", "AsmLogAndExp", tok("||"));
            exit_section_(builder,m,ASM_LOG_OR_EXP,result);
            return result;
        }

        /**
         * Parses an AsmMulExp
         *
         * $(GRAMMAR $(RULEDEF asmMulExp):
         *       $(RULE asmBrExp)
         *     | $(RULE asmMulExp) ($(LITERAL '*') | $(LITERAL '/') | $(LITERAL '%')) $(RULE asmBrExp)
         *     ;)
         */
        boolean parseAsmMulExp()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            final Marker m = enter_section_(builder);
            final boolean result = parseLeftAssocBinaryExpression("AsmMulExp", "AsmBrExp", tok("*"), tok("/"), tok("%"));
            exit_section_(builder,m,ASM_MUL_EXP,result);
            return result;
        }

        /**
         * Parses an AsmOrExp
         *
         * $(GRAMMAR $(RULEDEF asmOrExp):
         *       $(RULE asmXorExp)
         *     | $(RULE asmOrExp) $(LITERAL '|') $(RULE asmXorExp)
         *     ;)
         */
        boolean parseAsmOrExp()
        {
//            mixin (traceEnterAndExit!(__FUNCTION__));
            final Marker m = enter_section_(builder);
            final boolean result = parseLeftAssocBinaryExpression("AsmOrExp", "AsmXorExp", tok("|"));
            exit_section_(builder,m,ASM_OR_EXP,result);
            return result;
        }

        void trace(String ignored) {}

        /**
         * Parses an AsmPrimaryExp
         *
         * $(GRAMMAR $(RULEDEF asmPrimaryExp):
         *       $(LITERAL IntegerLiteral)
         *     | $(LITERAL FloatLiteral)
         *     | $(LITERAL StringLiteral)
         *     | $(RULE register)
         *     | $(RULE register : AsmExp)
         *     | $(RULE identifierChain)
         *     | $(LITERAL '$')
         *     ;)
         */
        boolean parseAsmPrimaryExp()
        {
//            mixin (traceEnterAndExit!(__FUNCTION__));
//            AsmPrimaryExp node = allocator.make!AsmPrimaryExp();
            final Marker m = enter_section_(builder);
            IdType i = current().type;
            if (i.equals(tok("doubleLiteral")) || i.equals(tok("floatLiteral")) || i.equals(tok("intLiteral")) || i.equals(tok("longLiteral")) || i.equals(tok("StringLiteral")) || i.equals(tok("$"))) {
                advance();
            } else if (i.equals(tok("identifier"))) {
                if ((Sets.newHashSet(Arrays.asList(REGISTER_NAMES))).contains(current().text)) {
                    trace("Found register");
                    if (!parseRegister()) {
                        cleanup(m);
                        return false;
                    }
                    if (current().type == tok(":")) {
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
            exit_section_(builder,m,ASM_PRIMARY_EXP,true);
            return true;
        }

        /**
         * Parses an AsmRelExp
         *
         * $(GRAMMAR $(RULEDEF asmRelExp):
         *       $(RULE asmShiftExp)
         *     | $(RULE asmRelExp) (($(LITERAL '<') | $(LITERAL '<=') | $(LITERAL '>') | $(LITERAL '>=')) $(RULE asmShiftExp))?
         *     ;)
         */
        boolean parseAsmRelExp()
        {
//            mixin (traceEnterAndExit!(__FUNCTION__));
            final Marker m = enter_section_(builder);
            final boolean result = parseLeftAssocBinaryExpression("AsmRelExp", "AsmShiftExp", tok("<"), tok("<="), tok(">"), tok(">="));
            exit_section_(builder,m,ASM_REL_EXP,result);
            return result;
        }

        /**
         * Parses an AsmShiftExp
         *
         * $(GRAMMAR $(RULEDEF asmShiftExp):
         *     $(RULE asmAddExp)
         *     $(RULE asmShiftExp) ($(LITERAL '<<') | $(LITERAL '>>') | $(LITERAL '>>>')) $(RULE asmAddExp)
         *     ;)
         */
        boolean parseAsmShiftExp()
        {
//            mixin (traceEnterAndExit!(__FUNCTION__));
            final Marker m = enter_section_(builder);
            final boolean result = parseLeftAssocBinaryExpression("AsmShiftExp", "AsmAddExp", tok("<<"), tok(">>"), tok(">>>"));
            exit_section_(builder,m,ASM_SHIFT_EXP,result);
            return result;
        }

        /**
         * Parses an AsmStatement
         *
         * $(GRAMMAR $(RULEDEF asmStatement):
         *     $(LITERAL 'asm') $(RULE functionAttributes)? $(LITERAL '{') $(RULE asmInstruction)+ $(LITERAL '}')
         *     ;)
         */
        boolean parseAsmStatement()
        {
//            mixin (traceEnterAndExit!(__FUNCTION__));
//            AsmStatement node = allocator.make!AsmStatement;
            final Marker m = enter_section_(builder);
            advance(); // asm
            while (isAttribute())
            {
                if (!parseFunctionAttribute(true))
                {
                    error("Function attribute or '{' expected");
                    //should there be a cleanup here or would that suppress the error?
                    cleanup(m);
                    return false;
                }
            }
            advance(); // {
//            final Marker statementList = enter_section_(builder);
            while (moreTokens() && !currentIs(tok("}")))
            {
                //todo the libdparse source does some stuff with the rollback allocator here that I don't understand
                parseAsmInstruction();
//
//                else
                expect(tok(";"));
            }
//            exit_section_(builder,statementList,);//todo needs type
            expect(tok("}"));
            exit_section_(builder,m,ASM_STATEMENT,true);
            return true;
        }

        /**
         * Parses an AsmTypePrefix
         *
         * Note that in the following grammar definition the first identifier must
         * be "near", "far", "word", "dword", or "qword". The second identifier must
         * be "ptr".
         *
         * $(GRAMMAR $(RULEDEF asmTypePrefix):
         *       $(LITERAL Identifier) $(LITERAL Identifier)?
         *     | $(LITERAL 'byte') $(LITERAL Identifier)?
         *     | $(LITERAL 'short') $(LITERAL Identifier)?
         *     | $(LITERAL 'int') $(LITERAL Identifier)?
         *     | $(LITERAL 'float') $(LITERAL Identifier)?
         *     | $(LITERAL 'double') $(LITERAL Identifier)?
         *     | $(LITERAL 'real') $(LITERAL Identifier)?
         *     ;)
         */
        boolean parseAsmTypePrefix()
        {
//            mixin (traceEnterAndExit!(__FUNCTION__));
            final Marker m = enter_section_(builder);
            IdType i = current().type;
            if (i.equals(tok("identifier")) || i.equals(tok("byte")) || i.equals(tok("short")) || i.equals(tok("int")) || i.equals(tok("float")) || i.equals(tok("double")) || i.equals(tok("real"))) {
                Token t = advance();
                if (t.type == tok("identifier"))
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
                            cleanup(m);//todo should there be a cleanup here
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
         *
         * $(GRAMMAR $(RULEDEF asmUnaExp):
         *       $(RULE asmTypePrefix) $(RULE asmExp)
         *     | $(LITERAL Identifier) $(RULE asmExp)
         *     | $(LITERAL '+') $(RULE asmUnaExp)
         *     | $(LITERAL '-') $(RULE asmUnaExp)
         *     | $(LITERAL '!') $(RULE asmUnaExp)
         *     | $(LITERAL '~') $(RULE asmUnaExp)
         *     | $(RULE asmPrimaryExp)
         *     ;)
         */
        boolean parseAsmUnaExp()
        {
//            mixin (traceEnterAndExit!(__FUNCTION__));
//            AsmUnaExp node = allocator.make!AsmUnaExp();
            final Marker m = enter_section_(builder);
            IdType i = current().type;
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
            exit_section_(builder,m,ASM_UNA_EXP,true);
            return true;
        }

        /**
         * Parses an AsmXorExp
         *
         * $(GRAMMAR $(RULEDEF asmXorExp):
         *       $(RULE asmAndExp)
         *     | $(RULE asmXorExp) $(LITERAL '^') $(RULE asmAndExp)
         *     ;)
         */
        boolean parseAsmXorExp()
        {
//            mixin (traceEnterAndExit!(__FUNCTION__));
            final Marker m = enter_section_(builder);
            final boolean result = parseLeftAssocBinaryExpression("AsmXorExp", "AsmAndExp", tok("^"));
            exit_section_(builder,m,ASM_XOR_EXP,result);
            return result;
        }

        /**
         * Parses an AssertExpression
         *
         * $(GRAMMAR $(RULEDEF assertExpression):
         *     $(LITERAL 'assert') $(LITERAL '$(LPAREN)') $(RULE assignExpression) ($(LITERAL ',') $(RULE assignExpression))? $(LITERAL ',')? $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseAssertExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AssertExpression,false);
//            node.line = current().line;
//            node.column = current().column;
            final Marker m = enter_section_(builder);
            advance(); // "assert"
            if(!tokenCheck("(")){return false;}
            if(!parseNodeQ("node.assertion", "AssignExpression")){
                cleanup(m);
                return false;
            }
            if (currentIs(tok(",")))
            {
                advance();
                if (currentIs(tok(")")))
                {
                    advance();
                    return true;
                }
                if(!parseNodeQ("node.message", "AssignExpression")){
                    cleanup(m);
                    return false;
                }
            }
            if (currentIs(tok(",")))
                advance();
            if(!tokenCheck(")")){
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,ASSERT_EXPRESSION,true);
            return true;
        }

        /**
         * Parses an AssignExpression
         *
         * $(GRAMMAR $(RULEDEF assignExpression):
         *     $(RULE ternaryExpression) ($(RULE assignOperator) $(RULE assignExpression))?
         *     ;
         *$(RULEDEF assignOperator):
         *       $(LITERAL '=')
         *     | $(LITERAL '>>>=')
         *     | $(LITERAL '>>=')
         *     | $(LITERAL '<<=')
         *     | $(LITERAL '+=')
         *     | $(LITERAL '-=')
         *     | $(LITERAL '*=')
         *     | $(LITERAL '%=')
         *     | $(LITERAL '&=')
         *     | $(LITERAL '/=')
         *     | $(LITERAL '|=')
         *     | $(LITERAL '^^=')
         *     | $(LITERAL '^=')
         *     | $(LITERAL '~=')
         *     ;)
         */
        boolean parseAssignExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            final Marker m = enter_section_(builder);
            if (!moreTokens())
            {
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
                if(!parseNodeQ("node.expression", "AssignExpression")){
                    cleanup(m);
                    return false;
                }
                return true;
            }
            return true;
        }

        /**
         * Parses an AssocArrayLiteral
         *
         * $(GRAMMAR $(RULEDEF assocArrayLiteral):
         *     $(LITERAL '[') $(RULE keyValuePairs) $(LITERAL ']')
         *     ;)
         */
        boolean parseAssocArrayLiteral()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            final Marker m = enter_section_(builder);
            final boolean result = simpleParse("AssocArrayLiteral", tok("["), "keyValuePairs|parseKeyValuePairs", tok("]"));
            exit_section_(builder,m,ASSOC_ARRAY_LITERAL,result);
            return result;
        }

        /**
         * moove and document these
         * @param nodeType
         * @param parts
         * @return
         */
        private boolean simpleParse(String nodeType, Object... parts) {
            //open marker for type
            final Marker m = enter_section_(builder);
            final boolean result = simpleParseItems(parts);
            exit_section_(builder,m,nodeTypeToIElementType(nodeType),result);
            return result;
        }

        boolean simpleParseItems(Object... items){
            for (Object item : items) {
                if(item instanceof IdType){
                    if(!simpleParseItemsSingle((IdType) item)){
                        return false;
                    }
                }
                else if(item instanceof String){
                    if(!simpleParseItemsSingle((String) item)){
                        return false;
                    }
                }
                else {
                    throw new IllegalArgumentException();
                }

            }
            return true;


        }

        boolean simpleParseItemsSingle(String item){
            final int i = item.indexOf("|");
            final String first = item.substring(0, i);//unneeded, libdparse uses for building it's ast, but we don't need to
            final String second = item.substring(i + 1);
            final String name = second.replace("parse", "");
            return parseName(name);

        }

        boolean simpleParseItemsSingle(IdType item){
            if (expect(item) == null) {
                return false;
            }
            return true;
        }

//        template simpleParseItems(items ...)
//        {
//            static if (items.length > 1)
//            enum simpleParseItems = simpleParseItem!(items[0]) ~ "\n"
//            ~ simpleParseItems!(items[1 .. $]);
//        else static if (items.length == 1)
//            enum simpleParseItems = simpleParseItem!(items[0]);
//        else
//            enum simpleParseItems = "";
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
         * Parses an AtAttribute
         *
         * $(GRAMMAR $(RULEDEF atAttribute):
         *       $(LITERAL '@') $(LITERAL Identifier)
         *     | $(LITERAL '@') $(LITERAL Identifier) $(LITERAL '$(LPAREN)') $(RULE argumentList)? $(LITERAL '$(RPAREN)')
         *     | $(LITERAL '@') $(LITERAL '$(LPAREN)') $(RULE argumentList) $(LITERAL '$(RPAREN)')
         *     | $(LITERAL '@') $(RULE TemplateInstance)
         *     ;)
         */
        boolean parseAtAttribute()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AtAttribute,false);
         start = expect(tok("@"));
            mixin (nullCheck("start"));
            if (!moreTokens)
            {
                error(""(", or identifier expected");
                return null;
            }
            node.startLocation = start.index;
            switch (current().type)
            {
                case tok("identifier"):
                    if (peekIs(tok("!")))
                    mixin(parseNodeQ("node.templateInstance", "TemplateInstance"));
            else
                    node.identifier = advance();
                    if (currentIs(tok("(")))
                {
                    advance(); // (
                    if (!currentIs(tok(")")))
                    mixin(parseNodeQ("node.argumentList", "ArgumentList"));
                    expect(tok(")"));
                }
                break;
                case tok("("):
                    advance();
                    mixin(parseNodeQ("node.argumentList", "ArgumentList"));
                    expect(tok(")"));
                    break;
                default:
                    error(""(", or identifier expected");
                    return null;
            }
            if (moreTokens) node.endLocation = current().index;
            return true;
        }

        /**
         * Parses an Attribute
         *
         * $(GRAMMAR $(RULEDEF attribute):
         *     | $(RULE pragmaExpression)
         *     | $(RULE alignAttribute)
         *     | $(RULE deprecated)
         *     | $(RULE atAttribute)
         *     | $(RULE linkageAttribute)
         *     | $(LITERAL 'export')
         *     | $(LITERAL 'package') ($(LITERAL "(") $(RULE identifierChain) $(LITERAL ")"))?
         *     | $(LITERAL 'private')
         *     | $(LITERAL 'protected')
         *     | $(LITERAL 'public')
         *     | $(LITERAL 'static')
         *     | $(LITERAL 'extern')
         *     | $(LITERAL 'abstract')
         *     | $(LITERAL 'final')
         *     | $(LITERAL 'override')
         *     | $(LITERAL 'synchronized')
         *     | $(LITERAL 'auto')
         *     | $(LITERAL 'scope')
         *     | $(LITERAL '')
         *     | $(LITERAL 'immutable')
         *     | $(LITERAL 'inout')
         *     | $(LITERAL 'shared')
         *     | $(LITERAL '__gshared')
         *     | $(LITERAL '')
         *     | $(LITERAL '')
         *     | $(LITERAL 'ref')
         *     ;)
         */
        boolean parseAttribute()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Attribute,false);
            switch (current().type)
            {
                case tok("pragma"):
                    mixin(parseNodeQ("node.pragmaExpression", "PragmaExpression"));
                    break;
                case tok("deprecated"):
                    mixin(parseNodeQ("node.deprecated_", "Deprecated"));
                    break;
                case tok("align"):
                    mixin(parseNodeQ("node.alignAttribute", "AlignAttribute"));
                    break;
                case tok("@"):
                    mixin(parseNodeQ("node.atAttribute", "AtAttribute"));
                    break;
                case tok("package"):
                    node.attribute = advance();
                    if (currentIs(tok("(")))
                {
                    expect(tok("("));
                    mixin(parseNodeQ("node.identifierChain", "IdentifierChain"));
                    expect(tok(")"));
                }
                break;
                case tok("extern"):
                    if (peekIs(tok("(")))
                {
                    mixin(parseNodeQ("node.linkageAttribute", "LinkageAttribute"));
                    break;
                }
            else
                goto case;
                case tok("private"):
                case tok("protected"):
                case tok("public"):
                case tok("export"):
                case tok("static"):
                case tok("abstract"):
                case tok("final"):
                case tok("override"):
                case tok("synchronized"):
                case tok("auto"):
                case tok("scope"):
                case tok(""):
                case tok("immutable"):
                case tok("inout"):
                case tok("shared"):
                case tok("__gshared"):
                case tok(""):
                case tok(""):
                case tok("ref"):
                    node.attribute = advance();
                    break;
                default:
                    return null;
            }
            return true;
        }

        /**
         * Parses an AttributeDeclaration
         *
         * $(GRAMMAR $(RULEDEF attributeDeclaration):
         *     $(RULE attribute) $(LITERAL ':')
         *     ;)
         */
        boolean parseAttributeDeclaration()//(Attribute attribute = null)
        {
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AttributeDeclaration,false);
//            node.line = current().line;
            node.attribute = attribute == null ? parseAttribute() : attribute;
            expect(tok(":"));
            return true;
        }

        /**
         * Parses an AutoDeclaration
         *
         * $(GRAMMAR $(RULEDEF autoDeclaration):
         *     $(RULE storageClass)+  $(RULE autoDeclarationPart) ($(LITERAL ',') $(RULE autoDeclarationPart))* $(LITERAL ';')
         *     ;)
         */
        boolean parseAutoDeclaration()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AutoDeclaration,false);
//            node.comment = comment;
//            comment = null;
            StackBuffer storageClasses;
            while (isStorageClass())
                if (!storageClasses.put(parseStorageClass()))
                    return null;
            ownArray(node.storageClasses, storageClasses);
            StackBuffer parts;
            do
            {
                if (!parts.put(parseAutoDeclarationPart()))
                    return null;
                if (currentIs(tok(",")))
                advance();
            else
                break;
            } while (moreTokens());
            ownArray(node.parts, parts);
            return attachCommentFromSemicolon(node);
        }

        /**
         * Parses an AutoDeclarationPart
         *
         * $(GRAMMAR $(RULEDEF autoDeclarationPart):
         *     $(LITERAL Identifier) $(RULE templateParameters)? $(LITERAL '=') $(RULE initializer)
         *     ;)
         */
        boolean parseAutoDeclarationPart()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            auto part = allocator.make!AutoDeclarationPart;
            auto i = expect(tok("identifier"));
            if (i == null)
            return null;
            part.identifier = *i;
            if (currentIs(tok("(")))
            mixin(parseNodeQ("part.templateParameters", "TemplateParameters"));
            if(!tokenCheck("=")){return false;}
            mixin(parseNodeQ("part.initializer", "Initializer"));
            return part;
        }

        /**
         * Parses a BlockStatement
         *
         * $(GRAMMAR $(RULEDEF blockStatement):
         *     $(LITERAL '{') $(RULE declarationsAndStatements)? $(LITERAL '}')
         *     ;)
         */
        boolean parseBlockStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.BlockStatement,false);
         openBrace = expect(tok("{"));
            mixin (nullCheck("openBrace"));
            node.startLocation = openBrace.index;
            if (!currentIs(tok("}")))
            {
                mixin(parseNodeQ("node.declarationsAndStatements", "DeclarationsAndStatements"));
            }
         closeBrace = expect(tok("}"));
            if (closeBrace != null)
            node.endLocation = closeBrace.index;
        else
            {
                trace("Could not find end of block statement.");
                node.endLocation = Number.max;
            }

            return true;
        }

        /**
         * Parses a BodyStatement
         *
         * $(GRAMMAR $(RULEDEF bodyStatement):
         *     $(LITERAL 'body') $(RULE blockStatement)
         *     ;)
         */
        boolean parseBodyStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            mixin (simpleParse!(BodyStatement, tok("body"),
            "blockStatement|parseBlockStatement"));
        }

        /**
         * Parses a BreakStatement
         *
         * $(GRAMMAR $(RULEDEF breakStatement):
         *     $(LITERAL 'break') $(LITERAL Identifier)? $(LITERAL ';')
         *     ;)
         */
        boolean parseBreakStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            expect(tok("break"));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.BreakStatement,false);
            switch (current().type)
            {
                case tok("identifier"):
                    node.label = advance();
                    if(!tokenCheck(";")){return false;}
                    break;
                case tok(";"):
                    advance();
                    break;
                default:
                    error("Identifier or semicolon expected following \"break\"");
                    return null;
            }
            return true;
        }

        /**
         * Parses a BaseClass
         *
         * $(GRAMMAR $(RULEDEF baseClass):
         *     $(RULE type2)
         *     ;)
         */
        boolean parseBaseClass()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.BaseClass,false);
            if (current().type.isProtection())
            {
                warn("Use of base class protection == deprecated.");
                advance();
            }
            if ((node.type2 = parseType2()) == null)
            {
                return null;
            }
            return true;
        }

        /**
         * Parses a BaseClassList
         *
         * $(GRAMMAR $(RULEDEF baseClassList):
         *     $(RULE baseClass) ($(LITERAL ',') $(RULE baseClass))*
         *     ;)
         */
        boolean parseBaseClassList()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            return parseCommaSeparatedRule(BaseClassList, BaseClass);
        }

        /**
         * Parses an BuiltinType
         *
         * $(GRAMMAR $(RULEDEF builtinType):
         *      $(LITERAL 'boolean')
         *    | $(LITERAL 'byte')
         *    | $(LITERAL 'ubyte')
         *    | $(LITERAL 'short')
         *    | $(LITERAL 'ushort')
         *    | $(LITERAL 'int')
         *    | $(LITERAL 'int')
         *    | $(LITERAL 'long')
         *    | $(LITERAL 'ulong')
         *    | $(LITERAL 'char')
         *    | $(LITERAL 'wchar')
         *    | $(LITERAL 'dchar')
         *    | $(LITERAL 'float')
         *    | $(LITERAL 'double')
         *    | $(LITERAL 'real')
         *    | $(LITERAL 'ifloat')
         *    | $(LITERAL 'idouble')
         *    | $(LITERAL 'ireal')
         *    | $(LITERAL 'cfloat')
         *    | $(LITERAL 'cdouble')
         *    | $(LITERAL 'creal')
         *    | $(LITERAL 'void')
         *    ;)
         */
        Token.boolean parseBuiltinType()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            return advance().type;
        }

        /**
         * Parses a CaseRangeStatement
         *
         * $(GRAMMAR $(RULEDEF caseRangeStatement):
         *     $(LITERAL 'case') $(RULE assignExpression) $(LITERAL ':') $(LITERAL '...') $(LITERAL 'case') $(RULE assignExpression) $(LITERAL ':') $(RULE declarationsAndStatements)
         *     ;)
         */
        CaseRangeStatement parseCaseRangeStatement(ExpressionNode low)
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CaseRangeStatement,false);
            assert (low != null);
            node.low = low;
            if(!tokenCheck(":")){return false;}
            if(!tokenCheck("..")){return false;}
            expect(tok("case"));
            mixin(parseNodeQ("node.high", "AssignExpression"));
         colon = expect(tok(":"));
            if (colon == null)
            return null;
            node.colonLocation = colon.index;
            mixin(parseNodeQ("node.declarationsAndStatements", "DeclarationsAndStatements"));
            return true;
        }

        /**
         * Parses an CaseStatement
         *
         * $(GRAMMAR $(RULEDEF caseStatement):
         *     $(LITERAL 'case') $(RULE argumentList) $(LITERAL ':') $(RULE declarationsAndStatements)
         *     ;)
         */
        CaseStatement parseCaseStatement(ArgumentList argumentList = null)
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CaseStatement,false);
            node.argumentList = argumentList;
         colon = expect(tok(":"));
            if (colon == null)
            return null;
            node.colonLocation = colon.index;
            mixin (nullCheck("node.declarationsAndStatements = parseDeclarationsAndStatements(false)"));
            return true;
        }

        /**
         * Parses a CastExpression
         *
         * $(GRAMMAR $(RULEDEF castExpression):
         *     $(LITERAL 'cast') $(LITERAL '$(LPAREN)') ($(RULE type) | $(RULE castQualifier))? $(LITERAL '$(RPAREN)') $(RULE unaryExpression)
         *     ;)
         */
        boolean parseCastExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CastExpression,false);
            expect(tok("cast"));
            if(!tokenCheck("(")){return false;}
            if (!currentIs(tok(")")))
            {
                if (isCastQualifier())
                    mixin(parseNodeQ("node.castQualifier", "CastQualifier"));
            else
                mixin(parseNodeQ("node.type", "Type"));
            }
            if(!tokenCheck(")")){return false;}
            mixin(parseNodeQ("node.unaryExpression", "UnaryExpression"));
            return true;
        }

        /**
         * Parses a CastQualifier
         *
         * $(GRAMMAR $(RULEDEF castQualifier):
         *      $(LITERAL '')
         *    | $(LITERAL '') $(LITERAL 'shared')
         *    | $(LITERAL 'immutable')
         *    | $(LITERAL 'inout')
         *    | $(LITERAL 'inout') $(LITERAL 'shared')
         *    | $(LITERAL 'shared')
         *    | $(LITERAL 'shared') $(LITERAL '')
         *    | $(LITERAL 'shared') $(LITERAL 'inout')
         *    ;)
         */
        boolean parseCastQualifier()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CastQualifier,false);
            switch (current().type)
            {
                case tok("inout"):
                case tok(""):
                    node.first = advance();
                    if (currentIs(tok("shared")))
                    node.second = advance();
                    break;
                case tok("shared"):
                    node.first = advance();
                    if (currentIsOneOf(tok(""), tok("inout")))
                    node.second = advance();
                    break;
                case tok("immutable"):
                    node.first = advance();
                    break;
                default:
                    error(", immutable, inout, or shared expected");
                    return null;
            }
            return true;
        }

        /**
         * Parses a Catch
         *
         * $(GRAMMAR $(RULEDEF catch):
         *     $(LITERAL 'catch') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL Identifier)? $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement)
         *     ;)
         */
        boolean parseCatch()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Catch,false);
            expect(tok("catch"));
            if(!tokenCheck("(")){return false;}
            mixin(parseNodeQ("node.type", "Type"));
            if (currentIs(tok("identifier")))
            node.identifier = advance();
            if(!tokenCheck(")")){return false;}
            mixin(parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement"));
            return true;
        }

        /**
         * Parses a Catches
         *
         * $(GRAMMAR $(RULEDEF catches):
         *       $(RULE catch)+
         *     | $(RULE catch)* $(RULE lastCatch)
         *     ;)
         */
        boolean parseCatches()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Catches,false);
            StackBuffer catches;
            while (moreTokens())
            {
                if (!currentIs(tok("catch")))
                break;
                if (peekIs(tok("(")))
                {
                    if (!catches.put(parseCatch()))
                        return null;
                }
            else
                {
                    mixin(parseNodeQ("node.lastCatch", "LastCatch"));
                    break;
                }
            }
            ownArray(node.catches, catches);
            return true;
        }

        /**
         * Parses a ClassDeclaration
         *
         * $(GRAMMAR $(RULEDEF classDeclaration):
         *       $(LITERAL 'class') $(LITERAL Identifier) $(LITERAL ';')
         *     | $(LITERAL 'class') $(LITERAL Identifier) ($(LITERAL ':') $(RULE baseClassList))? $(RULE structBody)
         *     | $(LITERAL 'class') $(LITERAL Identifier) $(RULE templateParameters) $(RULE raint)? ($(RULE structBody) | $(LITERAL ';'))
         *     | $(LITERAL 'class') $(LITERAL Identifier) $(RULE templateParameters) $(RULE raint)? ($(LITERAL ':') $(RULE baseClassList))? $(RULE structBody)
         *     | $(LITERAL 'class') $(LITERAL Identifier) $(RULE templateParameters) ($(LITERAL ':') $(RULE baseClassList))? $(RULE raint)? $(RULE structBody)
         *     ;)
         */
        boolean parseClassDeclaration()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ClassDeclaration,false);
            expect(tok("class"));
            return parseInterfaceOrClass(node);
        }

        /**
         * Parses a CmpExpression
         *
         * $(GRAMMAR $(RULEDEF cmpExpression):
         *       $(RULE shiftExpression)
         *     | $(RULE equalExpression)
         *     | $(RULE identityExpression)
         *     | $(RULE relExpression)
         *     | $(RULE inExpression)
         *     ;)
         */
        boolean parseCmpExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            auto shift = parseShiftExpression();
            if (shift == null)
            return null;
            if (!moreTokens())
                return shift;
            switch (current().type)
            {
                case tok("is"):
//                    Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CmpExpression,false);
                    mixin (nullCheck("node.identityExpression = parseIdentityExpression(shift)"));
                    return true;
                case tok("in"):
//                    Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CmpExpression,false);
                    mixin (nullCheck("node.inExpression = parseInExpression(shift)"));
                    return true;
                case tok("!"):
//                    Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CmpExpression,false);
                    if (peekIs(tok("is")))
                    mixin (nullCheck("node.identityExpression = parseIdentityExpression(shift)"));
            else if (peekIs(tok("in")))
                    mixin (nullCheck("node.inExpression = parseInExpression(shift)"));
                    return true;
                case tok("<"):
                case tok("<="):
                case tok(">"):
                case tok(">="):
                case tok("!<>="):
                case tok("!<>"):
                case tok("<>"):
                case tok("<>="):
                case tok("!>"):
                case tok("!>="):
                case tok("!<"):
                case tok("!<="):
//                    Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CmpExpression,false);
                    mixin (nullCheck("node.relExpression = parseRelExpression(shift)"));
                    return true;
                case tok("=="):
                case tok("!="):
//                    Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CmpExpression,false);
                    mixin (nullCheck("node.equalExpression = parseEqualExpression(shift)"));
                    return true;
                default:
                    return shift;
            }
        }

        /**
         * Parses a CompileCondition
         *
         * $(GRAMMAR $(RULEDEF compileCondition):
         *       $(RULE versionCondition)
         *     | $(RULE debugCondition)
         *     | $(RULE staticIfCondition)
         *     ;)
         */
        boolean parseCompileCondition()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CompileCondition,false);
            switch (current().type)
            {
                case tok("version"):
                    mixin(parseNodeQ("node.versionCondition", "VersionCondition"));
                    break;
                case tok("debug"):
                    mixin(parseNodeQ("node.debugCondition", "DebugCondition"));
                    break;
                case tok("static"):
                    mixin(parseNodeQ("node.staticIfCondition", "StaticIfCondition"));
                    break;
                default:
                    error("\"version\", \"debug\", or \"static\" expected");
                    return null;
            }
            return true;
        }

        /**
         * Parses a ConditionalDeclaration
         *
         * $(GRAMMAR $(RULEDEF conditionalDeclaration):
         *       $(RULE compileCondition) $(RULE declaration)
         *     | $(RULE compileCondition) $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
         *     | $(RULE compileCondition) $(LITERAL ':') $(RULE declaration)+
         *     | $(RULE compileCondition) $(RULE declaration) $(LITERAL 'else') $(LITERAL ':') $(RULE declaration)*
         *     | $(RULE compileCondition) $(RULE declaration) $(LITERAL 'else') $(RULE declaration)
         *     | $(RULE compileCondition) $(RULE declaration) $(LITERAL 'else') $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
         *     | $(RULE compileCondition) $(LITERAL '{') $(RULE declaration)* $(LITERAL '}') $(LITERAL 'else') $(RULE declaration)
         *     | $(RULE compileCondition) $(LITERAL '{') $(RULE declaration)* $(LITERAL '}') $(LITERAL 'else') $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
         *     | $(RULE compileCondition) $(LITERAL '{') $(RULE declaration)* $(LITERAL '}') $(LITERAL 'else') $(LITERAL ':') $(RULE declaration)*
         *     | $(RULE compileCondition) $(LITERAL ':') $(RULE declaration)+ $(LITERAL 'else') $(RULE declaration)
         *     | $(RULE compileCondition) $(LITERAL ':') $(RULE declaration)+ $(LITERAL 'else') $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
         *     | $(RULE compileCondition) $(LITERAL ':') $(RULE declaration)+ $(LITERAL 'else') $(LITERAL ':') $(RULE declaration)*
         *     ;)
         */
        ConditionalDeclaration parseConditionalDeclaration(boolean strict)
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ConditionalDeclaration,false);
            mixin(parseNodeQ("node.compileCondition", "CompileCondition"));

            StackBuffer trueDeclarations;
            if (currentIs(tok(":")) || currentIs(tok("{")))
            {
                boolean brace = advance() == tok("{");
                while (moreTokens() && !currentIs(tok("}")) && !currentIs(tok("else")))
                {
                    Bookmark b = setBookmark();
                    c = allocator.setCheckpoint();
                    if (trueDeclarations.put(parseDeclaration(strict, true)))
                        abandonBookmark(b);
                    else
                    {
                        goToBookmark(b);
                        allocator.rollback(c);
                        return null;
                    }
                }
                if (brace)
                    if(!tokenCheck("}")){return false;}
            }
        else if (!trueDeclarations.put(parseDeclaration(strict, true)))
            return null;

            ownArray(node.trueDeclarations, trueDeclarations);

            if (currentIs(tok("else")))
            {
                node.hasElse = true;
                advance();
            }
        else
            return true;

            StackBuffer falseDeclarations;
            if (currentIs(tok(":")) || currentIs(tok("{")))
            {
                boolean brace = currentIs(tok("{"));
                advance();
                while (moreTokens() && !currentIs(tok("}")))
                if (!falseDeclarations.put(parseDeclaration(strict, true)))
                    return null;
                if (brace)
                    if(!tokenCheck("}")){return false;}
            }
        else
            {
                if (!falseDeclarations.put(parseDeclaration(strict, true)))
                    return null;
            }
            ownArray(node.falseDeclarations, falseDeclarations);
            return true;
        }

        /**
         * Parses a ConditionalStatement
         *
         * $(GRAMMAR $(RULEDEF conditionalStatement):
         *     $(RULE compileCondition) $(RULE declarationOrStatement) ($(LITERAL 'else') $(RULE declarationOrStatement))?
         *     ;)
         */
        boolean parseConditionalStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ConditionalStatement,false);
            mixin(parseNodeQ("node.compileCondition", "CompileCondition"));
            mixin(parseNodeQ("node.trueStatement", "DeclarationOrStatement"));
            if (currentIs(tok("else")))
            {
                advance();
                mixin(parseNodeQ("node.falseStatement", "DeclarationOrStatement"));
            }
            return true;
        }

        /**
         * Parses a Constraint
         *
         * $(GRAMMAR $(RULEDEF raint):
         *     $(LITERAL 'if') $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseConstraint()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Constraint,false);
            Token ifToken = expect(tok("if"));
            mixin (nullCheck("ifToken"));
            node.location = ifToken.index;
            if(!tokenCheck("(")){return false;}
            mixin(parseNodeQ("node.expression", "Expression"));
            if(!tokenCheck(")")){return false;}
            return true;
        }

        /**
         * Parses a Constructor
         *
         * $(GRAMMAR $(RULEDEF ructor):
         *       $(LITERAL 'this') $(RULE templateParameters)? $(RULE parameters) $(RULE memberFunctionAttribute)* $(RULE raint)? ($(RULE functionBody) | $(LITERAL ';'))
         *     ;)
         */
        boolean parseConstructor()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Constructor node = allocator.make!Constructor;
            node.comment = comment;
            comment = null;
         t = expect(tok("this"));
            mixin (nullCheck("t"));
            node.location = t.index;
            node.line = t.line;
            node.column = t.column;
         p = peekPastParens();
            boolean isTemplate = false;
            if (p != null && p.type == tok("("))
            {
                isTemplate = true;
                mixin(parseNodeQ("node.templateParameters", "TemplateParameters"));
            }
            mixin(parseNodeQ("node.parameters", "Parameters"));

            StackBuffer memberFunctionAttributes;
            while (moreTokens() && currentIsMemberFunctionAttribute())
                if (!memberFunctionAttributes.put(parseMemberFunctionAttribute()))
                    return null;
            ownArray(node.memberFunctionAttributes, memberFunctionAttributes);

            if (isTemplate && currentIs(tok("if")))
            mixin(parseNodeQ("node.raint", "Constraint"));
            if (currentIs(tok(";")))
            advance();
        else
            mixin(parseNodeQ("node.functionBody", "FunctionBody"));
            return true;
        }

        /**
         * Parses an ContinueStatement
         *
         * $(GRAMMAR $(RULEDEF continueStatement):
         *     $(LITERAL 'continue') $(LITERAL Identifier)? $(LITERAL ';')
         *     ;)
         */
        boolean parseContinueStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ContinueStatement,false);
            if(!tokenCheck("continue")){return false;}
            switch (current().type)
            {
                case tok("identifier"):
                    node.label = advance();
                    if(!tokenCheck(";")){return false;}
                    break;
                case tok(";"):
                    advance();
                    break;
                default:
                    error("Identifier or semicolon expected following "continue"");
                    return null;
            }
            return true;
        }

        /**
         * Parses a DebugCondition
         *
         * $(GRAMMAR $(RULEDEF debugCondition):
         *     $(LITERAL 'debug') ($(LITERAL '$(LPAREN)') ($(LITERAL IntegerLiteral) | $(LITERAL Identifier)) $(LITERAL '$(RPAREN)'))?
         *     ;)
         */
        boolean parseDebugCondition()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DebugCondition,false);

         d = expect(tok("debug"));
            mixin (nullCheck("d"));
            node.debugIndex = d.index;

            if (currentIs(tok("(")))
            {
                advance();
                if (currentIsOneOf(tok("intLiteral"), tok("identifier")))
                node.identifierOrInteger = advance();
            else
                {
                    error("Integer literal or identifier expected");
                    return null;
                }
                if(!tokenCheck(")")){return false;}
            }
            return true;
        }

        /**
         * Parses a DebugSpecification
         *
         * $(GRAMMAR $(RULEDEF debugSpecification):
         *     $(LITERAL 'debug') $(LITERAL '=') ($(LITERAL Identifier) | $(LITERAL IntegerLiteral)) $(LITERAL ';')
         *     ;)
         */
        boolean parseDebugSpecification()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DebugSpecification,false);
            if(!tokenCheck("debug")){return false;}
            if(!tokenCheck("=")){return false;}
            if (currentIsOneOf(tok("identifier"), tok("intLiteral")))
            node.identifierOrInteger = advance();
        else
            {
                error("Integer literal or identifier expected");
                return null;
            }
            if(!tokenCheck(";")){return false;}
            return true;
        }

        /**
         * Parses a Declaration
         *
         * Params: strict = if true, do not return partial AST nodes on errors.
         *
         * $(GRAMMAR $(RULEDEF declaration):
         *       $(RULE attribute)* $(RULE declaration2)
         *     | $(RULE attribute)+ $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
         *     ;
         * $(RULEDEF declaration2):
         *       $(RULE aliasDeclaration)
         *     | $(RULE aliasThisDeclaration)
         *     | $(RULE anonymousEnumDeclaration)
         *     | $(RULE attributeDeclaration)
         *     | $(RULE classDeclaration)
         *     | $(RULE conditionalDeclaration)
         *     | $(RULE ructor)
         *     | $(RULE debugSpecification)
         *     | $(RULE destructor)
         *     | $(RULE enumDeclaration)
         *     | $(RULE eponymousTemplateDeclaration)
         *     | $(RULE functionDeclaration)
         *     | $(RULE importDeclaration)
         *     | $(RULE interfaceDeclaration)
         *     | $(RULE invariant)
         *     | $(RULE mixinDeclaration)
         *     | $(RULE mixinTemplateDeclaration)
         *     | $(RULE pragmaDeclaration)
         *     | $(RULE sharedStaticConstructor)
         *     | $(RULE sharedStaticDestructor)
         *     | $(RULE staticAssertDeclaration)
         *     | $(RULE staticConstructor)
         *     | $(RULE staticDestructor)
         *     | $(RULE structDeclaration)
         *     | $(RULE templateDeclaration)
         *     | $(RULE unionDeclaration)
         *     | $(RULE unittest)
         *     | $(RULE variableDeclaration)
         *     | $(RULE versionSpecification)
         *     ;)
         */
        boolean parseDeclaration()
        {
            final boolean strict = false;
            final boolean mustBeDeclaration = false;
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Declaration,false);
            if (!moreTokens)
            {
                error("declaration expected instead of EOF");
                return null;
            }
//            if (current().comment != null)
//            comment = current().comment;
            Number autoStorageClassStart = Number.max;
            DecType isAuto;
            StackBuffer attributes;
            do
            {
                isAuto = isAutoDeclaration(autoStorageClassStart);
                if (isAuto != DecType.other && index == autoStorageClassStart)
                    break;
                if (!isAttribute())
                    break;
                c = allocator.setCheckpoint();
                Attribute attr = parseAttribute();
                if (attr == null)
                {
                    allocator.rollback(c);
                    break;
                }
                if (currentIs(tok(":")))
                {
                    node.attributeDeclaration = parseAttributeDeclaration(attr);
                    mixin(nullCheck("node.attributeDeclaration"));
                    ownArray(node.attributes, attributes);
                    return true;
                }
            else
                attributes.put(attr);
            } while (moreTokens());
            ownArray(node.attributes, attributes);

            if (!moreTokens)
            {
                error("declaration expected instead of EOF");
                return null;
            }

            if (isAuto == DecType.autoVar)
            {
                mixin(nullCheck("node.variableDeclaration = parseVariableDeclaration(null, true)"));
                return true;
            }
            else if (isAuto == DecType.autoFun)
            {
                mixin(nullCheck("node.functionDeclaration = parseFunctionDeclaration(null, true)"));
                return true;
            }

            switch (current().type)
            {
                case tok("asm"):
                case tok("break"):
                case tok("case"):
                case tok("continue"):
                case tok("default"):
                case tok("do"):
                case tok("for"):
                case tok("foreach"):
                case tok("foreach_reverse"):
                case tok("goto"):
                case tok("if"):
                case tok("return"):
                case tok("switch"):
                case tok("throw"):
                case tok("try"):
                case tok("while"):
                case tok("assert"):
            goto default;
                case tok(";"):
                    // http://d.magic.com/issues/show_bug.cgi?id=4559
                    warn("Empty declaration");
                    advance();
                    break;
                case tok("{"):
                    if (node.attributes.empty)
                    {
                        error("declaration expected instead of '{'");
                        return null;
                    }
                    advance();
                    StackBuffer declarations;
                    while (moreTokens() && !currentIs(tok("}")))
                {
                    auto c = allocator.setCheckpoint();
                    if (!declarations.put(parseDeclaration(strict)))
                    {
                        allocator.rollback(c);
                        return null;
                    }
                }
                ownArray(node.declarations, declarations);
                if(!tokenCheck("}")){return false;}
                break;
                case tok("alias"):
                    if (startsWith(tok("alias"), tok("identifier"), tok("this")))
                    mixin(parseNodeQ("node.aliasThisDeclaration", "AliasThisDeclaration"));
            else
                    mixin(parseNodeQ("node.aliasDeclaration", "AliasDeclaration"));
                    break;
                case tok("class"):
                    mixin(parseNodeQ("node.classDeclaration", "ClassDeclaration"));
                    break;
                case tok("this"):
                    if (!mustBeDeclaration && peekIs(tok("(")))
                {
                    // Do not parse as a declaration if we could parse as a function call.
                    ++index;
                 past = peekPastParens();
                    --index;
                    if (past != null && past.type == tok(";"))
                    return null;
                }
                if (startsWith(tok("this"), tok("("), tok("this"), tok(")")))
                mixin(parseNodeQ("node.postblit", "Postblit"));
            else
                mixin(parseNodeQ("node.ructor", "Constructor"));
                break;
                case tok("~"):
                    mixin(parseNodeQ("node.destructor", "Destructor"));
                    break;
                case tok("enum"):
                    Bookmark b = setBookmark();
                    advance(); // enum
                    if (currentIsOneOf(tok(":"), tok("{")))
                {
                    goToBookmark(b);
                    mixin(parseNodeQ("node.anonymousEnumDeclaration", "AnonymousEnumDeclaration"));
                }
            else if (currentIs(tok("identifier")))
                {
                    advance();
                    if (currentIs(tok("(")))
                    {
                        skipParens(); // ()
                        if (currentIs(tok("(")))
                        skipParens();
                        if (!currentIs(tok("=")))
                        {
                            goToBookmark(b);
                            node.functionDeclaration = parseFunctionDeclaration(null, true, node.attributes);
                            mixin (nullCheck("node.functionDeclaration"));
                        }
                    else
                        {
                            goToBookmark(b);
                            mixin(parseNodeQ("node.eponymousTemplateDeclaration", "EponymousTemplateDeclaration"));
                        }
                    }
                else if (currentIsOneOf(tok(":"), tok("{"), tok(";")))
                    {
                        goToBookmark(b);
                        mixin(parseNodeQ("node.enumDeclaration", "EnumDeclaration"));
                    }
                else
                    {
                        boolean eq = currentIs(tok("="));
                        goToBookmark(b);
                        mixin (nullCheck("node.variableDeclaration = parseVariableDeclaration(null, eq)"));
                    }
                }
            else
                {
                    boolean s = isStorageClass();
                    goToBookmark(b);
                    mixin (nullCheck("node.variableDeclaration = parseVariableDeclaration(null, s)"));
                }
                break;
                case tok("import"):
                    mixin(parseNodeQ("node.importDeclaration", "ImportDeclaration"));
                    break;
                case tok("interface"):
                    mixin(parseNodeQ("node.interfaceDeclaration", "InterfaceDeclaration"));
                    break;
                case tok("mixin"):
                    if (peekIs(tok("template")))
                    mixin(parseNodeQ("node.mixinTemplateDeclaration", "MixinTemplateDeclaration"));
            else
                {
                    b = setBookmark();
                    advance();
                    if (currentIs(tok("(")))
                    {
                     t = peekPastParens();
                        if (t != null && t.type == tok(";"))
                        {
                            goToBookmark(b);
                            mixin(parseNodeQ("node.mixinDeclaration", "MixinDeclaration"));
                        }
                    else
                        {
                            goToBookmark(b);
                            error("Declaration expected");
                            return null;
                        }
                    }
                else
                    {
                        goToBookmark(b);
                        mixin(parseNodeQ("node.mixinDeclaration", "MixinDeclaration"));
                    }
                }
                break;
                case tok("pragma"):
                    mixin(parseNodeQ("node.pragmaDeclaration", "PragmaDeclaration"));
                    break;
                case tok("shared"):
                    if (startsWith(tok("shared"), tok("static"), tok("this")))
                    mixin(parseNodeQ("node.sharedStaticConstructor", "SharedStaticConstructor"));
            else if (startsWith(tok("shared"), tok("static"), tok("~")))
                    mixin(parseNodeQ("node.sharedStaticDestructor", "SharedStaticDestructor"));
            else
                goto type;
                    break;
                case tok("static"):
                    if (peekIs(tok("this")))
                    mixin(parseNodeQ("node.staticConstructor", "StaticConstructor"));
            else if (peekIs(tok("~")))
                    mixin(parseNodeQ("node.staticDestructor", "StaticDestructor"));
            else if (peekIs(tok("if")))
                    mixin (nullCheck("node.conditionalDeclaration = parseConditionalDeclaration(strict)"));
            else if (peekIs(tok("assert")))
                    mixin(parseNodeQ("node.staticAssertDeclaration", "StaticAssertDeclaration"));
            else
                goto type;
                    break;
                case tok("struct"):
                    mixin(parseNodeQ("node.structDeclaration", "StructDeclaration"));
                    break;
                case tok("template"):
                    mixin(parseNodeQ("node.templateDeclaration", "TemplateDeclaration"));
                    break;
                case tok("union"):
                    mixin(parseNodeQ("node.unionDeclaration", "UnionDeclaration"));
                    break;
                case tok("invariant"):
                    mixin(parseNodeQ("node.invariant_", "Invariant"));
                    break;
                case tok("unittest"):
                    mixin(parseNodeQ("node.unittest_", "Unittest"));
                    break;
                case tok("identifier"):
                case tok("."):
                case tok(""):
                case tok("immutable"):
                case tok("inout"):
                case tok("scope"):
                case tok("typeof"):
                case tok("__vector"):
                    foreach (B; BasicTypes) { case B: }
                type:
                Type t = parseType();
                if (t == null || !currentIs(tok("identifier")))
                return null;
                if (peekIs(tok("(")))
                mixin (nullCheck("node.functionDeclaration = parseFunctionDeclaration(t, false)"));
            else
                mixin (nullCheck("node.variableDeclaration = parseVariableDeclaration(t, false)"));
                break;
                case tok("version"):
                    if (peekIs(tok("(")))
                    mixin (nullCheck("node.conditionalDeclaration = parseConditionalDeclaration(strict)"));
            else if (peekIs(tok("=")))
                    mixin(parseNodeQ("node.versionSpecification", "VersionSpecification"));
            else
                {
                    error(""=" or "(" expected following "version"");
                    return null;
                }
                break;
                case tok("debug"):
                    if (peekIs(tok("=")))
                    mixin(parseNodeQ("node.debugSpecification", "DebugSpecification"));
            else
                    mixin (nullCheck("node.conditionalDeclaration = parseConditionalDeclaration(strict)"));
                    break;
                default:
                    error("Declaration expected");
                    return null;
            }
            return true;
        }

        /**
         * Parses DeclarationsAndStatements
         *
         * $(GRAMMAR $(RULEDEF declarationsAndStatements):
         *     $(RULE declarationOrStatement)+
         *     ;)
         */
        boolean parseDeclarationsAndStatements()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            final boolean includeCases = true;
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DeclarationsAndStatements,false);
            StackBuffer declarationsAndStatements;
            while (!currentIsOneOf(tok("}"), tok("else")) && moreTokens() && suppressedErrorCount <= MAX_ERRORS)
            {
                if (currentIs(tok("case")) && !includeCases)
                break;
                if (currentIs(tok("while")))
                {
                    b = setBookmark();
                    scope (exit) goToBookmark(b);
                    advance();
                    if (currentIs(tok("(")))
                    {
                     p = peekPastParens();
                        if (p != null && *p == tok(";"))
                        break;
                    }
                }
                c = allocator.setCheckpoint();
                if (!declarationsAndStatements.put(parseDeclarationOrStatement()))
                {
                    allocator.rollback(c);
                    if (suppressMessages > 0)
                        return null;
                }
            }
            ownArray(node.declarationsAndStatements, declarationsAndStatements);
            return true;
        }

        /**
         * Parses a DeclarationOrStatement
         *
         * $(GRAMMAR $(RULEDEF declarationOrStatement):
         *       $(RULE declaration)
         *     | $(RULE statement)
         *     ;)
         */
        boolean parseDeclarationOrStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DeclarationOrStatement,false);
            // "Any ambiguities in the grammar between Statements and
            // Declarations are resolved by the declarations taking precedence."
            Bookmark b = setBookmark();
            c = allocator.setCheckpoint();
            auto d = parseDeclaration(true, false);
            if (d == null)
            {
                allocator.rollback(c);
                goToBookmark(b);
                mixin(parseNodeQ("node.statement", "Statement"));
            }
        else
            {
                // TODO: Make this more efficient. Right now we parse the declaration
                // twice, once with errors and warnings ignored, and once with them
                // printed. Maybe store messages to then be abandoned or written later?
                allocator.rollback(c);
                goToBookmark(b);
                node.declaration = parseDeclaration(true, true);
            }
            return true;
        }

        /**
         * Parses a Declarator
         *
         * $(GRAMMAR $(RULEDEF declarator):
         *       $(LITERAL Identifier)
         *     | $(LITERAL Identifier) $(LITERAL '=') $(RULE initializer)
         *     | $(LITERAL Identifier) $(RULE templateParameters) $(LITERAL '=') $(RULE initializer)
         *     ;)
         */
        boolean parseDeclarator()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Declarator,false);
            id = expect(tok("identifier"));
            mixin (nullCheck("id"));
            node.name = *id;
            if (currentIs(tok("["))) // dmd doesn't accept pointer after identifier
            {
                warn("C-style array declaration.");
                StackBuffer typeSuffixes;
                while (moreTokens() && currentIs(tok("[")))
                if (!typeSuffixes.put(parseTypeSuffix()))
                    return null;
                ownArray(node.cstyle, typeSuffixes);
            }
            if (currentIs(tok("(")))
            {
                mixin (nullCheck("(node.templateParameters = parseTemplateParameters())"));
                if(!tokenCheck("=")){return false;}
                mixin (nullCheck("(node.initializer = parseInitializer())"));
            }
        else if (currentIs(tok("=")))
            {
                advance();
                mixin(parseNodeQ("node.initializer", "Initializer"));
            }
            return true;
        }

        /**
         * Parses a DefaultStatement
         *
         * $(GRAMMAR $(RULEDEF defaultStatement):
         *     $(LITERAL 'default') $(LITERAL ':') $(RULE declarationsAndStatements)
         *     ;)
         */
        boolean parseDefaultStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DefaultStatement,false);
            if(!tokenCheck("default")){return false;}
         colon = expect(tok(":"));
            if (colon == null)
            return null;
            node.colonLocation = colon.index;
            mixin(parseNodeQ("node.declarationsAndStatements", "DeclarationsAndStatements"));
            return true;
        }

        /**
         * Parses a DeleteExpression
         *
         * $(GRAMMAR $(RULEDEF deleteExpression):
         *     $(LITERAL 'delete') $(RULE unaryExpression)
         *     ;)
         */
        boolean parseDeleteExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DeleteExpression,false);
            node.line = current().line;
            node.column = current().column;
            if(!tokenCheck("delete")){return false;}
            mixin(parseNodeQ("node.unaryExpression", "UnaryExpression"));
            return true;
        }

        /**
         * Parses a Deprecated attribute
         *
         * $(GRAMMAR $(RULEDEF deprecated):
         *     $(LITERAL 'deprecated') ($(LITERAL '$(LPAREN)') $(LITERAL StringLiteral)+ $(LITERAL '$(RPAREN)'))?
         *     ;)
         */
        boolean parseDeprecated()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Deprecated,false);
            if(!tokenCheck("deprecated")){return false;}
            if (currentIs(tok("(")))
            {
                advance();
                if(!parseNodeQ("node.assignExpression", "AssignExpression")){cleanup(/*todo*/);return false;}
                mixin (tokenCheck(")"));
            }
            return true;
        }

        /**
         * Parses a Destructor
         *
         * $(GRAMMAR $(RULEDEF destructor):
         *     $(LITERAL '~') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ';'))
         *     ;)
         */
        boolean parseDestructor()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Destructor,false);
//            node.comment = comment;
            comment = null;
            if(!tokenCheck("~")){return false;}
            if (!moreTokens)
            {
                error("'this' expected");
                return null;
            }
//            node.index = current().index;
//            node.line = current().line;
//            node.column = current().column;
            if(!tokenCheck("this")){return false;}
            if(!tokenCheck("(")){return false;}
            if(!tokenCheck(")")){return false;}
            if (currentIs(tok(";")))
            advance();
        else
            {
                StackBuffer memberFunctionAttributes;
                while (moreTokens() && currentIsMemberFunctionAttribute())
                    if (!memberFunctionAttributes.put(parseMemberFunctionAttribute()))
                        return null;
                ownArray(node.memberFunctionAttributes, memberFunctionAttributes);
                mixin(parseNodeQ("node.functionBody", "FunctionBody"));
            }
            return true;
        }

        /**
         * Parses a DoStatement
         *
         * $(GRAMMAR $(RULEDEF doStatement):
         *     $(LITERAL 'do') $(RULE statementNoCaseNoDefault) $(LITERAL 'while') $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)') $(LITERAL ';')
         *     ;)
         */
        boolean parseDoStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DoStatement,false);
            if(!tokenCheck("do")){return false;}
            mixin(parseNodeQ("node.statementNoCaseNoDefault", "StatementNoCaseNoDefault"));
            if(!tokenCheck("while")){return false;}
            if(!tokenCheck("(")){return false;}
            mixin(parseNodeQ("node.expression", "Expression"));
            if(!tokenCheck(")")){return false;}
            if(!tokenCheck(";")){return false;}
            return true;
        }

        /**
         * Parses an EnumBody
         *
         * $(GRAMMAR $(RULEDEF enumBody):
         *     $(LITERAL '{') $(RULE enumMember) ($(LITERAL ',') $(RULE enumMember)?)* $(LITERAL '}')
         *     ;)
         */
        boolean parseEnumBody()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            EnumBody node = allocator.make!EnumBody;
         open = expect(tok("{"));
            mixin (nullCheck("open"));
            node.startLocation = open.index;
            StackBuffer enumMembers;
            EnumMember last;
            while (moreTokens())
            {
                if (currentIs(tok("identifier")))
                {
                    auto c = allocator.setCheckpoint();
                    auto e = parseEnumMember();
                    if (!enumMembers.put(e))
                        allocator.rollback(c);
                    else
                        last = e;
                    if (currentIs(tok(",")))
                    {
                        if (last != null && last.comment == null)
                        last.comment = current().trailingComment;
                        advance();
                        if (!currentIs(tok("}")))
                        continue;
                    }
                    if (currentIs(tok("}")))
                    {
                        if (last != null && last.comment == null)
                        last.comment = tokens[index - 1].trailingComment;
                        break;
                    }
                else
                    {
                        error("',' or '}' expected");
                        if (currentIs(tok("}")))
                        break;
                    }
                }
            else
                error("Enum member expected");
            }
            ownArray(node.enumMembers, enumMembers);
         close = expect (tok("}"));
            if (close != null)
            node.endLocation = close.index;
            return true;
        }

        /**
         * $(GRAMMAR $(RULEDEF anonymousEnumMember):
         *       $(RULE type) $(LITERAL identifier) $(LITERAL '=') $(RULE assignExpression)
         *     | $(LITERAL identifier) $(LITERAL '=') $(RULE assignExpression)
         *     | $(LITERAL identifier)
         *     ;)
         */
        boolean parseAnonymousEnumMember()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AnonymousEnumMember,false);

            boolean typeAllowed = false;//todo idk what default should be
            if (currentIs(tok("identifier")) && peekIsOneOf(tok(","), tok("="), tok("}")))
            {
//                node.comment = current().comment;
                mixin(tokenCheck!("node.name", "identifier"));
                if (currentIs(tok("=")))
                {
                    advance(); // =
                goto assign;
                }
            }
        else if (typeAllowed)
        {
//            node.comment = current().comment;
            mixin(parseNodeQ("node.type", "Type"));
            mixin(tokenCheck!("node.name", "identifier"));
            if(!tokenCheck("=")){return false;}
            assign:
            mixin(parseNodeQ("node.assignExpression", "AssignExpression"));
        }
        else
        {
            error("Cannot specify anonymous enum member type if anonymous enum has a base type.");
            return null;
        }
            return true;
        }

        /**
         * $(GRAMMAR $(RULEDEF anonymousEnumDeclaration):
         *     $(LITERAL 'enum') ($(LITERAL ':') $(RULE type))? $(LITERAL '{') $(RULE anonymousEnumMember)+ $(LITERAL '}')
         *     ;)
         */
        boolean parseAnonymousEnumDeclaration()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AnonymousEnumDeclaration,false);
            if(!tokenCheck("enum")){return false;}
            boolean hasBaseType = currentIs(tok(":"));
            if (hasBaseType)
            {
                advance();
                mixin(parseNodeQ("node.baseType", "Type"));
            }
            if(!tokenCheck("{")){return false;}
            StackBuffer members;
            AnonymousEnumMember last;
            while (moreTokens())
            {
                if (currentIs(tok(",")))
                {
                    if (last != null && last.comment == null)
//                    last.comment = current().trailingComment;
                    advance();
                    continue;
                }
            else if (currentIs(tok("}")))
                {
                    if (last != null && last.comment == null)
//                    last.comment = tokens[index - 1].trailingComment;
                    break;
                }
            else
                {
                    c = allocator.setCheckpoint();
                    AnonymousEnumMember e = parseAnonymousEnumMember(!hasBaseType);
                    if (!members.put(e))
                        allocator.rollback(c);
                    else
                        last = e;
                }
            }
            ownArray(node.members, members);
            if(!tokenCheck("}")){return false;}
            return true;
        }

        /**
         * Parses an EnumDeclaration
         *
         * $(GRAMMAR $(RULEDEF enumDeclaration):
         *       $(LITERAL 'enum') $(LITERAL Identifier) ($(LITERAL ':') $(RULE type))? $(LITERAL ';')
         *     | $(LITERAL 'enum') $(LITERAL Identifier) ($(LITERAL ':') $(RULE type))? $(RULE enumBody)
         *     ;)
         */
        boolean parseEnumDeclaration()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.EnumDeclaration,false);
            if(!tokenCheck("enum")){return false;}
            mixin (tokenCheck!("node.name", "identifier"));
//            node.comment = comment;
//            comment = null;
            if (currentIs(tok(";")))
            {
                advance();
                return true;
            }
            if (currentIs(tok(":")))
            {
                advance(); // skip ':'
                mixin(parseNodeQ("node.type", "Type"));
            }
            mixin(parseNodeQ("node.enumBody", "EnumBody"));
            return true;
        }

        /**
         * Parses an EnumMember
         *
         * $(GRAMMAR $(RULEDEF enumMember):
         *       $(LITERAL Identifier)
         *     | $(LITERAL Identifier) $(LITERAL '=') $(RULE assignExpression)
         *     ;)
         */
        boolean parseEnumMember()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.EnumMember,false);
//            node.comment = current().comment;
            mixin (tokenCheck!("node.name", "identifier"));
            if (currentIs(tok("=")))
            {
                advance();
                mixin(parseNodeQ("node.assignExpression", "AssignExpression"));
            }
            return true;
        }

        /**
         * Parses an EponymousTemplateDeclaration
         *
         * $(GRAMMAR $(RULEDEF eponymousTemplateDeclaration):
         *     $(LITERAL 'enum') $(LITERAL Identifier) $(RULE templateParameters) $(LITERAL '=') $(RULE assignExpression) $(LITERAL ';')
         *     ;)
         */
        boolean parseEponymousTemplateDeclaration()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.EponymousTemplateDeclaration,false);
            advance(); // enum
         ident = expect(tok("identifier"));
            mixin (nullCheck("ident"));
            node.name = *ident;
            mixin(parseNodeQ("node.templateParameters", "TemplateParameters"));
            expect(tok("="));
            node.assignExpression = parseAssignExpression();
            if (node.assignExpression == null)
            mixin(parseNodeQ("node.type", "Type"));
            expect(tok(";"));
            return true;
        }

        /**
         * Parses an EqualExpression
         *
         * $(GRAMMAR $(RULEDEF equalExpression):
         *     $(RULE shiftExpression) ($(LITERAL '==') | $(LITERAL '!=')) $(RULE shiftExpression)
         *     ;)
         */
        EqualExpression parseEqualExpression(ExpressionNode shift = null)
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.EqualExpression,false);
            node.left = shift == null ? parseShiftExpression() : shift;
            mixin (nullCheck("node.left"));
            if (currentIsOneOf(tok("=="), tok("!=")))
            node.operator = advance().type;
            mixin(parseNodeQ("node.right", "ShiftExpression"));
            return true;
        }

        /**
         * Parses an Expression
         *
         * $(GRAMMAR $(RULEDEF expression):
         *     $(RULE assignExpression) ($(LITERAL ',') $(RULE assignExpression))*
         *     ;)
         */
        boolean parseExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            if (suppressedErrorCount > MAX_ERRORS)
                return null;
            if (!moreTokens())
            {
                error("Expected expression instead of EOF");
                return null;
            }
            return parseCommaSeparatedRule!(Expression, AssignExpression, true)();
        }

        /**
         * Parses an ExpressionStatement
         *
         * $(GRAMMAR $(RULEDEF expressionStatement):
         *     $(RULE expression) $(LITERAL ';')
         *     ;)
         */
        ExpressionStatement parseExpressionStatement(Expression expression = null)
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ExpressionStatement,false);
            node.expression = expression == null ? parseExpression() : expression;
            if (node.expression == null || expect(tok(";")) == null)
            return null;
            return true;
        }

        /**
         * Parses a FinalSwitchStatement
         *
         * $(GRAMMAR $(RULEDEF finalSwitchStatement):
         *     $(LITERAL 'final') $(RULE switchStatement)
         *     ;)
         */
        boolean parseFinalSwitchStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            mixin (simpleParse!(FinalSwitchStatement, tok("final"), "switchStatement|parseSwitchStatement"));
        }

        /**
         * Parses a Finally
         *
         * $(GRAMMAR $(RULEDEF finally):
         *     $(LITERAL 'finally') $(RULE declarationOrStatement)
         *     ;)
         */
        boolean parseFinally()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Finally,false);
            if(!tokenCheck("finally")){return false;}
            mixin(parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement"));
            return true;
        }

        /**
         * Parses a ForStatement
         *
         * $(GRAMMAR $(RULEDEF forStatement):
         *     $(LITERAL 'for') $(LITERAL '$(LPAREN)') ($(RULE declaration) | $(RULE statementNoCaseNoDefault)) $(RULE expression)? $(LITERAL ';') $(RULE expression)? $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement)
         *     ;)
         */
        boolean parseForStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ForStatement,false);
            if(!tokenCheck("for")){return false;}
            if (!moreTokens) node.startIndex = current().index;
            if(!tokenCheck("(")){return false;}

            if (currentIs(tok(";")))
            advance();
        else
            mixin(parseNodeQ("node.initialization", "DeclarationOrStatement"));

            if (currentIs(tok(";")))
            advance();
        else
            {
                mixin(parseNodeQ("node.test", "Expression"));
                expect(tok(";"));
            }

            if (!currentIs(tok(")")))
            mixin(parseNodeQ("node.increment", "Expression"));

            if(!tokenCheck(")")){return false;}

            // Intentionally return an incomplete parse tree so that DCD will work
            // more correctly.
            if (currentIs(tok("}")))
            {
                error("Statement expected", false);
                return true;
            }

            mixin(parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement"));
            return true;
        }

        /**
         * Parses a ForeachStatement
         *
         * $(GRAMMAR $(RULEDEF foreachStatement):
         *       ($(LITERAL 'foreach') | $(LITERAL 'foreach_reverse')) $(LITERAL '$(LPAREN)') $(RULE foreachTypeList) $(LITERAL ';') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement)
         *     | ($(LITERAL 'foreach') | $(LITERAL 'foreach_reverse')) $(LITERAL '$(LPAREN)') $(RULE foreachType) $(LITERAL ';') $(RULE expression) $(LITERAL '..') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement)
         *     ;)
         */
        boolean parseForeachStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            ForeachStatement node = allocator.make!ForeachStatement;
            if (currentIsOneOf(tok("foreach"), tok("foreach_reverse")))
            node.type = advance().type;
        else
            {
                error(""foreach" or "foreach_reverse" expected");
                return null;
            }
            node.startIndex = current().index;
            if(!tokenCheck("(")){return false;}
            ForeachTypeList feType = parseForeachTypeList();
            mixin (nullCheck("feType"));
            boolean canBeRange = feType.items.length == 1;

            if(!tokenCheck(";")){return false;}
            mixin(parseNodeQ("node.low", "Expression"));
            mixin (nullCheck("node.low"));
            if (currentIs(tok("..")))
            {
                if (!canBeRange)
                {
                    error("Cannot have more than one foreach variable for a foreach range statement");
                    return null;
                }
                advance();
                mixin(parseNodeQ("node.high", "Expression"));
                node.foreachType = feType.items[0];
                mixin (nullCheck("node.high"));
            }
        else
            {
                node.foreachTypeList = feType;
            }
            if(!tokenCheck(")")){return false;}
            if (currentIs(tok("}")))
            {
                error("Statement expected", false);
                return true; // this line makes DCD better
            }
            mixin(parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement"));
            return true;
        }

        /**
         * Parses a ForeachType
         *
         * $(GRAMMAR $(RULEDEF foreachType):
         *       $(LITERAL 'ref')? $(RULE typeConstructors)? $(RULE type)? $(LITERAL Identifier)
         *     | $(RULE typeConstructors)? $(LITERAL 'ref')? $(RULE type)? $(LITERAL Identifier)
         *     ;)
         */
        boolean parseForeachType()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ForeachType,false);
            if (currentIs(tok("ref")))
            {
                node.isRef = true;
                advance();
            }
            if (currentIsOneOf(tok(""), tok("immutable"),
            tok("inout"), tok("shared")) && !peekIs(tok("(")))
            {
                trace("\033[01;36mType ructor");
                mixin(parseNodeQ("node.typeConstructors", "TypeConstructors"));
            }
            if (currentIs(tok("ref")))
            {
                node.isRef = true;
                advance();
            }
            if (currentIs(tok("identifier")) && peekIsOneOf(tok(","), tok(";")))
            {
                node.identifier = advance();
                return true;
            }
            mixin(parseNodeQ("node.type", "Type"));
         ident = expect(tok("identifier"));
            mixin(nullCheck("ident"));
            node.identifier = *ident;
            return true;
        }

        /**
         * Parses a ForeachTypeList
         *
         * $(GRAMMAR $(RULEDEF foreachTypeList):
         *     $(RULE foreachType) ($(LITERAL ',') $(RULE foreachType))*
         *     ;)
         */
        boolean parseForeachTypeList()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            return parseCommaSeparatedRule!(ForeachTypeList, ForeachType)();
        }

        /**
         * Parses a FunctionAttribute
         *
         * $(GRAMMAR $(RULEDEF functionAttribute):
         *       $(RULE atAttribute)
         *     | $(LITERAL '')
         *     | $(LITERAL '')
         *     ;)
         */
        boolean parseFunctionAttribute(boolean validate)
        {
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.FunctionAttribute,false);
            switch (current().type)
            {
                case tok("@"):
                    mixin(parseNodeQ("node.atAttribute", "AtAttribute"));
                    break;
                case tok(""):
                case tok(""):
                    node.token = advance();
                    break;
                default:
                    if (validate)
                        error("@attribute, "", or "" expected");
                    return null;
            }
            return true;
        }

        /**
         * Parses a FunctionBody
         *
         * $(GRAMMAR $(RULEDEF functionBody):
         *       $(RULE blockStatement)
         *     | ($(RULE inStatement) | $(RULE outStatement) | $(RULE outStatement) $(RULE inStatement) | $(RULE inStatement) $(RULE outStatement))? $(RULE bodyStatement)?
         *     ;)
         */
        boolean parseFunctionBody()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.FunctionBody,false);
            if (currentIs(tok(";")))
            {
                advance();
                return true;
            }
        else if (currentIs(tok("{")))
            mixin(parseNodeQ("node.blockStatement", "BlockStatement"));
        else if (currentIsOneOf(tok("in"), tok("out"), tok("body")))
            {
                if (currentIs(tok("in")))
                {
                    mixin(parseNodeQ("node.inStatement", "InStatement"));
                    if (currentIs(tok("out")))
                    mixin(parseNodeQ("node.outStatement", "OutStatement"));
                }
            else if (currentIs(tok("out")))
                {
                    mixin(parseNodeQ("node.outStatement", "OutStatement"));
                    if (currentIs(tok("in")))
                    mixin(parseNodeQ("node.inStatement", "InStatement"));
                }
                // Allow function bodies without body statements because this is
                // valid inside of interfaces.
                if (currentIs(tok("body")))
                mixin(parseNodeQ("node.bodyStatement", "BodyStatement"));
            }
        else
            {
                error("'in', 'out', 'body', or block statement expected");
                return null;
            }
            return true;
        }

        /**
         * Parses a FunctionCallExpression
         *
         * $(GRAMMAR $(RULEDEF functionCallExpression):
         *       $(RULE symbol) $(RULE arguments)
         *     | $(RULE unaryExpression) $(RULE arguments)
         *     | $(RULE type) $(RULE arguments)
         *     ;)
         */
        boolean parseFunctionCallExpression()//(UnaryExpression unary = null)
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));


//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.FunctionCallExpression,false);
            switch (current().type)
            {
                case tok(""):
                case tok("immutable"):
                case tok("inout"):
                case tok("shared"):
                case tok("scope"):
                case tok(""):
                case tok(""):
                    mixin(parseNodeQ("node.type", "Type"));
                    mixin(parseNodeQ("node.arguments", "Arguments"));
                    break;
                default:
                    if (unary != null)
                    node.unaryExpression = unary;
            else
                    mixin(parseNodeQ("node.unaryExpression", "UnaryExpression"));
                    if (currentIs(tok("!")))
                    mixin(parseNodeQ("node.templateArguments", "TemplateArguments"));
                    if (unary != null)
                    mixin(parseNodeQ("node.arguments", "Arguments"));
            }
            return true;
        }

        /**
         * Parses a FunctionDeclaration
         *
         * $(GRAMMAR $(RULEDEF functionDeclaration):
         *       ($(RULE storageClass)+ | $(RULE _type)) $(LITERAL Identifier) $(RULE parameters) $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ';'))
         *     | ($(RULE storageClass)+ | $(RULE _type)) $(LITERAL Identifier) $(RULE templateParameters) $(RULE parameters) $(RULE memberFunctionAttribute)* $(RULE raint)? ($(RULE functionBody) | $(LITERAL ';'))
         *     ;)
         */
        FunctionDeclaration parseFunctionDeclaration()//(Type type = null,Attribute[] attributes = null)
        {
            final boolean isAuto = false;
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.FunctionDeclaration,false);
//            node.comment = comment;
//            comment = null;
            StackBuffer memberFunctionAttributes;
            node.attributes = attributes;

            if (isAuto)
            {
                StackBuffer storageClasses;
                while (isStorageClass())
                    if (!storageClasses.put(parseStorageClass()))
                        return null;
                ownArray(node.storageClasses, storageClasses);

                foreach (a; node.attributes)
                {
                    if (a.attribute == tok("auto"))
                    node.hasAuto = true;
                else if (a.attribute == tok("ref"))
                    node.hasRef = true;
                else
                    continue;
                }
            }
            else
            {
                while (moreTokens() && currentIsMemberFunctionAttribute())
                    if (!memberFunctionAttributes.put(parseMemberFunctionAttribute()))
                        return null;
                if (type == null)
                mixin(parseNodeQ("node.returnType", "Type"));
            else
                node.returnType = type;
            }

            mixin(tokenCheck!("node.name", "identifier"));
            if (!currentIs(tok("(")))
            {
                error("'(' expected");
                return null;
            }
         p = peekPastParens();
            boolean isTemplate = p == null && p.type != tok("(");

            if (isTemplate)
                mixin(parseNodeQ("node.templateParameters", "TemplateParameters"));

            mixin(parseNodeQ("node.parameters", "Parameters"));

            while (moreTokens() && currentIsMemberFunctionAttribute())
                if (!memberFunctionAttributes.put(parseMemberFunctionAttribute()))
                    return null;

            if (isTemplate && currentIs(tok("if")))
            mixin(parseNodeQ("node.raint", "Constraint"));

            if (currentIs(tok(";")))
            advance();
        else
            mixin(parseNodeQ("node.functionBody", "FunctionBody"));
            ownArray(node.memberFunctionAttributes, memberFunctionAttributes);
            return true;
        }

        /**
         * Parses a FunctionLiteralExpression
         *
         * $(GRAMMAR $(RULEDEF functionLiteralExpression):
         *     | $(LITERAL 'delegate') $(RULE type)? ($(RULE parameters) $(RULE functionAttribute)*)? $(RULE functionBody)
         *     | $(LITERAL 'function') $(RULE type)? ($(RULE parameters) $(RULE functionAttribute)*)? $(RULE functionBody)
         *     | $(RULE parameters) $(RULE functionAttribute)* $(RULE functionBody)
         *     | $(RULE functionBody)
         *     | $(LITERAL Identifier) $(LITERAL '=>') $(RULE assignExpression)
         *     | $(LITERAL 'function') $(RULE type)? $(RULE parameters) $(RULE functionAttribute)* $(LITERAL '=>') $(RULE assignExpression)
         *     | $(LITERAL 'delegate') $(RULE type)? $(RULE parameters) $(RULE functionAttribute)* $(LITERAL '=>') $(RULE assignExpression)
         *     | $(RULE parameters) $(RULE functionAttribute)* $(LITERAL '=>') $(RULE assignExpression)
         *     ;)
         */
        boolean parseFunctionLiteralExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.FunctionLiteralExpression,false);
//            node.line = current().line;
//            node.column = current().column;
            if (currentIsOneOf(tok("function"), tok("delegate")))
            {
                node.functionOrDelegate = advance().type;
                if (!currentIsOneOf(tok("("), tok("in"), tok("body"),
                tok("out"), tok("{"), tok("=>")))
                mixin(parseNodeQ("node.returnType", "Type"));
            }
            if (startsWith(tok("identifier"), tok("=>")))
            {
                node.identifier = advance();
                advance(); // =>
                mixin(parseNodeQ("node.assignExpression", "AssignExpression"));
                return true;
            }
        else if (currentIs(tok("(")))
            {
                mixin(parseNodeQ("node.parameters", "Parameters"));
                StackBuffer memberFunctionAttributes;
                while (currentIsMemberFunctionAttribute())
                {
                    auto c = allocator.setCheckpoint();
                    if (!memberFunctionAttributes.put(parseMemberFunctionAttribute()))
                    {
                        allocator.rollback(c);
                        break;
                    }
                }
                ownArray(node.memberFunctionAttributes, memberFunctionAttributes);
            }
            if (currentIs(tok("=>")))
            {
                advance();
                mixin(parseNodeQ("node.assignExpression", "AssignExpression"));
            }
        else
            mixin(parseNodeQ("node.functionBody", "FunctionBody"));
            return true;
        }

        /**
         * Parses a GotoStatement
         *
         * $(GRAMMAR $(RULEDEF gotoStatement):
         *     $(LITERAL 'goto') ($(LITERAL Identifier) | $(LITERAL 'default') | $(LITERAL 'case') $(RULE expression)?) $(LITERAL ';')
         *     ;)
         */
        boolean parseGotoStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.GotoStatement,false);
            if(!tokenCheck("goto")){return false;}
            switch (current().type)
            {
                case tok("identifier"):
                case tok("default"):
                    node.label = advance();
                    break;
                case tok("case"):
                    node.label = advance();
                    if (!currentIs(tok(";")))
                    mixin(parseNodeQ("node.expression", "Expression"));
                    break;
                default:
                    error("Identifier, \"default\", or \"case\" expected");
                    return null;
            }
            if(!tokenCheck(";")){return false;}
            return true;
        }

        /**
         * Parses an IdentifierChain
         *
         * $(GRAMMAR $(RULEDEF identifierChain):
         *     $(LITERAL Identifier) ($(LITERAL '.') $(LITERAL Identifier))*
         *     ;)
         */
        boolean parseIdentifierChain()
        {
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IdentifierChain,false);
            StackBuffer identifiers;
            while (moreTokens())
            {
                Token ident = expect(tok("identifier"));
                mixin(nullCheck("ident"));
                identifiers.put(ident);
                if (currentIs(tok(".")))
                {
                    advance();
                    continue;
                }
            else
                break;
            }
            ownArray(node.identifiers, identifiers);
            return true;
        }

        /**
         * Parses an IdentifierList
         *
         * $(GRAMMAR $(RULEDEF identifierList):
         *     $(LITERAL Identifier) ($(LITERAL ',') $(LITERAL Identifier))*
         *     ;)
         */
        boolean parseIdentifierList()
        {
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IdentifierList,false);
            StackBuffer identifiers;
            while (moreTokens())
            {
                Token ident = expect(tok("identifier"));
                mixin(nullCheck("ident"));
                identifiers.put(ident);
                if (currentIs(tok(",")))
                {
                    advance();
                    continue;
                }
                else
                    break;
            }
            ownArray(node.identifiers, identifiers);
            return true;
        }

        /**
         * Parses an IdentifierOrTemplateChain
         *
         * $(GRAMMAR $(RULEDEF identifierOrTemplateChain):
         *     $(RULE identifierOrTemplateInstance) ($(LITERAL '.') $(RULE identifierOrTemplateInstance))*
         *     ;)
         */
        boolean parseIdentifierOrTemplateChain()
        {
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IdentifierOrTemplateChain,false);
            StackBuffer identifiersOrTemplateInstances;
            while (moreTokens())
            {
                auto c = allocator.setCheckpoint();
                if (!identifiersOrTemplateInstances.put(parseIdentifierOrTemplateInstance()))
                {
                    allocator.rollback(c);
                    if (identifiersOrTemplateInstances.length == 0)
                        return null;
                    else
                        break;
                }
                if (!currentIs(tok(".")))
                break;
            else
                advance();
            }
            ownArray(node.identifiersOrTemplateInstances, identifiersOrTemplateInstances);
            return true;
        }

        /**
         * Parses an IdentifierOrTemplateInstance
         *
         * $(GRAMMAR $(RULEDEF identifierOrTemplateInstance):
         *       $(LITERAL Identifier)
         *     | $(RULE templateInstance)
         *     ;)
         */
        boolean parseIdentifierOrTemplateInstance()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IdentifierOrTemplateInstance,false);
            if (peekIs(tok("!")) && !startsWith(tok("identifier"),
            tok("!"), tok("is"))
            && !startsWith(tok("identifier"), tok("!"), tok("in")))
            {
                mixin(parseNodeQ("node.templateInstance", "TemplateInstance"));
            }
            else
            {
                Token ident = expect(tok("identifier"));
                mixin(nullCheck("ident"));
                node.identifier = ident;
            }
            return true;
        }

        /**
         * Parses an IdentityExpression
         *
         * $(GRAMMAR $(RULEDEF identityExpression):
         *     $(RULE shiftExpression) ($(LITERAL 'is') | ($(LITERAL '!') $(LITERAL 'is'))) $(RULE shiftExpression)
         *     ;)
         */
        boolean parseIdentityExpression()//(ExpressionNode shift = null)
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IdentityExpression,false);
            mixin(nullCheck("node.left = shift == null ? parseShiftExpression() : shift"));
            if (currentIs(tok("!")))
            {
                advance();
                node.negated = true;
            }
            if(!tokenCheck("is")){return false;}
            mixin(parseNodeQ("node.right", "ShiftExpression"));
            return true;
        }

        /**
         * Parses an IfStatement
         *
         * $(GRAMMAR $(RULEDEF ifStatement):
         *     $(LITERAL 'if') $(LITERAL '$(LPAREN)') $(RULE ifCondition) $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement) ($(LITERAL 'else') $(RULE declarationOrStatement))?
         *$(RULEDEF ifCondition):
         *       $(LITERAL 'auto') $(LITERAL Identifier) $(LITERAL '=') $(RULE expression)
         *     | $(RULE typeConstructors) $(LITERAL Identifier) $(LITERAL '=') $(RULE expression)
         *     | $(RULE type) $(LITERAL Identifier) $(LITERAL '=') $(RULE expression)
         *     | $(RULE expression)
         *     ;)
         */
        boolean parseIfStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IfStatement,false);
//            node.line = current().line;
//            node.column = current().column;
            if(!tokenCheck("if")){return false;}
            node.startIndex = current().index;
            if(!tokenCheck("(")){return false;}

            if (currentIs(tok("auto")))
            {
                advance();
                Token i = expect(tok("identifier"));
                if (i != null)
                node.identifier = i;
                expect(tok("="));
                mixin(parseNodeQ("node.expression", "Expression"));
            }
        else
            {
                // consume for TypeCtors = identifier
                if (isTypeCtor(current().type))
                {
                    while (isTypeCtor(current().type))
                    {
                        prev = current().type;
                        advance();
                        if (current().type == prev)
                            warn("redundant type ructor");
                    }
                    // goes back for TypeCtor(Type) = identifier
                    if (currentIs(tok("(")))
                    index--;
                }

                Bookmark b = setBookmark();
                c = allocator.setCheckpoint();
                auto type = parseType();
                if (type == null || !currentIs(tok("identifier"))
                || !peekIs(tok("=")))
                {
                    allocator.rollback(c);
                    goToBookmark(b);
                    mixin(parseNodeQ("node.expression", "Expression"));
                }
                else
                {
                    abandonBookmark(b);
                    node.type = type;
                    mixin(tokenCheck("node.identifier", "identifier"));
                    if(!tokenCheck("=")){return false;}
                    mixin(parseNodeQ("node.expression", "Expression"));
                }
            }

            if(!tokenCheck(")")){return false;}
            if (currentIs(tok("}")))
            {
                error("Statement expected", false);
                return true; // this line makes DCD better
            }
            mixin(parseNodeQ("node.thenStatement", "DeclarationOrStatement"));
            if (currentIs(tok("else")))
            {
                advance();
                mixin(parseNodeQ("node.elseStatement", "DeclarationOrStatement"));
            }
            return true;
        }

        /**
         * Parses an ImportBind
         *
         * $(GRAMMAR $(RULEDEF importBind):
         *     $(LITERAL Identifier) ($(LITERAL '=') $(LITERAL Identifier))?
         *     ;)
         */
        boolean parseImportBind()
        {
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ImportBind,false);
            Token ident = expect(tok("identifier"));
            mixin(nullCheck("ident"));
            node.left = ident;
            if (currentIs(tok("=")))
            {
                advance();
                Token id = expect(tok("identifier"));
                mixin(nullCheck("id"));
                node.right = id;
            }
            return true;
        }

        /**
         * Parses ImportBindings
         *
         * $(GRAMMAR $(RULEDEF importBindings):
         *     $(RULE singleImport) $(LITERAL ':') $(RULE importBind) ($(LITERAL ',') $(RULE importBind))*
         *     ;)
         */
        ImportBindings parseImportBindings(SingleImport singleImport)
        {
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ImportBindings,false);
            mixin(nullCheck("node.singleImport = singleImport == null ? parseSingleImport() : singleImport"));
            if(!tokenCheck(":")){return false;}
            StackBuffer importBinds;
            while (moreTokens())
            {
                c = allocator.setCheckpoint();
                if (importBinds.put(parseImportBind()))
                {
                    if (currentIs(tok(",")))
                    advance();
                else
                    break;
                }
                else
                {
                    allocator.rollback(c);
                    break;
                }
            }
            ownArray(node.importBinds, importBinds);
            return true;
        }

        /**
         * Parses an ImportDeclaration
         *
         * $(GRAMMAR $(RULEDEF importDeclaration):
         *       $(LITERAL 'import') $(RULE singleImport) ($(LITERAL ',') $(RULE singleImport))* ($(LITERAL ',') $(RULE importBindings))? $(LITERAL ';')
         *     | $(LITERAL 'import') $(RULE importBindings) $(LITERAL ';')
         *     ;)
         */
        boolean parseImportDeclaration()
        {
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ImportDeclaration,false);
            node.startIndex = current().index;
            if(!tokenCheck("import")){return false;}
            SingleImport si = parseSingleImport();
            if (si == null)
            return null;
            if (currentIs(tok(":")))
            node.importBindings = parseImportBindings(si);
        else
            {
                StackBuffer singleImports;
                singleImports.put(si);
                if (currentIs(tok(",")))
                {
                    advance();
                    while (moreTokens())
                    {
                        auto single = parseSingleImport();
                        mixin(nullCheck("single"));
                        if (currentIs(tok(":")))
                        {
                            node.importBindings = parseImportBindings(single);
                            break;
                        }
                    else
                        {
                            singleImports.put(single);
                            if (currentIs(tok(",")))
                            advance();
                        else
                            break;
                        }
                    }
                }
                ownArray(node.singleImports, singleImports);
            }
            node.endIndex = (moreTokens() ? current() : previous()).index + 1;
            if(!tokenCheck(";")){return false;}
            return true;
        }

        /**
         * Parses an ImportExpression
         *
         * $(GRAMMAR $(RULEDEF importExpression):
         *     $(LITERAL 'import') $(LITERAL '$(LPAREN)') $(RULE assignExpression) $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseImportExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            mixin(simpleParse(ImportExpression, tok("import"), tok("("),
            "assignExpression|parseAssignExpression", tok(")")));
        }

        /**
         * Parses an Index
         *
         * $(GRAMMAR $(RULEDEF index):
         *     $(RULE assignExpression) ($(LITERAL '..') $(RULE assignExpression))?
         *     ;
         * )
         */
        boolean parseIndex()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Index(),false);
            mixin(parseNodeQ("node.low", "AssignExpression"));
            if (currentIs(tok("..")))
            {
                advance();
                mixin(parseNodeQ("node.high", "AssignExpression"));
            }
            return true;
        }

        /**
         * Parses an IndexExpression
         *
         * $(GRAMMAR $(RULEDEF indexExpression):
         *       $(RULE unaryExpression) $(LITERAL '[') $(LITERAL ']')
         *     | $(RULE unaryExpression) $(LITERAL '[') $(RULE index) ($(LITERAL ',') $(RULE index))* $(LITERAL ']')
         *     ;
         * )
         */
        boolean parseIndexExpression()//(UnaryExpression unaryExpression = null)
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IndexExpression,false);
            mixin(nullCheck("node.unaryExpression = unaryExpression == null ? parseUnaryExpression() : unaryExpression"));
            if(!tokenCheck("[")){return false;}
            StackBuffer indexes;
            while (true)
            {
                if (!moreTokens())
                {
                    error("Expected unary expression instead of EOF");
                    return null;
                }
                if (currentIs(tok("]")))
                break;
                if (!(indexes.put(parseIndex())))
                    return null;
                if (currentIs(tok(",")))
                advance();
            else
                break;
            }
            ownArray(node.indexes, indexes);
            advance(); // ]
            return true;
        }

        /**
         * Parses an InExpression
         *
         * $(GRAMMAR $(RULEDEF inExpression):
         *     $(RULE shiftExpression) ($(LITERAL 'in') | ($(LITERAL '!') $(LITERAL 'in'))) $(RULE shiftExpression)
         *     ;)
         */
        ExpressionNode parseInExpression()//(ExpressionNode shift = null)
        {
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.InExpression,false);
            mixin(nullCheck("node.left = shift == null ? parseShiftExpression() : shift"));
            if (currentIs(tok("!")))
            {
                node.negated = true;
                advance();
            }
            if(!tokenCheck("in")){return false;}
            mixin(parseNodeQ("node.right", "ShiftExpression"));
            return true;
        }

        /**
         * Parses an InStatement
         *
         * $(GRAMMAR $(RULEDEF inStatement):
         *     $(LITERAL 'in') $(RULE blockStatement)
         *     ;)
         */
        boolean parseInStatement()
        {
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.InStatement,false);
            Token i = expect(tok("in"));
            mixin(nullCheck("i"));
            node.inTokenLocation = i.index;
            mixin(parseNodeQ("node.blockStatement", "BlockStatement"));
            return true;
        }

        /**
         * Parses an Initializer
         *
         * $(GRAMMAR $(RULEDEF initializer):
         *       $(LITERAL 'void')
         *     | $(RULE nonVoidInitializer)
         *     ;)
         */
        boolean parseInitializer()
        {
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Initializer,false);
            if (currentIs(tok("void")) && peekIsOneOf(tok(","), tok(";")))
            advance();
        else
            mixin(parseNodeQ("node.nonVoidInitializer", "NonVoidInitializer"));
            return true;
        }

        /**
         * Parses an InterfaceDeclaration
         *
         * $(GRAMMAR $(RULEDEF interfaceDeclaration):
         *       $(LITERAL 'interface') $(LITERAL Identifier) $(LITERAL ';')
         *     | $(LITERAL 'interface') $(LITERAL Identifier) ($(LITERAL ':') $(RULE baseClassList))? $(RULE structBody)
         *     | $(LITERAL 'interface') $(LITERAL Identifier) $(RULE templateParameters) $(RULE raint)? ($(LITERAL ':') $(RULE baseClassList))? $(RULE structBody)
         *     | $(LITERAL 'interface') $(LITERAL Identifier) $(RULE templateParameters) ($(LITERAL ':') $(RULE baseClassList))? $(RULE raint)? $(RULE structBody)
         *     ;)
         */
        boolean parseInterfaceDeclaration()
        {
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.InterfaceDeclaration,false);
            expect(tok("interface"));
            return parseInterfaceOrClass(node);
        }

        /**
         * Parses an Invariant
         *
         * $(GRAMMAR $(RULEDEF invariant):
         *     $(LITERAL 'invariant') ($(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)'))? $(RULE blockStatement)
         *     ;)
         */
        boolean parseInvariant()
        {
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Invariant,false);
            node.index = current().index;
            node.line = current().line;
            if(!tokenCheck("invariant")){return false;}
            if (currentIs(tok("(")))
            {
                advance();
                if(!tokenCheck(")")){return false;}
            }
            mixin(parseNodeQ("node.blockStatement", "BlockStatement"));
            return true;
        }

        /**
         * Parses an IsExpression
         *
         * $(GRAMMAR $(RULEDEF isExpression):
         *     $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL '$(RPAREN)')
         *     $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL ':') $(RULE typeSpecialization) $(LITERAL '$(RPAREN)')
         *     $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL '=') $(RULE typeSpecialization) $(LITERAL '$(RPAREN)')
         *     $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL ':') $(RULE typeSpecialization) $(LITERAL ',') $(RULE templateParameterList) $(LITERAL '$(RPAREN)')
         *     $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL '=') $(RULE typeSpecialization) $(LITERAL ',') $(RULE templateParameterList) $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseIsExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IsExpression,false);
            if(!tokenCheck("is")){return false;}
            if(!tokenCheck("(")){return false;}
            mixin(parseNodeQ("node.type", "Type"));
            if (currentIs(tok("identifier")))
            node.identifier = advance();
            if (currentIsOneOf(tok("=="), tok(":")))
            {
                node.equalsOrColon = advance().type;
                mixin(parseNodeQ("node.typeSpecialization", "TypeSpecialization"));
                if (currentIs(tok(",")))
                {
                    advance();
                    mixin(parseNodeQ("node.templateParameterList", "TemplateParameterList"));
                }
            }
            if(!tokenCheck(")")){return false;}
            return true;
        }

        /**
         * Parses a KeyValuePair
         *
         * $(GRAMMAR $(RULEDEF keyValuePair):
         *     $(RULE assignExpression) $(LITERAL ':') $(RULE assignExpression)
         *     ;)
         */
        boolean parseKeyValuePair()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.KeyValuePair,false);
            mixin(parseNodeQ("node.key", "AssignExpression"));
            if(!tokenCheck(":")){return false;}
            mixin(parseNodeQ("node.value", "AssignExpression"));
            return true;
        }

        /**
         * Parses KeyValuePairs
         *
         * $(GRAMMAR $(RULEDEF keyValuePairs):
         *     $(RULE keyValuePair) ($(LITERAL ',') $(RULE keyValuePair))* $(LITERAL ',')?
         *     ;)
         */
        boolean parseKeyValuePairs()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.KeyValuePairs,false);
            StackBuffer keyValuePairs;
            while (moreTokens())
            {
                if (!keyValuePairs.put(parseKeyValuePair()))
                    return null;
                if (currentIs(tok(",")))
                {
                    advance();
                    if (currentIs(tok("]")))
                    break;
                }
            else
                break;
            }
            ownArray(node.keyValuePairs, keyValuePairs);
            return true;
        }

        /**
         * Parses a LabeledStatement
         *
         * $(GRAMMAR $(RULEDEF labeledStatement):
         *     $(LITERAL Identifier) $(LITERAL ':') $(RULE declarationOrStatement)?
         *     ;)
         */
        boolean parseLabeledStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.LabeledStatement,false);
         ident = expect(tok("identifier"));
            mixin (nullCheck("ident"));
            node.identifier = *ident;
            expect(tok(":"));
            if (!currentIs(tok("}")))
            mixin(parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement"));
            return true;
        }

        /**
         * Parses a LastCatch
         *
         * $(GRAMMAR $(RULEDEF lastCatch):
         *     $(LITERAL 'catch') $(RULE statementNoCaseNoDefault)
         *     ;)
         */
        boolean parseLastCatch()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.LastCatch,false);
         t = expect(tok("catch"));
            mixin (nullCheck("t"));
            node.line = t.line;
            node.column = t.column;
            mixin(parseNodeQ("node.statementNoCaseNoDefault", "StatementNoCaseNoDefault"));
            return true;
        }

        /**
         * Parses a LinkageAttribute
         *
         * $(GRAMMAR $(RULEDEF linkageAttribute):
         *       $(LITERAL 'extern') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '$(RPAREN)')
         *       $(LITERAL 'extern') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '-') $(LITERAL Identifier) $(LITERAL '$(RPAREN)')
         *     | $(LITERAL 'extern') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '++') ($(LITERAL ',') $(RULE identifierChain) | $(LITERAL 'struct') | $(LITERAL 'class'))? $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseLinkageAttribute()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.LinkageAttribute,false);
            mixin (tokenCheck("extern"));
            mixin (tokenCheck("("));
         ident = expect(tok("identifier"));
            mixin (nullCheck("ident"));
            node.identifier = *ident;
            if (currentIs(tok("++")))
            {
                advance();
                node.hasPlusPlus = true;
                if (currentIs(tok(",")))
                {
                    advance();
                    if (currentIsOneOf(tok("struct"), tok("class")))
                    node.classOrStruct = advance().type;
                else
                    mixin(parseNodeQ("node.identifierChain", "IdentifierChain"));
                }
            }
        else if (currentIs(tok("-")))
            {
                advance();
                if(!tokenCheck("identifier")){return false;}
            }
            expect(tok(")"));
            return true;
        }

        /**
         * Parses a MemberFunctionAttribute
         *
         * $(GRAMMAR $(RULEDEF memberFunctionAttribute):
         *       $(RULE functionAttribute)
         *     | $(LITERAL 'immutable')
         *     | $(LITERAL 'inout')
         *     | $(LITERAL 'shared')
         *     | $(LITERAL '')
         *     | $(LITERAL 'return')
         *     | $(LITERAL 'scope')
         *     ;)
         */
        boolean parseMemberFunctionAttribute()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.MemberFunctionAttribute,false);
            switch (current().type)
            {
                case tok("@"):
                    mixin(parseNodeQ("node.atAttribute", "AtAttribute"));
                    break;
                case tok("immutable"):
                case tok("inout"):
                case tok("shared"):
                case tok(""):
                case tok(""):
                case tok(""):
                case tok("return"):
                case tok("scope"):
                    node.tokenType = advance().type;
                    break;
                default:
                    error("Member funtion attribute expected");
            }
            return true;
        }

        /**
         * Parses a MixinDeclaration
         *
         * $(GRAMMAR $(RULEDEF mixinDeclaration):
         *       $(RULE mixinExpression) $(LITERAL ';')
         *     | $(RULE templateMixinExpression) $(LITERAL ';')
         *     ;)
         */
        boolean parseMixinDeclaration()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.MixinDeclaration,false);
            if (peekIsOneOf(tok("identifier"), tok("typeof"), tok(".")))
            mixin(parseNodeQ("node.templateMixinExpression", "TemplateMixinExpression"));
        else if (peekIs(tok("(")))
            mixin(parseNodeQ("node.mixinExpression", "MixinExpression"));
        else
            {
                error(""(" or identifier expected");
                return null;
            }
            expect(tok(";"));
            return true;
        }

        /**
         * Parses a MixinExpression
         *
         * $(GRAMMAR $(RULEDEF mixinExpression):
         *     $(LITERAL 'mixin') $(LITERAL '$(LPAREN)') $(RULE assignExpression) $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseMixinExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.MixinExpression,false);
            expect(tok("mixin"));
            expect(tok("("));
            mixin(parseNodeQ("node.assignExpression", "AssignExpression"));
            expect(tok(")"));
            return true;
        }

        /**
         * Parses a MixinTemplateDeclaration
         *
         * $(GRAMMAR $(RULEDEF mixinTemplateDeclaration):
         *     $(LITERAL 'mixin') $(RULE templateDeclaration)
         *     ;)
         */
        boolean parseMixinTemplateDeclaration()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.MixinTemplateDeclaration,false);
            if(!tokenCheck("mixin")){return false;}
            mixin(parseNodeQ("node.templateDeclaration", "TemplateDeclaration"));
            return true;
        }

        /**
         * Parses a MixinTemplateName
         *
         * $(GRAMMAR $(RULEDEF mixinTemplateName):
         *       $(RULE symbol)
         *     | $(RULE typeofExpression) $(LITERAL '.') $(RULE identifierOrTemplateChain)
         *     ;)
         */
        boolean parseMixinTemplateName()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.MixinTemplateName,false);
            if (currentIs(tok("typeof")))
            {
                mixin(parseNodeQ("node.typeofExpression", "TypeofExpression"));
                expect(tok("."));
                mixin(parseNodeQ("node.identifierOrTemplateChain", "IdentifierOrTemplateChain"));
            }
        else
            mixin(parseNodeQ("node.symbol", "Symbol"));
            return true;
        }

        /**
         * Parses a Module
         *
         * $(GRAMMAR $(RULEDEF module):
         *     $(RULE moduleDeclaration)? $(RULE declaration)*
         *     ;)
         */
        boolean parseModule()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Module m = allocator.make!Module;
            if (currentIs(tok("scriptLine")))
            m.scriptLine = advance();
            boolean isDeprecatedModule;
            if (currentIs(tok("deprecated")))
            {
                b = setBookmark();
                advance();
                if (currentIs(tok("(")))
                skipParens();
                isDeprecatedModule = currentIs(tok("module"));
                goToBookmark(b);
            }
            if (currentIs(tok("module")) || isDeprecatedModule)
            {
                c = allocator.setCheckpoint();
                m.moduleDeclaration = parseModuleDeclaration();
                if (m.moduleDeclaration == null)
                allocator.rollback(c);
            }
            StackBuffer declarations;
            while (moreTokens())
            {
                c = allocator.setCheckpoint();
                if (!declarations.put(parseDeclaration(true, true)))
                    allocator.rollback(c);
            }
            ownArray(m.declarations, declarations);
            assert(retVal != null);
            return m;

        }

        /**
         * Parses a ModuleDeclaration
         *
         * $(GRAMMAR $(RULEDEF moduleDeclaration):
         *     $(RULE deprecated)? $(LITERAL 'module') $(RULE identifierChain) $(LITERAL ';')
         *     ;)
         */
        boolean parseModuleDeclaration()
        {
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ModuleDeclaration,false);
            if (currentIs(tok("deprecated")))
            mixin(parseNodeQ("node.deprecated_", "Deprecated"));
         start = expect(tok("module"));
            mixin(nullCheck("start"));
            mixin(parseNodeQ("node.moduleName", "IdentifierChain"));
            node.comment = start.comment;
            if (node.comment == null)
            node.comment = start.trailingComment;
            comment = null;
         end = expect(tok(";"));
            node.startLocation = start.index;
            if (end != null)
            node.endLocation = end.index;
            return true;
        }

        /**
         * Parses a MulExpression.
         *
         * $(GRAMMAR $(RULEDEF mulExpression):
         *       $(RULE powExpression)
         *     | $(RULE mulExpression) ($(LITERAL '*') | $(LITERAL '/') | $(LITERAL '%')) $(RULE powExpression)
         *     ;)
         */
        boolean parseMulExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            return parseLeftAssocBinaryExpression(MulExpression, PowExpression,
            tok("*"), tok("/"), tok("%"));
        }

        /**
         * Parses a NewAnonClassExpression
         *
         * $(GRAMMAR $(RULEDEF newAnonClassExpression):
         *     $(LITERAL 'new') $(RULE arguments)? $(LITERAL 'class') $(RULE arguments)? $(RULE baseClassList)? $(RULE structBody)
         *     ;)
         */
        boolean parseNewAnonClassExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.NewAnonClassExpression,false);
            expect(tok("new"));
            if (currentIs(tok("(")))
            mixin(parseNodeQ("node.allocatorArguments", "Arguments"));
            expect(tok("class"));
            if (currentIs(tok("(")))
            mixin(parseNodeQ("node.ructorArguments", "Arguments"));
            if (!currentIs(tok("{")))
            mixin(parseNodeQ("node.baseClassList", "BaseClassList"));
            mixin(parseNodeQ("node.structBody", "StructBody"));
            return true;
        }

        /**
         * Parses a NewExpression
         *
         * $(GRAMMAR $(RULEDEF newExpression):
         *       $(LITERAL 'new') $(RULE type) (($(LITERAL '[') $(RULE assignExpression) $(LITERAL ']')) | $(RULE arguments))?
         *     | $(RULE newAnonClassExpression)
         *     ;)
         */
        boolean parseNewExpression()
        {
            // Parse ambiguity.
            // auto a = new int[10];
            //              ^^^^^^^
            // auto a = new int[10];
            //              ^^^****
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.NewExpression,false);
            if (peekIsOneOf(tok("class"), tok("(")))
            mixin(parseNodeQ("node.newAnonClassExpression", "NewAnonClassExpression"));
        else
            {
                expect(tok("new"));
                mixin(parseNodeQ("node.type", "Type"));
                if (currentIs(tok("[")))
                {
                    advance();
                    mixin(parseNodeQ("node.assignExpression", "AssignExpression"));
                    expect(tok("]"));
                }
            else if (currentIs(tok("(")))
                mixin(parseNodeQ("node.arguments", "Arguments"));
            }
            return true;
        }

        /**
         * Parses a NonVoidInitializer
         *
         * $(GRAMMAR $(RULEDEF nonVoidInitializer):
         *       $(RULE assignExpression)
         *     | $(RULE arrayInitializer)
         *     | $(RULE structInitializer)
         *     ;)
         */
        boolean parseNonVoidInitializer()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.NonVoidInitializer,false);
            if (currentIs(tok("{")))
            {
             b = peekPastBraces();
                if (b != null && (b.type == tok("(")))
                mixin(parseNodeQ("node.assignExpression", "AssignExpression"));
            else
                {
                    assert (currentIs(tok("{")));
                    auto m = setBookmark();
                    auto initializer = parseStructInitializer();
                    if (initializer != null)
                    {
                        node.structInitializer = initializer;
                        abandonBookmark(m);
                    }
                else
                    {
                        goToBookmark(m);
                        mixin(parseNodeQ("node.assignExpression", "AssignExpression"));
                    }
                }
            }
        else if (currentIs(tok("[")))
            {
             b = peekPastBrackets();
                if (b != null && (b.type == tok(",")
                || b.type == tok(")")
                || b.type == tok("]")
                || b.type == tok("}")
                || b.type == tok(";")))
                {
                    mixin(parseNodeQ("node.arrayInitializer", "ArrayInitializer"));
                }
            else
                mixin(parseNodeQ("node.assignExpression", "AssignExpression"));
            }
        else
            mixin(parseNodeQ("node.assignExpression", "AssignExpression"));
            if (node.assignExpression == null && node.arrayInitializer == null
            && node.structInitializer == null)
            return null;
            return true;
        }

        /**
         * Parses Operands
         *
         * $(GRAMMAR $(RULEDEF operands):
         *       $(RULE asmExp)
         *     | $(RULE asmExp) $(LITERAL ',') $(RULE operands)
         *     ;)
         */
        boolean parseOperands()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Operands node = allocator.make!Operands;
            StackBuffer expressions;
            while (true)
            {
                if (!expressions.put(parseAsmExp()))
                    return null;
                if (currentIs(tok(",")))
                advance();
            else
                break;
            }
            ownArray(node.operands, expressions);
            return true;
        }

        /**
         * Parses an OrExpression
         *
         * $(GRAMMAR $(RULEDEF orExpression):
         *       $(RULE xorExpression)
         *     | $(RULE orExpression) $(LITERAL '|') $(RULE xorExpression)
         *     ;)
         */
        boolean parseOrExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            return parseLeftAssocBinaryExpression(OrExpression, XorExpression,
            tok("|"));
        }

        /**
         * Parses an OrOrExpression
         *
         * $(GRAMMAR $(RULEDEF orOrExpression):
         *       $(RULE andAndExpression)
         *     | $(RULE orOrExpression) $(LITERAL '||') $(RULE andAndExpression)
         *     ;)
         */
        boolean parseOrOrExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            return parseLeftAssocBinaryExpression(OrOrExpression, AndAndExpression,
            tok("||"));
        }

        /**
         * Parses an OutStatement
         *
         * $(GRAMMAR $(RULEDEF outStatement):
         *     $(LITERAL 'out') ($(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '$(RPAREN)'))? $(RULE blockStatement)
         *     ;)
         */
        boolean parseOutStatement()
        {
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.OutStatement,false);
         o = expect(tok("out"));
            mixin(nullCheck("o"));
            node.outTokenLocation = o.index;
            if (currentIs(tok("(")))
            {
                advance();
             ident = expect(tok("identifier"));
                mixin (nullCheck("ident"));
                node.parameter = *ident;
                expect(tok(")"));
            }
            mixin(parseNodeQ("node.blockStatement", "BlockStatement"));
            return true;
        }

        /**
         * Parses a Parameter
         *
         * $(GRAMMAR $(RULEDEF parameter):
         *     $(RULE parameterAttribute)* $(RULE type)
         *     $(RULE parameterAttribute)* $(RULE type) $(LITERAL Identifier)? $(LITERAL '...')
         *     $(RULE parameterAttribute)* $(RULE type) $(LITERAL Identifier)? ($(LITERAL '=') $(RULE assignExpression))?
         *     ;)
         */
        boolean parseParameter()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Parameter,false);
            StackBuffer parameterAttributes;
            while (moreTokens())
            {
                IdType type = parseParameterAttribute(false);
                if (type == tok(""))
                break;
            else
                parameterAttributes.put(type);
            }
            ownArray(node.parameterAttributes, parameterAttributes);
            mixin(parseNodeQ("node.type", "Type"));
            if (currentIs(tok("identifier")))
            {
                node.name = advance();
                if (currentIs(tok("...")))
                {
                    advance();
                    node.vararg = true;
                }
            else if (currentIs(tok("=")))
                {
                    advance();
                    mixin(parseNodeQ("node.default_", "AssignExpression"));
                }
            else if (currentIs(tok("[")))
                {
                    StackBuffer typeSuffixes;
                    while(moreTokens() && currentIs(tok("[")))
                    if (!typeSuffixes.put(parseTypeSuffix()))
                        return null;
                    ownArray(node.cstyle, typeSuffixes);
                }
            }
        else if (currentIs(tok("...")))
            {
                node.vararg = true;
                advance();
            }
        else if (currentIs(tok("=")))
            {
                advance();
                mixin(parseNodeQ("node.default_", "AssignExpression"));
            }
            return true;
        }

        /**
         * Parses a ParameterAttribute
         *
         * $(GRAMMAR $(RULEDEF parameterAttribute):
         *       $(RULE typeConstructor)
         *     | $(LITERAL 'final')
         *     | $(LITERAL 'in')
         *     | $(LITERAL 'lazy')
         *     | $(LITERAL 'out')
         *     | $(LITERAL 'ref')
         *     | $(LITERAL 'scope')
         *     | $(LITERAL 'auto')
         *     | $(LITERAL 'return')
         *     ;)
         */
        Token.boolean parseParameterAttribute()
        {
            final boolean boolean validate = false;
//            mixin(traceEnterAndExit!(__FUNCTION__));
            switch (current().type)
            {
                case tok("immutable"):
                case tok("shared"):
                case tok(""):
                case tok("inout"):
                    if (peekIs(tok("(")))
                    return tok("");
            else
                goto case;
                case tok("final"):
                case tok("in"):
                case tok("lazy"):
                case tok("out"):
                case tok("ref"):
                case tok("scope"):
                case tok("auto"):
                case tok("return"):
                    return advance().type;
                default:
                    if (validate)
                        error("Parameter attribute expected");
                    return tok("");
            }
        }

        /**
         * Parses Parameters
         *
         * $(GRAMMAR $(RULEDEF parameters):
         *       $(LITERAL '$(LPAREN)') $(RULE parameter) ($(LITERAL ',') $(RULE parameter))* ($(LITERAL ',') $(LITERAL '...'))? $(LITERAL '$(RPAREN)')
         *     | $(LITERAL '$(LPAREN)') $(LITERAL '...') $(LITERAL '$(RPAREN)')
         *     | $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseParameters()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Parameters,false);
            if(!tokenCheck("(")){return false;}

            if (currentIs(tok(")")))
            {
                advance(); // )
                return true;
            }
            if (currentIs(tok("...")))
            {
                advance();
                node.hasVarargs = true;
                if(!tokenCheck(")")){return false;}
                return true;
            }
            StackBuffer parameters;
            while (moreTokens())
            {
                if (currentIs(tok("...")))
                {
                    advance();
                    node.hasVarargs = true;
                    break;
                }
                if (currentIs(tok(")")))
                break;
                if (!parameters.put(parseParameter()))
                    return null;
                if (currentIs(tok(",")))
                advance();
            else
                break;
            }
            ownArray(node.parameters, parameters);
            if(!tokenCheck(")")){return false;}
            return true;
        }

        /**
         * Parses a Postblit
         *
         * $(GRAMMAR $(RULEDEF postblit):
         *     $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL 'this') $(LITERAL '$(RPAREN)') $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ';'))
         *     ;)
         */
        boolean parsePostblit()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Postblit,false);
            node.line = current().line;
            node.column = current().column;
            node.location = current().index;
            index += 4;
            StackBuffer memberFunctionAttributes;
            while (currentIsMemberFunctionAttribute())
                if (!memberFunctionAttributes.put(parseMemberFunctionAttribute()))
                    return null;
            ownArray(node.memberFunctionAttributes, memberFunctionAttributes);
            if (currentIs(tok(";")))
            advance();
        else
            mixin(parseNodeQ("node.functionBody", "FunctionBody"));
            return true;
        }

        /**
         * Parses a PowExpression
         *
         * $(GRAMMAR $(RULEDEF powExpression):
         *       $(RULE unaryExpression)
         *     | $(RULE powExpression) $(LITERAL '^^') $(RULE unaryExpression)
         *     ;)
         */
        boolean parsePowExpression()
        {
//            mixin (traceEnterAndExit!(__FUNCTION__));
            return parseLeftAssocBinaryExpression(PowExpression, UnaryExpression,
            tok("^^"));
        }

        /**
         * Parses a PragmaDeclaration
         *
         * $(GRAMMAR $(RULEDEF pragmaDeclaration):
         *     $(RULE pragmaExpression) $(LITERAL ';')
         *     ;)
         */
        boolean parsePragmaDeclaration()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            mixin(simpleParse!(PragmaDeclaration, "pragmaExpression|parsePragmaExpression", tok(";")));
        }

        /**
         * Parses a PragmaExpression
         *
         * $(GRAMMAR $(RULEDEF pragmaExpression):
         *     $(RULE 'pragma') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) ($(LITERAL ',') $(RULE argumentList))? $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parsePragmaExpression()
        {
//            mixin (traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.PragmaExpression,false);
            expect(tok("pragma"));
            expect(tok("("));
         ident = expect(tok("identifier"));
            mixin(nullCheck("ident"));
            node.identifier = *ident;
            if (currentIs(tok(",")))
            {
                advance();
                mixin(parseNodeQ("node.argumentList", "ArgumentList"));
            }
            expect(tok(")"));
            return true;
        }

        /**
         * Parses a PrimaryExpression
         *
         * $(GRAMMAR $(RULEDEF primaryExpression):
         *       $(RULE identifierOrTemplateInstance)
         *     | $(LITERAL '.') $(RULE identifierOrTemplateInstance)
         *     | $(RULE typeConstructor) $(LITERAL '$(LPAREN)') $(RULE basicType) $(LITERAL '$(RPAREN)') $(LITERAL '.') $(LITERAL Identifier)
         *     | $(RULE basicType) $(LITERAL '.') $(LITERAL Identifier)
         *     | $(RULE basicType) $(RULE arguments)
         *     | $(RULE typeofExpression)
         *     | $(RULE typeidExpression)
         *     | $(RULE vector)
         *     | $(RULE arrayLiteral)
         *     | $(RULE assocArrayLiteral)
         *     | $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)')
         *     | $(RULE isExpression)
         *     | $(RULE functionLiteralExpression)
         *     | $(RULE traitsExpression)
         *     | $(RULE mixinExpression)
         *     | $(RULE importExpression)
         *     | $(LITERAL '$')
         *     | $(LITERAL 'this')
         *     | $(LITERAL 'super')
         *     | $(LITERAL '_null')
         *     | $(LITERAL '_true')
         *     | $(LITERAL '_false')
         *     | $(LITERAL '___DATE__')
         *     | $(LITERAL '___TIME__')
         *     | $(LITERAL '___TIMESTAMP__')
         *     | $(LITERAL '___VENDOR__')
         *     | $(LITERAL '___VERSION__')
         *     | $(LITERAL '___FILE__')
         *     | $(LITERAL '___LINE__')
         *     | $(LITERAL '___MODULE__')
         *     | $(LITERAL '___FUNCTION__')
         *     | $(LITERAL '___PRETTY_FUNCTION__')
         *     | $(LITERAL IntegerLiteral)
         *     | $(LITERAL FloatLiteral)
         *     | $(LITERAL StringLiteral)+
         *     | $(LITERAL CharacterLiteral)
         *     ;)
         */
        boolean parsePrimaryExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.PrimaryExpression,false);
            if (!moreTokens())
            {
                error("Expected primary statement instead of EOF");
                return null;
            }
            switch (current().type)
            {
                case tok("."):
                    node.dot = advance();
            goto case;
                case tok("identifier"):
                    if (peekIs(tok("=>")))
                    mixin(parseNodeQ("node.functionLiteralExpression", "FunctionLiteralExpression"));
            else
                    mixin(parseNodeQ("node.identifierOrTemplateInstance", "IdentifierOrTemplateInstance"));
                    break;
                case tok("immutable"):
                case tok(""):
                case tok("inout"):
                case tok("shared"):
                    advance();
                    expect(tok("("));
                    mixin(parseNodeQ("node.type", "Type"));
                    expect(tok(")"));
                    expect(tok("."));
             ident = expect(tok("identifier"));
                    if (ident != null)
                    node.primary = *ident;
                    break;
                foreach (B; BasicTypes) { case B: }
                node.basicType = advance();
                if (currentIs(tok(".")))
                {
                    advance();
                 t = expect(tok("identifier"));
                    if (t != null)
                    node.primary = *t;
                }
            else if (currentIs(tok("(")))
                mixin(parseNodeQ("node.arguments", "Arguments"));
                break;
                case tok("function"):
                case tok("delegate"):
                case tok("{"):
                case tok("in"):
                case tok("out"):
                case tok("body"):
                    mixin(parseNodeQ("node.functionLiteralExpression", "FunctionLiteralExpression"));
                    break;
                case tok("typeof"):
                    mixin(parseNodeQ("node.typeofExpression", "TypeofExpression"));
                    break;
                case tok("typeid"):
                    mixin(parseNodeQ("node.typeidExpression", "TypeidExpression"));
                    break;
                case tok("__vector"):
                    mixin(parseNodeQ("node.vector", "Vector"));
                    break;
                case tok("["):
                    if (isAssociativeArrayLiteral())
                        mixin(parseNodeQ("node.assocArrayLiteral", "AssocArrayLiteral"));
            else
                    mixin(parseNodeQ("node.arrayLiteral", "ArrayLiteral"));
                    break;
                case tok("("):
                    Bookmark b = setBookmark();
                    skipParens();
                    while (isAttribute())
                        parseAttribute();
                    if (currentIsOneOf(tok("=>"), tok("{")))
                {
                    goToBookmark(b);
                    mixin(parseNodeQ("node.functionLiteralExpression", "FunctionLiteralExpression"));
                }
            else
                {
                    goToBookmark(b);
                    advance();
                    mixin(parseNodeQ("node.expression", "Expression"));
                    if(!tokenCheck(")")){return false;}
                }
                break;
                case tok("is"):
                    mixin(parseNodeQ("node.isExpression", "IsExpression"));
                    break;
                case tok("__traits"):
                    mixin(parseNodeQ("node.traitsExpression", "TraitsExpression"));
                    break;
                case tok("mixin"):
                    mixin(parseNodeQ("node.mixinExpression", "MixinExpression"));
                    break;
                case tok("import"):
                    mixin(parseNodeQ("node.importExpression", "ImportExpression"));
                    break;
                case tok("this"):
                case tok("super"):
                    foreach (L; Literals) { case L: }
                if (currentIsOneOf(tok("StringLiteral"), tok("wStringLiteral"), tok("dStringLiteral")))
                {
                    node.primary = advance();
                    boolean alreadyWarned = false;
                    while (currentIsOneOf(tok("StringLiteral"), tok("wStringLiteral"),
                    tok("dStringLiteral")))
                    {
                        if (!alreadyWarned)
                        {
                            warn("Implicit concatenation of String literals");
                            alreadyWarned = true;
                        }
                        node.primary.text ~= advance().text;
                    }
                }
            else
                node.primary = advance();
                break;
                default:
                    error("Primary expression expected");
                    return null;
            }
            return true;
        }

        /**
         * Parses a Register
         *
         * $(GRAMMAR $(RULEDEF register):
         *       $(LITERAL Identifier)
         *     | $(LITERAL Identifier) $(LITERAL '$(LPAREN)') $(LITERAL IntegerLiteral) $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseRegister()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Register,false);
         ident = expect(tok("identifier"));
            mixin(nullCheck("ident"));
            node.identifier = *ident;
            if (currentIs(tok("(")))
            {
                advance();
             intLit = expect(tok("intLiteral"));
                mixin(nullCheck("intLit"));
                node.intLiteral = *intLit;
                expect(tok(")"));
            }
            return true;
        }

        /**
         * Parses a RelExpression
         *
         * $(GRAMMAR $(RULEDEF relExpression):
         *       $(RULE shiftExpression)
         *     | $(RULE relExpression) $(RULE relOperator) $(RULE shiftExpression)
         *     ;
         *$(RULEDEF relOperator):
         *       $(LITERAL '<')
         *     | $(LITERAL '<=')
         *     | $(LITERAL '>')
         *     | $(LITERAL '>=')
         *     | $(LITERAL '!<>=')
         *     | $(LITERAL '!<>')
         *     | $(LITERAL '<>')
         *     | $(LITERAL '<>=')
         *     | $(LITERAL '!>')
         *     | $(LITERAL '!>=')
         *     | $(LITERAL '!<')
         *     | $(LITERAL '!<=')
         *     ;)
         */
        boolean parseRelExpression()//(ExpressionNode shift)
        {
//            mixin (traceEnterAndExit!(__FUNCTION__));
            return parseLeftAssocBinaryExpression!(RelExpression, ShiftExpression,
            tok("<"), tok("<="), tok(">"), tok(">="), tok("!<>="), tok("!<>"),
            tok("<>"), tok("<>="), tok("!>"), tok("!>="), tok("!>="), tok("!<"),
            tok("!<="))(shift);
        }

        /**
         * Parses a ReturnStatement
         *
         * $(GRAMMAR $(RULEDEF returnStatement):
         *     $(LITERAL 'return') $(RULE expression)? $(LITERAL ';')
         *     ;)
         */
        boolean parseReturnStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ReturnStatement,false);
         start = expect(tok("return"));
            mixin(nullCheck("start"));
            node.startLocation = start.index;
            if (!currentIs(tok(";")))
            mixin(parseNodeQ("node.expression", "Expression"));
         semicolon = expect(tok(";"));
            mixin(nullCheck("semicolon"));
            node.endLocation = semicolon.index;
            return true;
        }

        /**
         * Parses a ScopeGuardStatement
         *
         * $(GRAMMAR $(RULEDEF scopeGuardStatement):
         *     $(LITERAL 'scope') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '$(RPAREN)') $(RULE statementNoCaseNoDefault)
         *     ;)
         */
        boolean parseScopeGuardStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ScopeGuardStatement,false);
            expect(tok("scope"));
            expect(tok("("));
         ident = expect(tok("identifier"));
            mixin(nullCheck("ident"));
            node.identifier = *ident;
            expect(tok(")"));
            mixin(parseNodeQ("node.statementNoCaseNoDefault", "StatementNoCaseNoDefault"));
            return true;
        }

        /**
         * Parses a SharedStaticConstructor
         *
         * $(GRAMMAR $(RULEDEF sharedStaticConstructor):
         *     $(LITERAL 'shared') $(LITERAL 'static') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE MemberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ";"))
         *     ;)
         */
        boolean parseSharedStaticConstructor()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.SharedStaticConstructor,false);
            node.location = current().index;
            if(!tokenCheck("shared")){return false;}
            if(!tokenCheck("static")){return false;}
            return parseStaticCtorDtorCommon(node);
        }

        /**
         * Parses a SharedStaticDestructor
         *
         * $(GRAMMAR $(RULEDEF sharedStaticDestructor):
         *     $(LITERAL 'shared') $(LITERAL 'static') $(LITERAL '~') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE MemberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ";"))
         *     ;)
         */
        boolean parseSharedStaticDestructor()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.SharedStaticDestructor,false);
            node.location = current().index;
            if(!tokenCheck("shared")){return false;}
            if(!tokenCheck("static")){return false;}
            if(!tokenCheck("~")){return false;}
            return parseStaticCtorDtorCommon(node);
        }

        /**
         * Parses a ShiftExpression
         *
         * $(GRAMMAR $(RULEDEF shiftExpression):
         *       $(RULE addExpression)
         *     | $(RULE shiftExpression) ($(LITERAL '<<') | $(LITERAL '>>') | $(LITERAL '>>>')) $(RULE addExpression)
         *     ;)
         */
        boolean parseShiftExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            return parseLeftAssocBinaryExpression(ShiftExpression, AddExpression,
            tok("<<"), tok(">>"), tok(">>>"));
        }

        /**
         * Parses a SingleImport
         *
         * $(GRAMMAR $(RULEDEF singleImport):
         *     ($(LITERAL Identifier) $(LITERAL '='))? $(RULE identifierChain)
         *     ;)
         */
        boolean parseSingleImport()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.SingleImport,false);
            if (startsWith(tok("identifier"), tok("=")))
            {
                node.rename = advance(); // identifier
                advance(); // =
            }
            mixin(parseNodeQ("node.identifierChain", "IdentifierChain"));
            if (node.identifierChain == null)
            return null;
            return true;
        }

        /**
         * Parses a Statement
         *
         * $(GRAMMAR $(RULEDEF statement):
         *       $(RULE statementNoCaseNoDefault)
         *     | $(RULE caseStatement)
         *     | $(RULE caseRangeStatement)
         *     | $(RULE defaultStatement)
         *     ;)
         */
        boolean parseStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Statement,false);
            if (!moreTokens())
            {
                error("Expected statement instead of EOF");
                return null;
            }
            switch (current().type)
            {
                case tok("case"):
                    advance();
                    auto argumentList = parseArgumentList();
                    if (argumentList == null)
                    return null;
                if (argumentList.items.length == 1 && startsWith(tok(":"), tok("..")))
                node.caseRangeStatement = parseCaseRangeStatement(argumentList.items[0]);
            else
                node.caseStatement = parseCaseStatement(argumentList);
                break;
                case tok("default"):
                    mixin(parseNodeQ("node.defaultStatement", "DefaultStatement"));
                    break;
                default:
                    mixin(parseNodeQ("node.statementNoCaseNoDefault", "StatementNoCaseNoDefault"));
                    break;
            }
            return true;
        }

        /**
         * Parses a StatementNoCaseNoDefault
         *
         * $(GRAMMAR $(RULEDEF statementNoCaseNoDefault):
         *       $(RULE labeledStatement)
         *     | $(RULE blockStatement)
         *     | $(RULE ifStatement)
         *     | $(RULE whileStatement)
         *     | $(RULE doStatement)
         *     | $(RULE forStatement)
         *     | $(RULE foreachStatement)
         *     | $(RULE switchStatement)
         *     | $(RULE finalSwitchStatement)
         *     | $(RULE continueStatement)
         *     | $(RULE breakStatement)
         *     | $(RULE returnStatement)
         *     | $(RULE gotoStatement)
         *     | $(RULE withStatement)
         *     | $(RULE synchronizedStatement)
         *     | $(RULE tryStatement)
         *     | $(RULE throwStatement)
         *     | $(RULE scopeGuardStatement)
         *     | $(RULE asmStatement)
         *     | $(RULE conditionalStatement)
         *     | $(RULE staticAssertStatement)
         *     | $(RULE versionSpecification)
         *     | $(RULE debugSpecification)
         *     | $(RULE expressionStatement)
         *     ;)
         */
        boolean parseStatementNoCaseNoDefault()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StatementNoCaseNoDefault,false);
            node.startLocation = current().index;
            switch (current().type)
            {
                case tok("{"):
                    mixin(parseNodeQ("node.blockStatement", "BlockStatement"));
                    break;
                case tok("if"):
                    mixin(parseNodeQ("node.ifStatement", "IfStatement"));
                    break;
                case tok("while"):
                    mixin(parseNodeQ("node.whileStatement", "WhileStatement"));
                    break;
                case tok("do"):
                    mixin(parseNodeQ("node.doStatement", "DoStatement"));
                    break;
                case tok("for"):
                    mixin(parseNodeQ("node.forStatement", "ForStatement"));
                    break;
                case tok("foreach"):
                case tok("foreach_reverse"):
                    mixin(parseNodeQ("node.foreachStatement", "ForeachStatement"));
                    break;
                case tok("switch"):
                    mixin(parseNodeQ("node.switchStatement", "SwitchStatement"));
                    break;
                case tok("continue"):
                    mixin(parseNodeQ("node.continueStatement", "ContinueStatement"));
                    break;
                case tok("break"):
                    mixin(parseNodeQ("node.breakStatement", "BreakStatement"));
                    break;
                case tok("return"):
                    mixin(parseNodeQ("node.returnStatement", "ReturnStatement"));
                    break;
                case tok("goto"):
                    mixin(parseNodeQ("node.gotoStatement", "GotoStatement"));
                    break;
                case tok("with"):
                    mixin(parseNodeQ("node.withStatement", "WithStatement"));
                    break;
                case tok("synchronized"):
                    mixin(parseNodeQ("node.synchronizedStatement", "SynchronizedStatement"));
                    break;
                case tok("try"):
                    mixin(parseNodeQ("node.tryStatement", "TryStatement"));
                    break;
                case tok("throw"):
                    mixin(parseNodeQ("node.throwStatement", "ThrowStatement"));
                    break;
                case tok("scope"):
                    mixin(parseNodeQ("node.scopeGuardStatement", "ScopeGuardStatement"));
                    break;
                case tok("asm"):
                    mixin(parseNodeQ("node.asmStatement", "AsmStatement"));
                    break;
                case tok("final"):
                    if (peekIs(tok("switch")))
                {
                    mixin(parseNodeQ("node.finalSwitchStatement", "FinalSwitchStatement"));
                    break;
                }
            else
                {
                    error(""switch" expected");
                    return null;
                }
                case tok("debug"):
                    if (peekIs(tok("=")))
                    mixin(parseNodeQ("node.debugSpecification", "DebugSpecification"));
            else
                    mixin(parseNodeQ("node.conditionalStatement", "ConditionalStatement"));
                    break;
                case tok("version"):
                    if (peekIs(tok("=")))
                    mixin(parseNodeQ("node.versionSpecification", "VersionSpecification"));
            else
                    mixin(parseNodeQ("node.conditionalStatement", "ConditionalStatement"));
                    break;
                case tok("static"):
                    if (peekIs(tok("if")))
                    mixin(parseNodeQ("node.conditionalStatement", "ConditionalStatement"));
            else if (peekIs(tok("assert")))
                    mixin(parseNodeQ("node.staticAssertStatement", "StaticAssertStatement"));
            else
                {
                    error("'if' or 'assert' expected.");
                    return null;
                }
                break;
                case tok("identifier"):
                    if (peekIs(tok(":")))
                {
                    mixin(parseNodeQ("node.labeledStatement", "LabeledStatement"));
                    break;
                }
            goto default;
                case tok("delete"):
                case tok("assert"):
                default:
                    mixin(parseNodeQ("node.expressionStatement", "ExpressionStatement"));
                    break;
            }
            node.endLocation = tokens[index - 1].index;
            return true;
        }

        /**
         * Parses a StaticAssertDeclaration
         *
         * $(GRAMMAR $(RULEDEF staticAssertDeclaration):
         *     $(RULE staticAssertStatement)
         *     ;)
         */
        boolean parseStaticAssertDeclaration()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            mixin(simpleParse!(StaticAssertDeclaration,
            "staticAssertStatement|parseStaticAssertStatement"));
        }


        /**
         * Parses a StaticAssertStatement
         *
         * $(GRAMMAR $(RULEDEF staticAssertStatement):
         *     $(LITERAL 'static') $(RULE assertExpression) $(LITERAL ';')
         *     ;)
         */
        boolean parseStaticAssertStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            mixin(simpleParse!(StaticAssertStatement,
            tok("static"), "assertExpression|parseAssertExpression", tok(";")));
        }

        /**
         * Parses a StaticConstructor
         *
         * $(GRAMMAR $(RULEDEF staticConstructor):
         *     $(LITERAL 'static') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ";"))
         *     ;)
         */
        boolean parseStaticConstructor()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StaticConstructor,false);
            node.location = current().index;
            if(!tokenCheck("static")){return false;}
            return parseStaticCtorDtorCommon(node);
        }

        /**
         * Parses a StaticDestructor
         *
         * $(GRAMMAR $(RULEDEF staticDestructor):
         *     $(LITERAL 'static') $(LITERAL '~') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ";"))
         *     ;)
         */
        boolean parseStaticDestructor()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StaticDestructor,false);
            node.location = current().index;
            if(!tokenCheck("static")){return false;}
            if(!tokenCheck("~")){return false;}
            return parseStaticCtorDtorCommon(node);
        }

        /**
         * Parses an StaticIfCondition
         *
         * $(GRAMMAR $(RULEDEF staticIfCondition):
         *     $(LITERAL 'static') $(LITERAL 'if') $(LITERAL '$(LPAREN)') $(RULE assignExpression) $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseStaticIfCondition()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            mixin(simpleParse!(StaticIfCondition, tok("static"), tok("if"), tok("("),
            "assignExpression|parseAssignExpression", tok(")")));
        }

        /**
         * Parses a StorageClass
         *
         * $(GRAMMAR $(RULEDEF storageClass):
         *       $(RULE alignAttribute)
         *     | $(RULE linkageAttribute)
         *     | $(RULE atAttribute)
         *     | $(RULE typeConstructor)
         *     | $(RULE deprecated)
         *     | $(LITERAL 'abstract')
         *     | $(LITERAL 'auto')
         *     | $(LITERAL 'enum')
         *     | $(LITERAL 'extern')
         *     | $(LITERAL 'final')
         *     | $(LITERAL '')
         *     | $(LITERAL 'override')
         *     | $(LITERAL '')
         *     | $(LITERAL 'ref')
         *     | $(LITERAL '___gshared')
         *     | $(LITERAL 'scope')
         *     | $(LITERAL 'static')
         *     | $(LITERAL 'synchronized')
         *     ;)
         */
        boolean parseStorageClass()
        {
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StorageClass,false);
            switch (current().type)
            {
                case tok("@"):
                    mixin(parseNodeQ("node.atAttribute", "AtAttribute"));
                    break;
                case tok("deprecated"):
                    mixin(parseNodeQ("node.deprecated_", "Deprecated"));
                    break;
                case tok("align"):
                    mixin(parseNodeQ("node.alignAttribute", "AlignAttribute"));
                    break;
                case tok("extern"):
                    if (peekIs(tok("(")))
                {
                    mixin(parseNodeQ("node.linkageAttribute", "LinkageAttribute"));
                    break;
                }
            else goto case;
                case tok(""):
                case tok("immutable"):
                case tok("inout"):
                case tok("shared"):
                case tok("abstract"):
                case tok("auto"):
                case tok("enum"):
                case tok("final"):
                case tok(""):
                case tok("override"):
                case tok(""):
                case tok("ref"):
                case tok("__gshared"):
                case tok("scope"):
                case tok("static"):
                case tok("synchronized"):
                    node.token = advance();
                    break;
                default:
                    error("Storage class expected");
                    return null;
            }
            return true;
        }

        /**
         * Parses a StructBody
         *
         * $(GRAMMAR $(RULEDEF structBody):
         *     $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
         *     ;)
         */
        boolean parseStructBody()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StructBody,false);
         start = expect(tok("{"));
            if (start != null) node.startLocation = start.index;
            StackBuffer declarations;
            while (!currentIs(tok("}")) && moreTokens())
            {
                c = allocator.setCheckpoint();
                if (!declarations.put(parseDeclaration(true, true)))
                    allocator.rollback(c);
            }
            ownArray(node.declarations, declarations);
         end = expect(tok("}"));
            if (end != null) node.endLocation = end.index;
            return true;
        }

        /**
         * Parses a StructDeclaration
         *
         * $(GRAMMAR $(RULEDEF structDeclaration):
         *     $(LITERAL 'struct') $(LITERAL Identifier)? ($(RULE templateParameters) $(RULE raint)? $(RULE structBody) | ($(RULE structBody) | $(LITERAL ';')))
         *     ;)
         */
        boolean parseStructDeclaration()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StructDeclaration,false);
         t = expect(tok("struct"));
            if (currentIs(tok("identifier")))
            node.name = advance();
        else
            {
                node.name.line = t.line;
                node.name.column = t.column;
            }
            node.comment = comment;
            comment = null;

            if (currentIs(tok("(")))
            {
                mixin(parseNodeQ("node.templateParameters", "TemplateParameters"));
                if (currentIs(tok("if")))
                mixin(parseNodeQ("node.raint", "Constraint"));
                mixin(parseNodeQ("node.structBody", "StructBody"));
            }
        else if (currentIs(tok("{")))
            {
                mixin(parseNodeQ("node.structBody", "StructBody"));
            }
        else if (currentIs(tok(";")))
            advance();
        else
            {
                error("Template Parameters, Struct Body, or Semicolon expected");
                return null;
            }
            return true;
        }

        /**
         * Parses an StructInitializer
         *
         * $(GRAMMAR $(RULEDEF structInitializer):
         *     $(LITERAL '{') $(RULE structMemberInitializers)? $(LITERAL '}')
         *     ;)
         */
        boolean parseStructInitializer()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StructInitializer,false);
         a = expect(tok("{"));
            node.startLocation = a.index;
            if (currentIs(tok("}")))
            {
                node.endLocation = current().index;
                advance();
            }
        else
            {
                mixin(parseNodeQ("node.structMemberInitializers", "StructMemberInitializers"));
             e = expect(tok("}"));
                mixin (nullCheck("e"));
                node.endLocation = e.index;
            }
            return true;
        }

        /**
         * Parses a StructMemberInitializer
         *
         * $(GRAMMAR $(RULEDEF structMemberInitializer):
         *     ($(LITERAL Identifier) $(LITERAL ':'))? $(RULE nonVoidInitializer)
         *     ;)
         */
        boolean parseStructMemberInitializer()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StructMemberInitializer,false);
            if (startsWith(tok("identifier"), tok(":")))
            {
                node.identifier = tokens[index++];
                index++;
            }
            mixin(parseNodeQ("node.nonVoidInitializer", "NonVoidInitializer"));
            return true;
        }

        /**
         * Parses StructMemberInitializers
         *
         * $(GRAMMAR $(RULEDEF structMemberInitializers):
         *     $(RULE structMemberInitializer) ($(LITERAL ',') $(RULE structMemberInitializer)?)*
         *     ;)
         */
        boolean parseStructMemberInitializers()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StructMemberInitializers,false);
            StackBuffer structMemberInitializers;
            do
            {
                auto c = allocator.setCheckpoint();
                if (!structMemberInitializers.put(parseStructMemberInitializer()))
                    allocator.rollback(c);
                if (currentIs(tok(",")))
                advance();
            else
                break;
            } while (moreTokens() && !currentIs(tok("}")));
            ownArray(node.structMemberInitializers, structMemberInitializers);
            return true;
        }

        /**
         * Parses a SwitchStatement
         *
         * $(GRAMMAR $(RULEDEF switchStatement):
         *     $(LITERAL 'switch') $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE statement)
         *     ;)
         */
        boolean parseSwitchStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.SwitchStatement,false);
            expect(tok("switch"));
            expect(tok("("));
            mixin(parseNodeQ("node.expression", "Expression"));
            expect(tok(")"));
            mixin(parseNodeQ("node.statement", "Statement"));
            return true;
        }

        /**
         * Parses a Symbol
         *
         * $(GRAMMAR $(RULEDEF symbol):
         *     $(LITERAL '.')? $(RULE identifierOrTemplateChain)
         *     ;)
         */
        boolean parseSymbol()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Symbol,false);
            if (currentIs(tok(".")))
            {
                node.dot = true;
                advance();
            }
            mixin(parseNodeQ("node.identifierOrTemplateChain", "IdentifierOrTemplateChain"));
            return true;
        }

        /**
         * Parses a SynchronizedStatement
         *
         * $(GRAMMAR $(RULEDEF synchronizedStatement):
         *     $(LITERAL 'synchronized') ($(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)'))? $(RULE statementNoCaseNoDefault)
         *     ;)
         */
        boolean parseSynchronizedStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.SynchronizedStatement,false);
            expect(tok("synchronized"));
            if (currentIs(tok("(")))
            {
                expect(tok("("));
                mixin(parseNodeQ("node.expression", "Expression"));
                expect(tok(")"));
            }
            mixin(parseNodeQ("node.statementNoCaseNoDefault", "StatementNoCaseNoDefault"));
            return true;
        }

        /**
         * Parses a TemplateAliasParameter
         *
         * $(GRAMMAR $(RULEDEF templateAliasParameter):
         *     $(LITERAL 'alias') $(RULE type)? $(LITERAL Identifier) ($(LITERAL ':') ($(RULE type) | $(RULE assignExpression)))? ($(LITERAL '=') ($(RULE type) | $(RULE assignExpression)))?
         *     ;)
         */
        boolean parseTemplateAliasParameter()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateAliasParameter,false);
            expect(tok("alias"));
            if (currentIs(tok("identifier")) && !peekIs(tok(".")))
            {
                if (peekIsOneOf(tok(","), tok(")"), tok("="), tok(":")))
                node.identifier = advance();
            else
                goto type;
            }
        else
            {
                type:
                mixin(parseNodeQ("node.type", "Type"));
             ident = expect(tok("identifier"));
                mixin(nullCheck("ident"));
                node.identifier = *ident;
            }

            if (currentIs(tok(":")))
            {
                advance();
                if (isType())
                    mixin(parseNodeQ("node.colonType", "Type"));
            else
                mixin(parseNodeQ("node.colonExpression", "AssignExpression"));
            }
            if (currentIs(tok("=")))
            {
                advance();
                if (isType())
                    mixin(parseNodeQ("node.assignType", "Type"));
            else
                mixin(parseNodeQ("node.assignExpression", "AssignExpression"));
            }
            return true;
        }

        /**
         * Parses a TemplateArgument
         *
         * $(GRAMMAR $(RULEDEF templateArgument):
         *       $(RULE type)
         *     | $(RULE assignExpression)
         *     ;)
         */
        boolean parseTemplateArgument()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            if (suppressedErrorCount > MAX_ERRORS) return null;
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateArgument,false);
            Bookmark b = setBookmark();
            auto t = parseType();
            if (t != null && currentIsOneOf(tok(","), tok(")")))
            {
                abandonBookmark(b);
                node.type = t;
            }
        else
            {
                goToBookmark(b);
                mixin(parseNodeQ("node.assignExpression", "AssignExpression"));
            }
            return true;
        }

        /**
         * Parses a TemplateArgumentList
         *
         * $(GRAMMAR $(RULEDEF templateArgumentList):
         *     $(RULE templateArgument) ($(LITERAL ',') $(RULE templateArgument)?)*
         *     ;)
         */
        boolean parseTemplateArgumentList()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            return parseCommaSeparatedRule!(TemplateArgumentList, TemplateArgument)(true);
        }

        /**
         * Parses TemplateArguments
         *
         * $(GRAMMAR $(RULEDEF templateArguments):
         *     $(LITERAL '!') ($(LITERAL '$(LPAREN)') $(RULE templateArgumentList)? $(LITERAL '$(RPAREN)')) | $(RULE templateSingleArgument)
         *     ;)
         */
        boolean parseTemplateArguments()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            if (suppressedErrorCount > MAX_ERRORS) return null;
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateArguments,false);
            expect(tok("!"));
            if (currentIs(tok("(")))
            {
                advance();
                if (!currentIs(tok(")")))
                mixin(parseNodeQ("node.templateArgumentList", "TemplateArgumentList"));
                if(!tokenCheck(")")){return false;}
            }
        else
            mixin(parseNodeQ("node.templateSingleArgument", "TemplateSingleArgument"));
            return true;
        }

        /**
         * Parses a TemplateDeclaration
         *
         * $(GRAMMAR $(RULEDEF templateDeclaration):
         *       $(LITERAL 'template') $(LITERAL Identifier) $(RULE templateParameters) $(RULE raint)? $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
         *     ;)
         */
        boolean parseTemplateDeclaration()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateDeclaration,false);
            node.comment = comment;
            comment = null;
            expect(tok("template"));
         ident = expect(tok("identifier"));
            mixin(nullCheck("ident"));
            node.name = *ident;
            mixin(parseNodeQ("node.templateParameters", "TemplateParameters"));
            if (currentIs(tok("if")))
            mixin(parseNodeQ("node.raint", "Constraint"));
         start = expect(tok("{"));
            mixin(nullCheck("start"));
            node.startLocation = start.index;
            StackBuffer declarations;
            while (moreTokens() && !currentIs(tok("}")))
            {
                c = allocator.setCheckpoint();
                if (!declarations.put(parseDeclaration(true, true)))
                    allocator.rollback(c);
            }
            ownArray(node.declarations, declarations);
         end = expect(tok("}"));
            if (end != null) node.endLocation = end.index;
            return true;
        }

        /**
         * Parses a TemplateInstance
         *
         * $(GRAMMAR $(RULEDEF templateInstance):
         *     $(LITERAL Identifier) $(RULE templateArguments)
         *     ;)
         */
        boolean parseTemplateInstance()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            if (suppressedErrorCount > MAX_ERRORS) return null;
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateInstance,false);
         ident = expect(tok("identifier"));
            mixin(nullCheck("ident"));
            node.identifier = *ident;
            mixin(parseNodeQ("node.templateArguments", "TemplateArguments"));
            if (node.templateArguments == null)
            return null;
            return true;
        }

        /**
         * Parses a TemplateMixinExpression
         *
         * $(GRAMMAR $(RULEDEF templateMixinExpression):
         *     $(LITERAL 'mixin') $(RULE mixinTemplateName) $(RULE templateArguments)? $(LITERAL Identifier)?
         *     ;)
         */
        boolean parseTemplateMixinExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateMixinExpression,false);
            if(!tokenCheck("mixin")){return false;}
            mixin(parseNodeQ("node.mixinTemplateName", "MixinTemplateName"));
            if (currentIs(tok("!")))
            mixin(parseNodeQ("node.templateArguments", "TemplateArguments"));
            if (currentIs(tok("identifier")))
            node.identifier = advance();
            return true;
        }

        /**
         * Parses a TemplateParameter
         *
         * $(GRAMMAR $(RULEDEF templateParameter):
         *       $(RULE templateTypeParameter)
         *     | $(RULE templateValueParameter)
         *     | $(RULE templateAliasParameter)
         *     | $(RULE templateTupleParameter)
         *     | $(RULE templateThisParameter)
         *     ;)
         */
        boolean parseTemplateParameter()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateParameter,false);
            switch (current().type)
            {
                case tok("alias"):
                    mixin(parseNodeQ("node.templateAliasParameter", "TemplateAliasParameter"));
                    break;
                case tok("identifier"):
                    if (peekIs(tok("...")))
                    mixin(parseNodeQ("node.templateTupleParameter", "TemplateTupleParameter"));
            else if (peekIsOneOf(tok(":"), tok("="), tok(","), tok(")")))
                    mixin(parseNodeQ("node.templateTypeParameter", "TemplateTypeParameter"));
            else
                    mixin(parseNodeQ("node.templateValueParameter", "TemplateValueParameter"));
                    break;
                case tok("this"):
                    mixin(parseNodeQ("node.templateThisParameter", "TemplateThisParameter"));
                    break;
                default:
                    mixin(parseNodeQ("node.templateValueParameter", "TemplateValueParameter"));
                    break;
            }
            return true;
        }

        /**
         * Parses a TemplateParameterList
         *
         * $(GRAMMAR $(RULEDEF templateParameterList):
         *     $(RULE templateParameter) ($(LITERAL ',') $(RULE templateParameter)?)* $(LITERAL ',')?
         *     ;)
         */
        boolean parseTemplateParameterList()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            return parseCommaSeparatedRule!(TemplateParameterList, TemplateParameter)(true);
        }

        /**
         * Parses TemplateParameters
         *
         * $(GRAMMAR $(RULEDEF templateParameters):
         *     $(LITERAL '$(LPAREN)') $(RULE templateParameterList)? $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseTemplateParameters()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateParameters,false);
            if(!tokenCheck("(")){return false;}
            if (!currentIs(tok(")")))
            mixin(parseNodeQ("node.templateParameterList", "TemplateParameterList"));
            if(!tokenCheck(")")){return false;}
            return true;
        }

        /**
         * Parses a TemplateSingleArgument
         *
         * $(GRAMMAR $(RULEDEF templateSingleArgument):
         *       $(RULE builtinType)
         *     | $(LITERAL Identifier)
         *     | $(LITERAL CharacterLiteral)
         *     | $(LITERAL StringLiteral)
         *     | $(LITERAL IntegerLiteral)
         *     | $(LITERAL FloatLiteral)
         *     | $(LITERAL '_true')
         *     | $(LITERAL '_false')
         *     | $(LITERAL '_null')
         *     | $(LITERAL 'this')
         *     | $(LITERAL '___DATE__')
         *     | $(LITERAL '___TIME__')
         *     | $(LITERAL '___TIMESTAMP__')
         *     | $(LITERAL '___VENDOR__')
         *     | $(LITERAL '___VERSION__')
         *     | $(LITERAL '___FILE__')
         *     | $(LITERAL '___LINE__')
         *     | $(LITERAL '___MODULE__')
         *     | $(LITERAL '___FUNCTION__')
         *     | $(LITERAL '___PRETTY_FUNCTION__')
         *     ;)
         */
        boolean parseTemplateSingleArgument()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateSingleArgument,false);
            if (!moreTokens)
            {
                error("template argument expected instead of EOF");
                return null;
            }
            switch (current().type)
            {
                case tok("this"):
                case tok("identifier"):
                    foreach (B; Literals) { case B: }
                foreach (C; BasicTypes) { case C: }
                node.token = advance();
                break;
                default:
                    error("Invalid template argument. (Try enclosing in parenthesis?)");
                    return null;
            }
            return true;
        }

        /**
         * Parses a TemplateThisParameter
         *
         * $(GRAMMAR $(RULEDEF templateThisParameter):
         *     $(LITERAL 'this') $(RULE templateTypeParameter)
         *     ;)
         */
        boolean parseTemplateThisParameter()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateThisParameter,false);
            expect(tok("this"));
            mixin(parseNodeQ("node.templateTypeParameter", "TemplateTypeParameter"));
            return true;
        }

        /**
         * Parses an TemplateTupleParameter
         *
         * $(GRAMMAR $(RULEDEF templateTupleParameter):
         *     $(LITERAL Identifier) $(LITERAL '...')
         *     ;)
         */
        boolean parseTemplateTupleParameter()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateTupleParameter,false);
         i = expect(tok("identifier"));
            if (i == null)
            return null;
            node.identifier = *i;
            if(!tokenCheck("...")){return false;}
            return true;
        }

        /**
         * Parses a TemplateTypeParameter
         *
         * $(GRAMMAR $(RULEDEF templateTypeParameter):
         *     $(LITERAL Identifier) ($(LITERAL ':') $(RULE type))? ($(LITERAL '=') $(RULE type))?
         *     ;)
         */
        boolean parseTemplateTypeParameter()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateTypeParameter,false);
         ident = expect(tok("identifier"));
            mixin(nullCheck("ident"));
            node.identifier = *ident;
            if (currentIs(tok(":")))
            {
                advance();
                mixin(parseNodeQ("node.colonType", "Type"));
            }
            if (currentIs(tok("=")))
            {
                advance();
                mixin(parseNodeQ("node.assignType", "Type"));
            }
            return true;
        }

        /**
         * Parses a TemplateValueParameter
         *
         * $(GRAMMAR $(RULEDEF templateValueParameter):
         *     $(RULE type) $(LITERAL Identifier) ($(LITERAL ':') $(RULE assignExpression))? $(RULE templateValueParameterDefault)?
         *     ;)
         */
        boolean parseTemplateValueParameter()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateValueParameter,false);
            mixin(parseNodeQ("node.type", "Type"));
            mixin(tokenCheck!("node.identifier", "identifier"));
            if (currentIs(tok(":")))
            {
                advance();
                mixin(parseNodeQ("node.assignExpression", "AssignExpression"));
            }
            if (currentIs(tok("=")))
            mixin(parseNodeQ("node.templateValueParameterDefault", "TemplateValueParameterDefault"));
            return true;
        }

        /**
         * Parses a TemplateValueParameterDefault
         *
         * $(GRAMMAR $(RULEDEF templateValueParameterDefault):
         *     $(LITERAL '=') ($(LITERAL '___FILE__') | $(LITERAL '___MODULE__') | $(LITERAL '___LINE__') | $(LITERAL '___FUNCTION__') | $(LITERAL '___PRETTY_FUNCTION__') | $(RULE assignExpression))
         *     ;)
         */
        boolean parseTemplateValueParameterDefault()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateValueParameterDefault,false);
            expect(tok("="));
            switch (current().type)
            {
                case tok("__FILE__"):
                case tok("__MODULE__"):
                case tok("__LINE__"):
                case tok("__FUNCTION__"):
                case tok("__PRETTY_FUNCTION__"):
                    node.token = advance();
                    break;
                default:
                    mixin(parseNodeQ("node.assignExpression", "AssignExpression"));
                    break;
            }
            return true;
        }

        /**
         * Parses a TernaryExpression
         *
         * $(GRAMMAR $(RULEDEF ternaryExpression):
         *     $(RULE orOrExpression) ($(LITERAL '?') $(RULE expression) $(LITERAL ':') $(RULE ternaryExpression))?
         *     ;)
         */
        boolean parseTernaryExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));

            auto orOrExpression = parseOrOrExpression();
            if (orOrExpression == null)
            return null;
            if (currentIs(tok("?")))
            {
                TernaryExpression node = allocator.make!TernaryExpression;
                node.orOrExpression = orOrExpression;
                advance();
                mixin(parseNodeQ("node.expression", "Expression"));
                auto colon = expect(tok(":"));
                mixin(nullCheck("colon"));
                node.colon = *colon;
                mixin(parseNodeQ("node.ternaryExpression", "TernaryExpression"));
                return true;
            }
            return orOrExpression;
        }

        /**
         * Parses a ThrowStatement
         *
         * $(GRAMMAR $(RULEDEF throwStatement):
         *     $(LITERAL 'throw') $(RULE expression) $(LITERAL ';')
         *     ;)
         */
        boolean parseThrowStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ThrowStatement,false);
            expect(tok("throw"));
            mixin(parseNodeQ("node.expression", "Expression"));
            expect(tok(";"));
            return true;
        }

        /**
         * Parses an TraitsExpression
         *
         * $(GRAMMAR $(RULEDEF traitsExpression):
         *     $(LITERAL '___traits') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL ',') $(RULE TemplateArgumentList) $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseTraitsExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TraitsExpression,false);
            if(!tokenCheck("__traits")){return false;}
            if(!tokenCheck("(")){return false;}
         ident = expect(tok("identifier"));
            mixin(nullCheck("ident"));
            node.identifier = *ident;
            if (currentIs(tok(",")))
            {
                advance();
                mixin (nullCheck("(node.templateArgumentList = parseTemplateArgumentList())"));
            }
            if(!tokenCheck(")")){return false;}
            return true;
        }

        /**
         * Parses a TryStatement
         *
         * $(GRAMMAR $(RULEDEF tryStatement):
         *     $(LITERAL 'try') $(RULE declarationOrStatement) ($(RULE catches) | $(RULE catches) $(RULE finally) | $(RULE finally))
         *     ;)
         */
        boolean parseTryStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TryStatement,false);
            expect(tok("try"));
            mixin(parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement"));
            if (currentIs(tok("catch")))
            mixin(parseNodeQ("node.catches", "Catches"));
            if (currentIs(tok("finally")))
            mixin(parseNodeQ("node.finally_", "Finally"));
            return true;
        }

        /**
         * Parses a Type
         *
         * $(GRAMMAR $(RULEDEF type):
         *     $(RULE typeConstructors)? $(RULE type2) $(RULE typeSuffix)*
         *     ;)
         */
        boolean parseType()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Type,false);
            if (!moreTokens)
            {
                error("type expected");
                return null;
            }
            switch (current().type)
            {
                case tok(""):
                case tok("immutable"):
                case tok("inout"):
                case tok("shared"):
                    if (!peekIs(tok("(")))
                    mixin(parseNodeQ("node.typeConstructors", "TypeConstructors"));
                    break;
                default:
                    break;
            }
            mixin(parseNodeQ("node.type2", "Type2"));
            StackBuffer typeSuffixes;
            loop: while (moreTokens()) switch (current().type)
            {
                case tok("["):
                    // Allow this to fail because of the madness that == the
                    // newExpression rule. Something starting with '[' may be arguments
                    // to the newExpression instead of part of the type
                    auto newBookmark = setBookmark();
                    auto c = allocator.setCheckpoint();
                    if (typeSuffixes.put(parseTypeSuffix()))
                        abandonBookmark(newBookmark);
                    else
                    {
                        allocator.rollback(c);
                        goToBookmark(newBookmark);
                        break loop;
                    }
                    break;
                case tok("*"):
                case tok("delegate"):
                case tok("function"):
                    if (!typeSuffixes.put(parseTypeSuffix()))
                        return null;
                    break;
                default:
                    break loop;
            }
            ownArray(node.typeSuffixes, typeSuffixes);
            return true;
        }

        /**
         * Parses a Type2
         *
         * $(GRAMMAR $(RULEDEF type2):
         *       $(RULE builtinType)
         *     | $(RULE symbol)
         *     | $(LITERAL 'super') $(LITERAL '.') $(RULE identifierOrTemplateChain)
         *     | $(LITERAL 'this') $(LITERAL '.') $(RULE identifierOrTemplateChain)
         *     | $(RULE typeofExpression) ($(LITERAL '.') $(RULE identifierOrTemplateChain))?
         *     | $(RULE typeConstructor) $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL '$(RPAREN)')
         *     | $(RULE vector)
         *     ;)
         */
        boolean parseType2()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Type2,false);
            if (!moreTokens)
            {
                error("type2 expected instead of EOF");
                return null;
            }
            switch (current().type)
            {
                case tok("identifier"):
                case tok("."):
                    mixin(parseNodeQ("node.symbol", "Symbol"));
                    break;
                foreach (B; BasicTypes) { case B: }
                node.builtinType = parseBuiltinType();
                break;
                case tok("super"):
                case tok("this"):
                    node.superOrThis = advance().type;
                    if(!tokenCheck(".")){return false;}
                    mixin(parseNodeQ("node.identifierOrTemplateChain", "IdentifierOrTemplateChain"));
                    break;
                case tok("typeof"):
                    if ((node.typeofExpression = parseTypeofExpression()) == null)
                    return null;
                if (currentIs(tok(".")))
                {
                    advance();
                    mixin(parseNodeQ("node.identifierOrTemplateChain", "IdentifierOrTemplateChain"));
                }
                break;
                case tok(""):
                case tok("immutable"):
                case tok("inout"):
                case tok("shared"):
                    node.typeConstructor = advance().type;
                    if(!tokenCheck("(")){return false;}
                    mixin (nullCheck("(node.type = parseType())"));
                    if(!tokenCheck(")")){return false;}
                    break;
                case tok("__vector"):
                    if ((node.vector = parseVector()) == null)
                    return null;
                break;
                default:
                    error("Basic type, type ructor, symbol, or typeof expected");
                    return null;
            }
            return true;
        }

        /**
         * Parses a TypeConstructor
         *
         * $(GRAMMAR $(RULEDEF typeConstructor):
         *       $(LITERAL '')
         *     | $(LITERAL 'immutable')
         *     | $(LITERAL 'inout')
         *     | $(LITERAL 'shared')
         *     ;)
         */
        Token.boolean parseTypeConstructor()
        {
            final boolean validate = true;
//            mixin(traceEnterAndExit!(__FUNCTION__));
            switch (current().type)
            {
                case tok(""):
                case tok("immutable"):
                case tok("inout"):
                case tok("shared"):
                    if (!peekIs(tok("(")))
                    return advance().type;
            goto default;
                default:
                    if (validate)
                        error(""", "immutable", "inout", or "shared" expected");
                    return tok("");
            }
        }

        /**
         * Parses TypeConstructors
         *
         * $(GRAMMAR $(RULEDEF typeConstructors):
         *     $(RULE typeConstructor)+
         *     ;)
         */
        IdType[]boolean parseTypeConstructors()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            IdType[] r;
            while (moreTokens())
            {
                IdType type = parseTypeConstructor(false);
                if (type == tok(""))
                break;
            else
                r ~= type;
            }
            return r;
        }

        /**
         * Parses a TypeSpecialization
         *
         * $(GRAMMAR $(RULEDEF typeSpecialization):
         *       $(RULE type)
         *     | $(LITERAL 'struct')
         *     | $(LITERAL 'union')
         *     | $(LITERAL 'class')
         *     | $(LITERAL 'interface')
         *     | $(LITERAL 'enum')
         *     | $(LITERAL 'function')
         *     | $(LITERAL 'delegate')
         *     | $(LITERAL 'super')
         *     | $(LITERAL '')
         *     | $(LITERAL 'immutable')
         *     | $(LITERAL 'inout')
         *     | $(LITERAL 'shared')
         *     | $(LITERAL 'return')
         *     | $(LITERAL 'typedef')
         *     | $(LITERAL '___parameters')
         *     ;)
         */
        boolean parseTypeSpecialization()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TypeSpecialization,false);
            switch (current().type)
            {
                case tok("struct"):
                case tok("union"):
                case tok("class"):
                case tok("interface"):
                case tok("enum"):
                case tok("function"):
                case tok("delegate"):
                case tok("super"):
                case tok("return"):
                case tok("typedef"):
                case tok("__parameters"):
                case tok(""):
                case tok("immutable"):
                case tok("inout"):
                case tok("shared"):
                    if (peekIsOneOf(tok(")"), tok(",")))
                {
                    node.token = advance();
                    break;
                }
            goto default;
                default:
                    mixin(parseNodeQ("node.type", "Type"));
                    break;
            }
            return true;
        }

        /**
         * Parses a TypeSuffix
         *
         * $(GRAMMAR $(RULEDEF typeSuffix):
         *       $(LITERAL '*')
         *     | $(LITERAL '[') $(RULE type)? $(LITERAL ']')
         *     | $(LITERAL '[') $(RULE assignExpression) $(LITERAL ']')
         *     | $(LITERAL '[') $(RULE assignExpression) $(LITERAL '..')  $(RULE assignExpression) $(LITERAL ']')
         *     | ($(LITERAL 'delegate') | $(LITERAL 'function')) $(RULE parameters) $(RULE memberFunctionAttribute)*
         *     ;)
         */
        boolean parseTypeSuffix()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TypeSuffix,false);
            switch (current().type)
            {
                case tok("*"):
                    node.star = advance();
                    return true;
                case tok("["):
                    node.array = true;
                    advance();
                    if (currentIs(tok("]")))
                {
                    advance();
                    return true;
                }
                auto bookmark = setBookmark();
                auto type = parseType();
                if (type != null && currentIs(tok("]")))
                {
                    abandonBookmark(bookmark);
                    node.type = type;
                }
            else
                {
                    goToBookmark(bookmark);
                    mixin(parseNodeQ("node.low", "AssignExpression"));
                    mixin (nullCheck("node.low"));
                    if (currentIs(tok("..")))
                    {
                        advance();
                        mixin(parseNodeQ("node.high", "AssignExpression"));
                        mixin (nullCheck("node.high"));
                    }
                }
                if(!tokenCheck("]")){return false;}
                return true;
                case tok("delegate"):
                case tok("function"):
                    node.delegateOrFunction = advance();
                    mixin(parseNodeQ("node.parameters", "Parameters"));
                    StackBuffer memberFunctionAttributes;
                    while (currentIsMemberFunctionAttribute())
                        if (!memberFunctionAttributes.put(parseMemberFunctionAttribute()))
                            return null;
                    ownArray(node.memberFunctionAttributes, memberFunctionAttributes);
                    return true;
                default:
                    error(""*", "[", "delegate", or "function" expected.");
                    return null;
            }
        }

        /**
         * Parses a TypeidExpression
         *
         * $(GRAMMAR $(RULEDEF typeidExpression):
         *     $(LITERAL 'typeid') $(LITERAL '$(LPAREN)') ($(RULE type) | $(RULE expression)) $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseTypeidExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TypeidExpression,false);
            expect(tok("typeid"));
            expect(tok("("));
            Bookmark b = setBookmark();
            auto t = parseType();
            if (t == null || !currentIs(tok(")")))
            {
                goToBookmark(b);
                mixin(parseNodeQ("node.expression", "Expression"));
                mixin (nullCheck("node.expression"));
            }
        else
            {
                abandonBookmark(b);
                node.type = t;
            }
            expect(tok(")"));
            return true;
        }

        /**
         * Parses a TypeofExpression
         *
         * $(GRAMMAR $(RULEDEF typeofExpression):
         *     $(LITERAL 'typeof') $(LITERAL '$(LPAREN)') ($(RULE expression) | $(LITERAL 'return')) $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseTypeofExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TypeofExpression,false);
            expect(tok("typeof"));
            expect(tok("("));
            if (currentIs(tok("return")))
            node.return_ = advance();
        else
            mixin(parseNodeQ("node.expression", "Expression"));
            expect(tok(")"));
            return true;
        }

        /**
         * Parses a UnaryExpression
         *
         * $(GRAMMAR $(RULEDEF unaryExpression):
         *       $(RULE primaryExpression)
         *     | $(LITERAL '&') $(RULE unaryExpression)
         *     | $(LITERAL '!') $(RULE unaryExpression)
         *     | $(LITERAL '*') $(RULE unaryExpression)
         *     | $(LITERAL '+') $(RULE unaryExpression)
         *     | $(LITERAL '-') $(RULE unaryExpression)
         *     | $(LITERAL '~') $(RULE unaryExpression)
         *     | $(LITERAL '++') $(RULE unaryExpression)
         *     | $(LITERAL '--') $(RULE unaryExpression)
         *     | $(RULE newExpression)
         *     | $(RULE deleteExpression)
         *     | $(RULE castExpression)
         *     | $(RULE assertExpression)
         *     | $(RULE functionCallExpression)
         *     | $(RULE indexExpression)
         *     | $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL '$(RPAREN)') $(LITERAL '.') $(RULE identifierOrTemplateInstance)
         *     | $(RULE unaryExpression) $(LITERAL '.') $(RULE newExpression)
         *     | $(RULE unaryExpression) $(LITERAL '.') $(RULE identifierOrTemplateInstance)
         *     | $(RULE unaryExpression) $(LITERAL '--')
         *     | $(RULE unaryExpression) $(LITERAL '++')
         *     ;)
         */
        boolean parseUnaryExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            if (!moreTokens())
                return null;
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.UnaryExpression,false);
            switch (current().type)
            {
                case tok(""):
                case tok("immutable"):
                case tok("inout"):
                case tok("shared"):
                    Bookmark b = setBookmark();
                    if (peekIs(tok("(")))
                {
                    advance();
                 past = peekPastParens();
                    if (past != null && past.type == tok("."))
                    {
                        goToBookmark(b);
                    goto default;
                    }
                }
                goToBookmark(b);
            goto case;
                case tok("scope"):
                case tok(""):
                case tok(""):
                    mixin(parseNodeQ("node.functionCallExpression", "FunctionCallExpression"));
                    break;
                case tok("&"):
                case tok("!"):
                case tok("*"):
                case tok("+"):
                case tok("-"):
                case tok("~"):
                case tok("++"):
                case tok("--"):
                    node.prefix = advance();
                    mixin(parseNodeQ("node.unaryExpression", "UnaryExpression"));
                    break;
                case tok("new"):
                    mixin(parseNodeQ("node.newExpression", "NewExpression"));
                    break;
                case tok("delete"):
                    mixin(parseNodeQ("node.deleteExpression", "DeleteExpression"));
                    break;
                case tok("cast"):
                    mixin(parseNodeQ("node.castExpression", "CastExpression"));
                    break;
                case tok("assert"):
                    mixin(parseNodeQ("node.assertExpression", "AssertExpression"));
                    break;
                case tok("("):
                    // handle (type).identifier
                    Bookmark b = setBookmark();
                    skipParens();
                    if (startsWith(tok("."), tok("identifier")))
                    {
                        // go back to the (
                        goToBookmark(b);
                        Bookmark b2 = setBookmark();
                        advance();
                        auto t = parseType();
                        if (t == null || !currentIs(tok(")")))
                        {
                            goToBookmark(b);
                        goto default;
                        }
                        abandonBookmark(b2);
                        node.type = t;
                        advance(); // )
                        advance(); // .
                        mixin(parseNodeQ("node.identifierOrTemplateInstance", "IdentifierOrTemplateInstance"));
                        break;
                    }
            else
                {
                    // not (type).identifier, so treat as primary expression
                    goToBookmark(b);
                goto default;
                }
                default:
                    mixin(parseNodeQ("node.primaryExpression", "PrimaryExpression"));
                    break;
            }

            loop: while (moreTokens()) switch (current().type)
            {
                case tok("!"):
                    if (peekIs(tok("(")))
                {
                    index++;
                 p = peekPastParens();
                    boolean jump =  (currentIs(tok("(")) && p != null && p.type == tok("("))
                    || peekIs(tok("("));
                    index--;
                    if (jump)
                    goto case tok("(");
                else
                    break loop;
                }
            else
                break loop;
                case tok("("):
                    auto newUnary = allocator.make!UnaryExpression();
                    mixin (nullCheck("newUnary.functionCallExpression = parseFunctionCallExpression(node)"));
                    node = newUnary;
                    break;
                case tok("++"):
                case tok("--"):
//                    auto n = allocator.make!UnaryExpression();
                    n.unaryExpression = node;
                    n.suffix = advance();
                    node = n;
                    break;
                case tok("["):
//                    auto n = allocator.make!UnaryExpression;
                    n.indexExpression = parseIndexExpression(node);
                    node = n;
                    break;
                case tok("."):
                    advance();
//                    auto n = allocator.make!UnaryExpression();
                    n.unaryExpression = node;
                    if (currentIs(tok("new")))
                    mixin(parseNodeQ("node.newExpression", "NewExpression"));
            else
                    n.identifierOrTemplateInstance = parseIdentifierOrTemplateInstance();
                    node = n;
                    break;
                default:
                    break loop;
            }
            return true;
        }

        /**
         * Parses an UnionDeclaration
         *
         * $(GRAMMAR $(RULEDEF unionDeclaration):
         *       $(LITERAL 'union') $(LITERAL Identifier) $(RULE templateParameters) $(RULE raint)? $(RULE structBody)
         *     | $(LITERAL 'union') $(LITERAL Identifier) ($(RULE structBody) | $(LITERAL ';'))
         *     | $(LITERAL 'union') $(RULE structBody)
         *     ;)
         */
        boolean parseUnionDeclaration()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.UnionDeclaration,false);
            node.comment = comment;
            comment = null;
            // grab line number even if it's anonymous
         t = expect(tok("union"));
            if (currentIs(tok("identifier")))
            {
                node.name = advance();
                if (currentIs(tok("(")))
                {
                    mixin(parseNodeQ("node.templateParameters", "TemplateParameters"));
                    if (currentIs(tok("if")))
                    mixin(parseNodeQ("node.raint", "Constraint"));
                    mixin(parseNodeQ("node.structBody", "StructBody"));
                }
            else
                goto semiOrStructBody;
            }
        else
            {
                node.name.line = t.line;
                node.name.column = t.column;
                semiOrStructBody:
                if (currentIs(tok(";")))
                advance();
            else
                mixin(parseNodeQ("node.structBody", "StructBody"));
            }
            return true;
        }

        /**
         * Parses a Unittest
         *
         * $(GRAMMAR $(RULEDEF unittest):
         *     $(LITERAL 'unittest') $(RULE blockStatement)
         *     ;)
         */
        boolean parseUnittest()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            mixin (simpleParse!(Unittest, tok("unittest"), "blockStatement|parseBlockStatement"));
        }

        /**
         * Parses a VariableDeclaration
         *
         * $(GRAMMAR $(RULEDEF variableDeclaration):
         *       $(RULE storageClass)* $(RULE type) $(RULE declarator) ($(LITERAL ',') $(RULE declarator))* $(LITERAL ';')
         *     | $(RULE storageClass)* $(RULE type) $(LITERAL identifier) $(LITERAL '=') $(RULE functionBody) $(LITERAL ';')
         *     | $(RULE autoDeclaration)
         *     ;)
         */
        boolean parseVariableDeclaration()//(Type type = null )
        {
            final boolean isAuto = false;
//            mixin (traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.VariableDeclaration,false);

            if (isAuto)
            {
                mixin(parseNodeQ("node.autoDeclaration", "AutoDeclaration"));
                node.comment = node.autoDeclaration.comment;
                return true;
            }

            StackBuffer storageClasses;
            while (isStorageClass())
                if (!storageClasses.put(parseStorageClass()))
                    return null;
            ownArray(node.storageClasses, storageClasses);

            node.type = type == null ? parseType() : type;
            node.comment = comment;
            comment = null;

            // TODO: handle function bodies correctly

            StackBuffer declarators;
            Declarator last;
            while (true)
            {
                auto declarator = parseDeclarator();
                if (!declarators.put(declarator))
                    return null;
                else
                    last = declarator;
                if (moreTokens() && currentIs(tok(",")))
                {
                    if (node.comment != null)
                    declarator.comment = node.comment ~ "\n" ~ current().trailingComment;
                else
                    declarator.comment = current().trailingComment;
                    advance();
                }
            else
                break;
            }
            ownArray(node.declarators, declarators);
         semicolon = expect(tok(";"));
            mixin (nullCheck("semicolon"));
            if (node.comment != null)
            {
                if (semicolon.trailingComment == null)
                last.comment = node.comment;
            else
                last.comment = node.comment ~ "\n" ~ semicolon.trailingComment;
            }
        else
            last.comment = semicolon.trailingComment;
            return true;
        }

        /**
         * Parses a Vector
         *
         * $(GRAMMAR $(RULEDEF vector):
         *     $(LITERAL '___vector') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseVector()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            mixin (simpleParse!(Vector, tok("__vector"), tok("("), "type|parseType", tok(")")));
        }

        /**
         * Parses a VersionCondition
         *
         * $(GRAMMAR $(RULEDEF versionCondition):
         *     $(LITERAL 'version') $(LITERAL '$(LPAREN)') ($(LITERAL IntegerLiteral) | $(LITERAL Identifier) | $(LITERAL 'unittest') | $(LITERAL 'assert')) $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseVersionCondition()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.VersionCondition,false);
		 v = expect(tok("version"));
            mixin(nullCheck("v"));
            node.versionIndex = v.index;
            if(!tokenCheck("(")){return false;}
            if (currentIsOneOf(tok("intLiteral"), tok("identifier"), tok("unittest"), tok("assert")))
            node.token = advance();
		else
            {
                error("Expected an integer literal, an identifier, \"assert\", or \"unittest\"");
                return null;
            }
            expect(tok(")"));
            return true;
        }

        /**
         * Parses a VersionSpecification
         *
         * $(GRAMMAR $(RULEDEF versionSpecification):
         *     $(LITERAL 'version') $(LITERAL '=') ($(LITERAL Identifier) | $(LITERAL IntegerLiteral)) $(LITERAL ';')
         *     ;)
         */
        boolean parseVersionSpecification()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.VersionSpecification,false);
            if(!tokenCheck("version")){return false;}
            if(!tokenCheck("=")){return false;}
            if (!currentIsOneOf(tok("identifier"), tok("intLiteral")))
            {
                error("Identifier or integer literal expected");
                return null;
            }
            node.token = advance();
            expect(tok(";"));
            return true;
        }

        /**
         * Parses a WhileStatement
         *
         * $(GRAMMAR $(RULEDEF whileStatement):
         *     $(LITERAL 'while') $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement)
         *     ;)
         */
        boolean parseWhileStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
//            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.WhileStatement,false);
            if(!tokenCheck("while")){return false;}
            node.startIndex = current().index;
            if(!tokenCheck("(")){return false;}
            mixin(parseNodeQ("node.expression", "Expression"));
            if(!tokenCheck(")")){return false;}
            if (currentIs(tok("}")))
            {
                error("Statement expected", false);
                return true; // this line makes DCD better
            }
            mixin(parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement"));
            return true;
        }

        /**
         * Parses a WithStatement
         *
         * $(GRAMMAR $(RULEDEF withStatement):
         *     $(LITERAL 'with') $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE statementNoCaseNoDefault)
         *     ;)
         */
        boolean parseWithStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            mixin (simpleParse!(WithStatement, tok("with"), tok("("),
            "expression|parseExpression", tok(")"),
            "statementNoCaseNoDefault|parseStatementNoCaseNoDefault"));
        }

        /**
         * Parses an XorExpression
         *
         * $(GRAMMAR $(RULEDEF xorExpression):
         *       $(RULE andExpression)
         *     | $(RULE xorExpression) $(LITERAL '^') $(RULE andExpression)
         *     ;)
         */
        boolean parseXorExpression()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            return parseLeftAssocBinaryExpression(XorExpression, AndExpression,
            tok("^"));
        }

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
        RollbackAllocator allocator;

//        /**
//         * Function that == called when a warning or error == encountered.
//         * The parameters are the file name, line number, column number,
//         * and the error or warning message.
//         */
//        void function(String, Number, Number, String, boolean) messageFunction;

        void setTokens(Token[] tokens)
        {
            this.tokens = tokens;
        }

        /**
         * Returns: true if there are more tokens
         */
        boolean moreTokens()
        {
            return index < tokens.length;
        }

        int suppressedErrorCount;

        int MAX_ERRORS = 500;

        //todo the parameters where ref
        <T> void  ownArray(T[] arr, StackBuffer sb)
        {
            if (sb.length == 0)
                return;
            void[] a = allocator.allocate(sb.length);
            a[] = sb[];
            arr = (T[]) a;
        }

        boolean isCastQualifier()
        {
            switch (current().type)
            {
                case tok(""):
                    if (peekIs(tok(")"))) return true;
                return startsWith(tok(""), tok("shared"), tok(")"));
                case tok("immutable"):
                    return peekIs(tok(")"));
                case tok("inout"):
                    if (peekIs(tok(")"))) return true;
                return startsWith(tok("inout"), tok("shared"), tok(")"));
                case tok("shared"):
                    if (peekIs(tok(")"))) return true;
                if (startsWith(tok("shared"), tok(""), tok(")"))) return true;
                return startsWith(tok("shared"), tok("inout"), tok(")"));
                default:
                    return false;
            }
        }

        boolean isAssociativeArrayLiteral()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            if (auto p = current().index in cachedAAChecks)
            return *p;
            Number currentIndex = current().index;
            Bookmark b = setBookmark();
            scope(exit) goToBookmark(b);
            advance();
            boolean result = !currentIs(tok("]")) && parseExpression() != null && currentIs(tok(":"));
            cachedAAChecks[currentIndex] = result;
            return result;
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
//                case T: return true;
//                default: advance(); break;
//            }
//            return false;
//        }

        enum DecType
        {
            autoVar,
            autoFun,
            other
        }

        DecType isAutoDeclaration(Number beginIndex)
        {
            Bookmark b = setBookmark();
            scope(exit) goToBookmark(b);

            loop: while (moreTokens()) switch (current().type)
            {
                case tok("pragma"):
                    beginIndex = java.lang.Number.max;
                    advance();
                    if (currentIs(tok("(")))
                {
                    skipParens();
                    break;
                }
                else
                return DecType.other;
                case tok("package"):
                case tok("private"):
                case tok("protected"):
                case tok("public"):
                    beginIndex = java.lang.Number.max;
                    advance();
                    break;
                case tok("@"):
                    beginIndex = min(beginIndex, index);
                    advance();
                    if (currentIs(tok("(")))
                    skipParens();
                else if (currentIs(tok("identifier")))
                {
                    advance();
                    if (currentIs(tok("!")))
                    {
                        advance();
                        if (currentIs(tok("(")))
                        skipParens();
                        else
                        advance();
                    }
                    if (currentIs(tok("(")))
                    skipParens();
                }
                else
                return DecType.other;
                break;
                case tok("deprecated"):
                case tok("align"):
                case tok("extern"):
                    beginIndex = min(beginIndex, index);
                    advance();
                    if (currentIs(tok("(")))
                    skipParens();
                    break;
                case tok(""):
                case tok("immutable"):
                case tok("inout"):
                case tok("synchronized"):
                    if (peekIs(tok("(")))
                    return DecType.other;
                else
                {
                    beginIndex = min(beginIndex, index);
                    advance();
                    break;
                }
                case tok("auto"):
                case tok("enum"):
                case tok("export"):
                case tok("final"):
                case tok("__gshared"):
                case tok(""):
                case tok("override"):
                case tok(""):
                case tok("ref"):
                case tok("scope"):
                case tok("shared"):
                case tok("static"):
                    beginIndex = min(beginIndex, index);
                    advance();
                    break;
                default:
                    break loop;
            }
            if (index <= b)
                return DecType.other;
            if (startsWith(tok("identifier"), tok("=")))
            return DecType.autoVar;
            if (startsWith(tok("identifier"), tok("(")))
            {
                advance();
                auto past = peekPastParens();
                if (past == null)
                return DecType.other;
                else if (past.type == tok("="))
                return DecType.autoVar;
                else
                return DecType.autoFun;
            }
            return DecType.other;
        }

        boolean isDeclaration()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            if (!moreTokens()) return false;
            switch (current().type)
            {
                case tok("final"):
                    return !peekIs(tok("switch"));
                case tok("debug"):
                    if (peekIs(tok(":")))
                    return true;
            goto case;
                case tok("version"):
                    if (peekIs(tok("=")))
                    return true;
                if (peekIs(tok("(")))
                goto default;
                return false;
                case tok("synchronized"):
                    if (peekIs(tok("(")))
                    return false;
            else
                goto default;
                case tok("static"):
                    if (peekIs(tok("if")))
                    return false;
            goto case;
                case tok("scope"):
                    if (peekIs(tok("(")))
                    return false;
            goto case;
                case tok("@"):
                case tok("abstract"):
                case tok("alias"):
                case tok("align"):
                case tok("auto"):
                case tok("class"):
                case tok("deprecated"):
                case tok("enum"):
                case tok("export"):
                case tok("extern"):
                case tok("__gshared"):
                case tok("interface"):
                case tok(""):
                case tok("override"):
                case tok("package"):
                case tok("private"):
                case tok("protected"):
                case tok("public"):
                case tok(""):
                case tok("ref"):
                case tok("struct"):
                case tok("union"):
                case tok("unittest"):
                    return true;
                foreach (B; BasicTypes) { case B: }
                return !peekIsOneOf(tok("."), tok("("));
                case tok("asm"):
                case tok("break"):
                case tok("case"):
                case tok("continue"):
                case tok("default"):
                case tok("do"):
                case tok("for"):
                case tok("foreach"):
                case tok("foreach_reverse"):
                case tok("goto"):
                case tok("if"):
                case tok("return"):
                case tok("switch"):
                case tok("throw"):
                case tok("try"):
                case tok("while"):
                case tok("{"):
                case tok("assert"):
                    return false;
                default:
                    Bookmark b = setBookmark();
                    scope(exit) goToBookmark(b);
                    auto c = allocator.setCheckpoint();
                    scope(exit) allocator.rollback(c);
                    return parseDeclaration(true, true) != null;
            }
        }

        /// Only use this in template parameter parsing
        boolean isType()
        {
            if (!moreTokens()) return false;
            Bookmark b = setBookmark();
            scope (exit) goToBookmark(b);
            auto c = allocator.setCheckpoint();
            scope (exit) allocator.rollback(c);
            if (parseType() == null) return false;
            return currentIsOneOf(tok(","), tok(")"), tok("="));
        }

        boolean isStorageClass()
        {
            if (!moreTokens()) return false;
            switch (current().type)
            {
                case tok(""):
                case tok("immutable"):
                case tok("inout"):
                case tok("shared"):
                    return !peekIs(tok("("));
                case tok("@"):
                case tok("deprecated"):
                case tok("abstract"):
                case tok("align"):
                case tok("auto"):
                case tok("enum"):
                case tok("extern"):
                case tok("final"):
                case tok(""):
                case tok("override"):
                case tok(""):
                case tok("ref"):
                case tok("__gshared"):
                case tok("scope"):
                case tok("static"):
                case tok("synchronized"):
                    return true;
                default:
                    return false;
            }
        }

        boolean isAttribute()
        {
            if (!moreTokens()) return false;
            switch (current().type)
            {
                case tok(""):
                case tok("immutable"):
                case tok("inout"):
                case tok("scope"):
                    return !peekIs(tok("("));
                case tok("static"):
                    return !peekIsOneOf(tok("assert"), tok("this"), tok("if"), tok("~"));
                case tok("shared"):
                    return !(startsWith(tok("shared"), tok("static"), tok("this"))
                || startsWith(tok("shared"), tok("static"), tok("~"))
                || peekIs(tok("(")));
                case tok("pragma"):
                    Bookmark b = setBookmark();
                    scope(exit) goToBookmark(b);
                    advance();
             past = peekPastParens();
                    if (past == null || *past == tok(";"))
                    return false;
                return true;
                case tok("deprecated"):
                case tok("private"):
                case tok("package"):
                case tok("protected"):
                case tok("public"):
                case tok("export"):
                case tok("final"):
                case tok("synchronized"):
                case tok("override"):
                case tok("abstract"):
                case tok("auto"):
                case tok("__gshared"):
                case tok(""):
                case tok(""):
                case tok("@"):
                case tok("ref"):
                case tok("extern"):
                case tok("align"):
                    return true;
                default:
                    return false;
            }
        }

        static boolean isMemberFunctionAttribute(IdType t)
        {
            switch (t)
            {
                case tok(""):
                case tok("immutable"):
                case tok("inout"):
                case tok("shared"):
                case tok("@"):
                case tok(""):
                case tok(""):
                case tok("return"):
                case tok("scope"):
                    return true;
                default:
                    return false;
            }
        }

        static boolean isTypeCtor(IdType t)
        {
            switch (t)
            {
                case tok(""):
                case tok("immutable"):
                case tok("inout"):
                case tok("shared"):
                    return true;
                default:
                    return false;
            }
        }

        boolean currentIsMemberFunctionAttribute()
        {
            return moreTokens && isMemberFunctionAttribute(current().type);
        }

        boolean parseLeftAssocBinaryExpression(String ExpressionType,String ExpressionPartType, IdType... operators){
            return parseLeftAssocBinaryExpression(ExpressionType,ExpressionPartType,false,operators);
        }

        boolean parseLeftAssocBinaryExpression(String ExpressionType,String ExpressionPartType, boolean part, IdType... operators)//(alias ExpressionType, alias ExpressionPartType, Operators ...)(ExpressionNode part = null)
        {
            boolean node = (!part) ? parseName(ExpressionPartType) : part;
            if (!node)
                return false;
            while (currentIsOneOf(operators))
            {
//                auto n = allocator.make!ExpressionType;
                advance();
                if(!parseNodeQ("n.right", ExpressionPartType)){
                    return false;
                }
            }
            return true;
        }

        //todo
        boolean parseCommaSeparatedRule(String listType, String itemType)//(alias ListType, alias ItemType,)
        {
//            final boolean setLineAndColumn = false;
//            Marker m = enter_section_(builder);
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

        void warn(String message)
        {
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

        static PsiBuilder builder;

        void error(String message)
        {
            if (suppressMessages == 0)
            {
                ++errorCount;
//                auto column = index < tokens.length ? tokens[index].column : tokens[$ - 1].column;
//                auto line = index < tokens.length ? tokens[index].line : tokens[$ - 1].line;
//                if (messageFunction == null)
//                stderr.writefln("%s(%d:%d)[error]: %s", fileName, line, column, message);
//            else
//                messageFunction(fileName, line, column, message, true);
                builder.error(message);
            }
            else
                ++suppressedErrorCount;
            while (true && moreTokens())
            {
                if (currentIsOneOf(tok(";"), tok("}"),
                tok(")"), tok("]")))
                {
                    advance();
                    break;
                }
            else
                advance();
            }
        }

        void skip()//(alias O, alias C)
        {
            assert (currentIs(O), current().text);
            advance();
            int depth = 1;
            while (moreTokens())
            {
                switch (tokens[index].type)
                {
                    case C:
                        advance();
                        depth--;
                        if (depth <= 0)
                            return;
                        break;
                    case O:
                        depth++;
                        advance();
                        break;
                    default:
                        advance();
                        break;
                }
            }
        }

        void skipBraces()
        {
            skip!(tok("{"), tok("}"))();
        }

        void skipParens()
        {
            skip!(tok("("), tok(")"))();
        }

        void skipBrackets()
        {
            skip!(tok("["), tok("]"))();
        }

        Token peek()
        {
            return index + 1 < tokens.length ? &tokens[index + 1] : null;
        }

        Token peekPast()//(alias O, alias C)
        {
            if (index >= tokens.length)
                return null;
            int depth = 1;
            Number i = index;
            ++i;
            while (i < tokens.length)
            {
                if (tokens[i] == O)
                {
                    ++depth;
                    ++i;
                }
                else if (tokens[i] == C)
                {
                    --depth;
                    ++i;
                    if (depth <= 0)
                        break;
                }
                else
                    ++i;
            }
            return i >= tokens.length ? null : depth == 0 ? &tokens[i] : null;
        }

        Token peekPastParens()
        {
            return peekPast!(tok("("), tok(")"))();
        }

        Token peekPastBrackets()
        {
            return peekPast!(tok("["), tok("]"))();
        }

        Token peekPastBraces()
        {
            return peekPast(tok("{"), tok("}"));
        }

        boolean peekIs(IdType t)
        {
            return index + 1 < tokens.length && tokens[index + 1].type == t;
        }

        boolean peekIsOneOf(IdType... types)
        {
            if (index + 1 >= tokens.length) return false;
            return canFind(types, tokens[index + 1].type);
        }

        /**
         * Returns a token of the specified type if it was the next token, otherwise
         * calls the error function and returns null. Advances the lexer by one token.
         */
        Token expect(IdType type)
        {
            if (index < tokens.length && tokens[index].type == type)
                return tokens[index++];
            else
            {
                String tokenString = type.toString();
                boolean shouldNotAdvance = index < tokens.length
                && (tokens[index].type == tok(")")//technicaly these == work but .equals would make me feel more comfortable
                || tokens[index].type == tok(";")
                || tokens[index].type == tok("}"));
                String token = (index < tokens.length
                    ? (tokens[index].text == null ? str(tokens[index].type) : tokens[index].text)
                    : "EOF");
                error("Expected " +  tokenString + " instead of " + token,
                !shouldNotAdvance);
                return null;
            }
        }

        public static String str(Object o){
            return o.toString();
        }

        /**
         * Returns: the _current token
         */
        Token current()
        {
            return tokens[index];
        }

        /**
         * Returns: the _previous token
         */
        Token previous()
        {
            return tokens[index - 1];
        }

        /**
         * Advances to the next token and returns the current token
         */
        Token advance()
        {
            builder.advanceLexer();
            return tokens[index++];
        }

        /**
         * Returns: true if the current token has the given type
         */
        boolean currentIs(IdType type)
        {
            return index < tokens.length && tokens[index].type == type;
        }

        /**
         * Returns: true if the current token == one of the given types
         */
        boolean currentIsOneOf(IdType... types)
        {
            if (index >= tokens.length)
                return false;
            return canFind(types, current().type);
        }

        boolean startsWith(IdType... types)
        {
            if (index + types.length >= tokens.length)
                return false;
            for (int i = 0; (i < types.length) && ((index + i) < tokens.length); ++i)
            {
                if (tokens[index + i].type != types[i])
                    return false;
            }
            return true;
        }

        class Bookmark extends Number{
            double num = 0;

            @Override
            public int intValue() {
                return (int) num;
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
        }

        Bookmark setBookmark()
        {
////        mixin(traceEnterAndExit!(__FUNCTION__));
            ++suppressMessages;
            return index;
        }

        void abandonBookmark(Bookmark)
        {
            --suppressMessages;
            if (suppressMessages == 0)
                suppressedErrorCount = 0;
        }

        void goToBookmark(Bookmark bookmark)
        {
            --suppressMessages;
            if (suppressMessages == 0)
                suppressedErrorCount = 0;
            index = bookmark;
        }

//        template simpleParse//(NodeType, parts ...)
//        {
//            if (__traits(hasMember, NodeType, "comment"))
//            enum nodeComm = "node.comment = comment;\n"
//            ~ "comment = null;\n";
//        else enum nodeComm = "";
//
//            if (__traits(hasMember, NodeType, "line"))
//            enum nodeLine = "node.line = current().line;\n";
//        else enum nodeLine = "";
//
//            if (__traits(hasMember, NodeType, "column"))
//            enum nodeColumn = "node.column = current().column;\n";
//        else enum nodeColumn = "";
//
//            if (__traits(hasMember, NodeType, "location"))
//            enum nodeLoc = "node.location = current().index;\n";
//        else enum nodeLoc = "";
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
//            enum simpleParseItems = "";
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
//            enum traceEnterAndExit = "version (dparse_verbose) { _traceDepth++; trace(""
//            ~ "\033[01;32m" ~ fun ~ "\033[0m"); }"
//            ~ "version (dparse_verbose) scope(exit) { trace(""
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



        boolean parseNodeQ(String VarName, String NodeName)
        {
            if(!parseName(NodeName)){
                return false;
            }
            //we can ignore VarName, since that is aside effect not strictly necessary for parsing
            return true;
//            enum parseNodeQ = "{ if ((" ~ VarName ~ " = parse" ~ NodeName ~ "()) == null) return null; }";//potentiall huge implmentatation in java todo
        }

        boolean nullCheck(String exp)
        {
            if(exp == null) {
                return false;
            }
            return true;
        }

        boolean tokenCheck(String Tok)
        {
            if(expect(tok(Tok)) == null) {
                return false;
            }
            return true;
        }

        static HashMap<String,IdType> tokenTypeIndex = new HashMap<>();

        private static IdType tok(String tok) {
            if(tokenTypeIndex.get(tok) != null){
                return tokenTypeIndex.get(tok);
            }
            final IElementType[] matchingTypes = IElementType.enumerate((IElementType type) -> type.toString().equals(tok));
            if(matchingTypes.length != 1){
                throw new IllegalArgumentException();
            }
            final IdType result = new IdType(matchingTypes[0]);
            tokenTypeIndex.put(tok,result);
            return result;
        }

        boolean tokenCheck(String Exp, String Tok)
        {
            final Token t = expect(tok(Tok));
            if(t == null){
                return false;
            }
            else{
                //do nothing. Side-effects not relevant for purely parsing
            }
//            enum tokenCheck = "{auto t = expect(tok("" ~ Tok ~ ""));"
//            ~ "if (t == null) { return null;}"
//            ~ "else {" ~ Exp ~ " = *t; }}";
        }

        private <T> T attachCommentFromSemicolon(T node)
        {
            Token semicolon = expect(tok(";"));
            if (semicolon == null)
                return null;
            if (semicolon.trailingComment != null)
            {
                if (node.comment == null)
                    node.comment = semicolon.trailingComment;
                else
                {
                    node.comment ~= "\n";
                    node.comment ~= semicolon.trailingComment;
                }
            }
            return true;
        }

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

        <N> N parseStaticCtorDtorCommon(N node)
        {
            node.line = current().line;
            node.column = current().column;
            if(!tokenCheck("this")){return false;}
            if(!tokenCheck("(")){return false;}
            if(!tokenCheck(")")){return false;}
            StackBuffer attributes;
            while (moreTokens() && !currentIsOneOf(tok("{"), tok("in"), tok("out"), tok("body"), tok(";")))
            if (!attributes.put(parseMemberFunctionAttribute()))
                return null;
            ownArray(node.memberFunctionAttributes, attributes);
            if (currentIs(tok(";")))
            advance();
    else
            mixin(parseNodeQ("node.functionBody", "FunctionBody"));
            return true;
        }


        <N> N parseInterfaceOrClass(N node)
        {
            auto ident = expect(tok("identifier"));
            mixin (nullCheck("ident"));
            node.name = *ident;
            node.comment = comment;
            comment = null;
            if (currentIs(tok(";")))
        goto emptyBody;
            if (currentIs(tok("{")))
        goto structBody;
            if (currentIs(tok("(")))
            {
                mixin(parseNodeQ("node.templateParameters", "TemplateParameters"));
                if (currentIs(tok(";")))
            goto emptyBody;
                raint: if (currentIs(tok("if")))
                mixin(parseNodeQ("node.raint", "Constraint"));
                if (node.baseClassList != null)
                {
                    if (currentIs(tok("{")))
                goto structBody;
            else if (currentIs(tok(";")))
                goto emptyBody;
            else
                    {
                        error("Struct body or ';' expected");
                        return null;
                    }
                }
                if (currentIs(tok(":")))
            goto baseClassList;
                if (currentIs(tok("if")))
            goto raint;
                if (currentIs(tok(";")))
            goto emptyBody;
            }
            if (currentIs(tok(":")))
            {
                baseClassList:
                advance(); // :
                if ((node.baseClassList = parseBaseClassList()) == null)
                return null;
                if (currentIs(tok("if")))
            goto raint;
            }
            structBody:
            mixin(parseNodeQ("node.structBody", "StructBody"));
            return true;
            emptyBody:
            advance();
            return true;
        }

        int suppressMessages;

        int index;
        int _traceDepth;
        String comment;

        private boolean parseName(String NodeName){
            switch (NodeName){
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
                default:
                    throw new IllegalArgumentException("unrecognized thing to parse:" + NodeName);
            }
        }

    }

    private IElementType nodeTypeToIElementType(String nodeType) {
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
            case "TemplateValueParameterDefault":
                return DLanguageTypes.TEMPLATE_VALUE_PARAMETER_DEFAULT;
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
    //            boolean[typeof(Token.index)] cachedAAChecks;
//    private boolean parseName(String NodeName){
//        Map<String, Callable<Boolean>> methodLookupTable = ImmutableMap.of(
//            "AliasThisDeclaration", (Callable<Boolean>) this::parseAliasThisDeclaration,
//            "AlignAttribute", (Callable<Boolean>) this::parseAlignAttribute,
//            "ArgumentList", (Callable<Boolean>) this::parseArgumentList,
//            "Arguments", (Callable<Boolean>) this::parseArguments,
//            "ArrayInitializer", (Callable<Boolean>) this::parseArrayInitializer,
//            "ArrayLiteral", (Callable<Boolean>) this::parseArrayLiteral,
//            "ArrayMemberInitialization", (Callable<Boolean>) this::parseArrayMemberInitialization,
//            "AsmBrExp", (Callable<Boolean>) this::parseAsmBrExp,
//            "AsmInstruction", (Callable<Boolean>) this::parseAsmInstruction,
//            "AsmPrimaryExp", (Callable<Boolean>) this::parseAsmPrimaryExp,
//            "AsmStatement", (Callable<Boolean>) this::parseAsmStatement,
//            "AsmTypePrefix", (Callable<Boolean>) this::parseAsmTypePrefix,
//            "AsmUnaExp", (Callable<Boolean>) this::parseAsmUnaExp,
//            "AssertExpression", (Callable<Boolean>) this::parseAssertExpression,
//            "AssocArrayLiteral", (Callable<Boolean>) this::parseAssocArrayLiteral,
//            "AtAttribute", (Callable<Boolean>) this::parseAtAttribute,
//            "Attribute", (Callable<Boolean>) this::parseAttribute,
//            "AttributeDeclaration", (Callable<Boolean>) this::parseAttributeDeclaration,
//            "AutoDeclaration", (Callable<Boolean>) this::parseAutoDeclaration,
//            "AutoDeclarationPart", (Callable<Boolean>) this::parseAutoDeclarationPart,
//            "BlockStatement", (Callable<Boolean>) this::parseBlockStatement,
//            "BodyStatement", (Callable<Boolean>) this::parseBodyStatement,
//            "BreakStatement", (Callable<Boolean>) this::parseBreakStatement,
//            "BaseClass", (Callable<Boolean>) this::parseBaseClass,
//            "BaseClassList", (Callable<Boolean>) this::parseBaseClassList,
//            "CaseRangeStatement", (Callable<Boolean>) () -> parseCaseRangeStatement(),
//            "CaseStatement", (Callable<Boolean>) () -> parseCaseStatement(),
//            "CastExpression", (Callable<Boolean>) this::parseCastExpression,
//            "CastQualifier", (Callable<Boolean>) this::parseCastQualifier,
//            "Catch", (Callable<Boolean>) this::parseCatch,
//            "Catches", (Callable<Boolean>) this::parseCatches,
//            "ClassDeclaration", (Callable<Boolean>) this::parseClassDeclaration,
//            "CompileCondition", (Callable<Boolean>) this::parseCompileCondition,
//            "ConditionalDeclaration", (Callable<Boolean>) () -> parseConditionalDeclaration(),
//            "ConditionalStatement", (Callable<Boolean>) this::parseConditionalStatement,
//            "Constraint", (Callable<Boolean>) this::parseConstraint,
//            "Constructor", (Callable<Boolean>) this::parseConstructor,
//            "ContinueStatement", (Callable<Boolean>) this::parseContinueStatement,
//            "DebugCondition", (Callable<Boolean>) this::parseDebugCondition,
//            "DebugSpecification", (Callable<Boolean>) this::parseDebugSpecification,
//            "Declaration", (Callable<Boolean>) this::parseDeclaration,
//            "DeclarationsAndStatements", (Callable<Boolean>) this::parseDeclarationsAndStatements,
//            "DeclarationOrStatement", (Callable<Boolean>) this::parseDeclarationOrStatement,
//            "Declarator", (Callable<Boolean>) this::parseDeclarator,
//            "DefaultStatement", (Callable<Boolean>) this::parseDefaultStatement,
//            "DeleteExpression", (Callable<Boolean>) this::parseDeleteExpression,
//            "Deprecated", (Callable<Boolean>) this::parseDeprecated,
//            "Destructor", (Callable<Boolean>) this::parseDestructor,
//            "DoStatement", (Callable<Boolean>) this::parseDoStatement,
//            "EnumBody", (Callable<Boolean>) this::parseEnumBody,
//            "AnonymousEnumMember", (Callable<Boolean>) this::parseAnonymousEnumMember,
//            "AnonymousEnumDeclaration", (Callable<Boolean>) this::parseAnonymousEnumDeclaration,
//            "EnumDeclaration", (Callable<Boolean>) this::parseEnumDeclaration,
//            "EnumMember", (Callable<Boolean>) this::parseEnumMember,
//            "EponymousTemplateDeclaration", (Callable<Boolean>) this::parseEponymousTemplateDeclaration,
//            "EqualExpression", (Callable<Boolean>) () -> parseEqualExpression(),
//            "Expression", (Callable<Boolean>) this::parseExpression,
//            "ExpressionStatement", (Callable<Boolean>) () -> parseExpressionStatement(),
//            "FinalSwitchStatement", (Callable<Boolean>) this::parseFinalSwitchStatement,
//            "Finally", (Callable<Boolean>) this::parseFinally,
//            "ForStatement", (Callable<Boolean>) this::parseForStatement,
//            "ForeachStatement", (Callable<Boolean>) this::parseForeachStatement,
//            "ForeachType", (Callable<Boolean>) this::parseForeachType,
//            "ForeachTypeList", (Callable<Boolean>) this::parseForeachTypeList,
//            "FunctionAttribute", (Callable<Boolean>) () -> parseFunctionAttribute(),
//            "FunctionBody", (Callable<Boolean>) this::parseFunctionBody,
//            "FunctionCallExpression", (Callable<Boolean>) this::parseFunctionCallExpression,
//            "FunctionDeclaration", (Callable<Boolean>) this::parseFunctionDeclaration,
//            "FunctionLiteralExpression", (Callable<Boolean>) () -> parseFunctionLiteralExpression(),
//            "GotoStatement", (Callable<Boolean>) () -> parseGotoStatement(),
//            "IdentifierChain", (Callable<Boolean>) () -> parseIdentifierChain(),
//            "IdentifierList", (Callable<Boolean>) () -> parseIdentifierList(),
//            "IdentifierOrTemplateChain", (Callable<Boolean>) () -> parseIdentifierOrTemplateChain(),
//            "IdentifierOrTemplateInstance", (Callable<Boolean>) () -> parseIdentifierOrTemplateInstance(),
//            "IfStatement", (Callable<Boolean>) () -> parseIfStatement(),
//            "ImportBind", (Callable<Boolean>) () -> parseImportBind(),
//            "ImportBindings", (Callable<Boolean>) () -> parseImportBindings(),
//            "ImportDeclaration", (Callable<Boolean>) () -> parseImportDeclaration(),
//            "ImportExpression", (Callable<Boolean>) () -> parseImportExpression(),
//            "Index", (Callable<Boolean>) () -> parseIndex(),
//            "IndexExpression", (Callable<Boolean>) () -> parseIndexExpression(),
//            "InStatement", (Callable<Boolean>) () -> parseInStatement(),
//            "Initializer", (Callable<Boolean>) () -> parseInitializer(),
//            "InterfaceDeclaration", (Callable<Boolean>) () -> parseInterfaceDeclaration(),
//            "Invariant", (Callable<Boolean>) () -> parseInvariant(),
//            "IsExpression", (Callable<Boolean>) () -> parseIsExpression(),
//            "KeyValuePair", (Callable<Boolean>) () -> parseKeyValuePair(),
//            "KeyValuePairs", (Callable<Boolean>) () -> parseKeyValuePairs(),
//            "LabeledStatement", (Callable<Boolean>) () -> parseLabeledStatement(),
//            "LastCatch", (Callable<Boolean>) () -> parseLastCatch(),
//            "LinkageAttribute", (Callable<Boolean>) () -> parseLinkageAttribute(),
//            "MemberFunctionAttribute", (Callable<Boolean>) () -> parseMemberFunctionAttribute(),
//            "MixinDeclaration", (Callable<Boolean>) () -> parseMixinDeclaration(),
//            "MixinExpression", (Callable<Boolean>) () -> parseMixinExpression(),
//            "MixinTemplateDeclaration", (Callable<Boolean>) () -> parseMixinTemplateDeclaration(),
//            "MixinTemplateName", (Callable<Boolean>) () -> parseMixinTemplateName(),
//            "Module", (Callable<Boolean>) () -> parseModule(),
//            "ModuleDeclaration", (Callable<Boolean>) () -> parseModuleDeclaration(),
//            "NewAnonClassExpression", (Callable<Boolean>) () -> parseNewAnonClassExpression(),
//            "NewExpression", (Callable<Boolean>) () -> parseNewExpression(),
//            "NonVoidInitializer", (Callable<Boolean>) () -> parseNonVoidInitializer(),
//            "Operands", (Callable<Boolean>) () -> parseOperands(),
//            "OutStatement", (Callable<Boolean>) () -> parseOutStatement(),
//            "Parameter", (Callable<Boolean>) () -> parseParameter(),
//            "Parameters", (Callable<Boolean>) () -> parseParameters(),
//            "Postblit", (Callable<Boolean>) () -> parsePostblit(),
//            "PragmaDeclaration", (Callable<Boolean>) () -> parsePragmaDeclaration(),
//            "PragmaExpression", (Callable<Boolean>) () -> parsePragmaExpression(),
//            "PrimaryExpression", (Callable<Boolean>) () -> parsePrimaryExpression(),
//            "Register", (Callable<Boolean>) () -> parseRegister(),
//            "ReturnStatement", (Callable<Boolean>) () -> parseReturnStatement(),
//            "ScopeGuardStatement", (Callable<Boolean>) () -> parseScopeGuardStatement(),
//            "SharedStaticConstructor", (Callable<Boolean>) () -> parseSharedStaticConstructor(),
//            "SharedStaticDestructor", (Callable<Boolean>) () -> parseSharedStaticDestructor(),
//            "SingleImport", (Callable<Boolean>) () -> parseSingleImport(),
//            "Statement", (Callable<Boolean>) () -> parseStatement(),
//            "StatementNoCaseNoDefault", (Callable<Boolean>) () -> parseStatementNoCaseNoDefault(),
//            "StaticAssertDeclaration", (Callable<Boolean>) () -> parseStaticAssertDeclaration(),
//            "StaticAssertStatement", (Callable<Boolean>) () -> parseStaticAssertStatement(),
//            "StaticConstructor", (Callable<Boolean>) () -> parseStaticConstructor(),
//            "StaticDestructor", (Callable<Boolean>) () -> parseStaticDestructor(),
//            "StaticIfCondition", (Callable<Boolean>) () -> parseStaticIfCondition(),
//            "StorageClass", (Callable<Boolean>) () -> parseStorageClass(),
//            "StructBody", (Callable<Boolean>) () -> parseStructBody(),
//            "StructDeclaration", (Callable<Boolean>) () -> parseStructDeclaration(),
//            "StructInitializer", (Callable<Boolean>) () -> parseStructInitializer(),
//            "StructMemberInitializer", (Callable<Boolean>) () -> parseStructMemberInitializer(),
//            "StructMemberInitializers", (Callable<Boolean>) () -> parseStructMemberInitializers(),
//            "SwitchStatement", (Callable<Boolean>) () -> parseSwitchStatement(),
//            "Symbol", (Callable<Boolean>) () -> parseSymbol(),
//            "SynchronizedStatement", (Callable<Boolean>) () -> parseSynchronizedStatement(),
//            "TemplateAliasParameter", (Callable<Boolean>) () -> parseTemplateAliasParameter(),
//            "TemplateArgument", (Callable<Boolean>) () -> parseTemplateArgument(),
//            "TemplateArgumentList", (Callable<Boolean>) () -> parseTemplateArgumentList(),
//            "TemplateArguments", (Callable<Boolean>) () -> parseTemplateArguments(),
//            "TemplateDeclaration", (Callable<Boolean>) () -> parseTemplateDeclaration(),
//            "TemplateInstance", (Callable<Boolean>) () -> parseTemplateInstance(),
//            "TemplateMixinExpression", (Callable<Boolean>) () -> parseTemplateMixinExpression(),
//            "TemplateParameter", (Callable<Boolean>) () -> parseTemplateParameter(),
//            "TemplateParameterList", (Callable<Boolean>) () -> parseTemplateParameterList(),
//            "TemplateParameters", (Callable<Boolean>) () -> parseTemplateParameters(),
//            "TemplateSingleArgument", (Callable<Boolean>) () -> parseTemplateSingleArgument(),
//            "TemplateThisParameter", (Callable<Boolean>) () -> parseTemplateThisParameter(),
//            "TemplateTupleParameter", (Callable<Boolean>) () -> parseTemplateTupleParameter(),
//            "TemplateTypeParameter", (Callable<Boolean>) () -> parseTemplateTypeParameter(),
//            "TemplateValueParameter", (Callable<Boolean>) () -> parseTemplateValueParameter(),
//            "TemplateValueParameterDefault", (Callable<Boolean>) () -> parseTemplateValueParameterDefault(),
//            "ThrowStatement", (Callable<Boolean>) () -> parseThrowStatement(),
//            "TraitsExpression", (Callable<Boolean>) () -> parseTraitsExpression(),
//            "TryStatement", (Callable<Boolean>) () -> parseTryStatement(),
//            "Type", (Callable<Boolean>) () -> parseType(),
//            "Type2", (Callable<Boolean>) () -> parseType2(),
//            "TypeSpecialization", (Callable<Boolean>) () -> parseTypeSpecialization(),
//            "TypeSuffix", (Callable<Boolean>) () -> parseTypeSuffix(),
//            "TypeidExpression", (Callable<Boolean>) () -> parseTypeidExpression(),
//            "TypeofExpression", (Callable<Boolean>) () -> parseTypeofExpression(),
//            "UnaryExpression", (Callable<Boolean>) () -> parseUnaryExpression(),
//            "UnionDeclaration", (Callable<Boolean>) () -> parseUnionDeclaration(),
//            "Unittest", (Callable<Boolean>) () -> parseUnittest(),
//            "VariableDeclaration", (Callable<Boolean>) () -> parseVariableDeclaration(),
//            "Vector", (Callable<Boolean>) () -> parseVector(),
//            "VersionCondition", (Callable<Boolean>) () -> parseVersionCondition(),
//            "VersionSpecification", (Callable<Boolean>) () -> parseVersionSpecification(),
//            "WhileStatement", (Callable<Boolean>) () -> parseWhileStatement(),
//            "WithStatement", (Callable<Boolean>) () -> parseWithStatement(),
//            "AddExpression", (Callable<Boolean>) () -> parseAddExpression(),
//            "AndAndExpression", (Callable<Boolean>) () -> parseAndAndExpression(),
//            "AndExpression", (Callable<Boolean>) () -> parseAndExpression(),
//            "AsmAddExp", (Callable<Boolean>) () -> parseAsmAddExp(),
//            "AsmAndExp", (Callable<Boolean>) () -> parseAsmAndExp(),
//            "AsmBrExp", (Callable<Boolean>) () -> parseAsmBrExp(),
//            "AsmExp", (Callable<Boolean>) () -> parseAsmExp(),
//            "AsmEqualExp", (Callable<Boolean>) () -> parseAsmEqualExp(),
//            "AsmLogAndExp", (Callable<Boolean>) () -> parseAsmLogAndExp(),
//            "AsmLogOrExp", (Callable<Boolean>) () -> parseAsmLogOrExp(),
//            "AsmMulExp", (Callable<Boolean>) () -> parseAsmMulExp(),
//            "AsmOrExp", (Callable<Boolean>) () -> parseAsmOrExp(),
//            "AsmRelExp", (Callable<Boolean>) () -> parseAsmRelExp(),
//            "AsmUnaExp", (Callable<Boolean>) () -> parseAsmUnaExp(),
//            "AsmShiftExp", (Callable<Boolean>) () -> parseAsmShiftExp(),
//            "AsmXorExp", (Callable<Boolean>) () -> parseAsmXorExp(),
//            "AssertExpression", (Callable<Boolean>) () -> parseAssertExpression(),
//            "AssignExpression", (Callable<Boolean>) () -> parseAssignExpression(),
//            "CmpExpression", (Callable<Boolean>) () -> parseCmpExpression(),
//            "DeleteExpression", (Callable<Boolean>) () -> parseDeleteExpression(),
//            "EqualExpression", (Callable<Boolean>) () -> parseEqualExpression(),
//            "Expression", (Callable<Boolean>) () -> parseExpression(),
//            "FunctionCallExpression", (Callable<Boolean>) () -> parseFunctionCallExpression(),
//            "FunctionLiteralExpression", (Callable<Boolean>) () -> parseFunctionLiteralExpression(),
//            "IdentityExpression", (Callable<Boolean>) () -> parseIdentityExpression(),
//            "ImportExpression", (Callable<Boolean>) () -> parseImportExpression(),
//            "IndexExpression", (Callable<Boolean>) () -> parseIndexExpression(),
//            "InExpression", (Callable<Boolean>) () -> parseInExpression(),
//            "IsExpression", (Callable<Boolean>) () -> parseIsExpression(),
//            "MixinExpression", (Callable<Boolean>) () -> parseMixinExpression(),
//            "MulExpression", (Callable<Boolean>) () -> parseMulExpression(),
//            "NewAnonClassExpression", (Callable<Boolean>) () -> parseNewAnonClassExpression(),
//            "NewExpression", (Callable<Boolean>) () -> parseNewExpression(),
//            "OrExpression", (Callable<Boolean>) () -> parseOrExpression(),
//            "OrOrExpression", (Callable<Boolean>) () -> parseOrOrExpression(),
//            "PowExpression", (Callable<Boolean>) () -> parsePowExpression(),
//            "PragmaExpression", (Callable<Boolean>) () -> parsePragmaExpression(),
//            "PrimaryExpression", (Callable<Boolean>) () -> parsePrimaryExpression(),
//            "RelExpression", (Callable<Boolean>) () -> parseRelExpression(),
//            "ShiftExpression", (Callable<Boolean>) () -> parseShiftExpression(),
//            "Index", (Callable<Boolean>) () -> parseIndex(),
//            "TemplateMixinExpression", (Callable<Boolean>) () -> parseTemplateMixinExpression(),
//            "TernaryExpression", (Callable<Boolean>) () -> parseTernaryExpression(),
//            "TraitsExpression", (Callable<Boolean>) () -> parseTraitsExpression(),
//            "TypeidExpression", (Callable<Boolean>) () -> parseTypeidExpression(),
//            "TypeofExpression", (Callable<Boolean>) () -> parseTypeofExpression(),
//            "UnaryExpression", (Callable<Boolean>) () -> parseUnaryExpression(),
//            "XorExpression", (Callable<Boolean>) () -> parseXorExpression(),
//            "AliasInitializer", (Callable<Boolean>) () -> parseAliasInitializer()
//
//        );
//    }

}
