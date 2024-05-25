package io.github.intellij.dlanguage.highlighting.exitpoint

import com.intellij.codeInsight.highlighting.HighlightUsagesHandlerBase
import com.intellij.codeInsight.highlighting.HighlightUsagesHandlerFactoryBase
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiUtilCore
import io.github.intellij.dlanguage.psi.DlangPsiFile
import io.github.intellij.dlanguage.psi.DlangTypes.KW_RETURN
import io.github.intellij.dlanguage.psi.DlangTypes.KW_THROW

class DHighlightExitPointsHandlerFactory : HighlightUsagesHandlerFactoryBase() {
    override fun createHighlightUsagesHandler(editor: Editor, file: PsiFile, target: PsiElement): HighlightUsagesHandlerBase<*>? {
        if (file !is DlangPsiFile) return null

        val elementType = PsiUtilCore.getElementType(target)
        if (elementType == KW_RETURN || elementType == KW_THROW) {
            return DHighlightExitPointsHandler(editor, file, target)
        }
        return null
    }
}
