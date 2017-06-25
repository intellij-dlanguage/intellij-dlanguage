package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 6/25/2017.
 */
public class TypeParser {
    /* ********************************************************** */
    // BasicTypeX
    //     | ('.')? IdentifierList
    //     | Typeof ('.' IdentifierList)?
    //     | '(' Type ')'
    //     | TypeVector
    //     | '(' TypeVector ')'
    public static boolean BasicType(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, BASIC_TYPE, "<basic type>");
        r = BasicTypeX(b, l + 1);
        if (!r) r = BasicType_1(b, l + 1);
        if (!r) r = BasicType_2(b, l + 1);
        if (!r) r = BasicType_3(b, l + 1);
        if (!r) r = TypeVector(b, l + 1);
        if (!r) r = BasicType_5(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ('.')? IdentifierList
    private static boolean BasicType_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = BasicType_1_0(b, l + 1);
        r = r && IdentifierListsParser.IdentifierList(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('.')?
    private static boolean BasicType_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType_1_0")) return false;
        consumeToken(b, OP_DOT);
        return true;
    }

    // Typeof ('.' IdentifierList)?
    private static boolean BasicType_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = Typeof(b, l + 1);
        r = r && BasicType_2_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('.' IdentifierList)?
    private static boolean BasicType_2_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType_2_1")) return false;
        BasicType_2_1_0(b, l + 1);
        return true;
    }

