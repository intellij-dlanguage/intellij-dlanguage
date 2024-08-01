package io.github.intellij.dlanguage.refactoring

import com.intellij.lang.refactoring.RefactoringSupportProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.resolve.DResolveUtil.Companion.getInstance

/**
 * Created by francis on 4/18/2017.
 */
class DRefactoringSupportProvider : RefactoringSupportProvider() {
    override fun isSafeDeleteAvailable(element: PsiElement): Boolean {
        if (element !is DNamedElement) return false
        val resolve = getInstance(element.getProject()).findDefinitionNode(
            (element as PsiNamedElement), false
        )
        return resolve.size == 1
    }
}
