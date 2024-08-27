package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DLanguageLiteralExpression
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.quickfix.ExplicitlyConcatenateStrings

/**
 * Created by francis on 11/23/2017.
 */
class ImplicitlyConcatenatedStrings : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor =
        ImplicitlyConcatenatedStringsVisitor(holder)

    override fun getDescriptionFileName(): String = "ImplicitlyConcatenatedStrings.html"

    override fun getDisplayName(): String = "Implicitly Concatenated Strings"//todo needs internationalization

    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")
}

class ImplicitlyConcatenatedStringsVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    override fun visitLiteralExpression(o: DLanguageLiteralExpression) {
        if (o.doublE_QUOTED_STRINGs.size + o.delimiteD_STRINGs.size + o.alternatE_WYSIWYG_STRINGs.size + o.wysiwyG_STRINGs.size > 1)
            holder.registerProblem(o, "Implicitly concatenated strings", ExplicitlyConcatenateStrings(o))
    }
}



