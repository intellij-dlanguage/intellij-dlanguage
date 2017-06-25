package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 6/25/2017.
 */
public class AsmParser {
    /* ********************************************************** */
    // AsmMulExp [ ('+' | '-') AsmAddExp ]
    public static boolean AsmAddExp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmAddExp")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_ADD_EXP, "<asm add exp>");
        r = AsmMulExp(b, l + 1);
        r = r && AsmAddExp_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // [ ('+' | '-') AsmAddExp ]
    private static boolean AsmAddExp_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmAddExp_1")) return false;
        AsmAddExp_1_0(b, l + 1);
        return true;
    }

    // ('+' | '-') AsmAddExp
    private static boolean AsmAddExp_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmAddExp_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AsmAddExp_1_0_0(b, l + 1);
        r = r && AsmAddExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '+' | '-'
    private static boolean AsmAddExp_1_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmAddExp_1_0_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PLUS);
        if (!r) r = consumeToken(b, OP_MINUS);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AsmEqualExp ['&' AsmAndExp]
    public static boolean AsmAndExp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmAndExp")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_AND_EXP, "<asm and exp>");
        r = AsmEqualExp(b, l + 1);
        r = r && AsmAndExp_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ['&' AsmAndExp]
    private static boolean AsmAndExp_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmAndExp_1")) return false;
        AsmAndExp_1_0(b, l + 1);
        return true;
    }

    // '&' AsmAndExp
    private static boolean AsmAndExp_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmAndExp_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_AND);
        r = r && AsmAndExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AsmUnaExp? ['[' AsmExp ']']
    public static boolean AsmBrExp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmBrExp")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_BR_EXP, "<asm br exp>");
        r = AsmBrExp_0(b, l + 1);
        r = r && AsmBrExp_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // AsmUnaExp?
    private static boolean AsmBrExp_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmBrExp_0")) return false;
        AsmUnaExp(b, l + 1);
        return true;
    }

    // ['[' AsmExp ']']
    private static boolean AsmBrExp_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmBrExp_1")) return false;
        AsmBrExp_1_0(b, l + 1);
        return true;
    }

    // '[' AsmExp ']'
    private static boolean AsmBrExp_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmBrExp_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BRACKET_LEFT);
        r = r && AsmExp(b, l + 1);
        r = r && consumeToken(b, OP_BRACKET_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AsmRelExp [ ('==' | '!=' ) AsmEqualExp]
    public static boolean AsmEqualExp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmEqualExp")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_EQUAL_EXP, "<asm equal exp>");
        r = AsmRelExp(b, l + 1);
        r = r && AsmEqualExp_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // [ ('==' | '!=' ) AsmEqualExp]
    private static boolean AsmEqualExp_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmEqualExp_1")) return false;
        AsmEqualExp_1_0(b, l + 1);
        return true;
    }

    // ('==' | '!=' ) AsmEqualExp
    private static boolean AsmEqualExp_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmEqualExp_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AsmEqualExp_1_0_0(b, l + 1);
        r = r && AsmEqualExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '==' | '!='
    private static boolean AsmEqualExp_1_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmEqualExp_1_0_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_EQ_EQ);
        if (!r) r = consumeToken(b, OP_NOT_EQ);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AsmLogOrExp ('?' AsmExp ':' AsmExp)?
    public static boolean AsmExp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmExp")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_EXP, "<asm exp>");
        r = AsmLogOrExp(b, l + 1);
        r = r && AsmExp_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ('?' AsmExp ':' AsmExp)?
    private static boolean AsmExp_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmExp_1")) return false;
        AsmExp_1_0(b, l + 1);
        return true;
    }

    // '?' AsmExp ':' AsmExp
    private static boolean AsmExp_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmExp_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_QUEST);
        r = r && AsmExp(b, l + 1);
        r = r && consumeToken(b, OP_COLON);
        r = r && AsmExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // Identifier ':' AsmInstruction?
    //     | 'align' IntegerExpression
    //     | 'even'
    //     | 'naked'
    //     | 'db' Operands
    //     | 'ds' Operands
    //     | 'di' Operands
    //     | 'dl' Operands
    //     | 'df' Operands
    //     | 'dd' Operands
    //     | 'de' Operands
    //     | Opcode Operands?
    //     | Opcode Operands? AsmBrExp
    public static boolean AsmInstruction(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_INSTRUCTION, "<asm instruction>");
        r = AsmInstruction_0(b, l + 1);
        if (!r) r = AsmInstruction_1(b, l + 1);
        if (!r) r = consumeToken(b, "even");
        if (!r) r = consumeToken(b, "naked");
        if (!r) r = AsmInstruction_4(b, l + 1);
        if (!r) r = AsmInstruction_5(b, l + 1);
        if (!r) r = AsmInstruction_6(b, l + 1);
        if (!r) r = AsmInstruction_7(b, l + 1);
        if (!r) r = AsmInstruction_8(b, l + 1);
        if (!r) r = AsmInstruction_9(b, l + 1);
        if (!r) r = AsmInstruction_10(b, l + 1);
        if (!r) r = AsmInstruction_11(b, l + 1);
        if (!r) r = AsmInstruction_12(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // Identifier ':' AsmInstruction?
    private static boolean AsmInstruction_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_COLON);
        r = r && AsmInstruction_0_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // AsmInstruction?
    private static boolean AsmInstruction_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction_0_2")) return false;
        AsmInstruction(b, l + 1);
        return true;
    }

    // 'align' IntegerExpression
    private static boolean AsmInstruction_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_ALIGN);
        r = r && ExpressionParser.IntegerExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'db' Operands
    private static boolean AsmInstruction_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction_4")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "db");
        r = r && Operands(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'ds' Operands
    private static boolean AsmInstruction_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction_5")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "ds");
        r = r && Operands(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'di' Operands
    private static boolean AsmInstruction_6(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction_6")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "di");
        r = r && Operands(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'dl' Operands
    private static boolean AsmInstruction_7(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction_7")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "dl");
        r = r && Operands(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'df' Operands
    private static boolean AsmInstruction_8(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction_8")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "df");
        r = r && Operands(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'dd' Operands
    private static boolean AsmInstruction_9(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction_9")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "dd");
        r = r && Operands(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'de' Operands
    private static boolean AsmInstruction_10(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction_10")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "de");
        r = r && Operands(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Opcode Operands?
    private static boolean AsmInstruction_11(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction_11")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Opcode(b, l + 1);
        r = r && AsmInstruction_11_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Operands?
    private static boolean AsmInstruction_11_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction_11_1")) return false;
        Operands(b, l + 1);
        return true;
    }

    // Opcode Operands? AsmBrExp
    private static boolean AsmInstruction_12(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction_12")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Opcode(b, l + 1);
        r = r && AsmInstruction_12_1(b, l + 1);
        r = r && AsmBrExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Operands?
    private static boolean AsmInstruction_12_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction_12_1")) return false;
        Operands(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // AsmInstruction ';' AsmInstructionList?
    public static boolean AsmInstructionList(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstructionList")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_INSTRUCTION_LIST, "<asm instruction list>");
        r = AsmInstruction(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        r = r && AsmInstructionList_2(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // AsmInstructionList?
    private static boolean AsmInstructionList_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstructionList_2")) return false;
        AsmInstructionList(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // AsmOrExp ['&&' AsmLogAndExp]
    public static boolean AsmLogAndExp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmLogAndExp")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_LOG_AND_EXP, "<asm log and exp>");
        r = AsmOrExp(b, l + 1);
        r = r && AsmLogAndExp_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ['&&' AsmLogAndExp]
    private static boolean AsmLogAndExp_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmLogAndExp_1")) return false;
        AsmLogAndExp_1_0(b, l + 1);
        return true;
    }

    // '&&' AsmLogAndExp
    private static boolean AsmLogAndExp_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmLogAndExp_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BOOL_AND);
        r = r && AsmLogAndExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AsmLogAndExp ['||' AsmLogOrExp]
    public static boolean AsmLogOrExp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmLogOrExp")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_LOG_OR_EXP, "<asm log or exp>");
        r = AsmLogAndExp(b, l + 1);
        r = r && AsmLogOrExp_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ['||' AsmLogOrExp]
    private static boolean AsmLogOrExp_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmLogOrExp_1")) return false;
        AsmLogOrExp_1_0(b, l + 1);
        return true;
    }

    // '||' AsmLogOrExp
    private static boolean AsmLogOrExp_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmLogOrExp_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BOOL_OR);
        r = r && AsmLogOrExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AsmBrExp [ ('*' | '/' | '%') AsmMulExp]
    public static boolean AsmMulExp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmMulExp")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_MUL_EXP, "<asm mul exp>");
        r = AsmBrExp(b, l + 1);
        r = r && AsmMulExp_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // [ ('*' | '/' | '%') AsmMulExp]
    private static boolean AsmMulExp_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmMulExp_1")) return false;
        AsmMulExp_1_0(b, l + 1);
        return true;
    }

    // ('*' | '/' | '%') AsmMulExp
    private static boolean AsmMulExp_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmMulExp_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AsmMulExp_1_0_0(b, l + 1);
        r = r && AsmMulExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '*' | '/' | '%'
    private static boolean AsmMulExp_1_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmMulExp_1_0_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_ASTERISK);
        if (!r) r = consumeToken(b, OP_DIV);
        if (!r) r = consumeToken(b, OP_MOD);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AsmXorExp ['|' AsmOrExp]
    public static boolean AsmOrExp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmOrExp")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_OR_EXP, "<asm or exp>");
        r = AsmXorExp(b, l + 1);
        r = r && AsmOrExp_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ['|' AsmOrExp]
    private static boolean AsmOrExp_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmOrExp_1")) return false;
        AsmOrExp_1_0(b, l + 1);
        return true;
    }

    // '|' AsmOrExp
    private static boolean AsmOrExp_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmOrExp_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_OR);
        r = r && AsmOrExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // INTEGER_LITERAL
    //     | StringLiteral
    //     | FLOAT_LITERAL
    //     | '__LOCAL_SIZE'
    //     | '$'
    //     | Register (':' AsmExp)?
    //     | Register64 (':' AsmExp)?
    //     | DotIdentifier
    //     | 'this'
    public static boolean AsmPrimaryExp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmPrimaryExp")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_PRIMARY_EXP, "<asm primary exp>");
        r = consumeToken(b, INTEGER_LITERAL);
        if (!r) r = BaseRulesParser.StringLiteral(b, l + 1);
        if (!r) r = consumeToken(b, FLOAT_LITERAL);
        if (!r) r = consumeToken(b, "__LOCAL_SIZE");
        if (!r) r = consumeToken(b, OP_DOLLAR);
        if (!r) r = AsmPrimaryExp_5(b, l + 1);
        if (!r) r = AsmPrimaryExp_6(b, l + 1);
        if (!r) r = MiscParser.DotIdentifier(b, l + 1);
        if (!r) r = consumeToken(b, KW_THIS);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // Register (':' AsmExp)?
    private static boolean AsmPrimaryExp_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmPrimaryExp_5")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = RegisterParser.Register(b, l + 1);
        r = r && AsmPrimaryExp_5_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // (':' AsmExp)?
    private static boolean AsmPrimaryExp_5_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmPrimaryExp_5_1")) return false;
        AsmPrimaryExp_5_1_0(b, l + 1);
        return true;
    }

    // ':' AsmExp
    private static boolean AsmPrimaryExp_5_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmPrimaryExp_5_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COLON);
        r = r && AsmExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Register64 (':' AsmExp)?
    private static boolean AsmPrimaryExp_6(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmPrimaryExp_6")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = RegisterParser.Register64(b, l + 1);
        r = r && AsmPrimaryExp_6_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // (':' AsmExp)?
    private static boolean AsmPrimaryExp_6_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmPrimaryExp_6_1")) return false;
        AsmPrimaryExp_6_1_0(b, l + 1);
        return true;
    }

    // ':' AsmExp
    private static boolean AsmPrimaryExp_6_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmPrimaryExp_6_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COLON);
        r = r && AsmExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AsmShiftExp [ ('<' | '<=' | '>' | '>=' ) AsmRelExp]
    public static boolean AsmRelExp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmRelExp")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_REL_EXP, "<asm rel exp>");
        r = AsmShiftExp(b, l + 1);
        r = r && AsmRelExp_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // [ ('<' | '<=' | '>' | '>=' ) AsmRelExp]
    private static boolean AsmRelExp_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmRelExp_1")) return false;
        AsmRelExp_1_0(b, l + 1);
        return true;
    }

    // ('<' | '<=' | '>' | '>=' ) AsmRelExp
    private static boolean AsmRelExp_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmRelExp_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AsmRelExp_1_0_0(b, l + 1);
        r = r && AsmRelExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '<' | '<=' | '>' | '>='
    private static boolean AsmRelExp_1_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmRelExp_1_0_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_LESS);
        if (!r) r = consumeToken(b, OP_LESS_EQ);
        if (!r) r = consumeToken(b, OP_GT);
        if (!r) r = consumeToken(b, OP_GT_EQ);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AsmAddExp [ ('<<' | '>>' | '>>>') AsmShiftExp ]
    public static boolean AsmShiftExp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmShiftExp")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_SHIFT_EXP, "<asm shift exp>");
        r = AsmAddExp(b, l + 1);
        r = r && AsmShiftExp_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // [ ('<<' | '>>' | '>>>') AsmShiftExp ]
    private static boolean AsmShiftExp_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmShiftExp_1")) return false;
        AsmShiftExp_1_0(b, l + 1);
        return true;
    }

    // ('<<' | '>>' | '>>>') AsmShiftExp
    private static boolean AsmShiftExp_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmShiftExp_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AsmShiftExp_1_0_0(b, l + 1);
        r = r && AsmShiftExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '<<' | '>>' | '>>>'
    private static boolean AsmShiftExp_1_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmShiftExp_1_0_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_SH_LEFT);
        if (!r) r = consumeToken(b, OP_SH_RIGHT);
        if (!r) r = consumeToken(b, OP_USH_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'near' 'ptr'
    //     | 'far' 'ptr'
    //     | 'byte' 'ptr'
    //     | 'short' 'ptr'
    //     | 'int' 'ptr'
    //     | 'word' 'ptr'
    //     | 'dword' 'ptr'
    //     | 'qword' 'ptr'
    //     | 'float' 'ptr'
    //     | 'double' 'ptr'
    //     | 'real' 'ptr'
    //     | 'real ptr'
    public static boolean AsmTypePrefix(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmTypePrefix")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_TYPE_PREFIX, "<asm type prefix>");
        r = AsmTypePrefix_0(b, l + 1);
        if (!r) r = AsmTypePrefix_1(b, l + 1);
        if (!r) r = AsmTypePrefix_2(b, l + 1);
        if (!r) r = AsmTypePrefix_3(b, l + 1);
        if (!r) r = AsmTypePrefix_4(b, l + 1);
        if (!r) r = AsmTypePrefix_5(b, l + 1);
        if (!r) r = AsmTypePrefix_6(b, l + 1);
        if (!r) r = AsmTypePrefix_7(b, l + 1);
        if (!r) r = AsmTypePrefix_8(b, l + 1);
        if (!r) r = AsmTypePrefix_9(b, l + 1);
        if (!r) r = AsmTypePrefix_10(b, l + 1);
        if (!r) r = consumeToken(b, "real ptr");
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // 'near' 'ptr'
    private static boolean AsmTypePrefix_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmTypePrefix_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "near");
        r = r && consumeToken(b, "ptr");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'far' 'ptr'
    private static boolean AsmTypePrefix_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmTypePrefix_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "far");
        r = r && consumeToken(b, "ptr");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'byte' 'ptr'
    private static boolean AsmTypePrefix_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmTypePrefix_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_BYTE);
        r = r && consumeToken(b, "ptr");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'short' 'ptr'
    private static boolean AsmTypePrefix_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmTypePrefix_3")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_SHORT);
        r = r && consumeToken(b, "ptr");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'int' 'ptr'
    private static boolean AsmTypePrefix_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmTypePrefix_4")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_INT);
        r = r && consumeToken(b, "ptr");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'word' 'ptr'
    private static boolean AsmTypePrefix_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmTypePrefix_5")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "word");
        r = r && consumeToken(b, "ptr");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'dword' 'ptr'
    private static boolean AsmTypePrefix_6(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmTypePrefix_6")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "dword");
        r = r && consumeToken(b, "ptr");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'qword' 'ptr'
    private static boolean AsmTypePrefix_7(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmTypePrefix_7")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "qword");
        r = r && consumeToken(b, "ptr");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'float' 'ptr'
    private static boolean AsmTypePrefix_8(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmTypePrefix_8")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_FLOAT);
        r = r && consumeToken(b, "ptr");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'double' 'ptr'
    private static boolean AsmTypePrefix_9(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmTypePrefix_9")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_DOUBLE);
        r = r && consumeToken(b, "ptr");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'real' 'ptr'
    private static boolean AsmTypePrefix_10(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmTypePrefix_10")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_REAL);
        r = r && consumeToken(b, "ptr");
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AsmTypePrefix AsmExp
    //     | 'offsetof' AsmExp
    //     | 'seg' AsmExp
    //     | '+' AsmUnaExp
    //     | '-' AsmUnaExp
    //     | '!' AsmUnaExp
    //     | '~' AsmUnaExp
    //     | AsmPrimaryExp
    public static boolean AsmUnaExp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmUnaExp")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_UNA_EXP, "<asm una exp>");
        r = AsmUnaExp_0(b, l + 1);
        if (!r) r = AsmUnaExp_1(b, l + 1);
        if (!r) r = AsmUnaExp_2(b, l + 1);
        if (!r) r = AsmUnaExp_3(b, l + 1);
        if (!r) r = AsmUnaExp_4(b, l + 1);
        if (!r) r = AsmUnaExp_5(b, l + 1);
        if (!r) r = AsmUnaExp_6(b, l + 1);
        if (!r) r = AsmPrimaryExp(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // AsmTypePrefix AsmExp
    private static boolean AsmUnaExp_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmUnaExp_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AsmTypePrefix(b, l + 1);
        r = r && AsmExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'offsetof' AsmExp
    private static boolean AsmUnaExp_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmUnaExp_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "offsetof");
        r = r && AsmExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'seg' AsmExp
    private static boolean AsmUnaExp_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmUnaExp_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "seg");
        r = r && AsmExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '+' AsmUnaExp
    private static boolean AsmUnaExp_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmUnaExp_3")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PLUS);
        r = r && AsmUnaExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '-' AsmUnaExp
    private static boolean AsmUnaExp_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmUnaExp_4")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_MINUS);
        r = r && AsmUnaExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '!' AsmUnaExp
    private static boolean AsmUnaExp_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmUnaExp_5")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_NOT);
        r = r && AsmUnaExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '~' AsmUnaExp
    private static boolean AsmUnaExp_6(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmUnaExp_6")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_TILDA);
        r = r && AsmUnaExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AsmAndExp [ '^' AsmXorExp ]
    public static boolean AsmXorExp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmXorExp")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ASM_XOR_EXP, "<asm xor exp>");
        r = AsmAndExp(b, l + 1);
        r = r && AsmXorExp_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // [ '^' AsmXorExp ]
    private static boolean AsmXorExp_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmXorExp_1")) return false;
        AsmXorExp_1_0(b, l + 1);
        return true;
    }

    // '^' AsmXorExp
    private static boolean AsmXorExp_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmXorExp_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_XOR);
        r = r && AsmXorExp(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'aaa'
    // | 'aad'
    // | 'aam'
    // | 'aas'
    // | 'adc'
    // | 'add'
    // | 'addpd'
    // | 'addps'
    // | 'addsd'
    // | 'addss'
    // | 'and'
    // | 'andnpd'
    // | 'andnps'
    // | 'andpd'
    // | 'andps'
    // | 'arpl'
    // | 'bound'
    // | 'bsf'
    // | 'bsr'
    // | 'bswap'
    // | 'bt'
    // | 'btc'
    // | 'btr'
    // | 'bts'
    // | 'call'
    // | 'cbw'
    // | 'cdq'
    // | 'clc'
    // | 'cld'
    // | 'clflush'
    // | 'cli'
    // | 'clts'
    // | 'cmc'
    // | 'cmova'
    // | 'cmovae'
    // | 'cmovb'
    // | 'cmovbe'
    // | 'cmovc'
    // | 'cmove'
    // | 'cmovg'
    // | 'cmovge'
    // | 'cmovl'
    // | 'cmovle'
    // | 'cmovna'
    // | 'cmovnae'
    // | 'cmovnb'
    // | 'cmovnbe'
    // | 'cmovnc'
    // | 'cmovne'
    // | 'cmovng'
    // | 'cmovnge'
    // | 'cmovnl'
    // | 'cmovnle'
    // | 'cmovno'
    // | 'cmovnp'
    // | 'cmovns'
    // | 'cmovnz'
    // | 'cmovo'
    // | 'cmovp'
    // | 'cmovpe'
    // | 'cmovpo'
    // | 'cmovs'
    // | 'cmovz'
    // | 'cmp'
    // | 'cmppd'
    // | 'cmpps'
    // | 'cmps'
    // | 'cmpsb'
    // | 'cmpsd'
    // | 'cmpss'
    // | 'cmpsw'
    // | 'cmpxchg'
    // | 'cmpxchg8b'
    // | 'cmpxchg16b'
    // | 'comisd'
    // | 'comiss'
    // | 'cpuid'
    // | 'cvtdq2pd'
    // | 'cvtdq2ps'
    // | 'cvtpd2dq'
    // | 'cvtpd2pi'
    // | 'cvtpd2ps'
    // | 'cvtpi2pd'
    // | 'cvtpi2ps'
    // | 'cvtps2dq'
    // | 'cvtps2pd'
    // | 'cvtps2pi'
    // | 'cvtsd2si'
    // | 'cvtsd2ss'
    // | 'cvtsi2sd'
    // | 'cvtsi2ss'
    // | 'cvtss2sd'
    // | 'cvtss2si'
    // | 'cvttpd2dq'
    // | 'cvttpd2pi'
    // | 'cvttps2dq'
    // | 'cvttps2pi'
    // | 'cvttsd2si'
    // | 'cvttss2si'
    // | 'cwd'
    // | 'cwde'
    // | 'da'
    // | 'daa'
    // | 'das'
    // | 'db'
    // | 'dd'
    // | 'de'
    // | 'dec'
    // | 'df'
    // | 'di'
    // | 'div'
    // | 'divpd'
    // | 'divps'
    // | 'divsd'
    // | 'divss'
    // | 'dl'
    // | 'dq'
    // | 'ds'
    // | 'dt'
    // | 'dw'
    // | 'emms'
    // | 'enter'
    // | 'f2xm1'
    // | 'fabs'
    // | 'fadd'
    // | 'faddp'
    // | 'fbld'
    // | 'fbstp'
    // | 'fchs'
    // | 'fclex'
    // | 'fcmovb'
    // | 'fcmovbe'
    // | 'fcmove'
    // | 'fcmovnb'
    // | 'fcmovnbe'
    // | 'fcmovne'
    // | 'fcmovnu'
    // | 'fcmovu'
    // | 'fcom'
    // | 'fcomi'
    // | 'fcomip'
    // | 'fcomp'
    // | 'fcompp'
    // | 'fcos'
    // | 'fdecstp'
    // | 'fdisi'
    // | 'fdiv'
    // | 'fdivp'
    // | 'fdivr'
    // | 'fdivrp'
    // | 'feni'
    // | 'ffree'
    // | 'fiadd'
    // | 'ficom'
    // | 'ficomp'
    // | 'fidiv'
    // | 'fidivr'
    // | 'fild'
    // | 'fimul'
    // | 'fincstp'
    // | 'finit'
    // | 'fist'
    // | 'fistp'
    // | 'fisub'
    // | 'fisubr'
    // | 'fld'
    // | 'fld1'
    // | 'fldcw'
    // | 'fldenv'
    // | 'fldl2e'
    // | 'fldl2t'
    // | 'fldlg2'
    // | 'fldln2'
    // | 'fldpi'
    // | 'fldz'
    // | 'fmul'
    // | 'fmulp'
    // | 'fnclex'
    // | 'fndisi'
    // | 'fneni'
    // | 'fninit'
    // | 'fnop'
    // | 'fnsave'
    // | 'fnstcw'
    // | 'fnstenv'
    // | 'fnstsw'
    // | 'fpatan'
    // | 'fprem'
    // | 'fprem1'
    // | 'fptan'
    // | 'frndint'
    // | 'frstor'
    // | 'fsave'
    // | 'fscale'
    // | 'fsetpm'
    // | 'fsin'
    // | 'fsincos'
    // | 'fsqrt'
    // | 'fst'
    // | 'fstcw'
    // | 'fstenv'
    // | 'fstp'
    // | 'fstsw'
    // | 'fsub'
    // | 'fsubp'
    // | 'fsubr'
    // | 'fsubrp'
    // | 'ftst'
    // | 'fucom'
    // | 'fucomi'
    // | 'fucomip'
    // | 'fucomp'
    // | 'fucompp'
    // | 'fwait'
    // | 'fxam'
    // | 'fxch'
    // | 'fxrstor'
    // | 'fxsave'
    // | 'fxtract'
    // | 'fyl2x'
    // | 'fyl2xp1'
    // | 'hlt'
    // | 'idiv'
    // | 'imul'
    // | 'in'
    // | 'inc'
    // | 'ins'
    // | 'insb'
    // | 'insd'
    // | 'insw'
    // | 'int'
    // | 'into'
    // | 'invd'
    // | 'invlpg'
    // | 'iret'
    // | 'iretd'
    // | 'ja'
    // | 'jae'
    // | 'jb'
    // | 'jbe'
    // | 'jc'
    // | 'jcxz'
    // | 'je'
    // | 'jecxz'
    // | 'jg'
    // | 'jge'
    // | 'jl'
    // | 'jle'
    // | 'jmp'
    // | 'jna'
    // | 'jnae'
    // | 'jnb'
    // | 'jnbe'
    // | 'jnc'
    // | 'jne'
    // | 'jng'
    // | 'jnge'
    // | 'jnl'
    // | 'jnle'
    // | 'jno'
    // | 'jnp'
    // | 'jns'
    // | 'jnz'
    // | 'jo'
    // | 'jp'
    // | 'jpe'
    // | 'jpo'
    // | 'js'
    // | 'jz'
    // | 'lahf'
    // | 'lar'
    // | 'ldmxcsr'
    // | 'lds'
    // | 'lea'
    // | 'leave'
    // | 'les'
    // | 'lfence'
    // | 'lfs'
    // | 'lgdt'
    // | 'lgs'
    // | 'lidt'
    // | 'lldt'
    // | 'lmsw'
    // | 'lock'
    // | 'lods'
    // | 'lodsb'
    // | 'lodsd'
    // | 'lodsw'
    // | 'loop'
    // | 'loope'
    // | 'loopne'
    // | 'loopnz'
    // | 'loopz'
    // | 'lsl'
    // | 'lss'
    // | 'ltr'
    // | 'maskmovdqu'
    // | 'maskmovq'
    // | 'maxpd'
    // | 'maxps'
    // | 'maxsd'
    // | 'maxss'
    // | 'mfence'
    // | 'minpd'
    // | 'minps'
    // | 'minsd'
    // | 'minss'
    // | 'mov'
    // | 'movapd'
    // | 'movaps'
    // | 'movd'
    // | 'movdq2q'
    // | 'movdqa'
    // | 'movdqu'
    // | 'movhlps'
    // | 'movhpd'
    // | 'movhps'
    // | 'movlhps'
    // | 'movlpd'
    // | 'movlps'
    // | 'movmskpd'
    // | 'movmskps'
    // | 'movntdq'
    // | 'movnti'
    // | 'movntpd'
    // | 'movntps'
    // | 'movntq'
    // | 'movq'
    // | 'movq2dq'
    // | 'movs'
    // | 'movsb'
    // | 'movsd'
    // | 'movss'
    // | 'movsw'
    // | 'movsx'
    // | 'movupd'
    // | 'movups'
    // | 'movzx'
    // | 'mul'
    // | 'mulpd'
    // | 'mulps'
    // | 'mulsd'
    // | 'mulss'
    // | 'neg'
    // | 'nop'
    // | 'not'
    // | 'or'
    // | 'orpd'
    // | 'orps'
    // | 'out'
    // | 'outs'
    // | 'outsb'
    // | 'outsd'
    // | 'outsw'
    // | 'packssdw'
    // | 'packsswb'
    // | 'packuswb'
    // | 'paddb'
    // | 'paddd'
    // | 'paddq'
    // | 'paddsb'
    // | 'paddsw'
    // | 'paddusb'
    // | 'paddusw'
    // | 'paddw'
    // | 'pand'
    // | 'pandn'
    // | 'pavgb'
    // | 'pavgw'
    // | 'pcmpeqb'
    // | 'pcmpeqd'
    // | 'pcmpeqw'
    // | 'pcmpgtb'
    // | 'pcmpgtd'
    // | 'pcmpgtw'
    // | 'pextrw'
    // | 'pinsrw'
    // | 'pmaddwd'
    // | 'pmaxsw'
    // | 'pmaxub'
    // | 'pminsw'
    // | 'pminub'
    // | 'pmovmskb'
    // | 'pmulhuw'
    // | 'pmulhw'
    // | 'pmullw'
    // | 'pmuludq'
    // | 'pop'
    // | 'popa'
    // | 'popad'
    // | 'popf'
    // | 'popfd'
    // | 'por'
    // | 'prefetchnta'
    // | 'prefetcht0'
    // | 'prefetcht1'
    // | 'prefetcht2'
    // | 'psadbw'
    // | 'pshufd'
    // | 'pshufhw'
    // | 'pshuflw'
    // | 'pshufw'
    // | 'pslld'
    // | 'pslldq'
    // | 'psllq'
    // | 'psllw'
    // | 'psrad'
    // | 'psraw'
    // | 'psrld'
    // | 'psrldq'
    // | 'psrlq'
    // | 'psrlw'
    // | 'psubb'
    // | 'psubd'
    // | 'psubq'
    // | 'psubsb'
    // | 'psubsw'
    // | 'psubusb'
    // | 'psubusw'
    // | 'psubw'
    // | 'punpckhbw'
    // | 'punpckhdq'
    // | 'punpckhqdq'
    // | 'punpckhwd'
    // | 'punpcklbw'
    // | 'punpckldq'
    // | 'punpcklqdq'
    // | 'punpcklwd'
    // | 'push'
    // | 'pusha'
    // | 'pushad'
    // | 'pushf'
    // | 'pushfd'
    // | 'pxor'
    // | 'rcl'
    // | 'rcpps'
    // | 'rcpss'
    // | 'rcr'
    // | 'rdmsr'
    // | 'rdpmc'
    // | 'rdtsc'
    // | 'rep'
    // | 'repe'
    // | 'repne'
    // | 'repnz'
    // | 'repz'
    // | 'ret'
    // | 'retf'
    // | 'rol'
    // | 'ror'
    // | 'rsm'
    // | 'rsqrtps'
    // | 'rsqrtss'
    // | 'sahf'
    // | 'sal'
    // | 'sar'
    // | 'sbb'
    // | 'scas'
    // | 'scasb'
    // | 'scasd'
    // | 'scasw'
    // | 'seta'
    // | 'setae'
    // | 'setb'
    // | 'setbe'
    // | 'setc'
    // | 'sete'
    // | 'setg'
    // | 'setge'
    // | 'setl'
    // | 'setle'
    // | 'setna'
    // | 'setnae'
    // | 'setnb'
    // | 'setnbe'
    // | 'setnc'
    // | 'setne'
    // | 'setng'
    // | 'setnge'
    // | 'setnl'
    // | 'setnle'
    // | 'setno'
    // | 'setnp'
    // | 'setns'
    // | 'setnz'
    // | 'seto'
    // | 'setp'
    // | 'setpe'
    // | 'setpo'
    // | 'sets'
    // | 'setz'
    // | 'sfence'
    // | 'sgdt'
    // | 'shl'
    // | 'shld'
    // | 'shr'
    // | 'shrd'
    // | 'shufpd'
    // | 'shufps'
    // | 'sidt'
    // | 'sldt'
    // | 'smsw'
    // | 'sqrtpd'
    // | 'sqrtps'
    // | 'sqrtsd'
    // | 'sqrtss'
    // | 'stc'
    // | 'std'
    // | 'sti'
    // | 'stmxcsr'
    // | 'stos'
    // | 'stosb'
    // | 'stosd'
    // | 'stosw'
    // | 'str'
    // | 'sub'
    // | 'subpd'
    // | 'subps'
    // | 'subsd'
    // | 'subss'
    // | 'sysenter'
    // | 'sysexit'
    // | 'test'
    // | 'ucomisd'
    // | 'ucomiss'
    // | 'ud2'
    // | 'unpckhpd'
    // | 'unpckhps'
    // | 'unpcklpd'
    // | 'unpcklps'
    // | 'verr'
    // | 'verw'
    // | 'wait'
    // | 'wbinvd'
    // | 'wrmsr'
    // | 'xadd'
    // | 'xchg'
    // | 'xlat'
    // | 'xlatb'
    // | 'xor'
    // | 'xorpd'
    // | 'xorps'
    // | 'addsubpd'
    // | 'addsubps'
    // | 'fisttp'
    // | 'haddpd'
    // | 'haddps'
    // | 'hsubpd'
    // | 'hsubps'
    // | 'lddqu'
    // | 'monitor'
    // | 'movddup'
    // | 'movshdup'
    // | 'movsldup'
    // | 'mwait'
    // | 'pavgusb'
    // | 'pf2id'
    // | 'pfacc'
    // | 'pfadd'
    // | 'pfcmpeq'
    // | 'pfcmpge'
    // | 'pfcmpgt'
    // | 'pfmax'
    // | 'pfmin'
    // | 'pfmul'
    // | 'pfnacc'
    // | 'pfpnacc'
    // | 'pfrcp'
    // | 'pfrcpit1'
    // | 'pfrcpit2'
    // | 'pfrsqit1'
    // | 'pfrsqrt'
    // | 'pfsub'
    // | 'pfsubr'
    // | 'pi2fd'
    // | 'pmulhrw'
    // | 'pswapd'
    public static boolean Opcode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Opcode")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, OPCODE, "<opcode>");
        r = consumeToken(b, "aaa");
        if (!r) r = consumeToken(b, "aad");
        if (!r) r = consumeToken(b, "aam");
        if (!r) r = consumeToken(b, "aas");
        if (!r) r = consumeToken(b, "adc");
        if (!r) r = consumeToken(b, "add");
        if (!r) r = consumeToken(b, "addpd");
        if (!r) r = consumeToken(b, "addps");
        if (!r) r = consumeToken(b, "addsd");
        if (!r) r = consumeToken(b, "addss");
        if (!r) r = consumeToken(b, "and");
        if (!r) r = consumeToken(b, "andnpd");
        if (!r) r = consumeToken(b, "andnps");
        if (!r) r = consumeToken(b, "andpd");
        if (!r) r = consumeToken(b, "andps");
        if (!r) r = consumeToken(b, "arpl");
        if (!r) r = consumeToken(b, "bound");
        if (!r) r = consumeToken(b, "bsf");
        if (!r) r = consumeToken(b, "bsr");
        if (!r) r = consumeToken(b, "bswap");
        if (!r) r = consumeToken(b, "bt");
        if (!r) r = consumeToken(b, "btc");
        if (!r) r = consumeToken(b, "btr");
        if (!r) r = consumeToken(b, "bts");
        if (!r) r = consumeToken(b, "call");
        if (!r) r = consumeToken(b, "cbw");
        if (!r) r = consumeToken(b, "cdq");
        if (!r) r = consumeToken(b, "clc");
        if (!r) r = consumeToken(b, "cld");
        if (!r) r = consumeToken(b, "clflush");
        if (!r) r = consumeToken(b, "cli");
        if (!r) r = consumeToken(b, "clts");
        if (!r) r = consumeToken(b, "cmc");
        if (!r) r = consumeToken(b, "cmova");
        if (!r) r = consumeToken(b, "cmovae");
        if (!r) r = consumeToken(b, "cmovb");
        if (!r) r = consumeToken(b, "cmovbe");
        if (!r) r = consumeToken(b, "cmovc");
        if (!r) r = consumeToken(b, "cmove");
        if (!r) r = consumeToken(b, "cmovg");
        if (!r) r = consumeToken(b, "cmovge");
        if (!r) r = consumeToken(b, "cmovl");
        if (!r) r = consumeToken(b, "cmovle");
        if (!r) r = consumeToken(b, "cmovna");
        if (!r) r = consumeToken(b, "cmovnae");
        if (!r) r = consumeToken(b, "cmovnb");
        if (!r) r = consumeToken(b, "cmovnbe");
        if (!r) r = consumeToken(b, "cmovnc");
        if (!r) r = consumeToken(b, "cmovne");
        if (!r) r = consumeToken(b, "cmovng");
        if (!r) r = consumeToken(b, "cmovnge");
        if (!r) r = consumeToken(b, "cmovnl");
        if (!r) r = consumeToken(b, "cmovnle");
        if (!r) r = consumeToken(b, "cmovno");
        if (!r) r = consumeToken(b, "cmovnp");
        if (!r) r = consumeToken(b, "cmovns");
        if (!r) r = consumeToken(b, "cmovnz");
        if (!r) r = consumeToken(b, "cmovo");
        if (!r) r = consumeToken(b, "cmovp");
        if (!r) r = consumeToken(b, "cmovpe");
        if (!r) r = consumeToken(b, "cmovpo");
        if (!r) r = consumeToken(b, "cmovs");
        if (!r) r = consumeToken(b, "cmovz");
        if (!r) r = consumeToken(b, "cmp");
        if (!r) r = consumeToken(b, "cmppd");
        if (!r) r = consumeToken(b, "cmpps");
        if (!r) r = consumeToken(b, "cmps");
        if (!r) r = consumeToken(b, "cmpsb");
        if (!r) r = consumeToken(b, "cmpsd");
        if (!r) r = consumeToken(b, "cmpss");
        if (!r) r = consumeToken(b, "cmpsw");
        if (!r) r = consumeToken(b, "cmpxchg");
        if (!r) r = consumeToken(b, "cmpxchg8b");
        if (!r) r = consumeToken(b, "cmpxchg16b");
        if (!r) r = consumeToken(b, "comisd");
        if (!r) r = consumeToken(b, "comiss");
        if (!r) r = consumeToken(b, "cpuid");
        if (!r) r = consumeToken(b, "cvtdq2pd");
        if (!r) r = consumeToken(b, "cvtdq2ps");
        if (!r) r = consumeToken(b, "cvtpd2dq");
        if (!r) r = consumeToken(b, "cvtpd2pi");
        if (!r) r = consumeToken(b, "cvtpd2ps");
        if (!r) r = consumeToken(b, "cvtpi2pd");
        if (!r) r = consumeToken(b, "cvtpi2ps");
        if (!r) r = consumeToken(b, "cvtps2dq");
        if (!r) r = consumeToken(b, "cvtps2pd");
        if (!r) r = consumeToken(b, "cvtps2pi");
        if (!r) r = consumeToken(b, "cvtsd2si");
        if (!r) r = consumeToken(b, "cvtsd2ss");
        if (!r) r = consumeToken(b, "cvtsi2sd");
        if (!r) r = consumeToken(b, "cvtsi2ss");
        if (!r) r = consumeToken(b, "cvtss2sd");
        if (!r) r = consumeToken(b, "cvtss2si");
        if (!r) r = consumeToken(b, "cvttpd2dq");
        if (!r) r = consumeToken(b, "cvttpd2pi");
        if (!r) r = consumeToken(b, "cvttps2dq");
        if (!r) r = consumeToken(b, "cvttps2pi");
        if (!r) r = consumeToken(b, "cvttsd2si");
        if (!r) r = consumeToken(b, "cvttss2si");
        if (!r) r = consumeToken(b, "cwd");
        if (!r) r = consumeToken(b, "cwde");
        if (!r) r = consumeToken(b, "da");
        if (!r) r = consumeToken(b, "daa");
        if (!r) r = consumeToken(b, "das");
        if (!r) r = consumeToken(b, "db");
        if (!r) r = consumeToken(b, "dd");
        if (!r) r = consumeToken(b, "de");
        if (!r) r = consumeToken(b, "dec");
        if (!r) r = consumeToken(b, "df");
        if (!r) r = consumeToken(b, "di");
        if (!r) r = consumeToken(b, "div");
        if (!r) r = consumeToken(b, "divpd");
        if (!r) r = consumeToken(b, "divps");
        if (!r) r = consumeToken(b, "divsd");
        if (!r) r = consumeToken(b, "divss");
        if (!r) r = consumeToken(b, "dl");
        if (!r) r = consumeToken(b, "dq");
        if (!r) r = consumeToken(b, "ds");
        if (!r) r = consumeToken(b, "dt");
        if (!r) r = consumeToken(b, "dw");
        if (!r) r = consumeToken(b, "emms");
        if (!r) r = consumeToken(b, "enter");
        if (!r) r = consumeToken(b, "f2xm1");
        if (!r) r = consumeToken(b, "fabs");
        if (!r) r = consumeToken(b, "fadd");
        if (!r) r = consumeToken(b, "faddp");
        if (!r) r = consumeToken(b, "fbld");
        if (!r) r = consumeToken(b, "fbstp");
        if (!r) r = consumeToken(b, "fchs");
        if (!r) r = consumeToken(b, "fclex");
        if (!r) r = consumeToken(b, "fcmovb");
        if (!r) r = consumeToken(b, "fcmovbe");
        if (!r) r = consumeToken(b, "fcmove");
        if (!r) r = consumeToken(b, "fcmovnb");
        if (!r) r = consumeToken(b, "fcmovnbe");
        if (!r) r = consumeToken(b, "fcmovne");
        if (!r) r = consumeToken(b, "fcmovnu");
        if (!r) r = consumeToken(b, "fcmovu");
        if (!r) r = consumeToken(b, "fcom");
        if (!r) r = consumeToken(b, "fcomi");
        if (!r) r = consumeToken(b, "fcomip");
        if (!r) r = consumeToken(b, "fcomp");
        if (!r) r = consumeToken(b, "fcompp");
        if (!r) r = consumeToken(b, "fcos");
        if (!r) r = consumeToken(b, "fdecstp");
        if (!r) r = consumeToken(b, "fdisi");
        if (!r) r = consumeToken(b, "fdiv");
        if (!r) r = consumeToken(b, "fdivp");
        if (!r) r = consumeToken(b, "fdivr");
        if (!r) r = consumeToken(b, "fdivrp");
        if (!r) r = consumeToken(b, "feni");
        if (!r) r = consumeToken(b, "ffree");
        if (!r) r = consumeToken(b, "fiadd");
        if (!r) r = consumeToken(b, "ficom");
        if (!r) r = consumeToken(b, "ficomp");
        if (!r) r = consumeToken(b, "fidiv");
        if (!r) r = consumeToken(b, "fidivr");
        if (!r) r = consumeToken(b, "fild");
        if (!r) r = consumeToken(b, "fimul");
        if (!r) r = consumeToken(b, "fincstp");
        if (!r) r = consumeToken(b, "finit");
        if (!r) r = consumeToken(b, "fist");
        if (!r) r = consumeToken(b, "fistp");
        if (!r) r = consumeToken(b, "fisub");
        if (!r) r = consumeToken(b, "fisubr");
        if (!r) r = consumeToken(b, "fld");
        if (!r) r = consumeToken(b, "fld1");
        if (!r) r = consumeToken(b, "fldcw");
        if (!r) r = consumeToken(b, "fldenv");
        if (!r) r = consumeToken(b, "fldl2e");
        if (!r) r = consumeToken(b, "fldl2t");
        if (!r) r = consumeToken(b, "fldlg2");
        if (!r) r = consumeToken(b, "fldln2");
        if (!r) r = consumeToken(b, "fldpi");
        if (!r) r = consumeToken(b, "fldz");
        if (!r) r = consumeToken(b, "fmul");
        if (!r) r = consumeToken(b, "fmulp");
        if (!r) r = consumeToken(b, "fnclex");
        if (!r) r = consumeToken(b, "fndisi");
        if (!r) r = consumeToken(b, "fneni");
        if (!r) r = consumeToken(b, "fninit");
        if (!r) r = consumeToken(b, "fnop");
        if (!r) r = consumeToken(b, "fnsave");
        if (!r) r = consumeToken(b, "fnstcw");
        if (!r) r = consumeToken(b, "fnstenv");
        if (!r) r = consumeToken(b, "fnstsw");
        if (!r) r = consumeToken(b, "fpatan");
        if (!r) r = consumeToken(b, "fprem");
        if (!r) r = consumeToken(b, "fprem1");
        if (!r) r = consumeToken(b, "fptan");
        if (!r) r = consumeToken(b, "frndint");
        if (!r) r = consumeToken(b, "frstor");
        if (!r) r = consumeToken(b, "fsave");
        if (!r) r = consumeToken(b, "fscale");
        if (!r) r = consumeToken(b, "fsetpm");
        if (!r) r = consumeToken(b, "fsin");
        if (!r) r = consumeToken(b, "fsincos");
        if (!r) r = consumeToken(b, "fsqrt");
        if (!r) r = consumeToken(b, "fst");
        if (!r) r = consumeToken(b, "fstcw");
        if (!r) r = consumeToken(b, "fstenv");
        if (!r) r = consumeToken(b, "fstp");
        if (!r) r = consumeToken(b, "fstsw");
        if (!r) r = consumeToken(b, "fsub");
        if (!r) r = consumeToken(b, "fsubp");
        if (!r) r = consumeToken(b, "fsubr");
        if (!r) r = consumeToken(b, "fsubrp");
        if (!r) r = consumeToken(b, "ftst");
        if (!r) r = consumeToken(b, "fucom");
        if (!r) r = consumeToken(b, "fucomi");
        if (!r) r = consumeToken(b, "fucomip");
        if (!r) r = consumeToken(b, "fucomp");
        if (!r) r = consumeToken(b, "fucompp");
        if (!r) r = consumeToken(b, "fwait");
        if (!r) r = consumeToken(b, "fxam");
        if (!r) r = consumeToken(b, "fxch");
        if (!r) r = consumeToken(b, "fxrstor");
        if (!r) r = consumeToken(b, "fxsave");
        if (!r) r = consumeToken(b, "fxtract");
        if (!r) r = consumeToken(b, "fyl2x");
        if (!r) r = consumeToken(b, "fyl2xp1");
        if (!r) r = consumeToken(b, "hlt");
        if (!r) r = consumeToken(b, "idiv");
        if (!r) r = consumeToken(b, "imul");
        if (!r) r = consumeToken(b, KW_IN);
        if (!r) r = consumeToken(b, "inc");
        if (!r) r = consumeToken(b, "ins");
        if (!r) r = consumeToken(b, "insb");
        if (!r) r = consumeToken(b, "insd");
        if (!r) r = consumeToken(b, "insw");
        if (!r) r = consumeToken(b, KW_INT);
        if (!r) r = consumeToken(b, "into");
        if (!r) r = consumeToken(b, "invd");
        if (!r) r = consumeToken(b, "invlpg");
        if (!r) r = consumeToken(b, "iret");
        if (!r) r = consumeToken(b, "iretd");
        if (!r) r = consumeToken(b, "ja");
        if (!r) r = consumeToken(b, "jae");
        if (!r) r = consumeToken(b, "jb");
        if (!r) r = consumeToken(b, "jbe");
        if (!r) r = consumeToken(b, "jc");
        if (!r) r = consumeToken(b, "jcxz");
        if (!r) r = consumeToken(b, "je");
        if (!r) r = consumeToken(b, "jecxz");
        if (!r) r = consumeToken(b, "jg");
        if (!r) r = consumeToken(b, "jge");
        if (!r) r = consumeToken(b, "jl");
        if (!r) r = consumeToken(b, "jle");
        if (!r) r = consumeToken(b, "jmp");
        if (!r) r = consumeToken(b, "jna");
        if (!r) r = consumeToken(b, "jnae");
        if (!r) r = consumeToken(b, "jnb");
        if (!r) r = consumeToken(b, "jnbe");
        if (!r) r = consumeToken(b, "jnc");
        if (!r) r = consumeToken(b, "jne");
        if (!r) r = consumeToken(b, "jng");
        if (!r) r = consumeToken(b, "jnge");
        if (!r) r = consumeToken(b, "jnl");
        if (!r) r = consumeToken(b, "jnle");
        if (!r) r = consumeToken(b, "jno");
        if (!r) r = consumeToken(b, "jnp");
        if (!r) r = consumeToken(b, "jns");
        if (!r) r = consumeToken(b, "jnz");
        if (!r) r = consumeToken(b, "jo");
        if (!r) r = consumeToken(b, "jp");
        if (!r) r = consumeToken(b, "jpe");
        if (!r) r = consumeToken(b, "jpo");
        if (!r) r = consumeToken(b, "js");
        if (!r) r = consumeToken(b, "jz");
        if (!r) r = consumeToken(b, "lahf");
        if (!r) r = consumeToken(b, "lar");
        if (!r) r = consumeToken(b, "ldmxcsr");
        if (!r) r = consumeToken(b, "lds");
        if (!r) r = consumeToken(b, "lea");
        if (!r) r = consumeToken(b, "leave");
        if (!r) r = consumeToken(b, "les");
        if (!r) r = consumeToken(b, "lfence");
        if (!r) r = consumeToken(b, "lfs");
        if (!r) r = consumeToken(b, "lgdt");
        if (!r) r = consumeToken(b, "lgs");
        if (!r) r = consumeToken(b, "lidt");
        if (!r) r = consumeToken(b, "lldt");
        if (!r) r = consumeToken(b, "lmsw");
        if (!r) r = consumeToken(b, "lock");
        if (!r) r = consumeToken(b, "lods");
        if (!r) r = consumeToken(b, "lodsb");
        if (!r) r = consumeToken(b, "lodsd");
        if (!r) r = consumeToken(b, "lodsw");
        if (!r) r = consumeToken(b, "loop");
        if (!r) r = consumeToken(b, "loope");
        if (!r) r = consumeToken(b, "loopne");
        if (!r) r = consumeToken(b, "loopnz");
        if (!r) r = consumeToken(b, "loopz");
        if (!r) r = consumeToken(b, "lsl");
        if (!r) r = consumeToken(b, "lss");
        if (!r) r = consumeToken(b, "ltr");
        if (!r) r = consumeToken(b, "maskmovdqu");
        if (!r) r = consumeToken(b, "maskmovq");
        if (!r) r = consumeToken(b, "maxpd");
        if (!r) r = consumeToken(b, "maxps");
        if (!r) r = consumeToken(b, "maxsd");
        if (!r) r = consumeToken(b, "maxss");
        if (!r) r = consumeToken(b, "mfence");
        if (!r) r = consumeToken(b, "minpd");
        if (!r) r = consumeToken(b, "minps");
        if (!r) r = consumeToken(b, "minsd");
        if (!r) r = consumeToken(b, "minss");
        if (!r) r = consumeToken(b, "mov");
        if (!r) r = consumeToken(b, "movapd");
        if (!r) r = consumeToken(b, "movaps");
        if (!r) r = consumeToken(b, "movd");
        if (!r) r = consumeToken(b, "movdq2q");
        if (!r) r = consumeToken(b, "movdqa");
        if (!r) r = consumeToken(b, "movdqu");
        if (!r) r = consumeToken(b, "movhlps");
        if (!r) r = consumeToken(b, "movhpd");
        if (!r) r = consumeToken(b, "movhps");
        if (!r) r = consumeToken(b, "movlhps");
        if (!r) r = consumeToken(b, "movlpd");
        if (!r) r = consumeToken(b, "movlps");
        if (!r) r = consumeToken(b, "movmskpd");
        if (!r) r = consumeToken(b, "movmskps");
        if (!r) r = consumeToken(b, "movntdq");
        if (!r) r = consumeToken(b, "movnti");
        if (!r) r = consumeToken(b, "movntpd");
        if (!r) r = consumeToken(b, "movntps");
        if (!r) r = consumeToken(b, "movntq");
        if (!r) r = consumeToken(b, "movq");
        if (!r) r = consumeToken(b, "movq2dq");
        if (!r) r = consumeToken(b, "movs");
        if (!r) r = consumeToken(b, "movsb");
        if (!r) r = consumeToken(b, "movsd");
        if (!r) r = consumeToken(b, "movss");
        if (!r) r = consumeToken(b, "movsw");
        if (!r) r = consumeToken(b, "movsx");
        if (!r) r = consumeToken(b, "movupd");
        if (!r) r = consumeToken(b, "movups");
        if (!r) r = consumeToken(b, "movzx");
        if (!r) r = consumeToken(b, "mul");
        if (!r) r = consumeToken(b, "mulpd");
        if (!r) r = consumeToken(b, "mulps");
        if (!r) r = consumeToken(b, "mulsd");
        if (!r) r = consumeToken(b, "mulss");
        if (!r) r = consumeToken(b, "neg");
        if (!r) r = consumeToken(b, "nop");
        if (!r) r = consumeToken(b, "not");
        if (!r) r = consumeToken(b, "or");
        if (!r) r = consumeToken(b, "orpd");
        if (!r) r = consumeToken(b, "orps");
        if (!r) r = consumeToken(b, KW_OUT);
        if (!r) r = consumeToken(b, "outs");
        if (!r) r = consumeToken(b, "outsb");
        if (!r) r = consumeToken(b, "outsd");
        if (!r) r = consumeToken(b, "outsw");
        if (!r) r = consumeToken(b, "packssdw");
        if (!r) r = consumeToken(b, "packsswb");
        if (!r) r = consumeToken(b, "packuswb");
        if (!r) r = consumeToken(b, "paddb");
        if (!r) r = consumeToken(b, "paddd");
        if (!r) r = consumeToken(b, "paddq");
        if (!r) r = consumeToken(b, "paddsb");
        if (!r) r = consumeToken(b, "paddsw");
        if (!r) r = consumeToken(b, "paddusb");
        if (!r) r = consumeToken(b, "paddusw");
        if (!r) r = consumeToken(b, "paddw");
        if (!r) r = consumeToken(b, "pand");
        if (!r) r = consumeToken(b, "pandn");
        if (!r) r = consumeToken(b, "pavgb");
        if (!r) r = consumeToken(b, "pavgw");
        if (!r) r = consumeToken(b, "pcmpeqb");
        if (!r) r = consumeToken(b, "pcmpeqd");
        if (!r) r = consumeToken(b, "pcmpeqw");
        if (!r) r = consumeToken(b, "pcmpgtb");
        if (!r) r = consumeToken(b, "pcmpgtd");
        if (!r) r = consumeToken(b, "pcmpgtw");
        if (!r) r = consumeToken(b, "pextrw");
        if (!r) r = consumeToken(b, "pinsrw");
        if (!r) r = consumeToken(b, "pmaddwd");
        if (!r) r = consumeToken(b, "pmaxsw");
        if (!r) r = consumeToken(b, "pmaxub");
        if (!r) r = consumeToken(b, "pminsw");
        if (!r) r = consumeToken(b, "pminub");
        if (!r) r = consumeToken(b, "pmovmskb");
        if (!r) r = consumeToken(b, "pmulhuw");
        if (!r) r = consumeToken(b, "pmulhw");
        if (!r) r = consumeToken(b, "pmullw");
        if (!r) r = consumeToken(b, "pmuludq");
        if (!r) r = consumeToken(b, "pop");
        if (!r) r = consumeToken(b, "popa");
        if (!r) r = consumeToken(b, "popad");
        if (!r) r = consumeToken(b, "popf");
        if (!r) r = consumeToken(b, "popfd");
        if (!r) r = consumeToken(b, "por");
        if (!r) r = consumeToken(b, "prefetchnta");
        if (!r) r = consumeToken(b, "prefetcht0");
        if (!r) r = consumeToken(b, "prefetcht1");
        if (!r) r = consumeToken(b, "prefetcht2");
        if (!r) r = consumeToken(b, "psadbw");
        if (!r) r = consumeToken(b, "pshufd");
        if (!r) r = consumeToken(b, "pshufhw");
        if (!r) r = consumeToken(b, "pshuflw");
        if (!r) r = consumeToken(b, "pshufw");
        if (!r) r = consumeToken(b, "pslld");
        if (!r) r = consumeToken(b, "pslldq");
        if (!r) r = consumeToken(b, "psllq");
        if (!r) r = consumeToken(b, "psllw");
        if (!r) r = consumeToken(b, "psrad");
        if (!r) r = consumeToken(b, "psraw");
        if (!r) r = consumeToken(b, "psrld");
        if (!r) r = consumeToken(b, "psrldq");
        if (!r) r = consumeToken(b, "psrlq");
        if (!r) r = consumeToken(b, "psrlw");
        if (!r) r = consumeToken(b, "psubb");
        if (!r) r = consumeToken(b, "psubd");
        if (!r) r = consumeToken(b, "psubq");
        if (!r) r = consumeToken(b, "psubsb");
        if (!r) r = consumeToken(b, "psubsw");
        if (!r) r = consumeToken(b, "psubusb");
        if (!r) r = consumeToken(b, "psubusw");
        if (!r) r = consumeToken(b, "psubw");
        if (!r) r = consumeToken(b, "punpckhbw");
        if (!r) r = consumeToken(b, "punpckhdq");
        if (!r) r = consumeToken(b, "punpckhqdq");
        if (!r) r = consumeToken(b, "punpckhwd");
        if (!r) r = consumeToken(b, "punpcklbw");
        if (!r) r = consumeToken(b, "punpckldq");
        if (!r) r = consumeToken(b, "punpcklqdq");
        if (!r) r = consumeToken(b, "punpcklwd");
        if (!r) r = consumeToken(b, "push");
        if (!r) r = consumeToken(b, "pusha");
        if (!r) r = consumeToken(b, "pushad");
        if (!r) r = consumeToken(b, "pushf");
        if (!r) r = consumeToken(b, "pushfd");
        if (!r) r = consumeToken(b, "pxor");
        if (!r) r = consumeToken(b, "rcl");
        if (!r) r = consumeToken(b, "rcpps");
        if (!r) r = consumeToken(b, "rcpss");
        if (!r) r = consumeToken(b, "rcr");
        if (!r) r = consumeToken(b, "rdmsr");
        if (!r) r = consumeToken(b, "rdpmc");
        if (!r) r = consumeToken(b, "rdtsc");
        if (!r) r = consumeToken(b, "rep");
        if (!r) r = consumeToken(b, "repe");
        if (!r) r = consumeToken(b, "repne");
        if (!r) r = consumeToken(b, "repnz");
        if (!r) r = consumeToken(b, "repz");
        if (!r) r = consumeToken(b, "ret");
        if (!r) r = consumeToken(b, "retf");
        if (!r) r = consumeToken(b, "rol");
        if (!r) r = consumeToken(b, "ror");
        if (!r) r = consumeToken(b, "rsm");
        if (!r) r = consumeToken(b, "rsqrtps");
        if (!r) r = consumeToken(b, "rsqrtss");
        if (!r) r = consumeToken(b, "sahf");
        if (!r) r = consumeToken(b, "sal");
        if (!r) r = consumeToken(b, "sar");
        if (!r) r = consumeToken(b, "sbb");
        if (!r) r = consumeToken(b, "scas");
        if (!r) r = consumeToken(b, "scasb");
        if (!r) r = consumeToken(b, "scasd");
        if (!r) r = consumeToken(b, "scasw");
        if (!r) r = consumeToken(b, "seta");
        if (!r) r = consumeToken(b, "setae");
        if (!r) r = consumeToken(b, "setb");
        if (!r) r = consumeToken(b, "setbe");
        if (!r) r = consumeToken(b, "setc");
        if (!r) r = consumeToken(b, "sete");
        if (!r) r = consumeToken(b, "setg");
        if (!r) r = consumeToken(b, "setge");
        if (!r) r = consumeToken(b, "setl");
        if (!r) r = consumeToken(b, "setle");
        if (!r) r = consumeToken(b, "setna");
        if (!r) r = consumeToken(b, "setnae");
        if (!r) r = consumeToken(b, "setnb");
        if (!r) r = consumeToken(b, "setnbe");
        if (!r) r = consumeToken(b, "setnc");
        if (!r) r = consumeToken(b, "setne");
        if (!r) r = consumeToken(b, "setng");
        if (!r) r = consumeToken(b, "setnge");
        if (!r) r = consumeToken(b, "setnl");
        if (!r) r = consumeToken(b, "setnle");
        if (!r) r = consumeToken(b, "setno");
        if (!r) r = consumeToken(b, "setnp");
        if (!r) r = consumeToken(b, "setns");
        if (!r) r = consumeToken(b, "setnz");
        if (!r) r = consumeToken(b, "seto");
        if (!r) r = consumeToken(b, "setp");
        if (!r) r = consumeToken(b, "setpe");
        if (!r) r = consumeToken(b, "setpo");
        if (!r) r = consumeToken(b, "sets");
        if (!r) r = consumeToken(b, "setz");
        if (!r) r = consumeToken(b, "sfence");
        if (!r) r = consumeToken(b, "sgdt");
        if (!r) r = consumeToken(b, "shl");
        if (!r) r = consumeToken(b, "shld");
        if (!r) r = consumeToken(b, "shr");
        if (!r) r = consumeToken(b, "shrd");
        if (!r) r = consumeToken(b, "shufpd");
        if (!r) r = consumeToken(b, "shufps");
        if (!r) r = consumeToken(b, "sidt");
        if (!r) r = consumeToken(b, "sldt");
        if (!r) r = consumeToken(b, "smsw");
        if (!r) r = consumeToken(b, "sqrtpd");
        if (!r) r = consumeToken(b, "sqrtps");
        if (!r) r = consumeToken(b, "sqrtsd");
        if (!r) r = consumeToken(b, "sqrtss");
        if (!r) r = consumeToken(b, "stc");
        if (!r) r = consumeToken(b, "std");
        if (!r) r = consumeToken(b, "sti");
        if (!r) r = consumeToken(b, "stmxcsr");
        if (!r) r = consumeToken(b, "stos");
        if (!r) r = consumeToken(b, "stosb");
        if (!r) r = consumeToken(b, "stosd");
        if (!r) r = consumeToken(b, "stosw");
        if (!r) r = consumeToken(b, "str");
        if (!r) r = consumeToken(b, "sub");
        if (!r) r = consumeToken(b, "subpd");
        if (!r) r = consumeToken(b, "subps");
        if (!r) r = consumeToken(b, "subsd");
        if (!r) r = consumeToken(b, "subss");
        if (!r) r = consumeToken(b, "sysenter");
        if (!r) r = consumeToken(b, "sysexit");
        if (!r) r = consumeToken(b, "test");
        if (!r) r = consumeToken(b, "ucomisd");
        if (!r) r = consumeToken(b, "ucomiss");
        if (!r) r = consumeToken(b, "ud2");
        if (!r) r = consumeToken(b, "unpckhpd");
        if (!r) r = consumeToken(b, "unpckhps");
        if (!r) r = consumeToken(b, "unpcklpd");
        if (!r) r = consumeToken(b, "unpcklps");
        if (!r) r = consumeToken(b, "verr");
        if (!r) r = consumeToken(b, "verw");
        if (!r) r = consumeToken(b, "wait");
        if (!r) r = consumeToken(b, "wbinvd");
        if (!r) r = consumeToken(b, "wrmsr");
        if (!r) r = consumeToken(b, "xadd");
        if (!r) r = consumeToken(b, "xchg");
        if (!r) r = consumeToken(b, "xlat");
        if (!r) r = consumeToken(b, "xlatb");
        if (!r) r = consumeToken(b, "xor");
        if (!r) r = consumeToken(b, "xorpd");
        if (!r) r = consumeToken(b, "xorps");
        if (!r) r = consumeToken(b, "addsubpd");
        if (!r) r = consumeToken(b, "addsubps");
        if (!r) r = consumeToken(b, "fisttp");
        if (!r) r = consumeToken(b, "haddpd");
        if (!r) r = consumeToken(b, "haddps");
        if (!r) r = consumeToken(b, "hsubpd");
        if (!r) r = consumeToken(b, "hsubps");
        if (!r) r = consumeToken(b, "lddqu");
        if (!r) r = consumeToken(b, "monitor");
        if (!r) r = consumeToken(b, "movddup");
        if (!r) r = consumeToken(b, "movshdup");
        if (!r) r = consumeToken(b, "movsldup");
        if (!r) r = consumeToken(b, "mwait");
        if (!r) r = consumeToken(b, "pavgusb");
        if (!r) r = consumeToken(b, "pf2id");
        if (!r) r = consumeToken(b, "pfacc");
        if (!r) r = consumeToken(b, "pfadd");
        if (!r) r = consumeToken(b, "pfcmpeq");
        if (!r) r = consumeToken(b, "pfcmpge");
        if (!r) r = consumeToken(b, "pfcmpgt");
        if (!r) r = consumeToken(b, "pfmax");
        if (!r) r = consumeToken(b, "pfmin");
        if (!r) r = consumeToken(b, "pfmul");
        if (!r) r = consumeToken(b, "pfnacc");
        if (!r) r = consumeToken(b, "pfpnacc");
        if (!r) r = consumeToken(b, "pfrcp");
        if (!r) r = consumeToken(b, "pfrcpit1");
        if (!r) r = consumeToken(b, "pfrcpit2");
        if (!r) r = consumeToken(b, "pfrsqit1");
        if (!r) r = consumeToken(b, "pfrsqrt");
        if (!r) r = consumeToken(b, "pfsub");
        if (!r) r = consumeToken(b, "pfsubr");
        if (!r) r = consumeToken(b, "pi2fd");
        if (!r) r = consumeToken(b, "pmulhrw");
        if (!r) r = consumeToken(b, "pswapd");
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // AsmExp
    public static boolean Operand(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Operand")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, OPERAND, "<operand>");
        r = AsmExp(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // Operand [',' Operands]
    public static boolean Operands(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Operands")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, OPERANDS, "<operands>");
        r = Operand(b, l + 1);
        r = r && Operands_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // [',' Operands]
    private static boolean Operands_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Operands_1")) return false;
        Operands_1_0(b, l + 1);
        return true;
    }

    // ',' Operands
    private static boolean Operands_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Operands_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && Operands(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }
}
