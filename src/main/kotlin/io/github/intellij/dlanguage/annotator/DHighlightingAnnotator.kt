package io.github.intellij.dlanguage.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.colors.DColor
import io.github.intellij.dlanguage.psi.DLanguageType_2
import io.github.intellij.dlanguage.resolve.processors.basic.BasicResolve
import io.github.intellij.dlanguage.utils.*

class DHighlightingAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        val (partToHighlight, color) = when (element) {
            is IdentifierOrTemplateInstance -> highlightReference(element)
            is TemplateSingleArgument -> highlightReference(element)
            is TemplateParameter -> highlightNotReference(element)
            is Identifier -> highlightIdentifier(element)
            else -> null
        } ?: return

        holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(partToHighlight).textAttributes(color.textAttributesKey).create()
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

        val color = colorForReferenced(result) ?: return null
        val part = partToHighlight(element) ?: return null

        return part to color
    }

    private fun highlightNotReference(element: PsiElement): Pair<TextRange, DColor>? {
        val color = colorFor(element) ?: return null
        val part = partToHighlight(element) ?: return null

        return part to color
    }

    private fun highlightIdentifier(element: PsiElement): Pair<TextRange, DColor>? {
        val parent = element.parent
        val color = when {
            parent is FunctionDeclaration -> DColor.FUNCTION_DEFINITION
            parent is TemplateDeclaration -> DColor.FUNCTION_DEFINITION
            parent is TemplateInstance -> {
                // don’t colorize templated class/struct/union instantiations as function calls
                if (PsiTreeUtil.getParentOfType(parent, Type_2::class.java, true, Declaration::class.java) == null)
                    DColor.FUNCTION_CALL
                else
                    null
            }
            else -> null
        }?: return null

        return element.textRange to color
    }

    private fun colorFor(element: PsiElement): DColor? = when (element) {
        is TemplateTypeParameter,
        is TemplateParameter -> DColor.TYPE_PARAMETER
        else -> null
    }

    /**
     * Return the color for a PsiElement that has `element` as reference
     * @param element: the reference of the element to color
     */
    private fun colorForReferenced(element: PsiElement): DColor? = when (element) {
        is TemplateTypeParameter,
        is TemplateParameter -> DColor.TYPE_PARAMETER
        is FunctionDeclaration -> {
            // We ignore properties here because they behave like struct/class field
            if (element.isProperty)
                null
            else
                DColor.FUNCTION_CALL
        }
        is TemplateDeclaration -> DColor.FUNCTION_CALL
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
