package io.github.intellij.dlanguage.psi.impl.named

import com.intellij.lang.ASTNode
import com.intellij.lang.parser.GeneratedParserUtilBase
import com.intellij.testFramework.LightPlatform4TestCase
import io.github.intellij.dlanguage.psi.named.DLanguageClassDeclaration
import io.github.intellij.dlanguage.psi.named.DLanguageDeclaratorIdentifier
import io.github.intellij.dlanguage.psi.named.DLanguageEnumDeclaration
import org.junit.Test

/*
* Doesn't do much in the way of testing but at least ensures that psi-impl has compiled correctly
* with the interfaces made available from psi-api
*/
class DLangNamedPsiImplTest : LightPlatform4TestCase() {

    @Test
    fun `test Class Declaration`() {
        val node: ASTNode = GeneratedParserUtilBase.DummyBlock()
        val classDeclaration: DLanguageClassDeclaration = DlangClassDeclarationImpl(node)
        assertFalse(classDeclaration.isValid)
    }

    @Test
    fun `test Declarator Identifier`() {
        val node: ASTNode = GeneratedParserUtilBase.DummyBlock()
        val declaratorIdentifier: DLanguageDeclaratorIdentifier = DlangDeclaratorIdentifierImpl(node)
        assertFalse(declaratorIdentifier.isValid)
    }

    @Test
    fun `test Enum Declaration`() {
        val node: ASTNode = GeneratedParserUtilBase.DummyBlock()
        val enumDeclaration: DLanguageEnumDeclaration = DlangEnumDeclarationImpl(node)
        assertFalse(enumDeclaration.isValid)
    }
}
