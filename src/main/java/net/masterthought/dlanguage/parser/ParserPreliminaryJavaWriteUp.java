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
                    if(!parseNodeQ("node.parameters", "Parameters")){cleanup(m/*todo*/);return false;}
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
            Marker m = enter_section_(builder);
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
            Marker m = enter_section_(builder);
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
            }
            exit_section_(builder,m,ASSERT_EXPRESSION,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AtAttribute,false);
            Token start = expect(tok("@"));
            mixin (nullCheck("start"));
            if (!moreTokens())
            {
                error("\"(\", or identifier expected");
                return false;
            }
//            node.startLocation = start.index;
            IdType i = current().type;
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
            exit_section_(builder,m,ATTRIBUTE,true);//todo elementType
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Attribute,false);
            IdType i = current().type;
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
            } else if (i.equals(tok("private")) || i.equals(tok("protected")) || i.equals(tok("public")) || i.equals(tok("export")) || i.equals(tok("static")) || i.equals(tok("abstract")) || i.equals(tok("final")) || i.equals(tok("override")) || i.equals(tok("synchronized")) || i.equals(tok("auto")) || i.equals(tok("scope")) || i.equals(tok("")) || i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("shared")) || i.equals(tok("__gshared")) || i.equals(tok("")) || i.equals(tok("")) || i.equals(tok("ref"))) {
                advance();
            } else {
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,ATTRIBUTE,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AttributeDeclaration,false);
//            node.line = current().line;
            final boolean result = parseAttribute();
            expect(tok(":"));
            exit_section_(builder,m,ATTRIBUTE_SPECIFIER,result);
            return true;
        }

        /**
         * Parses an AutoDeclaration
         *
         * $(GRAMMAR $(RULEDEF autoDeclaration):
         *     $(RULE storageClass)+  $(RULE autoDeclarationPart) ($(LITERAL ',') $(RULE autoDeclarationPart))* $(LITERAL ';')
         *     ;)
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
            exit_section_(builder,m,AUTO_DECLARATION,true);
            return true;
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
            final Marker m = enter_section_(builder);
            Token i = expect(tok("identifier"));//todo
            if (i == null) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok("("))) {
                if(!parseNodeQ("part.templateParameters", "TemplateParameters")){
                    cleanup(m);
                    return false;
                }
            }
            if (!tokenCheck("=")) {
                cleanup(m);
                return false;
            }
            if(!parseNodeQ("part.initializer", "Initializer")){
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,AUTO_DECLARATION_X,true);
            return true;
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.BlockStatement,false);
            Token openBrace = expect(tok("{"));
            if(nullCheck("openBrace")){
                cleanup(m);
                return false;
            }
//            node.startLocation = openBrace.index;
            if (!currentIs(tok("}")))
            {
                if(parseNodeQ("node.declarationsAndStatements", "DeclarationsAndStatements")){
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
            exit_section_(builder,m,BLOCK_STATEMENT,true);
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
            final Marker m = enter_section_(builder);
            final boolean result = simpleParse("BodyStatement", tok("body"), "blockStatement|parseBlockStatement");
            exit_section_(builder,m,BODY_STATEMENT,result);
            return result;
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
            Marker m = enter_section_(builder);
            expect(tok("break"));
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.BreakStatement,false);
            IdType i = current().type;
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
            exit_section_(builder,m,BREAK_STATEMENT,true);
            return true;
        }


        public IdType[] Protections = {tok("export"), tok("package"),
        tok("private"), tok("public"), tok("protected")};

        /**
         * todo document and move
         * @param type
         * @return
         */
        public boolean isProtection(IdType type)
        {

            for (IdType t:Protections)
            {
                if(t.equals(type)){
                    return true;
                }
            }
            return false;

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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.BaseClass,false);
            if (isProtection(current().type))
            {
                warn("Use of base class protection == deprecated.");
                advance();
            }
            if (!parseType2())
            {
                cleanup(m);
                return false;
            }
//            exit_section_(builder,m,"vghbj0,",true);//todo needs type
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
            Marker m = enter_section_(builder);
            final boolean result = parseCommaSeparatedRule("BaseClassList", "BaseClass");
            exit_section_(builder,m,BASE_CLASS_LIST,result);
            return result;
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
        IdType parseBuiltinType()
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
        boolean parseCaseRangeStatement()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CaseRangeStatement,false);
//            assert (low != null);
//            node.low = low;
            if(!tokenCheck(":")) {
                cleanup(m);
                return false;
            }
            if (!tokenCheck("..")) {
                cleanup(m);
                return false;
            }
            expect(tok("case"));
            if(!parseNodeQ("node.high", "AssignExpression")){
                cleanup(m);
                return false;
            }
            Token colon = expect(tok(":"));
            if (colon == null)
                return false;
            if(!parseNodeQ("node.declarationsAndStatements", "DeclarationsAndStatements")){
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,CASE_RANGE_STATEMENT,true);
            return true;
        }

        /**
         * Parses an CaseStatement
         *
         * $(GRAMMAR $(RULEDEF caseStatement):
         *     $(LITERAL 'case') $(RULE argumentList) $(LITERAL ':') $(RULE declarationsAndStatements)
         *     ;)
         */
        boolean parseCaseStatement()
        {
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
            if(!nullCheck("node.declarationsAndStatements = parseDeclarationsAndStatements(false)")){
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,CASE_STATEMENT,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CastExpression,false);
            expect(tok("cast"));
            if (!tokenCheck("(")) {
                cleanup(m);
                return false;
            }
            if (!currentIs(tok(")")))
            {
                if (isCastQualifier()) {
                    if(!parseNodeQ("node.castQualifier", "CastQualifier")){
                        cleanup(m);
                        return false;
                    }
                } else {
                    if(!parseNodeQ("node.type", "Type")){
                        cleanup(m);
                        return false;
                    }
                }
            }
            if (!tokenCheck(")")) {
                cleanup(m);
                return false;
            }
            if(!parseNodeQ("node.unaryExpression", "UnaryExpression")){
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,CAST_EXPRESSION,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CastQualifier,false);
            IdType i = current().type;
            if (i.equals(tok("inout")) || i.equals(tok(""))) {
                advance();
                if (currentIs(tok("shared")))
                    advance();
            } else if (i.equals(tok("shared"))) {
                advance();
                if (currentIsOneOf(tok(""), tok("inout")))
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
         *
         * $(GRAMMAR $(RULEDEF catch):
         *     $(LITERAL 'catch') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL Identifier)? $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement)
         *     ;)
         */
        boolean parseCatch()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Catch,false);
            expect(tok("catch"));
            if (!tokenCheck("(")) {
                cleanup(m);
                return false;
            }
            if(!parseNodeQ("node.type", "Type")){
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
            if(!parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement")){
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,CATCH,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Catches,false);
            while (moreTokens())
            {
                if (!currentIs(tok("catch")))
                    break;
                if (peekIs(tok("("))) {
                    if (!parseCatch()) {
                        cleanup(m);
                        return false;
                    }
                } else {
                    if(!parseNodeQ("node.lastCatch", "LastCatch")){
                        cleanup(m);
                        return false;
                    }
                    break;
                }
            }
            exit_section_(builder,m,CATCHES,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ClassDeclaration,false);
            expect(tok("class"));
            final boolean result = parseInterfaceOrClass();
            exit_section_(builder,m,CLASS_DECLARATION,result);
            return result;
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
            final Marker m = enter_section_(builder);
            boolean shift = parseShiftExpression();
            if (!shift) {
                cleanup(m);
                return false;
            }
            if (!moreTokens()) {
                //                exit_section_(builder,m,DLanguageTypes.CMP_EXPRESSION,true);//todo
                return shift;
            }
            IdType i = current().type;
            if (i.equals(tok("is"))) {
                if (!parseIdentityExpression()) {
                    cleanup(m);
                    return false;
                }
                //                exit_section_(builder,m,DLanguageTypes.CMP_EXPRESSION,true);//todo
                return true;
            } else if (i.equals(tok("in"))) {
                if (!parseInExpression()) {
                    cleanup(m);
                    return false;
                }
                //                exit_section_(builder,m,DLanguageTypes.CMP_EXPRESSION,true);//todo
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
                //                exit_section_(builder,m,DLanguageTypes.CMP_EXPRESSION,true);//todo
                return true;
            } else if (i.equals(tok("<")) || i.equals(tok("<=")) || i.equals(tok(">")) || i.equals(tok(">=")) || i.equals(tok("!<>=")) || i.equals(tok("!<>")) || i.equals(tok("<>")) || i.equals(tok("<>=")) || i.equals(tok("!>")) || i.equals(tok("!>=")) || i.equals(tok("!<")) || i.equals(tok("!<="))) {
                if (!parseRelExpression()) {
                    cleanup(m);
                    return false;
                }
                //                exit_section_(builder,m,DLanguageTypes.CMP_EXPRESSION,true);//todo
                return true;
            } else if (i.equals(tok("==")) || i.equals(tok("!="))) {
                if (!parseEqualExpression()) {
                    cleanup(m);
                    return false;
                }
                return true;
                //                exit_section_(builder,m,DLanguageTypes.CMP_EXPRESSION,true);//todo
            } else {
//                exit_section_(builder,m,DLanguageTypes.CMP_EXPRESSION,true);//todo
                return true;
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.CompileCondition,false);
            IdType i = current().type;
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
            exit_section_(builder,m,CONDITIONAL_DECLARATION,true);//todo types
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
        boolean parseConditionalDeclaration(boolean strict)
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ConditionalDeclaration,false);
            if(!parseNodeQ("node.compileCondition", "CompileCondition")){cleanup(m/*todo*/);return false;}

            StackBuffer trueDeclarations;
            if (currentIs(tok(":")) || currentIs(tok("{")))
            {
                boolean brace = advance().type == tok("{");
                while (moreTokens() && !currentIs(tok("}")) && !currentIs(tok("else")))
                {
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
            }
            else if (!parseDeclaration(strict, true)) {
                return false;
            }

            if (currentIs(tok("else")))
            {
                advance();
            }
            else {
                exit_section_(builder,m,CONDITIONAL_DECLARATION,true);
                return true;
            }

            StackBuffer falseDeclarations;
            if (currentIs(tok(":")) || currentIs(tok("{")))
            {
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
            }
            else
            {
                if (!parseDeclaration(strict, true)) {
                    cleanup(m);
                    return false;
                }
            }
            exit_section_(builder,m,CONDITIONAL_DECLARATION,true);
            return true;
        }

        /**
         * Parses a ConditionalStatement
         *
         * $(GRAMMAR $(RULEDEF conditionalStatement):
         *     $(RULE compileCondition) $(RULE declarationOrStatement) ($(LITERAL 'else') $(RULE declarationOrStatement))?
         *     ;)
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
         *
         * $(GRAMMAR $(RULEDEF raint):
         *     $(LITERAL 'if') $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)')
         *     ;)
         */
        boolean parseConstraint()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Constraint,false);
            Token ifToken = expect(tok("if"));
            if(ifToken == null){
                cleanup(m);
                return false;
            }
            if (!tokenCheck("(")) {
                cleanup(m);
                return false;
            }
            if(!parseNodeQ("node.expression", "Expression")){
                cleanup(m);
                return false;
            }
            if (!tokenCheck(")")) {
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,CONSTRAINT,true);
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
//            Constructor node = allocator.make!Constructor;
//            node.comment = comment;
//            comment = null;
            final Marker m = enter_section_(builder);
            Token t = expect(tok("this"));
            mixin (nullCheck("t"));
