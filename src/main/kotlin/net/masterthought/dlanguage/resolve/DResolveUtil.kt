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
        if (e !is Identifier) {
            return emptySet()
        }


        if (shouldNotResolveToAnything(e)) {
            return emptySet()
        }

        if (SpecialCaseResolve.isApplicable(e)) {
            return SpecialCaseResolve.findDefinitionNode(e)
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

    fun getAllImportedModules(start: PsiElement): /*Pair<*/MutableSet<String>/*, MutableSet<SingleImport>> */{
        val importScopeProcessor = DImportScopeProcessor()
        PsiTreeUtil.treeWalkUp(importScopeProcessor, start, start.containingFile, ResolveState.initial())

        val toProcess = mutableSetOf<String>()
        for (import in importScopeProcessor.imports) {
            toProcess.add(import.name)
        }

        val alreadyProcessed = mutableSetOf<String>()
        val project = start.project
        while (true) {
            val tempSet = mutableSetOf<String>()
            for (import in toProcess) {
                if (!alreadyProcessed.contains(import)) {
                    for (foundModule in StubIndex.getElements(DPublicImportIndex.KEY, import, project, GlobalSearchScope.everythingScope(project), SingleImport::class.java)) {
                        tempSet.add(foundModule.name)
                    }
                }
            }
            alreadyProcessed.addAll(toProcess)
            toProcess.addAll(tempSet)
            if (alreadyProcessed == toProcess)
                break
        }
        return toProcess
    }

    val versionIdentifiers = setOf("DigitalMars", "GNU", "LDC", "SDC", "Windows", "Win32", "Win64", "linux", "OSX", "FreeBSD", "OpenBSD", "NetBSD", "DragonFlyBSD", "BSD", "Solaris", "Posix", "AIX", "Haiku", "SkyOS", "SysV3", "SysV4", "Hurd", "Android", "PlayStation", "PlayStation4", "Cygwin", "MinGW", "FreeStanding", "CRuntime_Bionic", "CRuntime_DigitalMars", "CRuntime_Glibc", "CRuntime_Microsoft", "CRuntime_Musl", "CRuntime_UClibc", "X86", "X86_64", "ARM", "ARM_Thumb", "ARM_SoftFloat", "ARM_SoftFP", "ARM_HardFloat", "AArch64", "Epiphany", "PPC", "PPC_SoftFloat", "PPC_HardFloat", "PPC64", "IA64", "MIPS32", "MIPS64", "MIPS_O32", "MIPS_N32", "MIPS_O64", "MIPS_N64", "MIPS_EABI", "MIPS_SoftFloat", "MIPS_HardFloat", "NVPTX", "NVPTX64", "RISCV32", "RISCV64", "SPARC", "SPARC_V8Plus", "SPARC_SoftFloat", "SPARC_HardFloat", "SPARC64", "S390", "SystemZ", "HPPA", "HPPA64", "SH", "Alpha", "Alpha_SoftFloat", "Alpha_HardFloat", "LittleEndian", "BigEndian", "ELFv1", "ELFv2", "D_Coverage", "D_Ddoc", "D_InlineAsm_X86", "D_InlineAsm_X86_64", "D_LP64", "D_X32", "D_HardFloat", "D_SoftFloat", "D_PIC", "D_SIMD", "D_AVX", "D_AVX2", "D_Version2", "D_NoBoundsChecks", "D_ObjectiveC", "unittest", "assert", "none", "all")

    val traitsIdentifiers = setOf("isAbstractClass", "isArithmetic", "isAssociativeArray", "isFinalClass", "isPOD", "isNested", "isFloating", "isIntegral", "isScalar", "isStaticArray", "isUnsigned", "isVirtualFunction", "isVirtualMethod", "isAbstractFunction", "isFinalFunction", "isStaticFunction", "isOverrideFunction", "isTemplate", "isRef", "isOut", "isLazy", "hasMember", "identifier", "getAliasThis", "getAttributes", "getFunctionAttributes", "getFunctionVariadicStyle", "getLinkage", "getMember", "getOverloads", "getParameterStorageClasses", "getPointerBitmap", "getProtection", "getVirtualFunctions", "getVirtualMethods", "getUnitTests", "parent", "classInstanceSize", "getVirtualIndex", "allMembers", "derivedMembers", "isSame", "compiles")

    val pragmaIdentifiers = setOf("lib", "inline", "mangle", "msg", "startaddress")

    val atAttributeIdentifiers = setOf("safe", "trusted", "nogc", "disable","property")

    val externAttributeIdentifiers = setOf("C","D","Windows","System","Pascal","Objective-C")


    fun shouldNotResolveToAnything(e: PsiNamedElement): Boolean {
        if (e !is DNamedElement) {
            return true
        }
        if (e !is Identifier) {
            return true
        }
        val name = e.name
        val parent = e.parent
        if (name == "string" || name == "size_t" || name == "ptrdiff_t" || name == "dstring" || name == "wstring")     //todo this might be  defined in runtime
            return true
        if (parent is AtAttribute && atAttributeIdentifiers.contains(name)) return true
        if (parent is ScopeGuardStatement)
            if (name == "exit" || name == "success" || name == "failure")
                return true
        if (parent is VersionCondition && versionIdentifiers.contains(name)) return true
        if (parent is LinkageAttribute && externAttributeIdentifiers.contains(name)) return true
        if (parent is TraitsExpression && traitsIdentifiers.contains(name)) return true
        if (parent is FunctionDeclaration || parent is InterfaceOrClass || parent is StructDeclaration || parent is UnionDeclaration || parent is EnumDeclaration || parent is EnumMember || parent is AutoDeclarationPart || parent is Declarator || parent is TemplateDeclaration || parent is Catch) return true
        if (parent is PragmaExpression && pragmaIdentifiers.contains(name)) return true
        if (parent is IdentifierList && parent.parent is AliasDeclaration) return true
        if (parent is AsmInstruction || parent.parent is AsmPrimaryExp) return true
        return false
    }

}

class Resolver(val project: Project,val checkParametersFunction: Boolean = true, val checkParametersConstructor: Boolean = true, val searchScope: GlobalSearchScope = GlobalSearchScope.everythingScope(project)) {
}

