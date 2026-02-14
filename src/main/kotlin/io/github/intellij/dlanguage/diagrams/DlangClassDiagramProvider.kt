package io.github.intellij.dlanguage.diagrams

import com.intellij.diagram.*
import com.intellij.diagram.DiagramRelationshipInfoAdapter
import com.intellij.diagram.presentation.DiagramLineType
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.ModificationTracker
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement

/*
* A diagram provider that generates UML based on D source code
*
* For example code, take a look at:
* https://github.com/JetBrains/intellij-plugins/blob/master/AngularJS/src/org/angularjs/diagram/AngularUiRouterDiagramProvider.java
*/
class DlangClassDiagramProvider : BaseDiagramProvider<PsiElement>() {

    // val DOTTED_STROKE: BasicStroke = BasicStroke(0.7f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, floatArrayOf(2f, 2f), 0.0f)
    // val WARNING_BORDER: StrokeBorder = StrokeBorder(DOTTED_STROKE, JBColor.red)
    // val ERROR_BORDER: Border = JBUI.Borders.customLine(JBColor.red)
    // val NORMAL_BORDER: Border = JBUI.Borders.customLine(Gray._190)

    private companion object {
        const val ID = "DlangUML"
    }

    val log: Logger = Logger.getInstance(DlangClassDiagramProvider::class.java)
    val classDiagramElementManager: AbstractDiagramElementManager<PsiElement> = DlangClassDiagramElementManager()

    override fun getID(): String = ID

    override fun getPresentableName(): String = "D class diagram"

    override fun createVisibilityManager(): DiagramVisibilityManager {
        return DlangDiagramVisibilityManager()
    }

    override fun getElementManager(): DiagramElementManager<PsiElement> = this.classDiagramElementManager

    // get as a service?? ApplicationManager.getApplication().getService(DlangClassDiagramVfsResolver.class)
    override fun getVfsResolver(): DiagramVfsResolver<PsiElement> = DlangClassDiagramVfsResolver()

    override fun getRelationshipManager(): DiagramRelationshipManager<PsiElement> {
        return DiagramRelationshipManager<PsiElement> { p0, p1, category -> DiagramRelationshipInfoAdapter("todo", DiagramLineType.DASHED) }
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
//            com.intellij.uml.UmlGraphBuilder(Project, Graph2D, Graph2DView, DiagramDataModel<?>, GraphThreadingType, DiagramPresentationModel)

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
            //log.info("dont think this should be called")
        }

        override fun isRelayoutNeeded(): Boolean {
            // todo: decide how to implement this
            return false
        }
    }
}
