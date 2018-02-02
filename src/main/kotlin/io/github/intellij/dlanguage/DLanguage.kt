package io.github.intellij.dlanguage

import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.tree.TokenSet.create
import io.github.intellij.dlanguage.dlanguage.DlangLexer
import io.github.intellij.dlanguage.parser.ParserWrapper
import io.github.intellij.dlanguage.psi.DlangFile
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.stubs.types.DFileStubElementType
import org.jetbrains.annotations.NotNull

object DLanguage : Language("D")

class DLanguageLexerAdapter : FlexAdapter(DlangLexer())

class DLangParserDefinition : ParserDefinition {
    val WHITE_SPACES: TokenSet = create(TokenType.WHITE_SPACE)
    val COMMENTS: TokenSet = create(DlangTypes.LINE_COMMENT, DlangTypes.BLOCK_COMMENT, DlangTypes.NESTING_BLOCK_COMMENT)

    @NotNull
    override fun createParser(project: Project?): PsiParser = ParserWrapper()

    override fun createFile(viewProvider: FileViewProvider): PsiFile? = DlangFile(viewProvider)

    override fun spaceExistanceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements? = ParserDefinition.SpaceRequirements.MAY

    @NotNull
    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

    override fun getFileNodeType(): DFileStubElementType = DFileStubElementType.INSTANCE

    @NotNull
    override fun getWhitespaceTokens(): TokenSet = WHITE_SPACES

    @NotNull
    override fun createLexer(project: Project?): Lexer = FlexAdapter(DlangLexer(null))

    @NotNull
    override fun createElement(node: ASTNode?): PsiElement = DlangTypes.Factory.createElement(node)

    @NotNull
    override fun getCommentTokens(): TokenSet = COMMENTS
}