    // '.' IdentifierList
    private static boolean BasicType_2_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType_2_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_DOT);
        r = r && IdentifierListsParser.IdentifierList(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '(' Type ')'
    private static boolean BasicType_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType_3")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && Type(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // '(' TypeVector ')'
    private static boolean BasicType_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType_5")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_PAR_LEFT);
        r = r && TypeVector(b, l + 1);
        r = r && consumeToken(b, OP_PAR_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // BasicType2X BasicType2?
    public static boolean BasicType2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, BASIC_TYPE_2, "<basic type 2>");
        r = BasicType2X(b, l + 1);
        r = r && BasicType2_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // BasicType2?
    private static boolean BasicType2_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType2_1")) return false;
        BasicType2(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // '*'
    //     | '[' Type? ']'
    //     | '[' AssignExpression ']'
    //     | '[' AssignExpression '..' AssignExpression ']'
    //     | 'delegate' Parameters MemberFunctionAttributes?
    //     | 'function' Parameters FunctionAttributes?
    public static boolean BasicType2X(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType2X")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, BASIC_TYPE_2_X, "<basic type 2 x>");
        r = consumeToken(b, OP_ASTERISK);
        if (!r) r = BasicType2X_1(b, l + 1);
        if (!r) r = BasicType2X_2(b, l + 1);
        if (!r) r = BasicType2X_3(b, l + 1);
        if (!r) r = BasicType2X_4(b, l + 1);
        if (!r) r = BasicType2X_5(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // '[' Type? ']'
    private static boolean BasicType2X_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType2X_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BRACKET_LEFT);
        r = r && BasicType2X_1_1(b, l + 1);
        r = r && consumeToken(b, OP_BRACKET_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // Type?
    private static boolean BasicType2X_1_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType2X_1_1")) return false;
        Type(b, l + 1);
        return true;
    }

    // '[' AssignExpression ']'
    private static boolean BasicType2X_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType2X_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BRACKET_LEFT);
        r = r && ExpressionParser.AssignExpression(b, l + 1);
        r = r && consumeToken(b, OP_BRACKET_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // '[' AssignExpression '..' AssignExpression ']'
    private static boolean BasicType2X_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType2X_3")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_BRACKET_LEFT);
        r = r && ExpressionParser.AssignExpression(b, l + 1);
        r = r && consumeToken(b, OP_DDOT);
        r = r && ExpressionParser.AssignExpression(b, l + 1);
        r = r && consumeToken(b, OP_BRACKET_RIGHT);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'delegate' Parameters MemberFunctionAttributes?
    private static boolean BasicType2X_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType2X_4")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_DELEGATE);
        r = r && ParameterParser.Parameters(b, l + 1);
        r = r && BasicType2X_4_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // MemberFunctionAttributes?
    private static boolean BasicType2X_4_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType2X_4_2")) return false;
        DeclarationParser.MemberFunctionAttributes(b, l + 1);
        return true;
    }

    // 'function' Parameters FunctionAttributes?
    private static boolean BasicType2X_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType2X_5")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, KW_FUNCTION);
        r = r && ParameterParser.Parameters(b, l + 1);
        r = r && BasicType2X_5_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // FunctionAttributes?
    private static boolean BasicType2X_5_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicType2X_5_2")) return false;
        DeclarationParser.FunctionAttributes(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'bool'
    //     | 'byte'
    //     | 'ubyte'
    //     | 'short'
    //     | 'ushort'
    //     | 'int'
    //     | 'uint'
    //     | 'long'
    //     | 'ulong'
    //     | 'char'
    //     | 'wchar'
    //     | 'dchar'
    //     | 'float'
    //     | 'double'
    //     | 'real'
    //     | 'ifloat'
    //     | 'idouble'
    //     | 'ireal'
    //     | 'cfloat'
    //     | 'cdouble'
    //     | 'creal'
    //     | 'void'
    public static boolean BasicTypeX(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "BasicTypeX")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, BASIC_TYPE_X, "<basic type x>");
        r = consumeToken(b, KW_BOOL);
        if (!r) r = consumeToken(b, KW_BYTE);
        if (!r) r = consumeToken(b, KW_UBYTE);
        if (!r) r = consumeToken(b, KW_SHORT);
        if (!r) r = consumeToken(b, KW_USHORT);
        if (!r) r = consumeToken(b, KW_INT);
        if (!r) r = consumeToken(b, KW_UINT);
        if (!r) r = consumeToken(b, KW_LONG);
        if (!r) r = consumeToken(b, KW_ULONG);
        if (!r) r = consumeToken(b, KW_CHAR);
        if (!r) r = consumeToken(b, KW_WCHAR);
        if (!r) r = consumeToken(b, KW_DCHAR);
        if (!r) r = consumeToken(b, KW_FLOAT);
        if (!r) r = consumeToken(b, KW_DOUBLE);
        if (!r) r = consumeToken(b, KW_REAL);
        if (!r) r = consumeToken(b, KW_IFLOAT);
        if (!r) r = consumeToken(b, KW_IDOUBLE);
        if (!r) r = consumeToken(b, KW_IREAL);
        if (!r) r = consumeToken(b, KW_CFLOAT);
        if (!r) r = consumeToken(b, KW_CDOUBLE);
        if (!r) r = consumeToken(b, KW_CREAL);
        if (!r) r = consumeToken(b, KW_VOID);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // TypeCtors? BasicType BasicType2?
    public static boolean Type(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Type")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TYPE, "<type>");
        r = Type_0(b, l + 1);
        r = r && BasicType(b, l + 1);
        r = r && Type_2(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // TypeCtors?
    private static boolean Type_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Type_0")) return false;
        TypeCtors(b, l + 1);
        return true;
    }

    // BasicType2?
    private static boolean Type_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Type_2")) return false;
        BasicType2(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // 'const'
    //     | 'immutable'
    //     | 'inout'
    //     | 'shared'
    public static boolean TypeCtor(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TypeCtor")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TYPE_CTOR, "<type ctor>");
        r = consumeToken(b, KW_CONST);
        if (!r) r = consumeToken(b, KW_IMMUTABLE);
        if (!r) r = consumeToken(b, KW_INOUT);
        if (!r) r = consumeToken(b, KW_SHARED);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // TypeCtor [TypeCtors]
    public static boolean TypeCtors(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TypeCtors")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TYPE_CTORS, "<type ctors>");
        r = TypeCtor(b, l + 1);
        r = r && TypeCtors_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // [TypeCtors]
    private static boolean TypeCtors_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TypeCtors_1")) return false;
        TypeCtors(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // TypeVector
    //     | Type
    //     | 'struct'
    //     | 'union'
    //     | 'class'
    //     | 'interface'
    //     | 'enum'
    //     | 'function'
    //     | 'delegate'
    //     | 'super'
    //     | 'const'
    //     | 'immutable'
    //     | 'inout'
    //     | 'shared'
    //     | 'return'
    //     | '__parameters'
    public static boolean TypeSpecialization(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TypeSpecialization")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TYPE_SPECIALIZATION, "<type specialization>");
        r = TypeVector(b, l + 1);
        if (!r) r = Type(b, l + 1);
        if (!r) r = consumeToken(b, KW_STRUCT);
        if (!r) r = consumeToken(b, KW_UNION);
        if (!r) r = consumeToken(b, KW_CLASS);
        if (!r) r = consumeToken(b, KW_INTERFACE);
        if (!r) r = consumeToken(b, KW_ENUM);
        if (!r) r = consumeToken(b, KW_FUNCTION);
        if (!r) r = consumeToken(b, KW_DELEGATE);
        if (!r) r = consumeToken(b, KW_SUPER);
        if (!r) r = consumeToken(b, KW_CONST);
        if (!r) r = consumeToken(b, KW_IMMUTABLE);
        if (!r) r = consumeToken(b, KW_INOUT);
        if (!r) r = consumeToken(b, KW_SHARED);
        if (!r) r = consumeToken(b, KW_RETURN);
        if (!r) r = consumeToken(b, KW___PARAMETERS);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // '__vector' '(' Type')'
    public static boolean TypeVector(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TypeVector")) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TYPE_VECTOR, "<type vector>");
        r = consumeToken(b, "__vector");
        p = r; // pin = 1
        r = r && report_error_(b, consumeToken(b, OP_PAR_LEFT));
        r = p && report_error_(b, Type(b, l + 1)) && r;
        r = p && consumeToken(b, OP_PAR_RIGHT) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    /* ********************************************************** */
    // 'typeof' '(' (Expression | 'return') ')'
    public static boolean Typeof(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Typeof")) return false;
        if (!nextTokenIs(b, KW_TYPEOF)) return false;
        boolean r, p;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, TYPEOF, null);
        r = consumeTokens(b, 1, KW_TYPEOF, OP_PAR_LEFT);
        p = r; // pin = 1
        r = r && report_error_(b, Typeof_2(b, l + 1));
        r = p && consumeToken(b, OP_PAR_RIGHT) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // Expression | 'return'
    private static boolean Typeof_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Typeof_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = ExpressionParser.Expression(b, l + 1);
        if (!r) r = consumeToken(b, KW_RETURN);
        exit_section_(b, m, null, r);
        return r;
    }
}
