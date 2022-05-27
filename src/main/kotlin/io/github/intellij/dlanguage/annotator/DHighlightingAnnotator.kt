package io.github.intellij.dlanguage.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.colors.DColor
import io.github.intellij.dlanguage.resolve.processors.basic.BasicResolve
import io.github.intellij.dlanguage.utils.*

class DHighlightingAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        val (partToHighlight, color) = when (element) {
            is IdentifierOrTemplateInstance -> highlightReference(element)
            is TemplateSingleArgument -> highlightReference(element)
            is TemplateParameter -> highlightNotReference(element)
            else -> null
        } ?: return

        holder.newAnnotation(HighlightSeverity.INFORMATION, "").textAttributes(color.textAttributesKey).create()
    }

    private fun highlightReference(element: PsiElement): Pair<TextRange, DColor>? {
        val identifier = when (element) {
            is IdentifierOrTemplateInstance -> element.identifier
            is TemplateSingleArgument -> element.identifier
            else -> null
        } ?: return null

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
        is TemplateTypeParameter,
        is TemplateParameter -> DColor.TYPE_PARAMETER
        else -> null
    }

    private fun partToHighlight(element: PsiElement): TextRange? {
        val name = when (element) {
            is TemplateParameter -> {
                when {
                    element.templateTypeParameter != null -> return partToHighlight(element.templateTypeParameter!!)
                    element.templateValueParameter != null -> return partToHighlight(element.templateValueParameter!!)
                    element.templateAliasParameter != null -> return partToHighlight(element.templateAliasParameter!!)
                    element.templateThisParameter != null -> return partToHighlight(element.templateThisParameter!!)
                    else -> element
                }
            }
            is TemplateTypeParameter -> element.identifier
            is TemplateValueParameter -> element.identifier
            is TemplateAliasParameter -> element.identifier
            is TemplateThisParameter -> element.templateTypeParameter?.identifier
            else -> element
        }

        return name?.textRange
    }
}
