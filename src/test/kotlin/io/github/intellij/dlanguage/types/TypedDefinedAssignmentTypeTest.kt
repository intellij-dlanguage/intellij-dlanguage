package io.github.intellij.dlanguage.types

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.psi.named.DLanguageIdentifierInitializer
import org.intellij.lang.annotations.Language
import org.junit.Test

class TypedDefinedAssignmentTypeTest : LightPlatformCodeInsightFixture4TestCase() {

    @Test
    fun testEnsureNonAutoVariableDeclarationTypedCorrectly() {
        doTest("bool", "bool expr;")
        doTest("byte", "byte expr;")
        doTest("ubyte", "ubyte expr;")
        doTest("short", "short expr;")
        doTest("ushort", "ushort expr;")
        doTest("int", "int expr;")
        doTest("uint", "uint expr;")
        doTest("long", "long expr;")
        doTest("ulong", "ulong expr;")
        doTest("float", "float expr;")
        doTest("double", "double expr;")
        doTest("char", "char expr;")
        doTest("wchar", "wchar expr;")
        doTest("dchar", "dchar expr;")
    }

    @Test
    fun testSimpleCustomTypeInstance() {
        doTest("Foo", """
            class Foo {}

            Foo expr;
        """.trimIndent())
        doTest("Foo", """
            interface Foo {}

            Foo expr;
        """.trimIndent())
        doTest("Foo", """
            struct Foo {}

            Foo expr;
        """.trimIndent())
        doTest("Foo", """
            enum Foo { E }

            Foo expr;
        """.trimIndent())
        doTest("Foo", """
            union Foo {}

            Foo expr;
        """.trimIndent())
    }

    @Test
    fun testArrayTypeInstance() {
        doTest("char[]", """char[] expr;""")
        doTest("char[]", """char[12] expr;""") // TODO should be char[12] but it is currently unsupported
        doTest("int[int]", """int[int] expr;""")
    }

    /**
     * Take a file text and the expected type string representation and ensure that the expression named (expr) has the corresponding type
     */
    private fun doTest(expectedType: String, @Language("D") text: String) =
        checkTypes(expectedType, parseVariableDeclaration(text))

    private fun parseVariableDeclaration(text: String): DLanguageIdentifierInitializer {
        myFixture.configureByText(DlangFileType, text)
        return myFixture.findElementByText("expr", DLanguageIdentifierInitializer::class.java)
    }

    private fun checkTypes(expectedType: String, expr: DLanguageIdentifierInitializer) {
        assertNotNull(expr)
        assertEquals(expectedType, expr.dType.toString())
    }

}
