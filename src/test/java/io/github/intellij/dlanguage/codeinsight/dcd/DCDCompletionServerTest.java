package io.github.intellij.dlanguage.codeinsight.dcd;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.util.PathUtil;
import io.github.intellij.dlanguage.LightDlangTestCase;
import org.junit.Test;

public class DCDCompletionServerTest extends LightDlangTestCase {

    private DCDCompletionServer dcd;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.dcd = new DCDCompletionServer(this.getModule());
    }

    @Test
    public void testBuildDcdCommand() {
        final GeneralCommandLine cmd = this.dcd.buildDcdCommand("dcd-server");

        final String expected = String.format("dcd-server --ignoreConfig --logLevel all -I %s -I %s -I %s",
            PathUtil.toSystemDependentName("/src"),
            PathUtil.toSystemDependentName("/phobos"),
            PathUtil.toSystemDependentName("/druntime"));

        assertEquals(expected, cmd.getCommandLineString());
    }
}
