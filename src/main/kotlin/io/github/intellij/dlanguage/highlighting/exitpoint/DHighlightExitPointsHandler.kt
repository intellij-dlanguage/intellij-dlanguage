package io.github.intellij.dlanguage.highlighting.exitpoint

import com.intellij.codeInsight.highlighting.HighlightUsagesHandlerBase
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.util.Consumer
import io.github.intellij.dlanguage.psi.ext.ancestors
import io.github.intellij.dlanguage.utils.FunctionDeclaration
import io.github.intellij.dlanguage.utils.FunctionLiteralExpression

class DHighlightExitPointsHandler(
    editor: Editor,
    file: PsiFile,
    var target: PsiElement
) : HighlightUsagesHandlerBase<PsiElement>(editor, file) {

    override fun getTargets() = listOf(target)

    override fun selectTargets(targets: List<PsiElement>, selectionConsumer: Consumer<List<PsiElement>>) {
        selectionConsumer.consume(targets)
    }

    override fun computeUsages(targets: MutableList<PsiElement>?) {
        val sink: (ExitPoint) -> Unit = { exitPoint ->
            when (exitPoint) {
                is ExitPoint.Return -> addOccurrence(exitPoint.e)
                is ExitPoint.Throw -> addOccurrence(exitPoint.e)
            }
        }

        val functionOrFunctionLiteral = target.ancestors.firstOrNull {
            it is FunctionDeclaration || it is FunctionLiteralExpression
        }

        when (functionOrFunctionLiteral) {
            is FunctionDeclaration -> ExitPoint.process(functionOrFunctionLiteral, sink)
            is FunctionLiteralExpression -> ExitPoint.process(functionOrFunctionLiteral, sink)
        }
    }
}
