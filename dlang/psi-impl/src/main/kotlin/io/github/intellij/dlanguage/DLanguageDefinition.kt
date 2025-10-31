package io.github.intellij.dlanguage

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.tree.TokenSet.create
import com.intellij.psi.tree.TokenSet.orSet
import io.github.intellij.dlanguage.lexer.DlangLexer
import io.github.intellij.dlanguage.parser.ParserWrapper
import io.github.intellij.dlanguage.psi.DTokenSets.IES_TOKENS
import io.github.intellij.dlanguage.psi.DTokenSets.STRING_LITERALS
import io.github.intellij.dlanguage.psi.DlangPsiFileImpl
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.stubs.types.DFileStubElementType

class DLangParserDefinition : ParserDefinition {
    val WHITE_SPACES: TokenSet = create(TokenType.WHITE_SPACE)
    val REGULAR_COMMENTS: TokenSet = create(DlangTypes.LINE_COMMENT, DlangTypes.BLOCK_COMMENT, DlangTypes.NESTING_BLOCK_COMMENT)
    val DOC_COMMENTS: TokenSet = create(DlangTypes.LINE_DOC, DlangTypes.BLOCK_DOC, DlangTypes.NESTING_BLOCK_DOC)
    val COMMENTS: TokenSet = orSet(REGULAR_COMMENTS, DOC_COMMENTS)

    override fun createParser(project: Project?): ParserWrapper = ParserWrapper()

    override fun createFile(viewProvider: FileViewProvider): PsiFile = DlangPsiFileImpl(viewProvider)

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements = ParserDefinition.SpaceRequirements.MAY

    override fun getStringLiteralElements(): TokenSet = orSet(STRING_LITERALS, IES_TOKENS)

    override fun getFileNodeType(): DFileStubElementType = DFileStubElementType.INSTANCE

    override fun getWhitespaceTokens(): TokenSet = WHITE_SPACES

    override fun createLexer(project: Project?): Lexer = DlangLexer()

    override fun createElement(node: ASTNode?): PsiElement = DlangTypes.Factory.createElement(node)

    override fun getCommentTokens(): TokenSet = COMMENTS
}

