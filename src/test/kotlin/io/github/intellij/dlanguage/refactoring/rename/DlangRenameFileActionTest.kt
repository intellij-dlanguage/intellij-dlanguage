package io.github.intellij.dlanguage.refactoring.rename

import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.util.text.StringUtil
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import org.junit.Test

class DlangRenameFileActionTest : LightPlatformCodeInsightFixture4TestCase() {

    @Test
    fun testFooToTest() {
        myFixture.addFileToProject("main.d", "import std.bar.foo;\nvoid main() {testing();}")
        val fooFile = myFixture.addFileToProject("std/bar/foo.d", "module std.bar.foo;\nvoid testing() {}")
        myFixture.renameElement(fooFile, "test.d", false, true)
        FileDocumentManager.getInstance().saveAllDocuments()
        val mainAfter = StringUtil.convertLineSeparators(String(myFixture.findFileInTempDir("main.d").contentsToByteArray(), Charsets.UTF_8))
        val testAfter = StringUtil.convertLineSeparators(String(myFixture.findFileInTempDir("std/bar/test.d").contentsToByteArray(), Charsets.UTF_8))
        assertEquals("import std.bar.test;\nvoid main() {testing();}", mainAfter)
        assertEquals("module std.bar.test;\nvoid testing() {}", testAfter)
        assertNull(myFixture.findFileInTempDir("std/bar/foo.d"))
    }
}
