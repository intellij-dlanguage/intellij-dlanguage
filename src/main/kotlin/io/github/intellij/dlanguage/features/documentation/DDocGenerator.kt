package io.github.intellij.dlanguage.features.documentation

import com.intellij.lang.Language
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.lang.documentation.DocumentationSettings
import com.intellij.openapi.editor.richcopy.HtmlSyntaxInfoUtil
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_ANONYMOUS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_COLON
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_COMMENT_DATA
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_COMMENT_LEADING_ASTERISKS
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_DOUBLE_EMPHASIS
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_EMBEDDED_CODE
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_EMBEDDED_CODE_DELIMITER
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_HEADING
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_HEADING_CHARS
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_HORIZONTAL_RULE
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_IMAGE
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_INLINE_CODE
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_LINK
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_LINK_DECLARATION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_LINK_INLINE_REFERENCE_TEXT
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_LINK_REFERENCE_TO
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_LINK_TEXT
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_MACRO_CALL
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_QUOTE
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_QUOTE_CHAR
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_SIMPLE_EMPHASIS
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_WHITESPACE
import io.github.intellij.dlanguage.features.documentation.psi.DlangDocComment
import io.github.intellij.dlanguage.features.documentation.psi.DlangDocPsiElement
import io.github.intellij.dlanguage.features.documentation.psi.impl.*
import io.github.intellij.dlanguage.psi.DLanguageDeclaration
import io.github.intellij.dlanguage.psi.DLanguageVariableDeclaration
import io.github.intellij.dlanguage.utils.*
import org.intellij.markdown.html.isWhitespace

class DDocGenerator {

    private var renderedDoc = false

    private val highlightingSaturation: Float = DocumentationSettings.getHighlightingSaturation(renderedDoc)

    fun generateDoc(element: PsiElement): String {
        renderedDoc = false
        val builder = StringBuilder()

        if (element.parent is FunctionDeclaration ||
            element.parent.parent is DLanguageVariableDeclaration ||
            element.parent.parent is ClassDeclaration ||
            element.parent.parent is InterfaceDeclaration ||
            element.parent.parent is AliasDeclaration ||
            element.parent is EnumDeclaration ||
            element.parent is StructDeclaration ||
            element.parent is UnionDeclaration ||
            element.parent is TemplateDeclaration)  {
            val declarationElement = PsiTreeUtil.getParentOfType(element, DLanguageDeclaration::class.java)
            val elements = declarationElement!!.children.takeWhile { it is DlangDocComment || it is PsiWhiteSpace }.filterIsInstance<DlangDocComment>()
            if (elements.isNotEmpty())
                appendDdoc(builder, elements)
        }
        if (element.parent is EnumMember) {
            // TODO find enum member documentation
        }
        return builder.toString()
    }

    fun generateDocRendered(comment: DlangDocComment): String {
        renderedDoc = true
        val builder = StringBuilder()
        appendContentSections(builder, comment.sections())
        return builder.toString()
    }

    private fun appendDdoc(builder: StringBuilder, elements: List<DlangDocComment>) {
        val sectionsBuilder = StringBuilder()
        // TODO actually choose which section to add (example copyright to display only for module)
        for (el in elements) {
            appendContentSections(sectionsBuilder, el.sections())
        }

        if (sectionsBuilder.isBlank())
            return
        builder.append(DocumentationMarkup.CONTENT_START)
        builder.append(sectionsBuilder)
        builder.append(DocumentationMarkup.CONTENT_END)
    }

    private fun appendContentSections(builder: StringBuilder, sections: Array<PsiElement>) {
        var startedSection = false
        for (section in sections) {
            when (section) {
                is DDocAnonymousSectionImpl -> {
                    if (startedSection) {
                        startedSection = false
                        builder.append(DocumentationMarkup.SECTIONS_END)
                    }
                    builder.append("<p>")
                    builder.append(buildContent(section.getContent()!!))
                    builder.append("</p>")
                }

                is DDocDescriptionSectionImpl -> {
                    for (anonymousSection in section.getSections()) {
                        builder.append("<p>")
                        builder.append(buildContent(anonymousSection.getContent()!!))
                        builder.append("</p>")
                    }
                }

                is DDocNamedSectionImpl -> {
                    if (!startedSection) {
                        startedSection = true
                        builder.append(DocumentationMarkup.SECTIONS_START)
                    }
                    builder.append(DocumentationMarkup.SECTION_HEADER_START)
                    builder.append(section.getTitle()!!.text)
                    builder.append(DocumentationMarkup.SECTION_SEPARATOR)
                    builder.append(buildContent(section.getContent()!!))
                    builder.append(DocumentationMarkup.SECTION_END)
                    builder.append("</tr>")
                }
            }
        }
    }

