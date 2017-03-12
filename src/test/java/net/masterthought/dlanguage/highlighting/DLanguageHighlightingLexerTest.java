package net.masterthought.dlanguage.highlighting;

public class DLanguageHighlightingLexerTest extends DLanguageHighlightingLexerTestBase {

    public DLanguageHighlightingLexerTest() {
        super("highlighting");
    }

    // shebang
    public void testsyntax_highlighting() { doTest(true, true); }

    // keywords
//    public void testkeyword_abstract() { doTestModule(true, true); }
//    public void testkeyword_deprecated() { doTestModule(true, true); }
//    public void testkeyword_final() { doTestModule(true, true); }
//    public void testkeyword_nothrow() { doTestModule(true, true); }
//    public void testkeyword_override() { doTestModule(true, true); }
//    public void testkeyword_pure() { doTestModule(true, true); }
//    public void testkeyword_scope() { doTestModule(true, true); }
//    public void testkeyword_static() { doTestModule(true, true); }
//    public void testkeyword_synchronized() { doTestModule(true, true); }
//    public void testkeyword_ref() { doTestModule(true, true); }
//    public void testkeyword_const() { doTestModule(true, true); }
//    public void testkeyword_immutable() { doTestModule(true, true); }
//    public void testkeyword_inout() { doTestModule(true, true); }
//    public void testkeyword_shared() { doTestModule(true, true); }
//    public void testkeyword_auto() { doTestModule(true, true); }
//    public void testkeyword_alias() { doTestModule(true, true); }
//    public void testkeyword_align() { doTestModule(true, true); }
//    public void testkeyword_asm() { doTestModule(true, true); }
//    public void testkeyword_assert() { doTestModule(true, true); }
//    public void testkeyword_body() { doTestModule(true, true); }
//    public void testkeyword_break() { doTestModule(true, true); }
//    public void testkeyword_case() { doTestModule(true, true); }
//    public void testkeyword_cast() { doTestModule(true, true); }
//    public void testkeyword_catch() { doTestModule(true, true); }
//    public void testkeyword_class() { doTestModule(true, true); }
//    public void testkeyword_continue() { doTestModule(true, true); }
//    public void testkeyword_debug() { doTestModule(true, true); }
//    public void testkeyword_default() { doTestModule(true, true); }
//    public void testkeyword_delegate() { doTestModule(true, true); }
//    public void testkeyword_delete() { doTestModule(true, true); }
//    public void testkeyword_do() { doTestModule(true, true); }
//    public void testkeyword_else() { doTestModule(true, true); }
//    public void testkeyword_enum() { doTestModule(true, true); }
//    public void testkeyword_extern() { doTestModule(true, true); }
//    public void testkeyword_false() { doTestModule(true, true); }
//    public void testkeyword_finally() { doTestModule(true, true); }
//    public void testkeyword_for() { doTestModule(true, true); }
//    public void testkeyword_foreach() { doTestModule(true, true); }
//    public void testkeyword_foreach_reverse() { doTestModule(true, true); }
//    public void testkeyword_function() { doTestModule(true, true); }
//    public void testkeyword_goto() { doTestModule(true, true); }
//    public void testkeyword_if() { doTestModule(true, true); }
//    public void testkeyword_import() { doTestModule(true, true); }
//    public void testkeyword_in() { doTestModule(true, true); }
//    public void testkeyword_interface() { doTestModule(true, true); }
//    public void testkeyword_invariant() { doTestModule(true, true); }
//    public void testkeyword_is() { doTestModule(true, true); }
//    public void testkeyword_lazy() { doTestModule(true, true); }
//    public void testkeyword_macro() { doTestModule(true, true); }
//    public void testkeyword_mixin() { doTestModule(true, true); }
//    public void testkeyword_module() { doTestModule(true, true); }
//    public void testkeyword_new() { doTestModule(true, true); }
//    public void testkeyword_null() { doTestModule(true, true); }
//    public void testkeyword_out() { doTestModule(true, true); }
//    public void testkeyword_pragma() { doTestModule(true, true); }
//    public void testkeyword_return() { doTestModule(true, true); }
//    public void testkeyword_struct() { doTestModule(true, true); }
//    public void testkeyword_super() { doTestModule(true, true); }
//    public void testkeyword_switch() { doTestModule(true, true); }
//    public void testkeyword_template() { doTestModule(true, true); }
//    public void testkeyword_this() { doTestModule(true, true); }
//    public void testkeyword_throw() { doTestModule(true, true); }
//    public void testkeyword_true() { doTestModule(true, true); }
//    public void testkeyword_try() { doTestModule(true, true); }
//    public void testkeyword_typedef() { doTestModule(true, true); }
//    public void testkeyword_typeid() { doTestModule(true, true); }
//    public void testkeyword_typeof() { doTestModule(true, true); }
//    public void testkeyword_union() { doTestModule(true, true); }
//    public void testkeyword_unittest() { doTestModule(true, true); }
//    public void testkeyword_version() { doTestModule(true, true); }
//    public void testkeyword_volatile() { doTestModule(true, true); }
//    public void testkeyword_while() { doTestModule(true, true); }
//    public void testkeyword_with() { doTestModule(true, true); }
//    public void testkeyword___traits() { doTestModule(true, true); }
//    public void testkeyword___gshared() { doTestModule(true, true); }
//    public void testkeyword___thread() { doTestModule(true, true); }
//    public void testkeyword___vector() { doTestModule(true, true); }
//    public void testkeyword___file__() { doTestModule(true, true); }
//    public void testkeyword___line__() { doTestModule(true, true); }
//    public void testkeyword___module__() { doTestModule(true, true); }
//    public void testkeyword___function__() { doTestModule(true, true); }
//    public void testkeyword___pretty_function__() { doTestModule(true, true); }
//    public void testkeyword___date__() { doTestModule(true, true); }
//    public void testkeyword___time__() { doTestModule(true, true); }
//    public void testkeyword___timestamp__() { doTestModule(true, true); }
//    public void testkeyword___vendor__() { doTestModule(true, true); }
//    public void testkeyword___version__() { doTestModule(true, true); }
//
//    // comments
////    public void testcomment_doc_line() { doTestModule(true, true); }
////    public void testcomment_doc_multi() { doTestModule(true, true); }
////    public void testcomment_doc_nested() { doTestModule(true, true); }
//    public void testcomment_line() { doTestModule(true, true); }
////    public void testcomment_mulit() { doTestModule(true, true); }
////    public void testcomment_nested() { doTestModule(true, true); }
//
//    // floats
//    public void testfloat_decimal() { doTestModule(true, true); }
//    public void testfloat_hex() { doTestModule(true, true); }
//
//    // integers
//    public void testinteger_binary() { doTestModule(true, true); }
//    public void testinteger_decimal() { doTestModule(true, true); }
//    public void testinteger_hex() { doTestModule(true, true); }
//    public void testinteger_octal() { doTestModule(true, true); }
//
//    // strings
//    public void teststring_delim() { doTestModule(true, true); }
//    public void teststring_dq() { doTestModule(true, true); }
//    public void teststring_hex() { doTestModule(true, true); }
//    public void teststring_tokens() { doTestModule(true, true); }
//    public void teststring_wysiwyg() { doTestModule(true, true); }
//
//    // tokens
//    public void testtokens_and() { doTestModule(true, true); }
//    public void testtokens_and_assign() { doTestModule(true, true); }
//    public void testtokens_assign() { doTestModule(true, true); }
//    public void testtokens_at() { doTestModule(true, true); }
//    public void testtokens_close_brace() { doTestModule(true, true); }
//    public void testtokens_close_bracket() { doTestModule(true, true); }
//    public void testtokens_close_parens() { doTestModule(true, true); }
//    public void testtokens_colon() { doTestModule(true, true); }
//    public void testtokens_comma() { doTestModule(true, true); }
//    public void testtokens_concat() { doTestModule(true, true); }
//    public void testtokens_concat_assign() { doTestModule(true, true); }
//    public void testtokens_decrement() { doTestModule(true, true); }
//    public void testtokens_div() { doTestModule(true, true); }
//    public void testtokens_div_assign() { doTestModule(true, true); }
//    public void testtokens_dollar() { doTestModule(true, true); }
//    public void testtokens_dot() { doTestModule(true, true); }
//    public void testtokens_double_dot() { doTestModule(true, true); }
//    public void testtokens_equals() { doTestModule(true, true); }
//    public void testtokens_greater_equal() { doTestModule(true, true); }
//    public void testtokens_greater_than() { doTestModule(true, true); }
//    public void testtokens_increment() { doTestModule(true, true); }
//    public void testtokens_lambda() { doTestModule(true, true); }
//    public void testtokens_left_shift() { doTestModule(true, true); }
//    public void testtokens_left_shift_assign() { doTestModule(true, true); }
//    public void testtokens_less_equal() { doTestModule(true, true); }
//    public void testtokens_less_greater() { doTestModule(true, true); }
//    public void testtokens_less_greater_equal() { doTestModule(true, true); }
//    public void testtokens_less_than() { doTestModule(true, true); }
//    public void testtokens_logical_and() { doTestModule(true, true); }
//    public void testtokens_logical_or() { doTestModule(true, true); }
//    public void testtokens_minus() { doTestModule(true, true); }
//    public void testtokens_minus_assign() { doTestModule(true, true); }
//    public void testtokens_mod() { doTestModule(true, true); }
//    public void testtokens_mod_assign() { doTestModule(true, true); }
//    public void testtokens_mult_assign() { doTestModule(true, true); }
//    public void testtokens_not() { doTestModule(true, true); }
//    public void testtokens_not_equal() { doTestModule(true, true); }
//    public void testtokens_open_brace() { doTestModule(true, true); }
//    public void testtokens_open_bracket() { doTestModule(true, true); }
//    public void testtokens_open_parens() { doTestModule(true, true); }
//    public void testtokens_or() { doTestModule(true, true); }
//    public void testtokens_or_assign() { doTestModule(true, true); }
//    public void testtokens_plus() { doTestModule(true, true); }
//    public void testtokens_plus_assign() { doTestModule(true, true); }
//    public void testtokens_pow() { doTestModule(true, true); }
//    public void testtokens_pow_assign() { doTestModule(true, true); }
//    public void testtokens_question() { doTestModule(true, true); }
//    public void testtokens_right_shift() { doTestModule(true, true); }
//    public void testtokens_right_shift_assign() { doTestModule(true, true); }
//    public void testtokens_semicolon() { doTestModule(true, true); }
//    public void testtokens_star() { doTestModule(true, true); }
//    public void testtokens_triple_dot() { doTestModule(true, true); }
//    public void testtokens_triple_rshift() { doTestModule(true, true); }
//    public void testtokens_triple_rshift_assign() { doTestModule(true, true); }
//    public void testtokens_unordered() { doTestModule(true, true); }
//    public void testtokens_unordered_e() { doTestModule(true, true); }
//    public void testtokens_unordered_g() { doTestModule(true, true); }
//    public void testtokens_unordered_ge() { doTestModule(true, true); }
//    public void testtokens_unordered_l() { doTestModule(true, true); }
//    public void testtokens_unordered_le() { doTestModule(true, true); }
//    public void testtokens_xor() { doTestModule(true, true); }
//    public void testtokens_xor_assign() { doTestModule(true, true); }
}
