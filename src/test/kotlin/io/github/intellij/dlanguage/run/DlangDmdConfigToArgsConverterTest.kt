package io.github.intellij.dlanguage.run

import com.intellij.openapi.util.SystemInfo
import io.github.intellij.dlanguage.LightDlangTestCase
import io.github.intellij.dlanguage.run.exception.NoSourcesException
import junit.framework.TestCase
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.io.File

/**
 * @author Samael Bate (singingbush)
 */
class DlangDmdConfigToArgsConverterTest : LightDlangTestCase() {

    private lateinit var outputDirArg: String
    private lateinit var outputFile: String
    private lateinit var outputFileArg: String

    override fun setUp() {
        super.setUp()

        // these need to be set within setUp so that the project basePath is not null
        this.outputDirArg = "-od" + File(project.basePath, "obj").absolutePath
        this.outputFile = "light_idea_test_case" + if (SystemInfo.isWindows) ".exe" else ""
        this.outputFileArg = "-of" + File(project.basePath, "$outputFile").absolutePath
    }

    @Test
    @Throws(Exception::class)
    fun `test Get DMD Parameters Should output valid args by default`() {
        addFileToModuleSource("myapp.d")
        addFileToModuleSource("othersource.di")

        val dmdConfig = mock(DlangRunDmdConfiguration::class.java)

        val dmdParameters = DlangDmdConfigToArgsConverter.getDmdParameters(dmdConfig, module)

        TestCase.assertEquals(mutableListOf(outputDirArg, outputFileArg, "/src/myapp.d", "/src/othersource.di"), dmdParameters)
    }

    @Test
    @Throws(Exception::class)
    fun `test Get DMD Parameters building a library`() {
        addFileToModuleSource("myapp.d")

        val dmdConfig = mock(DlangRunDmdConfiguration::class.java)
        `when`(dmdConfig.isLibrary).thenReturn(true)

        val dmdParameters = DlangDmdConfigToArgsConverter.getDmdParameters(dmdConfig, module)

        TestCase.assertEquals(mutableListOf("-lib", outputDirArg, outputFileArg.removeSuffix(".exe").plus(".lib"), "/src/myapp.d"), dmdParameters)
    }

    @Test
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

    @Test
    @Throws(Exception::class)
    fun `test Get DMD Parameters Should throw NoSourcesException when no D src files found`() {
        val config = mock(DlangRunDmdConfiguration::class.java)

        assertThrows(NoSourcesException::class.java) {
            DlangDmdConfigToArgsConverter.getDmdParameters(config, module)
        }
    }
}
