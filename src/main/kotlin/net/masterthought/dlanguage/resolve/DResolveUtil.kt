package net.masterthought.dlanguage.resolve

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.ResolveState
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import net.masterthought.dlanguage.processors.DImportScopeProcessor
import net.masterthought.dlanguage.psi.DLanguageTypes.*
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.resolve.processors.basic.BasicResolve
import net.masterthought.dlanguage.resolve.processors.parameters.ParameterCountingResolve
import net.masterthought.dlanguage.stubs.index.DPublicImportIndex
import net.masterthought.dlanguage.utils.*

/**
 * Created by francis on 5/12/17.
 */
object DResolveUtil {

    fun findDefinitionNode(project: Project, e: PsiNamedElement): Set<PsiNamedElement> {
        if (shouldNotResolveToAnything(e)) {
            return emptySet()
        }

        val basicResolveResult = BasicResolve.findDefinitionNode(project, e)
        if (basicResolveResult.size == 1) {
            return basicResolveResult
        }
        val parameterCountingResult = ParameterCountingResolve.findDefinitionNode(project, e)
        if (parameterCountingResult.isEmpty()) {
            return basicResolveResult
        }
        return parameterCountingResult
    }


    fun resolvingConstructor(e: PsiElement): NewExpression? {
        return DPsiUtil.getParent(e, setOf(NEW_EXPRESSION), setOf(BLOCK_STATEMENT, STRUCT_BODY, DECLARATION)) as NewExpression?
    }

    fun getAllImportedModules(start: PsiElement): MutableSet<SingleImport> {
        val alreadyProcessed = mutableSetOf<SingleImport>()
        val toProcess = mutableSetOf<SingleImport>()
        val importScopeProcessor = DImportScopeProcessor()
        PsiTreeUtil.treeWalkUp(importScopeProcessor, start, start.containingFile, ResolveState.initial())
        toProcess.addAll(importScopeProcessor.imports)

        while (true) {
            val tempSet = mutableSetOf<SingleImport>()
            for (import in toProcess) {
                if (!alreadyProcessed.contains(import)) {
                    tempSet.addAll(StubIndex.getElements(DPublicImportIndex.KEY, import.name, import.project, GlobalSearchScope.everythingScope(import.project), SingleImport::class.java))
                }
            }
            alreadyProcessed.addAll(toProcess)
            toProcess.addAll(tempSet)
            if (alreadyProcessed == toProcess)
                break
        }
        return toProcess
    }

    private fun getAllPublicModules(start: PsiElement): MutableSet<SingleImport> {
        val importScopeProcessor = DImportScopeProcessor()
        PsiTreeUtil.treeWalkUp(importScopeProcessor, start, start.containingFile, ResolveState.initial())
        return importScopeProcessor.publicImports
    }


