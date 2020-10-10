package io.github.intellij.dlanguage.psi;

import com.intellij.mock.MockPsiManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.SingleRootFileViewProvider;
import com.intellij.testFramework.LightPlatformTestCase;
import com.intellij.testFramework.LightVirtualFile;
import io.github.intellij.dlanguage.DLanguage;

/**
 * @author Samael Bate (singingbush)
 * created on 20/10/18
 */
public class DlangFileTest extends LightPlatformTestCase {

    /**
     * D module names are, by default, the file name with the path and extension stripped off.
     * https://dlang.org/spec/module.html
     */
    public void testGetModuleName_shouldBeFilenameWithoutExtension() {
        final DlangFile dlangFile = lightDlangPsiFile("myapp.d", "// no module defined in source");

        assertEquals("myapp", dlangFile.getModuleName());
    }

    /**
     * D module names are, by default, the file name with the path and extension stripped off.
     * https://dlang.org/spec/module.html
     */
    public void testGetModuleName_HonoursTheNameDefinedInSource() {
        final DlangFile dlangFile = lightDlangPsiFile("myapp.d", "module realname;");

        assertEquals("realname", dlangFile.getModuleName());
    }

    /**
     * https://dlang.org/spec/module.html
     */
    public void testGetModuleName_ReturnsProperModuleName_stdio() {
        final DlangFile dlangFile = lightDlangPsiFile(
            "stdio.d",
            "module c.stdio; // module stdio in the c package"
        );

        assertEquals("stdio", dlangFile.getModuleName()); // is this right?

        final String fullyQualifiedModuleName = dlangFile.getFullyQualifiedModuleName();
        assertNotNull(fullyQualifiedModuleName);
        assertEquals("c.stdio", fullyQualifiedModuleName);
    }

    // https://dlang.org/spec/module.html
    public void testGetModuleName_ReturnsProperModuleName_foo_bar() {
        final DlangFile dlangFile = lightDlangPsiFile("foo-bar.d", "module foo_bar;");

        assertEquals("foo_bar", dlangFile.getModuleName());
        assertEquals("foo_bar", dlangFile.getFullyQualifiedModuleName());
    }

    // https://dlang.org/spec/module.html
    public void testGetModuleName_ReturnsProperModuleName_deprecated_foo() {
        final DlangFile dlangFile = lightDlangPsiFile("foo-bar.d", "deprecated module foo;");

        assertEquals("foo", dlangFile.getModuleName());
        assertEquals("foo", dlangFile.getFullyQualifiedModuleName());
    }

    // https://dlang.org/spec/module.html
    public void testGetModuleName_ReturnsProperModuleName_deprecated_foo_with_message() {
        final DlangFile dlangFile = lightDlangPsiFile("foo-bar.d", "deprecated(\"Please use foo2 instead.\") module foo;");

        assertEquals("foo", dlangFile.getModuleName());
        assertEquals("foo", dlangFile.getFullyQualifiedModuleName());
    }

    /**
     * This test is to handle an invalid module name.
     * The file names for packages and modules should be composed only of
     * ASCII lower case letters, digits, and underscores, and should not be a Keyword.
     *
     * https://dlang.org/spec/module.html
     */
    public void testGetModuleName_HandlesInvalidModuleName() {
        final DlangFile dlangFile = lightDlangPsiFile("foo-bar.d", "");

        assertNotNull("Should return file name without extension", dlangFile.getModuleName());
        assertEquals("foo-bar", dlangFile.getModuleName());

        // todo: would probably be worth having a way for DlangFile to indicate if the package/module name is valid or not

        // todo: assertEquals("foo-bar", dlangFile.getFullyQualifiedModuleName());
    }

    /**
     * Use this method to help simplify the creation of test data for these unit tests
     *
     * @param filename the filename, including extension, of a D source file
     * @param content the file content (source code)
     * @return a Dlang Psi File from a single LightVirtualFile (In-memory implementation of {@link VirtualFile})
     */
    private DlangFile lightDlangPsiFile(final String filename, final String content) {
        final FileViewProvider provider = new SingleRootFileViewProvider(
            new MockPsiManager(getProject()),
            new LightVirtualFile(filename, DLanguage.INSTANCE, content)
        );

        return new DlangFile(provider);
    }
}
