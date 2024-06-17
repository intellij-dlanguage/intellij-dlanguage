package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.impl.named.DlangClassDeclarationImpl
import io.github.intellij.dlanguage.psi.impl.named.DlangStructDeclarationImpl
import io.github.intellij.dlanguage.psi.impl.named.DlangUnionDeclarationImpl
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.quickfix.MakeStaticImmutable
import io.github.intellij.dlanguage.utils.AutoAssignment

/**
 * Created by francis on 1/5/2018.
 */
class EnumArrayLiteralsInClassDeclaration : LocalInspectionTool() {
    override fun getDescriptionFileName(): String = "EnumArrayLiteralsInClassDeclaration.html"
    override fun getDisplayName(): String = "EnumArrayLiteralsInClassDeclaration"
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): DlangVisitor = EnumArrayLiteralsInClassDeclarationVisitor(holder)
    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")
}

class EnumArrayLiteralsInClassDeclarationVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    override fun visitClassDeclaration(o: DlangClassDeclarationImpl) {
        checkForEnumLiterals(o)
    }

    override fun visitUnionDeclaration(o: DlangUnionDeclarationImpl) {
        checkForEnumLiterals(o)
    }

    override fun visitStructDeclaration(o: DlangStructDeclarationImpl) {
        checkForEnumLiterals(o)
    }

    fun checkForEnumLiterals(o: DNamedElement) {
        for (decl in PsiTreeUtil.findChildrenOfType(o, AutoAssignment::class.java)) {
            if (decl.isEnum) {
                if (decl.initializer?.arrayLiteral?.arrayInitializer == null)
                    continue
                holder.registerProblem(decl, "This enum may lead to unnecessary allocation at run-time. Use \"static immutable instead\"", MakeStaticImmutable(decl))
                // TODO when type deduction becomes a thing use that instead of checking initializers
            }
        }
    }
}
