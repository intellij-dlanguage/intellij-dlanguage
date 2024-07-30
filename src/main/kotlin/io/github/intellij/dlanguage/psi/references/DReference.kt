package io.github.intellij.dlanguage.psi.references

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.IncorrectOperationException
import io.github.intellij.dlanguage.processors.DCompletionProcessor
import io.github.intellij.dlanguage.psi.DlangPsiFile
import io.github.intellij.dlanguage.psi.impl.DElementFactory
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.resolve.DResolveUtil
import io.github.intellij.dlanguage.resolve.processors.basic.BasicResolve
import io.github.intellij.dlanguage.stubs.index.DTopLevelDeclarationsByModule
import io.github.intellij.dlanguage.utils.*
import java.util.*


/**
 * Resolves references to elements.
 */
class DReference(element: PsiElement, textRange: TextRange, private val qualifier: PsiElement?, private val referenceName: String?)
    : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference, PsiQualifiedReference {

    /**
     * Resolves references to a set of results.
     */
    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val project = myElement.project
        val namedElements = DResolveUtil.getInstance(project).findDefinitionNode(myElement, false)
        val results = mutableListOf<PsiElementResolveResult>()
        for (property in namedElements) {
            results.add(PsiElementResolveResult(property))
        }
        return results.toTypedArray()
    }

    /**
     * Resolves references to a single result, or fails.
     */
    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    val log = Logger.getInstance(this::class.java)

    /**
     * Controls what names that get added to the autocompletion popup available
     * on ctrl-space.
     */
    override fun getVariants(): Array<Any> {

        if(!PropertiesComponent.getInstance().getBoolean("USE_NATIVE_CODE_COMPLETION")) return EMPTY_ARRAY

        val project = myElement.project
        val result = Collections.synchronizedList(ArrayList<String>())
        //todo a lot of these would be best implemented with a completion contributor
        addSymbolsFromFile(result)
        addSymbolsFromImports(project, result)
        addDRuntimeSymbols(project, result)
        val inImportOrModuleDeclaration: Boolean = PsiTreeUtil.getParentOfType(element, ImportDeclaration::class.java, ModuleDeclaration::class.java) != null
        if (!inImportOrModuleDeclaration) {
            addAllModules(result, project)
        } else {
            addModuleVariants(result, element)
        }
        addKeywords(result)
        return result.toTypedArray()
    }

    override fun getQualifier(): PsiElement? {
        return qualifier
    }

    override fun getReferenceName(): String? {
        return referenceName
    }

    private fun addModuleVariants(result: MutableList<String>, element: PsiElement) {
        val moduleSoFar: IdentifierChain
        if (element is SingleImport) {
            moduleSoFar = element.identifierChain!!
        } else {
            if (element.parent is IdentifierChain)
                moduleSoFar = element.parent as IdentifierChain
            else {
                return
            }
        }
        val moduleNameSoFar: String = moduleSoFar.text.replace(".IntellijIdeaRulezzz", "")
        val matchingModules = StubIndex.getInstance().getAllKeys(DTopLevelDeclarationsByModule.KEY, element.project).filter { it.startsWith(moduleNameSoFar) }
        val suggestedCompletions = matchingModules.map { it.removePrefix(moduleNameSoFar + ".") }
        result += suggestedCompletions

    }

    private fun addSymbolsFromImports(project: Project, result: MutableList<String>) {
        val decls = StubIndex.getElements(DTopLevelDeclarationsByModule.KEY, (element.containingFile as DlangPsiFile).getFullyQualifiedModuleName(), project, GlobalSearchScope.fileScope(element.containingFile), DNamedElement::class.java)
        for (decl in decls) {
            if (decl is FunctionDeclaration) {
                result.add(decl.name + "(" + ")")
            } else
                result.add(decl.name)
        }
    }

    private fun addSymbolsFromFile(result: MutableList<String>) {
        val completionProcessor = DCompletionProcessor()
        PsiTreeUtil.treeWalkUp(completionProcessor, myElement, myElement.containingFile, ResolveState.initial())
        val elementsWithModuleNames = completionProcessor.completions
        elementsWithModuleNames.removeAll(HashSet(StubIndex.getInstance().getAllKeys(DTopLevelDeclarationsByModule.KEY, myElement.project)))//this should be fixed in the index getSymbolsFromImports
        result.addAll(elementsWithModuleNames)//todo this could be more efficiently implemented
    }

    private fun addKeywords(result: MutableList<String>) {
        result.add("abstract")
        result.add("alias")
        result.add("align")
        result.add("asm")
        result.add("assert")
        result.add("auto")
        result.add("bool")
        result.add("break")
        result.add("byte")
        result.add("case")
        result.add("cast")
        result.add("catch")
        result.add("cdouble")
        result.add("cent")
        result.add("cfloat")
        result.add("char")
        result.add("class")
        result.add("const")
        result.add("continue")
        result.add("creal")
        result.add("dchar")
        result.add("debug")
        result.add("default")
        result.add("delegate")
        result.add("delete")
        result.add("deprecated")
        result.add("do")
        result.add("double")
        result.add("else")
        result.add("enum")
        result.add("export")
        result.add("extern")
        result.add("false")
        result.add("final")
        result.add("finally")
        result.add("float")
        result.add("for")
        result.add("foreach")
        result.add("foreach_reverse")
        result.add("function")
        result.add("goto")
        result.add("idouble")
        result.add("if")
        result.add("ifloat")
        result.add("immutable")
        result.add("import")
        result.add("in")
        result.add("inout")
        result.add("int")
        result.add("interface")
        result.add("invariant")
        result.add("ireal")
        result.add("is")
        result.add("lazy")
        result.add("long")
        result.add("mixin")
        result.add("module")
        result.add("new")
        result.add("nothrow")
        result.add("null")
        result.add("out")
        result.add("override")
        result.add("package")
        result.add("pragma")
        result.add("private")
        result.add("protected")
        result.add("public")
        result.add("pure")
        result.add("real")
        result.add("ref")
        result.add("return")
        result.add("scope")
        result.add("shared")
        result.add("short")
        result.add("static")
        result.add("struct")
        result.add("super")
        result.add("switch")
        result.add("synchronized")
        result.add("template")
        result.add("this")
        result.add("throw")
        result.add("true")
        result.add("try")
        result.add("typedef")
        result.add("typeid")
        result.add("typeof")
        result.add("ubyte")
        result.add("ucent")
        result.add("uint")
        result.add("ulong")
        result.add("union")
        result.add("unittest")
        result.add("ushort")
        result.add("version")
        result.add("void")
        result.add("volatile")
        result.add("wchar")
        result.add("while")
        result.add("with")
        result.add("__FILE__")
        result.add("__FILE_FULL_PATH__")
        result.add("__MODULE__")
        result.add("__LINE__")
        result.add("__FUNCTION__")
        result.add("__PRETTY_FUNCTION__")
        result.add("__gshared")
        result.add("__traits")
        result.add("__vector")
        result.add("__parameters")
    }

    private fun addDRuntimeSymbols(project: Project, result: MutableList<String>) {
        val objectDotD = BasicResolve.getInstance(project).`object`
        if (objectDotD != null) {
            result.addAll(StubIndex.getElements(DTopLevelDeclarationsByModule.KEY, objectDotD.getFullyQualifiedModuleName(), project, GlobalSearchScope.fileScope(objectDotD), DNamedElement::class.java).map { it.name })
        }
    }

    private fun addAllModules(result: MutableList<String>, project: Project) {
        result.addAll(StubIndex.getInstance().getAllKeys(DTopLevelDeclarationsByModule.KEY, project))
    }

    /**
     * Called when renaming refactoring tries to rename the Psi tree.
     */
    @Throws(IncorrectOperationException::class)
    override fun handleElementRename(newElementName: String): PsiElement {
        if (myElement is IdentifierChain) {
            if ((myElement as IdentifierChain).identifier == null)
                throw IncorrectOperationException()
            // Renaming files is tricky: we don't want to change `RenamePsiFileProcessor`,
            // If it’s end by `.d` then it’s because we renamed a file
            val newName = StringUtil.trimEnd(newElementName, ".d")
            (myElement as IdentifierChain).identifier!!.replace(DElementFactory.createDLanguageIdentifierFromText(myElement.project, newName)!!)
            return myElement
        }
        if (myElement is ImportBind) {
            if ((myElement as ImportBind).identifier == null)
                throw IncorrectOperationException()
            (myElement as ImportBind).identifier!!.replace(DElementFactory.createDLanguageIdentifierFromText(myElement.project, newElementName)!!)
            return myElement
        }
        if (myElement is ReferenceExpression) {
            if ((myElement as ReferenceExpression).identifier != null) {
                (myElement as ReferenceExpression).identifier!!.replace(
                    DElementFactory.createDLanguageIdentifierFromText(
                        myElement.project,
                        newElementName
                    )!!
                )
            } else if ((myElement as ReferenceExpression).templateInstance != null && (myElement as ReferenceExpression).templateInstance!!.identifier != null) {
                (myElement as ReferenceExpression).templateInstance!!.identifier!!.replace(
                    DElementFactory.createDLanguageIdentifierFromText(
                        myElement.project,
                        newElementName
                    )!!
                )
            } else {
                throw IncorrectOperationException()
            }
            return myElement
        }
        if (myElement is QualifiedIdentifier) {
            if ((myElement as QualifiedIdentifier).identifier != null) {
                (myElement as QualifiedIdentifier).identifier!!.replace(
                    DElementFactory.createDLanguageIdentifierFromText(
                        myElement.project,
                        newElementName
                    )!!
                )
            } else if ((myElement as QualifiedIdentifier).templateInstance != null && (myElement as QualifiedIdentifier).templateInstance!!.identifier != null) {
                (myElement as QualifiedIdentifier).templateInstance!!.identifier!!.replace(
                    DElementFactory.createDLanguageIdentifierFromText(
                        myElement.project,
                        newElementName
                    )!!
                )
            } else {
                throw IncorrectOperationException()
            }
            return myElement
        }
        return super.handleElementRename(newElementName)
    }

    companion object {
        val EMPTY_RESOLVE_RESULT = arrayOfNulls<ResolveResult>(0)
        val EMPTY_ARRAY = emptyArray<Any>()
        val NAME_NOT_FOUND_STRING: String = ""
    }
}

