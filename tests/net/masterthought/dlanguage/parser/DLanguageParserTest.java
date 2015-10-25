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
    public void testdeclaration_c_style() { doTest(true, true); }
    public void testdeclaration_symbols() { doTest(true, true); }
    public void testdeclaration_type_inference() { doTest(true, true); }
    public void testdeclaration_array_literal() { doTest(true, true); }
    public void testdeclaration_alias() { doTest(true, true); }
    public void testdeclaration_alias2() { doTest(true, true); }
    public void testdeclaration_alias3() { doTest(true, true); }
    public void testdeclaration_alias_symbol() { doTest(true, true); }
    public void testdeclaration_extern() { doTest(true, true); }
    public void testdeclaration_typeof() { doTest(true, true); }
    public void testdeclaration_typeof2() { doTest(true, true); }
    public void testdeclaration_typeof3() { doTest(true, true); }
    public void testdeclaration_void() { doTest(true, true); }
    public void testdeclaration_type_qualifier() { doTest(true, true); }
    public void testdeclaration_storage() { doTest(true, true); }
    public void testdeclaration_type_storage1() { doTest(true, true); }
    public void testdeclaration_type_storage2() { doTest(true, true); }
    public void testdeclaration_type_storage3() { doTest(true, true); }
    public void testdeclaration_type_storage4() { doTest(true, true); }

    // types
    public void testtype_arithmetic_conversion() { doTest(true, true); }
    public void testtype_implicit_conversion() { doTest(true, true); }
    public void testtype_delegates() { doTest(true, true); }
    public void testtype_delegates2() { doTest(true, true); }
    public void testtype_delegates3() { doTest(true, true); }
    public void testtype_delegates4() { doTest(true, true); }
    public void testtype_c_style() { doTest(true, true); }

    // properties
    public void testproperties_init() { doTest(true, true); }
    public void testproperties_init2() { doTest(true, true); }
    public void testproperties_init3() { doTest(true, true); }
    public void testproperties_stringof() { doTest(true, true); }
    public void testproperties_sizeof() { doTest(true, true); }
    public void testproperties_user_defined() { doTest(true, true); }

    // attributes
    public void testattributes_align() { doTest(true, true); }
    public void testattributes_auto() { doTest(true, true); }
    public void testattributes_const() { doTest(true, true); }
    public void testattributes_deprecated() { doTest(true, true); }
    public void testattributes_deprecated_optional() { doTest(true, true); }
    public void testattributes_disabled() { doTest(true, true); }
    public void testattributes_extern() { doTest(true, true); }
    public void testattributes_gshared() { doTest(true, true); }
    public void testattributes_namespaces() { doTest(true, true); }
    public void testattributes_namespaces_multiple() { doTest(true, true); }
    public void testattributes_nogc() { doTest(true, true); }
    public void testattributes_override() { doTest(true, true); }
    public void testattributes_private() { doTest(true, true); }
    public void testattributes_protection() { doTest(true, true); }
    public void testattributes_static() { doTest(true, true); }
    public void testattributes_traits() { doTest(true, true); }
    public void testattributes_user_defined() { doTest(true, true); }

    // pragmas
    public void testpragmas() { doTest(true, true); }
    public void testpragmas_inline() { doTest(true, true); }
    public void testpragmas_methods() { doTest(true, true); }

    // expressions
    public void testexpressions_array_literal() { doTest(true, true); }
    public void testexpressions_assert() { doTest(true, true); }
    public void testexpressions_associative_array_literal() { doTest(true, true); }
    public void testexpressions_cast() { doTest(true, true); }
    public void testexpressions_cast2() { doTest(true, true); }
    public void testexpressions_cast_float() { doTest(true, true); }
    public void testexpressions_cast_pointer() { doTest(true, true); }
    public void testexpressions_class_comparison() { doTest(true, true); }
    public void testexpressions_equality() { doTest(true, true); }
    public void testexpressions_function_literal() { doTest(true, true); }
    public void testexpressions_function_literal2() { doTest(true, true); }
    public void testexpressions_import() { doTest(true, true); }
    public void testexpressions_in() { doTest(true, true); }
    public void testexpressions_is() { doTest(true, true); }
    public void testexpressions_lambda() { doTest(true, true); }
    public void testexpressions_mixin() { doTest(true, true); }
    public void testexpressions_new() { doTest(true, true); }
    public void testexpressions_operators() { doTest(true, true); }
    public void testexpressions_precedence() { doTest(true, true); }
    public void testexpressions_scalar() { doTest(true, true); }
    public void testexpressions_slice() { doTest(true, true); }
    public void testexpressions_string_literal() { doTest(true, true); }
    public void testexpressions_this() { doTest(true, true); }
    public void testexpressions_type_info() { doTest(true, true); }
}
