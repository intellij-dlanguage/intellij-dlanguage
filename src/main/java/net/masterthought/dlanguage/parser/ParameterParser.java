package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 6/25/2017.
 */
public class ParameterParser {
    /* ********************************************************** */
    // InOut? BasicType Declarator (('...' | '=') AssignExpression?)?
    //        | InOut? Type '=' AssignExpression
    //        | InOut? 'alias' Identifier '=' AssignExpression
    //        | InOut? Type ('...')?
    public static boolean Parameter(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameter")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, PARAMETER, "<parameter>");
        r = Parameter_0(b, l + 1);
        if (!r) r = Parameter_1(b, l + 1);
        if (!r) r = Parameter_2(b, l + 1);
        if (!r) r = Parameter_3(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // InOut? BasicType Declarator (('...' | '=') AssignExpression?)?
    private static boolean Parameter_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameter_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Parameter_0_0(b, l + 1);
        r = r && TypeParser.BasicType(b, l + 1);
        r = r && VariableDeclarationParser.Declarator(b, l + 1);
        r = r && Parameter_0_3(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // InOut?
    private static boolean Parameter_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameter_0_0")) return false;
        VariableDeclarationParser.InOut(b, l + 1);
        return true;
    }

    // (('...' | '=') AssignExpression?)?
    private static boolean Parameter_0_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameter_0_3")) return false;
        Parameter_0_3_0(b, l + 1);
        return true;
    }

    // ('...' | '=') AssignExpression?
    private static boolean Parameter_0_3_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameter_0_3_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Parameter_0_3_0_0(b, l + 1);
        r = r && Parameter_0_3_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '...' | '='
    private static boolean Parameter_0_3_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameter_0_3_0_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_TRIPLEDOT);
        if (!r) r = consumeToken(b, OP_EQ);
        exit_section_(b, m, null, r);
        return r;
    }

    // AssignExpression?
    private static boolean Parameter_0_3_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameter_0_3_0_1")) return false;
        ExpressionParser.AssignExpression(b, l + 1);
        return true;
    }

    // InOut? Type '=' AssignExpression
    private static boolean Parameter_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameter_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Parameter_1_0(b, l + 1);
        r = r && TypeParser.Type(b, l + 1);
        r = r && consumeToken(b, OP_EQ);
        r = r && ExpressionParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // InOut?
    private static boolean Parameter_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameter_1_0")) return false;
        VariableDeclarationParser.InOut(b, l + 1);
        return true;
    }

    // InOut? 'alias' Identifier '=' AssignExpression
    private static boolean Parameter_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameter_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Parameter_2_0(b, l + 1);
        r = r && consumeToken(b, KW_ALIAS);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_EQ);
        r = r && ExpressionParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // InOut?
    private static boolean Parameter_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameter_2_0")) return false;
        VariableDeclarationParser.InOut(b, l + 1);
        return true;
    }

    // InOut? Type ('...')?
    private static boolean Parameter_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameter_3")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Parameter_3_0(b, l + 1);
        r = r && TypeParser.Type(b, l + 1);
        r = r && Parameter_3_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // InOut?
    private static boolean Parameter_3_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameter_3_0")) return false;
        VariableDeclarationParser.InOut(b, l + 1);
        return true;
    }

    // ('...')?
    private static boolean Parameter_3_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameter_3_2")) return false;
        consumeToken(b, OP_TRIPLEDOT);
        return true;
    }

    /* ********************************************************** */
    // Parameters FunctionAttributes?
    public static boolean ParameterAttributes(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParameterAttributes")) return false;
        if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Parameters(b, l + 1);
        r = r && ParameterAttributes_1(b, l + 1);
        exit_section_(b, m, PARAMETER_ATTRIBUTES, r);
        return r;
    }

    // FunctionAttributes?
    private static boolean ParameterAttributes_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParameterAttributes_1")) return false;
        DeclarationParser.FunctionAttributes(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // Parameter (',' Parameter)* ','? '...'?
    //     | '...'
    public static boolean ParameterList(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParameterList")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, PARAMETER_LIST, "<parameter list>");
        r = ParameterList_0(b, l + 1);
        if (!r) r = consumeToken(b, OP_TRIPLEDOT);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // Parameter (',' Parameter)* ','? '...'?
    private static boolean ParameterList_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParameterList_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Parameter(b, l + 1);
        r = r && ParameterList_0_1(b, l + 1);
        r = r && ParameterList_0_2(b, l + 1);
        r = r && ParameterList_0_3(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // (',' Parameter)*
    private static boolean ParameterList_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParameterList_0_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!ParameterList_0_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "ParameterList_0_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    // ',' Parameter
    private static boolean ParameterList_0_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParameterList_0_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && Parameter(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ','?
    private static boolean ParameterList_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParameterList_0_2")) return false;
        consumeToken(b, OP_COMMA);
        return true;
    }

    // '...'?
    private static boolean ParameterList_0_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParameterList_0_3")) return false;
        consumeToken(b, OP_TRIPLEDOT);
        return true;
    }

    /* ********************************************************** */
    // Parameters MemberFunctionAttributes?
    public static boolean ParameterMemberAttributes(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParameterMemberAttributes")) return false;
        if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Parameters(b, l + 1);
        r = r && ParameterMemberAttributes_1(b, l + 1);
        exit_section_(b, m, PARAMETER_MEMBER_ATTRIBUTES, r);
        return r;
    }

    // MemberFunctionAttributes?
    private static boolean ParameterMemberAttributes_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParameterMemberAttributes_1")) return false;
        DeclarationParser.MemberFunctionAttributes(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // '(' ParameterList? ')'
    public static boolean Parameters(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameters")) return false;
        if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && Parameters_1(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, PARAMETERS, r);
        return r;
    }

    // ParameterList?
    private static boolean Parameters_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameters_1")) return false;
        ParameterList(b, l + 1);
        return true;
    }
}
