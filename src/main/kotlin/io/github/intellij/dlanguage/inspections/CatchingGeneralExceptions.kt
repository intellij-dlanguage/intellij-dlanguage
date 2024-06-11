package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.impl.DLanguageLastCatchImpl
import io.github.intellij.dlanguage.psi.impl.named.DLanguageCatchImpl

class CatchingGeneralExceptions : LocalInspectionTool() {
    override fun getDescriptionFileName(): String = "CatchingGeneralExceptions.html"
    override fun getDisplayName(): String = "Catching Error or Throwable is A Bad Idea"
    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): DlangVisitor = CatchingGeneralExceptionsVisitor(holder)
}

class CatchingGeneralExceptionsVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    override fun visitCatch(o: DLanguageCatchImpl) {
        val exceptionType = o.type?.basicType?.qualifiedIdentifier?.text
        if (exceptionType != null)
            if (exceptionType == "Error" || exceptionType == "Throwable")
                holder.registerProblem(o, "Catching Error or Throwable is usually a bad idea.")
    }

    override fun visitLastCatch(o: DLanguageLastCatchImpl) {
        holder.registerProblem(o, "Catching Error or Throwable is usually a bad idea.")
    }

}
