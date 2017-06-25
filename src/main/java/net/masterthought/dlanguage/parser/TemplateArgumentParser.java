package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 6/25/2017.
 */
public class TemplateArgumentParser {
    /* ********************************************************** */
    // Lambda //must be above assign expression
    //     | AssignExpression
    //     | Type //this branch should be deprecated  since anything matched by Type is in theory matched by assignexpression
    //     | Symbol
    public static boolean TemplateArgument(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateArgument")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TEMPLATE_ARGUMENT, "<template argument>");
        r = ExpressionParser.Lambda(b, l + 1);
        if (!r) r = ExpressionParser.AssignExpression(b, l + 1);
        if (!r) r = TypeParser.Type(b, l + 1);
        if (!r) r = MiscParser.Symbol(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // TemplateArgument (',' TemplateArgument)* ','?
    public static boolean TemplateArgumentList(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateArgumentList")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TEMPLATE_ARGUMENT_LIST, "<template argument list>");
        r = TemplateArgument(b, l + 1);
        r = r && TemplateArgumentList_1(b, l + 1);
        r = r && TemplateArgumentList_2(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (',' TemplateArgument)*
    private static boolean TemplateArgumentList_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateArgumentList_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!TemplateArgumentList_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "TemplateArgumentList_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    // ',' TemplateArgument
    private static boolean TemplateArgumentList_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateArgumentList_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && TemplateArgument(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ','?
    private static boolean TemplateArgumentList_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateArgumentList_2")) return false;
        consumeToken(b, OP_COMMA);
        return true;
    }

    /* ********************************************************** */
    // TemplateArgumentsWithParen
    //      | TemplateArgumentsWithoutParen
    public static boolean TemplateArguments(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateArguments")) return false;
        if (!nextTokenIs(b, OP_NOT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = TemplateArgumentsWithParen(b, l + 1);
        if (!r) r = TemplateArgumentsWithoutParen(b, l + 1);
        exit_section_(b, m, TEMPLATE_ARGUMENTS, r);
        return r;
    }

    /* ********************************************************** */
    // '!' '(' TemplateArgumentList? ')'
    static boolean TemplateArgumentsWithParen(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateArgumentsWithParen")) return false;
        if (!nextTokenIs(b, OP_NOT)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_);
        r = consumeTokens(b, 2, OP_NOT, OP_PAR_LEFT);
        p = r; // pin = 2
        r = r && report_error_(b, TemplateArgumentsWithParen_2(b, l + 1));
        r = p && consumeToken(b, OP_PAR_RIGHT) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // TemplateArgumentList?
    private static boolean TemplateArgumentsWithParen_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateArgumentsWithParen_2")) return false;
        TemplateArgumentList(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // '!' TemplateSingleArgument
    static boolean TemplateArgumentsWithoutParen(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateArgumentsWithoutParen")) return false;
        if (!nextTokenIs(b, OP_NOT)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_);
        r = consumeToken(b, OP_NOT);
        p = r; // pin = 1
        r = r && TemplateSingleArgument(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // IdentifierList
    //     | BasicTypeX
    //     | CHARACTER_LITERAL
    //     | StringLiteral
    //     | INTEGER_LITERAL
    //     | FLOAT_LITERAL
    //     | 'true'
    //     | 'false'
    //     | 'null'
    //     | 'this'
    //     | SpecialKeyword
    //     | AssignExpression
    public static boolean TemplateSingleArgument(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateSingleArgument")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TEMPLATE_SINGLE_ARGUMENT, "<template single argument>");
        r = IdentifierListsParser.IdentifierList(b, l + 1);
        if (!r) r = TypeParser.BasicTypeX(b, l + 1);
        if (!r) r = consumeToken(b, CHARACTER_LITERAL);
        if (!r) r = BaseRulesParser.StringLiteral(b, l + 1);
        if (!r) r = consumeToken(b, INTEGER_LITERAL);
        if (!r) r = consumeToken(b, FLOAT_LITERAL);
        if (!r) r = consumeToken(b, KW_TRUE);
        if (!r) r = consumeToken(b, KW_FALSE);
        if (!r) r = consumeToken(b, KW_NULL);
        if (!r) r = consumeToken(b, KW_THIS);
        if (!r) r = MiscParser.SpecialKeyword(b, l + 1);
        if (!r) r = ExpressionParser.AssignExpression(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }
}
