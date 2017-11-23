package io.github.intellij.dlanguage.highlighting.exitpoint

import com.intellij.codeInsight.highlighting.HighlightUsagesHandler
import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase

class DHighlightExitPointsHandlerFactoryTest : DLightPlatformCodeInsightFixtureTestCase(
    "highlighting/exitpoints"
) {
    private fun doTest(vararg usages: String) {
        myFixture.configureByFile(getTestDataPath(fileName))
        HighlightUsagesHandler.invoke(myFixture.project, myFixture.editor, myFixture.file)
        val highlighters = myFixture.editor.markupModel.allHighlighters
        val actual = highlighters.map { myFixture.file.text.substring(it.startOffset, it.endOffset) }.toList()
        assertSameElements(actual, usages.toList())
    }

    fun testAllReturns() = doTest("return 1;", "return 2;")
    fun testReturnsAndThrows() = doTest("return 1;", "return 2;", "throw new Error(\"Oh no!\");")
    fun testFunctionLiterals() = doTest("return 6;", "return 7;", "throw new Error(\"Unknown\");")
}
