package io.github.intellij.dlanguage.types.infer

import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.psi.*
import io.github.intellij.dlanguage.psi.ext.containsToken
import io.github.intellij.dlanguage.types.*

fun inferExprType(literal: PsiElement?): DType = when (literal) {
    is DLanguageInitializer -> inferAssignType(literal.nonVoidInitializer?.assignExpression)
    is DLanguageUnaryExpression -> inferExprType(literal.primaryExpression)  // ignore unary operator
    is DLanguagePrimaryExpression -> inferPrimaryExprType(literal)
    else -> DTypeUnknown
}

private fun inferPrimaryExprType(literal: DLanguagePrimaryExpression): DType = when {
    literal.containsToken(DlangTypes.WYSIWYG_STRING) -> inferStringLiteral(literal)
    literal.containsToken(DlangTypes.ALTERNATE_WYSIWYG_STRING) -> inferStringLiteral(literal)
    literal.firstChild is DLanguageString -> inferStringLiteral(literal)
    literal.integeR_LITERAL != null -> inferIntegerLiteral(literal.integeR_LITERAL!!)
    literal.floaT_LITERAL != null -> inferFloatLiteral(literal.floaT_LITERAL!!)
    literal.characteR_LITERAL != null -> DTypeChar
    literal.doublE_QUOTED_STRINGs.size > 0 -> inferStringLiteral(literal)
    literal.kW_TRUE != null -> DTypeBool
    literal.kW_FALSE != null -> DTypeBool
    else -> DTypeUnknown
}

fun inferStringLiteral(literal: DLanguagePrimaryExpression): DType {
    val text = literal.text

    return when {
        text.endsWith("\"d") -> DTypeDString
        text.endsWith("\"w") -> DTypeWString
        else -> DTypeString
    }
}

private fun inferAssignType(assignExpression: DLanguageAssignExpression?): DType {
    if (assignExpression == null)
        return DTypeUnknown

    val cmpExpr = assignExpression.children.firstOrNull() as? DLanguageCmpExpression ?: return DTypeUnknown
    val unary = cmpExpr.children.firstOrNull() as? DLanguageUnaryExpression ?: return DTypeUnknown

    return inferExprType(unary)
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
