package io.github.intellij.dlanguage.resolve

import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.CharsetToolkit
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.resolve.reference.impl.PsiMultiReference
import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase
import io.github.intellij.dlanguage.psi.DLanguageClassDeclaration
import io.github.intellij.dlanguage.psi.named.DlangConstructor
import io.github.intellij.dlanguage.psi.named.DlangFunctionDeclaration
import io.github.intellij.dlanguage.psi.named.DlangIdentifier
import java.io.File

abstract class DResolveTestCase : DLightPlatformCodeInsightFixtureTestCase("resolve", "resolve") {
    private var referencedElement: PsiReference? = null
    private var resolvedElement: PsiElement? = null
    override fun getTestDataPath(): String = this.javaClass.classLoader.getResource("gold/resolve/$testDirectoryName")!!.path!!

    private val testDataFiles: Array<File>
        get() = File(testDataPath).listFiles()!!

    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        for (file in testDataFiles) {
            var text = FileUtil.loadFile(file, CharsetToolkit.UTF8)
            text = StringUtil.convertLineSeparators(text)
            val referencedOffset = text.indexOf("<ref>")
            text = text.replace("<ref>", "")
            val resolvedOffset = text.indexOf("<resolved>")
            text = text.replace("<resolved>", "")
            val psiFile = myFixture.configureByText(file.name, text)
            if (referencedOffset != -1) {
                referencedElement = psiFile.findReferenceAt(referencedOffset)
            }
            if (resolvedOffset != -1) {
                val ref = psiFile.findReferenceAt(resolvedOffset)
                if (ref == null) {
                    fail("Reference was null in " + file.name)
                }
                resolvedElement = ref!!.element
                ensureNotNull(file)
                // container elements like DEFINITION_FUNCTION need to be looked up by .getElement().getParent()
                if (resolvedElement is DlangIdentifier) {
                    resolvedElement = ref.element.parent
                }
                //if we're resolving something within a class don't resolve the class
                if (ref is PsiMultiReference && resolvedElement is DLanguageClassDeclaration) {
                    for (psiReference in ref.references) {
                        if (psiReference.element !is DLanguageClassDeclaration) {
                            resolvedElement = psiReference.element
                        }
                    }
                }
                ensureNotNull(file)
            }
        }
    }

    private fun ensureNotNull(file: File) {
        if (resolvedElement == null) {
            fail("Reference returned null element in " + file.name)
        }
    }

    protected fun doTest(succeed: Boolean = true) {
        if (succeed && referencedElement == null) {
            fail("Could not find reference at caret.")
        }
        if (succeed && resolvedElement == null) {
            fail("Could not find resolved element.")
        }
        if (succeed) {
            val element = referencedElement!!.resolve()
            //function,class,constructor
            /*if (resolvedElement instanceof DlangInterfaceOrClass ) {
                assertEquals("Could not resolve expected reference.", resolvedElement, referencedElement.resolve().getParent());
            }*/
            /* else if (resolvedElement instanceof DLanguageConstructor) {
                assertTrue(referencedElement.resolve() instanceof DLanguageConstructor);
            }*/
            /*else*/if (resolvedElement is DlangConstructor) {
                assertEquals("Could not resolve expected reference.", resolvedElement, element)
            } else if (super.getTestName(true) == "scopedImportsMembers") {
                assertNotNull("Could not resolve expected reference.", element)
                assertEquals("Could not resolve expected reference.", "struct_member",
                        (element!!.parent as DlangFunctionDeclaration).name)
            } else {
                assertNotNull("Could not resolve expected reference.", element)
                assertEquals("Could not resolve expected reference.", resolvedElement, element!!.parent)
            }
        } else {
            assertFalse("Resolved unexpected reference.", resolvedElement == referencedElement!!.resolve())
        }
    }
}
