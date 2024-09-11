package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.named.DLanguageFunctionDeclaration
import io.github.intellij.dlanguage.quickfix.MakeFunctionConst
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributesFinder

/**
 * Created by francis on 11/26/2017.
 */
class FunctionShouldBeConst : LocalInspectionTool() {
    override fun getDescriptionFileName(): String = "FunctionShouldBeConst.html"
    override fun getDisplayName(): String = "Function Should be Const"
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): DlangVisitor = FunctionShouldBeConstVisitor(holder)
    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")
}

class FunctionShouldBeConstVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    override fun visitFunctionDeclaration(o: DLanguageFunctionDeclaration) {
        val name = o.name
        if (name == "opCmp" || name == "opEquals" || name == "toHash") {
            val finder = DAttributesFinder(o)//todo make function declarations have an isConst method.
            finder.recurseUp()
            if (!finder.isConst()) {
                holder.registerProblem(o, "opCmp or opEquals, or toHash not declared \"const\"", MakeFunctionConst(o))
            }
        }
    }
}
