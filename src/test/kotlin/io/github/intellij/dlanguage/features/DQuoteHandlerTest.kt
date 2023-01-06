package io.github.intellij.dlanguage.features

import com.intellij.testFramework.PsiTestUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class DQuoteHandlerTest : BasePlatformTestCase() {

    fun `test complete char quote`() = doTestByText("""
        void main() {
            <caret>
        }
    """, """
        void main() {
            '<caret>'
        }
    """, '\'')
    fun `test complete char quote empty file`() = doTestByText("<caret>", "'<caret>'", '\'')

    fun `test complete string double quote`() = doTestByText("""
        void main() {
            <caret>
        }
    """, """
        void main() {
            "<caret>"
        }
    """, '"')

    fun `test complete alternate wysiwyg quote`() = doTestByText("""
        void main() {
            <caret>
        }
    """, """
        void main() {
            `<caret>`
        }
    """, '`')

    fun `test complete alternate wysiwyg quote 2`() = doTestByText("""
        void main() {
            `<caret>`
        }
    """, """
        void main() {
            `"<caret>`
        }
    """, '"')

    fun `test complete wysiwyg quote`() = doTestByText("""
        void main() {
            r<caret>
        }
    """, """
        void main() {
            r"<caret>"
        }
    """, '"')

    fun `test complete string double quote with string after`() = doTestByText("""
        void main() {
            string new = <caret>
            string existing = "foo";
        }
    """, """
        void main() {
            string new = "<caret>"
            string existing = "foo";
        }
    """, '"')

    fun `test complete string double quote with string after with escaped double quote`() = doTestByText("""
        void main() {
            string new = <caret>
            string existing = "foo\"bar";
        }
    """, """
        void main() {
            string new = "<caret>"
            string existing = "foo\"bar";
        }
    """, '"')

    fun `test complete alternate wysiwig quote with string after`() = doTestByText("""
        void main() {
            string new = <caret>
            string existing = "foo";
        }
    """, """
        void main() {
            string new = `<caret>`
            string existing = "foo";
        }
    """, '`')

    fun `test don't complete escape double quote in string`() = doTestByText("""
        void main() {
            "\<caret>"
        }
    """, """
        void main() {
            "\"<caret>"
        }
    """, '"')
    fun `test don't complete escape double quote in alternate wysiwyg string`() = doTestByText("""
        void main() {
            `\<caret>`
        }
    """, """
        void main() {
            `\`<caret>`
        }
    """, '`')

    private fun doTestByText(
        before: String,
        after: String,
        c: Char
    ) {
        myFixture.configureByText("main.d", before.trimIndent())
        myFixture.type(c)
        PsiTestUtil.checkPsiStructureWithCommit(myFixture.file, PsiTestUtil::checkPsiMatchesTextIgnoringNonCode)
        myFixture.checkResult(after.trimIndent())
    }
}
