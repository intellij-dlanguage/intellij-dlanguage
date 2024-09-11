package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DLanguageImportDeclaration
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.named.DLanguageSingleImport

class DeprecatedPhobosImport : LocalInspectionTool() {
    override fun getDescriptionFileName(): String = "DeprecatedPhobosImport.html"
    override fun getDisplayName(): String = "Deprecated Phobos import"
    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): DlangVisitor = DeprecatedPhobosImportVisitor(holder)
}

class DeprecatedPhobosImportVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    override fun visitImportDeclaration(declaration: DLanguageImportDeclaration) {
        declaration.singleImports.forEach {
            when (it.text) {
                "std.experimental.checkedint" -> register(it, "std.experimental.checkedint became std.checkedint in D 2.099")
                "std.experimental.logger" -> register(it, "std.experimental.logger moved to std.logger in D 2.100")
                "std.xml" -> register(it, "std.xml is due to be removed from phobos in 2.101 as it's considered out-dated and not up to Phobos' standards")
            }
        }
    }

    fun register(dlangSingleImport: DLanguageSingleImport, descriptionTemplate: String) {
        holder.registerProblem(dlangSingleImport, descriptionTemplate, ProblemHighlightType.LIKE_DEPRECATED)
    }
}
