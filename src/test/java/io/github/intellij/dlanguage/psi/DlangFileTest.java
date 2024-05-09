package io.github.intellij.dlanguage.psi;

import io.github.intellij.dlanguage.LightDlangTestCase;
import org.junit.Test;

/**
 * @author Samael Bate (singingbush)
 * created on 20/10/18
 */
public class DlangFileTest extends LightDlangTestCase {

    /**
     * D module names are, by default, the file name with the path and extension stripped off.
     * <a href="https://dlang.org/spec/module.html">module</a>
     */
    @Test
    public void testGetModuleName_shouldBeFilenameWithoutExtension() {
        final DlangPsiFileImpl dlangFile = virtualDlangPsiFile("myapp.d", "// no module defined in source");

        assertEquals("myapp.d", dlangFile.getName()); // should be the filename
        assertEquals("myapp", dlangFile.getModuleName());
        assertEquals("myapp", dlangFile.getFullyQualifiedModuleName());
        assertNull(dlangFile.getPackageName());
    }

    /**
     * D module names are, by default, the file name with the path and extension stripped off.
     * <a href="https://dlang.org/spec/module.html">module</a>
     */
    @Test
    public void testGetModuleName_shouldBeFilenameWithoutExtension_IncludingPath() {
        final DlangPsiFileImpl dlangFile = virtualDlangPsiFile("awesome/app.d", "// no module defined in source");

        assertEquals("app.d", dlangFile.getName()); // should be the filename
        assertEquals("app", dlangFile.getModuleName());
        assertEquals("app", dlangFile.getFullyQualifiedModuleName());
        assertNull(dlangFile.getPackageName());
    }

    /**
     * D module names are, by default, the file name with the path and extension stripped off.
     * <a href="https://dlang.org/spec/module.html">module</a>
     */
    @Test
    public void testGetModuleName_HonoursTheNameDefinedInSource() {
        final DlangPsiFileImpl dlangFile = virtualDlangPsiFile("myapp.d", "module realname;");

        assertEquals("myapp.d", dlangFile.getName()); // should be the filename
        assertEquals("realname", dlangFile.getModuleName());
        assertEquals("realname", dlangFile.getFullyQualifiedModuleName());
        assertNull(dlangFile.getPackageName());
    }

    /**
     * D module names are, by default, the file name with the path and extension stripped off.
     * <a href="https://dlang.org/spec/module.html">module</a>
     */
    @Test
    public void testGetModuleName_HonoursTheNameDefinedInSource_Multipart() {
        final DlangPsiFileImpl dlangFile = virtualDlangPsiFile("myapp.d", "module my.app.realname;");

        assertEquals("myapp.d", dlangFile.getName());
        assertEquals("realname", dlangFile.getModuleName());
        assertEquals("my.app.realname", dlangFile.getFullyQualifiedModuleName());
        assertEquals("my.app", dlangFile.getPackageName());
    }

    /**
     * <a href="https://dlang.org/spec/module.html">module</a>
     */
    @Test
    public void testGetModuleName_ReturnsProperModuleName_stdio() {
        final DlangPsiFileImpl dlangFile = virtualDlangPsiFile(
            "c/stdio.d",
            "module c.stdio; // module stdio in the c package"
        );

        assertEquals("stdio.d", dlangFile.getName());
        assertEquals("stdio", dlangFile.getModuleName()); // is this right?

        final String fullyQualifiedModuleName = dlangFile.getFullyQualifiedModuleName();
        assertNotNull(fullyQualifiedModuleName);
        assertEquals("c.stdio", fullyQualifiedModuleName);
        assertEquals("c", dlangFile.getPackageName());
    }

    // https://dlang.org/spec/module.html
    @Test
    public void testGetModuleName_ReturnsProperModuleName_foo_bar() {
        final DlangPsiFileImpl dlangFile = virtualDlangPsiFile("foo-bar.d", "module foo_bar;");

        assertEquals("foo-bar.d", dlangFile.getName());
        assertEquals("foo_bar", dlangFile.getModuleName());
        assertEquals("foo_bar", dlangFile.getFullyQualifiedModuleName());
        assertNull(dlangFile.getPackageName());
    }

    // https://dlang.org/spec/module.html
    @Test
    public void testGetModuleName_ReturnsProperModuleName_deprecated_foo() {
        final DlangPsiFileImpl dlangFile = virtualDlangPsiFile("foo-bar.d", "deprecated module foo;");

        assertEquals("foo-bar.d", dlangFile.getName());
        assertEquals("foo", dlangFile.getModuleName());
        assertEquals("foo", dlangFile.getFullyQualifiedModuleName());
        assertNull(dlangFile.getPackageName());
    }

    // https://dlang.org/spec/module.html
    @Test
    public void testGetModuleName_ReturnsProperModuleName_deprecated_foo_with_message() {
        final DlangPsiFileImpl dlangFile = virtualDlangPsiFile("foo-bar.d", "deprecated(\"Please use foo2 instead.\") module foo;");

        assertEquals("foo-bar.d", dlangFile.getName());
        assertEquals("foo", dlangFile.getModuleName());
        assertEquals("foo", dlangFile.getFullyQualifiedModuleName());
        assertNull(dlangFile.getPackageName());
    }

    /**
     * This test is to handle an invalid module name.
     * The file names for packages and modules should be composed only of
     * ASCII lower case letters, digits, and underscores, and should not be a Keyword.
     *
     * <a href="https://dlang.org/spec/module.html">module</a>
     */
    @Test
    public void testGetModuleName_HandlesInvalidModuleName() {
        final DlangPsiFileImpl dlangFile = virtualDlangPsiFile("foo-bar.d", "");

        assertEquals("foo-bar.d", dlangFile.getName());
        assertNotNull("Should return file name without extension", dlangFile.getModuleName());
        assertEquals("foo-bar", dlangFile.getModuleName());

        // todo: would probably be worth having a way for DlangFile to indicate if the package/module name is valid or not

        assertEquals("foo-bar", dlangFile.getFullyQualifiedModuleName());
        assertNull(dlangFile.getPackageName());
    }

}
