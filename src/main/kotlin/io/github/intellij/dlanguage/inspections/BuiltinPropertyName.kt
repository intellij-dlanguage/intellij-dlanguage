package io.github.intellij.dlanguage.inspections

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

class BuiltinPropertyNameVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    val properties = setOf<String>("init", "sizeof", "mangleof", "alignof", "stringof")
    override fun visitDNamedElement(o: DNamedElement) {
        if (properties.contains(o.name)) {
            holder.registerProblem(o, String.format("Avoid naming members '%s'. This can confuse code that depends on the '.%s' property of a type.", o.name, o.name))
        }
    }
}

