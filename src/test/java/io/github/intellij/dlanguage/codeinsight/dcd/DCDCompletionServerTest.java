package io.github.intellij.dlanguage.codeinsight.dcd;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.testFramework.LightPlatformTestCase;

public class DCDCompletionServerTest extends LightPlatformTestCase {

    private DCDCompletionServer dcd;

    public void testBuildDcdCommand() {
        this.dcd = new DCDCompletionServer(this.getModule());

        final GeneralCommandLine cmd = this.dcd.buildDcdCommand("dcd-server");

        assertEquals("dcd-server -I /src", cmd.getCommandLineString());
    }
}
