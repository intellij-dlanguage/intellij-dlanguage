package net.masterthought.dlanguage.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import net.masterthought.dlanguage.psi.DLanguageTokenType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

/**
 * Highlighter for D source.  Uses DHighlightingLexer to create tokens of
 * DElementType, then returns the highlighting TextAttributesKey associated
 * with the DElementType token.
 *
 */
public class DHighlighter extends SyntaxHighlighterBase {
    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new DHighlightingLexer();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(final IElementType tokenType) {

        TokenSet set = DLanguageTokenType.findSet(tokenType);
        if (set == null) {
            return EMPTY;
        }

        TextAttributesKey[] key = keys.get(set);
        if (key != null) {
            return key;
        }

        return EMPTY;
    }



    public static final TextAttributesKey LINE_COMMENT = createTextAttributesKey(
                                                                "LINE_COMMENT",
                                                                DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey BLOCK_COMMENT = createTextAttributesKey(
                                                                "BLOCK_COMMENT",
                                                                DefaultLanguageHighlighterColors.BLOCK_COMMENT);
    public static final TextAttributesKey DOC_COMMENT = createTextAttributesKey(
                                                                "DOC_COMMENT",
                                                                DefaultLanguageHighlighterColors.DOC_COMMENT);

    private static final TextAttributesKey[] COMMENTS = new TextAttributesKey[]{LINE_COMMENT,BLOCK_COMMENT,DOC_COMMENT};


    public static final TextAttributesKey STRING_LITERAL = createTextAttributesKey(
                                                                "STRING_LITERAL",
                                                                DefaultLanguageHighlighterColors.STRING);

    public static final TextAttributesKey NUM_LITERAL = createTextAttributesKey(
                                                                "NUM_LITERAL",
                                                                DefaultLanguageHighlighterColors.NUMBER);


    public static final TextAttributesKey OPERATOR = createTextAttributesKey(
                                                                "OPERATOR",
                                                                DefaultLanguageHighlighterColors.OPERATION_SIGN);

    public static final TextAttributesKey KEYWORD = createTextAttributesKey(
                                                                "KEYWORD",
                                                                DefaultLanguageHighlighterColors.KEYWORD);

    public static final TextAttributesKey PARENS = createTextAttributesKey(
                                                                "PARENS",
                                                                DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey BRACES = createTextAttributesKey(
                                                                "BRACES",
                                                                DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey BRACKETS = createTextAttributesKey(
                                                                "BRACKETS",
                                                                DefaultLanguageHighlighterColors.BRACKETS);

/*
    public static final TextAttributesKey IDENTIFIER = TextAttributesKey.createTextAttributesKey("IDENTIFIER",
                                                                                                 DefaultLanguageHighlighterColors.KEYWORD);
*/

    private static final Map<TokenSet, TextAttributesKey[]> keys;
    static {
        keys = new HashMap<TokenSet, TextAttributesKey[]>();

/*
        keys.put(DTokenType.LINE_COMMENT, LINE_COMMENT);
        keys.put(DTokenType.BLOCK_COMMENT, BLOCK_COMMENT);
        keys.put(DTokenType.BLOCK_COMMENT_NEST, BLOCK_COMMENT);
        keys.put(DTokenType.DOC_COMMENT, DOC_COMMENT);
        keys.put(DTokenType.DOC_COMMENT_NEST, DOC_COMMENT);
        keys.put(DTokenType.DOC_LINE_COMMENT, DOC_COMMENT);
*/

        keys.put(DLanguageTokenType.COMMENTS, COMMENTS);
        keys.put(DLanguageTokenType.PARENS, pack(PARENS));
        keys.put(DLanguageTokenType.BRACE, pack(BRACES));
        keys.put(DLanguageTokenType.BRACKET, pack(BRACKETS));
        keys.put(DLanguageTokenType.OPERATOR, pack(OPERATOR));
        keys.put(DLanguageTokenType.KEYWORD, pack(KEYWORD));
        keys.put(DLanguageTokenType.STRING_LITERALS, pack(STRING_LITERAL));

/*
        keys.put(DTokenType.STRING_LITERAL, STRING_LITERAL);
        keys.put(DTokenType.DSTR_LITERAL, STRING_LITERAL);
        keys.put(DTokenType.WSTR_LITERAL, STRING_LITERAL);
        keys.put(DTokenType.CHAR_LITERAL, STRING_LITERAL);
*/

//        keys.put(DTokenType.LITERAL, NUM_LITERAL);
//        keys.put(DTokenType.IDENTIFIER, IDENTIFIER);
    }

}
