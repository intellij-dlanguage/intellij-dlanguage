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

}
