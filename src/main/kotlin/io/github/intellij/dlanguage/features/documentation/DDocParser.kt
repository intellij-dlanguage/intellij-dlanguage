package io.github.intellij.dlanguage.features.documentation

import com.intellij.lang.ASTNode
import com.intellij.lang.LightPsiParser
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import io.github.intellij.dlanguage.documentation.psi.DDocMetaElementTypes.DDOC_COMMENT_END
import io.github.intellij.dlanguage.documentation.psi.DDocMetaElementTypes.DDOC_COMMENT_LEADING_ASTERISKS
import io.github.intellij.dlanguage.documentation.psi.DDocMetaElementTypes.DDOC_COMMENT_START

class DDocParser : PsiParser, LightPsiParser {
    override fun parse(root: IElementType, builder: PsiBuilder): ASTNode {
        parseLight(root, builder)
        return builder.treeBuilt
    }

    override fun parseLight(root: IElementType, builder: PsiBuilder) {
       DDocParserImpl(builder).parse(root)
    }
}

private class DDocParserImpl(private val builder: PsiBuilder) {

    fun parse(root: IElementType) {
        builder.enforceCommentTokens(SKIP_TOKENS)
        val marker = builder.mark()
        if (builder.tokenType == DDOC_COMMENT_START)
            builder.advanceLexer()
        parseSections()
        // Swallow all ending tokens that may exist
        while (!builder.eof()) {
            builder.advanceLexer()
        }
        marker.done(root)
    }

    private fun isBlankToken(tokenType: IElementType? = builder.tokenType): Boolean {
        return when(tokenType) {
            DDocElementTypes.DDOC_WHITESPACE, TokenType.WHITE_SPACE -> true
            else -> false
        }
    }

    private fun isEndOfComment(): Boolean {
        return builder.eof() || builder.tokenType == DDOC_COMMENT_END
    }

    private fun hasNewLine(): Boolean {
        return isBlankToken() && builder.tokenText?.contains("\n") ?: false
    }

    private fun has2NewLine(): Boolean {
        if (!isBlankToken()) return false
        var newLineCount = builder.tokenText?.count { it == '\n' } ?: 0
        var i = 1
        var next = builder.rawLookup(i)
        // Note: could avoid this if-block if it was able to get text of next token
        if (next == DDOC_COMMENT_LEADING_ASTERISKS) {
            i++
            next = builder.rawLookup(i)
        }
        if (next == DDocElementTypes.DDOC_WHITESPACE) {
            val text = builder.rawLookupText(i)
            newLineCount += text.count { it == '\n' }
        }
        return newLineCount >= 2
    }

    private fun consumeBlankLines() {
        while (isBlankToken(getTokenType()) && !isEndOfComment()) {
            builder.advanceLexer()
        }
    }

    private fun PsiBuilder.rawLookupText(steps: Int): CharSequence {
        val start = rawTokenTypeStart(steps)
        val end = rawTokenTypeStart(steps + 1)
        return if (start == -1 || end == -1) "" else originalText.subSequence(start, end)
    }

    private fun getTokenType(): IElementType? {
        val tokenType = builder.tokenType
        if (builder.tokenType == DDocElementTypes.DDOC_WHITESPACE) {
            builder.remapCurrentToken(TokenType.WHITE_SPACE)
        }
        return tokenType
    }

    private fun hasSectionName(): Boolean {
        val marker = builder.mark()
        val next = builder.lookAhead(1)
        val nextNext = builder.lookAhead(2)
        var res = next == DDocElementTypes.DDOC_COLON && builder.tokenText?.contains(" ") != true
            // To reject `http://` eagerly
            && (nextNext == DDocElementTypes.DDOC_COMMENT_DATA || isBlankToken(nextNext))
        if (res) {
            builder.advanceLexer() // section name
            builder.advanceLexer() // `:`
            res = if (hasNewLine()) {
                true
            } else {
                builder.tokenText?.startsWith(" ") == true
            }
        }
        marker.rollbackTo()
        return res
    }

    private fun getSectionName(): String {
        //assert (hasSectionName())
        val marker = builder.mark()
        val name = builder.tokenText!!
        builder.advanceLexer() // section name
        builder.advanceLexer() // `:`
        marker.done(DDocElementTypes.DDOC_SECTION_TITLE)
        return name
    }

    private fun parseSections() {
        consumeBlankLines()
        if (isEndOfComment())
            return
        parseSummary()
        consumeBlankLines()
        if (isEndOfComment())
            return
        parseDescription()
        consumeBlankLines()
        while (!isEndOfComment()) {
            parseNamedSection()
            consumeBlankLines()
        }
    }

