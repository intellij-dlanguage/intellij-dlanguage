package net.masterthought.dlanguage.parser;

public class DParserTest extends DParserTestBase {

    public DParserTest() {
        super("parser", "d", new DLanguageParserDefinition());
    }

//    public void testnull() { doTest(true, true); }
    public void testsymbol() { doTest(true, true); }
    public void testdeclaration_module() { doTest(true, true); }
    public void testdeclaration_import() { doTest(true, true); }
//    public void testimport_content() { doTest(true, true); }
    public void testimport_alias() { doTest(true, true); }
    public void testimport_selective() { doTest(true, true); }
    public void testimport_selective_alias() { doTest(true, true); }
//    public void testdecl_list() { doTest(true, true); }
//    public void testdecl_block() { doTest(true, true); }
//    public void testdeclaration_emtpy() { doTest(true, true); }
//    public void testmissing_declaration() { doTest(true, true); }
//    public void testinvalid_syntax() { doTest(true, true); }
//    public void testincomplete_declarator() { doTest(true, true); }
    public void testref_import_selection() { doTest(true, true); }
    public void testref_module() { doTest(true, true); }
//    public void testref_identifier() { doTest(true, true); }
//    public void testref_qualified() { doTest(true, true); }
//    public void testref_module_qualified() { doTest(true, true); }
//    public void testref_primitive() { doTest(true, true); }
//    public void testref_type_dyn_array() { doTest(true, true); }
//    public void testref_type_pointer() { doTest(true, true); }
//    public void testref_indexing() { doTest(true, true); }
//    public void testref_slice() { doTest(true, true); }
//    public void testref_type_function() { doTest(true, true); }
//    public void testref_template_instance() { doTest(true, true); }
//    public void testref_typeof() { doTest(true, true); }
//    public void testref_modifier() { doTest(true, true); }
//    public void testref_auto() { doTest(true, true); }
//    public void testmissing_expression() { doTest(true, true); }
//    public void testexp_ref_return() { doTest(true, true); }
//    public void testexp_this() { doTest(true, true); }
//    public void testexp_super() { doTest(true, true); }
//    public void testexp_null() { doTest(true, true); }
//    public void testexp_array_length() { doTest(true, true); }
//    public void testexp_literal_bool() { doTest(true, true); }
//    public void testexp_literal_integer() { doTest(true, true); }
//    public void testexp_literal_string() { doTest(true, true); }
//    public void testexp_literal_char() { doTest(true, true); }
//    public void testexp_literal_float() { doTest(true, true); }
//    public void testexp_literal_array() { doTest(true, true); }
//    public void testexp_literal_maparray() { doTest(true, true); }
//    public void testmaparray_entry() { doTest(true, true); }
//    public void testexp_function_literal() { doTest(true, true); }
//    public void testexp_simple_lambda() { doTest(true, true); }
//    public void testsimple_lambda_defunit() { doTest(true, true); }
//    public void testexp_reference() { doTest(true, true); }
//    public void testexp_parentheses() { doTest(true, true); }
//    public void testexp_assert() { doTest(true, true); }
//    public void testexp_mixin_string() { doTest(true, true); }
//    public void testexp_import_string() { doTest(true, true); }
//    public void testexp_typeid() { doTest(true, true); }
//    public void testexp_index() { doTest(true, true); }
//    public void testexp_call() { doTest(true, true); }
//    public void testexp_prefix() { doTest(true, true); }
//    public void testexp_new() { doTest(true, true); }
//    public void testexp_new_anon_class() { doTest(true, true); }
//    public void testexp_cast() { doTest(true, true); }
//    public void testexp_cast_qual() { doTest(true, true); }
//    public void testexp_postfix_op() { doTest(true, true); }
//    public void testexp_infix() { doTest(true, true); }
//    public void testexp_conditional() { doTest(true, true); }
//    public void testexp_is() { doTest(true, true); }
//    public void teststatic_if_exp_is() { doTest(true, true); }
//    public void teststatic_if_exp_is_def_unit() { doTest(true, true); }
//    public void testexp_traits() { doTest(true, true); }
//    public void testdeclaration_attrib() { doTest(true, true); }
//    public void testattrib_linkage() { doTest(true, true); }
//    public void testattrib_cpp_linkage() { doTest(true, true); }
//    public void testattrib_align() { doTest(true, true); }
//    public void testattrib_pragma() { doTest(true, true); }
//    public void testattrib_protection() { doTest(true, true); }
//    public void testattrib_basic() { doTest(true, true); }
//    public void testattrib_at_keyword() { doTest(true, true); }
//    public void testattrib_custom() { doTest(true, true); }
//    public void testdeclaration_mixin_string() { doTest(true, true); }
//    public void testdeclaration_mixin() { doTest(true, true); }
//    public void testdeclaration_alias_this() { doTest(true, true); }
//    public void testdeclaration_invariant() { doTest(true, true); }
//    public void testdeclaration_unitest() { doTest(true, true); }
//    public void testdeclaration_allocator_function() { doTest(true, true); }
//    public void testdeclaration_special_function() { doTest(true, true); }
//    public void testdeclaration_debug_version_spec() { doTest(true, true); }
//    public void testdeclaration_debug_version() { doTest(true, true); }
//    public void testdeclaration_static_if() { doTest(true, true); }
//    public void testdeclaration_static_assert() { doTest(true, true); }
//    public void testdefinition_variable() { doTest(true, true); }
//    public void testdefinition_var_fragment() { doTest(true, true); }
//    public void testdefinition_auto_variable() { doTest(true, true); }
//    public void testcstyle_root_ref() { doTest(true, true); }
//    public void testinitializer_void() { doTest(true, true); }
//    public void testinitializer_array() { doTest(true, true); }
//    public void testarray_init_entry() { doTest(true, true); }
//    public void testinitializer_struct() { doTest(true, true); }
//    public void teststruct_init_entry() { doTest(true, true); }
//    public void testdefinition_function() { doTest(true, true); }
//    public void testfunction_parameter() { doTest(true, true); }
//    public void testnameless_parameter() { doTest(true, true); }
//    public void testvar_args_parameter() { doTest(true, true); }
//    public void testfunction_body() { doTest(true, true); }
//    public void testin_out_function_body() { doTest(true, true); }
//    public void testfunction_body_out_block() { doTest(true, true); }
//    public void testdefinition_constructor() { doTest(true, true); }
//    public void testdefinition_struct() { doTest(true, true); }
//    public void testdefinition_union() { doTest(true, true); }
//    public void testdefinition_class() { doTest(true, true); }
//    public void testdefinition_interface() { doTest(true, true); }
//    public void testdefinition_template() { doTest(true, true); }
//    public void testtemplate_type_param() { doTest(true, true); }
//    public void testtemplate_value_param() { doTest(true, true); }
//    public void testtemplate_alias_param() { doTest(true, true); }
//    public void testtemplate_tuple_param() { doTest(true, true); }
//    public void testtemplate_this_param() { doTest(true, true); }
//    public void testdefinition_mixin_instance() { doTest(true, true); }
//    public void testdefinition_enum() { doTest(true, true); }
//    public void testdeclaration_enum() { doTest(true, true); }
//    public void testenum_body() { doTest(true, true); }
//    public void testenum_member() { doTest(true, true); }
//    public void testdefinition_enum_var() { doTest(true, true); }
//    public void testdefinition_enum_var_fragment() { doTest(true, true); }
//    public void testdefinition_alias() { doTest(true, true); }
//    public void testdefinition_alias_fragment() { doTest(true, true); }
//    public void testdefinition_alias_var_decl() { doTest(true, true); }
//    public void testdefinition_alias_function_decl() { doTest(true, true); }
//    public void testalias_var_decl_fragment() { doTest(true, true); }
//    public void testtemplate_type_param__instance() { doTest(true, true); }
//    public void testtemplate_value_param__instance() { doTest(true, true); }
//    public void testtemplate_alias_param__instance() { doTest(true, true); }
//    public void testblock_statement() { doTest(true, true); }
//    public void testblock_statement_unscoped() { doTest(true, true); }
//    public void testempty_statement() { doTest(true, true); }
//    public void testscoped_statement_list() { doTest(true, true); }
//    public void teststatement_expression() { doTest(true, true); }
//    public void teststatement_label() { doTest(true, true); }
//    public void teststatement_if() { doTest(true, true); }
//    public void teststatement_if_var() { doTest(true, true); }
//    public void testvariable_def_with_init() { doTest(true, true); }
//    public void teststatement_while() { doTest(true, true); }
//    public void teststatement_do_while() { doTest(true, true); }
//    public void teststatement_for() { doTest(true, true); }
//    public void teststatement_foreach() { doTest(true, true); }
//    public void testforeach_variable_def() { doTest(true, true); }
//    public void teststatement_switch() { doTest(true, true); }
//    public void teststatement_case() { doTest(true, true); }
//    public void teststatement_case_range() { doTest(true, true); }
//    public void teststatement_default() { doTest(true, true); }
//    public void teststatement_continue() { doTest(true, true); }
//    public void teststatement_break() { doTest(true, true); }
//    public void teststatement_return() { doTest(true, true); }
//    public void teststatement_goto() { doTest(true, true); }
//    public void teststatement_goto_default() { doTest(true, true); }
//    public void teststatement_goto_case() { doTest(true, true); }
//    public void teststatement_throw() { doTest(true, true); }
//    public void teststatement_synchronized() { doTest(true, true); }
//    public void teststatement_with() { doTest(true, true); }
//    public void teststatement_asm() { doTest(true, true); }
//    public void teststatement_scope() { doTest(true, true); }
//    public void teststatement_try() { doTest(true, true); }
//    public void testtry_catch_clause() { doTest(true, true); }
//    public void testsimple_variable_def() { doTest(true, true); }

}
