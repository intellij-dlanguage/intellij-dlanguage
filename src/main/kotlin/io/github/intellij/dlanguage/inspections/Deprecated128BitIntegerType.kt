package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.psi.DlangPsiFile
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.DLanguageBasicType
import io.github.intellij.dlanguage.psi.types.DPrimitiveType

// https://dlang.org/deprecate.html#128-bit%20integer%20types
class Deprecated128BitIntegerType : LocalInspectionTool() {
    override fun getDescriptionFileName(): String = "Deprecated128BitIntegerType.html"
    override fun getDisplayName(): String = "Deprecated data type"
    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): DlangVisitor = Deprecated128BitIntegerTypeVisitor(holder)
}

class Deprecated128BitIntegerTypeVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    override fun visitBasicType(type: DLanguageBasicType) {
        when(type.dType) {
            DPrimitiveType.CENT,
            DPrimitiveType.UCENT -> holder.registerProblem(
                type,
                DlangBundle.message("d.inspections.symbol.deprecated128bitintegertype.description"),
                UseAlternativeInt128(type, "std.int128"),
                UseAlternativeInt128(type, "core.int128")
            )
        }
    }
}

class UseAlternativeInt128(type: DLanguageBasicType, val replacement: String) : LocalQuickFixOnPsiElement(type) {

    override fun getFamilyName(): String = "DLang" // todo: find out what we should do with family name

    override fun getText(): String = "Use $replacement"

    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
        val dlangFile = PsiFileFactory.getInstance(project)
            .createFileFromText("tmp", DlangFileType, replacement) as DlangPsiFile
        getStartElement().replace(dlangFile.firstChild)
    }
}
