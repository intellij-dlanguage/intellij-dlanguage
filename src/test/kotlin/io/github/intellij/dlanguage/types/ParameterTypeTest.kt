package io.github.intellij.dlanguage.types

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.psi.named.DLanguageParameter
import org.intellij.lang.annotations.Language
import org.junit.Test

class ParameterTypeTest : LightPlatformCodeInsightFixture4TestCase() {


    @Test
    fun testSimpleParameterInstance() {
        doTest(
            "Foo", """
            class Foo {}

            void foo(Foo expr) {}
        """.trimIndent()
        )
        doTest(
            "Foo", """
            interface Foo {}

            void foo(Foo expr) {}
        """.trimIndent()
        )
        doTest(
            "Foo", """
            struct Foo {}

            void foo(Foo expr) {}
        """.trimIndent()
        )
        doTest(
            "Foo", """
            enum Foo { E }

            void foo(Foo expr) {}
        """.trimIndent()
        )
        doTest(
            "Foo", """
            union Foo {}

            void foo(Foo expr) {}
        """.trimIndent()
        )
    }

    @Test
    fun testAliasedTypeParameterInstance() {
        doTest("alias Foo = int", """
            alias Foo = int;

            void foo(Foo expr) {}
        """.trimIndent())
    }

    /**
     * Take a file text and the expected type string representation and ensure that the expression named (expr) has the corresponding type
     */
    private fun doTest(expectedType: String, @Language("D") text: String) =
        checkTypes(expectedType, parseParameter(text))

    private fun parseParameter(text: String): DLanguageParameter {
        myFixture.configureByText(DlangFileType, text)
        return myFixture.findElementByText("expr", DLanguageParameter::class.java)
    }

    private fun checkTypes(expectedType: String, expr: DLanguageParameter) {
        assertNotNull(expr)
        assertEquals(expectedType, expr.dType.toString())
    }
}
