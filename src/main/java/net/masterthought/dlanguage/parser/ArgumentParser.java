package net.masterthought.dlanguage.parser;

import com.intellij.lang.PsiBuilder;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static net.masterthought.dlanguage.psi.DLanguageTypes.ARGUMENT_LIST;
import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_COMMA;

/**
 * Created by francis on 6/25/2017.
 */
public class ArgumentParser {
    /* ********************************************************** */
    // ArgumentListMember (','  ArgumentListMember)* ','?
    public static boolean ArgumentList(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ArgumentList")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b, l, _NONE_, ARGUMENT_LIST, "<argument list>");
        r = ArgumentListMember(b, l + 1);
        r = r && ArgumentList_1(b, l + 1);
        r = r && ArgumentList_2(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (','  ArgumentListMember)*
    private static boolean ArgumentList_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ArgumentList_1")) return false;
        int c = current_position_(b);
        while (true) {
            if (!ArgumentList_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "ArgumentList_1", c)) break;
            c = current_position_(b);
        }
        return true;
    }

    // ','  ArgumentListMember
    private static boolean ArgumentList_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ArgumentList_1_0")) return false;
        boolean r;
        PsiBuilder.Marker m = enter_section_(b);
        r = consumeToken(b, OP_COMMA);
        r = r && ArgumentListMember(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ','?
    private static boolean ArgumentList_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ArgumentList_2")) return false;
        consumeToken(b, OP_COMMA);
        return true;
    }

    /* ********************************************************** */
    // AssignExpression
    static boolean ArgumentListMember(PsiBuilder b, int l) {
        return ExpressionParser.AssignExpression(b, l + 1);
    }
}
