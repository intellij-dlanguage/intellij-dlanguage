package io.github.intellij.dlanguage.annotator

import com.intellij.codeInspection.InspectionProfileEntry
import com.intellij.psi.PsiFile
import com.intellij.testFramework.EditorTestUtil
import com.intellij.testFramework.PlatformTestUtil
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.inspections.PhobosStyleGuidelines
//import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import org.junit.Test

class DHighlightingAnnotatorTest : LightPlatformCodeInsightFixture4TestCase() {

    override fun getTestDataPath(): String = this.javaClass.classLoader.getResource("gold/highlighting/annotator")!!.path

    @Test
    fun testTypeParameters() {
        myFixture.configureByFile("type_parameters.d")
        //myFixture.enableInspections(PhobosStyleGuidelines())
        myFixture.testHighlighting(false, true, false)
    }

//    @Test
//    fun testInvalidDelimiterString() {
//        //var file = myFixture.configureByFile("invalid_string_delimiters.d")
//        //PlatformTestUtil.dispatchAllInvocationEventsInIdeEventQueue()
//
//        val file: PsiFile = myFixture.configureByText(DlangFileType.INSTANCE, "enum a = q\"_test_\";")
//        //EditorTestUtil.testFileSyntaxHighlighting()
//        EditorTestUtil.testFileSyntaxHighlighting(
//            file,
//            false,
//            "enum a = <error descr=\"Invalid string delimiter\">q\"_test_\"</error>;"
//        )
//
//        //myFixture.testHighlighting(false, false, false);
//    }
//
//    @Test
//    fun testUnclosedComment() {
//        myFixture.configureByFile("unclosed_comment.d")
//        myFixture.testHighlighting(false, false, false);
//    }
}
