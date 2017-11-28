package io.github.intellij.dlanguage.types.infer

import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.psi.DLanguagePrimaryExpression
import io.github.intellij.dlanguage.psi.DLanguageUnaryExpression
import io.github.intellij.dlanguage.types.*
import java.lang.Long.parseLong

/**
 * Literals:
 *   1; 'a'; "Hello"; true; -1; 1.2f; 1.3; q{asdasda aaa}; `asdas`;
 */
private fun inferLiteralExprType(literal: PsiElement?): DType = when (literal) {
    is DLanguageUnaryExpression -> inferLiteralExprType(literal.primaryExpression)  // ignore unary operator
    is DLanguagePrimaryExpression -> inferPrimaryExprType(literal)
    else -> DTypeUnknown
}

private fun inferPrimaryExprType(literal: DLanguagePrimaryExpression): DType = when {
    literal.integeR_LITERAL != null -> inferIntegerLiteral(literal.integeR_LITERAL!!)
    literal.floaT_LITERAL != null -> inferFloatLiteral(literal.floaT_LITERAL!!)
    literal.characteR_LITERAL != null -> DTypeChar
    literal.doublE_QUOTED_STRINGs.size > 0 -> DTypeString
    literal.kW_TRUE != null -> DTypeBool
    literal.kW_FALSE != null -> DTypeBool
    else -> DTypeUnknown
}

private fun inferIntegerLiteral(literal: PsiElement): DType {
    val text = literal.text.toLowerCase()

    return when {
        text.endsWith("lu") -> DTypeULong
        text.endsWith("ul") -> DTypeULong
        text.endsWith("l") -> DTypeLong
        text.endsWith("u") -> DTypeUInt
        else -> DTypeInt
    }
}

private fun inferFloatLiteral(literal: PsiElement): DType {
    val text = literal.text.toLowerCase()

    return when {
        text.endsWith("fi") -> DTypeIFloat
        text.endsWith("if") -> DTypeIFloat
        text.endsWith("li") -> DTypeIReal
        text.endsWith("il") -> DTypeIReal
        text.endsWith("f") -> DTypeFloat
        text.endsWith("l") -> DTypeReal
        text.endsWith("i") -> DTypeIDouble
        else -> DTypeDouble
    }
}
