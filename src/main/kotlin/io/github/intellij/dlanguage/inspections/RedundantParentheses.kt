package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.impl.DLanguageIfStatementImpl

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
        val expressions = o.ifCondition?.assignExpressions
        if (expressions?.size != 1)
            return
        if (expressions[0] == null)
            return
        holder.registerProblem(o.ifCondition!!, "Redundant parantheses")
    }

    /*override fun visitPrimaryExpression(o: DLanguagePrimaryExpressionImpl) {
        /*if (o.assignExpressions.isEmpty() || o.assignExpressions[0] == null)
            return
        val unary = o.assignExpressions[0]
        if (unary.assignExpression == null || unary.assignExpression!!.assignExpression == null)
            return
        holder.registerProblem(o, "Redundant parantheses")*/
    }*/
}
