package io.github.intellij.dlanguage.projectview

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.BasePsiNode
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.io.FileUtil
import com.intellij.ui.SimpleTextAttributes
import io.github.intellij.dlanguage.psi.DlangPsiFile

/**
 * Each instance of this class corresponds to a D language file
 * in the project view.
 */
class DPsiFileNode(project: Project?, dFilePsi: DlangPsiFile, viewSettings: ViewSettings) :
    BasePsiNode<DlangPsiFile>(project, dFilePsi, viewSettings) {

    override fun getTypeSortKey(): Comparable<*> = TypeSortKey(this)

    override fun getTitle(): String? {
        return if (virtualFile != null) FileUtil.getLocationRelativeToUserHome(virtualFile!!.presentableUrl) else super.getTitle()
    }

    override fun canRepresent(element: Any?): Boolean {
        if (super.canRepresent(element)) return true

        val value: DlangPsiFile? = value
        return value != null && element != null && element == value.virtualFile
    }

    override fun updateImpl(data: PresentationData) {
        val filePsi: DlangPsiFile? = value
        if (filePsi != null) {
            data.presentableText = value.getModuleName()
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
