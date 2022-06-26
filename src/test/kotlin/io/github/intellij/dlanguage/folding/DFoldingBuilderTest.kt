package io.github.intellij.dlanguage.folding

import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase

class DFoldingBuilderTest : DLightPlatformCodeInsightFixtureTestCase("folding") {
    private fun doTest() {
        myFixture.testFolding(getTestDataPath(fileName))
    }

    fun testBlock() = doTest()
    fun testClass() = doTest()
    fun testComments() = doTest()
    fun testEnum() = doTest()
    fun testExtern() = doTest()
    fun testFunction() = doTest()
    fun testImport() = doTest()
    fun testOneLinerFunction() = doTest()
    fun testStruct() = doTest()
    fun testVersion() = doTest()
    fun testOneLinerImport() = doTest()
}
