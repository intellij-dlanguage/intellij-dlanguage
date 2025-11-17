package io.github.intellij.dlanguage.parser

import io.github.intellij.dlanguage.DLangParserDefinition

class DLanguageParserTest : DLanguageParserTestBase("parser", "d", DLangParserDefinition()) {
    /**
     * Checks the shebang.d file in /parser directory. File content is:
     * '#!/usr/bin/env rdmd -unittest -Isrc'
     */
    fun testshebang() = doDlangParserTest()

    // modules
    fun testmodule1() = doDlangParserTest()
    fun testmodule2() = doDlangParserTest()
    fun testmodule_deprecated1() = doDlangParserTest()
    fun testmodule_deprecated2() = doDlangParserTest()
    fun testmodule_scope() = doDlangParserTest()

    fun testimport() = doDlangParserTest()

    // declarations
    fun testdeclaration1() = doDlangParserTest()
    fun testdeclaration_alias() = doDlangParserTest()
    fun testdeclaration_alias2() = doDlangParserTest()
    fun testdeclaration_anonymous_function_call() = doDlangParserTest()
    fun testdeclaration_symbols() = doDlangParserTest()
    fun testdeclaration_type_storage2() = doDlangParserTest()
    fun testdeclaration_type_storage1() = doDlangParserTest()
    fun testdeclaration_alias3() = doDlangParserTest()
    fun testdeclaration_alias4() = doDlangParserTest()
    fun testdeclaration_extern() = doDlangParserTest()
    fun testdeclaration_typeof2() = doDlangParserTest()
    fun testdeclaration_void() = doDlangParserTest()
    fun testdeclaration_storage() = doDlangParserTest()
    fun testdeclaration_type_storage3() = doDlangParserTest()
    fun testdeclaration_type_storage4() = doDlangParserTest()
    fun testdeclaration_alias_symbol() = doDlangParserTest()
    fun testdeclaration_type_inference() = doDlangParserTest()
    fun testdeclaration_array_literal() = doDlangParserTest()
    fun testdeclaration_type_qualifier() = doDlangParserTest()
    fun testdeclaration_typeof3() = doDlangParserTest()
    fun testdeclaration_typeof() = doDlangParserTest()
    fun testdeclaration_static_foreach() = doDlangParserTest()
    fun testdeclaration_strings() = doDlangParserTest()
    fun testdeclaration_vector() = doDlangParserTest()
    fun testdeclaration_block_with_invalid_function_declaration() = doDlangParserTest(checkResult = true, shouldPass = false)

    //  types
    fun testtype_arithmetic_conversion() = doDlangParserTest()
    fun testtype_implicit_conversion() = doDlangParserTest()
    fun testtype_delegates() = doDlangParserTest()
    fun testtype_delegates2() = doDlangParserTest()
    fun testtype_delegates3() = doDlangParserTest()
    fun testtype_delegates4() = doDlangParserTest()
    fun testtype_c_style() = doDlangParserTest()
    fun testtype_expression_with_array_indexing() = doDlangParserTest()

    // documentation
    fun testdocumentation_attached_declaration() = doDlangParserTest()

    //  properties
    fun testproperties_init() = doDlangParserTest()
    fun testproperties_init2() = doDlangParserTest()
    fun testproperties_init3() = doDlangParserTest()
    fun testproperties_user_defined() = doDlangParserTest()
    fun testproperties_stringof() = doDlangParserTest()
    fun testproperties_sizeof() = doDlangParserTest()

