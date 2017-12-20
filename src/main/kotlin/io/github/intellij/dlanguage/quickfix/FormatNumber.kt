package io.github.intellij.dlanguage.quickfix

import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import io.github.intellij.dlanguage.DlangBundle

class FormatNumber(elem : PsiElement) : LocalQuickFixOnPsiElement(elem){
    override fun getFamilyName(): String = DlangBundle.message("d.inspections.groupname")

    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
        val text = startElement.text.replace("_","");

        var withUnderScores = getWithUnderScores(text)
        ApplicationManager.getApplication().runWriteAction {
            val doc = file.viewProvider.document!!
            doc.replaceString(startElement.textRange.startOffset,startElement.textRange.endOffset,withUnderScores)
        }

    }

    private fun getWithUnderScores(text: String): String {
        val len = text.length
        if(len < 4)
            return text
        return getWithUnderScores(text.substring(0, len - 3)) + "_" + text.subSequence(len - 3, text.length)
    }

    override fun getText(): String = "Format Number correctly"
}
