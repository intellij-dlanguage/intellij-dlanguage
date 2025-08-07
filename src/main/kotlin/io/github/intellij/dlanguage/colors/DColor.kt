package io.github.intellij.dlanguage.colors

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default

/**
 * The enum entries here are mapped in DHighlighter based on the DlangTypes supported by the highlighting lexer.
 * @see io.github.intellij.dlanguage.highlighting.DHighlightingTokenSets
 * @see io.github.intellij.dlanguage.psi.DlangTypes
 */
enum class DColor(humanName: String, val default: TextAttributesKey) {

    SHEBANG("Shebang", Default.METADATA),

    LINE_COMMENT("Comments//Line Comment", Default.LINE_COMMENT),
    BLOCK_COMMENT("Comments//Block Comment", Default.BLOCK_COMMENT),

    LINE_DOCUMENTATION("DDoc//Line Documentation", Default.DOC_COMMENT),
    BLOCK_DOCUMENTATION("DDoc//Block Documentation", Default.DOC_COMMENT),

    AT_ATTRIBUTE("Attribute", Default.METADATA),

    KEYWORD("Keyword", Default.KEYWORD),
    SPECIAL_KEYWORD("Special Keyword", Default.KEYWORD),
    SPECIAL_TOKEN("Special Token", Default.KEYWORD),

    NUMBER("Number", Default.NUMBER),

    CHAR("String//Char", Default.STRING),
    STRING_LITERAL("String//String Literal", Default.STRING),
    VALID_STRING_ESCAPE("String//Escape Sequence", Default.VALID_STRING_ESCAPE),
    INVALID_STRING_ESCAPE("String//Invalid Escape Sequence", Default.INVALID_STRING_ESCAPE),
    STRING_NAMED_CHARACTER_ENTITY("String//Named entity", Default.MARKUP_ENTITY),
    ILLEGAL("String//Illegal character", Default.INVALID_STRING_ESCAPE),

    PARENTHESES("Braces and Operators//Parenthesis", Default.PARENTHESES),
    BRACES("Braces and Operators//Braces", Default.BRACES),
    BRACKETS("Braces and Operators//Brackets", Default.BRACKETS),
    OPERATOR("Braces and Operators//Operator", Default.OPERATION_SIGN),
    DOT("Braces and Operators//Dot", Default.DOT),
    SEMICOLON("Braces and Operators//Semicolon", Default.SEMICOLON),
    COMMA("Braces and Operators//Comma", Default.COMMA),

    MODULE_DEFINITION("Module Definition", Default.GLOBAL_VARIABLE),
    FUNCTION_DEFINITION("Function Definition", Default.FUNCTION_DECLARATION),
    FUNCTION_CALL("Function Call", Default.FUNCTION_CALL),
    //IDENTIFIER("Identifier", Default.IDENTIFIER),
    PARAMETER("Parameter", Default.PARAMETER),
    TYPE_PARAMETER("Type Parameter", Default.PARAMETER),
    ;

    val textAttributesKey = TextAttributesKey.createTextAttributesKey("io.github.intellij.dlanguage.$name", default)
    val attributesDescriptor = AttributesDescriptor(humanName, textAttributesKey)
}
