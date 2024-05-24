package io.github.intellij.dlanguage.highlighting;

public class DlangHighlightingLexerTest extends DHighlightingLexerTestBase {

    public DlangHighlightingLexerTest() {
        super("highlighting");
    }

    // shebang
    public void testsyntax_highlighting() {
        doTest(true, true);
    }

    // keywords
    public void testkeyword_abstract() { doTest(true, true); }
    public void testkeyword_deprecated() { doTest(true, true); }
    public void testkeyword_final() { doTest(true, true); }
    public void testkeyword_nothrow() { doTest(true, true); }
    public void testkeyword_override() { doTest(true, true); }
    public void testkeyword_pure() { doTest(true, true); }
    public void testkeyword_scope() { doTest(true, true); }
    public void testkeyword_static() { doTest(true, true); }
    public void testkeyword_synchronized() { doTest(true, true); }
    public void testkeyword_ref() { doTest(true, true); }
    public void testkeyword_const() { doTest(true, true); }
    public void testkeyword_immutable() { doTest(true, true); }
    public void testkeyword_inout() { doTest(true, true); }
    public void testkeyword_shared() { doTest(true, true); }
    public void testkeyword_auto() { doTest(true, true); }
    public void testkeyword_alias() { doTest(true, true); }
    public void testkeyword_align() { doTest(true, true); }
    public void testkeyword_asm() { doTest(true, true); }
    public void testkeyword_assert() { doTest(true, true); }
    public void testkeyword_break() { doTest(true, true); }
    public void testkeyword_case() { doTest(true, true); }
    public void testkeyword_cast() { doTest(true, true); }
    public void testkeyword_catch() { doTest(true, true); }
    public void testkeyword_class() { doTest(true, true); }
    public void testkeyword_continue() { doTest(true, true); }
    public void testkeyword_debug() { doTest(true, true); }
    public void testkeyword_default() { doTest(true, true); }
    public void testkeyword_delegate() { doTest(true, true); }
    public void testkeyword_delete() { doTest(true, true); }
    public void testkeyword_do() { doTest(true, true); }
    public void testkeyword_else() { doTest(true, true); }
    public void testkeyword_enum() { doTest(true, true); }
    public void testkeyword_extern() { doTest(true, true); }
    public void testkeyword_false() { doTest(true, true); }
    public void testkeyword_finally() { doTest(true, true); }
    public void testkeyword_for() { doTest(true, true); }
    public void testkeyword_foreach() { doTest(true, true); }
    public void testkeyword_foreach_reverse() { doTest(true, true); }
    public void testkeyword_function() { doTest(true, true); }
    public void testkeyword_goto() { doTest(true, true); }
    public void testkeyword_if() { doTest(true, true); }
    public void testkeyword_import() { doTest(true, true); }
    public void testkeyword_in() { doTest(true, true); }
    public void testkeyword_interface() { doTest(true, true); }
    public void testkeyword_invariant() { doTest(true, true); }
    public void testkeyword_is() { doTest(true, true); }
    public void testkeyword_lazy() { doTest(true, true); }
    public void testkeyword_macro() { doTest(true, true); }
    public void testkeyword_mixin() { doTest(true, true); }
    public void testkeyword_module() { doTest(true, true); }
    public void testkeyword_new() { doTest(true, true); }
    public void testkeyword_null() { doTest(true, true); }
    public void testkeyword_out() { doTest(true, true); }
    public void testkeyword_pragma() { doTest(true, true); }
    public void testkeyword_return() { doTest(true, true); }
    public void testkeyword_struct() { doTest(true, true); }
    public void testkeyword_super() { doTest(true, true); }
    public void testkeyword_switch() { doTest(true, true); }
    public void testkeyword_template() { doTest(true, true); }
    public void testkeyword_this() { doTest(true, true); }
    public void testkeyword_throw() { doTest(true, true); }
    public void testkeyword_true() { doTest(true, true); }
    public void testkeyword_try() { doTest(true, true); }
    public void testkeyword_typedef() { doTest(true, true); }
    public void testkeyword_typeid() { doTest(true, true); }
    public void testkeyword_typeof() { doTest(true, true); }
    public void testkeyword_union() { doTest(true, true); }
    public void testkeyword_unittest() { doTest(true, true); }
    public void testkeyword_version() { doTest(true, true); }
    public void testkeyword_volatile() { doTest(true, true); }
    public void testkeyword_while() { doTest(true, true); }
    public void testkeyword_with() { doTest(true, true); }
    public void testkeyword___traits() { doTest(true, true); }
    public void testkeyword___gshared() { doTest(true, true); }
    public void testkeyword___thread() { doTest(true, true); }
    public void testkeyword___vector() { doTest(true, true); }
    public void testkeyword___file__() { doTest(true, true); }
    public void testkeyword___line__() { doTest(true, true); }
    public void testkeyword___module__() { doTest(true, true); }
    public void testkeyword___function__() { doTest(true, true); }
    public void testkeyword___pretty_function__() { doTest(true, true); }
    public void testkeyword___date__() { doTest(true, true); }
    public void testkeyword___time__() { doTest(true, true); }
    public void testkeyword___timestamp__() { doTest(true, true); }
    public void testkeyword___vendor__() { doTest(true, true); }
    public void testkeyword___version__() { doTest(true, true); }

