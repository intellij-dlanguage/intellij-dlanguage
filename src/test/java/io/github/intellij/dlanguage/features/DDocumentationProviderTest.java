package io.github.intellij.dlanguage.features;

import com.intellij.codeInsight.documentation.DocumentationManager;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class DDocumentationProviderTest extends BasePlatformTestCase {

    private DDocumentationProvider provider;

    public void setUp() throws Exception {
        super.setUp();

        provider = new DDocumentationProvider();
    }

    @Override
    protected String getTestDataPath() {
        return this.getClass().getClassLoader().getResource("gold/documentation").getPath();
    }

    public void testGenerateDocHandlesNull() {
        final String text = provider.generateDoc(null, null);

        assertNull("Should return null rather than throwing exception", text);
    }

    public void testGenerateDocForPublicClassVariable() {
        this.myFixture.configureByText("example.d", "class User { int id; string name;}");

        // class User { int id; string name;}
        //                  ^
        myFixture.getEditor().getCaretModel().moveToOffset(17);

        final PsiElement docElement = DocumentationManager.getInstance(getProject())
            .findTargetElement(myFixture.getEditor(), myFixture.getFile());

        final String text = provider.generateDoc(docElement, null);

        assertNotNull(text);
        assertTrue(text.contains("symbol"));
        assertTrue(text.contains("public"));
        assertFalse(text.contains("private"));
    }

    public void testGenerateDocForPrivateClassVariable() {
        this.myFixture.configureByText("example.d", "class User { int id; private string name;}");

        // class User { int id; private string name;}
        //                                      ^
        myFixture.getEditor().getCaretModel().moveToOffset(37);

        final PsiElement docElement = DocumentationManager.getInstance(getProject())
            .findTargetElement(myFixture.getEditor(), myFixture.getFile());

        final String text = provider.generateDoc(docElement, null);

        assertNotNull(text);
        assertTrue(text.contains("symbol"));
        assertFalse(text.contains("public"));
        assertTrue(text.contains("private"));
    }

    public void testGenerateDocForGlobalInt() {
        this.myFixture.configureByText("int.d", "int x = 0;");

        // int x = 0;
        //     ^
        myFixture.getEditor().getCaretModel().moveToOffset(4);

        final PsiElement docElement = DocumentationManager.getInstance(getProject())
            .findTargetElement(myFixture.getEditor(), myFixture.getFile());

        final String text = provider.generateDoc(docElement, null);

        assertNotNull(text);
        assertTrue(text.contains("symbol"));
        assertTrue(text.contains("public"));
        assertFalse(text.contains("private"));
        assertTrue(text.contains("static"));
    }

    /*
     * Run a test that uses the "gold/documentation/example.d" file
     */
    public void testGenerateDocForFileBasedExample() {
        this.myFixture.configureByFile("example.d");

        // put the caret on the doSomething() function in the source file
        myFixture.getEditor().getCaretModel().moveToOffset(40);

        final PsiElement docElement = DocumentationManager.getInstance(getProject())
            .findTargetElement(myFixture.getEditor(), myFixture.getFile());

        final String text = provider.generateDoc(docElement, null);

        assertNotNull(text);
        assertTrue(text.contains("symbol"));
        assertTrue(text.contains("public"));
        assertFalse(text.contains("private"));
        assertTrue(text.contains("static"));
    }
}
