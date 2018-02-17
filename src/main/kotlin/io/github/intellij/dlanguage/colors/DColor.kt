package io.github.intellij.dlanguage.colors

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default

enum class DColor(humanName: String, val default: TextAttributesKey) {
    ILLEGAL("Illegal character", Default.INVALID_STRING_ESCAPE),
    LINE_COMMENT("Line Comment", Default.LINE_COMMENT),
    LINE_DOC("Line documentation", Default.DOC_COMMENT),
    BLOCK_COMMENT("Block Comment", Default.BLOCK_COMMENT),
    DOC_COMMENT("Ddoc comment", Default.DOC_COMMENT),
    AT_ATTRIBUTE("Attribute", Default.METADATA),
    CHAR("Char", Default.STRING),
    STRING("String", Default.STRING),
    NUMBER("Number", Default.NUMBER),
    KEYWORD("Keyword", Default.KEYWORD),
    PARENTHESES("Parenthesis", Default.PARENTHESES),
    BRACES("Braces", Default.BRACES),
    BRACKETS("Brackets", Default.BRACKETS),
    OPERATOR("Operator", Default.OPERATION_SIGN),
    DOT("Dot", Default.DOT),
    SEMICOLON("Semicolon", Default.SEMICOLON),
    COMMA("Comma", Default.COMMA),
    MODULE_DEFINITION("Module definition", Default.GLOBAL_VARIABLE),
    FUNCTION_DEFINITION("Function definition", Default.GLOBAL_VARIABLE),
    IDENTIFIER("Identifier", Default.IDENTIFIER),
    TYPE_PARAMETER("Type parameter", Default.PARAMETER),
    ;

    val textAttributesKey = TextAttributesKey.createTextAttributesKey("io.github.intellij.dlanguage.$name", default)
    val attributesDescriptor = AttributesDescriptor(humanName, textAttributesKey)
}
