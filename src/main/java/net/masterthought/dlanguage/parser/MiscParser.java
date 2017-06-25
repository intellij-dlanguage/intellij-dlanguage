package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 6/25/2017.
 */
public class MiscParser {
    /* ********************************************************** */
    // '[' ArrayMemberInitializations? ','? ']'
    public static boolean ArrayInitializer(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ArrayInitializer")) return false;
        if (!nextTokenIs(b, OP_BRACKET_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BRACKET_LEFT);
        r = r && ArrayInitializer_1(b, l + 1);
        r = r && ArrayInitializer_2(b, l + 1);
        r = r && consumeToken(b, OP_BRACKET_RIGHT);
        exit_section_(b, m, ARRAY_INITIALIZER, r);
        return r;
    }

    // ArrayMemberInitializations?
    private static boolean ArrayInitializer_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ArrayInitializer_1")) return false;
        ArrayMemberInitializations(b, l + 1);
        return true;
    }

    // ','?
    private static boolean ArrayInitializer_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ArrayInitializer_2")) return false;
        consumeToken(b, OP_COMMA);
        return true;
    }

    /* ********************************************************** */
    // NonVoidInitializer [':' NonVoidInitializer]
    public static boolean ArrayMemberInitialization(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ArrayMemberInitialization")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ARRAY_MEMBER_INITIALIZATION, "<array member initialization>");
        r = VariableDeclarationParser.NonVoidInitializer(b, l + 1);
        r = r && ArrayMemberInitialization_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // [':' NonVoidInitializer]
    private static boolean ArrayMemberInitialization_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ArrayMemberInitialization_1")) return false;
        ArrayMemberInitialization_1_0(b, l + 1);
        return true;
    }

    // ':' NonVoidInitializer
    private static boolean ArrayMemberInitialization_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ArrayMemberInitialization_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COLON);
        r = r && VariableDeclarationParser.NonVoidInitializer(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // ArrayMemberInitialization (',' ArrayMemberInitialization)*
    public static boolean ArrayMemberInitializations(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ArrayMemberInitializations")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ARRAY_MEMBER_INITIALIZATIONS, "<array member initializations>");
        r = ArrayMemberInitialization(b, l + 1);
        r = r && ArrayMemberInitializations_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (',' ArrayMemberInitialization)*
    private static boolean ArrayMemberInitializations_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ArrayMemberInitializations_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!ArrayMemberInitializations_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "ArrayMemberInitializations_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    // ',' ArrayMemberInitialization
    private static boolean ArrayMemberInitializations_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ArrayMemberInitializations_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && ArrayMemberInitialization(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // Identifier ['.' DotIdentifier]
    public static boolean DotIdentifier(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DotIdentifier")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        r = r && DotIdentifier_1(b, l + 1);
        exit_section_(b, m, DOT_IDENTIFIER, r);
        return r;
    }

    // ['.' DotIdentifier]
    private static boolean DotIdentifier_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DotIdentifier_1")) return false;
        DotIdentifier_1_0(b, l + 1);
        return true;
    }

    // '.' DotIdentifier
    private static boolean DotIdentifier_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DotIdentifier_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_DOT);
        r = r && DotIdentifier(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AssignExpression
    public static boolean FirstExp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FirstExp")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FIRST_EXP, "<first exp>");
        r = ExpressionParser.AssignExpression(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // Expression
    public static boolean Increment(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Increment")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, INCREMENT, "<increment>");
        r = ExpressionParser.Expression(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // Statement
    //    | ';'
    public static boolean Initialize(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Initialize")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, INITIALIZE, "<initialize>");
        r = StatementParser.Statement(b, l + 1);
        if (!r) r = consumeToken(b, OP_SCOLON);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // BasicType
    public static boolean Interface(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Interface")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, INTERFACE, "<interface>");
        r = TypeParser.BasicType(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // AssignExpression ':' AssignExpression
    public static boolean KeyValuePair(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "KeyValuePair")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, KEY_VALUE_PAIR, "<key value pair>");
        r = ExpressionParser.AssignExpression(b, l + 1);
        r = r && consumeToken(b, OP_COLON);
        r = r && ExpressionParser.AssignExpression(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // KeyValuePair ((',') KeyValuePair)*
    public static boolean KeyValuePairs(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "KeyValuePairs")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, KEY_VALUE_PAIRS, "<key value pairs>");
        r = KeyValuePair(b, l + 1);
        r = r && KeyValuePairs_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ((',') KeyValuePair)*
    private static boolean KeyValuePairs_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "KeyValuePairs_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!KeyValuePairs_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "KeyValuePairs_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    // (',') KeyValuePair
    private static boolean KeyValuePairs_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "KeyValuePairs_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && KeyValuePair(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // '__FILE__'
    //     | '__MODULE__'
    //     | '__LINE__'
    //     | '__FUNCTION__'
    //     | '__PRETTY_FUNCTION__'
    public static boolean SpecialKeyword(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SpecialKeyword")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, SPECIAL_KEYWORD, "<special keyword>");
        r = consumeToken(b, KW___FILE__);
        if (!r) r = consumeToken(b, KW___MODULE__);
        if (!r) r = consumeToken(b, KW___LINE__);
        if (!r) r = consumeToken(b, KW___FUNCTION__);
        if (!r) r = consumeToken(b, KW___PRETTY_FUNCTION__);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // ('.')? SymbolTail
    public static boolean Symbol(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Symbol")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, SYMBOL, "<symbol>");
        r = Symbol_0(b, l + 1);
        r = r && SymbolTail(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ('.')?
    private static boolean Symbol_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Symbol_0")) return false;
        consumeToken(b, OP_DOT);
        return true;
    }

    /* ********************************************************** */
    // (Identifier | TemplateInstance) ('.' (Identifier | TemplateInstance))*
    public static boolean SymbolTail(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SymbolTail")) return false;
        if (!nextTokenIs(b, "<symbol tail>", OP_NOT, ID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, SYMBOL_TAIL, "<symbol tail>");
        r = SymbolTail_0(b, l + 1);
        r = r && SymbolTail_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // Identifier | TemplateInstance
    private static boolean SymbolTail_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SymbolTail_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        if (!r) r = TemplateInstance(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('.' (Identifier | TemplateInstance))*
    private static boolean SymbolTail_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SymbolTail_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!SymbolTail_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "SymbolTail_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    // '.' (Identifier | TemplateInstance)
    private static boolean SymbolTail_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SymbolTail_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_DOT);
        r = r && SymbolTail_1_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Identifier | TemplateInstance
    private static boolean SymbolTail_1_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SymbolTail_1_0_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        if (!r) r = TemplateInstance(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // Identifier? TemplateArguments
    public static boolean TemplateInstance(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateInstance")) return false;
        if (!nextTokenIs(b, "<template instance>", OP_NOT, ID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TEMPLATE_INSTANCE, "<template instance>");
        r = TemplateInstance_0(b, l + 1);
        r = r && TemplateArgumentParser.TemplateArguments(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // Identifier?
    private static boolean TemplateInstance_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateInstance_0")) return false;
        BaseRulesParser.Identifier(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // Expression
    public static boolean Test(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Test")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TEST, "<test>");
        r = ExpressionParser.Expression(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // 'void'
    public static boolean VoidInitializer(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VoidInitializer")) return false;
        if (!nextTokenIs(b, KW_VOID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_VOID);
        exit_section_(b, m, VOID_INITIALIZER, r);
        return r;
    }
}
