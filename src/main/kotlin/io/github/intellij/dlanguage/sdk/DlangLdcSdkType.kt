package io.github.intellij.dlanguage.sdk

import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.util.SystemInfo
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangBundle
import java.nio.file.Paths
import javax.swing.Icon

class DlangLdcSdkType : DlangDependentSdkType(SDK_TYPE_ID) {
    companion object {
        const val SDK_TYPE_ID = "LDC"
        const val SDK_NAME = "LDC SDK"
        val ldcBinary = if(SystemInfo.isWindows) "ldc2.exe" else "ldc2"
        val ldcIcon = DLanguage.Icons.LDC
    }

    override fun getIcon(): Icon = ldcIcon

    override fun getPresentableName(): String = DlangBundle.message("compilers.ldc.presentableName")

    // On Windows it's likely to be "C:\LDC\ldc2-x.xx.x-windows-multilib"
    //                              "C:\tools\ldc2-1.32.2-windows-multilib\bin\ldc2.exe"
    //                              "C:\tools\ldc2-1.32.2-windows-multilib\bin\dub.exe" (LDC has it's own dub)
    //                              "C:\tools\ldc2-1.32.2-windows-multilib\import\" (object.d & all D sources)
    //                              "C:\tools\ldc2-1.32.2-windows-multilib\lib32\ldc2.exe" (phobos & druntime)
    //                              "C:\tools\ldc2-1.32.2-windows-multilib\lib64\ldc2.exe" (phobos & druntime)
    // On Linux it could be "/usr/bin" or "/usr/local/bin"
    override fun suggestHomePath(): String? {
        // todo: perhaps attempt Windows lookup with: FileUtil.findAncestor(Paths.get(sdkHome).toFile(), File(ldcBinary))
        return if(SystemInfo.isUnix) "/usr/bin" else null
    }

    override fun isValidSdkHome(sdkHome: String): Boolean {
        val binaryPath = Paths.get(sdkHome, ldcBinary).toFile()
        return binaryPath.exists() && binaryPath.canExecute()
    }

    override fun suggestSdkName(currentSdkName: String?, sdkHome: String): String = presentableName

    /*
    * example output from 'ldc2 --version':
    *
    * LDC - the LLVM D compiler (1.32.2):
    *  based on DMD v2.102.2 and LLVM 15.0.7
    *  built with LDC - the LLVM D compiler (1.32.2)
    *  Default target: x86_64-pc-windows-msvc
    *  Host CPU: skylake
    *  http://dlang.org - http://wiki.dlang.org/LDC
    *
    */
    override fun getVersionString(sdk: Sdk): String? = null // todo
}
