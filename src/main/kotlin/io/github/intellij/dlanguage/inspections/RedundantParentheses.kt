package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.impl.DLanguageIfStatementImpl
import io.github.intellij.dlanguage.psi.impl.DLanguagePrimaryExpressionImpl

class RedundantParentheses : LocalInspectionTool() {
    override fun getDescriptionFileName(): String = "RedundantParentheses.html"
    override fun getDisplayName(): String = "RedundantParentheses"
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): DlangVisitor = RedundantParenthesesVisitor(holder)
    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")
}

/**
 * todo this is not done.
 */
class RedundantParenthesesVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    override fun visitIfStatement(o: DLanguageIfStatementImpl) {
        val expression = o.ifCondition?.expression
        if (expression == null || expression.assignExpressions.size != 1)
            return
        if (expression.assignExpressions[0] == null)
            return
        holder.registerProblem(expression, "Redundant parantheses")
    }

    override fun visitPrimaryExpression(o: DLanguagePrimaryExpressionImpl) {
        val expression = o.expression
        if (expression == null)
            return
        if (expression.assignExpressions.size == 0 || expression.assignExpressions[0] == null)
            return
        val unary = expression.assignExpressions[0]
        if (unary.assignExpression == null || unary.assignExpression!!.assignExpression == null)
            return
        holder.registerProblem(expression, "Redundant parantheses")
    }
}
