package io.github.intellij.dlanguage.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.lang.parser.GeneratedParserUtilBase
import com.intellij.testFramework.LightPlatform4TestCase
import io.github.intellij.dlanguage.psi.DLanguageAddExpression
import io.github.intellij.dlanguage.psi.DLanguageAliasAssign
import io.github.intellij.dlanguage.psi.DLanguageAndExpression
import org.junit.Test

/*
* Doesn't do much in the way of testing but at least ensures that our codegen has compiled correctly
* with the interfaces made available from psi-api
*/
class DLanguagePsiImplementationTest : LightPlatform4TestCase() {

    @Test
    fun `test Add Expression`() {
        val node: ASTNode = GeneratedParserUtilBase.DummyBlock()
        val addExpression: DLanguageAddExpression = DLanguageAddExpressionImpl(node)
        assertFalse(addExpression.isValid)
    }

    @Test
    fun `test Alias Assign`() {
        val node: ASTNode = GeneratedParserUtilBase.DummyBlock()
        val aliasAssign: DLanguageAliasAssign = DLanguageAliasAssignImpl(node)
        assertFalse(aliasAssign.isValid)
    }

    @Test
    fun `test And Expression`() {
        val node: ASTNode = GeneratedParserUtilBase.DummyBlock()
        val andExpression: DLanguageAndExpression = DLanguageAndExpressionImpl(node)
        assertFalse(andExpression.isValid)
    }
}
