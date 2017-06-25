package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.parser.DLanguageParser.module_name_recover_parser_;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 6/25/2017.
 */
public class DeclDefParser {
    /* ********************************************************** */
    // 'alias' Identifier 'this' ';'
    public static boolean AliasThis(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AliasThis")) return false;
        if (!nextTokenIs(b, KW_ALIAS)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ALIAS_THIS, null);
        r = consumeToken(b, KW_ALIAS);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeTokens(b, 1, KW_THIS, OP_SCOLON);
        p = r; // pin = 3
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'new' Parameters ';'
    //     | 'new' Parameters FunctionBody
    public static boolean Allocator(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Allocator")) return false;
        if (!nextTokenIs(b, KW_NEW)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Allocator_0(b, l + 1);
        if (!r) r = Allocator_1(b, l + 1);
        exit_section_(b, m, ALLOCATOR, r);
        return r;
    }

    // 'new' Parameters ';'
    private static boolean Allocator_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Allocator_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_NEW);
        r = r && ParameterParser.Parameters(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'new' Parameters FunctionBody
    private static boolean Allocator_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Allocator_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_NEW);
        r = r && ParameterParser.Parameters(b, l + 1);
        r = r && DeclarationParser.FunctionBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // '(' ArgumentList? ')'
    public static boolean AllocatorArguments(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AllocatorArguments")) return false;
        if (!nextTokenIs(b, OP_PAR_LEFT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && AllocatorArguments_1(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, ALLOCATOR_ARGUMENTS, r);
        return r;
    }

    // ArgumentList?
    private static boolean AllocatorArguments_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AllocatorArguments_1")) return false;
        ArgumentParser.ArgumentList(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // VersionCondition
    //     | DebugCondition
    //     | StaticIfCondition
    public static boolean Condition(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Condition")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, CONDITION, "<condition>");
        r = VersionCondition(b, l + 1);
        if (!r) r = DebugCondition(b, l + 1);
        if (!r) r = StaticIfCondition(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // 'auto' Identifier '=' Expression
    //     | TypeCtors Identifier '=' Expression
    //     | TypeCtors? BasicType Declarator '=' Expression
    public static boolean ConditionVariableDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionVariableDeclaration")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, CONDITION_VARIABLE_DECLARATION, "<condition variable declaration>");
        r = ConditionVariableDeclaration_0(b, l + 1);
        if (!r) r = ConditionVariableDeclaration_1(b, l + 1);
        if (!r) r = ConditionVariableDeclaration_2(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // 'auto' Identifier '=' Expression
    private static boolean ConditionVariableDeclaration_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionVariableDeclaration_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_AUTO);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_EQ);
        r = r && ExpressionParser.Expression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // TypeCtors Identifier '=' Expression
    private static boolean ConditionVariableDeclaration_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionVariableDeclaration_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = TypeParser.TypeCtors(b, l + 1);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_EQ);
        r = r && ExpressionParser.Expression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // TypeCtors? BasicType Declarator '=' Expression
    private static boolean ConditionVariableDeclaration_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionVariableDeclaration_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ConditionVariableDeclaration_2_0(b, l + 1);
        r = r && TypeParser.BasicType(b, l + 1);
        r = r && VariableDeclarationParser.Declarator(b, l + 1);
        r = r && consumeToken(b, OP_EQ);
        r = r && ExpressionParser.Expression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // TypeCtors?
    private static boolean ConditionVariableDeclaration_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionVariableDeclaration_2_0")) return false;
        TypeParser.TypeCtors(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // Condition DeclarationBlock 'else' ':' DeclDefs?//must be on top
    //     | Condition DeclarationBlock ('else' DeclarationBlock)?
    //     | Condition ':' DeclDefs?
    public static boolean ConditionalDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalDeclaration")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, CONDITIONAL_DECLARATION, "<conditional declaration>");
        r = ConditionalDeclaration_0(b, l + 1);
        if (!r) r = ConditionalDeclaration_1(b, l + 1);
        if (!r) r = ConditionalDeclaration_2(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // Condition DeclarationBlock 'else' ':' DeclDefs?
    private static boolean ConditionalDeclaration_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalDeclaration_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Condition(b, l + 1);
        r = r && DeclarationParser.DeclarationBlock(b, l + 1);
        r = r && consumeTokens(b, 0, KW_ELSE, OP_COLON);
        r = r && ConditionalDeclaration_0_4(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // DeclDefs?
    private static boolean ConditionalDeclaration_0_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalDeclaration_0_4")) return false;
        DeclDefs(b, l + 1);
        return true;
    }

    // Condition DeclarationBlock ('else' DeclarationBlock)?
    private static boolean ConditionalDeclaration_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalDeclaration_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Condition(b, l + 1);
        r = r && DeclarationParser.DeclarationBlock(b, l + 1);
        r = r && ConditionalDeclaration_1_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('else' DeclarationBlock)?
    private static boolean ConditionalDeclaration_1_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalDeclaration_1_2")) return false;
        ConditionalDeclaration_1_2_0(b, l + 1);
        return true;
    }

    // 'else' DeclarationBlock
    private static boolean ConditionalDeclaration_1_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalDeclaration_1_2_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_ELSE);
        r = r && DeclarationParser.DeclarationBlock(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Condition ':' DeclDefs?
    private static boolean ConditionalDeclaration_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalDeclaration_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Condition(b, l + 1);
        r = r && consumeToken(b, OP_COLON);
        r = r && ConditionalDeclaration_2_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // DeclDefs?
    private static boolean ConditionalDeclaration_2_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalDeclaration_2_2")) return false;
        DeclDefs(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'if' '(' Expression ')'
    public static boolean Constraint(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Constraint")) return false;
        if (!nextTokenIs(b, KW_IF)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, CONSTRAINT, null);
        r = consumeTokens(b, 1, KW_IF, OP_PAR_LEFT);
        p = r; // pin = 1
        r = r && report_error_(b, ExpressionParser.Expression(b, l + 1));
        r = p && consumeToken(b, OP_PAR_RIGHT) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'this' Parameters MemberFunctionAttributes? ';'
    //     | 'this' Parameters MemberFunctionAttributes? FunctionBody
    //     | ConstructorTemplate
    public static boolean Constructor(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Constructor")) return false;
        if (!nextTokenIs(b, KW_THIS)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Constructor_0(b, l + 1);
        if (!r) r = Constructor_1(b, l + 1);
        if (!r) r = ConstructorTemplate(b, l + 1);
        exit_section_(b, m, CONSTRUCTOR, r);
        return r;
    }

    // 'this' Parameters MemberFunctionAttributes? ';'
    private static boolean Constructor_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Constructor_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_THIS);
        r = r && ParameterParser.Parameters(b, l + 1);
        r = r && Constructor_0_2(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // MemberFunctionAttributes?
    private static boolean Constructor_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Constructor_0_2")) return false;
        DeclarationParser.MemberFunctionAttributes(b, l + 1);
        return true;
    }

    // 'this' Parameters MemberFunctionAttributes? FunctionBody
    private static boolean Constructor_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Constructor_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_THIS);
        r = r && ParameterParser.Parameters(b, l + 1);
        r = r && Constructor_1_2(b, l + 1);
        r = r && DeclarationParser.FunctionBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // MemberFunctionAttributes?
    private static boolean Constructor_1_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Constructor_1_2")) return false;
        DeclarationParser.MemberFunctionAttributes(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'this' TemplateParameters Parameters MemberFunctionAttributes? Constraint? ';'
    //     | 'this' TemplateParameters Parameters MemberFunctionAttributes? Constraint? FunctionBody
    static boolean ConstructorTemplate(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConstructorTemplate")) return false;
        if (!nextTokenIs(b, KW_THIS)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ConstructorTemplate_0(b, l + 1);
        if (!r) r = ConstructorTemplate_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'this' TemplateParameters Parameters MemberFunctionAttributes? Constraint? ';'
    private static boolean ConstructorTemplate_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConstructorTemplate_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_THIS);
        r = r && TemplateParameterParser.TemplateParameters(b, l + 1);
        r = r && ParameterParser.Parameters(b, l + 1);
        r = r && ConstructorTemplate_0_3(b, l + 1);
        r = r && ConstructorTemplate_0_4(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // MemberFunctionAttributes?
    private static boolean ConstructorTemplate_0_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConstructorTemplate_0_3")) return false;
        DeclarationParser.MemberFunctionAttributes(b, l + 1);
        return true;
    }

    // Constraint?
    private static boolean ConstructorTemplate_0_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConstructorTemplate_0_4")) return false;
        Constraint(b, l + 1);
        return true;
    }

    // 'this' TemplateParameters Parameters MemberFunctionAttributes? Constraint? FunctionBody
    private static boolean ConstructorTemplate_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConstructorTemplate_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_THIS);
        r = r && TemplateParameterParser.TemplateParameters(b, l + 1);
        r = r && ParameterParser.Parameters(b, l + 1);
        r = r && ConstructorTemplate_1_3(b, l + 1);
        r = r && ConstructorTemplate_1_4(b, l + 1);
        r = r && DeclarationParser.FunctionBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // MemberFunctionAttributes?
    private static boolean ConstructorTemplate_1_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConstructorTemplate_1_3")) return false;
        DeclarationParser.MemberFunctionAttributes(b, l + 1);
        return true;
    }

    // Constraint?
    private static boolean ConstructorTemplate_1_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConstructorTemplate_1_4")) return false;
        Constraint(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'delete' Parameters ';'
    //     | 'delete' Parameters FunctionBody
    public static boolean Deallocator(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Deallocator")) return false;
        if (!nextTokenIs(b, KW_DELETE)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Deallocator_0(b, l + 1);
        if (!r) r = Deallocator_1(b, l + 1);
        exit_section_(b, m, DEALLOCATOR, r);
        return r;
    }

    // 'delete' Parameters ';'
    private static boolean Deallocator_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Deallocator_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_DELETE);
        r = r && ParameterParser.Parameters(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'delete' Parameters FunctionBody
    private static boolean Deallocator_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Deallocator_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_DELETE);
        r = r && ParameterParser.Parameters(b, l + 1);
        r = r && DeclarationParser.FunctionBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'debug' ('(' (INTEGER_LITERAL | Identifier) ')')?
    public static boolean DebugCondition(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DebugCondition")) return false;
        if (!nextTokenIs(b, KW_DEBUG)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, DEBUG_CONDITION, null);
        r = consumeToken(b, KW_DEBUG);
        p = r; // pin = 1
        r = r && DebugCondition_1(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // ('(' (INTEGER_LITERAL | Identifier) ')')?
    private static boolean DebugCondition_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DebugCondition_1")) return false;
        DebugCondition_1_0(b, l + 1);
        return true;
    }

    // '(' (INTEGER_LITERAL | Identifier) ')'
    private static boolean DebugCondition_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DebugCondition_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && DebugCondition_1_0_1(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // INTEGER_LITERAL | Identifier
    private static boolean DebugCondition_1_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DebugCondition_1_0_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, INTEGER_LITERAL);
        if (!r) r = BaseRulesParser.Identifier(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'debug' '=' (Identifier | INTEGER_LITERAL) ';'
    public static boolean DebugSpecification(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DebugSpecification")) return false;
        if (!nextTokenIs(b, KW_DEBUG)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, DEBUG_SPECIFICATION, null);
        r = consumeTokens(b, 2, KW_DEBUG, OP_EQ);
        p = r; // pin = 2
        r = r && report_error_(b, DebugSpecification_2(b, l + 1));
        r = p && consumeToken(b, OP_SCOLON) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // Identifier | INTEGER_LITERAL
    private static boolean DebugSpecification_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DebugSpecification_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        if (!r) r = consumeToken(b, INTEGER_LITERAL);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // AttributeSpecifier
    //     | Declaration
    //     | Postblit
    //     | Constructor
    //     | Destructor
    //     | Allocator
    //     | Deallocator
    //     | Invariant
    //     | UnitTesting
    //     | AliasThis
    //     | StaticConstructor
    //     | StaticDestructor
    //     | SharedStaticConstructor
    //     | SharedStaticDestructor
    //     | ConditionalDeclaration
    //     | DebugSpecification
    //     | VersionSpecification
    //     | StaticAssert
    //     | TemplateDeclaration
    //     | TemplateMixinDeclaration
    //     | TemplateMixin
    //     | MixinDeclaration
    //     | StaticIfCondition
    //     | StaticElseCondition
    //     | ';'
    public static boolean DeclDef(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclDef")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, DECL_DEF, "<decl def>");
        r = AttributesParser.AttributeSpecifier(b, l + 1);
        if (!r) r = DeclarationParser.Declaration(b, l + 1);
        if (!r) r = Postblit(b, l + 1);
        if (!r) r = Constructor(b, l + 1);
        if (!r) r = Destructor(b, l + 1);
        if (!r) r = Allocator(b, l + 1);
        if (!r) r = Deallocator(b, l + 1);
        if (!r) r = Invariant(b, l + 1);
        if (!r) r = UnitTesting(b, l + 1);
        if (!r) r = AliasThis(b, l + 1);
        if (!r) r = StaticConstructor(b, l + 1);
        if (!r) r = StaticDestructor(b, l + 1);
        if (!r) r = SharedStaticConstructor(b, l + 1);
        if (!r) r = SharedStaticDestructor(b, l + 1);
        if (!r) r = ConditionalDeclaration(b, l + 1);
        if (!r) r = DebugSpecification(b, l + 1);
        if (!r) r = VersionSpecification(b, l + 1);
        if (!r) r = StaticAssert(b, l + 1);
        if (!r) r = DeclarationParser.TemplateDeclaration(b, l + 1);
        if (!r) r = DeclarationParser.TemplateMixinDeclaration(b, l + 1);
        if (!r) r = TemplateMixin(b, l + 1);
        if (!r) r = MixinDeclaration(b, l + 1);
        if (!r) r = StaticIfCondition(b, l + 1);
        if (!r) r = StaticElseCondition(b, l + 1);
        if (!r) r = consumeToken(b, OP_SCOLON);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // DeclDef DeclDef*
    public static boolean DeclDefs(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclDefs")) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, DECL_DEFS, "<decl defs>");
        r = DeclDef(b, l + 1);
        p = r; // pin = 1
        r = r && DeclDefs_1(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // DeclDef*
    private static boolean DeclDefs_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeclDefs_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!DeclDef(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "DeclDefs_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    /* ********************************************************** */
    // '~' 'this' '(' ')' MemberFunctionAttributes? ';'
    //     | '~' 'this' '(' ')' MemberFunctionAttributes? FunctionBody
    public static boolean Destructor(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Destructor")) return false;
        if (!nextTokenIs(b, OP_TILDA)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Destructor_0(b, l + 1);
        if (!r) r = Destructor_1(b, l + 1);
        exit_section_(b, m, DESTRUCTOR, r);
        return r;
    }

    // '~' 'this' '(' ')' MemberFunctionAttributes? ';'
    private static boolean Destructor_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Destructor_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, OP_TILDA, KW_THIS, OP_PAR_LEFT, OP_PAR_RIGHT);
        r = r && Destructor_0_4(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // MemberFunctionAttributes?
    private static boolean Destructor_0_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Destructor_0_4")) return false;
        DeclarationParser.MemberFunctionAttributes(b, l + 1);
        return true;
    }

    // '~' 'this' '(' ')' MemberFunctionAttributes? FunctionBody
    private static boolean Destructor_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Destructor_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, OP_TILDA, KW_THIS, OP_PAR_LEFT, OP_PAR_RIGHT);
        r = r && Destructor_1_4(b, l + 1);
        r = r && DeclarationParser.FunctionBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // MemberFunctionAttributes?
    private static boolean Destructor_1_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Destructor_1_4")) return false;
        DeclarationParser.MemberFunctionAttributes(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // (Identifier '=')? ModuleFullyQualifiedName
    public static boolean Import(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Import")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Import_0(b, l + 1);
        r = r && ModuleFullyQualifiedName(b, l + 1);
        exit_section_(b, m, IMPORT, r);
        return r;
    }

    // (Identifier '=')?
    private static boolean Import_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Import_0")) return false;
        Import_0_0(b, l + 1);
        return true;
    }

    // Identifier '='
    private static boolean Import_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Import_0_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_EQ);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // Identifier ('=' Identifier)?
    public static boolean ImportBind(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ImportBind")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        r = r && ImportBind_1(b, l + 1);
        exit_section_(b, m, IMPORT_BIND, r);
        return r;
    }

    // ('=' Identifier)?
    private static boolean ImportBind_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ImportBind_1")) return false;
        ImportBind_1_0(b, l + 1);
        return true;
    }

    // '=' Identifier
    private static boolean ImportBind_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ImportBind_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_EQ);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // ImportBind (',' ImportBindList)?
    public static boolean ImportBindList(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ImportBindList")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ImportBind(b, l + 1);
        r = r && ImportBindList_1(b, l + 1);
        exit_section_(b, m, IMPORT_BIND_LIST, r);
        return r;
    }

    // (',' ImportBindList)?
    private static boolean ImportBindList_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ImportBindList_1")) return false;
        ImportBindList_1_0(b, l + 1);
        return true;
    }

    // ',' ImportBindList
    private static boolean ImportBindList_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ImportBindList_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && ImportBindList(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // RegularImport |
    //     StaticImport
    public static boolean ImportDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ImportDeclaration")) return false;
        if (!nextTokenIs(b, "<import declaration>", KW_IMPORT, KW_STATIC)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, IMPORT_DECLARATION, "<import declaration>");
        r = RegularImport(b, l + 1);
        if (!r) r = StaticImport(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // Import (':' ImportBindList | ',' ImportList)?
    public static boolean ImportList(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ImportList")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Import(b, l + 1);
        r = r && ImportList_1(b, l + 1);
        exit_section_(b, m, IMPORT_LIST, r);
        return r;
    }

    // (':' ImportBindList | ',' ImportList)?
    private static boolean ImportList_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ImportList_1")) return false;
        ImportList_1_0(b, l + 1);
        return true;
    }

    // ':' ImportBindList | ',' ImportList
    private static boolean ImportList_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ImportList_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ImportList_1_0_0(b, l + 1);
        if (!r) r = ImportList_1_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ':' ImportBindList
    private static boolean ImportList_1_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ImportList_1_0_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COLON);
        r = r && ImportBindList(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ',' ImportList
    private static boolean ImportList_1_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ImportList_1_0_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && ImportList(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // InvariantWithParen
    //     | InvariantWithoutParen
    public static boolean Invariant(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Invariant")) return false;
        if (!nextTokenIs(b, KW_INVARIANT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = InvariantWithParen(b, l + 1);
        if (!r) r = InvariantWithoutParen(b, l + 1);
        exit_section_(b, m, INVARIANT, r);
        return r;
    }

    /* ********************************************************** */
    // 'invariant' '(' ')' BlockStatement
    static boolean InvariantWithParen(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InvariantWithParen")) return false;
        if (!nextTokenIs(b, KW_INVARIANT)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_);
        r = consumeTokens(b, 2, KW_INVARIANT, OP_PAR_LEFT, OP_PAR_RIGHT);
        p = r; // pin = 2
        r = r && StatementParser.BlockStatement(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'invariant' BlockStatement
    static boolean InvariantWithoutParen(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InvariantWithoutParen")) return false;
        if (!nextTokenIs(b, KW_INVARIANT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_INVARIANT);
        r = r && StatementParser.BlockStatement(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'mixin' '(' ((TemplateInstance AssignExpression?)| AssignExpression) ')' ';'
    public static boolean MixinDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MixinDeclaration")) return false;
        if (!nextTokenIs(b, KW_MIXIN)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_MIXIN, OP_PAR_LEFT);
        r = r && MixinDeclaration_2(b, l + 1);
        r = r && consumeTokens(b, 0, OP_PAR_RIGHT, OP_SCOLON);
        exit_section_(b, m, MIXIN_DECLARATION, r);
        return r;
    }

    // (TemplateInstance AssignExpression?)| AssignExpression
    private static boolean MixinDeclaration_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MixinDeclaration_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = MixinDeclaration_2_0(b, l + 1);
        if (!r) r = ExpressionParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // TemplateInstance AssignExpression?
    private static boolean MixinDeclaration_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MixinDeclaration_2_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = MiscParser.TemplateInstance(b, l + 1);
        r = r && MixinDeclaration_2_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // AssignExpression?
    private static boolean MixinDeclaration_2_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MixinDeclaration_2_0_1")) return false;
        ExpressionParser.AssignExpression(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // Typeof? '.'? QualifiedIdentifierList
    public static boolean MixinTemplateName(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MixinTemplateName")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, MIXIN_TEMPLATE_NAME, "<mixin template name>");
        r = MixinTemplateName_0(b, l + 1);
        r = r && MixinTemplateName_1(b, l + 1);
        r = r && IdentifierListsParser.QualifiedIdentifierList(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // Typeof?
    private static boolean MixinTemplateName_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MixinTemplateName_0")) return false;
        TypeParser.Typeof(b, l + 1);
        return true;
    }

    // '.'?
    private static boolean MixinTemplateName_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MixinTemplateName_1")) return false;
        consumeToken(b, OP_DOT);
        return true;
    }

    /* ********************************************************** */
    // Attribute? 'module' ModuleFullyQualifiedName ';'
    public static boolean ModuleDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ModuleDeclaration")) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, MODULE_DECLARATION, "<module declaration>");
        r = ModuleDeclaration_0(b, l + 1);
        r = r && consumeToken(b, KW_MODULE);
        p = r; // pin = 2
        r = r && report_error_(b, ModuleFullyQualifiedName(b, l + 1));
        r = p && consumeToken(b, OP_SCOLON) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // Attribute?
    private static boolean ModuleDeclaration_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ModuleDeclaration_0")) return false;
        AttributesParser.Attribute(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // Identifier ['.' ModuleFullyQualifiedName]
    public static boolean ModuleFullyQualifiedName(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ModuleFullyQualifiedName")) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, MODULE_FULLY_QUALIFIED_NAME, "<module fully qualified name>");
        r = BaseRulesParser.Identifier(b, l + 1);
        p = r; // pin = 1
        r = r && ModuleFullyQualifiedName_1(b, l + 1);
        exit_section_(b, l, m, r, p, module_name_recover_parser_);
        return r || p;
    }

    // ['.' ModuleFullyQualifiedName]
    private static boolean ModuleFullyQualifiedName_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ModuleFullyQualifiedName_1")) return false;
        ModuleFullyQualifiedName_1_0(b, l + 1);
        return true;
    }

    // '.' ModuleFullyQualifiedName
    private static boolean ModuleFullyQualifiedName_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ModuleFullyQualifiedName_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_DOT);
        r = r && ModuleFullyQualifiedName(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'this' '(' 'this' ')' MemberFunctionAttributes? ';'
    //     | 'this' '(' 'this' ')' MemberFunctionAttributes? FunctionBody
    public static boolean Postblit(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Postblit")) return false;
        if (!nextTokenIs(b, KW_THIS)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Postblit_0(b, l + 1);
        if (!r) r = Postblit_1(b, l + 1);
        exit_section_(b, m, POSTBLIT, r);
        return r;
    }

    // 'this' '(' 'this' ')' MemberFunctionAttributes? ';'
    private static boolean Postblit_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Postblit_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_THIS, OP_PAR_LEFT, KW_THIS, OP_PAR_RIGHT);
        r = r && Postblit_0_4(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // MemberFunctionAttributes?
    private static boolean Postblit_0_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Postblit_0_4")) return false;
        DeclarationParser.MemberFunctionAttributes(b, l + 1);
        return true;
    }

    // 'this' '(' 'this' ')' MemberFunctionAttributes? FunctionBody
    private static boolean Postblit_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Postblit_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_THIS, OP_PAR_LEFT, KW_THIS, OP_PAR_RIGHT);
        r = r && Postblit_1_4(b, l + 1);
        r = r && DeclarationParser.FunctionBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // MemberFunctionAttributes?
    private static boolean Postblit_1_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Postblit_1_4")) return false;
        DeclarationParser.MemberFunctionAttributes(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'import' ImportList ';'
    static boolean RegularImport(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RegularImport")) return false;
        if (!nextTokenIs(b, KW_IMPORT)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_);
        r = consumeToken(b, KW_IMPORT);
        p = r; // pin = 1
        r = r && report_error_(b, ImportList(b, l + 1));
        r = p && consumeToken(b, OP_SCOLON) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'shared' 'static' 'this' '(' ')' ';'
    //     | 'shared' 'static' 'this' '(' ')' FunctionBody
    public static boolean SharedStaticConstructor(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SharedStaticConstructor")) return false;
        if (!nextTokenIs(b, KW_SHARED)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = parseTokens(b, 0, KW_SHARED, KW_STATIC, KW_THIS, OP_PAR_LEFT, OP_PAR_RIGHT, OP_SCOLON);
        if (!r) r = SharedStaticConstructor_1(b, l + 1);
        exit_section_(b, m, SHARED_STATIC_CONSTRUCTOR, r);
        return r;
    }

    // 'shared' 'static' 'this' '(' ')' FunctionBody
    private static boolean SharedStaticConstructor_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SharedStaticConstructor_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_SHARED, KW_STATIC, KW_THIS, OP_PAR_LEFT, OP_PAR_RIGHT);
        r = r && DeclarationParser.FunctionBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'shared' 'static' '~' 'this' '(' ')' MemberFunctionAttributes? ';'
    //     | 'shared' 'static' '~' 'this' '(' ')' MemberFunctionAttributes? FunctionBody
    public static boolean SharedStaticDestructor(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SharedStaticDestructor")) return false;
        if (!nextTokenIs(b, KW_SHARED)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = SharedStaticDestructor_0(b, l + 1);
        if (!r) r = SharedStaticDestructor_1(b, l + 1);
        exit_section_(b, m, SHARED_STATIC_DESTRUCTOR, r);
        return r;
    }

    // 'shared' 'static' '~' 'this' '(' ')' MemberFunctionAttributes? ';'
    private static boolean SharedStaticDestructor_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SharedStaticDestructor_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_SHARED, KW_STATIC, OP_TILDA, KW_THIS, OP_PAR_LEFT, OP_PAR_RIGHT);
        r = r && SharedStaticDestructor_0_6(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // MemberFunctionAttributes?
    private static boolean SharedStaticDestructor_0_6(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SharedStaticDestructor_0_6")) return false;
        DeclarationParser.MemberFunctionAttributes(b, l + 1);
        return true;
    }

    // 'shared' 'static' '~' 'this' '(' ')' MemberFunctionAttributes? FunctionBody
    private static boolean SharedStaticDestructor_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SharedStaticDestructor_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_SHARED, KW_STATIC, OP_TILDA, KW_THIS, OP_PAR_LEFT, OP_PAR_RIGHT);
        r = r && SharedStaticDestructor_1_6(b, l + 1);
        r = r && DeclarationParser.FunctionBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // MemberFunctionAttributes?
    private static boolean SharedStaticDestructor_1_6(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SharedStaticDestructor_1_6")) return false;
        DeclarationParser.MemberFunctionAttributes(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'static' 'assert' '(' AssignExpression (',' AssignExpression)? ')' ';'
    public static boolean StaticAssert(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StaticAssert")) return false;
        if (!nextTokenIs(b, KW_STATIC)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, STATIC_ASSERT, null);
        r = consumeTokens(b, 3, KW_STATIC, KW_ASSERT, OP_PAR_LEFT);
        p = r; // pin = 3
        r = r && report_error_(b, ExpressionParser.AssignExpression(b, l + 1));
        r = p && report_error_(b, StaticAssert_4(b, l + 1)) && r;
        r = p && report_error_(b, consumeTokens(b, -1, OP_PAR_RIGHT, OP_SCOLON)) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // (',' AssignExpression)?
    private static boolean StaticAssert_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StaticAssert_4")) return false;
        StaticAssert_4_0(b, l + 1);
        return true;
    }

    // ',' AssignExpression
    private static boolean StaticAssert_4_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StaticAssert_4_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && ExpressionParser.AssignExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'static' 'this' '(' ')' ';'
    //     | 'static' 'this' '(' ')' FunctionBody
    public static boolean StaticConstructor(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StaticConstructor")) return false;
        if (!nextTokenIs(b, KW_STATIC)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = parseTokens(b, 0, KW_STATIC, KW_THIS, OP_PAR_LEFT, OP_PAR_RIGHT, OP_SCOLON);
        if (!r) r = StaticConstructor_1(b, l + 1);
        exit_section_(b, m, STATIC_CONSTRUCTOR, r);
        return r;
    }

    // 'static' 'this' '(' ')' FunctionBody
    private static boolean StaticConstructor_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StaticConstructor_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_STATIC, KW_THIS, OP_PAR_LEFT, OP_PAR_RIGHT);
        r = r && DeclarationParser.FunctionBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'static' '~' 'this' '(' ')' MemberFunctionAttributes? ';'
    //     | 'static' '~' 'this' '(' ')' MemberFunctionAttributes? FunctionBody
    public static boolean StaticDestructor(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StaticDestructor")) return false;
        if (!nextTokenIs(b, KW_STATIC)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = StaticDestructor_0(b, l + 1);
        if (!r) r = StaticDestructor_1(b, l + 1);
        exit_section_(b, m, STATIC_DESTRUCTOR, r);
        return r;
    }

    // 'static' '~' 'this' '(' ')' MemberFunctionAttributes? ';'
    private static boolean StaticDestructor_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StaticDestructor_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_STATIC, OP_TILDA, KW_THIS, OP_PAR_LEFT, OP_PAR_RIGHT);
        r = r && StaticDestructor_0_5(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, null, r);
        return r;
    }

    // MemberFunctionAttributes?
    private static boolean StaticDestructor_0_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StaticDestructor_0_5")) return false;
        DeclarationParser.MemberFunctionAttributes(b, l + 1);
        return true;
    }

    // 'static' '~' 'this' '(' ')' MemberFunctionAttributes? FunctionBody
    private static boolean StaticDestructor_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StaticDestructor_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_STATIC, OP_TILDA, KW_THIS, OP_PAR_LEFT, OP_PAR_RIGHT);
        r = r && StaticDestructor_1_5(b, l + 1);
        r = r && DeclarationParser.FunctionBody(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // MemberFunctionAttributes?
    private static boolean StaticDestructor_1_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StaticDestructor_1_5")) return false;
        DeclarationParser.MemberFunctionAttributes(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'else' DeclarationBlock
    public static boolean StaticElseCondition(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StaticElseCondition")) return false;
        if (!nextTokenIs(b, KW_ELSE)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, STATIC_ELSE_CONDITION, null);
        r = consumeToken(b, KW_ELSE);
        p = r; // pin = 1
        r = r && DeclarationParser.DeclarationBlock(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'static' 'if' '(' AssignExpression ')'
    public static boolean StaticIfCondition(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StaticIfCondition")) return false;
        if (!nextTokenIs(b, KW_STATIC)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, STATIC_IF_CONDITION, null);
        r = consumeTokens(b, 3, KW_STATIC, KW_IF, OP_PAR_LEFT);
        p = r; // pin = 3
        r = r && report_error_(b, ExpressionParser.AssignExpression(b, l + 1));
        r = p && consumeToken(b, OP_PAR_RIGHT) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'static' 'import' ImportList ';'
    static boolean StaticImport(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "StaticImport")) return false;
        if (!nextTokenIs(b, KW_STATIC)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_);
        r = consumeTokens(b, 2, KW_STATIC, KW_IMPORT);
        p = r; // pin = 2
        r = r && report_error_(b, ImportList(b, l + 1));
        r = p && consumeToken(b, OP_SCOLON) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'mixin' MixinTemplateName TemplateArguments? Identifier? ';'
    public static boolean TemplateMixin(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateMixin")) return false;
        if (!nextTokenIs(b, KW_MIXIN)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_MIXIN);
        r = r && MixinTemplateName(b, l + 1);
        r = r && TemplateMixin_2(b, l + 1);
        r = r && TemplateMixin_3(b, l + 1);
        r = r && consumeToken(b, OP_SCOLON);
        exit_section_(b, m, TEMPLATE_MIXIN, r);
        return r;
    }

    // TemplateArguments?
    private static boolean TemplateMixin_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateMixin_2")) return false;
        TemplateArgumentParser.TemplateArguments(b, l + 1);
        return true;
    }

    // Identifier?
    private static boolean TemplateMixin_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TemplateMixin_3")) return false;
        BaseRulesParser.Identifier(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'unittest' BlockStatement
    public static boolean UnitTesting(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnitTesting")) return false;
        if (!nextTokenIs(b, KW_UNITTEST)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, UNIT_TESTING, null);
        r = consumeToken(b, KW_UNITTEST);
        p = r; // pin = 1
        r = r && StatementParser.BlockStatement(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'version' '(' (INTEGER_LITERAL | Identifier | 'unittest' | 'assert') ')'
    public static boolean VersionCondition(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VersionCondition")) return false;
        if (!nextTokenIs(b, KW_VERSION)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, VERSION_CONDITION, null);
        r = consumeTokens(b, 2, KW_VERSION, OP_PAR_LEFT);
        p = r; // pin = 2
        r = r && report_error_(b, VersionCondition_2(b, l + 1));
        r = p && consumeToken(b, OP_PAR_RIGHT) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // INTEGER_LITERAL | Identifier | 'unittest' | 'assert'
    private static boolean VersionCondition_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VersionCondition_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, INTEGER_LITERAL);
        if (!r) r = BaseRulesParser.Identifier(b, l + 1);
        if (!r) r = consumeToken(b, KW_UNITTEST);
        if (!r) r = consumeToken(b, KW_ASSERT);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'version' '=' (Identifier | INTEGER_LITERAL) ';'
    public static boolean VersionSpecification(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VersionSpecification")) return false;
        if (!nextTokenIs(b, KW_VERSION)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, VERSION_SPECIFICATION, null);
        r = consumeTokens(b, 2, KW_VERSION, OP_EQ);
        p = r; // pin = 2
        r = r && report_error_(b, VersionSpecification_2(b, l + 1));
        r = p && consumeToken(b, OP_SCOLON) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // Identifier | INTEGER_LITERAL
    private static boolean VersionSpecification_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "VersionSpecification_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        if (!r) r = consumeToken(b, INTEGER_LITERAL);
        exit_section_(b, m, null, r);
        return r;
    }
}
