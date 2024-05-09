package io.github.intellij.dlanguage.psi.references

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiNamedElement
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.psi.impl.DElementFactory
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.utils.Constructor
import org.junit.Test

/**
 * @author Samael Bate (singingbush)
 * created on 20/02/2022
 */
class DReferenceTest : LightPlatformCodeInsightFixture4TestCase() {

    // todo: finish writing this test
    @Test
    fun `test DReference MultiResolve`() {
        val source = """import std.stdio : writeln;
            |class MyClass {
            |    private string message;
            |    this(string message) {
            |        this.message = message;
            |    }
            |
            |    public void print() {
            |        writeln(this.message);
            |    }
            |}
            |void main() {
            |    auto myC = new My<caret>Class("Hello World!");
            |
            |    myC.print();
            |}
            """.trimMargin()

        myFixture.configureByText(DlangFileType, source)

        val element = myFixture.elementAtCaret as DNamedElement // DlangIdentifier

        assertTrue(element is Constructor)
    }

    @Test
    fun `test DReference getVariants() for native completion (incomplete test)`() {
        val source = """import std.stdio : writeln;
            |class MyClass {
            |    private string message;
            |    this(string message) {
            |        this.message = message;
            |    }
            |
            |    public void print() {
            |        writeln(this.message);
            |    }
            |}
            |void main() {
            |    auto myC = new MyClass("Hello World!");
            |
            |    myC.<caret>print();
            |}
            """.trimMargin()

        myFixture.configureByText(DlangFileType, source)

        val element = myFixture.elementAtCaret as PsiNamedElement
        val textRange: TextRange = element.textRange

        val ref = DReference(element, textRange)

        PropertiesComponent.getInstance().setValue("USE_NATIVE_CODE_COMPLETION", false)

        assertTrue("With native completion disabled the result should be an empty array", ref.variants.isEmpty())

        PropertiesComponent.getInstance().setValue("USE_NATIVE_CODE_COMPLETION", true)

        val results = ref.variants

        assertNotNull("With native completion enabled there should be results", results)
        assertTrue(results.isNotEmpty())
        assertTrue(results.size > 110) // got different size locally compared to CI. Either way there's at least 110
    }

    @Test
    fun `test DReference handleElementRename with Identifier`() {
        val element: PsiNamedElement = DElementFactory.createDLanguageIdentifierFromText(project, "myvar")!!

        val ref = DReference(element, element.textRange)

        WriteCommandAction.runWriteCommandAction(project) {
            val result = ref.handleElementRename("betterName")
            assertEquals("betterName", result.text)
        }
    }
}