    //  attributes
    fun testattributes_align() = doDlangParserTest()
    fun testattributes_auto() = doDlangParserTest()
    fun testattributes_const() = doDlangParserTest()
    fun testattributes_deprecated() = doDlangParserTest()
    fun testattributes_deprecated_optional() = doDlangParserTest()
    fun testattributes_disabled() = doDlangParserTest()
    fun testattributes_extern() = doDlangParserTest()
    fun testattributes_gshared() = doDlangParserTest()
    fun testattributes_namespaces() = doDlangParserTest()
    fun testattributes_override() = doDlangParserTest()
    fun testattributes_private() = doDlangParserTest()
    fun testattributes_protection() = doDlangParserTest()
    fun testattributes_static() = doDlangParserTest()
    fun testattributes_traits() = doDlangParserTest()
    fun testattributes_namespaces_multiple() = doDlangParserTest()
    fun testattributes_user_defined() = doDlangParserTest()
    fun testattributes_nogc() = doDlangParserTest()

    //  pragmas
    fun testpragmas() = doDlangParserTest()
    fun testpragmas_inline() = doDlangParserTest()
    fun testpragmas_methods() = doDlangParserTest()

    //  expressions
    fun testexpressions_array_literal() = doDlangParserTest()
    fun testexpressions_assert() = doDlangParserTest()
    fun testexpressions_associative_array_literal() = doDlangParserTest()
    fun testexpressions_cast() = doDlangParserTest()
    fun testexpressions_cast2() = doDlangParserTest()
    fun testexpressions_cast_float() = doDlangParserTest()
    fun testexpressions_cast_pointer() = doDlangParserTest()
    fun testexpressions_is() = doDlangParserTest()
    fun testexpressions_lambda() = doDlangParserTest()
    fun testexpressions_lambda_auto_ref() = doDlangParserTest()
    fun testexpressions_new() = doDlangParserTest()
    fun testexpressions_class_comparison() = doDlangParserTest()
    fun testexpressions_equality() = doDlangParserTest()
    fun testexpressions_function_literal() = doDlangParserTest()
    fun testexpressions_function_literal2() = doDlangParserTest()
    fun testexpressions_function_literal3() = doDlangParserTest()
    fun testexpressions_function_literal_parameterless() = doDlangParserTest() // issue #1174
    fun testexpressions_ies_string() = doDlangParserTest()
    fun testexpressions_import() = doDlangParserTest()
    fun testexpressions_in() = doDlangParserTest()
    fun testexpressions_mixin() = doDlangParserTest()
    fun testexpressions_named_parameters() = doDlangParserTest()
    fun testexpressions_operators() = doDlangParserTest()
    fun testexpressions_precedence() = doDlangParserTest()
    fun testexpressions_scalar() = doDlangParserTest()
    fun testexpressions_slice() = doDlangParserTest()
    fun testexpressions_string_literal() = doDlangParserTest()
    fun testexpressions_this() = doDlangParserTest()
    fun testexpressions_throw() = doDlangParserTest()
    fun testexpressions_type_info() = doDlangParserTest()

    // asm
    fun testasm() = doDlangParserTest()