    private fun parseNamedSection() {
        val marker = builder.mark()
        val sectionName: String = getSectionName()
        val markerType = when (sectionName.uppercase()) {
            "AUTHORS" -> DDocElementTypes.DDOC_AUTHORS_SECTION
            "BUGS" -> DDocElementTypes.DDOC_BUGS_SECTION
            "DATE" -> DDocElementTypes.DDOC_DATE_SECTION
            "DEPRECATED" -> DDocElementTypes.DDOC_DEPRECATED_SECTION
            "EXAMPLES" -> DDocElementTypes.DDOC_EXAMPLES_SECTION
            "HISTORY" -> DDocElementTypes.DDOC_HISTORY_SECTION
            "LICENSE" -> DDocElementTypes.DDOC_LICENSE_SECTION
            "RETURNS" -> DDocElementTypes.DDOC_RETURNS_SECTION
            "SEE_ALSO" -> DDocElementTypes.DDOC_SEE_ALSO_SECTION
            "STANDARDS" -> DDocElementTypes.DDOC_STANDARDS_SECTION
            "THROWS" -> DDocElementTypes.DDOC_THROWS_SECTION
            "VERSION" -> DDocElementTypes.DDOC_VERSION_SECTION
            "PARAMS" -> DDocElementTypes.DDOC_PARAMS_SECTION
            "MACROS" -> DDocElementTypes.DDOC_MACROS_SECTION
            "COPYRIGHT" -> DDocElementTypes.DDOC_COPYRIGHT_SECTION
            else -> DDocElementTypes.DDOC_NAMED_SECTION
        }
        when(markerType) {
            DDocElementTypes.DDOC_MACROS_SECTION -> parseMacroSection(marker, markerType)
            DDocElementTypes.DDOC_PARAMS_SECTION -> parseParamsSection(marker, markerType)
            else -> {
                do {
                    parseParagraph()
                    consumeBlankLines()
                } while (!isEndOfComment() && !hasSectionName())
                marker.done(markerType)
            }
        }
    }

    private fun parseSummary() {
        if (hasSectionName())
            return
        val marker = builder.mark()
        parseParagraph()
        marker.done(DDocElementTypes.DDOC_SUMMARY_SECTION)
    }

    private fun parseDescription() {
        if (isEndOfComment() || hasSectionName())
            return
        val marker = builder.mark()
        while(!isEndOfComment() && !hasSectionName()) {
            parseParagraph()
            consumeBlankLines()
        }
        marker.done(DDocElementTypes.DDOC_DESCRIPTION_SECTION)
    }

    private fun parseParagraph() {
        val contentMarker = builder.mark()
        var continueParagraph = true
        while (!isEndOfComment() && !hasSectionName() && continueParagraph) {
            val stopSection = parseItem()
            if (stopSection) {
                continueParagraph = false
            }
        }
        contentMarker.done(DDocElementTypes.DDOC_SECTION_PARAGRAPH)
    }

    /**
     * Return: true if we reached the end of the section, false if we should continue
     */
    private fun parseItem(): Boolean {
        val tokenType = getTokenType()
        if (tokenType == DDocElementTypes.DDOC_WHITESPACE) {
            val endOfParagraph = has2NewLine()
            consumeBlankLines()
            return endOfParagraph
        }
        when (tokenType) {
            DDOC_COMMENT_END -> builder.advanceLexer()
            DDocElementTypes.DDOC_COMMENT_DATA -> builder.advanceLexer()
            DDocElementTypes.DDOC_MACRO_OPEN -> parseMacroCall()
            DDocElementTypes.DDOC_QUOTE_CHAR -> return parseQuote()
            DDocElementTypes.DDOC_HEADING_CHARS -> parseHeading()
            DDocElementTypes.DDOC_LEFT_BRACKET -> parseLink()
            DDocElementTypes.DDOC_EXCLAMATION_MARK -> parseImage()
            DDocElementTypes.DDOC_EMBEDDED_CODE_DELIMITER -> parseEmbeddedCode()
            DDocElementTypes.DDOC_ORDERED_LIST_POINT -> parseOrderedList()
            DDocElementTypes.DDOC_UNORDERED_LIST_POINT -> parseUnorderedList()
            else -> builder.advanceLexer()
        }
        return false
    }

    private fun parseMacroCall() {
        val marker = builder.mark()
        assert(builder.tokenType == DDocElementTypes.DDOC_MACRO_OPEN)
        builder.advanceLexer()
         while (!isEndOfComment() && builder.tokenType != DDocElementTypes.DDOC_MACRO_END) {
            if (builder.tokenType == DDocElementTypes.DDOC_MACRO_OPEN) {
                parseMacroCall()
            } else {
                builder.advanceLexer()
            }
        }
        if (!isEndOfComment())
            builder.advanceLexer()
        marker.done(DDocElementTypes.DDOC_MACRO_CALL)
    }

