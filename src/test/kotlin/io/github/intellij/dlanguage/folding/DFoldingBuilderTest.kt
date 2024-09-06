package io.github.intellij.dlanguage.folding

import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase
import org.junit.Test

class DFoldingBuilderTest : DLightPlatformCodeInsightFixtureTestCase("folding") {

    private fun doTest() {
        myFixture.testFolding(getTestDataPath(fileName))
    }

    @Test
    fun testBlock() = doTest()
    @Test
    fun testClass() = doTest()
    @Test
    fun testComments() = doTest()
    @Test
    fun testEnum() = doTest()
    @Test
    fun testExtern() = doTest()
    @Test
    fun testFunction() = doTest()
    @Test
    fun testImport() = doTest()
    @Test
    fun testOneLinerFunction() = doTest()
    @Test
    fun testStruct() = doTest()
    @Test
    fun testVersion() = doTest()
    @Test
    fun testOneLinerImport() = doTest()
    @Test
    fun testStaticForeach() = doTest()
}
