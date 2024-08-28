package io.github.intellij.dlanguage.utils

import io.github.intellij.dlanguage.utils.DUtil.isValidDlangFileName
import junit.framework.TestCase

/**
 * @author Samael Bate (singingbush)
 * created on 27/10/2019
 */
class DUtilTest : TestCase() {
    fun testIsValidDlangFileName() {
        assertFalse("should handle empty string", isValidDlangFileName(""))

        assertTrue("should handle valid .d file", isValidDlangFileName("myfile.d"))
        assertTrue("should handle valid .di file", isValidDlangFileName("myfile.di"))

        assertTrue("should handle underscore in source file", isValidDlangFileName("my_file.d"))

        assertFalse("Ignore other file types", isValidDlangFileName("myfile.da"))
        assertFalse("Ignore other file types", isValidDlangFileName("myfile.exe"))
        assertFalse("Ignore other file types", isValidDlangFileName("myfile.c"))

        assertFalse("Ignore invalid characters", isValidDlangFileName("my#f'le.d"))
    }
}
