package io.github.intellij.dlanguage.sdlang.lexer

import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase

abstract class SDLangLexerTestBase : LexerTestCase() {

    fun doTest() = doFileTest("sdl")

    override fun createLexer(): Lexer = SDLangLexer()

    override fun getDirPath(): String = "gold/lexer"

    override fun getPathToTestDataFile(extension: String) : String {
        val name = getTestName(true) + extension
        return this.javaClass.classLoader.getResource(String.format("%s/%s", dirPath, name))!!.path
    }
}
