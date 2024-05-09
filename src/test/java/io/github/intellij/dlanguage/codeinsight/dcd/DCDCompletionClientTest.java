package io.github.intellij.dlanguage.codeinsight.dcd;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.util.PathUtil;
import io.github.intellij.dlanguage.LightDlangTestCase;
import io.github.intellij.dlanguage.codeinsight.dcd.completions.Completion;
import io.github.intellij.dlanguage.psi.DlangPsiFile;
import io.github.intellij.dlanguage.settings.ToolKey;
import org.junit.Test;

import java.util.List;

/**
 * @author Samael Bate (singingbush)
 * created on 17/09/2020
 */
public class DCDCompletionClientTest extends LightDlangTestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testBuildDcdCommand() {
        final DlangPsiFile sourceFile = super.lightDlangPsiFile("app.d", "");
        PropertiesComponent.getInstance().setValue(ToolKey.DCD_CLIENT_KEY.getFlagsKey(), null);

        final GeneralCommandLine commandLine = DCDCompletionClient.buildDcdCommand("dcd-client", 33, sourceFile);

        assertNotNull(commandLine);
        assertEquals(PathUtil.toSystemDependentName("/src"), commandLine.getWorkDirectory().getPath());
        assertEquals("dcd-client -c 33", commandLine.getCommandLineString());
    }

    @Test
    public void testBuildDcdCommandWithDcdParams() {
        final DlangPsiFile sourceFile = super.lightDlangPsiFile("app.d", "");
        PropertiesComponent.getInstance().setValue(ToolKey.DCD_CLIENT_KEY.getFlagsKey(), "one,\\test directory\\fileTwo");

        final GeneralCommandLine commandLine = DCDCompletionClient.buildDcdCommand("dcd-client", 0, sourceFile);

        assertNotNull(commandLine);
        assertEquals(PathUtil.toSystemDependentName("/src"), commandLine.getWorkDirectory().getPath());

        // The value following the -I should match exactly what has been set on DCD_CLIENT_KEY flags
        assertEquals("dcd-client -c 0 -I one -I \"\\test directory\\fileTwo\"", commandLine.getCommandLineString());
    }

    @Test
    public void testProcessDcdOutput_EmptyString() {
        final List<Completion> completions = DCDCompletionClient.processDcdOutput("");
        assertNotNull(completions);
        assertEquals(0, completions.size());
    }

    @Test
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

        final List<Completion> completions = DCDCompletionClient.processDcdOutput(output);
        assertEquals(9, completions.size());

        assertEquals(8L, completions.stream()
            .filter(c -> "Variable".equals(c.completionType()))
            .count()
        );

        assertEquals(1L, completions.stream()
            .filter(c -> "Function".equals(c.completionType()))
            .count()
        );
    }

    @Test
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

        final List<Completion> completions = DCDCompletionClient.processDcdOutput(output);
        assertEquals(9, completions.size());
    }

    @Test
    public void testProcessDcdOutput_Example3() {
        final String output = "identifiers\n" +
            "SBUF_AUTOEXTEND\te\n" +
            "SBUF_DYNAMIC\te\n" +
            "SBUF_DYNSTRUCT\te\n" +
            "SBUF_FINISHED\te\n" +
            "SBUF_FIXEDLEN\te\n" +
            "SBUF_INSECTION\te\n" +
            "SBUF_USRFLAGMSK\te\n" +
            "SEEK_CUR\te\n" +
            "SEEK_END\te\n" +
            "SEEK_SET\te\n" +
            "SH_DENYNO\te\n" +
            "SH_DENYRD\te\n" +
            "SH_DENYRW\te\n" +
            "SH_DENYWR\te\n" +
            "STDERR_FILENO\te\n" +
            "STDIN_FILENO\te\n" +
            "STDOUT_FILENO\te\n" +
            "SYS_OPEN\te\n" +
            "S_IREAD\te\n" +
            "S_IWRITE\te\n" +
            "StdFileHandle\tg\n" +
            "StdioException\tc\n" +
            "scanf\tl\n" +
            "sdf\tv\n" +
            "selector\ts\n" +
            "setSameMutex\tf\n" +
            "setbuf\tf\n" +
            "setmode\tf\n" +
            "setmode\tl\n" +
            "setvbuf\tf\n" +
            "short\tk\n" +
            "size_t\tl\n" +
            "sizediff_t\tl\n" +
            "snprintf\tl\n" +
            "sopen\tf\n" +
            "sprintf\tl\n" +
            "sscanf\tl\n" +
            "std\tP\n" +
            "stdaux\tv\n" +
            "stderr\tl\n" +
            "stderr\tv\n" +
            "stdin\tl\n" +
            "stdin\tv\n" +
            "stdout\tl\n" +
            "stdout\tv\n" +
            "stdprn\tv\n" +
            "string\tl";

        final List<Completion> completions = DCDCompletionClient.processDcdOutput(output);
        assertEquals(47, completions.size());

        // both "g" and "e" are mapped to Enum
        assertEquals(21L, completions.stream()
            .filter(c -> "Enum".equals(c.completionType()))
            .count()
        );

        assertEquals(11L, completions.stream()
            .filter(c -> "Alias".equals(c.completionType()))
            .count()
        );

        assertEquals(5L, completions.stream()
            .filter(c -> "Function".equals(c.completionType()))
            .count()
        );

        assertEquals(6L, completions.stream()
            .filter(c -> "Variable".equals(c.completionType()))
            .count()
        );
    }
}
