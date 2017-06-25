package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.parser.DLanguageParser.statement_recover_parser_;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 6/25/2017.
 */
public class StatementParser {
    /* ********************************************************** */
    // 'asm' FunctionAttributes? '{' AsmInstructionList? '}'
    public static boolean AsmStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmStatement")) return false;
        if (!nextTokenIs(b, KW_ASM)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_STATEMENT, null);
        r = consumeToken(b, KW_ASM);
        p = r; // pin = 1
        r = r && report_error_(b, AsmStatement_1(b, l + 1));
        r = p && report_error_(b, consumeToken(b, OP_BRACES_LEFT)) && r;
        r = p && report_error_(b, AsmStatement_3(b, l + 1)) && r;
        r = p && consumeToken(b, OP_BRACES_RIGHT) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // FunctionAttributes?
    private static boolean AsmStatement_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmStatement_1")) return false;
        DeclarationParser.FunctionAttributes(b, l + 1);
        return true;
    }

    // AsmInstructionList?
    private static boolean AsmStatement_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmStatement_3")) return false;
        AsmParser.AsmInstructionList(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // '{' StatementList? '}'
    public static boolean BlockStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BlockStatement")) return false;
        if (!nextTokenIs(b, OP_BRACES_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BRACES_LEFT);
        r = r && BlockStatement_1(b, l + 1);
        r = r && consumeToken(b, OP_BRACES_RIGHT);
        exit_section_(b, m, BLOCK_STATEMENT, r);
        return r;
    }

    // StatementList?
    private static boolean BlockStatement_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BlockStatement_1")) return false;
        StatementList(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'body' BlockStatement
    public static boolean BodyStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BodyStatement")) return false;
        if (!nextTokenIs(b, KW_BODY)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, BODY_STATEMENT, null);
        r = consumeToken(b, KW_BODY);
        p = r; // pin = 1
        r = r && BlockStatement(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'break' Identifier? ';'
    public static boolean BreakStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BreakStatement")) return false;
        if (!nextTokenIs(b, KW_BREAK)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, BREAK_STATEMENT, null);
        r = consumeToken(b, KW_BREAK);
        p = r; // pin = 1
        r = r && report_error_(b, BreakStatement_1(b, l + 1));
        r = p && consumeToken(b, OP_SCOLON) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // Identifier?
    private static boolean BreakStatement_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BreakStatement_1")) return false;
        BaseRulesParser.Identifier(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'case' FirstExp ':' '..' 'case' LastExp ':' StatementList
    public static boolean CaseRangeStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "CaseRangeStatement")) return false;
        if (!nextTokenIs(b, KW_CASE)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, CASE_RANGE_STATEMENT, null);
        r = consumeToken(b, KW_CASE);
        r = r && MiscParser.FirstExp(b, l + 1);
        r = r && consumeTokens(b, 2, OP_COLON, OP_DDOT, KW_CASE);
        p = r; // pin = 4
        r = r && report_error_(b, LastExp(b, l + 1));
        r = p && report_error_(b, consumeToken(b, OP_COLON)) && r;
        r = p && StatementList(b, l + 1) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'case' ArgumentList ':' ScopeStatementList?
    public static boolean CaseStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "CaseStatement")) return false;
        if (!nextTokenIs(b, KW_CASE)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, CASE_STATEMENT, null);
        r = consumeToken(b, KW_CASE);
        r = r && ArgumentParser.ArgumentList(b, l + 1);
        r = r && consumeToken(b, OP_COLON);
        p = r; // pin = 3
        r = r && CaseStatement_3(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // ScopeStatementList?
    private static boolean CaseStatement_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "CaseStatement_3")) return false;
        ScopeStatementList(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'catch' '(' CatchParameter ')' Statement
    public static boolean Catch(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Catch")) return false;
        if (!nextTokenIs(b, KW_CATCH)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, CATCH, null);
        r = consumeTokens(b, 0, KW_CATCH, OP_PAR_LEFT);
        r = r && CatchParameter(b, l + 1);
        p = r; // pin = 3
        r = r && report_error_(b, consumeToken(b, OP_PAR_RIGHT));
        r = p && Statement(b, l + 1) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // BasicType Identifier?
    public static boolean CatchParameter(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "CatchParameter")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, CATCH_PARAMETER, "<catch parameter>");
        r = TypeParser.BasicType(b, l + 1);
        r = r && CatchParameter_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // Identifier?
    private static boolean CatchParameter_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "CatchParameter_1")) return false;
        BaseRulesParser.Identifier(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // Catch Catch*//todo simplify
    //      | LastCatch
    public static boolean Catches(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Catches")) return false;
        if (!nextTokenIs(b, KW_CATCH)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Catches_0(b, l + 1);
        if (!r) r = LastCatch(b, l + 1);
        exit_section_(b, m, CATCHES, r);
        return r;
    }

    // Catch Catch*
    private static boolean Catches_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Catches_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Catch(b, l + 1);
        r = r && Catches_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Catch*
    private static boolean Catches_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Catches_0_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!Catch(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "Catches_0_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    /* ********************************************************** */
    // Condition (Statement | BlockStatement | DeclarationBlock) ('else' (Statement | DeclarationBlock))?
    public static boolean ConditionalStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalStatement")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, CONDITIONAL_STATEMENT, "<conditional statement>");
        r = DeclDefParser.Condition(b, l + 1);
        r = r && ConditionalStatement_1(b, l + 1);
        r = r && ConditionalStatement_2(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // Statement | BlockStatement | DeclarationBlock
    private static boolean ConditionalStatement_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalStatement_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Statement(b, l + 1);
        if (!r) r = BlockStatement(b, l + 1);
        if (!r) r = DeclarationParser.DeclarationBlock(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('else' (Statement | DeclarationBlock))?
    private static boolean ConditionalStatement_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalStatement_2")) return false;
        ConditionalStatement_2_0(b, l + 1);
        return true;
    }

    // 'else' (Statement | DeclarationBlock)
    private static boolean ConditionalStatement_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalStatement_2_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_ELSE);
        r = r && ConditionalStatement_2_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Statement | DeclarationBlock
    private static boolean ConditionalStatement_2_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalStatement_2_0_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Statement(b, l + 1);
        if (!r) r = DeclarationParser.DeclarationBlock(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'continue' Identifier? ';'
    public static boolean ContinueStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ContinueStatement")) return false;
        if (!nextTokenIs(b, KW_CONTINUE)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, CONTINUE_STATEMENT, null);
        r = consumeToken(b, KW_CONTINUE);
        p = r; // pin = 1
        r = r && report_error_(b, ContinueStatement_1(b, l + 1));
        r = p && consumeToken(b, OP_SCOLON) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // Identifier?
    private static boolean ContinueStatement_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ContinueStatement_1")) return false;
        BaseRulesParser.Identifier(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // StorageClasses? Declaration
    public static boolean DeclarationStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclarationStatement")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, DECLARATION_STATEMENT, "<declaration statement>");
        r = DeclarationStatement_0(b, l + 1);
        r = r && DeclarationParser.Declaration(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // StorageClasses?
    private static boolean DeclarationStatement_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclarationStatement_0")) return false;
        VariableDeclarationParser.StorageClasses(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'default' ':' ScopeStatementList?
    public static boolean DefaultStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DefaultStatement")) return false;
        if (!nextTokenIs(b, KW_DEFAULT)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, DEFAULT_STATEMENT, null);
        r = consumeTokens(b, 1, KW_DEFAULT, OP_COLON);
        p = r; // pin = 1
        r = r && DefaultStatement_2(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // ScopeStatementList?
    private static boolean DefaultStatement_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DefaultStatement_2")) return false;
        ScopeStatementList(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'do' ScopeStatement 'while' '(' Expression ')' ';'
    public static boolean DoStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DoStatement")) return false;
        if (!nextTokenIs(b, KW_DO)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, DO_STATEMENT, null);
        r = consumeToken(b, KW_DO);
        p = r; // pin = 1
        r = r && report_error_(b, ScopeStatement(b, l + 1));
        r = p && report_error_(b, consumeTokens(b, -1, KW_WHILE, OP_PAR_LEFT)) && r;
        r = p && report_error_(b, ExpressionParser.Expression(b, l + 1)) && r;
        r = p && report_error_(b, consumeTokens(b, -1, OP_PAR_RIGHT, OP_SCOLON)) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // ScopeStatement
    public static boolean ElseStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ElseStatement")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ELSE_STATEMENT, "<else statement>");
        r = ScopeStatement(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // 'final' 'switch' '(' Expression ')' ScopeStatement
    public static boolean FinalSwitchStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FinalSwitchStatement")) return false;
        if (!nextTokenIs(b, KW_FINAL)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FINAL_SWITCH_STATEMENT, null);
        r = consumeTokens(b, 2, KW_FINAL, KW_SWITCH, OP_PAR_LEFT);
        p = r; // pin = 2
        r = r && report_error_(b, ExpressionParser.Expression(b, l + 1));
        r = p && report_error_(b, consumeToken(b, OP_PAR_RIGHT)) && r;
        r = p && ScopeStatement(b, l + 1) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'finally' Statement
    public static boolean FinallyStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FinallyStatement")) return false;
        if (!nextTokenIs(b, KW_FINALLY)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FINALLY_STATEMENT, null);
        r = consumeToken(b, KW_FINALLY);
        p = r; // pin = 1
        r = r && Statement(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'for' '(' Initialize Test? ';' Increment? ')' ScopeStatement
    public static boolean ForStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForStatement")) return false;
        if (!nextTokenIs(b, KW_FOR)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FOR_STATEMENT, null);
        r = consumeTokens(b, 1, KW_FOR, OP_PAR_LEFT);
        p = r; // pin = 1
        r = r && report_error_(b, MiscParser.Initialize(b, l + 1));
        r = p && report_error_(b, ForStatement_3(b, l + 1)) && r;
        r = p && report_error_(b, consumeToken(b, OP_SCOLON)) && r;
        r = p && report_error_(b, ForStatement_5(b, l + 1)) && r;
        r = p && report_error_(b, consumeToken(b, OP_PAR_RIGHT)) && r;
        r = p && ScopeStatement(b, l + 1) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // Test?
    private static boolean ForStatement_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForStatement_3")) return false;
        MiscParser.Test(b, l + 1);
        return true;
    }

    // Increment?
    private static boolean ForStatement_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForStatement_5")) return false;
        MiscParser.Increment(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'foreach'
    //     | 'foreach_reverse'
    public static boolean Foreach(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Foreach")) return false;
        if (!nextTokenIs(b, "<foreach>", KW_FOREACH, KW_FOREACH_REVERSE)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FOREACH, "<foreach>");
        r = consumeToken(b, KW_FOREACH);
        if (!r) r = consumeToken(b, KW_FOREACH_REVERSE);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // Expression
    public static boolean ForeachAggregate(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachAggregate")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FOREACH_AGGREGATE, "<foreach aggregate>");
        r = ExpressionParser.Expression(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // Foreach '(' ForeachType ';' LwrExpression '..' UprExpression ')' ScopeStatement
    public static boolean ForeachRangeStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachRangeStatement")) return false;
        if (!nextTokenIs(b, "<foreach range statement>", KW_FOREACH, KW_FOREACH_REVERSE)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FOREACH_RANGE_STATEMENT, "<foreach range statement>");
        r = Foreach(b, l + 1);
        r = r && consumeToken(b, OP_PAR_LEFT);
        r = r && ForeachType(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        r = r && ExpressionParser.LwrExpression(b, l + 1);
        r = r && consumeToken(b, OP_DDOT);
        r = r && ExpressionParser.UprExpression(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        r = r && ScopeStatement(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // Foreach '(' ForeachTypeList ';' ForeachAggregate ')' Statement
    public static boolean ForeachStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachStatement")) return false;
        if (!nextTokenIs(b, "<foreach statement>", KW_FOREACH, KW_FOREACH_REVERSE)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FOREACH_STATEMENT, "<foreach statement>");
        r = Foreach(b, l + 1);
        r = r && consumeToken(b, OP_PAR_LEFT);
        r = r && ForeachTypeList(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        r = r && ForeachAggregate(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        r = r && Statement(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // ForeachTypeAttributes? Type? Identifier // this needs to be above for ForeachTypeAttributes? Type? Identifier
    //    | ForeachTypeAttribute Identifier
    //    | Type? ForeachTypeAttributes? Identifier
    //    | Identifier
    public static boolean ForeachType(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachType")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FOREACH_TYPE, "<foreach type>");
        r = ForeachType_0(b, l + 1);
        if (!r) r = ForeachType_1(b, l + 1);
        if (!r) r = ForeachType_2(b, l + 1);
        if (!r) r = BaseRulesParser.Identifier(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ForeachTypeAttributes? Type? Identifier
    private static boolean ForeachType_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachType_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ForeachType_0_0(b, l + 1);
        r = r && ForeachType_0_1(b, l + 1);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ForeachTypeAttributes?
    private static boolean ForeachType_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachType_0_0")) return false;
        ForeachTypeAttributes(b, l + 1);
        return true;
    }

    // Type?
    private static boolean ForeachType_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachType_0_1")) return false;
        TypeParser.Type(b, l + 1);
        return true;
    }

    // ForeachTypeAttribute Identifier
    private static boolean ForeachType_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachType_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ForeachTypeAttribute(b, l + 1);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Type? ForeachTypeAttributes? Identifier
    private static boolean ForeachType_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachType_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ForeachType_2_0(b, l + 1);
        r = r && ForeachType_2_1(b, l + 1);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Type?
    private static boolean ForeachType_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachType_2_0")) return false;
        TypeParser.Type(b, l + 1);
        return true;
    }

    // ForeachTypeAttributes?
    private static boolean ForeachType_2_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachType_2_1")) return false;
        ForeachTypeAttributes(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'ref'
    //     | TypeCtor
    public static boolean ForeachTypeAttribute(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachTypeAttribute")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FOREACH_TYPE_ATTRIBUTE, "<foreach type attribute>");
        r = consumeToken(b, KW_REF);
        if (!r) r = TypeParser.TypeCtor(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // ForeachTypeAttribute [ForeachTypeAttributes]
    public static boolean ForeachTypeAttributes(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachTypeAttributes")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FOREACH_TYPE_ATTRIBUTES, "<foreach type attributes>");
        r = ForeachTypeAttribute(b, l + 1);
        r = r && ForeachTypeAttributes_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // [ForeachTypeAttributes]
    private static boolean ForeachTypeAttributes_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachTypeAttributes_1")) return false;
        ForeachTypeAttributes(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // ForeachType (',' ForeachType)*
    public static boolean ForeachTypeList(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachTypeList")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FOREACH_TYPE_LIST, "<foreach type list>");
        r = ForeachType(b, l + 1);
        r = r && ForeachTypeList_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (',' ForeachType)*
    private static boolean ForeachTypeList_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachTypeList_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!ForeachTypeList_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "ForeachTypeList_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    // ',' ForeachType
    private static boolean ForeachTypeList_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ForeachTypeList_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && ForeachType(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'goto' Identifier ';'
    //     | 'goto' 'default' ';'
    //     | 'goto' 'case' ';'
    //     | 'goto' 'case' Expression ';'
    public static boolean GotoStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "GotoStatement")) return false;
        if (!nextTokenIs(b, KW_GOTO)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = GotoStatement_0(b, l + 1);
        if (!r) r = parseTokens(b, 0, KW_GOTO, KW_DEFAULT, OP_SCOLON);
        if (!r) r = parseTokens(b, 0, KW_GOTO, KW_CASE, OP_SCOLON);
        if (!r) r = GotoStatement_3(b, l + 1);
        exit_section_(b, m, GOTO_STATEMENT, r);
        return r;
    }

    // 'goto' Identifier ';'
    private static boolean GotoStatement_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "GotoStatement_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_GOTO);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'goto' 'case' Expression ';'
    private static boolean GotoStatement_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "GotoStatement_3")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_GOTO, KW_CASE);
        r = r && ExpressionParser.Expression(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // Expression
    //     | ConditionVariableDeclaration
    public static boolean IfCondition(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IfCondition")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, IF_CONDITION, "<if condition>");
        r = ExpressionParser.Expression(b, l + 1);
        if (!r) r = DeclDefParser.ConditionVariableDeclaration(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // 'if' '(' IfCondition ')' ThenStatement ('else' ElseStatement)?
    public static boolean IfStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IfStatement")) return false;
        if (!nextTokenIs(b, KW_IF)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, IF_STATEMENT, null);
        r = consumeTokens(b, 1, KW_IF, OP_PAR_LEFT);
        p = r; // pin = 1
        r = r && report_error_(b, IfCondition(b, l + 1));
        r = p && report_error_(b, consumeToken(b, OP_PAR_RIGHT)) && r;
        r = p && report_error_(b, ThenStatement(b, l + 1)) && r;
        r = p && IfStatement_5(b, l + 1) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // ('else' ElseStatement)?
    private static boolean IfStatement_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IfStatement_5")) return false;
        IfStatement_5_0(b, l + 1);
        return true;
    }

    // 'else' ElseStatement
    private static boolean IfStatement_5_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IfStatement_5_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_ELSE);
        r = r && ElseStatement(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'in' BlockStatement
    public static boolean InStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InStatement")) return false;
        if (!nextTokenIs(b, KW_IN)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_IN);
        r = r && BlockStatement(b, l + 1);
        exit_section_(b, m, IN_STATEMENT, r);
        return r;
    }

    /* ********************************************************** */
    // Identifier ':' Statement?
    public static boolean LabeledStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LabeledStatement")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, LABELED_STATEMENT, null);
        r = BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_COLON);
        p = r; // pin = 2
        r = r && LabeledStatement_2(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // Statement?
    private static boolean LabeledStatement_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LabeledStatement_2")) return false;
        Statement(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'mixin' '(' ( AssignExpression) ')' ';'
    public static boolean MixinStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MixinStatement")) return false;
        if (!nextTokenIs(b, KW_MIXIN)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_MIXIN, OP_PAR_LEFT);
        r = r && MixinStatement_2(b, l + 1);
        r = r && consumeTokens(b, 0, OP_PAR_RIGHT, OP_SCOLON);
        exit_section_(b, m, MIXIN_STATEMENT, r);
        return r;
    }

    // ( AssignExpression)
    private static boolean MixinStatement_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MixinStatement_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ExpressionParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // NonEmptyStatementNoCaseNoDefault
    //     | CaseRangeStatement//must be above case statement because pin on case statement will detect a case statement, where there is a case range statement
    //     | CaseStatement
    //     | DefaultStatement
    public static boolean NonEmptyStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NonEmptyStatement")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, NON_EMPTY_STATEMENT, "<non empty statement>");
        r = NonEmptyStatementNoCaseNoDefault(b, l + 1);
        if (!r) r = CaseRangeStatement(b, l + 1);
        if (!r) r = CaseStatement(b, l + 1);
        if (!r) r = DefaultStatement(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // LabeledStatement
    //     | DeclarationStatement
    //     | Declaration
    //     | BlockStatement // must be above expression statement
    //     | IfStatement
    //     | WhileStatement
    //     | DoStatement
    //     | ForStatement
    //     | ForeachStatement
    //     | SwitchStatement
    //     | FinalSwitchStatement
    //     | ContinueStatement
    //     | BreakStatement
    //     | ReturnStatement
    //     | GotoStatement
    //     | WithStatement
    //     | SynchronizedStatement
    //     | TryStatement
    //     | ScopeGuardStatement
    //     | ThrowStatement
    //     | AsmStatement
    //     | PragmaStatement
    //     | MixinStatement
    //     | ForeachRangeStatement
    //     | ConditionalStatement
    //     | StaticAssert
    //     | TemplateMixin
    //     | ImportDeclaration
    //     | ExpressionStatement
    static boolean NonEmptyStatementNoCaseNoDefault(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NonEmptyStatementNoCaseNoDefault")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = LabeledStatement(b, l + 1);
        if (!r) r = DeclarationStatement(b, l + 1);
        if (!r) r = DeclarationParser.Declaration(b, l + 1);
        if (!r) r = BlockStatement(b, l + 1);
        if (!r) r = IfStatement(b, l + 1);
        if (!r) r = WhileStatement(b, l + 1);
        if (!r) r = DoStatement(b, l + 1);
        if (!r) r = ForStatement(b, l + 1);
        if (!r) r = ForeachStatement(b, l + 1);
        if (!r) r = SwitchStatement(b, l + 1);
        if (!r) r = FinalSwitchStatement(b, l + 1);
        if (!r) r = ContinueStatement(b, l + 1);
        if (!r) r = BreakStatement(b, l + 1);
        if (!r) r = ReturnStatement(b, l + 1);
        if (!r) r = GotoStatement(b, l + 1);
        if (!r) r = WithStatement(b, l + 1);
        if (!r) r = SynchronizedStatement(b, l + 1);
        if (!r) r = TryStatement(b, l + 1);
        if (!r) r = ScopeGuardStatement(b, l + 1);
        if (!r) r = ThrowStatement(b, l + 1);
        if (!r) r = AsmStatement(b, l + 1);
        if (!r) r = PragmaStatement(b, l + 1);
        if (!r) r = MixinStatement(b, l + 1);
        if (!r) r = ForeachRangeStatement(b, l + 1);
        if (!r) r = ConditionalStatement(b, l + 1);
        if (!r) r = DeclDefParser.StaticAssert(b, l + 1);
        if (!r) r = DeclDefParser.TemplateMixin(b, l + 1);
        if (!r) r = DeclDefParser.ImportDeclaration(b, l + 1);
        if (!r) r = ExpressionParser.ExpressionStatement(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'out' BlockStatement
    //     | 'out' '(' Identifier ')' BlockStatement
    //     {
    //     }
    public static boolean OutStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "OutStatement")) return false;
        if (!nextTokenIs(b, KW_OUT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = OutStatement_0(b, l + 1);
        if (!r) r = OutStatement_1(b, l + 1);
        exit_section_(b, m, OUT_STATEMENT, r);
        return r;
    }

    // 'out' BlockStatement
    private static boolean OutStatement_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "OutStatement_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_OUT);
        r = r && BlockStatement(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'out' '(' Identifier ')' BlockStatement
    //     {
    //     }
    private static boolean OutStatement_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "OutStatement_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_OUT, OP_PAR_LEFT);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        r = r && BlockStatement(b, l + 1);
        r = r && OutStatement_1_5(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // {
    //     }
    private static boolean OutStatement_1_5(PsiBuilder b, int l) {
        return true;
    }

    /* ********************************************************** */
    // 'pragma' '(' Identifier (',' ArgumentList)? ')'
    public static boolean Pragma(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Pragma")) return false;
        if (!nextTokenIs(b, KW_PRAGMA)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, PRAGMA, null);
        r = consumeTokens(b, 1, KW_PRAGMA, OP_PAR_LEFT);
        p = r; // pin = 1
        r = r && report_error_(b, BaseRulesParser.Identifier(b, l + 1));
        r = p && report_error_(b, Pragma_3(b, l + 1)) && r;
        r = p && consumeToken(b, OP_PAR_RIGHT) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // (',' ArgumentList)?
    private static boolean Pragma_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Pragma_3")) return false;
        Pragma_3_0(b, l + 1);
        return true;
    }

    // ',' ArgumentList
    private static boolean Pragma_3_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Pragma_3_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && ArgumentParser.ArgumentList(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // Pragma Statement
    public static boolean PragmaStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PragmaStatement")) return false;
        if (!nextTokenIs(b, KW_PRAGMA)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, PRAGMA_STATEMENT, null);
        r = Pragma(b, l + 1);
        p = r; // pin = 1
        r = r && Statement(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'return' Expression? ';'
    public static boolean ReturnStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ReturnStatement")) return false;
        if (!nextTokenIs(b, KW_RETURN)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, RETURN_STATEMENT, null);
        r = consumeToken(b, KW_RETURN);
        p = r; // pin = 1
        r = r && report_error_(b, ReturnStatement_1(b, l + 1));
        r = p && consumeToken(b, OP_SCOLON) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // Expression?
    private static boolean ReturnStatement_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ReturnStatement_1")) return false;
        ExpressionParser.Expression(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // '(' 'exit' ')' Statement
    static boolean ScopeExit(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ScopeExit")) return false;
        if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && consumeToken(b, "exit");
        p = r; // pin = 2
        r = r && report_error_(b, consumeToken(b, OP_PAR_RIGHT));
        r = p && Statement(b, l + 1) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // '(' 'success' ')' Statement
    static boolean ScopeFailure(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ScopeFailure")) return false;
        if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && consumeToken(b, "success");
        p = r; // pin = 2
        r = r && report_error_(b, consumeToken(b, OP_PAR_RIGHT));
        r = p && Statement(b, l + 1) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'scope' (ScopeExit|ScopeFailure|ScopeSuccess)
    public static boolean ScopeGuardStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ScopeGuardStatement")) return false;
        if (!nextTokenIs(b, KW_SCOPE)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, SCOPE_GUARD_STATEMENT, null);
        r = consumeToken(b, KW_SCOPE);
        p = r; // pin = 1
        r = r && ScopeGuardStatement_1(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // ScopeExit|ScopeFailure|ScopeSuccess
    private static boolean ScopeGuardStatement_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ScopeGuardStatement_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ScopeExit(b, l + 1);
        if (!r) r = ScopeFailure(b, l + 1);
        if (!r) r = ScopeSuccess(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // NonEmptyStatement
    //     | BlockStatement
    public static boolean ScopeStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ScopeStatement")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, SCOPE_STATEMENT, "<scope statement>");
        r = NonEmptyStatement(b, l + 1);
        if (!r) r = BlockStatement(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // StatementListNoCaseNoDefault
    public static boolean ScopeStatementList(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ScopeStatementList")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, SCOPE_STATEMENT_LIST, "<scope statement list>");
        r = StatementListNoCaseNoDefault(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // '(' 'failure' ')' Statement
    static boolean ScopeSuccess(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ScopeSuccess")) return false;
        if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && consumeToken(b, "failure");
        p = r; // pin = 2
        r = r && report_error_(b, consumeToken(b, OP_PAR_RIGHT));
        r = p && Statement(b, l + 1) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // ';'
    //     | NonEmptyStatement
    //     | BlockStatement
    public static boolean Statement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Statement")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, STATEMENT, "<statement>");
        r = consumeToken(b, OP_SCOLON);
        if (!r) r = NonEmptyStatement(b, l + 1);
        if (!r) r = BlockStatement(b, l + 1);
        exit_section_(b, l, m, r, false, statement_recover_parser_);
        return r;
    }

    /* ********************************************************** */
    // Statement Statement*
    public static boolean StatementList(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StatementList")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, STATEMENT_LIST, "<statement list>");
        r = Statement(b, l + 1);
        r = r && StatementList_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // Statement*
    private static boolean StatementList_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StatementList_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!Statement(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "StatementList_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    /* ********************************************************** */
    // StatementNoCaseNoDefault StatementNoCaseNoDefault*
    public static boolean StatementListNoCaseNoDefault(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StatementListNoCaseNoDefault")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, STATEMENT_LIST_NO_CASE_NO_DEFAULT, "<statement list no case no default>");
        r = StatementNoCaseNoDefault(b, l + 1);
        r = r && StatementListNoCaseNoDefault_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // StatementNoCaseNoDefault*
    private static boolean StatementListNoCaseNoDefault_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StatementListNoCaseNoDefault_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!StatementNoCaseNoDefault(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "StatementListNoCaseNoDefault_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    /* ********************************************************** */
    // NonEmptyStatementNoCaseNoDefault
    //     | BlockStatement
    public static boolean StatementNoCaseNoDefault(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StatementNoCaseNoDefault")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, STATEMENT_NO_CASE_NO_DEFAULT, "<statement no case no default>");
        r = NonEmptyStatementNoCaseNoDefault(b, l + 1);
        if (!r) r = BlockStatement(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // 'switch' '(' Expression ')' ScopeStatement
    public static boolean SwitchStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SwitchStatement")) return false;
        if (!nextTokenIs(b, KW_SWITCH)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, SWITCH_STATEMENT, null);
        r = consumeTokens(b, 1, KW_SWITCH, OP_PAR_LEFT);
        p = r; // pin = 1
        r = r && report_error_(b, ExpressionParser.Expression(b, l + 1));
        r = p && report_error_(b, consumeToken(b, OP_PAR_RIGHT)) && r;
        r = p && ScopeStatement(b, l + 1) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // SynchronizedStatementWithExpression
    //     | SynchronizedStatementWithoutExpression
    public static boolean SynchronizedStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SynchronizedStatement")) return false;
        if (!nextTokenIs(b, KW_SYNCHRONIZED)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = SynchronizedStatementWithExpression(b, l + 1);
        if (!r) r = SynchronizedStatementWithoutExpression(b, l + 1);
        exit_section_(b, m, SYNCHRONIZED_STATEMENT, r);
        return r;
    }

    /* ********************************************************** */
    // 'synchronized' '(' Expression ')' ScopeStatement
    static boolean SynchronizedStatementWithExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SynchronizedStatementWithExpression")) return false;
        if (!nextTokenIs(b, KW_SYNCHRONIZED)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_);
        r = consumeTokens(b, 2, KW_SYNCHRONIZED, OP_PAR_LEFT);
        p = r; // pin = 2
        r = r && report_error_(b, ExpressionParser.Expression(b, l + 1));
        r = p && report_error_(b, consumeToken(b, OP_PAR_RIGHT)) && r;
        r = p && ScopeStatement(b, l + 1) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'synchronized' ScopeStatement
    static boolean SynchronizedStatementWithoutExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SynchronizedStatementWithoutExpression")) return false;
        if (!nextTokenIs(b, KW_SYNCHRONIZED)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_SYNCHRONIZED);
        r = r && ScopeStatement(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // ScopeStatement
    public static boolean ThenStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ThenStatement")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, THEN_STATEMENT, "<then statement>");
        r = ScopeStatement(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // 'throw' Expression ';'
    public static boolean ThrowStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ThrowStatement")) return false;
        if (!nextTokenIs(b, KW_THROW)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, THROW_STATEMENT, null);
        r = consumeToken(b, KW_THROW);
        p = r; // pin = 1
        r = r && report_error_(b, ExpressionParser.Expression(b, l + 1));
        r = p && consumeToken(b, OP_SCOLON) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'try' ScopeStatement Catches
    //     | 'try' ScopeStatement Catches FinallyStatement
    //     | 'try' ScopeStatement FinallyStatement
    public static boolean TryStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TryStatement")) return false;
        if (!nextTokenIs(b, KW_TRY)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = TryStatement_0(b, l + 1);
        if (!r) r = TryStatement_1(b, l + 1);
        if (!r) r = TryStatement_2(b, l + 1);
        exit_section_(b, m, TRY_STATEMENT, r);
        return r;
    }

    // 'try' ScopeStatement Catches
    private static boolean TryStatement_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TryStatement_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_TRY);
        r = r && ScopeStatement(b, l + 1);
        r = r && Catches(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'try' ScopeStatement Catches FinallyStatement
    private static boolean TryStatement_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TryStatement_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_TRY);
        r = r && ScopeStatement(b, l + 1);
        r = r && Catches(b, l + 1);
        r = r && FinallyStatement(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'try' ScopeStatement FinallyStatement
    private static boolean TryStatement_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TryStatement_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_TRY);
        r = r && ScopeStatement(b, l + 1);
        r = r && FinallyStatement(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'while' '(' Expression ')' ScopeStatement
    public static boolean WhileStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "WhileStatement")) return false;
        if (!nextTokenIs(b, KW_WHILE)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, WHILE_STATEMENT, null);
        r = consumeTokens(b, 1, KW_WHILE, OP_PAR_LEFT);
        p = r; // pin = 1
        r = r && report_error_(b, ExpressionParser.Expression(b, l + 1));
        r = p && report_error_(b, consumeToken(b, OP_PAR_RIGHT)) && r;
        r = p && ScopeStatement(b, l + 1) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // WithStatementExpression
    //     | WithStatementSymbol
    //     | WithStatementTemplateInstance
    public static boolean WithStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "WithStatement")) return false;
        if (!nextTokenIs(b, KW_WITH)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = WithStatementExpression(b, l + 1);
        if (!r) r = WithStatementSymbol(b, l + 1);
        if (!r) r = WithStatementTemplateInstance(b, l + 1);
        exit_section_(b, m, WITH_STATEMENT, r);
        return r;
    }

    /* ********************************************************** */
    // 'with' '(' Expression ')' ScopeStatement
    static boolean WithStatementExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "WithStatementExpression")) return false;
        if (!nextTokenIs(b, KW_WITH)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_);
        r = consumeTokens(b, 0, KW_WITH, OP_PAR_LEFT);
        r = r && ExpressionParser.Expression(b, l + 1);
        p = r; // pin = 3
        r = r && report_error_(b, consumeToken(b, OP_PAR_RIGHT));
        r = p && ScopeStatement(b, l + 1) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'with' '(' Symbol ')' ScopeStatement
    static boolean WithStatementSymbol(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "WithStatementSymbol")) return false;
        if (!nextTokenIs(b, KW_WITH)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_);
        r = consumeTokens(b, 0, KW_WITH, OP_PAR_LEFT);
        r = r && MiscParser.Symbol(b, l + 1);
        p = r; // pin = 3
        r = r && report_error_(b, consumeToken(b, OP_PAR_RIGHT));
        r = p && ScopeStatement(b, l + 1) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'with' '(' TemplateInstance ')' ScopeStatement
    static boolean WithStatementTemplateInstance(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "WithStatementTemplateInstance")) return false;
        if (!nextTokenIs(b, KW_WITH)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_);
        r = consumeTokens(b, 0, KW_WITH, OP_PAR_LEFT);
        r = r && MiscParser.TemplateInstance(b, l + 1);
        p = r; // pin = 3
        r = r && report_error_(b, consumeToken(b, OP_PAR_RIGHT));
        r = p && ScopeStatement(b, l + 1) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'catch' Statement
    public static boolean LastCatch(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LastCatch")) return false;
        if (!nextTokenIs(b, KW_CATCH)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_CATCH);
        r = r && Statement(b, l + 1);
        exit_section_(b, m, LAST_CATCH, r);
        return r;
    }

    /* ********************************************************** */
    // AssignExpression
    public static boolean LastExp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LastExp")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, LAST_EXP, "<last exp>");
        r = ExpressionParser.AssignExpression(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }
}
