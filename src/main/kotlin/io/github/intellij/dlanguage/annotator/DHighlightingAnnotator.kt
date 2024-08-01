package io.github.intellij.dlanguage.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import io.github.intellij.dlanguage.colors.DColor
import io.github.intellij.dlanguage.psi.DLanguageBasicType
import io.github.intellij.dlanguage.psi.DlangTokenType
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.psi.interfaces.Declaration
import io.github.intellij.dlanguage.resolve.processors.basic.BasicResolve
import io.github.intellij.dlanguage.utils.*

// TODO find a better place for this variable
/**
 * Contains the "standard aliased types" as defined in the specs.
 * The standards aliased types are not native types but are defined in object.d
 */
val DlangAliasedTypes = setOf("size_t", "ptrdiff_t", "noreturn", "string", "wstring", "dstring")

class DHighlightingAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        val (partToHighlight, color) = when (element) {
            is AtAttribute -> element.textRange to DColor.AT_ATTRIBUTE
            is ModuleDeclaration -> element.identifierChain?.textRange to DColor.MODULE_DEFINITION
            is ReferenceExpression -> highlightReference(element)
            is QualifiedIdentifier -> highlightReference(element)
            is TemplateSingleArgument -> highlightNotReference(element)
            is TemplateParameter -> highlightNotReference(element)
            else -> if (element.elementType == DlangTypes.ID) { highlightIdentifier(element) }
                    else null
        } ?: return

        partToHighlight?:return

        holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(partToHighlight).textAttributes(color.textAttributesKey).create()
    }

    private fun highlightReference(element: PsiElement): Pair<TextRange, DColor>? {
        val identifier = when (element) {
            is ReferenceExpression -> element.identifier
            is TemplateSingleArgument -> element.identifier
            is QualifiedIdentifier -> element.identifier
            else -> element
        } ?: return null

        val basicResolveResult = BasicResolve.getInstance(element.project, false)
            .findDefinitionNode(element)

        val result = basicResolveResult.firstOrNull() ?: return null

        val color = colorForReferenced(result) ?: return null
        val part = partToHighlight(identifier) ?: return null

        return part to color
    }

    private fun highlightNotReference(element: PsiElement): Pair<TextRange, DColor>? {
        val color = colorFor(element) ?: return null
        val part = partToHighlight(element) ?: return null

        return part to color
    }

    private fun highlightIdentifier(element: PsiElement): Pair<TextRange, DColor>? {
        val color = when (val parent = element.parent) {
            is FunctionDeclaration,
            is TemplateDeclaration,
            is TemplateMixinDeclaration -> DColor.FUNCTION_DEFINITION
            is TemplateInstance -> {
                // don’t colorize templated class/struct/union instantiations as function calls
                if (PsiTreeUtil.getParentOfType(parent, DLanguageBasicType::class.java, true, Declaration::class.java) == null)
                    DColor.FUNCTION_CALL
                else
                    null
            }

            else -> null
        } ?: return null

        return element.textRange to color
    }

    private fun colorFor(element: PsiElement): DColor? = when (element) {
        is TemplateSingleArgument,
        is TemplateTypeParameter,
        is TemplateParameter -> DColor.TYPE_PARAMETER
        else -> null
    }

    /**
     * Return the color for a PsiElement that has `element` as reference
     * @param element: the reference of the element to color
     */
    private fun colorForReferenced(element: PsiElement): DColor? = when (element) {
        is TemplateSingleArgument,
        is TemplateTypeParameter,
        is TemplateParameter -> DColor.TYPE_PARAMETER
        is FunctionDeclaration -> {
            // We ignore properties here because they behave like struct/class field
            if (element.isProperty)
                null
            else
                DColor.FUNCTION_CALL
        }
        is TemplateDeclaration,
        is TemplateMixinDeclaration -> DColor.FUNCTION_CALL
        is AliasInitializer -> {
            // Colorize standard alias types as if they were keywords
            if (element.identifier?.text in DlangAliasedTypes)
                DColor.KEYWORD
            else
                null
        }
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
            is ReferenceExpression -> element.identifier
            is TemplateSingleArgument -> element.identifier
            else -> element
        }

        return name?.textRange
    }
}
