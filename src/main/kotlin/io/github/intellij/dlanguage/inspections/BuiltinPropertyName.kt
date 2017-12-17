package io.github.intellij.dlanguage.beginningss

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement

class BuiltinPropertyName : LocalInspectionTool() {
    override fun getDescriptionFileName(): String = "BuiltinPropertyName.html"
    override fun getDisplayName(): String = "BuiltinPropertyName"
    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return BuiltinPropertyNameVisitor(holder)
    }
}

class BuiltinPropertyNameVisitor(holder: ProblemsHolder) : DlangVisitor() {
    override fun visitDNamedElement(o: DNamedElement) {

    }
}

