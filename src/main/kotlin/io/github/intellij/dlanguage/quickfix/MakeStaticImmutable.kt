package io.github.intellij.dlanguage.quickfix

import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import io.github.intellij.dlanguage.utils.AutoDeclaration

/**
 * Created by francis on 1/5/2018.
 */
class MakeStaticImmutable(elem: PsiElement) : LocalQuickFixOnPsiElement(elem) {
    /**
     * @return text to appear in "Apply Fix" popup when multiple Quick Fixes exist (in the results of batch code inspection). For example,
     * if the name of the quickfix is "Create template &lt;filename&gt", the return value of getFamilyName() should be "Create template".
     * If the name of the quickfix does not depend on a specific element, simply return getName().
     */
    override fun getFamilyName(): String = name

    override fun getText(): String = "Make Static Immutable"

    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
        val autoDeclarationOverall = startElement.parent as AutoDeclaration
        for (storageClasss in autoDeclarationOverall.storageClasss) {
            if (storageClasss.kW_ENUM != null) {
                storageClasss.delete()
            }
        }
        val startOffset = autoDeclarationOverall.textRange.startOffset
        val document = file.viewProvider.document!!
        document.insertString(startOffset, " static immutable ")
    }

}
