package io.github.intellij.dlanguage.inspections

import com.intellij.codeInsight.intention.HighPriorityAction
import com.intellij.codeInspection.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.module.ModuleUtilCore
import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.ProjectJdkTable
import com.intellij.openapi.roots.ModuleRootModificationUtil
import com.intellij.openapi.roots.ui.configuration.ProjectSettingsService
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.impl.named.DlangIdentifierImpl
import io.github.intellij.dlanguage.resolve.DResolveUtil
import io.github.intellij.dlanguage.resolve.SpecialCaseResolve
import io.github.intellij.dlanguage.utils.Identifier
import io.github.intellij.dlanguage.utils.ModuleDeclaration
import io.github.intellij.dlanguage.utils.VersionCondition


/**
 * Created by francis on 7/25/2017.
 * due to string mixins it is very impractical to determine whether something is actually an undefined symbol
 */

fun symbolIsDefinedByDefault(identifier: Identifier): Boolean {
    val name = identifier.name
    if(PsiTreeUtil.getParentOfType(identifier,ModuleDeclaration::class.java) != null)
        return true
    return name == "sizeof" || name == "nan" || name == "init" || name == "mangleof" || name == "stringof" || name == "alignof" || name == "max" || name == "min" || name == "infinity" || name == "dig" || name == "epsilon" || name == "mant_dig" || name == "max_10_exp" || name == "max_exp" || name == "min_10_exp" || name == "min_exp" || name == "min_normal" || name == "re" || name == "im" || name == "classinfo"
}

class PossiblyUndefinedSymbol : LocalInspectionTool() {
    class UndefinedSymbolVisitor(val holder: ProblemsHolder) : DlangVisitor() {
        val objectDotDContents = setOf<String>("string", "size_t", "ptrdiff_t", "_d_newclass", "rt_finalize", "object", "sizediff_t", "hash_t", "equals_t", "wstring", "dstring", "selector", "Object", "toString", "toHash", "opCmp", "opEquals", "Monitor", "lock", "unlock", "factory", "opEquals", "_d_setSameMutex", "setSameMutex", "Interface", "classinfo", "vtbl", "offset", "OffsetTypeInfo", "ti", "TypeInfo", "getHash", "equals", "compare", "tsize", "swap", "next", "initializer", "init", "flags", "offTi", "destroy", "postblit", "talign", "argTypes", "rtInfo", "TypeInfo_Typedef", "base", "name", "m_init", "TypeInfo_Enum", "TypeInfo_Pointer", "m_next", "TypeInfo_Array", "TypeInfo_StaticArray", "value", "len", "TypeInfo_AssociativeArray", "value", "key", "TypeInfo_Vector", "TypeInfo_Function", "deco", "TypeInfo_Delegate", "TypeInfo_Class", "interfaces", "ClassFlags", "classInvariant", "m_flags", "deallocator", "m_offTi", "defaultConstructor", "m_RTInfo", "find", "create", "ClassInfo", "TypeInfo_Interface", "TypeInfo_Struct", "xtoHash", "xopEquals", "xopCmp", "xtoString", "StructFlags", "hasPointers", "isDynamicType", "m_flags", "xdtor", "xdtorti", "xpostblit", "m_align", "TypeInfo_Tuple", "elements", "TypeInfo_Invariant", "TypeInfo_Const", "TypeInfo_Shared", "TypeInfo_Inout", "MIctorstart", "MIctordone", "MIstandalone", "MItlsctor", "MItlsdtor", "MIctor", "MIdtor", "MIxgetMembers", "MIictor", "MIunitTest", "MIimportedModules", "MIlocalClasses", "MIname", "ModuleInfo", "_flags", "_index", "opAssign", "ctor", "dtor", "ictor", "Throwable", "TraceInfo", "Exception", "Error")//todo this isn't all public symbols in object.d, and this is probably not the best way of detecting if runtime is not configured
        val log: Logger = Logger.getInstance(this::class.java)

        fun isVersion(identifier: DlangIdentifierImpl) = identifier.parent is VersionCondition


        override fun visitIdentifier(identifier: DlangIdentifierImpl) {
            if (identifier != null) {
                val start = System.currentTimeMillis()
                if (DResolveUtil.getInstance(identifier.project).shouldNotResolveToAnything(identifier)) {
                    return
                }
//                if(SpecialCaseResolve.isApplicable(identifier)){
//                    if(SpecialCaseResolve.findDefinitionNode(identifier).isEmpty() && !symbolIsDefinedByDefault(identifier)){
//                        holder.registerProblem(identifier, "Possibly undefined symbol")
//                    }
//                }
//                else if (BasicResolve.findDefinitionNode(identifier.project, identifier).isEmpty() && !symbolIsDefinedByDefault(identifier)) {
//                    holder.registerProblem(identifier, "Possibly undefined symbol")
//                }
                if (DResolveUtil.getInstance(identifier.project).findDefinitionNode(identifier, false).isEmpty() && !symbolIsDefinedByDefault(identifier)) {

                    if (objectDotDContents.contains(identifier.name))
                        holder.registerProblem(identifier, "Possibly undefined symbol - SDK not setup", SetupSDK(identifier.containingFile))
                    else if (isVersion(identifier))
                        holder.registerProblem(identifier, "Possibly undefined symbol", ProblemHighlightType.WEAK_WARNING)
                    else
                        holder.registerProblem(identifier, "Possibly undefined symbol")//todo add quick fix
                }
                val end = System.currentTimeMillis()
                if (end - start > 50) {
                    log.info("resolve took a while" + (end - start))
                    DResolveUtil.getInstance(identifier.project).findDefinitionNode(identifier, true)
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


class SetupSDK(file: PsiFile) : LocalQuickFixOnPsiElement(file),HighPriorityAction{
    override fun getText(): String {
        return "Setup SDK"//todo
    }

    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
        val projectJdk = ProjectSettingsService.getInstance(project).chooseAndSetSdk() ?: return
        ApplicationManager.getApplication().runWriteAction {
            val module = ModuleUtilCore.findModuleForPsiElement(file)
            if (module != null) {
                ModuleRootModificationUtil.setSdkInherited(module)
            }
        }
    }

    override fun getFamilyName(): String {
        return "DLang"//todo needs internationalization
    }

    override fun startInWriteAction(): Boolean = false

}
