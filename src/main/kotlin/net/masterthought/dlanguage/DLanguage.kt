package net.masterthought.dlanguage

import com.intellij.CommonBundle
import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.TokenSet
import net.masterthought.dlanguage.dlanguage.DLanguageLexer
import net.masterthought.dlanguage.parser.DLanguageParser
import net.masterthought.dlanguage.psi.DLanguageFile
import net.masterthought.dlanguage.psi.DLanguageTypes
import net.masterthought.dlanguage.stubs.types.DFileStubElementType
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.PropertyKey
import java.lang.ref.Reference
import java.lang.ref.SoftReference
import java.util.*

object DLanguage : Language("D")

class DLanguageLexerAdapter : FlexAdapter(DLanguageLexer())

object DLanguageBundle {

    val log: Logger = Logger.getInstance(DLanguageBundle.javaClass)

    var dLangBundle: Reference<ResourceBundle>? = null

    @NonNls private const val BUNDLE_ID: String = "messages.Dlanguage"

    init {
        log.info("initialising D Language Bundle")
    }

    fun message(@PropertyKey(resourceBundle = BUNDLE_ID) key: String, vararg params: Any ): String {
        log.debug("Getting message: {}, {}", key, params)

        return CommonBundle.message(getBundle(), key, params)
    }

    private fun getBundle(): ResourceBundle {
        var bundle: ResourceBundle? = null

        if (dLangBundle != null) {
            bundle = dLangBundle?.get()
        }

        if (bundle == null) {
            bundle = ResourceBundle.getBundle(BUNDLE_ID)
            dLangBundle = SoftReference<ResourceBundle>(bundle)
        }
        return bundle!!
    }
}

class DLangParserDefinition : ParserDefinition {
    val WHITE_SPACES: TokenSet = TokenSet.create(TokenType.WHITE_SPACE)
    val COMMENTS: TokenSet = TokenSet.create(DLanguageTypes.LINE_COMMENT, DLanguageTypes.BLOCK_COMMENT, DLanguageTypes.NESTING_BLOCK_COMMENT)

    @NotNull
    override fun createParser(project: Project?): PsiParser = DLanguageParser()

    override fun createFile(viewProvider: FileViewProvider): PsiFile? = DLanguageFile(viewProvider)

    override fun spaceExistanceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements? = ParserDefinition.SpaceRequirements.MAY

    @NotNull
    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

    override fun getFileNodeType(): DFileStubElementType = DFileStubElementType.INSTANCE

    @NotNull
    override fun getWhitespaceTokens(): TokenSet = WHITE_SPACES

    @NotNull
    override fun createLexer(project: Project?): Lexer = FlexAdapter(DLanguageLexer(null))

    @NotNull
    override fun createElement(node: ASTNode?): PsiElement = DLanguageTypes.Factory.createElement(node)

    @NotNull
    override fun getCommentTokens(): TokenSet = COMMENTS
}
