package io.github.intellij.dlanguage.quickfix

import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import io.github.intellij.dlanguage.utils.FunctionDeclaration

class MakeFunctionVoid(elem: FunctionDeclaration) : LocalQuickFixOnPsiElement(elem) {
    override fun getFamilyName(): String = "DLang"

    override fun getText(): String = "Make Function Void"

    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
        val funcDecl = startElement as FunctionDeclaration
        val autoElem = funcDecl.getAutoElem()!!
        val range = autoElem.textRange
        val document = file.viewProvider.document!!
        document.replaceString(range.startOffset, range.endOffset, "void")
    }

}
