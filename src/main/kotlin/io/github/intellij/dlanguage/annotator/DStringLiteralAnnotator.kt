package io.github.intellij.dlanguage.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.startOffset
import io.github.intellij.dlanguage.utils.LiteralExpression
import io.github.intellij.dlanguage.utils.getCorrespondingClosingDelimiter
import io.github.intellij.dlanguage.utils.getOpeningDelimiter
import io.github.intellij.dlanguage.utils.isPredefinedDelimiter
import org.apache.commons.lang3.StringUtils

/**
 * Handle all errors in String and characters literals. It annotates the errors on invalid literal.
 */
class DStringLiteralAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element !is LiteralExpression) return
        if (element.delimiteD_STRINGs.isNotEmpty()) {
            for (elem in element.delimiteD_STRINGs) {
                val endPos = if (elem.text[elem.textLength - 1] == '"')  1 else 2;
                val value = elem.text.substring(2, elem.textLength - endPos) // Skip `q"` and `"`
                val openingDelimiter = getOpeningDelimiter(value)
                if (openingDelimiter == null) {
                    when {
                        value[0].isWhitespace() -> "Delimiter cannot be whitespace"
                        else -> "Invalid string delimiter"
                    }.let { holder.newAnnotation(HighlightSeverity.ERROR, it).create() }
                    continue
                }
                var closingDelimiter = getCorrespondingClosingDelimiter(openingDelimiter)

                // impossible, handled at lexer directly
                if (closingDelimiter != openingDelimiter)
                    continue

                if (!isPredefinedDelimiter(openingDelimiter))
                    closingDelimiter = "\n$closingDelimiter"

                val lines = value.split(closingDelimiter)
                val expectedDelimitersCount = if (isPredefinedDelimiter(openingDelimiter) && getCorrespondingClosingDelimiter(openingDelimiter) == openingDelimiter) 3 else 2
                if (lines.size > expectedDelimitersCount) {
                    val elemStartIndex = elem.startOffset + 2 // + 2 to because we skipped q"
                    holder.newAnnotation(HighlightSeverity.ERROR, "Illegal text found after closing delimiter, expected \" character instead")
                        .range(TextRange(elemStartIndex + StringUtils.ordinalIndexOf(value, closingDelimiter, expectedDelimitersCount - 1) + closingDelimiter.length, elemStartIndex + value.length)).create()
                }
            }
            return
        }
    }
}
