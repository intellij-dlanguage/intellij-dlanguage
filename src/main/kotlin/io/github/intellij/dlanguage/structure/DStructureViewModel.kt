package io.github.intellij.dlanguage.structure

import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.psi.DLanguageTemplateMixinDeclaration
import io.github.intellij.dlanguage.psi.DLanguageUnittest
import io.github.intellij.dlanguage.psi.DlangPsiFile
import io.github.intellij.dlanguage.psi.interfaces.UserDefinedType
import io.github.intellij.dlanguage.psi.named.DLanguageFunctionDeclaration
import io.github.intellij.dlanguage.psi.named.DLanguageTemplateDeclaration

class DStructureViewModel(editor: Editor?, psiFile: PsiFile) :
    StructureViewModelBase(psiFile, editor, DStructureViewElement(psiFile)),
    StructureViewModel.ElementInfoProvider
{
    init {
        withSuitableClasses(
            DLanguageTemplateMixinDeclaration::class.java,
            DLanguageTemplateDeclaration::class.java,
            UserDefinedType::class.java,
            DLanguageFunctionDeclaration::class.java,
        )
        withSorters(Sorter.ALPHA_SORTER)
    }

    override fun isAlwaysShowsPlus(structureViewTreeElement: StructureViewTreeElement?): Boolean =
        structureViewTreeElement?.value is DlangPsiFile || structureViewTreeElement?.value is UserDefinedType

    override fun isAlwaysLeaf(structureViewTreeElement: StructureViewTreeElement?): Boolean = false

    override fun isSuitable(element: PsiElement?): Boolean {
        val suitable = super.isSuitable(element)
        if (!suitable) return false

        if (PsiTreeUtil.getParentOfType(element, DLanguageUnittest::class.java) != null) {
            // We ignore the sub-elements in Unittests
            return false
        }

        return true
    }
}
