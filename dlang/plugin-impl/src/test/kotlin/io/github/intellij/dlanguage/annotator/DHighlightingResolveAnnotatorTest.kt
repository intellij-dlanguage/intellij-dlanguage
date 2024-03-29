package io.github.intellij.dlanguage.annotator

import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import io.github.intellij.dlanguage.inspections.PossiblyUndefinedSymbol
import org.junit.Test

/**
 * Represents tests for highlighting due to unresolved symbols
 */
class DHighlightingResolveAnnotatorTest : LightPlatformCodeInsightFixture4TestCase() {

    @Test
    fun testNamedImportShouldNotBeUndefined() {
        myFixture.addFileToProject("main.d", "import foo: bar = test;")
        myFixture.addFileToProject("foo.d", "void test() {}")
        FileDocumentManager.getInstance().saveAllDocuments()
        myFixture.configureByFile("main.d")
        myFixture.enableInspections(PossiblyUndefinedSymbol())
        myFixture.testHighlighting(true, false, true)
    }
}
