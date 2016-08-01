package net.masterthought.dlanguage.lexer

import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.util.Comparing
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.CharsetToolkit
import com.intellij.rt.execution.junit.FileComparisonFailure
import com.intellij.testFramework.LexerTestCase
import com.intellij.testFramework.TestDataFile
import com.intellij.testFramework.UsefulTestCase
import com.intellij.testFramework.VfsTestUtil
import junit.framework.TestCase
import org.jetbrains.annotations.NonNls
import java.io.File
import java.io.IOException
import java.net.URISyntaxException

open class DLanguageLexerTestBase(expectPath: String) : LexerTestCase() {

    private val srcPath = dirPath + File.separator + "lexer"
    private val myExpectPath: String

    init {
        myExpectPath = dirPath + File.separator + expectPath
    }

    fun doTest() {
        val fileName = getTestName(false) + ".d"
        var text = ""
        try {
            text = loadFile(fileName)
        } catch (e: IOException) {
            TestCase.fail("can't load file " + fileName + ": " + e.message)
        } catch (e: URISyntaxException) {
            TestCase.fail("can't load file " + fileName + ": " + e.message)
        }

        val result = printTokens(text, 0)
        try {
            doCheckResult(myExpectPath + File.separator + "expected",
                    getTestName(false) + ".txt", result)
        } catch (e: IOException) {
            TestCase.fail("Unexpected IO Exception: " + e.message)
        }

    }

    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
    }

    override fun createLexer(): Lexer {
        return FlexAdapter(DLanguageLexer(null))
    }

    override fun getDirPath(): String {
        return "gold"
    }

    /**
     * Loads the test data file from the right place.
     */
    @Throws(IOException::class, URISyntaxException::class)
    private fun loadFile(@NonNls @TestDataFile name: String): String {
        return doLoadFile(srcPath, name)
    }

    @Throws(IOException::class, URISyntaxException::class)
    private fun doLoadFile(myFullDataPath: String, name: String): String {
        val resource = this.javaClass.classLoader.getResource(String.format("%s/%s", myFullDataPath, name))!!.toURI()
        var text = FileUtil.loadFile(File(resource), CharsetToolkit.UTF8).trim { it <= ' ' }
        text = StringUtil.convertLineSeparators(text)
        return text
    }

    /**
     * Check the result against a plain text file. Creates file if missing.
     * Avoids the default sandboxing in IntelliJ.
     */
    @Throws(IOException::class)
    private fun doCheckResult(fullPath: String, targetDataName: String, text: String) {
        var text = text
        text = text.trim { it <= ' ' }
        val expectedFileName = fullPath + File.separator + targetDataName
        if (UsefulTestCase.OVERWRITE_TESTDATA) {
            VfsTestUtil.overwriteTestData(expectedFileName, text)
            println("File $expectedFileName created.")
        }
        try {
            val expectedText = doLoadFile(fullPath, targetDataName)
            if (!Comparing.equal(expectedText, text)) {
                throw FileComparisonFailure(targetDataName, expectedText, text, expectedFileName)
            }
        } catch (e: URISyntaxException) {
            VfsTestUtil.overwriteTestData(expectedFileName, text)
            TestCase.fail("No output text found. File $expectedFileName created.")
        } catch (e: IOException) {
            VfsTestUtil.overwriteTestData(expectedFileName, text)
            TestCase.fail("No output text found. File $expectedFileName created.")
        }

    }
}
