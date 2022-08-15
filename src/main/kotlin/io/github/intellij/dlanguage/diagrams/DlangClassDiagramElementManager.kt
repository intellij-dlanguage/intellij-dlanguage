package io.github.intellij.dlanguage.diagrams

import com.intellij.diagram.AbstractDiagramElementManager
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.psi.PsiElement

class DlangClassDiagramElementManager : AbstractDiagramElementManager<PsiElement>() {

    override fun findInDataContext(context: DataContext): PsiElement? = CommonDataKeys.PSI_ELEMENT.getData(context)

    override fun isAcceptableAsNode(e: Any?): Boolean = e is PsiElement

    override fun getNodeTooltip(p0: PsiElement?): String? = null

    override fun getElementTitle(p0: PsiElement?): String? = null

}
