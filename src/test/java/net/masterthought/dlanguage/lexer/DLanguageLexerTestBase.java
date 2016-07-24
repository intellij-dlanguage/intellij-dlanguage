package net.masterthought.dlanguage.lexer;

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
import org.jetbrains.annotations.NonNls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class DLanguageLexerTestBase extends LexerTestCase {

    private final String srcPath = getDirPath() + File.separator + "lexer";
    private final String myExpectPath;

    public DLanguageLexerTestBase(String expectPath) {
        super();
        myExpectPath = getDirPath() + File.separator + expectPath;
    }

    public void doTest(boolean checkResult, boolean shouldPass) {
        String fileName = getTestName(false) + ".d";
        String text = "";
        try {
            text = loadFile(fileName);
        } catch (IOException | URISyntaxException e) {
            fail("can't load file " + fileName + ": " + e.getMessage());
        }
        String result = printTokens(text, 0);
        try {
            doCheckResult(myExpectPath + File.separator + "expected",
                    getTestName(false) + ".txt", result);
        } catch (IOException e) {
            fail("Unexpected IO Exception: " + e.getMessage());
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected Lexer createLexer() {
        return new FlexAdapter(new DLanguageLexer(null));
    }

    @Override
    protected String getDirPath() {
        return "gold";
    }

    /**
     * Loads the test data file from the right place.
     */
    private String loadFile(@NonNls @TestDataFile String name) throws IOException, URISyntaxException {
        return doLoadFile(srcPath, name);
    }

    private String doLoadFile(final String myFullDataPath, final String name) throws IOException, URISyntaxException {
        final URI resource = this.getClass().getClassLoader().getResource(String.format("%s/%s", myFullDataPath, name)).toURI();
        String text = FileUtil.loadFile(new File(resource), CharsetToolkit.UTF8).trim();
        text = StringUtil.convertLineSeparators(text);
        return text;
    }

    /**
     * Check the result against a plain text file. Creates file if missing.
     * Avoids the default sandboxing in IntelliJ.
     */
    private void doCheckResult(String fullPath, String targetDataName, String text) throws IOException {
        text = text.trim();
        String expectedFileName = fullPath + File.separator + targetDataName;
        if (OVERWRITE_TESTDATA) {
            VfsTestUtil.overwriteTestData(expectedFileName, text);
            System.out.println("File " + expectedFileName + " created.");
        }
        try {
            String expectedText = doLoadFile(fullPath, targetDataName);
            if (!Comparing.equal(expectedText, text)) {
                throw new FileComparisonFailure(targetDataName, expectedText, text, expectedFileName);
            }
        } catch (URISyntaxException | IOException e) {
            VfsTestUtil.overwriteTestData(expectedFileName, text);
            fail("No output text found. File " + expectedFileName + " created.");
        }
    }
}


