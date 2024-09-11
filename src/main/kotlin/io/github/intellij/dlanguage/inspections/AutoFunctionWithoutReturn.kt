package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil.findChildrenOfType
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DLanguageReturnStatement
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.named.DLanguageFunctionDeclaration
import io.github.intellij.dlanguage.quickfix.MakeFunctionVoid

class AutoFunctionWithoutReturn : LocalInspectionTool() {
    override fun getDescriptionFileName(): String = "AutoFunctionWithoutReturn.html"
    override fun getDisplayName(): String = "AutoFunctionWithoutReturn"
    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return AutoFunctionReturnVisitor(holder)
    }
}

class AutoFunctionReturnVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    override fun visitFunctionDeclaration(o: DLanguageFunctionDeclaration) {
        if (shouldWarn(o)) {
            holder.registerProblem(o, "Don't create auto functions without return types", MakeFunctionVoid(o))
        }
    }

    private fun shouldWarn(o: DLanguageFunctionDeclaration): Boolean {
        if (!o.isAuto()) {
            return false
        }
        val returnStatements = findChildrenOfType(o, DLanguageReturnStatement::class.java)
        for (returnStatement in returnStatements) {
            if (returnStatement.expressions.isNotEmpty()) {
                return false;
            }
        }
        return true;
    }
}
