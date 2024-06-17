package io.github.intellij.dlanguage.actions

import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.testFramework.LightPlatform4TestCase
import org.junit.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * @author Samael Bate (singingbush)
 * created on 13/02/2022
 */
class CreateDlangClassDeclarationActionTest : LightPlatform4TestCase() {

    private val action = CreateDlangClassActionWrapper()

    @Test
    fun testClassNameValidatorCheckInput() {
        val validator = action.makeClassNameValidator()

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
        val validator = action.makeClassNameValidator()

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
        val validator = action.makeClassNameValidator()

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

    @Test
    fun testBuildDialog() {
        val directory: PsiDirectory = mock()
        val builder: CreateFileFromTemplateDialog.Builder = mock()
        whenever(builder.setTitle(anyString())).thenReturn(builder)

        this.action.buildDialog(super.getProject(), directory, builder)

        verify(builder, times(1)).setTitle(eq(CreateDlangClassAction.NEW_D_FILE))
        verify(builder, times(1)).setValidator(any(CreateDlangClassAction.ClassNameValidator::class.java))
    }

// todo: work out how to test createFileFromTemplate() method. Where most the functionality is
//    @Test
//    fun testCreateFileFromTemplate() {
//        val directory: PsiDirectory = mock()
//        val virtualDir: VirtualFile = mock()
//        whenever(virtualDir.path).thenReturn("")
//        whenever(directory.virtualFile).thenReturn(virtualDir)
//        whenever(directory.project).thenReturn(super.getProject())
//
//        val template: FileTemplate = mock()
//
//        val file = this.action.createFileFromTemplate("file name", template, directory)
//
//        assertNotNull(file)
//    }

    @Test
    fun testGetActionNameAlwaysReturnsSameValue() {
        val directory: PsiDirectory = mock()

        assertEquals(CreateDlangClassAction.NEW_D_FILE, action.getActionName(directory, "some text", "invalid template name"))

        assertEquals(CreateDlangClassAction.NEW_D_FILE, action.getActionName(directory, "", ""))
    }

    @Test
    fun testIsAvailableReturnsFalseByDefault() {
        val context: DataContext = mock()

        assertFalse(this.action.isAvailable(context))
    }

// todo: work out how to test this scenario
//    @Test
//    fun testIsAvailableReturnsTrueUnderRightConditions() {
//        val context: DataContext = mock()
//
//        assertTrue(this.action.isAvailable(context))
//    }

    @Test
    fun testTheActionHasCorrectProperties() {
        assertTrue(this.action.templateText == CreateDlangClassAction.NEW_D_FILE)

        assertTrue(this.action.isDefaultIcon) // shouldn't this be false???
        assertFalse(this.action.isEnabledInModalContext)
        assertTrue(this.action.synonyms.isEmpty())
        assertTrue(this.action.startInWriteAction());
        assertFalse(this.action.displayTextInToolbar())
    }

    /*
    * This wrapper class enables us to test the protected functions in CreateDlangClassAction.kt
    */
    private class CreateDlangClassActionWrapper : CreateDlangClassAction() {

        // Use this wrapper class to surface the inner class
        fun makeClassNameValidator(): ClassNameValidator = ClassNameValidator()

        public override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
            super.buildDialog(project, directory, builder)
        }

        public override fun createFileFromTemplate(name: String, template: FileTemplate, inRequestedDirectory: PsiDirectory): PsiFile {
            return super.createFileFromTemplate(name, template, inRequestedDirectory)
        }

        public override fun getActionName(directory: PsiDirectory, newName: String, templateName: String): String {
            return super.getActionName(directory, newName, templateName)
        }

        public override fun isAvailable(dataContext: DataContext): Boolean {
            return super.isAvailable(dataContext)
        }
    }
}
