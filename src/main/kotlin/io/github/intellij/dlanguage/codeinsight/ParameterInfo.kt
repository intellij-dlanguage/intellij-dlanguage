package io.github.intellij.dlanguage.codeinsight

import com.intellij.lang.parameterInfo.*
import com.intellij.psi.util.PsiTreeUtil.findChildrenOfType
import com.intellij.psi.util.PsiTreeUtil.getParentOfType
import io.github.intellij.dlanguage.psi.DLanguageFunctionCallExpression
import io.github.intellij.dlanguage.utils.*

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

    override fun showParameterInfo(functionCallExpression: FunctionCallExpression, context: CreateParameterInfoContext) {
        /*var reference = functionCallExpression.unaryExpression?.prmaryExpression?.identifierOrTemplateInstance?.identifier?.reference
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
                val structConstructors = functionOrStructDefinition.structBody?.declarations.orEmpty().filterIsInstance<Constructor>()
                structConstructors.map { Pair(it.templateParameters, it.parameters) }
            } else if (functionOrStructDefinition is FunctionDeclaration) {
                val funcDecl = functionOrStructDefinition
                listOf(Pair(funcDecl.templateParameters, funcDecl.parameters))
            } else
                emptyList()
        }.toTypedArray()
        context.showHint(functionCallExpression, 0, this)*/
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

    override fun showParameterInfo(newExpression: NewExpression, context: CreateParameterInfoContext) {
        val reference = newExpression.type?.basicType?.qualifiedIdentifier?.reference
        if (reference == null) {
            return
        }
        val resolved = reference.resolve()
        if (resolved !is ClassDeclaration) {
            return
        }
        val constructors = resolved.structBody?.declarations.orEmpty().filterIsInstance<Constructor>()
        context.itemsToShow = constructors.flatMap { findChildrenOfType(it, Parameters::class.java) }.filterNotNull().toTypedArray()
        context.showHint(newExpression, 0, this)
    }

    override fun updateUI(p: Parameters, context: ParameterInfoUIContext) {
        context.setupRawUIComponentPresentation(removeParentheses(p.text))
    }

    override fun findElementForUpdatingParameterInfo(context: UpdateParameterInfoContext): NewExpression? {
        return findNewExpressionFromCursor(context)
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

    override fun showParameterInfo(templateExpression: TemplateInstance, context: CreateParameterInfoContext) {
        val reference = templateExpression.reference
        if (reference == null) {
            return
        }
        val resolved = reference.resolve()
        if (resolved !is TemplateDeclaration) {
            return
        }
        context.itemsToShow = arrayOf(resolved.templateParameters)

        context.showHint(templateExpression, 0, this)
    }

    override fun updateUI(p: TemplateParameters, context: ParameterInfoUIContext) {
        context.setupRawUIComponentPresentation(removeParentheses(p.text))
    }

    override fun findElementForUpdatingParameterInfo(context: UpdateParameterInfoContext): TemplateInstance? {
        return findTemplateInstanceFromCursor(context)
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
