package io.github.intellij.dlanguage.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.colors.DColor
import io.github.intellij.dlanguage.psi.DLanguageIdentifierOrTemplateInstance
import io.github.intellij.dlanguage.psi.DLanguageTemplateTypeParameter
import io.github.intellij.dlanguage.resolve.processors.basic.BasicResolve
import io.github.intellij.dlanguage.utils.TemplateParameter

class DHighlightingAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        val (partToHighlight, color) = when (element) {
            is DLanguageIdentifierOrTemplateInstance -> highlightReference(element)
            is DLanguageTemplateTypeParameter -> highlightNotReference(element)
            else -> null
        } ?: return

        holder.createInfoAnnotation(partToHighlight, null).textAttributes = color.textAttributesKey
    }

    private fun highlightReference(element: DLanguageIdentifierOrTemplateInstance): Pair<TextRange, DColor>? {
        val identifier = element.identifier ?: return null
        val basicResolveResult = BasicResolve.getInstance(element.project, false)
            .findDefinitionNode(identifier)

        val result = basicResolveResult.firstOrNull() ?: return null

        val color = colorFor(result) ?: return null
        val part = partToHighlight(element) ?: return null

        return part to color
    }

    private fun highlightNotReference(element: PsiElement): Pair<TextRange, DColor>? {
        val color = colorFor(element) ?: return null
        val part = partToHighlight(element) ?: return null

        return part to color
    }

    private fun colorFor(element: PsiElement): DColor? = when (element) {
        is DLanguageTemplateTypeParameter,
        is TemplateParameter -> DColor.TYPE_PARAMETER
        else -> null
    }

    private fun partToHighlight(element: PsiElement): TextRange? {
        val name = when (element) {
            is DLanguageTemplateTypeParameter -> element.identifier
            else -> element
        }

        return name?.textRange
    }
}
