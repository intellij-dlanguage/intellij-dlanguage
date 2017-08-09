package net.masterthought.dlanguage.resolve.processors

import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.NamedStubBase
import com.intellij.psi.stubs.StubIndex
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.psi.interfaces.HasMembers
import net.masterthought.dlanguage.resolve.DResolveUtil
import net.masterthought.dlanguage.stubs.index.DTopLevelDeclarationIndex
import net.masterthought.dlanguage.utils.Constructor
import net.masterthought.dlanguage.utils.Identifier
import net.masterthought.dlanguage.utils.ImportDeclaration
import net.masterthought.dlanguage.utils.SingleImport

/**
 * Created by francis on 6/15/2017.
 */
class DNameScopeProcessor(var start: Identifier, val profile: Boolean = false) : DResolveProcessor<DNamedElement, DNamedElement> {
    override fun matches(call: DNamedElement, decl: DNamedElement): Boolean {
        return true
    }

    val project = start.project
    val log: Logger = Logger.getInstance(this::class.java)

    override val result = mutableSetOf<DNamedElement>()

    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        if (element is DNamedElement) {
            if (element.name == start.name) {
                if (element !is Constructor) {//todo this class should be renamed because of this
                    result.add(element)
                    return false
                }
            }
            if (element is SingleImport) {
                if ((element.parent as ImportDeclaration).importBindings?.importBinds?.size == 0 || (element.parent as ImportDeclaration).importBindings?.importBinds == null) {
                    for (import in (element.parent as ImportDeclaration).singleImports) {
                        val startTime = System.currentTimeMillis()
                        val imported = DResolveUtil.getInstance(element.project).getAllPubliclyImportedAsFiles(mutableSetOf(import.identifierChain!!.text))
                        val end = System.currentTimeMillis()
                        if (profile) {
                            log.info("getting imported:" + (end - startTime))
                        }
                        for (file in imported) {
                            result.addAll(StubIndex.getElements(DTopLevelDeclarationIndex.KEY, start.name, project, GlobalSearchScope.fileScope(file), DNamedElement::class.java))
                        }
                    }
                } else {
                    for (bind in (element.parent as ImportDeclaration).importBindings?.importBinds!!) {
                        val startTime = System.currentTimeMillis()
                        val bindSymbolDeclarations = DResolveUtil.getInstance(project).findDefinitionNode(bind.identifier!!, profile)
                        val end = System.currentTimeMillis()
                        if (profile) {
                            log.info("getting bind symbol declaration:" + (end - startTime))
                        }
                        for (resolveResult in bindSymbolDeclarations) {
                            if ((resolveResult as DNamedElement).name == start.name) {
                                result.add(resolveResult)
                            }
                            if (resolveResult is HasMembers<*>) {
                                getMembersOfBind(resolveResult as DNamedElement)
                            }
                        }
                    }
                }
            }
        }
        else{
            throw IllegalStateException()
        }
        return true
    }

    private fun getMembersOfBind(resolveResult: DNamedElement) {
        for (member: NamedStubBase<*> in (resolveResult as HasMembers<*>).members) {
            if (member.name == start.name) {
                result.add((member.psi as DNamedElement))
            }
        }
    }

}
