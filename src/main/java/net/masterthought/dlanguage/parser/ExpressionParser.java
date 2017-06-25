package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 6/25/2017.
 */
public class ExpressionParser {
    /* ********************************************************** */
    // MulExpression [ AddExpression_]
    static boolean AddExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = MulExpression(b, l + 1);
        r = r && AddExpression_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // [ AddExpression_]
    private static boolean AddExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddExpression_1")) return false;
        AddExpression_(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // ('+' | '-' | '~') AddExpression
    public static boolean AddExpression_(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddExpression_")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _COLLAPSE_, ADD_EXPRESSION_, "<add expression>");
        r = AddExpression__0(b, l + 1);
        r = r && DLanguageParser.AddExpression(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // '+' | '-' | '~'
    private static boolean AddExpression__0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddExpression__0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PLUS);
        if (!r) r = consumeToken(b, OP_MINUS);
        if (!r) r = consumeToken(b, OP_TILDA);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // '&&'? (OrExpression | CmpExpression) [AndAndExpression]
    static boolean AndAndExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AndAndExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AndAndExpression_0(b, l + 1);
        r = r && AndAndExpression_1(b, l + 1);
        r = r && AndAndExpression_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '&&'?
    private static boolean AndAndExpression_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AndAndExpression_0")) return false;
        consumeToken(b, OP_BOOL_AND);
        return true;
    }

    // OrExpression | CmpExpression
    private static boolean AndAndExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AndAndExpression_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = OrExpression(b, l + 1);
        if (!r) r = CmpExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // [AndAndExpression]
    private static boolean AndAndExpression_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AndAndExpression_2")) return false;
        DLanguageParser.AndAndExpression(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // ShiftExpression [ AndExxpression_ ]
    static boolean AndExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AndExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ShiftExpression(b, l + 1);
        r = r && AndExpression_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // [ AndExxpression_ ]
    private static boolean AndExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AndExpression_1")) return false;
        AndExxpression_(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // '&' AndExpression
    public static boolean AndExxpression_(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AndExxpression_")) return false;
        if (!nextTokenIs(b, OP_AND)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_AND);
        r = r && DLanguageParser.AndExpression(b, l + 1);
        exit_section_(b, m, AND_EXXPRESSION_, r);
        return r;
    }

    /* ********************************************************** */
    // '[' ArgumentList? ','? ']'
    public static boolean ArrayLiteral(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ArrayLiteral")) return false;
        if (!nextTokenIs(b, OP_BRACKET_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BRACKET_LEFT);
        r = r && ArrayLiteral_1(b, l + 1);
        r = r && ArrayLiteral_2(b, l + 1);
        r = r && consumeToken(b, OP_BRACKET_RIGHT);
        exit_section_(b, m, ARRAY_LITERAL, r);
        return r;
    }

    // ArgumentList?
    private static boolean ArrayLiteral_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ArrayLiteral_1")) return false;
        ArgumentParser.ArgumentList(b, l + 1);
        return true;
    }

    // ','?
    private static boolean ArrayLiteral_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ArrayLiteral_2")) return false;
        consumeToken(b, OP_COMMA);
        return true;
    }

    /* ********************************************************** */
    // 'assert' '(' AssignExpression (',' AssignExpression)? ')'
    public static boolean AssertExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AssertExpression")) return false;
        if (!nextTokenIs(b, KW_ASSERT)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASSERT_EXPRESSION, null);
        r = consumeTokens(b, 1, KW_ASSERT, OP_PAR_LEFT);
        p = r; // pin = 1
        r = r && report_error_(b, AssignExpression(b, l + 1));
        r = p && report_error_(b, AssertExpression_3(b, l + 1)) && r;
        r = p && consumeToken(b, OP_PAR_RIGHT) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // (',' AssignExpression)?
    private static boolean AssertExpression_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AssertExpression_3")) return false;
        AssertExpression_3_0(b, l + 1);
        return true;
    }

    // ',' AssignExpression
    private static boolean AssertExpression_3_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AssertExpression_3_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // ConditionalExpression [ ( '=' | '+=' | '-=' | '*=' | '/=' | '%=' | '&=' | '|=' | '^=' | '~=' | '<<=' | '>>=' | '>>>=' | '^^=') AssignExpression]
    public static boolean AssignExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AssignExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _COLLAPSE_, ASSIGN_EXPRESSION, "<assign expression>");
        r = ConditionalExpression(b, l + 1);
        r = r && AssignExpression_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // [ ( '=' | '+=' | '-=' | '*=' | '/=' | '%=' | '&=' | '|=' | '^=' | '~=' | '<<=' | '>>=' | '>>>=' | '^^=') AssignExpression]
    private static boolean AssignExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AssignExpression_1")) return false;
        AssignExpression_1_0(b, l + 1);
        return true;
    }

    // ( '=' | '+=' | '-=' | '*=' | '/=' | '%=' | '&=' | '|=' | '^=' | '~=' | '<<=' | '>>=' | '>>>=' | '^^=') AssignExpression
    private static boolean AssignExpression_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AssignExpression_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AssignExpression_1_0_0(b, l + 1);
        r = r && DLanguageParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '=' | '+=' | '-=' | '*=' | '/=' | '%=' | '&=' | '|=' | '^=' | '~=' | '<<=' | '>>=' | '>>>=' | '^^='
    private static boolean AssignExpression_1_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AssignExpression_1_0_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_EQ);
        if (!r) r = consumeToken(b, OP_PLUS_EQ);
        if (!r) r = consumeToken(b, OP_MINUS_EQ);
        if (!r) r = consumeToken(b, OP_MUL_EQ);
        if (!r) r = consumeToken(b, OP_DIV_EQ);
        if (!r) r = consumeToken(b, OP_MOD_EQ);
        if (!r) r = consumeToken(b, OP_AND_EQ);
        if (!r) r = consumeToken(b, OP_OR_EQ);
        if (!r) r = consumeToken(b, OP_XOR_EQ);
        if (!r) r = consumeToken(b, OP_TILDA_EQ);
        if (!r) r = consumeToken(b, OP_SH_LEFT_EQ);
        if (!r) r = consumeToken(b, OP_SH_RIGHT_EQ);
        if (!r) r = consumeToken(b, OP_USH_RIGHT_EQ);
        if (!r) r = consumeToken(b, OP_POW_EQ);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // '[' KeyValuePairs ']'
    public static boolean AssocArrayLiteral(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AssocArrayLiteral")) return false;
        if (!nextTokenIs(b, OP_BRACKET_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BRACKET_LEFT);
        r = r && MiscParser.KeyValuePairs(b, l + 1);
        r = r && consumeToken(b, OP_BRACKET_RIGHT);
        exit_section_(b, m, ASSOC_ARRAY_LITERAL, r);
        return r;
    }

    /* ********************************************************** */
    // 'cast' '(' Type ')' UnaryExpression
    //     | 'cast' '(' TypeCtors? ')' UnaryExpression
    public static boolean CastExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "CastExpression")) return false;
        if (!nextTokenIs(b, KW_CAST)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = CastExpression_0(b, l + 1);
        if (!r) r = CastExpression_1(b, l + 1);
        exit_section_(b, m, CAST_EXPRESSION, r);
        return r;
    }

    // 'cast' '(' Type ')' UnaryExpression
    private static boolean CastExpression_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "CastExpression_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_CAST, OP_PAR_LEFT);
        r = r && TypeParser.Type(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        r = r && UnaryExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'cast' '(' TypeCtors? ')' UnaryExpression
    private static boolean CastExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "CastExpression_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_CAST, OP_PAR_LEFT);
        r = r && CastExpression_1_2(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        r = r && UnaryExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // TypeCtors?
    private static boolean CastExpression_1_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "CastExpression_1_2")) return false;
        TypeParser.TypeCtors(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // ShiftExpression
    //     | EqualExpression
    //     | IdentityExpression
    //     | RelExpression
    //     | InExpression
    static boolean CmpExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "CmpExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ShiftExpression(b, l + 1);
        if (!r) r = EqualExpression(b, l + 1);
        if (!r) r = IdentityExpression(b, l + 1);
        if (!r) r = RelExpression(b, l + 1);
        if (!r) r = InExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AssignExpression (',' AssignExpression)*
    public static boolean CommaExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "CommaExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, COMMA_EXPRESSION, "<comma expression>");
        r = DLanguageParser.AssignExpression(b, l + 1);
        r = r && CommaExpression_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (',' AssignExpression)*
    private static boolean CommaExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "CommaExpression_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!CommaExpression_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "CommaExpression_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    // ',' AssignExpression
    private static boolean CommaExpression_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "CommaExpression_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && DLanguageParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // OrOrExpression_ [ConditionalExpression_]
    static boolean ConditionalExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = OrOrExpression_(b, l + 1);
        r = r && ConditionalExpression_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // [ConditionalExpression_]
    private static boolean ConditionalExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalExpression_1")) return false;
        ConditionalExpression_(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // '?' Expression ':' ConditionalExpression
    public static boolean ConditionalExpression_(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalExpression_")) return false;
        if (!nextTokenIs(b, OP_QUEST)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_QUEST);
        r = r && Expression(b, l + 1);
        r = r && consumeToken(b, OP_COLON);
        r = r && DLanguageParser.ConditionalExpression(b, l + 1);
        exit_section_(b, m, CONDITIONAL_EXPRESSION_, r);
        return r;
    }

    /* ********************************************************** */
    // 'delete' UnaryExpression
    public static boolean DeleteExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeleteExpression")) return false;
        if (!nextTokenIs(b, KW_DELETE)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_DELETE);
        r = r && UnaryExpression(b, l + 1);
        exit_section_(b, m, DELETE_EXPRESSION, r);
        return r;
    }

    /* ********************************************************** */
    // ('==' | '!=') [ShiftExpression]
    public static boolean EqualExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EqualExpression")) return false;
        if (!nextTokenIs(b, "<equal expression>", OP_NOT_EQ, OP_EQ_EQ)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, EQUAL_EXPRESSION, "<equal expression>");
        r = EqualExpression_0(b, l + 1);
        p = r; // pin = 1
        r = r && EqualExpression_1(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // '==' | '!='
    private static boolean EqualExpression_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EqualExpression_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_EQ_EQ);
        if (!r) r = consumeToken(b, OP_NOT_EQ);
        exit_section_(b, m, null, r);
        return r;
    }

    // [ShiftExpression]
    private static boolean EqualExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EqualExpression_1")) return false;
        ShiftExpression(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // CommaExpression
    static boolean Expression(PsiBuilder b, int l) {
        return DLanguageParser.CommaExpression(b, l + 1);
    }

    /* ********************************************************** */
    // Expression ';'?
    public static boolean ExpressionStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ExpressionStatement")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, EXPRESSION_STATEMENT, "<expression statement>");
        r = DLanguageParser.Expression(b, l + 1);
        r = r && ExpressionStatement_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ';'?
    private static boolean ExpressionStatement_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ExpressionStatement_1")) return false;
        consumeToken(b, OP_SCOLON);
        return true;
    }

    /* ********************************************************** */
    // 'function' Type? ParameterAttributes? FunctionLiteralBody?
    //     | 'delegate' Type? ParameterMemberAttributes? FunctionLiteralBody?
    //     | ParameterMemberAttributes FunctionLiteralBody//Body cannot be optional
    //     | FunctionLiteralBody
    //     | Lambda
    public static boolean FunctionLiteral(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionLiteral")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FUNCTION_LITERAL, "<function literal>");
        r = FunctionLiteral_0(b, l + 1);
        if (!r) r = FunctionLiteral_1(b, l + 1);
        if (!r) r = FunctionLiteral_2(b, l + 1);
        if (!r) r = FunctionLiteralBody(b, l + 1);
        if (!r) r = Lambda(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // 'function' Type? ParameterAttributes? FunctionLiteralBody?
    private static boolean FunctionLiteral_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionLiteral_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_FUNCTION);
        r = r && FunctionLiteral_0_1(b, l + 1);
        r = r && FunctionLiteral_0_2(b, l + 1);
        r = r && FunctionLiteral_0_3(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Type?
    private static boolean FunctionLiteral_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionLiteral_0_1")) return false;
        TypeParser.Type(b, l + 1);
        return true;
    }

    // ParameterAttributes?
    private static boolean FunctionLiteral_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionLiteral_0_2")) return false;
        ParameterParser.ParameterAttributes(b, l + 1);
        return true;
    }

    // FunctionLiteralBody?
    private static boolean FunctionLiteral_0_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionLiteral_0_3")) return false;
        FunctionLiteralBody(b, l + 1);
        return true;
    }

    // 'delegate' Type? ParameterMemberAttributes? FunctionLiteralBody?
    private static boolean FunctionLiteral_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionLiteral_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_DELEGATE);
        r = r && FunctionLiteral_1_1(b, l + 1);
        r = r && FunctionLiteral_1_2(b, l + 1);
        r = r && FunctionLiteral_1_3(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Type?
    private static boolean FunctionLiteral_1_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionLiteral_1_1")) return false;
        TypeParser.Type(b, l + 1);
        return true;
    }

    // ParameterMemberAttributes?
    private static boolean FunctionLiteral_1_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionLiteral_1_2")) return false;
        ParameterParser.ParameterMemberAttributes(b, l + 1);
        return true;
    }

    // FunctionLiteralBody?
    private static boolean FunctionLiteral_1_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionLiteral_1_3")) return false;
        FunctionLiteralBody(b, l + 1);
        return true;
    }

    // ParameterMemberAttributes FunctionLiteralBody
    private static boolean FunctionLiteral_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionLiteral_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ParameterParser.ParameterMemberAttributes(b, l + 1);
        r = r && FunctionLiteralBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // BlockStatement
    //     | Property* BlockStatement
    //     | FunctionContracts BodyStatement?
    public static boolean FunctionLiteralBody(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionLiteralBody")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FUNCTION_LITERAL_BODY, "<function literal body>");
        r = StatementParser.BlockStatement(b, l + 1);
        if (!r) r = FunctionLiteralBody_1(b, l + 1);
        if (!r) r = FunctionLiteralBody_2(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // Property* BlockStatement
    private static boolean FunctionLiteralBody_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionLiteralBody_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = FunctionLiteralBody_1_0(b, l + 1);
        r = r && StatementParser.BlockStatement(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Property*
    private static boolean FunctionLiteralBody_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionLiteralBody_1_0")) return false;
        int c = current_position_(b);
        while (true) {
            if (!AttributesParser.Property(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "FunctionLiteralBody_1_0", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    // FunctionContracts BodyStatement?
    private static boolean FunctionLiteralBody_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionLiteralBody_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = DeclarationParser.FunctionContracts(b, l + 1);
        r = r && FunctionLiteralBody_2_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // BodyStatement?
    private static boolean FunctionLiteralBody_2_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionLiteralBody_2_1")) return false;
        StatementParser.BodyStatement(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // ('is'|'!is') (ShiftExpression | AssignExpression)
    public static boolean IdentityExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IdentityExpression")) return false;
        if (!nextTokenIs(b, "<identity expression>", KW_NOT_IS, KW_IS)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, IDENTITY_EXPRESSION, "<identity expression>");
        r = IdentityExpression_0(b, l + 1);
        p = r; // pin = 1
        r = r && IdentityExpression_1(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // 'is'|'!is'
    private static boolean IdentityExpression_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IdentityExpression_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_IS);
        if (!r) r = consumeToken(b, KW_NOT_IS);
        exit_section_(b, m, null, r);
        return r;
    }

    // ShiftExpression | AssignExpression
    private static boolean IdentityExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IdentityExpression_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ShiftExpression(b, l + 1);
        if (!r) r = DLanguageParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'import' '(' AssignExpression ')'
    public static boolean ImportExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ImportExpression")) return false;
        if (!nextTokenIs(b, KW_IMPORT)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, IMPORT_EXPRESSION, null);
        r = consumeTokens(b, 1, KW_IMPORT, OP_PAR_LEFT);
        p = r; // pin = 1
        r = r && report_error_(b, DLanguageParser.AssignExpression(b, l + 1));
        r = p && consumeToken(b, OP_PAR_RIGHT) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // ('in'| ('!''in')) ShiftExpression
    public static boolean InExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InExpression")) return false;
        if (!nextTokenIs(b, "<in expression>", OP_NOT, KW_IN)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, IN_EXPRESSION, "<in expression>");
        r = InExpression_0(b, l + 1);
        p = r; // pin = 1
        r = r && ShiftExpression(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // 'in'| ('!''in')
    private static boolean InExpression_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InExpression_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_IN);
        if (!r) r = InExpression_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '!''in'
    private static boolean InExpression_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InExpression_0_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, OP_NOT, KW_IN);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // '[' ArgumentList ']' [PostfixExpression]
    public static boolean IndexExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IndexExpression")) return false;
        if (!nextTokenIs(b, OP_BRACKET_LEFT)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, INDEX_EXPRESSION, null);
        r = consumeToken(b, OP_BRACKET_LEFT);
        r = r && ArgumentParser.ArgumentList(b, l + 1);
        r = r && consumeToken(b, OP_BRACKET_RIGHT);
        p = r; // pin = 3
        r = r && IndexExpression_3(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // [PostfixExpression]
    private static boolean IndexExpression_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IndexExpression_3")) return false;
        PostfixExpression(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // ('is' | '!is') IsExpressionSuffix
    public static boolean IsExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IsExpression")) return false;
        if (!nextTokenIs(b, "<is expression>", KW_NOT_IS, KW_IS)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, IS_EXPRESSION, "<is expression>");
        r = IsExpression_0(b, l + 1);
        r = r && IsExpressionSuffix(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // 'is' | '!is'
    private static boolean IsExpression_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IsExpression_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_IS);
        if (!r) r = consumeToken(b, KW_NOT_IS);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // '(' Type ')'
    //     | '(' Type ':' TypeSpecialization ')'
    //     | '(' Type '==' TypeSpecialization ')'
    //     | '(' Type ':' TypeSpecialization ',' TemplateParameterList ')'
    //     | '(' Type '==' TypeSpecialization ',' TemplateParameterList ')'
    //     | '(' Type Identifier ')'
    //     | '(' Type Identifier ':' TypeSpecialization ')'
    //     | '(' Type Identifier '==' TypeSpecialization ')'
    //     | '(' Type Identifier ':' TypeSpecialization ',' TemplateParameterList ')'
    //     | '(' Type Identifier '==' TypeSpecialization ',' TemplateParameterList ')'
    static boolean IsExpressionSuffix(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IsExpressionSuffix")) return false;
        if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = IsExpressionSuffix_0(b, l + 1);
        if (!r) r = IsExpressionSuffix_1(b, l + 1);
        if (!r) r = IsExpressionSuffix_2(b, l + 1);
        if (!r) r = IsExpressionSuffix_3(b, l + 1);
        if (!r) r = IsExpressionSuffix_4(b, l + 1);
        if (!r) r = IsExpressionSuffix_5(b, l + 1);
        if (!r) r = IsExpressionSuffix_6(b, l + 1);
        if (!r) r = IsExpressionSuffix_7(b, l + 1);
        if (!r) r = IsExpressionSuffix_8(b, l + 1);
        if (!r) r = IsExpressionSuffix_9(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '(' Type ')'
    private static boolean IsExpressionSuffix_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IsExpressionSuffix_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && TypeParser.Type(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // '(' Type ':' TypeSpecialization ')'
    private static boolean IsExpressionSuffix_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IsExpressionSuffix_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && TypeParser.Type(b, l + 1);
        r = r && consumeToken(b, OP_COLON);
        r = r && TypeParser.TypeSpecialization(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // '(' Type '==' TypeSpecialization ')'
    private static boolean IsExpressionSuffix_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IsExpressionSuffix_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && TypeParser.Type(b, l + 1);
        r = r && consumeToken(b, OP_EQ_EQ);
        r = r && TypeParser.TypeSpecialization(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // '(' Type ':' TypeSpecialization ',' TemplateParameterList ')'
    private static boolean IsExpressionSuffix_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IsExpressionSuffix_3")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && TypeParser.Type(b, l + 1);
        r = r && consumeToken(b, OP_COLON);
        r = r && TypeParser.TypeSpecialization(b, l + 1);
        r = r && consumeToken(b, OP_COMMA);
        r = r && TemplateParameterParser.TemplateParameterList(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // '(' Type '==' TypeSpecialization ',' TemplateParameterList ')'
    private static boolean IsExpressionSuffix_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IsExpressionSuffix_4")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && TypeParser.Type(b, l + 1);
        r = r && consumeToken(b, OP_EQ_EQ);
        r = r && TypeParser.TypeSpecialization(b, l + 1);
        r = r && consumeToken(b, OP_COMMA);
        r = r && TemplateParameterParser.TemplateParameterList(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // '(' Type Identifier ')'
    private static boolean IsExpressionSuffix_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IsExpressionSuffix_5")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && TypeParser.Type(b, l + 1);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // '(' Type Identifier ':' TypeSpecialization ')'
    private static boolean IsExpressionSuffix_6(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IsExpressionSuffix_6")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && TypeParser.Type(b, l + 1);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_COLON);
        r = r && TypeParser.TypeSpecialization(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // '(' Type Identifier '==' TypeSpecialization ')'
    private static boolean IsExpressionSuffix_7(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IsExpressionSuffix_7")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && TypeParser.Type(b, l + 1);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_EQ_EQ);
        r = r && TypeParser.TypeSpecialization(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // '(' Type Identifier ':' TypeSpecialization ',' TemplateParameterList ')'
    private static boolean IsExpressionSuffix_8(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IsExpressionSuffix_8")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && TypeParser.Type(b, l + 1);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_COLON);
        r = r && TypeParser.TypeSpecialization(b, l + 1);
        r = r && consumeToken(b, OP_COMMA);
        r = r && TemplateParameterParser.TemplateParameterList(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // '(' Type Identifier '==' TypeSpecialization ',' TemplateParameterList ')'
    private static boolean IsExpressionSuffix_9(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IsExpressionSuffix_9")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && TypeParser.Type(b, l + 1);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_EQ_EQ);
        r = r && TypeParser.TypeSpecialization(b, l + 1);
        r = r && consumeToken(b, OP_COMMA);
        r = r && TemplateParameterParser.TemplateParameterList(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'function' Type? ParameterAttributes '=>' AssignExpression
    //     | 'delegate' Type? ParameterMemberAttributes '=>' AssignExpression
    //     | ParameterMemberAttributes '=>' AssignExpression
    //     | Identifier? '=>' AssignExpression
    public static boolean Lambda(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Lambda")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, LAMBDA, "<lambda>");
        r = Lambda_0(b, l + 1);
        if (!r) r = Lambda_1(b, l + 1);
        if (!r) r = Lambda_2(b, l + 1);
        if (!r) r = Lambda_3(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // 'function' Type? ParameterAttributes '=>' AssignExpression
    private static boolean Lambda_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Lambda_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_FUNCTION);
        r = r && Lambda_0_1(b, l + 1);
        r = r && ParameterParser.ParameterAttributes(b, l + 1);
        r = r && consumeToken(b, OP_LAMBDA_ARROW);
        r = r && DLanguageParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Type?
    private static boolean Lambda_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Lambda_0_1")) return false;
        TypeParser.Type(b, l + 1);
        return true;
    }

    // 'delegate' Type? ParameterMemberAttributes '=>' AssignExpression
    private static boolean Lambda_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Lambda_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_DELEGATE);
        r = r && Lambda_1_1(b, l + 1);
        r = r && ParameterParser.ParameterMemberAttributes(b, l + 1);
        r = r && consumeToken(b, OP_LAMBDA_ARROW);
        r = r && DLanguageParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Type?
    private static boolean Lambda_1_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Lambda_1_1")) return false;
        TypeParser.Type(b, l + 1);
        return true;
    }

    // ParameterMemberAttributes '=>' AssignExpression
    private static boolean Lambda_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Lambda_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ParameterParser.ParameterMemberAttributes(b, l + 1);
        r = r && consumeToken(b, OP_LAMBDA_ARROW);
        r = r && DLanguageParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Identifier? '=>' AssignExpression
    private static boolean Lambda_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Lambda_3")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Lambda_3_0(b, l + 1);
        r = r && consumeToken(b, OP_LAMBDA_ARROW);
        r = r && DLanguageParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Identifier?
    private static boolean Lambda_3_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Lambda_3_0")) return false;
        BaseRulesParser.Identifier(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'mixin' '(' ( AssignExpression) ')'
    public static boolean MixinExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MixinExpression")) return false;
        if (!nextTokenIs(b, KW_MIXIN)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_MIXIN, OP_PAR_LEFT);
        r = r && MixinExpression_2(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, MIXIN_EXPRESSION, r);
        return r;
    }

    // ( AssignExpression)
    private static boolean MixinExpression_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MixinExpression_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = DLanguageParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // UnaryExpression [ MulExpression_ ]
    static boolean MulExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MulExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = UnaryExpression(b, l + 1);
        r = r && MulExpression_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // [ MulExpression_ ]
    private static boolean MulExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MulExpression_1")) return false;
        MulExpression_(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // ('*' | '/' | '%') MulExpression
    public static boolean MulExpression_(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MulExpression_")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _COLLAPSE_, MUL_EXPRESSION_, "<mul expression>");
        r = MulExpression__0(b, l + 1);
        r = r && DLanguageParser.MulExpression(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // '*' | '/' | '%'
    private static boolean MulExpression__0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MulExpression__0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_ASTERISK);
        if (!r) r = consumeToken(b, OP_DIV);
        if (!r) r = consumeToken(b, OP_MOD);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // ((( (AssignExpression '..')) AssignExpression) | AssignExpression) [',' MultipleAssign ]
    public static boolean MultipleAssign(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MultipleAssign")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, MULTIPLE_ASSIGN, "<multiple assign>");
        r = MultipleAssign_0(b, l + 1);
        r = r && MultipleAssign_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (( (AssignExpression '..')) AssignExpression) | AssignExpression
    private static boolean MultipleAssign_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MultipleAssign_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = MultipleAssign_0_0(b, l + 1);
        if (!r) r = DLanguageParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ( (AssignExpression '..')) AssignExpression
    private static boolean MultipleAssign_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MultipleAssign_0_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = MultipleAssign_0_0_0(b, l + 1);
        r = r && DLanguageParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // AssignExpression '..'
    private static boolean MultipleAssign_0_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MultipleAssign_0_0_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = DLanguageParser.AssignExpression(b, l + 1);
        r = r && consumeToken(b, OP_DDOT);
        exit_section_(b, m, null, r);
        return r;
    }

    // [',' MultipleAssign ]
    private static boolean MultipleAssign_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MultipleAssign_1")) return false;
        MultipleAssign_1_0(b, l + 1);
        return true;
    }

    // ',' MultipleAssign
    private static boolean MultipleAssign_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MultipleAssign_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && DLanguageParser.MultipleAssign(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'new' AllocatorArguments? 'class' ClassArguments? SuperClass? Interfaces? AggregateBody
    public static boolean NewAnonClassExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NewAnonClassExpression")) return false;
        if (!nextTokenIs(b, KW_NEW)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, NEW_ANON_CLASS_EXPRESSION, null);
        r = consumeToken(b, KW_NEW);
        p = r; // pin = 1
        r = r && report_error_(b, NewAnonClassExpression_1(b, l + 1));
        r = p && report_error_(b, consumeToken(b, KW_CLASS)) && r;
        r = p && report_error_(b, NewAnonClassExpression_3(b, l + 1)) && r;
        r = p && report_error_(b, NewAnonClassExpression_4(b, l + 1)) && r;
        r = p && report_error_(b, NewAnonClassExpression_5(b, l + 1)) && r;
        r = p && DeclarationParser.AggregateBody(b, l + 1) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // AllocatorArguments?
    private static boolean NewAnonClassExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NewAnonClassExpression_1")) return false;
        DeclDefParser.AllocatorArguments(b, l + 1);
        return true;
    }

    // ClassArguments?
    private static boolean NewAnonClassExpression_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NewAnonClassExpression_3")) return false;
        DeclarationParser.ClassArguments(b, l + 1);
        return true;
    }

    // SuperClass?
    private static boolean NewAnonClassExpression_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NewAnonClassExpression_4")) return false;
        DeclarationParser.SuperClass(b, l + 1);
        return true;
    }

    // Interfaces?
    private static boolean NewAnonClassExpression_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NewAnonClassExpression_5")) return false;
        DeclarationParser.Interfaces(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'new' AllocatorArguments? Type
    //     | NewExpressionWithArgs
    public static boolean NewExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NewExpression")) return false;
        if (!nextTokenIs(b, KW_NEW)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = NewExpression_0(b, l + 1);
        if (!r) r = NewExpressionWithArgs(b, l + 1);
        exit_section_(b, m, NEW_EXPRESSION, r);
        return r;
    }

    // 'new' AllocatorArguments? Type
    private static boolean NewExpression_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NewExpression_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_NEW);
        r = r && NewExpression_0_1(b, l + 1);
        r = r && TypeParser.Type(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // AllocatorArguments?
    private static boolean NewExpression_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NewExpression_0_1")) return false;
        DeclDefParser.AllocatorArguments(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'new' AllocatorArguments? Type '[' AssignExpression ']'
    //    | 'new' AllocatorArguments? Type ('(' ArgumentList? ')')?
    //    | NewAnonClassExpression
    public static boolean NewExpressionWithArgs(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NewExpressionWithArgs")) return false;
        if (!nextTokenIs(b, KW_NEW)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = NewExpressionWithArgs_0(b, l + 1);
        if (!r) r = NewExpressionWithArgs_1(b, l + 1);
        if (!r) r = DLanguageParser.NewAnonClassExpression(b, l + 1);
        exit_section_(b, m, NEW_EXPRESSION_WITH_ARGS, r);
        return r;
    }

    // 'new' AllocatorArguments? Type '[' AssignExpression ']'
    private static boolean NewExpressionWithArgs_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NewExpressionWithArgs_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_NEW);
        r = r && NewExpressionWithArgs_0_1(b, l + 1);
        r = r && TypeParser.Type(b, l + 1);
        r = r && consumeToken(b, OP_BRACKET_LEFT);
        r = r && DLanguageParser.AssignExpression(b, l + 1);
        r = r && consumeToken(b, OP_BRACKET_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // AllocatorArguments?
    private static boolean NewExpressionWithArgs_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NewExpressionWithArgs_0_1")) return false;
        DeclDefParser.AllocatorArguments(b, l + 1);
        return true;
    }

    // 'new' AllocatorArguments? Type ('(' ArgumentList? ')')?
    private static boolean NewExpressionWithArgs_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NewExpressionWithArgs_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_NEW);
        r = r && NewExpressionWithArgs_1_1(b, l + 1);
        r = r && TypeParser.Type(b, l + 1);
        r = r && NewExpressionWithArgs_1_3(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // AllocatorArguments?
    private static boolean NewExpressionWithArgs_1_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NewExpressionWithArgs_1_1")) return false;
        DeclDefParser.AllocatorArguments(b, l + 1);
        return true;
    }

    // ('(' ArgumentList? ')')?
    private static boolean NewExpressionWithArgs_1_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NewExpressionWithArgs_1_3")) return false;
        NewExpressionWithArgs_1_3_0(b, l + 1);
        return true;
    }

    // '(' ArgumentList? ')'
    private static boolean NewExpressionWithArgs_1_3_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NewExpressionWithArgs_1_3_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && NewExpressionWithArgs_1_3_0_1(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // ArgumentList?
    private static boolean NewExpressionWithArgs_1_3_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NewExpressionWithArgs_1_3_0_1")) return false;
        ArgumentParser.ArgumentList(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // XorExpression [OrExpression]
    static boolean OrExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "OrExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = XorExpression(b, l + 1);
        r = r && OrExpression_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // [OrExpression]
    private static boolean OrExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "OrExpression_1")) return false;
        DLanguageParser.OrExpression(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // '||' OrOrExpression_
    public static boolean OrOrExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "OrOrExpression")) return false;
        if (!nextTokenIs(b, OP_BOOL_OR)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BOOL_OR);
        r = r && OrOrExpression_(b, l + 1);
        exit_section_(b, m, OR_OR_EXPRESSION, r);
        return r;
    }

    /* ********************************************************** */
    // AndAndExpression [ OrOrExpression]
    static boolean OrOrExpression_(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "OrOrExpression_")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = DLanguageParser.AndAndExpression(b, l + 1);
        r = r && OrOrExpression__1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // [ OrOrExpression]
    private static boolean OrOrExpression__1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "OrOrExpression__1")) return false;
        DLanguageParser.OrOrExpression(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // PrimaryExpression
    //     | '.' Identifier [PostfixExpression]
    //     | '.' TemplateInstance [PostfixExpression]
    //     | '.' NewExpression [PostfixExpression]
    //     | '++' [PostfixExpression]
    //     |  '--' [PostfixExpression]
    //     | '(' ArgumentList? ')' [PostfixExpression]
    //     | TypeCtors? BasicType '(' ArgumentList? ')' [PostfixExpression]
    //     | IndexExpression
    //     | SliceExpression
    public static boolean PostfixExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, POSTFIX_EXPRESSION, "<postfix expression>");
        r = PrimaryExpression(b, l + 1);
        if (!r) r = PostfixExpression_1(b, l + 1);
        if (!r) r = PostfixExpression_2(b, l + 1);
        if (!r) r = PostfixExpression_3(b, l + 1);
        if (!r) r = PostfixExpression_4(b, l + 1);
        if (!r) r = PostfixExpression_5(b, l + 1);
        if (!r) r = PostfixExpression_6(b, l + 1);
        if (!r) r = PostfixExpression_7(b, l + 1);
        if (!r) r = DLanguageParser.IndexExpression(b, l + 1);
        if (!r) r = SliceExpression(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // '.' Identifier [PostfixExpression]
    private static boolean PostfixExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_DOT);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && PostfixExpression_1_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // [PostfixExpression]
    private static boolean PostfixExpression_1_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_1_2")) return false;
        DLanguageParser.PostfixExpression(b, l + 1);
        return true;
    }

    // '.' TemplateInstance [PostfixExpression]
    private static boolean PostfixExpression_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_DOT);
        r = r && MiscParser.TemplateInstance(b, l + 1);
        r = r && PostfixExpression_2_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // [PostfixExpression]
    private static boolean PostfixExpression_2_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_2_2")) return false;
        DLanguageParser.PostfixExpression(b, l + 1);
        return true;
    }

    // '.' NewExpression [PostfixExpression]
    private static boolean PostfixExpression_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_3")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_DOT);
        r = r && DLanguageParser.NewExpression(b, l + 1);
        r = r && PostfixExpression_3_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // [PostfixExpression]
    private static boolean PostfixExpression_3_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_3_2")) return false;
        DLanguageParser.PostfixExpression(b, l + 1);
        return true;
    }

    // '++' [PostfixExpression]
    private static boolean PostfixExpression_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_4")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PLUS_PLUS);
        r = r && PostfixExpression_4_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // [PostfixExpression]
    private static boolean PostfixExpression_4_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_4_1")) return false;
        DLanguageParser.PostfixExpression(b, l + 1);
        return true;
    }

    // '--' [PostfixExpression]
    private static boolean PostfixExpression_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_5")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_MINUS_MINUS);
        r = r && PostfixExpression_5_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // [PostfixExpression]
    private static boolean PostfixExpression_5_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_5_1")) return false;
        DLanguageParser.PostfixExpression(b, l + 1);
        return true;
    }

    // '(' ArgumentList? ')' [PostfixExpression]
    private static boolean PostfixExpression_6(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_6")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && PostfixExpression_6_1(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        r = r && PostfixExpression_6_3(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ArgumentList?
    private static boolean PostfixExpression_6_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_6_1")) return false;
        ArgumentParser.ArgumentList(b, l + 1);
        return true;
    }

    // [PostfixExpression]
    private static boolean PostfixExpression_6_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_6_3")) return false;
        DLanguageParser.PostfixExpression(b, l + 1);
        return true;
    }

    // TypeCtors? BasicType '(' ArgumentList? ')' [PostfixExpression]
    private static boolean PostfixExpression_7(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_7")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = PostfixExpression_7_0(b, l + 1);
        r = r && TypeParser.BasicType(b, l + 1);
        r = r && consumeToken(b, OP_PAR_LEFT);
        r = r && PostfixExpression_7_3(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        r = r && PostfixExpression_7_5(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // TypeCtors?
    private static boolean PostfixExpression_7_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_7_0")) return false;
        TypeParser.TypeCtors(b, l + 1);
        return true;
    }

    // ArgumentList?
    private static boolean PostfixExpression_7_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_7_3")) return false;
        ArgumentParser.ArgumentList(b, l + 1);
        return true;
    }

    // [PostfixExpression]
    private static boolean PostfixExpression_7_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PostfixExpression_7_5")) return false;
        DLanguageParser.PostfixExpression(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // PostfixExpression [PowExpression_]
    static boolean PowExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PowExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = DLanguageParser.PostfixExpression(b, l + 1);
        r = r && PowExpression_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // [PowExpression_]
    private static boolean PowExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PowExpression_1")) return false;
        PowExpression_(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // '^^' UnaryExpression PowExpression
    public static boolean PowExpression_(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PowExpression_")) return false;
        if (!nextTokenIs(b, OP_POW)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_POW);
        r = r && UnaryExpression(b, l + 1);
        r = r && DLanguageParser.PowExpression(b, l + 1);
        exit_section_(b, m, POW_EXPRESSION_, r);
        return r;
    }

    /* ********************************************************** */
    // ('.')? (TemplateInstance | Identifier)
    //     | FunctionLiteral //must be above ( Expression) and Type
    //     | Type
    //     | 'this'
    //     | 'super'
    //     | 'null'
    //     | 'true'
    //     | 'false'
    //     | '$'
    //     | INTEGER_LITERAL
    //     | FLOAT_LITERAL
    //     | CHARACTER_LITERAL
    //     | StringLiterals
    //     | ArrayLiteral
    //     | AssocArrayLiteral
    //     | AssertExpression
    //     | MixinExpression
    //     | ImportExpression
    //     | NewExpressionWithArgs
    //     | BasicTypeX '.' Identifier
    //     | Typeof
    //     | TypeidExpression
    //     | IsExpression
    //     | '(' Expression ')'
    //     | TraitsExpression
    //     | SpecialKeyword
    public static boolean PrimaryExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PrimaryExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, PRIMARY_EXPRESSION, "<primary expression>");
        r = PrimaryExpression_0(b, l + 1);
        if (!r) r = DLanguageParser.FunctionLiteral(b, l + 1);
        if (!r) r = TypeParser.Type(b, l + 1);
        if (!r) r = consumeToken(b, KW_THIS);
        if (!r) r = consumeToken(b, KW_SUPER);
        if (!r) r = consumeToken(b, KW_NULL);
        if (!r) r = consumeToken(b, KW_TRUE);
        if (!r) r = consumeToken(b, KW_FALSE);
        if (!r) r = consumeToken(b, OP_DOLLAR);
        if (!r) r = consumeToken(b, INTEGER_LITERAL);
        if (!r) r = consumeToken(b, FLOAT_LITERAL);
        if (!r) r = consumeToken(b, CHARACTER_LITERAL);
        if (!r) r = BaseRulesParser.StringLiterals(b, l + 1);
        if (!r) r = DLanguageParser.ArrayLiteral(b, l + 1);
        if (!r) r = DLanguageParser.AssocArrayLiteral(b, l + 1);
        if (!r) r = DLanguageParser.AssertExpression(b, l + 1);
        if (!r) r = DLanguageParser.MixinExpression(b, l + 1);
        if (!r) r = DLanguageParser.ImportExpression(b, l + 1);
        if (!r) r = DLanguageParser.NewExpressionWithArgs(b, l + 1);
        if (!r) r = PrimaryExpression_19(b, l + 1);
        if (!r) r = TypeParser.Typeof(b, l + 1);
        if (!r) r = TypeidExpression(b, l + 1);
        if (!r) r = DLanguageParser.IsExpression(b, l + 1);
        if (!r) r = PrimaryExpression_23(b, l + 1);
        if (!r) r = TraitsExpression(b, l + 1);
        if (!r) r = MiscParser.SpecialKeyword(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ('.')? (TemplateInstance | Identifier)
    private static boolean PrimaryExpression_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PrimaryExpression_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = PrimaryExpression_0_0(b, l + 1);
        r = r && PrimaryExpression_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('.')?
    private static boolean PrimaryExpression_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PrimaryExpression_0_0")) return false;
        consumeToken(b, OP_DOT);
        return true;
    }

    // TemplateInstance | Identifier
    private static boolean PrimaryExpression_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PrimaryExpression_0_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = MiscParser.TemplateInstance(b, l + 1);
        if (!r) r = BaseRulesParser.Identifier(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // BasicTypeX '.' Identifier
    private static boolean PrimaryExpression_19(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PrimaryExpression_19")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = TypeParser.BasicTypeX(b, l + 1);
        r = r && consumeToken(b, OP_DOT);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '(' Expression ')'
    private static boolean PrimaryExpression_23(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PrimaryExpression_23")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && DLanguageParser.Expression(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AddExpression [ShiftExpression_]
    static boolean ShiftExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ShiftExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = DLanguageParser.AddExpression(b, l + 1);
        r = r && ShiftExpression_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // [ShiftExpression_]
    private static boolean ShiftExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ShiftExpression_1")) return false;
        ShiftExpression_(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // ('<<' | '>>' | '>>>') ShiftExpression
    public static boolean ShiftExpression_(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ShiftExpression_")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _COLLAPSE_, SHIFT_EXPRESSION_, "<shift expression>");
        r = ShiftExpression__0(b, l + 1);
        r = r && DLanguageParser.ShiftExpression(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // '<<' | '>>' | '>>>'
    private static boolean ShiftExpression__0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ShiftExpression__0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_SH_LEFT);
        if (!r) r = consumeToken(b, OP_SH_RIGHT);
        if (!r) r = consumeToken(b, OP_USH_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // '[' ']' [PostfixExpression]
    //     |  '[' MultipleAssign ']' [PostfixExpression]
    public static boolean SliceExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SliceExpression")) return false;
        if (!nextTokenIs(b, OP_BRACKET_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = SliceExpression_0(b, l + 1);
        if (!r) r = SliceExpression_1(b, l + 1);
        exit_section_(b, m, SLICE_EXPRESSION, r);
        return r;
    }

    // '[' ']' [PostfixExpression]
    private static boolean SliceExpression_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SliceExpression_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, OP_BRACKET_LEFT, OP_BRACKET_RIGHT);
        r = r && SliceExpression_0_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // [PostfixExpression]
    private static boolean SliceExpression_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SliceExpression_0_2")) return false;
        DLanguageParser.PostfixExpression(b, l + 1);
        return true;
    }

    // '[' MultipleAssign ']' [PostfixExpression]
    private static boolean SliceExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SliceExpression_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BRACKET_LEFT);
        r = r && DLanguageParser.MultipleAssign(b, l + 1);
        r = r && consumeToken(b, OP_BRACKET_RIGHT);
        r = r && SliceExpression_1_3(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // [PostfixExpression]
    private static boolean SliceExpression_1_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SliceExpression_1_3")) return false;
        DLanguageParser.PostfixExpression(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // AssignExpression
    //     | Type
    public static boolean TraitsArgument(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TraitsArgument")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TRAITS_ARGUMENT, "<traits argument>");
        r = DLanguageParser.AssignExpression(b, l + 1);
        if (!r) r = TypeParser.Type(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // TraitsArgument (',' TraitsArguments)*
    public static boolean TraitsArguments(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TraitsArguments")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TRAITS_ARGUMENTS, "<traits arguments>");
        r = DLanguageParser.TraitsArgument(b, l + 1);
        r = r && TraitsArguments_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (',' TraitsArguments)*
    private static boolean TraitsArguments_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TraitsArguments_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!TraitsArguments_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "TraitsArguments_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    // ',' TraitsArguments
    private static boolean TraitsArguments_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TraitsArguments_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && DLanguageParser.TraitsArguments(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // '__traits' ('(' TraitsKeyword ',' TraitsArguments ')')?
    public static boolean TraitsExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TraitsExpression")) return false;
        if (!nextTokenIs(b, KW___TRAITS)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TRAITS_EXPRESSION, null);
        r = consumeToken(b, KW___TRAITS);
        p = r; // pin = 1
        r = r && TraitsExpression_1(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // ('(' TraitsKeyword ',' TraitsArguments ')')?
    private static boolean TraitsExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TraitsExpression_1")) return false;
        TraitsExpression_1_0(b, l + 1);
        return true;
    }

    // '(' TraitsKeyword ',' TraitsArguments ')'
    private static boolean TraitsExpression_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TraitsExpression_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && TraitsKeyword(b, l + 1);
        r = r && consumeToken(b, OP_COMMA);
        r = r && DLanguageParser.TraitsArguments(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'isAbstractClass'
    //     | 'isTemplate'
    //     | 'isArithmetic'
    //     | 'isAssociativeArray'
    //     | 'isFinalClass'
    //     | 'isPOD'
    //     | 'isNested'
    //     | 'isFloating'
    //     | 'isIntegral'
    //     | 'isScalar'
    //     | 'isStaticArray'
    //     | 'isUnsigned'
    //     | 'isVirtualFunction'
    //     | 'isVirtualMethod'
    //     | 'isAbstractFunction'
    //     | 'isFinalFunction'
    //     | 'isStaticFunction'
    //     | 'isOverrideFunction'
    //     | 'isRef'
    //     | 'isOut'
    //     | 'isLazy'
    //     | 'hasMember'
    //     | 'identifier'
    //     | 'getAliasThis'
    //     | 'getAttributes'
    //     | 'getFunctionAttributes'
    //     | 'getMember'
    //     | 'getOverloads'
    //     | 'getProtection'
    //     | 'getPointerBitmap'
    //     | 'isSomeVisibility'
    //     | 'getVirtualFunctions'
    //     | 'getVirtualMethods'
    //     | 'getUnitTests'
    //     | 'parent'
    //     | 'classInstanceSize'
    //     | 'getVirtualIndex'
    //     | 'allMembers'
    //     | 'derivedMembers'
    //     | 'isSame'
    //     | 'compiles'
    public static boolean TraitsKeyword(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TraitsKeyword")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TRAITS_KEYWORD, "<traits keyword>");
        r = consumeToken(b, "isAbstractClass");
        if (!r) r = consumeToken(b, "isTemplate");
        if (!r) r = consumeToken(b, "isArithmetic");
        if (!r) r = consumeToken(b, "isAssociativeArray");
        if (!r) r = consumeToken(b, "isFinalClass");
        if (!r) r = consumeToken(b, "isPOD");
        if (!r) r = consumeToken(b, "isNested");
        if (!r) r = consumeToken(b, "isFloating");
        if (!r) r = consumeToken(b, "isIntegral");
        if (!r) r = consumeToken(b, "isScalar");
        if (!r) r = consumeToken(b, "isStaticArray");
        if (!r) r = consumeToken(b, "isUnsigned");
        if (!r) r = consumeToken(b, "isVirtualFunction");
        if (!r) r = consumeToken(b, "isVirtualMethod");
        if (!r) r = consumeToken(b, "isAbstractFunction");
        if (!r) r = consumeToken(b, "isFinalFunction");
        if (!r) r = consumeToken(b, "isStaticFunction");
        if (!r) r = consumeToken(b, "isOverrideFunction");
        if (!r) r = consumeToken(b, "isRef");
        if (!r) r = consumeToken(b, "isOut");
        if (!r) r = consumeToken(b, "isLazy");
        if (!r) r = consumeToken(b, "hasMember");
        if (!r) r = consumeToken(b, "identifier");
        if (!r) r = consumeToken(b, "getAliasThis");
        if (!r) r = consumeToken(b, "getAttributes");
        if (!r) r = consumeToken(b, "getFunctionAttributes");
        if (!r) r = consumeToken(b, "getMember");
        if (!r) r = consumeToken(b, "getOverloads");
        if (!r) r = consumeToken(b, "getProtection");
        if (!r) r = consumeToken(b, "getPointerBitmap");
        if (!r) r = consumeToken(b, "isSomeVisibility");
        if (!r) r = consumeToken(b, "getVirtualFunctions");
        if (!r) r = consumeToken(b, "getVirtualMethods");
        if (!r) r = consumeToken(b, "getUnitTests");
        if (!r) r = consumeToken(b, "parent");
        if (!r) r = consumeToken(b, "classInstanceSize");
        if (!r) r = consumeToken(b, "getVirtualIndex");
        if (!r) r = consumeToken(b, "allMembers");
        if (!r) r = consumeToken(b, "derivedMembers");
        if (!r) r = consumeToken(b, "isSame");
        if (!r) r = consumeToken(b, "compiles");
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // ('&'
    //     | '++'
    //     | '|'
    //     | '^^'
    //     | '--'
    //     | '*'
    //     | '-'
    //     | '+'
    //     | '!'
    //     | '~') [UnaryExpression]
    //     | TypeCtor? '(' Type ')' '.' Identifier
    //     | TypeCtor? '(' Type ')' '.' TemplateInstance
    //     | DeleteExpression
    //     | CastExpression
    //     | PowExpression
    static boolean UnaryExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnaryExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = UnaryExpression_0(b, l + 1);
        if (!r) r = UnaryExpression_1(b, l + 1);
        if (!r) r = UnaryExpression_2(b, l + 1);
        if (!r) r = DLanguageParser.DeleteExpression(b, l + 1);
        if (!r) r = DLanguageParser.CastExpression(b, l + 1);
        if (!r) r = DLanguageParser.PowExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('&'
    //     | '++'
    //     | '|'
    //     | '^^'
    //     | '--'
    //     | '*'
    //     | '-'
    //     | '+'
    //     | '!'
    //     | '~') [UnaryExpression]
    private static boolean UnaryExpression_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnaryExpression_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = UnaryExpression_0_0(b, l + 1);
        r = r && UnaryExpression_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '&'
    //     | '++'
    //     | '|'
    //     | '^^'
    //     | '--'
    //     | '*'
    //     | '-'
    //     | '+'
    //     | '!'
    //     | '~'
    private static boolean UnaryExpression_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnaryExpression_0_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_AND);
        if (!r) r = consumeToken(b, OP_PLUS_PLUS);
        if (!r) r = consumeToken(b, OP_OR);
        if (!r) r = consumeToken(b, OP_POW);
        if (!r) r = consumeToken(b, OP_MINUS_MINUS);
        if (!r) r = consumeToken(b, OP_ASTERISK);
        if (!r) r = consumeToken(b, OP_MINUS);
        if (!r) r = consumeToken(b, OP_PLUS);
        if (!r) r = consumeToken(b, OP_NOT);
        if (!r) r = consumeToken(b, OP_TILDA);
        exit_section_(b, m, null, r);
        return r;
    }

    // [UnaryExpression]
    private static boolean UnaryExpression_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnaryExpression_0_1")) return false;
        DLanguageParser.UnaryExpression(b, l + 1);
        return true;
    }

    // TypeCtor? '(' Type ')' '.' Identifier
    private static boolean UnaryExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnaryExpression_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = UnaryExpression_1_0(b, l + 1);
        r = r && consumeToken(b, OP_PAR_LEFT);
        r = r && TypeParser.Type(b, l + 1);
        r = r && consumeTokens(b, 0, OP_PAR_RIGHT, OP_DOT);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // TypeCtor?
    private static boolean UnaryExpression_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnaryExpression_1_0")) return false;
        TypeParser.TypeCtor(b, l + 1);
        return true;
    }

    // TypeCtor? '(' Type ')' '.' TemplateInstance
    private static boolean UnaryExpression_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnaryExpression_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = UnaryExpression_2_0(b, l + 1);
        r = r && consumeToken(b, OP_PAR_LEFT);
        r = r && TypeParser.Type(b, l + 1);
        r = r && consumeTokens(b, 0, OP_PAR_RIGHT, OP_DOT);
        r = r && MiscParser.TemplateInstance(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // TypeCtor?
    private static boolean UnaryExpression_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnaryExpression_2_0")) return false;
        TypeParser.TypeCtor(b, l + 1);
        return true;
    }

    // [ XorExpression_ ]
    static boolean XorExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "XorExpression_1")) return false;
        XorExpression_(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // '^' XorExpression
    public static boolean XorExpression_(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "XorExpression_")) return false;
        if (!nextTokenIs(b, OP_XOR)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_XOR);
        r = r && XorExpression(b, l + 1);
        exit_section_(b, m, XOR_EXPRESSION_, r);
        return r;
    }

    /* ********************************************************** */
    // INTEGER_LITERAL
    //    |  Identifier
    public static boolean IntegerExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IntegerExpression")) return false;
        if (!nextTokenIs(b, "<integer expression>", ID, INTEGER_LITERAL)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, INTEGER_EXPRESSION, "<integer expression>");
        r = consumeToken(b, INTEGER_LITERAL);
        if (!r) r = BaseRulesParser.Identifier(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // Expression
    public static boolean LwrExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LwrExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, LWR_EXPRESSION, "<lwr expression>");
        r = Expression(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // ('<' | '<=' | '>' | '>=' | '!<>=' | '!<>' | '<>' | '<>=' | '!>' | '!>=' | '!<' | '!<=') ShiftExpression
    public static boolean RelExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RelExpression")) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, REL_EXPRESSION, "<rel expression>");
        r = RelExpression_0(b, l + 1);
        p = r; // pin = 1
        r = r && ShiftExpression(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // '<' | '<=' | '>' | '>=' | '!<>=' | '!<>' | '<>' | '<>=' | '!>' | '!>=' | '!<' | '!<='
    private static boolean RelExpression_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RelExpression_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_LESS);
        if (!r) r = consumeToken(b, OP_LESS_EQ);
        if (!r) r = consumeToken(b, OP_GT);
        if (!r) r = consumeToken(b, OP_GT_EQ);
        if (!r) r = consumeToken(b, OP_UNORD);
        if (!r) r = consumeToken(b, OP_UNORD_EQ);
        if (!r) r = consumeToken(b, OP_LESS_GR);
        if (!r) r = consumeToken(b, OP_LESS_GR_EQ);
        if (!r) r = consumeToken(b, OP_NOT_GR);
        if (!r) r = consumeToken(b, OP_NOT_GR_EQ);
        if (!r) r = consumeToken(b, OP_NOT_LESS);
        if (!r) r = consumeToken(b, OP_NOT_LESS_EQ);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // TypeidExpressionType
    //     | TypeidExpressionExpression
    public static boolean TypeidExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TypeidExpression")) return false;
        if (!nextTokenIs(b, KW_TYPEID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = TypeidExpressionType(b, l + 1);
        if (!r) r = TypeidExpressionExpression(b, l + 1);
        exit_section_(b, m, TYPEID_EXPRESSION, r);
        return r;
    }

    /* ********************************************************** */
    // 'typeid' '(' Expression ')'
    static boolean TypeidExpressionExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TypeidExpressionExpression")) return false;
        if (!nextTokenIs(b, KW_TYPEID)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_);
        r = consumeTokens(b, 0, KW_TYPEID, OP_PAR_LEFT);
        r = r && Expression(b, l + 1);
        p = r; // pin = 3
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'typeid' '(' Type ')'
    static boolean TypeidExpressionType(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TypeidExpressionType")) return false;
        if (!nextTokenIs(b, KW_TYPEID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_TYPEID, OP_PAR_LEFT);
        r = r && TypeParser.Type(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // Expression
    public static boolean UprExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UprExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, UPR_EXPRESSION, "<upr expression>");
        r = Expression(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // AndExpression [ XorExpression_ ]
    static boolean XorExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "XorExpression")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AndExpression(b, l + 1);
        r = r && XorExpression_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }
}
