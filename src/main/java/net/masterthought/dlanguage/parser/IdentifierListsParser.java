package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 6/25/2017.
 */
public class IdentifierListsParser {
    /* ********************************************************** */
    // Identifier TemplateInstance? ['.' IdentifierList]
    //     |  Identifier '[' AssignExpression ']' '.' IdentifierList
    public static boolean IdentifierList(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IdentifierList")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = IdentifierList_0(b, l + 1);
        if (!r) r = IdentifierList_1(b, l + 1);
        exit_section_(b, m, IDENTIFIER_LIST, r);
        return r;
    }

    // Identifier TemplateInstance? ['.' IdentifierList]
    private static boolean IdentifierList_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IdentifierList_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        r = r && IdentifierList_0_1(b, l + 1);
        r = r && IdentifierList_0_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // TemplateInstance?
    private static boolean IdentifierList_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IdentifierList_0_1")) return false;
        MiscParser.TemplateInstance(b, l + 1);
        return true;
    }

    // ['.' IdentifierList]
    private static boolean IdentifierList_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IdentifierList_0_2")) return false;
        IdentifierList_0_2_0(b, l + 1);
        return true;
    }

    // '.' IdentifierList
    private static boolean IdentifierList_0_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IdentifierList_0_2_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_DOT);
        r = r && IdentifierList(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Identifier '[' AssignExpression ']' '.' IdentifierList
    private static boolean IdentifierList_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IdentifierList_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_BRACKET_LEFT);
        r = r && ExpressionParser.AssignExpression(b, l + 1);
        r = r && consumeTokens(b, 0, OP_BRACKET_RIGHT, OP_DOT);
        r = r && IdentifierList(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // (Identifier | TemplateInstance) ( '.' (Identifier | TemplateInstance) )*
    public static boolean QualifiedIdentifierList(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "QualifiedIdentifierList")) return false;
        if (!nextTokenIs(b, "<qualified identifier list>", OP_NOT, ID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, QUALIFIED_IDENTIFIER_LIST, "<qualified identifier list>");
        r = QualifiedIdentifierList_0(b, l + 1);
        r = r && QualifiedIdentifierList_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // Identifier | TemplateInstance
    private static boolean QualifiedIdentifierList_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "QualifiedIdentifierList_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        if (!r) r = MiscParser.TemplateInstance(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ( '.' (Identifier | TemplateInstance) )*
    private static boolean QualifiedIdentifierList_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "QualifiedIdentifierList_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!QualifiedIdentifierList_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "QualifiedIdentifierList_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    // '.' (Identifier | TemplateInstance)
    private static boolean QualifiedIdentifierList_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "QualifiedIdentifierList_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_DOT);
        r = r && QualifiedIdentifierList_1_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Identifier | TemplateInstance
    private static boolean QualifiedIdentifierList_1_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "QualifiedIdentifierList_1_0_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        if (!r) r = MiscParser.TemplateInstance(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }
}
