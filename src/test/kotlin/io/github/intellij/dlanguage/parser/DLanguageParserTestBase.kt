package io.github.intellij.dlanguage.parser

import com.intellij.lang.ParserDefinition
import com.intellij.psi.PsiFile
import com.intellij.testFramework.ParsingTestCase
import com.intellij.testFramework.TestDataFile
import org.jetbrains.annotations.NonNls
import java.io.File

abstract class DLanguageParserTestBase internal constructor(
    dataPath: String,
    fileExt: String,
    vararg definitions: ParserDefinition?
) : ParsingTestCase(dataPath, fileExt, *definitions) {
    override fun getTestDataPath(): String? {
        return this.javaClass.getClassLoader().getResource("gold")!!.path
    }

    override fun skipSpaces(): Boolean = true

    /**
     * Perform a test. Add tests that should work but does not work yet with
     * doTestModule(false, false).
     */
    fun doDlangParserTest(checkResult: Boolean = true, shouldPass: Boolean = true) {
        doTest(checkResult, shouldPass)
        if (shouldPass) {
            assertFalse(
                "PsiFile contains error elements",
                toParseTreeText(myFile, skipSpaces(), includeRanges()).contains("PsiErrorElement")
            )
        }
    }


    override fun checkResult(
        @TestDataFile targetDataName: @NonNls String,
        file: PsiFile
    ) {
        checkResult(
            myFullDataPath, "expected" + File.separator + targetDataName, file
        )
    }

}
