package io.github.intellij.dlanguage.parser

import com.intellij.testFramework.ParsingTestCase
import io.github.intellij.dlanguage.features.documentation.DDocParserDefinition
import java.io.File

class DDocParserTest : ParsingTestCase("ddoc" + File.separator + "parser", "d", true, DDocParserDefinition()) {

    override fun getTestDataPath(): String =
        this.javaClass.classLoader.getResource("gold")!!.path

    fun testSimple() = doTest(true, true)
    fun testEmbeddedCode() = doTest(true, true)
    fun testEmbeddedCode2() = doTest(true, true)
    fun testEmbeddedCode3() = doTest(true, true)
    fun testEmbeddedHTML() = doTest(true, true)
    fun testEmphasis() = doTest(true, true)
    fun testHorizontalRules() = doTest(true, true)
    fun testInlineCode() = doTest(true, true)
    fun testLinks() = doTest(true, true)
    fun testMarkdownHeading() = doTest(true, true)
    fun testQuotes() = doTest(true, true)
    fun testFull() = doTest(true, true)
}