    // statements
    fun teststatements_asm() = doDlangParserTest()
    fun teststatements_asm_inline() = doDlangParserTest()
    fun teststatements_foreach_associative_array() = doDlangParserTest()
    fun teststatements_foreach_delegates() = doDlangParserTest()
    fun teststatements_foreach_templated() = doDlangParserTest()
    fun teststatements_foreach_tuples() = doDlangParserTest()
    fun teststatements_switch() = doDlangParserTest()
    fun teststatements_switch2() = doDlangParserTest()
    fun teststatements_switch_unterminated_case() = doDlangParserTest(checkResult = true, shouldPass = false)
    fun teststatements_block() = doDlangParserTest()
    fun teststatements_break() = doDlangParserTest()
    fun teststatements_declaration() = doDlangParserTest()
    fun teststatements_declarator() = doDlangParserTest()
    fun teststatements_do() = doDlangParserTest()
    fun teststatements_expression() = doDlangParserTest()
    fun teststatements_for() = doDlangParserTest()
    fun teststatements_foreach() = doDlangParserTest()
    fun teststatements_foreach_invalid() = doDlangParserTest(shouldPass = false)
    fun teststatements_foreach_aggregates() = doDlangParserTest()
    fun teststatements_foreach_array() = doDlangParserTest()
    fun teststatements_foreach_range() = doDlangParserTest()
    fun teststatements_foreach_alias_params() = doDlangParserTest()
    fun teststatements_foreach_alias_params_invalid() = doDlangParserTest(shouldPass = false)
    fun teststatements_foreach_ref_params() = doDlangParserTest()
    fun teststatements_foreach_scoped_params() = doDlangParserTest()
    fun teststatements_foreach_reverse() = doDlangParserTest()
    fun teststatements_foreach_types() = doDlangParserTest()
    fun teststatements_goto() = doDlangParserTest()
    fun teststatements_mixin() = doDlangParserTest()
    fun teststatements_pragma() = doDlangParserTest()
    fun teststatements_return() = doDlangParserTest()
    fun teststatements_scope() = doDlangParserTest()
    fun teststatements_synchronised() = doDlangParserTest()
    fun teststatements_if() = doDlangParserTest()
    fun teststatements_while() = doDlangParserTest()
    fun teststatements_while_condition_assignment() = doDlangParserTest()
    fun teststatements_with() = doDlangParserTest()
    fun teststatements_unterminated_if() = doDlangParserTest(checkResult = true, shouldPass = false)

    // arrays
    fun testarrays() = doDlangParserTest()
    fun testarrays_concatenation() = doDlangParserTest()
    fun testarrays_copying() = doDlangParserTest()
    fun testarrays_declarations() = doDlangParserTest()
    fun testarrays_dynamic() = doDlangParserTest()
    fun testarrays_function_properties() = doDlangParserTest()
    fun testarrays_bounds_checking() = doDlangParserTest()
    fun testarrays_dynamic_length() = doDlangParserTest()
    fun testarrays_initialization() = doDlangParserTest()
    fun testarrays_rectangular() = doDlangParserTest()
    fun testarrays_length() = doDlangParserTest()
    fun testarrays_operations() = doDlangParserTest()
    fun testarrays_pointer_arithmetic() = doDlangParserTest()
    fun testarrays_pointers() = doDlangParserTest()
    fun testarrays_printf_c() = doDlangParserTest()
    fun testarrays_properties() = doDlangParserTest()
    fun testarrays_setting() = doDlangParserTest()
    fun testarrays_slicing() = doDlangParserTest()
    fun testarrays_special_strings() = doDlangParserTest()
    fun testarrays_static() = doDlangParserTest()
    fun testarrays_void() = doDlangParserTest()

    // associative arrays
    fun testassocarrays() = doDlangParserTest()
    fun testassocarrays_literal() = doDlangParserTest()
    fun testassocarrays_classes() = doDlangParserTest()
    fun testassocarrays_constructor() = doDlangParserTest()
    fun testassocarrays_removing() = doDlangParserTest()
    fun testassocarrays_immutable() = doDlangParserTest()
    fun testassocarrays_membership() = doDlangParserTest()
    fun testassocarrays_structs() = doDlangParserTest()
    fun testassocarrays_word_count() = doDlangParserTest()
    fun testassocarrays_invalid_syntax() = doDlangParserTest(shouldPass = false)

    // structs and unions
    fun teststructs() = doDlangParserTest()
    fun teststructs_constructors() = doDlangParserTest()
    fun teststructs_dynamic_init() = doDlangParserTest()
    fun teststructs_initialization() = doDlangParserTest()
    fun teststructs_literal() = doDlangParserTest()
    fun teststructs_nested() = doDlangParserTest()
    fun teststructs_overload() = doDlangParserTest()
    fun testunion_initialization() = doDlangParserTest()
    fun teststructs_postblits() = doDlangParserTest()
    fun teststructs_shared() = doDlangParserTest()

