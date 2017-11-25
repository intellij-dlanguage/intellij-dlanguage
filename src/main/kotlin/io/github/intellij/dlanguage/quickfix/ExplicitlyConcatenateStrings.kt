package io.github.intellij.dlanguage.quickfix

import com.intellij.codeInspection.LocalQuickFixBase
import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.utils.PrimaryExpression

/**
 * Created by francis on 11/23/2017.
 */
class ExplicitlyConcatenateStrings(elem: PrimaryExpression) : LocalQuickFixOnPsiElement(elem, elem) {

    override fun getText(): String {
        return "Explicitly concatenate strings"
    }

    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
        ApplicationManager.getApplication().runWriteAction {

            val primaryExpression = getStartElement() as PrimaryExpression
            val doc = file.viewProvider.document!!
            var last: PsiElement? = null
            var i = 0
            for (string in primaryExpression.doublE_QUOTED_STRINGs) {
                if (last != null) {
                    val insertLocation = last.textOffset + last.textLength + i
                    doc.insertString(insertLocation, " ~")
                    i += 2 //this i is used to handle the offset becoming out of sync as we write to the file
                }
                last = string
            }
        }
    }

    /**
     * Returns the name of the family of intentions. It is used to externalize
     * "auto-show" state of intentions. When user clicks on a lightbulb in intention list,
     * all intentions with the same family name get enabled/disabled.
     * The name is also shown in settings tree.
     *
     * @return the intention family name.
     * @see IntentionManager.registerIntentionAndMetaData
     */
    override fun getFamilyName(): String {
        return "DLang"//todo idk what to put here
    }

    /**
     * Checks whether this intention is available at a caret offset in file.
     * If this method returns true, a light bulb for this intention is shown.
     *
     * @param project the project in which the availability is checked.
     * @param editor the editor in which the intention will be invoked.
     * @param file the file open in the editor.
     * @return true if the intention is available, false otherwise.
     */
    fun isAvailable(project: Project, editor: Editor, file: PsiFile): Boolean {
        val element = file.findElementAt(editor.caretModel.currentCaret.offset)
        val primaryExpression = PsiTreeUtil.findFirstParent(element, { t -> t is PrimaryExpression }) as PrimaryExpression?
        if (primaryExpression == null)
            return false
        if (primaryExpression.doublE_QUOTED_STRINGs.size > 1) {
            return true
        }
        return false
    }

}
