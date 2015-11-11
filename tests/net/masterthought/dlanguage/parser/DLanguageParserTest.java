package net.masterthought.dlanguage.parser;

import net.masterthought.dlanguage.DLanguageParserDefinition;

public class DLanguageParserTest extends DLanguageParserTestBase {

    public DLanguageParserTest() {
        super("parser", "d", new DLanguageParserDefinition());
    }

    // modules
    public void testmodule1() { doTest(true, true); }
    public void testmodule2() { doTest(true, true); }
    public void testmodule_deprecated1() { doTest(true, true); }
    public void testmodule_deprecated2() { doTest(true, true); }
    public void testimport() { doTest(true, true); }
    public void testmodule_scope() { doTest(true, true); }

    // declarations
    public void testdeclaration1() { doTest(true, true); }
    public void testdeclaration_alias() { doTest(true, true); }
    public void testdeclaration_alias2() { doTest(true, true); }
//    public void testdeclaration_c_style() { doTest(true, true); }
//    public void testdeclaration_symbols() { doTest(true, true); }
    public void testdeclaration_type_storage1() { doTest(true, true); }
//    public void testdeclaration_type_storage2() { doTest(true, true); }
    public void testdeclaration_alias3() { doTest(true, true); }
    public void testdeclaration_extern() { doTest(true, true); }
    public void testdeclaration_typeof2() { doTest(true, true); }
    public void testdeclaration_void() { doTest(true, true); }
    public void testdeclaration_storage() { doTest(true, true); }
    public void testdeclaration_type_storage3() { doTest(true, true); }
    public void testdeclaration_type_storage4() { doTest(true, true); }
    public void testdeclaration_alias_symbol() { doTest(true, true); }
    public void testdeclaration_type_inference() { doTest(true, true); }
    public void testdeclaration_array_literal() { doTest(true, true); }
    public void testdeclaration_type_qualifier() { doTest(true, true); }
    public void testdeclaration_typeof3() { doTest(true, true); }
    public void testdeclaration_typeof() { doTest(true, true); }

//  types
    public void testtype_arithmetic_conversion() { doTest(true, true); }
    public void testtype_implicit_conversion() { doTest(true, true); }
    public void testtype_delegates() { doTest(true, true); }
    public void testtype_delegates2() { doTest(true, true); }
    public void testtype_delegates3() { doTest(true, true); }
    public void testtype_delegates4() { doTest(true, true); }
    public void testtype_c_style() { doTest(true, true); }

//  properties
    public void testproperties_init() { doTest(true, true); }
    public void testproperties_init2() { doTest(true, true); }
    public void testproperties_init3() { doTest(true, true); }
    public void testproperties_user_defined() { doTest(true, true); }
    public void testproperties_stringof() { doTest(true, true); }
    public void testproperties_sizeof() { doTest(true, true); }

//  attributes
    public void testattributes_align() { doTest(true, true); }
    public void testattributes_auto() { doTest(true, true); }
    public void testattributes_const() { doTest(true, true); }
    public void testattributes_deprecated() { doTest(true, true); }
    public void testattributes_deprecated_optional() { doTest(true, true); }
    public void testattributes_disabled() { doTest(true, true); }
    public void testattributes_extern() { doTest(true, true); }
    public void testattributes_gshared() { doTest(true, true); }
    public void testattributes_namespaces() { doTest(true, true); }
    public void testattributes_override() { doTest(true, true); }
    public void testattributes_private() { doTest(true, true); }
    public void testattributes_protection() { doTest(true, true); }
    public void testattributes_static() { doTest(true, true); }
//    public void testattributes_traits() { doTest(true, true); }
//    public void testattributes_user_defined() { doTest(true, true); }
//    public void testattributes_namespaces_multiple() { doTest(true, true); }
//    public void testattributes_nogc() { doTest(true, true); }

//  pragmas
    public void testpragmas() { doTest(true, true); }
    public void testpragmas_inline() { doTest(true, true); }
    public void testpragmas_methods() { doTest(true, true); }

//  expressions
    public void testexpressions_array_literal() { doTest(true, true); }
    public void testexpressions_assert() { doTest(true, true); }
    public void testexpressions_associative_array_literal() { doTest(true, true); }
    public void testexpressions_cast() { doTest(true, true); }
    public void testexpressions_cast2() { doTest(true, true); }
    public void testexpressions_cast_float() { doTest(true, true); }
    public void testexpressions_cast_pointer() { doTest(true, true); }
//    public void testexpressions_is() { doTest(true, true); }
    public void testexpressions_lambda() { doTest(true, true); }
    public void testexpressions_new() { doTest(true, true); }
    public void testexpressions_class_comparison() { doTest(true, true); }
    public void testexpressions_equality() { doTest(true, true); }
    public void testexpressions_function_literal() { doTest(true, true); }
    public void testexpressions_function_literal2() { doTest(true, true); }
    public void testexpressions_import() { doTest(true, true); }
    public void testexpressions_in() { doTest(true, true); }
    public void testexpressions_mixin() { doTest(true, true); }
    public void testexpressions_operators() { doTest(true, true); }
    public void testexpressions_precedence() { doTest(true, true); }
    public void testexpressions_scalar() { doTest(true, true); }
    public void testexpressions_slice() { doTest(true, true); }
    public void testexpressions_string_literal() { doTest(true, true); }
    public void testexpressions_this() { doTest(true, true); }
    public void testexpressions_type_info() { doTest(true, true); }
//
//    // statements
//    public void teststatements_asm() { doTest(true, true); }
//    public void teststatements_asm_inline() { doTest(true, true); }
//    public void teststatements_foreach_associative_array() { doTest(true, true); }
//    public void teststatements_foreach_delegates() { doTest(true, true); }
//    public void teststatements_foreach_templated() { doTest(true, true); }
//    public void teststatements_foreach_tuples() { doTest(true, true); }
//    public void teststatements_switch() { doTest(true, true); }
//    public void teststatements_switch2() { doTest(true, true); }
    public void teststatements_block() { doTest(true, true); }
    public void teststatements_break() { doTest(true, true); }
    public void teststatements_declaration() { doTest(true, true); }
    public void teststatements_declarator() { doTest(true, true); }
    public void teststatements_do() { doTest(true, true); }
    public void teststatements_expression() { doTest(true, true); }
    public void teststatements_for() { doTest(true, true); }
    public void teststatements_foreach() { doTest(true, true); }
    public void teststatements_foreach_aggregates() { doTest(true, true); }
    public void teststatements_foreach_array() { doTest(true, true); }
    public void teststatements_foreach_range() { doTest(true, true); }
    public void teststatements_foreach_ref_params() { doTest(true, true); }
    public void teststatements_foreach_reverse() { doTest(true, true); }
    public void teststatements_foreach_types() { doTest(true, true); }
    public void teststatements_goto() { doTest(true, true); }
    public void teststatements_mixin() { doTest(true, true); }
    public void teststatements_return() { doTest(true, true); }
    public void teststatements_scope() { doTest(true, true); }
    public void teststatements_synchronised() { doTest(true, true); }
    public void teststatements_throw() { doTest(true, true); }
    public void teststatements_while() { doTest(true, true); }
    public void teststatements_with() { doTest(true, true); }

