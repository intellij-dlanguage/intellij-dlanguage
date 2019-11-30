package io.github.intellij.dlanguage.annotator

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class DHighlightingAnnotatorTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String = this.javaClass.classLoader.getResource("gold/highlighting/annotator")!!.path

    fun testTypeParameters() {
        myFixture.configureByFile("type_parameters.d")
        myFixture.testHighlighting(false, true, false)
    }
}
