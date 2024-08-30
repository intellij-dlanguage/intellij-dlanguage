package io.github.intellij.dlanguage.documentation.psi

import com.intellij.psi.PsiDocCommentBase
import com.intellij.psi.PsiElement

interface DlangDocComment : PsiDocCommentBase {

    fun sections(): Array<PsiElement>
}