    // arrays
    public void testarrays() { doTest(true, true); }
    public void testarrays_concatenation() { doTest(true, true); }
    public void testarrays_copying() { doTest(true, true); }
    public void testarrays_declarations() { doTest(true, true); }
    public void testarrays_dynamic() { doTest(true, true); }
    public void testarrays_function_properties() { doTest(true, true); }
//    public void testarrays_bounds_checking() { doTest(true, true); }
//    public void testarrays_dynamic_length() { doTest(true, true); }
//    public void testarrays_initialization() { doTest(true, true); }
//    public void testarrays_rectangular() { doTest(true, true); }
    public void testarrays_length() { doTest(true, true); }
    public void testarrays_operations() { doTest(true, true); }
    public void testarrays_pointer_arithmetic() { doTest(true, true); }
    public void testarrays_pointers() { doTest(true, true); }
    public void testarrays_printf_c() { doTest(true, true); }
    public void testarrays_properties() { doTest(true, true); }
    public void testarrays_setting() { doTest(true, true); }
    public void testarrays_slicing() { doTest(true, true); }
    public void testarrays_special_strings() { doTest(true, true); }
    public void testarrays_static() { doTest(true, true); }
    public void testarrays_void() { doTest(true, true); }


// associative arrays
    public void testassocarrays() { doTest(true, true); }
    public void testassocarrays_classes() { doTest(true, true); }
    public void testassocarrays_constructor() { doTest(true, true); }
    public void testassocarrays_removing() { doTest(true, true); }
//    public void testassocarrays_immutable() { doTest(true, true); }
//    public void testassocarrays_membership() { doTest(true, true); }
//    public void testassocarrays_structs() { doTest(true, true); }
//    public void testassocarrays_word_count() { doTest(true, true); }

// structs and unions
//      public void teststructs() { doTest(true, true); }
      public void teststructs_constructors() { doTest(true, true); }
      public void teststructs_dynamic_init() { doTest(true, true); }
//      public void teststructs_initialization() { doTest(true, true); }
//      public void teststructs_literal() { doTest(true, true); }
      public void teststructs_nested() { doTest(true, true); }
//      public void teststructs_overload() { doTest(true, true); }
      public void testunion_initialization() { doTest(true, true); }
      public void teststructs_postblits() { doTest(true, true); }
      public void teststructs_shared() { doTest(true, true); }


// classes
      public void testclasses() { doTest(true, true); }
      public void testclasses_alias_this() { doTest(true, true); }
      public void testclasses_constructors_fields() { doTest(true, true); }
      public void testclasses_nested() { doTest(true, true); }
      public void testclasses_properties() { doTest(true, true); }
      public void testclasses_allocators() { doTest(true, true); }
      public void testclasses_constructors() { doTest(true, true); }
      public void testclasses_deallocators() { doTest(true, true); }
      public void testclasses_destructors() { doTest(true, true); }
      public void testclasses_field_properties() { doTest(true, true); }
      public void testclasses_fields() { doTest(true, true); }
      public void testclasses_final() { doTest(true, true); }
      public void testclasses_members() { doTest(true, true); }
      public void testclasses_scope() { doTest(true, true); }
      public void testclasses_static_constructors() { doTest(true, true); }
      public void testclasses_static_destructor() { doTest(true, true); }
      public void testclasses_synchronized() { doTest(true, true); }

// interfaces
      public void testinterfaces() { doTest(true, true); }
      public void testinterfaces_com() { doTest(true, true); }
//      public void testinterfaces_contracts() { doTest(true, true); }
//      public void testinterfaces_cplus() { doTest(true, true); }



// enums
//        public void testenums() { doTest(true, true); }

// immutable const
//    public void testconst_immutable() { doTest(true, true); }

// functions
      public void testfunctions_auto() { doTest(true, true); }
      public void testfunctions_auto_ref() { doTest(true, true); }
//      public void testfunctions_default_args() { doTest(true, true); }
//      public void testfunctions_inheritance() { doTest(true, true); }
//      public void testfunctions_nested() { doTest(true, true); }
//      public void testfunctions_optional_parens() { doTest(true, true); }
//      public void testfunctions_overloading() { doTest(true, true); }
//      public void testfunctions_templates() { doTest(true, true); }
//      public void testfunctions_ucfs() { doTest(true, true); }
      public void testfunctions_delegated_fp_closures() { doTest(true, true); }
      public void testfunctions_inout() { doTest(true, true); }
      public void testfunctions_local_static() { doTest(true, true); }
      public void testfunctions_local_vars() { doTest(true, true); }
      public void testfunctions_overload_sets() { doTest(true, true); }
      public void testfunctions_parameters() { doTest(true, true); }
      public void testfunctions_properties() { doTest(true, true); }
      public void testfunctions_pure() { doTest(true, true); }
      public void testfunctions_ref() { doTest(true, true); }
      public void testfunctions_string_mixin() { doTest(true, true); }
      public void testfunctions_variadic() { doTest(true, true); }
      public void testfunctions_variadic_d() { doTest(true, true); }
      public void testfunctions_variadic_lazy() { doTest(true, true); }
      public void testfunctions_variadic_typesafe() { doTest(true, true); }
      public void testfunctions_virtual() { doTest(true, true); }

// operator overloading
//        public void testoperator_overloading() { doTest(true, true); }


// templates
//    public void testtemplates() { doTest(true, true); }
//    public void testtemplates_alias() { doTest(true, true); }
//    public void testtemplates_defaults() { doTest(true, true); }
//    public void testtemplates_function_templates() { doTest(true, true); }
    public void testtemplates_alias_params() { doTest(true, true); }
    public void testtemplates_argument_deduction() { doTest(true, true); }
    public void testtemplates_instant_scope() { doTest(true, true); }
    public void testtemplates_limitation() { doTest(true, true); }
    public void testtemplates_recursive() { doTest(true, true); }
    public void testtemplates_specialization() { doTest(true, true); }
    public void testtemplates_typed_params() { doTest(true, true); }
//    public void testtemplates_mixins() { doTest(true, true); }
//    public void testtemplates_mixins_scope() { doTest(true, true); }
//    public void testtemplates_nested() { doTest(true, true); }
//    public void testtemplates_params() { doTest(true, true); }
//    public void testtemplates_ref() { doTest(true, true); }
//    public void testtemplates_this_params() { doTest(true, true); }
//    public void testtemplates_tuples() { doTest(true, true); }
//    public void testtemplates_typed_specialization() { doTest(true, true); }
//    public void testtemplates_variable() { doTest(true, true); }


// contract programming
//    public void testcontract_programming() { doTest(true, true); }

// conditional compilation
//    public void testconditional_compilation() { doTest(true, true); }

// traits
    public void testtraits_allmembers() { doTest(true, true); }
    public void testtraits_derivedmembers() { doTest(true, true); }
    public void testtraits_getfunctionattributes() { doTest(true, true); }
    public void testtraits_getpointerbitmap() { doTest(true, true); }
    public void testtraits_getprotection() { doTest(true, true); }
    public void testtraits_isabstractionfunction() { doTest(true, true); }
    public void testtraits_isarithmetic() { doTest(true, true); }
    public void testtraits_isfinalfunction() { doTest(true, true); }
    public void testtraits_isrefoutlazy() { doTest(true, true); }
    public void testtraits_isvirtualmethod() { doTest(true, true); }
    public void testtraits_compiles() { doTest(true, true); }
    public void testtraits_getmember() { doTest(true, true); }
    public void testtraits_getoverloads() { doTest(true, true); }
    public void testtraits_getvirtualmethods() { doTest(true, true); }
    public void testtraits_hasmember() { doTest(true, true); }
    public void testtraits_isabstractclass() { doTest(true, true); }
    public void testtraits_isoverridefunction() { doTest(true, true); }
    public void testtraits_issame() { doTest(true, true); }
    public void testtraits_istemplate() { doTest(true, true); }
//    public void testtraits_getunittests() { doTest(true, true); }
//    public void testtraits_special_keyword() { doTest(true, true); }    
//    public void testtraits_getattributes() { doTest(true, true); }


// unittest
//   public void testunittests() { doTest(true, true); }
}


