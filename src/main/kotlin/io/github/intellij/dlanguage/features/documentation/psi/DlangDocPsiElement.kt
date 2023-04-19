package io.github.intellij.dlanguage.features.documentation.psi

import com.intellij.psi.PsiElement

interface DlangDocPsiElement : PsiElement {
    fun getDescriptionElements(): Array<PsiElement>
}
