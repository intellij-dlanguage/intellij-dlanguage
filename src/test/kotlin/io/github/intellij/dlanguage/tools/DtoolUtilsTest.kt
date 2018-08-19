package io.github.intellij.dlanguage.tools

import com.intellij.util.text.SemVer
import io.github.intellij.dlanguage.tools.DtoolUtils.Companion.versionPredates
import io.github.intellij.dlanguage.tools.DtoolUtils.Companion.isSemVer
import io.github.intellij.dlanguage.tools.DtoolUtils.Companion.toSemVer
import org.junit.Test

import org.junit.Assert.*

/**
 * @author Samael Bate (singingbush)
 * created on 19/08/18
 */
class DtoolUtilsTest {

    @Test
    fun testVersionPredates() {
        assertFalse(null.versionPredates(null))
        assertFalse(null.versionPredates(""))
        assertFalse(null.versionPredates("1.0.0"))

        assertFalse("".versionPredates(null))
        assertFalse("".versionPredates(""))
        assertFalse("".versionPredates("1.0.0"))

        assertFalse("2.1".versionPredates("2.0"))
        assertFalse("2.1.1".versionPredates("2.0.9"))
        assertFalse("2.1.1".versionPredates("2.1.1"))

        assertTrue("3.5.4".versionPredates("4.0"))
        assertTrue("3.5.4".versionPredates("4.0.1"))
        assertTrue("3.5.4".versionPredates("3.6.1"))
        assertTrue("3.5.4".versionPredates("3.5.5"))
    }

    @Test
    fun testToSemVer() {
        assertNull(null.toSemVer())
        assertNull("".toSemVer())
        assertNull("5135".toSemVer())

        assertEquals("Should handle version string returned by DUB",
            SemVer("DUB version 1.9.0, built on Jun  7 2018", 1, 9, 0),
            "DUB version 1.9.0, built on Jun  7 2018".toSemVer()
        )

        //assertEquals(SemVer("v2", 2, 0, 0), "v2".toSemVer())
        assertEquals(SemVer("1.5", 1, 5, 0), "1.5".toSemVer())
        assertEquals(SemVer("0.1.0", 0, 1, 0), "0.1.0".toSemVer())
        assertEquals(SemVer("v0.1.0", 0, 1, 0), "v0.1.0".toSemVer())
        assertEquals(SemVer("v0.1.1-alpha.0", 0, 1, 1), "v0.1.1-alpha.0".toSemVer())
        assertEquals(SemVer("v1.1.0-beta", 1, 1, 0), "v1.1.0-beta".toSemVer())
        assertEquals(SemVer("v0.3.4-rc1", 0, 3, 4), "v0.3.4-rc1".toSemVer())
    }

    @Test
    fun testIsSemVer() {
        assertFalse(null.isSemVer())
        assertFalse("".isSemVer())

        assertTrue("0.1.0".isSemVer())
        assertTrue("v0.1.0".isSemVer())
        assertTrue("v0.1.1-alpha.0".isSemVer())
        assertTrue("v1.1.0-beta".isSemVer())
        assertTrue("v0.3.4-rc1".isSemVer())
    }
}
