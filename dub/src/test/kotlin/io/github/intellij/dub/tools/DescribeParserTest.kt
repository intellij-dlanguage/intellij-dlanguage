package io.github.intellij.dub.tools

import com.intellij.testFramework.LightPlatformTestCase
import io.github.intellij.dub.project.DubPackage
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author Samael Bate (singingbush)
 * created on 06/02/18
 */
class DescribeParserTest : LightPlatformTestCase() {

    var parser: DescribeParser = DescribeParserImpl()

    @Throws(Exception::class)
    fun `test parsing the json output of dub describe With Unix paths`() {
        val json = String(Files.readAllBytes(
            Paths.get(javaClass.classLoader.getResource("dub/dubDescribeOutput-Unix.json")!!.toURI())
        ))

        val dubProject = parser.parse(json)

        assertEquals("SuperAwesomeApp", dubProject.rootPackageName)
        assertEquals("default", dubProject.configuration)
        assertEquals("debug", dubProject.buildType)
        assertEquals("dmd", dubProject.compiler)
        assertEquals(1, dubProject.architecture.size)
        assertEquals(2, dubProject.platform.size)
        assertEquals("linux", dubProject.platform[0])
        assertEquals("posix", dubProject.platform[1])

        assertDubPackageValid("root package", dubProject.rootPackage)

        assertFalse(
            "The Root package should not be included in the dependencies",
            dubProject.packages.contains(dubProject.rootPackage)
        )

        assertEquals(29, dubProject.packages.size)

        dubProject.packages.forEach {
            assertDubPackageValid(it.name, it)
            it.path.contains("/home/username/.dub/packages/.*")
            assertNotEmpty(it.sourcesDirs)
        }
    }

    @Throws(Exception::class)
    fun `test parsing the json output of dub describe With Windows paths`() {
        val json = String(Files.readAllBytes(
            Paths.get(javaClass.classLoader.getResource("dub/dubDescribeOutput-Windows.json")!!.toURI())
        ))

        val dubProject = parser.parse(json.byteInputStream().bufferedReader())

        assertEquals("testapp", dubProject.rootPackageName)
        assertEquals("default", dubProject.configuration)
        assertEquals("debug", dubProject.buildType)
        assertEquals("dmd", dubProject.compiler)
        assertEquals(1, dubProject.architecture.size)
        assertEquals(1, dubProject.platform.size)
        assertEquals("windows", dubProject.platform[0])

        assertDubPackageValid("root package", dubProject.rootPackage)

        assertFalse(
            "The Root package should not be included in the dependencies",
            dubProject.packages.contains(dubProject.rootPackage)
        )

        assertEquals(38, dubProject.packages.size)

        dubProject.packages.forEach {
            assertDubPackageValid(it.name, it)
            it.path.contains("C:\\Users\\TestUser\\AppData\\Local\\dub\\packages\\.*")

            // vibe-d:core doesn't specify importPaths
            if("vibe-d:core" == it.name) {
                // vibe-d:core is basically a package that pulls in "vibe-d:data", "vibe-d:utils", "vibe-core"
                //assertEquals("none", it.targetType)
                assertEmpty(it.sourcesDirs)
                assertContainsElements(it.dependencies, "vibe-d:data", "vibe-d:utils", "vibe-core")
                assertEquals("Compatibility forwarding package for vibe-core", it.description)
                assertEmpty(it.resources)
                assertEmpty(it.stringImportFiles)
                assertEmpty(it.files)
            } else if("vibe-d:data" == it.name) {
                assertEquals("Data format and serialization support", it.description)
                assertNotEmpty(it.sourcesDirs)
                assertContainsElements(it.dependencies, "vibe-d:utils")
                assertContainsElements(it.sourcesDirs, ".")
                assertEmpty(it.resources)
                assertEmpty(it.stringImportFiles)
                assertEquals(3, it.files.size)
            } else {
                assertNotEmpty(it.files)
            }
        }

        assertNotEmpty(dubProject.targets)
        dubProject.targets.forEach {
            assertNotBlank("rootPackage should not be blank", it.rootPackage)
            assertNotBlank("rootConfiguration should not be blank", it.rootConfiguration)
            assertNotEmpty(it.packages)
        }
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
        assertNotNull("$name Should have resources", pkg.resources)
        assertNotNull("$name Should have sourceFiles", pkg.sourceFiles)
        assertNotNull("$name Should have stringImportFiles", pkg.stringImportFiles)

        //assertTrue(pkg.active)
        assertNotNull(pkg.workingDirectory)
        assertNotNull(pkg.mainSourceFile)

        assertNotNull(pkg.libs)
        assertNotNull(pkg.copyFiles)
        assertNotNull(pkg.extraDependencyFiles)
        assertNotNull(pkg.versions)
        assertNotNull(pkg.debugVersions)
        assertNotNull(pkg.preGenerateCommands)
        assertNotNull(pkg.postGenerateCommands)
        assertNotNull(pkg.preBuildCommands)
        assertNotNull(pkg.postBuildCommands)
        assertNotNull(pkg.preRunCommands)
        assertNotNull(pkg.postRunCommands)

        ////val dflags
        ////val lflags
        //assertTrue(pkg.files.isNotEmpty())
    }

    private fun assertNotBlank(message: String, value: String) {
        assertNotNull(message, value)
        assertTrue(message, value.isNotEmpty())
    }
}
