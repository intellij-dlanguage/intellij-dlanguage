package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.impl.DLanguageAliasDeclarationImpl

/**
 * Created by francis on 11/23/2017.
 */
class OldAliasSyntax : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return OldAliasSyntaxVisitor(holder)
    }

    override fun getDisplayName(): String {
        return "Old Alias Syntax"//todo needs internationalization
    }

    override fun getGroupDisplayName(): String {
        return DlangBundle.message("d.inspections.groupname")
    }
}

class OldAliasSyntaxVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    override fun visitAliasDeclaration(o: DLanguageAliasDeclarationImpl) {
        if (o.identifierList == null || o.identifierList!!.identifiers.size == 0)
            return
        holder.registerProblem(o, "Old Alias Syntax in use.")
    }
}



