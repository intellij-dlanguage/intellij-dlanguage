package io.github.intellij.dlanguage.codeinsight.dcd;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.util.SystemInfo;
import io.github.intellij.dlanguage.LightDlangTestCase;

public class DCDCompletionServerTest extends LightDlangTestCase {

    private DCDCompletionServer dcd;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.dcd = new DCDCompletionServer(this.getModule());
    }

    public void testBuildDcdCommand() {
        final GeneralCommandLine cmd = this.dcd.buildDcdCommand("dcd-server");

        if(SystemInfo.isWindows) {
            assertEquals("dcd-server --ignoreConfig --logLevel all -I \\src -I \\phobos -I \\druntime", cmd.getCommandLineString());
        } else {
            assertEquals("dcd-server --ignoreConfig --logLevel all -I /src -I /phobos -I /druntime", cmd.getCommandLineString());
        }
    }
}
