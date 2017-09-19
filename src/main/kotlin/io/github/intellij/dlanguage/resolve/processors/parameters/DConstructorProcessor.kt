package io.github.intellij.dlanguage.resolve.processors.parameters

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.ResolveState
import io.github.intellij.dlanguage.resolve.processors.DResolveProcessor
import io.github.intellij.dlanguage.utils.Constructor
import io.github.intellij.dlanguage.utils.DPsiUtil.getNumParameters
import io.github.intellij.dlanguage.utils.NewExpression

/**
 * Created by francis on 7/22/2017.
 */
class DConstructorProcessor(val e: PsiNamedElement, val newExpression: NewExpression) : DResolveProcessor<NewExpression, Constructor> {
    override fun matches(call: NewExpression, decl: Constructor): Boolean {
        if (decl.name == e.name) {
            val range = getNumParameters(decl.parameters!!, decl.kW_THIS!!)
            var numArgs = newExpression.arguments!!.argumentList!!.assignExpressions.size
            val constructorTypeName = call.type?.type_2?.symbol?.identifierOrTemplateChain?.identifierOrTemplateInstances
            if (constructorTypeName?.size!! > 1) {
                val identifierOrTemplateInstance = constructorTypeName[constructorTypeName.size - 2]
                val parentDecl: PsiElement?
                if (identifierOrTemplateInstance.identifier != null) {
                    parentDecl = identifierOrTemplateInstance.identifier!!.reference!!.resolve()
                } else if (identifierOrTemplateInstance.templateInstance?.identifier != null) {
                    parentDecl = identifierOrTemplateInstance.templateInstance!!.identifier!!.reference!!.resolve()
                } else {
                    throw IllegalStateException()
                }
                if (parentDecl != null) {
                    val finder = DAttributesFinder(parentDecl)
                    finder.recurseUp()
                    if ((!finder.isStatic()) || finder.isLocal()) {
                        numArgs++
                    }
                } else {
                    throw IllegalStateException()
                }
            }

            if (range.matches(numArgs)) {
                return true
            }
        }
        return false
    }

    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        if (element is Constructor) {
            if (matches(newExpression, element)) {
                result.add(element)
            }
        }
        return true
    }

    override val result: MutableSet<Constructor> = mutableSetOf()

}
