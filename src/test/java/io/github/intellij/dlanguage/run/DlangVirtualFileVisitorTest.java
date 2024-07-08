package io.github.intellij.dlanguage.run;

import com.intellij.mock.MockVirtualFile;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import io.github.intellij.dlanguage.LightDlangTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Samael Bate (singingbush)
 * created on 19/03/2021
 */
@RunWith(JUnit4.class)
public class DlangVirtualFileVisitorTest extends LightDlangTestCase {

    @Test
    public void testVisitFile_Individual_Files() {
        final DlangVirtualFileVisitor visitor = new DlangVirtualFileVisitor(null);

        assertEquals(VirtualFileVisitor.SKIP_CHILDREN, visitor.visitFileEx(new MockVirtualFile("source.d")));
        assertEquals(VirtualFileVisitor.SKIP_CHILDREN, visitor.visitFileEx(new MockVirtualFile("source.di")));
        assertEquals(VirtualFileVisitor.SKIP_CHILDREN, visitor.visitFileEx(new MockVirtualFile("source.java")));

        assertEquals(2, visitor.getDlangSources().size());
        assertFalse(visitor.getDlangSources().stream().anyMatch(f -> f.contains("source.java")));
    }

    @Test
    public void testVisitFile_Recursively() {
        final DlangVirtualFileVisitor visitor = new DlangVirtualFileVisitor(null);

        final MockVirtualFile sourceDir = new MockVirtualFile(true, "myfolder");
        sourceDir.addChild(new MockVirtualFile("app.d"));
        sourceDir.addChild(new MockVirtualFile("my-impl.d"));
        sourceDir.addChild(new MockVirtualFile("my-interface.di"));
        sourceDir.addChild(new MockVirtualFile("README.md"));

        // check mock directory with sources
        assertEquals(VirtualFileVisitor.CONTINUE, VfsUtilCore.visitChildrenRecursively(
            sourceDir,
            visitor
        ));
        assertEquals(3, visitor.getDlangSources().size());
    }

    @Test
    public void testVisitFile_Recursively_With_exclusions() {
        final MockVirtualFile projectRoot = new MockVirtualFile(true, "projectRoot");

        // setup sources that we want to scan
        final MockVirtualFile sourceDir = new MockVirtualFile(true, "source");
        projectRoot.addChild(sourceDir);
        sourceDir.addChild(new MockVirtualFile("app.d"));
        sourceDir.addChild(new MockVirtualFile("my-impl.d"));
        sourceDir.addChild(new MockVirtualFile("my-interface.di"));
        sourceDir.addChild(new MockVirtualFile("README.md"));

        // setup sources that we want to exclude
        final MockVirtualFile examplesDir = new MockVirtualFile(true, "examples");
        projectRoot.addChild(examplesDir);
        examplesDir.addChild(new MockVirtualFile("example.d"));

        final DlangVirtualFileVisitor visitor = new DlangVirtualFileVisitor(new VirtualFile[] { examplesDir });

        assertEquals(VirtualFileVisitor.CONTINUE, VfsUtilCore.visitChildrenRecursively(
            projectRoot,
            visitor
        ));
        assertEquals(3, visitor.getDlangSources().size());
    }
}
