package io.github.intellij.dlanguage.highlighting;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

/**
 * Highlighter for D source.  Uses DHighlightingLexer to create tokens of
 * DElementType, then returns the highlighting TextAttributesKey associated
 * with the DElementType token.
 */
public class DHighlighter extends SyntaxHighlighterBase {

    //    // Token based highlighting
    public static final TextAttributesKey ILLEGAL = createTextAttributesKey("D_ILLEGAL", DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE);
    public static final TextAttributesKey LINE_COMMENT = createTextAttributesKey("D_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey LINE_DOC = createTextAttributesKey("D_LINE_DOC", DefaultLanguageHighlighterColors.DOC_COMMENT);
    public static final TextAttributesKey BLOCK_COMMENT = createTextAttributesKey("D_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
    public static final TextAttributesKey DOC_COMMENT = createTextAttributesKey("D_DOC_COMMENT", DefaultLanguageHighlighterColors.DOC_COMMENT);
    public static final TextAttributesKey CHAR = createTextAttributesKey("D_STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey STRING = createTextAttributesKey("D_STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey NUMBER = createTextAttributesKey("D_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey KEYWORD = createTextAttributesKey("D_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey PARENTHESES = createTextAttributesKey("D_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey BRACES = createTextAttributesKey("D_BRACES", DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey BRACKETS = createTextAttributesKey("D_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
    public static final TextAttributesKey OPERATOR = createTextAttributesKey("D_OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey DOT = createTextAttributesKey("D_DOT", DefaultLanguageHighlighterColors.DOT);
    public static final TextAttributesKey SEMICOLON = createTextAttributesKey("D_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);
    public static final TextAttributesKey COMMA = createTextAttributesKey("D_COMMA", DefaultLanguageHighlighterColors.COMMA);
    public static final TextAttributesKey MODULE_DEFINITION = createTextAttributesKey("D_MODULE_DEFINITION", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);
    public static final TextAttributesKey FUNCTION_DEFINITION = createTextAttributesKey("D_FUNCTION_DEFINITION", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);

//    // Annotation based highlighting
//    public static final TextAttributesKey FUNCTION_DEFINITION = createTextAttributesKey("D_FUNCTION_DEFINITION", DefaultLanguageHighlighterColors.INSTANCE_METHOD);
//    public static final TextAttributesKey MODULE_DEFINITION = createTextAttributesKey("D_MODULE_DEFINITION", DefaultLanguageHighlighterColors.INSTANCE_METHOD);
//    public static final TextAttributesKey AGGREGATE_DEFINITION = createTextAttributesKey("D_AGGREGATE_DEFINITION", DefaultLanguageHighlighterColors.INSTANCE_METHOD);
//    public static final TextAttributesKey STD_IMPORT = createTextAttributesKey("D_STD_IMPORT", DefaultLanguageHighlighterColors.INSTANCE_METHOD);
//    public static final TextAttributesKey BASIC_TYPE = createTextAttributesKey("D_BASIC_TYPE", DefaultLanguageHighlighterColors.INSTANCE_FIELD);
//    public static final TextAttributesKey USER_DEFINED_ATTRIBUTE = createTextAttributesKey("D_USER_DEFINED_ATTRIBUTE", DefaultLanguageHighlighterColors.INSTANCE_FIELD);

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new FlexAdapter(new DHighlightingLexer());
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(final IElementType type) {
        if (type == TokenType.BAD_CHARACTER) {
            return pack(ILLEGAL);
        }
        if (DHighlightingTokenSets.LINE_DOC.contains(type)) {
            return pack(LINE_DOC);
        }
        if (DHighlightingTokenSets.LINE_COMMENT.contains(type)) {
            return pack(LINE_COMMENT);
        }
        if (DHighlightingTokenSets.BLOCK_COMMENT.contains(type)) {
            return pack(BLOCK_COMMENT);
        }
        if (DHighlightingTokenSets.DOC_COMMENT.contains(type)) {
            return pack(DOC_COMMENT);
        }
        if (DHighlightingTokenSets.STRING.contains(type)) {
            return pack(STRING);
        }
        if (DHighlightingTokenSets.CHAR.contains(type)) {
            return pack(CHAR);
        }
        if (DHighlightingTokenSets.NUMBER.contains(type)) {
            return pack(NUMBER);
        }
        if (DHighlightingTokenSets.OPERATOR.contains(type)) {
            return pack(OPERATOR);
        }
        if (DHighlightingTokenSets.PARENTHESES_RIGHT.contains(type)) {
            return pack(PARENTHESES);
        }
        if (DHighlightingTokenSets.PARENTHESES_LEFT.contains(type)) {
            return pack(PARENTHESES);
        }
        if (DHighlightingTokenSets.BRACES_RTGHT.contains(type)) {
            return pack(BRACES);
        }if (DHighlightingTokenSets.BRACES_LEFT.contains(type)) {
            return pack(BRACES);
        }
        if (DHighlightingTokenSets.SEMICOLON.contains(type)) {
            return pack(SEMICOLON);
        }
        if (DHighlightingTokenSets.COMMA.contains(type)) {
            return pack(COMMA);
        }
        if (DHighlightingTokenSets.DOT.contains(type)) {
            return pack(DOT);
        }
        if (DHighlightingTokenSets.BRACKETS_RIGHT.contains(type)) {
            return pack(BRACKETS);
        }
        if (DHighlightingTokenSets.BRACKETS_LEFT.contains(type)) {
            return pack(BRACKETS);
        }
        if (DHighlightingTokenSets.MODULE_DEFINITION.contains(type)) {
            return pack(MODULE_DEFINITION);
        }
        if (DHighlightingTokenSets.FUNCTION_DEFINITION.contains(type)) {
            return pack(FUNCTION_DEFINITION);
        }
        if (DHighlightingTokenSets.KEYWORD.contains(type)) {
            return pack(KEYWORD);
        }
        return EMPTY;
    }

}
