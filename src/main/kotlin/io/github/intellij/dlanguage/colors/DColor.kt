package io.github.intellij.dlanguage.colors

import com.intellij.openapi.editor.XmlHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default

/**
 * The enum entries here are mapped in DHighlighter based on the DlangTypes supported by the highlighting lexer.
 * @see io.github.intellij.dlanguage.highlighting.DHighlightingTokenSets
 * @see io.github.intellij.dlanguage.psi.DlangTypes
 */
enum class DColor(humanName: String, val default: TextAttributesKey) {
    ILLEGAL("Illegal character", Default.INVALID_STRING_ESCAPE),
    LINE_COMMENT("Line Comment", Default.LINE_COMMENT),
    LINE_DOCUMENTATION("Line Documentation", Default.DOC_COMMENT),
    BLOCK_COMMENT("Block Comment", Default.BLOCK_COMMENT),
    BLOCK_DOCUMENTATION("Ddoc comment", Default.DOC_COMMENT),
    AT_ATTRIBUTE("Attribute", Default.METADATA),
    CHAR("Char", Default.STRING),
    STRING_LITERAL("String Literal", Default.STRING),
    NUMBER("Number", Default.NUMBER),
    KEYWORD("Keyword", Default.KEYWORD),
    PARENTHESES("Parenthesis", Default.PARENTHESES),
    BRACES("Braces", Default.BRACES),
    BRACKETS("Brackets", Default.BRACKETS),
    OPERATOR("Operator", Default.OPERATION_SIGN),
    DOT("Dot", Default.DOT),
    SEMICOLON("Semicolon", Default.SEMICOLON),
    COMMA("Comma", Default.COMMA),
    MODULE_DEFINITION("Module Definition", Default.GLOBAL_VARIABLE),
    //FUNCTION_DEFINITION("Function Definition", Default.FUNCTION_DECLARATION),
    //IDENTIFIER("Identifier", Default.IDENTIFIER),
    TYPE_PARAMETER("Type Parameter", Default.PARAMETER),

    VALID_STRING_ESCAPE("String Escape Sequence", Default.VALID_STRING_ESCAPE),
    INVALID_STRING_ESCAPE("String Invalid Escape Sequence", Default.INVALID_STRING_ESCAPE),
    STRING_NAMED_CHARACTER_ENTITY("String Named entity", XmlHighlighterColors.HTML_ENTITY_REFERENCE),
    ;

    val textAttributesKey = TextAttributesKey.createTextAttributesKey("io.github.intellij.dlanguage.$name", default)
    val attributesDescriptor = AttributesDescriptor(humanName, textAttributesKey)
}
