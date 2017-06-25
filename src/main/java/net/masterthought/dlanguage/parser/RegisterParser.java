package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.REGISTER;
import static net.masterthought.dlanguage.psi.DLanguageTypes.REGISTER_64;

/**
 * Created by francis on 6/25/2017.
 */
public class RegisterParser {
    /* ********************************************************** */
    // 'AL' 'AH' 'AX' 'EAX'
    //     | 'BL' 'BH' 'BX' 'EBX'
    //     | 'CL' 'CH' 'CX' 'ECX'
    //     | 'DL' 'DH' 'DX' 'EDX'
    //     | 'BP' 'EBP'
    //     | 'SP' 'ESP'
    //     | 'DI' 'EDI'
    //     | 'SI' 'ESI'
    //     | 'ES' 'CS SS DS GS FS'
    //     | 'CR0' 'CR2' 'CR3' 'CR4'
    //     | 'DR0' 'DR1' 'DR2' 'DR3' 'DR6' 'DR7'
    //     | 'TR3' 'TR4' 'TR5' 'TR6' 'TR7'
    //     | 'ST'
    //     | 'ST(0)' 'ST(1)' 'ST(2)' 'ST(3)' 'ST(4)' 'ST(5)' 'ST(6)' 'ST(7)'
    //     | 'MM0'  'MM1'  'MM2'  'MM3'  'MM4'  'MM5'  'MM6'  'MM7'
    //     | 'XMM0' 'XMM1' 'XMM2' 'XMM3' 'XMM4' 'XMM5' 'XMM6' 'XMM7'
    //     | 'AL' | 'AH' | 'AX' | 'EAX'
    //     | 'BL' | 'BH' | 'BX' | 'EBX'
    //     | 'CL' | 'CH' | 'CX' | 'ECX'
    //     | 'DL' | 'DH' | 'DX' | 'EDX'
    //     | 'BP' | 'EBP'
    //     | 'SP' | 'ESP'
    //     | 'DI' | 'EDI'
    //     | 'SI' | 'ESI'
    //     | 'ES' | 'CS SS DS GS FS'
    //     | 'CR0' | 'CR2' | 'CR3' | 'CR4'
    //     | 'DR0' | 'DR1' | 'DR2' | 'DR3' | 'DR6' | 'DR7'
    //     | 'TR3' | 'TR4' | 'TR5' | 'TR6' | 'TR7'
    //     | 'ST(0)' | 'ST(1)' | 'ST(2)' | 'ST(3)' | 'ST(4)' | 'ST(5)' | 'ST(6)' | 'ST(7)'
    //     | 'MM0' | 'MM1' | 'MM2' | 'MM3' | 'MM4' | 'MM5' | 'MM6' | 'MM7'
    //     | 'XMM0' | 'XMM1' | 'XMM2' | 'XMM3' | 'XMM4' | 'XMM5' | 'XMM6' | 'XMM7'
    public static boolean Register(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, REGISTER, "<register>");
        r = Register_0(b, l + 1);
        if (!r) r = Register_1(b, l + 1);
        if (!r) r = Register_2(b, l + 1);
        if (!r) r = Register_3(b, l + 1);
        if (!r) r = Register_4(b, l + 1);
        if (!r) r = Register_5(b, l + 1);
        if (!r) r = Register_6(b, l + 1);
        if (!r) r = Register_7(b, l + 1);
        if (!r) r = Register_8(b, l + 1);
        if (!r) r = Register_9(b, l + 1);
        if (!r) r = Register_10(b, l + 1);
        if (!r) r = Register_11(b, l + 1);
        if (!r) r = consumeToken(b, "ST");
        if (!r) r = Register_13(b, l + 1);
        if (!r) r = Register_14(b, l + 1);
        if (!r) r = Register_15(b, l + 1);
        if (!r) r = consumeToken(b, "AL");
        if (!r) r = consumeToken(b, "AH");
        if (!r) r = consumeToken(b, "AX");
        if (!r) r = consumeToken(b, "EAX");
        if (!r) r = consumeToken(b, "BL");
        if (!r) r = consumeToken(b, "BH");
        if (!r) r = consumeToken(b, "BX");
        if (!r) r = consumeToken(b, "EBX");
        if (!r) r = consumeToken(b, "CL");
        if (!r) r = consumeToken(b, "CH");
        if (!r) r = consumeToken(b, "CX");
        if (!r) r = consumeToken(b, "ECX");
        if (!r) r = consumeToken(b, "DL");
        if (!r) r = consumeToken(b, "DH");
        if (!r) r = consumeToken(b, "DX");
        if (!r) r = consumeToken(b, "EDX");
        if (!r) r = consumeToken(b, "BP");
        if (!r) r = consumeToken(b, "EBP");
        if (!r) r = consumeToken(b, "SP");
        if (!r) r = consumeToken(b, "ESP");
        if (!r) r = consumeToken(b, "DI");
        if (!r) r = consumeToken(b, "EDI");
        if (!r) r = consumeToken(b, "SI");
        if (!r) r = consumeToken(b, "ESI");
        if (!r) r = consumeToken(b, "ES");
        if (!r) r = consumeToken(b, "CS SS DS GS FS");
        if (!r) r = consumeToken(b, "CR0");
        if (!r) r = consumeToken(b, "CR2");
        if (!r) r = consumeToken(b, "CR3");
        if (!r) r = consumeToken(b, "CR4");
        if (!r) r = consumeToken(b, "DR0");
        if (!r) r = consumeToken(b, "DR1");
        if (!r) r = consumeToken(b, "DR2");
        if (!r) r = consumeToken(b, "DR3");
        if (!r) r = consumeToken(b, "DR6");
        if (!r) r = consumeToken(b, "DR7");
        if (!r) r = consumeToken(b, "TR3");
        if (!r) r = consumeToken(b, "TR4");
        if (!r) r = consumeToken(b, "TR5");
        if (!r) r = consumeToken(b, "TR6");
        if (!r) r = consumeToken(b, "TR7");
        if (!r) r = consumeToken(b, "ST(0)");
        if (!r) r = consumeToken(b, "ST(1)");
        if (!r) r = consumeToken(b, "ST(2)");
        if (!r) r = consumeToken(b, "ST(3)");
        if (!r) r = consumeToken(b, "ST(4)");
        if (!r) r = consumeToken(b, "ST(5)");
        if (!r) r = consumeToken(b, "ST(6)");
        if (!r) r = consumeToken(b, "ST(7)");
        if (!r) r = consumeToken(b, "MM0");
        if (!r) r = consumeToken(b, "MM1");
        if (!r) r = consumeToken(b, "MM2");
        if (!r) r = consumeToken(b, "MM3");
        if (!r) r = consumeToken(b, "MM4");
        if (!r) r = consumeToken(b, "MM5");
        if (!r) r = consumeToken(b, "MM6");
        if (!r) r = consumeToken(b, "MM7");
        if (!r) r = consumeToken(b, "XMM0");
        if (!r) r = consumeToken(b, "XMM1");
        if (!r) r = consumeToken(b, "XMM2");
        if (!r) r = consumeToken(b, "XMM3");
        if (!r) r = consumeToken(b, "XMM4");
        if (!r) r = consumeToken(b, "XMM5");
        if (!r) r = consumeToken(b, "XMM6");
        if (!r) r = consumeToken(b, "XMM7");
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // 'AL' 'AH' 'AX' 'EAX'
    private static boolean Register_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "AL");
        r = r && consumeToken(b, "AH");
        r = r && consumeToken(b, "AX");
        r = r && consumeToken(b, "EAX");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'BL' 'BH' 'BX' 'EBX'
    private static boolean Register_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "BL");
        r = r && consumeToken(b, "BH");
        r = r && consumeToken(b, "BX");
        r = r && consumeToken(b, "EBX");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'CL' 'CH' 'CX' 'ECX'
    private static boolean Register_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "CL");
        r = r && consumeToken(b, "CH");
        r = r && consumeToken(b, "CX");
        r = r && consumeToken(b, "ECX");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'DL' 'DH' 'DX' 'EDX'
    private static boolean Register_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register_3")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "DL");
        r = r && consumeToken(b, "DH");
        r = r && consumeToken(b, "DX");
        r = r && consumeToken(b, "EDX");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'BP' 'EBP'
    private static boolean Register_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register_4")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "BP");
        r = r && consumeToken(b, "EBP");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'SP' 'ESP'
    private static boolean Register_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register_5")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "SP");
        r = r && consumeToken(b, "ESP");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'DI' 'EDI'
    private static boolean Register_6(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register_6")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "DI");
        r = r && consumeToken(b, "EDI");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'SI' 'ESI'
    private static boolean Register_7(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register_7")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "SI");
        r = r && consumeToken(b, "ESI");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'ES' 'CS SS DS GS FS'
    private static boolean Register_8(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register_8")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "ES");
        r = r && consumeToken(b, "CS SS DS GS FS");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'CR0' 'CR2' 'CR3' 'CR4'
    private static boolean Register_9(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register_9")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "CR0");
        r = r && consumeToken(b, "CR2");
        r = r && consumeToken(b, "CR3");
        r = r && consumeToken(b, "CR4");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'DR0' 'DR1' 'DR2' 'DR3' 'DR6' 'DR7'
    private static boolean Register_10(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register_10")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "DR0");
        r = r && consumeToken(b, "DR1");
        r = r && consumeToken(b, "DR2");
        r = r && consumeToken(b, "DR3");
        r = r && consumeToken(b, "DR6");
        r = r && consumeToken(b, "DR7");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'TR3' 'TR4' 'TR5' 'TR6' 'TR7'
    private static boolean Register_11(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register_11")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "TR3");
        r = r && consumeToken(b, "TR4");
        r = r && consumeToken(b, "TR5");
        r = r && consumeToken(b, "TR6");
        r = r && consumeToken(b, "TR7");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'ST(0)' 'ST(1)' 'ST(2)' 'ST(3)' 'ST(4)' 'ST(5)' 'ST(6)' 'ST(7)'
    private static boolean Register_13(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register_13")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "ST(0)");
        r = r && consumeToken(b, "ST(1)");
        r = r && consumeToken(b, "ST(2)");
        r = r && consumeToken(b, "ST(3)");
        r = r && consumeToken(b, "ST(4)");
        r = r && consumeToken(b, "ST(5)");
        r = r && consumeToken(b, "ST(6)");
        r = r && consumeToken(b, "ST(7)");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'MM0'  'MM1'  'MM2'  'MM3'  'MM4'  'MM5'  'MM6'  'MM7'
    private static boolean Register_14(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register_14")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "MM0");
        r = r && consumeToken(b, "MM1");
        r = r && consumeToken(b, "MM2");
        r = r && consumeToken(b, "MM3");
        r = r && consumeToken(b, "MM4");
        r = r && consumeToken(b, "MM5");
        r = r && consumeToken(b, "MM6");
        r = r && consumeToken(b, "MM7");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'XMM0' 'XMM1' 'XMM2' 'XMM3' 'XMM4' 'XMM5' 'XMM6' 'XMM7'
    private static boolean Register_15(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register_15")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "XMM0");
        r = r && consumeToken(b, "XMM1");
        r = r && consumeToken(b, "XMM2");
        r = r && consumeToken(b, "XMM3");
        r = r && consumeToken(b, "XMM4");
        r = r && consumeToken(b, "XMM5");
        r = r && consumeToken(b, "XMM6");
        r = r && consumeToken(b, "XMM7");
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'RAX'  'RBX'  'RCX'  'RDX'
    //     | 'BPL'  'RBP'
    //     | 'SPL'  'RSP'
    //     | 'DIL'  'RDI'
    //     | 'SIL'  'RSI'
    //     | 'R8B'  'R8W'  'R8D'  'R8'
    //     | 'R9B'  'R9W'  'R9D'  'R9'
    //     | 'R10B' 'R10W' 'R10D' 'R10'
    //     | 'R11B' 'R11W' 'R11D' 'R11'
    //     | 'R12B' 'R12W' 'R12D' 'R12'
    //     | 'R13B' 'R13W' 'R13D' 'R13'
    //     | 'R14B' 'R14W' 'R14D' 'R14'
    //     | 'R15B' 'R15W' 'R15D' 'R15'
    //     | 'XMM8' 'XMM9' 'XMM10' 'XMM11' 'XMM12' 'XMM13' 'XMM14' 'XMM15'
    //     | 'YMM0' 'YMM1' 'YMM2' 'YMM3' 'YMM4' 'YMM5' 'YMM6' 'YMM7'
    //     | 'YMM8' 'YMM9' 'YMM10' 'YMM11' 'YMM12' 'YMM13' 'YMM14' 'YMM15'
    public static boolean Register64(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, REGISTER_64, "<register 64>");
        r = Register64_0(b, l + 1);
        if (!r) r = Register64_1(b, l + 1);
        if (!r) r = Register64_2(b, l + 1);
        if (!r) r = Register64_3(b, l + 1);
        if (!r) r = Register64_4(b, l + 1);
        if (!r) r = Register64_5(b, l + 1);
        if (!r) r = Register64_6(b, l + 1);
        if (!r) r = Register64_7(b, l + 1);
        if (!r) r = Register64_8(b, l + 1);
        if (!r) r = Register64_9(b, l + 1);
        if (!r) r = Register64_10(b, l + 1);
        if (!r) r = Register64_11(b, l + 1);
        if (!r) r = Register64_12(b, l + 1);
        if (!r) r = Register64_13(b, l + 1);
        if (!r) r = Register64_14(b, l + 1);
        if (!r) r = Register64_15(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // 'RAX'  'RBX'  'RCX'  'RDX'
    private static boolean Register64_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "RAX");
        r = r && consumeToken(b, "RBX");
        r = r && consumeToken(b, "RCX");
        r = r && consumeToken(b, "RDX");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'BPL'  'RBP'
    private static boolean Register64_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64_1")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "BPL");
        r = r && consumeToken(b, "RBP");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'SPL'  'RSP'
    private static boolean Register64_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64_2")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "SPL");
        r = r && consumeToken(b, "RSP");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'DIL'  'RDI'
    private static boolean Register64_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64_3")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "DIL");
        r = r && consumeToken(b, "RDI");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'SIL'  'RSI'
    private static boolean Register64_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64_4")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "SIL");
        r = r && consumeToken(b, "RSI");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'R8B'  'R8W'  'R8D'  'R8'
    private static boolean Register64_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64_5")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "R8B");
        r = r && consumeToken(b, "R8W");
        r = r && consumeToken(b, "R8D");
        r = r && consumeToken(b, "R8");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'R9B'  'R9W'  'R9D'  'R9'
    private static boolean Register64_6(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64_6")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "R9B");
        r = r && consumeToken(b, "R9W");
        r = r && consumeToken(b, "R9D");
        r = r && consumeToken(b, "R9");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'R10B' 'R10W' 'R10D' 'R10'
    private static boolean Register64_7(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64_7")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "R10B");
        r = r && consumeToken(b, "R10W");
        r = r && consumeToken(b, "R10D");
        r = r && consumeToken(b, "R10");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'R11B' 'R11W' 'R11D' 'R11'
    private static boolean Register64_8(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64_8")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "R11B");
        r = r && consumeToken(b, "R11W");
        r = r && consumeToken(b, "R11D");
        r = r && consumeToken(b, "R11");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'R12B' 'R12W' 'R12D' 'R12'
    private static boolean Register64_9(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64_9")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "R12B");
        r = r && consumeToken(b, "R12W");
        r = r && consumeToken(b, "R12D");
        r = r && consumeToken(b, "R12");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'R13B' 'R13W' 'R13D' 'R13'
    private static boolean Register64_10(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64_10")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "R13B");
        r = r && consumeToken(b, "R13W");
        r = r && consumeToken(b, "R13D");
        r = r && consumeToken(b, "R13");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'R14B' 'R14W' 'R14D' 'R14'
    private static boolean Register64_11(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64_11")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "R14B");
        r = r && consumeToken(b, "R14W");
        r = r && consumeToken(b, "R14D");
        r = r && consumeToken(b, "R14");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'R15B' 'R15W' 'R15D' 'R15'
    private static boolean Register64_12(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64_12")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "R15B");
        r = r && consumeToken(b, "R15W");
        r = r && consumeToken(b, "R15D");
        r = r && consumeToken(b, "R15");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'XMM8' 'XMM9' 'XMM10' 'XMM11' 'XMM12' 'XMM13' 'XMM14' 'XMM15'
    private static boolean Register64_13(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64_13")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "XMM8");
        r = r && consumeToken(b, "XMM9");
        r = r && consumeToken(b, "XMM10");
        r = r && consumeToken(b, "XMM11");
        r = r && consumeToken(b, "XMM12");
        r = r && consumeToken(b, "XMM13");
        r = r && consumeToken(b, "XMM14");
        r = r && consumeToken(b, "XMM15");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'YMM0' 'YMM1' 'YMM2' 'YMM3' 'YMM4' 'YMM5' 'YMM6' 'YMM7'
    private static boolean Register64_14(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64_14")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "YMM0");
        r = r && consumeToken(b, "YMM1");
        r = r && consumeToken(b, "YMM2");
        r = r && consumeToken(b, "YMM3");
        r = r && consumeToken(b, "YMM4");
        r = r && consumeToken(b, "YMM5");
        r = r && consumeToken(b, "YMM6");
        r = r && consumeToken(b, "YMM7");
        exit_section_(b, m, null, r);
        return r;
    }

    // 'YMM8' 'YMM9' 'YMM10' 'YMM11' 'YMM12' 'YMM13' 'YMM14' 'YMM15'
    private static boolean Register64_15(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register64_15")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, "YMM8");
        r = r && consumeToken(b, "YMM9");
        r = r && consumeToken(b, "YMM10");
        r = r && consumeToken(b, "YMM11");
        r = r && consumeToken(b, "YMM12");
        r = r && consumeToken(b, "YMM13");
        r = r && consumeToken(b, "YMM14");
        r = r && consumeToken(b, "YMM15");
        exit_section_(b, m, null, r);
        return r;
    }
}
