package io.github.intellij.dlanguage.resolve

import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.writeText
import com.intellij.testFramework.utils.vfs.createFile
import org.junit.Test

/**
 * Check that imports modules declaration are correctly resolved
 * This check that no file is loaded for the resolve and only stubs index are used.
 * The checks ensure that the import (at `/*<ref>*/`) points to the file they
 * should (can be null if no resolved expected)
 */
class DImportsModulesResolveTest : DResolveTestCase() {

    override fun getTestDataPath(): String = ""

    private lateinit var resolvedFile: VirtualFile


    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        // Prepare working directory with by default 1 directory "foo" and 1 file "resolved.d" in it
        val currentDir = myFixture.findFileInTempDir(".")
        runWriteAction {
            val fooDir = currentDir.createChildDirectory(currentDir, "foo")
            resolvedFile = fooDir.createFile("resolved.d")
            VfsUtil.markDirtyAndRefresh(/* async = */ false, /* recursive = */ true, /* reloadChildren = */ true, fooDir)
        }
    }

    @Test
    fun testImportModuleInSubFolderWithoutModuleDeclaration() {
        // Module name without "module ..." declaration is just the file name (without extension) whatever is his location
        doTestStubOnlyResolve(
            """
                import /*<ref>*/resolved;
            """,
            "resolved.d"
        )
        // Module "foo.resolved" does not exists, only "resolved exists"
        doTestStubOnlyResolve(
            """
                import foo./*<ref>*/resolved;
            """,
            null
        )
    }

    @Test
    fun testImportModuleInSubFolderWithModuleDeclaration() {
        runWriteAction {
            resolvedFile.writeText("module foo.resolved;")
        }
        doTestStubOnlyResolve(
            """
                import foo./*<ref>*/resolved;
            """,
            "resolved.d"
        )
    }

    @Test
    fun testImportModuleInSubFolderWithCustomModuleDeclaration() {
        // When the module name does not follow the folder architecture, it should still work
        runWriteAction {
            resolvedFile.writeText("module bar.test;")
        }
        doTestStubOnlyResolve(
            """
                import bar./*<ref>*/test;
            """,
            "resolved.d"
        )
        // Ensure only 1 way to access to it is possible
        doTestStubOnlyResolve(
            """
                import foo./*<ref>*/resolved;
            """,
            null
        )
        doTestStubOnlyResolve(
            """
                import /*<ref>*/resolved;
            """,
            null
        )
    }

    @Test
    fun testImportPackageModuleWithoutModuleDeclaration() {
        val fooDir = myFixture.findFileInTempDir("foo")
        runWriteAction {
            val packageFile = fooDir.createFile("package.d")
            VfsUtil.markDirtyAndRefresh(/* async = */ false, /* recursive = */ true, /* reloadChildren = */ true, fooDir)
        }
        doTestStubOnlyResolve(
            """
                import /*<ref>*/foo;
            """,
            "package.d"
        )
    }

    @Test
    fun testImportPackageModuleWithModuleDeclaration() {
        val fooDir = myFixture.findFileInTempDir("foo")
        runWriteAction {
            val packageFile = fooDir.createFile("package.d")
            VfsUtil.markDirtyAndRefresh(/* async = */ false, /* recursive = */ true, /* reloadChildren = */ true, fooDir)
            packageFile.writeText("module foo;");
        }
        doTestStubOnlyResolve(
            """
                import /*<ref>*/foo;
            """,
            "package.d"
        )
    }

    @Test
    fun testImportPackageModuleWithCustomModuleDeclaration() {
        // One strange behavior with package.d file is that they by definition are accessible through the package they are in
        // But if you override the module name with whatever else, both names are valid. You can import your file through both names.
        val fooDir = myFixture.findFileInTempDir("foo")
        runWriteAction {
            val packageFile = fooDir.createFile("package.d")
            VfsUtil.markDirtyAndRefresh(/* async = */ false, /* recursive = */ true, /* reloadChildren = */ true, fooDir)
            packageFile.writeText("module bar;");
        }
        doTestStubOnlyResolve(
            """
                import /*<ref>*/foo;
            """,
            "package.d"
        )
        doTestStubOnlyResolve(
            """
                import /*<ref>*/bar;
            """,
            "package.d"
        )
    }
}
