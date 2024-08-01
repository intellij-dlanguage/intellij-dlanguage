package io.github.intellij.dlanguage.resolve

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import io.github.intellij.dlanguage.psi.DlangPsiFile
import io.github.intellij.dlanguage.psi.DlangTypes.*
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.resolve.processors.basic.BasicResolve
import io.github.intellij.dlanguage.stubs.index.DTopLevelDeclarationIndex
import io.github.intellij.dlanguage.utils.*

/**
 * Created by francis on 5/12/17.
 */


class DResolveUtil private constructor(val project: Project) {
    //also should use a weak  reference or psi file gist
    //also this won't invalidate if a external file is editted but should
    companion object {
        private val resolveUtils: MutableMap<Project, DResolveUtil> = mutableMapOf()
        //
        fun getInstance(project: Project): DResolveUtil {
            return resolveUtils.getOrPut(project, { DResolveUtil(project) })
        }
    }

//    private val resolveCache: MutableMap<PsiFile, Pair<Long, MutableMap<PsiNamedElement, Set<PsiNamedElement>>>> = mutableMapOf()

    fun findDefinitionNode(e: PsiElement, profile: Boolean): Set<PsiNamedElement> {
//        fun invalidateCache(file: PsiFile) {
//            resolveCache.remove(file)
//        }

//        val fileResolveCache = resolveCache.getOrPut(e.containingFile, { Pair(e.containingFile.modificationStamp, mutableMapOf<PsiNamedElement, Set<PsiNamedElement>>()) })
//        val oldFileStamp = fileResolveCache.first
//        if (oldFileStamp != e.containingFile.modificationStamp) {
//            invalidateCache(e.containingFile)
        return findDefinitionNodeImpl(e, profile)
//        }
//        return fileResolveCache.second.getOrPut(e, { findDefinitionNodeImpl(e, profile) })
    }

    fun findDefinitionNodeImpl(e: PsiElement, profile: Boolean): Set<PsiNamedElement> {
        if (shouldNotResolveToAnything(e)) {
            return emptySet()
        }

        if (SpecialCaseResolve.isApplicable(e)) {
            return SpecialCaseResolve.findDefinitionNode(e)
        }

        if (resolvingConstructorCall(e)) {
            val basicResolveResult = BasicResolve.getInstance(project, profile).findDefinitionNode(e)
            val potential = basicResolveResult.filter { it is Constructor }.toSet()
            if (potential.isNotEmpty()) {
                return potential
            }
            // No constructor found, so point to the class/struct itself
            val result = basicResolveResult.filter { it is ClassDeclaration || it is StructDeclaration}.toSet()
            if (result.size != 1 || result.first() !is ClassDeclaration) {
                return result
            }
            // If it was a class definition, search for his constructors if any
            val constructors = (result.first() as ClassDeclaration).structBody?.declarations?.filterIsInstance<Constructor>()
            return if (constructors?.isNotEmpty() == true) constructors.toSet() else result
        }

        if (isModuleScopeOperator(e)) {
            val name = (e as ReferenceExpression).identifier?.text?:return emptySet()
            return DTopLevelDeclarationIndex.getTopLevelSymbols(name, (e.containingFile as DlangPsiFile).getFullyQualifiedModuleName(), project)
        }

        var basicResolveResult = BasicResolve.getInstance(project, profile).findDefinitionNode(e)
        basicResolveResult = basicResolveResult.filter { it !is Constructor }.toSet()

        /*if (basicResolveResult.isEmpty())
            return SpecialCaseResolve.tryPackageResolve(e)*/
        return basicResolveResult
    }


    fun resolvingConstructorCall(e: PsiElement): Boolean {
        return e is QualifiedIdentifier && e.parent is BasicType
            && DPsiUtil.getParent(e, setOf(NEW_EXPRESSION), setOf(BLOCK_STATEMENT, STRUCT_BODY)) is NewExpression
    }

    fun isModuleScopeOperator(e: PsiElement): Boolean {
        return e is ReferenceExpression && e.oP_DOT != null && e.referenceExpression == null
    }

