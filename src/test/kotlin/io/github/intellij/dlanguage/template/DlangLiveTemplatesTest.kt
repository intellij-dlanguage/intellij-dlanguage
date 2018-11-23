package io.github.intellij.dlanguage.template

import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase
import org.intellij.lang.annotations.Language
import org.junit.Test

@Suppress("PossiblyUndefinedSymbol")
class DlangLiveTemplatesTest : DlangTestBase() {
    @Test
/*
    fun testfuck() {
        assertFalse(true)
    }
*/

    fun `test struct field`() = expandSnippet("""
        struct S {
            f/*caret*/
        }
    """, """
        struct S {
            foo: u32,
        }
    """)


    private fun expandSnippet(@Language("D") before: String, @Language("D") after: String) =
        checkByText(before.trimIndent(), after.trimIndent()) {
            myFixture.performEditorAction(IdeActions.ACTION_EXPAND_LIVE_TEMPLATE_BY_TAB)
        }

    private fun noSnippet(@Language("D") code: String) = expandSnippet(code, code)

}
