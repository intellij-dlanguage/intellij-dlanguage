package io.github.intellij.dlanguage.features.documentation

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
import io.github.intellij.dlanguage.documentation.DDocLanguage
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_ANONYMOUS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_AUTHORS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_BUGS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_COPYRIGHT_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_DATE_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_DEPRECATED_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_DESCRIPTION_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_EXAMPLES_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_HISTORY_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_LICENSE_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_LINK_DECLARATION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_MACROS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_NAMED_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_PARAMS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_RETURNS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_SEE_ALSO_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_STANDARDS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_SUMMARY_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_THROWS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_VERSION_SECTION
import io.github.intellij.dlanguage.features.documentation.psi.impl.*
import io.github.intellij.dlanguage.psi.DlangPsiFileImpl

class DDocParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?): Lexer = DDocLexer()

    override fun createParser(project: Project?): PsiParser = DDocParser()

    override fun getFileNodeType(): IFileElementType = IFileElementType(DDocLanguage)

    override fun getCommentTokens(): TokenSet = TokenSet.EMPTY

    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

    override fun createElement(node: ASTNode): PsiElement {
        return when(node.elementType) {
            DDOC_ANONYMOUS_SECTION -> DDocAnonymousSectionImpl(node)
            DDOC_NAMED_SECTION -> DDocNamedSectionImpl(node)
            DDOC_SUMMARY_SECTION -> DDocSummarySectionImpl(node)
            DDOC_DESCRIPTION_SECTION -> DDocDescriptionSectionImpl(node)
            DDOC_AUTHORS_SECTION -> DDocNamedSectionImpl(node)
            DDOC_BUGS_SECTION -> DDocNamedSectionImpl(node)
            DDOC_DATE_SECTION -> DDocNamedSectionImpl(node)
            DDOC_DEPRECATED_SECTION -> DDocNamedSectionImpl(node)
            DDOC_EXAMPLES_SECTION -> DDocNamedSectionImpl(node)
            DDOC_HISTORY_SECTION -> DDocNamedSectionImpl(node)
            DDOC_LICENSE_SECTION -> DDocNamedSectionImpl(node)
            DDOC_RETURNS_SECTION -> DDocNamedSectionImpl(node)
            DDOC_SEE_ALSO_SECTION -> DDocNamedSectionImpl(node)
            DDOC_STANDARDS_SECTION -> DDocNamedSectionImpl(node)
            DDOC_THROWS_SECTION -> DDocNamedSectionImpl(node)
            DDOC_VERSION_SECTION -> DDocNamedSectionImpl(node)

            DDOC_COPYRIGHT_SECTION -> DDocNamedSectionImpl(node)
            DDOC_PARAMS_SECTION -> DDocParamsSectionImpl(node)
            DDOC_MACROS_SECTION -> DDocMacroSectionImpl(node)

            DDOC_LINK_DECLARATION -> DDocLinkDeclarationImpl(node)
            else -> return DlangDocPsiElementImpl(node)
        }
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile = DlangPsiFileImpl(viewProvider) // TODO can actually be DDocFile (.dd)

}
