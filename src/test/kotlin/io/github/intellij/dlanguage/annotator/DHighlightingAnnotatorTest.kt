package io.github.intellij.dlanguage.annotator

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import org.junit.Test

class DHighlightingAnnotatorTest : LightPlatformCodeInsightFixture4TestCase() {

    override fun getTestDataPath(): String = this.javaClass.classLoader.getResource("gold/highlighting/annotator")!!.path

    @Test
    fun testTypeParameters() {
        myFixture.configureByFile("type_parameters.d")
        myFixture.testHighlighting(false, true, false)
    }

    @Test
    fun testInvalidDelimiterString() {
        myFixture.configureByFile("invalid_string_delimiters.d")
        myFixture.testHighlighting(false, false, false);
    }

    @Test
    fun testUnclosedComment() {
        myFixture.configureByFile("unclosed_comment.d")
        myFixture.testHighlighting(false, false, false);
    }
}
