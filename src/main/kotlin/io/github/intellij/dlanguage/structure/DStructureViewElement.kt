package io.github.intellij.dlanguage.structure

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.pom.Navigatable
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.icons.addVisibilityToIcon
import io.github.intellij.dlanguage.presentation.*
import io.github.intellij.dlanguage.psi.*
import io.github.intellij.dlanguage.utils.*

class DStructureViewElement(val element: PsiElement) : StructureViewTreeElement,
    Navigatable by (element as NavigatablePsiElement)
{
    private fun variableDeclarationAttributes(element: VariableDeclaration): String? {
        val allowedAttributes = arrayOf("const", "enum", "immutable")

        if (element.autoDeclaration != null) {
            val attribute = element.autoDeclaration?.storageClass?.text

            if (attribute in allowedAttributes) {
                return attribute
            }
        } else {
            var attributes = ""

            (element.parent as? DLanguageDeclaration)?.attributes?.forEach {
                if (it.text in allowedAttributes) {
                    attributes += it.text + " "
                }
            }

            return attributes.trim()
        }

        return null
    }

    override fun getPresentation(): ItemPresentation {
        val presentation = buildString {
            fun appendCommaList(xs: List<String>?, bl: Char = '(', br: Char = ')') {
                if (xs == null) {
                    return
                }

                append(bl)
                append(xs.joinToString(", "))
                append(br)
            }

            // Insert variables attributes
            if (element is VariableDeclaration) {
                val attributes = variableDeclarationAttributes(element)

                if (attributes != null) {
                    append(attributes)

                    if (attributes.isNotEmpty())
                        append(" ")
                }
            }

            append(presentableName(element))

            // Additional information
            when (element) {
                is FunctionDeclaration -> {
                    when {
                        psiElementIsGetter(element) -> append(" : ${element.type?.text}")
                        psiElementIsSetter(element) -> {
                            val parametersNode = element.parameters
                            appendCommaList(parametersNode?.parameters?.mapNotNull { it.type?.text })
                        }
                        else -> {
                            val parametersNode = element.parameters
                            appendCommaList(parametersNode?.parameters?.mapNotNull { it.type?.text })
                            append(" : ${element.type?.text}")
                        }
                    }
                }
                is Constructor -> {
                    val parametersNode = element.parameters
                    appendCommaList(parametersNode?.parameters?.mapNotNull { it.type?.text })
                }
                is VariableDeclaration -> {
                    if (element.autoDeclaration == null) {
                        append(" : ${element.type?.text}")

                        val initializer = element.declarators.firstOrNull()?.initializer

                        if (initializer != null) {
                            append(" = ${psiElementShortText(initializer)}")
                        }
                    } else {
                        val parts = element.autoDeclaration?.autoDeclarationParts
                        val declPart = parts?.firstOrNull()

                        if (declPart != null) {
                            val initializer = declPart.initializer

                            if (initializer != null) {
                                append(" = ${psiElementShortText(initializer)}")
                            }
                        }
                    }
                }
            }
        }

        var icon = getPresentationIcon(element)

        if (element is FunctionDeclaration || element is Constructor || element is VariableDeclaration) {
            if (icon == null)
                return PresentationData(presentation, null, icon, null)

            val visibility = psiElementGetVisibility(element)

            if (visibility != Visibility.NONE)
                icon = addVisibilityToIcon(icon, visibility)
        }

        return PresentationData(presentation, null, icon, null)
    }

    override fun getChildren(): Array<TreeElement> {
        val treeElements = ArrayList<TreeElement>()

        element.children.forEach {
            val nodes = findContentNode(it).filterNotNull()
            nodes.forEach {
                treeElements.add(DStructureViewElement(it))
            }
        }

        return treeElements.toTypedArray()
    }

    private fun findContentNode(psi: PsiElement?): List<PsiElement?> = when (psi) {
        is DLanguageDeclaration -> {
            val res = ArrayList<PsiElement?>()

            psi.children.forEach {
                res.addAll(findContentNode(it).filterNotNull())
            }

            res
        }
        is DLanguageClassDeclaration -> listOf(psi.interfaceOrClass?.structBody)
        is DLanguageInterfaceDeclaration -> listOf(psi.interfaceOrClass?.structBody)
        is FunctionDeclaration -> listOf(psi)
        is EnumDeclaration -> listOf(psi)
        is StructDeclaration -> listOf(psi.structBody)
        is Constructor -> listOf(psi)
        is VariableDeclaration -> listOf(psi)
        else -> emptyList()
    }

    override fun getValue(): Any = element
}
