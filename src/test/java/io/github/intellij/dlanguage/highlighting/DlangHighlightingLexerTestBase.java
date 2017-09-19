package io.github.intellij.dlanguage.highlighting;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.rt.execution.junit.FileComparisonFailure;
import com.intellij.testFramework.LexerTestCase;
import com.intellij.testFramework.TestDataFile;
import com.intellij.testFramework.VfsTestUtil;
import io.github.intellij.dlanguage.DlangHighlightingLexer;
import io.github.intellij.dlanguage.DlangHighlightingLexer;
import org.jetbrains.annotations.NonNls;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class DlangHighlightingLexerTestBase extends LexerTestCase {

    private final String srcPath = getDirPath() + File.separator + "highlighting";
    private final String myExpectPath;

    DlangHighlightingLexerTestBase(final String expectPath) {
        super();
        myExpectPath = getDirPath() + File.separator + expectPath;
    }

    public void doTest(final boolean checkResult, final boolean shouldPass) {
        final String fileName = getTestName(false) + ".d";
        String text = "";
        try {
            text = loadFile(fileName);
        } catch (IOException | URISyntaxException e) {
            fail("can't load file " + fileName + ": " + e.getMessage());
        }
        final String result = printTokens(text, 0);
        try {
            doCheckResult(myExpectPath + File.separator + "expected",
                getTestName(false) + ".txt", result);
        } catch (final IOException e) {
            fail("Unexpected IO Exception: " + e.getMessage());
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected Lexer createLexer() {
        return new FlexAdapter(new DlangHighlightingLexer(null));
    }

    @Override
    protected String getDirPath() {
        return "gold";
    }

    /**
     * Loads the test data file from the right place.
     */
    protected String loadFile(@NonNls @TestDataFile final String name) throws IOException, URISyntaxException {
        return doLoadFile(srcPath, name);
    }

    private String doLoadFile(final String myFullDataPath, final String name) throws IOException, URISyntaxException {
        final URI resource;
        try {
            resource = this.getClass().getClassLoader().getResource(String.format("%s/%s", myFullDataPath, name)).toURI();
        } catch (final NullPointerException e) {
            throw new IOException(e);
        }
        String text = FileUtil.loadFile(new File(resource), CharsetToolkit.UTF8).trim();
        text = StringUtil.convertLineSeparators(text);
        return text;
    }

    /**
     * Check the result against a plain text file. Creates file if missing.
     * Avoids the default sandboxing in IntelliJ.
     */
    public void doCheckResult(final String fullPath, final String targetDataName, String text) throws IOException {
        text = text.trim();
        final String expectedFileName = fullPath + File.separator + targetDataName;
        if (OVERWRITE_TESTDATA) {
            VfsTestUtil.overwriteTestData(expectedFileName, text);
            System.out.println("File " + expectedFileName + " created.");
        }
        try {
            final String expectedText = doLoadFile(fullPath, targetDataName);
            if (!Comparing.equal(expectedText, text)) {
                throw new FileComparisonFailure(targetDataName, expectedText, text, expectedFileName);
            }
        } catch (URISyntaxException | IOException e) {
            VfsTestUtil.overwriteTestData(expectedFileName, text);
            fail("No output text found. File " + expectedFileName + " created.");
        }
    }
}


