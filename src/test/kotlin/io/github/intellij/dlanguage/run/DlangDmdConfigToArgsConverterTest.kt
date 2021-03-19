package io.github.intellij.dlanguage.run

import com.intellij.openapi.util.SystemInfo
import junit.framework.TestCase
import io.github.intellij.dlanguage.run.exception.NoSourcesException
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import io.github.intellij.dlanguage.LightDlangTestCase
import java.io.File

/**
 * @author Samael Bate (singingbush)
 */
class DlangDmdConfigToArgsConverterTest : LightDlangTestCase() {

    private val outputDirArg: String = "-od${File.separatorChar}obj"
    private val outputFile = "light_idea_test_case" + if (SystemInfo.isWindows) ".exe" else ""
    private val outputFileArg: String = "-of${File.separatorChar}$outputFile"

    @Throws(Exception::class)
    fun `test Get DMD Parameters Should output valid args by default`() {
        addFileToModuleSource("myapp.d")
        addFileToModuleSource("othersource.d")

        val dmdConfig = mock(DlangRunDmdConfiguration::class.java)

        val dmdParameters = DlangDmdConfigToArgsConverter.getDmdParameters(dmdConfig, module)

        TestCase.assertEquals(mutableListOf(outputDirArg, outputFileArg, "/src/myapp.d", "/src/othersource.d"), dmdParameters)
    }

    @Throws(Exception::class)
    fun `test Get DMD Parameters building a library`() {
        addFileToModuleSource("myapp.d")

        val dmdConfig = mock(DlangRunDmdConfiguration::class.java)
        `when`(dmdConfig.isLibrary).thenReturn(true)

        val dmdParameters = DlangDmdConfigToArgsConverter.getDmdParameters(dmdConfig, module)

        TestCase.assertEquals(mutableListOf("-lib", outputDirArg, "-of${File.separatorChar}light_idea_test_case.lib", "/src/myapp.d"), dmdParameters)
    }

    @Throws(Exception::class)
    fun `test Get DMD Parameters Should set additional args correctly`() {
        addFileToModuleSource("myapp.d")

        val dmdConfig = mock(DlangRunDmdConfiguration::class.java)
        `when`<Boolean>(dmdConfig.isDebug).thenReturn(true)
        `when`<Boolean>(dmdConfig.isUnitTest).thenReturn(true)
        `when`<Boolean>(dmdConfig.isCoverageAnalysis).thenReturn(true)
        `when`<Boolean>(dmdConfig.isVerbose).thenReturn(true)

        val dmdParameters = DlangDmdConfigToArgsConverter.getDmdParameters(dmdConfig, module)

        TestCase.assertEquals(mutableListOf("-debug", "-unittest", "-cov", "-v", outputDirArg, outputFileArg, "/src/myapp.d"), dmdParameters)
    }

    @Throws(Exception::class)
    fun `test Get DMD Parameters Should throw NoSourcesException when no D src files found`() {
        val config = mock(DlangRunDmdConfiguration::class.java)

        try {
            DlangDmdConfigToArgsConverter.getDmdParameters(config, getModule())
            TestCase.fail("There should be a NoSourcesException if no .d files are in the project")
        } catch (e: NoSourcesException) {
        }
    }
}
