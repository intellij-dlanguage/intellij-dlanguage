package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DLanguageEmptyStatement
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.quickfix.RemoveEmptyStatement

/**
 * Created by francis on 11/25/2017.
 */
class EmptyStatement : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return EmptyStatementVisitor(holder)
    }

    override fun getDescriptionFileName(): String = "EmptyStatement.html"
    override fun getDisplayName(): String = "Empty Statement"
    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")
}

class EmptyStatementVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    override fun visitEmptyStatement(o: DLanguageEmptyStatement) {
        holder.registerProblem(o, "Empty Statement", RemoveEmptyStatement(o))
    }
}
