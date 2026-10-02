package io.github.intellij.dlanguage.sdlang.psi

import com.intellij.lang.ASTNode
import com.intellij.lang.parser.GeneratedParserUtilBase
import com.intellij.testFramework.LightPlatform4TestCase
import io.github.intellij.dlanguage.sdlang.impl.SDLangAttributeImpl
import io.github.intellij.dlanguage.sdlang.impl.SDLangBinaryLiteralImpl
import io.github.intellij.dlanguage.sdlang.impl.SDLangBooleanLiteralImpl
import io.github.intellij.dlanguage.sdlang.impl.SDLangChildrensImpl
import org.junit.Test

/*
* Doesn't do much in the way of testing but at least ensures that grammarkit codegen worked
*/
class SDLangPsiImplTest : LightPlatform4TestCase() {

    @Test
    fun `test Attribute`() {
        val node: ASTNode = GeneratedParserUtilBase.DummyBlock()
        val attribute: SDLangAttribute = SDLangAttributeImpl(node)
        assertFalse(attribute.isValid)
    }

    @Test
    fun `test Binary Literal`() {
        val node: ASTNode = GeneratedParserUtilBase.DummyBlock()
        val binaryLiteral: SDLangBinaryLiteral = SDLangBinaryLiteralImpl(node)
        assertFalse(binaryLiteral.isValid)
    }

    @Test
    fun `test Boolean Literal`() {
        val node: ASTNode = GeneratedParserUtilBase.DummyBlock()
        val booleanLiteral: SDLangBooleanLiteral = SDLangBooleanLiteralImpl(node)
        assertFalse(booleanLiteral.isValid)
    }

    @Test
    fun `test Childrens`() {
        val node: ASTNode = GeneratedParserUtilBase.DummyBlock()
        val childrens: SDLangChildrens = SDLangChildrensImpl(node)
        assertFalse(childrens.isValid)
    }
}
