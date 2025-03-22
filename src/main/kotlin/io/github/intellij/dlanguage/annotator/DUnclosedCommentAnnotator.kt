package io.github.intellij.dlanguage.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.util.endOffset
import io.github.intellij.dlanguage.DLanguage

/**
 * Detect if a comment is not closed and annotate with error if that’s the case.
 */
class DUnclosedCommentAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element !is PsiComment || element.language !is DLanguage) return
        if (element.text.startsWith("/*") && !element.text.endsWith("*/") ||
            element.text.startsWith("/+") && (!element.text.endsWith("+/") ||
            element.text.split("/+").size != element.text.split("+/").size)) {
            val start = element.endOffset - 1
            val end = start + 1
            holder.newAnnotation(HighlightSeverity.ERROR, "Unclosed comment")
                .range(TextRange(start, end)).create()
        }
    }
}
