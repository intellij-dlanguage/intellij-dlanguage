package io.github.intellij.dlanguage.structure

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.pom.Navigatable
import com.intellij.psi.PsiElement
import com.intellij.ui.RowIcon
import com.intellij.util.PlatformIcons
import io.github.intellij.dlanguage.presentation.*
import io.github.intellij.dlanguage.psi.DlangPsiFile
import io.github.intellij.dlanguage.psi.interfaces.Declaration
import io.github.intellij.dlanguage.psi.interfaces.VariableDeclaration
import io.github.intellij.dlanguage.utils.*
import java.util.*
import javax.swing.Icon

class DStructureViewElement(val element: PsiElement) : StructureViewTreeElement
{
    private fun variableDeclarationAttributes(element: VariableDeclaration): String? {
        val allowedAttributes = arrayOf("const", "enum", "immutable")

        if (element is AutoDeclaration) {
            val attributes = element.storageClasss

            for (attribute in attributes) {
                if (attribute.text in allowedAttributes) {
                    return attribute.text
                }
            }

        } else if (element is SpecifiedVariableDeclaration) {
            var attributes = ""
            element.storageClasss.forEach {
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
                        psiElementIsGetter(element) -> append(" : ${element.basicType?.text}${element.typeSuffixes.joinToString(""){ it.text }}")
                        psiElementIsSetter(element) -> {
                            val parametersNode = element.parameters
                            appendCommaList(parametersNode?.parameters?.mapNotNull { presentableName(it.type) })
                        }
                        else -> {
                            val parametersNode = element.parameters
                            appendCommaList(parametersNode?.parameters?.mapNotNull { presentableName(it.type) })
                            append(" : ${element.basicType?.text}${element.typeSuffixes.joinToString(""){ it.text }}")
                        }
                    }
                }
                is Constructor -> {
                    val parametersNode = element.parameters
                    appendCommaList(parametersNode?.parameters?.mapNotNull { presentableName(it.type) })
                }
                is SpecifiedVariableDeclaration -> {
                    append(" : ${element.basicType?.text}${element.typeSuffixs.joinToString("") { it.text }}")

                    val initializer = element.identifierInitializers.firstOrNull()?.initializer

                    if (initializer != null) {
                        append(" = ${psiElementShortText(initializer)}")
                    }
                }
                is AutoDeclaration -> {
                    val parts = element.autoAssignments
                    val declPart = parts.firstOrNull()

                    if (declPart != null) {
                        val initializer = declPart.initializer

                        if (initializer != null) {
                            append(" = ${psiElementShortText(initializer)}")
                        }
                    }
                }
                is AliasDeclaration -> {
                    if (element.type != null) {
                        append(" = ${psiElementShortText(element.type!!)}")
                    }
                }
                is EnumMember -> {
                    var elementType = ""
                    if (element.parent is AnonymousEnumDeclaration)
                        elementType = (element.parent as AnonymousEnumDeclaration).type?.text?:""
                    else if (element.parent is EnumBody && element.parent.parent is EnumDeclaration)
                        elementType = (element.parent.parent as EnumDeclaration).type?.text?: (element.parent.parent as EnumDeclaration).identifier!!.text
                    append(": ${elementType}")
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

        findChildrenNodes(element).forEach {
            treeElements.add(DStructureViewElement(it))
        }

        return treeElements.toTypedArray()
    }

    override fun navigate(requestFocus: Boolean) {
        (element as? Navigatable)?.navigate(requestFocus)
    }

    override fun canNavigate(): Boolean = (element is Navigatable) && element.canNavigate()

    override fun canNavigateToSource(): Boolean = (element is Navigatable) && element.canNavigateToSource()

    private fun findChildrenNodes(psi: PsiElement?): List<PsiElement> {
        val elements = when (psi) {
            is DlangPsiFile -> {
                psi.children.filter { it !is ModuleDeclaration }
                    .filterIsInstance<Declaration>()
            }
            is ClassDeclaration -> psi.structBody?.declarations?: emptyList()
            is InterfaceDeclaration -> psi.structBody?.declarations?: emptyList()
            is FunctionDeclaration -> emptyList()
            is EnumDeclaration -> psi.enumBody?.enumMembers?: emptyList()
            is AnonymousEnumDeclaration -> psi.enumMembers
            is StructDeclaration -> psi.structBody?.declarations?: emptyList()
            is UnionDeclaration -> psi.structBody?.declarations?: emptyList()
            is Constructor -> emptyList()
            is VariableDeclaration -> emptyList()
            is AliasDeclaration -> emptyList()
            is MixinTemplateDeclaration -> psi.templateDeclaration?.declarations?: emptyList()
            is ConditionalDeclaration -> psi.declarations
            is AttributeSpecifier -> psi.declarationBlock?.declarations?: emptyList()
            else -> emptyList()
        }
        return findInnerDeclarations(elements)
    }

    private fun findInnerDeclarations(declarations: List<PsiElement>): List<PsiElement> =
        declarations.filter { it !is ImportDeclaration && it !is EmptyDeclaration && it !is Unittest }
            .flatMap {
                when (it) {
                    is AttributeSpecifier -> findInnerDeclarations(it.declarationBlock?.declarations?: emptyList())
                    is ConditionalDeclaration -> findInnerDeclarations(it.declarations)
                    else -> listOf(it)
                }
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
