package io.github.intellij.dlanguage.inspections

import com.intellij.codeInsight.intention.HighPriorityAction
import com.intellij.codeInspection.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.module.ModuleUtilCore
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootModificationUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiReference
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.resolve.DResolveUtil
import io.github.intellij.dlanguage.utils.*


/**
 * Created by francis on 7/25/2017.
 * due to string mixins it is very impractical to determine whether something is actually an undefined symbol
 */

fun symbolIsDefinedByDefault(identifier: PsiElement): Boolean {
    val name = identifier.text
    if(PsiTreeUtil.getParentOfType(identifier, ModuleDeclaration::class.java) != null)
        return true
    return name == "sizeof" || name == "nan" || name == "init" || name == "mangleof" || name == "stringof" || name == "alignof" || name == "max" || name == "min" || name == "infinity" || name == "dig" || name == "epsilon" || name == "mant_dig" || name == "max_10_exp" || name == "max_exp" || name == "min_10_exp" || name == "min_exp" || name == "min_normal" || name == "re" || name == "im" || name == "classinfo"
}

class PossiblyUndefinedSymbol : LocalInspectionTool() {
    class UndefinedSymbolVisitor(val holder: ProblemsHolder) : DlangVisitor() {
        private val objectDotDContents = setOf("string", "size_t", "ptrdiff_t", "_d_newclass", "rt_finalize", "object", "sizediff_t", "hash_t", "equals_t", "wstring", "dstring", "selector", "Object", "toString", "toHash", "opCmp", "opEquals", "Monitor", "lock", "unlock", "factory", "opEquals", "_d_setSameMutex", "setSameMutex", "Interface", "classinfo", "vtbl", "offset", "OffsetTypeInfo", "ti", "TypeInfo", "getHash", "equals", "compare", "tsize", "swap", "next", "initializer", "init", "flags", "offTi", "destroy", "postblit", "talign", "argTypes", "rtInfo", "TypeInfo_Typedef", "base", "name", "m_init", "TypeInfo_Enum", "TypeInfo_Pointer", "m_next", "TypeInfo_Array", "TypeInfo_StaticArray", "value", "len", "TypeInfo_AssociativeArray", "value", "key", "TypeInfo_Vector", "TypeInfo_Function", "deco", "TypeInfo_Delegate", "TypeInfo_Class", "interfaces", "ClassFlags", "classInvariant", "m_flags", "deallocator", "m_offTi", "defaultConstructor", "m_RTInfo", "find", "create", "ClassInfo", "TypeInfo_Interface", "TypeInfo_Struct", "xtoHash", "xopEquals", "xopCmp", "xtoString", "StructFlags", "hasPointers", "isDynamicType", "m_flags", "xdtor", "xdtorti", "xpostblit", "m_align", "TypeInfo_Tuple", "elements", "TypeInfo_Invariant", "TypeInfo_Const", "TypeInfo_Shared", "TypeInfo_Inout", "MIctorstart", "MIctordone", "MIstandalone", "MItlsctor", "MItlsdtor", "MIctor", "MIdtor", "MIxgetMembers", "MIictor", "MIunitTest", "MIimportedModules", "MIlocalClasses", "MIname", "ModuleInfo", "_flags", "_index", "opAssign", "ctor", "dtor", "ictor", "Throwable", "TraceInfo", "Exception", "Error")//todo this isn't all public symbols in object.d, and this is probably not the best way of detecting if runtime is not configured
        private val log: Logger = Logger.getInstance(this::class.java)

        override fun visitElement(element: PsiElement) {
            val start = System.currentTimeMillis()
            if (element.reference == null && element.references.isEmpty())
                return

            if (DResolveUtil.getInstance(element.project).shouldNotResolveToAnything(element)) {
                return
            }
            if (element.reference != null) {
                handleReference(element.reference!!)
            } else {
                for (ref in element.references) {
                    handleReference(ref)
                }
            }
            val end = System.currentTimeMillis()
            if (end - start > 50) {
                log.info("resolve took a while" + (end - start))
                DResolveUtil.getInstance(element.project).findDefinitionNode(element, true)
            }
        }

        private fun handleReference(reference: PsiReference) {
            val element = reference.element
            if (reference.resolve() == null && !symbolIsDefinedByDefault(element)) {
                if (element is IdentifierChain) {
                    val importElt = PsiTreeUtil.getParentOfType(element, SingleImport::class.java)
                    if (importElt != null && importElt.identifierChain == element) {
                        holder.registerProblem(element.parent, "Unresolved import", ProblemHighlightType.ERROR)
                    }
                    // else it’s a package. Packages may not reflect a real folder, it’s fine
                } else if (element.parent is SingleImport) {
                    // Its new name of a renamed import, it’s a new identifier fine
                } else if (objectDotDContents.contains(element.text))
                    holder.registerProblem(
                        element,
                        "Possibly undefined symbol - SDK not setup", // todo: add internationalization string to message bundle
                        SetupSdkQuickFix(element.containingFile)
                    )
                else if (element.parent is VersionCondition) {
                    // If version identifier is not defined, this mean that the version is not enabled
                } else if (element.parent is DebugCondition) {
                    // If version identifier is not defined, this mean that the debug is not enabled
                } else {
                    var elementToRegister = element
                    when (element) {
                        is QualifiedIdentifier -> {
                            element.identifier?:return
                            elementToRegister = element.identifier!!
                        }

                        is ReferenceExpression -> {
                            element.identifier ?: return
                            elementToRegister = element.identifier!!
                        }

                        is ImportBind -> {
                            element.identifier ?: return
                            elementToRegister = element.identifier!!
                        }
                    }
                    holder.registerProblem(elementToRegister, "Possibly undefined symbol") // todo: add quick fix
                }
            }
        }
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = UndefinedSymbolVisitor(holder)

    override fun getDisplayName(): String = DlangBundle.message("d.inspections.symbol.possiblyundefined.displayname")

    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")
}

//fun isSDKSetup(project: Project){
//    ProjectJdkTable.getInstance().allJdks
//}


// todo: move this Quickfix to its own class file
class SetupSdkQuickFix(file: PsiFile) : LocalQuickFixOnPsiElement(file), HighPriorityAction {
    override fun getText(): String = "Setup SDK" // todo: needs internationalization

    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
//        val projectJdk = ProjectSettingsService.getInstance(project).chooseAndSetSdk() ?: return
        ApplicationManager.getApplication().runWriteAction {
            val module = ModuleUtilCore.findModuleForPsiElement(file)
            if (module != null) {
                ModuleRootModificationUtil.setSdkInherited(module)
            }
        }
    }

    override fun getFamilyName(): String = "DLang" // todo: needs internationalization

    override fun startInWriteAction(): Boolean = false

}
