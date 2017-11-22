package io.github.intellij.dlanguage.folding

import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase

class DFoldingBuilderTest : DLightPlatformCodeInsightFixtureTestCase("folding", "folding") {
    private val fileName: String
        get() = "$testName.d"

    private val testName: String
        get() = camelOrWordsToSnake(getTestName(true))

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
}

fun camelOrWordsToSnake(name: String): String {
    if (' ' in name) return name.replace(" ", "_")

    return name.split("(?=[A-Z])".toRegex()).joinToString("_", transform = String::toLowerCase)
}
