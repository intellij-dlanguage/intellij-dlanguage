package io.github.intellij.dlanguage.resolve

import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.CharsetToolkit
import com.intellij.openapi.vfs.VirtualFileFilter
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.PsiManagerEx
import com.intellij.psi.impl.source.resolve.reference.impl.PsiMultiReference
import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase
import io.github.intellij.dlanguage.psi.DlangPsiFileImpl
import io.github.intellij.dlanguage.psi.named.DlangClassDeclaration
import io.github.intellij.dlanguage.psi.named.DlangConstructor
import io.github.intellij.dlanguage.psi.named.DlangFunctionDeclaration
import org.intellij.lang.annotations.Language
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
    }

    private fun prepareFilesFindReferences() {
        for (file in testDataFiles) {
            var text = FileUtil.loadFile(file, CharsetToolkit.UTF8)
            text = StringUtil.convertLineSeparators(text)
            val refIndexBefore = text.indexOf("<ref>")
            val resolvedIndexBefore = text.indexOf("<resolved>")
            val referencedOffset: Int
            val resolvedOffset: Int
            if (refIndexBefore >= 0 && resolvedIndexBefore >= 0 && refIndexBefore < resolvedIndexBefore) {
                referencedOffset = text.indexOf("<ref>")
                text = text.replace("<ref>", "")
                resolvedOffset = text.indexOf("<resolved>")
                text = text.replace("<resolved>", "")
            } else {
                resolvedOffset = text.indexOf("<resolved>")
                text = text.replace("<resolved>", "")
                referencedOffset = text.indexOf("<ref>")
                text = text.replace("<ref>", "")
            }
            val psiFile = myFixture.configureByText(file.name, text)
            if (referencedOffset != -1) {
                referencedElement = psiFile.findReferenceAt(referencedOffset)!!
            }
            if (resolvedOffset != -1) {
                resolvedElement = psiFile.findElementAt(resolvedOffset)!!.parent
            }
        }
    }

    private fun doCheck(succeed: Boolean) {
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
                        (element as DlangFunctionDeclaration).name)
            } else {
                assertNotNull("Could not resolve expected reference.", element)
                assertEquals("Could not resolve expected reference.", resolvedElement, element!!)
            }
        } else {
            if (resolvedElement == null) {
                assertNull("The reference should not resolve to anything but a reference was found", referencedElement!!.resolve())
            } else {
                assertFalse("Resolved unexpected reference.", resolvedElement == referencedElement!!.resolve())
            }
        }
    }

    protected fun doTest(succeed: Boolean = true) {
        prepareFilesFindReferences()
        doCheck(succeed)
    }

    protected fun doCheckByText2(mainFileContent: String, file2: String, succeed: Boolean = true) {
        val referenceIndicator = "/*<ref>*/"
        val resolvedIndicator = "/*<resolved>*/"
        val referencedOffset = mainFileContent.indexOf(referenceIndicator)
        val mainFileText = mainFileContent.replace(referenceIndicator, "")
        val resolvedOffset = file2.indexOf(resolvedIndicator)
        val file2Text = file2.replace(resolvedIndicator, "")
        val psiFile2 = myFixture.configureByText("file2.d", file2Text)
        val psiFile = myFixture.configureByText("main.d", mainFileText)
        referencedElement = psiFile.findReferenceAt(referencedOffset)
        resolvedElement = psiFile2.findElementAt(resolvedOffset)!!.parent
        doCheck(succeed)
    }

    protected fun doTestStubOnlyResolve(
        @Language("D") mainFileContent: String,
        resolvedFileName: String?
    ) {
        val referenceIndicator = "/*<ref>*/"
        var mainFileText = StringUtil.convertLineSeparators(mainFileContent)
        val referencedOffset = mainFileText.indexOf(referenceIndicator)
        assertTrue("Test is not properly configured, need $referenceIndicator to be defined", referencedOffset > 0)
        mainFileText = mainFileText.replace(referenceIndicator, "")
        val psiMainFile = myFixture.configureByText("main.d", mainFileText)
        referencedElement = psiMainFile.findReferenceAt(referencedOffset)
        val element = referencedElement!!.resolve()
        if (resolvedFileName != null) {
            assertNotNull("Referenced not resolved", element)
            assertInstanceOf(element!!, DlangPsiFileImpl::class.java)
            assertEquals((element as DlangPsiFileImpl).name, resolvedFileName)
        } else {
            assertNull(element)
        }

        checkAstNotLoaded { file -> file.path.endsWith("main.d")}
    }

     private fun checkAstNotLoaded(fileFilter: VirtualFileFilter) {
        PsiManagerEx.getInstanceEx(project).setAssertOnFileLoadingFilter(fileFilter, testRootDisposable)
    }
}
