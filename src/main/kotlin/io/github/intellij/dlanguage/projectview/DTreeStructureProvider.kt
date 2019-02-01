package io.github.intellij.dlanguage.projectview

import com.intellij.ide.projectView.TreeStructureProvider
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.ide.util.treeView.AbstractTreeNode

/**
 * Modifies the Project View tree to better fit the D workflow and to take
 * advantage of features offered by the IDEA plugin API.
 *
 * D source files get their own special node type here ([DPsiFileNode]).
 *
 * Based on the Kotlin plugin's Project TreeStructureProvider:
 * [https://github.com/JetBrains/kotlin/blob/26413acf33a86375e1c63b9dae9fdefbe75b0561/idea/src/org/jetbrains/kotlin/idea/projectView/projectViewProviders.kt]
 */
class DTreeStructureProvider : TreeStructureProvider {
    override fun modify(parent: AbstractTreeNode<*>,
                        children: MutableCollection<AbstractTreeNode<*>>,
                        settings: ViewSettings): Collection<AbstractTreeNode<*>> {

        return children.map { child ->
            if (child is PsiFileNode) {
                val dlangFile = child.dlangFile
                if (dlangFile != null) {
                    DPsiFileNode(child.project, dlangFile, settings)
                } else {
                    child
                }
            } else {
                child
            }
        }
    }
}
