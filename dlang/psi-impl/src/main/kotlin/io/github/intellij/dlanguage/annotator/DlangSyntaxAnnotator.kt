package io.github.intellij.dlanguage.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.project.DumbAware
import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.utils.AnonymousEnumDeclaration
import io.github.intellij.dlanguage.utils.EnumDeclaration
import io.github.intellij.dlanguage.utils.LiteralExpression

class DlangSyntaxAnnotator : Annotator, DumbAware {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is LiteralExpression) {
            checkLiteral(element, holder)
        }
        else if (element is EnumDeclaration) {
            if (element.enumBody?.enumMembers?.isEmpty()?:false) {
                holder.newAnnotation(HighlightSeverity.ERROR, DlangBundle.message("enum.no.members")).range(element).create()
            }
        }
        else if (element is AnonymousEnumDeclaration) {
            if (element.enumMembers.isEmpty()) {
                holder.newAnnotation(HighlightSeverity.ERROR, DlangBundle.message("enum.no.members")).range(element).create()
            }
        }
    }
}
