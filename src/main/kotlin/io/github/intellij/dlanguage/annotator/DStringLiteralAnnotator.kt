package io.github.intellij.dlanguage.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.refactoring.suggested.startOffset
import io.github.intellij.dlanguage.utils.PrimaryExpression
import org.apache.commons.lang.StringUtils

/**
 * Handle all errors in String and characters literals. It annotates the errors on invalid literal.
 */
class DStringLiteralAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element !is PrimaryExpression) return
        if (element.delimiteD_STRINGs.isNotEmpty()) {
            for (elem in element.delimiteD_STRINGs) {
                val value = elem.text.substring(2, elem.textLength - 1) // Skip `q"` and `"`
                val openingDelimiter = getOpeningDelimiter(value)
                if (openingDelimiter == null) {
                    when {
                        value[0].isWhitespace() -> "Delimiter cannot be whitespace"
                        else -> "Invalid string delimiter"
                    }.let { holder.newAnnotation(HighlightSeverity.ERROR, it).create() }
                    continue
                }
                val closingDelimiter = getCorrespondingClosingDelimiter(openingDelimiter)
                val lines = value.split(closingDelimiter)
                val expectedDelimitersCount = if (openingDelimiter == closingDelimiter) 2 else 1
                if (lines.size > expectedDelimitersCount + 1) {
                    val elemStartIndex = elem.startOffset + 2 // + 2 to because we skipped q"
                    holder.newAnnotation(HighlightSeverity.ERROR, "Illegal text found after closing delimiter, expected \" character instead")
                        .range(TextRange(elemStartIndex + StringUtils.ordinalIndexOf(value, closingDelimiter, expectedDelimitersCount) + closingDelimiter.length, elemStartIndex + value.length)).create()
                }
            }
            return
        }
    }

    /**
     * Take the opening delimiter of a String and return the corresponding closing one
     */
    fun getCorrespondingClosingDelimiter(openingDelimiter: String) : String {
        return when (openingDelimiter) {
            "(" -> ")"
            "[" -> "]"
            "{" -> "}"
            "<" -> ">"
            else -> openingDelimiter
        }
    }

    /**
     * Take a Delimited String content without the surrounding delimiters (`q"` and `"`) and
     * return the opening delimiter, or null if the delimiter is invalid.
     */
    fun getOpeningDelimiter(literal: String) : String? {
        if (listOf('(', ')' , '[', ']', '{', '}', '<', '>', '/', '\\').indexOf(literal[0]) != -1)
            return literal[0].toString()

        // Invalid delimiter
        if (literal[0].isWhitespace())
            return null

        // otherwise it’s a id\n
        val lines = literal.lines()
        if (lines.size == 1)
            return null // invalid string delimiter
        return lines[0]
    }
}
