package io.github.intellij.dlanguage.lexer

import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.util.Comparing
import com.intellij.openapi.util.io.FileUtil.loadFile
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

abstract class DlangLexerTestBase(expectPath: String) : LexerTestCase() {

    private val srcPath = dirPath + File.separator + "lexer"
    private val myExpectPath: String

    init {
        myExpectPath = dirPath + File.separator + expectPath
    }

    fun doTest() {
        val fileName = getTestName(false) + ".d"
        try {
            val text = loadFile(fileName)
            doCheckResult(myExpectPath + File.separator + "expected", getTestName(false) + ".txt", printTokens(text, 0))
        } catch (e: Exception) {
            TestCase.fail("Something went wrong using file: " + fileName + ": " + e.message)
        }

    }

    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
    }

    override fun createLexer(): Lexer = FlexAdapter(io.github.intellij.dlanguage.dlanguage.DlangLexer(null))

    override fun getDirPath(): String = "gold"

    // Loads the test data file from the right place.
    @Throws(IOException::class, URISyntaxException::class)
    private fun loadFile(@NonNls @TestDataFile name: String): String {
        return doLoadFile(srcPath, name)
    }

    @Throws(IOException::class, URISyntaxException::class)
    private fun doLoadFile(myFullDataPath: String, name: String): String {
        val resource = this.javaClass.classLoader.getResource(String.format("%s/%s", myFullDataPath, name))!!.toURI()
        return StringUtil.convertLineSeparators(loadFile(File(resource), CharsetToolkit.UTF8).trim { it <= ' ' })
    }


    // Check the result against a plain text file. Creates file if missing.
    // Avoids the default sandboxing in IntelliJ.
    @Throws(IOException::class)
    private fun doCheckResult(fullPath: String, targetDataName: String, text: String) {
        val theText = text.trim { it <= ' ' }
        val expectedFileName = fullPath + File.separator + targetDataName
        if (UsefulTestCase.OVERWRITE_TESTDATA) {
            VfsTestUtil.overwriteTestData(expectedFileName, theText)
            println("File $expectedFileName created.")
        }
        try {
            val expectedText = doLoadFile(fullPath, targetDataName)
            if (!Comparing.equal(expectedText, theText)) {
                throw FileComparisonFailure(targetDataName, expectedText, theText, expectedFileName)
            }
        } catch (e: Exception) {
            VfsTestUtil.overwriteTestData(expectedFileName, theText)
            TestCase.fail("No output text found. File $expectedFileName created.")
        }

    }
}
