package io.github.intellij.dlanguage.features.documentation

import com.intellij.lang.Language
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.lang.documentation.QuickDocHighlightingHelper
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.TokenType
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.childLeafs
import com.intellij.psi.util.elementType
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.documentation.psi.DDocMetaElementTypes.DDOC_COMMENT_LEADING_ASTERISKS
import io.github.intellij.dlanguage.documentation.psi.DlangDocComment
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_COLON
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_COMMENT_DATA
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
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_ORDERED_LIST
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_QUOTE
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_QUOTE_CHAR
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_SIMPLE_EMPHASIS
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_TRIPLE_EMPHASIS
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_UNORDERED_LIST
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_WHITESPACE
import io.github.intellij.dlanguage.features.documentation.psi.DlangDocPsiElement
import io.github.intellij.dlanguage.features.documentation.psi.impl.DDocDescriptionSectionImpl
import io.github.intellij.dlanguage.features.documentation.psi.impl.DDocLinkDeclarationImpl
import io.github.intellij.dlanguage.features.documentation.psi.impl.DDocNamedSectionImpl
import io.github.intellij.dlanguage.features.documentation.psi.impl.DDocSummarySectionImpl
import io.github.intellij.dlanguage.psi.interfaces.VariableDeclaration
import io.github.intellij.dlanguage.utils.*

class DDocGenerator {

    private var renderedDoc = false

    fun generateDoc(element: PsiElement): String {
        renderedDoc = false
        val builder = StringBuilder()

        if (element is FunctionDeclaration ||
            element is VariableDeclaration ||
            element is ClassDeclaration ||
            element is InterfaceDeclaration ||
            element is AliasDeclaration ||
            element is EnumDeclaration ||
            element is StructDeclaration ||
            element is UnionDeclaration ||
            element is TemplateDeclaration)  {
            val elements = element.children.takeWhile { it is DlangDocComment || it is PsiWhiteSpace }.filterIsInstance<DlangDocComment>()
            if (elements.isNotEmpty())
                appendDdoc(builder, elements)
        }
        if (element is EnumMember) {
            // TODO find enum member documentation
        }
        return builder.toString()
    }

    fun generateDocRendered(comment: DlangDocComment): String {
        renderedDoc = true
        val builder = StringBuilder()
        val linksDeclarations = PsiTreeUtil.findChildrenOfType(comment, DDocLinkDeclarationImpl::class.java)
        appendContentSections(builder, comment.sections(), linksDeclarations)
        return builder.toString()
    }

    private fun appendDdoc(builder: StringBuilder, elements: List<DlangDocComment>) {
        val sectionsBuilder = StringBuilder()
        // TODO actually choose which section to add (example copyright to display only for module)
        for (el in elements) {
            val linksDeclarations = PsiTreeUtil.findChildrenOfType(el, DDocLinkDeclarationImpl::class.java)
            if (el.sections().isEmpty())
                continue
            appendContentSections(sectionsBuilder, el.sections(), linksDeclarations)
        }

        if (sectionsBuilder.isBlank())
            return
        builder.append(sectionsBuilder)
    }

    private fun appendContentSections(builder: StringBuilder, sections: Array<PsiElement>, linksDeclarations: Collection<DDocLinkDeclarationImpl>) {
        var i = 0
        // TODO refactor to have section only being sections (not polluted)
        while (sections[i].elementType == DDOC_WHITESPACE || sections[i].elementType == TokenType.WHITE_SPACE || sections[i].elementType == DDOC_COMMENT_LEADING_ASTERISKS) {
            i++
        }
        val summary = sections[i]
        if (summary is DDocSummarySectionImpl) {
            builder.append(DocumentationMarkup.CONTENT_START)
            builder.append("<p>")
            builder.append(buildContent(summary.getSection(), linksDeclarations))
            builder.append("</p>")
            i++
            if (i >= sections.size) {
                builder.append(DocumentationMarkup.CONTENT_END)
                return
            }
            while (sections[i].elementType == DDOC_WHITESPACE || sections[i].elementType == DDOC_COMMENT_LEADING_ASTERISKS) {
                i++
                if (i >= sections.size) {
                    builder.append(DocumentationMarkup.CONTENT_END)
                    return
                }
            }
            if (sections.size > i) {
                while (sections[i].elementType == DDOC_WHITESPACE || sections[i].elementType == TokenType.WHITE_SPACE || sections[i].elementType == DDOC_COMMENT_LEADING_ASTERISKS) {
                    i++
                    if (i >= sections.size) {
                        builder.append(DocumentationMarkup.CONTENT_END)
                        return
                    }
                }
                val description = sections[i]
                if (description is DDocDescriptionSectionImpl) {
                    for (content in description.getSections()) {
                        builder.append("<p>")
                        builder.append(buildContent(content, linksDeclarations))
                        builder.append("</p>")
                    }
                    i++
                }
            }
            builder.append(DocumentationMarkup.CONTENT_END)
        }
        if (i >= sections.size) return
        builder.append(DocumentationMarkup.SECTIONS_START)
        for (section in sections.slice(i until sections.size)) {
            when (section) {
                is DDocNamedSectionImpl -> {
                    builder.append(DocumentationMarkup.SECTION_HEADER_START)
                    builder.append(section.getTitle().text)
                    builder.append(DocumentationMarkup.SECTION_SEPARATOR)
                    val contents = section.getContents()
                    for (content in contents) {
                        builder.append("<p>")
                        builder.append(buildContent(content, linksDeclarations))
                        builder.append("</p>")
                    }
                    builder.append(DocumentationMarkup.SECTION_END)
                    builder.append("</tr>")
                }
            }
        }
        builder.append(DocumentationMarkup.SECTIONS_END)
    }

