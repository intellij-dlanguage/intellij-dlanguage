package io.github.intellij.dlanguage.resolve

import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.VirtualFileFilter
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.PsiManagerEx
import com.intellij.psi.search.GlobalSearchScope
import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase
import io.github.intellij.dlanguage.psi.named.DLanguageConstructor
import io.github.intellij.dlanguage.psi.named.DLanguageFunctionDeclaration
import io.github.intellij.dlanguage.psi.named.DLanguageModule
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

    private fun prepareFilesFindReferences(files: Array<File>) {
        for (file in files) {
            var text = FileUtil.loadFile(file, Charsets.UTF_8)
            text = StringUtil.convertLineSeparators(text)
            prepareFileFindReferences("<ref>", "<resolved>", text, file.name)
        }
    }

    protected fun prepareFileFindReferences(refName: String, resolvedName: String, fileText: String, fileName: String) {
        var text = fileText
        val refIndexBefore = text.indexOf(refName)
        val resolvedIndexBefore = text.indexOf(resolvedName)
        val referencedOffset: Int
        val resolvedOffset: Int
        if (refIndexBefore >= 0 && resolvedIndexBefore >= 0 && refIndexBefore < resolvedIndexBefore) {
            referencedOffset = text.indexOf(refName)
            text = text.replace(refName, "")
            resolvedOffset = text.indexOf(resolvedName)
            text = text.replace(resolvedName, "")
        } else {
            resolvedOffset = text.indexOf(resolvedName)
            text = text.replace(resolvedName, "")
            referencedOffset = text.indexOf(refName)
            text = text.replace(refName, "")
        }
        val psiFile = myFixture.configureByText(fileName, text)
        if (referencedOffset != -1) {
            referencedElement = psiFile.findReferenceAt(referencedOffset)!!
        }
        if (resolvedOffset != -1) {
            resolvedElement = psiFile.findElementAt(resolvedOffset)!!.parent
        }
    }

    protected fun doCheck(succeed: Boolean, refName: String = "<ref>", resolvedName: String = "<resolved>" ) {
        if (succeed && referencedElement == null) {
            fail("Reference not found, ensure `${refName}` is in the file.")
        }
        if (succeed && resolvedElement == null) {
            fail("Resolved not found, ensure `${resolvedName}` is in the file.")
        }
        if (succeed) {
            val element = referencedElement!!.resolve()
            if (resolvedElement is DLanguageConstructor) {
                assertEquals("Could not resolve expected reference.", resolvedElement, element)
            } else if (super.getTestName(true) == "scopedImportsMembers") {
                assertNotNull("Could not resolve expected reference.", element)
                assertEquals("Could not resolve expected reference.", "struct_member",
                        (element as DLanguageFunctionDeclaration).name)
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
        prepareFilesFindReferences(testDataFiles)
        doCheck(succeed)
    }

    protected fun doCheckByText1(@Language("D") mainFileContent: String,
                                 succeed: Boolean = true) {
        val referenceIndicator = "/*<ref>*/"
        val resolvedIndicator = "/*<resolved>*/"
        prepareFileFindReferences(referenceIndicator,  resolvedIndicator, mainFileContent, "main.d")
        doCheck(succeed, referenceIndicator, resolvedIndicator)
    }

    protected fun doCheckByText2(@Language("D") mainFileContent: String,
                                 @Language("D") file2: String,
                                 succeed: Boolean = true) {
        val referenceIndicator = "/*<ref>*/"
        val resolvedIndicator = "/*<resolved>*/"
        prepareFileFindReferences(referenceIndicator,  resolvedIndicator, mainFileContent, "main.d")
        prepareFileFindReferences(referenceIndicator,  resolvedIndicator, file2, "file2.d")
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
            assertInstanceOf(element!!, DLanguageModule::class.java)
            assertEquals((element as DLanguageModule).getFile(GlobalSearchScope.EMPTY_SCOPE).name, resolvedFileName)
        } else {
            assertNull(element)
        }

        checkAstNotLoaded { file -> file.path.endsWith("main.d")}
    }

     private fun checkAstNotLoaded(fileFilter: VirtualFileFilter) {
        PsiManagerEx.getInstanceEx(project).setAssertOnFileLoadingFilter(fileFilter, testRootDisposable)
    }
}
