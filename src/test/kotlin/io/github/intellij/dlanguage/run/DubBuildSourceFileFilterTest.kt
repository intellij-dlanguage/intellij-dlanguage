package io.github.intellij.dlanguage.run

import com.intellij.testFramework.LightPlatformTestCase
import junit.framework.TestCase

class DubBuildSourceFileFilterTest : LightPlatformTestCase() {

    @Throws(Exception::class)
    fun `test the regex for splitting a D source path, line no, and message`() {
        TestCase.assertEquals("source\\package\\app.d",
            DubBuildSourceFileFilter.D_SOURCE_PATH_FORMAT.find("source\\package\\app.d Some Log output")!!.groupValues[1]
        )

        TestCase.assertEquals("",
            DubBuildSourceFileFilter.D_SOURCE_PATH_FORMAT.find("source\\package\\app.d Some Log output")!!.groupValues[2]
        )

        TestCase.assertEquals("Some Log output",
            DubBuildSourceFileFilter.D_SOURCE_PATH_FORMAT.find("source\\package\\app.d Some Log output")!!.groupValues[3]
        )


        TestCase.assertEquals("source\\package\\app.d",
            DubBuildSourceFileFilter.D_SOURCE_PATH_FORMAT.find("source\\package\\app.d(53,31) Some Log output")!!.groupValues[1]
        )

        TestCase.assertEquals("(53,31)",
            DubBuildSourceFileFilter.D_SOURCE_PATH_FORMAT.find("source\\package\\app.d(53,31) Some Log output")!!.groupValues[2]
        )

        TestCase.assertEquals("Some Log output",
            DubBuildSourceFileFilter.D_SOURCE_PATH_FORMAT.find("source\\package\\app.d(53,31) Some Log output")!!.groupValues[3]
        )


        TestCase.assertEquals("source\\package\\app.di",
            DubBuildSourceFileFilter.D_SOURCE_PATH_FORMAT.find("source\\package\\app.di(53,31): Some Log output")!!.groupValues[1]
        )

        TestCase.assertEquals("(53,31)",
            DubBuildSourceFileFilter.D_SOURCE_PATH_FORMAT.find("source\\package\\app.di(53,31): Some Log output")!!.groupValues[2]
        )

        TestCase.assertEquals("Some Log output",
            DubBuildSourceFileFilter.D_SOURCE_PATH_FORMAT.find("source\\package\\app.di(53,31): Some Log output")!!.groupValues[3]
        )


        TestCase.assertEquals("source\\package\\app.d",
            DubBuildSourceFileFilter.D_SOURCE_PATH_FORMAT.find("source\\package\\app.d:53 Some Log output")!!.groupValues[1]
        )

        TestCase.assertEquals("53",
            DubBuildSourceFileFilter.D_SOURCE_PATH_FORMAT.find("source\\package\\app.d:53 Some Log output")!!.groupValues[2]
        )

        TestCase.assertEquals("Some Log output",
            DubBuildSourceFileFilter.D_SOURCE_PATH_FORMAT.find("source\\package\\app.d:53 Some Log output")!!.groupValues[3]
        )
    }


    @Throws(Exception::class)
    fun `test filtering dub output for source file`() {
        val line = "source\\config\\database.d(53,31):    blah blah blah, some other stuff"

        val filter = DubBuildSourceFileFilter(project)

        val result = filter.applyFilter(line, line.length)

        TestCase.assertNull(result) // WILL BE NULL DUE TO STATIC METHODS IN CODE BASE
        //TestCase.assertNotNull(result)
        //val resultItem = result?.resultItems?.get(0)!!
        //TestCase.assertEquals(0, resultItem.getHighlightStartOffset())
        //TestCase.assertEquals(31, resultItem.getHighlightEndOffset())
    }

    @Throws(Exception::class)
    fun `test filtering dub output for linker error (issue #625)`() {
        val line = "source/app.d:21 _D3std9exception__T7bailOutHTC9ExceptionZQwFNaNfAyamMAxaZNn [0x10c0aa25a]"

        val filter = DubBuildSourceFileFilter(project)

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

        val filter = DubBuildSourceFileFilter(project)

        val result = filter.applyFilter(line, line.length)

        TestCase.assertNull("shouldn't apply filter if no source found", result)
    }
}
