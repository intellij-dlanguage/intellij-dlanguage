package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.impl.DLanguageAliasDeclarationImpl
import io.github.intellij.dlanguage.psi.impl.named.DLanguageAliasInitializerImpl

/**
 * Created by francis on 11/23/2017.
 */
class OldAliasSyntax : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor =
        OldAliasSyntaxVisitor(holder)

    override fun getDisplayName(): String = "Old Alias Syntax"//todo needs internationalization

    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")
}

class OldAliasSyntaxVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    override fun visitAliasDeclaration(o: DLanguageAliasDeclarationImpl) {
        if (o.type == null)
            return
        holder.registerProblem(o, "Old Alias Syntax in use.")
    }
}