    private fun buildContent(section: PsiElement): String {
        val builder = StringBuilder()

        val childs = (section as? DlangDocPsiElement)?.getDescriptionElements()?: section.children
        for (element in childs) {
            if (element.elementType == DDOC_COMMENT_LEADING_ASTERISKS)
                continue
            when (element.elementType) {
                DDOC_WHITESPACE -> builder.append(" ")
                DDOC_ANONYMOUS_SECTION -> builder.append(buildContent(element))
                DDOC_COMMENT_DATA -> builder.append(element.text)
                DDOC_COLON -> builder.append(element.text)
                DDOC_MACRO_CALL -> builder.append(element.text) // TODO interpret macro (substitute by the value)
                DDOC_HORIZONTAL_RULE -> builder.append("<hr/>")
                DDOC_HEADING -> {
                    val level = element.text.indexOfFirst { it != '#' }
                    builder.append("<h")
                    builder.append(level)
                    builder.append(">")
                    builder.append(buildContent(element))
                    builder.append("</h")
                    builder.append(level)
                    builder.append(">")
                }
                DDOC_HEADING_CHARS -> continue // not displayed, it’s delimiter of heading
                DDOC_INLINE_CODE -> {
                    builder.append("<code>")
                    builder.append(StringUtil.escapeXmlEntities(element.text.substring(1, element.text.length - 1))) // text without ` `
                    builder.append("</code>")
                }
                DDOC_EMBEDDED_CODE -> {
                   builder.append(buildEmbeddedCodeContent(element as DlangDocPsiElement))
                }
                DDOC_SIMPLE_EMPHASIS -> {
                    builder.append("<i>")
                    builder.append(element.text.substring(1, element.text.length - 1)) // text without * *
                    builder.append("</i>")
                }
                DDOC_DOUBLE_EMPHASIS -> {
                    builder.append("<b>")
                    builder.append(element.text.substring(2, element.text.length - 2)) // text without ** **
                    builder.append("</b>")
                }
                DDOC_QUOTE -> {
                    builder.append("<blockquote>")
                    builder.append(buildContent(element))
                    builder.append("</blockquote>")
                }
                DDOC_QUOTE_CHAR -> continue // not displayed, already handled in DDOC_QUOTE
                DDOC_LINK -> {
                    builder.append(buildLinkContent(element as DlangDocPsiElement, false))
                }
                DDOC_IMAGE -> {
                    val linkContent = (element as DlangDocPsiElement).firstChild.nextSibling as DlangDocPsiElement
                    builder.append(buildLinkContent(linkContent, true))
                }
                DDOC_LINK_DECLARATION -> continue // its content is replaced in DDOC_LINKs
                else -> builder.append(element.text)
            }
        }
        return builder.toString()
    }

    private fun buildLinkContent(element: DlangDocPsiElement, isImage: Boolean = false): String {
        val builder = StringBuilder()
        var text: String? = null
        var reference: String? = null
        var quote: String? = null
        for (children in element.getDescriptionElements()) {
            when (children.elementType) {
                DDOC_LINK_TEXT -> text = children.text.substring(1, children.text.length - 1) // text without [ ]
                DDOC_LINK_REFERENCE_TO -> reference = children.text.substring(1, children.text.length - 1) // text without [ ]
                DDOC_LINK_INLINE_REFERENCE_TEXT -> {
                    reference = children.text.substring(1, children.text.length - 1)  // text without ( )
                    val quoteIdx = reference.indexOf('\'')
                    if (quoteIdx > 0 && quoteIdx < reference.length) {
                        quote = reference.substring(quoteIdx + 1 , reference.length)
                    }
                }
            }
        }
        if (isImage) {
            builder.append("<img src=\"")
            builder.append(reference)
            builder.append("\"")
            if (text != null) {
                builder.append(" alt=\"")
                builder.append(text)
                builder.append("\"")
            }
            builder.append(">")
            builder.append("</img>")
        }
        else {
            // TODO resolve reference and point to it (take care of reference empty case: [Ref][])
            builder.append("<a href=\"")
            builder.append(reference)
            builder.append("\"")
            if (quote != null) {
                builder.append(" title=\"")
                builder.append(quote)
                builder.append("\"")
            }
            builder.append(">")
            builder.append(text ?: reference)
            builder.append("</a>")
        }
        return builder.toString()
    }

    private fun buildEmbeddedCodeContent(element: DlangDocPsiElement): String {
        val builder = StringBuilder()
        builder.append("<pre>")
        builder.append("<code style='font-size:")
        builder.append(DocumentationSettings.getMonospaceFontSizeCorrection(renderedDoc))
        builder.append("%;'>")
        val elements = element.getDescriptionElements()
        val rawTextBuilder = StringBuilder()
        for (psiElement in elements) {
            val txt = when(psiElement.elementType) {
                DDOC_QUOTE_CHAR -> ""
                DDOC_EMBEDDED_CODE_DELIMITER -> ""
                DDOC_COMMENT_LEADING_ASTERISKS -> ""
                else -> psiElement.text
            }
            rawTextBuilder.append(txt)
        }
        val rawText = rawTextBuilder.toString()
        val codeSnippet = rawText.substring(rawText.indexOf("\n"))
        val rawLanguage = rawText.substring(0, rawText.indexOf("\n")).trim()
        val language =  if (rawLanguage.isNotEmpty()) Language.findLanguageByID(rawLanguage)?: DLanguage else DLanguage
        HtmlSyntaxInfoUtil.appendHighlightedByLexerAndEncodedAsHtmlCodeSnippet(
            builder, element.project, language, codeSnippet, highlightingSaturation
        )
        builder.append("</code>")
        builder.append("</pre>")

        return builder.toString()
    }
}
