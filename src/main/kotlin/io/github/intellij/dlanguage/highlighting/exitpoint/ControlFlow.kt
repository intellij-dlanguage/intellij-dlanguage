package io.github.intellij.dlanguage.highlighting.exitpoint

import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.psi.DLanguageReturnStatement
import io.github.intellij.dlanguage.psi.DLanguageThrowExpression
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.impl.DLanguageFunctionLiteralExpressionImpl
import io.github.intellij.dlanguage.psi.impl.DLanguageReturnStatementImpl
import io.github.intellij.dlanguage.psi.impl.DLanguageThrowExpressionImpl
import io.github.intellij.dlanguage.psi.impl.named.DLanguageFunctionDeclarationImpl
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

    override fun visitFunctionDeclaration(o: DLanguageFunctionDeclarationImpl) = Unit

    override fun visitFunctionLiteralExpression(o: DLanguageFunctionLiteralExpressionImpl) =
        Unit

    override fun visitReturnStatement(o: DLanguageReturnStatementImpl) = sink(ExitPoint.Return(o))

    override fun visitThrowExpression(o: DLanguageThrowExpressionImpl) = sink(ExitPoint.Throw(o))
}
