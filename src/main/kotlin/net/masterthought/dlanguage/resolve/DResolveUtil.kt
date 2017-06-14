package net.masterthought.dlanguage.resolve

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.util.containers.ContainerUtil
import net.masterthought.dlanguage.index.DModuleIndex
import net.masterthought.dlanguage.psi.*
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.psi.interfaces.Declaration
import net.masterthought.dlanguage.stubs.index.DAllNameIndex
import java.util.*

/**
 * Created by francis on 5/12/17.
 */
object DResolveUtil {
    /**
     * Finds name definition across all Haskell files in the project. All
     * definitions are found when name is null.
     */
    fun findDefinitionNode(project: Project, name: String?, e: PsiNamedElement?): List<PsiNamedElement> {
        // Guess where the name could be defined by lookup up potential modules.
        val potentialModules: MutableSet<String> = if (e == null)
            mutableSetOf()
        else
            DPsiUtil.parseImports(e.containingFile)

        val result = ContainerUtil.newArrayList<PsiNamedElement>()
        val psiFile = if (e == null) null else e.containingFile.originalFile
        // find definition in current file
        if (psiFile is DLanguageFile) {
            findDefinitionNode(psiFile as DLanguageFile?, name, e, result)
        }
        // find definition in imported files
        for (potentialModule in potentialModules) {
            val files = DModuleIndex.getFilesByModuleName(project, potentialModule, GlobalSearchScope.allScope(project))
            for (f in files) {
                val returnAllReferences = name == null
                val inLocalModule = f != null && f == psiFile
                val inImportedModule = f != null && potentialModules.contains(f.moduleName)
                if (returnAllReferences || inLocalModule || inImportedModule) {
                    findDefinitionNode(f, name, e, result)
                }
            }
        }
        return result
    }

    /**
     * finds definition(s) of functions/class/template
     * todo this method could be made more efficient and effective. Use a stub tree?
     * @param file the file to search for definitions in
     * *
     * @param name the name of the function/class/template to resolve
     * *
     * @param e the usage of the defined function/class/template etc.
     * *
     * @param result the results are added to the is arraylist
     */
    fun findDefinitionNode(file: DLanguageFile?, name: String?, e: PsiNamedElement?, result: MutableList<PsiNamedElement>) {
        if (file == null) return
        // start with empty list of potential named elements
        val declarationElements = ArrayList<DNamedElement>()

        if (e is DLanguageIdentifier) {

            val declarations = ArrayList<Declaration>()
            val elements = StubIndex.getElements(DAllNameIndex.KEY, e.name, e.project, GlobalSearchScope.fileScope(file), DNamedElement::class.java)
            for (element in elements) {
                if (element is Declaration) {
                    declarations.add(element)
                }
            }

            for (candidateDeclaration in declarations) {
                if (candidateDeclaration is DLanguageAutoDeclarationY) {
                    if (candidateDeclaration.actuallyIsDeclaration()) {
                        declarationElements.add(candidateDeclaration)
                    }
                    continue
                }
                declarationElements.add(candidateDeclaration)
            }
        }

        var resolvingConstructor = false

        var parent: PsiElement? = e!!.parent
        while (true) {
            if (parent == null)
                break
            if (parent is DLanguageNewExpression || parent is DLanguageNewExpressionWithArgs)
                resolvingConstructor = true
            parent = parent.parent
        }

        // check the list of potential named elements for a match on name
        for (namedElement in declarationElements) {
            //non void initializer
            if (resolvingConstructor) {
                if (namedElement is DLanguageConstructor) {
                    result.add(namedElement)
                }
            } else if (name == null || name == namedElement.name && e != namedElement && namedElement !is DLanguageConstructor) {
                result.add(namedElement)
            }
        }
    }

    /**
     * Finds a name definition inside a D file. All definitions are found when name
     * is null.
     */
    fun findDefinitionNodes(dLanguageFile: DLanguageFile?, name: String?): MutableList<PsiNamedElement> {
        val ret = ContainerUtil.newArrayList<PsiNamedElement>()
        findDefinitionNode(dLanguageFile, name, null, ret)
        return ret
    }

    /**
     * Finds name definition across all D files in the project. All
     * definitions are found when name is null.
     */
    fun findDefinitionNodes(project: Project): List<PsiNamedElement> {
        return findDefinitionNode(project, null, null)
    }

    /**
     * Finds name definitions that are within the scope of a file, including imports (to some degree).
     */
    fun findDefinitionNodes(psiFile: DLanguageFile): List<PsiNamedElement> {
        val result = findDefinitionNodes(psiFile, null)
        result.addAll(findDefinitionNode(psiFile.project, null, null))
        return result
    }
}
