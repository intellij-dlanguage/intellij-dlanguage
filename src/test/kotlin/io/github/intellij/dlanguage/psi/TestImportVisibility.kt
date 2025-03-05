package io.github.intellij.dlanguage.psi
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.psi.interfaces.DVisibility
import org.intellij.lang.annotations.Language
import org.junit.Test

class TestImportVisibility : LightPlatformCodeInsightFixture4TestCase() {

    @Test
    fun testSimpleImport() = doTest(DVisibility.Private, """import myimport;""")

    @Test
    fun testPublicImport() = doTest(DVisibility.Public, """public import myimport;""")

    @Test
    fun testImportInPublicBlock() = doTest(DVisibility.Public, """public: import myimport;""")

    @Test
    fun testImportInPublicBlockWithOtherDeclaration() = doTest(DVisibility.Public, """public: struct A {} import myimport;""")

    @Test
    fun testImportInPublicStruct() = doTest(DVisibility.Private, """public: struct A { import myimport; }""")

    @Test
    fun testImportInPrivateAfterPublic() = doTest(DVisibility.Private, """public:private: import myimport;""")

    @Test
    fun testImportPublicBlock() = doTest(DVisibility.Private, """public{} import myimport;""")

    @Test
    fun testImportInPublicConstructor() = doTest(DVisibility.Private, """class A { public this() { import myimport; } }""")

    @Test
    fun testImportInConditionalDeclaration() = doTest(DVisibility.Public, """public: version(Foo) { import myimport; }""")

    @Test
    fun testImportInConditionalDeclarationWithDoubleAttribute() = doTest(DVisibility.Private, """public:private: version(Foo) { import myimport; }""")

    private fun doTest(expectedVisibility: DVisibility,  @Language("D") text: String) =
        checkVisibility(expectedVisibility, parseImportDeclaration(text))

    private fun parseImportDeclaration(text: String): DLanguageImportDeclaration {
        myFixture.configureByText(DlangFileType, text)
        return myFixture.findElementByText("myimport", DLanguageImportDeclaration::class.java)
    }

    private fun checkVisibility(expectedVisibility: DVisibility, expr: DLanguageImportDeclaration) {
        assertNotNull(expr)
        assertEquals(expectedVisibility, expr.visibility())
    }
}
