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
    }

    @Test
    fun testForeachVariableFromAssociativeArray() {
        doTest("int", """void foo() { int[char] arr; foreach(expr; arr) { } }""")
        doTest("char", """void foo() { int[char] arr; foreach(expr, a; arr) { } }""")
        doTest("int", """void foo() { int[char] arr; foreach(a, expr; arr) { } }""")
        doTest("int", """void foo() { int[char] arr; foreach(a, ref expr; arr) { } }""")
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
