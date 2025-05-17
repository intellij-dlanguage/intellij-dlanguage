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
        doTest("float", """auto expr = 1F;""")
        doTest("long", """auto expr = 1L;""")
        doTest("long", """auto expr = 0b1L;""")
        doTest("long", """auto expr = 0xFL;""")
        doTest("ulong", """auto expr = 1UL;""")
        doTest("double", """auto expr = 1.0;""")
        doTest("double", """auto expr = 1.;""")
        doTest("float", """auto expr = 1.0f;""")
        doTest("bool", """auto expr = true;""")
        doTest("bool", """auto expr = false;""")
        doTest("char", """auto expr = 'a';""")

        // Check against limit provided in the lexer spec page
        doTest("int", """auto expr = 0;""")
        doTest("int", """auto expr = 2_147_483_647;""")
        doTest("long", """auto expr = 2_147_483_648;""")
        doTest("long", """auto expr = 9_223_372_036_854_775_807;""")
        doTest("ulong", """auto expr = 9_223_372_036_854_775_808;""")
        doTest("ulong", """auto expr = 18_446_744_073_709_551_615;""")

        doTest("long", """auto expr = 0L;""")
        doTest("long", """auto expr = 9_223_372_036_854_775_807L;""")
        doTest("uint", """auto expr = 0U;""")
        doTest("uint", """auto expr = 4_294_967_295U;""")
        doTest("ulong", """auto expr = 4_294_967_296U;""")
        doTest("ulong", """auto expr = 18_446_744_073_709_551_615U;""")
        doTest("ulong", """auto expr = 0UL;""")
        doTest("ulong", """auto expr = 18_446_744_073_709_551_615UL;""")

        doTest("int", """auto expr = 0x0;""")
        doTest("int", """auto expr = 0x7FFF_FFFF;""")
        doTest("uint", """auto expr = 0x8000_0000;""")
        doTest("uint", """auto expr = 0xFFFF_FFFF;""")
        doTest("long", """auto expr = 0x1_0000_0000;""")
        doTest("long", """auto expr = 0x7FFF_FFFF_FFFF_FFFF;""")
        doTest("ulong", """auto expr = 0x8000_0000_0000_0000;""")
        doTest("ulong", """auto expr = 0xFFFF_FFFF_FFFF_FFFF;""")

        doTest("long", """auto expr = 0x0L;""")
        doTest("long", """auto expr = 0x7FFF_FFFF_FFFF_FFFFL;""")
        doTest("ulong", """auto expr = 0x8000_0000_0000_0000L;""")
        doTest("ulong", """auto expr = 0xFFFF_FFFF_FFFF_FFFFL;""")
        doTest("uint", """auto expr = 0x0U;""")
        doTest("uint", """auto expr = 0xFFFF_FFFFU;""")
        doTest("ulong", """auto expr = 0x1_0000_0000U;""")
        doTest("ulong", """auto expr = 0xFFFF_FFFF_FFFF_FFFFU;""")
        doTest("ulong", """auto expr = 0x0UL;""")
        doTest("ulong", """auto expr = 0xFFFF_FFFF_FFFF_FFFFUL;""")

        doTest("float", """auto expr = -1.175494351e-38F;""")
        doTest("double", """auto expr = 0xAp0;""")
        doTest("double", """auto expr = 0x1.FFFFFFFFFFFFFp1023;""")
        doTest("double", """auto expr = 0x1p-52;""")
    }

    @Test
    fun testAutoExpressionArrayAssignment() {
        doTest("char", """char[] x; auto expr = x[3];""")
        doTest("char[]", """char* x; auto expr = x[0 .. 7];""")
    }

    @Test
    fun testFunctionCall() {
        doTest("void[]", """void[] a() {} auto expr = a();""")
    }

    @Test
    fun testAutoExpressionPropertiesCallAssignment() {
        doTest("int", """int[] x; auto expr = x.length;""")
        doTest("void[]*", """void[] x; auto expr = x.ptr;""")
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
