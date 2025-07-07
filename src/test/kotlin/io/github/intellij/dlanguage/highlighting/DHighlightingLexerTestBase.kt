package io.github.intellij.dlanguage.highlighting

import com.intellij.lexer.Lexer
import com.intellij.openapi.util.Comparing
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.text.StringUtil
import com.intellij.platform.testFramework.core.FileComparisonFailedError
import com.intellij.testFramework.LexerTestCase
import com.intellij.testFramework.TestDataFile
import com.intellij.testFramework.VfsTestUtil
import io.github.intellij.dlanguage.lexer.DHighlightingLexer
import org.jetbrains.annotations.NonNls
import java.io.File
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import kotlin.text.Charsets.UTF_8

abstract class DHighlightingLexerTestBase internal constructor(expectPath: String) : LexerTestCase() {
    private val srcPath = dirPath + File.separator + "highlighting"
    private val myExpectPath: String

    init {
        myExpectPath = dirPath + File.separator + expectPath
    }

    fun doTest(checkResult: Boolean = true) {
        val fileName = getTestName(false) + ".d"
        var text = ""
        try {
            text = loadFile(fileName)
        } catch (e: IOException) {
            fail("can't load file " + fileName + ": " + e.message)
        } catch (e: URISyntaxException) {
            fail("can't load file " + fileName + ": " + e.message)
        }
        val result = printTokens(text, 0)
        if (checkResult) {
            try {
                doCheckResult(
                    myExpectPath + File.separator + "expected",
                    getTestName(false) + ".txt", result
                )
            } catch (e: IOException) {
                fail("Unexpected IO Exception: " + e.message)
            }
        }
    }

    override fun createLexer(): Lexer {
        return DHighlightingLexer()
    }

    override fun getDirPath(): String {
        return "gold"
    }

    /**
     * Loads the test data file from the right place.
     */
    @Throws(IOException::class, URISyntaxException::class)
    protected fun loadFile(@TestDataFile name: @NonNls String): String {
        return doLoadFile(srcPath, name)
    }

    @Throws(IOException::class, URISyntaxException::class)
    private fun doLoadFile(myFullDataPath: String, name: String): String {
        val resource: URI
        try {
            resource = this.javaClass.getClassLoader().getResource("$myFullDataPath/$name")!!.toURI()
        } catch (e: NullPointerException) {
            throw IOException(e)
        }
        var text = FileUtil.loadFile(File(resource), UTF_8).trim { it <= ' ' }
        text = StringUtil.convertLineSeparators(text)
        return text
    }

    /**
     * Check the result against a plain text file. Creates file if missing.
     * Avoids the default sandboxing in IntelliJ.
     */
    @Throws(IOException::class)
    fun doCheckResult(fullPath: String, targetDataName: String, actualText: String) {
        val text = actualText.trim { it <= ' ' }
        val expectedFileName = fullPath + File.separator + targetDataName
        if (OVERWRITE_TESTDATA) {
            VfsTestUtil.overwriteTestData(expectedFileName, text)
            println("File $expectedFileName created.")
        }
        try {
            val expectedText = doLoadFile(fullPath, targetDataName)
            if (!Comparing.strEqual(expectedText, text)) {
                throw FileComparisonFailedError(targetDataName, expectedText, text)
            }
        } catch (_: URISyntaxException) {
            VfsTestUtil.overwriteTestData(expectedFileName, text)
            fail("No output text found. File $expectedFileName created.")
        } catch (_: IOException) {
            VfsTestUtil.overwriteTestData(expectedFileName, text)
            fail("No output text found. File $expectedFileName created.")
        }
    }
}


