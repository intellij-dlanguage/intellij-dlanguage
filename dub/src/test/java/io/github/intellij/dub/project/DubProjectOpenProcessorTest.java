package io.github.intellij.dub.project;

import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.LightPlatform4TestCase;
import com.intellij.testFramework.VfsTestUtil;
import org.junit.Before;
import org.junit.Test;

public class DubProjectOpenProcessorTest extends LightPlatform4TestCase {

    private DubProjectOpenProcessor projectOpenProcessor;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        projectOpenProcessor = new DubProjectOpenProcessor();
    }

    @Test
    public void testCanOpenProjectWithDubJson() {
        final VirtualFile sourcesRoot = ModuleRootManager.getInstance(getModule()).getSourceRoots()[0];

        final VirtualFile folder = VfsTestUtil.createDir(sourcesRoot, "folder");
        final VirtualFile dubFile = VfsTestUtil.createFile(folder, "dub.json", "");

        assertTrue("A dub.json should be able to open a dub project", projectOpenProcessor.canOpenProject(dubFile));
        assertTrue("A directory containing a dub.json should be able to open a dub project", projectOpenProcessor.canOpenProject(folder));
    }

    @Test
    public void testCanOpenProjectWithDubSdl() {
        final VirtualFile sourcesRoot = ModuleRootManager.getInstance(getModule()).getSourceRoots()[0];

        final VirtualFile folder = VfsTestUtil.createDir(sourcesRoot, "folder");
        final VirtualFile dubFile = VfsTestUtil.createFile(folder, "dub.sdl", "");

        assertTrue("A dub.sdl should be able to open a dub project", projectOpenProcessor.canOpenProject(dubFile));
        assertTrue("A directory containing a dub.sdl should be able to open a dub project", projectOpenProcessor.canOpenProject(folder));
    }

    @Test
    public void testCanOpenProjectDirectoryReturnsFalseForNonDubFile() {
        final VirtualFile sourcesRoot = ModuleRootManager.getInstance(getModule()).getSourceRoots()[0];

        final VirtualFile folder = VfsTestUtil.createDir(sourcesRoot, "folder");
        final VirtualFile jsonFile = VfsTestUtil.createFile(folder, "package.json", "");

        assertFalse(projectOpenProcessor.canOpenProject(jsonFile));
        assertFalse(projectOpenProcessor.canOpenProject(folder));
    }
}
