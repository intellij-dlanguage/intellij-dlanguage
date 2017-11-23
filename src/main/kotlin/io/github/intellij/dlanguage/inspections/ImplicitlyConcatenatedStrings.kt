package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.impl.DLanguageAliasDeclarationImpl
import io.github.intellij.dlanguage.psi.impl.DLanguagePrimaryExpressionImpl

/**
 * Created by francis on 11/23/2017.
 */
class ImplicitlyConcatenatedStrings : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return ImplicitlyConcatenatedStringsVisitor(holder)
    }

    override fun getDisplayName(): String {
        return "Implicitly Concatenated Strings"//todo needs internationalization
    }

    override fun getGroupDisplayName(): String {
        return DlangBundle.message("d.inspections.groupname")
    }
}

class ImplicitlyConcatenatedStringsVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    override fun visitPrimaryExpression(o: DLanguagePrimaryExpressionImpl) {
        if (o.doublE_QUOTED_STRINGs.size > 1)
            holder.registerProblem(o, "Implicitly concatenated strings")
    }
}



