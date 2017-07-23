package net.masterthought.dlanguage.processors

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.ResolveState
import net.masterthought.dlanguage.psi.DPsiUtil.getNumParameters
import net.masterthought.dlanguage.utils.Constructor
import net.masterthought.dlanguage.utils.NewExpression

/**
 * Created by francis on 7/22/2017.
 */
class DConstructorProcessor(val e: PsiNamedElement, val newExpression: NewExpression) : DResolveProcessor<NewExpression, Constructor> {
    override fun matches(call: NewExpression, decl: Constructor): Boolean {
        if (decl.name == e.name) {
            val range = getNumParameters(decl.parameters!!)
            val numArgs = newExpression.arguments!!.argumentList!!.assignExpressions.size
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
