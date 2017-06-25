package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 6/25/2017.
 */
public class VariableDeclarationParser {
    /* ********************************************************** */
    // AliasDeclarationSingle
    //   | 'alias' AliasDeclarationX ';'
    public static boolean AliasDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclaration")) return false;
        if (!nextTokenIs(b, KW_ALIAS)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AliasDeclarationSingle(b, l + 1);
        if (!r) r = AliasDeclaration_1(b, l + 1);
        exit_section_(b, m, ALIAS_DECLARATION, r);
        return r;
    }

    // 'alias' AliasDeclarationX ';'
    private static boolean AliasDeclaration_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclaration_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_ALIAS);
        r = r && AliasDeclarationX(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'alias' Identifier '=' Type TemplateArguments? ';'
    //     | 'alias' Identifier '=' Initializer ';'
    //     | 'alias' StorageClasses? BasicType (FuncDeclarator|Declarator) ';'
    public static boolean AliasDeclarationSingle(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclarationSingle")) return false;
        if (!nextTokenIs(b, KW_ALIAS)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AliasDeclarationSingle_0(b, l + 1);
        if (!r) r = AliasDeclarationSingle_1(b, l + 1);
        if (!r) r = AliasDeclarationSingle_2(b, l + 1);
        exit_section_(b, m, ALIAS_DECLARATION_SINGLE, r);
        return r;
    }

    // 'alias' Identifier '=' Type TemplateArguments? ';'
    private static boolean AliasDeclarationSingle_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclarationSingle_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_ALIAS);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_EQ);
        r = r && TypeParser.Type(b, l + 1);
        r = r && AliasDeclarationSingle_0_4(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // TemplateArguments?
    private static boolean AliasDeclarationSingle_0_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclarationSingle_0_4")) return false;
        TemplateArgumentParser.TemplateArguments(b, l + 1);
        return true;
    }

    // 'alias' Identifier '=' Initializer ';'
    private static boolean AliasDeclarationSingle_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclarationSingle_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_ALIAS);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_EQ);
        r = r && Initializer(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'alias' StorageClasses? BasicType (FuncDeclarator|Declarator) ';'
    private static boolean AliasDeclarationSingle_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclarationSingle_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_ALIAS);
        r = r && AliasDeclarationSingle_2_1(b, l + 1);
        r = r && TypeParser.BasicType(b, l + 1);
        r = r && AliasDeclarationSingle_2_3(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // StorageClasses?
    private static boolean AliasDeclarationSingle_2_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclarationSingle_2_1")) return false;
        StorageClasses(b, l + 1);
        return true;
    }

    // FuncDeclarator|Declarator
    private static boolean AliasDeclarationSingle_2_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclarationSingle_2_3")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = DeclarationParser.FuncDeclarator(b, l + 1);
        if (!r) r = Declarator(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AliasDeclarationY [',' AliasDeclarationX]
    public static boolean AliasDeclarationX(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclarationX")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AliasDeclarationY(b, l + 1);
        r = r && AliasDeclarationX_1(b, l + 1);
        exit_section_(b, m, ALIAS_DECLARATION_X, r);
        return r;
    }

    // [',' AliasDeclarationX]
    private static boolean AliasDeclarationX_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclarationX_1")) return false;
        AliasDeclarationX_1_0(b, l + 1);
        return true;
    }

    // ',' AliasDeclarationX
    private static boolean AliasDeclarationX_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclarationX_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && AliasDeclarationX(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // Identifier TemplateParameters? '=' ( StorageClasses? Type | FunctionLiteral)
    public static boolean AliasDeclarationY(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclarationY")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        r = r && AliasDeclarationY_1(b, l + 1);
        r = r && consumeToken(b, OP_EQ);
        r = r && AliasDeclarationY_3(b, l + 1);
        exit_section_(b, m, ALIAS_DECLARATION_Y, r);
        return r;
    }

    // TemplateParameters?
    private static boolean AliasDeclarationY_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclarationY_1")) return false;
        TemplateParameterParser.TemplateParameters(b, l + 1);
        return true;
    }

    // StorageClasses? Type | FunctionLiteral
    private static boolean AliasDeclarationY_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclarationY_3")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AliasDeclarationY_3_0(b, l + 1);
        if (!r) r = ExpressionParser.FunctionLiteral(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // StorageClasses? Type
    private static boolean AliasDeclarationY_3_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclarationY_3_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AliasDeclarationY_3_0_0(b, l + 1);
        r = r && TypeParser.Type(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // StorageClasses?
    private static boolean AliasDeclarationY_3_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasDeclarationY_3_0_0")) return false;
        StorageClasses(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // BasicType2? Identifier AltDeclaratorSuffixes
    //     | BasicType2? '(' AltDeclaratorX ')' AltFuncDeclaratorSuffix? AltDeclaratorSuffixes?
    public static boolean AltDeclarator(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclarator")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ALT_DECLARATOR, "<alt declarator>");
        r = AltDeclarator_0(b, l + 1);
        if (!r) r = AltDeclarator_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // BasicType2? Identifier AltDeclaratorSuffixes
    private static boolean AltDeclarator_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclarator_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AltDeclarator_0_0(b, l + 1);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && AltDeclaratorSuffixes(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // BasicType2?
    private static boolean AltDeclarator_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclarator_0_0")) return false;
        TypeParser.BasicType2(b, l + 1);
        return true;
    }

    // BasicType2? '(' AltDeclaratorX ')' AltFuncDeclaratorSuffix? AltDeclaratorSuffixes?
    private static boolean AltDeclarator_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclarator_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AltDeclarator_1_0(b, l + 1);
        r = r && consumeToken(b, OP_PAR_LEFT);
        r = r && AltDeclaratorX(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        r = r && AltDeclarator_1_4(b, l + 1);
        r = r && AltDeclarator_1_5(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // BasicType2?
    private static boolean AltDeclarator_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclarator_1_0")) return false;
        TypeParser.BasicType2(b, l + 1);
        return true;
    }

    // AltFuncDeclaratorSuffix?
    private static boolean AltDeclarator_1_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclarator_1_4")) return false;
        AltFuncDeclaratorSuffix(b, l + 1);
        return true;
    }

    // AltDeclaratorSuffixes?
    private static boolean AltDeclarator_1_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclarator_1_5")) return false;
        AltDeclaratorSuffixes(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // BasicType2? Identifier AltDeclaratorSuffixes? ('=' Initializer)?
    public static boolean AltDeclaratorIdentifier(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclaratorIdentifier")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ALT_DECLARATOR_IDENTIFIER, "<alt declarator identifier>");
        r = AltDeclaratorIdentifier_0(b, l + 1);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && AltDeclaratorIdentifier_2(b, l + 1);
        r = r && AltDeclaratorIdentifier_3(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // BasicType2?
    private static boolean AltDeclaratorIdentifier_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclaratorIdentifier_0")) return false;
        TypeParser.BasicType2(b, l + 1);
        return true;
    }

    // AltDeclaratorSuffixes?
    private static boolean AltDeclaratorIdentifier_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclaratorIdentifier_2")) return false;
        AltDeclaratorSuffixes(b, l + 1);
        return true;
    }

    // ('=' Initializer)?
    private static boolean AltDeclaratorIdentifier_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclaratorIdentifier_3")) return false;
        AltDeclaratorIdentifier_3_0(b, l + 1);
        return true;
    }

    // '=' Initializer
    private static boolean AltDeclaratorIdentifier_3_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclaratorIdentifier_3_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_EQ);
        r = r && Initializer(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // '[' (AssignExpression | Type)? ']'
    public static boolean AltDeclaratorSuffix(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclaratorSuffix")) return false;
        if (!nextTokenIs(b, OP_BRACKET_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BRACKET_LEFT);
        r = r && AltDeclaratorSuffix_1(b, l + 1);
        r = r && consumeToken(b, OP_BRACKET_RIGHT);
        exit_section_(b, m, ALT_DECLARATOR_SUFFIX, r);
        return r;
    }

    // (AssignExpression | Type)?
    private static boolean AltDeclaratorSuffix_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclaratorSuffix_1")) return false;
        AltDeclaratorSuffix_1_0(b, l + 1);
        return true;
    }

    // AssignExpression | Type
    private static boolean AltDeclaratorSuffix_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclaratorSuffix_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ExpressionParser.AssignExpression(b, l + 1);
        if (!r) r = TypeParser.Type(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AltDeclaratorSuffix AltDeclaratorSuffix*
    public static boolean AltDeclaratorSuffixes(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclaratorSuffixes")) return false;
        if (!nextTokenIs(b, OP_BRACKET_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AltDeclaratorSuffix(b, l + 1);
        r = r && AltDeclaratorSuffixes_1(b, l + 1);
        exit_section_(b, m, ALT_DECLARATOR_SUFFIXES, r);
        return r;
    }

    // AltDeclaratorSuffix*
    private static boolean AltDeclaratorSuffixes_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclaratorSuffixes_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!AltDeclaratorSuffix(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "AltDeclaratorSuffixes_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    /* ********************************************************** */
    // BasicType2? Identifier AltFuncDeclaratorSuffix?
    //     | AltDeclarator
    public static boolean AltDeclaratorX(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclaratorX")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ALT_DECLARATOR_X, "<alt declarator x>");
        r = AltDeclaratorX_0(b, l + 1);
        if (!r) r = AltDeclarator(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // BasicType2? Identifier AltFuncDeclaratorSuffix?
    private static boolean AltDeclaratorX_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclaratorX_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AltDeclaratorX_0_0(b, l + 1);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && AltDeclaratorX_0_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // BasicType2?
    private static boolean AltDeclaratorX_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclaratorX_0_0")) return false;
        TypeParser.BasicType2(b, l + 1);
        return true;
    }

    // AltFuncDeclaratorSuffix?
    private static boolean AltDeclaratorX_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltDeclaratorX_0_2")) return false;
        AltFuncDeclaratorSuffix(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // Parameters MemberFunctionAttributes?
    public static boolean AltFuncDeclaratorSuffix(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltFuncDeclaratorSuffix")) return false;
        if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ParameterParser.Parameters(b, l + 1);
        r = r && AltFuncDeclaratorSuffix_1(b, l + 1);
        exit_section_(b, m, ALT_FUNC_DECLARATOR_SUFFIX, r);
        return r;
    }

    // MemberFunctionAttributes?
    private static boolean AltFuncDeclaratorSuffix_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AltFuncDeclaratorSuffix_1")) return false;
        DeclarationParser.MemberFunctionAttributes(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // StorageClasses? AutoDeclarationX ';'
    public static boolean AutoDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AutoDeclaration")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, AUTO_DECLARATION, "<auto declaration>");
        r = AutoDeclaration_0(b, l + 1);
        r = r && AutoDeclarationX(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // StorageClasses?
    private static boolean AutoDeclaration_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AutoDeclaration_0")) return false;
        StorageClasses(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // AutoDeclarationY [',' AutoDeclarationX]
    public static boolean AutoDeclarationX(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AutoDeclarationX")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AutoDeclarationY(b, l + 1);
        r = r && AutoDeclarationX_1(b, l + 1);
        exit_section_(b, m, AUTO_DECLARATION_X, r);
        return r;
    }

    // [',' AutoDeclarationX]
    private static boolean AutoDeclarationX_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AutoDeclarationX_1")) return false;
        AutoDeclarationX_1_0(b, l + 1);
        return true;
    }

    // ',' AutoDeclarationX
    private static boolean AutoDeclarationX_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AutoDeclarationX_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && AutoDeclarationX(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // Identifier TemplateParameters? '=' Initializer
    public static boolean AutoDeclarationY(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AutoDeclarationY")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        r = r && AutoDeclarationY_1(b, l + 1);
        r = r && consumeToken(b, OP_EQ);
        r = r && Initializer(b, l + 1);
        exit_section_(b, m, AUTO_DECLARATION_Y, r);
        return r;
    }

    // TemplateParameters?
    private static boolean AutoDeclarationY_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AutoDeclarationY_1")) return false;
        TemplateParameterParser.TemplateParameters(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // VarDeclarator
    //     | AltDeclarator
    public static boolean Declarator(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Declarator")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, DECLARATOR, "<declarator>");
        r = VarDeclarator(b, l + 1);
        if (!r) r = AltDeclarator(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // VarDeclaratorIdentifier
    //     | AltDeclaratorIdentifier
    public static boolean DeclaratorIdentifier(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclaratorIdentifier")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, DECLARATOR_IDENTIFIER, "<declarator identifier>");
        r = VarDeclaratorIdentifier(b, l + 1);
        if (!r) r = AltDeclaratorIdentifier(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // DeclaratorIdentifier [',' DeclaratorIdentifierList]
    public static boolean DeclaratorIdentifierList(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclaratorIdentifierList")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, DECLARATOR_IDENTIFIER_LIST, "<declarator identifier list>");
        r = DeclaratorIdentifier(b, l + 1);
        r = r && DeclaratorIdentifierList_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // [',' DeclaratorIdentifierList]
    private static boolean DeclaratorIdentifierList_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclaratorIdentifierList_1")) return false;
        DeclaratorIdentifierList_1_0(b, l + 1);
        return true;
    }

    // ',' DeclaratorIdentifierList
    private static boolean DeclaratorIdentifierList_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclaratorIdentifierList_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && DeclaratorIdentifierList(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AltDeclarator ('=' Initializer)?
    //     | VarDeclarator (TemplateParameters? '=' Initializer)?
    public static boolean DeclaratorInitializer(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclaratorInitializer")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, DECLARATOR_INITIALIZER, "<declarator initializer>");
        r = DeclaratorInitializer_0(b, l + 1);
        if (!r) r = DeclaratorInitializer_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // AltDeclarator ('=' Initializer)?
    private static boolean DeclaratorInitializer_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclaratorInitializer_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AltDeclarator(b, l + 1);
        r = r && DeclaratorInitializer_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('=' Initializer)?
    private static boolean DeclaratorInitializer_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclaratorInitializer_0_1")) return false;
        DeclaratorInitializer_0_1_0(b, l + 1);
        return true;
    }

    // '=' Initializer
    private static boolean DeclaratorInitializer_0_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclaratorInitializer_0_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_EQ);
        r = r && Initializer(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // VarDeclarator (TemplateParameters? '=' Initializer)?
    private static boolean DeclaratorInitializer_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclaratorInitializer_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = VarDeclarator(b, l + 1);
        r = r && DeclaratorInitializer_1_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // (TemplateParameters? '=' Initializer)?
    private static boolean DeclaratorInitializer_1_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclaratorInitializer_1_1")) return false;
        DeclaratorInitializer_1_1_0(b, l + 1);
        return true;
    }

    // TemplateParameters? '=' Initializer
    private static boolean DeclaratorInitializer_1_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclaratorInitializer_1_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = DeclaratorInitializer_1_1_0_0(b, l + 1);
        r = r && consumeToken(b, OP_EQ);
        r = r && Initializer(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // TemplateParameters?
    private static boolean DeclaratorInitializer_1_1_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclaratorInitializer_1_1_0_0")) return false;
        TemplateParameterParser.TemplateParameters(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // DeclaratorInitializer (',' DeclaratorIdentifierList)?
    public static boolean Declarators(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Declarators")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, DECLARATORS, "<declarators>");
        r = DeclaratorInitializer(b, l + 1);
        r = r && Declarators_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (',' DeclaratorIdentifierList)?
    private static boolean Declarators_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Declarators_1")) return false;
        Declarators_1_0(b, l + 1);
        return true;
    }

    // ',' DeclaratorIdentifierList
    private static boolean Declarators_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Declarators_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && DeclaratorIdentifierList(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // InOutX InOutX*
    public static boolean InOut(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InOut")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, IN_OUT, "<in out>");
        r = InOutX(b, l + 1);
        r = r && InOut_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // InOutX*
    private static boolean InOut_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InOut_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!InOutX(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "InOut_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    /* ********************************************************** */
    // 'auto'
    //     | TypeCtor
    //     | 'final'
    //     | 'in'
    //     | 'lazy'
    //     | 'out'
    //     | 'ref'
    //     | 'scope'
    //     | ('return' 'ref')
    //     | 'return'
    public static boolean InOutX(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InOutX")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, IN_OUT_X, "<in out x>");
        r = consumeToken(b, KW_AUTO);
        if (!r) r = TypeParser.TypeCtor(b, l + 1);
        if (!r) r = consumeToken(b, KW_FINAL);
        if (!r) r = consumeToken(b, KW_IN);
        if (!r) r = consumeToken(b, KW_LAZY);
        if (!r) r = consumeToken(b, KW_OUT);
        if (!r) r = consumeToken(b, KW_REF);
        if (!r) r = consumeToken(b, KW_SCOPE);
        if (!r) r = InOutX_8(b, l + 1);
        if (!r) r = consumeToken(b, KW_RETURN);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // 'return' 'ref'
    private static boolean InOutX_8(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InOutX_8")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_RETURN, KW_REF);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // VoidInitializer
    //     | NonVoidInitializer
    public static boolean Initializer(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Initializer")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, INITIALIZER, "<initializer>");
        r = MiscParser.VoidInitializer(b, l + 1);
        if (!r) r = NonVoidInitializer(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // AssignExpression
    //     | ArrayInitializer
    //     | StructInitializer
    public static boolean NonVoidInitializer(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NonVoidInitializer")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, NON_VOID_INITIALIZER, "<non void initializer>");
        r = ExpressionParser.AssignExpression(b, l + 1);
        if (!r) r = MiscParser.ArrayInitializer(b, l + 1);
        if (!r) r = StructInitializer(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // LinkageAttribute
    //     | AlignAttribute
    //     | 'deprecated'
    //     | 'enum'
    //     | 'static'
    //     | 'extern'
    //     | 'abstract'
    //     | 'final'
    //     | 'override'
    //     | 'synchronized'
    //     | 'auto'
    //     | 'scope'
    //     | 'const'
    //     | 'immutable'
    //     | 'inout'
    //     | 'shared'
    //     | '__gshared'
    //     | Property
    //     | 'nothrow'
    //     | 'pure'
    //     | 'ref'
    public static boolean StorageClass(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StorageClass")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, STORAGE_CLASS, "<storage class>");
        r = AttributesParser.LinkageAttribute(b, l + 1);
        if (!r) r = AttributesParser.AlignAttribute(b, l + 1);
        if (!r) r = consumeToken(b, KW_DEPRECATED);
        if (!r) r = consumeToken(b, KW_ENUM);
        if (!r) r = consumeToken(b, KW_STATIC);
        if (!r) r = consumeToken(b, KW_EXTERN);
        if (!r) r = consumeToken(b, KW_ABSTRACT);
        if (!r) r = consumeToken(b, KW_FINAL);
        if (!r) r = consumeToken(b, KW_OVERRIDE);
        if (!r) r = consumeToken(b, KW_SYNCHRONIZED);
        if (!r) r = consumeToken(b, KW_AUTO);
        if (!r) r = consumeToken(b, KW_SCOPE);
        if (!r) r = consumeToken(b, KW_CONST);
        if (!r) r = consumeToken(b, KW_IMMUTABLE);
        if (!r) r = consumeToken(b, KW_INOUT);
        if (!r) r = consumeToken(b, KW_SHARED);
        if (!r) r = consumeToken(b, KW___GSHARED);
        if (!r) r = AttributesParser.Property(b, l + 1);
        if (!r) r = consumeToken(b, KW_NOTHROW);
        if (!r) r = consumeToken(b, KW_PURE);
        if (!r) r = consumeToken(b, KW_REF);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // StorageClass StorageClass*
    public static boolean StorageClasses(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StorageClasses")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, STORAGE_CLASSES, "<storage classes>");
        r = StorageClass(b, l + 1);
        r = r && StorageClasses_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // StorageClass*
    private static boolean StorageClasses_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StorageClasses_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!StorageClass(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "StorageClasses_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    /* ********************************************************** */
    // '{' StructMemberInitializers? ','? '}'
    public static boolean StructInitializer(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StructInitializer")) return false;
        if (!nextTokenIs(b, OP_BRACES_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BRACES_LEFT);
        r = r && StructInitializer_1(b, l + 1);
        r = r && StructInitializer_2(b, l + 1);
        r = r && consumeToken(b, OP_BRACES_RIGHT);
        exit_section_(b, m, STRUCT_INITIALIZER, r);
        return r;
    }

    // StructMemberInitializers?
    private static boolean StructInitializer_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StructInitializer_1")) return false;
        StructMemberInitializers(b, l + 1);
        return true;
    }

    // ','?
    private static boolean StructInitializer_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StructInitializer_2")) return false;
        consumeToken(b, OP_COMMA);
        return true;
    }

    /* ********************************************************** */
    // NonVoidInitializer [':' NonVoidInitializer]
    public static boolean StructMemberInitializer(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StructMemberInitializer")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, STRUCT_MEMBER_INITIALIZER, "<struct member initializer>");
        r = NonVoidInitializer(b, l + 1);
        r = r && StructMemberInitializer_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // [':' NonVoidInitializer]
    private static boolean StructMemberInitializer_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StructMemberInitializer_1")) return false;
        StructMemberInitializer_1_0(b, l + 1);
        return true;
    }

    // ':' NonVoidInitializer
    private static boolean StructMemberInitializer_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StructMemberInitializer_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COLON);
        r = r && NonVoidInitializer(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // StructMemberInitializer (',' StructMemberInitializer)*
    public static boolean StructMemberInitializers(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StructMemberInitializers")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, STRUCT_MEMBER_INITIALIZERS, "<struct member initializers>");
        r = StructMemberInitializer(b, l + 1);
        r = r && StructMemberInitializers_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (',' StructMemberInitializer)*
    private static boolean StructMemberInitializers_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StructMemberInitializers_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!StructMemberInitializers_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "StructMemberInitializers_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    // ',' StructMemberInitializer
    private static boolean StructMemberInitializers_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StructMemberInitializers_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && StructMemberInitializer(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // StorageClasses BasicType Declarators ';'
    //     | StorageClasses? BasicType? Declarators ';'
    //     | AutoDeclaration
    public static boolean VarDeclarations(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VarDeclarations")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, VAR_DECLARATIONS, "<var declarations>");
        r = VarDeclarations_0(b, l + 1);
        if (!r) r = VarDeclarations_1(b, l + 1);
        if (!r) r = AutoDeclaration(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // StorageClasses BasicType Declarators ';'
    private static boolean VarDeclarations_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VarDeclarations_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = StorageClasses(b, l + 1);
        r = r && TypeParser.BasicType(b, l + 1);
        r = r && Declarators(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // StorageClasses? BasicType? Declarators ';'
    private static boolean VarDeclarations_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VarDeclarations_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = VarDeclarations_1_0(b, l + 1);
        r = r && VarDeclarations_1_1(b, l + 1);
        r = r && Declarators(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // StorageClasses?
    private static boolean VarDeclarations_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VarDeclarations_1_0")) return false;
        StorageClasses(b, l + 1);
        return true;
    }

    // BasicType?
    private static boolean VarDeclarations_1_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VarDeclarations_1_1")) return false;
        TypeParser.BasicType(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // BasicType2? Identifier
    public static boolean VarDeclarator(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VarDeclarator")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, VAR_DECLARATOR, "<var declarator>");
        r = VarDeclarator_0(b, l + 1);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // BasicType2?
    private static boolean VarDeclarator_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VarDeclarator_0")) return false;
        TypeParser.BasicType2(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // Identifier TemplateParameters? '=' Initializer
    public static boolean VarDeclaratorIdentifier(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VarDeclaratorIdentifier")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        r = r && VarDeclaratorIdentifier_1(b, l + 1);
        r = r && consumeToken(b, OP_EQ);
        r = r && Initializer(b, l + 1);
        exit_section_(b, m, VAR_DECLARATOR_IDENTIFIER, r);
        return r;
    }

    // TemplateParameters?
    private static boolean VarDeclaratorIdentifier_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VarDeclaratorIdentifier_1")) return false;
        TemplateParameterParser.TemplateParameters(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // (('enum'|'auto'|Type) FuncDeclarator '=' AssignExpression ';')
    //     | (('enum'|'auto'|Type) BasicType FuncDeclarator '=' AssignExpression ';')
    public static boolean VarFuncDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VarFuncDeclaration")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, VAR_FUNC_DECLARATION, "<var func declaration>");
        r = VarFuncDeclaration_0(b, l + 1);
        if (!r) r = VarFuncDeclaration_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ('enum'|'auto'|Type) FuncDeclarator '=' AssignExpression ';'
    private static boolean VarFuncDeclaration_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VarFuncDeclaration_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = VarFuncDeclaration_0_0(b, l + 1);
        r = r && DeclarationParser.FuncDeclarator(b, l + 1);
        r = r && consumeToken(b, OP_EQ);
        r = r && ExpressionParser.AssignExpression(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'enum'|'auto'|Type
    private static boolean VarFuncDeclaration_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VarFuncDeclaration_0_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_ENUM);
        if (!r) r = consumeToken(b, KW_AUTO);
        if (!r) r = TypeParser.Type(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('enum'|'auto'|Type) BasicType FuncDeclarator '=' AssignExpression ';'
    private static boolean VarFuncDeclaration_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VarFuncDeclaration_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = VarFuncDeclaration_1_0(b, l + 1);
        r = r && TypeParser.BasicType(b, l + 1);
        r = r && DeclarationParser.FuncDeclarator(b, l + 1);
        r = r && consumeToken(b, OP_EQ);
        r = r && ExpressionParser.AssignExpression(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'enum'|'auto'|Type
    private static boolean VarFuncDeclaration_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VarFuncDeclaration_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_ENUM);
        if (!r) r = consumeToken(b, KW_AUTO);
        if (!r) r = TypeParser.Type(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }
}
