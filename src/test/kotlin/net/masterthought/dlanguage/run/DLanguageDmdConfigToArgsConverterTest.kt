package net.masterthought.dlanguage.run

import com.intellij.testFramework.LightPlatformTestCase
import junit.framework.TestCase
import net.masterthought.dlanguage.run.exception.NoSourcesException
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

/**
 * @author Samael Bate (singingbush)
 */
class DLanguageDmdConfigToArgsConverterTest : LightPlatformTestCase() {

    @Throws(Exception::class)
    fun `test Get DMD Parameters Should throw NoSourcesException when no D src files found`() {
        val config = mock<DLanguageRunDmdConfiguration>(DLanguageRunDmdConfiguration::class.java)
        `when`<Boolean>(config.isDebug).thenReturn(true)
        `when`<Boolean>(config.isUnitTest).thenReturn(true)
        `when`<Boolean>(config.isCoverageAnalysis).thenReturn(true)
        `when`<Boolean>(config.isVerbose).thenReturn(true)

        try {
            DLanguageDmdConfigToArgsConverter.getDmdParameters(config, LightPlatformTestCase.getModule())
            TestCase.fail("There should be a NoSourcesException if no .d files are in the project")
        } catch (e: NoSourcesException) {
        }
    }
}