    private fun buildContent(section: PsiElement, linksDeclarations: Collection<DDocLinkDeclarationImpl>): String {
        val builder = StringBuilder()

        val children = (section as? DlangDocPsiElement)?.getDescriptionElements()?.toList()?: section.childLeafs().toList()
        for (element in children) {
            if (element.elementType == DDOC_COMMENT_LEADING_ASTERISKS)
                continue
            when (element.elementType) {
                DDOC_WHITESPACE -> builder.append(" ")
                DDOC_COMMENT_DATA -> builder.append(element.text)
                DDOC_COLON -> builder.append(element.text)
                DDOC_MACRO_CALL -> builder.append(element.text) // TODO interpret macro (substitute by the value)
                DDOC_HORIZONTAL_RULE -> builder.append("<hr/>")
                DDOC_HEADING -> {
                    val level = element.text.indexOfFirst { it != '#' }
                    builder.append("<h")
                    builder.append(level)
                    builder.append(">")
                    builder.append(buildContent(element, linksDeclarations))
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
                DDOC_TRIPLE_EMPHASIS -> {
                    builder.append("<b><i>")
                    builder.append(element.text.substring(3, element.text.length - 3)) // text without *** ***
                    builder.append("</i></b>")
                }
                DDOC_QUOTE -> {
                    builder.append("<blockquote>")
                    builder.append(buildContent(element, linksDeclarations))
                    builder.append("</blockquote>")
                }
                DDOC_QUOTE_CHAR -> continue // not displayed, already handled in DDOC_QUOTE
                DDOC_LINK -> {
                    builder.append(buildLinkContent(element as DlangDocPsiElement, false, linksDeclarations))
                }
                DDOC_IMAGE -> {
                    val linkContent = (element as DlangDocPsiElement).firstChild.nextSibling as DlangDocPsiElement
                    builder.append(buildLinkContent(linkContent, true, linksDeclarations))
                }
                DDOC_LINK_DECLARATION -> continue // its content is replaced in DDOC_LINKs
                DDOC_ORDERED_LIST -> {
                    builder.append("<ol>")
                    buildListContent(builder, element, linksDeclarations)
                    builder.append("</ol>")
                }
                DDOC_UNORDERED_LIST -> {
                    builder.append("<ul>")
                    buildListContent(builder, element, linksDeclarations)
                    builder.append("</ul>")
                }
                else -> builder.append(element.text)
            }
        }
        return builder.toString()
    }

    private fun buildLinkContent(element: DlangDocPsiElement, isImage: Boolean = false, linksDeclarations: Collection<DDocLinkDeclarationImpl>): String {
        val builder = StringBuilder()
        var text: String? = null
        var referenceTo: String? = null
        var reference: String? = null
        var quote: String? = null
        for (children in element.getDescriptionElements()) {
            when (children.elementType) {
                DDOC_LINK_TEXT -> text = children.text.substring(1, children.text.length - 1) // text without [ ]
                DDOC_LINK_REFERENCE_TO -> referenceTo = children.text
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
            if (referenceTo != null) {
                val target = linksDeclarations.find { it.firstChild.text == referenceTo }
                var referenceToText = ""
                for (e in element.getDescriptionElements()) {
                    referenceToText += e.text
                }
                if (target == null) {
                    // TODO handle reference to code elements
                    builder.append(referenceToText)
                } else {
                    val referencePointer = target.text.substringAfter(":").trim().split(" ", limit = 1)
                    reference = referencePointer.first()
                    if (text == null) {
                        text = referenceTo.substring(1, referenceTo.length - 1) // text without [ ]
                    }
                    addLink(builder, reference, text, quote)
                }
            } else {
                addLink(builder, reference, text, quote)
            }
        }
        return builder.toString()
    }

    private fun addLink(builder: StringBuilder, reference: String?, text: String?, quote: String?) {
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

    private fun buildEmbeddedCodeContent(element: DlangDocPsiElement): String {
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
        val rawLanguage = rawText.substringBefore("\n", missingDelimiterValue = "").trim()
        val codeSnippet = rawText.substringAfter("\n", missingDelimiterValue = rawText)
        val language =  if (rawLanguage.isNotEmpty()) Language.findLanguageByID(rawLanguage)?: DLanguage else DLanguage
        return QuickDocHighlightingHelper.getStyledCodeBlock(element.project, language, codeSnippet )
    }

    private fun buildListContent(builder: StringBuilder, element: PsiElement, linksDeclarations: Collection<DDocLinkDeclarationImpl>) {
        val children = element.children
        for (child in children) {
            builder.append("<li>")
            builder.append(buildContent(child.lastChild, linksDeclarations))
            builder.append("</li>")
        }
    }
}
