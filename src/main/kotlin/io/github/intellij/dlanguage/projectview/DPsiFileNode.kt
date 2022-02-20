package io.github.intellij.dlanguage.projectview

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.AbstractPsiBasedNode
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.ui.SimpleTextAttributes
import io.github.intellij.dlanguage.psi.DlangFile

/**
 * Each instance of this class corresponds to a D language file
 * in the project view.
 */
class DPsiFileNode(project: Project?, dFilePsi: DlangFile, viewSettings: ViewSettings) :
    AbstractPsiBasedNode<DlangFile>(project, dFilePsi, viewSettings) {

    override fun getTypeSortKey(): Comparable<*> = TypeSortKey(this)

    override fun updateImpl(data: PresentationData) {
        val filePsi: DlangFile? = value
        if (filePsi != null) {
            val fileName = filePsi.virtualFile.nameWithoutExtension
            if (fileName == "package") {
                data.addText("package.${filePsi.virtualFile.extension}", SimpleTextAttributes.REGULAR_ATTRIBUTES)
            } else {
                val presentableModuleName = filePsi.getModuleName()
                val presentableFileName: String? =
                    if (fileName != presentableModuleName) {
                        "$fileName.${filePsi.virtualFile.extension}"
                    } else {
                        null
                    }

                data.addText("$presentableModuleName   ", SimpleTextAttributes.REGULAR_ATTRIBUTES)
                presentableFileName?.let { data.addText(ColoredFragment(it, SimpleTextAttributes.GRAYED_ATTRIBUTES)) }
            }
        }
    }

    override fun getChildrenImpl(): Collection<AbstractTreeNode<*>> {
        // TODO: Eventually the plugin could support the "Show Members" feature of the project view.
        return emptyList()
    }

    override fun extractPsiFromValue(): PsiElement? = value

    override fun getTypeSortWeight(sortByType: Boolean): Int {
        return if (sortByType) {
            if (value?.virtualFile?.nameWithoutExtension == "package") {
                1
            } else {
                0
            }
        } else {
            super.getTypeSortWeight(sortByType)
        }
    }

    private class TypeSortKey(node: DPsiFileNode) : Comparable<TypeSortKey>
    {
        private val unqualifiedModuleName = node.value.getModuleName()

        override fun compareTo(other: TypeSortKey) = unqualifiedModuleName.compareTo(other.unqualifiedModuleName)
    }
}
