package io.github.intellij.dlanguage.parser;

import io.github.intellij.dlanguage.DLangParserDefinition;

public class DLanguageParserTest extends DLanguageParserTestBase {

    public DLanguageParserTest() {
        super("parser", "d", new DLangParserDefinition());
    }

    /**
     * Checks the shebang.d file in /parser directory. File content is:
     * '#!/usr/bin/env rdmd -unittest -Isrc'
     */
    public void testshebang() {
        doDlangParserTest(true, true);
    }

    // modules
    public void testmodule1() {
        doDlangParserTest(true, true);
    }

    public void testmodule2() {
        doDlangParserTest(true, true);
    }

    public void testmodule_deprecated1() {
        doDlangParserTest(true, true);
    }

    public void testmodule_deprecated2() {
        doDlangParserTest(true, true);
    }

    public void testimport() {
        doDlangParserTest(true, true);
    }

    public void testmodule_scope() {
        doDlangParserTest(true, true);
    }

    // declarations
    public void testdeclaration1() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_alias() {
        doDlangParserTest(true, true);
    }

    /**
     * Checks the declaration_alias2.d file in /parser directory.
     */
    public void testdeclaration_alias2() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_symbols() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_type_storage2() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_type_storage1() {
        doDlangParserTest(true, true);
    }

    /**
     * Checks the declaration_alias3.d file in /parser directory.
     */
    public void testdeclaration_alias3() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_alias4() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_extern() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_typeof2() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_void() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_storage() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_type_storage3() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_type_storage4() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_alias_symbol() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_type_inference() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_array_literal() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_type_qualifier() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_typeof3() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_typeof() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_static_foreach() {
        doDlangParserTest(true, true);
    }

    public void testdeclaration_strings() {
        doDlangParserTest(true, true);
    }

    //  types
    public void testtype_arithmetic_conversion() {
        doDlangParserTest(true, true);
    }

    public void testtype_implicit_conversion() {
        doDlangParserTest(true, true);
    }

    public void testtype_delegates() {
        doDlangParserTest(true, true);
    }

    public void testtype_delegates2() {
        doDlangParserTest(true, true);
    }

    public void testtype_delegates3() {
        doDlangParserTest(true, true);
    }

    public void testtype_delegates4() {
        doDlangParserTest(true, true);
    }

    public void testtype_c_style() {
        doDlangParserTest(true, true);
    }

    public void testtype_expression_with_array_indexing() {
        doDlangParserTest(true, true);
    }

    // documentation

    public void testdocumentation_attached_declaration() {
        doDlangParserTest(true, true);
    }

    //  properties
    public void testproperties_init() {
        doDlangParserTest(true, true);
    }

    public void testproperties_init2() {
        doDlangParserTest(true, true);
    }

    public void testproperties_init3() {
        doDlangParserTest(true, true);
    }

    public void testproperties_user_defined() {
        doDlangParserTest(true, true);
    }

    public void testproperties_stringof() {
        doDlangParserTest(true, true);
    }

    public void testproperties_sizeof() {
        doDlangParserTest(true, true);
    }

    //  attributes
    public void testattributes_align() {
        doDlangParserTest(true, true);
    }

    public void testattributes_auto() {
        doDlangParserTest(true, true);
    }

    public void testattributes_const() {
        doDlangParserTest(true, true);
    }

    public void testattributes_deprecated() {
        doDlangParserTest(true, true);
    }

    public void testattributes_deprecated_optional() {
        doDlangParserTest(true, true);
    }

    public void testattributes_disabled() {
        doDlangParserTest(true, true);
    }

    public void testattributes_extern() {
        doDlangParserTest(true, true);
    }

    public void testattributes_gshared() {
        doDlangParserTest(true, true);
    }

    public void testattributes_namespaces() {
        doDlangParserTest(true, true);
    }

    public void testattributes_override() {
        doDlangParserTest(true, true);
    }

    public void testattributes_private() {
        doDlangParserTest(true, true);
    }

    public void testattributes_protection() {
        doDlangParserTest(true, true);
    }

    public void testattributes_static() {
        doDlangParserTest(true, true);
    }

    public void testattributes_traits() {
        doDlangParserTest(true, true);
    }

    public void testattributes_namespaces_multiple() {
        doDlangParserTest(true, true);
    }

    public void testattributes_user_defined() {
        doDlangParserTest(true, true);
    }

    public void testattributes_nogc() {
        doDlangParserTest(true, true);
    }

    //  pragmas
    public void testpragmas() {
        doDlangParserTest(true, true);
    }

