package io.github.intellij.dlanguage.annotator

import io.github.intellij.dlanguage.LightDlangTestCase
import io.github.intellij.dlanguage.highlighting.annotation.external.LinterHelper
import org.junit.Test

class LinterHelperTest : LightDlangTestCase() {

    @Test
    fun `test Valid Range Should Highlight Semicolon`() {
        val dlangFile = virtualDlangPsiFile("myapp.d", "module ;")

        val range = LinterHelper.calculateTextRange(dlangFile, 1, 8)
        assertNotNull(range)
        assertEquals(1, range!!.length)
        assertEquals(7, range.startOffset)
    }

    @Test
    fun `test Valid Range Highlight Out Of File`() {
        val dlangFile = virtualDlangPsiFile("myapp.d", "module ;")

        val range = LinterHelper.calculateTextRange(dlangFile, 2, 12)
        assertNotNull(range)
        assertEquals(0, range!!.length)
        assertEquals(8, range.startOffset)
    }
}
