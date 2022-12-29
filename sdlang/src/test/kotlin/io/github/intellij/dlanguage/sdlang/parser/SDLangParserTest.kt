package io.github.intellij.dlanguage.sdlang.parser

import com.intellij.testFramework.ParsingTestCase

class SDLangParserTest
    : ParsingTestCase("parser", "sdl", true , SDLangParserDefinition()) {

    fun testSimple() = doTest()

    override fun getTestDataPath(): String =
        this.javaClass.classLoader.getResource("gold")!!.path

    private fun doTest() = doTest(true)
}
