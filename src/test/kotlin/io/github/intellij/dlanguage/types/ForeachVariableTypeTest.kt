package io.github.intellij.dlanguage.types

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.psi.named.DLanguageForeachType
import org.intellij.lang.annotations.Language
import org.junit.Test

class ForeachVariableTypeTest : LightPlatformCodeInsightFixture4TestCase() {

    @Test
    fun testForeachVariableFromArray() {
        doTest("int", """void foo() { int[] arr; foreach(expr; arr) { } }""")
        doTest("int", """void foo() { foreach(expr; [1, 2, 3]) { } }""")
        doTest("float", """void foo() { foreach(expr; [1f, 2f, 3f]) { } }""")
        doTest("double", """void foo() { foreach(expr; [1, 2., 3]) { } }""")
        doTest("char", """void foo() { foreach(expr; "test") { } }""")
        doTest("int", """void foo() { foreach(expr; 1 .. 3) { } }""")
        doTest("long", """void foo() { foreach(expr; 1 .. 3L) { } }""")
        doTest("long", """void foo() { foreach(expr; 1L .. 3) { } }""")
        doTest("long", """void foo() { foreach(idx, expr; [1, 2, 3L]) { } }""")
    }

    @Test
    fun testForeachVariableIndexFromArray() {
        doTest("ushort", """void foo() { foreach(expr, a; [1, 2, 3L]) { } }""")
        doTest("int", """void foo() { int[] arr; foreach(expr, a; arr) { } }""") // TODO size_t
    }

    @Test
    fun testForeachVariableFromAssociativeArray() {
        doTest("int", """void foo() { int[char] arr; foreach(expr; arr) { } }""")
        doTest("char", """void foo() { int[char] arr; foreach(expr, a; arr) { } }""")
        doTest("int", """void foo() { int[char] arr; foreach(a, expr; arr) { } }""")
        doTest("int", """void foo() { int[char] arr; foreach(a, ref expr; arr) { } }""")
        doTest("int", """void foo() { foreach(a, expr; ["key": 1]) { } }""")
        doTest("char[]", """void foo() { foreach(expr, a; ["key": 1]) { } }""")
    }

    @Test
    fun testStaticForeachVariableFromArray() {
        doTest("int", """static foreach(expr; [1, 2, 3]) { }""")
    }

    private fun doTest(expectedType: String, @Language("D") text: String) =
        checkTypes(expectedType, parseForeach(text))

    private fun parseForeach(text: String): DLanguageForeachType {
        myFixture.configureByText(DlangFileType, text)
        return myFixture.findElementByText("expr", DLanguageForeachType::class.java)
    }

    private fun checkTypes(expectedType: String, expr: DLanguageForeachType) {
        assertNotNull(expr)
        assertEquals(expectedType, expr.dType.toString())
    }
}
