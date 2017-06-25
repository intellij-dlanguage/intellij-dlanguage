package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 6/25/2017.
 */
public class DeclarationParser {
    /* ********************************************************** */
    // '{' DeclDefs? '}'
    public static boolean AggregateBody(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AggregateBody")) return false;
        if (!nextTokenIs(b, OP_BRACES_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BRACES_LEFT);
        r = r && AggregateBody_1(b, l + 1);
        r = r && consumeToken(b, OP_BRACES_RIGHT);
        exit_section_(b, m, AGGREGATE_BODY, r);
        return r;
    }

    // DeclDefs?
    private static boolean AggregateBody_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AggregateBody_1")) return false;
        DeclDefParser.DeclDefs(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // ClassDeclaration
    //     | InterfaceDeclaration
    //     | StructDeclaration
    //     | UnionDeclaration
    public static boolean AggregateDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AggregateDeclaration")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, AGGREGATE_DECLARATION, "<aggregate declaration>");
        r = ClassDeclaration(b, l + 1);
        if (!r) r = InterfaceDeclaration(b, l + 1);
        if (!r) r = StructDeclaration(b, l + 1);
        if (!r) r = UnionDeclaration(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // 'union' AggregateBody
    static boolean AnonUnionDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AnonUnionDeclaration")) return false;
        if (!nextTokenIs(b, KW_UNION)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_);
        r = consumeToken(b, KW_UNION);
        p = r; // pin = 1
        r = r && AggregateBody(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'enum' (':' EnumBaseType)? ('{' EnumMembers ','? '}')?
    public static boolean AnonymousEnumDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AnonymousEnumDeclaration")) return false;
        if (!nextTokenIs(b, KW_ENUM)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_ENUM);
        r = r && AnonymousEnumDeclaration_1(b, l + 1);
        r = r && AnonymousEnumDeclaration_2(b, l + 1);
        exit_section_(b, m, ANONYMOUS_ENUM_DECLARATION, r);
        return r;
    }

    // (':' EnumBaseType)?
    private static boolean AnonymousEnumDeclaration_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AnonymousEnumDeclaration_1")) return false;
        AnonymousEnumDeclaration_1_0(b, l + 1);
        return true;
    }

    // ':' EnumBaseType
    private static boolean AnonymousEnumDeclaration_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AnonymousEnumDeclaration_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COLON);
        r = r && EnumBaseType(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('{' EnumMembers ','? '}')?
    private static boolean AnonymousEnumDeclaration_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AnonymousEnumDeclaration_2")) return false;
        AnonymousEnumDeclaration_2_0(b, l + 1);
        return true;
    }

    // '{' EnumMembers ','? '}'
    private static boolean AnonymousEnumDeclaration_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AnonymousEnumDeclaration_2_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BRACES_LEFT);
        r = r && EnumMembers(b, l + 1);
        r = r && AnonymousEnumDeclaration_2_0_2(b, l + 1);
        r = r && consumeToken(b, OP_BRACES_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // ','?
    private static boolean AnonymousEnumDeclaration_2_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AnonymousEnumDeclaration_2_0_2")) return false;
        consumeToken(b, OP_COMMA);
        return true;
    }