//            node.location = t.index;
//            node.line = t.line;
//            node.column = t.column;
            Token p = peekPastParens();
            boolean isTemplate = false;
            if (p != null && p.type == tok("("))
            {
                isTemplate = true;
                if(!parseNodeQ("node.templateParameters", "TemplateParameters")){
                    cleanup(m);
                    return false;
                }
            }
            if(!parseNodeQ("node.parameters", "Parameters")){
                cleanup(m);
                return false;
            }

            while (moreTokens() && currentIsMemberFunctionAttribute())
                if (!parseMemberFunctionAttribute()) {
                    cleanup(m);
                    return false;
                }

            if (isTemplate && currentIs(tok("if")))
            if(!parseNodeQ("node.raint", "Constraint")){
                cleanup(m);
                return false;
            }
            if (currentIs(tok(";")))
                advance();
            else
                if(!parseNodeQ("node.functionBody", "FunctionBody")){
                    cleanup(m);
                    return false;
                }
            exit_section_(builder,m,CONSTRUCTOR,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ContinueStatement,false);
            if (!tokenCheck("continue")) {
                cleanup(m);
                return false;
            }
            IdType i = current().type;
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
            exit_section_(builder,m,CONTINUE_STATEMENT,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DebugCondition,false);
            Token d = expect(tok("debug"));
            if(d == null){
                cleanup(m);
                return false;
            }
