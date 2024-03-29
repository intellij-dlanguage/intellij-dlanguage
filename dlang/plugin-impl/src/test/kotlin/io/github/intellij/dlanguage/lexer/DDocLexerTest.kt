package io.github.intellij.dlanguage.lexer

import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase
import io.github.intellij.dlanguage.features.documentation.DDocLexer
import java.io.File

class DDocLexerTest: LexerTestCase() {

    override fun createLexer(): Lexer = DDocLexer()

    override fun getDirPath(): String = "gold" + File.separator + "ddoc" + File.separator + "lexer"

    override fun getPathToTestDataFile(extension: String) : String {
        val name = getTestName(true) + extension
        return this.javaClass.classLoader.getResource(String.format("%s/%s", dirPath, name))!!.path
    }

    fun doTest() = doFileTest("d")

    fun testSimple() = doTest()
}