    /* ********************************************************** */
    // StorageClasses Identifier FuncDeclaratorSuffix FunctionBody?
    static boolean AutoFuncDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AutoFuncDeclaration")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = VariableDeclarationParser.StorageClasses(b, l + 1);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && FuncDeclaratorSuffix(b, l + 1);
        r = r && AutoFuncDeclaration_3(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // FunctionBody?
    private static boolean AutoFuncDeclaration_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AutoFuncDeclaration_3")) return false;
        FunctionBody(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // ':' SuperClass (',' Interfaces)?
    //     | ':' Interfaces
    public static boolean BaseClassList(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BaseClassList")) return false;
        if (!nextTokenIs(b, OP_COLON)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseClassList_0(b, l + 1);
        if (!r) r = BaseClassList_1(b, l + 1);
        exit_section_(b, m, BASE_CLASS_LIST, r);
        return r;
    }

    // ':' SuperClass (',' Interfaces)?
    private static boolean BaseClassList_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BaseClassList_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COLON);
        r = r && SuperClass(b, l + 1);
        r = r && BaseClassList_0_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // (',' Interfaces)?
    private static boolean BaseClassList_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BaseClassList_0_2")) return false;
        BaseClassList_0_2_0(b, l + 1);
        return true;
    }

    // ',' Interfaces
    private static boolean BaseClassList_0_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BaseClassList_0_2_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && Interfaces(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ':' Interfaces
    private static boolean BaseClassList_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BaseClassList_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COLON);
        r = r && Interfaces(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // ':' Interfaces
    public static boolean BaseInterfaceList(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BaseInterfaceList")) return false;
        if (!nextTokenIs(b, OP_COLON)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COLON);
        r = r && Interfaces(b, l + 1);
        exit_section_(b, m, BASE_INTERFACE_LIST, r);
        return r;
    }

    /* ********************************************************** */
    // '(' ArgumentList? ')'
    public static boolean ClassArguments(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ClassArguments")) return false;
        if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && ClassArguments_1(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, CLASS_ARGUMENTS, r);
        return r;
    }

    // ArgumentList?
    private static boolean ClassArguments_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ClassArguments_1")) return false;
        ArgumentParser.ArgumentList(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'class' Identifier ';'
    //     | 'class' Identifier BaseClassList? AggregateBody
    //     | ClassTemplateDeclaration
    public static boolean ClassDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ClassDeclaration")) return false;
        if (!nextTokenIs(b, KW_CLASS)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ClassDeclaration_0(b, l + 1);
        if (!r) r = ClassDeclaration_1(b, l + 1);
        if (!r) r = ClassTemplateDeclaration(b, l + 1);
        exit_section_(b, m, CLASS_DECLARATION, r);
        return r;
    }

    // 'class' Identifier ';'
    private static boolean ClassDeclaration_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ClassDeclaration_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_CLASS);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'class' Identifier BaseClassList? AggregateBody
    private static boolean ClassDeclaration_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ClassDeclaration_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_CLASS);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && ClassDeclaration_1_2(b, l + 1);
        r = r && AggregateBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // BaseClassList?
    private static boolean ClassDeclaration_1_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ClassDeclaration_1_2")) return false;
        BaseClassList(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'class' Identifier TemplateParameters Constraint? BaseClassList? AggregateBody
    //     | 'class' Identifier TemplateParameters BaseClassList Constraint AggregateBody
    static boolean ClassTemplateDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ClassTemplateDeclaration")) return false;
        if (!nextTokenIs(b, KW_CLASS)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ClassTemplateDeclaration_0(b, l + 1);
        if (!r) r = ClassTemplateDeclaration_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'class' Identifier TemplateParameters Constraint? BaseClassList? AggregateBody
    private static boolean ClassTemplateDeclaration_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ClassTemplateDeclaration_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_CLASS);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && TemplateParameterParser.TemplateParameters(b, l + 1);
        r = r && ClassTemplateDeclaration_0_3(b, l + 1);
        r = r && ClassTemplateDeclaration_0_4(b, l + 1);
        r = r && AggregateBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Constraint?
    private static boolean ClassTemplateDeclaration_0_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ClassTemplateDeclaration_0_3")) return false;
        DeclDefParser.Constraint(b, l + 1);
        return true;
    }

    // BaseClassList?
    private static boolean ClassTemplateDeclaration_0_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ClassTemplateDeclaration_0_4")) return false;
        BaseClassList(b, l + 1);
        return true;
    }