    private fun parseQuote(): Boolean {
        val marker = builder.mark()
        builder.advanceLexer()
        // list, tables, embedded code, quote, etc. may be part of a quote
        var sectionEnd = false
        while (!sectionEnd && !isEndOfComment()) {
            if (builder.tokenType == DDocElementTypes.DDOC_QUOTE_CHAR) {
                builder.advanceLexer() // skip this one as it’s a continuation quote char
                if (builder.tokenType == DDocElementTypes.DDOC_WHITESPACE && builder.tokenText?.contains('\n') != true) {
                    // skip whitespace if next token on the same line to properly detect quote in quote
                    builder.advanceLexer()
                }
            }
            sectionEnd = parseItem()
        }
        marker.done(DDocElementTypes.DDOC_QUOTE)
        return sectionEnd
    }

    private fun parseLink() {
        if (!isBlankToken(builder.rawLookup(-1)) && !builder.rawLookupText(-1).endsWith(" ")) {
            // no space before, it’s not a link (can be something like `int[Obj]`)
            builder.advanceLexer()
            return
        }
        parseLinkContent()
    }

    private fun parseLinkContent(): Boolean {
        val linkMarker = builder.mark()
        val marker1 = builder.mark()
        builder.advanceLexer()
        while (!isEndOfComment() && (builder.tokenType == DDocElementTypes.DDOC_WHITESPACE
                || builder.tokenType == DDocElementTypes.DDOC_COMMENT_DATA
                || builder.tokenType == DDocElementTypes.DDOC_QUOTE)) {
            if (builder.tokenType == DDocElementTypes.DDOC_QUOTE) {
                parseQuote()
            } else {
                builder.advanceLexer()
            }
        }
        if (builder.tokenType != DDocElementTypes.DDOC_RIGHT_BRACKET) {
            // not a link (`[` but not `]`)
            marker1.drop()
            linkMarker.drop()
            builder.advanceLexer()
            return false
        }
        builder.advanceLexer()
        when (builder.tokenType) {
            DDocElementTypes.DDOC_LEFT_BRACKET,
            DDocElementTypes.DDOC_LEFT_PARENTHESES -> marker1.done(DDocElementTypes.DDOC_LINK_TEXT)
            DDocElementTypes.DDOC_COLON -> marker1.done(DDocElementTypes.DDOC_LINK_NAME)
            else -> marker1.done(DDocElementTypes.DDOC_LINK_REFERENCE_TO)
        }
        when (builder.tokenType) {
            DDocElementTypes.DDOC_LEFT_BRACKET -> {
                val markerReference = builder.mark()
                builder.advanceLexer()
                while (!isEndOfComment() && (builder.tokenType == DDocElementTypes.DDOC_WHITESPACE
                        || builder.tokenType == DDocElementTypes.DDOC_COMMENT_DATA)) {
                    builder.advanceLexer()
                }
                if (builder.tokenType != DDocElementTypes.DDOC_RIGHT_BRACKET) {
                    markerReference.rollbackTo()
                    linkMarker.done(DDocElementTypes.DDOC_LINK)
                    return true
                }
                builder.advanceLexer()
                markerReference.done(DDocElementTypes.DDOC_LINK_REFERENCE_TO)
                linkMarker.done(DDocElementTypes.DDOC_LINK)
            }
            DDocElementTypes.DDOC_LEFT_PARENTHESES -> {
                val markerRawUrl = builder.mark()
                builder.advanceLexer()
                while (!isEndOfComment() && (builder.tokenType == DDocElementTypes.DDOC_WHITESPACE
                        || builder.tokenType == DDocElementTypes.DDOC_COMMENT_DATA
                        || builder.tokenType == DDocElementTypes.DDOC_COLON)) {
                    builder.advanceLexer()
                }
                if (builder.tokenType != DDocElementTypes.DDOC_RIGHT_PARENTHESES) {
                    markerRawUrl.rollbackTo()
                    linkMarker.done(DDocElementTypes.DDOC_LINK)
                    return true
                }
                builder.advanceLexer()
                markerRawUrl.done(DDocElementTypes.DDOC_LINK_INLINE_REFERENCE_TEXT)
                linkMarker.done(DDocElementTypes.DDOC_LINK)
            }
            DDocElementTypes.DDOC_COLON -> {
                builder.advanceLexer()
                while (!isEndOfComment() && !hasNewLine()) {
                    builder.advanceLexer()
                }
                linkMarker.done(DDocElementTypes.DDOC_LINK_DECLARATION)
            }
            else -> {
                linkMarker.done(DDocElementTypes.DDOC_LINK)
            }
        }
        return true
    }