    public void testpragmas_inline() {
        doDlangParserTest(true, true);
    }

    public void testpragmas_methods() {
        doDlangParserTest(true, true);
    }

    //  expressions
    public void testexpressions_array_literal() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_assert() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_associative_array_literal() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_cast() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_cast2() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_cast_float() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_cast_pointer() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_is() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_lambda() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_lambda_auto_ref() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_new() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_class_comparison() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_equality() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_function_literal() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_function_literal2() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_function_literal3() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_import() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_in() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_mixin() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_operators() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_precedence() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_scalar() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_slice() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_string_literal() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_this() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_throw() {
        doDlangParserTest(true, true);
    }

    public void testexpressions_type_info() {
        doDlangParserTest(true, true);
    }

    // asm
    public void testasm() {
        doDlangParserTest(true, true);
    }

    // statements
    public void teststatements_asm() {
        doDlangParserTest(true, true);
    }

    public void teststatements_asm_inline() {
        doDlangParserTest(true, true);
    }

    public void teststatements_foreach_associative_array() {
        doDlangParserTest(true, true);
    }

    public void teststatements_foreach_delegates() {
        doDlangParserTest(true, true);
    }

    public void teststatements_foreach_templated() {
        doDlangParserTest(true, true);
    }

    public void teststatements_foreach_tuples() {
        doDlangParserTest(true, true);
    }

    public void teststatements_switch() {
        doDlangParserTest(true, true);
    }

    public void teststatements_switch2() {
        doDlangParserTest(true, true);
    }

    public void teststatements_switch_unterminated_case() {
        doDlangParserTest(true, false);
    }

    public void teststatements_block() {
        doDlangParserTest(true, true);
    }

    public void teststatements_break() {
        doDlangParserTest(true, true);
    }

    public void teststatements_declaration() {
        doDlangParserTest(true, true);
    }

    public void teststatements_declarator() {
        doDlangParserTest(true, true);
    }

    public void teststatements_do() {
        doDlangParserTest(true, true);
    }

    public void teststatements_expression() {
        doDlangParserTest(true, true);
    }

    public void teststatements_for() {
        doDlangParserTest(true, true);
    }

    public void teststatements_foreach() {
        doDlangParserTest(true, true);
    }

    public void teststatements_foreach_aggregates() {
        doDlangParserTest(true, true);
    }

    public void teststatements_foreach_array() {
        doDlangParserTest(true, true);
    }

    public void teststatements_foreach_range() {
        doDlangParserTest(true, true);
    }

    public void teststatements_foreach_ref_params() {
        doDlangParserTest(true, true);
    }

    public void teststatements_foreach_reverse() {
        doDlangParserTest(true, true);
    }

    public void teststatements_foreach_types() {
        doDlangParserTest(true, true);
    }

    public void teststatements_goto() {
        doDlangParserTest(true, true);
    }

    public void teststatements_mixin() {
        doDlangParserTest(true, true);
    }

    public void teststatements_pragma() {
        doDlangParserTest(true, true);
    }

    public void teststatements_return() {
        doDlangParserTest(true, true);
    }

    public void teststatements_scope() {
        doDlangParserTest(true, true);
    }

    public void teststatements_synchronised() {
        doDlangParserTest(true, true);
    }

    public void teststatements_while() {
        doDlangParserTest(true, true);
    }

    public void teststatements_while_condition_assignment() {
        doDlangParserTest(true, true);
    }

    public void teststatements_with() {
        doDlangParserTest(true, true);
    }

    public void teststatements_unterminated_if() {
        doDlangParserTest(true, false);
    }

    // arrays
    public void testarrays() {
        doDlangParserTest(true, true);
    }

    public void testarrays_concatenation() {
        doDlangParserTest(true, true);
    }

    public void testarrays_copying() {
        doDlangParserTest(true, true);
    }

    public void testarrays_declarations() {
        doDlangParserTest(true, true);
    }

    public void testarrays_dynamic() {
        doDlangParserTest(true, true);
    }

    public void testarrays_function_properties() {
        doDlangParserTest(true, true);
    }

    public void testarrays_bounds_checking() {
        doDlangParserTest(true, true);
    }

    public void testarrays_dynamic_length() {
        doDlangParserTest(true, true);
    }

    public void testarrays_initialization() {
        doDlangParserTest(true, true);
    }

    public void testarrays_rectangular() {
        doDlangParserTest(true, true);
    }

    public void testarrays_length() {
        doDlangParserTest(true, true);
    }

