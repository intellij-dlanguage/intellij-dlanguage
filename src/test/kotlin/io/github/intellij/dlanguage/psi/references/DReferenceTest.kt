package io.github.intellij.dlanguage.psi.references

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiNamedElement
import com.intellij.testFramework.LightPlatform4TestCase
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.psi.DlangPsiFile
import org.junit.Test

/**
 * @author Samael Bate (singingbush)
 * created on 20/02/2022
 */
class DReferenceTest : LightPlatform4TestCase() {

    // todo: finish writing this test
    @Test
    fun testMultiResolve() {
        val psiFile = PsiFileFactory.getInstance(project)
            .createFileFromText("testMultiResolve.d", DLanguage, "class MyClass {\n\tthis() {}\n}") as DlangPsiFile

        val element = psiFile.firstChild.firstChild.firstChild.nextSibling.nextSibling as PsiNamedElement
        val textRange: TextRange = element.textRange

        val ref = DReference(element, textRange)

        val results = ref.multiResolve(false)

        assertNotNull(results)
        assertEquals(0, results.size)
    }

//    @Test
//    fun handleElementRename() {
//        val element: PsiNamedElement = DElementFactory.createDLanguageIdentifierFromText(project, "myvar")!!
//        //val element: PsiNamedElement = DlangIdentifierImpl(null) // needs to be concrete class
//        val textRange: TextRange = element.textRange // mock()
//
//
//        val ref: DReference = DReference(element, textRange)
//
//        val result = ref.handleElementRename("myText")
//
//        assertEquals(element.node, result.node)
//    }
}
