package io.github.intellij.dlanguage.resolve.processors.basic

import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.resolve.processors.DResolveProcessor

/**
 * Created by francis on 6/15/2017.
 */
class DAllNameScopeProcessor(var start: PsiElement) : DResolveProcessor<DNamedElement, DNamedElement> {
    override fun matches(call: DNamedElement, decl: DNamedElement): Boolean {
        return true
    }

    override val result = mutableSetOf<DNamedElement>()

    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        if (element is DNamedElement) {
            if (element.name == start.text) {
                result.add(element)
            }
        } else {
            throw IllegalStateException()
        }
        return true
    }

}
