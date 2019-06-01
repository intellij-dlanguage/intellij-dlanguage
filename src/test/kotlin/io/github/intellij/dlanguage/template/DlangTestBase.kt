package io.github.intellij.dlanguage.template

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase
import org.intellij.lang.annotations.Language

open class DlangTestBase : LightPlatformCodeInsightFixtureTestCase() {
    protected fun replaceCaretMarker(text: String) = text.replace("/*caret*/", "<caret>")
    protected fun checkByText(
        @Language("D") before: String,
        @Language("D") after: String,
        action: () -> Unit
    ) {
        InlineFile(before)
        action()
        myFixture.checkResult(replaceCaretMarker(after))
    }

    inner class InlineFile(private @Language("D") val code: String, val name: String = "main.d") {
        private val hasCaretMarker = "/*caret*/" in code

        init {
            myFixture.configureByText(name, replaceCaretMarker(code))
        }

        fun withCaret() {
            check(hasCaretMarker) {
                "Please, add `/*caret*/` marker to\n$code"
            }
        }
    }
}