    public void testarrays_operations() {
        doDlangParserTest(true, true);
    }

    public void testarrays_pointer_arithmetic() {
        doDlangParserTest(true, true);
    }

    public void testarrays_pointers() {
        doDlangParserTest(true, true);
    }

    public void testarrays_printf_c() {
        doDlangParserTest(true, true);
    }

    public void testarrays_properties() {
        doDlangParserTest(true, true);
    }

    public void testarrays_setting() {
        doDlangParserTest(true, true);
    }

    public void testarrays_slicing() {
        doDlangParserTest(true, true);
    }

    public void testarrays_special_strings() {
        doDlangParserTest(true, true);
    }

    public void testarrays_static() {
        doDlangParserTest(true, true);
    }

    public void testarrays_void() {
        doDlangParserTest(true, true);
    }


    // associative arrays
    public void testassocarrays() {
        doDlangParserTest(true, true);
    }

    public void testassocarrays_literal() {
        doDlangParserTest(true, true);
    }

    public void testassocarrays_classes() {
        doDlangParserTest(true, true);
    }

    public void testassocarrays_constructor() {
        doDlangParserTest(true, true);
    }

    public void testassocarrays_removing() {
        doDlangParserTest(true, true);
    }

    public void testassocarrays_immutable() {
        doDlangParserTest(true, true);
    }

    public void testassocarrays_membership() {
        doDlangParserTest(true, true);
    }

    public void testassocarrays_structs() {
        doDlangParserTest(true, true);
    }

    public void testassocarrays_word_count() {
        doDlangParserTest(true, true);
    }

    // structs and unions
    public void teststructs() {
        doDlangParserTest(true, true);
    }

    public void teststructs_constructors() {
        doDlangParserTest(true, true);
    }

    public void teststructs_dynamic_init() {
        doDlangParserTest(true, true);
    }

    public void teststructs_initialization() {
        doDlangParserTest(true, true);
    }

    public void teststructs_literal() {
        doDlangParserTest(true, true);
    }

    public void teststructs_nested() {
        doDlangParserTest(true, true);
    }

    public void teststructs_overload() {
        doDlangParserTest(true, true);
    }

    public void testunion_initialization() {
        doDlangParserTest(true, true);
    }

    public void teststructs_postblits() {
        doDlangParserTest(true, true);
    }

    public void teststructs_shared() {
        doDlangParserTest(true, true);
    }


    // classes
    public void testclasses() {
        doDlangParserTest(true, true);
    }

    public void testclasses_alias_this() {
        doDlangParserTest(true, true);
    }

    public void testclasses_constructors_fields() {
        doDlangParserTest(true, true);
    }

    public void testclasses_nested() {
        doDlangParserTest(true, true);
    }

    public void testclasses_properties() {
        doDlangParserTest(true, true);
    }

    public void testclasses_allocators() {
        doDlangParserTest(true, true);
    }

    public void testclasses_constructors() {
        doDlangParserTest(true, true);
    }

    public void testclasses_deallocators() {
        doDlangParserTest(true, true);
    }

    public void testclasses_destructors() {
        doDlangParserTest(true, true);
    }

    public void testclasses_field_properties() {
        doDlangParserTest(true, true);
    }

    public void testclasses_fields() {
        doDlangParserTest(true, true);
    }

    public void testclasses_final() {
        doDlangParserTest(true, true);
    }

    public void testclasses_members() {
        doDlangParserTest(true, true);
    }

    public void testclasses_scope() {
        doDlangParserTest(true, true);
    }

    public void testclasses_static_constructors() {
        doDlangParserTest(true, true);
    }

    public void testclasses_static_destructor() {
        doDlangParserTest(true, true);
    }

    public void testclasses_synchronized() {
        doDlangParserTest(true, true);
    }

    // interfaces
    public void testinterfaces() {
        doDlangParserTest(true, true);
    }

    public void testinterfaces_com() {
        doDlangParserTest(true, true);
    }

    public void testinterfaces_contracts() {
        doDlangParserTest(true, true);
    }

    public void testinterfaces_cplus() {
        doDlangParserTest(true, true);
    }

    // enums
    public void testenums() {
        doDlangParserTest(true, true);
    }

    // immutable const
    public void testconst_immutable() {
        doDlangParserTest(true, true);
    }

    // functions
    public void testfunctions_auto() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_auto_ref() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_default_args() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_inheritance() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_nested() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_optional_parens() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_overloading() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_templates() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_ucfs() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_delegated_fp_closures() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_inout() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_local_static() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_local_vars() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_overload_sets() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_parameters() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_properties() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_pure() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_ref() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_shortened_body() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_string_mixin() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_variadic() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_variadic_d() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_variadic_lazy() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_variadic_typesafe() {
        doDlangParserTest(true, true);
    }

