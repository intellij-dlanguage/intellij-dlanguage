package io.github.intellij.dlanguage.types

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.psi.named.DLanguageAutoAssignment
import org.intellij.lang.annotations.Language
import org.junit.Test

class AutoValueAssignmentTypeTest : LightPlatformCodeInsightFixture4TestCase() {

    @Test
    fun testAutoExpressionLiteralExpressionAssignment() {
        doTest("int", """auto expr = 1;""")
        doTest("float", """auto expr = 1f;""")
        // TODO
        //doTest("long", """auto expr = 1L;""")
        //doTest("ulong", """auto expr = 1UL;""")
        //doTest("double", """auto expr = 1.0;""")
        doTest("float", """auto expr = 1.0f;""")
        doTest("bool", """auto expr = true;""")
        doTest("bool", """auto expr = false;""")
        doTest("char", """auto expr = 'a';""")
    }

    @Test
    fun testAutoExpressionArrayAssignment() {
        doTest("char", """char[] x; auto expr = x[3];""")
        doTest("char[]", """char* x; auto expr = x[0 .. 7];""")
    }

    /**
     * Take a file text and the expected type string representation and ensure that the expression named (expr) has the corresponding type
     */
    private fun doTest(expectedType: String, @Language("D") text: String) =
        checkTypes(expectedType, parseAutoDeclaration(text))

    private fun parseAutoDeclaration(text: String): DLanguageAutoAssignment {
        myFixture.configureByText(DlangFileType, text)
        return myFixture.findElementByText("expr", DLanguageAutoAssignment::class.java)
    }

    private fun checkTypes(expectedType: String, expr: DLanguageAutoAssignment) {
        assertNotNull(expr)
        assertEquals(expectedType, expr.dType.toString())
    }

}
