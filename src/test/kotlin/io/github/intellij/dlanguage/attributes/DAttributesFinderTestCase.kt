package io.github.intellij.dlanguage.attributes

import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.CharsetToolkit
import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributesFinder
import junit.framework.Assert
import java.io.File

/**
 * Created by francis on 12/3/2017.
 */
abstract class DAttributesFinderTestCase : DLightPlatformCodeInsightFixtureTestCase("attributes", "attributes") {
    var elem: PsiElement? = null

    override fun getTestDataPath(): String {
        return this.javaClass.classLoader.getResource("gold/attributes/" + testDirectoryName)!!.path
    }

    private fun getTestDataFiles(): Array<File> {
        return File(testDataPath).listFiles()
    }

    override fun setUp() {

        super.setUp()
        for (file in getTestDataFiles()) {
            var text = FileUtil.loadFile(file, CharsetToolkit.UTF8)
            val offset = text.indexOf("<attrib>")
            if (offset != -1) {
                text = text.replace("<attrib>", "")
                val psiFile = myFixture.configureByText(file.name, text)
                elem = psiFile.findElementAt(offset)
            }
        }
    }

    protected fun doTest(attribs: DAttributes) {
        doTest(true, attribs)
    }

    protected fun doTest(succeed: Boolean, attribs: DAttributes) {
        if (succeed && elem == null) {
            Assert.fail("Could not find reference at caret.")
        }
        val finder = DAttributesFinder(elem!!)
        finder.recurseUp()
        if (succeed) {
            Assert.assertTrue("expected attribs and actual must be equal", attribs.equals(finder.attributes))
        } else {
            Assert.assertFalse("Test should not have suceeded", attribs.equals(finder.attributes))
        }
    }
}