    // classes
    fun testclasses() = doDlangParserTest()
    fun testclasses_alias_this() = doDlangParserTest()
    fun testclasses_constructors_fields() = doDlangParserTest()
    fun testclasses_nested() = doDlangParserTest()
    fun testclasses_properties() = doDlangParserTest()
    fun testclasses_allocators() = doDlangParserTest()
    fun testclasses_constructors() = doDlangParserTest()
    fun testclasses_deallocators() = doDlangParserTest()
    fun testclasses_destructors() = doDlangParserTest()
    fun testclasses_field_properties() = doDlangParserTest()
    fun testclasses_fields() = doDlangParserTest()
    fun testclasses_final() = doDlangParserTest()
    fun testclasses_members() = doDlangParserTest()
    fun testclasses_scope() = doDlangParserTest()
    fun testclasses_static_constructors() = doDlangParserTest()
    fun testclasses_static_destructor() = doDlangParserTest()
    fun testclasses_synchronized() = doDlangParserTest()

    // interfaces
    fun testinterfaces() = doDlangParserTest()
    fun testinterfaces_com() = doDlangParserTest()
    fun testinterfaces_contracts() = doDlangParserTest()
    fun testinterfaces_cplus() = doDlangParserTest()

    // enums
    fun testenums() = doDlangParserTest()
    fun testenums_errors() = doDlangParserTest(checkResult = true, shouldPass = false)
    fun testenums_error_unfinished() = doDlangParserTest(checkResult = false, shouldPass = false)

    // immutable const
    fun testconst_immutable() = doDlangParserTest()

    // functions
    fun testfunctions_auto() = doDlangParserTest()
    fun testfunctions_auto_ref() = doDlangParserTest()
    fun testfunctions_default_args() = doDlangParserTest()
    fun testfunctions_inheritance() = doDlangParserTest()
    fun testfunctions_nested() = doDlangParserTest()
    fun testfunctions_optional_parens() = doDlangParserTest()
    fun testfunctions_overloading() = doDlangParserTest()
    fun testfunctions_templates() = doDlangParserTest()
    fun testfunctions_ucfs() = doDlangParserTest()
    fun testfunctions_delegated_fp_closures() = doDlangParserTest()
    fun testfunctions_inout() = doDlangParserTest()
    fun testfunctions_local_static() = doDlangParserTest()
    fun testfunctions_local_vars() = doDlangParserTest()
    fun testfunctions_overload_sets() = doDlangParserTest()
    fun testfunctions_parameters() = doDlangParserTest()
    fun testfunctions_properties() = doDlangParserTest()
    fun testfunctions_pure() = doDlangParserTest()
    fun testfunctions_ref() = doDlangParserTest()
    fun testfunctions_shortened_body() = doDlangParserTest()
    fun testfunctions_string_mixin() = doDlangParserTest()
    fun testfunctions_variadic() = doDlangParserTest()
    fun testfunctions_variadic_d() = doDlangParserTest()
    fun testfunctions_variadic_lazy() = doDlangParserTest()
    fun testfunctions_variadic_typesafe() = doDlangParserTest()
    fun testfunctions_virtual() = doDlangParserTest()

    // operator overloading
    fun testoperator_overloading() = doDlangParserTest()

    // templates
    fun testtemplates() = doDlangParserTest()
    fun testtemplates_alias() = doDlangParserTest()
    fun testtemplates_alias_assign() = doDlangParserTest()
    fun testtemplates_defaults() = doDlangParserTest()
    fun testtemplates_function_templates() = doDlangParserTest()
    fun testtemplates_alias_params() = doDlangParserTest()
    fun testtemplates_argument_deduction() = doDlangParserTest()
    fun testtemplates_instant_scope() = doDlangParserTest()
    fun testtemplates_limitation() = doDlangParserTest()
    fun testtemplates_recursive() = doDlangParserTest()
    fun testtemplates_specialization() = doDlangParserTest()
    fun testtemplates_typed_params() = doDlangParserTest()
    fun testtemplates_mixins() = doDlangParserTest()
    fun testtemplates_mixins_scope() = doDlangParserTest()
    fun testtemplates_nested() = doDlangParserTest()
    fun testtemplates_params() = doDlangParserTest()
    fun testtemplates_ref() = doDlangParserTest()
    fun testtemplates_this_params() = doDlangParserTest()
    fun testtemplates_tuples() = doDlangParserTest()
    fun testtemplates_typed_specialization() = doDlangParserTest()
    fun testtemplates_variable() = doDlangParserTest()
    fun testtemplates_variable_declaration() = doDlangParserTest()