//            node.debugIndex = d.index;

            if (currentIs(tok("(")))
            {
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
            exit_section_(builder,m,DEBUG_CONDITION,true);
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
            exit_section_(builder,m,DEBUG_SPECIFICATION,true);
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
        boolean parseDeclaration() {
            return parseDeclaration(false,false);
        }

        boolean parseDeclaration(boolean strict) {
            return parseDeclaration(strict,false);
        }

        boolean parseDeclaration(boolean strict, boolean mustBeDeclaration) {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Declaration,false);
            if (!moreTokens()) {
                error("declaration expected instead of EOF");
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

            final IdType idType = current().type;
            {
                if (idType.equals(tok("asm")) ||
                    idType.equals(tok("break")) || idType.equals(tok("case")) || idType.equals(tok("continue")) || idType.equals(tok("default")) || idType.equals(tok("do")) || idType.equals(tok("for")) || idType.equals(tok("foreach")) || idType.equals(tok("foreach_reverse")) || idType.equals(tok("goto")) || idType.equals(tok("if")) || idType.equals(tok("return")) || idType.equals(tok("switch")) || idType.equals(tok("throw")) || idType.equals(tok("try")) || idType.equals(tok("while")) || idType.equals(tok("assert"))) {
                    error("Declaration expected");
                    return false;
                } else if (idType.equals(tok(";"))) {
                    // http://d.magic.com/issues/show_bug.cgi?id=4559
                    warn("Empty declaration");
                    advance();
                } else if (idType.equals(tok("{"))) {
                    if (!nodeAttributes) {
                        error("declaration expected instead of '{'");
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
                    if (startsWith(tok("alias"), tok("identifier"), tok("this")))
                        if (!parseNodeQ("node.aliasThisDeclaration", "AliasThisDeclaration")) {
                            cleanup(m);
                            return false;
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
                        ++index;
                        Token past = peekPastParens();
                        --index;
                        if (past != null && past.type == tok(";"))
                            return false;
                    }
                    if (startsWith(tok("this"), tok("("), tok("this"), tok(")")))
                        if (!parseNodeQ("node.postblit", "Postblit")) {
                            cleanup(m);
                            return false;
                        } else if (!parseNodeQ("node.ructor", "Constructor")) {
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
                    if (peekIs(tok("template")))
                        if (!parseNodeQ("node.mixinTemplateDeclaration", "MixinTemplateDeclaration")) {
                            cleanup(m);
                            return false;
                        } else {
                            Bookmark b = setBookmark();
                            advance();
                            if (currentIs(tok("("))) {
                                Token t = peekPastParens();
                                if (t != null && t.type == tok(";")) {
                                    goToBookmark(b);
                                    if (!parseNodeQ("node.mixinDeclaration", "MixinDeclaration")) {
                                        cleanup(m);
                                        return false;
                                    }
                                } else {
                                    goToBookmark(b);
                                    error("Declaration expected");
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
                        } else {
                            if (startsWith(tok("shared"), tok("static"), tok("~")))
                                if (!parseNodeQ("node.sharedStaticDestructor", "SharedStaticDestructor")) {
                                    cleanup(m);
                                    return false;
                                } else {
                                    if (!type(m)) {
                                        cleanup(m);
                                        return false;
                                    }
                                }
                        }
                    }
                } else if (idType.equals(tok("static"))) {
                    if (peekIs(tok("this")))
                        if (!parseNodeQ("node.staticConstructor", "StaticConstructor")) {
                            cleanup(m);
                            return false;
                        } else if (peekIs(tok("~"))) {
                            if (!parseNodeQ("node.staticDestructor", "StaticDestructor")) {
                                cleanup(m);
                                return false;
                            } else if (peekIs(tok("if"))) {
                                if (!parseConditionalDeclaration(strict)) {
                                    cleanup(m);
                                    return false;
                                }
                            } else if (peekIs(tok("assert"))) {
                                if (!parseNodeQ("node.staticAssertDeclaration", "StaticAssertDeclaration")) {
                                    cleanup(m);
                                    return false;
                                } else {
                                    if (!type(m)) {
                                        cleanup(m);
                                        return false;
                                    }
                                }
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
                } else if (idType.equals(tok("identifier")) || idType.equals(tok(".")) || idType.equals(tok("")) || idType.equals(tok("immutable")) || idType.equals(tok("inout")) || idType.equals(tok("scope")) || idType.equals(tok("typeof")) || idType.equals(tok("__vector")) || idType.equals(tok("int")) || idType.equals(tok("bool")) || idType.equals(tok("byte")) || idType.equals(tok("cdouble")) || idType.equals(tok("cent")) || idType.equals(tok("cfloat")) || idType.equals(tok("char")) || idType.equals(tok("creal")) || idType.equals(tok("dchar")) || idType.equals(tok("double")) || idType.equals(tok("float")) || idType.equals(tok("idouble")) || idType.equals(tok("ifloat")) || idType.equals(tok("ireal")) || idType.equals(tok("long")) || idType.equals(tok("real")) || idType.equals(tok("short")) || idType.equals(tok("ubyte")) || idType.equals(tok("ucent")) || idType.equals(tok("uint")) || idType.equals(tok("ulong")) || idType.equals(tok("ushort")) || idType.equals(tok("void")) || idType.equals(tok("wchar"))) {
                    if (!type(m)) {
                        cleanup(m);
                        return false;
                    }
                } else if (idType.equals(tok("version"))) {
                    if (peekIs(tok("(")))
                        if (!parseConditionalDeclaration(strict)) {
                            cleanup(m);
                            return false;
                        } else if (peekIs(tok("=")))
                            if (!parseNodeQ("node.versionSpecification", "VersionSpecification")) {
                                cleanup(m);
                                return false;
                            } else {
                                error("\"=\" or \"(\" expected following \"version\"");
                                return false;
                            }
                } else if (idType.equals(tok("debug"))) {
                    if (peekIs(tok("=")))
                        if (!parseNodeQ("node.debugSpecification", "DebugSpecification")) {
                            cleanup(m);
                            return false;
                        } else if (!parseConditionalDeclaration(strict)) {
                            cleanup(m);
                            return false;
                        }
                } else {
                    error("Declaration expected");
                    return false;
                }
            }
            exit_section_(builder, m, DECLARATION, true);
            return true;
        }

        private boolean type(Marker m) {
            boolean t = parseType();
            if ((!t) || !currentIs(tok("identifier")))
                return false;
            if (peekIs(tok("(")))
                if (!parseFunctionDeclaration(t, false)) {
                    cleanup(m);
                    return false;
                } else if (!parseVariableDeclaration(t, false)) {
                    cleanup(m);
                    return false;
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DeclarationsAndStatements,false);
            StackBuffer declarationsAndStatements;
            while (!currentIsOneOf(tok("}"), tok("else")) && moreTokens() && suppressedErrorCount <= MAX_ERRORS)
            {
                if (currentIs(tok("case")) && !includeCases) {
                    break;
                }
                if (currentIs(tok("while")))
                {
                    Bookmark b = setBookmark();
                    advance();
                    if (currentIs(tok("(")))
                    {
                        Token p = peekPastParens();
                        if (p != null && p.type == tok(";")) {
                            goToBookmark(b);
                            break;
                        }
                        goToBookmark(b);
                    }
                }
//                c = allocator.setCheckpoint();
                if (!parseDeclarationOrStatement())
                {
//                    allocator.rollback(c);
                    if (suppressMessages > 0) {
                        cleanup(m);
                        return false;
                    }
                }
            }
//            ownArray(node.declarationsAndStatements, declarationsAndStatements);
            exit_section_(builder,m,DECLARATION_STATEMENT,true);//todo types
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DeclarationOrStatement,false);
            // "Any ambiguities in the grammar between Statements and
            // Declarations are resolved by the declarations taking precedence."
            Bookmark b = setBookmark();
//            c = allocator.setCheckpoint();
            boolean d = parseDeclaration(true, false);
            if (!d)
            {
//                allocator.rollback(c);
                goToBookmark(b);
                if(!parseNodeQ("node.statement", "Statement")){
                    cleanup(m);
                    return false;
                }
            }
            else
            {
                // TODO: Make this more efficient. Right now we parse the declaration
                // twice, once with errors and warnings ignored, and once with them
                // printed. Maybe store messages to then be abandoned or written later?
//                allocator.rollback(c);
                goToBookmark(b);
                parseDeclaration(true, true);
            }
            exit_section_(builder,m,DECLARATION_STATEMENT,true);//todo type
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Declarator,false);
            Token id = expect(tok("identifier"));
            if(id == null){
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
            if (currentIs(tok("(")))
            {
                if(!parseTemplateParameters()){
                    cleanup(m);
                    return false;
                }
                if (!tokenCheck("=")) {
                    cleanup(m);
                    return false;
                }
                if(!parseInitializer()){
                    cleanup(m);
                    return false;
                }
            }
            else if (currentIs(tok("=")))
            {
                advance();
                if(!parseNodeQ("node.initializer", "Initializer")){
                    cleanup(m);
                    return false;
                }
            }
            exit_section_(builder,m,DECLARATOR,true);
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
            if(!parseNodeQ("node.declarationsAndStatements", "DeclarationsAndStatements")){
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,DEFAULT_STATEMENT,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DeleteExpression,false);
//            node.line = current().line;
//            node.column = current().column;
            if (!tokenCheck("delete")) {
                cleanup(m);
                return false;
            }
            if(!parseNodeQ("node.unaryExpression", "UnaryExpression")){
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,DELETE_EXPRESSION,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Deprecated,false);
            if (!tokenCheck("deprecated")) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok("(")))
            {
                advance();
                if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                    cleanup(m);
                    return false;
                }
                if(!tokenCheck(")")){
                    cleanup(m);
                    return false;
                }
            }
            exit_section_(builder,m,DEPRECATED_ATTRIBUTE,true);//todo element type
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Destructor,false);
//            node.comment = comment;
//            comment = null;
            if (!tokenCheck("~")) {
                cleanup(m);
                return false;
            }
            if (!moreTokens())
            {
                error("'this' expected");
                return false;
            }
//            node.index = current().index;
//            node.line = current().line;
//            node.column = current().column;
            if(!tokenCheck("this")) {
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
            else
            {
                while (moreTokens() && currentIsMemberFunctionAttribute())
                    if (!parseMemberFunctionAttribute()) {
                        cleanup(m);
                        return false;
                    }
//                ownArray(node.memberFunctionAttributes, memberFunctionAttributes);
                if(!parseNodeQ("node.functionBody", "FunctionBody")){
                    cleanup(m);
                    return false;
                }
            }
            exit_section_(builder,m,DESTRUCTOR,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.DoStatement,false);
            if (!tokenCheck("do")) {
                cleanup(m);
                return false;
            }
            if(!parseNodeQ("node.statementNoCaseNoDefault", "StatementNoCaseNoDefault")){
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
            if(!parseNodeQ("node.expression", "Expression")){
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
            exit_section_(builder,m,DO_STATEMENT,true);
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
//            EnumBody node = allocator.make!EnumBody;
            final Marker m = enter_section_(builder);
            Token open = expect(tok("{"));
            mixin (nullCheck("open"));
//            node.startLocation = open.index;
//            StackBuffer enumMembers;
            EnumMember last;
            while (moreTokens())
            {
                if (currentIs(tok("identifier")))
                {
//                    auto c = allocator.setCheckpoint();
                    boolean e = parseEnumMember();
//                    if (!e)
//                        allocator.rollback(c);//todo
//                    else
//                        last = e;
                    if (currentIs(tok(",")))
                    {
//                        if (last != null && last.comment == null)
//                        last.comment = current().trailingComment;
                        advance();
                        if (!currentIs(tok("}")))
                            continue;
                    }
                    if (currentIs(tok("}")))
                    {
//                        if (last != null && last.comment == null)
//                        last.comment = tokens[index - 1].trailingComment;
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
//            ownArray(node.enumMembers, enumMembers);
            Token close = expect (tok("}"));
//            if (close != null)
//            node.endLocation = close.index;
            exit_section_(builder,m,ENUM_BODY,true);
            return true;
        }

        boolean parseAnonymousEnumMember(){
            return parseAnonymousEnumMember(false);
        }

        /**
         * $(GRAMMAR $(RULEDEF anonymousEnumMember):
         *       $(RULE type) $(LITERAL identifier) $(LITERAL '=') $(RULE assignExpression)
         *     | $(LITERAL identifier) $(LITERAL '=') $(RULE assignExpression)
         *     | $(LITERAL identifier)
         *     ;)
         */
        boolean parseAnonymousEnumMember(boolean typeAllowed)
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AnonymousEnumMember,false);

            if (currentIs(tok("identifier")) && peekIsOneOf(tok(","), tok("="), tok("}")))
            {
//                node.comment = current().comment;
                if(!tokenCheck("node.name", "identifier")){
                    cleanup(m);
                    return false;
                }
                if (currentIs(tok("=")))
                {
                    advance(); // =
                    if(!parseNodeQ("node.assignExpression", "AssignExpression")){
                        cleanup(m);
                        return false;
                    }
                }
            }
            else if (typeAllowed)
            {
    //            node.comment = current().comment;
                if(!parseNodeQ("node.type", "Type")){
                    cleanup(m);
                    return false;
                }
                if(tokenCheck("node.name", "identifier")){
                    cleanup(m);
                    return false;
                }
                if (!tokenCheck("=")) {
                    cleanup(m);
                    return false;
                }
                if(!parseNodeQ("node.assignExpression", "AssignExpression")){
                    cleanup(m);
                    return false;
                }
            }
            else
            {
                error("Cannot specify anonymous enum member type if anonymous enum has a base type.");
                return false;
            }
            exit_section_(builder,m,ENUM_MEMBER,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.AnonymousEnumDeclaration,false);
            if (!tokenCheck("enum")) {
                cleanup(m);
                return false;
            }
            boolean hasBaseType = currentIs(tok(":"));
            if (hasBaseType)
            {
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
            exit_section_(builder,m,ANONYMOUS_ENUM_DECLARATION,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.EnumDeclaration,false);
            if (!tokenCheck("enum")) {
                cleanup(m);
                return false;
            }
            if(!tokenCheck("node.name", "identifier")){
                cleanup(m);
                return false;
            }
//            node.comment = comment;
//            comment = null;
            if (currentIs(tok(";")))
            {
                advance();
                return false;
            }
            if (currentIs(tok(":")))
            {
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
            exit_section_(builder,m,ENUM_DECLARATION,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.EnumMember,false);
//            node.comment = current().comment;
            if(!tokenCheck("node.name", "identifier")){
                cleanup(m);
                return false;
            }
            if (currentIs(tok("=")))
            {
                advance();
                if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                    cleanup(m);
                    return false;
                }
            }
            exit_section_(builder,m,ENUM_MEMBER,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.EponymousTemplateDeclaration,false);
            advance(); // enum
            Token ident = expect(tok("identifier"));
            if (!nullCheck("ident")){
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
            exit_section_(builder, m,VAR_FUNC_DECLARATION, true);//todo check type
            return true;
        }

        boolean parseEqualExpression()
        {
            return parseEqualExpression(true);
        }

        /**
         * Parses an EqualExpression
         *
         * $(GRAMMAR $(RULEDEF equalExpression):
         *     $(RULE shiftExpression) ($(LITERAL '==') | $(LITERAL '!=')) $(RULE shiftExpression)
         *     ;)
         */
        boolean parseEqualExpression(boolean parseShift )
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.EqualExpression,false);
            if (parseShift) {
                boolean shift = parseShiftExpression();
                if(!shift){
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
            exit_section_(builder,m,EQUAL_EXPRESSION,true);
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
            final Marker m = enter_section_(builder);
            if (suppressedErrorCount > MAX_ERRORS)
                return false;
            if (!moreTokens())
            {
                error("Expected expression instead of EOF");
                return false;
            }
            final boolean result = parseCommaSeparatedRule("Expression", "AssignExpression");
            exit_section_(builder,m,EXPRESSION_STATEMENT,result);//todo types
            return result;
        }

        /**
         * Parses an ExpressionStatement
         *
         * $(GRAMMAR $(RULEDEF expressionStatement):
         *     $(RULE expression) $(LITERAL ';')
         *     ;)
         */
        boolean parseExpressionStatement(boolean parseExpression)
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ExpressionStatement,false);
            if (parseExpression) {
                final boolean b = parseExpression();
                if(!b){
                    cleanup(m);
                    return false;
                }
            }
            if (expect(tok(";")) == null) {
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,EXPRESSION_STATEMENT,true);
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
            final Marker m = enter_section_(builder);
            final boolean result = simpleParse("FinalSwitchStatement", tok("final"), "switchStatement|parseSwitchStatement");
            exit_section_(builder,m,FINAL_SWITCH_STATEMENT,result);
            return result;
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
            exit_section_(builder,m,FINALLY_STATEMENT,true);
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
            if (currentIs(tok("}")))
            {
                error("Statement expected");
                exit_section_(builder,m,FOR_STATEMENT,true);
                return true;
            }

            if (!parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement")) {
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,FOR_STATEMENT,true);
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
//            ForeachStatement node = allocator.make!ForeachStatement;
            final Marker m = enter_section_(builder);
            if (currentIsOneOf(tok("foreach"), tok("foreach_reverse"))) {
                advance();
            } else
            {
                error("\"foreach\" or \"foreach_reverse\" expected");
                cleanup(m);
                return false;
            }
            if (!tokenCheck("(")) {
                cleanup(m);
                return false;
            }
            boolean feType = parseForeachTypeList();
            if(!feType){
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
            mixin (nullCheck("node.low"));
            if (currentIs(tok("..")))
            {
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
            if (currentIs(tok("}")))
            {
                error("Statement expected"/*, false*/);
                exit_section_(builder,m,FOREACH_STATEMENT,true);
                return true; // this line makes DCD better
            }
            if (!parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement")) {
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,FOREACH_STATEMENT,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ForeachType,false);
            if (currentIs(tok("ref"))) {
                advance();
            }
            if (currentIsOneOf(tok(""), tok("immutable"),
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
                cleanup(m/*todo*/);
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
         *
         * $(GRAMMAR $(RULEDEF foreachTypeList):
         *     $(RULE foreachType) ($(LITERAL ',') $(RULE foreachType))*
         *     ;)
         */
        boolean parseForeachTypeList()
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            final Marker marker = enter_section_(builder);
            final boolean b = parseCommaSeparatedRule("ForeachTypeList", "ForeachType");
            exit_section_(builder,marker,FOREACH_TYPE_LIST,b);
            return b;
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.FunctionAttribute,false);
            IdType i = current().type;
            if (i.equals(tok("@"))) {
                if (!parseNodeQ("node.atAttribute", "AtAttribute")) {
                    cleanup(m);
                    return false;
                }
            } else if (i.equals(tok("")) || i.equals(tok(""))) {
                advance();
            } else {
                if (validate)
                    error("@attribute, \"\", or \"\" expected");
                return false;
            }
            exit_section_(builder,m,FUNCTION_ATTRIBUTE,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.FunctionBody,false);
            if (currentIs(tok(";"))) {
                advance();
                exit_section_(builder, m, FUNCTION_BODY, true);
                return true;
            } else if (currentIs(tok("{")))
                if (!parseNodeQ("node.blockStatement", "BlockStatement")) {
                    cleanup(m);
                    return false;
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
                    return false;
                }
            exit_section_(builder, m, FUNCTION_BODY, true);
            return true;
        }

        boolean parseFunctionCallExpression(){
            return parseFunctionCallExpression(true);
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
        boolean parseFunctionCallExpression(boolean parseUnary)//(UnaryExpression unary = null)
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));


            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.FunctionCallExpression,false);
            IdType i = current().type;
            if (i.equals(tok("")) || i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("shared")) || i.equals(tok("scope")) || i.equals(tok("")) || i.equals(tok(""))) {
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
            exit_section_(builder,m,null,true);//todo type
            return true;
        }

        boolean parseFunctionDeclaration(){
            return parseFunctionDeclaration(true,false);
        }

        /**
         * Parses a FunctionDeclaration
         *
         * $(GRAMMAR $(RULEDEF functionDeclaration):
         *       ($(RULE storageClass)+ | $(RULE _type)) $(LITERAL Identifier) $(RULE parameters) $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ';'))
         *     | ($(RULE storageClass)+ | $(RULE _type)) $(LITERAL Identifier) $(RULE templateParameters) $(RULE parameters) $(RULE memberFunctionAttribute)* $(RULE raint)? ($(RULE functionBody) | $(LITERAL ';'))
         *     ;)
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
            if (isAuto)
            {
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
            }
            else
            {
                while (moreTokens() && currentIsMemberFunctionAttribute())
                    if (!parseMemberFunctionAttribute()) {
                        cleanup(m);
                        return false;
                    }
                if (parseType) {
                    if(!parseNodeQ("node.returnType", "Type")){
                        cleanup(m);
                        return false;
                    }
                }
//                else
//                node.returnType = type;
            }

            if(!tokenCheck("node.name", "identifier")){//todo check this
                cleanup(m);
                return false;
            }
            if (!currentIs(tok("(")))
            {
                error("'(' expected");
//                cleanup(m);//todo
                return false;
            }
            Token p = peekPastParens();
            boolean isTemplate = p != null && p.type != tok("(");

            if (isTemplate) {
                if(!parseNodeQ("node.templateParameters", "TemplateParameters")){
                    cleanup(m);
                    return false;
                }
            }

            if(!parseNodeQ("node.parameters", "Parameters")){
                cleanup(m);
                return false;
            }

            while (moreTokens() && currentIsMemberFunctionAttribute())
                if (!parseMemberFunctionAttribute()) {
                cleanup(m);
                return false;
                }

            if (isTemplate && currentIs(tok("if"))) {
                if(!parseNodeQ("node.raint", "Constraint")){
                    cleanup(m);
                    return false;
                };
            }

            if (currentIs(tok(";")))
                advance();
            else {
                if(!parseNodeQ("node.functionBody", "FunctionBody")){
                    cleanup(m);
                    return false;
                }
            }
            exit_section_(builder,m,FUNC_DECLARATION,true);
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
            exit_section_(builder, m,FUNCTION_LITERAL, true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.GotoStatement,false);
            if (!tokenCheck("goto")) {
                cleanup(m);
                return false;
            }
            IdType i = current().type;
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
                return false;//todo cleanup?
            }
            if (!tokenCheck(";")) {
                return false;
            }
            exit_section_(builder, m,GOTO_STATEMENT, true);
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
            exit_section_(builder, m,null/*todo*/, true);//todo type
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IdentifierList,false);
            while (moreTokens())
            {
                Token ident = expect(tok("identifier"));
                if (ident == null) {
                    cleanup(m);
                    return false;
                }
                if (currentIs(tok(",")))
                {
                    advance();
                    continue;
                }
                else
                    break;
            }
            exit_section_(builder,m,IDENTIFIER_LIST,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IdentifierOrTemplateChain,false);
            int identifiersOrTemplateInstancesLength = 0;
            while (moreTokens())
            {
//                auto c = allocator.setCheckpoint();
                if (!parseIdentifierOrTemplateInstance())
                {
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
            exit_section_(builder,m,IDENTIFIER_LIST/*todo type*/,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IdentifierOrTemplateInstance,false);
            if (peekIs(tok("!")) && !startsWith(tok("identifier"),
                tok("!"), tok("is"))
                && !startsWith(tok("identifier"), tok("!"), tok("in"))) {
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

        boolean parseIdentityExpression(){
            return parseIdentityExpression(true);
        }

        /**
         * Parses an IdentityExpression
         *
         * $(GRAMMAR $(RULEDEF identityExpression):
         *     $(RULE shiftExpression) ($(LITERAL 'is') | ($(LITERAL '!') $(LITERAL 'is'))) $(RULE shiftExpression)
         *     ;)
         */
        boolean parseIdentityExpression(boolean parseShift)//(ExpressionNode shift = null)
        {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.IdentityExpression,false);
            if(parseShift){
                if(!parseShiftExpression()){
                    cleanup(m);
                    return false;
                }
            }
            if (currentIs(tok("!")))
            {
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
            exit_section_(builder,m,IDENTITY_EXPRESSION,true);
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
                    while (isTypeCtor(current().type)) {
                        advance();
                    }
                    // goes back for TypeCtor(Type) = identifier
                    if (currentIs(tok("(")))
                        index--;
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
                    mixin(tokenCheck("node.identifier", "identifier"));
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
         *
         * $(GRAMMAR $(RULEDEF importBind):
         *     $(LITERAL Identifier) ($(LITERAL '=') $(LITERAL Identifier))?
         *     ;)
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
         *
         * $(GRAMMAR $(RULEDEF importBindings):
         *     $(RULE singleImport) $(LITERAL ':') $(RULE importBind) ($(LITERAL ',') $(RULE importBind))*
         *     ;)
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
         *
         * $(GRAMMAR $(RULEDEF importDeclaration):
         *       $(LITERAL 'import') $(RULE singleImport) ($(LITERAL ',') $(RULE singleImport))* ($(LITERAL ',') $(RULE importBindings))? $(LITERAL ';')
         *     | $(LITERAL 'import') $(RULE importBindings) $(LITERAL ';')
         *     ;)
         */
        boolean parseImportDeclaration()
        {
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
            exit_section_(builder, m,IMPORT_DECLARATION, true);
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
            final Marker marker = enter_section_(builder);
            final boolean b = simpleParse("ImportExpression", tok("import"), tok("("),
                "assignExpression|parseAssignExpression", tok(")"));
            exit_section_(builder,marker,IMPORT_EXPRESSION,b);
            return b;
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

        boolean parseIndexExpression(){
            return parseIndexExpression(true);
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
            exit_section_(builder, m,INDEX_EXPRESSION, true);
            return true;
        }

        boolean parseInExpression(){
            return parseInExpression(true);
        }

        /**
         * Parses an InExpression
         *
         * $(GRAMMAR $(RULEDEF inExpression):
         *     $(RULE shiftExpression) ($(LITERAL 'in') | ($(LITERAL '!') $(LITERAL 'in'))) $(RULE shiftExpression)
         *     ;)
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
            exit_section_(builder, m,IN_EXPRESSION, true);
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
            exit_section_(builder, m,IN_STATEMENT, true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Initializer,false);
            if (currentIs(tok("void")) && peekIsOneOf(tok(","), tok(";")))
                advance();
            else if (!parseNodeQ("node.nonVoidInitializer", "NonVoidInitializer")) {
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,INITIALIZER,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.InterfaceDeclaration,false);
            expect(tok("interface"));
            final boolean b = parseInterfaceOrClass();
            exit_section_(builder,m,INTERFACE_DECLARATION,b);
            return b;
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Invariant,false);
//            node.index = current().index;
//            node.line = current().line;
            if (!tokenCheck("invariant")) {
                cleanup(m);
                return false;
            }
            if (currentIs(tok("(")))
            {
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
            exit_section_(builder,m,INVARIANT,true);
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
                return false;
            }
            exit_section_(builder, m,IS_EXPRESSION, true);
            return true;
        }

        /**
         * Parses a KeyValuePair
         *
         * $(GRAMMAR $(RULEDEF keyValuePair):
         *     $(RULE assignExpression) $(LITERAL ':') $(RULE assignExpression)
         *     ;)
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
            exit_section_(builder, m,KEY_VALUE_PAIR, true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.KeyValuePairs,false);
            while (moreTokens())
            {
                if (!parseKeyValuePair()) {
                    cleanup(m);
                    return false;
                }
                if (currentIs(tok(",")))
                {
                    advance();
                    if (currentIs(tok("]")))
                    break;
                }
            else
                break;
            }
            exit_section_(builder,m,KEY_VALUE_PAIRS,true);
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
            exit_section_(builder, m,LABELED_STATEMENT, true);
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
            exit_section_(builder, m,LAST_CATCH, true);
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
        boolean parseLinkageAttribute() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.LinkageAttribute,false);
            if(!tokenCheck("extern")){
                cleanup(m);
                return false;
            }
            if(!tokenCheck("(")){
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
            exit_section_(builder, m,LINKAGE_ATTRIBUTE, true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.MemberFunctionAttribute,false);
            IdType i = current().type;
            if (i.equals(tok("@"))) {
                if (!parseNodeQ("node.atAttribute", "AtAttribute")) {
                    cleanup(m);
                    return false;
                }
            } else if (i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("shared")) || i.equals(tok("")) || i.equals(tok("")) || i.equals(tok("")) || i.equals(tok("return")) || i.equals(tok("scope"))) {
                advance();
            } else {
                error("Member function attribute expected");//todo notify libdparse of spelling mistae in original source
            }
            exit_section_(builder,m,MEMBER_FUNCTION_ATTRIBUTE,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.MixinDeclaration,false);
            if (peekIsOneOf(tok("identifier"), tok("typeof"), tok(".")))
                if (!parseNodeQ("node.templateMixinExpression", "TemplateMixinExpression")) {
                    cleanup(m);
                    return false;
                } else if (peekIs(tok("(")))
                    if (!parseNodeQ("node.mixinExpression", "MixinExpression")) {
                        cleanup(m);
                        return false;
                    } else {
                        error("\" (\" or identifier expected");
                        return false;
                    }
            expect(tok(";"));
            exit_section_(builder, m,MIXIN_DECLARATION, true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.MixinExpression,false);
            expect(tok("mixin"));
            expect(tok("("));
            if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                cleanup(m);
                return false;
            }
            expect(tok(")"));
            exit_section_(builder, m,MIXIN_EXPRESSION, true);
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
            exit_section_(builder, m,TEMPLATE_MIXIN_DECLARATION, true);
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
            exit_section_(builder, m,MIXIN_TEMPLATE_NAME, true);
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
//            Module m = allocator.make!Module;
            final Marker marker = enter_section_(builder);
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
         *
         * $(GRAMMAR $(RULEDEF moduleDeclaration):
         *     $(RULE deprecated)? $(LITERAL 'module') $(RULE identifierChain) $(LITERAL ';')
         *     ;)
         */
        boolean parseModuleDeclaration()
        {
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
            exit_section_(builder, m,MODULE_DECLARATION, true);
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
            final Marker marker = enter_section_(builder);
            final boolean b = parseLeftAssocBinaryExpression("MulExpression", "PowExpression",
                tok("*"), tok("/"), tok("%"));
            exit_section_(builder,marker,MUL_EXPRESSION_,b);
            return b;
        }

        /**
         * Parses a NewAnonClassExpression
         *
         * $(GRAMMAR $(RULEDEF newAnonClassExpression):
         *     $(LITERAL 'new') $(RULE arguments)? $(LITERAL 'class') $(RULE arguments)? $(RULE baseClassList)? $(RULE structBody)
         *     ;)
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
            exit_section_(builder, m,NEW_ANON_CLASS_EXPRESSION, true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.NewExpression,false);
            if (peekIsOneOf(tok("class"), tok("(")))
                if (!parseNodeQ("node.newAnonClassExpression", "NewAnonClassExpression")) {
                    cleanup(m);
                    return false;
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
            exit_section_(builder, m,NEW_EXPRESSION_WITH_ARGS, true);
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
        boolean parseNonVoidInitializer() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.NonVoidInitializer,false);
            boolean assignExpressionParsed = false;
            boolean arrayInitializerParsed = false;
            boolean structInitializerParsed = false;
            if (currentIs(tok("{"))) {
                Token b = peekPastBraces();
                if (b != null && (b.type == tok("(")))
                    if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                        cleanup(m);
                        return false;
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
                if (b != null && (b.type == tok(",")
                    || b.type == tok(")")
                    || b.type == tok("]")
                    || b.type == tok("}")
                    || b.type == tok(";"))) {
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
            exit_section_(builder, m,NON_VOID_INITIALIZER, true);
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
//            Operands node = allocator.make!Operands;
            final Marker marker = enter_section_(builder);
//            StackBuffer expressions;
            while (true)
            {
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
            exit_section_(builder,marker,OPERANDS,true);
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
            final Marker marker = enter_section_(builder);
            final boolean b = parseLeftAssocBinaryExpression("OrExpression", "XorExpression",
                tok("|"));
            exit_section_(builder,marker,AND_EXXPRESSION_,b);//todo type
            return b;
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
            final Marker marker = enter_section_(builder);
            final boolean b = parseLeftAssocBinaryExpression("OrOrExpression", "AndAndExpression",
                tok("||"));
            exit_section_(builder,marker,OR_OR_EXPRESSION,b);
            return b;
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.OutStatement,false);
            Token o = expect(tok("out"));
            if (o == null) {
                cleanup(m);
                return false;
            }
//            node.outTokenLocation = o.index;
            if (currentIs(tok("(")))
            {
                advance();
                Token ident = expect(tok("identifier"));
                if(ident == null) {
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
            exit_section_(builder,m,OUT_STATEMENT,true);
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
        boolean parseParameter() {
//            mixin(traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Parameter,false);
            while (moreTokens()) {
                IdType type = parseParameterAttribute();//todo check args
                if (type == tok(""))
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
            exit_section_(builder, m,PARAMETER, true);
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
        Token.IdType parseParameterAttribute() {
            final boolean validate = false;
//            mixin(traceEnterAndExit!(__FUNCTION__));
            IdType i = current().type;
            if (i.equals(tok("immutable")) || i.equals(tok("shared")) || i.equals(tok("")) || i.equals(tok("inout"))) {
                if (peekIs(tok("(")))
                    return tok("");
            }
            if (i.equals(tok("final")) || i.equals(tok("in")) || i.equals(tok("lazy")) || i.equals(tok("out")) || i.equals(tok("ref")) || i.equals(tok("scope")) || i.equals(tok("auto")) || i.equals(tok("return"))) {
                return advance().type;
            } else {
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Parameters,false);
            if (!tokenCheck("(")) {
                cleanup(m);
                return false;
            }

            if (currentIs(tok(")")))
            {
                advance(); // )
                exit_section_(builder,m,PARAMETERS,true);
                return true;
            }
            if (currentIs(tok("...")))
            {
                advance();
//                node.hasVarargs = true;
                if (!tokenCheck(")")) {
                    cleanup(m);
                    return false;
                }
                exit_section_(builder,m,PARAMETERS,true);
                return true;
            }
            StackBuffer parameters;
            while (moreTokens())
            {
                if (currentIs(tok("...")))
                {
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
            exit_section_(builder,m,PARAMETERS,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Postblit,false);
//            node.line = current().line;
//            node.column = current().column;
//            node.location = current().index;
            index += 4;//todo make sure stuff is consumed appropriately
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
            exit_section_(builder,m,POSTBLIT,true);
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
            final Marker marker = enter_section_(builder);
            final boolean b = parseLeftAssocBinaryExpression("PowExpression", "UnaryExpression",
                tok("^^"));
            exit_section_(builder,marker,POW_EXPRESSION_,b);
            return b;
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
            final Marker marker = enter_section_(builder);
            final boolean res = simpleParse("PragmaDeclaration", "pragmaExpression|parsePragmaExpression", tok(";"));
            exit_section_(builder,marker,PRAGMA_STATEMENT,true);//todo type
            return res;
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
            if (currentIs(tok(",")))
            {
                advance();
                if (!parseNodeQ("node.argumentList", "ArgumentList")) {
                    cleanup(m);
                    return false;
                }
            }
            expect(tok(")"));
            exit_section_(builder,m,PRAGMA,true);//todo type
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.PrimaryExpression,false);
            if (!moreTokens())
            {
                error("Expected primary statement instead of EOF");
                return false;
            }
            if(current().type == tok(".")){
                advance();
            }
            IdType i = current().type;
            if (i.equals(tok("identifier"))) {
                if (peekIs(tok("=>")))
                    if (!parseNodeQ("node.functionLiteralExpression", "FunctionLiteralExpression")) {
                        cleanup(m);
                        return false;
                    } else if (!parseNodeQ("node.identifierOrTemplateInstance", "IdentifierOrTemplateInstance")) {
                        cleanup(m);
                        return false;
                    }
            } else if (i.equals(tok("immutable")) || i.equals(tok("")) || i.equals(tok("inout")) || i.equals(tok("shared"))) {
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
                if (isAssociativeArrayLiteral())
                    if (!parseNodeQ("node.assocArrayLiteral", "AssocArrayLiteral")) {
                        cleanup(m);
                        return false;
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
                if (currentIsOneOf(tok("StringLiteral"), tok("wStringLiteral"), tok("dStringLiteral"))) {
                    advance();
                    boolean alreadyWarned = false;
                    while (currentIsOneOf(tok("StringLiteral"), tok("wStringLiteral"),
                        tok("dStringLiteral"))) {
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
                return false;
            }
            exit_section_(builder,m,PRIMARY_EXPRESSION,true);
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

        boolean parseRelExpression(){
            return parseRelExpression(true);
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
        boolean parseRelExpression(boolean parseShift)//(ExpressionNode shift)
        {
//            mixin (traceEnterAndExit!(__FUNCTION__));
            final Marker marker = enter_section_(builder);
            final boolean b = parseLeftAssocBinaryExpression("RelExpression", "ShiftExpression", parseShift, tok("<"), tok("<="), tok(">"), tok(">="), tok("!<>="), tok("!<>"), tok("<>"), tok("<>="), tok("!>"), tok("!>="), tok("!>="), tok("!<"), tok("!<="));
            exit_section_(builder,marker,REL_EXPRESSION,b);
            return b;
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ReturnStatement,false);
            Token start = expect(tok("return"));
            if(start == null) {
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
            exit_section_(builder,m,RETURN_STATEMENT,true);
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
            exit_section_(builder,m,SCOPE_GUARD_STATEMENT,true);
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
            exit_section_(builder,m,SHARED_STATIC_CONSTRUCTOR,b);
            return b;
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
            exit_section_(builder,m,SHARED_STATIC_DESTRUCTOR,b);
            return b;
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
            final Marker marker = enter_section_(builder);
            final boolean b = parseLeftAssocBinaryExpression("ShiftExpression", "AddExpression", tok("<<"), tok(">>"), tok(">>>"));
            exit_section_(builder,marker,SHIFT_EXPRESSION_,b);
            return b;
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.SingleImport,false);
            if (startsWith(tok("identifier"), tok("=")))
            {
                advance(); // identifier
                advance(); // =
            }
            if (!parseNodeQ("node.identifierChain", "IdentifierChain")) {
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,IMPORT,true);//todo type
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Statement,false);
            if (!moreTokens())
            {
                error("Expected statement instead of EOF");
                return false;
            }
            IdType i = current().type;
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
            exit_section_(builder,m,STATEMENT,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StatementNoCaseNoDefault,false);
            current();
            IdType i = current().type;
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
                    exit_section_(builder,m,STATEMENT_NO_CASE_NO_DEFAULT,true);
                    return true;
                } else {
                    error("\"switch\" expected");
                    return false;
                }
            } else if (i.equals(tok("debug"))) {
                if (peekIs(tok("=")))
                    if (!parseNodeQ("node.debugSpecification", "DebugSpecification")) {
                        cleanup(m);
                        return false;
                    } else if (!parseNodeQ("node.conditionalStatement", "ConditionalStatement")) {
                        cleanup(m);
                        return false;
                    }
            } else if (i.equals(tok("version"))) {
                if (peekIs(tok("=")))
                    if (!parseNodeQ("node.versionSpecification", "VersionSpecification")) {
                        cleanup(m);
                        return false;
                    } else if (!parseNodeQ("node.conditionalStatement", "ConditionalStatement")) {
                        cleanup(m);
                        return false;
                    }
            } else if (i.equals(tok("static"))) {
                if (peekIs(tok("if")))
                    if (!parseNodeQ("node.conditionalStatement", "ConditionalStatement")) {
                        cleanup(m);
                        return false;
                    } else if (peekIs(tok("assert")))
                        if (!parseNodeQ("node.staticAssertStatement", "StaticAssertStatement")) {
                            cleanup(m);
                            return false;
                        } else {
                            error("'if' or 'assert' expected.");
                            return false;
                        }
            } else if (i.equals(tok("identifier"))) {
                if (peekIs(tok(":"))) {
                    if (!parseNodeQ("node.labeledStatement", "LabeledStatement")) {
                        cleanup(m);
                        return false;
                    }
                    exit_section_(builder,m,STATEMENT_NO_CASE_NO_DEFAULT,true);
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
            exit_section_(builder,m,STATEMENT_NO_CASE_NO_DEFAULT,true);
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
            final Marker marker = enter_section_(builder);
            final boolean b = simpleParse("StaticAssertDeclaration",
                "staticAssertStatement|parseStaticAssertStatement");
            exit_section_(builder,marker,STATIC_ASSERT,b);
            return b;
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
            final Marker marker = enter_section_(builder);
            final boolean b = simpleParse("StaticAssertStatement",
                tok("static"), "assertExpression|parseAssertExpression", tok(";"));
            exit_section_(builder,marker,STATIC_ASSERT,b);
            return b;
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StaticConstructor,false);
//            node.location = current().index;
            if (!tokenCheck("static")) {
                cleanup(m);
                return false;
            }
            final boolean b = parseStaticCtorDtorCommon();
            exit_section_(builder,m,STATIC_CONSTRUCTOR,b);
            return b;
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StaticDestructor,false);
//            node.location = current().index;
            if(!tokenCheck("static")) {
                cleanup(m);
                return false;
            }
            if (!tokenCheck("~")) {
                cleanup(m);
                return false;
            }
            final boolean b = parseStaticCtorDtorCommon();
            exit_section_(builder,m,STATIC_DESTRUCTOR,b);
            return b;
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
            final Marker marker = enter_section_(builder);
            final boolean b = simpleParse("StaticIfCondition", tok("static"), tok("if"), tok("("),
                "assignExpression|parseAssignExpression", tok(")"));
            exit_section_(builder,marker,STATIC_IF_CONDITION,b);
            return b;
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
        boolean parseStorageClass() {
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StorageClass,false);
            IdType i = current().type;
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
            } else if (i.equals(tok("")) || i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("shared")) || i.equals(tok("abstract")) || i.equals(tok("auto")) || i.equals(tok("enum")) || i.equals(tok("final")) || i.equals(tok("")) || i.equals(tok("override")) || i.equals(tok("")) || i.equals(tok("ref")) || i.equals(tok("__gshared")) || i.equals(tok("scope")) || i.equals(tok("static")) || i.equals(tok("synchronized"))) {
                advance();
            } else {
                error("Storage class expected");
                return false;
            }
            exit_section_(builder, m, STORAGE_CLASS, true);
            return true;
        }

        /**
         * Parses a StructBody
         *
         * $(GRAMMAR $(RULEDEF structBody):
         *     $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
         *     ;)
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
         *
         * $(GRAMMAR $(RULEDEF structDeclaration):
         *     $(LITERAL 'struct') $(LITERAL Identifier)? ($(RULE templateParameters) $(RULE raint)? $(RULE structBody) | ($(RULE structBody) | $(LITERAL ';')))
         *     ;)
         */
        boolean parseStructDeclaration()
        {
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
                return false;
            }
            exit_section_(builder,m,STRUCT_DECLARATION,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StructInitializer,false);
            Token a = expect(tok("{"));
//            node.startLocation = a.index;
            if (currentIs(tok("}")))
            {
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
            exit_section_(builder, m,STRUCT_INITIALIZER, true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StructMemberInitializer,false);
            if (startsWith(tok("identifier"), tok(":")))
            {
                /*node.identifier = *//*tokens[*/index++/*]*/;//todo make sure consumption is done correctly
                index++;
            }
            if (!parseNodeQ("node.nonVoidInitializer", "NonVoidInitializer")) {
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,STRUCT_MEMBER_INITIALIZER,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.StructMemberInitializers,false);
            do
            {
//                auto c = allocator.setCheckpoint();
                parseStructMemberInitializer();
//                    allocator.rollback(c);
                if (currentIs(tok(",")))
                    advance();
            else
                break;
            } while (moreTokens() && !currentIs(tok("}")));
//            ownArray(node.structMemberInitializers, structMemberInitializers);
            exit_section_(builder,m,STRUCT_MEMBER_INITIALIZERS,true);
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
            exit_section_(builder, m,SWITCH_STATEMENT, true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.Symbol,false);
            if (currentIs(tok(".")))
            {
//                node.dot = true;
                advance();
            }
            if (!parseNodeQ("node.identifierOrTemplateChain", "IdentifierOrTemplateChain")) {
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,SYMBOL,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.SynchronizedStatement,false);
            expect(tok("synchronized"));
            if (currentIs(tok("(")))
            {
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
            exit_section_(builder,m,SYNCHRONIZED_STATEMENT,true);
            return true;
        }

        /**
         * Parses a TemplateAliasParameter
         *
         * $(GRAMMAR $(RULEDEF templateAliasParameter):
         *     $(LITERAL 'alias') $(RULE type)? $(LITERAL Identifier) ($(LITERAL ':') ($(RULE type) | $(RULE assignExpression)))? ($(LITERAL '=') ($(RULE type) | $(RULE assignExpression)))?
         *     ;)
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
                if (isType())
                    if (!parseNodeQ("node.colonType", "Type")) {
                        cleanup(m);
                        return false;
                    } else if (!parseNodeQ("node.colonExpression", "AssignExpression")) {
                        cleanup(m);
                        return false;
                    }
            }
            if (currentIs(tok("="))) {
                advance();
                if (isType())
                    if (!parseNodeQ("node.assignType", "Type")) {
                        cleanup(m);
                        return false;
                    } else if (!parseNodeQ("node.assignExpression", "AssignExpression")) {
                        cleanup(m);
                        return false;
                    }
            }
            exit_section_(builder, m,TEMPLATE_ALIAS_PARAMETER, true);
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
            exit_section_(builder, m,TEMPLATE_ARGUMENT, true);
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
            final Marker marker = enter_section_(builder);
            final boolean b = parseCommaSeparatedRule("TemplateArgumentList", "TemplateArgument");
            exit_section_(builder,marker,TEMPLATE_ARGUMENT_LIST,b);
            return b;
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
            exit_section_(builder, m,TEMPLATE_ARGUMENTS, true);
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
                if (!parseNodeQ("node.raint", "Constraint")) {
                    cleanup(m);
                    return false;
                }
            Token start = expect(tok("{"));
            if(start == null) {
                cleanup(m);
                return false;
            }
//            node.startLocation = start.index;
//            StackBuffer declarations;
            while (moreTokens() && !currentIs(tok("}")))
            {
//                c = allocator.setCheckpoint();
                parseDeclaration(true, true);
//                    allocator.rollback(c);
            }
//            ownArray(node.declarations, declarations);
            Token end = expect(tok("}"));
//            if (end != null) node.endLocation = end.index;
            exit_section_(builder,m,TEMPLATE_DECLARATION,true);
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
            if (suppressedErrorCount > MAX_ERRORS) return false;
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateInstance,false);
            Token ident = expect(tok("identifier"));
            if(ident == null) {
                cleanup(m);
                return false;
            }
//            node.identifier = *ident;
            if (!parseNodeQ("node.templateArguments", "TemplateArguments")) {
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,TEMPLATE_INSTANCE,true);
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
            exit_section_(builder,m,MIXIN_EXPRESSION,true);//todo type
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateParameter,false);
            IdType i = current().type;
            if (i.equals(tok("alias"))) {
                if (!parseNodeQ("node.templateAliasParameter", "TemplateAliasParameter")) {
                    cleanup(m);
                    return false;
                }
            } else if (i.equals(tok("identifier"))) {
                if (peekIs(tok("...")))
                    if (!parseNodeQ("node.templateTupleParameter", "TemplateTupleParameter")) {
                        cleanup(m);
                        return false;
                    } else if (peekIsOneOf(tok(":"), tok("="), tok(","), tok(")")))
                        if (!parseNodeQ("node.templateTypeParameter", "TemplateTypeParameter")) {
                            cleanup(m);
                            return false;
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
            exit_section_(builder,m,TEMPLATE_PARAMETER,true);
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
            final Marker marker = enter_section_(builder);
            final boolean b = parseCommaSeparatedRule("TemplateParameterList", "TemplateParameter");
            exit_section_(builder,marker,TEMPLATE_PARAMETER_LIST,b);
            return b;
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
                return false;
            }
            exit_section_(builder, m,TEMPLATE_PARAMETERS, true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateSingleArgument,false);
            if (!moreTokens())
            {
                error("template argument expected instead of EOF");
                return false;
            }
            IdType i = current().type;
            if (i.equals(tok("this")) || i.equals(tok("identifier")) || i.equals(tok("characterLiteral")) || i.equals(tok("true")) || i.equals(tok("false")) || i.equals(tok("null")) || i.equals(tok("$")) || i.equals(tok("dstringLiteral")) || i.equals(tok("stringLiteral")) || i.equals(tok("wstringLiteral")) || i.equals(tok("doubleLiteral")) || i.equals(tok("floatLiteral")) || i.equals(tok("idoubleLiteral")) || i.equals(tok("ifloatLiteral")) || i.equals(tok("intLiteral")) || i.equals(tok("longLiteral")) || i.equals(tok("realLiteral")) || i.equals(tok("irealLiteral")) || i.equals(tok("uintLiteral")) || i.equals(tok("ulongLiteral")) || i.equals(tok("__DATE__")) || i.equals(tok("__TIME__")) || i.equals(tok("__TIMESTAMP__")) || i.equals(tok("__VENDOR__")) || i.equals(tok("__VERSION__")) || i.equals(tok("__FILE__")) || i.equals(tok("__FILE_FULL_PATH__")) || i.equals(tok("__LINE__")) || i.equals(tok("__MODULE__")) || i.equals(tok("__FUNCTION__")) || i.equals(tok("__PRETTY_FUNCTION__")) || i.equals(tok("int")) || i.equals(tok("bool")) || i.equals(tok("byte")) || i.equals(tok("cdouble")) || i.equals(tok("cent")) || i.equals(tok("cfloat")) || i.equals(tok("char")) || i.equals(tok("creal")) || i.equals(tok("dchar")) || i.equals(tok("double")) || i.equals(tok("float")) || i.equals(tok("idouble")) || i.equals(tok("ifloat")) || i.equals(tok("ireal")) || i.equals(tok("long")) || i.equals(tok("real")) || i.equals(tok("short")) || i.equals(tok("ubyte")) || i.equals(tok("ucent")) || i.equals(tok("uint")) || i.equals(tok("ulong")) || i.equals(tok("ushort")) || i.equals(tok("void")) || i.equals(tok("wchar"))) {
                advance();
            } else {
                error("Invalid template argument. (Try enclosing in parenthesis?)");
                return false;
            }
            exit_section_(builder,m,TEMPLATE_SINGLE_ARGUMENT,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateThisParameter,false);
            expect(tok("this"));
            if (!parseNodeQ("node.templateTypeParameter", "TemplateTypeParameter")) {
                cleanup(m);
                return false;
            }
            exit_section_(builder,m,TEMPLATE_THIS_PARAMETER,true);
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
            exit_section_(builder,m,TEMPLATE_TUPLE_PARAMETER,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateTypeParameter,false);
         ident = expect(tok("identifier"));
            if(!ident){
            cleanup(m/*todo*/);
            return false;
            }
            node.identifier = *ident;
            if (currentIs(tok(":")))
            {
                advance();
                if(!parseNodeQ("node.colonType", "Type")){cleanup(m/*todo*/);return false;}
            }
            if (currentIs(tok("=")))
            {
                advance();
                if(!parseNodeQ("node.assignType", "Type")){cleanup(m/*todo*/);return false;}
            }
            exit_section_(builder,m,/*todo*/,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TemplateValueParameter,false);
            if(!parseNodeQ("node.type", "Type")){cleanup(m/*todo*/);return false;}
            mixin(tokenCheck!("node.identifier", "identifier"));
            if (currentIs(tok(":")))
            {
                advance();
                if(!parseNodeQ("node.assignExpression", "AssignExpression")){cleanup(m/*todo*/);return false;}
            }
            if (currentIs(tok("=")))
            if(!parseNodeQ("node.templateValueParameterDefault", "TemplateValueParameterDefault")){cleanup(m/*todo*/);return false;}
            exit_section_(builder,m,/*todo*/,true);
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
            Marker m = enter_section_(builder);
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
                    if(!parseNodeQ("node.assignExpression", "AssignExpression")){cleanup(m/*todo*/);return false;}
                    break;
            }
            exit_section_(builder,m,/*todo*/,true);
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
                if(!parseNodeQ("node.expression", "Expression")){cleanup(m/*todo*/);return false;}
                auto colon = expect(tok(":"));
                if(!colon){
                cleanup(m/*todo*/);
                return false;
                }
                node.colon = *colon;
                if(!parseNodeQ("node.ternaryExpression", "TernaryExpression")){cleanup(m/*todo*/);return false;}
                exit_section_(builder,m,/*todo*/,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.ThrowStatement,false);
            expect(tok("throw"));
            if(!parseNodeQ("node.expression", "Expression")){cleanup(m/*todo*/);return false;}
            expect(tok(";"));
            exit_section_(builder,m,/*todo*/,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TraitsExpression,false);
            if(!tokenCheck("__traits")){return false;}
            if(!tokenCheck("(")){return false;}
         ident = expect(tok("identifier"));
            if(!ident){
            cleanup(m/*todo*/);
            return false;
            }
            node.identifier = *ident;
            if (currentIs(tok(",")))
            {
                advance();
                if(!(node.templateArgumentList = parseTemplateArgumentList())){
                cleanup(m/*todo*/);
                return false;
                }
            }
            if(!tokenCheck(")")){return false;}
            exit_section_(builder,m,/*todo*/,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TryStatement,false);
            expect(tok("try"));
            if(!parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement")){cleanup(m/*todo*/);return false;}
            if (currentIs(tok("catch")))
            if(!parseNodeQ("node.catches", "Catches")){cleanup(m/*todo*/);return false;}
            if (currentIs(tok("finally")))
            if(!parseNodeQ("node.finally_", "Finally")){cleanup(m/*todo*/);return false;}
            exit_section_(builder,m,/*todo*/,true);
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
            Marker m = enter_section_(builder);
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
                    if(!parseNodeQ("node.typeConstructors", "TypeConstructors")){cleanup(m/*todo*/);return false;}
                    break;
                default:
                    break;
            }
            if(!parseNodeQ("node.type2", "Type2")){cleanup(m/*todo*/);return false;}
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
            exit_section_(builder,m,/*todo*/,true);
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
            Marker m = enter_section_(builder);
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
                    if(!parseNodeQ("node.symbol", "Symbol")){cleanup(m/*todo*/);return false;}
                    break;
                foreach (B; BasicTypes) { case B: }
                node.builtinType = parseBuiltinType();
                break;
                case tok("super"):
                case tok("this"):
                    node.superOrThis = advance().type;
                    if(!tokenCheck(".")){return false;}
                    if(!parseNodeQ("node.identifierOrTemplateChain", "IdentifierOrTemplateChain")){cleanup(m/*todo*/);return false;}
                    break;
                case tok("typeof"):
                    if ((node.typeofExpression = parseTypeofExpression()) == null)
                    return null;
                if (currentIs(tok(".")))
                {
                    advance();
                    if(!parseNodeQ("node.identifierOrTemplateChain", "IdentifierOrTemplateChain")){cleanup(m/*todo*/);return false;}
                }
                break;
                case tok(""):
                case tok("immutable"):
                case tok("inout"):
                case tok("shared"):
                    node.typeConstructor = advance().type;
                    if(!tokenCheck("(")){return false;}
                    if(!(node.type = parseType())){
                    cleanup(m/*todo*/);
                    return false;
                    }
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
            exit_section_(builder,m,/*todo*/,true);
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
            Marker m = enter_section_(builder);
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
                    if(!parseNodeQ("node.type", "Type")){cleanup(m/*todo*/);return false;}
                    break;
            }
            exit_section_(builder,m,/*todo*/,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TypeSuffix,false);
            switch (current().type)
            {
                case tok("*"):
                    node.star = advance();
                    exit_section_(builder,m,/*todo*/,true);
                    return true;
                case tok("["):
                    node.array = true;
                    advance();
                    if (currentIs(tok("]")))
                {
                    advance();
                    exit_section_(builder,m,/*todo*/,true);
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
                    if(!parseNodeQ("node.low", "AssignExpression")){cleanup(m/*todo*/);return false;}
                    if(!node.low){
                    cleanup(m/*todo*/);
                    return false;
                    }
                    if (currentIs(tok("..")))
                    {
                        advance();
                        if(!parseNodeQ("node.high", "AssignExpression")){cleanup(m/*todo*/);return false;}
                        if(!node.high){
                        cleanup(m/*todo*/);
                        return false;
                        }
                    }
                }
                if(!tokenCheck("]")){return false;}
                exit_section_(builder,m,/*todo*/,true);
                return true;
                case tok("delegate"):
                case tok("function"):
                    node.delegateOrFunction = advance();
                    if(!parseNodeQ("node.parameters", "Parameters")){cleanup(m/*todo*/);return false;}
                    StackBuffer memberFunctionAttributes;
                    while (currentIsMemberFunctionAttribute())
                        if (!memberFunctionAttributes.put(parseMemberFunctionAttribute()))
                            return null;
                    ownArray(node.memberFunctionAttributes, memberFunctionAttributes);
                    exit_section_(builder,m,/*todo*/,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TypeidExpression,false);
            expect(tok("typeid"));
            expect(tok("("));
            Bookmark b = setBookmark();
            auto t = parseType();
            if (t == null || !currentIs(tok(")")))
            {
                goToBookmark(b);
                if(!parseNodeQ("node.expression", "Expression")){cleanup(m/*todo*/);return false;}
                if(!node.expression){
                cleanup(m/*todo*/);
                return false;
                }
            }
        else
            {
                abandonBookmark(b);
                node.type = t;
            }
            expect(tok(")"));
            exit_section_(builder,m,/*todo*/,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.TypeofExpression,false);
            expect(tok("typeof"));
            expect(tok("("));
            if (currentIs(tok("return")))
            node.return_ = advance();
        else
            if(!parseNodeQ("node.expression", "Expression")){cleanup(m/*todo*/);return false;}
            expect(tok(")"));
            exit_section_(builder,m,/*todo*/,true);
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
            Marker m = enter_section_(builder);
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
                    if(!parseNodeQ("node.functionCallExpression", "FunctionCallExpression")){cleanup(m/*todo*/);return false;}
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
                    if(!parseNodeQ("node.unaryExpression", "UnaryExpression")){cleanup(m/*todo*/);return false;}
                    break;
                case tok("new"):
                    if(!parseNodeQ("node.newExpression", "NewExpression")){cleanup(m/*todo*/);return false;}
                    break;
                case tok("delete"):
                    if(!parseNodeQ("node.deleteExpression", "DeleteExpression")){cleanup(m/*todo*/);return false;}
                    break;
                case tok("cast"):
                    if(!parseNodeQ("node.castExpression", "CastExpression")){cleanup(m/*todo*/);return false;}
                    break;
                case tok("assert"):
                    if(!parseNodeQ("node.assertExpression", "AssertExpression")){cleanup(m/*todo*/);return false;}
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
                        if(!parseNodeQ("node.identifierOrTemplateInstance", "IdentifierOrTemplateInstance")){cleanup(m/*todo*/);return false;}
                        break;
                    }
            else
                {
                    // not (type).identifier, so treat as primary expression
                    goToBookmark(b);
                goto default;
                }
                default:
                    if(!parseNodeQ("node.primaryExpression", "PrimaryExpression")){cleanup(m/*todo*/);return false;}
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
                    if(!newUnary.functionCallExpression = parseFunctionCallExpression(node)){
                    cleanup(m/*todo*/);
                    return false;
                    }
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
                    if(!parseNodeQ("node.newExpression", "NewExpression")){cleanup(m/*todo*/);return false;}
            else
                    n.identifierOrTemplateInstance = parseIdentifierOrTemplateInstance();
                    node = n;
                    break;
                default:
                    break loop;
            }
            exit_section_(builder,m,/*todo*/,true);
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
            Marker m = enter_section_(builder);
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
                    if(!parseNodeQ("node.templateParameters", "TemplateParameters")){cleanup(m/*todo*/);return false;}
                    if (currentIs(tok("if")))
                    if(!parseNodeQ("node.raint", "Constraint")){cleanup(m/*todo*/);return false;}
                    if(!parseNodeQ("node.structBody", "StructBody")){cleanup(m/*todo*/);return false;}
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
                if(!parseNodeQ("node.structBody", "StructBody")){cleanup(m/*todo*/);return false;}
            }
            exit_section_(builder,m,/*todo*/,true);
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
            mixin (simpleParse(Unittest, tok("unittest"), "blockStatement|parseBlockStatement"));
        }

        boolean parseVariableDeclaration(){
            return parseVariableDeclaration(true,false);
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
        boolean parseVariableDeclaration(boolean parseType, boolean isAuto)//(Type type = null )
        {
//            mixin (traceEnterAndExit!(__FUNCTION__));
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.VariableDeclaration,false);

            if (isAuto)
            {
                if(!parseNodeQ("node.autoDeclaration", "AutoDeclaration")){cleanup(m/*todo*/);return false;}
//                node.comment = node.autoDeclaration.comment;
                exit_section_(builder,m,/*todo*/,true);
                return true;
            }

            StackBuffer storageClasses;
            while (isStorageClass())
                if (!parseStorageClass()) {
                    cleanup(m);
                    return false;
                }
//            ownArray(node.storageClasses, storageClasses);

            if(parseType) {
                parseType();
            }
//            node.comment = comment;
//            comment = null;

            // TODO: handle function bodies correctly

            while (true)
            {
                boolean declarator = parseDeclarator();
                if (!declarator) {
                    cleanup(m);
                    return false;
                } else {
//                    last = declarator;
                }
                if (moreTokens() && currentIs(tok(",")))
                {
//                    if (node.comment != null)
//                    declarator.comment = node.comment ~ "\n" ~ current().trailingComment;
//                else
//                    declarator.comment = current().trailingComment;
                    advance();
                }
            else
                break;
            }
//            ownArray(node.declarators, declarators);
            Token semicolon = expect(tok(";"));
            if(semicolon == null){
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
            exit_section_(builder,m,VAR_DECLARATIONS,true);//todo type
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
            mixin (simpleParse(Vector, tok("__vector"), tok("("), "type|parseType", tok(")")));
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.VersionCondition,false);
		 v = expect(tok("version"));
            if(!v){
            cleanup(m/*todo*/);
            return false;
            }
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
            exit_section_(builder,m,/*todo*/,true);
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
            Marker m = enter_section_(builder);
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
            exit_section_(builder,m,/*todo*/,true);
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
            Marker m = enter_section_(builder);
//			Runnable cleanup =() ->  exit_section_(builder,m,DLanguageTypes.WhileStatement,false);
            if(!tokenCheck("while")){return false;}
            node.startIndex = current().index;
            if(!tokenCheck("(")){return false;}
            if(!parseNodeQ("node.expression", "Expression")){cleanup(m/*todo*/);return false;}
            if(!tokenCheck(")")){return false;}
            if (currentIs(tok("}")))
            {
                error("Statement expected", false);
                exit_section_(builder,m,/*todo*/,true);
                return true; // this line makes DCD better
            }
            if(!parseNodeQ("node.declarationOrStatement", "DeclarationOrStatement")){cleanup(m/*todo*/);return false;}
            exit_section_(builder,m,/*todo*/,true);
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
            mixin (simpleParse(WithStatement, tok("with"), tok("("),
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
                    if (peekIs(tok(")"))) exit_section_(builder,m,/*todo*/,true);
 return true;
                return startsWith(tok(""), tok("shared"), tok(")"));
                case tok("immutable"):
                    return peekIs(tok(")"));
                case tok("inout"):
                    if (peekIs(tok(")"))) exit_section_(builder,m,/*todo*/,true);
 return true;
                return startsWith(tok("inout"), tok("shared"), tok(")"));
                case tok("shared"):
                    if (peekIs(tok(")"))) exit_section_(builder,m,/*todo*/,true);
 return true;
                if (startsWith(tok("shared"), tok(""), tok(")"))) exit_section_(builder,m,/*todo*/,true);
 return true;
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
//                case T: exit_section_(builder,m,/*todo*/,true);
 return true;
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

        class Pair<First,Second>{
            First first;
            Second second;

            Pair(First first,Second second){
                this.first = first;
                this.second = second;
            }
        }

        Pair<DecType,Integer> isAutoDeclaration()
        {
            int beginIndex = Integer.MAX_VALUE;
            Bookmark b = setBookmark();
//            goToBookmark(b);// on scope exit
            loop: while (moreTokens()) {
                IdType i = current().type;
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
                } else if (i.equals(tok("")) || i.equals(tok("immutable")) || i.equals(tok("inout")) || i.equals(tok("synchronized"))) {
                    if (peekIs(tok("("))) {
                        goToBookmark(b);
                        return new Pair<>(DecType.other, beginIndex);
                    } else {
                        beginIndex = Math.min(beginIndex, index);
                        advance();
                        break;
                    }
                } else if (i.equals(tok("auto")) || i.equals(tok("enum")) || i.equals(tok("export")) || i.equals(tok("final")) || i.equals(tok("__gshared")) || i.equals(tok("")) || i.equals(tok("override")) || i.equals(tok("")) || i.equals(tok("ref")) || i.equals(tok("scope")) || i.equals(tok("shared")) || i.equals(tok("static"))) {
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
            if (startsWith(tok("identifier"), tok("(")))
            {
                advance();
                Token past = peekPastParens();
                if (past == null) {
                    goToBookmark(b);
                    return new Pair<>(DecType.other, beginIndex);
                } else if (past.type == tok("=")) {
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
                    exit_section_(builder,m,/*todo*/,true);
                    return true;
            goto case;
                case tok("version"):
                    if (peekIs(tok("=")))
                    exit_section_(builder,m,/*todo*/,true);
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
                    exit_section_(builder,m,/*todo*/,true);
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
                    exit_section_(builder,m,/*todo*/,true);
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
                exit_section_(builder,m,/*todo*/,true);
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
                    exit_section_(builder,m,/*todo*/,true);
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
                    exit_section_(builder,m,/*todo*/,true);
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
                    exit_section_(builder,m,/*todo*/,true);
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
            exit_section_(builder,m,/*todo*/,true);
            return true;
        }

        //todo idk why though, probably the true/false args
        boolean parseCommaSeparatedRule(String listType, String itemType)//(alias ListType, alias ItemType,)
        {
//            final boolean setLineAndColumn = false;
            Marker m = enter_section_(builder);
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
            exit_section_(builder,m,/*todo*/,true);
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

        void abandonBookmark(Bookmark bookmark)
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

        boolean parseStaticCtorDtorCommon()
        {
//            node.line = current().line;
//            node.column = current().column;
            if(!tokenCheck("this")) {
                return false;
            }
            if (!tokenCheck("(")) {
                return false;
            }
            if (!tokenCheck(")")) {
                return false;}
            StackBuffer attributes;
            while (moreTokens() && !currentIsOneOf(tok("{"), tok("in"), tok("out"), tok("body"), tok(";")))
            if (!attributes.put(parseMemberFunctionAttribute()))
                return null;
//            ownArray(node.memberFunctionAttributes, attributes);
            if (currentIs(tok(";")))
            advance();
    else
            if(!parseNodeQ("node.functionBody", "FunctionBody")){cleanup(m/*todo*/);return false;}
            exit_section_(builder,m,/*todo*/,true);
            return true;
        }


        boolean parseInterfaceOrClass()
        {
            Token ident = expect(tok("identifier"));
            if(ident == null){
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
            if (currentIs(tok("(")))
            {
                if(!parseNodeQ("node.templateParameters", "TemplateParameters")){cleanup(m/*todo*/);return false;}
                if (currentIs(tok(";"))) {
                    return emptyBody();
                }
                return constraint(false);
            }
            if (currentIs(tok(":")))
            {
                return baseClassList();
            }
            return structBody();

        }

        private boolean emptyBody() {
            advance();
            return true;
        }

        private boolean structBody() {
            if(!parseNodeQ("node.structBody", "StructBody")){cleanup(m/*todo*/);return false;}
            return true;
        }

        private boolean baseClassList(){
            advance(); // :
            if (!parseBaseClassList()) {
                return false;
            }
            if (currentIs(tok("if"))) {
                return constraint(true);
            }
            return structBody();
        }

        private boolean constraint(boolean baseClassListQ){
            if (currentIs(tok("if"))) {
                if(!parseNodeQ("node.raint", "Constraint")){cleanup(m/*todo*/);return false;}
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
            if (currentIs(tok(":")))
            {
                return baseClassList();
            }
            return structBody();
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
