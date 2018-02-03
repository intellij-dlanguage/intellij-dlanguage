package io.github.intellij.dlanguage

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.projectRoots.ProjectJdkTable
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.CharsetToolkit
import com.intellij.testFramework.TestDataFile
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase
import org.jetbrains.annotations.NonNls
import java.io.File
import java.io.IOException
import java.net.URISyntaxException

open class DLightPlatformCodeInsightFixtureTestCase(srcName: String, expectName: String = srcName) : LightPlatformCodeInsightFixtureTestCase() {
    private var srcPath: String
    private var expectPath: String

    protected val fileName: String
        get() = "$testName.d"

    protected val testName: String
        get() = camelOrWordsToSnake(getTestName(true))

    protected fun camelOrWordsToSnake(name: String): String {
        if (' ' in name) return name.replace(" ", "_")

        return name.split("(?=[A-Z])".toRegex()).joinToString("_", transform = String::toLowerCase)
    }

    /**
     * Sets the expected input and outputs and calls the constructor of the parent.
     *
     * @param srcName    Directory name of test inputs.
     * @param expectName Directory name of expected outputs.
     */
    init {
        srcPath = "${getDirPath()}/$srcName"
        expectPath = "${getDirPath()}/$expectName"
    }

    /**
     * Base path to the test files.
     */
    protected fun getDirPath(): String = "gold"

    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
    }

    @Throws(Exception::class)
    override fun tearDown() {
        super.tearDown()
    }

    override fun getTestDataPath(): String = srcPath

    protected fun getTestDataPath(vararg names: String): String {
        return this.javaClass.classLoader
            .getResource("$srcPath/${names.joinToString("/")}")!!
            .path.replace(Regex("/\\D:"), "")
    }

    /**
     * Loads the test data file from the right place.
     */
    @Throws(IOException::class, URISyntaxException::class)
    protected fun loadFile(@NonNls @TestDataFile name: String): String = doLoadFile(srcPath, name)

    @Throws(IOException::class, URISyntaxException::class)
    private fun doLoadFile(myFullDataPath: String, name: String): String {
        val resource = this.javaClass.classLoader
            .getResource("$myFullDataPath/$name")!!
            .toURI()

        var text = FileUtil.loadFile(File(resource), CharsetToolkit.UTF8).trim { it <= ' ' }
        text = StringUtil.convertLineSeparators(text)
        return text
    }

    protected fun setUpProjectSdk() {
        ApplicationManager.getApplication().runWriteAction {
            val sdk = projectDescriptor.sdk
            ProjectJdkTable.getInstance().addJdk(sdk!!)
            ProjectRootManager.getInstance(myFixture.project).projectSdk = sdk
        }
    }
}