    // contract programming
    fun testcontract_programming() = doDlangParserTest()

    // conditional compilation
    fun testconditional_compilation() = doDlangParserTest()

    // traits
    fun testtraits_allmembers() = doDlangParserTest()
    fun testtraits_derivedmembers() = doDlangParserTest()
    fun testtraits_getfunctionattributes() = doDlangParserTest()
    fun testtraits_getpointerbitmap() = doDlangParserTest()
    fun testtraits_getprotection() = doDlangParserTest()
    fun testtraits_isabstractionfunction() = doDlangParserTest()
    fun testtraits_isarithmetic() = doDlangParserTest()
    fun testtraits_isfinalfunction() = doDlangParserTest()
    fun testtraits_isrefoutlazy() = doDlangParserTest()
    fun testtraits_isvirtualmethod() = doDlangParserTest()
    fun testtraits_compiles() = doDlangParserTest()
    fun testtraits_getmember() = doDlangParserTest()
    fun testtraits_getoverloads() = doDlangParserTest()
    fun testtraits_getvirtualmethods() = doDlangParserTest()
    fun testtraits_hasmember() = doDlangParserTest()
    fun testtraits_isabstractclass() = doDlangParserTest()
    fun testtraits_isoverridefunction() = doDlangParserTest()
    fun testtraits_issame() = doDlangParserTest()
    fun testtraits_istemplate() = doDlangParserTest()
    fun testtraits_getunittests() = doDlangParserTest()
    fun testtraits_special_keywords() = doDlangParserTest()
    fun testtraits_getattributes() = doDlangParserTest()

    // types
    fun testtype_mixin() = doDlangParserTest()

    // unittest
    fun testunittests() = doDlangParserTest()

    // bug fixes
    fun testbitwise_or_bug() = doDlangParserTest()
    fun testbug_assoc_array() = doDlangParserTest()
    fun testbug_try_catch() = doDlangParserTest()
    fun testbug_fixes() = doDlangParserTest()
    fun teststaticifbug() = doDlangParserTest()
    fun testcollection_of_bugs() = doDlangParserTest()

    // standard library
    fun teststdio() = doDlangParserTest(checkResult = false, shouldPass = true)
    fun testtraits() = doDlangParserTest(checkResult = false, shouldPass = true)
    fun teststring() = doDlangParserTest(checkResult = false, shouldPass = true)
    fun testbase64() = doDlangParserTest(checkResult = false, shouldPass = true)
    fun testarray() = doDlangParserTest(checkResult = false, shouldPass = true)
    fun testtypecons() = doDlangParserTest(checkResult = false, shouldPass = true)
    fun testparallelism() = doDlangParserTest(checkResult = false, shouldPass = true)

    //dmd source
    fun testexpression() = doDlangParserTest()

    // dmd fail_compilation test file
    fun teste15876_1() = doDlangParserTest(checkResult = true, shouldPass = false)

    // libdparse incompleteStatement198_1.d test file
    fun testincompleteStatement198_1() = doDlangParserTest(checkResult = true, shouldPass = false)
    fun testkiller() = doDlangParserTest(checkResult = false, shouldPass = false)
    fun testkiller2() = doDlangParserTest(checkResult = false, shouldPass = false)
    fun testobject() = doDlangParserTest()
}

