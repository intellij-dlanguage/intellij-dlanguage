package io.github.intellij.dlanguage.resolve.processors.parameters

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.ResolveState
import io.github.intellij.dlanguage.resolve.processors.DResolveProcessor
import io.github.intellij.dlanguage.utils.DPsiUtil.getNumParameters
import io.github.intellij.dlanguage.utils.FunctionCallExpression
import io.github.intellij.dlanguage.utils.FunctionDeclaration

/**
 * Created by francis on 7/22/2017.
 */
class DFunctionProcessor(val e: PsiNamedElement, val functionCallExpression: FunctionCallExpression) : DResolveProcessor<FunctionCallExpression, FunctionDeclaration> {
    private fun searchUpAndLeft() {

    }

    override fun matches(call: FunctionCallExpression, decl: FunctionDeclaration): Boolean {
        //todo change this to check that the call name is the same
        if (e.name == decl.name) {
            val range = getNumParameters(decl.parameters!!, decl.identifier!!)
            if (call.arguments?.argumentList == null) {
                return range.matches(0)
            }
            val size = call.arguments!!.argumentList!!.assignExpressions.size

            if (range.matches(size)) {
                return true
            }
        }
        return false
    }

    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        if (element is FunctionDeclaration) {
            if (matches(functionCallExpression, element)) {
                result.add(element)
            }
        }
        return true
    }

    override val result: MutableSet<FunctionDeclaration> = mutableSetOf()

}

