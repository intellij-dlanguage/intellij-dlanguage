package io.github.intellij.dlanguage.parser

import com.google.common.collect.Sets
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiBuilderUtil
import com.intellij.psi.tree.IElementType
import io.github.intellij.dlanguage.psi.DlangTypes
import java.util.*

internal class DLangParser(private val builder: PsiBuilder) {
    private val cachedAAChecks: MutableMap<Int, Boolean> = HashMap()
    private val cachedTypedChecks: MutableMap<Int, Boolean> = HashMap()

    private fun cleanup(marker: PsiBuilder.Marker, element: IElementType) {
        exit_section_modified(builder, marker, element, true)
    }

    /**
     * Parses an AddExpression.
     * converted
     * $(GRAMMAR $(RULEDEF addExpression):
     * $(RULE mulExpression)
     * | $(RULE addExpression) $(LPAREN)$(LITERAL '+') | $(LITERAL'-') | $(LITERAL'~')$(RPAREN) $(RULE mulExpression)
     * ;)
     */
    fun parseAddExpression(): PsiBuilder.Marker? {
        var m = parseMulExpression() ?: return null
        while (currentIsOneOf(DlangTypes.OP_PLUS, DlangTypes.OP_MINUS, DlangTypes.OP_TILDA)) {
            m = m.precede()
            builder.advanceLexer()
            if (parseMulExpression() == null) {
                cleanup(m, DlangTypes.ADD_EXPRESSION)
                return null
            }
            m.done(DlangTypes.ADD_EXPRESSION)
        }
        return m
    }

