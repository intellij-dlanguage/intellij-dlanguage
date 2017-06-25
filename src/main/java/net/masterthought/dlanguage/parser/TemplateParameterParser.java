package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 6/25/2017.
 */
public class TemplateParameterParser {
    /* ********************************************************** */
    // 'alias' Identifier '=' AssignExpression
    //      | 'alias' Type? Identifier? (':' (Type | AssignExpression))? ('=' (Type | AssignExpression))?
    public static boolean TemplateAliasParameter(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateAliasParameter")) return false;
        if (!nextTokenIs(b, KW_ALIAS)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = TemplateAliasParameter_0(b, l + 1);
        if (!r) r = TemplateAliasParameter_1(b, l + 1);
        exit_section_(b, m, TEMPLATE_ALIAS_PARAMETER, r);
        return r;
    }

    // 'alias' Identifier '=' AssignExpression
    private static boolean TemplateAliasParameter_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateAliasParameter_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_ALIAS);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_EQ);
        r = r && ExpressionParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'alias' Type? Identifier? (':' (Type | AssignExpression))? ('=' (Type | AssignExpression))?
    private static boolean TemplateAliasParameter_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateAliasParameter_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_ALIAS);
        r = r && TemplateAliasParameter_1_1(b, l + 1);
        r = r && TemplateAliasParameter_1_2(b, l + 1);
        r = r && TemplateAliasParameter_1_3(b, l + 1);
        r = r && TemplateAliasParameter_1_4(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Type?
    private static boolean TemplateAliasParameter_1_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateAliasParameter_1_1")) return false;
        TypeParser.Type(b, l + 1);
        return true;
    }

    // Identifier?
    private static boolean TemplateAliasParameter_1_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateAliasParameter_1_2")) return false;
        BaseRulesParser.Identifier(b, l + 1);
        return true;
    }

    // (':' (Type | AssignExpression))?
    private static boolean TemplateAliasParameter_1_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateAliasParameter_1_3")) return false;
        TemplateAliasParameter_1_3_0(b, l + 1);
        return true;
    }

    // ':' (Type | AssignExpression)
    private static boolean TemplateAliasParameter_1_3_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateAliasParameter_1_3_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COLON);
        r = r && TemplateAliasParameter_1_3_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Type | AssignExpression
    private static boolean TemplateAliasParameter_1_3_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateAliasParameter_1_3_0_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = TypeParser.Type(b, l + 1);
        if (!r) r = ExpressionParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('=' (Type | AssignExpression))?
    private static boolean TemplateAliasParameter_1_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateAliasParameter_1_4")) return false;
        TemplateAliasParameter_1_4_0(b, l + 1);
        return true;
    }

    // '=' (Type | AssignExpression)
    private static boolean TemplateAliasParameter_1_4_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateAliasParameter_1_4_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_EQ);
        r = r && TemplateAliasParameter_1_4_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Type | AssignExpression
    private static boolean TemplateAliasParameter_1_4_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateAliasParameter_1_4_0_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = TypeParser.Type(b, l + 1);
        if (!r) r = ExpressionParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // TemplateValueParameter
    //     | TemplateTypeParameter
    //     | TemplateAliasParameter
    //     | TemplateTupleParameter
    //     | TemplateThisParameter
    public static boolean TemplateParameter(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateParameter")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TEMPLATE_PARAMETER, "<template parameter>");
        r = TemplateValueParameter(b, l + 1);
        if (!r) r = TemplateTypeParameter(b, l + 1);
        if (!r) r = TemplateAliasParameter(b, l + 1);
        if (!r) r = TemplateTupleParameter(b, l + 1);
        if (!r) r = TemplateThisParameter(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // TemplateParameter (',' TemplateParameter)*
    public static boolean TemplateParameterList(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateParameterList")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TEMPLATE_PARAMETER_LIST, "<template parameter list>");
        r = TemplateParameter(b, l + 1);
        r = r && TemplateParameterList_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (',' TemplateParameter)*
    private static boolean TemplateParameterList_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateParameterList_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!TemplateParameterList_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "TemplateParameterList_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    // ',' TemplateParameter
    private static boolean TemplateParameterList_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateParameterList_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && TemplateParameter(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // '(' TemplateParameterList? ','? ')'
    public static boolean TemplateParameters(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateParameters")) return false;
        if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && TemplateParameters_1(b, l + 1);
        r = r && TemplateParameters_2(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, TEMPLATE_PARAMETERS, r);
        return r;
    }

    // TemplateParameterList?
    private static boolean TemplateParameters_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateParameters_1")) return false;
        TemplateParameterList(b, l + 1);
        return true;
    }

    // ','?
    private static boolean TemplateParameters_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateParameters_2")) return false;
        consumeToken(b, OP_COMMA);
        return true;
    }

    /* ********************************************************** */
    // 'this' TemplateTypeParameter
    public static boolean TemplateThisParameter(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateThisParameter")) return false;
        if (!nextTokenIs(b, KW_THIS)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_THIS);
        r = r && TemplateTypeParameter(b, l + 1);
        exit_section_(b, m, TEMPLATE_THIS_PARAMETER, r);
        return r;
    }

    /* ********************************************************** */
    // Identifier '...'
    public static boolean TemplateTupleParameter(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateTupleParameter")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_TRIPLEDOT);
        exit_section_(b, m, TEMPLATE_TUPLE_PARAMETER, r);
        return r;
    }

    /* ********************************************************** */
    // Type Identifier? '...'? (':' Type)? ('=' Type)? (':' AssignExpression)? TemplateValueParameterDefault?
    public static boolean TemplateTypeParameter(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateTypeParameter")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TEMPLATE_TYPE_PARAMETER, "<template type parameter>");
        r = TypeParser.Type(b, l + 1);
        r = r && TemplateTypeParameter_1(b, l + 1);
        r = r && TemplateTypeParameter_2(b, l + 1);
        r = r && TemplateTypeParameter_3(b, l + 1);
        r = r && TemplateTypeParameter_4(b, l + 1);
        r = r && TemplateTypeParameter_5(b, l + 1);
        r = r && TemplateTypeParameter_6(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // Identifier?
    private static boolean TemplateTypeParameter_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateTypeParameter_1")) return false;
        BaseRulesParser.Identifier(b, l + 1);
        return true;
    }

    // '...'?
    private static boolean TemplateTypeParameter_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateTypeParameter_2")) return false;
        consumeToken(b, OP_TRIPLEDOT);
        return true;
    }

    // (':' Type)?
    private static boolean TemplateTypeParameter_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateTypeParameter_3")) return false;
        TemplateTypeParameter_3_0(b, l + 1);
        return true;
    }

    // ':' Type
    private static boolean TemplateTypeParameter_3_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateTypeParameter_3_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COLON);
        r = r && TypeParser.Type(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('=' Type)?
    private static boolean TemplateTypeParameter_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateTypeParameter_4")) return false;
        TemplateTypeParameter_4_0(b, l + 1);
        return true;
    }

    // '=' Type
    private static boolean TemplateTypeParameter_4_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateTypeParameter_4_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_EQ);
        r = r && TypeParser.Type(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // (':' AssignExpression)?
    private static boolean TemplateTypeParameter_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateTypeParameter_5")) return false;
        TemplateTypeParameter_5_0(b, l + 1);
        return true;
    }

    // ':' AssignExpression
    private static boolean TemplateTypeParameter_5_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateTypeParameter_5_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COLON);
        r = r && ExpressionParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // TemplateValueParameterDefault?
    private static boolean TemplateTypeParameter_6(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateTypeParameter_6")) return false;
        TemplateValueParameterDefault(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // BasicType Declarator ('=' (AssignExpression | SpecialKeyword))
    //        | BasicType Declarator TemplateValueParameterSpecialization TemplateValueParameterDefault?
    public static boolean TemplateValueParameter(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateValueParameter")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TEMPLATE_VALUE_PARAMETER, "<template value parameter>");
        r = TemplateValueParameter_0(b, l + 1);
        if (!r) r = TemplateValueParameter_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // BasicType Declarator ('=' (AssignExpression | SpecialKeyword))
    private static boolean TemplateValueParameter_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateValueParameter_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = TypeParser.BasicType(b, l + 1);
        r = r && VariableDeclarationParser.Declarator(b, l + 1);
        r = r && TemplateValueParameter_0_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '=' (AssignExpression | SpecialKeyword)
    private static boolean TemplateValueParameter_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateValueParameter_0_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_EQ);
        r = r && TemplateValueParameter_0_2_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // AssignExpression | SpecialKeyword
    private static boolean TemplateValueParameter_0_2_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateValueParameter_0_2_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ExpressionParser.AssignExpression(b, l + 1);
        if (!r) r = MiscParser.SpecialKeyword(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // BasicType Declarator TemplateValueParameterSpecialization TemplateValueParameterDefault?
    private static boolean TemplateValueParameter_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateValueParameter_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = TypeParser.BasicType(b, l + 1);
        r = r && VariableDeclarationParser.Declarator(b, l + 1);
        r = r && TemplateValueParameterSpecialization(b, l + 1);
        r = r && TemplateValueParameter_1_3(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // TemplateValueParameterDefault?
    private static boolean TemplateValueParameter_1_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateValueParameter_1_3")) return false;
        TemplateValueParameterDefault(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // '=' ( AssignExpression | SpecialKeyword)
    public static boolean TemplateValueParameterDefault(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateValueParameterDefault")) return false;
        if (!nextTokenIs(b, OP_EQ)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_EQ);
        r = r && TemplateValueParameterDefault_1(b, l + 1);
        exit_section_(b, m, TEMPLATE_VALUE_PARAMETER_DEFAULT, r);
        return r;
    }

    // AssignExpression | SpecialKeyword
    private static boolean TemplateValueParameterDefault_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateValueParameterDefault_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ExpressionParser.AssignExpression(b, l + 1);
        if (!r) r = MiscParser.SpecialKeyword(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // ConditionalExpression
    static boolean TemplateValueParameterSpecialization(PsiBuilder b, int l) {
        return ExpressionParser.ConditionalExpression(b, l + 1);
    }

}
