package io.github.intellij.dlanguage.structure

import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.intellij.psi.PsiFile

class DStructureViewModel(psiFile: PsiFile) :
    StructureViewModelBase(psiFile, DStructureViewElement(psiFile)),
    StructureViewModel.ElementInfoProvider
{
    override fun getSorters(): Array<Sorter> = arrayOf(Sorter.ALPHA_SORTER)

    override fun isAlwaysShowsPlus(structureViewTreeElement: StructureViewTreeElement?): Boolean = false

    override fun isAlwaysLeaf(structureViewTreeElement: StructureViewTreeElement?): Boolean = false
}