    // comments
    public void testcomment_doc_line() { doTest(true, true); }
    public void testcomment_doc_multi() { doTest(true, true); }
    public void testcomment_doc_nested() { doTest(true, true); }
    public void testcomment_line() { doTest(true, true); }
    public void testcomment_multi() { doTest(true, true); }
    public void testcomment_nested() { doTest(true, true); }

    // Ensure that the parser don’t crash if a comment is unclosed
    public void testcomment_unclosed() { doTest("/* unclosed comment", "DlangTokenType.BLOCK_COMMENT ('/* unclosed comment')", createLexer()); }

    // floats
    public void testfloat_decimal() { doTest(true, true); }
    public void testfloat_hex() { doTest(true, true); }

    // integers
    public void testinteger_binary() { doTest(true, true); }
    public void testinteger_decimal() { doTest(true, true); }
    public void testinteger_hex() { doTest(true, true); }
    public void testinteger_octal() { doTest(true, true); }

    // strings
    public void teststring_delim() { doTest(true, true); }
    public void teststring_dq() { doTest(true, true); }
    public void teststring_hex() { doTest(true, true); }
    public void teststring_tokens() { doTest(true, true); }
    public void teststring_wysiwyg() { doTest(true, true); }
    public void testchar() {
        doTest(true, true);
    }
    public void teststrings_escapes() {
        doTest(true, true);
    }

    // tokens
    public void testtokens_and() { doTest(true, true); }
    public void testtokens_and_assign() { doTest(true, true); }
    public void testtokens_assign() { doTest(true, true); }
    public void testtokens_at() { doTest(true, true); }
    public void testtokens_close_brace() { doTest(true, true); }
    public void testtokens_close_bracket() { doTest(true, true); }
    public void testtokens_close_parens() { doTest(true, true); }
    public void testtokens_colon() { doTest(true, true); }
    public void testtokens_comma() { doTest(true, true); }
    public void testtokens_concat() { doTest(true, true); }
    public void testtokens_concat_assign() { doTest(true, true); }
    public void testtokens_decrement() { doTest(true, true); }
    public void testtokens_div() { doTest(true, true); }
    public void testtokens_div_assign() { doTest(true, true); }
    public void testtokens_dollar() { doTest(true, true); }
    public void testtokens_dot() { doTest(true, true); }
    public void testtokens_double_dot() { doTest(true, true); }
    public void testtokens_equals() { doTest(true, true); }
    public void testtokens_greater_equal() { doTest(true, true); }
    public void testtokens_greater_than() { doTest(true, true); }
    public void testtokens_increment() { doTest(true, true); }
    public void testtokens_lambda() { doTest(true, true); }
    public void testtokens_left_shift() { doTest(true, true); }
    public void testtokens_left_shift_assign() { doTest(true, true); }
    public void testtokens_less_equal() { doTest(true, true); }
    public void testtokens_less_than() { doTest(true, true); }
    public void testtokens_logical_and() { doTest(true, true); }
    public void testtokens_logical_or() { doTest(true, true); }
    public void testtokens_minus() { doTest(true, true); }
    public void testtokens_minus_assign() { doTest(true, true); }
    public void testtokens_mod() { doTest(true, true); }
    public void testtokens_mod_assign() { doTest(true, true); }
    public void testtokens_mult_assign() { doTest(true, true); }
    public void testtokens_not() { doTest(true, true); }
    public void testtokens_not_equal() { doTest(true, true); }
    public void testtokens_open_brace() { doTest(true, true); }
    public void testtokens_open_bracket() { doTest(true, true); }
    public void testtokens_open_parens() { doTest(true, true); }
    public void testtokens_or() { doTest(true, true); }
    public void testtokens_or_assign() { doTest(true, true); }
    public void testtokens_plus() { doTest(true, true); }
    public void testtokens_plus_assign() { doTest(true, true); }
    public void testtokens_pow() { doTest(true, true); }
    public void testtokens_pow_assign() { doTest(true, true); }
    public void testtokens_question() { doTest(true, true); }
    public void testtokens_right_shift() { doTest(true, true); }
    public void testtokens_right_shift_assign() { doTest(true, true); }
    public void testtokens_semicolon() { doTest(true, true); }
    public void testtokens_star() { doTest(true, true); }
    public void testtokens_triple_dot() { doTest(true, true); }
    public void testtokens_triple_rshift() { doTest(true, true); }
    public void testtokens_triple_rshift_assign() { doTest(true, true); }
    public void testtokens_unordered_g() { doTest(true, true); }
    public void testtokens_unordered_ge() { doTest(true, true); }
    public void testtokens_unordered_l() { doTest(true, true); }
    public void testtokens_unordered_le() { doTest(true, true); }
    public void testtokens_xor() { doTest(true, true); }
    public void testtokens_xor_assign() { doTest(true, true); }
    public void testissue77() {doTest(true,true);}
    public void testissue365() {doTest(true,true);}
}
