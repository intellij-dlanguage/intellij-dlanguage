package io.github.intellij.dlanguage.structure

import com.intellij.ide.structureView.StructureViewBuilder
import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder
import com.intellij.lang.PsiStructureViewFactory
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile
import io.github.intellij.dlanguage.psi.DlangFile

class DStructureViewFactory : PsiStructureViewFactory {
    override fun getStructureViewBuilder(psiFile: PsiFile): StructureViewBuilder? {
        return if(psiFile is DlangFile) DlangTreeBasedStructureViewBuilder(psiFile) else null
    }
}

class DlangTreeBasedStructureViewBuilder(val psiFile: DlangFile) : TreeBasedStructureViewBuilder() {
    override fun createStructureViewModel(editor: Editor?): StructureViewModel = DStructureViewModel(psiFile)
}
