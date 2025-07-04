package io.github.intellij.dlanguage.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.utils.DPsiLiteralUtil
import io.github.intellij.dlanguage.utils.LiteralExpression

fun checkLiteral(expressionElement: LiteralExpression, holder: AnnotationHolder) {
    if (expressionElement.integeR_LITERAL != null) {
        val element = expressionElement.integeR_LITERAL!!
        val text = element.text
        if (DPsiLiteralUtil.isHexadecimal(text) && !text.substring(2).contains("[0-9a-fA-F]".toRegex()))
            holder.newAnnotation(HighlightSeverity.ERROR, DlangBundle.message("literal.hexadecimal.no.digits")).range(element).create()
        else if (DPsiLiteralUtil.isBinary(text) && !text.substring(2).contains("[01]".toRegex()))
            holder.newAnnotation(HighlightSeverity.ERROR, DlangBundle.message("literal.binary.no.digits")).range(element).create()
        else {
            val value = DPsiLiteralUtil.parseIntegerLiteral(text)
            if (value == null)
                holder.newAnnotation(HighlightSeverity.ERROR, DlangBundle.message("literal.integer.too.large")).range(element).create()
            else if (DPsiLiteralUtil.hasLongNotUnsignedSuffix(text) &&
                !(DPsiLiteralUtil.isHexadecimal(text) || DPsiLiteralUtil.isBinary(text)) &&
                value > DPsiLiteralUtil.SIGNED_LONG_MAX.toULong())
                holder.newAnnotation(HighlightSeverity.ERROR, DlangBundle.message("literal.integer.not.unsigned.overflow")).range(element).create()
        }
        return
    }
}
