package io.github.intellij.dlanguage.features.documentation

import io.github.intellij.dlanguage.documentation.psi.DlangDocElementType

object DDocElementTypes {
    // Tokens from Lexer
    @JvmField val DDOC_WHITESPACE = DlangDocElementType("DDOC_WHITESPACE")
    @JvmField val DDOC_COMMENT_DATA = DlangDocElementType("DDOC_COMMENT_DATA")
    @JvmField val DDOC_COLON = DlangDocElementType("DDOC_COLON")
    @JvmField val DDOC_QUOTE_CHAR = DlangDocElementType("DDOC_QUOTE_CHAR")
    @JvmField val DDOC_EXCLAMATION_MARK = DlangDocElementType("DDOC_EXCLAMATION_MARK")
    @JvmField val DDOC_LEFT_BRACKET = DlangDocElementType("DDOC_LEFT_BRACKET")
    @JvmField val DDOC_RIGHT_BRACKET = DlangDocElementType("DDOC_RIGHT_BRACKET")
    @JvmField val DDOC_LEFT_PARENTHESES = DlangDocElementType("DDOC_LEFT_PARENTHESES")
    @JvmField val DDOC_RIGHT_PARENTHESES = DlangDocElementType("DDOC_RIGHT_PARENTHESES")
    @JvmField val DDOC_BAD_CHARACTER = DlangDocElementType("DDOC_BAD_CHARACTER")
    @JvmField val DDOC_EMBEDDED_CODE_DELIMITER = DlangDocElementType("DDOC_EMBEDDED_CODE_DELIMITER")
    @JvmField val DDOC_EMBEDDED_CODE_CONTENT = DlangDocElementType("DDOC_EMBEDDED_CODE_CONTENT")
    @JvmField val DDOC_HEADING_CHARS = DlangDocElementType("DDOC_HEADING_CHARS")

    @JvmField val DDOC_MACRO_OPEN = DlangDocElementType("DDOC_MACRO_OPEN")
    @JvmField val DDOC_MACRO_END = DlangDocElementType("DDOC_MACRO_END")
    @JvmField val DDOC_MACRO_COMA = DlangDocElementType("DDOC_MACRO_COMA")
    @JvmField val DDOC_MACRO_BACKSLASH = DlangDocElementType("DDOC_MACRO_BACKSLASH")
    @JvmField val DDOC_MACRO_EQUALS = DlangDocElementType("DDOC_MACRO_EQUALS")
    @JvmField val DDOC_MACRO_IDENTIFIER = DlangDocElementType("DDOC_MACRO_IDENTIFIER")
    @JvmField val DDOC_MACRO_ARGUMENT = DlangDocElementType("DDOC_MACRO_ARGUMENT")

    // Highlighting
    @JvmField val DDOC_EMBEDDED_CODE = DlangDocElementType("DDOC_EMBEDDED_CODE")
    @JvmField val DDOC_INLINE_CODE = DlangDocElementType("DDOC_INLINE_CODE")
    @JvmField val DDOC_HEADING = DlangDocElementType("DDOC_HEADING")
    @JvmField val DDOC_QUOTE = DlangDocElementType("DDOC_QUOTE")
    @JvmField val DDOC_ORDERED_LIST = DlangDocElementType("DDOC_ORDERED_LIST")
    @JvmField val DDOC_UNORDERED_LIST = DlangDocElementType("DDOC_UNORDERED_LIST")
    @JvmField val DDOC_HORIZONTAL_RULE = DlangDocElementType("DDOC_HORIZONTAL_RULE")
    @JvmField val DDOC_SIMPLE_EMPHASIS = DlangDocElementType("DDOC_SIMPLE_EMPHASIS")
    @JvmField val DDOC_DOUBLE_EMPHASIS = DlangDocElementType("DDOC_DOUBLE_EMPHASIS")
    @JvmField val DDOC_MACRO_CALL = DlangDocElementType("DDOC_MACRO_CALL")
    @JvmField val DDOC_LINK = DlangDocElementType("DDOC_LINK")
    @JvmField val DDOC_LINK_DECLARATION = DlangDocElementType("DDOC_LINK_DECLARATION")
    @JvmField val DDOC_IMAGE = DlangDocElementType("DDOC_IMAGE")

