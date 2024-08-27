package io.github.intellij.dlanguage.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveResult

class DResolveResult(private val element: PsiElement) : ResolveResult {
    override fun getElement(): PsiElement = element

    override fun isValidResult(): Boolean = true
}
