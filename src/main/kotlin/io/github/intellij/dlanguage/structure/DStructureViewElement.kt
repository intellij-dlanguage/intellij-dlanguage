package io.github.intellij.dlanguage.structure

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.pom.Navigatable
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiElement
import com.intellij.ui.RowIcon
import com.intellij.util.PlatformIcons
import io.github.intellij.dlanguage.presentation.*
import io.github.intellij.dlanguage.utils.*
import java.util.*
import javax.swing.Icon

class DStructureViewElement(val element: PsiElement) : StructureViewTreeElement
{
    private fun variableDeclarationAttributes(element: VariableDeclaration): String? {
        val allowedAttributes = arrayOf("const", "enum", "immutable")

        if (element.autoDeclaration != null) {
            val attributes = element.autoDeclaration?.storageClasss!!

            for (attribute in attributes) {
                if (attribute.text in allowedAttributes) {
                    return attribute.text
                }
            }

        } else {
            var attributes = ""

            (element.parent as? Declaration)?.attributes?.forEach {
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
                            appendCommaList(parametersNode?.parameters?.mapNotNull { presentableName(it.type) })
                        }
                        else -> {
                            val parametersNode = element.parameters
                            appendCommaList(parametersNode?.parameters?.mapNotNull { presentableName(it.type) })
                            append(" : ${element.type?.text}")
                        }
                    }
                }
                is Constructor -> {
                    val parametersNode = element.parameters
                    appendCommaList(parametersNode?.parameters?.mapNotNull { presentableName(it.type) })
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
                is AliasDeclaration -> {
                    if (element.type != null) {
                        append(" = ${psiElementShortText(element.type!!)}")
                    }
                }
            }
        }

        var icon = getPresentationIcon(element)

        if (icon == null)
            return PresentationData(presentation, null, icon, null)

        val visibility = psiElementGetVisibility(element)

        if (visibility != Visibility.NONE)
            icon = addVisibilityToIcon(icon, visibility)

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

    override fun navigate(requestFocus: Boolean) {
        (element as? Navigatable)?.navigate(requestFocus)
    }

    override fun canNavigate(): Boolean = (element is Navigatable) && element.canNavigate()

    override fun canNavigateToSource(): Boolean = (element is Navigatable) && element.canNavigateToSource()

    private fun findContentNode(psi: PsiElement?): List<PsiElement?> = when (psi) {
        is Declaration -> {
            val res = ArrayList<PsiElement?>()

            psi.children.forEach {
                res.addAll(findContentNode(it).filterNotNull())
            }

            res
        }
        is ClassDeclaration -> listOf(psi.interfaceOrClass?.structBody)
        is InterfaceDeclaration -> listOf(psi.interfaceOrClass?.structBody)
        is FunctionDeclaration -> listOf(psi)
        is EnumDeclaration -> listOf(psi)
        is StructDeclaration -> listOf(psi.structBody)
        is UnionDeclaration -> listOf(psi.structBody)
        is Constructor -> listOf(psi)
        is VariableDeclaration -> listOf(psi)
        is AliasDeclaration -> listOf(psi)
        is MixinTemplateDeclaration -> listOf(psi.templateDeclaration)
        else -> emptyList()
    }

    override fun getValue(): Any = element

    private fun addVisibilityToIcon(icon: Icon, visibility: Visibility) : RowIcon =
        RowIcon(icon, when (visibility) {
            Visibility.PUBLIC -> PlatformIcons.PUBLIC_ICON
            Visibility.PRIVATE -> PlatformIcons.PRIVATE_ICON
            Visibility.PROTECTED -> PlatformIcons.PROTECTED_ICON
            Visibility.PACKAGE -> PlatformIcons.PACKAGE_LOCAL_ICON
            Visibility.NONE -> PlatformIcons.PUBLIC_ICON
        })
}
