package net.masterthought.dlanguage.psi.references

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.IncorrectOperationException
import net.masterthought.dlanguage.index.DModuleIndex
import net.masterthought.dlanguage.processors.DCompletionProcessor
import net.masterthought.dlanguage.processors.DImportScopeProcessor
import net.masterthought.dlanguage.psi.DLanguageFile
import net.masterthought.dlanguage.psi.DLanguageIdentifier
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.resolve.DResolveUtil
import net.masterthought.dlanguage.stubs.index.DTopLevelDeclarationsByModule
import net.masterthought.dlanguage.utils.ModuleDeclaration
import net.masterthought.dlanguage.utils.SingleImport
import java.util.*


/**
 * Resolves references to elements.
 */
class DReference(element: PsiNamedElement, textRange: TextRange) : PsiReferenceBase<PsiNamedElement>(element, textRange), PsiPolyVariantReference {

    private val name: String

    init {
        name = element.name!!
    }

    /**
     * Resolves references to a set of results.
     */
    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        //        // We should only be resolving ddfunction_definition.
        //        if (!(myElement instanceof DDefinitionFunction)) {
        //            return EMPTY_RESOLVE_RESULT;
        //        }
        //        // Make sure that we only complete the last conid in a qualified expression.
        //        if (myElement instanceof HaskellConid) {
        //            // Don't resolve a module import to a constructor.
        //            if (PsiTreeUtil.getParentOfType(myElement, HaskellImpdecl.class) != null) { return EMPTY_RESOLVE_RESULT; }
        //            HaskellQconid qconid = PsiTreeUtil.getParentOfType(myElement, HaskellQconid.class);
        //            if (qconid == null) { return EMPTY_RESOLVE_RESULT; }
        //            if (!myElement.equals(Iterables.getLast(qconid.getConidList()))) { return EMPTY_RESOLVE_RESULT; }
        //        }


        val project = myElement.project
        val namedElements = DResolveUtil.getInstance(project).findDefinitionNode(myElement, true).map { if (it is PsiNameIdentifierOwner && it !is ModuleDeclaration && it !is SingleImport) if (it.nameIdentifier != null) it.nameIdentifier!! else it else it }
        val results = mutableListOf<PsiElementResolveResult>()
        for (property in namedElements) {
            results.add(PsiElementResolveResult(property))
        }
//        DirectoryIndex.getInstance(project).getDirectoriesByPackageName()
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
        //        // If we are not in an expression, don't provide reference completion.
        //        if (PsiTreeUtil.getParentOfType(myElement, HaskellExp.class) == null) {
        //            return new Object[]{};
        //        }
        //        // If we are in a qualified name, don't provide reference completion.
        //        final PsiElement qId = PsiTreeUtil.getParentOfType(myElement, HaskellQconid.class, HaskellQvarid.class);
        //        if (qId != null && qId.textContains('.')) {
        //            return new Object[]{};
        //        }

        //        final PsiFile containingFile = myElement.getContainingFile();
        //               if (!(containingFile instanceof DLanguageFile)) {
        //                   return new Object[]{};
        //               }
        //        int offset = myElement.getTextOffset();
        //
        //        DCDCompletion dcdCompletion = new DCDCompletion();
        //        return dcdCompletion.autoComplete(offset,containingFile).toArray();

        //        final PsiFile containingFile = myElement.getContainingFile();
        //        if (!(containingFile instanceof DLanguageFile)) {
        //            return new Object[]{};
        //        }
        //        List<PsiNamedElement> namedNodes = DUtil.findDefinitionNodes((DLanguageFile)containingFile);
        //        List<String> variants = new ArrayList<String>(20);
        //        for (final PsiNamedElement namedElement : namedNodes) {
        //            variants.add(namedElement.getName());
        //        }
        //        return variants.toArray();


//        val startProcessors = System.currentTimeMillis()
        val project = myElement.project
        val result = Collections.synchronizedList(ArrayList<String>())
        val importScopeProcessor = DImportScopeProcessor()
        PsiTreeUtil.treeWalkUp(importScopeProcessor, myElement, myElement.containingFile, ResolveState.initial())
        val potentialModules = HashSet<String>()
        for (dLanguageImport in importScopeProcessor.imports) {
            potentialModules.add(dLanguageImport.name)
        }

        val completionProcessor = DCompletionProcessor()
        PsiTreeUtil.treeWalkUp(completionProcessor, myElement, myElement.containingFile, ResolveState.initial())
        result.addAll(completionProcessor.completions)
        val decls = StubIndex.getElements(DTopLevelDeclarationsByModule.KEY, (element.containingFile as DLanguageFile).moduleOrFileName, project, GlobalSearchScope.fileScope(element.containingFile), DNamedElement::class.java)
        for (decl in decls) {
            result.add(decl.name)
        }



//        val endProcessors = System.currentTimeMillis()
//        log.info("processors " + (endProcessors - startProcessors))


//        val start = System.currentTimeMillis()
        // find definition in imported files
        for (potentialModule in potentialModules) {
            //todo add public imports
            val files = DModuleIndex.getFilesByModuleName(project, potentialModule, GlobalSearchScope.allScope(project))
            files.parallelStream().forEach { f ->
                val elements: MutableCollection<DNamedElement> = StubIndex.getElements(DTopLevelDeclarationsByModule.KEY, f.moduleOrFileName, project, GlobalSearchScope.fileScope(f), DNamedElement::class.java)
//                result.ensureCapacity(elements.size);
//                val startNames = System.currentTimeMillis()
                for (declaration in elements) {
                    result.add(declaration.name)
                }
//                val endNames = System.currentTimeMillis()
//                log.info("names:" + (endNames - startNames))
            }
        }
//        val end = System.currentTimeMillis()
//        log.info("other files:" + (end - start))
        result.add("abstract")
        result.add("alias")
        result.add("align")
        result.add("asm")
        result.add("assert")
        result.add("auto")
        result.add("body")
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
        return result.toTypedArray()
    }

    override fun getRangeInElement(): TextRange {
        return TextRange(0, this.element.node.textLength)
    }

    /**
     * Called when renaming refactoring tries to rename the Psi tree.
     */
    @Throws(IncorrectOperationException::class)
    override fun handleElementRename(newName: String): PsiElement {
        val element: PsiElement?
        if (myElement is DLanguageIdentifier) {
            element = myElement.setName(newName)
            return element
        }
        return super.handleElementRename(newName)
    }

    companion object {
        val EMPTY_RESOLVE_RESULT = arrayOfNulls<ResolveResult>(0)
        val NAME_NOT_FOUND_STRING: String = ""
    }
}

