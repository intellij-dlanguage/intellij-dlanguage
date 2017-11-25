package io.github.intellij.dlanguage.annotator

import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase

class DHighlightingAnnotatorTest : DLightPlatformCodeInsightFixtureTestCase(
    "highlighting/annotator"
) {
    private fun doTest() {
        myFixture.configureByFile(getTestDataPath(fileName))
        myFixture.testHighlighting(false, true, false)
    }

    fun testTypeParameters() = doTest()
}
