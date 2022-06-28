package io.github.intellij.dlanguage.annotator

import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import io.github.intellij.dlanguage.inspections.PossiblyUndefinedSymbol

/**
 * Represents tests for highlighting due to unresolved symbols
 */
class DHighlightingResolveAnnotatorTest : BasePlatformTestCase() {

    fun testNamedImportShouldNotBeUndefined() {
        myFixture.addFileToProject("main.d", "import foo: bar = test;")
        myFixture.addFileToProject("foo.d", "void test() {}")
        FileDocumentManager.getInstance().saveAllDocuments()
        myFixture.configureByFile("main.d")
        myFixture.enableInspections(PossiblyUndefinedSymbol())
        myFixture.testHighlighting(true, false, true)
    }
}