    fun shouldNotResolveToAnything(e: PsiNamedElement): Boolean {
        fun inModuleDeclaration(e: DNamedElement): ModuleDeclaration? {
            return PsiTreeUtil.getTopmostParentOfType(e, ModuleDeclaration::class.java)
        }

        if (e !is DNamedElement) {
            return true
        }
        if (e !is Identifier) {
            return true
        }
        val name = e.name
        val parent = e.parent
        if (e is Identifier) {
            if (name == "string" || name == "size_t" || name == "ptrdiff_t" || name == "dstring" || name == "wstring")     //todo this might be  defined in runtime
                return true
            if (parent is AtAttribute)
                if (name == "safe" || name == "trusted" || name == "nogc" || name == "disable" || name == "property")
                    return true
            if (parent is ScopeGuardStatement)
                if (name == "exit" || name == "success" || name == "failure")
                    return true
            if (parent is VersionCondition)
                if (name == "DigitalMars" || name == "GNU" || name == "LDC" || name == "SDC" || name == "Windows" || name == "Win32" || name == "Win64" || name == "linux" || name == "OSX" || name == "FreeBSD" || name == "OpenBSD" || name == "NetBSD" || name == "DragonFlyBSD" || name == "BSD" || name == "Solaris" || name == "Posix" || name == "AIX" || name == "Haiku" || name == "SkyOS" || name == "SysV3" || name == "SysV4" || name == "Hurd" || name == "Android" || name == "PlayStation" || name == "PlayStation4" || name == "Cygwin" || name == "MinGW" || name == "FreeStanding" || name == "CRuntime_Bionic" || name == "CRuntime_DigitalMars" || name == "CRuntime_Glibc" || name == "CRuntime_Microsoft" || name == "CRuntime_Musl" || name == "CRuntime_UClibc" || name == "X86" || name == "X86_64" || name == "ARM" || name == "ARM_Thumb" || name == "ARM_SoftFloat" || name == "ARM_SoftFP" || name == "ARM_HardFloat" || name == "AArch64" || name == "Epiphany" || name == "PPC" || name == "PPC_SoftFloat" || name == "PPC_HardFloat" || name == "PPC64" || name == "IA64" || name == "MIPS32" || name == "MIPS64" || name == "MIPS_O32" || name == "MIPS_N32" || name == "MIPS_O64" || name == "MIPS_N64" || name == "MIPS_EABI" || name == "MIPS_SoftFloat" || name == "MIPS_HardFloat" || name == "NVPTX" || name == "NVPTX64" || name == "RISCV32" || name == "RISCV64" || name == "SPARC" || name == "SPARC_V8Plus" || name == "SPARC_SoftFloat" || name == "SPARC_HardFloat" || name == "SPARC64" || name == "S390" || name == "SystemZ" || name == "HPPA" || name == "HPPA64" || name == "SH" || name == "Alpha" || name == "Alpha_SoftFloat" || name == "Alpha_HardFloat" || name == "LittleEndian" || name == "BigEndian" || name == "ELFv1" || name == "ELFv2" || name == "D_Coverage" || name == "D_Ddoc" || name == "D_InlineAsm_X86" || name == "D_InlineAsm_X86_64" || name == "D_LP64" || name == "D_X32" || name == "D_HardFloat" || name == "D_SoftFloat" || name == "D_PIC" || name == "D_SIMD" || name == "D_AVX" || name == "D_AVX2" || name == "D_Version2" || name == "D_NoBoundsChecks" || name == "D_ObjectiveC" || name == "unittest" || name == "assert" || name == "none" || name == "all")
                    return true
            if (parent is TraitsExpression)
                if (name == "isAbstractClass" || name == "isArithmetic" || name == "isAssociativeArray" || name == "isFinalClass" || name == "isPOD" || name == "isNested" || name == "isFloating" || name == "isIntegral" || name == "isScalar" || name == "isStaticArray" || name == "isUnsigned" || name == "isVirtualFunction" || name == "isVirtualMethod" || name == "isAbstractFunction" || name == "isFinalFunction" || name == "isStaticFunction" || name == "isOverrideFunction" || name == "isTemplate" || name == "isRef" || name == "isOut" || name == "isLazy" || name == "hasMember" || name == "identifier" || name == "getAliasThis" || name == "getAttributes" || name == "getFunctionAttributes" || name == "getFunctionVariadicStyle" || name == "getLinkage" || name == "getMember" || name == "getOverloads" || name == "getParameterStorageClasses" || name == "getPointerBitmap" || name == "getProtection" || name == "getVirtualFunctions" || name == "getVirtualMethods" || name == "getUnitTests" || name == "parent" || name == "classInstanceSize" || name == "getVirtualIndex" || name == "allMembers" || name == "derivedMembers" || name == "isSame" || name == "compiles")
                    return true
        }
        if (parent is FunctionDeclaration || parent is InterfaceOrClass || parent is StructDeclaration || parent is UnionDeclaration || parent is EnumDeclaration || parent is EnumMember || parent is AutoDeclarationPart || parent is Declarator || parent is TemplateDeclaration || parent is Catch) {
            return true
        }
        if (inModuleDeclaration(e) != null) {
            return true
        }
        return false
    }


}
