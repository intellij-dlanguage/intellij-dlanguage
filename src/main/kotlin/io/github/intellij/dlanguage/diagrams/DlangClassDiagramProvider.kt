package io.github.intellij.dlanguage.diagrams

import com.intellij.diagram.*
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.ModificationTracker
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement

/*
* A diagram provider that generates UML based on D source code
*/
class DlangClassDiagramProvider : BaseDiagramProvider<PsiElement>() {

    private companion object {
        const val ID = "DCD Server Status Bar Widget"
        val log: Logger = Logger.getInstance(DlangClassDiagramProvider::class.java)
    }

    override fun getID(): String = "DlangUML"

    override fun getPresentableName(): String = "D class diagram"

    override fun createVisibilityManager(): DiagramVisibilityManager {
        return DlangDiagramVisibilityManager()
    }

    override fun getElementManager(): DiagramElementManager<PsiElement> = DlangClassDiagramElementManager()

    override fun getVfsResolver(): DiagramVfsResolver<PsiElement> = DlangClassDiagramVfsResolver()

    override fun getRelationshipManager(): DiagramRelationshipManager<PsiElement> {
        TODO("Not yet implemented")
    }

    override fun createDataModel(
        project: Project,
        psiElement: PsiElement?,
        virtualFile: VirtualFile?,
        presentationModel: DiagramPresentationModel
    ): DiagramDataModel<PsiElement> = DlangClassDataModel(project, this, psiElement)

    // todo: finish implementing this. For an example see: https://github.com/JetBrains/Grammar-Kit/blob/4d1434a81aeb6263ff4a7cf0f67a242b2ba4bc87/src/org/intellij/grammar/diagram/BnfDiagramProvider.java
    class DlangClassDataModel(project: Project, provider: DiagramProvider<PsiElement>, val psiNamedElement: PsiElement?)
                : DiagramDataModel<PsiElement>(project, provider) {

        private val myNodes: Collection<DiagramNode<PsiElement>> = HashSet()
        private val myEdges: Collection<DiagramEdge<PsiElement>> = HashSet()

        override fun dispose() {
            TODO("Not yet implemented")
        }

        override fun getNodes(): MutableCollection<out DiagramNode<PsiElement>> {
            TODO("Not yet implemented")
        }

        override fun getEdges(): MutableCollection<out DiagramEdge<PsiElement>> {
            TODO("Not yet implemented")
        }

        override fun getModificationTracker(): ModificationTracker {
            TODO("Not yet implemented")
        }

        override fun addElement(p0: PsiElement?): DiagramNode<PsiElement>? {
            TODO("Not yet implemented")
        }

        override fun getNodeName(p0: DiagramNode<PsiElement>): String {
            TODO("Not yet implemented")
        }
    }

    class DlangDiagramVisibilityManager : DiagramVisibilityManager {

        private val visLevel = VisibilityLevel("VisLevel")

        override fun getVisibilityLevels(): Array<VisibilityLevel> = arrayOf(visLevel)

        override fun getVisibilityLevel(p0: Any?): VisibilityLevel? = visLevel

        override fun getComparator(): Comparator<VisibilityLevel> = VisibilityLevel.NAME_COMPARATOR

        override fun getCurrentVisibilityLevel(): VisibilityLevel? = visLevel

        override fun setCurrentVisibilityLevel(p0: VisibilityLevel?) {
            log.info("dont think thi9s should be called")
        }

        override fun isRelayoutNeeded(): Boolean {
            // todo: decide how to implement this
            return false
        }
    }
}
