package io.github.intellij.dlanguage.features.documentation

import com.intellij.lang.ASTNode
import com.intellij.lang.LightPsiParser
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.tree.IElementType
import io.github.intellij.dlanguage.documentation.psi.DDocMetaElementTypes.DDOC_COMMENT_END
import io.github.intellij.dlanguage.documentation.psi.DDocMetaElementTypes.DDOC_COMMENT_LEADING_ASTERISKS
import io.github.intellij.dlanguage.documentation.psi.DDocMetaElementTypes.DDOC_COMMENT_START
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_ANONYMOUS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_AUTHORS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_BUGS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_COLON
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_COMMENT_DATA
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_COPYRIGHT_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_DATE_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_DEPRECATED_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_DESCRIPTION_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_EMBEDDED_CODE
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_EMBEDDED_CODE_DELIMITER
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_EXAMPLES_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_EXCLAMATION_MARK
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_HEADING
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_HEADING_CHARS
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_HISTORY_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_HORIZONTAL_RULE
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_IMAGE
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_LINK_INLINE_REFERENCE_TEXT
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_LEFT_BRACKET
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_LEFT_PARENTHESES
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_LICENSE_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_LINK
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_LINK_DECLARATION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_LINK_NAME
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_LINK_TEXT
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_MACROS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_MACRO_CALL
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_MACRO_END
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_MACRO_OPEN
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_NAMED_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_PARAMS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_QUOTE
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_QUOTE_CHAR
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_LINK_REFERENCE_TO
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_RETURNS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_RIGHT_BRACKET
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_RIGHT_PARENTHESES
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_SECTION_CONTENT
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_SECTION_TITLE
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_SEE_ALSO_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_STANDARDS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_SUMMARY_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_THROWS_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_VERSION_SECTION
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_WHITESPACE

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

    private var hasLineBreak = false
    private var hasSomeData = false

    fun parse(root: IElementType) {
        val marker = builder.mark()
        // TODO handle ddoc (.dd) files that are not comments and have ddoc at the beginning
        if (builder.tokenType == DDOC_COMMENT_START)
            builder.advanceLexer()
        parseSections()
        while (!builder.eof()) {
            builder.advanceLexer()
        }
        marker.done(root)
    }

    private fun isBlankToken(): Boolean {
        return when(builder.tokenType) {
            DDOC_COMMENT_LEADING_ASTERISKS, DDOC_WHITESPACE -> true
            else -> false
        }
    }

    private fun isEndOfComment(): Boolean {
        return builder.eof() || builder.tokenType == DDOC_COMMENT_END
    }

    private fun hasNewLine(): Boolean {
        return builder.tokenType == DDOC_WHITESPACE && builder.tokenText?.contains("\n") ?: false
    }

    private fun has2NewLine(): Boolean {
        return builder.tokenType == DDOC_WHITESPACE && (builder.tokenText?.count { it == '\n' } ?: 0) >= 2
    }

    private fun consumeBlankLines() {
        while (!isEndOfComment() && isBlankToken()) {
            builder.advanceLexer()
        }
    }


    private fun updateLineBreakFlagAfterAsterisks() {
        if (hasNewLine())
            hasLineBreak = true
    }

    private fun updateLineBreakFlagAfterWhitespace() {
        if (has2NewLine())
            hasLineBreak = true
    }

    private fun hasSectionName(): Boolean {
        val marker = builder.mark()
        while (!isEndOfComment() && builder.tokenType == DDOC_WHITESPACE)
            builder.advanceLexer()
        var res = builder.lookAhead(1) == DDOC_COLON && builder.tokenText?.contains(" ") != true
            && (builder.lookAhead(2) == DDOC_COMMENT_DATA || builder.lookAhead(2) == DDOC_WHITESPACE)
        if (res) {
            builder.advanceLexer()
            builder.advanceLexer()
            res = if (builder.tokenType == DDOC_WHITESPACE && hasNewLine()) {
                true
            } else {
                builder.tokenText?.startsWith(" ") == true
            }
        }
        marker.rollbackTo()
        return res
    }

    private fun getSectionName(): String? {
        if (!hasSectionName()) {
            return null
        }
        val marker = builder.mark()
        val name = builder.tokenText!!
        builder.advanceLexer()
        builder.advanceLexer()
        marker.done(DDOC_SECTION_TITLE)
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
        if (isEndOfComment())
            return
        while (!isEndOfComment()) {
            parseSectionPart()
            consumeBlankLines()
        }
    }

    private fun parseSectionPart() {
        val marker = builder.mark()
        val sectionName: String? = getSectionName()
        val markerType = when (sectionName?.uppercase()) {
            null -> DDOC_ANONYMOUS_SECTION
            "AUTHORS" -> DDOC_AUTHORS_SECTION
            "BUGS" -> DDOC_BUGS_SECTION
            "DATE" -> DDOC_DATE_SECTION
            "DEPRECATED" -> DDOC_DEPRECATED_SECTION
            "EXAMPLES" -> DDOC_EXAMPLES_SECTION
            "HISTORY" -> DDOC_HISTORY_SECTION
            "LICENSE" -> DDOC_LICENSE_SECTION
            "RETURNS" -> DDOC_RETURNS_SECTION
            "SEE_ALSO" -> DDOC_SEE_ALSO_SECTION
            "STANDARDS" -> DDOC_STANDARDS_SECTION
            "THROWS" -> DDOC_THROWS_SECTION
            "VERSION" -> DDOC_VERSION_SECTION
            "PARAMS" -> DDOC_PARAMS_SECTION
            "MACROS" -> DDOC_MACROS_SECTION
            "COPYRIGHT" -> DDOC_COPYRIGHT_SECTION
            else -> DDOC_NAMED_SECTION
        }
        when(markerType) {
            DDOC_MACROS_SECTION -> parseMacroSection(marker, markerType)
            DDOC_PARAMS_SECTION -> parseParamsSection(marker, markerType)
            else ->
                parseSection(marker, markerType)
        }
    }

    private fun parseSummary() {
        if (isEndOfComment() || hasSectionName())
            return
        parseSection(builder.mark(), DDOC_SUMMARY_SECTION)
    }

    private fun parseDescription() {
        if (isEndOfComment() || hasSectionName())
            return
        val marker = builder.mark()
        while(!isEndOfComment() && !hasSectionName()) {
            parseSection(builder.mark(), DDOC_ANONYMOUS_SECTION)
            consumeBlankLines()
        }
        marker.done(DDOC_DESCRIPTION_SECTION)
    }

    private fun parseSection(marker: PsiBuilder.Marker, markerType: IElementType) {
        val contentMarker = builder.mark()
        var continueParagraph = true
        hasSomeData = false
        while (!isEndOfComment() && continueParagraph) {
            if (hasSomeData && hasSectionName()){
                continueParagraph = false
            } else {
                parseSectionContent()
                if (hasLineBreak || hasSectionName()) {
                    continueParagraph = false
                }
            }
        }
        if (hasSomeData) {
            contentMarker.done(DDOC_SECTION_CONTENT)
            marker.done(markerType)
        } else {
            contentMarker.drop()
            marker.drop()
        }
    }

    private fun parseSectionContent() {
        val tokenType = builder.tokenType
        when (tokenType) {
            // In case where no leading asterisk is in the comment, then check for 2 newlines
            DDOC_WHITESPACE -> {
                updateLineBreakFlagAfterWhitespace()
                builder.advanceLexer()
                return
            }
        }
        when (tokenType) {
            DDOC_COMMENT_LEADING_ASTERISKS,
                DDOC_COMMENT_END -> builder.advanceLexer()
            DDOC_COMMENT_DATA -> builder.advanceLexer()
            DDOC_MACRO_OPEN -> parseMacroCall()
            DDOC_QUOTE_CHAR -> parseQuote()
            DDOC_HEADING_CHARS -> parseHeading()
            DDOC_LEFT_BRACKET -> parseLink()
            DDOC_EXCLAMATION_MARK -> parseImage()
            DDOC_EMBEDDED_CODE_DELIMITER -> parseEmbeddedCode()
            else -> builder.advanceLexer()
        }
        when(tokenType) {
            DDOC_COMMENT_LEADING_ASTERISKS -> updateLineBreakFlagAfterAsterisks()
            DDOC_QUOTE_CHAR -> {} // Quote char is recursive, so hasLineBreak is already updated
            else -> hasLineBreak = false
        }
        when (tokenType) {
            DDOC_COMMENT_LEADING_ASTERISKS,
                DDOC_COMMENT_START, DDOC_COMMENT_END -> {}
            else -> hasSomeData = true
        }
    }

    private fun parseMacroCall() {
        val marker = builder.mark()
        assert(builder.tokenType == DDOC_MACRO_OPEN)
        builder.advanceLexer()
         while (!isEndOfComment() && builder.tokenType != DDOC_MACRO_END) {
            if (builder.tokenType == DDOC_MACRO_OPEN) {
                parseMacroCall()
            } else {
                builder.advanceLexer()
            }
        }
        if (!isEndOfComment())
            builder.advanceLexer()
        marker.done(DDOC_MACRO_CALL)
    }

    private fun parseQuote() {
        val marker = builder.mark()
        hasLineBreak = false
        builder.advanceLexer()
        var newLine = false
        // end condition: end of paragraph or end of comment or (newline + (HEADING or HORIZONTAL_RULE or BLOCK_CODE or LIST))
        // TODO list when they are supported
        while (!hasLineBreak && !isEndOfComment() && !(newLine &&
                (builder.tokenType == DDOC_HORIZONTAL_RULE || builder.tokenType == DDOC_HEADING
                || builder.tokenType == DDOC_EMBEDDED_CODE_DELIMITER))
        ) {
            if (builder.tokenType == DDOC_QUOTE_CHAR && newLine) {
                builder.advanceLexer() // skip this one as it’s a continuation quote char
            }
            newLine = hasNewLine()
            parseSectionContent()
        }
        marker.done(DDOC_QUOTE)
    }

    private fun parseLink() {
        if (builder.rawLookup(-1) != DDOC_WHITESPACE && !builder.rawLookupText(-1).endsWith(" ")) {
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
        while (!isEndOfComment() && (builder.tokenType == DDOC_WHITESPACE || builder.tokenType == DDOC_COMMENT_DATA || builder.tokenType == DDOC_QUOTE)) {
            if (builder.tokenType == DDOC_QUOTE) {
                parseQuote()
            } else {
                builder.advanceLexer()
            }
        }
        if (builder.tokenType != DDOC_RIGHT_BRACKET) {
            // not a link (`[` but not `]`)
            marker1.drop()
            linkMarker.drop()
            builder.advanceLexer()
            return false
        }
        builder.advanceLexer()
        when (builder.tokenType) {
            DDOC_LEFT_BRACKET, DDOC_LEFT_PARENTHESES -> marker1.done(DDOC_LINK_TEXT)
            DDOC_COLON -> marker1.done(DDOC_LINK_NAME)
            else -> marker1.done(DDOC_LINK_REFERENCE_TO)
        }
        when (builder.tokenType) {
            DDOC_LEFT_BRACKET -> {
                val markerReference = builder.mark()
                builder.advanceLexer()
                while (!isEndOfComment() && (builder.tokenType == DDOC_WHITESPACE || builder.tokenType == DDOC_COMMENT_DATA)) {
                    builder.advanceLexer()
                }
                if (builder.tokenType != DDOC_RIGHT_BRACKET) {
                    markerReference.rollbackTo()
                    linkMarker.done(DDOC_LINK)
                    return true
                }
                builder.advanceLexer()
                markerReference.done(DDOC_LINK_REFERENCE_TO)
                linkMarker.done(DDOC_LINK)
                return true
            }
            DDOC_LEFT_PARENTHESES -> {
                val markerRawUrl = builder.mark()
                builder.advanceLexer()
                while (!isEndOfComment() && (builder.tokenType == DDOC_WHITESPACE || builder.tokenType == DDOC_COMMENT_DATA || builder.tokenType == DDOC_COLON)) {
                    builder.advanceLexer()
                }
                if (builder.tokenType != DDOC_RIGHT_PARENTHESES) {
                    markerRawUrl.rollbackTo()
                    linkMarker.done(DDOC_LINK)
                    return true
                }
                builder.advanceLexer()
                markerRawUrl.done(DDOC_LINK_INLINE_REFERENCE_TEXT)
                linkMarker.done(DDOC_LINK)
            }
            DDOC_COLON -> {
                builder.advanceLexer()
                while (!isEndOfComment() && !hasNewLine()) {
                    builder.advanceLexer()
                }
                linkMarker.done(DDOC_LINK_DECLARATION)
                return true
            }
            else -> {
                linkMarker.done(DDOC_LINK)
                return true
            }
        }
        return true
    }

    private fun parseImage() {
        val marker = builder.mark()
        builder.advanceLexer()
        val wasLink = parseLinkContent()
        if (wasLink) {
            marker.done(DDOC_IMAGE)
        } else {
            marker.drop()
        }
    }

    private fun parseEmbeddedCode() {
        val marker = builder.mark()
        builder.advanceLexer()
        while (!isEndOfComment() && builder.tokenType != DDOC_EMBEDDED_CODE_DELIMITER) {
            builder.advanceLexer()
        }
        if (!isEndOfComment()) {
            builder.advanceLexer()
        }
        marker.done(DDOC_EMBEDDED_CODE)
    }

    private fun parseHeading() {
        assert(builder.tokenType == DDOC_HEADING_CHARS)
        val marker = builder.mark()
        builder.advanceLexer()
        while (!isEndOfComment() && builder.tokenType != DDOC_HEADING_CHARS && builder.tokenType != DDOC_WHITESPACE) {
            builder.advanceLexer()
        }
        if (!isEndOfComment() && builder.tokenType != DDOC_WHITESPACE) {
            builder.advanceLexer()
        }
        marker.done(DDOC_HEADING)
    }

    private fun parseMacroSection(marker: PsiBuilder.Marker, markerType: IElementType) {
        // FIXME actually parse the real section
        parseSection(marker, markerType)
    }

    private fun parseParamsSection(marker: PsiBuilder.Marker, markerType: IElementType) {
        // The current approach is to parse in normally and handle the params rendering and validation
        // by directly checking the content and not the PSI. Change this method to really parse and use the PSI for them.
        parseSection(marker, markerType)
    }

    private fun PsiBuilder.rawLookupText(steps: Int): CharSequence {
        val start = rawTokenTypeStart(steps)
        val end = rawTokenTypeStart(steps + 1)
        return if (start == -1 || end == -1) "" else originalText.subSequence(start, end)
    }
}
