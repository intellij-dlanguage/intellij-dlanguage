package io.github.intellij.dlanguage.presentation

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import io.github.intellij.dlanguage.icons.DlangIcons
import io.github.intellij.dlanguage.psi.*
import io.github.intellij.dlanguage.utils.FunctionDeclaration
import io.github.intellij.dlanguage.utils.InterfaceOrClass
import javax.swing.Icon

fun presentableName(psi: PsiElement?): String? = when (psi) {
    is PsiNamedElement -> psi.name
    is DLanguageDeclaration -> presentableName(psi.functionDeclaration)
    is DLanguageStructBody -> presentableName(psi.parent)
    else -> psi.toString()
}

fun getPresentationIcon(psi: PsiElement?): Icon? = when (psi) {
    is DLanguageClassDeclaration -> DlangIcons.NODE_CLASS
    is DLanguageInterfaceDeclaration -> DlangIcons.NODE_INTERFACE
    is FunctionDeclaration -> DlangIcons.NODE_FUNCTION
    is InterfaceOrClass -> getPresentationIcon(psi.parent)
    is DLanguageStructBody -> getPresentationIcon(psi.parent)
    is DlangFile -> DlangIcons.FILE
    else -> null
}
