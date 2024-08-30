package io.github.intellij.dlanguage.documentation

import com.intellij.lang.ASTNode
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.lang.PsiBuilderFactory
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.tree.ILazyParseableElementType
import io.github.intellij.dlanguage.DLangParserDefinition
import io.github.intellij.dlanguage.documentation.psi.DlangDocTypes
import io.github.intellij.dlanguage.documentation.psi.impl.DlangDocCommentImpl

class DlangDocCommentType(debugName: String) : ILazyParseableElementType(debugName, DDocLanguage) {

    override fun doParseContents(chameleon: ASTNode, psi: PsiElement): ASTNode? {
        val project = psi.project
        val parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(language)

        // Happens in parser test where the DDoc language definition is not injected
        // We don’t want to parse the comments in this case anyway, the comments parsing
        // is done in another tests.
        if (parserDefinition is DLangParserDefinition) {
            val root = DlangDocCommentImpl(this, null)
            root.rawAddChildrenWithoutNotifications(LeafPsiElement(DlangDocTypes.docDataType, chameleon.chars))
            return root.firstChildNode
        }

        val builder = PsiBuilderFactory.getInstance()
            .createBuilder(
                project,
                chameleon,
                parserDefinition.createLexer(project),
                language,
                chameleon.text
            )

        return parserDefinition
            .createParser(project)
            .parse(this, builder)
            .firstChildNode
    }

    override fun createNode(text: CharSequence?): ASTNode = DlangDocCommentImpl(this, text)

    override fun toString(): String = "DlangTokenType." + super.toString()
}
