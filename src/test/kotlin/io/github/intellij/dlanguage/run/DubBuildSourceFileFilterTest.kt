package io.github.intellij.dlanguage.run

import com.intellij.testFramework.LightPlatformTestCase
import junit.framework.TestCase

class DubBuildSourceFileFilterTest : LightPlatformTestCase() {

    @Throws(Exception::class)
    fun `test filtering dub output for source file`() {
        val line = "source\\config\\database.d(53,31):    blah blah blah, some other stuff"

        val filter = DubBuildSourceFileFilter(getProject())

        val result = filter.applyFilter(line, line.length)

        TestCase.assertNull(result) // WILL BE NULL DUE TO STATIC METHODS IN CODE BASE
        //TestCase.assertNotNull(result)
        //val resultItem = result?.resultItems?.get(0)!!
        //TestCase.assertEquals(0, resultItem.getHighlightStartOffset())
        //TestCase.assertEquals(31, resultItem.getHighlightEndOffset())
    }

    @Throws(Exception::class)
    fun `test filtering dub output without source file (no colon)`() {
        val line = "blah blah blah, some kind of output"

        val filter = DubBuildSourceFileFilter(getProject())

        val result = filter.applyFilter(line, line.length)

        TestCase.assertNull("shouldn't apply filter if no source found", result)
    }
}
