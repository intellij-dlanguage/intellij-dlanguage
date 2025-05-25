package io.github.intellij.dlanguage.types

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.psi.named.DLanguageEnumMember
import org.intellij.lang.annotations.Language
import org.junit.Test

class EnumTypeTest : LightPlatformCodeInsightFixture4TestCase() {

    @Test
    fun testEnumMemberOfEnumType() {
        doTest("x", """enum x { MEMBER }""")
        doTest("x", """enum x { foo = 3L, MEMBER }""")
        doTest("x", """enum x : int { MEMBER }""")
        doTest("x", """enum x : short { MEMBER }""")
        doTest("x", """enum x { short MEMBER }""")
    }

    // Need to introduce another method or whatever to allow to gen the "native" type of the member
    // TODO need to add a method in enum to get the native type (not the enum type but the "final" type)
    /*@Test
    fun testEnumMemberOfEnumType() {
        doTestNativeType("int", """enum x { MEMBER }""")
        doTestNativeType("long", """enum x { foo = 3L, MEMBER }""")
        doTestNativeType("int", """enum x : int { MEMBER }""")
        doTestNativeType("short", """enum x : short { MEMBER }""")
        doTestNativeType("int", """enum x { short MEMBER }""") // named enum cannot have type per member
    }*/

    @Test
    fun testEnumMemberOfAnonymousEnumType() {
        doTest("int", """enum { MEMBER }""")
        doTest("short", """enum : short { MEMBER }""")
        doTest("long", """enum { long MEMBER = 3 }""")
        doTest("long", """enum { MEMBER = 3L }""")
        doTest("long", """enum { MEMBER = B, B = C, C = 3L }""")
        doTest("short", """enum { long bar = 1, short Foo = 2, MEMBER }""")
        doTest("long", """enum : long { short MEMBER = 3 }""") // anonymous enum and member type cannot have a type at the same time
    }

    /**
     * Take a file text and the expected type string representation and ensure that the expression named (expr) has the corresponding type
     */
    private fun doTest(expectedType: String, @Language("D") text: String) =
        checkTypes(expectedType, parseEnumDeclaration(text))

    private fun parseEnumDeclaration(text: String): DLanguageEnumMember {
        myFixture.configureByText(DlangFileType, text)
        return myFixture.findElementByText("MEMBER", DLanguageEnumMember::class.java)
    }

    private fun checkTypes(expectedType: String, expr: DLanguageEnumMember) {
        assertNotNull(expr)
        assertEquals(expectedType, expr.dType.toString())
    }
}
