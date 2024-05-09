package io.github.intellij.dlanguage.structure

import com.intellij.ide.structureView.StructureViewBuilder
import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder
import com.intellij.lang.PsiStructureViewFactory
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile
import io.github.intellij.dlanguage.psi.DlangPsiFile

class DStructureViewFactory : PsiStructureViewFactory {
    override fun getStructureViewBuilder(psiFile: PsiFile): StructureViewBuilder? {
        return if(psiFile is DlangPsiFile) DlangTreeBasedStructureViewBuilder(psiFile) else null
    }
}

class DlangTreeBasedStructureViewBuilder(val psiFile: DlangPsiFile) : TreeBasedStructureViewBuilder() {
    override fun createStructureViewModel(editor: Editor?): StructureViewModel = DStructureViewModel(psiFile)
}
