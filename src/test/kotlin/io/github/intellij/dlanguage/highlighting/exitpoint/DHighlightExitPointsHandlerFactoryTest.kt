package io.github.intellij.dlanguage.highlighting.exitpoint

import com.intellij.codeInsight.highlighting.HighlightUsagesHandler
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class DHighlightExitPointsHandlerFactoryTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String = this.javaClass.classLoader.getResource("gold/highlighting/exitpoints")!!.path

    private fun doTest(fileName: String, vararg usages: String) {
        myFixture.configureByFile(fileName)
        HighlightUsagesHandler.invoke(myFixture.project, myFixture.editor, myFixture.file)
        val highlighters = myFixture.editor.markupModel.allHighlighters
        val actual = highlighters.map { myFixture.file.text.substring(it.startOffset, it.endOffset) }.sorted().toList()
        assertSameElements(actual, usages.toList())
    }

    fun testAllReturns() = doTest("all_returns.d", "return 1;", "return 2;")
    fun testReturnsAndThrows() = doTest("returns_and_throws.d", "return 1;", "return 2;", "throw new Error(\"Oh no!\")")
    fun testFunctionLiterals() = doTest("function_literals.d", "return 6;", "return 7;", "throw new Error(\"Unknown\")")
}
