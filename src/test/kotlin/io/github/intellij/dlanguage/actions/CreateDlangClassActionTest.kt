package io.github.intellij.dlanguage.actions

import com.intellij.testFramework.LightPlatform4TestCase
import org.junit.Test


class TestClassNameValidator : LightPlatform4TestCase() {

    private val validator = CreateDlangClassAction.ClassNameValidator()

    @Test
    fun testClassNameValidatorCheckInput() {

        // The validator currently returns true for any scenario. Possibly a problem, should check into it
        assertTrue(validator.checkInput(""))

        assertTrue(validator.checkInput("myclass.d"))
        assertTrue(validator.checkInput("myclass"))
        assertTrue(validator.checkInput("my.class"))
        assertTrue(validator.checkInput("MyClass"))
        assertTrue(validator.checkInput("my_class"))
        assertTrue(validator.checkInput("my-class"))
        assertTrue(validator.checkInput("  spaces around words  "))
        assertTrue(validator.checkInput("test123"))
        assertTrue(validator.checkInput("test123.d"))
    }

    @Test
    fun testClassNameValidatorCanClose() {

        assertTrue(validator.canClose(""))
        assertTrue(validator.canClose("myclass.d"))
        assertTrue(validator.canClose("myclass"))
        assertTrue(validator.canClose("my.class"))
        assertTrue(validator.canClose("MyClass"))
        assertTrue(validator.canClose("my_class"))
        assertTrue(validator.checkInput("test123"))
        assertTrue(validator.checkInput("test123.d"))

        assertFalse(validator.canClose("my-class"))
        assertFalse(validator.canClose(".myclass"))
        assertFalse(validator.canClose("1myclass"))
        assertFalse(validator.canClose("  spaces around words  "))
    }

    @Test
    fun testClassNameValidatorGetErrorText() {

        // valid text (should return null)
        assertNull(validator.getErrorText(""))
        assertNull(validator.getErrorText("myclass.d"))
        assertNull(validator.getErrorText("myclass"))
        assertNull(validator.getErrorText("my.class"))
        assertNull(validator.getErrorText("MyClass"))
        assertNull(validator.getErrorText("my_class"))

        // invalid text will return an error message
        assertEquals("'my-class' is not a valid D class name.", validator.getErrorText("my-class"))
        assertEquals("'  spaces around words  ' is not a valid D class name.", validator.getErrorText("  spaces around words  "))
    }
}
