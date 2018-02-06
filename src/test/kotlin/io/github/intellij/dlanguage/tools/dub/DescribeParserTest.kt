package io.github.intellij.dlanguage.tools.dub

import com.intellij.testFramework.LightPlatformTestCase
import io.github.intellij.dlanguage.project.DubPackage
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author Samael Bate (singingbush)
 * created on 06/02/18
 */
class DescribeParserTest : LightPlatformTestCase() {

    var parser: DescribeParser = DescribeParserImpl()

    @Throws(Exception::class)
    fun `test parsing the json output of dub describe`() {
        val json = String(Files.readAllBytes(
            Paths.get(javaClass.classLoader.getResource("dub/dubDescribeOutput.json")!!.toURI())
        ))

        val dubProject = parser.parse(json)

        assertEquals("SuperAwesomeApp", dubProject.rootPackageName)
        assertEquals("default", dubProject.configuration)
        assertEquals("debug", dubProject.buildType)
        assertEquals("dmd", dubProject.compiler)
        assertEquals(1, dubProject.architecture.size)
        assertEquals(2, dubProject.platform.size)

        assertDubPackageValid("root package", dubProject.rootPackage)

        assertFalse(
            "The Root package should not be included in the dependencies",
            dubProject.packages.contains(dubProject.rootPackage)
        )

        assertEquals(29, dubProject.packages.size)

        dubProject.packages.forEach { assertDubPackageValid(it.name, it) }
    }

    private fun assertDubPackageValid(name: String, pkg: DubPackage) {
        assertNotBlank("$name Should have name", pkg.name)
        assertNotBlank("$name Should have path", pkg.path)
        assertNotBlank("$name Should have version", pkg.version)

        assertNotNull("$name Should have description", pkg.description)
        assertNotNull("$name Should have copyright", pkg.copyright)
        assertNotNull("$name Should have license", pkg.license)

        assertNotNull("$name Should have dependencies", pkg.dependencies)
        assertNotNull("$name Should have sourcesDirs", pkg.sourcesDirs)
        assertNotEmpty(pkg.sourcesDirs)
        assertNotNull("$name Should have resources", pkg.resources)
        assertNotNull("$name Should have sourceFiles", pkg.sourceFiles)
        assertNotNull("$name Should have stringImportFiles", pkg.stringImportFiles)
    }

    private fun assertNotBlank(message: String, value: String) {
        assertNotNull(message, value)
        assertTrue(message, value.isNotEmpty())
    }
}
