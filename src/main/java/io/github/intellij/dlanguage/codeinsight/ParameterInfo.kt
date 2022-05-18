package io.github.intellij.dlanguage.codeinsight

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.lang.parameterInfo.*
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.util.PsiTreeUtil.findChildrenOfType
import com.intellij.psi.util.PsiTreeUtil.getParentOfType
import io.github.intellij.dlanguage.psi.DLanguageFunctionCallExpression
import io.github.intellij.dlanguage.psi.references.DReference
import io.github.intellij.dlanguage.resolve.DResolveUtil
import io.github.intellij.dlanguage.utils.*
import kotlin.streams.toList

private fun removeParentheses(parameterText: String): String {
    var text = parameterText
    if (text.first() == '(')
        text = text.drop(1)
    if (text.last() == ')')
        text = text.dropLast(1)
    return text
}


class ParameterInfo : ParameterInfoHandler<FunctionCallExpression, Pair<TemplateParameters?, Parameters>> {
    override fun updateParameterInfo(parameterOwner: FunctionCallExpression, context: UpdateParameterInfoContext) {

    }

    override fun getParametersForDocumentation(p: Pair<TemplateParameters?, Parameters>?, context: ParameterInfoContext?): Array<Any>? {
        TODO("this doesn't need to be implemented b/c it is not used by intellij-core")
    }

    override fun tracksParameterIndex(): Boolean {
        TODO("this doesn't need to be implemented b/c it is not used by intellij-core")
    }

    override fun showParameterInfo(functionCallExpression: FunctionCallExpression, context: CreateParameterInfoContext) {
        var reference = functionCallExpression.unaryExpression?.primaryExpression?.identifierOrTemplateInstance?.identifier?.reference
        if (reference == null) {
            reference = functionCallExpression.unaryExpression?.primaryExpression?.identifierOrTemplateInstance?.templateInstance?.identifier?.reference
        }
        if (reference == null || reference !is DReference) {
            return
        }
        val definitionNodes = DResolveUtil.getInstance(functionCallExpression.project).findDefinitionNode(reference.element, false)
        // The following code may not clear.
        // Struct construction can be syntactically identical to function calling.
        // In the special case of struct construction we want too find parameters
        // from struct constructors.
        context.itemsToShow = definitionNodes.flatMap { functionOrStructDefinition: PsiNamedElement ->
            if (functionOrStructDefinition is StructDeclaration) {
                val structConstructors = functionOrStructDefinition.structBody?.declarations.orEmpty().map { it.constructor }.filterNotNull()
                structConstructors.map { Pair(it.templateParameters, it.parameters) }
            } else if (functionOrStructDefinition is FunctionDeclaration) {
                val funcDecl = functionOrStructDefinition
                listOf(Pair(funcDecl.templateParameters, funcDecl.parameters))
            } else
                emptyList()
        }.filterNotNull().toTypedArray()
        context.showHint(functionCallExpression, 0, this)
    }

    override fun updateUI(p: Pair<TemplateParameters?, Parameters>, context: ParameterInfoUIContext) {
        context.setupRawUIComponentPresentation((p.first?.text?.plus(" ! ")).orEmpty() + p.second.text)
    }

    override fun findElementForUpdatingParameterInfo(context: UpdateParameterInfoContext): FunctionCallExpression? {
        val listStart = context.offset
        val file = context.file
        val element = file.findElementAt(listStart)
        val functionCallExpression = getParentOfType(element, DLanguageFunctionCallExpression::class.java)
        return functionCallExpression
    }

    override fun getParameterCloseChars(): String? {
        TODO("this doesn't need to be implemented because it is not used by intellij-core")
    }

    override fun getParametersForLookup(item: LookupElement, context: ParameterInfoContext): Array<Any>? {
        //todo I'm not sure what this is meant to do. However going off of intellij source and other plugins it is safe to return null
        return null
    }

    override fun couldShowInLookup(): Boolean {
        return true
    }

    override fun findElementForParameterInfo(context: CreateParameterInfoContext): FunctionCallExpression? {
        val listStart = context.offset
        val file = context.file
        val element = file.findElementAt(listStart)
        val functionCallExpression = getParentOfType(element, DLanguageFunctionCallExpression::class.java)
        return functionCallExpression
    }


}


class ConstructorParameterInfo : ParameterInfoHandler<NewExpression, Parameters> {
    override fun updateParameterInfo(parameterOwner: NewExpression, context: UpdateParameterInfoContext) {

    }

    override fun getParametersForDocumentation(p: Parameters?, context: ParameterInfoContext?): Array<Any>? {
        TODO("this doesn't need to be implemented b/c it is not used by intellij-core")
    }

