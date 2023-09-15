package io.github.intellij.dlanguage.run

import com.intellij.psi.search.GlobalSearchScope
import com.intellij.testFramework.LightPlatformTestCase
import junit.framework.TestCase

class DmdBuildStackTraceFilterTest : LightPlatformTestCase() {
    @Throws(Exception::class)
    fun `test line match correctly matching`() {
        TestCase.assertNotNull(DmdConsoleFilter.pattern.find("/home/user/project/source/main.d(135): Error: declaration `main.main.x` is already defined"))
        TestCase.assertNotNull(DmdConsoleFilter.pattern.find("/home/user/project/source/main.d(130):        `variable` `x` is defined here"))
    }

    fun `test find correct elements`() {
        val found = DmdConsoleFilter.pattern.find("/home/user/project/source/main.d(135): Error: declaration `main.main.x` is already defined")!!
        TestCase.assertEquals(3, found.groups.size)
        TestCase.assertEquals("/home/user/project/source/main.d".length, found.groups[1]!!.range.last + 1)
        TestCase.assertEquals("135", found.groupValues[2])
    }

    fun `test apply filter`() {
        val line = "/home/user/project/source/main.d(135): Error: declaration `main.main.x` is already defined"
        assertNoThrowable{DmdConsoleFilter(project, GlobalSearchScope.allScope(project)).applyFilter(line, line.length)}
    }
}