    private fun parseImage() {
        val marker = builder.mark()
        builder.advanceLexer()
        val wasLink = parseLinkContent()
        if (wasLink) {
            marker.done(DDocElementTypes.DDOC_IMAGE)
        } else {
            marker.drop()
        }
    }

    private fun parseEmbeddedCode() {
        val marker = builder.mark()
        builder.advanceLexer()
        while (!isEndOfComment() && builder.tokenType != DDocElementTypes.DDOC_EMBEDDED_CODE_DELIMITER) {
            builder.advanceLexer()
        }
        if (!isEndOfComment()) {
            builder.advanceLexer()
        }
        marker.done(DDocElementTypes.DDOC_EMBEDDED_CODE)
    }

    private fun parseHeading() {
        assert(builder.tokenType == DDocElementTypes.DDOC_HEADING_CHARS)
        val marker = builder.mark()
        builder.advanceLexer()
        while (!isEndOfComment() && builder.tokenType != DDocElementTypes.DDOC_HEADING_CHARS
            && builder.tokenType != DDocElementTypes.DDOC_WHITESPACE) {
            builder.advanceLexer()
        }
        if (!isEndOfComment() && builder.tokenType != DDocElementTypes.DDOC_WHITESPACE) {
            builder.advanceLexer()
        }
        marker.done(DDocElementTypes.DDOC_HEADING)
    }

    private fun parseOrderedList() {
        assert(builder.tokenType === DDocElementTypes.DDOC_ORDERED_LIST_POINT)
        val marker = builder.mark()
        val rootSpaceLevel = builder.rawLookupText(-1).split("\n").last().count { it == ' ' }
        while(!isEndOfComment() && !has2NewLine()) {
            consumeBlankLines()
            val spaceLevel = builder.rawLookupText(-1).split("\n").last().count { it == ' ' }
            if (spaceLevel < rootSpaceLevel
                || builder.tokenType !== DDocElementTypes.DDOC_ORDERED_LIST_POINT) {
                break
            }
            assert (builder.tokenType === DDocElementTypes.DDOC_ORDERED_LIST_POINT)
            parseListContent(rootSpaceLevel)
        }
        marker.done(DDocElementTypes.DDOC_ORDERED_LIST)
    }

    private fun parseUnorderedList() {
        val marker = builder.mark()
        val rootSpaceLevel = builder.rawLookupText(-1).split("\n").last().count { it == ' ' }
        while(!isEndOfComment() && !has2NewLine()) {
            consumeBlankLines()
            val spaceLevel = builder.rawLookupText(-1).split("\n").last().count { it == ' ' }
            if (spaceLevel < rootSpaceLevel
                || builder.tokenType !== DDocElementTypes.DDOC_UNORDERED_LIST_POINT) {
                break
            }
            assert (builder.tokenType === DDocElementTypes.DDOC_UNORDERED_LIST_POINT)
            parseListContent(rootSpaceLevel)
        }
        marker.done(DDocElementTypes.DDOC_UNORDERED_LIST)
    }

    private fun parseListContent(rootSpaceLevel: Int) {
        val m = builder.mark()
        builder.advanceLexer() // 1. or +
        val contentMarker = builder.mark()
        parseItem()
        while (!isEndOfComment() && !has2NewLine()) {
            consumeBlankLines()
            val previousToken = builder.rawLookup(-1)
            if (isBlankToken(previousToken) || previousToken === DDOC_COMMENT_LEADING_ASTERISKS) {
                val spaceLevel = if (isBlankToken(previousToken)) builder.rawLookupText(-1).split("\n").last().count { it == ' ' } else 0
                if (spaceLevel <= rootSpaceLevel) {
                    break
                }
            }
            parseItem()
        }
        contentMarker.done(DDocElementTypes.DDOC_LIST_ITEM_CONTENT)
        m.done(DDocElementTypes.DDOC_LIST_ITEM)
    }

    private fun parseMacroSection(marker: PsiBuilder.Marker, markerType: IElementType) {
        // FIXME actually parse the real section (same as praams)
        do {
            parseParagraph()
            consumeBlankLines()
        } while (!isEndOfComment() && !hasSectionName())
        marker.done(markerType)
    }

    private fun parseParamsSection(marker: PsiBuilder.Marker, markerType: IElementType) {
        // FIXME actually parse the real section (same as praams)
        do {
            parseParagraph()
            consumeBlankLines()
        } while (!isEndOfComment() && !hasSectionName())
        marker.done(markerType)
    }

}

private val SKIP_TOKENS = TokenSet.create(DDOC_COMMENT_LEADING_ASTERISKS)
