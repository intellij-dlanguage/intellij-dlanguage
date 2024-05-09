package io.github.intellij.dlanguage.refactoring.rename

import com.intellij.psi.PsiFile
import com.intellij.refactoring.actions.RenameFileActionProvider
import io.github.intellij.dlanguage.psi.DlangPsiFile

/**
 * @author Samael Bate (singingbush)
 * created on 21/10/18
 */
class DlangRenameFileActionProvider : RenameFileActionProvider {
    override fun enabledInProjectView(file: PsiFile): Boolean {
        return file is DlangPsiFile
    }
}
