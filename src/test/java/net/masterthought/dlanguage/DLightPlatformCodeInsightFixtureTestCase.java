package net.masterthought.dlanguage;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.testFramework.TestDataFile;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NonNls;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Lightweight test case base.
 */
public abstract class DLightPlatformCodeInsightFixtureTestCase extends LightPlatformCodeInsightFixtureTestCase {
    private final String srcPath;
    private final String expectPath;

    /**
     * Sets the expected input and outputs and calls the constructor of the parent.
     *
     * @param srcName    Directory name of test inputs.
     * @param expectName Directory name of expected outputs.
     */
    protected DLightPlatformCodeInsightFixtureTestCase(String srcName, String expectName) {
        super();
        srcPath = String.format("%s/%s", getDirPath(), srcName);
        expectPath = String.format("%s/%s", getDirPath(), expectName);
    }

    /**
     * Base path to the test files.
     */
    protected static String getDirPath() {
        return "gold";
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected String getTestDataPath() {
        return srcPath;
    }

    protected String getTestDataPath(String... names) {
        return this.getClass().getClassLoader().getResource(String.format("%s/%s", srcPath, StringUtil.join(names, "/"))).getPath().replace("/C:", "");
    }

    /**
     * Loads the test data file from the right place.
     */
    protected String loadFile(@NonNls @TestDataFile String name) throws IOException, URISyntaxException {
        return doLoadFile(srcPath, name);
    }

    private String doLoadFile(final String myFullDataPath, final String name) throws IOException, URISyntaxException {
        final URI resource = this.getClass().getClassLoader().getResource(String.format("%s/%s", myFullDataPath, name)).toURI();
        String text = FileUtil.loadFile(new File(resource), CharsetToolkit.UTF8).trim();
        text = StringUtil.convertLineSeparators(text);
        return text;
    }

    protected void setUpProjectSdk() {
        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
                Sdk sdk = getProjectDescriptor().getSdk();
                ProjectJdkTable.getInstance().addJdk(sdk);
                ProjectRootManager.getInstance(myFixture.getProject()).setProjectSdk(sdk);
            }
        });
    }
}
