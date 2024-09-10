package io.github.intellij.dlanguage.highlighting.exitpoint

import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.psi.DLanguageFunctionLiteralExpression
import io.github.intellij.dlanguage.psi.DLanguageReturnStatement
import io.github.intellij.dlanguage.psi.DLanguageThrowExpression
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.named.DLanguageFunctionDeclaration
import io.github.intellij.dlanguage.utils.FunctionDeclaration
import io.github.intellij.dlanguage.utils.FunctionLiteralExpression

sealed class ExitPoint {
    class Return(val e: DLanguageReturnStatement) : ExitPoint()
    class Throw(val e: DLanguageThrowExpression) : ExitPoint()

    companion object {
        fun process(fn: FunctionDeclaration, sink: (ExitPoint) -> Unit) =
            fn.acceptChildren(ExitPointVisitor(sink))

        fun process(fn: FunctionLiteralExpression, sink: (ExitPoint) -> Unit) =
            fn.specifiedFunctionBody?.acceptChildren(ExitPointVisitor(sink))
    }
}

private class ExitPointVisitor(
    private val sink: (ExitPoint) -> Unit
) : DlangVisitor() {

    override fun visitPsiElement(o: PsiElement) = o.acceptChildren(this)

    override fun visitFunctionDeclaration(o: DLanguageFunctionDeclaration) = Unit

    override fun visitFunctionLiteralExpression(o: DLanguageFunctionLiteralExpression) =
        Unit

    override fun visitReturnStatement(o: DLanguageReturnStatement) = sink(ExitPoint.Return(o))

    override fun visitThrowExpression(o: DLanguageThrowExpression) = sink(ExitPoint.Throw(o))
}