    public void testfunctions_virtual() {
        doDlangParserTest(true, true);
    }

    // operator overloading
    public void testoperator_overloading() {
        doDlangParserTest(true, true);
    }

    // templates
    public void testtemplates() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_alias() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_alias_assign() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_defaults() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_function_templates() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_alias_params() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_argument_deduction() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_instant_scope() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_limitation() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_recursive() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_specialization() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_typed_params() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_mixins() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_mixins_scope() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_nested() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_params() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_ref() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_this_params() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_tuples() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_typed_specialization() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_variable() {
        doDlangParserTest(true, true);
    }

    public void testtemplates_variable_declaration() {
        doDlangParserTest(true, true);
    }

    // contract programming
    public void testcontract_programming() {
        doDlangParserTest(true, true);
    }

    // conditional compilation
    public void testconditional_compilation() {
        doDlangParserTest(true, true);
    }

    // traits
    public void testtraits_allmembers() {
        doDlangParserTest(true, true);
    }

    public void testtraits_derivedmembers() {
        doDlangParserTest(true, true);
    }

    public void testtraits_getfunctionattributes() {
        doDlangParserTest(true, true);
    }

    public void testtraits_getpointerbitmap() {
        doDlangParserTest(true, true);
    }

    public void testtraits_getprotection() {
        doDlangParserTest(true, true);
    }

    public void testtraits_isabstractionfunction() {
        doDlangParserTest(true, true);
    }

    public void testtraits_isarithmetic() {
        doDlangParserTest(true, true);
    }

    public void testtraits_isfinalfunction() {
        doDlangParserTest(true, true);
    }

    public void testtraits_isrefoutlazy() {
        doDlangParserTest(true, true);
    }

    public void testtraits_isvirtualmethod() {
        doDlangParserTest(true, true);
    }

    public void testtraits_compiles() {
        doDlangParserTest(true, true);
    }

    public void testtraits_getmember() {
        doDlangParserTest(true, true);
    }

    public void testtraits_getoverloads() {
        doDlangParserTest(true, true);
    }

    public void testtraits_getvirtualmethods() {
        doDlangParserTest(true, true);
    }

    public void testtraits_hasmember() {
        doDlangParserTest(true, true);
    }

    public void testtraits_isabstractclass() {
        doDlangParserTest(true, true);
    }

    public void testtraits_isoverridefunction() {
        doDlangParserTest(true, true);
    }

    public void testtraits_issame() {
        doDlangParserTest(true, true);
    }

    public void testtraits_istemplate() {
        doDlangParserTest(true, true);
    }

    public void testtraits_getunittests() {
        doDlangParserTest(true, true);
    }

    public void testtraits_special_keywords() {
        doDlangParserTest(true, true);
    }

    public void testtraits_getattributes() {
        doDlangParserTest(true, true);
    }

    // unittest
    public void testunittests() {
        doDlangParserTest(true, true);
    }

    // bug fixes
    public void testbitwise_or_bug() {
        doDlangParserTest(true, true);
    }

    public void testbug_assoc_array() {
        doDlangParserTest(true, true);
    }

    public void testbug_try_catch() {
        doDlangParserTest(true, true);
    }

    public void testbug_fixes() {
        doDlangParserTest(true, true);
    }

    public void teststaticifbug() {
        doDlangParserTest(true, true);
    }

    public void testcollection_of_bugs() {
        doDlangParserTest(true, true);
    }

// standard library

    public void teststdio() {
        doDlangParserTest(true, true);
    }

    public void testtraits() {
        doDlangParserTest(true, true);
    }

    public void teststring() {
        doDlangParserTest(true, true);
    }

    public void testbase64() {
        doDlangParserTest(true, true);
    }

    public void testarray() {
        doDlangParserTest(true, true);
    }

    public void testtypecons() {
        doDlangParserTest(true, true);
    }

    public void testparallelism() {
        doDlangParserTest(true, true);
    }

    //dmd source

    public void testexpression() {
        doDlangParserTest(true, true);
    }

    // dmd fail_compilation test file
    public void teste15876_1() {
        doDlangParserTest(true, false);
    }


    // libdparse incompleteStatement198_1.d test file
    public void testincompleteStatement198_1() { doDlangParserTest(true, false);}

    public void testobject(){
        doDlangParserTest(true,true);
    }

}