    // 'class' Identifier TemplateParameters BaseClassList Constraint AggregateBody
    private static boolean ClassTemplateDeclaration_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ClassTemplateDeclaration_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_CLASS);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && TemplateParameterParser.TemplateParameters(b, l + 1);
        r = r && BaseClassList(b, l + 1);
        r = r && DeclDefParser.Constraint(b, l + 1);
        r = r && AggregateBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // VarFuncDeclaration
    //    | FuncDeclaration
    //    | EnumDeclaration
    //    | VarDeclarations //must come before alias decleration
    //    | AliasDeclaration
    //    | AggregateDeclaration
    //    | ImportDeclaration
    //    | TemplateDeclaration
    public static boolean Declaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Declaration")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, DECLARATION, "<declaration>");
        r = VariableDeclarationParser.VarFuncDeclaration(b, l + 1);
        if (!r) r = FuncDeclaration(b, l + 1);
        if (!r) r = EnumDeclaration(b, l + 1);
        if (!r) r = VariableDeclarationParser.VarDeclarations(b, l + 1);
        if (!r) r = VariableDeclarationParser.AliasDeclaration(b, l + 1);
        if (!r) r = AggregateDeclaration(b, l + 1);
        if (!r) r = DeclDefParser.ImportDeclaration(b, l + 1);
        if (!r) r = TemplateDeclaration(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // DeclDef
    //     | '{' DeclDefs? '}'
    //     {
    //     }
    public static boolean DeclarationBlock(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclarationBlock")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, DECLARATION_BLOCK, "<declaration block>");
        r = DeclDefParser.DeclDef(b, l + 1);
        if (!r) r = DeclarationBlock_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // '{' DeclDefs? '}'
    //     {
    //     }
    private static boolean DeclarationBlock_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclarationBlock_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BRACES_LEFT);
        r = r && DeclarationBlock_1_1(b, l + 1);
        r = r && consumeToken(b, OP_BRACES_RIGHT);
        r = r && DeclarationBlock_1_3(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // DeclDefs?
    private static boolean DeclarationBlock_1_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclarationBlock_1_1")) return false;
        DeclDefParser.DeclDefs(b, l + 1);
        return true;
    }

    // {
    //     }
    private static boolean DeclarationBlock_1_3(PsiBuilder b, int l) {
        return true;
    }

    /* ********************************************************** */
    // Type
    public static boolean EnumBaseType(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumBaseType")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ENUM_BASE_TYPE, "<enum base type>");
        r = TypeParser.Type(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // '{' EnumMembers ','? '}'
    public static boolean EnumBody(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumBody")) return false;
        if (!nextTokenIs(b, OP_BRACES_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BRACES_LEFT);
        r = r && EnumMembers(b, l + 1);
        r = r && EnumBody_2(b, l + 1);
        r = r && consumeToken(b, OP_BRACES_RIGHT);
        exit_section_(b, m, ENUM_BODY, r);
        return r;
    }

    // ','?
    private static boolean EnumBody_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumBody_2")) return false;
        consumeToken(b, OP_COMMA);
        return true;
    }

    /* ********************************************************** */
    // 'enum' Identifier (':' EnumBaseType)? EnumBody
    //     | AnonymousEnumDeclaration
    public static boolean EnumDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumDeclaration")) return false;
        if (!nextTokenIs(b, KW_ENUM)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = EnumDeclaration_0(b, l + 1);
        if (!r) r = AnonymousEnumDeclaration(b, l + 1);
        exit_section_(b, m, ENUM_DECLARATION, r);
        return r;
    }

    // 'enum' Identifier (':' EnumBaseType)? EnumBody
    private static boolean EnumDeclaration_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumDeclaration_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_ENUM);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && EnumDeclaration_0_2(b, l + 1);
        r = r && EnumBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // (':' EnumBaseType)?
    private static boolean EnumDeclaration_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumDeclaration_0_2")) return false;
        EnumDeclaration_0_2_0(b, l + 1);
        return true;
    }

    // ':' EnumBaseType
    private static boolean EnumDeclaration_0_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumDeclaration_0_2_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COLON);
        r = r && EnumBaseType(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // Identifier ('=' AssignExpression)? | Type Identifier '=' AssignExpression
    public static boolean EnumMember(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumMember")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ENUM_MEMBER, "<enum member>");
        r = EnumMember_0(b, l + 1);
        if (!r) r = EnumMember_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // Identifier ('=' AssignExpression)?
    private static boolean EnumMember_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumMember_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        r = r && EnumMember_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('=' AssignExpression)?
    private static boolean EnumMember_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumMember_0_1")) return false;
        EnumMember_0_1_0(b, l + 1);
        return true;
    }

    // '=' AssignExpression
    private static boolean EnumMember_0_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumMember_0_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_EQ);
        r = r && ExpressionParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Type Identifier '=' AssignExpression
    private static boolean EnumMember_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumMember_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = TypeParser.Type(b, l + 1);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_EQ);
        r = r && ExpressionParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // EnumMember (',' EnumMember)*
    public static boolean EnumMembers(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumMembers")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ENUM_MEMBERS, "<enum members>");
        r = EnumMember(b, l + 1);
        r = r && EnumMembers_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (',' EnumMember)*
    private static boolean EnumMembers_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumMembers_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!EnumMembers_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "EnumMembers_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    // ',' EnumMember
    private static boolean EnumMembers_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumMembers_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && EnumMember(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // StorageClasses? BasicType FuncDeclarator (FunctionBody |';' )?
    //     | AutoFuncDeclaration
    public static boolean FuncDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FuncDeclaration")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FUNC_DECLARATION, "<func declaration>");
        r = FuncDeclaration_0(b, l + 1);
        if (!r) r = AutoFuncDeclaration(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // StorageClasses? BasicType FuncDeclarator (FunctionBody |';' )?
    private static boolean FuncDeclaration_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FuncDeclaration_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = FuncDeclaration_0_0(b, l + 1);
        r = r && TypeParser.BasicType(b, l + 1);
        r = r && FuncDeclarator(b, l + 1);
        r = r && FuncDeclaration_0_3(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // StorageClasses?
    private static boolean FuncDeclaration_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FuncDeclaration_0_0")) return false;
        VariableDeclarationParser.StorageClasses(b, l + 1);
        return true;
    }

    // (FunctionBody |';' )?
    private static boolean FuncDeclaration_0_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FuncDeclaration_0_3")) return false;
        FuncDeclaration_0_3_0(b, l + 1);
        return true;
    }

    // FunctionBody |';'
    private static boolean FuncDeclaration_0_3_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FuncDeclaration_0_3_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = FunctionBody(b, l + 1);
        if (!r) r = consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // BasicType2? Identifier FuncDeclaratorSuffix
    static boolean FuncDeclarator(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FuncDeclarator")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = FuncDeclarator_0(b, l + 1);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && FuncDeclaratorSuffix(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // BasicType2?
    private static boolean FuncDeclarator_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FuncDeclarator_0")) return false;
        TypeParser.BasicType2(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // TemplateParameters? Parameters MemberFunctionAttributes? Constraint?
    //      | Parameters MemberFunctionAttributes?
    static boolean FuncDeclaratorSuffix(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FuncDeclaratorSuffix")) return false;
        if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = FuncDeclaratorSuffix_0(b, l + 1);
        if (!r) r = FuncDeclaratorSuffix_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // TemplateParameters? Parameters MemberFunctionAttributes? Constraint?
    private static boolean FuncDeclaratorSuffix_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FuncDeclaratorSuffix_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = FuncDeclaratorSuffix_0_0(b, l + 1);
        r = r && ParameterParser.Parameters(b, l + 1);
        r = r && FuncDeclaratorSuffix_0_2(b, l + 1);
        r = r && FuncDeclaratorSuffix_0_3(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // TemplateParameters?
    private static boolean FuncDeclaratorSuffix_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FuncDeclaratorSuffix_0_0")) return false;
        TemplateParameterParser.TemplateParameters(b, l + 1);
        return true;
    }

    // MemberFunctionAttributes?
    private static boolean FuncDeclaratorSuffix_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FuncDeclaratorSuffix_0_2")) return false;
        MemberFunctionAttributes(b, l + 1);
        return true;
    }

    // Constraint?
    private static boolean FuncDeclaratorSuffix_0_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FuncDeclaratorSuffix_0_3")) return false;
        DeclDefParser.Constraint(b, l + 1);
        return true;
    }

    // Parameters MemberFunctionAttributes?
    private static boolean FuncDeclaratorSuffix_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FuncDeclaratorSuffix_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ParameterParser.Parameters(b, l + 1);
        r = r && FuncDeclaratorSuffix_1_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // MemberFunctionAttributes?
    private static boolean FuncDeclaratorSuffix_1_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FuncDeclaratorSuffix_1_1")) return false;
        MemberFunctionAttributes(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'nothrow'
    //     | 'pure'
    //     | Property
    public static boolean FunctionAttribute(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionAttribute")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FUNCTION_ATTRIBUTE, "<function attribute>");
        r = consumeToken(b, KW_NOTHROW);
        if (!r) r = consumeToken(b, KW_PURE);
        if (!r) r = AttributesParser.Property(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // FunctionAttribute FunctionAttribute*
    public static boolean FunctionAttributes(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionAttributes")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FUNCTION_ATTRIBUTES, "<function attributes>");
        r = FunctionAttribute(b, l + 1);
        r = r && FunctionAttributes_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // FunctionAttribute*
    private static boolean FunctionAttributes_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionAttributes_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!FunctionAttribute(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "FunctionAttributes_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    /* ********************************************************** */
    // BlockStatement
    //    | FunctionContracts? BodyStatement
    public static boolean FunctionBody(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionBody")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FUNCTION_BODY, "<function body>");
        r = StatementParser.BlockStatement(b, l + 1);
        if (!r) r = FunctionBody_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // FunctionContracts? BodyStatement
    private static boolean FunctionBody_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionBody_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = FunctionBody_1_0(b, l + 1);
        r = r && StatementParser.BodyStatement(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // FunctionContracts?
    private static boolean FunctionBody_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionBody_1_0")) return false;
        FunctionContracts(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // InStatement OutStatement?
    //     | OutStatement InStatement?
    public static boolean FunctionContracts(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionContracts")) return false;
        if (!nextTokenIs(b, "<function contracts>", KW_IN, KW_OUT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, FUNCTION_CONTRACTS, "<function contracts>");
        r = FunctionContracts_0(b, l + 1);
        if (!r) r = FunctionContracts_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // InStatement OutStatement?
    private static boolean FunctionContracts_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionContracts_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = StatementParser.InStatement(b, l + 1);
        r = r && FunctionContracts_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // OutStatement?
    private static boolean FunctionContracts_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionContracts_0_1")) return false;
        StatementParser.OutStatement(b, l + 1);
        return true;
    }

    // OutStatement InStatement?
    private static boolean FunctionContracts_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionContracts_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = StatementParser.OutStatement(b, l + 1);
        r = r && FunctionContracts_1_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // InStatement?
    private static boolean FunctionContracts_1_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "FunctionContracts_1_1")) return false;
        StatementParser.InStatement(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'interface' Identifier ';'
    //     | InterfaceTemplateDeclaration
    //     | 'interface' Identifier BaseInterfaceList? AggregateBody?
    public static boolean InterfaceDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InterfaceDeclaration")) return false;
        if (!nextTokenIs(b, KW_INTERFACE)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = InterfaceDeclaration_0(b, l + 1);
        if (!r) r = InterfaceTemplateDeclaration(b, l + 1);
        if (!r) r = InterfaceDeclaration_2(b, l + 1);
        exit_section_(b, m, INTERFACE_DECLARATION, r);
        return r;
    }

    // 'interface' Identifier ';'
    private static boolean InterfaceDeclaration_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InterfaceDeclaration_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_INTERFACE);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'interface' Identifier BaseInterfaceList? AggregateBody?
    private static boolean InterfaceDeclaration_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InterfaceDeclaration_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_INTERFACE);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && InterfaceDeclaration_2_2(b, l + 1);
        r = r && InterfaceDeclaration_2_3(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // BaseInterfaceList?
    private static boolean InterfaceDeclaration_2_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InterfaceDeclaration_2_2")) return false;
        BaseInterfaceList(b, l + 1);
        return true;
    }

    // AggregateBody?
    private static boolean InterfaceDeclaration_2_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InterfaceDeclaration_2_3")) return false;
        AggregateBody(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'interface' Identifier TemplateParameters Constraint? BaseInterfaceList? AggregateBody
    static boolean InterfaceTemplateDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InterfaceTemplateDeclaration")) return false;
        if (!nextTokenIs(b, KW_INTERFACE)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_INTERFACE);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && TemplateParameterParser.TemplateParameters(b, l + 1);
        r = r && InterfaceTemplateDeclaration_3(b, l + 1);
        r = r && InterfaceTemplateDeclaration_4(b, l + 1);
        r = r && AggregateBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Constraint?
    private static boolean InterfaceTemplateDeclaration_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InterfaceTemplateDeclaration_3")) return false;
        DeclDefParser.Constraint(b, l + 1);
        return true;
    }

    // BaseInterfaceList?
    private static boolean InterfaceTemplateDeclaration_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InterfaceTemplateDeclaration_4")) return false;
        BaseInterfaceList(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // Interface (',' Interface)*
    public static boolean Interfaces(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Interfaces")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, INTERFACES, "<interfaces>");
        r = MiscParser.Interface(b, l + 1);
        r = r && Interfaces_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (',' Interface)*
    private static boolean Interfaces_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Interfaces_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!Interfaces_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "Interfaces_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    // ',' Interface
    private static boolean Interfaces_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Interfaces_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && MiscParser.Interface(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'const'
    //     | 'immutable'
    //     | 'inout'
    //     | 'shared'
    //     | 'return'
    //     | FunctionAttribute
    public static boolean MemberFunctionAttribute(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MemberFunctionAttribute")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, MEMBER_FUNCTION_ATTRIBUTE, "<member function attribute>");
        r = consumeToken(b, KW_CONST);
        if (!r) r = consumeToken(b, KW_IMMUTABLE);
        if (!r) r = consumeToken(b, KW_INOUT);
        if (!r) r = consumeToken(b, KW_SHARED);
        if (!r) r = consumeToken(b, KW_RETURN);
        if (!r) r = FunctionAttribute(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // MemberFunctionAttribute MemberFunctionAttribute*
    public static boolean MemberFunctionAttributes(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MemberFunctionAttributes")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, MEMBER_FUNCTION_ATTRIBUTES, "<member function attributes>");
        r = MemberFunctionAttribute(b, l + 1);
        r = r && MemberFunctionAttributes_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // MemberFunctionAttribute*
    private static boolean MemberFunctionAttributes_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MemberFunctionAttributes_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!MemberFunctionAttribute(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "MemberFunctionAttributes_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    /* ********************************************************** */
    // 'struct' Identifier? TemplateParameters? Constraint? (AggregateBody | ';')?
    public static boolean StructDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StructDeclaration")) return false;
        if (!nextTokenIs(b, KW_STRUCT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_STRUCT);
        r = r && StructDeclaration_1(b, l + 1);
        r = r && StructDeclaration_2(b, l + 1);
        r = r && StructDeclaration_3(b, l + 1);
        r = r && StructDeclaration_4(b, l + 1);
        exit_section_(b, m, STRUCT_DECLARATION, r);
        return r;
    }

    // Identifier?
    private static boolean StructDeclaration_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StructDeclaration_1")) return false;
        BaseRulesParser.Identifier(b, l + 1);
        return true;
    }

    // TemplateParameters?
    private static boolean StructDeclaration_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StructDeclaration_2")) return false;
        TemplateParameterParser.TemplateParameters(b, l + 1);
        return true;
    }

    // Constraint?
    private static boolean StructDeclaration_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StructDeclaration_3")) return false;
        DeclDefParser.Constraint(b, l + 1);
        return true;
    }

    // (AggregateBody | ';')?
    private static boolean StructDeclaration_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StructDeclaration_4")) return false;
        StructDeclaration_4_0(b, l + 1);
        return true;
    }

    // AggregateBody | ';'
    private static boolean StructDeclaration_4_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StructDeclaration_4_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = AggregateBody(b, l + 1);
        if (!r) r = consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // BasicType
    public static boolean SuperClass(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SuperClass")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, SUPER_CLASS, "<super class>");
        r = TypeParser.BasicType(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // 'template' Identifier TemplateParameters Constraint? '{' DeclDefs? '}'
    public static boolean TemplateDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateDeclaration")) return false;
        if (!nextTokenIs(b, KW_TEMPLATE)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TEMPLATE_DECLARATION, null);
        r = consumeToken(b, KW_TEMPLATE);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        p = r; // pin = 2
        r = r && report_error_(b, TemplateParameterParser.TemplateParameters(b, l + 1));
        r = p && report_error_(b, TemplateDeclaration_3(b, l + 1)) && r;
        r = p && report_error_(b, consumeToken(b, OP_BRACES_LEFT)) && r;
        r = p && report_error_(b, TemplateDeclaration_5(b, l + 1)) && r;
        r = p && consumeToken(b, OP_BRACES_RIGHT) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // Constraint?
    private static boolean TemplateDeclaration_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateDeclaration_3")) return false;
        DeclDefParser.Constraint(b, l + 1);
        return true;
    }

    // DeclDefs?
    private static boolean TemplateDeclaration_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateDeclaration_5")) return false;
        DeclDefParser.DeclDefs(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'mixin' 'template' Identifier TemplateParameters Constraint? '{' DeclDefs? '}'
    public static boolean TemplateMixinDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateMixinDeclaration")) return false;
        if (!nextTokenIs(b, KW_MIXIN)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TEMPLATE_MIXIN_DECLARATION, null);
        r = consumeTokens(b, 0, KW_MIXIN, KW_TEMPLATE);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        p = r; // pin = 3
        r = r && report_error_(b, TemplateParameterParser.TemplateParameters(b, l + 1));
        r = p && report_error_(b, TemplateMixinDeclaration_4(b, l + 1)) && r;
        r = p && report_error_(b, consumeToken(b, OP_BRACES_LEFT)) && r;
        r = p && report_error_(b, TemplateMixinDeclaration_6(b, l + 1)) && r;
        r = p && consumeToken(b, OP_BRACES_RIGHT) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // Constraint?
    private static boolean TemplateMixinDeclaration_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateMixinDeclaration_4")) return false;
        DeclDefParser.Constraint(b, l + 1);
        return true;
    }

    // DeclDefs?
    private static boolean TemplateMixinDeclaration_6(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateMixinDeclaration_6")) return false;
        DeclDefParser.DeclDefs(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'union' Identifier ';'
    //     | 'union' Identifier AggregateBody
    //     | UnionTemplateDeclaration
    //     | AnonUnionDeclaration
    public static boolean UnionDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnionDeclaration")) return false;
        if (!nextTokenIs(b, KW_UNION)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = UnionDeclaration_0(b, l + 1);
        if (!r) r = UnionDeclaration_1(b, l + 1);
        if (!r) r = UnionTemplateDeclaration(b, l + 1);
        if (!r) r = AnonUnionDeclaration(b, l + 1);
        exit_section_(b, m, UNION_DECLARATION, r);
        return r;
    }

    // 'union' Identifier ';'
    private static boolean UnionDeclaration_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnionDeclaration_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_UNION);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'union' Identifier AggregateBody
    private static boolean UnionDeclaration_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnionDeclaration_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_UNION);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && AggregateBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'union' Identifier TemplateParameters Constraint? AggregateBody
    static boolean UnionTemplateDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnionTemplateDeclaration")) return false;
        if (!nextTokenIs(b, KW_UNION)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_);
        r = consumeToken(b, KW_UNION);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        p = r; // pin = 2
        r = r && report_error_(b, TemplateParameterParser.TemplateParameters(b, l + 1));
        r = p && report_error_(b, UnionTemplateDeclaration_3(b, l + 1)) && r;
        r = p && AggregateBody(b, l + 1) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // Constraint?
    private static boolean UnionTemplateDeclaration_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnionTemplateDeclaration_3")) return false;
        DeclDefParser.Constraint(b, l + 1);
        return true;
    }
}