    val versionIdentifiers = setOf("DigitalMars", "GNU", "LDC", "SDC", "Windows", "Win32", "Win64", "linux", "OSX", "FreeBSD", "OpenBSD", "NetBSD", "DragonFlyBSD", "BSD", "Solaris", "Posix", "AIX", "Haiku", "SkyOS", "SysV3", "SysV4", "Hurd", "Android", "PlayStation", "PlayStation4", "Cygwin", "MinGW", "FreeStanding", "CRuntime_Bionic", "CRuntime_DigitalMars", "CRuntime_Glibc", "CRuntime_Microsoft", "CRuntime_Musl", "CRuntime_UClibc", "X86", "X86_64", "ARM", "ARM_Thumb", "ARM_SoftFloat", "ARM_SoftFP", "ARM_HardFloat", "AArch64", "Epiphany", "PPC", "PPC_SoftFloat", "PPC_HardFloat", "PPC64", "IA64", "MIPS32", "MIPS64", "MIPS_O32", "MIPS_N32", "MIPS_O64", "MIPS_N64", "MIPS_EABI", "MIPS_SoftFloat", "MIPS_HardFloat", "NVPTX", "NVPTX64", "RISCV32", "RISCV64", "SPARC", "SPARC_V8Plus", "SPARC_SoftFloat", "SPARC_HardFloat", "SPARC64", "S390", "SystemZ", "HPPA", "HPPA64", "SH", "Alpha", "Alpha_SoftFloat", "Alpha_HardFloat", "LittleEndian", "BigEndian", "ELFv1", "ELFv2", "D_Coverage", "D_Ddoc", "D_InlineAsm_X86", "D_InlineAsm_X86_64", "D_LP64", "D_X32", "D_HardFloat", "D_SoftFloat", "D_PIC", "D_SIMD", "D_AVX", "D_AVX2", "D_Version2", "D_NoBoundsChecks", "D_ObjectiveC", "unittest", "assert", "none", "all","None")

    val traitsIdentifiers = setOf("isAbstractClass", "isArithmetic", "isAssociativeArray", "isFinalClass", "isPOD", "isNested", "isFuture", "isDeprecated", "isFloating", "isIntegral", "isScalar", "isStaticArray", "isUnsigned", "isDisabled", "isVirtualFunction", "isVirtualMethod", "isAbstractFunction", "isFinalFunction", "isStaticFunction", "isOverrideFunction", "isTemplate", "isRef", "isOut", "isLazy", "isReturnOnStack", "isCopyable", "isZeroInit", "isModule", "isPackage", "hasMember", "hasCopyConstructor", "hasPostblit", "identifier", "getAliasThis", "getAttributes", "getFunctionAttributes", "getFunctionVariadicStyle", "getLinkage", "getLocation", "getMember", "getOverloads", "getParameterStorageClasses", "getPointerBitmap", "getCppNamespaces", "getVisibility", "getProtection", "getTargetInfo", "getVirtualFunctions", "getVirtualMethods", "getUnitTests", "parent", "child", "classInstanceSize", "getVirtualIndex", "allMembers", "derivedMembers", "isSame", "compiles", "toType", "initSymbol", "parameters")

    val pragmaIdentifiers = setOf("lib", "inline", "mangle", "msg", "startaddress")

    val atAttributeIdentifiers = setOf("safe", "trusted", "nogc", "disable", "property", "system")

    val externAttributeIdentifiers = setOf("C","D","Windows","System","Pascal","Objective-C")


    fun shouldNotResolveToAnything(e: PsiElement): Boolean {
        val name = e.text
        val parent = e.parent
        if (name.length > 2)
            if (name.substring(0, 2) == "__")
                return true
        if (name == "sizeof" || name == "alignof" || name == "tupleof" || name == "offsetof" || name == "init")     //todo this might be  defined in runtime
            return true
        if (parent is AtAttribute && atAttributeIdentifiers.contains(name)) return true
        if (parent is ScopeGuardStatement)
            if (name == "exit" || name == "success" || name == "failure")
                return true
        if (parent is VersionCondition && versionIdentifiers.contains(name)) return true
        if (parent is LinkageAttribute && externAttributeIdentifiers.contains(name)) return true
        if (parent is TraitsExpression && traitsIdentifiers.contains(name)) return true
        if (parent is FunctionDeclaration || parent is ClassDeclaration || parent is InterfaceDeclaration || parent is StructDeclaration ||
            parent is UnionDeclaration || parent is EnumDeclaration || parent is EnumMember ||
            parent is AutoAssignment || parent is IdentifierInitializer ||
            parent is TemplateDeclaration || parent is TemplateMixinDeclaration || parent is TemplateTypeParameter ||
            parent is Catch || parent is NamedImportBind) return true
        if (parent is Parameter)
            if (parent.identifier == e)
                return true
        if (parent is PragmaExpression && pragmaIdentifiers.contains(name)) return true
        if (parent is DeclaratorIdentifier && parent.parent is AliasDeclaration) return true
        if (parent is AliasInitializer) return true
        if (parent is AsmInstruction || parent.parent is AsmPrimaryExp) return true
        return false
    }
}


//class Resolver(val project: Project,val checkParametersFunction: Boolean = true, val checkParametersConstructor: Boolean = true, val searchScope: GlobalSearchScope = GlobalSearchScope.everythingScope(project)) {
//}
//
