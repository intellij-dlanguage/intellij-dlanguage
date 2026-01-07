package io.github.intellij.dlanguage.features

import com.intellij.lang.documentation.ide.IdeDocumentationTargetProvider
import com.intellij.platform.backend.documentation.DocumentationData
import com.intellij.platform.backend.documentation.impl.computeDocumentationBlocking
import com.intellij.psi.util.startOffset
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import io.github.intellij.dlanguage.psi.named.DLanguageClassDeclaration
import io.github.intellij.dlanguage.psi.named.DLanguageFunctionDeclaration
import org.junit.Test

class DDocumentationProviderTest : LightPlatformCodeInsightFixture4TestCase() {

    @Throws(Exception::class)
    public override fun setUp() {
        super.setUp()
    }

    override fun getTestDataPath(): String {
        return this.javaClass.classLoader.getResource("gold/documentation")!!.path
    }

    @Test
    fun testGenerateDocForPublicClassVariable() {
        myFixture.configureByText("example.d", "class User { int id; string name;}")

        // class User { int id; string name;}
        //                  ^
        myFixture.editor.caretModel.moveToOffset(17)
        val data = findDocumentationData()
        assertNotNull(data)
        val text = data!!.html
        assertTrue(text.contains("int"))
        assertTrue(text.contains("id"))
    }

    @Test
    fun testGenerateDocForPrivateClassVariable() {
        myFixture.configureByText("example.d", "class User { int id; private string name;}")

        // class User { int id; private string name;}
        //                                      ^
        myFixture.editor.caretModel.moveToOffset(37)
        val data = findDocumentationData()
        assertNotNull(data)
        val text = data!!.html
        //assertTrue(text.contains("private")) XXX it’s private, so we can display it
        assertTrue(text.contains("string"))
        assertTrue(text.contains("name"))
    }

    @Test
    fun testGenerateDocForGlobalInt() {
        myFixture.configureByText("int.d", "int x = 0;")

        // int x = 0;
        //     ^
        myFixture.editor.caretModel.moveToOffset(4)
        val data = findDocumentationData()
        assertNotNull(data)
        val text = data!!.html
        assertTrue(text.contains("int"))
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
        val data = findDocumentationData()
        assertNotNull(data)
        val text = data!!.html
        assertTrue(text.contains("void"))
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
        val data = findDocumentationData()
        assertNotNull(data)
        val text = data!!.html
        assertTrue(text.contains("This is the CLASS documentation"))
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
        val data = findDocumentationData()
        assertNotNull(data)
        val text = data!!.html
        assertTrue(text.contains("Contains empty code snippet"))
    }

    @Test
    fun testGenerateDocWithEmptyDocComment() {
        myFixture.configureByText("example.d", """
            ///
            class I {}
            """.trimIndent())

        val myCodeClass = myFixture.findElementByText("I", DLanguageClassDeclaration::class.java)

        // put the caret on the declaration in the source file
        myFixture.editor.caretModel.moveToOffset(myCodeClass!!.identifier!!.startOffset)
        val data = findDocumentationData()
        assertNotNull(data)
        val text = data!!.html
        assertFalse(text.contains("<div class='content'>"))
    }

    private fun findDocumentationData(): DocumentationData? {
        val targets = IdeDocumentationTargetProvider.getInstance(project)
            .documentationTargets(myFixture.editor, myFixture.file, myFixture.caretOffset)
        assertSize(1, targets)
        val target = targets.single()
        val data = computeDocumentationBlocking(target.createPointer())
        return data
    }

}
