package io.github.intellij.dlanguage.quickfix

import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import io.github.intellij.dlanguage.psi.DLanguageFunctionDeclaration

/**
 * Created by francis on 11/26/2017.
 */
class MakeFunctionConst(elem: DLanguageFunctionDeclaration) : LocalQuickFixOnPsiElement(elem) {
    /**
     * @return text to appear in "Apply Fix" popup when multiple Quick Fixes exist (in the results of batch code inspection). For example,
     * if the name of the quickfix is "Create template &lt;filename&gt", the return value of getFamilyName() should be "Create template".
     * If the name of the quickfix does not depend on a specific element, simply return getName().
     */
    override fun getFamilyName(): String = "DLang"

    override fun getText(): String = "Make Function Const"

    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
        val funcDecl = startElement as DLanguageFunctionDeclaration
        val endOffset = funcDecl.parameters!!.textRange.endOffset
        val document = file.viewProvider.document!!
        document.insertString(endOffset, " const")
    }

}
