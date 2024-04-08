package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.impl.DLanguageRelExpressionImpl

/**
 * Created by francis on 1/5/2018.
 */
class FishOperators : LocalInspectionTool() {
    override fun getDescriptionFileName(): String = "FishOperators.html"
    override fun getDisplayName(): String = "Deprecated Floating point operators"
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): DlangVisitor = FishOperatorsVisitor(holder)
    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")
}

class FishOperatorsVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    override fun visitRelExpression(o: DLanguageRelExpressionImpl) {
        if (o.oP_GT != null || o.oP_GT_EQ != null || o.oP_LESS != null || o.oP_LESS_EQ != null || o.oP_LESS_GR != null || o.oP_LESS_GR_EQ != null || o.oP_NOT_GR != null || o.oP_NOT_LESS != null || o.oP_NOT_GR_EQ != null || o.oP_UNORD != null || o.oP_UNORD_EQ != null)
            holder.registerProblem(o, "Avoid using the deprecated floating-point operators.")
    }
}
