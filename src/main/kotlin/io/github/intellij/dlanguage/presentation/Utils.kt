package io.github.intellij.dlanguage.presentation

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import io.github.intellij.dlanguage.icons.DlangIcons
import io.github.intellij.dlanguage.psi.*
import io.github.intellij.dlanguage.structure.Visibility
import io.github.intellij.dlanguage.structure.fromPsiAttribute
import io.github.intellij.dlanguage.utils.*
import javax.swing.Icon

fun presentableName(psi: PsiElement?): String? = when (psi) {
    is Constructor -> "this"
    is PsiNamedElement -> psi.name
    is DLanguageDeclaration -> presentableName(psi.functionDeclaration)
    is DLanguageStructBody -> presentableName(psi.parent)
    is VariableDeclaration -> {
        if (psi.autoDeclaration == null) {
            psi.declarators.firstOrNull()?.identifier?.name
        } else {
            psi.autoDeclaration?.autoDeclarationParts?.firstOrNull()?.name
        }
    }
    else -> psi.toString()
}

fun getPresentationIcon(psi: PsiElement?): Icon? = when (psi) {
    is DLanguageClassDeclaration -> DlangIcons.NODE_CLASS
    is DLanguageInterfaceDeclaration -> DlangIcons.NODE_INTERFACE
    is FunctionDeclaration -> {
        when {
            psiElementIsProperty(psi) -> DlangIcons.NODE_PROPERTY
            psiElementIsMethod(psi) -> DlangIcons.NODE_METHOD
            else -> DlangIcons.NODE_FUNCTION
        }
    }
    is Constructor -> DlangIcons.NODE_METHOD
    is InterfaceOrClass -> getPresentationIcon(psi.parent)
    is EnumDeclaration -> DlangIcons.NODE_ENUM
    is StructDeclaration -> DlangIcons.NODE_STRUCT
    is DLanguageStructBody -> getPresentationIcon(psi.parent)
    is VariableDeclaration -> DlangIcons.NODE_FIELD
    is DlangFile -> DlangIcons.FILE
    else -> null
}

fun psiElementIsProperty(psi: FunctionDeclaration): Boolean {
    val parent = psi.parent as? DLanguageDeclaration ?: return false

    parent.attributes.forEach {
        if (it.atAttribute?.identifier?.name == "property")
            return true
    }

    return false
}

fun psiElementIsGetter(psi: FunctionDeclaration): Boolean =
    psiElementIsProperty(psi) && psi.parameters?.parameters?.size == 0

fun psiElementIsSetter(psi: FunctionDeclaration): Boolean =
    psiElementIsProperty(psi) && psi.parameters?.parameters?.size == 1

fun psiElementIsMethod(psi: PsiElement?): Boolean {
    if (psi == null) {
        return false
    }

    var parent = psi.parent

    while (parent != null) {
        val isMethod = when (parent) {
            is DlangInterfaceOrClass -> true
            is DlangStructDeclaration -> true
            else -> false
        }

        if (isMethod) {
            return true
        }

        parent = parent.parent
    }

    return false
}

// TODO: rm hardcode - 15 and 11
fun psiElementShortText(psi: PsiElement): String {
    val text = psi.text.trim()

    return if (text.length > 15) {
        text.subSequence(0, 11).toString() + " ..."
    } else {
        text
    }
}

fun psiElementGetVisibility(psi: PsiElement?): Visibility {
    if (psi == null || !psiElementIsMethod(psi)) {
        return Visibility.NONE
    }

    fun extractNodeVisibility(psi: PsiElement?): Visibility {
        if (psi == null) {
            return Visibility.NONE
        }

        when (psi) {
            is DLanguageDeclaration -> {
                psi.attributes
                    .map { fromPsiAttribute(it) }
                    .filter { it != Visibility.NONE }
                    .forEach { return it }

                var sibling = psi.prevSibling

                while (sibling != null) {
                    if (sibling is DLanguageDeclaration && sibling.attributeDeclaration != null) {
                        sibling.attributes
                            .map { fromPsiAttribute(it) }
                            .filter { it != Visibility.NONE }
                            .forEach { return it }
                    }

                    sibling = sibling.prevSibling
                }

                return extractNodeVisibility(psi.parent)
            }
            is DLanguageClassDeclaration -> {
                return Visibility.PUBLIC
            }
            is DlangStructDeclaration -> {
                return Visibility.PUBLIC
            }
            else -> return extractNodeVisibility(psi.parent)
        }
    }

    return extractNodeVisibility(psi)
}
