package io.github.intellij.dlanguage.sdlang.colors

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default

enum class SDLangColor(humanName: String, val default: TextAttributesKey) {

    LINE_COMMENT("Comments//Line Comment", Default.LINE_COMMENT),
    BLOCK_COMMENT("Comments//Block Comment", Default.BLOCK_COMMENT),

    KEYWORD("Keyword", Default.KEYWORD),
    NUMBER("Number", Default.NUMBER),

    DATETIME("Datetime", Default.NUMBER),

    STRING("String//String Literal", Default.STRING),
    ILLEGAL("String//Illegal character", Default.INVALID_STRING_ESCAPE),

    BRACES("Braces and Operators//Braces", Default.BRACES),
    BRACKETS("Braces and Operators//Brackets", Default.BRACKETS),
    SEMICOLON("Braces and Operators//Semicolon", Default.SEMICOLON),
    COLON("Braces and Operators//Colon", Default.SEMICOLON),

    TAG_IDENTIFIER("Identifier", Default.INSTANCE_FIELD)
    ;

    val textAttributesKey = TextAttributesKey.createTextAttributesKey("io.github.intellij.dlanguage.sdlang.$name", default)
    val attributesDescriptor = AttributesDescriptor(humanName, textAttributesKey)
}
