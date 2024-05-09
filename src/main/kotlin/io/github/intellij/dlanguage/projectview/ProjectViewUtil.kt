package io.github.intellij.dlanguage.projectview

import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.psi.PsiManager
import io.github.intellij.dlanguage.psi.DlangPsiFile

/**
 * Returns the [DlangPsiFile] associated with this [PsiFileNode], or
 * `null` if at least one of the following is true for this node:
 *
 *  - Not part of a project.
 *  - Has no associated [VirtualFile][com.intellij.openapi.vfs.VirtualFile].
 *  - Not a D source file.
 */
val PsiFileNode.dlangFile: DlangPsiFile?
    get() = this.project?.let { p -> virtualFile?.let { vf -> PsiManager.getInstance(p).findFile(vf) } } as? DlangPsiFile
