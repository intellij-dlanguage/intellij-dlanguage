package io.github.intellij.dlanguage.codeinsight.dcd;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.VfsTestUtil;
import com.intellij.util.PathUtil;
import io.github.intellij.dlanguage.DlangSdkType;
import io.github.intellij.dlanguage.LightDlangTestCase;
import org.junit.Test;

import java.io.File;

public class DCDCompletionServerTest extends LightDlangTestCase {

    // this method will be called prior to the test being run and MockSdk is read only
    @Override
    public Sdk getProjectJDK() {
        // Get a tmp folder to be the root of our "fake sdk"
        var tmp = new File(FileUtil.getTempDirectory());
        sdkRoot = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(tmp);
        var phobos = VfsTestUtil.createDir(sdkRoot, "phobos");
        var druntime = VfsTestUtil.createDir(sdkRoot, "druntime");

        // Configure a sdk
        var dlangSdk = DlangSdkType.getInstance();
        var sdk = ProjectJdkTable.getInstance().createSdk("dmd", dlangSdk);
        var sdkModificator = sdk.getSdkModificator();
        sdkModificator.setVersionString("2");
        sdkModificator.addRoot(phobos.getUrl(), OrderRootType.SOURCES);
        sdkModificator.addRoot(druntime.getUrl(), OrderRootType.SOURCES);
        // Ensure it is saved to be detected by the later code
        Application application = ApplicationManager.getApplication();
        Runnable runnable = sdkModificator::commitChanges;
        if (application.isDispatchThread()) {
            application.runWriteAction(runnable);
        } else {
            application.invokeAndWait(() -> application.runWriteAction(runnable));
        }
        return sdk;
    }

    private DCDCompletionServer dcd;
    private VirtualFile sdkRoot;

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
            PathUtil.toSystemDependentName(sdkRoot.getPath() + "/phobos"),
            PathUtil.toSystemDependentName(sdkRoot.getPath() + "/druntime"));

        assertEquals(expected, cmd.getCommandLineString());
    }
}
