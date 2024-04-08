package io.github.intellij.dlanguage.run;

import com.intellij.mock.MockVirtualFile;
import com.intellij.testFramework.UsefulTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Samael Bate (singingbush)
 * created on 19/03/2021
 */
@RunWith(JUnit4.class)
public class DlangVirtualFileVisitorTest extends UsefulTestCase {

    @Test
    public void testVisitFile() {
        final DlangVirtualFileVisitor visitor = new DlangVirtualFileVisitor(null);

        visitor.visitFile(new MockVirtualFile("source.d"));
        visitor.visitFile(new MockVirtualFile("source.di"));
        visitor.visitFile(new MockVirtualFile("source.java"));

        assertEquals(2, visitor.getDlangSources().size());
        assertFalse(visitor.getDlangSources().stream().anyMatch(f -> f.contains("source.java")));
    }
}
