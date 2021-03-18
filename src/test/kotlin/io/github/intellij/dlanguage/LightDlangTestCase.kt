package io.github.intellij.dlanguage

import com.intellij.mock.MockPsiManager
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.FileViewProvider
import com.intellij.psi.SingleRootFileViewProvider
import com.intellij.testFramework.LightPlatformTestCase
import com.intellij.testFramework.LightVirtualFile
import io.github.intellij.dlanguage.psi.DlangFile
import com.intellij.testFramework.VfsTestUtil

/**
 * Provides a base for unit testing that requires in-memory D source files
 */
abstract class LightDlangTestCase : LightPlatformTestCase() {

    fun addFileToModuleSource(filename: String, content: String? = null): VirtualFile {
        val sourcesRoot = ModuleRootManager.getInstance(module).sourceRoots[0]
        return VfsTestUtil.createFile(sourcesRoot, filename, content)
    }

    /**
     * Use this method to help simplify the creation of test data for these unit tests
     *
     * @param filename the filename, including extension, of a D source file
     * @param content the file content (source code)
     * @return a Dlang LightVirtualFile (In-memory implementation of [VirtualFile])
     */
    fun lightDlangVirtualFile(filename: String, content: String) = LightVirtualFile(filename, DLanguage, content)

    /**
     * Use this method to help simplify the creation of test data for these unit tests
     *
     * @param filename the filename, including extension, of a D source file
     * @param content the file content (source code)
     * @return a Dlang Psi File from a single LightVirtualFile (In-memory implementation of [VirtualFile])
     */
    fun lightDlangPsiFile(filename: String, content: String): DlangFile {
        val provider: FileViewProvider = SingleRootFileViewProvider(
            MockPsiManager(project),
            lightDlangVirtualFile(filename, content)
        )
        return DlangFile(provider)
    }

}
