package io.github.intellij.dlanguage.diagrams

import com.intellij.diagram.DiagramVfsResolver
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement

class DlangClassDiagramVfsResolver : DiagramVfsResolver<PsiElement> {

    override fun getQualifiedName(element: PsiElement?): String? = element?.text

    override fun resolveElementByFQN(fqn: String, project: Project): PsiElement? {
        TODO("Not yet implemented")
    }
}
