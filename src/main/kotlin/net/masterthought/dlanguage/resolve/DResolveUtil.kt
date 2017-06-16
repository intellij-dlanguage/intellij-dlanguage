package net.masterthought.dlanguage.resolve

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.ResolveState
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.containers.ContainerUtil
import net.masterthought.dlanguage.index.DModuleIndex
import net.masterthought.dlanguage.psi.*
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.psi.interfaces.Declaration
import net.masterthought.dlanguage.stubs.index.DTopLevelDeclarationIndex
import net.masterthought.dlanguage.utils.Constructor
import net.masterthought.dlanguage.utils.Identifier
import java.util.*

/**
 * Created by francis on 5/12/17.
 */
object DResolveUtil {
    /**
     * Finds name definition across all Haskell files in the project. All
     * definitions are found when name is null.
     */
    fun findDefinitionNode(project: Project, e: PsiNamedElement): List<PsiNamedElement> {
        // Guess where the name could be defined by lookup up potential modules.
        if (e !is Identifier) {
            return emptyList()
        }

        val nameProcessor = DNameScopeProcessor(e)
        PsiTreeUtil.treeWalkUp(nameProcessor, e, e.containingFile, ResolveState.initial())
        if (nameProcessor.result.size != 0) {
            return nameProcessor.result.toList()
        }

        val importProcessor = DImportScopeProcessor()
        PsiTreeUtil.treeWalkUp(importProcessor, e, e.containingFile, ResolveState.initial())
        val potentialModules: MutableList<String> = mutableListOf()
        importProcessor.imports.mapTo(potentialModules) { it.name }

        val result = mutableSetOf<PsiNamedElement>()
        // find definition in imported files
        for (potentialModule in potentialModules) {
            val files = DModuleIndex.getFilesByModuleName(project, potentialModule, GlobalSearchScope.allScope(project))
            for (f in files) {
                result.addAll(StubIndex.getElements(DTopLevelDeclarationIndex.KEY, e.name, e.project, GlobalSearchScope.fileScope(f), Declaration::class.java))
            }
        }
        val finalResult = mutableListOf<PsiNamedElement>()
        for (element in result) {
            if (element is Constructor) {
                if (resolvingConstructor(e)) {
                    finalResult.add(element)
                } else continue
            }
            if (!resolvingConstructor(e)) {
                finalResult.add(element)
            }
        }
        return finalResult
    }

    fun resolvingConstructor(e: PsiElement): Boolean {
        var parent: PsiElement? = e.parent
        while (true) {
            if (parent == null)
                break
            if (parent is DLanguageNewExpression || parent is DLanguageNewExpressionWithArgs)
                return true
            parent = parent.parent
        }
        return false
    }

    /**
     * finds definition(s) of functions/class/template
     * @param file the file to search for definitions in
     * @param name the name of the function/class/template to resolve
     * @param e the usage of the defined function/class/template etc.
     * @param result the results are added to the is arraylist
     */
    fun findDefinitionNode(file: DLanguageFile?, name: String?, e: PsiNamedElement?, result: MutableList<PsiNamedElement>) {
        if (file == null) return
        // start with empty list of potential named elements
        val declarationElements = ArrayList<DNamedElement>()

        if (e is DLanguageIdentifier) {

            val declarations = StubIndex.getElements(DTopLevelDeclarationIndex.KEY, e.name, e.project, GlobalSearchScope.fileScope(file), Declaration::class.java)

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



        // check the list of potential named elements for a match on name
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

}
