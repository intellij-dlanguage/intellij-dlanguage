package io.github.intellij.dlanguage.features

import com.intellij.codeInsight.documentation.DocumentationManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class DDocumentationProviderTest : BasePlatformTestCase() {
    private var provider: DDocumentationProvider? = null
    @Throws(Exception::class)
    public override fun setUp() {
        super.setUp()
        provider = DDocumentationProvider()
    }

    override fun getTestDataPath(): String {
        return this.javaClass.classLoader.getResource("gold/documentation")!!.path
    }

    fun testGenerateDocHandlesNull() {
        val text = provider!!.generateDoc(null, null)
        assertNull("Should return null rather than throwing exception", text)
    }

    fun testGenerateDocForPublicClassVariable() {
        myFixture.configureByText("example.d", "class User { int id; string name;}")

        // class User { int id; string name;}
        //                  ^
        myFixture.editor.caretModel.moveToOffset(17)
        val docElement = DocumentationManager.getInstance(project)
            .findTargetElement(myFixture.editor, myFixture.file)
        val text = provider!!.generateDoc(docElement, null)
        assertNotNull(text)
        assertTrue(text!!.contains("symbol"))
        assertTrue(text.contains("public"))
        assertFalse(text.contains("private"))
    }

    fun testGenerateDocForPrivateClassVariable() {
        myFixture.configureByText("example.d", "class User { int id; private string name;}")

        // class User { int id; private string name;}
        //                                      ^
        myFixture.editor.caretModel.moveToOffset(37)
        val docElement = DocumentationManager.getInstance(project)
            .findTargetElement(myFixture.editor, myFixture.file)
        val text = provider!!.generateDoc(docElement, null)
        assertNotNull(text)
        assertTrue(text!!.contains("symbol"))
        assertFalse(text.contains("public"))
        assertTrue(text.contains("private"))
    }

    fun testGenerateDocForGlobalInt() {
        myFixture.configureByText("int.d", "int x = 0;")

        // int x = 0;
        //     ^
        myFixture.editor.caretModel.moveToOffset(4)
        val docElement = DocumentationManager.getInstance(project)
            .findTargetElement(myFixture.editor, myFixture.file)
        val text = provider!!.generateDoc(docElement, null)
        assertNotNull(text)
        assertTrue(text!!.contains("symbol"))
        assertTrue(text.contains("public"))
        assertFalse(text.contains("private"))
        assertTrue(text.contains("static"))
    }

    /*
     * Run a test that uses the "gold/documentation/example.d" file
     */
    fun testGenerateDocForFileBasedExample() {
        myFixture.configureByFile("example.d")

        // put the caret on the doSomething() function in the source file
        myFixture.editor.caretModel.moveToOffset(40)
        val docElement = DocumentationManager.getInstance(project)
            .findTargetElement(myFixture.editor, myFixture.file)
        val text = provider!!.generateDoc(docElement, null)
        assertNotNull(text)
        assertTrue(text!!.contains("symbol"))
        assertTrue(text.contains("public"))
        assertFalse(text.contains("private"))
        assertTrue(text.contains("static"))
    }
}
