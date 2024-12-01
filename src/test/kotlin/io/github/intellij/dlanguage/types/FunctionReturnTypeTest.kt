package io.github.intellij.dlanguage.types

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.psi.named.DLanguageFunctionDeclaration
import org.intellij.lang.annotations.Language
import org.junit.Test

class FunctionReturnTypeTest : LightPlatformCodeInsightFixture4TestCase() {
    @Test
    fun testDefinedReturnedTypeFunction() {
        doTest("int", """int func() {}""")
        doTest("void[]", """void[] func() {}""")
    }

    /**
     * Take a file text and the expected type string representation and ensure that the function named (func) has the corresponding type
     */
    private fun doTest(expectedType: String, @Language("D") text: String) =
        checkTypes(expectedType, parseFunctionDeclaration(text))

    private fun parseFunctionDeclaration(text: String): DLanguageFunctionDeclaration {
        myFixture.configureByText(DlangFileType, text)
        return myFixture.findElementByText("func", DLanguageFunctionDeclaration::class.java)
    }

    private fun checkTypes(expectedType: String, func: DLanguageFunctionDeclaration) {
        assertNotNull(func)
        assertEquals(expectedType, func.returnDType.toString())
    }
}
