package io.github.intellij.dlanguage.structure

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.pom.Navigatable
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.icons.addVisibilityToIcon
import io.github.intellij.dlanguage.presentation.getPresentationIcon
import io.github.intellij.dlanguage.presentation.presentableName
import io.github.intellij.dlanguage.presentation.psiElementGetVisibility
import io.github.intellij.dlanguage.psi.*
import io.github.intellij.dlanguage.utils.Constructor
import io.github.intellij.dlanguage.utils.FunctionDeclaration

class DStructureViewElement(val element: PsiElement) : StructureViewTreeElement,
    Navigatable by (element as NavigatablePsiElement) {
    override fun getPresentation(): ItemPresentation {
        val presentation = buildString {
            fun appendCommaList(xs: List<String>?) {
                if (xs == null) {
                    return
                }

                append('(')
                append(xs.joinToString(", "))
                append(')')
            }

            append(presentableName(element))

            when (element) {
                is FunctionDeclaration -> {
                    val parametersNode = element.parameters
                    appendCommaList(parametersNode?.parameters?.mapNotNull { it.type?.text })
                    append(" : ${element.type?.text}")
                }
                is Constructor -> {
                    val parametersNode = element.parameters
                    appendCommaList(parametersNode?.parameters?.mapNotNull { it.type?.text })
                }
            }
        }

        var icon = getPresentationIcon(element)

        when (element) {
            is FunctionDeclaration -> {
                if (icon != null) {
                    val visibility = psiElementGetVisibility(element)

                    if (visibility != Visibility.NONE)
                        icon = addVisibilityToIcon(icon, visibility)
                }
            }
        }

        return PresentationData(presentation, null, icon, null)
    }

    override fun getChildren(): Array<TreeElement> {
        val treeElements = element.children
            .sortedBy { it.textOffset }
            .mapNotNull { findContentNode(it) }
            .map { DStructureViewElement(it) }

        return treeElements.toTypedArray()
    }

    private fun findContentNode(psi: PsiElement?): PsiElement? = when (psi) {
        is DLanguageDeclaration -> {
            psi.children
                .map { findContentNode(it) }
                .firstOrNull { it != null }
        }
        is DLanguageClassDeclaration -> psi.interfaceOrClass?.structBody
        is DLanguageInterfaceDeclaration -> psi.interfaceOrClass?.structBody
        is FunctionDeclaration -> psi
        else -> null
    }

    override fun getValue(): Any = element
}
