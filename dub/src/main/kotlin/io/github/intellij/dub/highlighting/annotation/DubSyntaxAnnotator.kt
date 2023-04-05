package io.github.intellij.dub.highlighting.annotation

import com.intellij.lang.annotation.ExternalAnnotator
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.progress.ProcessCanceledException
import com.intellij.psi.PsiFile
import io.github.intellij.dlanguage.highlighting.annotation.DProblem


class DubSyntaxAnnotator : ExternalAnnotator<PsiFile, DubSyntaxAnnotator.State>() {
    data class State(val annotations: Array<DProblem>)

    private val LOG = Logger.getInstance(DubSyntaxAnnotator::class.java)

    override fun collectInformation(file: PsiFile, editor: Editor, hasErrors: Boolean): PsiFile =
        collectInformation(file)

    override fun collectInformation(file: PsiFile): PsiFile = file

    override fun doAnnotate(collectedInfo: PsiFile): State {
        // Force all files to save to ensure annotations are in sync with the file system.

        // Force all files to save to ensure annotations are in sync with the file system.
        val fileDocumentManager = FileDocumentManager.getInstance()
        val application = ApplicationManager.getApplication()

        if (fileDocumentManager.unsavedDocuments.isNotEmpty()) {
            application.invokeAndWait(
                { fileDocumentManager.saveAllDocuments() },
                application.defaultModalityState
            )
        }

        return State(
            CompileCheck().checkFileSyntax(collectedInfo)
        )
    }
}
