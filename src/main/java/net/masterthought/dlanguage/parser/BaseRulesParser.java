package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 6/25/2017.
 */
public class BaseRulesParser {
    /* ********************************************************** */
    // ID
    public static boolean Identifier(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Identifier")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, ID);
        exit_section_(b, m, IDENTIFIER, r);
        return r;
    }

    /* ********************************************************** */
    // WYSIWYG_STRING|ALTERNATE_WYSIWYG_STRING|DOUBLE_QUOTED_STRING|HEX_STRING|DELIMITED_STRING| (TOKEN_STRING TOKEN_STRING*)
    public static boolean StringLiteral(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StringLiteral")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, STRING_LITERAL, "<string literal>");
        r = consumeToken(b, WYSIWYG_STRING);
        if (!r) r = consumeToken(b, ALTERNATE_WYSIWYG_STRING);
        if (!r) r = consumeToken(b, DOUBLE_QUOTED_STRING);
        if (!r) r = consumeToken(b, HEX_STRING);
        if (!r) r = consumeToken(b, DELIMITED_STRING);
        if (!r) r = StringLiteral_5(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // TOKEN_STRING TOKEN_STRING*
    private static boolean StringLiteral_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StringLiteral_5")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, TOKEN_STRING);
        r = r && StringLiteral_5_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // TOKEN_STRING*
    private static boolean StringLiteral_5_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StringLiteral_5_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!consumeToken(b, TOKEN_STRING)) break;
            if (!empty_element_parsed_guard_(b, "StringLiteral_5_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    /* ********************************************************** */
    // StringLiteral [StringLiterals]
    public static boolean StringLiterals(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StringLiterals")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, STRING_LITERALS, "<string literals>");
        r = StringLiteral(b, l + 1);
        r = r && StringLiterals_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // [StringLiterals]
    private static boolean StringLiterals_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StringLiterals_1")) return false;
        StringLiterals(b, l + 1);
        return true;
    }
}