    /**
     * Parses an AliasDeclaration.
     * converted
     * $(GRAMMAR $(RULEDEF aliasDeclaration):
     * $(LITERAL 'alias') $(RULE aliasInitializer) $(LPAREN)$(LITERAL ',') $(RULE aliasInitializer)$(RPAREN)* $(LITERAL ';')
     * | $(LITERAL 'alias') $(RULE storageClass)* $(RULE type) $(RULE declaratorIdentifierList) $(LITERAL ';')
     * | $(LITERAL 'alias') $(RULE storageClass)* $(RULE type) $(RULE identifier) $(LITERAL '(') $(RULE parameters) $(LITERAL ')') $(memberFunctionAttribute)* $(LITERAL ';')
     * ;)
     */
    fun parseAliasDeclaration(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_ALIAS) return false
        builder.advanceLexer()
        if (startsWith(DlangTypes.ID, DlangTypes.OP_EQ) || startsWith(DlangTypes.ID, DlangTypes.OP_PAR_LEFT)) {
            do {
                if (!parseAliasInitializer()) {
                    m.done(DlangTypes.ALIAS_DECLARATION)
                    return true
                }
                if (currentIs(DlangTypes.OP_COMMA)) {
                    advance()
                } else {
                    break
                }
            } while (moreTokens())
        } else {
            while (moreTokens() && isStorageClass) {
                if (!parseStorageClass()) {
                    m.done(DlangTypes.ALIAS_DECLARATION)
                    return true
                }
            }
            if (!parseType()) {
                m.done(DlangTypes.ALIAS_DECLARATION)
                return true
            }
            if (!parseDeclaratorIdentifierList()) {
                m.done(DlangTypes.ALIAS_DECLARATION)
                return true
            }
            if (currentIs(DlangTypes.OP_PAR_LEFT)) {
                if (!parseParameters()) {
                    m.done(DlangTypes.ALIAS_DECLARATION)
                    return true
                }
                while (moreTokens() && currentIsMemberFunctionAttribute()) {
                    if (!parseMemberFunctionAttribute()) {
                        m.done(DlangTypes.ALIAS_DECLARATION)
                        return true
                    }
                }
            }
        }
        expect(DlangTypes.OP_SCOLON)
        m.done(DlangTypes.ALIAS_DECLARATION)
        return true
    }

    private val isFunction: Boolean
        get() {
            if (currentIsOneOf(
                    DlangTypes.KW_FUNCTION,
                    DlangTypes.KW_DELEGATE,
                    DlangTypes.OP_BRACES_LEFT
                )
            ) return true
            if (startsWith(
                    DlangTypes.ID,
                    DlangTypes.OP_LAMBDA_ARROW
                )
            ) return true
            val bookmark = builder.mark()
            if (currentIs(DlangTypes.OP_PAR_LEFT) || currentIs(DlangTypes.KW_REF) && peekIs(
                    DlangTypes.OP_PAR_LEFT
                )
            ) {
                if (currentIs(DlangTypes.KW_REF)) advance()
                val t = peekPastParens()
                if (t != null) {
                    if (t === DlangTypes.OP_LAMBDA_ARROW || t === DlangTypes.OP_BRACES_LEFT || isMemberFunctionAttribute(
                            t
                        )
                    ) {
                        bookmark.rollbackTo()
                        return true
                    }
                }
            }
            bookmark.rollbackTo()
            return false
        }

    /**
     * Parses an AliasAssign.
     *
     *
     * $(GRAMMAR $(RULEDEF aliasAssign):
     * $(LITERAL Identifier) $(LITERAL '=') $(RULE type)
     */
    fun parseAliasAssign(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.ID) return false
        val bookmark = builder.mark()
        advance()
        if (!tokenCheck(DlangTypes.OP_EQ)) {
            bookmark.rollbackTo()
            return false
        }
        if (!parseType()) {
            bookmark.rollbackTo()
            return false
        }
        if (builder.tokenType !== DlangTypes.OP_SCOLON) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        builder.advanceLexer()
        m.done(DlangTypes.ALIAS_ASSIGN)
        return true
    }

    /**
     * Parses an AliasInitializer.
     *
     *
     * $(GRAMMAR $(RULEDEF aliasInitializer):
     * $(LITERAL Identifier) $(RULE templateParameters)? $(LITERAL '=') $(RULE storageClass)* $(RULE type)
     * | $(LITERAL Identifier) $(RULE templateParameters)? $(LITERAL '=') $(RULE storageClass)* $(RULE type) $(RULE parameters) $(RULE memberFunctionAttribute)*
     * | $(LITERAL Identifier) $(RULE templateParameters)? $(LITERAL '=') $(RULE functionLiteralExpression)
     * ;)
     */
    fun parseAliasInitializer(): Boolean {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.ID)) {
            cleanup(m, DlangTypes.ALIAS_INITIALIZER)
            return false
        }
        if (currentIs(DlangTypes.OP_PAR_LEFT)) {
            if (!parseTemplateParameters()) {
                cleanup(m, DlangTypes.ALIAS_INITIALIZER)
                return false
            }
        }
        if (!tokenCheck(DlangTypes.OP_EQ)) {
            cleanup(m, DlangTypes.ALIAS_INITIALIZER)
            return false
        }
        if (isFunction) {
            if (parseFunctionLiteralExpression() == null) {
                cleanup(m, DlangTypes.ALIAS_INITIALIZER)
                return false
            }
        } else {
            while (moreTokens() && isStorageClass) {
                if (!parseStorageClass()) {
                    cleanup(m, DlangTypes.ALIAS_INITIALIZER)
                    return false
                }
            }
            if (!parseType()) {
                cleanup(m, DlangTypes.ALIAS_INITIALIZER)
                return false
            }
            if (currentIs(DlangTypes.OP_PAR_LEFT)) {
                if (!parseParameters()) {
                    cleanup(m, DlangTypes.ALIAS_INITIALIZER)
                    return false
                }
                while (moreTokens() && currentIsMemberFunctionAttribute()) if (!parseMemberFunctionAttribute()) {
                    cleanup(m, DlangTypes.ALIAS_INITIALIZER)
                    return false
                }
            }
        }
        exit_section_modified(builder, m, DlangTypes.ALIAS_INITIALIZER, true)
        return true
    }

    /**
     * Parses an AliasThisDeclaration.
     *
     *
     * $(GRAMMAR $(RULEDEF aliasThisDeclaration):
     * $(LITERAL 'alias') $(LITERAL Identifier) $(LITERAL 'this') $(LITERAL ';')
     * | $(LITERAL 'alias') $(LITERAL 'this') $(LITERAL Identifier) $(LITERAL ';')
     * ;)
     */
    fun parseAliasThisDeclaration(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_ALIAS) return false
        val bookmark = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType === DlangTypes.ID) {
            if (!tokenCheck(DlangTypes.ID)) {
                bookmark.rollbackTo()
                return false
            }
            if (!tokenCheck(DlangTypes.KW_THIS)) {
                bookmark.rollbackTo()
                return false
            }
            expect(DlangTypes.OP_SCOLON)
        } else if (builder.tokenType === DlangTypes.KW_THIS) {
            builder.advanceLexer()
            expect(DlangTypes.OP_EQ)
            expect(DlangTypes.ID)
            expect(DlangTypes.OP_SCOLON)
        } else {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        m.done(DlangTypes.ALIAS_THIS_DECLARATION)
        return true
    }

    /**
     * Parses an AlignAttribute.
     *
     *
     * $(GRAMMAR $(RULEDEF alignAttribute):
     * $(LITERAL 'align') ($(LITERAL '$(LPAREN)') $(RULE AssignExpression) $(LITERAL '$(RPAREN)'))?
     * ;)
     */
    fun parseAlignAttribute(): Boolean {
        val m = builder.mark()
        expect(DlangTypes.KW_ALIGN)
        if (currentIs(DlangTypes.OP_PAR_LEFT)) {
            if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
                cleanup(m, DlangTypes.ALIGN_ATTRIBUTE)
                return false
            }
            if (parseAssignExpression() == null) {
                cleanup(m, DlangTypes.ALIGN_ATTRIBUTE)
                return false
            }
            if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
                cleanup(m, DlangTypes.ALIGN_ATTRIBUTE)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.ALIGN_ATTRIBUTE, true)
        return true
    }

    /**
     * Parses an AndAndExpression.
     *
     *
     * $(GRAMMAR $(RULEDEF andAndExpression):
     * $(RULE orExpression)
     * | $(RULE andAndExpression) $(LITERAL '&&') $(RULE orExpression)
     * ;)
     */
    fun parseAndAndExpression(): PsiBuilder.Marker? {
        var m = parseOrExpression() ?: return null
        while (currentIs(DlangTypes.OP_BOOL_AND)) {
            m = m.precede()
            builder.advanceLexer()
            if (parseOrExpression() == null) {
                cleanup(m, DlangTypes.AND_AND_EXPRESSION)
                return null
            }
            m.done(DlangTypes.AND_AND_EXPRESSION)
        }
        return m
    }

    /**
     * Parses an AndExpression.
     *
     *
     * $(GRAMMAR $(RULEDEF andExpression):
     * $(RULE cmpExpression)
     * | $(RULE andExpression) $(LITERAL '&') $(RULE cmpExpression)
     * ;)
     */
    fun parseAndExpression(): PsiBuilder.Marker? {
        var m = parseCmpExpression() ?: return null
        while (currentIs(DlangTypes.OP_AND)) {
            m = m.precede()
            builder.advanceLexer()
            if (parseCmpExpression() == null) {
                cleanup(m, DlangTypes.AND_EXPRESSION)
                return null
            }
            m.done(DlangTypes.AND_EXPRESSION)
        }
        return m
    }

    /**
     * Parses an ArgumentList.
     *
     *
     * $(GRAMMAR $(RULEDEF argumentList):
     * $(RULE assignExpression) ($(LITERAL ',') $(RULE assignExpression)?)*
     * ;)
     */
    private fun parseArgumentList(): Boolean {
        if (currentIs(DlangTypes.OP_PAR_RIGHT)) return true
        val m = builder.mark()
        while (!currentIs(DlangTypes.OP_PAR_RIGHT)) {
            if (parseAssignExpression() == null) {
                cleanup(m, DlangTypes.ARGUMENT_LIST)
                return false
            }
            if (currentIs(DlangTypes.OP_PAR_RIGHT)) break
            if (!currentIs(DlangTypes.OP_COMMA)) {
                break
            }
            advance()
        }
        m.done(DlangTypes.ARGUMENT_LIST)
        return true
    }

    /**
     * Parses Arguments.
     *
     *
     * $(GRAMMAR $(RULEDEF arguments):
     * $(LITERAL '$(LPAREN)') $(RULE argumentList)? $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseArguments(): Boolean {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, DlangTypes.ARGUMENTS)
            return false
        }
        if (!currentIs(DlangTypes.OP_PAR_RIGHT)) if (!parseArgumentList()) {
            cleanup(m, DlangTypes.ARGUMENTS)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            cleanup(m, DlangTypes.ARGUMENTS)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.ARGUMENTS, true)
        return true
    }

    /**
     * Parses an ArrayInitializer.
     *
     *
     * $(GRAMMAR $(RULEDEF arrayInitializer):
     * ($(RULE arrayMemberInitialization) ($(LITERAL ',') $(RULE arrayMemberInitialization)?)*)?
     * ;)
     */
    fun parseArrayInitializer(): Boolean {
        val arrayInit = builder.mark()
        while (moreTokens()) {
            if (currentIs(DlangTypes.OP_BRACKET_RIGHT)) break
            if (!parseArrayMemberInitialization()) {
                cleanup(arrayInit, DlangTypes.ARRAY_INITIALIZER)
                return false
            }
            if (currentIs(DlangTypes.OP_COMMA)) advance()
            else break
        }
        exit_section_modified(builder, arrayInit, DlangTypes.ARRAY_INITIALIZER, true)
        return true
    }

    /**
     * Parses an ArrayLiteral.
     *
     *
     * $(GRAMMAR $(RULEDEF arrayLiteral):
     * $(LITERAL '[') $(RULE ArrayMemberInitiolizations)? $(LITERAL ']')
     * ;)
     */
    fun parseArrayLiteral(): PsiBuilder.Marker? {
        val m = builder.mark()
        val open = expect(DlangTypes.OP_BRACKET_LEFT)
        if (open == null) {
            cleanup(m, DlangTypes.ARRAY_LITERAL)
            return null
        }
        if (!currentIs(DlangTypes.OP_BRACKET_RIGHT)) {
            if (!parseArrayInitializer()) {
                cleanup(m, DlangTypes.ARRAY_LITERAL)
                return null
            }
        }
        val close = expect(DlangTypes.OP_BRACKET_RIGHT)
        if (close == null) {
            cleanup(m, DlangTypes.ARRAY_INITIALIZER)
            return null
        }
        exit_section_modified(builder, m, DlangTypes.ARRAY_LITERAL, true)
        return m
    }

    /**
     * Parses an ArrayMemberInitialization.
     *
     *
     * $(GRAMMAR $(RULEDEF arrayMemberInitialization):
     * ($(RULE assignExpression) $(LITERAL ':'))? $(RULE nonVoidInitializer)
     * ;)
     */
    fun parseArrayMemberInitialization(): Boolean {
        val m = builder.mark()
        if (currentIs(DlangTypes.OP_BRACKET_LEFT)) {
            val bookmark = builder.mark()
            skipBrackets()
            if (currentIs(DlangTypes.OP_COLON)) {
                bookmark.rollbackTo()
                if (parseAssignExpression() == null) {
                    cleanup(m, DlangTypes.ARRAY_MEMBER_INITIALIZATION)
                    return false
                }
                advance() // :
                if (!parseNonVoidInitializer()) {
                    cleanup(m, DlangTypes.ARRAY_MEMBER_INITIALIZATION)
                    return false
                }
                exit_section_modified(builder, m, DlangTypes.ARRAY_MEMBER_INITIALIZATION, true)
                return true
            } else {
                bookmark.rollbackTo()
            }
        }
        if (currentIs(DlangTypes.OP_BRACES_LEFT)) {
            if (!parseNonVoidInitializer()) {
                cleanup(m, DlangTypes.ARRAY_MEMBER_INITIALIZATION)
                return false
            }
        } else {
            val assignExpression = parseAssignExpression() != null
            if (!assignExpression) {
                cleanup(m, DlangTypes.ARRAY_MEMBER_INITIALIZATION)
                return false
            }
            if (currentIs(DlangTypes.OP_COLON)) {
                advance()
                if (!parseNonVoidInitializer()) {
                    cleanup(m, DlangTypes.ARRAY_MEMBER_INITIALIZATION)
                    return false
                }
            }
        }
        exit_section_modified(builder, m, DlangTypes.ARRAY_MEMBER_INITIALIZATION, true)
        return true
    }

    /**
     * Parses an AsmAddExp
     *
     *
     * $(GRAMMAR $(RULEDEF asmAddExp):
     * $(RULE asmMulExp)
     * | $(RULE asmAddExp) ($(LITERAL '+') | $(LITERAL '-')) $(RULE asmMulExp)
     * ;)
     */
    fun parseAsmAddExp(): Boolean {
        val m = builder.mark()
        val node = parseAsmMulExp()
        if (!node) {
            m.drop()
            return false
        }
        while (currentIsOneOf(DlangTypes.OP_PLUS, DlangTypes.OP_MINUS)) {
            advance()
            if (!parseAsmMulExp()) {
                cleanup(m, DlangTypes.ASM_ADD_EXP)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.ASM_ADD_EXP, true)
        return true
    }

    /**
     * Parses an AsmAndExp
     *
     *
     * $(GRAMMAR $(RULEDEF asmAndExp):
     * $(RULE asmEqualExp)
     * | $(RULE asmAndExp) $(LITERAL '&') $(RULE asmEqualExp)
     * ;)
     */
    fun parseAsmAndExp(): Boolean {
        val m = builder.mark()
        val node = parseAsmEqualExp()
        if (!node) {
            m.drop()
            return false
        }
        while (currentIs(DlangTypes.OP_AND)) {
            advance()
            if (!parseAsmEqualExp()) {
                cleanup(m, DlangTypes.ASM_AND_EXP)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.ASM_AND_EXP, true)
        return true
    }

    /**
     * Parses an AsmBrExp
     *
     *
     * $(GRAMMAR $(RULEDEF asmBrExp):
     * $(RULE asmUnaExp)
     * | $(RULE asmBrExp)? $(LITERAL '[') $(RULE asmExp) $(LITERAL ']')
     * ;)
     */
    fun parseAsmBrExp(): Boolean {
        val m = builder.mark()
        if (!moreTokens()) {
            error("Found end-of-file when expecting an AsmBrExp" /*, false*/)
            cleanup(m, DlangTypes.ASM_BR_EXP)
            return false
        }
        if (currentIs(DlangTypes.OP_BRACKET_LEFT)) {
            advance() // [
            if (!parseAsmExp()) {
                cleanup(m, DlangTypes.ASM_BR_EXP)
                return false
            }
            if (!tokenCheck(DlangTypes.OP_BRACKET_RIGHT)) {
                cleanup(m, DlangTypes.ASM_BR_EXP)
                return false
            }
            if (currentIs(DlangTypes.OP_BRACKET_LEFT)) {
                while (currentIs(DlangTypes.OP_BRACKET_LEFT)) {
                    advance() // [
                    if (!parseAsmExp()) {
                        cleanup(m, DlangTypes.ASM_BR_EXP)
                        return false
                    }
                    if (!tokenCheck(DlangTypes.OP_BRACKET_RIGHT)) {
                        cleanup(m, DlangTypes.ASM_BR_EXP)
                        return false
                    }
                }
            }
        } else {
            if (!parseAsmUnaExp()) {
                cleanup(m, DlangTypes.ASM_BR_EXP)
                return false
            }
            while (currentIs(DlangTypes.OP_BRACKET_LEFT)) {
                advance() // [
                if (!parseAsmExp()) {
                    cleanup(m, DlangTypes.ASM_BR_EXP)
                    return false
                }
                if (!tokenCheck(DlangTypes.OP_BRACKET_RIGHT)) {
                    cleanup(m, DlangTypes.ASM_BR_EXP)
                    return false
                }
            }
        }
        exit_section_modified(builder, m, DlangTypes.ASM_BR_EXP, true)
        return true
    }

    /**
     * Parses an AsmEqualExp
     *
     *
     * $(GRAMMAR $(RULEDEF asmEqualExp):
     * $(RULE asmRelExp)
     * | $(RULE asmEqualExp) ('==' | '!=') $(RULE asmRelExp)
     * ;)
     */
    fun parseAsmEqualExp(): Boolean {
        val m = builder.mark()
        val node = parseAsmRelExp()
        if (!node) {
            m.drop()
            return false
        }
        while (currentIsOneOf(DlangTypes.OP_EQ_EQ, DlangTypes.OP_NOT_EQ)) {
            advance()
            if (!parseAsmRelExp()) {
                cleanup(m, DlangTypes.ASM_EQUAL_EXP)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.ASM_EQUAL_EXP, true)
        return true
    }

    /**
     * Parses an AsmExp
     *
     *
     * $(GRAMMAR $(RULEDEF asmExp):
     * $(RULE asmLogOrExp) ($(LITERAL '?') $(RULE asmExp) $(LITERAL ':') $(RULE asmExp))?
     * ;)
     */
    fun parseAsmExp(): Boolean {
        val m = builder.mark()
        if (!parseAsmLogOrExp()) {
            cleanup(m, DlangTypes.ASM_EXP)
            return false
        }
        if (currentIs(DlangTypes.OP_QUEST)) {
            advance()
            if (!parseAsmExp()) {
                cleanup(m, DlangTypes.ASM_EXP)
                return false
            }
            if (!tokenCheck(DlangTypes.OP_COLON)) {
                cleanup(m, DlangTypes.ASM_EXP)
                return false
            }
            if (!parseAsmExp()) {
                cleanup(m, DlangTypes.ASM_EXP)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.ASM_EXP, true)
        return true
    }

    /**
     * Parses an AsmInstruction
     *
     *
     * $(GRAMMAR $(RULEDEF asmInstruction):
     * $(LITERAL Identifier)
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
    fun parseAsmInstruction(): Boolean {
        val m = builder.mark()
        if (currentIs(DlangTypes.OP_SCOLON)) {
            builder.advanceLexer()
            m.done(DlangTypes.ASM_INSTRUCTION)
            return true
        }
        if (currentIs(DlangTypes.KW_ALIGN)) {
            advance() // align
            if (currentIsOneOf(DlangTypes.INTEGER_LITERAL, DlangTypes.ID)) {
                if (!currentIs(DlangTypes.OP_SCOLON)) {
                    builder.error("`;` expected.")
                    m.done(DlangTypes.ASM_INSTRUCTION)
                    return false
                }
                builder.advanceLexer()
            } else {
                error("Identifier or integer literal expected.")
                m.done(DlangTypes.ASM_INSTRUCTION)
                return false
            }
        } else if (currentIsOneOf(DlangTypes.ID, DlangTypes.KW_IN, DlangTypes.KW_OUT, DlangTypes.KW_INT)) {
            val t = advance()
            if (t === DlangTypes.ID && currentIs(DlangTypes.OP_COLON)) {
                advance() // :
                if (currentIs(DlangTypes.OP_SCOLON)) {
                    builder.advanceLexer()
                    m.done(DlangTypes.ASM_INSTRUCTION)
                    return true
                }
                if (!parseAsmInstruction()) {
                    m.done(DlangTypes.ASM_INSTRUCTION)
                    return false
                }
            } else if (!currentIs(DlangTypes.OP_SCOLON)) {
                if (!parseOperands()) {
                    m.done(DlangTypes.ASM_INSTRUCTION)
                    return false
                }
            }
            builder.advanceLexer()
        } else {
            m.rollbackTo()
            return false
        }
        m.done(DlangTypes.ASM_INSTRUCTION)
        return true
    }

    /**
     * Parses an AsmLogAndExp
     *
     *
     * $(GRAMMAR $(RULEDEF asmLogAndExp):
     * $(RULE asmOrExp)
     * | $(RULE asmLogAndExp) $(LITERAL '&&') $(RULE asmOrExp)
     * ;)
     */
    fun parseAsmLogAndExp(): Boolean {
        val m = builder.mark()
        val node = parseAsmOrExp()
        if (!node) {
            m.drop()
            return false
        }
        while (currentIs(DlangTypes.OP_BOOL_AND)) {
            advance()
            if (!parseAsmOrExp()) {
                cleanup(m, DlangTypes.ASM_LOG_AND_EXP)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.ASM_LOG_AND_EXP, true)
        return true
    }

    /**
     * Parses an AsmLogOrExp
     *
     *
     * $(GRAMMAR $(RULEDEF asmLogOrExp):
     * $(RULE asmLogAndExp)
     * | $(RULE asmLogOrExp) '||' $(RULE asmLogAndExp)
     * ;)
     */
    fun parseAsmLogOrExp(): Boolean {
        val m = builder.mark()
        val node = parseAsmLogAndExp()
        if (!node) {
            m.drop()
            return false
        }
        while (currentIs(DlangTypes.OP_BOOL_OR)) {
            advance()
            if (!parseAsmLogAndExp()) {
                cleanup(m, DlangTypes.ASM_LOG_OR_EXP)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.ASM_LOG_OR_EXP, true)
        return true
    }

    /**
     * Parses an AsmMulExp
     *
     *
     * $(GRAMMAR $(RULEDEF asmMulExp):
     * $(RULE asmBrExp)
     * | $(RULE asmMulExp) ($(LITERAL '*') | $(LITERAL '/') | $(LITERAL '%')) $(RULE asmBrExp)
     * ;)
     */
    fun parseAsmMulExp(): Boolean {
        val m = builder.mark()
        val node = parseAsmBrExp()
        if (!node) {
            m.drop()
            return false
        }
        while (currentIsOneOf(DlangTypes.OP_ASTERISK, DlangTypes.OP_DIV, DlangTypes.OP_MOD)) {
            advance()
            if (!parseAsmBrExp()) {
                cleanup(m, DlangTypes.ASM_MUL_EXP)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.ASM_MUL_EXP, true)
        return true
    }

    /**
     * Parses an AsmOrExp
     *
     *
     * $(GRAMMAR $(RULEDEF asmOrExp):
     * $(RULE asmXorExp)
     * | $(RULE asmOrExp) $(LITERAL '|') $(RULE asmXorExp)
     * ;)
     */
    fun parseAsmOrExp(): Boolean {
        val m = builder.mark()
        val node = parseAsmXorExp()
        if (!node) {
            m.drop()
            return false
        }
        while (currentIs(DlangTypes.OP_OR)) {
            advance()
            if (!parseAsmXorExp()) {
                cleanup(m, DlangTypes.ASM_OR_EXP)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.ASM_OR_EXP, true)
        return true
    }

    /**
     * Parses an AsmPrimaryExp
     *
     *
     * $(GRAMMAR $(RULEDEF asmPrimaryExp):
     * $(LITERAL IntegerLiteral)
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
    fun parseAsmPrimaryExp(): Boolean {
        val m = builder.mark()
        val i = current()
        if (i === DlangTypes.FLOAT_LITERAL || i === DlangTypes.INTEGER_LITERAL || i === DlangTypes.DOUBLE_QUOTED_STRING || i === DlangTypes.OP_DOLLAR || i === DlangTypes.KW_THIS) {
            advance()
        } else if (i === DlangTypes.ID) {
            if (REGISTER_NAMES.contains(builder.tokenText)) {
                if (!parseRegister()) {
                    cleanup(m, DlangTypes.ASM_PRIMARY_EXP)
                    return false
                }
                if (current() === DlangTypes.OP_COLON) {
                    advance()
                    if (!parseAsmExp()) {
                        cleanup(m, DlangTypes.ASM_PRIMARY_EXP)
                        return false
                    }
                }
            } else {
                if (parseIdentifierChain() == null) {
                    cleanup(m, DlangTypes.ASM_PRIMARY_EXP)
                    return false
                }
            }
        } else {
            error("Float literal, integer literal, `\$` `this` or identifier expected.")
            cleanup(m, DlangTypes.ASM_PRIMARY_EXP)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.ASM_PRIMARY_EXP, true)
        return true
    }

    /**
     * Parses an AsmRelExp
     *
     *
     * $(GRAMMAR $(RULEDEF asmRelExp):
     * $(RULE asmShiftExp)
     * | $(RULE asmRelExp) (($(LITERAL '<') | $(LITERAL '<=') | $(LITERAL '>') | $(LITERAL '>=')) $(RULE asmShiftExp))?
     * ;)
     */
    fun parseAsmRelExp(): Boolean {
        val m = builder.mark()
        val node = parseAsmShiftExp()
        if (!node) {
            m.drop()
            return false
        }
        while (currentIsOneOf(DlangTypes.OP_LESS, DlangTypes.OP_LESS_EQ, DlangTypes.OP_GT, DlangTypes.OP_GT_EQ)) {
            advance()
            if (!parseAsmShiftExp()) {
                cleanup(m, DlangTypes.ASM_REL_EXP)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.ASM_REL_EXP, true)
        return true
    }

    /**
     * Parses an AsmShiftExp
     *
     *
     * $(GRAMMAR $(RULEDEF asmShiftExp):
     * $(RULE asmAddExp)
     * $(RULE asmShiftExp) ($(LITERAL '<<') | $(LITERAL '>>') | $(LITERAL '>>>')) $(RULE asmAddExp)
     * ;)
     */
    fun parseAsmShiftExp(): Boolean {
        val m = builder.mark()
        val node = parseAsmAddExp()
        if (!node) {
            m.drop()
            return false
        }
        while (currentIsOneOf(DlangTypes.OP_SH_LEFT, DlangTypes.OP_SH_RIGHT, DlangTypes.OP_USH_RIGHT)) {
            advance()
            if (!parseAsmAddExp()) {
                cleanup(m, DlangTypes.ASM_SHIFT_EXP)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.ASM_SHIFT_EXP, true)
        return true
    }

    /**
     * Parses an AsmStatement
     *
     *
     * $(GRAMMAR $(RULEDEF asmStatement):
     * $(LITERAL 'asm') $(RULE functionAttributes)? $(LITERAL '{') $(RULE asmInstruction)+ $(LITERAL '}')
     * ;)
     */
    fun parseAsmStatement(): Boolean {
        val m = builder.mark()
        advance() // asm
        while (isAttribute) {
            if (!parseFunctionAttribute()) {
                error("Function attribute or '{' expected")
                cleanup(m, DlangTypes.ASM_STATEMENT)
                return false
            }
        }
        expect(DlangTypes.OP_BRACES_LEFT)
        while (moreTokens() && !currentIs(DlangTypes.OP_BRACES_RIGHT)) {
            if (!parseAsmInstruction()) {
                // TODO: here libdparse handle gcc asm instructions
                while (builder.tokenType !== DlangTypes.OP_BRACES_RIGHT) {
                    builder.advanceLexer()
                }
                break
            }
        }
        expect(DlangTypes.OP_BRACES_RIGHT)
        exit_section_modified(builder, m, DlangTypes.ASM_STATEMENT, true)
        return true
    }

    /**
     * Parses an AsmTypePrefix
     *
     *
     * Note that in the following grammar definition the first identifier must
     * be "near", "far", "word", "dword", or "qword". The second identifier must
     * be "ptr".
     *
     *
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
    fun parseAsmTypePrefix(): Boolean {
        val m = builder.mark()
        val i = current()
        if (i === DlangTypes.ID || i === DlangTypes.KW_BYTE || i === DlangTypes.KW_SHORT || i === DlangTypes.KW_INT || i === DlangTypes.KW_FLOAT || i === DlangTypes.KW_DOUBLE || i === DlangTypes.KW_REAL) {
            val tokenText = builder.tokenText
            val t = advance()
            if (t === DlangTypes.ID) when (tokenText) {
                "near", "far", "word", "dword", "qword" -> {}
                else -> {
                    error("ASM type node expected")
                    exit_section_modified(builder, m, DlangTypes.ASM_TYPE_PREFIX, true) //todo
                    return false
                }
            }
            if (currentIs(DlangTypes.ID) && builder.tokenText == "ptr") {
                advance()
            }
            exit_section_modified(builder, m, DlangTypes.ASM_TYPE_PREFIX, true)
            return true
        } else {
            error("Expected an identifier, 'byte', 'short', 'int', 'float', 'double', or 'real'")
            cleanup(m, DlangTypes.ASM_TYPE_PREFIX)
            return false
        }
    }

    /**
     * Parses an AsmUnaExp
     *
     *
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
    fun parseAsmUnaExp(): Boolean {
        val m = builder.mark()
        val i = current()
        if (i === DlangTypes.OP_PLUS || i === DlangTypes.OP_MINUS || i === DlangTypes.OP_NOT || i === DlangTypes.OP_TILDA) {
            advance()
            if (!parseAsmUnaExp()) {
                cleanup(m, DlangTypes.ASM_UNA_EXP)
                return false
            }
        } else if (i === DlangTypes.KW_BYTE || i === DlangTypes.KW_SHORT || i === DlangTypes.KW_INT || i === DlangTypes.KW_FLOAT || i === DlangTypes.KW_DOUBLE || i === DlangTypes.KW_REAL) {
            if (!typePrefix(m)) return false
        } else if (i === DlangTypes.ID) {
            when (builder.tokenText) {
                "offsetof", "seg" -> {
                    advance()
                    if (!parseAsmExp()) {
                        cleanup(m, DlangTypes.ASM_UNA_EXP)
                        return false
                    }
                }

                "near", "far", "word", "dword", "qword" -> {
                    if (!typePrefix(m)) return false
                }

                else -> {
                    if (!outerDefault(m)) return false
                }
            }
        } else {
            if (!outerDefault(m)) return false
        }
        exit_section_modified(builder, m, DlangTypes.ASM_UNA_EXP, true)
        return true
    }

    private fun outerDefault(m: PsiBuilder.Marker): Boolean {
        if (!parseAsmPrimaryExp()) {
            cleanup(m, DlangTypes.ASM_UNA_EXP)
            return false
        }
        return true
    }

    private fun typePrefix(m: PsiBuilder.Marker): Boolean {
        if (!parseAsmTypePrefix()) {
            cleanup(m, DlangTypes.ASM_UNA_EXP)
            return false
        }
        if (!parseAsmExp()) {
            cleanup(m, DlangTypes.ASM_UNA_EXP)
            return false
        }
        return true
    }

    /**
     * Parses an AsmXorExp
     *
     *
     * $(GRAMMAR $(RULEDEF asmXorExp):
     * $(RULE asmAndExp)
     * | $(RULE asmXorExp) $(LITERAL '^') $(RULE asmAndExp)
     * ;)
     */
    fun parseAsmXorExp(): Boolean {
        val m = builder.mark()
        val node = parseAsmAndExp()
        if (!node) {
            m.drop()
            return false
        }
        while (currentIs(DlangTypes.OP_XOR)) {
            advance()
            if (!parseAsmAndExp()) {
                cleanup(m, DlangTypes.ASM_XOR_EXP)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.ASM_XOR_EXP, true)
        return true
    }

    /**
     * Parses an AssertArguments
     *
     * $(GRAMMAR $(RULEDEF assertArguments):
     * $(RULE assignExpression) ($(LITERAL ',') ($(RULE assignExpression))* $(LITERAL ',')?
     * ;)
     */
    fun parseAssertArguments(): Boolean {
        val m = builder.mark()
        if (parseAssignExpression() == null) {
            cleanup(m, DlangTypes.ASSERT_ARGUMENTS)
            return false
        }
        if (currentIs(DlangTypes.OP_COMMA)) {
            advance()
        }
        if (currentIs(DlangTypes.OP_PAR_RIGHT)) {
            exit_section_modified(builder, m, DlangTypes.ASSERT_ARGUMENTS, true)
            return true
        }
        while (!currentIs(DlangTypes.OP_PAR_RIGHT)) {
            if (parseAssignExpression() == null) {
                cleanup(m, DlangTypes.ASSERT_ARGUMENTS)
                return false
            }
            if (currentIs(DlangTypes.OP_COMMA)) {
                advance()
            }
        }
        exit_section_modified(builder, m, DlangTypes.ASSERT_ARGUMENTS, true)
        return true
    }

    /**
     * Parses an AssertExpression
     *
     *
     * $(GRAMMAR $(RULEDEF assertExpression):
     * $(LITERAL 'assert') $(LITERAL '$(LPAREN)') $(RULE assertArguments) ($(LITERAL ',') $(RULE assignExpression))? $(LITERAL ',')? $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseAssertExpression(): PsiBuilder.Marker? {
        val m = builder.mark()
        advance() // "assert"
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, DlangTypes.ASSERT_EXPRESSION)
            return null
        }
        if (!parseAssertArguments()) {
            cleanup(m, DlangTypes.ASSERT_EXPRESSION)
            return null
        }
        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            cleanup(m, DlangTypes.ASSERT_EXPRESSION)
            return null
        }
        exit_section_modified(builder, m, DlangTypes.ASSERT_EXPRESSION, true)
        return m
    }

    /**
     * Parses an AssignExpression
     *
     *
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
    fun parseAssignExpression(): PsiBuilder.Marker? {
        val ternary = parseTernaryExpression() ?: return null
        if (currentIsOneOf(
                DlangTypes.OP_EQ,
                DlangTypes.OP_USH_RIGHT_EQ,
                DlangTypes.OP_SH_RIGHT_EQ,
                DlangTypes.OP_SH_LEFT_EQ,
                DlangTypes.OP_PLUS_EQ,
                DlangTypes.OP_MINUS_EQ,
                DlangTypes.OP_MUL_EQ,
                DlangTypes.OP_MOD_EQ,
                DlangTypes.OP_AND_EQ,
                DlangTypes.OP_DIV_EQ,
                DlangTypes.OP_OR_EQ,
                DlangTypes.OP_POW_EQ,
                DlangTypes.OP_XOR_EQ,
                DlangTypes.OP_TILDA_EQ
            )
        ) {
            val m = ternary.precede()
            advance()
            if (parseAssignExpression() == null) {
                cleanup(m, DlangTypes.ASSIGN_EXPRESSION)
                return m
            }
            exit_section_modified(builder, m, DlangTypes.ASSIGN_EXPRESSION, true)
            return m
        }
        return ternary
    }

    /**
     * Parses an AssocArrayLiteral
     *
     *
     * $(GRAMMAR $(RULEDEF assocArrayLiteral):
     * $(LITERAL '[') $(RULE keyValuePairs) $(LITERAL ']')
     * ;)
     */
    fun parseAssocArrayLiteral(): PsiBuilder.Marker? {
        val m = builder.mark()
        if (expect(DlangTypes.OP_BRACKET_LEFT) == null) {
            cleanup(m, DlangTypes.ASSOC_ARRAY_LITERAL)
            return null
        }
        if (!parseKeyValuePairs()) {
            cleanup(m, DlangTypes.ASSOC_ARRAY_LITERAL)
            return null
        }
        if (expect(DlangTypes.OP_BRACKET_RIGHT) == null) {
            cleanup(m, DlangTypes.ASSOC_ARRAY_LITERAL)
            return null
        }
        exit_section_modified(builder, m, DlangTypes.ASSOC_ARRAY_LITERAL, true)
        return m
    }

    /**
     * Parses an AtAttribute
     *
     *
     * $(GRAMMAR $(RULEDEF atAttribute):
     * $(LITERAL '@') $(LITERAL Identifier)
     * | $(LITERAL '@') $(LITERAL Identifier) $(LITERAL '$(LPAREN)') $(RULE argumentList)? $(LITERAL '$(RPAREN)')
     * | $(LITERAL '@') $(LITERAL '$(LPAREN)') $(RULE argumentList) $(LITERAL '$(RPAREN)')
     * | $(LITERAL '@') $(RULE TemplateInstance)
     * ;)
     */
    fun parseAtAttribute(): Boolean {
        val m = builder.mark()
        val start = expect(DlangTypes.OP_AT)
        if (start == null) {
            cleanup(m, DlangTypes.AT_ATTRIBUTE)
            return false
        }
        if (!moreTokens()) {
            error("`(`, or identifier expected")
            exit_section_modified(builder, m, DlangTypes.AT_ATTRIBUTE, true)
            return false
        }
        val i = current()
        if (i === DlangTypes.ID) {
            if (peekIs(DlangTypes.OP_NOT)) {
                if (!parseTemplateInstance()) {
                    cleanup(m, DlangTypes.AT_ATTRIBUTE)
                    return false
                }
            } else advance()
            if (currentIs(DlangTypes.OP_PAR_LEFT)) {
                advance() // (
                if (!currentIs(DlangTypes.OP_PAR_RIGHT)) {
                    if (!parseArgumentList()) {
                        cleanup(m, DlangTypes.AT_ATTRIBUTE)
                        return false
                    }
                }
                expect(DlangTypes.OP_PAR_RIGHT)
            }
        } else if (i === DlangTypes.OP_PAR_LEFT) {
            advance()
            if (!parseArgumentList()) {
                cleanup(m, DlangTypes.AT_ATTRIBUTE)
                return false
            }
            expect(DlangTypes.OP_PAR_RIGHT)
        } else {
            error("`(`, or identifier expected")
            exit_section_modified(builder, m, DlangTypes.AT_ATTRIBUTE, true)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.AT_ATTRIBUTE, true)
        return true
    }

    /**
     * Parses an Attribute
     *
     *
     * $(GRAMMAR $(RULEDEF attribute):
     * $(RULE pragmaExpression)
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
    fun parseAttribute(): Boolean {
        val m = builder.mark()
        val i = current()
        if (i === DlangTypes.KW_PRAGMA) {
            if (!parsePragmaExpression()) {
                cleanup(m, DlangTypes.ATTRIBUTE)
                return false
            }
        } else if (i === DlangTypes.KW_DEPRECATED) {
            if (!parseDeprecated()) {
                cleanup(m, DlangTypes.ATTRIBUTE)
                return false
            }
        } else if (i === DlangTypes.KW_ALIGN) {
            if (!parseAlignAttribute()) {
                cleanup(m, DlangTypes.ATTRIBUTE)
                return false
            }
        } else if (i === DlangTypes.OP_AT) {
            if (!parseAtAttribute()) {
                cleanup(m, DlangTypes.ATTRIBUTE)
                return false
            }
        } else if (i === DlangTypes.KW_PACKAGE) {
            advance()
            if (currentIs(DlangTypes.OP_PAR_LEFT)) {
                expect(DlangTypes.OP_PAR_LEFT)
                if (parseIdentifierChain() == null) {
                    cleanup(m, DlangTypes.ATTRIBUTE)
                    return false
                }
                expect(DlangTypes.OP_PAR_RIGHT)
            }
        } else if (i === DlangTypes.KW_EXTERN) {
            if (peekIs(DlangTypes.OP_PAR_LEFT)) {
                if (!parseLinkageAttribute()) {
                    cleanup(m, DlangTypes.ATTRIBUTE)
                    return false
                }
            } else advance()
        } else if (i === DlangTypes.KW_CONST || i === DlangTypes.KW_IMMUTABLE || i === DlangTypes.KW_INOUT || i === DlangTypes.KW_SHARED) {
            if (peekIs(DlangTypes.OP_PAR_LEFT)) {
                m.rollbackTo()
                return false
            }
            builder.advanceLexer()
            if (builder.tokenType === DlangTypes.ID) {
                m.rollbackTo()
                return false
            }
        } else if (i === DlangTypes.KW_PRIVATE || i === DlangTypes.KW_PROTECTED || i === DlangTypes.KW_PUBLIC || i === DlangTypes.KW_EXPORT || i === DlangTypes.KW_STATIC || i === DlangTypes.KW_ABSTRACT || i === DlangTypes.KW_FINAL || i === DlangTypes.KW_OVERRIDE || i === DlangTypes.KW_SYNCHRONIZED || i === DlangTypes.KW_AUTO || i === DlangTypes.KW_SCOPE || i === DlangTypes.KW___GSHARED || i === DlangTypes.KW_NOTHROW || i === DlangTypes.KW_PURE || i === DlangTypes.KW_REF || i === DlangTypes.KW_THROW) {
            val isStorageClass = isStorageClass
            advance()
            if (isStorageClass && builder.tokenType === DlangTypes.ID) {
                m.rollbackTo()
                return false
            }
        } else {
            m.drop()
            return false
        }
        exit_section_modified(builder, m, DlangTypes.ATTRIBUTE, true)
        return true
    }

    fun parseAutoAssignments() {
        do {
            if (!parseAutoDeclarationPart()) {
                return
            }
            if (currentIs(DlangTypes.OP_COMMA)) advance()
            else break
        } while (moreTokens())
    }

    /**
     * Parses an AutoDeclarationPart
     *
     *
     * $(GRAMMAR $(RULEDEF autoDeclarationPart):
     * $(LITERAL Identifier) $(RULE templateParameters)? $(LITERAL '=') $(RULE initializer)
     * ;)
     */
    fun parseAutoDeclarationPart(): Boolean {
        val m = builder.mark()
        val i = expect(DlangTypes.ID)
        if (i == null) {
            cleanup(m, DlangTypes.AUTO_ASSIGNMENT)
            return false
        }
        if (currentIs(DlangTypes.OP_PAR_LEFT)) {
            if (!parseTemplateParameters()) {
                cleanup(m, DlangTypes.AUTO_ASSIGNMENT)
                return false
            }
        }
        if (!tokenCheck(DlangTypes.OP_EQ)) {
            cleanup(m, DlangTypes.AUTO_ASSIGNMENT)
            return false
        }
        if (!parseInitializer()) {
            cleanup(m, DlangTypes.AUTO_ASSIGNMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.AUTO_ASSIGNMENT, true)
        return true
    }

    fun parseBlocStatementLazy(): Boolean {
        val openBrace = expect(DlangTypes.OP_BRACES_LEFT)
        if (openBrace == null) {
            return false
        }
        val marker = PsiBuilderUtil.parseBlockLazy(builder, DlangTypes.OP_BRACES_LEFT, DlangTypes.OP_BRACES_RIGHT, DlangTypes.BLOCK_STATEMENT)
        return marker != null
    }

    /**
     * Parses a BlockStatement
     *
     *
     * $(GRAMMAR $(RULEDEF blockStatement):
     * $(LITERAL '{') $(RULE statement)* $(LITERAL '}')
     * ;)
     */
    fun parseBlockStatement(): Boolean {
        val m = builder.mark()
        val openBrace = expect(DlangTypes.OP_BRACES_LEFT)
        if (openBrace == null) {
            m.done(DlangTypes.BLOCK_STATEMENT)
            return false
        }
        while (!builder.eof() && builder.tokenType !== DlangTypes.OP_BRACES_RIGHT) {
            if (!parseStatement()) {
                val recovery = builder.mark()
                var parentLevel = 0
                while (!builder.eof()) {
                    if (builder.tokenType === DlangTypes.OP_BRACES_RIGHT) {
                        parentLevel--
                        if (parentLevel < 0) {
                            break
                        }
                    } else if (builder.tokenType === DlangTypes.OP_BRACES_LEFT) {
                        parentLevel++
                    } else if (builder.tokenType === DlangTypes.OP_SCOLON) {
                        if (parentLevel <= 0) {
                            break
                        }
                    }
                    builder.advanceLexer()
                }
                if (builder.tokenType === DlangTypes.OP_SCOLON) builder.advanceLexer()
                recovery.error("Unable to parse this statement")
            }
        }
        expect(DlangTypes.OP_BRACES_RIGHT)
        m.done(DlangTypes.BLOCK_STATEMENT)
        return true
    }

    /**
     * Parses a BreakStatement
     *
     *
     * $(GRAMMAR $(RULEDEF breakStatement):
     * $(LITERAL 'break') $(LITERAL Identifier)? $(LITERAL ';')
     * ;)
     */
    fun parseBreakStatement(): Boolean {
        val m = builder.mark()
        expect(DlangTypes.KW_BREAK)
        if (!moreTokens()) {
            cleanup(m, DlangTypes.BREAK_STATEMENT)
            return false
        }
        val i = current()
        if (i === DlangTypes.ID) {
            advance()
            if (!tokenCheck(DlangTypes.OP_SCOLON)) {
                cleanup(m, DlangTypes.BREAK_STATEMENT)
                return false
            }
        } else if (i === DlangTypes.OP_SCOLON) {
            advance()
        } else {
            error("Identifier or semicolon expected following `break`")
            exit_section_modified(builder, m, DlangTypes.BREAK_STATEMENT, true)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.BREAK_STATEMENT, true)
        return true
    }

    private fun isProtection(type: IElementType?): Boolean {
        return Protections.contains(type)
    }

    /**
     * Parses a BaseClass
     *
     *
     * $(GRAMMAR $(RULEDEF baseClass):
     * $(RULE type2)
     * ;)
     */
    fun parseBaseClass(): Boolean {
        val m = builder.mark()
        if (!moreTokens()) {
            cleanup(m, DlangTypes.BASE_CLASS)
            return false
        }
        if (isProtection(current())) {
            advance()
        }
        if (!parseBasicType()) {
            cleanup(m, DlangTypes.BASE_CLASS)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.BASE_CLASS, true)
        return true
    }

    /**
     * Parses a BaseClassList
     *
     *
     * $(GRAMMAR $(RULEDEF baseClassList):
     * $(RULE baseClass) ($(LITERAL ',') $(RULE baseClass))*
     * ;)
     */
    fun parseBaseClassList(): Boolean {
        val m = builder.mark()
        while (moreTokens()) {
            if (!parseBaseClass()) {
                cleanup(m, DlangTypes.BASE_CLASS_LIST)
                return false
            }
            if (currentIs(DlangTypes.OP_COMMA)) {
                advance()
            } else break
        }
        exit_section_modified(builder, m, DlangTypes.BASE_CLASS_LIST, true)
        return true
    }

    /**
     * Parses an BuiltinType
     *
     *
     * $(GRAMMAR $(RULEDEF builtinType):
     * $(LITERAL 'bool')
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
    private fun parseBuiltinType(): IElementType? {
        assert(isBasicType(current()))
        val marker = builder.mark()
        val type = advance()
        marker.done(DlangTypes.BUILTIN_TYPE)
        return type
    }

    /**
     * Parses a CaseRangeStatement
     *
     *
     * $(GRAMMAR $(RULEDEF caseRangeStatement):
     * $(LITERAL 'case') $(RULE assignExpression) $(LITERAL ':') $(LITERAL '...') $(LITERAL 'case') $(RULE assignExpression) $(LITERAL ':') $(RULE scopeStatementList)?
     * ;)
     */
    fun parseCaseRangeStatement(m: PsiBuilder.Marker): Boolean {
        if (!tokenCheck(DlangTypes.OP_COLON)) {
            cleanup(m, DlangTypes.CASE_RANGE_STATEMENT)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_DDOT)) {
            cleanup(m, DlangTypes.CASE_RANGE_STATEMENT)
            return false
        }
        expect(DlangTypes.KW_CASE)
        if (parseAssignExpression() == null) {
            cleanup(m, DlangTypes.CASE_RANGE_STATEMENT)
            return false
        }
        val colon = expect(DlangTypes.OP_COLON)
        if (colon == null) {
            cleanup(m, DlangTypes.CASE_RANGE_STATEMENT)
            return false
        }
        if (!parseScopeStatementList()) {
            cleanup(m, DlangTypes.CASE_RANGE_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.CASE_RANGE_STATEMENT, true)
        return true
    }

    fun parseCaseRangeStatement(): Boolean {
        val m = builder.mark()
        return parseCaseRangeStatement(m)
    }

    /**
     * Parses an CaseStatement
     *
     *
     * $(GRAMMAR $(RULEDEF caseStatement):
     * $(LITERAL 'case') $(RULE argumentList) $(LITERAL ':') $(RULE scopeArgumentList)?
     * ;)
     */
    fun parseCaseStatement(m: PsiBuilder.Marker): Boolean {
        val colon = expect(DlangTypes.OP_COLON)
        if (colon == null) {
            cleanup(m, DlangTypes.CASE_STATEMENT)
            return false
        }
        if (!parseScopeStatementList()) {
            cleanup(m, DlangTypes.CASE_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.CASE_STATEMENT, true)
        return true
    }

    /**
     * Parses a CastExpression
     *
     *
     * $(GRAMMAR $(RULEDEF castExpression):
     * $(LITERAL 'cast') $(LITERAL '$(LPAREN)') ($(RULE type) | $(RULE castQualifier))? $(LITERAL '$(RPAREN)') $(RULE unaryExpression)
     * ;)
     */
    fun parseCastExpression(): PsiBuilder.Marker? {
        val m = builder.mark()
        expect(DlangTypes.KW_CAST)
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, DlangTypes.CAST_EXPRESSION)
            return null
        }
        if (!currentIs(DlangTypes.OP_PAR_RIGHT)) {
            if (isCastQualifier) {
                if (!parseCastQualifier()) {
                    cleanup(m, DlangTypes.CAST_EXPRESSION)
                    return null
                }
            } else {
                if (!parseType()) {
                    cleanup(m, DlangTypes.CAST_EXPRESSION)
                    return null
                }
            }
        }
        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            cleanup(m, DlangTypes.CAST_EXPRESSION)
            return null
        }
        if (parseUnaryExpression() == null) {
            cleanup(m, DlangTypes.CAST_EXPRESSION)
            return null
        }
        exit_section_modified(builder, m, DlangTypes.CAST_EXPRESSION, true)
        return m
    }

    /**
     * Parses a CastQualifier
     *
     *
     * $(GRAMMAR $(RULEDEF castQualifier):
     * $(LITERAL 'const')
     * | $(LITERAL 'const') $(LITERAL 'shared')
     * | $(LITERAL 'immutable')
     * | $(LITERAL 'inout')
     * | $(LITERAL 'inout') $(LITERAL 'shared')
     * | $(LITERAL 'shared')
     * | $(LITERAL 'shared') $(LITERAL 'const')
     * | $(LITERAL 'shared') $(LITERAL 'inout')
     * ;)
     */
    fun parseCastQualifier(): Boolean {
        if (!moreTokens()) {
            return false
        }
        val marker = builder.mark()
        val i = current()
        if (i === DlangTypes.KW_INOUT || i === DlangTypes.KW_CONST) {
            advance()
            if (currentIs(DlangTypes.KW_SHARED)) advance()
        } else if (i === DlangTypes.KW_SHARED) {
            advance()
            if (currentIsOneOf(DlangTypes.KW_CONST, DlangTypes.KW_INOUT)) advance()
        } else if (i === DlangTypes.KW_IMMUTABLE) {
            advance()
        } else {
            error("`const`, `immutable`, `inout`, or `shared` expected")
            return false
        }
        exit_section_modified(builder, marker, DlangTypes.CAST_QUALIFIER, true)
        return true
    }

    /**
     * Parses a Catch
     *
     *
     * $(GRAMMAR $(RULEDEF catch):
     * $(LITERAL 'catch') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL Identifier)? $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement)
     * ;)
     */
    fun parseCatch(): Boolean {
        val m = builder.mark()
        expect(DlangTypes.KW_CATCH)
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, DlangTypes.CATCH)
            return false
        }
        if (!parseType()) {
            cleanup(m, DlangTypes.CATCH)
            return false
        }
        if (currentIs(DlangTypes.ID)) {
            advance()
        }
        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            cleanup(m, DlangTypes.CATCH)
            return false
        }
        if (!parseNoScopeNonEmptyStatement()) {
            cleanup(m, DlangTypes.CATCH)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.CATCH, true)
        return true
    }

    /**
     * Parses a Catches
     *
     *
     * $(GRAMMAR $(RULEDEF catches):
     * $(RULE catch)+
     * | $(RULE catch)* $(RULE lastCatch)
     * ;)
     */
    fun parseCatches(): Boolean {
        val m = builder.mark()
        while (moreTokens()) {
            if (!currentIs(DlangTypes.KW_CATCH)) break
            if (peekIs(DlangTypes.OP_PAR_LEFT)) {
                if (!parseCatch()) {
                    cleanup(m, DlangTypes.CATCHES)
                    return false
                }
            } else {
                if (!parseLastCatch()) {
                    cleanup(m, DlangTypes.CATCHES)
                    return false
                }
                break
            }
        }
        exit_section_modified(builder, m, DlangTypes.CATCHES, true)
        return true
    }

    /**
     * Parses a ClassDeclaration
     *
     *
     * $(GRAMMAR $(RULEDEF classDeclaration):
     * $(LITERAL 'class') $(LITERAL Identifier) $(LITERAL ';')
     * | $(LITERAL 'class') $(LITERAL Identifier) ($(LITERAL ':') $(RULE baseClassList))? $(RULE structBody)
     * | $(LITERAL 'class') $(LITERAL Identifier) $(RULE templateParameters) $(RULE consraint)? ($(RULE structBody) | $(LITERAL ';'))
     * | $(LITERAL 'class') $(LITERAL Identifier) $(RULE templateParameters) $(RULE consraint)? ($(LITERAL ':') $(RULE baseClassList))? $(RULE structBody)
     * | $(LITERAL 'class') $(LITERAL Identifier) $(RULE templateParameters) ($(LITERAL ':') $(RULE baseClassList))? $(RULE raint)? $(RULE structBody)
     * ;)
     */
    fun parseClassDeclaration(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_CLASS) return false
        builder.advanceLexer()
        parseInterfaceOrClass()
        m.done(DlangTypes.CLASS_DECLARATION)
        return true
    }

    /**
     * Parses a CmpExpression
     *
     *
     * $(GRAMMAR $(RULEDEF cmpExpression):
     * $(RULE shiftExpression)
     * | $(RULE equalExpression)
     * | $(RULE identityExpression)
     * | $(RULE relExpression)
     * | $(RULE inExpression)
     * ;)
     */
    fun parseCmpExpression(): PsiBuilder.Marker? {
        val shift = parseShiftExpression() ?: return null
        val i = current()
        if (i === DlangTypes.KW_IS) {
            return parseIdentityExpression(shift.precede())
        } else if (i === DlangTypes.KW_IN) {
            return parseInExpression(shift.precede())
        } else if (i === DlangTypes.OP_NOT) {
            if (peekIs(DlangTypes.KW_IS)) {
                return parseIdentityExpression(shift.precede())
            } else if (peekIs(DlangTypes.KW_IN)) return parseInExpression(shift.precede())
        } else if (i === DlangTypes.OP_LESS || i === DlangTypes.OP_LESS_EQ || i === DlangTypes.OP_GT || i === DlangTypes.OP_GT_EQ || i === DlangTypes.OP_NOT_GR || i === DlangTypes.OP_NOT_GR_EQ || i === DlangTypes.OP_NOT_LESS || i === DlangTypes.OP_NOT_LESS_EQ) {
            return parseRelExpression(shift.precede())
        } else if (i === DlangTypes.OP_EQ_EQ || i === DlangTypes.OP_NOT_EQ) {
            return parseEqualExpression(shift.precede())
        }
        return shift
    }

    /**
     * Parses a CompileCondition
     *
     *
     * $(GRAMMAR $(RULEDEF compileCondition):
     * $(RULE versionCondition)
     * | $(RULE debugCondition)
     * | $(RULE staticIfCondition)
     * ;)
     */
    fun parseCompileCondition(): Boolean {
        val m = builder.mark()
        val i = current()
        if (i === DlangTypes.KW_VERSION) {
            if (!parseVersionCondition()) {
                cleanup(m, DlangTypes.COMPILE_CONDITION)
                return false
            }
        } else if (i === DlangTypes.KW_DEBUG) {
            if (!parseDebugCondition()) {
                cleanup(m, DlangTypes.COMPILE_CONDITION)
                return false
            }
        } else if (i === DlangTypes.KW_STATIC) {
            if (!parseStaticIfCondition()) {
                cleanup(m, DlangTypes.COMPILE_CONDITION)
                return false
            }
        } else {
            m.drop()
            return false
        }
        exit_section_modified(builder, m, DlangTypes.COMPILE_CONDITION, true)
        return true
    }

    fun parseDeclDefsWithRecoveryUpToParentScope() {
        // Assume that the open brace is already passed
        while (!builder.eof() && builder.tokenType !== DlangTypes.OP_BRACES_RIGHT) {
            parseDeclDefWithRecovery()
        }
    }

    fun parseDeclDefWithRecovery() {
        val bookmark = builder.mark()
        if (!parseDeclDef()) {
            bookmark.rollbackTo()
            val recovery = builder.mark()
            var braces_level = 0
            while (!builder.eof()) {
                if (builder.tokenType === DlangTypes.OP_BRACES_RIGHT) {
                    braces_level--
                    if (braces_level < 0) break
                } else if (builder.tokenType === DlangTypes.OP_BRACES_LEFT) {
                    braces_level++
                }
                if (builder.tokenType === DlangTypes.OP_SCOLON) {
                    if (braces_level <= 0) break
                }
                builder.advanceLexer()
            }
            if (builder.tokenType === DlangTypes.OP_SCOLON) builder.advanceLexer()
            recovery.error("Unable to parse this declaration")
        } else {
            bookmark.drop()
        }
    }

    /**
     * Parses a ConditionalDeclaration
     *
     *
     * $(GRAMMAR $(RULEDEF conditionalDeclaration):
     * $(RULE compileCondition) $(RULE declarationBlock)
     * | $(RULE compileCondition) $(RULE declarationBlock) $(LITERAL 'else') $(RULE declarationBlock)
     * | $(RULE compileCondition) $(RULE declarationBlock) $(LITERAL 'else') $(LITERAL ':') $(RULE declDefs)*
     * | $(RULE compileCondition) $(LITERAL ':') $(RULE declDefs)+
     * ;)
     */
    fun parseConditionalDeclaration(m: PsiBuilder.Marker): Boolean {
        val bookmark = builder.mark()
        if (!parseCompileCondition()) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        if (currentIs(DlangTypes.OP_COLON)) {
            builder.advanceLexer()
            parseDeclDefsWithRecoveryUpToParentScope()
            m.done(DlangTypes.CONDITIONAL_DECLARATION)
            return true
        }
        if (!parseDeclarationBlock()) {
            m.done(DlangTypes.CONDITIONAL_DECLARATION)
            return true
        }

        if (builder.tokenType !== DlangTypes.KW_ELSE) {
            m.done(DlangTypes.CONDITIONAL_DECLARATION)
            return true
        }
        builder.advanceLexer()

        if (builder.tokenType === DlangTypes.OP_COLON) {
            builder.advanceLexer()
            parseDeclDefsWithRecoveryUpToParentScope()
        } else {
            parseDeclarationBlock()
        }
        m.done(DlangTypes.CONDITIONAL_DECLARATION)
        return true
    }

    /**
     * Parses a ConditionalStatement
     *
     *
     * $(GRAMMAR $(RULEDEF conditionalStatement):
     * $(RULE compileCondition) $(RULE noScopeNonEmptyStatement) ($(LITERAL 'else') $(RULE noScopeNonEmptyStatement))?
     * ;)
     */
    fun parseConditionalStatement(): Boolean {
        val m = builder.mark()
        if (!parseCompileCondition()) {
            cleanup(m, DlangTypes.CONDITIONAL_STATEMENT)
            return false
        }
        if (!parseNoScopeNonEmptyStatement()) {
            cleanup(m, DlangTypes.CONDITIONAL_STATEMENT)
            return false
        }
        if (currentIs(DlangTypes.KW_ELSE)) {
            advance()
            if (!parseNoScopeNonEmptyStatement()) {
                cleanup(m, DlangTypes.CONDITIONAL_STATEMENT)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.CONDITIONAL_STATEMENT, true)
        return true
    }

    /**
     * Parses a Constraint
     *
     *
     * $(GRAMMAR $(RULEDEF raint):
     * $(LITERAL 'if') $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseConstraint(): Boolean {
        val m = builder.mark()
        val ifToken = expect(DlangTypes.KW_IF)
        if (ifToken == null) {
            cleanup(m, DlangTypes.CONSTRAINT)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, DlangTypes.CONSTRAINT)
            return false
        }
        if (!parseExpression()) {
            cleanup(m, DlangTypes.CONSTRAINT)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            cleanup(m, DlangTypes.CONSTRAINT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.CONSTRAINT, true)
        return true
    }

    /**
     * Parses a Constructor
     *
     *
     * $(GRAMMAR $(RULEDEF ructor):
     * $(LITERAL 'this') $(RULE templateParameters)? $(RULE parameters) $(RULE memberFunctionAttribute)* $(RULE constraint)? ($(RULE functionBody) | $(LITERAL ';'))
     * ;)
     */
    fun parseConstructor(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_THIS) return false
        builder.advanceLexer()
        val p = peekPastParens()
        var isTemplate = false
        if (p === DlangTypes.OP_PAR_LEFT) {
            isTemplate = true
            if (!parseTemplateParameters()) {
                m.done(DlangTypes.CONSTRUCTOR)
                return true
            }
        }
        if (!parseParameters()) {
            m.done(DlangTypes.CONSTRUCTOR)
            return true
        }
        while (moreTokens() && currentIsMemberFunctionAttribute()) if (!parseMemberFunctionAttribute()) {
            m.done(DlangTypes.CONSTRUCTOR)
            return true
        }
        if (isTemplate && currentIs(DlangTypes.KW_IF)) if (!parseConstraint()) {
            m.done(DlangTypes.CONSTRUCTOR)
            return true
        }
        if (currentIs(DlangTypes.OP_SCOLON)) advance()
        else if (!parseFunctionBody()) {
            m.done(DlangTypes.CONSTRUCTOR)
            return true
        }
        m.done(DlangTypes.CONSTRUCTOR)
        return true
    }

    /**
     * Parses an ContinueStatement
     *
     *
     * $(GRAMMAR $(RULEDEF continueStatement):
     * $(LITERAL 'continue') $(LITERAL Identifier)? $(LITERAL ';')
     * ;)
     */
    fun parseContinueStatement(): Boolean {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.KW_CONTINUE)) {
            cleanup(m, DlangTypes.CONTINUE_STATEMENT)
            return false
        }
        if (!moreTokens()) {
            cleanup(m, DlangTypes.CONTINUE_STATEMENT)
            return false
        }
        val i = current()
        if (i === DlangTypes.ID) {
            advance()
            if (!tokenCheck(DlangTypes.OP_SCOLON)) {
                cleanup(m, DlangTypes.CONTINUE_STATEMENT)
                return false
            }
        } else if (i === DlangTypes.OP_SCOLON) {
            advance()
        } else {
            error("Identifier or semicolon expected following \"continue\"")
            exit_section_modified(builder, m, DlangTypes.CONTINUE_STATEMENT, true)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.CONTINUE_STATEMENT, true)
        return true
    }

    /**
     * Parses a DebugCondition
     *
     *
     * $(GRAMMAR $(RULEDEF debugCondition):
     * $(LITERAL 'debug') ($(LITERAL '$(LPAREN)') ($(LITERAL IntegerLiteral) | $(LITERAL Identifier)) $(LITERAL '$(RPAREN)'))?
     * ;)
     */
    fun parseDebugCondition(): Boolean {
        val m = builder.mark()
        val d = expect(DlangTypes.KW_DEBUG)
        if (d == null) {
            cleanup(m, DlangTypes.DEBUG_CONDITION)
            return false
        }
        if (currentIs(DlangTypes.OP_PAR_LEFT)) {
            advance()
            if (currentIsOneOf(DlangTypes.INTEGER_LITERAL, DlangTypes.ID)) {
                advance()
            } else {
                error("Integer literal or identifier expected")
                exit_section_modified(builder, m, DlangTypes.DEBUG_CONDITION, true)
                return false
            }
            if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
                cleanup(m, DlangTypes.DEBUG_CONDITION)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.DEBUG_CONDITION, true)
        return true
    }

    /**
     * Parses a DebugSpecification
     *
     *
     * $(GRAMMAR $(RULEDEF debugSpecification):
     * $(LITERAL 'debug') $(LITERAL '=') ($(LITERAL Identifier) | $(LITERAL IntegerLiteral)) $(LITERAL ';')
     * ;)
     */
    fun parseDebugSpecification(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_DEBUG) return false
        val bookmark = builder.mark()
        builder.advanceLexer()
        if (!tokenCheck(DlangTypes.OP_EQ)) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        if (currentIsOneOf(
                DlangTypes.ID,
                DlangTypes.INTEGER_LITERAL
            )
        ) { // Note: that using integer for version is deprecated since 2.101.0
            advance()
        } else {
            error("Integer literal or identifier expected")
            m.done(DlangTypes.DEBUG_SPECIFICATION)
            return true
        }
        tokenCheck(DlangTypes.OP_SCOLON)
        m.done(DlangTypes.DEBUG_SPECIFICATION)
        return true
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
    fun parseDeclDef(): Boolean {
        val m = builder.mark()
        val result = parseDeclDef(m)
        if (!result) m.drop()
        return result
    }

    fun parseDeclDef(m: PsiBuilder.Marker): Boolean {
        m.setCustomEdgeTokenBinders(LeadingDocCommentBinder.INSTANCE, TrailingDocCommentBinder.INSTANCE)
        var result = false
        result = result || parseDestructor(m)
        result = result || parsePostblit(m)
        result = result || parseInvariant(m)
        result = result || parseUnittest(m)
        result = result || parseDebugSpecification(m)
        result = result || parseVersionSpecification(m)
        result = result || parseAliasThisDeclaration(m)
        result = result || parseConstructor(m)
        result = result || parseStaticConstructor(m)
        result = result || parseStaticDestructor(m)
        result = result || parseSharedStaticConstructor(m)
        result = result || parseSharedStaticDestructor(m)
        result = result || parseConditionalDeclaration(m)
        result = result || parseMixinDeclaration(m)
        result = result || parseDeclaration(m)
        result = result || parseAttributeSpecifier(m)
        result = result || parseEmptyDeclaration(m)
        return result
    }

    fun parseAttributeSpecifier(marker: PsiBuilder.Marker): Boolean {
        val bookmark = builder.mark()
        var hasAttribute = false
        while (!builder.eof()) {
            if (!parseAttribute()) {
                break
            }
            hasAttribute = true
        }
        if (!hasAttribute) {
            bookmark.rollbackTo()
            return false
        }
        if (currentIs(DlangTypes.OP_COLON)) {
            bookmark.drop()
            advance()
            marker.done(DlangTypes.ATTRIBUTE_SPECIFIER)
            return true
        }
        if (!parseDeclarationBlockWithAttribute(marker)) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        // Marker done by parseDeclarationBlock
        return true
    }

    fun parseDeclarationBlockWithAttribute(marker: PsiBuilder.Marker): Boolean {
        // single statement
        if (!currentIs(DlangTypes.OP_BRACES_LEFT)) {
            return parseDeclDef(marker)
        }
        // Multiple statements
        parseDeclarationBlockWithBlock()
        marker.done(DlangTypes.ATTRIBUTE_SPECIFIER)
        return true
    }

    fun parseDeclarationBlock(): Boolean {
        // single statement
        if (!currentIs(DlangTypes.OP_BRACES_LEFT)) {
            return parseDeclDef()
        }
        // Multiple statements
        parseDeclarationBlockWithBlock()
        return true
    }

    private fun parseDeclarationBlockWithBlock() {
        assert(builder.tokenType === DlangTypes.OP_BRACES_LEFT)
        // Multiple statements
        val marker = builder.mark()
        builder.advanceLexer()
        parseDeclDefsWithRecoveryUpToParentScope()
        expect(DlangTypes.OP_BRACES_RIGHT)
        marker.done(DlangTypes.DECLARATION_BLOCK)
    }

    fun parseEmptyDeclaration(marker: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.OP_SCOLON) return false
        builder.advanceLexer()
        marker.done(DlangTypes.EMPTY_DECLARATION)
        return true
    }

    /**
     * Parses a Declaration
     * $(GRAMMAR $(RULEDEF declaration):
     * $(RULE functionDeclaration)
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
    fun parseDeclaration(m: PsiBuilder.Marker): Boolean {
        m.setCustomEdgeTokenBinders(LeadingDocCommentBinder.INSTANCE, TrailingDocCommentBinder.INSTANCE)
        var result = parseAliasDeclaration(m)
        result = result || parseAliasAssign(m)
        result = result || parseAggregateDeclaration(m)
        result = result || parseEnumDeclaration(m)
        result = result || parseImportDeclaration(m)
        result = result || parseConditionalDeclaration(m)
        result = result || parseStaticForeachDeclaration(m)
        result = result || parseStaticAssertDeclaration(m)
        result = result || parseTemplateDeclaration(m)
        result = result || parseTemplateMixinDeclaration(m)
        result = result || parseTemplateMixin(m)
        result = result || parseFunctionDeclaration(m)
        result = result || parseVariableDeclaration(m)
        return result
    }

    fun parseAggregateDeclaration(m: PsiBuilder.Marker): Boolean {
        var result = parseClassDeclaration(m)
        result = result || parseInterfaceDeclaration(m)
        result = result || parseStructDeclaration(m)
        result = result || parseUnionDeclaration(m)
        return result
    }

    private fun exit_section_modified(builder: PsiBuilder, m: PsiBuilder.Marker, type: IElementType, b: Boolean) {
        m.done(type)
    }

    /**
     * Parses an Identifier initializer
     *
     *
     * $(GRAMMAR $(RULEDEF declarator):
     * $(LITERAL Identifier)
     * | $(LITERAL Identifier) $(LITERAL '=') $(RULE initializer)
     * | $(LITERAL Identifier) $(RULE templateParameters) $(LITERAL '=') $(RULE initializer)
     * ;)
     */
    fun parseIdentifierInitializer(): Boolean {
        if (builder.tokenType !== DlangTypes.ID) {
            return false
        }
        val m = builder.mark()
        advance()
        if (currentIs(DlangTypes.OP_BRACKET_LEFT))  // dmd doesn't accept pointer after identifier
        {
            while (moreTokens() && currentIs(DlangTypes.OP_BRACKET_LEFT)) if (!parseTypeSuffix()) {
                cleanup(m, DlangTypes.IDENTIFIER_INITIALIZER)
                return false
            }
        }
        if (currentIs(DlangTypes.OP_PAR_LEFT)) {
            if (!parseTemplateParameters()) {
                cleanup(m, DlangTypes.IDENTIFIER_INITIALIZER)
                return false
            }
            if (!tokenCheck(DlangTypes.OP_EQ)) {
                cleanup(m, DlangTypes.IDENTIFIER_INITIALIZER)
                return false
            }
            if (!parseInitializer()) {
                cleanup(m, DlangTypes.IDENTIFIER_INITIALIZER)
                return true
            }
        } else if (currentIs(DlangTypes.OP_EQ)) {
            advance()
            if (!parseInitializer()) {
                cleanup(m, DlangTypes.IDENTIFIER_INITIALIZER)
                return true
            }
        }
        exit_section_modified(builder, m, DlangTypes.IDENTIFIER_INITIALIZER, true)
        return true
    }

    /**
     * Parses a DeclaratorIdentifierList
     *
     *
     * $(GRAMMAR $(RULEDEF declaratorIdentifierList):
     * $(LITERAL Identifier) ($(LITERAL ',') $(LITERAL Identifier))*
     * ;)
     */
    fun parseDeclaratorIdentifierList(): Boolean {
        while (moreTokens()) {
            val m = builder.mark()
            val ident = expect(DlangTypes.ID)
            if (ident == null) {
                cleanup(m, DlangTypes.DECLARATOR_IDENTIFIER)
                return false
            }
            if (currentIs(DlangTypes.OP_COMMA)) {
                advance()
            } else {
                exit_section_modified(builder, m, DlangTypes.DECLARATOR_IDENTIFIER, true)
                break
            }
            exit_section_modified(builder, m, DlangTypes.DECLARATOR_IDENTIFIER, true)
        }
        return true
    }

    /**
     * Parses a DefaultStatement
     *
     *
     * $(GRAMMAR $(RULEDEF defaultStatement):
     * $(LITERAL 'default') $(LITERAL ':') $(RULE scopeStatementList)?
     * ;)
     */
    fun parseDefaultStatement(): Boolean {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.KW_DEFAULT)) {
            cleanup(m, DlangTypes.DEFAULT_STATEMENT)
            return false
        }
        val colon = expect(DlangTypes.OP_COLON)
        if (colon == null) {
            cleanup(m, DlangTypes.DEFAULT_STATEMENT)
            return false
        }
        if (!parseScopeStatementList()) {
            cleanup(m, DlangTypes.DEFAULT_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.DEFAULT_STATEMENT, true)
        return true
    }

    /**
     * Parses a DeleteExpression
     *
     *
     * $(GRAMMAR $(RULEDEF deleteExpression):
     * $(LITERAL 'delete') $(RULE unaryExpression)
     * ;)
     */
    fun parseDeleteExpression(): PsiBuilder.Marker? {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.KW_DELETE)) {
            cleanup(m, DlangTypes.DELETE_EXPRESSION)
            return null
        }
        if (parseUnaryExpression() == null) {
            cleanup(m, DlangTypes.DELETE_EXPRESSION)
            return null
        }
        exit_section_modified(builder, m, DlangTypes.DELETE_EXPRESSION, true)
        return m
    }

    /**
     * Parses a Deprecated attribute
     *
     *
     * $(GRAMMAR $(RULEDEF deprecated):
     * $(LITERAL 'deprecated') ($(LITERAL '$(LPAREN)') $(LITERAL StringLiteral)+ $(LITERAL '$(RPAREN)'))?
     * ;)
     */
    fun parseDeprecated(): Boolean {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.KW_DEPRECATED)) {
            cleanup(m, DlangTypes.DEPRECATED)
            return false
        }
        if (currentIs(DlangTypes.OP_PAR_LEFT)) {
            advance()
            if (parseAssignExpression() == null) {
                cleanup(m, DlangTypes.DEPRECATED)
                return false
            }
            if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
                cleanup(m, DlangTypes.DEPRECATED)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.DEPRECATED, true)
        return true
    }

    /**
     * Parses a Destructor
     *
     *
     * $(GRAMMAR $(RULEDEF destructor):
     * $(LITERAL '~') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ';'))
     * ;)
     */
    fun parseDestructor(m: PsiBuilder.Marker): Boolean {
        if (!currentIs(DlangTypes.OP_TILDA)) return false
        builder.advanceLexer()
        if (!moreTokens()) {
            error("`this` expected")
            m.done(DlangTypes.DESTRUCTOR)
            return true
        }
        if (!tokenCheck(DlangTypes.KW_THIS)) {
            m.done(DlangTypes.DESTRUCTOR)
            return true
        }
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            m.done(DlangTypes.DESTRUCTOR)
            return true
        }
        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            m.done(DlangTypes.DESTRUCTOR)
            return true
        }
        if (currentIs(DlangTypes.OP_SCOLON)) advance()
        else {
            while (moreTokens() && currentIsMemberFunctionAttribute()) if (!parseMemberFunctionAttribute()) {
                m.done(DlangTypes.DESTRUCTOR)
                return true
            }
            if (!parseFunctionBody()) {
                m.done(DlangTypes.DESTRUCTOR)
                return true
            }
        }
        m.done(DlangTypes.DESTRUCTOR)
        return true
    }

    /**
     * Parses a DoStatement
     *
     *
     * $(GRAMMAR $(RULEDEF doStatement):
     * $(LITERAL 'do') $(RULE statementNoCaseNoDefault) $(LITERAL 'while') $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)') $(LITERAL ';')
     * ;)
     */
    fun parseDoStatement(): Boolean {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.KW_DO)) {
            cleanup(m, DlangTypes.DO_STATEMENT)
            return false
        }
        if (!moreTokens()) {
            cleanup(m, DlangTypes.DO_STATEMENT)
            return false
        }
        if (!parseNonEmptyStatementNoCaseNoDefault()) {
            cleanup(m, DlangTypes.DO_STATEMENT)
            return false
        }
        if (!tokenCheck(DlangTypes.KW_WHILE)) {
            cleanup(m, DlangTypes.DO_STATEMENT)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, DlangTypes.DO_STATEMENT)
            return false
        }
        if (!parseExpression()) {
            cleanup(m, DlangTypes.DO_STATEMENT)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            cleanup(m, DlangTypes.DO_STATEMENT)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_SCOLON)) {
            cleanup(m, DlangTypes.DO_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.DO_STATEMENT, true)
        return true
    }

    /**
     * Parses an EnumBody
     *
     *
     * $(GRAMMAR $(RULEDEF enumBody):
     * $(LITERAL '{') $(RULE enumMember) ($(LITERAL ',') $(RULE enumMember)?)* $(LITERAL '}')
     * ;)
     */
    fun parseEnumBody(): Boolean {
        if (builder.tokenType !== DlangTypes.OP_BRACES_LEFT) {
            return false
        }
        val m = builder.mark()
        builder.advanceLexer()
        while (moreTokens()) {
            if (currentIsOneOf(DlangTypes.ID, DlangTypes.OP_AT, DlangTypes.KW_DEPRECATED)) {
                parseEnumMember()
                if (currentIs(DlangTypes.OP_COMMA)) {
                    advance()
                    if (!currentIs(DlangTypes.OP_BRACES_RIGHT)) continue
                }
                if (currentIs(DlangTypes.OP_BRACES_RIGHT)) {
                    break
                } else {
                    error("`,` or `}` expected")
                    if (currentIs(DlangTypes.OP_BRACES_RIGHT)) break
                }
            } else error("Enum member expected")
        }
        expect(DlangTypes.OP_BRACES_RIGHT)
        exit_section_modified(builder, m, DlangTypes.ENUM_BODY, true)
        return true
    }

    /**
     * $(GRAMMAR $(RULEDEF enumMember):
     * $(RULE enumMemberAttributes) $(RULE type)? $(LITERAL identifier) ($(LITERAL '=') $(RULE assignExpression))?
     * ;)
     */
    fun parseEnumMember(typeAllowed: Boolean): Boolean {
        val m = builder.mark()
        // TODO parseEnumMemberAttributes
        if (currentIs(DlangTypes.ID) && peekIsOneOf(
                DlangTypes.OP_COMMA,
                DlangTypes.OP_EQ,
                DlangTypes.OP_BRACES_RIGHT
            )
        ) {
            if (!tokenCheck(DlangTypes.ID)) {
                cleanup(m, DlangTypes.ENUM_MEMBER)
                return false
            }
            if (currentIs(DlangTypes.OP_EQ)) {
                advance() // =
                if (!assignAnonEnumMember(m)) return false
            }
        } else if (typeAllowed) {
            if (!parseType()) {
                cleanup(m, DlangTypes.ENUM_MEMBER)
                return false
            }
            if (!tokenCheck(DlangTypes.ID)) {
                cleanup(m, DlangTypes.ENUM_MEMBER)
                return false
            }
            if (!tokenCheck(DlangTypes.OP_EQ)) {
                cleanup(m, DlangTypes.ENUM_MEMBER)
                return false
            }
            if (!assignAnonEnumMember(m)) return false
        } else {
            error("Cannot specify anonymous enum member type if anonymous enum has a base type.")
            exit_section_modified(builder, m, DlangTypes.ENUM_MEMBER, true)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.ENUM_MEMBER, true)
        return true
    }

    private fun assignAnonEnumMember(m: PsiBuilder.Marker): Boolean {
        if (parseAssignExpression() == null) {
            cleanup(m, DlangTypes.ENUM_MEMBER)
            return false
        }
        return true
    }

    fun parseAnonymousEnumDeclaration(): Boolean {
        var hasBaseType = false
        if (currentIs(DlangTypes.OP_COLON)) {
            advance()
            if (!parseType()) {
                return true
            }
            hasBaseType = true
        }
        if (builder.tokenType !== DlangTypes.OP_BRACES_LEFT) {
            return false
        }
        builder.advanceLexer()
        while (moreTokens()) {
            if (currentIs(DlangTypes.OP_COMMA)) {
                advance()
            } else if (currentIs(DlangTypes.OP_BRACES_RIGHT)) {
                break
            } else {
                parseEnumMember(!hasBaseType)
            }
        }
        if (!tokenCheck(DlangTypes.OP_BRACES_RIGHT)) {
            return true
        }
        return true
    }

    /**
     * Parses an EnumDeclaration
     *
     *
     * $(GRAMMAR $(RULEDEF enumDeclaration):
     * $(LITERAL 'enum') $(LITERAL Identifier) ($(LITERAL ':') $(RULE type)) $(LITERAL ';')
     * | $(LITERAL 'enum') $(LITERAL Identifier) ($(LITERAL ':') $(RULE type)) $(RULE enumBody)
     * $(GRAMMAR $(RULEDEF anonymousEnumDeclaration):
     * $(LITERAL 'enum') ($(LITERAL ':') $(RULE type))? $(LITERAL '{') $(RULE anonymousEnumMember)+ $(LITERAL '}')
     * ;)
     */
    fun parseEnumDeclaration(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_ENUM) return false
        val bookmark = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType !== DlangTypes.ID) {
            if (!parseAnonymousEnumDeclaration()) {
                bookmark.rollbackTo()
                return false
            }
            bookmark.drop()
            m.done(DlangTypes.ANONYMOUS_ENUM_DECLARATION)
            return true
        }
        advance()
        if (currentIs(DlangTypes.OP_COLON)) {
            advance() // skip ':'
            if (!parseType()) {
                bookmark.drop()
                m.done(DlangTypes.ENUM_DECLARATION)
                return true
            }
        }
        if (currentIs(DlangTypes.OP_SCOLON)) {
            advance()
            bookmark.drop()
            m.done(DlangTypes.ENUM_DECLARATION)
            return true
        }
        if (!parseEnumBody()) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        m.done(DlangTypes.ENUM_DECLARATION)
        return true
    }

    /**
     * Parses an EnumMemberAttribute
     *
     *
     * $(GRAMMAR $(RULEDEF enumMemberAttribute):
     * $(RULE atAttribute)
     * | $(RULE deprecated)
     * ;)
     */
    fun parseEnumMemberAttribute(): Boolean {
        val m = builder.mark()
        if (currentIs(DlangTypes.OP_AT)) {
            if (!parseAtAttribute()) {
                cleanup(m, DlangTypes.ENUM_MEMBER_ATTRIBUTE)
                return false
            }
        } else if (currentIs(DlangTypes.KW_DEPRECATED)) {
            if (!parseDeprecated()) {
                cleanup(m, DlangTypes.ENUM_MEMBER_ATTRIBUTE)
                return false
            }
        } else {
            m.drop() // drop instead of cleanup otherwise an empty EnumMemberAttribute will be created
            return false
        }
        exit_section_modified(builder, m, DlangTypes.ENUM_MEMBER_ATTRIBUTE, true)
        return true
    }

    /**
     * Parses an EnumMember
     *
     *
     * $(GRAMMAR $(RULEDEF enumMember):
     * ($(RULE enumMemberAttribute))* $(LITERAL Identifier) $(LITERAL '=') $(RULE assignExpression)
     * ;)
     */
    fun parseEnumMember(): Boolean {
        val m = builder.mark()
        while (moreTokens()) {
            if (!parseEnumMemberAttribute()) {
                break
            }
        }
        if (!tokenCheck(DlangTypes.ID)) {
            cleanup(m, DlangTypes.ENUM_MEMBER)
            return false
        }
        if (currentIs(DlangTypes.OP_EQ)) {
            advance()
            if (parseAssignExpression() == null) {
                cleanup(m, DlangTypes.ENUM_MEMBER)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.ENUM_MEMBER, true)
        return true
    }

    /**
     * Parses an EqualExpression
     *
     *
     * $(GRAMMAR $(RULEDEF equalExpression):
     * $(RULE shiftExpression) ($(LITERAL '==') | $(LITERAL '!=')) $(RULE shiftExpression)
     * ;)
     */
    fun parseEqualExpression(m: PsiBuilder.Marker?): PsiBuilder.Marker? {
        var m = m
        if (m == null) {
            m = builder.mark()
            if (parseShiftExpression() == null) {
                cleanup(m, DlangTypes.EQUAL_EXPRESSION)
                return null
            }
        }
        if (currentIsOneOf(DlangTypes.OP_EQ_EQ, DlangTypes.OP_NOT_EQ)) {
            advance()
        }
        if (parseShiftExpression() == null) {
            cleanup(m, DlangTypes.EQUAL_EXPRESSION)
            return null
        }
        exit_section_modified(builder, m, DlangTypes.EQUAL_EXPRESSION, true)
        return m
    }

    /**
     * Parses an Expression
     *
     *
     * $(GRAMMAR $(RULEDEF expression):
     * $(RULE commaExpression)
     * ;)
     */
    fun parseExpression(): Boolean {
        return parseCommaExpression() != null
    }

    /**
     * Parses an Expression
     *
     *
     * $(GRAMMAR $(RULEDEF expression):
     * $(RULE assignExpression)
     * | $(RULE commaExpression) ($(LITERAL ',') $(RULE assignExpression))
     * ;)
     */
    fun parseCommaExpression(): PsiBuilder.Marker? {
        val assign = parseAssignExpression() ?: return null
        if (currentIs(DlangTypes.OP_COMMA)) {
            val m = assign.precede()
            advance()
            if (!currentIsOneOf(DlangTypes.OP_PAR_RIGHT, DlangTypes.OP_BRACES_RIGHT, DlangTypes.OP_BRACKET_RIGHT)) {
                if (parseCommaExpression() == null) {
                    cleanup(m, DlangTypes.COMMA_EXPRESSION)
                    return m
                }
            }
            m.done(DlangTypes.COMMA_EXPRESSION)
            return m
        }
        return assign
    }

    /**
     * Parses an ExpressionStatement
     *
     *
     * $(GRAMMAR $(RULEDEF expressionStatement):
     * $(RULE expression) $(LITERAL ';')
     * ;)
     */
    fun parseExpressionStatement(): Boolean {
        val m = builder.mark()
        val b = parseExpression()
        if (!b) {
            m.rollbackTo()
            return false
        }
        if (expect(DlangTypes.OP_SCOLON) == null) {
            // To have enter key properly work in unfinished expression statement
            m.setCustomEdgeTokenBinders(null, TrailingSpaceBinder.INSTANCE)
        }
        m.done(DlangTypes.EXPRESSION_STATEMENT)
        return true
    }

    fun parseDeclarationStatement(): Boolean {
        var marker = builder.mark()
        // First try without storage class to have storage class attached to the declaration that can have some
        // It produces a nicer tree
        if (parseDeclaration(builder.mark())) {
            marker.done(DlangTypes.DECLARATION_STATEMENT)
            return true
        }
        // Declaration does not contains storage class
        marker.rollbackTo()
        marker = builder.mark()
        while (!builder.eof()) {
            if (!parseStorageClass()) break
        }
        if (!parseDeclaration(builder.mark())) {
            marker.rollbackTo()
            return false
        }
        marker.done(DlangTypes.DECLARATION_STATEMENT)
        return true
    }

    /**
     * Parses a FinalSwitchStatement
     *
     *
     * $(GRAMMAR $(RULEDEF finalSwitchStatement):
     * $(LITERAL 'final') $(RULE switchStatement)
     * ;)
     */
    fun parseFinalSwitchStatement(): Boolean {
        val m = builder.mark()
        if (expect(DlangTypes.KW_FINAL) == null) {
            cleanup(m, DlangTypes.FINAL_SWITCH_STATEMENT)
            return false
        }
        if (!parseSwitchStatement()) {
            cleanup(m, DlangTypes.FINAL_SWITCH_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.FINAL_SWITCH_STATEMENT, true)
        return true
    }

    /**
     * Parses a Finally statement
     *
     *
     * $(GRAMMAR $(RULEDEF finallyStatement):
     * $(LITERAL 'finally') $(RULE noScopeNonEmptyStatement)
     * ;)
     */
    fun parseFinally(): Boolean {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.KW_FINALLY)) {
            cleanup(m, DlangTypes.FINALLY)
            return false
        }
        if (!parseNoScopeNonEmptyStatement()) {
            cleanup(m, DlangTypes.FINALLY)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.FINALLY, true)
        return true
    }

    /**
     * Parses a ForStatement
     *
     *
     * $(GRAMMAR $(RULEDEF forStatement):
     * $(LITERAL 'for') $(LITERAL '$(LPAREN)') ($LITERAL ';') | $(RULE noScopeNonEmptyStatement)) $(RULE expression)? $(LITERAL ';') $(RULE expression)? $(LITERAL '$(RPAREN)') $(RULE scopeStatement)
     * ;)
     */
    fun parseForStatement(): Boolean {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.KW_FOR)) {
            cleanup(m, DlangTypes.FOR_STATEMENT)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, DlangTypes.FOR_STATEMENT)
            return false
        }
        if (currentIs(DlangTypes.OP_SCOLON)) advance()
        else if (!parseNoScopeNonEmptyStatement()) {
            cleanup(m, DlangTypes.FOR_STATEMENT)
            return false
        }
        if (currentIs(DlangTypes.OP_SCOLON)) advance()
        else {
            if (!parseExpression()) {
                cleanup(m, DlangTypes.FOR_STATEMENT)
                return false
            }
            expect(DlangTypes.OP_SCOLON)
        }
        if (!currentIs(DlangTypes.OP_PAR_RIGHT)) if (!parseExpression()) {
            cleanup(m, DlangTypes.FOR_STATEMENT)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            cleanup(m, DlangTypes.FOR_STATEMENT)
            return false
        }
        if (!parseScopeStatement()) {
            cleanup(m, DlangTypes.FOR_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.FOR_STATEMENT, true)
        return true
    }


    /**
     * Parses a StaticForeachDeclaration
     *
     *
     * $(GRAMMAR $(RULEDEF staticForeachDeclaration):
     * $(LITERAL 'static') ($(LITERAL 'foreach') | $(LITERAL 'foreach_reverse')) $(LITERAL '$(LPAREN)') $(RULE foreachTypeList) $(LITERAL ';') $(RULE expression) $(LITERAL '$(RPAREN)') ($(RULE declaration) | $(LITERAL '{') $(RULE declaration)* $(LITERAL '}'))
     * | $(LITERAL 'static') ($(LITERAL 'foreach') | $(LITERAL 'foreach_reverse')) $(LITERAL '$(LPAREN)') $(RULE foreachType) $(LITERAL ';') $(RULE expression) $(LITERAL '..') $(RULE expression) $(LITERAL '$(RPAREN)') ($(RULE declaration) | $(LITERAL '{') $(RULE declaration)* $(LITERAL '}'))
     * ;)
     */
    fun parseStaticForeachDeclaration(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_STATIC) return false
        val bookmark = builder.mark()
        builder.advanceLexer()
        if (!currentIsOneOf(DlangTypes.KW_FOREACH, DlangTypes.KW_FOREACH_REVERSE)) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        parseForeach(DlangTypes.STATIC_FOREACH_DECLARATION, true)
        exit_section_modified(builder, m, DlangTypes.STATIC_FOREACH_DECLARATION, true)
        return true
    }

    /**
     * Parses a ForeachStatement
     *
     *
     * $(GRAMMAR $(RULEDEF foreachStatement):
     * ($(LITERAL 'foreach') | $(LITERAL 'foreach_reverse')) $(LITERAL '$(LPAREN)') $(RULE foreachTypeList) $(LITERAL ';') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE NoScopeNonEmptyStatement)
     * | ($(LITERAL 'foreach') | $(LITERAL 'foreach_reverse')) $(LITERAL '$(LPAREN)') $(RULE foreachType) $(LITERAL ';') $(RULE expression) $(LITERAL '..') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE NoScopeNonEmptyStatement)
     * ;)
     */
    fun parseForeachStatement(): Boolean {
        return parseForeach(DlangTypes.FOREACH_STATEMENT, false)
    }

    fun parseStaticForeachStatement(): Boolean {
        val m = builder.mark()
        if (expect(DlangTypes.KW_STATIC) == null) {
            cleanup(m, DlangTypes.ASSOC_ARRAY_LITERAL)
            return false
        }
        if (!parseForeachStatement()) {
            cleanup(m, DlangTypes.ASSOC_ARRAY_LITERAL)
            return false
        }
        m.done(DlangTypes.ASSOC_ARRAY_LITERAL)
        return true
    }

    fun parseForeach(elementType: IElementType, declOnly: Boolean): Boolean {
        val m = builder.mark()
        if (currentIsOneOf(DlangTypes.KW_FOREACH, DlangTypes.KW_FOREACH_REVERSE)) {
            advance()
        } else {
            error("`foreach` or `foreach_reverse` expected")
            cleanup(m, elementType)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, elementType)
            return false
        }
        val types = parseForeachTypeList()
        if (types < 0) {
            cleanup(m, elementType)
            return false
        }
        val canBeRange = types == 1
        if (!tokenCheck(DlangTypes.OP_SCOLON)) {
            cleanup(m, elementType)
            return false
        }
        if (!parseExpression()) {
            cleanup(m, elementType)
            return false
        }
        if (currentIs(DlangTypes.OP_DDOT)) {
            if (!canBeRange) {
                error("Cannot have more than one foreach variable for a foreach range statement")
                cleanup(m, elementType)
                return false
            }
            advance()
            if (!parseExpression()) {
                cleanup(m, elementType)
                return false
            }
        }
        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            cleanup(m, elementType)
            return false
        }
        if (currentIs(DlangTypes.OP_BRACES_RIGHT)) {
            error("Statement expected")
            cleanup(m, elementType)
            return true
        }
        if (declOnly) {
            if (currentIs(DlangTypes.OP_BRACES_LEFT)) {
                advance()
                parseDeclDefsWithRecoveryUpToParentScope()
                if (!tokenCheck(DlangTypes.OP_BRACES_RIGHT)) {
                    cleanup(m, elementType)
                    return false
                }
            } else if (!parseDeclDef()) {
                cleanup(m, elementType)
                return false
            }
        } else {
            if (!parseNoScopeNonEmptyStatement()) {
                cleanup(m, elementType)
                return false
            }
        }
        exit_section_modified(builder, m, elementType, true)
        return true
    }

    /**
     * Parses a ForeachType
     *
     *
     * $(GRAMMAR $(RULEDEF foreachType):
     * ($(LITERAL 'scope') | $(LITERAL 'ref') | $(LITERAL 'alias') | $(LITERAL 'enum') | $(RULE typeConstructor))* $(RULE type)? $(LITERAL Identifier)
     * ;)
     */
    fun parseForeachType(): Boolean {
        val m = builder.mark()
        while (moreTokens()) {
            if (currentIs(DlangTypes.KW_SCOPE)) {
                advance()
            } else if (currentIs(DlangTypes.KW_REF)) {
                advance()
            } else if (currentIs(DlangTypes.KW_ALIAS)) {
                advance()
            } else if (currentIs(DlangTypes.KW_ENUM)) {
                advance()
            } else if (!parseTypeConstructor()) {
                break
            }
        }
        if (currentIs(DlangTypes.ID) && peekIsOneOf(DlangTypes.OP_COMMA, DlangTypes.OP_SCOLON)) {
            advance()
            exit_section_modified(builder, m, DlangTypes.FOREACH_TYPE, true)
            return true
        }
        if (!parseType()) {
            cleanup(m, DlangTypes.FOREACH_TYPE)
            return false
        }
        val ident = expect(DlangTypes.ID)
        if (ident == null) {
            cleanup(m, DlangTypes.FOREACH_TYPE)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.FOREACH_TYPE, true)
        return true
    }

    /**
     * Parses a ForeachTypeList
     *
     *
     * $(GRAMMAR $(RULEDEF foreachTypeList):
     * $(RULE foreachType) ($(LITERAL ',') $(RULE foreachType))*
     * ;)
     */
    fun parseForeachTypeList(): Int {
        val marker = builder.mark()
        var count = 0
        while (moreTokens()) {
            if (!parseForeachType()) {
                cleanup(marker, DlangTypes.FOREACH_TYPE_LIST)
                return -1
            }
            count++
            if (currentIs(DlangTypes.OP_COMMA)) {
                advance()
            } else break
        }
        exit_section_modified(builder, marker, DlangTypes.FOREACH_TYPE_LIST, true)
        return count
    }

    /**
     * Parses a FunctionAttribute
     *
     *
     * $(GRAMMAR $(RULEDEF functionAttribute):
     * $(RULE atAttribute)
     * | $(LITERAL 'pure')
     * | $(LITERAL 'nothrow')
     * ;)
     */
    fun parseFunctionAttribute(): Boolean {
        val m = builder.mark()
        val i = current()
        if (i === DlangTypes.OP_AT) {
            if (!parseAtAttribute()) {
                cleanup(m, DlangTypes.FUNCTION_ATTRIBUTE)
                return false
            }
        } else if (i === DlangTypes.KW_PURE || i === DlangTypes.KW_NOTHROW) {
            advance()
        } else {
            error("@attribute, `pure`, or `nothrow` expected")
            exit_section_modified(builder, m, DlangTypes.FUNCTION_ATTRIBUTE, true)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.FUNCTION_ATTRIBUTE, true)
        return true
    }

    /**
     * Parses a FunctionBody
     *
     *
     * $(GRAMMAR $(RULEDEF functionBody):
     * $(RULE specifiedFunctionBody)
     * | $(RULE missingFunctionBody)
     * | $(RULE shortenedFunctionBody)
     * ;)
     */
    fun parseFunctionBody(): Boolean {
        var bookmark = builder.mark()
        if (parseMissingFunctionBody()) {
            bookmark.drop()
        } else {
            bookmark.rollbackTo()
            bookmark = builder.mark()
            if (parseShortenedFunctionBody()) {
                bookmark.drop()
            } else {
                bookmark.rollbackTo()
                return parseSpecifiedFunctionBody()
            }
        }
        return true
    }

    /**
     * Parses a FunctionContract
     *
     *
     * $(GRAMMAR $(RULEDEF functionContract):
     * $(RULE inOutContractExpression)
     * | $(RULE inOutStatement)
     * ;)
     */
    @JvmOverloads
    fun parseFunctionContract(allowStatement: Boolean = true): Boolean {
        val m = builder.mark()
        if (allowStatement && (peekIs(DlangTypes.OP_BRACES_LEFT) || (currentIs(DlangTypes.KW_OUT) && peekAre(
                DlangTypes.OP_PAR_LEFT,
                DlangTypes.ID,
                DlangTypes.OP_PAR_RIGHT
            )))
        ) {
            if (!parseInOutStatement()) {
                cleanup(m, DlangTypes.FUNCTION_CONTRACT)
                return false
            }
        } else if (peekIs(DlangTypes.OP_PAR_LEFT)) {
            if (!parseInOutContractExpression()) {
                cleanup(m, DlangTypes.FUNCTION_CONTRACT)
                return false
            }
        } else {
            error(
                if (allowStatement)
                    "`{` or `(` expected"
                else
                    "`(` expected"
            )
            cleanup(m, DlangTypes.FUNCTION_CONTRACT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.FUNCTION_CONTRACT, true)
        return true
    }

    /**
     * Parses a FunctionDeclaration
     *
     *
     * $(GRAMMAR $(RULEDEF functionDeclaration):
     * ($(RULE storageClass)+ | $(RULE _type)) $(LITERAL Identifier) $(RULE parameters) $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ';'))
     * | ($(RULE storageClass)+ | $(RULE _type)) $(LITERAL Identifier) $(RULE templateParameters) $(RULE parameters) $(RULE memberFunctionAttribute)* $(RULE constraint)? ($(RULE functionBody) | $(LITERAL ';'))
     * ;)
     */
    fun parseFunctionDeclaration(m: PsiBuilder.Marker): Boolean {
        val bookmark = builder.mark()
        var hasStorageClass = false
        while (!builder.eof()) {
            if (!parseStorageClass()) break
            hasStorageClass = true
        }
        if (hasStorageClass && currentIs(DlangTypes.ID) && peekIs(DlangTypes.OP_PAR_LEFT)) {
            // it’s an auto function declaration
            advance()
            if (!parseFunctionDeclaratorSuffix()) {
                bookmark.rollbackTo()
                return false
            }
            if (!parseFunctionBody()) {
                bookmark.rollbackTo()
                return false
            }
            bookmark.drop()
            m.done(DlangTypes.FUNCTION_DECLARATION)
            return true
        }
        if (!parseBasicType()) {
            bookmark.rollbackTo()
            return false
        }
        if (!parseFunctionDeclarator()) {
            bookmark.rollbackTo()
            return false
        }
        if (!parseFunctionBody()) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        m.done(DlangTypes.FUNCTION_DECLARATION)
        return true
    }

    fun parseFunctionDeclarator(): Boolean {
        parseTypeSuffixes()
        if (!tokenCheck(DlangTypes.ID)) {
            return false
        }
        return parseFunctionDeclaratorSuffix()
    }

    fun parseFunctionDeclaratorSuffix(): Boolean {
        if (!currentIs(DlangTypes.OP_PAR_LEFT)) {
            error("`(` expected")
            return false
        }
        val p = peekPastParens()
        val isTemplate = p === DlangTypes.OP_PAR_LEFT
        if (isTemplate) {
            if (!parseTemplateParameters()) {
                return false
            }
        }
        if (!parseParameters()) {
            return false
        }
        while (moreTokens() && currentIsMemberFunctionAttribute()) if (!parseMemberFunctionAttribute()) {
            return false
        }
        if (isTemplate && currentIs(DlangTypes.KW_IF)) {
            return parseConstraint()
        }
        return true
    }

    /**
     * Parses a FunctionLiteralExpression
     *
     *
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
    fun parseFunctionLiteralExpression(): PsiBuilder.Marker? {
        val m = builder.mark()
        if (currentIsOneOf(DlangTypes.KW_FUNCTION, DlangTypes.KW_DELEGATE)) {
            advance()
            if (currentIs(DlangTypes.KW_AUTO)) {
                advance()
                expect(DlangTypes.KW_REF)
            } else if (currentIs(DlangTypes.KW_REF)) {
                advance()
            }
            if (!currentIsOneOf(
                    DlangTypes.OP_PAR_LEFT, DlangTypes.KW_IN, DlangTypes.KW_DO,
                    DlangTypes.KW_OUT, DlangTypes.OP_BRACES_LEFT, DlangTypes.OP_LAMBDA_ARROW
                )
            ) if (!parseType()) {
                m.done(DlangTypes.FUNCTION_LITERAL_EXPRESSION)
                return null
            }
        }
        if (startsWith(DlangTypes.ID, DlangTypes.OP_LAMBDA_ARROW)) {
            parseSingleParameter()
            builder.advanceLexer() // =>
            val success = parseAssignExpression()
            m.done(DlangTypes.FUNCTION_LITERAL_EXPRESSION)
            return if (success != null) m else null
        } else if (currentIs(DlangTypes.OP_PAR_LEFT)
            || currentIs(DlangTypes.KW_REF) && peekIs(DlangTypes.OP_PAR_LEFT) || currentIs(DlangTypes.KW_AUTO) && peekAre(
                DlangTypes.KW_REF, DlangTypes.OP_PAR_LEFT
            )
        ) {
            if (currentIs(DlangTypes.KW_AUTO)) {
                advance()
                expect(DlangTypes.KW_REF)
            } else if (currentIs(DlangTypes.KW_REF)) {
                advance()
            }
            if (!parseParameters()) {
                m.done(DlangTypes.FUNCTION_LITERAL_EXPRESSION)
                return null
            }
            while (currentIsMemberFunctionAttribute()) {
                if (!parseMemberFunctionAttribute()) {
                    break
                }
            }
        }
        if (currentIs(DlangTypes.OP_LAMBDA_ARROW)) {
            advance()
            if (parseAssignExpression() == null) {
                m.done(DlangTypes.FUNCTION_LITERAL_EXPRESSION)
                return null
            }
        } else if (!parseSpecifiedFunctionBody()) {
            m.done(DlangTypes.FUNCTION_LITERAL_EXPRESSION)
            return null
        }
        m.done(DlangTypes.FUNCTION_LITERAL_EXPRESSION)
        return m
    }

    /**
     * Parses a GotoStatement
     *
     *
     * $(GRAMMAR $(RULEDEF gotoStatement):
     * $(LITERAL 'goto') ($(LITERAL Identifier) | $(LITERAL 'default') | $(LITERAL 'case') $(RULE expression)?) $(LITERAL ';')
     * ;)
     */
    fun parseGotoStatement(): Boolean {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.KW_GOTO)) {
            cleanup(m, DlangTypes.GOTO_STATEMENT)
            return false
        }
        val i = current()
        if (i === DlangTypes.ID || i === DlangTypes.KW_DEFAULT) {
            advance()
        } else if (i === DlangTypes.KW_CASE) {
            advance()
            if (!currentIs(DlangTypes.OP_SCOLON)) if (!parseExpression()) {
                cleanup(m, DlangTypes.GOTO_STATEMENT)
                return false
            }
        } else {
            error("Identifier, `default`, or `case` expected")
            exit_section_modified(builder, m, DlangTypes.GOTO_STATEMENT, true)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_SCOLON)) {
            cleanup(m, DlangTypes.GOTO_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.GOTO_STATEMENT, true)
        return true
    }

    /**
     * Parses an IdentifierChain
     *
     *
     * $(GRAMMAR $(RULEDEF identifierChain):
     * $(LITERAL Identifier) ($(LITERAL '.') $(LITERAL Identifier))*
     * ;)
     */
    fun parseIdentifierChain(): PsiBuilder.Marker? {
        if (builder.tokenType !== DlangTypes.ID) return null
        var m = builder.mark()
        builder.advanceLexer()
        m.done(DlangTypes.IDENTIFIER_CHAIN)
        while (builder.tokenType === DlangTypes.OP_DOT) {
            m = m.precede()
            builder.advanceLexer()
            expect(DlangTypes.ID)
            m.done(DlangTypes.IDENTIFIER_CHAIN)
        }
        return m
    }

    /**
     * Parses a QualifiedIdentifier.
     *
     *
     * $(GRAMMAR $(RULEDEF qualifiedIdentifier):
     * $(RULE identifierOrTemplateInstance)
     * | $(RULE identifierOrTemplateInstance) $(LITERAL '.') $(RULE qualifiedIdentifier)
     * | $(RULE identifier) $(LITERAL '[') $(RULE assignExpression) $(LITERAL ']')
     * | $(RULE identifier) $(LITERAL '[') $(RULE assignExpression) $(LITERAL ']') $(LITERAL '.') $(RULE qualifiedIdentifier)
     * ;)
     */
    fun parseQualifiedIdentifier(): Boolean {
        if (builder.tokenType !== DlangTypes.ID) {
            builder.error("Identifier expected")
            return false
        }
        var m = builder.mark()
        while (!builder.eof()) {
            var bookmark = builder.mark()
            if (parseTemplateInstance()) {
                bookmark.drop()
                if (currentIs(DlangTypes.OP_DOT)) {
                    advance()
                    continue
                }
                m.done(DlangTypes.QUALIFIED_IDENTIFIER)
                return true
            } else {
                bookmark.rollbackTo()
                advance() // Identifier
                if (currentIs(DlangTypes.OP_BRACKET_LEFT)) {
                    // dyn arrays -> type suffixes
                    if (peekIs(DlangTypes.OP_BRACKET_RIGHT)) {
                        break
                    }
                    bookmark = builder.mark()
                    advance()
                    // here we can have a type (AA key)
                    if (parseAssignExpression() == null) {
                        bookmark.rollbackTo()
                        break
                    } else if (currentIs(DlangTypes.OP_DDOT)) {
                        bookmark.rollbackTo()
                        break
                    }
                    // otherwise either the index of a type list or a dim
                    bookmark.drop()
                    expect(DlangTypes.OP_BRACKET_RIGHT)
                }
            }
            if (builder.tokenType !== DlangTypes.OP_DOT) {
                break
            }
            advance()
            m.done(DlangTypes.QUALIFIED_IDENTIFIER)
            m = m.precede()
        }
        m.done(DlangTypes.QUALIFIED_IDENTIFIER)
        return true
    }


    /**
     * Parses an MixinQualifiedIdentifier
     *
     *
     * $(GRAMMAR $(RULEDEF MixinQualifiedIdentifier):
     * $(RULE identifier)
     * | $(RULE identifier) ($(LITERAL '.') $(RULE MixinQualifiedIdentifier)
     * | $(RULE TemplateInstance) ($(LITERAL '.') $(RULE MixinQualifiedIdentifier)
     * ;)
     */
    fun parseMixinQualifiedIdentifier(): Boolean {
        if (!currentIs(DlangTypes.ID)) {
            builder.error("Identifier expected")
            return false
        }
        var m = builder.mark()
        while (moreTokens()) {
            val bookmark = builder.mark()
            if (parseTemplateInstance()) {
                if (builder.tokenType !== DlangTypes.OP_DOT) {
                    // Template arguments not part of the name, just take the id
                    bookmark.rollbackTo()
                    assert(builder.tokenType === DlangTypes.ID)
                    advance()
                } else {
                    bookmark.drop()
                }
            } else {
                bookmark.rollbackTo()
                assert(builder.tokenType === DlangTypes.ID)
                advance()
            }
            if (builder.tokenType !== DlangTypes.OP_DOT) {
                break
            }
            builder.advanceLexer()
            if (builder.tokenType !== DlangTypes.ID) {
                break
            }
            m.done(DlangTypes.MIXIN_QUALIFIED_IDENTIFIER)
            m = m.precede()
        }
        m.done(DlangTypes.MIXIN_QUALIFIED_IDENTIFIER)
        return true
    }

    /**
     * Parses an IdentifierOrTemplateInstance
     *
     *
     * $(GRAMMAR $(RULEDEF identifierOrTemplateInstance):
     * $(LITERAL Identifier)
     * | $(RULE templateInstance)
     * ;)
     */
    fun parseIdentifierOrTemplateInstance(): Boolean {
        if (peekIs(DlangTypes.OP_NOT) && !startsWith(DlangTypes.ID, DlangTypes.OP_NOT, DlangTypes.KW_IS) && !startsWith(
                DlangTypes.ID, DlangTypes.OP_NOT, DlangTypes.KW_IN
            )
        ) {
            return parseTemplateInstance()
        } else {
            val ident = expect(DlangTypes.ID)
            return ident != null
        }
    }

    /**
     * Parses an IdentityExpression
     *
     *
     * $(GRAMMAR $(RULEDEF identityExpression):
     * $(RULE shiftExpression) ($(LITERAL 'is') | ($(LITERAL '!') $(LITERAL 'is'))) $(RULE shiftExpression)
     * ;)
     */
    fun parseIdentityExpression(marker: PsiBuilder.Marker?): PsiBuilder.Marker? {
        var m = marker
        if (m == null) {
            m = builder.mark()
            if (parseShiftExpression() == null) {
                cleanup(m, DlangTypes.IDENTITY_EXPRESSION)
                return null
            }
        }
        if (currentIs(DlangTypes.OP_NOT)) {
            advance()
        }
        if (!tokenCheck(DlangTypes.KW_IS)) {
            cleanup(m, DlangTypes.IDENTITY_EXPRESSION)
            return null
        }
        if (parseShiftExpression() == null) {
            cleanup(m, DlangTypes.IDENTITY_EXPRESSION)
            return null
        }
        exit_section_modified(builder, m, DlangTypes.IDENTITY_EXPRESSION, true)
        return m
    }

    /**
     * Parses an IfStatement
     *
     *
     * $(GRAMMAR $(RULEDEF ifStatement):
     * $(LITERAL 'if') $(LITERAL '$(LPAREN)') $(RULE ifCondition) $(LITERAL '$(RPAREN)') $(RULE ifStatement) ($(LITERAL 'else') $(RULE elseStatement))?
     * ;)
     */
    fun parseIfStatement(): Boolean {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.KW_IF)) {
            cleanup(m, DlangTypes.IF_STATEMENT)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, DlangTypes.IF_STATEMENT)
            return false
        }
        if (!parseIfCondition()) {
            cleanup(m, DlangTypes.IF_STATEMENT)
            return false
        }

        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            cleanup(m, DlangTypes.IF_STATEMENT)
            return false
        }
        if (currentIs(DlangTypes.OP_BRACES_RIGHT)) {
            error("Statement expected")
            exit_section_modified(builder, m, DlangTypes.IF_STATEMENT, true)
            return true
        }
        if (!parseThenStatement()) {
            cleanup(m, DlangTypes.IF_STATEMENT)
            return false
        }
        if (currentIs(DlangTypes.KW_ELSE)) {
            advance()
            if (!parseElseStatement()) {
                cleanup(m, DlangTypes.IF_STATEMENT)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.IF_STATEMENT, true)
        return true
    }


    /**
     * Parse IfCondition
     *
     *
     * $(RULEDEF ifCondition):
     * $(LITERAL 'auto') $(LITERAL Identifier) $(LITERAL '=') $(RULE expression)
     * $(LITERAL 'scope') $(LITERAL Identifier) $(LITERAL '=') $(RULE expression)
     * | $(RULE typeConstructors) $(LITERAL Identifier) $(LITERAL '=') $(RULE expression)
     * | $(RULE type) $(LITERAL Identifier) $(LITERAL '=') $(RULE expression)
     * | $(RULE expression)
     * ;)
     */
    fun parseIfCondition(): Boolean {
        // ex. case:
        //    `if (auto identifier = exp)`
        //    `if (scope identifier = exp)`
        if (currentIsOneOf(DlangTypes.KW_AUTO, DlangTypes.KW_SCOPE)) {
            val ifCondition = builder.mark()
            advance()
            val i = expect(DlangTypes.ID)
            if (i != null) expect(DlangTypes.OP_EQ)
            if (!parseExpression()) {
                cleanup(ifCondition, DlangTypes.IF_CONDITION)
                return false
            }
            ifCondition.done(DlangTypes.IF_CONDITION)
        } else {
            if (builder.eof()) {
                return false
            }
            // consume for TypeCtors = identifier
            if (isTypeCtor(current())) {
                var before_advance = builder.mark()
                while (isTypeCtor(current())) {
                    before_advance.drop()
                    before_advance = builder.mark()
                    advance()
                }
                // goes back for TypeCtor(Type) = identifier
                if (currentIs(DlangTypes.OP_PAR_LEFT)) {
                    before_advance.rollbackTo()
                } else {
                    before_advance.drop()
                }
            }
            val bookmark = builder.mark()
            val type = parseType()
            if (!type || !currentIs(DlangTypes.ID) || !peekIs(DlangTypes.OP_EQ)) {
                bookmark.rollbackTo()
                return parseExpression()
            } else {
                bookmark.drop()
                val ifCondition = builder.mark()
                if (!tokenCheck(DlangTypes.ID)) {
                    cleanup(ifCondition, DlangTypes.IF_CONDITION)
                    return false
                }
                if (!tokenCheck(DlangTypes.OP_EQ)) {
                    cleanup(ifCondition, DlangTypes.IF_CONDITION)
                    return false
                }
                if (!parseExpression()) {
                    cleanup(ifCondition, DlangTypes.IF_CONDITION)
                    return false
                }
                ifCondition.done(DlangTypes.IF_CONDITION)
            }
        }
        return true
    }

    fun parseThenStatement(): Boolean {
        return parseScopeStatement()
    }

    fun parseElseStatement(): Boolean {
        return parseScopeStatement()
    }


    /**
     * Parses an ImportBind
     *
     *
     * $(GRAMMAR $(RULEDEF importBind):
     * $(LITERAL Identifier) ($(LITERAL '=') $(LITERAL Identifier))?
     * ;)
     */
    fun parseImportBind(): Boolean {
        val m = builder.mark()
        var isNamedBind = false
        val bookmark = builder.mark()
        var ident = expect(DlangTypes.ID)
        if (ident == null) {
            bookmark.drop()
            cleanup(m, DlangTypes.IMPORT_BIND)
            return false
        }
        if (currentIs(DlangTypes.OP_EQ)) {
            isNamedBind = true
            advance()
            val id = expect(DlangTypes.ID)
            if (id == null) {
                bookmark.drop()
                cleanup(m, DlangTypes.IMPORT_BIND)
                return false
            }
        }
        bookmark.rollbackTo()
        if (isNamedBind) {
            val namedImportBind = builder.mark()
            ident = expect(DlangTypes.ID)
            if (ident == null) {
                cleanup(m, DlangTypes.IMPORT_BIND)
                return false
            }
            exit_section_modified(builder, namedImportBind, DlangTypes.NAMED_IMPORT_BIND, true)
            if (currentIs(DlangTypes.OP_EQ)) {
                advance()
                val id = expect(DlangTypes.ID)
                if (id == null) {
                    cleanup(m, DlangTypes.IMPORT_BIND)
                    return false
                }
            }
        } else {
            ident = expect(DlangTypes.ID)
            if (ident == null) {
                cleanup(m, DlangTypes.IMPORT_BIND)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.IMPORT_BIND, true)
        return true
    }

    /**
     * Parses ImportBindings
     *
     *
     * $(GRAMMAR $(RULEDEF importBindings):
     * $(RULE import) $(LITERAL ':') $(RULE importBind) ($(LITERAL ',') $(RULE importBind))*
     * ;)
     */
    fun parseImportBindings(): Boolean {
        val m = builder.mark()
        builder.advanceLexer()
        while (moreTokens()) {
            if (parseImportBind()) {
                if (currentIs(DlangTypes.OP_COMMA)) advance()
                else break
            } else {
                break
            }
        }
        exit_section_modified(builder, m, DlangTypes.IMPORT_BINDINGS, true)
        return true
    }

    /**
     * Parses an ImportDeclaration
     *
     *
     * $(GRAMMAR $(RULEDEF importDeclaration):
     * $(LITERAL 'static')? $(LITERAL 'import') $(RULE import) ($(LITERAL ',') $(RULE import))* ($(LITERAL ',') $(RULE importBindings))? $(LITERAL ';')
     * | $(LITERAL 'static')? $(LITERAL 'import') $(RULE importBindings) $(LITERAL ';')
     * ;)
     */
    fun parseImportDeclaration(m: PsiBuilder.Marker): Boolean {
        val bookmark = builder.mark()
        var isStatic = false
        if (builder.tokenType === DlangTypes.KW_STATIC) {
            builder.advanceLexer()
            isStatic = true
        }
        if (!tokenCheck(DlangTypes.KW_IMPORT)) {
            bookmark.rollbackTo()
            return false
        }
        if (builder.tokenType === DlangTypes.OP_PAR_LEFT) {
            if (isStatic) {
                builder.error("Expected identifier instead of '(")
                bookmark.drop()
                m.done(DlangTypes.IMPORT_DECLARATION)
                return true
            }
            // It’s an import expression
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        val si = parseSingleImport()
        if (!si) {
            m.done(DlangTypes.IMPORT_DECLARATION)
            return true
        }
        if (currentIs(DlangTypes.OP_COLON)) parseImportBindings()
        else {
            if (currentIs(DlangTypes.OP_COMMA)) {
                advance()
                while (moreTokens()) {
                    if (!parseSingleImport()) {
                        m.done(DlangTypes.IMPORT_DECLARATION)
                        return true
                    }
                    if (currentIs(DlangTypes.OP_COLON)) {
                        parseImportBindings()
                        break
                    } else {
                        if (currentIs(DlangTypes.OP_COMMA)) advance()
                        else break
                    }
                }
            }
        }
        if (!tokenCheck(DlangTypes.OP_SCOLON)) {
            m.done(DlangTypes.IMPORT_DECLARATION)
            return true
        }
        exit_section_modified(builder, m, DlangTypes.IMPORT_DECLARATION, true)
        return true
    }

    /**
     * Parses an ImportExpression
     *
     *
     * $(GRAMMAR $(RULEDEF importExpression):
     * $(LITERAL 'import') $(LITERAL '$(LPAREN)') $(RULE assignExpression) $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseImportExpression(): PsiBuilder.Marker? {
        val marker = builder.mark()
        if (expect(DlangTypes.KW_IMPORT) == null) {
            cleanup(marker, DlangTypes.IMPORT_EXPRESSION)
            return null
        }
        if (expect(DlangTypes.OP_PAR_LEFT) == null) {
            cleanup(marker, DlangTypes.IMPORT_EXPRESSION)
            return null
        }
        if (parseAssignExpression() == null) {
            cleanup(marker, DlangTypes.IMPORT_EXPRESSION)
            return null
        }
        if (expect(DlangTypes.OP_PAR_RIGHT) == null) {
            cleanup(marker, DlangTypes.IMPORT_EXPRESSION)
            return null
        }
        exit_section_modified(builder, marker, DlangTypes.IMPORT_EXPRESSION, true)
        return marker
    }

    /**
     * Parses an InContractExpression
     *
     *
     * $(GRAMMAR $(RULEDEF inContractExpression):
     * $(LITERAL 'in') $(LITERAL '$(LPAREN)') $(RULE assertArguments) $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseInContractExpression(): Boolean {
        val m = builder.mark()
        if (expect(DlangTypes.KW_IN) == null) {
            cleanup(m, DlangTypes.IN_CONTRACT_EXPRESSION)
            return false
        }
        if (expect(DlangTypes.OP_PAR_LEFT) == null) {
            cleanup(m, DlangTypes.IN_CONTRACT_EXPRESSION)
            return false
        }
        if (!parseAssertArguments()) {
            cleanup(m, DlangTypes.IN_CONTRACT_EXPRESSION)
            return false
        }
        if (expect(DlangTypes.OP_PAR_RIGHT) == null) {
            cleanup(m, DlangTypes.IN_CONTRACT_EXPRESSION)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.IN_CONTRACT_EXPRESSION, true)
        return true
    }

    /**
     * Parses an InExpression
     *
     *
     * $(GRAMMAR $(RULEDEF inExpression):
     * $(RULE shiftExpression) ($(LITERAL 'in') | ($(LITERAL '!') $(LITERAL 'in'))) $(RULE shiftExpression)
     * ;)
     */
    fun parseInExpression(m: PsiBuilder.Marker?): PsiBuilder.Marker? {
        var m = m
        if (m == null) {
            m = builder.mark()
            if (parseShiftExpression() == null) {
                cleanup(m, DlangTypes.IN_EXPRESSION)
                return null
            }
        }
        if (currentIs(DlangTypes.OP_NOT)) {
            advance()
        }
        if (!tokenCheck(DlangTypes.KW_IN)) {
            cleanup(m, DlangTypes.IN_EXPRESSION)
            return null
        }
        if (parseShiftExpression() == null) {
            cleanup(m, DlangTypes.IN_EXPRESSION)
            return null
        }
        exit_section_modified(builder, m, DlangTypes.IN_EXPRESSION, true)
        return m
    }

    /**
     * Parses an InOutContractExpression
     *
     *
     * $(GRAMMAR $(RULEDEF inOutContractExpression):
     * $(RULE inContractExpression)
     * | $(RULE outContractExpression)
     * ;)
     */
    fun parseInOutContractExpression(): Boolean {
        val m = builder.mark()
        if (currentIs(DlangTypes.KW_IN)) {
            if (!parseInContractExpression()) {
                cleanup(m, DlangTypes.IN_OUT_CONTRACT_EXPRESSION)
                return false
            }
        } else if (!parseOutContractExpression()) {
            cleanup(m, DlangTypes.IN_OUT_CONTRACT_EXPRESSION)
            return false
        } else {
            cleanup(m, DlangTypes.IN_OUT_CONTRACT_EXPRESSION)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.IN_OUT_CONTRACT_EXPRESSION, true)
        return true
    }

    /**
     * Parses an InOutStatement
     *
     *
     * $(GRAMMAR $(RULEDEF inOutStatement):
     * $(RULE inStatement)
     * | $(RULE outStatement)
     * ;)
     */
    fun parseInOutStatement(): Boolean {
        val m = builder.mark()
        if (currentIs(DlangTypes.KW_IN)) {
            if (!parseInStatement()) {
                cleanup(m, DlangTypes.IN_OUT_STATEMENT)
                return false
            }
        } else if (currentIs(DlangTypes.KW_OUT)) {
            if (!parseOutStatement()) {
                cleanup(m, DlangTypes.IN_OUT_STATEMENT)
                return false
            }
        } else {
            cleanup(m, DlangTypes.IN_OUT_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.IN_OUT_STATEMENT, true)
        return true
    }

    /**
     * Parses an InStatement
     *
     *
     * $(GRAMMAR $(RULEDEF inStatement):
     * $(LITERAL 'in') $(RULE blockStatement)
     * ;)
     */
    fun parseInStatement(): Boolean {
        val m = builder.mark()
        val i = expect(DlangTypes.KW_IN)
        if (i == null) {
            cleanup(m, DlangTypes.IN_STATEMENT)
            return false
        }
        if (!parseBlockStatement()) {
            cleanup(m, DlangTypes.IN_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.IN_STATEMENT, true)
        return true
    }

    /**
     * Parses an Initializer
     *
     *
     * $(GRAMMAR $(RULEDEF initializer):
     * $(LITERAL 'void')
     * | $(RULE nonVoidInitializer)
     * ;)
     */
    fun parseInitializer(): Boolean {
        val m = builder.mark()
        if (currentIs(DlangTypes.KW_VOID)) advance()
        else if (!parseNonVoidInitializer()) {
            cleanup(m, DlangTypes.INITIALIZER)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.INITIALIZER, true)
        return true
    }

    /**
     * Parses an InterfaceDeclaration
     *
     *
     * $(GRAMMAR $(RULEDEF interfaceDeclaration):
     * $(LITERAL 'interface') $(LITERAL Identifier) $(LITERAL ';')
     * | $(LITERAL 'interface') $(LITERAL Identifier) ($(LITERAL ':') $(RULE baseClassList))? $(RULE structBody)
     * | $(LITERAL 'interface') $(LITERAL Identifier) $(RULE templateParameters) $(RULE raint)? ($(LITERAL ':') $(RULE baseClassList))? $(RULE structBody)
     * | $(LITERAL 'interface') $(LITERAL Identifier) $(RULE templateParameters) ($(LITERAL ':') $(RULE baseClassList))? $(RULE raint)? $(RULE structBody)
     * ;)
     */
    fun parseInterfaceDeclaration(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_INTERFACE) return false
        builder.advanceLexer()
        parseInterfaceOrClass()
        m.done(DlangTypes.INTERFACE_DECLARATION)
        return true
    }

    /**
     * Parses an Invariant
     *
     *
     * $(GRAMMAR $(RULEDEF invariant):
     * $(LITERAL 'invariant') ($(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)'))? $(RULE blockStatement)
     * | $(LITERAL 'invariant') $(LITERAL '$(LPAREN)') $(RULE assertArguments) $(LITERAL '$(RPAREN)') $(LITERAL ';')
     * ;)
     */
    fun parseInvariant(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_INVARIANT) return false
        builder.advanceLexer()
        if (currentIs(DlangTypes.OP_PAR_LEFT)) {
            builder.advanceLexer()
            if (builder.tokenType !== DlangTypes.OP_PAR_RIGHT) {
                if (!parseAssertArguments()) {
                    m.done(DlangTypes.INVARIANT)
                    return true
                }
                if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
                    m.done(DlangTypes.INVARIANT)
                    return true
                }
                if (!tokenCheck(DlangTypes.OP_SCOLON)) {
                    m.done(DlangTypes.INVARIANT)
                    return true
                }
                m.done(DlangTypes.INVARIANT)
                return true
            }
            builder.advanceLexer()
        }
        parseBlockStatement()
        m.done(DlangTypes.INVARIANT)
        return true
    }

    /**
     * Parses an IsExpression
     *
     *
     * $(GRAMMAR $(RULEDEF isExpression):
     * $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL '$(RPAREN)')
     * | $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL ':') $(RULE typeSpecialization) $(LITERAL '$(RPAREN)')
     * | $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL '=') $(RULE typeSpecialization) $(LITERAL '$(RPAREN)')
     * | $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL ':') $(RULE typeSpecialization) $(LITERAL ',') $(RULE templateParameterList) $(LITERAL '$(RPAREN)')
     * | $(LITERAL'is') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL identifier)? $(LITERAL '=') $(RULE typeSpecialization) $(LITERAL ',') $(RULE templateParameterList) $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseIsExpression(): PsiBuilder.Marker? {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.KW_IS)) {
            cleanup(m, DlangTypes.IS_EXPRESSION)
            return null
        }
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, DlangTypes.IS_EXPRESSION)
            return null
        }
        if (!parseType()) {
            cleanup(m, DlangTypes.IS_EXPRESSION)
            return null
        }
        if (currentIs(DlangTypes.ID)) advance()
        if (currentIsOneOf(DlangTypes.OP_EQ_EQ, DlangTypes.OP_COLON)) {
            advance()
            if (!parseTypeSpecialization()) {
                cleanup(m, DlangTypes.IS_EXPRESSION)
                return null
            }
            if (currentIs(DlangTypes.OP_COMMA)) {
                advance()
                if (!parseTemplateParameterList()) {
                    cleanup(m, DlangTypes.IS_EXPRESSION)
                    return null
                }
            }
        }
        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            cleanup(m, DlangTypes.IS_EXPRESSION)
            return null
        }
        exit_section_modified(builder, m, DlangTypes.IS_EXPRESSION, true)
        return m
    }

    /**
     * Parses a KeyValuePair
     *
     *
     * $(GRAMMAR $(RULEDEF keyValuePair):
     * $(RULE assignExpression) $(LITERAL ':') $(RULE assignExpression)
     * ;)
     */
    fun parseKeyValuePair(): Boolean {
        val m = builder.mark()
        if (parseAssignExpression() == null) {
            cleanup(m, DlangTypes.KEY_VALUE_PAIR)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_COLON)) {
            cleanup(m, DlangTypes.KEY_VALUE_PAIR)
            return false
        }
        if (parseAssignExpression() == null) {
            cleanup(m, DlangTypes.KEY_VALUE_PAIR)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.KEY_VALUE_PAIR, true)
        return true
    }

    /**
     * Parses KeyValuePairs
     *
     *
     * $(GRAMMAR $(RULEDEF keyValuePairs):
     * $(RULE keyValuePair) ($(LITERAL ',') $(RULE keyValuePair))* $(LITERAL ',')?
     * ;)
     */
    fun parseKeyValuePairs(): Boolean {
        val m = builder.mark()
        while (moreTokens()) {
            if (!parseKeyValuePair()) {
                cleanup(m, DlangTypes.KEY_VALUE_PAIRS)
                return false
            }
            if (currentIs(DlangTypes.OP_COMMA)) {
                advance()
                if (currentIs(DlangTypes.OP_BRACKET_RIGHT)) break
            } else break
        }
        exit_section_modified(builder, m, DlangTypes.KEY_VALUE_PAIRS, true)
        return true
    }

    /**
     * Parses a LabeledStatement
     *
     *
     * $(GRAMMAR $(RULEDEF labeledStatement):
     * $(LITERAL Identifier) $(LITERAL ':') $(RULE Statement)?
     * ;)
     */
    fun parseLabeledStatement(): Boolean {
        val m = builder.mark()
        val ident = expect(DlangTypes.ID)
        if (ident == null) {
            cleanup(m, DlangTypes.LABELED_STATEMENT)
            return false
        }
        expect(DlangTypes.OP_COLON)
        if (!currentIs(DlangTypes.OP_BRACES_RIGHT)) if (!parseStatement()) {
            cleanup(m, DlangTypes.LABELED_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.LABELED_STATEMENT, true)
        return true
    }

    /**
     * Parses a LastCatch
     *
     *
     * $(GRAMMAR $(RULEDEF lastCatch):
     * $(LITERAL 'catch') $(RULE statementNoCaseNoDefault)
     * ;)
     */
    fun parseLastCatch(): Boolean {
        val m = builder.mark()
        val t = expect(DlangTypes.KW_CATCH)
        if (t == null) {
            cleanup(m, DlangTypes.LAST_CATCH)
            return false
        }
        if (!parseNonEmptyStatementNoCaseNoDefault()) {
            cleanup(m, DlangTypes.LAST_CATCH)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.LAST_CATCH, true)
        return true
    }

    /**
     * Parses a LinkageAttribute
     *
     *
     * $(GRAMMAR $(RULEDEF linkageAttribute):
     * $(LITERAL 'extern') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '$(RPAREN)')
     * | $(LITERAL 'extern') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '-') $(LITERAL Identifier) $(LITERAL '$(RPAREN)')
     * | $(LITERAL 'extern') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '++') ($(LITERAL ',') $(RULE QualifiedIdentifier) | $(RULE namespaceList) | $(LITERAL 'struct') | $(LITERAL 'class'))? $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseLinkageAttribute(): Boolean {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.KW_EXTERN)) {
            cleanup(m, DlangTypes.LINKAGE_ATTRIBUTE)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, DlangTypes.LINKAGE_ATTRIBUTE)
            return false
        }
        val ident = expect(DlangTypes.ID)
        if (ident == null) {
            cleanup(m, DlangTypes.LINKAGE_ATTRIBUTE)
            return false
        }
        if (currentIs(DlangTypes.OP_PLUS_PLUS)) {
            advance()
            if (currentIs(DlangTypes.OP_COMMA)) {
                advance()
                if (currentIsOneOf(DlangTypes.KW_STRUCT, DlangTypes.KW_CLASS)) advance()
                else if (currentIs(DlangTypes.ID)) {
                    if (!parseQualifiedIdentifier()) {
                        cleanup(m, DlangTypes.LINKAGE_ATTRIBUTE)
                        return false
                    }
                } else {
                    if (!parseNamespaceList()) {
                        cleanup(m, DlangTypes.LINKAGE_ATTRIBUTE)
                        return false
                    }
                }
            }
        } else if (currentIs(DlangTypes.OP_MINUS)) {
            advance()
            if (!tokenCheck(DlangTypes.ID)) {
                cleanup(m, DlangTypes.LINKAGE_ATTRIBUTE)
                return false
            }
        }
        expect(DlangTypes.OP_PAR_RIGHT)
        exit_section_modified(builder, m, DlangTypes.LINKAGE_ATTRIBUTE, true)
        return true
    }

    /**
     * Parses a MemberFunctionAttribute
     *
     *
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
    fun parseMemberFunctionAttribute(): Boolean {
        val m = builder.mark()
        if (!moreTokens()) {
            cleanup(m, DlangTypes.MEMBER_FUNCTION_ATTRIBUTE)
            return false
        }
        val i = current()
        if (i === DlangTypes.OP_AT) {
            if (!parseAtAttribute()) {
                cleanup(m, DlangTypes.MEMBER_FUNCTION_ATTRIBUTE)
                return false
            }
        } else if (i === DlangTypes.KW_IMMUTABLE || i === DlangTypes.KW_INOUT || i === DlangTypes.KW_SHARED || i === DlangTypes.KW_CONST || i === DlangTypes.KW_PURE || i === DlangTypes.KW_NOTHROW || i === DlangTypes.KW_RETURN || i === DlangTypes.KW_SCOPE || i === DlangTypes.KW_THROW) {
            advance()
        } else {
            error("Member function attribute expected")
        }
        exit_section_modified(builder, m, DlangTypes.MEMBER_FUNCTION_ATTRIBUTE, true)
        return true
    }

    /**
     * Parses a MissingFunctionBody
     *
     *
     * $(GRAMMAR $(RULEDEF missingFunctionBody):
     * $(LITERAL ';')
     * | $(RULE functionContract)* $(LITERAL ';')
     * ;)
     */
    fun parseMissingFunctionBody(): Boolean {
        val m = builder.mark()
        var haveContract = false
        var lastIsOutContractExpression = false
        while (currentIsOneOf(DlangTypes.KW_IN, DlangTypes.KW_OUT)) {
            val isOut = currentIs(DlangTypes.KW_OUT)
            val bookmark = builder.mark()
            if (parseFunctionContract(false)) {
                lastIsOutContractExpression = isOut
            } else {
                lastIsOutContractExpression = false
                bookmark.rollbackTo()
                if (parseFunctionContract()) haveContract = true
            }
            bookmark.drop()
        }
        if (!haveContract || lastIsOutContractExpression) {
            if (expect(DlangTypes.OP_SCOLON) == null) {
                cleanup(m, DlangTypes.MISSING_FUNCTION_BODY)
                return false
            }
        } else if (moreTokens() && currentIsOneOf(DlangTypes.KW_DO, DlangTypes.OP_LAMBDA_ARROW)) {
            cleanup(m, DlangTypes.MISSING_FUNCTION_BODY)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.MISSING_FUNCTION_BODY, true)
        return true
    }

    /**
     * Parses a MixinDeclaration
     *
     *
     * $(GRAMMAR $(RULEDEF mixinDeclaration):
     * $(LITERAL 'mixin') $(LITERAL '$(LPAREN)') $(RULE argumentList) $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseMixinDeclaration(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_MIXIN) return false
        val bookmark = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType !== DlangTypes.OP_PAR_LEFT) {
            bookmark.rollbackTo()
            return false
        }
        builder.advanceLexer()
        if (!parseArgumentList()) {
            m.done(DlangTypes.MIXIN_DECLARATION)
            return true
        }
        bookmark.drop()
        expect(DlangTypes.OP_PAR_RIGHT)
        expect(DlangTypes.OP_SCOLON)
        m.done(DlangTypes.MIXIN_DECLARATION)
        return true
    }

    /**
     * Parses a MixinType
     *
     *
     * $(GRAMMAR $(RULEDEF mixinType):
     * $(LITERAL 'mixin') $(LITERAL '$(LPAREN)') $(RULE argumentList) $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseMixinType(): PsiBuilder.Marker? {
        val m = builder.mark()
        expect(DlangTypes.KW_MIXIN)
        if (builder.tokenType !== DlangTypes.OP_PAR_LEFT) {
            m.rollbackTo()
            return null
        }
        builder.advanceLexer()
        if (!parseArgumentList()) {
            cleanup(m, DlangTypes.MIXIN_TYPE)
            return null
        }
        expect(DlangTypes.OP_PAR_RIGHT)
        exit_section_modified(builder, m, DlangTypes.MIXIN_TYPE, true)
        return m
    }

    /**
     * Parses a MixinTemplateDeclaration
     *
     *
     * $(GRAMMAR $(RULEDEF mixinTemplateDeclaration):
     * $(LITERAL 'mixin') $(RULE templateDeclaration)
     * ;)
     */
    fun parseTemplateMixinDeclaration(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_MIXIN) {
            return false
        }
        val bookmark = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType !== DlangTypes.KW_TEMPLATE) {
            bookmark.rollbackTo()
            return false
        }
        if (!parseTemplateDeclarationCommon()) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        m.done(DlangTypes.TEMPLATE_MIXIN_DECLARATION)
        return true
    }

    /**
     * Parses a MixinTemplateName
     *
     *
     * $(GRAMMAR $(RULEDEF mixinTemplateName):
     * $(LITERAL '.')? $(RULE mixinQualifiedIdentifier)
     * | $(RULE typeofExpression) $(LITERAL '.') $(RULE mixinQualifiedIdentifier)
     * ;)
     */
    fun parseMixinTemplateName(): Boolean {
        val m = builder.mark()
        if (currentIs(DlangTypes.KW_TYPEOF)) {
            if (parseTypeofExpression() != null) {
                cleanup(m, DlangTypes.MIXIN_TEMPLATE_NAME)
                return false
            }
            expect(DlangTypes.OP_DOT)
            if (!parseMixinQualifiedIdentifier()) {
                cleanup(m, DlangTypes.MIXIN_TEMPLATE_NAME)
                return false
            }
        } else {
            if (currentIs(DlangTypes.OP_DOT)) builder.advanceLexer()
            if (!parseMixinQualifiedIdentifier()) {
                cleanup(m, DlangTypes.MIXIN_TEMPLATE_NAME)
                return false
            }
        }
        m.done(DlangTypes.MIXIN_TEMPLATE_NAME)
        return true
    }

    /**
     * Parses a Module
     *
     *
     * $(GRAMMAR $(RULEDEF module):
     * $(RULE moduleDeclaration)? $(RULE declaration)*
     * ;)
     */
    fun parseModule() {
        if (currentIs(DlangTypes.SHEBANG)) {
            advance()
        }
        val bookmark = builder.mark()
        while (currentIs(DlangTypes.OP_AT) || currentIs(DlangTypes.KW_DEPRECATED)) {
            parseAttribute()
        }
        val isModule = currentIs(DlangTypes.KW_MODULE)
        bookmark.rollbackTo()
        if (isModule) {
            parseModuleDeclaration()
        }
        while (!builder.eof()) {
            // To prevent infinite loop if top level } are present
            if (builder.tokenType === DlangTypes.OP_BRACES_RIGHT) {
                val error = builder.mark()
                builder.advanceLexer()
                error.error("Unexpected '}'")
            }
            parseDeclDefWithRecovery()
        }
    }

    /**
     * Parses a ModuleDeclaration
     *
     *
     * $(GRAMMAR $(RULEDEF moduleDeclaration):
     * $(RULE atAttribute)* $(RULE deprecated)? $(RULE atAttribute)* $(LITERAL 'module') $(RULE identifierChain) $(LITERAL ';')
     * ;)
     */
    fun parseModuleDeclaration() {
        val m = builder.mark()
        m.setCustomEdgeTokenBinders(LeadingDocCommentBinder.INSTANCE, TrailingDocCommentBinder.INSTANCE)
        while (currentIs(DlangTypes.OP_AT)) {
            parseAttribute()
        }
        if (currentIs(DlangTypes.KW_DEPRECATED)) if (!parseDeprecated()) {
            m.done(DlangTypes.MODULE_DECLARATION)
            return
        }
        while (currentIs(DlangTypes.OP_AT)) {
            parseAttribute()
        }
        val start = expect(DlangTypes.KW_MODULE)
        if (start == null) {
            m.done(DlangTypes.MODULE_DECLARATION)
            return
        }
        if (parseIdentifierChain() == null) {
            m.done(DlangTypes.MODULE_DECLARATION)
            return
        }
        expect(DlangTypes.OP_SCOLON)
        m.done(DlangTypes.MODULE_DECLARATION)
    }

    /**
     * Parses a MulExpression.
     *
     *
     * $(GRAMMAR $(RULEDEF mulExpression):
     * $(RULE powExpression)
     * | $(RULE mulExpression) ($(LITERAL '*') | $(LITERAL '/') | $(LITERAL '%')) $(RULE powExpression)
     * ;)
     */
    fun parseMulExpression(): PsiBuilder.Marker? {
        var m = parseUnaryExpression() ?: return null
        while (currentIsOneOf(DlangTypes.OP_ASTERISK, DlangTypes.OP_DIV, DlangTypes.OP_MOD)) {
            m = m.precede()
            builder.advanceLexer()
            if (parseUnaryExpression() == null) {
                cleanup(m, DlangTypes.MUL_EXPRESSION)
                return null
            }
            m.done(DlangTypes.MUL_EXPRESSION)
        }
        return m
    }


    /**
     * Parses a NamespaceList.
     *
     *
     * $(GRAMMAR $(RULEDEF namespaceList):
     * $(RULE ternaryExpression) ($(LITERAL ',') $(RULE ternaryExpression)?)* $(LITERAL ',')?
     * ;)
     */
    fun parseNamespaceList(): Boolean {
        val marker = builder.mark()
        while (moreTokens()) {
            if (parseTernaryExpression() == null) {
                marker.drop()
                return false
            }
            if (currentIs(DlangTypes.OP_COMMA)) {
                advance()
                if (currentIsOneOf(DlangTypes.OP_PAR_RIGHT)) break
            } else break
        }
        exit_section_modified(builder, marker, DlangTypes.NAMESPACE_LIST, true)
        return true
    }

    /**
     * Parses a NewAnonClassExpression
     *
     *
     * $(GRAMMAR $(RULEDEF newAnonClassExpression):
     * $(LITERAL 'new') $(LITERAL 'class') $(RULE arguments)? $(RULE baseClassList)? $(RULE structBody)
     * ;)
     */
    fun parseNewAnonClassExpression(): Boolean {
        val m = builder.mark()
        expect(DlangTypes.KW_NEW)
        expect(DlangTypes.KW_CLASS)
        if (currentIs(DlangTypes.OP_PAR_LEFT)) if (!parseArguments()) {
            cleanup(m, DlangTypes.NEW_ANON_CLASS_EXPRESSION)
            return false
        }
        if (!currentIs(DlangTypes.OP_BRACES_LEFT)) if (!parseBaseClassList()) {
            cleanup(m, DlangTypes.NEW_ANON_CLASS_EXPRESSION)
            return false
        }
        if (!parseStructBody()) {
            cleanup(m, DlangTypes.NEW_ANON_CLASS_EXPRESSION)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.NEW_ANON_CLASS_EXPRESSION, true)
        return true
    }

    /**
     * Parses a NewExpression
     *
     *
     * $(GRAMMAR $(RULEDEF newExpression):
     * $(LITERAL 'new') $(RULE type) (($(LITERAL '[') $(RULE assignExpression) $(LITERAL ']')) | $(RULE arguments))?
     * | $(RULE newAnonClassExpression)
     * ;)
     */
    fun parseNewExpression(): PsiBuilder.Marker? {
        val m = builder.mark()
        if (peekIs(DlangTypes.KW_CLASS)) {
            if (!parseNewAnonClassExpression()) {
                cleanup(m, DlangTypes.NEW_EXPRESSION)
                return null
            }
        } else {
            expect(DlangTypes.KW_NEW)
            if (!parseType(true)) {
                cleanup(m, DlangTypes.NEW_EXPRESSION)
                return null
            }
            if (currentIs(DlangTypes.OP_BRACKET_LEFT)) {
                advance()
                if (parseAssignExpression() == null) {
                    cleanup(m, DlangTypes.NEW_EXPRESSION)
                    return null
                }
                expect(DlangTypes.OP_BRACKET_RIGHT)
            } else if (currentIs(DlangTypes.OP_PAR_LEFT)) if (!parseArguments()) {
                cleanup(m, DlangTypes.NEW_EXPRESSION)
                return null
            }
        }
        exit_section_modified(builder, m, DlangTypes.NEW_EXPRESSION, true)
        return m
    }

    /**
     * Parses a NonVoidInitializer
     *
     *
     * $(GRAMMAR $(RULEDEF nonVoidInitializer):
     * $(RULE assignExpression)
     * | $(RULE arrayLiteral)
     * | $(RULE structInitializer)
     * ;)
     */
    fun parseNonVoidInitializer(): Boolean {
        if (currentIs(DlangTypes.OP_BRACES_LEFT)) {
            val b = peekPastBraces()
            if (b === DlangTypes.OP_PAR_LEFT) {
                return parseAssignExpression() != null
            }
            val bookmark = builder.mark()
            val initializer = parseStructInitializer()
            if (initializer) {
                bookmark.drop()
                return true
            }
            bookmark.rollbackTo()
        } else if (currentIs(DlangTypes.OP_BRACKET_LEFT)) {
            var isAA = false
            val bookmark = builder.mark()
            advance()
            if (currentIs(DlangTypes.OP_BRACKET_LEFT)) {
                advance()
                val c = peekPastBrackets()
                isAA = c === DlangTypes.OP_COLON
            }
            bookmark.rollbackTo()
            val b = peekPastBrackets()
            if (!isAA && (b === DlangTypes.OP_COMMA || b === DlangTypes.OP_PAR_RIGHT || b === DlangTypes.OP_BRACKET_RIGHT || b === DlangTypes.OP_BRACES_RIGHT || b === DlangTypes.OP_SCOLON)) {
                return parseArrayLiteral() != null
            }
        }
        return parseAssignExpression() != null
    }

    /**
     * Parses Operands
     *
     *
     * $(GRAMMAR $(RULEDEF operands):
     * $(RULE asmExp)
     * | $(RULE asmExp) $(LITERAL ',') $(RULE operands)
     * ;)
     */
    fun parseOperands(): Boolean {
        val marker = builder.mark()
        while (true) {
            if (!(parseAsmExp())) {
                cleanup(marker, DlangTypes.OPERANDS)
                return false
            }
            if (currentIs(DlangTypes.OP_COMMA)) advance()
            else break
        }
        exit_section_modified(builder, marker, DlangTypes.OPERANDS, true)
        return true
    }

    /**
     * Parses an OrExpression
     *
     *
     * $(GRAMMAR $(RULEDEF orExpression):
     * $(RULE xorExpression)
     * | $(RULE orExpression) $(LITERAL '|') $(RULE xorExpression)
     * ;)
     */
    fun parseOrExpression(): PsiBuilder.Marker? {
        var m = parseXorExpression() ?: return null
        while (currentIs(DlangTypes.OP_OR)) {
            m = m.precede()
            builder.advanceLexer()
            if (parseXorExpression() == null) {
                cleanup(m, DlangTypes.OR_EXPRESSION)
                return null
            }
            m.done(DlangTypes.OR_EXPRESSION)
        }
        return m
    }

    /**
     * Parses an OrOrExpression
     *
     *
     * $(GRAMMAR $(RULEDEF orOrExpression):
     * $(RULE andAndExpression)
     * | $(RULE orOrExpression) $(LITERAL '||') $(RULE andAndExpression)
     * ;)
     */
    fun parseOrOrExpression(): PsiBuilder.Marker? {
        var m = parseAndAndExpression() ?: return null
        while (currentIs(DlangTypes.OP_BOOL_OR)) {
            m = m.precede()
            builder.advanceLexer()
            if (parseAndAndExpression() == null) {
                cleanup(m, DlangTypes.OR_OR_EXPRESSION)
                return null
            }
            m.done(DlangTypes.OR_OR_EXPRESSION)
        }
        return m
    }

    /**
     * Parses an OutContractExpression
     *
     *
     * $(GRAMMAR $(RULEDEF outContractExpression):
     * $(LITERAL 'out') $(LITERAL '$(LPAREN)') $(LITERAL Identifier)? $(LITERAL ';') $(RULE assertArguments) $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseOutContractExpression(): Boolean {
        val m = builder.mark()
        val o = expect(DlangTypes.KW_OUT)
        if (o == null) {
            cleanup(m, DlangTypes.OUT_CONTRACT_EXPRESSION)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, DlangTypes.OUT_CONTRACT_EXPRESSION)
            return false
        }
        if (currentIs(DlangTypes.ID)) {
            advance()
        }
        if (!tokenCheck(DlangTypes.OP_SCOLON)) {
            cleanup(m, DlangTypes.OUT_CONTRACT_EXPRESSION)
            return false
        }
        if (!parseAssertArguments()) {
            cleanup(m, DlangTypes.OUT_CONTRACT_EXPRESSION)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            cleanup(m, DlangTypes.OUT_CONTRACT_EXPRESSION)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.OUT_CONTRACT_EXPRESSION, true)
        return true
    }

    /**
     * Parses an OutStatement
     *
     *
     * $(GRAMMAR $(RULEDEF outStatement):
     * $(LITERAL 'out') ($(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '$(RPAREN)'))? $(RULE blockStatement)
     * ;)
     */
    fun parseOutStatement(): Boolean {
        val m = builder.mark()
        val o = expect(DlangTypes.KW_OUT)
        if (o == null) {
            cleanup(m, DlangTypes.OUT_STATEMENT)
            return false
        }
        if (currentIs(DlangTypes.OP_PAR_LEFT)) {
            advance()
            val ident = expect(DlangTypes.ID)
            if (ident == null) {
                cleanup(m, DlangTypes.OUT_STATEMENT)
                return false
            }
            expect(DlangTypes.OP_PAR_RIGHT)
        }
        if (!parseBlockStatement()) {
            cleanup(m, DlangTypes.OUT_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.OUT_STATEMENT, true)
        return true
    }

    /**
     * Parses a Parameter
     *
     *
     * $(GRAMMAR $(RULEDEF parameter):
     * $(RULE parameterAttribute)* $(RULE type)
     * | $(RULE parameterAttribute)* $(RULE type) $(LITERAL Identifier)? $(LITERAL '...')
     * | $(RULE parameterAttribute)* $(RULE type) $(LITERAL Identifier)? ($(LITERAL '=') $(RULE assignExpression))?
     * ;)
     */
    fun parseParameter(): Boolean {
        val m = builder.mark()
        while (!builder.eof()) {
            if (!parseParameterAttribute()) break
        }
        // Parsed the attributes of the variadic attributes.
        // Abort and defer to parseVariadicArgumentsAttributes
        if (currentIs(DlangTypes.OP_TRIPLEDOT)) {
            cleanup(m, DlangTypes.PARAMETER)
            return false
        }

        if (!parseType()) {
            cleanup(m, DlangTypes.PARAMETER)
            return false
        }
        if (currentIs(DlangTypes.ID)) {
            advance()
            if (currentIs(DlangTypes.OP_TRIPLEDOT)) {
                advance()
            } else if (currentIs(DlangTypes.OP_EQ)) {
                advance()
                if (parseAssignExpression() == null) {
                    cleanup(m, DlangTypes.PARAMETER)
                    return false
                }
                if (currentIs(DlangTypes.OP_TRIPLEDOT)) {
                    advance()
                }
            } else if (currentIs(DlangTypes.OP_BRACKET_LEFT)) {
                while (moreTokens() && currentIs(DlangTypes.OP_BRACKET_LEFT)) if (!(parseTypeSuffix())) {
                    cleanup(m, DlangTypes.PARAMETER)
                    return false
                }
            }
        } else if (currentIs(DlangTypes.OP_TRIPLEDOT)) {
            advance()
        } else if (currentIs(DlangTypes.OP_EQ)) {
            advance()
            if (parseAssignExpression() == null) {
                cleanup(m, DlangTypes.PARAMETER)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.PARAMETER, true)
        return true
    }

    /**
     * Parses a ParameterAttribute
     *
     *
     * $(GRAMMAR $(RULEDEF parameterAttribute):
     * $(RULE atAttribute)
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
    fun parseParameterAttribute(): Boolean {
        val i = builder.tokenType
        if (i === DlangTypes.OP_AT) {
            if (!parseAtAttribute()) {
                error("Parameter attribute expected")
                return false
            }
            return true
        } else if (i === DlangTypes.KW_IMMUTABLE || i === DlangTypes.KW_SHARED || i === DlangTypes.KW_CONST || i === DlangTypes.KW_INOUT) {
            if (peekIs(DlangTypes.OP_PAR_LEFT)) return false
            else builder.advanceLexer()
        } else if (i === DlangTypes.KW_FINAL || i === DlangTypes.KW_IN || i === DlangTypes.KW_LAZY || i === DlangTypes.KW_OUT || i === DlangTypes.KW_REF || i === DlangTypes.KW_SCOPE || i === DlangTypes.KW_AUTO || i === DlangTypes.KW_RETURN) {
            builder.advanceLexer()
        } else {
            return false
        }
        return true
    }

    /**
     * Parse a single parameter of a FunctionLiteral Expression
     */
    fun parseSingleParameter() {
        val parametersListMarker = builder.mark()
        val parameterMarker = builder.mark()
        assert(builder.tokenType === DlangTypes.ID)
        builder.advanceLexer()
        parameterMarker.done(DlangTypes.PARAMETER)
        parametersListMarker.done(DlangTypes.PARAMETERS)
    }

    /**
     * Parses Parameters
     *
     *
     * $(GRAMMAR $(RULEDEF parameters):
     * $(LITERAL '$(LPAREN)') $(RULE parameter) ($(LITERAL ',') $(RULE parameter))* ($(LITERAL ',') $(LITERAL '...'))? $(LITERAL '$(RPAREN)')
     * | $(LITERAL '$(LPAREN)') $(LITERAL '...') $(LITERAL '$(RPAREN)')
     * | $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseParameters(): Boolean {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, DlangTypes.PARAMETERS)
            return false
        }

        if (currentIs(DlangTypes.OP_PAR_RIGHT)) {
            advance() // )
            exit_section_modified(builder, m, DlangTypes.PARAMETERS, true)
            return true
        }
        if (currentIs(DlangTypes.OP_TRIPLEDOT)) {
            advance()
            if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
                cleanup(m, DlangTypes.PARAMETERS)
                return false
            }
            exit_section_modified(builder, m, DlangTypes.PARAMETERS, true)
            return true
        }
        while (moreTokens()) {
            if (currentIs(DlangTypes.OP_TRIPLEDOT)) {
                advance()
                break
            }
            if (currentIs(DlangTypes.OP_PAR_RIGHT)) break
            // Save starting point to deal with attributed variadics, e.g.
            // int printf(in char* format, scope const ...);
            val bookmark = builder.mark()
            if (!(parseParameter())) {
                // parseParameter fails for C-style variadics, they are parsed below
                if (!currentIs(DlangTypes.OP_TRIPLEDOT)) {
                    bookmark.drop()
                    cleanup(m, DlangTypes.PARAMETERS)
                    return false
                }
                // Reset to the beginning of the current parameters
                bookmark.rollbackTo()
                if (!parseVariadicArgumentsAttributes()) {
                    cleanup(m, DlangTypes.PARAMETERS)
                    return false
                }
                if (!tokenCheck(DlangTypes.OP_TRIPLEDOT)) {
                    cleanup(m, DlangTypes.PARAMETERS)
                    return false
                }
            } else {
                bookmark.drop()
            }
            if (currentIs(DlangTypes.OP_COMMA)) advance()
            else break
        }
        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            cleanup(m, DlangTypes.PARAMETERS)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.PARAMETERS, true)
        return true
    }

    /**
     * Parses attributes of C-style variadic parameters.
     *
     *
     * $(GRAMMAR $(RULEDEF variadicArgumentsAttributes):
     * $(RULE variadicArgumentsAttribute)+
     * ;)
     */
    fun parseVariadicArgumentsAttributes(): Boolean {
        val m = builder.mark()
        while (moreTokens() && !currentIs(DlangTypes.OP_TRIPLEDOT)) {
            if (!parseVariadicArgumentsAttribute()) {
                cleanup(m, DlangTypes.VARIADIC_ARGUMENTS_ATTRIBUTES)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.VARIADIC_ARGUMENTS_ATTRIBUTES, true)
        return true
    }

    /**
     * Parses an attribute of C-style variadic parameters.
     *
     *
     * $(GRAMMAR $(RULEDEF variadicArgumentsAttribute):
     * $(LITERAL 'const')
     * | $(LITERAL 'immutable')
     * | $(LITERAL 'scope')
     * | $(LITERAL 'shared')
     * | $(LITERAL 'return')
     * ;)
     */
    fun parseVariadicArgumentsAttribute(): Boolean {
        val m = builder.mark()
        if (!currentIsOneOf(
                DlangTypes.KW_CONST,
                DlangTypes.KW_IMMUTABLE,
                DlangTypes.KW_SHARED,
                DlangTypes.KW_SCOPE,
                DlangTypes.KW_RETURN
            )
        ) {
            error("`const`, `immutable`, `shared`, `scope` or `return` expected")
            cleanup(m, DlangTypes.VARIADIC_ARGUMENTS_ATTRIBUTE)
            return false
        }
        advance()
        exit_section_modified(builder, m, DlangTypes.VARIADIC_ARGUMENTS_ATTRIBUTE, true)
        return true
    }

    /**
     * Parses a Postblit
     *
     *
     * $(GRAMMAR $(RULEDEF postblit):
     * $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL 'this') $(LITERAL '$(RPAREN)') $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ';'))
     * ;)
     */
    fun parsePostblit(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_THIS) return false
        val bookmark = builder.mark()
        advance()
        if (builder.tokenType !== DlangTypes.OP_PAR_LEFT) {
            bookmark.rollbackTo()
            return false
        }
        advance()
        if (builder.tokenType !== DlangTypes.KW_THIS) {
            // probably a constructor
            bookmark.rollbackTo()
            return false
        }
        advance()
        if (builder.tokenType !== DlangTypes.OP_PAR_RIGHT) {
            // probably a template taking this as template argument
            bookmark.rollbackTo()
            return false
        }
        advance()
        bookmark.drop()
        while (currentIsMemberFunctionAttribute()) if (!parseMemberFunctionAttribute()) {
            m.done(DlangTypes.POSTBLIT)
            return true
        }
        if (currentIs(DlangTypes.OP_SCOLON)) advance()
        else if (!parseFunctionBody()) {
            m.done(DlangTypes.POSTBLIT)
            return true
        }
        m.done(DlangTypes.POSTBLIT)
        return true
    }

    /**
     * Parses a PowExpression
     *
     *
     * $(GRAMMAR $(RULEDEF powExpression):
     * $(RULE unaryExpression)
     * | $(RULE powExpression) $(LITERAL '^^') $(RULE unaryExpression)
     * ;)
     */
    fun parsePowExpression(): PsiBuilder.Marker? {
        val m = parsePostfixExpression() ?: return null
        if (builder.tokenType === DlangTypes.OP_POW) {
            val marker = m.precede()
            builder.advanceLexer()
            if (parseUnaryExpression() == null) {
                cleanup(marker, DlangTypes.POW_EXPRESSION)
                return null
            }
            marker.done(DlangTypes.POW_EXPRESSION)
            return marker
        }
        return m
    }

    /**
     * Parses a PragmaExpression
     *
     *
     * $(GRAMMAR $(RULEDEF pragmaExpression):
     * $(RULE 'pragma') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) ($(LITERAL ',') $(RULE argumentList))? $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parsePragmaExpression(): Boolean {
        val m = builder.mark()
        expect(DlangTypes.KW_PRAGMA)
        expect(DlangTypes.OP_PAR_LEFT)
        val ident = expect(DlangTypes.ID)
        if (ident == null) {
            cleanup(m, DlangTypes.PRAGMA_EXPRESSION)
            return false
        }
        if (currentIs(DlangTypes.OP_COMMA)) {
            advance()
            if (!parseArgumentList()) {
                cleanup(m, DlangTypes.PRAGMA_EXPRESSION)
                return false
            }
        }
        expect(DlangTypes.OP_PAR_RIGHT)
        exit_section_modified(builder, m, DlangTypes.PRAGMA_EXPRESSION, true)
        return true
    }

    /**
     * Parses a PragmaStatement
     *
     * $(GRAMMAR $(RULEDEF pragmaStatement):
     * $(RULE pragmaExpression) $(RULE statement)
     * | $(RULE pragmaExpression) $(RULE blockStatement)
     * | $(RULE pragmaExpression) $(LITERAL ';')
     * ;)
     */
    fun parsePragmaStatement(): Boolean {
        val m = builder.mark()
        if (!parsePragmaExpression()) {
            cleanup(m, DlangTypes.PRAGMA_STATEMENT)
            return false
        }
        if (current() === DlangTypes.OP_BRACES_LEFT) {
            if (!parseBlockStatement()) {
                cleanup(m, DlangTypes.PRAGMA_STATEMENT)
                return false
            }
        } else if (current() === DlangTypes.OP_SCOLON) {
            advance()
        } else {
            if (!parseNonEmptyStatement()) {
                cleanup(m, DlangTypes.PRAGMA_STATEMENT)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.PRAGMA_STATEMENT, true)
        return true
    }

    /**
     * Parses a PrimaryExpression
     *
     *
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
    fun parsePrimaryExpression(): PsiBuilder.Marker? {
        val i = current()

        // Special keyword
        if (i === DlangTypes.KW___FILE__ || i === DlangTypes.KW___FILE_FULL_PATH__ || i === DlangTypes.KW___MODULE__ || i === DlangTypes.KW___LINE__ || i === DlangTypes.KW___FUNCTION__ || i === DlangTypes.KW___PRETTY_FUNCTION__) {
            val m = builder.mark()
            advance()
            exit_section_modified(builder, m, DlangTypes.MAGIC_LITERAL_EXPRESSION, true)
            return m
        }

        // Special token
        if (i === DlangTypes.KW___DATE__ || i === DlangTypes.KW___TIME__ || i === DlangTypes.KW___TIMESTAMP__ || i === DlangTypes.KW___VENDOR__ || i === DlangTypes.KW___VERSION__) {
            val m = builder.mark()
            advance()
            exit_section_modified(builder, m, DlangTypes.MAGIC_LITERAL_EXPRESSION, true)
            return m
        }

        if (i === DlangTypes.ID && peekIs(DlangTypes.OP_LAMBDA_ARROW)) {
            return parseFunctionLiteralExpression()
        }

        // [.] (Identifier|TemplateInstance)
        if (i === DlangTypes.OP_DOT || i === DlangTypes.ID) {
            return primaryExpressionIdentifierCase()
        }

        if (i === DlangTypes.OP_DOLLAR) {
            val m = builder.mark()
            builder.advanceLexer()
            m.done(DlangTypes.DOLLAR_EXPRESSION)
            return m
        }

        // LiteralExpression
        if (isLiteral(i)) {
            val m = builder.mark()
            if (currentIsOneOf(*stringLiteralsArray)) {
                do {
                    advance()
                } while (currentIsOneOf(*stringLiteralsArray))
            } else {
                advance()
            }
            exit_section_modified(builder, m, DlangTypes.LITERAL_EXPRESSION, true)
            return m
        }
        if (i === DlangTypes.OP_BRACKET_LEFT) {
            // ArrayLiteral | AssocArrayLiteral
            return if (isAssociativeArrayLiteral) {
                parseAssocArrayLiteral()
            } else {
                parseArrayLiteral()
            }
        }
        // Function Literal
        var bookmark = builder.mark()
        val fnctlMarker = parseFunctionLiteralExpression()
        if (fnctlMarker != null) {
            bookmark.drop()
            return fnctlMarker
        }
        bookmark.rollbackTo()

        // AssertExpression
        if (i === DlangTypes.KW_ASSERT) {
            return parseAssertExpression()
        }

        // MixinExpression
        if (i === DlangTypes.KW_MIXIN) {
            return parseMixinType()
        }

        // ImportExpression
        if (i === DlangTypes.KW_IMPORT) {
            return parseImportExpression()
        }

        // NewExpression
        if (i === DlangTypes.KW_NEW) {
            return parseNewExpression()
        }

        // FundamentalType . Identifier | FundamentalType ( NamedArgumentList )
        if (isBasicType(i)) {
            val m = builder.mark()
            advance()
            if (currentIs(DlangTypes.OP_DOT)) {
                advance()
                expect(DlangTypes.ID)
            } else if (currentIs(DlangTypes.OP_PAR_LEFT)) {
                if (!parseArguments()) {
                    cleanup(m, DlangTypes.FUNDAMENTAL_TYPE_CONSTRUCT_EXPRESSION)
                    return null
                }
                m.done(DlangTypes.FUNDAMENTAL_TYPE_CONSTRUCT_EXPRESSION)
                return m
            } else {
                builder.error("`.` or `(` expected")
                m.drop()
                return null
            }
            exit_section_modified(builder, m, DlangTypes.FUNDAMENTAL_TYPE_PROPERTY_EXPRESSION, true)
            return m
        }

        // TypeCtor? ...
        if (i === DlangTypes.KW_IMMUTABLE || i === DlangTypes.KW_CONST || i === DlangTypes.KW_INOUT || i === DlangTypes.KW_SHARED) {
            val m = builder.mark()
            bookmark = builder.mark()
            advance()
            if (expect(DlangTypes.OP_PAR_LEFT) == null) {
                bookmark.rollbackTo()
                m.drop()
                return null
            }
            if (!parseType()) {
                bookmark.rollbackTo()
                m.drop()
                return null
            }
            bookmark.drop()
            expect(DlangTypes.OP_PAR_RIGHT)
            if (currentIs(DlangTypes.OP_DOT)) {
                // ( type ) . id
                advance()
                if (currentIs(DlangTypes.ID)) {
                    advance()
                    exit_section_modified(builder, m, DlangTypes.TYPE_PROPERTY_EXPRESSION, true)
                    return m
                }
            } else if (builder.tokenType !== DlangTypes.OP_PAR_LEFT) {
                builder.error("`.` or `(` expected")
                m.done(DlangTypes.TYPE_CONSTRUCT_EXPRESSION)
                return null
            }
            // (type) (args)
            builder.advanceLexer()
            parseArgumentList()
            expect(DlangTypes.OP_PAR_RIGHT)
            m.done(DlangTypes.TYPE_CONSTRUCT_EXPRESSION)
            return m
        }

        if (i === DlangTypes.OP_PAR_LEFT) {
            val m = builder.mark()
            advance()
            bookmark = builder.mark()
            if (parseType()) {
                if (expect(DlangTypes.OP_PAR_RIGHT) != null) {
                    if (builder.tokenType === DlangTypes.OP_DOT) {
                        // ( type ) . id
                        builder.advanceLexer()
                        expect(DlangTypes.ID)
                        if (builder.tokenType !== DlangTypes.OP_NOT && builder.tokenType !== DlangTypes.OP_PAR_LEFT && builder.tokenType !== DlangTypes.OP_DDOT) {
                            bookmark.drop()
                            exit_section_modified(builder, m, DlangTypes.TYPE_PROPERTY_EXPRESSION, true)
                            return m
                        }
                        // then it’s a parenthesised expression followed by reference expression
                    } else if (builder.tokenType === DlangTypes.OP_PAR_LEFT) {
                        // (type) (args)
                        bookmark.drop()
                        builder.advanceLexer()
                        parseArgumentList()
                        expect(DlangTypes.OP_PAR_RIGHT)
                        m.done(DlangTypes.TYPE_CONSTRUCT_EXPRESSION)
                        return m
                    }
                }
            }
            bookmark.rollbackTo()

            // ( expression )
            if (!parseExpression()) {
                cleanup(m, DlangTypes.PARENTHESISED_EXPRESSION)
                return null
            }
            expect(DlangTypes.OP_PAR_RIGHT)
            exit_section_modified(builder, m, DlangTypes.PARENTHESISED_EXPRESSION, true)
            return m
        }

        if (i === DlangTypes.KW_TYPEOF) {
            return parseTypeofExpression()
        }

        if (i === DlangTypes.KW_TYPEID) {
            return parseTypeidExpression()
        }

        if (i === DlangTypes.KW_IS) {
            return parseIsExpression()
        }

        if (i === DlangTypes.KW___TRAITS) {
            return parseTraitsExpression()
        }

        return null
    }

    private fun isLiteral(i: IElementType?): Boolean {
        return literals.contains(i)
    }

    private fun isBasicType(i: IElementType?): Boolean {
        return basicTypes.contains(i)
    }

    private fun primaryExpressionIdentifierCase(): PsiBuilder.Marker? {
        val m = builder.mark()
        if (builder.tokenType === DlangTypes.OP_DOT) {
            builder.advanceLexer()
        }
        val result = parseIdentifierOrTemplateInstance()
        m.done(DlangTypes.REFERENCE_EXPRESSION)
        return if (result) m else null
    }

    /**
     * Parses a Register
     *
     *
     * $(GRAMMAR $(RULEDEF register):
     * $(LITERAL Identifier)
     * | $(LITERAL Identifier) $(LITERAL '$(LPAREN)') $(LITERAL IntegerLiteral) $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseRegister(): Boolean {
        val m = builder.mark()
        val ident = expect(DlangTypes.ID)
        if (ident == null) {
            cleanup(m, DlangTypes.REGISTER)
            return false
        }
        if (currentIs(DlangTypes.OP_PAR_LEFT)) {
            advance()
            val intLit = expect(DlangTypes.INTEGER_LITERAL)
            if (intLit == null) {
                cleanup(m, DlangTypes.REGISTER)
                return false
            }
            expect(DlangTypes.OP_PAR_RIGHT)
        }
        exit_section_modified(builder, m, DlangTypes.REGISTER, true)
        return true
    }

    /**
     * Parses a RelExpression
     *
     *
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
    fun parseRelExpression(m: PsiBuilder.Marker?): PsiBuilder.Marker? {
        var m = m
        if (m == null) {
            m = builder.mark()
            if (parseShiftExpression() == null) {
                cleanup(m, DlangTypes.IDENTITY_EXPRESSION)
                return null
            }
        }
        if (!currentIsOneOf(DlangTypes.OP_LESS, DlangTypes.OP_LESS_EQ, DlangTypes.OP_GT, DlangTypes.OP_GT_EQ)) {
            cleanup(m, DlangTypes.IDENTITY_EXPRESSION)
            return null
        }
        advance()
        if (parseShiftExpression() == null) {
            cleanup(m, DlangTypes.IDENTITY_EXPRESSION)
            return null
        }
        exit_section_modified(builder, m, DlangTypes.REL_EXPRESSION, true)
        return m
    }

    /**
     * Parses a ReturnStatement
     *
     *
     * $(GRAMMAR $(RULEDEF returnStatement):
     * $(LITERAL 'return') $(RULE expression)? $(LITERAL ';')
     * ;)
     */
    fun parseReturnStatement(): Boolean {
        val m = builder.mark()
        val start = expect(DlangTypes.KW_RETURN)
        if (start == null) {
            cleanup(m, DlangTypes.RETURN_STATEMENT)
            return false
        }
        if (!currentIs(DlangTypes.OP_SCOLON)) if (!parseExpression()) {
            cleanup(m, DlangTypes.RETURN_STATEMENT)
            return false
        }
        val semicolon = expect(DlangTypes.OP_SCOLON)
        if (semicolon == null) {
            cleanup(m, DlangTypes.RETURN_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.RETURN_STATEMENT, true)
        return true
    }

    /**
     * Parses a ScopeGuardStatement
     *
     *
     * $(GRAMMAR $(RULEDEF scopeGuardStatement):
     * $(LITERAL 'scope') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL '$(RPAREN)') $(RULE statementNoCaseNoDefault)
     * ;)
     */
    fun parseScopeGuardStatement(): Boolean {
        val m = builder.mark()
        expect(DlangTypes.KW_SCOPE)
        expect(DlangTypes.OP_PAR_LEFT)
        val ident = expect(DlangTypes.ID)
        if (ident == null) {
            cleanup(m, DlangTypes.SCOPE_GUARD_STATEMENT)
            return false
        }
        expect(DlangTypes.OP_PAR_RIGHT)
        if (!parseNonEmptyStatementNoCaseNoDefault()) {
            cleanup(m, DlangTypes.SCOPE_GUARD_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.SCOPE_GUARD_STATEMENT, true)
        return true
    }

    /**
     * Parses a SharedStaticConstructor
     *
     *
     * $(GRAMMAR $(RULEDEF sharedStaticConstructor):
     * $(LITERAL 'shared') $(LITERAL 'static') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE MemberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ";"))
     * ;)
     */
    fun parseSharedStaticConstructor(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_SHARED) return false
        val bookmark = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType !== DlangTypes.KW_STATIC) {
            bookmark.rollbackTo()
            return false
        }
        builder.advanceLexer()
        val b = parseStaticCtorDtorCommon()
        if (!b) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        m.done(DlangTypes.SHARED_STATIC_CONSTRUCTOR)
        return true
    }

    /**
     * Parses a SharedStaticDestructor
     *
     *
     * $(GRAMMAR $(RULEDEF sharedStaticDestructor):
     * $(LITERAL 'shared') $(LITERAL 'static') $(LITERAL '~') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE MemberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ";"))
     * ;)
     */
    fun parseSharedStaticDestructor(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_SHARED) return false
        val bookmark = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType !== DlangTypes.KW_STATIC) {
            bookmark.rollbackTo()
            return false
        }
        if (!tokenCheck(DlangTypes.OP_TILDA)) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        parseStaticCtorDtorCommon()
        m.done(DlangTypes.SHARED_STATIC_DESTRUCTOR)
        return true
    }

    /**
     * Parses a ShiftExpression
     *
     *
     * $(GRAMMAR $(RULEDEF shiftExpression):
     * $(RULE addExpression)
     * | $(RULE shiftExpression) ($(LITERAL '<<') | $(LITERAL '>>') | $(LITERAL '>>>')) $(RULE addExpression)
     * ;)
     */
    fun parseShiftExpression(): PsiBuilder.Marker? {
        var m = parseAddExpression() ?: return null
        while (currentIsOneOf(DlangTypes.OP_SH_LEFT, DlangTypes.OP_SH_RIGHT, DlangTypes.OP_USH_RIGHT)) {
            m = m.precede()
            builder.advanceLexer()
            if (parseAddExpression() == null) {
                cleanup(m, DlangTypes.SHIFT_EXPRESSION)
                return null
            }
            m.done(DlangTypes.SHIFT_EXPRESSION)
        }
        return m
    }

    /**
     * Parses a SingleImport
     *
     *
     * $(GRAMMAR $(RULEDEF import):
     * ($(LITERAL Identifier) $(LITERAL '='))? $(RULE identifierChain)
     * ;)
     */
    fun parseSingleImport(): Boolean {
        val m = builder.mark()
        if (startsWith(DlangTypes.ID, DlangTypes.OP_EQ)) {
            advance() // identifier
            advance() // =
        }
        if (parseIdentifierChain() == null) {
            cleanup(m, DlangTypes.SINGLE_IMPORT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.SINGLE_IMPORT, true)
        return true
    }

    /**
     * Parses a ShortenedFunctionBody
     *
     *
     * $(GRAMMAR $(RULEDEF shortenedFunctionBody):
     * $(RULE inOutContractExpression)* $(LITERAL '=>') $(RULE expression) $(LITERAL ';')
     * ;)
     */
    fun parseShortenedFunctionBody(): Boolean {
        val m = builder.mark()
        while (currentIsOneOf(DlangTypes.KW_IN, DlangTypes.KW_OUT)) {
            parseFunctionContract(false)
        }
        if (!tokenCheck(DlangTypes.OP_LAMBDA_ARROW)) {
            cleanup(m, DlangTypes.SHORTENED_FUNCTION_BODY)
            return false
        }
        if (!parseExpression()) {
            cleanup(m, DlangTypes.SHORTENED_FUNCTION_BODY)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_SCOLON)) {
            cleanup(m, DlangTypes.SHORTENED_FUNCTION_BODY)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.SHORTENED_FUNCTION_BODY, true)
        return true
    }

    /**
     * Parses a SpecifiedFunctionBody
     *
     *
     * $(GRAMMAR $(RULEDEF specifiedFunctionBody):
     * $(LITERAL 'do')? $(RULE blockStatement)
     * | $(RULE functionContract)* $(RULE inOutContractExpression) $(LITERAL 'do')? $(RULE blockStatement)
     * | $(RULE functionContract)* $(RULE inOutStatement) $(LITERAL 'do') $(RULE blockStatement)
     * ;)
     */
    fun parseSpecifiedFunctionBody(): Boolean {
        val m = builder.mark()
        var requireDo = false
        while (currentIsOneOf(DlangTypes.KW_IN, DlangTypes.KW_OUT)) {
            val bookmark = builder.mark()
            if (parseFunctionContract(false)) {
                requireDo = false
            } else {
                bookmark.rollbackTo()
                if (parseFunctionContract()) {
                    requireDo = true
                }
            }
            bookmark.drop()
        }
        if (currentIs(DlangTypes.KW_DO) || currentIs(DlangTypes.ID)) {
            advance()
            requireDo = false
        }
        if (requireDo) {
            error("`do` expected after InStatement or OutStatement")
            cleanup(m, DlangTypes.SPECIFIED_FUNCTION_BODY)
            return false
        }
        if (!parseBlockStatement()) {
            cleanup(m, DlangTypes.SPECIFIED_FUNCTION_BODY)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.SPECIFIED_FUNCTION_BODY, true)
        return true
    }

    /**
     * Parse a Statement
     * $(GRAMMAR Statement):
     * $(RULE emptyStatement)
     * | $(RULE scopeBlockStatement)
     * | $(RULE nonEmptyStatement)
     */
    fun parseStatement(): Boolean {
        val i = builder.tokenType
        return if (i === DlangTypes.OP_SCOLON) {
            parseEmptyStatement()
        } else if (i === DlangTypes.OP_BRACES_LEFT) {
            parseScopeBlockStatement()
        } else {
            parseNonEmptyStatement()
        }
    }

    /**
     * Parses a Non Empty Statement
     *
     *
     * $(GRAMMAR $(RULEDEF nonEmptyStatement):
     * $(RULE statementNoCaseNoDefault)
     * | $(RULE caseStatement)
     * | $(RULE caseRangeStatement)
     * | $(RULE defaultStatement)
     * ;)
     */
    fun parseNonEmptyStatement(): Boolean {
        if (!moreTokens()) {
            error("Expected statement instead of EOF")
            return false
        }
        val i = current()
        if (i === DlangTypes.KW_CASE) {
            val m_case = builder.mark()
            advance()
            val argumentList = parseArgumentList()
            if (!argumentList) {
                m_case.drop()
                return false
            }
            return if (startsWith(
                    DlangTypes.OP_COLON,
                    DlangTypes.OP_DDOT
                )
            ) {
                parseCaseRangeStatement(m_case)
            } else {
                parseCaseStatement(m_case)
            }
        } else if (i === DlangTypes.KW_DEFAULT) {
            return parseDefaultStatement()
        } else {
            return parseNonEmptyStatementNoCaseNoDefault()
        }
    }

    /**
     * Parses a StatementNoCaseNoDefault
     *
     *
     * $(GRAMMAR $(RULEDEF statementNoCaseNoDefault):
     * $(RULE labeledStatement)
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
    fun parseNonEmptyStatementNoCaseNoDefault(): Boolean {
        if (!moreTokens()) {
            error("Expected statement instead of EOF")
            return false
        }
        val i = current()
        if (i === DlangTypes.OP_BRACES_LEFT) {
            return parseBlockStatement()
        } else if (i === DlangTypes.KW_IF) {
            return parseIfStatement()
        } else if (i === DlangTypes.KW_WHILE) {
            return parseWhileStatement()
        } else if (i === DlangTypes.KW_DO) {
            return parseDoStatement()
        } else if (i === DlangTypes.KW_FOR) {
            return parseForStatement()
        } else if (i === DlangTypes.KW_FOREACH || i === DlangTypes.KW_FOREACH_REVERSE) {
            return parseForeachStatement()
        } else if (i === DlangTypes.KW_SWITCH) {
            return parseSwitchStatement()
        } else if (i === DlangTypes.KW_CONTINUE) {
            return parseContinueStatement()
        } else if (i === DlangTypes.KW_BREAK) {
            return parseBreakStatement()
        } else if (i === DlangTypes.KW_RETURN) {
            return parseReturnStatement()
        } else if (i === DlangTypes.KW_GOTO) {
            return parseGotoStatement()
        } else if (i === DlangTypes.KW_WITH) {
            return parseWithStatement()
        } else if (i === DlangTypes.KW_SYNCHRONIZED) {
            return parseSynchronizedStatement()
        } else if (i === DlangTypes.KW_TRY) {
            return parseTryStatement()
        } else if (i === DlangTypes.KW_SCOPE) {
            return if (peekIs(DlangTypes.OP_PAR_LEFT)) parseScopeGuardStatement()
            else parseDeclarationStatement()
        } else if (i === DlangTypes.KW_ASM) {
            return parseAsmStatement()
        } else if (i === DlangTypes.KW_PRAGMA) {
            return parsePragmaStatement()
        } else if (i === DlangTypes.KW_FINAL && peekIs(DlangTypes.KW_SWITCH)) {
            return parseFinalSwitchStatement()
        } else if (i === DlangTypes.KW_DEBUG) {
            if (peekIs(DlangTypes.OP_EQ)) {
                return parseDeclarationStatement()
            }
            return parseConditionalStatement()
        } else if (i === DlangTypes.KW_VERSION) {
            if (peekIs(DlangTypes.OP_EQ)) {
                // TODO for better error reporting `version = X;` in statement is an error
                //  the parsing should print a pretty error, specifying that they must be done at module level
                return false
            }
            return parseConditionalStatement()
        } else if (i === DlangTypes.KW_STATIC) {
            val next = builder.lookAhead(1)
            if (next === DlangTypes.KW_IF) {
                return parseConditionalStatement()
            } else if (next === DlangTypes.KW_ASSERT) {
                return parseStaticAssertStatement()
            } else if (next === DlangTypes.KW_FOREACH || next === DlangTypes.KW_FOREACH_REVERSE) {
                return parseStaticForeachStatement()
            } else if (next === DlangTypes.KW_IMPORT) {
                return parseImportDeclaration(builder.mark())
            } else {
                val m = builder.mark()
                if (parseDeclarationStatement()) {
                    m.drop()
                    return true
                }
                builder.advanceLexer()
                error("'if', 'assert', 'foreach', 'foreach_reverse' or 'import' expected.")
                m.rollbackTo()
                return false
            }
        } else if (i === DlangTypes.ID && peekIs(DlangTypes.OP_COLON)) {
            return parseLabeledStatement()
        } else if (i === DlangTypes.KW_IMPORT && !peekIs(DlangTypes.OP_PAR_LEFT)) {
            return parseImportDeclaration(builder.mark())
        } else {
            val marker = builder.mark()
            if (currentIs(DlangTypes.ID) && builder.lookAhead(1) === DlangTypes.OP_EQ) {
                // there can’t be alias reassignment at this level
                marker.drop()
                return parseExpressionStatement()
            }
            if (parseDeclarationStatement()) {
                marker.drop()
                return true
            } else {
                marker.rollbackTo()
                return parseExpressionStatement()
            }
        }
    }

    fun parseEmptyStatement(): Boolean {
        val marker = builder.mark()
        if (expect(DlangTypes.OP_SCOLON) == null) {
            marker.drop()
            return false
        }
        marker.done(DlangTypes.EMPTY_STATEMENT)
        return true
    }

    fun parseScopeBlockStatement(): Boolean {
        return parseBlockStatement()
    }

    fun parseScopeStatementList(): Boolean {
        while (!builder.eof()) {
            if (!parseStatementNoCaseNoDefault()) {
                break
            }
        }
        return true
    }

    fun parseStatementNoCaseNoDefault(): Boolean {
        val i = builder.tokenType
        return if (i === DlangTypes.OP_SCOLON) {
            parseEmptyStatement()
        } else if (i === DlangTypes.OP_BRACES_LEFT) {
            parseScopeBlockStatement()
        } else {
            parseNonEmptyStatementNoCaseNoDefault()
        }
    }

    fun parseNoScopeNonEmptyStatement(): Boolean {
        return if (builder.tokenType === DlangTypes.OP_BRACES_LEFT) {
            parseBlockStatement()
        } else {
            parseNonEmptyStatement()
        }
    }

    fun parseScopeStatement(): Boolean {
        return if (builder.tokenType === DlangTypes.OP_BRACES_LEFT) {
            parseScopeBlockStatement()
        } else {
            parseNonEmptyStatement()
        }
    }

    /**
     * Parses a StaticAssertDeclaration
     *
     *
     * $(GRAMMAR $(RULEDEF staticAssertDeclaration):
     * $(RULE staticAssertStatement)
     * ;)
     */
    fun parseStaticAssertDeclaration(marker: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_STATIC) {
            return false
        }
        val bookmark = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType !== DlangTypes.KW_ASSERT) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        if (parseAssertExpression() == null) {
            marker.done(DlangTypes.STATIC_ASSERT_DECLARATION)
            return true
        }
        expect(DlangTypes.OP_SCOLON)
        marker.done(DlangTypes.STATIC_ASSERT_DECLARATION)
        return true
    }

    /**
     * Parses a StaticAssertStatement
     *
     *
     * $(GRAMMAR $(RULEDEF staticAssertStatement):
     * $(LITERAL 'static') $(RULE assertExpression) $(LITERAL ';')
     * ;)
     */
    fun parseStaticAssertStatement(): Boolean {
        val marker = builder.mark()
        if (expect(DlangTypes.KW_STATIC) == null) {
            cleanup(marker, DlangTypes.STATIC_ASSERT_STATEMENT)
            return false
        }
        if (parseAssertExpression() == null) {
            cleanup(marker, DlangTypes.STATIC_ASSERT_STATEMENT)
            return false
        }
        if (expect(DlangTypes.OP_SCOLON) == null) {
            cleanup(marker, DlangTypes.STATIC_ASSERT_STATEMENT)
            return false
        }
        exit_section_modified(builder, marker, DlangTypes.STATIC_ASSERT_STATEMENT, true)
        return true
    }

    /**
     * Parses a StaticConstructor
     *
     *
     * $(GRAMMAR $(RULEDEF staticConstructor):
     * $(LITERAL 'static') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ";"))
     * ;)
     */
    fun parseStaticConstructor(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_STATIC) return false
        val bookmark = builder.mark()
        builder.advanceLexer()
        if (!parseStaticCtorDtorCommon()) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        m.done(DlangTypes.STATIC_CONSTRUCTOR)
        return true
    }

    /**
     * Parses a StaticDestructor
     *
     *
     * $(GRAMMAR $(RULEDEF staticDestructor):
     * $(LITERAL 'static') $(LITERAL '~') $(LITERAL 'this') $(LITERAL '$(LPAREN)') $(LITERAL '$(RPAREN)') $(RULE memberFunctionAttribute)* ($(RULE functionBody) | $(LITERAL ";"))
     * ;)
     */
    fun parseStaticDestructor(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_STATIC) return false
        val bookmark = builder.mark()
        builder.advanceLexer()
        if (!tokenCheck(DlangTypes.OP_TILDA)) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        parseStaticCtorDtorCommon()
        m.done(DlangTypes.STATIC_DESTRUCTOR)
        return true
    }

    /**
     * Parses an StaticIfCondition
     *
     *
     * $(GRAMMAR $(RULEDEF staticIfCondition):
     * $(LITERAL 'static') $(LITERAL 'if') $(LITERAL '$(LPAREN)') $(RULE assignExpression) $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseStaticIfCondition(): Boolean {
        val marker = builder.mark()
        if (expect(DlangTypes.KW_STATIC) == null) {
            cleanup(marker, DlangTypes.STATIC_IF_CONDITION)
            return false
        }
        if (expect(DlangTypes.KW_IF) == null) {
            cleanup(marker, DlangTypes.STATIC_IF_CONDITION)
            return false
        }
        if (expect(DlangTypes.OP_PAR_LEFT) == null) {
            cleanup(marker, DlangTypes.STATIC_IF_CONDITION)
            return false
        }
        if (parseAssignExpression() == null) {
            cleanup(marker, DlangTypes.STATIC_IF_CONDITION)
            return false
        }
        if (expect(DlangTypes.OP_PAR_RIGHT) == null) {
            cleanup(marker, DlangTypes.STATIC_IF_CONDITION)
            return false
        }
        exit_section_modified(builder, marker, DlangTypes.STATIC_IF_CONDITION, true)
        return true
    }

    /**
     * Parses a StorageClass
     *
     *
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
    fun parseStorageClass(): Boolean {
        val m = builder.mark()
        val i = current()
        if (i === DlangTypes.OP_AT) {
            if (!parseAtAttribute()) {
                cleanup(m, DlangTypes.STORAGE_CLASS)
                return false
            }
        } else if (i === DlangTypes.KW_DEPRECATED) {
            builder.advanceLexer()
            if (builder.tokenType === DlangTypes.OP_PAR_LEFT) {
                m.rollbackTo()
                return false
            }
        } else if (i === DlangTypes.KW_ALIGN) {
            if (!parseAlignAttribute()) {
                cleanup(m, DlangTypes.STORAGE_CLASS)
                return false
            }
        } else if (i === DlangTypes.KW_EXTERN) {
            if (peekIs(DlangTypes.OP_PAR_LEFT)) {
                if (!parseLinkageAttribute()) {
                    cleanup(m, DlangTypes.STORAGE_CLASS)
                    return false
                }
                exit_section_modified(builder, m, DlangTypes.STORAGE_CLASS, true)
                return true
            } else advance()
        } else if (i === DlangTypes.KW_CONST || i === DlangTypes.KW_IMMUTABLE || i === DlangTypes.KW_INOUT || i === DlangTypes.KW_SHARED || i === DlangTypes.KW_ABSTRACT || i === DlangTypes.KW_AUTO || i === DlangTypes.KW_ENUM || i === DlangTypes.KW_FINAL || i === DlangTypes.KW_NOTHROW || i === DlangTypes.KW_OVERRIDE || i === DlangTypes.KW_PURE || i === DlangTypes.KW_REF || i === DlangTypes.KW___GSHARED || i === DlangTypes.KW_SCOPE || i === DlangTypes.KW_STATIC || i === DlangTypes.KW_SYNCHRONIZED) {
            advance()
            if (builder.tokenType === DlangTypes.OP_PAR_LEFT) {
                m.rollbackTo()
                return false
            }
        } else {
            m.rollbackTo()
            return false
        }
        exit_section_modified(builder, m, DlangTypes.STORAGE_CLASS, true)
        return true
    }

    /**
     * Parses a StructBody
     *
     *
     * $(GRAMMAR $(RULEDEF structBody):
     * $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
     * ;)
     */
    fun parseStructBody(): Boolean {
        val m = builder.mark()
        expect(DlangTypes.OP_BRACES_LEFT)
        while (!builder.eof() && builder.tokenType !== DlangTypes.OP_BRACES_RIGHT) {
            if (!parseDeclDef()) {
                val recovery = builder.mark()
                while (!builder.eof() && builder.tokenType !== DlangTypes.OP_BRACES_RIGHT && builder.tokenType !== DlangTypes.OP_SCOLON) builder.advanceLexer()
                if (builder.tokenType === DlangTypes.OP_SCOLON) builder.advanceLexer()
                recovery.error("Unable to parse this declaration")
            }
        }
        expect(DlangTypes.OP_BRACES_RIGHT)
        m.done(DlangTypes.STRUCT_BODY)
        return true
    }

    /**
     * Parses a StructDeclaration
     *
     *
     * $(GRAMMAR $(RULEDEF structDeclaration):
     * $(LITERAL 'struct') $(LITERAL Identifier)? ($(RULE templateParameters) $(RULE raint)? $(RULE structBody) | ($(RULE structBody) | $(LITERAL ';')))
     * ;)
     */
    fun parseStructDeclaration(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_STRUCT) return false
        builder.advanceLexer()
        if (currentIs(DlangTypes.ID)) advance()
        if (currentIs(DlangTypes.OP_PAR_LEFT)) {
            if (!parseTemplateParameters()) {
                m.done(DlangTypes.STRUCT_DECLARATION)
                return true
            }
            if (currentIs(DlangTypes.KW_IF)) if (!parseConstraint()) {
                m.done(DlangTypes.STRUCT_DECLARATION)
                return true
            }
        }
        if (currentIs(DlangTypes.OP_BRACES_LEFT)) {
            if (!parseStructBody()) {
                m.done(DlangTypes.STRUCT_DECLARATION)
                return true
            }
        } else if (currentIs(DlangTypes.OP_SCOLON)) advance()
        else {
            error("Template Parameters, Struct Body, or Semicolon expected")
        }
        m.done(DlangTypes.STRUCT_DECLARATION)
        return true
    }

    /**
     * Parses an StructInitializer
     *
     *
     * $(GRAMMAR $(RULEDEF structInitializer):
     * $(LITERAL '{') $(RULE structMemberInitializers)? $(LITERAL '}')
     * ;)
     */
    fun parseStructInitializer(): Boolean {
        val m = builder.mark()
        expect(DlangTypes.OP_BRACES_LEFT)
        if (currentIs(DlangTypes.OP_BRACES_RIGHT)) {
            advance()
        } else {
            if (!parseStructMemberInitializers()) {
                cleanup(m, DlangTypes.STRUCT_INITIALIZER)
                return false
            }
            val e = expect(DlangTypes.OP_BRACES_RIGHT)
            if (e == null) {
                cleanup(m, DlangTypes.STRUCT_INITIALIZER)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.STRUCT_INITIALIZER, true)
        return true
    }

    /**
     * Parses a StructMemberInitializer
     *
     *
     * $(GRAMMAR $(RULEDEF structMemberInitializer):
     * ($(LITERAL Identifier) $(LITERAL ':'))? $(RULE nonVoidInitializer)
     * ;)
     */
    fun parseStructMemberInitializer(): Boolean {
        val m = builder.mark()
        if (startsWith(DlangTypes.ID, DlangTypes.OP_COLON)) {
            advance()
            advance()
        }
        if (!parseNonVoidInitializer()) {
            cleanup(m, DlangTypes.STRUCT_MEMBER_INITIALIZER)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.STRUCT_MEMBER_INITIALIZER, true)
        return true
    }

    /**
     * Parses StructMemberInitializers
     *
     *
     * $(GRAMMAR $(RULEDEF structMemberInitializers):
     * $(RULE structMemberInitializer) ($(LITERAL ',') $(RULE structMemberInitializer)?)*
     * ;)
     */
    fun parseStructMemberInitializers(): Boolean {
        val m = builder.mark()
        do {
            parseStructMemberInitializer()
            if (currentIs(DlangTypes.OP_COMMA)) advance()
            else break
        } while (moreTokens() && !currentIs(DlangTypes.OP_BRACES_RIGHT))
        exit_section_modified(builder, m, DlangTypes.STRUCT_MEMBER_INITIALIZERS, true)
        return true
    }

    /**
     * Parses a SwitchStatement
     *
     *
     * $(GRAMMAR $(RULEDEF switchStatement):
     * $(LITERAL 'switch') $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE statement)
     * ;)
     */
    fun parseSwitchStatement(): Boolean {
        val m = builder.mark()
        expect(DlangTypes.KW_SWITCH)
        expect(DlangTypes.OP_PAR_LEFT)
        if (!parseExpression()) {
            cleanup(m, DlangTypes.SWITCH_STATEMENT)
            return false
        }
        expect(DlangTypes.OP_PAR_RIGHT)
        if (!parseNonEmptyStatement()) {
            cleanup(m, DlangTypes.SWITCH_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.SWITCH_STATEMENT, true)
        return true
    }

    /**
     * Parses a SynchronizedStatement
     *
     *
     * $(GRAMMAR $(RULEDEF synchronizedStatement):
     * $(LITERAL 'synchronized') ($(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)'))? $(RULE scopeStatement)
     * ;)
     */
    fun parseSynchronizedStatement(): Boolean {
        val m = builder.mark()
        expect(DlangTypes.KW_SYNCHRONIZED)
        if (currentIs(DlangTypes.OP_PAR_LEFT)) {
            expect(DlangTypes.OP_PAR_LEFT)
            if (!parseExpression()) {
                cleanup(m, DlangTypes.SYNCHRONIZED_STATEMENT)
                return false
            }
            expect(DlangTypes.OP_PAR_RIGHT)
        }
        if (!parseScopeStatement()) {
            cleanup(m, DlangTypes.SYNCHRONIZED_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.SYNCHRONIZED_STATEMENT, true)
        return true
    }

    /**
     * Parses a TemplateAliasParameter
     *
     *
     * $(GRAMMAR $(RULEDEF templateAliasParameter):
     * $(LITERAL 'alias') $(RULE type)? $(LITERAL Identifier) ($(LITERAL ':') ($(RULE type) | $(RULE assignExpression)))? ($(LITERAL '=') ($(RULE type) | $(RULE assignExpression)))?
     * ;)
     */
    fun parseTemplateAliasParameter(): Boolean {
        val m = builder.mark()
        expect(DlangTypes.KW_ALIAS)
        if (currentIs(DlangTypes.ID) && !peekIs(DlangTypes.OP_DOT)) {
            if (peekIsOneOf(
                    DlangTypes.OP_COMMA,
                    DlangTypes.OP_PAR_RIGHT,
                    DlangTypes.OP_EQ,
                    DlangTypes.OP_COLON
                )
            ) advance()
            else {
                if (!parseType()) {
                    cleanup(m, DlangTypes.TEMPLATE_ALIAS_PARAMETER)
                    return false
                }
                val ident = expect(DlangTypes.ID)
                if (ident == null) {
                    cleanup(m, DlangTypes.TEMPLATE_ALIAS_PARAMETER)
                    return false
                }
            }
        } else {
            if (!parseType()) {
                cleanup(m, DlangTypes.TEMPLATE_ALIAS_PARAMETER)
                return false
            }
            val ident = expect(DlangTypes.ID)
            if (ident == null) {
                cleanup(m, DlangTypes.TEMPLATE_ALIAS_PARAMETER)
                return false
            }
        }

        if (currentIs(DlangTypes.OP_COLON)) {
            advance()
            if (isType) {
                if (!parseType()) {
                    cleanup(m, DlangTypes.TEMPLATE_ALIAS_PARAMETER)
                    return false
                }
            } else if (parseAssignExpression() == null) {
                cleanup(m, DlangTypes.TEMPLATE_ALIAS_PARAMETER)
                return false
            }
        }
        if (currentIs(DlangTypes.OP_EQ)) {
            advance()
            if (isType) {
                if (!parseType()) {
                    cleanup(m, DlangTypes.TEMPLATE_ALIAS_PARAMETER)
                    return false
                }
            } else if (parseAssignExpression() == null) {
                cleanup(m, DlangTypes.TEMPLATE_ALIAS_PARAMETER)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.TEMPLATE_ALIAS_PARAMETER, true)
        return true
    }

    /**
     * Parses a TemplateArgument
     *
     *
     * $(GRAMMAR $(RULEDEF templateArgument):
     * $(RULE type)
     * | $(RULE assignExpression)
     * ;)
     */
    fun parseTemplateArgument(): Boolean {
        val m = builder.mark()
        val startIndex = builder.currentOffset
        val p = cachedTypedChecks.containsKey(startIndex)
        if (p) {
            if (cachedTypedChecks[startIndex]!!) {
                parseType()
            } else {
                if (parseAssignExpression() == null) {
                    cleanup(m, DlangTypes.TEMPLATE_ARGUMENT)
                    return false
                }
            }
        } else {
            val bookmark = builder.mark()
            val t = parseType()
            if (t && currentIsOneOf(DlangTypes.OP_COMMA, DlangTypes.OP_PAR_RIGHT)) {
                cachedTypedChecks[startIndex] = true
                bookmark.drop()
            } else {
                cachedTypedChecks[startIndex] = false
                bookmark.rollbackTo()
                if (parseAssignExpression() == null) {
                    cleanup(m, DlangTypes.TEMPLATE_ARGUMENT)
                    return false
                }
            }
        }
        exit_section_modified(builder, m, DlangTypes.TEMPLATE_ARGUMENT, true)
        return true
    }

    /**
     * Parses a TemplateArgumentList
     *
     *
     * $(GRAMMAR $(RULEDEF templateArgumentList):
     * $(RULE templateArgument) ($(LITERAL ',') $(RULE templateArgument)?)*
     * ;)
     */
    fun parseTemplateArgumentList(): Boolean {
        val marker = builder.mark()
        while (moreTokens()) {
            if (!parseTemplateArgument()) {
                cleanup(marker, DlangTypes.TEMPLATE_ARGUMENT_LIST)
                return false
            }
            if (currentIs(DlangTypes.OP_COMMA)) {
                advance()
                if (currentIsOneOf(DlangTypes.OP_PAR_RIGHT)) break
            } else break
        }
        exit_section_modified(builder, marker, DlangTypes.TEMPLATE_ARGUMENT_LIST, true)
        return true
    }

    /**
     * Parses TemplateArguments
     *
     *
     * $(GRAMMAR $(RULEDEF templateArguments):
     * $(LITERAL '!') ($(LITERAL '$(LPAREN)') $(RULE templateArgumentList)? $(LITERAL '$(RPAREN)')) | $(RULE templateSingleArgument)
     * ;)
     */
    fun parseTemplateArguments(): Boolean {
        val m = builder.mark()
        expect(DlangTypes.OP_NOT)
        if (currentIs(DlangTypes.OP_PAR_LEFT)) {
            advance()
            if (!currentIs(DlangTypes.OP_PAR_RIGHT)) if (!parseTemplateArgumentList()) {
                cleanup(m, DlangTypes.TEMPLATE_ARGUMENTS)
                return false
            }
            if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
                cleanup(m, DlangTypes.TEMPLATE_ARGUMENTS)
                return false
            }
        } else if (!parseTemplateSingleArgument()) {
            cleanup(m, DlangTypes.TEMPLATE_ARGUMENTS)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.TEMPLATE_ARGUMENTS, true)
        return true
    }

    /**
     * Parses a TemplateDeclaration
     *
     *
     * $(GRAMMAR $(RULEDEF templateDeclaration):
     * $(LITERAL 'template') $(LITERAL Identifier) $(RULE templateParameters) $(RULE raint)? $(LITERAL '{') $(RULE declaration)* $(LITERAL '}')
     * ;)
     */
    fun parseTemplateDeclaration(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_TEMPLATE) return false
        val bookmark = builder.mark()
        if (!parseTemplateDeclarationCommon()) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        m.done(DlangTypes.TEMPLATE_DECLARATION)
        return true
    }

    private fun parseTemplateDeclarationCommon(): Boolean {
        assert(builder.tokenType === DlangTypes.KW_TEMPLATE)
        builder.advanceLexer()
        if (builder.tokenType !== DlangTypes.ID) {
            return false
        }
        advance()
        if (!parseTemplateParameters()) {
            return true
        }
        if (currentIs(DlangTypes.KW_IF)) if (!parseConstraint()) {
            return true
        }
        expect(DlangTypes.OP_BRACES_LEFT) ?: return true
        while (!builder.eof() && builder.tokenType !== DlangTypes.OP_BRACES_RIGHT) {
            if (!parseDeclDef()) {
                val recovery = builder.mark()
                while (!builder.eof() && builder.tokenType !== DlangTypes.OP_BRACES_RIGHT && builder.tokenType !== DlangTypes.OP_SCOLON) builder.advanceLexer()
                if (builder.tokenType === DlangTypes.OP_SCOLON) builder.advanceLexer()
                recovery.error("Unable to parse this declaration")
            }
        }
        expect(DlangTypes.OP_BRACES_RIGHT)
        return true
    }

    /**
     * Parses a TemplateInstance
     *
     *
     * $(GRAMMAR $(RULEDEF templateInstance):
     * $(LITERAL Identifier) $(RULE templateArguments)
     * ;)
     */
    fun parseTemplateInstance(): Boolean {
        val m = builder.mark()
        val ident = expect(DlangTypes.ID)
        if (ident == null) {
            cleanup(m, DlangTypes.TEMPLATE_INSTANCE)
            return false
        }
        if (!currentIs(DlangTypes.OP_NOT)) {
            cleanup(m, DlangTypes.TEMPLATE_INSTANCE)
            return false
        }
        if (!parseTemplateArguments()) {
            cleanup(m, DlangTypes.TEMPLATE_INSTANCE)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.TEMPLATE_INSTANCE, true)
        return true
    }

    /**
     * Parses a TemplateMixin
     *
     *
     * $(GRAMMAR $(RULEDEF templateMixinExpression):
     * $(LITERAL 'mixin') $(RULE mixinTemplateName) $(RULE templateArguments)? $(LITERAL Identifier)?
     * ;)
     */
    fun parseTemplateMixin(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_MIXIN) return false
        val bookmark = builder.mark()
        builder.advanceLexer()
        if (!parseMixinTemplateName()) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        if (currentIs(DlangTypes.OP_NOT)) {
            if (!parseTemplateArguments()) {
                m.done(DlangTypes.TEMPLATE_MIXIN)
                return true
            }
        }
        if (currentIs(DlangTypes.ID)) advance()
        expect(DlangTypes.OP_SCOLON)
        m.done(DlangTypes.TEMPLATE_MIXIN)
        return true
    }

    /**
     * Parses a TemplateParameter
     *
     *
     * $(GRAMMAR $(RULEDEF templateParameter):
     * $(RULE templateTypeParameter)
     * | $(RULE templateValueParameter)
     * | $(RULE templateAliasParameter)
     * | $(RULE templateTupleParameter)
     * | $(RULE templateThisParameter)
     * ;)
     */
    fun parseTemplateParameter(): Boolean {
        val i = current()
        if (i === DlangTypes.KW_ALIAS) {
            return parseTemplateAliasParameter();
        } else if (i === DlangTypes.KW_THIS) {
            return parseTemplateThisParameter();
        } else if (i === DlangTypes.ID) {
            if (peekIs(DlangTypes.OP_TRIPLEDOT)) {
                return parseTemplateTupleParameter();
            } else if (peekIsOneOf(
                    DlangTypes.OP_COLON,
                    DlangTypes.OP_EQ,
                    DlangTypes.OP_COMMA,
                    DlangTypes.OP_PAR_RIGHT
                )
            ) {
                return parseTemplateTypeParameter();
            }
        }
        return parseTemplateValueParameter();
    }

    /**
     * Parses a TemplateParameterList
     *
     *
     * $(GRAMMAR $(RULEDEF templateParameterList):
     * $(RULE templateParameter) ($(LITERAL ',') $(RULE templateParameter)?)* $(LITERAL ',')?
     * ;)
     */
    fun parseTemplateParameterList(): Boolean {
        val marker = builder.mark()
        while (moreTokens()) {
            if (!parseTemplateParameter()) {
                cleanup(marker, DlangTypes.TEMPLATE_PARAMETER_LIST)
                return false
            }
            if (currentIs(DlangTypes.OP_COMMA)) {
                advance()
                if (currentIsOneOf(
                        DlangTypes.OP_PAR_RIGHT,
                        DlangTypes.OP_BRACES_RIGHT,
                        DlangTypes.OP_BRACKET_RIGHT
                    )
                ) break
            } else break
        }
        exit_section_modified(builder, marker, DlangTypes.TEMPLATE_PARAMETER_LIST, true)
        return true
    }

    /**
     * Parses TemplateParameters
     *
     *
     * $(GRAMMAR $(RULEDEF templateParameters):
     * $(LITERAL '$(LPAREN)') $(RULE templateParameterList)? $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseTemplateParameters(): Boolean {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, DlangTypes.TEMPLATE_PARAMETERS)
            return false
        }
        if (!currentIs(DlangTypes.OP_PAR_RIGHT)) if (!parseTemplateParameterList()) {
            cleanup(m, DlangTypes.TEMPLATE_PARAMETERS)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            cleanup(m, DlangTypes.TEMPLATE_PARAMETERS)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.TEMPLATE_PARAMETERS, true)
        return true
    }

    /**
     * Parses a TemplateSingleArgument
     *
     *
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
    fun parseTemplateSingleArgument(): Boolean {
        val m = builder.mark()
        if (!moreTokens()) {
            error("template argument expected instead of EOF")
            exit_section_modified(builder, m, DlangTypes.TEMPLATE_SINGLE_ARGUMENT, true)
            return false
        }
        val i = current()
        if (i === DlangTypes.KW_THIS || i === DlangTypes.ID || isLiteral(i)) {
            advance()
        } else if (isBasicType(i)) {
            parseBuiltinType()
        } else {
            error("Invalid template argument. (Try enclosing in parenthesis?)")
            exit_section_modified(builder, m, DlangTypes.TEMPLATE_SINGLE_ARGUMENT, true)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.TEMPLATE_SINGLE_ARGUMENT, true)
        return true
    }

    /**
     * Parses a TemplateThisParameter
     *
     *
     * $(GRAMMAR $(RULEDEF templateThisParameter):
     * $(LITERAL 'this') $(RULE templateTypeParameter)
     * ;)
     */
    fun parseTemplateThisParameter(): Boolean {
        val m = builder.mark()
        expect(DlangTypes.KW_THIS)
        if (!parseTemplateTypeParameter()) {
            cleanup(m, DlangTypes.TEMPLATE_THIS_PARAMETER)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.TEMPLATE_THIS_PARAMETER, true)
        return true
    }

    /**
     * Parses an TemplateTupleParameter
     *
     *
     * $(GRAMMAR $(RULEDEF templateTupleParameter):
     * $(LITERAL Identifier) $(LITERAL '...')
     * ;)
     */
    fun parseTemplateTupleParameter(): Boolean {
        val m = builder.mark()
        val i = expect(DlangTypes.ID)
        if (i == null) {
            cleanup(m, DlangTypes.TEMPLATE_TUPLE_PARAMETER)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_TRIPLEDOT)) {
            cleanup(m, DlangTypes.TEMPLATE_TUPLE_PARAMETER)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.TEMPLATE_TUPLE_PARAMETER, true)
        return true
    }

    /**
     * Parses a TemplateTypeParameter
     *
     *
     * $(GRAMMAR $(RULEDEF templateTypeParameter):
     * $(LITERAL Identifier) ($(LITERAL ':') $(RULE type))? ($(LITERAL '=') $(RULE type))?
     * ;)
     */
    fun parseTemplateTypeParameter(): Boolean {
        val m = builder.mark()
        val ident = expect(DlangTypes.ID)
        if (ident == null) {
            cleanup(m, DlangTypes.TEMPLATE_TYPE_PARAMETER)
            return false
        }
        if (currentIs(DlangTypes.OP_COLON)) {
            advance()
            if (!parseType()) {
                cleanup(m, DlangTypes.TEMPLATE_TYPE_PARAMETER)
                return false
            }
        }
        if (currentIs(DlangTypes.OP_EQ)) {
            advance()
            if (!parseType()) {
                cleanup(m, DlangTypes.TEMPLATE_TYPE_PARAMETER)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.TEMPLATE_TYPE_PARAMETER, true)
        return true
    }

    /**
     * Parses a TemplateValueParameter
     *
     *
     * $(GRAMMAR $(RULEDEF templateValueParameter):
     * $(RULE type) $(LITERAL Identifier) ($(LITERAL ':') $(RULE assignExpression))? $(RULE templateValueParameterDefault)?
     * ;)
     */
    fun parseTemplateValueParameter(): Boolean {
        val m = builder.mark()
        if (!parseType()) {
            cleanup(m, DlangTypes.TEMPLATE_VALUE_PARAMETER)
            return false
        }
        if (!tokenCheck(DlangTypes.ID)) {
            cleanup(m, DlangTypes.TEMPLATE_VALUE_PARAMETER)
            return false
        }
        if (currentIs(DlangTypes.OP_COLON)) {
            advance()
            if (parseAssignExpression() == null) {
                cleanup(m, DlangTypes.TEMPLATE_VALUE_PARAMETER)
                return false
            }
        }
        if (currentIs(DlangTypes.OP_EQ)) {
            if (!parseTemplateValueParameterDefault()) {
                cleanup(m, DlangTypes.TEMPLATE_VALUE_PARAMETER)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.TEMPLATE_VALUE_PARAMETER, true)
        return true
    }

    /**
     * Parses a TemplateValueParameterDefault
     *
     *
     * $(GRAMMAR $(RULEDEF templateValueParameterDefault):
     * $(LITERAL '=') $(LITERAL '___DATE__')
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
    fun parseTemplateValueParameterDefault(): Boolean {
        val m = builder.mark()
        expect(DlangTypes.OP_EQ)
        val i = current()
        if (isMagicKeywordLiteral(i)) {
            advance()
        } else {
            if (parseAssignExpression() == null) {
                cleanup(m, DlangTypes.TEMPLATE_VALUE_PARAMETER_DEFAULT)
                return false
            }
        }
        exit_section_modified(builder, m, DlangTypes.TEMPLATE_VALUE_PARAMETER_DEFAULT, true)
        return true
    }

    fun isMagicKeywordLiteral(i: IElementType?): Boolean {
        return i === DlangTypes.KW___FILE__ || i === DlangTypes.KW___FILE_FULL_PATH__ || i === DlangTypes.KW___MODULE__ || i === DlangTypes.KW___LINE__ || i === DlangTypes.KW___FUNCTION__ || i === DlangTypes.KW___PRETTY_FUNCTION__
    }

    /**
     * Parses a TernaryExpression (called ConditionalExpression in specs)
     *
     *
     * $(GRAMMAR $(RULEDEF ternaryExpression):
     * $(RULE orOrExpression) ($(LITERAL '?') $(RULE expression) $(LITERAL ':') $(RULE ternaryExpression))?
     * ;)
     */
    fun parseTernaryExpression(): PsiBuilder.Marker? {
        val orOrExpression = parseOrOrExpression() ?: return null
        if (currentIs(DlangTypes.OP_QUEST)) {
            val m = orOrExpression.precede()
            advance()
            if (!parseExpression()) {
                cleanup(m, DlangTypes.TERNARY_EXPRESSION)
                return null
            }
            val colon = expect(DlangTypes.OP_COLON)
            if (colon == null) {
                cleanup(m, DlangTypes.TERNARY_EXPRESSION)
                return null
            }
            if (parseTernaryExpression() == null) {
                cleanup(m, DlangTypes.TERNARY_EXPRESSION)
                return null
            }
            exit_section_modified(builder, m, DlangTypes.TERNARY_EXPRESSION, true)
            return m
        }
        return orOrExpression
    }

    /**
     * Parses a ThrowExpression
     *
     *
     * $(GRAMMAR $(RULEDEF throwExpression):
     * $(LITERAL 'throw') $(RULE assignExpression)
     * ;)
     */
    fun parseThrowExpression(): PsiBuilder.Marker? {
        val m = builder.mark()
        expect(DlangTypes.KW_THROW)
        if (parseAssignExpression() == null) {
            cleanup(m, DlangTypes.THROW_EXPRESSION)
            return null
        }
        exit_section_modified(builder, m, DlangTypes.THROW_EXPRESSION, true)
        return m
    }

    /**
     * Parses an TraitsExpression
     *
     *
     * $(GRAMMAR $(RULEDEF traitsExpression):
     * $(LITERAL '___traits') $(LITERAL '$(LPAREN)') $(LITERAL Identifier) $(LITERAL ',') $(RULE TemplateArgumentList) $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseTraitsExpression(): PsiBuilder.Marker? {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.KW___TRAITS)) {
            cleanup(m, DlangTypes.TRAITS_EXPRESSION)
            return null
        }
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, DlangTypes.TRAITS_EXPRESSION)
            return null
        }
        val ident = expect(DlangTypes.ID)
        if (ident == null) {
            cleanup(m, DlangTypes.TRAITS_EXPRESSION)
            return null
        }
        if (currentIs(DlangTypes.OP_COMMA)) {
            advance()
            if (!(parseTemplateArgumentList())) {
                cleanup(m, DlangTypes.TRAITS_EXPRESSION)
                return null
            }
        }
        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            cleanup(m, DlangTypes.TRAITS_EXPRESSION)
            return null
        }
        exit_section_modified(builder, m, DlangTypes.TRAITS_EXPRESSION, true)
        return m
    }

    /**
     * Parses a TryStatement
     *
     *
     * $(GRAMMAR $(RULEDEF tryStatement):
     * $(LITERAL 'try') $(RULE scopeStatement) ($(RULE catches) | $(RULE catches) $(RULE finally) | $(RULE finally))
     * ;)
     */
    fun parseTryStatement(): Boolean {
        val m = builder.mark()
        expect(DlangTypes.KW_TRY)
        if (!parseScopeStatement()) {
            cleanup(m, DlangTypes.TRY_STATEMENT)
            return false
        }
        if (currentIs(DlangTypes.KW_CATCH)) if (!parseCatches()) {
            cleanup(m, DlangTypes.TRY_STATEMENT)
            return false
        }
        if (currentIs(DlangTypes.KW_FINALLY)) if (!parseFinally()) {
            cleanup(m, DlangTypes.TRY_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.TRY_STATEMENT, true)
        return true
    }

    /**
     * Parses a Type
     *
     *
     * $(GRAMMAR $(RULEDEF type):
     * $(RULE typeConstructors)? $(RULE type2) $(RULE typeSuffix)*
     * ;)
     */
    @JvmOverloads
    fun parseType(inNewExpression: Boolean = false): Boolean {
        val m = builder.mark()
        if (!moreTokens()) {
            error("type expected")
            exit_section_modified(builder, m, DlangTypes.TYPE, true)
            return false
        }
        val i = current()
        if (isTypeCtor(i)) {
            if (!peekIs(DlangTypes.OP_PAR_LEFT)) if (!parseTypeConstructors()) {
                cleanup(m, DlangTypes.TYPE)
                return false
            }
        }
        if (!parseBasicType()) {
            cleanup(m, DlangTypes.TYPE)
            return false
        }
        while (moreTokens()) {
            val i1 = current()

            if (i1 === DlangTypes.OP_BRACKET_LEFT) {
                val bookmark = builder.mark()
                if (inNewExpression) {
                    builder.advanceLexer()
                    if (parseAssignExpression() != null && builder.tokenType === DlangTypes.OP_BRACES_RIGHT) {
                        builder.advanceLexer()
                        if (builder.tokenType !== DlangTypes.OP_BRACKET_LEFT && builder.tokenType !== DlangTypes.OP_ASTERISK && builder.tokenType !== DlangTypes.KW_DELEGATE && builder.tokenType !== DlangTypes.KW_FUNCTION && builder.tokenType !== DlangTypes.OP_PAR_LEFT) {
                            // This one is not a type prefix but part of the parent newExpression
                            bookmark.rollbackTo()
                            break
                        }
                    }
                }
                bookmark.rollbackTo()
                if (!parseTypeSuffix()) {
                    cleanup(m, DlangTypes.TYPE)
                    return false
                }
            } else if (i1 === DlangTypes.OP_ASTERISK || i1 === DlangTypes.KW_DELEGATE || i1 === DlangTypes.KW_FUNCTION) {
                if (!parseTypeSuffix()) {
                    cleanup(m, DlangTypes.TYPE)
                    return false
                }
            } else {
                break
            }
        }
        exit_section_modified(builder, m, DlangTypes.TYPE, true)
        return true
    }

    fun parseTypeSuffixes() {
        while (!builder.eof()) {
            if (!parseTypeSuffix()) break
        }
    }

    /**
     * Parses a BasicType
     *
     *
     * $(GRAMMAR $(RULEDEF basicType):
     * $(RULE builtinType)
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
    fun parseBasicType(): Boolean {
        val m = builder.mark()
        if (!moreTokens()) {
            error("basic type expected instead of EOF")
            exit_section_modified(builder, m, DlangTypes.BASIC_TYPE, true)
            return false
        }
        if (builder.tokenType === DlangTypes.OP_DOT) {
            builder.advanceLexer()
        }
        val i = builder.tokenType
        if (i === DlangTypes.ID) {
            if (!parseQualifiedIdentifier()) {
                cleanup(m, DlangTypes.BASIC_TYPE)
                return false
            }
        } else if (isBasicType(i)) {
            parseBuiltinType()
        } else if (i === DlangTypes.KW_SUPER || i === DlangTypes.KW_THIS) {
            // note: super can be removed but `this` can be an alias to an instance.
            advance()
            if (builder.tokenType !== DlangTypes.OP_DOT) {
                cleanup(m, DlangTypes.BASIC_TYPE)
                return false
            }
            advance()
            if (!parseQualifiedIdentifier()) {
                cleanup(m, DlangTypes.BASIC_TYPE)
                return false
            }
        } else if (i === DlangTypes.KW___TRAITS) {
            if (parseTraitsExpression() != null) {
                cleanup(m, DlangTypes.BASIC_TYPE)
                return false
            }
        } else if (i === DlangTypes.KW_TYPEOF) {
            if (parseTypeofExpression() == null) {
                cleanup(m, DlangTypes.BASIC_TYPE)
                return false
            }
            if (currentIs(DlangTypes.OP_DOT)) {
                advance()
                if (!parseQualifiedIdentifier()) {
                    cleanup(m, DlangTypes.BASIC_TYPE)
                    return false
                }
            }
        } else if (i === DlangTypes.KW_MIXIN) {
            if (parseMixinType() != null) {
                cleanup(m, DlangTypes.BASIC_TYPE)
                return false
            }
        } else if (i === DlangTypes.KW_CONST || i === DlangTypes.KW_IMMUTABLE || i === DlangTypes.KW_INOUT || i === DlangTypes.KW_SHARED) {
            advance()
            if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
                cleanup(m, DlangTypes.BASIC_TYPE)
                return false
            }
            if (!(parseType())) {
                cleanup(m, DlangTypes.BASIC_TYPE)
                return false
            }
            if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
                cleanup(m, DlangTypes.BASIC_TYPE)
                return false
            }
        } else if (i === DlangTypes.KW___VECTOR) {
            if (!(parseVector())) {
                cleanup(m, DlangTypes.BASIC_TYPE)
                return false
            }
        } else {
            error("Basic type, type constructor, symbol, `typeof`, `__traits`, `__vector` or `mixin` expected")
            exit_section_modified(builder, m, DlangTypes.BASIC_TYPE, true)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.BASIC_TYPE, true)
        return true
    }

    /**
     * Parses a TypeConstructor
     *
     *
     * $(GRAMMAR $(RULEDEF typeConstructor):
     * $(LITERAL 'const')
     * | $(LITERAL 'immutable')
     * | $(LITERAL 'inout')
     * | $(LITERAL 'shared')
     * ;)
     */
    fun parseTypeConstructor(): Boolean {
        val i = current()
        if (i === DlangTypes.KW_CONST || i === DlangTypes.KW_IMMUTABLE || i === DlangTypes.KW_INOUT || i === DlangTypes.KW_SHARED) {
            if (!peekIs(DlangTypes.OP_PAR_LEFT)) {
                advance()
                return true
            }
        }
        return false
    }

    /**
     * Parses TypeConstructors
     *
     *
     * $(GRAMMAR $(RULEDEF typeConstructors):
     * $(RULE typeConstructor)+
     * ;)
     */
    fun parseTypeConstructors(): Boolean {
        var containsConstructors = false
        while (moreTokens()) {
            if (!parseTypeConstructor()) break
            containsConstructors = true
        }
        return containsConstructors
    }

    /**
     * Parses a TypeSpecialization
     *
     *
     * $(GRAMMAR $(RULEDEF typeSpecialization):
     * $(RULE type)
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
    fun parseTypeSpecialization(): Boolean {
        val m = builder.mark()
        val i = current()
        if (i === DlangTypes.KW_STRUCT || i === DlangTypes.KW_UNION || i === DlangTypes.KW_CLASS || i === DlangTypes.KW_INTERFACE || i === DlangTypes.KW___VECTOR || i === DlangTypes.KW_ENUM || i === DlangTypes.KW_FUNCTION || i === DlangTypes.KW_DELEGATE || i === DlangTypes.KW_SUPER || i === DlangTypes.KW_CONST || i === DlangTypes.KW_IMMUTABLE || i === DlangTypes.KW_INOUT || i === DlangTypes.KW_SHARED || i === DlangTypes.KW_RETURN || i === DlangTypes.KW___PARAMETERS || i === DlangTypes.KW_MODULE || i === DlangTypes.KW_PACKAGE) {
            if (peekIsOneOf(DlangTypes.OP_PAR_RIGHT, DlangTypes.OP_COMMA)) {
                advance()
                exit_section_modified(builder, m, DlangTypes.TYPE_SPECIALIZATION, true)
                return true
            }
            if (!parseType()) {
                cleanup(m, DlangTypes.TYPE_SPECIALIZATION)
                return false
            }
        } else if (!parseType()) {
            cleanup(m, DlangTypes.TYPE_SPECIALIZATION)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.TYPE_SPECIALIZATION, true)
        return true
    }

    /**
     * Parses a TypeSuffix
     *
     *
     * $(GRAMMAR $(RULEDEF typeSuffix):
     * $(LITERAL '*')
     * | $(LITERAL '[') $(RULE type)? $(LITERAL ']')
     * | $(LITERAL '[') $(RULE assignExpression) $(LITERAL ']')
     * | $(LITERAL '[') $(RULE assignExpression) $(LITERAL '..')  $(RULE assignExpression) $(LITERAL ']')
     * | ($(LITERAL 'delegate') | $(LITERAL 'function')) $(RULE parameters) $(RULE memberFunctionAttribute)*
     * ;)
     */
    fun parseTypeSuffix(): Boolean {
        val m = builder.mark()
        val i = current()
        if (i === DlangTypes.OP_ASTERISK) {
            advance()
            exit_section_modified(builder, m, DlangTypes.TYPE_SUFFIX, true)
            return true
        } else if (i === DlangTypes.OP_BRACKET_LEFT) {
            advance()
            if (currentIs(DlangTypes.OP_BRACKET_RIGHT)) {
                advance()
                exit_section_modified(builder, m, DlangTypes.TYPE_SUFFIX, true)
                return true
            }
            val bookmark = builder.mark()
            val type = parseType()
            if (type && currentIs(DlangTypes.OP_BRACKET_RIGHT)) {
                bookmark.drop()
            } else {
                bookmark.rollbackTo()
                if (parseAssignExpression() == null) {
                    cleanup(m, DlangTypes.TYPE_SUFFIX)
                    return false
                }
                if (currentIs(DlangTypes.OP_DDOT)) {
                    advance()
                    if (parseAssignExpression() == null) {
                        cleanup(m, DlangTypes.TYPE_SUFFIX)
                        return false
                    }
                }
            }
            if (!tokenCheck(DlangTypes.OP_BRACKET_RIGHT)) {
                cleanup(m, DlangTypes.TYPE_SUFFIX)
                return false
            }
            exit_section_modified(builder, m, DlangTypes.TYPE_SUFFIX, true)
            return true
        } else if (i === DlangTypes.KW_DELEGATE || i === DlangTypes.KW_FUNCTION) {
            advance()
            if (!parseParameters()) {
                cleanup(m, DlangTypes.TYPE_SUFFIX)
                return false
            }
            while (currentIsMemberFunctionAttribute()) if (!parseMemberFunctionAttribute()) {
                cleanup(m, DlangTypes.TYPE_SUFFIX)
                return false
            }
            exit_section_modified(builder, m, DlangTypes.TYPE_SUFFIX, true)
            return true
        } else {
            m.drop()
            return false
        }
    }

    /**
     * Parses a TypeidExpression
     *
     *
     * $(GRAMMAR $(RULEDEF typeidExpression):
     * $(LITERAL 'typeid') $(LITERAL '$(LPAREN)') ($(RULE type) | $(RULE expression)) $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseTypeidExpression(): PsiBuilder.Marker? {
        val m = builder.mark()
        expect(DlangTypes.KW_TYPEID)
        expect(DlangTypes.OP_PAR_LEFT)
        val bookmark = builder.mark()
        val t = parseType()
        if (!t || !currentIs(DlangTypes.OP_PAR_RIGHT)) {
            bookmark.rollbackTo()
            if (!parseExpression()) {
                cleanup(m, DlangTypes.TYPEID_EXPRESSION)
                return null
            }
        } else {
            bookmark.drop()
        }
        expect(DlangTypes.OP_PAR_RIGHT)
        exit_section_modified(builder, m, DlangTypes.TYPEID_EXPRESSION, true)
        return m
    }

    /**
     * Parses a TypeofExpression
     *
     *
     * $(GRAMMAR $(RULEDEF typeofExpression):
     * $(LITERAL 'typeof') $(LITERAL '$(LPAREN)') ($(RULE expression) | $(LITERAL 'return')) $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseTypeofExpression(): PsiBuilder.Marker? {
        val m = builder.mark()
        expect(DlangTypes.KW_TYPEOF)
        expect(DlangTypes.OP_PAR_LEFT)
        if (currentIs(DlangTypes.KW_RETURN)) advance()
        else if (!parseExpression()) {
            cleanup(m, DlangTypes.TYPEOF_EXPRESSION)
            return null
        }
        expect(DlangTypes.OP_PAR_RIGHT)
        exit_section_modified(builder, m, DlangTypes.TYPEOF_EXPRESSION, true)
        return m
    }

    /**
     * Parses a UnaryExpression
     *
     *
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
    fun parseUnaryExpression(): PsiBuilder.Marker? {
        val i = current()
        if (i === DlangTypes.OP_AND || i === DlangTypes.OP_NOT || i === DlangTypes.OP_ASTERISK || i === DlangTypes.OP_PLUS || i === DlangTypes.OP_MINUS || i === DlangTypes.OP_TILDA || i === DlangTypes.OP_PLUS_PLUS || i === DlangTypes.OP_MINUS_MINUS) {
            val m = builder.mark()
            advance()
            if (parseUnaryExpression() == null) {
                cleanup(m, DlangTypes.UNARY_EXPRESSION)
                return null
            }
            exit_section_modified(builder, m, DlangTypes.UNARY_EXPRESSION, true)
            return m
        } else if (i === DlangTypes.KW_DELETE) {
            return parseDeleteExpression()
        } else if (i === DlangTypes.KW_CAST) {
            return parseCastExpression()
        } else if (i === DlangTypes.KW_THROW) {
            return parseThrowExpression()
        }
        return parsePowExpression()
    }

    fun parsePostfixExpression_terminal(): PsiBuilder.Marker? {
        var m = parsePrimaryExpression()
        if (m != null) return m
        // TypeCtor
        val i = current()
        if (isTypeCtor(i)) {
            m = builder.mark()
            builder.advanceLexer()
            parseBasicType()
            if (expect(DlangTypes.OP_PAR_LEFT) == null) {
                m.rollbackTo()
                return null
            }
            parseArgumentList()
            expect(DlangTypes.OP_PAR_RIGHT)
            m.done(DlangTypes.POSTFIX_EXPRESSION)
            return m
        }
        return null
    }

    fun parsePostfixExpression_0(m: PsiBuilder.Marker): PsiBuilder.Marker? {
        if (currentIs(DlangTypes.OP_DOT)) {
            val marker = m.precede()
            builder.advanceLexer()
            val bookmark = builder.mark()
            if (currentIs(DlangTypes.KW_NEW)) {
                bookmark.drop()
                parseNewExpression()
            } else if (parseTemplateInstance()) {
                bookmark.drop()
            } else {
                bookmark.rollbackTo()
                if (builder.tokenType === DlangTypes.ID) {
                    advance()
                } else {
                    builder.error("Identifier, template instance or new expression expected")
                }
            }
            marker.done(DlangTypes.REFERENCE_EXPRESSION)
            return marker
        }
        if (currentIsOneOf(DlangTypes.OP_PLUS_PLUS, DlangTypes.OP_MINUS_MINUS)) {
            val marker = m.precede()
            builder.advanceLexer()
            marker.done(DlangTypes.POSTFIX_EXPRESSION)
            return marker
        }
        if (currentIs(DlangTypes.OP_PAR_LEFT)) {
            val marker = m.precede()
            builder.advanceLexer()
            parseArgumentList()
            expect(DlangTypes.OP_PAR_RIGHT)
            marker.done(DlangTypes.FUNCTION_CALL_EXPRESSION)
            return marker
        }
        if (currentIs(DlangTypes.OP_BRACKET_LEFT)) {
            val marker = m.precede()
            parseSliceOrIndexExpression()
            marker.done(DlangTypes.ARRAY_ACCESS_EXPRESSION)
            return marker
        }
        return null
    }

    fun parseSliceOrIndexExpression() {
        val m = builder.mark()
        advance()
        if (currentIs(DlangTypes.OP_BRACKET_RIGHT)) {
            advance()
            m.done(DlangTypes.INDEX_EXPRESSION)
            return
        }
        if (parseAssignExpression() == null) {
            m.drop()
            expect(DlangTypes.OP_BRACKET_RIGHT)
            return
        }
        while (!builder.eof() && builder.tokenType === DlangTypes.OP_COMMA) {
            advance()
            if (builder.tokenType !== DlangTypes.OP_BRACKET_RIGHT) {
                parseAssignExpression()
            }
        }
        if (builder.tokenType === DlangTypes.OP_BRACKET_RIGHT) {
            advance()
            m.done(DlangTypes.INDEX_EXPRESSION)
            return
        }
        while (!builder.eof() && (builder.tokenType === DlangTypes.OP_COMMA || builder.tokenType === DlangTypes.OP_DDOT)) {
            advance()
            parseAssignExpression()
        }
        expect(DlangTypes.OP_BRACKET_RIGHT)
        m.done(DlangTypes.INDEX_EXPRESSION)
    }

    fun parsePostfixExpression(): PsiBuilder.Marker? {
        var m = parsePostfixExpression_terminal()
        if (m == null) return m
        while (true) {
            val marker = parsePostfixExpression_0(m!!) ?: break
            m = marker
        }
        return m
    }

    /**
     * Parses an UnionDeclaration
     *
     *
     * $(GRAMMAR $(RULEDEF unionDeclaration):
     * $(LITERAL 'union') $(LITERAL Identifier) ($(RULE templateParameters) $(RULE constraint)?)? ($(RULE structBody) | $(LITERAL ';'))
     * | $(LITERAL 'union') $(RULE structBody)
     * ;)
     */
    fun parseUnionDeclaration(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_UNION) return false
        builder.advanceLexer()
        if (currentIs(DlangTypes.ID)) {
            advance()
            if (currentIs(DlangTypes.OP_PAR_LEFT)) {
                if (!parseTemplateParameters()) {
                    m.done(DlangTypes.UNION_DECLARATION)
                    return true
                }
                if (currentIs(DlangTypes.KW_IF)) if (!parseConstraint()) {
                    m.done(DlangTypes.UNION_DECLARATION)
                    return true
                }
            }
        }
        if (currentIs(DlangTypes.OP_SCOLON)) advance()
        else if (!parseStructBody()) {
            m.done(DlangTypes.UNION_DECLARATION)
            return true
        }
        m.done(DlangTypes.UNION_DECLARATION)
        return true
    }

    /**
     * Parses a Unittest
     *
     *
     * $(GRAMMAR $(RULEDEF unittest):
     * $(LITERAL 'unittest') $(RULE blockStatement)
     * ;)
     */
    fun parseUnittest(marker: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_UNITTEST) return false
        builder.advanceLexer()
        parseBlockStatement()
        marker.done(DlangTypes.UNITTEST)
        return true
    }

    /**
     * Parses a VariableDeclaration
     *
     *
     * $(GRAMMAR $(RULEDEF variableDeclaration):
     * $(RULE storageClass)* $(RULE type) $(RULE declarator) ($(LITERAL ',') $(RULE declarator))* $(LITERAL ';')
     * | $(RULE autoDeclaration)
     * $(GRAMMAR $(RULEDEF autoDeclaration):
     * $(RULE storageClass)+  $(RULE autoDeclarationPart) ($(LITERAL ',') $(RULE autoDeclarationPart))* $(LITERAL ';')
     * ;)
     */
    fun parseVariableDeclaration(m: PsiBuilder.Marker): Boolean {
        val bookmark = builder.mark()
        var hasStorageClass = false
        while (!builder.eof()) {
            if (!parseStorageClass()) break
            hasStorageClass = true
        }
        val bookmark2 = builder.mark()
        val parsedVariable = parseNonAutoVariableDeclaration()
        if (!parsedVariable && hasStorageClass) {
            bookmark2.rollbackTo()
            // Then it’s an auto declaration
            parseAutoAssignments()
            if (builder.tokenType !== DlangTypes.OP_SCOLON) {
                bookmark.rollbackTo()
                return false
            }
            bookmark.drop()
            builder.advanceLexer()
            m.done(DlangTypes.AUTO_DECLARATION)
            return true
        } else {
            bookmark2.drop()
        }
        if (!parsedVariable) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        expect(DlangTypes.OP_SCOLON)
        m.done(DlangTypes.VARIABLE_DECLARATION)
        return true
    }

    private fun parseNonAutoVariableDeclaration(): Boolean {
        if (!parseBasicType()) {
            return false
        }
        parseTypeSuffixes()
        var hasDeclarator = false
        while (!builder.eof()) {
            if (!parseIdentifierInitializer()) break
            hasDeclarator = true
            if (currentIs(DlangTypes.OP_COMMA)) advance()
            else break
        }
        return hasDeclarator
    }

    /**
     * Parses a Vector
     *
     *
     * $(GRAMMAR $(RULEDEF vector):
     * $(LITERAL '___vector') $(LITERAL '$(LPAREN)') $(RULE type) $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseVector(): Boolean {
        val marker = builder.mark()
        if (expect(DlangTypes.KW___VECTOR) == null) {
            cleanup(marker, DlangTypes.VECTOR)
            return false
        }
        if (expect(DlangTypes.OP_PAR_LEFT) == null) {
            cleanup(marker, DlangTypes.VECTOR)
            return false
        }
        if (!parseType()) {
            cleanup(marker, DlangTypes.VECTOR)
            return false
        }
        if (expect(DlangTypes.OP_PAR_RIGHT) == null) {
            cleanup(marker, DlangTypes.VECTOR)
            return false
        }
        exit_section_modified(builder, marker, DlangTypes.VECTOR, true)
        return true
    }

    /**
     * Parses a VersionCondition
     *
     *
     * $(GRAMMAR $(RULEDEF versionCondition):
     * $(LITERAL 'version') $(LITERAL '$(LPAREN)') ($(LITERAL IntegerLiteral) | $(LITERAL Identifier) | $(LITERAL 'unittest') | $(LITERAL 'assert')) $(LITERAL '$(RPAREN)')
     * ;)
     */
    fun parseVersionCondition(): Boolean {
        val m = builder.mark()
        val v = expect(DlangTypes.KW_VERSION)
        if (v == null) {
            cleanup(m, DlangTypes.VERSION_CONDITION)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, DlangTypes.VERSION_CONDITION)
            return false
        }
        if (currentIsOneOf(
                DlangTypes.INTEGER_LITERAL,
                DlangTypes.ID,
                DlangTypes.KW_UNITTEST,
                DlangTypes.KW_ASSERT
            )
        ) advance()
        else {
            error("Expected an integer literal, an identifier, `assert`, or `unittest`")
            exit_section_modified(builder, m, DlangTypes.VERSION_CONDITION, true)
            return false
        }
        expect(DlangTypes.OP_PAR_RIGHT)
        exit_section_modified(builder, m, DlangTypes.VERSION_CONDITION, true)
        return true
    }

    /**
     * Parses a VersionSpecification
     *
     *
     * $(GRAMMAR $(RULEDEF versionSpecification):
     * $(LITERAL 'version') $(LITERAL '=') ($(LITERAL Identifier) | $(LITERAL IntegerLiteral)) $(LITERAL ';')
     * ;)
     */
    fun parseVersionSpecification(m: PsiBuilder.Marker): Boolean {
        if (builder.tokenType !== DlangTypes.KW_VERSION) return false
        val bookmark = builder.mark()
        builder.advanceLexer()
        if (!tokenCheck(DlangTypes.OP_EQ)) {
            bookmark.rollbackTo()
            return false
        }
        bookmark.drop()
        if (!currentIsOneOf(
                DlangTypes.ID,
                DlangTypes.INTEGER_LITERAL
            )
        ) { // Note: that using integer for version is deprecated since 2.101.0
            error("Identifier or integer literal expected")
            m.done(DlangTypes.VERSION_SPECIFICATION)
            return true
        }
        advance()
        expect(DlangTypes.OP_SCOLON)
        m.done(DlangTypes.VERSION_SPECIFICATION)
        return true
    }

    /**
     * Parses a WhileStatement
     *
     *
     * $(GRAMMAR $(RULEDEF whileStatement):
     * $(LITERAL 'while') $(LITERAL '$(LPAREN)') $(RULE ifCondition) $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement)
     * ;)
     */
    fun parseWhileStatement(): Boolean {
        val m = builder.mark()
        if (!tokenCheck(DlangTypes.KW_WHILE)) {
            cleanup(m, DlangTypes.WHILE_STATEMENT)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            cleanup(m, DlangTypes.WHILE_STATEMENT)
            return false
        }
        if (!parseIfCondition()) {
            cleanup(m, DlangTypes.WHILE_STATEMENT)
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            cleanup(m, DlangTypes.WHILE_STATEMENT)
            return false
        }
        if (currentIs(DlangTypes.OP_BRACES_RIGHT)) {
            error("Statement expected")
            exit_section_modified(builder, m, DlangTypes.WHILE_STATEMENT, true)
            return true // this line makes DCD better
        }
        if (!parseScopeStatement()) {
            cleanup(m, DlangTypes.WHILE_STATEMENT)
            return false
        }
        exit_section_modified(builder, m, DlangTypes.WHILE_STATEMENT, true)
        return true
    }

    /**
     * Parses a WithStatement
     *
     *
     * $(GRAMMAR $(RULEDEF withStatement):
     * $(LITERAL 'with') $(LITERAL '$(LPAREN)') $(RULE expression) $(LITERAL '$(RPAREN)') $(RULE declarationOrStatement)
     * ;)
     */
    fun parseWithStatement(): Boolean {
        val marker = builder.mark()
        if (expect(DlangTypes.KW_WITH) == null) {
            cleanup(marker, DlangTypes.WITH_STATEMENT)
            return false
        }
        if (expect(DlangTypes.OP_PAR_LEFT) == null) {
            cleanup(marker, DlangTypes.WITH_STATEMENT)
            return false
        }
        if (!parseExpression()) {
            cleanup(marker, DlangTypes.WITH_STATEMENT)
            return false
        }
        if (expect(DlangTypes.OP_PAR_RIGHT) == null) {
            cleanup(marker, DlangTypes.WITH_STATEMENT)
            return false
        }
        if (!parseScopeStatement()) {
            cleanup(marker, DlangTypes.WITH_STATEMENT)
            return false
        }
        exit_section_modified(builder, marker, DlangTypes.WITH_STATEMENT, true)
        return true
    }

    /**
     * Parses an XorExpression
     *
     *
     * $(GRAMMAR $(RULEDEF xorExpression):
     * $(RULE andExpression)
     * | $(RULE xorExpression) $(LITERAL '^') $(RULE andExpression)
     * ;)
     */
    fun parseXorExpression(): PsiBuilder.Marker? {
        var marker = parseAndExpression() ?: return null
        while (currentIs(DlangTypes.OP_XOR)) {
            marker = marker.precede()
            builder.advanceLexer()
            if (parseAndExpression() == null) {
                cleanup(marker, DlangTypes.XOR_EXPRESSION)
                return null
            }
            marker.done(DlangTypes.XOR_EXPRESSION)
        }
        return marker
    }

    /**
     * Returns: true if there are more tokens
     */
    fun moreTokens(): Boolean {
        return !builder.eof()
    }

    val isCastQualifier: Boolean
        get() {
            val i = current()
            if (i === DlangTypes.KW_CONST) {
                if (peekIs(DlangTypes.OP_PAR_RIGHT)) return true
                return startsWith(
                    DlangTypes.KW_CONST,
                    DlangTypes.KW_SHARED,
                    DlangTypes.OP_PAR_RIGHT
                )
            } else if (i === DlangTypes.KW_IMMUTABLE) {
                return peekIs(DlangTypes.OP_PAR_RIGHT)
            } else if (i === DlangTypes.KW_INOUT) {
                if (peekIs(DlangTypes.OP_PAR_RIGHT)) return true
                return startsWith(
                    DlangTypes.KW_INOUT,
                    DlangTypes.KW_SHARED,
                    DlangTypes.OP_PAR_RIGHT
                )
            } else if (i === DlangTypes.KW_SHARED) {
                if (peekIs(DlangTypes.OP_PAR_RIGHT)) return true
                if (startsWith(
                        DlangTypes.KW_SHARED,
                        DlangTypes.KW_CONST,
                        DlangTypes.OP_PAR_RIGHT
                    )
                ) return true
                return startsWith(
                    DlangTypes.KW_SHARED,
                    DlangTypes.KW_INOUT,
                    DlangTypes.OP_PAR_RIGHT
                )
            } else {
                return false
            }
        }

    val isAssociativeArrayLiteral: Boolean
        get() {
            if (cachedAAChecks.containsKey(builder.currentOffset)) return cachedAAChecks[builder.currentOffset]!!
            val currentIndex = builder.currentOffset
            val bookmark = builder.mark()
            advance()
            val result =
                !currentIs(DlangTypes.OP_BRACKET_RIGHT) && parseExpression() && currentIs(
                    DlangTypes.OP_COLON
                )
            cachedAAChecks[currentIndex] = result
            bookmark.rollbackTo()
            return result
        }

    val isType: Boolean
        /// Only use this in template parameter parsing
        get() {
            if (!moreTokens()) return false
            val bookmark = builder.mark()
            if (!parseType()) {
                bookmark.rollbackTo()
                return false
            }
            bookmark.rollbackTo()
            return currentIsOneOf(
                DlangTypes.OP_COMMA,
                DlangTypes.OP_PAR_RIGHT,
                DlangTypes.OP_EQ
            )
        }

    private val isStorageClass: Boolean
        get() {
            if (!moreTokens()) return false
            val i = current()
            return if (i === DlangTypes.KW_CONST || i === DlangTypes.KW_IMMUTABLE || i === DlangTypes.KW_INOUT || i === DlangTypes.KW_SHARED) {
                !peekIs(DlangTypes.OP_PAR_LEFT)
            } else i === DlangTypes.OP_AT || i === DlangTypes.KW_DEPRECATED || i === DlangTypes.KW_ABSTRACT || i === DlangTypes.KW_ALIGN || i === DlangTypes.KW_AUTO || i === DlangTypes.KW_ENUM || i === DlangTypes.KW_EXTERN || i === DlangTypes.KW_FINAL || i === DlangTypes.KW_NOTHROW || i === DlangTypes.KW_OVERRIDE || i === DlangTypes.KW_PURE || i === DlangTypes.KW_REF || i === DlangTypes.KW___GSHARED || i === DlangTypes.KW_SCOPE || i === DlangTypes.KW_STATIC || i === DlangTypes.KW_SYNCHRONIZED
        }

    val isAttribute: Boolean
        get() {
            if (!moreTokens()) return false
            val i = current()
            if (i === DlangTypes.KW_CONST || i === DlangTypes.KW_IMMUTABLE || i === DlangTypes.KW_INOUT || i === DlangTypes.KW_SCOPE) {
                return !peekIs(DlangTypes.OP_PAR_LEFT)
            } else if (i === DlangTypes.KW_STATIC) {
                return !peekIsOneOf(
                    DlangTypes.KW_ASSERT,
                    DlangTypes.KW_THIS,
                    DlangTypes.KW_IF,
                    DlangTypes.OP_TILDA,
                    DlangTypes.KW_FOREACH,
                    DlangTypes.KW_FOREACH_REVERSE
                )
            } else if (i === DlangTypes.KW_SHARED) {
                return !(startsWith(
                    DlangTypes.KW_SHARED,
                    DlangTypes.KW_STATIC,
                    DlangTypes.KW_THIS
                ) || startsWith(
                    DlangTypes.KW_SHARED,
                    DlangTypes.KW_STATIC,
                    DlangTypes.OP_TILDA
                ) || peekIs(DlangTypes.OP_PAR_LEFT))
            } else if (i === DlangTypes.KW_PRAGMA) {
                val bookmark = builder.mark()
                advance()
                val past = peekPastParens()
                if (past == null || past === DlangTypes.OP_SCOLON) {
                    bookmark.rollbackTo()
                    return false
                }
                bookmark.rollbackTo()
                return true
            } else return i === DlangTypes.KW_DEPRECATED || i === DlangTypes.KW_PRIVATE || i === DlangTypes.KW_PACKAGE || i === DlangTypes.KW_PROTECTED || i === DlangTypes.KW_PUBLIC || i === DlangTypes.KW_EXPORT || i === DlangTypes.KW_FINAL || i === DlangTypes.KW_SYNCHRONIZED || i === DlangTypes.KW_OVERRIDE || i === DlangTypes.KW_ABSTRACT || i === DlangTypes.KW_AUTO || i === DlangTypes.KW___GSHARED || i === DlangTypes.KW_PURE || i === DlangTypes.KW_NOTHROW || i === DlangTypes.OP_AT || i === DlangTypes.KW_REF || i === DlangTypes.KW_EXTERN || i === DlangTypes.KW_ALIGN
        }

    private fun isMemberFunctionAttribute(t: IElementType?): Boolean {
        return t === DlangTypes.KW_CONST || t === DlangTypes.KW_IMMUTABLE || t === DlangTypes.KW_INOUT || t === DlangTypes.KW_SHARED || t === DlangTypes.OP_AT || t === DlangTypes.KW_PURE || t === DlangTypes.KW_NOTHROW || t === DlangTypes.KW_RETURN || t === DlangTypes.KW_SCOPE
    }

    private fun isTypeCtor(t: IElementType?): Boolean {
        return t === DlangTypes.KW_CONST || t === DlangTypes.KW_IMMUTABLE || t === DlangTypes.KW_INOUT || t === DlangTypes.KW_SHARED
    }

    private fun currentIsMemberFunctionAttribute(): Boolean {
        return moreTokens() && isMemberFunctionAttribute(current())
    }

    private fun error(message: String) {
        builder.error(message)
        while (moreTokens()) {
            if (currentIsOneOf(
                    DlangTypes.OP_SCOLON, DlangTypes.OP_BRACES_RIGHT,
                    DlangTypes.OP_PAR_RIGHT, DlangTypes.OP_BRACKET_RIGHT
                )
            ) {
                advance()
                break
            } else advance()
        }
    }

    private fun skip(o: IElementType, c: IElementType) {
        assert(currentIs(o))
        advance()
        var depth = 1
        while (moreTokens()) {
            if (builder.tokenType === c) {
                advance()
                depth--
                if (depth <= 0) return
            } else if (builder.tokenType === o) {
                depth++
                advance()
            } else {
                advance()
            }
        }
    }

    private fun skipBrackets() {
        skip(DlangTypes.OP_BRACKET_LEFT, DlangTypes.OP_BRACKET_RIGHT)
    }

    private fun peekPast(o: IElementType, c: IElementType): IElementType? {
        if (builder.eof()) return null
        var depth = 1
        val marker = builder.mark()
        builder.advanceLexer()
        while (builder.tokenType != null) {
            if (builder.tokenType === o) {
                ++depth
            } else if (builder.tokenType === c) {
                --depth
            }
            builder.advanceLexer()
            if (depth <= 0) break
        }
        val token = builder.tokenType
        marker.rollbackTo()
        return if (depth == 0) token else null
    }

    private fun peekPastParens(): IElementType? {
        return peekPast(DlangTypes.OP_PAR_LEFT, DlangTypes.OP_PAR_RIGHT)
    }

    private fun peekPastBrackets(): IElementType? {
        return peekPast(DlangTypes.OP_BRACKET_LEFT, DlangTypes.OP_BRACKET_RIGHT)
    }

    private fun peekPastBraces(): IElementType? {
        return peekPast(DlangTypes.OP_BRACES_LEFT, DlangTypes.OP_BRACES_RIGHT)
    }

    /**
     * Returns: `true` if there is a next token and that token has the type `t`.
     */
    private fun peekIs(t: IElementType): Boolean {
        return peekNIs(t, 1)
    }

    /**
     * Returns: `true` if the token `offset` tokens ahead exists and is of type `t`.
     */
    private fun peekNIs(t: IElementType, offset: Int): Boolean {
        return builder.lookAhead(offset) === t
    }

    /**
     * Returns: `true` if there are at least `types.length` tokens following the
     * current one and they have types matching the corresponding elements of
     * `types`.
     */
    private fun peekAre(vararg types: IElementType): Boolean {
        var i = 0
        for (type in types) {
            if (!peekNIs(type, i + 1)) return false
            i++
        }
        return true
    }

    /**
     * Returns: `true` if there is a next token and its type is one of the given
     * `types`.
     */
    private fun peekIsOneOf(vararg types: IElementType): Boolean {
        val needle = builder.lookAhead(1) ?: return false
        for (type in types) {
            if (type === needle) {
                return true
            }
        }
        return false
    }

    /**
     * Returns a token of the specified type if it was the next token, otherwise
     * calls the error function and returns null. Advances the lexer by one token.
     */
    private fun expect(tok: IElementType): IElementType? {
        if (!builder.eof() && builder.tokenType === tok) {
            return advance()
        } else {
            val tokenString = tok.debugName
            val token = (if (!builder.eof()) (builder.tokenType.toString()) else "EOF")
            builder.error("Expected $tokenString instead of $token")
            return null
        }
    }

    /**
     * Returns: the _current token
     */
    private fun current(): IElementType? {
        return builder.tokenType
    }

    /**
     * Advances to the next token and returns the current token
     */
    private fun advance(): IElementType? {
        val token = builder.tokenType
        builder.advanceLexer()
        return token
    }

    /**
     * Returns: true if the current token has the given type
     */
    private fun currentIs(type: IElementType): Boolean {
        return !builder.eof() && builder.tokenType === type
    }

    /**
     * Returns: true if the current token is one of the given types
     */
    private fun currentIsOneOf(vararg types: IElementType): Boolean {
        if (builder.eof()) return false

        val curr = current()

        if (curr != null) {
            return listOf(*types).contains(curr)
        }
        return false
    }

    private fun startsWith(vararg types: IElementType): Boolean {
        for (i in types.indices) {
            val token = builder.lookAhead(i)
            if (token == null || token !== types[i]) return false
        }
        return true
    }

    private fun tokenCheck(tok: IElementType): Boolean {
        return expect(tok) != null
    }

    private fun parseStaticCtorDtorCommon(): Boolean {
        if (!tokenCheck(DlangTypes.KW_THIS)) {
            return false
        }
        if (!tokenCheck(DlangTypes.OP_PAR_LEFT)) {
            return true
        }
        if (!tokenCheck(DlangTypes.OP_PAR_RIGHT)) {
            return true
        }
        while (moreTokens() && !currentIsOneOf(
                DlangTypes.OP_BRACES_LEFT, DlangTypes.KW_IN, DlangTypes.KW_OUT,
                DlangTypes.KW_DO, DlangTypes.OP_SCOLON
            )
        ) {
            if (!(parseMemberFunctionAttribute())) {
                return true
            }
        }
        if (currentIs(DlangTypes.OP_SCOLON)) advance()
        else {
            return parseFunctionBody()
        }
        return true
    }

    fun parseInterfaceOrClass() {
        expect(DlangTypes.ID) ?: return
        if (currentIs(DlangTypes.OP_SCOLON)) {
            advance()
            return
        }
        if (currentIs(DlangTypes.OP_BRACES_LEFT)) {
            parseStructBody()
            return
        }
        if (currentIs(DlangTypes.OP_PAR_LEFT)) {
            if (!parseTemplateParameters()) {
                return
            }
            if (currentIs(DlangTypes.OP_SCOLON)) {
                advance()
                return
            }
            constraint(false)
            return
        }
        if (currentIs(DlangTypes.OP_COLON)) {
            baseClassList()
            return
        }
        parseStructBody()
    }

    private fun emptyBody(): Boolean {
        advance()
        return true
    }

    private fun baseClassList(): Boolean {
        advance() // :
        if (!parseBaseClassList()) {
            return false
        }
        if (currentIs(DlangTypes.KW_IF)) {
            return constraint(true)
        }
        return parseStructBody()
    }

    private fun constraint(baseClassListQ: Boolean): Boolean {
        if (currentIs(DlangTypes.KW_IF)) {
            if (!parseConstraint()) {
                return false
            }
        }
        if (baseClassListQ) {
            if (currentIs(DlangTypes.OP_BRACES_LEFT)) {
                return parseStructBody()
            } else if (currentIs(DlangTypes.OP_SCOLON)) {
                return emptyBody()
            } else {
                error("Struct body or ';' expected")
                return false
            }
        }
        if (currentIs(DlangTypes.OP_COLON)) {
            return baseClassList()
        }
        if (currentIs(DlangTypes.KW_IF)) {
            return constraint(false)
        }
        if (currentIs(DlangTypes.OP_SCOLON)) {
            return emptyBody()
        }
        if (currentIs(DlangTypes.OP_COLON)) {
            return baseClassList()
        }
        return parseStructBody()
    }

    companion object {
        // Please keep this list in order
        private val REGISTER_NAMES = HashSet(
            mutableListOf<String>(
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
            )
        )

        private val stringLiteralsArray = arrayOf<IElementType>(
            DlangTypes.ALTERNATE_WYSIWYG_STRING,
            DlangTypes.DOUBLE_QUOTED_STRING,
            DlangTypes.WYSIWYG_STRING,
            DlangTypes.DELIMITED_STRING,
            DlangTypes.TOKEN_STRING
        )

        private val literals: Set<IElementType?> = Sets.newHashSet<IElementType?>(
            DlangTypes.KW_THIS,
            DlangTypes.KW_SUPER,
            DlangTypes.KW_NULL,
            DlangTypes.KW_TRUE,
            DlangTypes.KW_FALSE,
            DlangTypes.INTEGER_LITERAL,  // represent int, long, uint and ulong values
            DlangTypes.FLOAT_LITERAL,  // represent float, double, ifloat, idouble, real and ireal values
            DlangTypes.CHARACTER_LITERAL,
            DlangTypes.ALTERNATE_WYSIWYG_STRING,
            DlangTypes.DOUBLE_QUOTED_STRING,
            DlangTypes.WYSIWYG_STRING,
            DlangTypes.DELIMITED_STRING,
            DlangTypes.TOKEN_STRING
        )

        private val basicTypes: Set<IElementType?> = Sets.newHashSet<IElementType?>(
            DlangTypes.KW_INT,
            DlangTypes.KW_BOOL,
            DlangTypes.KW_BYTE,
            DlangTypes.KW_CDOUBLE,
            DlangTypes.KW_CENT,
            DlangTypes.KW_CFLOAT,
            DlangTypes.KW_CHAR,
            DlangTypes.KW_CREAL,
            DlangTypes.KW_DCHAR,
            DlangTypes.KW_DOUBLE,
            DlangTypes.KW_FLOAT,
            DlangTypes.KW_IDOUBLE,
            DlangTypes.KW_IFLOAT,
            DlangTypes.KW_IREAL,
            DlangTypes.KW_LONG,
            DlangTypes.KW_REAL,
            DlangTypes.KW_SHORT,
            DlangTypes.KW_UBYTE,
            DlangTypes.KW_UCENT,
            DlangTypes.KW_UINT,
            DlangTypes.KW_ULONG,
            DlangTypes.KW_USHORT,
            DlangTypes.KW_VOID,
            DlangTypes.KW_WCHAR
        )

        private val Protections: Set<IElementType?> = Sets.newHashSet<IElementType?>(
            DlangTypes.KW_EXPORT,
            DlangTypes.KW_PACKAGE,
            DlangTypes.KW_PRIVATE,
            DlangTypes.KW_PUBLIC,
            DlangTypes.KW_PROTECTED
        )
    }
}