    override fun tracksParameterIndex(): Boolean {
        TODO("this doesn't need to be implemented b/c it is not used by intellij-core")
    }

    override fun showParameterInfo(newExpression: NewExpression, context: CreateParameterInfoContext) {
        val reference = newExpression.type?.type_2?.typeIdentifierPart?.identifierOrTemplateInstance?.identifier?.reference
        if (reference == null || reference !is DReference) {
            return
        }
        val definitionNodes = DResolveUtil.getInstance(newExpression.project).findDefinitionNode(reference.element, false)
        val classDecls: List<InterfaceOrClass> = definitionNodes.filter { it.parent is ClassDeclaration }.filterIsInstance(InterfaceOrClass::class.java)
        val constructors = classDecls.flatMap { it.structBody?.declarations.orEmpty() }.map { it.constructor }.filterNotNull()
        context.itemsToShow = constructors.flatMap { findChildrenOfType(it, Parameters::class.java) }.filterNotNull().toTypedArray()
        context.showHint(newExpression, 0, this)
    }

    override fun updateUI(p: Parameters, context: ParameterInfoUIContext) {
        context.setupRawUIComponentPresentation(removeParentheses(p.text))
    }

    override fun findElementForUpdatingParameterInfo(context: UpdateParameterInfoContext): NewExpression? {
        return findNewExpressionFromCursor(context)
    }

    override fun getParameterCloseChars(): String? {
        TODO("this doesn't need to be implemented because it is not used by intellij-core")
    }

    override fun getParametersForLookup(item: LookupElement, context: ParameterInfoContext): Array<Any>? {
        //todo I'm not sure what this is meant to do. However going off of intellij source and other plugins it is safe to return null
        return null
    }

    override fun couldShowInLookup(): Boolean {
        return true
    }

    override fun findElementForParameterInfo(context: CreateParameterInfoContext): NewExpression? {
        return findNewExpressionFromCursor(context)
    }

    private fun findNewExpressionFromCursor(context: ParameterInfoContext): NewExpression? {
        val listStart = context.offset
        val file = context.file
        val element = file.findElementAt(listStart)
        val newExpression = getParentOfType(element, NewExpression::class.java)
        return newExpression
    }


}


class TemplateParameterInfo : ParameterInfoHandler<TemplateInstance, TemplateParameters> {
    override fun updateParameterInfo(parameterOwner: TemplateInstance, context: UpdateParameterInfoContext) {

    }

    override fun getParametersForDocumentation(p: TemplateParameters?, context: ParameterInfoContext?): Array<Any>? {
        TODO("this doesn't need to be implemented b/c it is not used by intellij-core")
    }

    override fun tracksParameterIndex(): Boolean {
        TODO("this doesn't need to be implemented b/c it is not used by intellij-core")
    }

    override fun showParameterInfo(templateExpression: TemplateInstance, context: CreateParameterInfoContext) {
        val reference = templateExpression.identifier?.reference
        if (reference == null || reference !is DReference) {
            return
        }
        val definitionNodes = DResolveUtil.getInstance(templateExpression.project).findDefinitionNode(reference.element, false).filterIsInstance(TemplateDeclaration::class.java)
        context.itemsToShow = definitionNodes.map { it.templateParameters }.filterNotNull().toTypedArray()

        context.showHint(templateExpression, 0, this)
    }

    override fun updateUI(p: TemplateParameters, context: ParameterInfoUIContext) {
        context.setupRawUIComponentPresentation(removeParentheses(p.text))
    }

    override fun findElementForUpdatingParameterInfo(context: UpdateParameterInfoContext): TemplateInstance? {
        return findTemplateInstanceFromCursor(context)
    }

    override fun getParameterCloseChars(): String? {
        TODO("this doesn't need to be implemented because it is not used by intellij-core")
    }

    override fun getParametersForLookup(item: LookupElement, context: ParameterInfoContext): Array<Any>? {
        //todo I'm not sure what this is meant to do. However going off of intellij source and other plugins it is safe to return null
        return null
    }

    override fun couldShowInLookup(): Boolean {
        return true
    }

    override fun findElementForParameterInfo(context: CreateParameterInfoContext): TemplateInstance? {
        return findTemplateInstanceFromCursor(context)
    }

    private fun findTemplateInstanceFromCursor(context: ParameterInfoContext): TemplateInstance? {
        val listStart = context.offset
        val file = context.file
        val element = file.findElementAt(listStart)
        val newExpression = getParentOfType(element, TemplateInstance::class.java)
        return newExpression
    }


}
