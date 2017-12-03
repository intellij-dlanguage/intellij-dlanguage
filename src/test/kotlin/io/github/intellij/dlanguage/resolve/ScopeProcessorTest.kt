package io.github.intellij.dlanguage.resolve

import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.testFramework.UsefulTestCase
import io.github.intellij.dlanguage.psi.DLanguageAliasDeclaration
import io.github.intellij.dlanguage.psi.DLanguageDeclaration
import io.github.intellij.dlanguage.psi.DlangAliasInitializer
import io.github.intellij.dlanguage.utils.StructBody
import io.kotlintest.mock.`when`
import io.kotlintest.mock.mock
import junit.framework.TestCase
import org.junit.Test

class ScopeProcessorTest : UsefulTestCase() {

    @Test
    fun testProcessDeclarationsWithEmptyList() {
        val element = mock<StructBody>()
        `when`(element.declarations).thenReturn(emptyList())
        val processor = mock<PsiScopeProcessor>()
        val state = mock<ResolveState>()
        val lastParent = mock<PsiElement>()
        val place = mock<PsiElement>()

        val shouldContinue = ScopeProcessorImpl.processDeclarations(element, processor, state, lastParent, place)
        TestCase.assertTrue("Should continue", shouldContinue)
    }

    @Test
    fun testProcessDeclarationsShouldntContinue() {
        val alias = mock<DLanguageAliasDeclaration>()
        val aliasInitializer: DlangAliasInitializer = mock<DlangAliasInitializer>()
        `when`(alias.aliasInitializers).thenReturn(listOf(aliasInitializer))
        val declaration = mock<DLanguageDeclaration>()
        `when`(declaration.aliasDeclaration).thenReturn(alias)

        val element = mock<StructBody>()
        `when`(element.declarations).thenReturn(listOf(declaration))
        val processor = mock<PsiScopeProcessor>()
        val state = mock<ResolveState>()
        `when`(processor.execute(declaration, state)).thenReturn(false)
        val lastParent = mock<PsiElement>()
        val place = mock<PsiElement>()

        val shouldContinue = ScopeProcessorImpl.processDeclarations(element, processor, state, lastParent, place)
        TestCase.assertFalse("Shouldn't continue", shouldContinue)
    }

}
