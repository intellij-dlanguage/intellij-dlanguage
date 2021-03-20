package io.github.intellij.dlanguage.codeinsight.dcd;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.ide.util.PropertiesComponent;
import io.github.intellij.dlanguage.LightDlangTestCase;
import io.github.intellij.dlanguage.codeinsight.dcd.completions.Completion;
import io.github.intellij.dlanguage.psi.DlangFile;
import io.github.intellij.dlanguage.settings.ToolKey;

import java.nio.file.Paths;
import java.util.List;

/**
 * @author Samael Bate (singingbush)
 * created on 17/09/2020
 */
public class DCDCompletionClientTest extends LightDlangTestCase {

    private DCDCompletionClient dcdClient;

    public void setUp() throws Exception {
        super.setUp();

        this.dcdClient = new DCDCompletionClient();
    }

    public void testBuildDcdCommand() {
        final DlangFile sourceFile = super.lightDlangPsiFile("app.d", "");
        PropertiesComponent.getInstance().setValue(ToolKey.DCD_CLIENT_KEY.getFlagsKey(), null);

        final GeneralCommandLine commandLine = dcdClient.buildDcdCommand("dcd-client", 33, sourceFile);

        assertNotNull(commandLine);
        assertEquals(Paths.get(sourceFile.getProject().getBasePath()).toString(), commandLine.getWorkDirectory().getPath());
        assertEquals("dcd-client -c 33", commandLine.getCommandLineString());
    }

    public void testBuildDcdCommandWithDcdParams() {
        final DlangFile sourceFile = super.lightDlangPsiFile("app.d", "");
        PropertiesComponent.getInstance().setValue(ToolKey.DCD_CLIENT_KEY.getFlagsKey(), "one,\\test directory\\fileTwo");

        final GeneralCommandLine commandLine = dcdClient.buildDcdCommand("dcd-client", 0, sourceFile);

        assertNotNull(commandLine);
        assertEquals(Paths.get(sourceFile.getProject().getBasePath()).toString(), commandLine.getWorkDirectory().getPath());
        assertEquals("dcd-client -c 0 -I one -I \"\\test directory\\fileTwo\"", commandLine.getCommandLineString());
    }

    public void testProcessDcdOutput_EmptyString() {
        final List<Completion> completions = dcdClient.processDcdOutput("");
        assertNotNull(completions);
        assertEquals(0, completions.size());
    }

    public void testProcessDcdOutput_Example1() {
        final String output = "identifiers\n" +
            "parts\tv\n" +
            "name\tv\n" +
            "location\tv\n" +
            "qualifier\tv\n" +
            "kind\tv\n" +
            "type\tv\n" +
            "resolvedType\tv\n" +
            "calltip\tv\n" +
            "getPartByName\tf";

        final List<Completion> completions = dcdClient.processDcdOutput(output);
        assertEquals(9, completions.size());
    }

    public void testProcessDcdOutput_Example2() {
        final String output = "identifiers\n" +
            "alignof\tk\n" +
            "dup\tk\n" +
            "idup\tk\n" +
            "init\tk\n" +
            "length\tk\n" +
            "mangleof\tk\n" +
            "ptr\tk\n" +
            "sizeof\tk\n" +
            "stringof\tk";

        final List<Completion> completions = dcdClient.processDcdOutput(output);
        assertEquals(9, completions.size());
    }
}
