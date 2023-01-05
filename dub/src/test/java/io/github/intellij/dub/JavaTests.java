package io.github.intellij.dub;

import org.junit.Test;

import static org.junit.Assert.*;

public class JavaTests {

    @Test
    public void testJavaInterop() {
        assertNotNull("This should compile if annotation correct", Dub.BALL);
        assertNotNull("This should compile if annotation correct", Dub.SYSTEM_ID);
        assertEquals("dub", Dub.SYSTEM_ID.getReadableName());
    }
}
