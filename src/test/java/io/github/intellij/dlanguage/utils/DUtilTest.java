package io.github.intellij.dlanguage.utils;

import junit.framework.TestCase;

/**
 * @author Samael Bate (singingbush)
 * created on 27/10/2019
 */
public class DUtilTest extends TestCase {

    public void testIsValidDlangFileName() {
        assertFalse("should handle empty string", DUtil.isValidDlangFileName(""));

        assertTrue("should handle valid .d file", DUtil.isValidDlangFileName("myfile.d"));
        assertTrue("should handle valid .di file", DUtil.isValidDlangFileName("myfile.di"));

        assertTrue("should handle underscore in source file", DUtil.isValidDlangFileName("my_file.d"));

        assertFalse("Ignore other file types", DUtil.isValidDlangFileName("myfile.da"));
        assertFalse("Ignore other file types", DUtil.isValidDlangFileName("myfile.exe"));
        assertFalse("Ignore other file types", DUtil.isValidDlangFileName("myfile.c"));

        assertFalse("Ignore invalid characters", DUtil.isValidDlangFileName("my#f'le.d"));
    }
}
