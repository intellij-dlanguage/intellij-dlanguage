package io.github.intellij.dlanguage.diagrams

import com.intellij.diagram.DiagramVfsResolver
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.elementType
import io.github.intellij.dlanguage.index.DModuleIndex
import io.github.intellij.dlanguage.psi.DlangPsiFile
import io.github.intellij.dlanguage.psi.DlangTypes

class DlangClassDiagramVfsResolver : DiagramVfsResolver<PsiElement> {

    override fun getQualifiedName(element: PsiElement?): String? = element?.text

    override fun resolveElementByFQN(fqn: String, project: Project): PsiElement? {
        // todo: get all classes and interfaces in the source code
        // ASTNode node = this.getNode().findChildByType(type);
        val a = DModuleIndex.getFilesByModuleName(
            project = project,
            moduleName = fqn,
            searchScope = GlobalSearchScope.allScope(project)
        ).filter { it.elementType == DlangTypes.INTERFACE_DECLARATION || it.elementType == DlangTypes.CLASS_DECLARATION }

        return a.find { it.getFullyQualifiedModuleName() == fqn }
        // for (file in DModuleIndex.getFilesByModuleName(project, module, GlobalSearchScope.allScope(project))) {
        //     elements.addAll(StubIndex.getElements(KEY, name, project, GlobalSearchScope.fileScope(file), DNamedElement::class.java))
        // }
    }
}
