package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.impl.DLanguageDeleteExpressionImpl
import io.github.intellij.dlanguage.psi.impl.DLanguageDeleteStatementImpl

/**
 * Created by francis on 11/26/2017.
 */
class DeleteStatementsAreDeprecated : LocalInspectionTool() {
    override fun getDescriptionFileName(): String = "DeleteStatementsAreDeprecated.html"
    override fun getDisplayName(): String = "Delete Statements are Deprecated"
    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): DlangVisitor = DeleteStatementsVisitor(holder)
}

class DeleteStatementsVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    override fun visitDeleteExpression(o: DLanguageDeleteExpressionImpl) {
        register(o)
    }

    override fun visitDeleteStatement(o: DLanguageDeleteStatementImpl) {
        register(o)
    }

    fun register(o: PsiElement) {
        holder.registerProblem(o, "Delete statements are deprecated")
    }
}
