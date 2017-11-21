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
    is Declaration -> presentableName(psi.functionDeclaration)
    is StructBody -> presentableName(psi.parent)
    is VariableDeclaration -> {
        if (psi.autoDeclaration == null) {
            psi.declarators.firstOrNull()?.identifier?.name
        } else {
            psi.autoDeclaration?.autoDeclarationParts?.firstOrNull()?.name
        }
    }
    is AliasDeclaration -> {
        psi.aliasInitializers.joinToString(", ") { it.name }
    }
    else -> psi.toString()
}

fun getPresentationIcon(psi: PsiElement?): Icon? = when (psi) {
    is ClassDeclaration -> DlangIcons.NODE_CLASS
    is InterfaceDeclaration -> DlangIcons.NODE_INTERFACE
    is FunctionDeclaration -> {
        when {
            psiElementIsGetter(psi) -> DlangIcons.NODE_PROPERTY_GETTER
            psiElementIsSetter(psi) -> DlangIcons.NODE_PROPERTY_SETTER
            psiElementIsProperty(psi) -> DlangIcons.NODE_PROPERTY
            psiElementIsMethod(psi) -> DlangIcons.NODE_METHOD
            else -> DlangIcons.NODE_FUNCTION
        }
    }
    is Constructor -> DlangIcons.NODE_METHOD
    is InterfaceOrClass -> getPresentationIcon(psi.parent)
    is EnumDeclaration -> DlangIcons.NODE_ENUM
    is StructDeclaration -> DlangIcons.NODE_STRUCT
    is UnionDeclaration -> DlangIcons.NODE_UNION
    is StructBody -> getPresentationIcon(psi.parent)
    is VariableDeclaration -> DlangIcons.NODE_FIELD
    is AliasDeclaration -> DlangIcons.NODE_ALIAS
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
            is InterfaceOrClass -> true
            is StructDeclaration -> true
            is UnionDeclaration -> true
            else -> false
        }

        if (isMethod) {
            return true
        }

        parent = parent.parent
    }

    return false
}

fun psiElementShortText(psi: PsiElement): String {
    val text = psi.text.trim()
    val maxLength = 40

    return if (text.length > maxLength) {
        text.subSequence(0, maxLength - 3).toString() + " ..."
    } else {
        text
    }
}

fun psiElementGetVisibility(psi: PsiElement?): Visibility {
    if (psi == null) {
        return Visibility.NONE
    }

    fun extractNodeVisibility(psi: PsiElement?): Visibility {
        if (psi == null) {
            return Visibility.NONE
        }

        when (psi) {
            is InterfaceOrClass -> return Visibility.PUBLIC
            is StructDeclaration -> return Visibility.PUBLIC
            is EnumDeclaration -> return Visibility.PUBLIC
            is UnionDeclaration -> return Visibility.PUBLIC
            is Declaration -> {
                psi.attributes
                    .map { fromPsiAttribute(it) }
                    .filter { it != Visibility.NONE }
                    .forEach { return it }

                var sibling = psi.prevSibling

                while (sibling != null) {
                    if (sibling is Declaration && sibling.attributeDeclaration != null) {
                        sibling.attributes
                            .map { fromPsiAttribute(it) }
                            .filter { it != Visibility.NONE }
                            .forEach { return it }
                    }

                    sibling = sibling.prevSibling
                }

                return extractNodeVisibility(psi.parent)
            }
            else -> return extractNodeVisibility(psi.parent)
        }
    }

    return extractNodeVisibility(psi)
}
