package io.github.intellij.dlanguage.refactoring

import com.intellij.lang.refactoring.RefactoringSupportProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiPolyVariantReference
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement

/**
 * Created by francis on 4/18/2017.
 */
class DRefactoringSupportProvider : RefactoringSupportProvider() {
    override fun isSafeDeleteAvailable(element: PsiElement): Boolean {
        if (element !is DNamedElement) return false
        val reference = element.reference
        if (reference is PsiPolyVariantReference) {
            return reference.multiResolve(false).isEmpty()
        }
        return reference?.resolve() == null
    }
}
