package io.github.intellij.dlanguage.presentation

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import io.github.intellij.dlanguage.DLanguage
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
    is ClassDeclaration -> DLanguage.Icons.NODE_CLASS
    is InterfaceDeclaration -> DLanguage.Icons.NODE_INTERFACE
    is FunctionDeclaration -> {
        when {
            psiElementIsGetter(psi) -> DLanguage.Icons.NODE_PROPERTY_GETTER
            psiElementIsSetter(psi) -> DLanguage.Icons.NODE_PROPERTY_SETTER
            psiElementIsProperty(psi) -> DLanguage.Icons.NODE_PROPERTY
            psiElementIsMethod(psi) -> DLanguage.Icons.NODE_METHOD
            else -> DLanguage.Icons.NODE_FUNCTION
        }
    }
    is Constructor -> DLanguage.Icons.NODE_METHOD
    is InterfaceOrClass -> getPresentationIcon(psi.parent)
    is EnumDeclaration -> DLanguage.Icons.NODE_ENUM
    is StructDeclaration -> DLanguage.Icons.NODE_STRUCT
    is UnionDeclaration -> DLanguage.Icons.NODE_UNION
    is StructBody -> getPresentationIcon(psi.parent)
    is VariableDeclaration -> DLanguage.Icons.NODE_FIELD
    is AliasDeclaration -> DLanguage.Icons.NODE_ALIAS
    is MixinTemplateDeclaration -> DLanguage.Icons.NODE_MIXIN
    is TemplateDeclaration -> getPresentationIcon(psi.parent)
    is DlangFile -> DLanguage.Icons.FILE
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
