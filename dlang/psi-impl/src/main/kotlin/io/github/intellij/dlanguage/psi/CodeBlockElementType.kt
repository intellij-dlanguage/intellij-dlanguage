package io.github.intellij.dlanguage.psi

import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.lang.LighterASTNode
import com.intellij.lang.LighterLazyParseableNode
import com.intellij.lang.PsiBuilderFactory
import com.intellij.lang.PsiBuilderUtil
import com.intellij.openapi.project.Project
import com.intellij.psi.tree.ICompositeElementType
import com.intellij.psi.tree.IErrorCounterReparseableElementType
import com.intellij.psi.tree.ILightLazyParseableElementType
import com.intellij.util.diff.FlyweightCapableTreeStructure
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.parser.DLangParser
import io.github.intellij.dlanguage.psi.impl.DLanguageBlockStatementImpl

class CodeBlockElementType(name: String) : IErrorCounterReparseableElementType(name, DLanguage),
    ICompositeElementType, ILightLazyParseableElementType {

    override fun createNode(text: CharSequence?): ASTNode? = DLanguageBlockStatementImpl(text)
    override fun createCompositeNode(): ASTNode = DLanguageBlockStatementImpl(null)

    override fun parseContents(chameleon: ASTNode): ASTNode? {
        val psi = chameleon.psi

        val project = psi.project
        val parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(language)

        val builder = PsiBuilderFactory.getInstance()
            .createBuilder(
                project,
                chameleon,
                parserDefinition.createLexer(project),
                language,
                chameleon.chars
            )

        val parser = DLangParser(builder)
        parser.parseStatement()
        return builder.treeBuilt.firstChildNode
    }

    override fun parseContents(chameleon: LighterLazyParseableNode): FlyweightCapableTreeStructure<LighterASTNode?> {
        val psi = chameleon.containingFile!!

        val project = psi.project
        val parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(language)

        val builder = PsiBuilderFactory.getInstance()
            .createBuilder(
                project,
                chameleon,
                parserDefinition.createLexer(project),
                language,
                chameleon.text
            )

        val parser = DLangParser(builder)
        parser.parseStatement()
        return builder.lightTree
    }

    override fun getErrorsCount(seq: CharSequence, fileLanguage: Language, project: Project): Int {
        val parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(language)
        val lexer = parserDefinition.createLexer(project)
        return if(PsiBuilderUtil.hasProperBraceBalance(seq, lexer, DlangTypes.OP_BRACES_LEFT, DlangTypes.OP_BRACES_RIGHT))
            NO_ERRORS
        else
            FATAL_ERROR
    }

    override fun reuseCollapsedTokens(): Boolean = true

}
