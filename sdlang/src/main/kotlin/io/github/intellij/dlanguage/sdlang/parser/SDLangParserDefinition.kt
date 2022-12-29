package io.github.intellij.dlanguage.sdlang.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import io.github.intellij.dlanguage.sdlang.SimpleDefinitionLanguage
import io.github.intellij.dlanguage.sdlang.lexer.SDLangLexer
import io.github.intellij.dlanguage.sdlang.psi.SDLANG_COMMENTS
import io.github.intellij.dlanguage.sdlang.psi.SDLANG_STRINGS
import io.github.intellij.dlanguage.sdlang.psi.SDLangElementTypes
import io.github.intellij.dlanguage.sdlang.psi.SDLangFile

class SDLangParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?): Lexer = SDLangLexer()

    override fun createParser(project: Project?): PsiParser = SDLangParser()

    override fun getFileNodeType(): IFileElementType = FILE

    override fun getCommentTokens(): TokenSet = SDLANG_COMMENTS

    override fun getStringLiteralElements(): TokenSet = SDLANG_STRINGS

    override fun createElement(node: ASTNode?): PsiElement = SDLangElementTypes.Factory.createElement(node)

    override fun createFile(viewProvider: FileViewProvider): PsiFile = SDLangFile(viewProvider)

    companion object {
        val FILE: IFileElementType = IFileElementType(SimpleDefinitionLanguage)
    }
}
