package io.github.intellij.dlanguage.actions

import com.intellij.testFramework.LightPlatform4TestCase
import org.junit.Test

/**
 * @author Samael Bate (singingbush)
 * created on 13/02/2022
 */
class CreateDlangClassDeclarationValidatorTest : LightPlatform4TestCase() {
    companion object {

        private const val EMPTY_ERROR = "Name can’t be empty"
        private const val EMPTY_PARTS_ERROR = "Name can’t have empty parts"
        private const val INVALID_NAME_ERROR = "Not a valid D class name."
    }

    @Test
    fun testEmptyName() {
        validateName("", EMPTY_ERROR)
    }

    @Test
    fun testSpaces() {
        validateName("       ", EMPTY_ERROR)
    }

    @Test
    fun testEmptyEnd() {
        validateName("test.", EMPTY_PARTS_ERROR)
    }

    @Test
    fun testEmptyPartInQualified() {
        validateName("a..b", EMPTY_PARTS_ERROR)
    }

    @Test
    fun testModuleWithPackage() {
        validateName("a.b.d", null)
    }

    @Test
    fun testSimpleFile() {
        validateName("foo", null)
    }

    @Test
    fun test() {
        validateName("MyClass", null)
        validateName("ny_class", null)
        validateName("my-class", INVALID_NAME_ERROR)
    }

    @Test
    fun testArroundSpaces() {
        validateName("  space   ", null)
    }

    private fun validateName(name: String, errorMessage: String?) {
        val actualError = ClassNameValidator.getErrorText(name)
        assertEquals("Invalid error message", errorMessage, actualError)
    }
}