    // Intermediate Content for highlighting
    @JvmField val DDOC_LINK_TEXT = DlangDocElementType("DDOC_LINK_TEXT")
    @JvmField val DDOC_LINK_NAME = DlangDocElementType("DDOC_LINK_NAME")
    @JvmField val DDOC_LINK_REFERENCE_TO = DlangDocElementType("DDOC_LINK_REFERENCE_TO")
    @JvmField val DDOC_LINK_INLINE_REFERENCE_TEXT = DlangDocElementType("DDOC_LINK_INLINE_REFERENCE_TEXT")

    // Sections
    @JvmField val DDOC_SECTION_TITLE = DlangDocElementType("DDOC_SECTION_TITLE")
    @JvmField val DDOC_SECTION_CONTENT = DlangDocElementType("DDOC_SECTION_CONTENT")
    @JvmField val DDOC_ANONYMOUS_SECTION = DlangDocElementType("DDOC_ANONYMOUS_SECTION")
    @JvmField val DDOC_NAMED_SECTION = DlangDocElementType("DDOC_NAMED_SECTION")

    @JvmField val DDOC_SUMMARY_SECTION = DlangDocElementType("DDOC_SUMMARY_SECTION") // unnamed
    @JvmField val DDOC_DESCRIPTION_SECTION = DlangDocElementType("DDOC_DESCRIPTION_SECTION") // unnamed
    @JvmField val DDOC_AUTHORS_SECTION = DlangDocElementType("DDOC_AUTHORS_SECTION")
    @JvmField val DDOC_BUGS_SECTION = DlangDocElementType("DDOC_BUGS_SECTION")
    @JvmField val DDOC_DATE_SECTION = DlangDocElementType("DDOC_DATE_SECTION")
    @JvmField val DDOC_DEPRECATED_SECTION = DlangDocElementType("DDOC_DEPRECATED_SECTION")
    @JvmField val DDOC_EXAMPLES_SECTION = DlangDocElementType("DDOC_EXAMPLES_SECTION")
    @JvmField val DDOC_HISTORY_SECTION = DlangDocElementType("DDOC_HISTORY_SECTION")
    @JvmField val DDOC_LICENSE_SECTION = DlangDocElementType("DDOC_LICENSE_SECTION")
    @JvmField val DDOC_RETURNS_SECTION = DlangDocElementType("DDOC_RETURNS_SECTION")
    @JvmField val DDOC_SEE_ALSO_SECTION = DlangDocElementType("DDOC_SEE_ALSO_SECTION")
    @JvmField val DDOC_STANDARDS_SECTION = DlangDocElementType("DDOC_STANDARDS_SECTION")
    @JvmField val DDOC_THROWS_SECTION = DlangDocElementType("DDOC_THROWS_SECTION")
    @JvmField val DDOC_VERSION_SECTION = DlangDocElementType("DDOC_VERSION_SECTION")

    // Specials sections
    @JvmField val DDOC_COPYRIGHT_SECTION = DlangDocElementType("DDOC_COPYRIGHT_SECTION") // Module level only
    @JvmField val DDOC_PARAMS_SECTION = DlangDocElementType("DDOC_COPYRIGHT_SECTION") // Function level, special syntax
    @JvmField val DDOC_MACROS_SECTION = DlangDocElementType("DDOC_MACROS_SECTION") // Same syntax as params


    // TODO Remain Highlighting
    /*
    LISTS (think to support them in markdown quote)
      ordered
      unordered
    TABLES
    ~ TEXT_EMPHASIS  // see ddoc tests, (see italic in bold)
    IDENTIFIER_EMPHASIS // highlighting ref (see java)
    ~ PUNCTUATION_ESCAPES // mainly macro but need to support \\
    */
}
