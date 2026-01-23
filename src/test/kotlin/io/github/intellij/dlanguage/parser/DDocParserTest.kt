package io.github.intellij.dlanguage.parser

import com.intellij.testFramework.ParsingTestCase
import io.github.intellij.dlanguage.features.documentation.DDocParserDefinition
import java.io.File

class DDocParserTest : ParsingTestCase("ddoc" + File.separator + "parser", "d", true, DDocParserDefinition()) {

    override fun getTestDataPath(): String =
        this.javaClass.classLoader.getResource("gold")!!.path

    private fun doTest() = doTest(true, true)

    fun testSimple() = doTest()
    fun testEmbeddedCode() = doTest()
    fun testEmbeddedCode2() = doTest()
    fun testEmbeddedCode3() = doTest()
    fun testEmbeddedHTML() = doTest()
    fun testEmphasis() = doTest()
    fun testHorizontalRules() = doTest()
    fun testInlineCode() = doTest()
    fun testLinks() = doTest()
    fun testLinks2() = doTest()
    fun testListsOrdered() = doTest()
    fun testListsUnordered() = doTest()
    fun testListsUnordered2() = doTest()
    fun testListsUnordered3() = doTest()
    fun testMarkdownHeading() = doTest()
    fun testParamsSection() = doTest()
    fun testQuotes() = doTest()
    fun testFull() = doTest()
    fun testWrongImageDetection() = doTest()
}
