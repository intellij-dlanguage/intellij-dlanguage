package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 6/25/2017.
 */
public class AttributesParser {
    /* ********************************************************** */
    // 'align' ('(' INTEGER_LITERAL ')')?
    public static boolean AlignAttribute(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AlignAttribute")) return false;
        if (!nextTokenIs(b, KW_ALIGN)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ALIGN_ATTRIBUTE, null);
        r = consumeToken(b, KW_ALIGN);
        p = r; // pin = 1
        r = r && AlignAttribute_1(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // ('(' INTEGER_LITERAL ')')?
    private static boolean AlignAttribute_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AlignAttribute_1")) return false;
        AlignAttribute_1_0(b, l + 1);
        return true;
    }

    // '(' INTEGER_LITERAL ')'
    private static boolean AlignAttribute_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AlignAttribute_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, OP_PAR_LEFT, INTEGER_LITERAL, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // LinkageAttribute
    //     | AlignAttribute
    //     | DeprecatedAttribute
    //     | ProtectionAttribute
    //     | Pragma
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
    public static boolean Attribute(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Attribute")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ATTRIBUTE, "<attribute>");
        r = LinkageAttribute(b, l + 1);
        if (!r) r = AlignAttribute(b, l + 1);
        if (!r) r = DeprecatedAttribute(b, l + 1);
        if (!r) r = ProtectionAttribute(b, l + 1);
        if (!r) r = StatementParser.Pragma(b, l + 1);
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
        if (!r) r = Property(b, l + 1);
        if (!r) r = consumeToken(b, KW_NOTHROW);
        if (!r) r = consumeToken(b, KW_PURE);
        if (!r) r = consumeToken(b, KW_REF);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // Attribute (':' | DeclarationBlock)
    public static boolean AttributeSpecifier(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AttributeSpecifier")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ATTRIBUTE_SPECIFIER, "<attribute specifier>");
        r = Attribute(b, l + 1);
        r = r && AttributeSpecifier_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ':' | DeclarationBlock
    private static boolean AttributeSpecifier_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AttributeSpecifier_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COLON);
        if (!r) r = DeclarationParser.DeclarationBlock(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // '@' PropertyIdentifier
    static boolean BuiltInProperty(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BuiltInProperty")) return false;
        if (!nextTokenIs(b, OP_AT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_AT);
        r = r && PropertyIdentifier(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'deprecated' ('(' AssignExpression')')?
    public static boolean DeprecatedAttribute(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeprecatedAttribute")) return false;
        if (!nextTokenIs(b, KW_DEPRECATED)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, DEPRECATED_ATTRIBUTE, null);
        r = consumeToken(b, KW_DEPRECATED);
        p = r; // pin = 1
        r = r && DeprecatedAttribute_1(b, l + 1);
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // ('(' AssignExpression')')?
    private static boolean DeprecatedAttribute_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeprecatedAttribute_1")) return false;
        DeprecatedAttribute_1_0(b, l + 1);
        return true;
    }

    // '(' AssignExpression')'
    private static boolean DeprecatedAttribute_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DeprecatedAttribute_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && ExpressionParser.AssignExpression(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'extern' '(' LinkageType ')'
    //     | 'extern' '(' Identifier '++' (',' IdentifierList)? ')'
    public static boolean LinkageAttribute(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LinkageAttribute")) return false;
        if (!nextTokenIs(b, KW_EXTERN)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = LinkageAttribute_0(b, l + 1);
        if (!r) r = LinkageAttribute_1(b, l + 1);
        exit_section_(b, m, LINKAGE_ATTRIBUTE, r);
        return r;
    }

    // 'extern' '(' LinkageType ')'
    private static boolean LinkageAttribute_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LinkageAttribute_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_EXTERN, OP_PAR_LEFT);
        r = r && LinkageType(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'extern' '(' Identifier '++' (',' IdentifierList)? ')'
    private static boolean LinkageAttribute_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LinkageAttribute_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_EXTERN, OP_PAR_LEFT);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_PLUS_PLUS);
        r = r && LinkageAttribute_1_4(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // (',' IdentifierList)?
    private static boolean LinkageAttribute_1_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LinkageAttribute_1_4")) return false;
        LinkageAttribute_1_4_0(b, l + 1);
        return true;
    }

    // ',' IdentifierList
    private static boolean LinkageAttribute_1_4_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LinkageAttribute_1_4_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && IdentifierListsParser.IdentifierList(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'C'
    //     | Identifier '++'
    //     | 'D'
    //     | 'Windows'
    //     | 'Pascal'
    //     | 'System'
    //     | 'Objective-C'
    public static boolean LinkageType(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LinkageType")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, LINKAGE_TYPE, "<linkage type>");
        r = consumeToken(b, "C");
        if (!r) r = LinkageType_1(b, l + 1);
        if (!r) r = consumeToken(b, "D");
        if (!r) r = consumeToken(b, "Windows");
        if (!r) r = consumeToken(b, "Pascal");
        if (!r) r = consumeToken(b, "System");
        if (!r) r = consumeToken(b, "Objective-C");
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // Identifier '++'
    private static boolean LinkageType_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LinkageType_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BaseRulesParser.Identifier(b, l + 1);
        r = r && consumeToken(b, OP_PLUS_PLUS);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // BuiltInProperty
    //     | UserDefinedAttribute
    public static boolean Property(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Property")) return false;
        if (!nextTokenIs(b, OP_AT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BuiltInProperty(b, l + 1);
        if (!r) r = UserDefinedAttribute(b, l + 1);
        exit_section_(b, m, PROPERTY, r);
        return r;
    }

    /* ********************************************************** */
    // 'property'
    //     | 'safe'
    //     | 'trusted'
    //     | 'system'
    //     | 'disable'
    //     | 'nogc'
    public static boolean PropertyIdentifier(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PropertyIdentifier")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, PROPERTY_IDENTIFIER, "<property identifier>");
        r = consumeToken(b, "property");
        if (!r) r = consumeToken(b, "safe");
        if (!r) r = consumeToken(b, "trusted");
        if (!r) r = consumeToken(b, "system");
        if (!r) r = consumeToken(b, "disable");
        if (!r) r = consumeToken(b, "nogc");
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // 'private'
    //     | 'package' '(' IdentifierList ')'
    //     | 'package'
    //     | 'protected'
    //     | 'public'
    //     | 'export'
    public static boolean ProtectionAttribute(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ProtectionAttribute")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, PROTECTION_ATTRIBUTE, "<protection attribute>");
        r = consumeToken(b, KW_PRIVATE);
        if (!r) r = ProtectionAttribute_1(b, l + 1);
        if (!r) r = consumeToken(b, KW_PACKAGE);
        if (!r) r = consumeToken(b, KW_PROTECTED);
        if (!r) r = consumeToken(b, KW_PUBLIC);
        if (!r) r = consumeToken(b, KW_EXPORT);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // 'package' '(' IdentifierList ')'
    private static boolean ProtectionAttribute_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ProtectionAttribute_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeTokens(b, 0, KW_PACKAGE, OP_PAR_LEFT);
        r = r && IdentifierListsParser.IdentifierList(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // UserDefinedAttributeArgumentOnly
    //     | UserDefinedAttributeWithTemplateInstance //must be above with identifier, becuae the pin on with Identifier will falsely match templates todo
    //     | UserDefinedAttributeWithIdentifier
    public static boolean UserDefinedAttribute(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UserDefinedAttribute")) return false;
        if (!nextTokenIs(b, OP_AT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = UserDefinedAttributeArgumentOnly(b, l + 1);
        if (!r) r = UserDefinedAttributeWithTemplateInstance(b, l + 1);
        if (!r) r = UserDefinedAttributeWithIdentifier(b, l + 1);
        exit_section_(b, m, USER_DEFINED_ATTRIBUTE, r);
        return r;
    }

    /* ********************************************************** */
    // '@' '(' ArgumentList ')'
    static boolean UserDefinedAttributeArgumentOnly(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UserDefinedAttributeArgumentOnly")) return false;
        if (!nextTokenIs(b, OP_AT)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_);
        r = consumeTokens(b, 2, OP_AT, OP_PAR_LEFT);
        p = r; // pin = 2
        r = r && report_error_(b, ArgumentParser.ArgumentList(b, l + 1));
        r = p && consumeToken(b, OP_PAR_RIGHT) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // '@' Identifier ('(' ArgumentList? ')')?
    static boolean UserDefinedAttributeWithIdentifier(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UserDefinedAttributeWithIdentifier")) return false;
        if (!nextTokenIs(b, OP_AT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_AT);
        r = r && BaseRulesParser.Identifier(b, l + 1);
        r = r && UserDefinedAttributeWithIdentifier_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('(' ArgumentList? ')')?
    private static boolean UserDefinedAttributeWithIdentifier_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UserDefinedAttributeWithIdentifier_2")) return false;
        UserDefinedAttributeWithIdentifier_2_0(b, l + 1);
        return true;
    }

    // '(' ArgumentList? ')'
    private static boolean UserDefinedAttributeWithIdentifier_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UserDefinedAttributeWithIdentifier_2_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && UserDefinedAttributeWithIdentifier_2_0_1(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // ArgumentList?
    private static boolean UserDefinedAttributeWithIdentifier_2_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UserDefinedAttributeWithIdentifier_2_0_1")) return false;
        ArgumentParser.ArgumentList(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // '@' TemplateInstance ('(' ArgumentList? ')')?
    static boolean UserDefinedAttributeWithTemplateInstance(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UserDefinedAttributeWithTemplateInstance")) return false;
        if (!nextTokenIs(b, OP_AT)) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_AT);
        r = r && MiscParser.TemplateInstance(b, l + 1);
        r = r && UserDefinedAttributeWithTemplateInstance_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('(' ArgumentList? ')')?
    private static boolean UserDefinedAttributeWithTemplateInstance_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UserDefinedAttributeWithTemplateInstance_2")) return false;
        UserDefinedAttributeWithTemplateInstance_2_0(b, l + 1);
        return true;
    }

    // '(' ArgumentList? ')'
    private static boolean UserDefinedAttributeWithTemplateInstance_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UserDefinedAttributeWithTemplateInstance_2_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && UserDefinedAttributeWithTemplateInstance_2_0_1(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // ArgumentList?
    private static boolean UserDefinedAttributeWithTemplateInstance_2_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UserDefinedAttributeWithTemplateInstance_2_0_1")) return false;
        ArgumentParser.ArgumentList(b, l + 1);
        return true;
    }
}
