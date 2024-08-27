package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DLanguageDeleteExpression
import io.github.intellij.dlanguage.psi.DlangVisitor

/**
 * Created by francis on 1/5/2018.
 */
class DeleteIsDeprecated : LocalInspectionTool() {
    override fun getDescriptionFileName(): String = "DeleteIsDeprecated.html"
    override fun getDisplayName(): String = "DeleteIsDeprecated"
    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return DeleteIsDeprecatedVisitor(holder)
    }
}

class DeleteIsDeprecatedVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    override fun visitDeleteExpression(o: DLanguageDeleteExpression) {
        holder.registerProblem(o, "Avoid using the 'delete' keyword. It is deprecated")
    }
}
