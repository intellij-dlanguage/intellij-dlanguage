package io.github.intellij.dlanguage.features

import com.intellij.codeInsight.documentation.DocumentationManager
import com.intellij.psi.util.startOffset
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import io.github.intellij.dlanguage.psi.named.DLanguageClassDeclaration
import io.github.intellij.dlanguage.psi.named.DLanguageFunctionDeclaration
import org.junit.Test

class DDocumentationProviderTest : LightPlatformCodeInsightFixture4TestCase() {

    private var provider: DDocumentationProvider? = null

    @Throws(Exception::class)
    public override fun setUp() {
        super.setUp()
        provider = DDocumentationProvider()
    }

    override fun getTestDataPath(): String {
        return this.javaClass.classLoader.getResource("gold/documentation")!!.path
    }

    @Test
    fun testGetUrlForHandlesSingleImport() {
        myFixture.configureByText("example.d", "import std.typecons;")

        val elementAndContext = DocumentationManager.getInstance(project)
            .findTargetElementAndContext(myFixture.editor, 13, myFixture.file)!!

        val result = provider!!.getUrlFor(elementAndContext.first, elementAndContext.second)
        assertEquals("https://dlang.org/phobos/std_typecons.html", result[0])
    }

    @Test
    fun testGetUrlForHandlesNullAndNonImportStatement() {
        myFixture.configureByText("example.d", "class User { int id; string name;}")

        val elementAndContext = DocumentationManager.getInstance(project)
            .findTargetElementAndContext(myFixture.editor, 17, myFixture.file)!!

        assertEmpty("Should return empty list rather than throwing exception", provider!!.getUrlFor(null, null))
        assertEmpty("Should return empty list rather than throwing exception", provider!!.getUrlFor(elementAndContext.first, null))
        assertEmpty("Should return empty list rather than throwing exception", provider!!.getUrlFor(null, elementAndContext.second))
        assertEmpty("Should return empty list rather than throwing exception", provider!!.getUrlFor(elementAndContext.first, elementAndContext.second))
    }

    @Test
    fun testGenerateDocHandlesNull() {
        val text = provider!!.generateDoc(null, null)
        assertNull("Should return null rather than throwing exception", text)
    }

    @Test
    fun testGenerateDocForPublicClassVariable() {
        myFixture.configureByText("example.d", "class User { int id; string name;}")

        // class User { int id; string name;}
        //                  ^
        myFixture.editor.caretModel.moveToOffset(17)
        val docElement = DocumentationManager.getInstance(project)
            .findTargetElement(myFixture.editor, myFixture.file)
        val text = provider!!.generateDoc(docElement, null)
        assertNotNull(text)
        assertTrue(text!!.contains("int"))
        assertTrue(text.contains("id"))
    }

    @Test
    fun testGenerateDocForPrivateClassVariable() {
        myFixture.configureByText("example.d", "class User { int id; private string name;}")

        // class User { int id; private string name;}
        //                                      ^
        myFixture.editor.caretModel.moveToOffset(37)
        val docElement = DocumentationManager.getInstance(project)
            .findTargetElement(myFixture.editor, myFixture.file)
        val text = provider!!.generateDoc(docElement, null)
        assertNotNull(text)
        //assertTrue(text.contains("private")) XXX it’s private, so we can display it
        assertTrue(text!!.contains("string"))
        assertTrue(text.contains("name"))
    }

    @Test
    fun testGenerateDocForGlobalInt() {
        myFixture.configureByText("int.d", "int x = 0;")

        // int x = 0;
        //     ^
        myFixture.editor.caretModel.moveToOffset(4)
        val docElement = DocumentationManager.getInstance(project)
            .findTargetElement(myFixture.editor, myFixture.file)
        val text = provider!!.generateDoc(docElement, null)
        assertNotNull(text)
        assertTrue(text!!.contains("int"))
        assertTrue(text.contains("x"))
    }

    /*
     * Run a test that uses the "gold/documentation/example.d" file
     */
    @Test
    fun testGenerateDocForFileBasedExample() {
        myFixture.configureByFile("example.d")

        val doSomethingMethod = myFixture.findElementByText("doSomething", DLanguageFunctionDeclaration::class.java)

        // put the caret on the doSomething() function in the source file
        myFixture.editor.caretModel.moveToOffset(doSomethingMethod!!.identifier!!.startOffset)
        val docElement = DocumentationManager.getInstance(project)
            .findTargetElement(myFixture.editor, myFixture.file)
        val text = provider!!.generateDoc(docElement, null)
        assertNotNull(text)
        //assertTrue(text!!.contains("static"))
        assertTrue(text!!.contains("void"))
        assertTrue(text.contains("doSomething"))
        assertTrue(text.contains("()"))
        assertTrue(text.contains("This is the method documentation."))
    }

    @Test
    fun testGenerateDocForClassBasedExample() {
        myFixture.configureByFile("example.d")

        val myCodeClass = myFixture.findElementByText("MyCode", DLanguageClassDeclaration::class.java)

        // put the caret on the doSomething() function in the source file
        myFixture.editor.caretModel.moveToOffset(myCodeClass!!.identifier!!.startOffset)
        val docElement = DocumentationManager.getInstance(project)
            .findTargetElement(myFixture.editor, myFixture.file)
        val text = provider!!.generateDoc(docElement, null)
        assertNotNull(text)

        assertTrue(text!!.contains("This is the CLASS documentation"))
    }


    @Test
    fun testGenerateDocWithEmptyCodeSnippet() {
        myFixture.configureByText("example.d", """
            /**
             Contains empty code snippet
             ~~~ ~~~*/
             class I {}
        """.trimIndent())

        val myCodeClass = myFixture.findElementByText("I", DLanguageClassDeclaration::class.java)

        // put the caret on the doSomething() function in the source file
        myFixture.editor.caretModel.moveToOffset(myCodeClass!!.identifier!!.startOffset)
        val docElement = DocumentationManager.getInstance(project)
            .findTargetElement(myFixture.editor, myFixture.file)
        val text = provider!!.generateDoc(docElement, null)
        assertNotNull(text)

        assertTrue(text!!.contains("Contains empty code snippet"))
    }
}
