package io.github.intellij.dlanguage.template

import com.intellij.openapi.actionSystem.IdeActions
import org.intellij.lang.annotations.Language
import org.junit.Test

@Suppress("PossiblyUndefinedSymbol")
class DlangLiveTemplatesTest : DlangTestBase() {
    @Test
    fun `test f development template`() = expandSnippet("""
        struct S {
            f/*caret*/
        }
    """, """
        struct S {
            foo: u32,
        }
    """)

    @Test
    fun `test if then else expansion`() = expandSnippet("""
        if/*caret*/
    """, """
        if(true) {
           /* statement(s) will execute if the boolean expression is true */
        }
    """)


    private fun expandSnippet(@Language("D") before: String, @Language("D") after: String) =
        checkByText(before.trimIndent(), after.trimIndent()) {
            myFixture.performEditorAction(IdeActions.ACTION_EXPAND_LIVE_TEMPLATE_BY_TAB)
        }

    private fun noSnippet(@Language("D") code: String) = expandSnippet(code, code)

}
