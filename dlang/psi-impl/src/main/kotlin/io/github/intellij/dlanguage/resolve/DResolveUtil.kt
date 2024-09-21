package io.github.intellij.dlanguage.resolve

import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.utils.*

/**
 * Created by francis on 5/12/17.
 */


object DResolveUtil {
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

val PROCESSED_FILES_KEY = Key.create<MutableList<String>>("PROCESSED_FILES")
