package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiFile
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DLanguageTryStatement
import io.github.intellij.dlanguage.psi.DlangVisitor

/**
 * Better C is not compatible with try/catch blocks.
 * "cannot use try-catch statements with -betterC"
 *
 * @author Samael Bate (singingbush)
 * created on 18/05/2026
 * @since 1.40
 */
class BettercTryCatch : LocalInspectionTool() {
//    override fun getDescriptionFileName(): String = ""

    override fun getDisplayName(): String = "Cannot use try-catch statements with BetterC"

    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")

    override fun isAvailableForFile(file: PsiFile): Boolean {
        // check if BETTER_C is enabled on the SDK
        val projectSdk = ProjectRootManager.getInstance(file.project).projectSdk ?: return false
        return projectSdk.getUserData(Key.create("BETTER_C")) ?: false
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): DlangVisitor = object: DlangVisitor() {
        override fun visitTryStatement(statement: DLanguageTryStatement) {
            holder.registerProblem(statement,"Cannot use try-catch statements with BetterC", ProblemHighlightType.WARNING)
        }
    }
}
