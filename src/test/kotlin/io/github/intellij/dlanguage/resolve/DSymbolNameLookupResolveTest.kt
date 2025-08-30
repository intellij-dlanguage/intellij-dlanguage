package io.github.intellij.dlanguage.resolve

import org.intellij.lang.annotations.Language
import org.junit.Test


/**
 * Test resolution algorithm fom symbol when there is import "conflict".
 *
 * Test case are taken from specs: https://dlang.org/spec/module.html#name_lookup
 */
class DSymbolNameLookupResolveTest : DResolveTestCase() {

    override fun getTestDataPath(): String = ""

    private fun doCheckByText3(@Language("D") main: String,
                               @Language("D") second: String,
                               @Language("D") third: String,
                               succeed: Boolean = true) {
        val referenceIndicator = "/*<ref>*/"
        val resolvedIndicator = "/*<resolved>*/"
        prepareFileFindReferences(referenceIndicator,  resolvedIndicator, main, "main.d")
        prepareFileFindReferences(referenceIndicator,  resolvedIndicator, second, "file2.d")
        prepareFileFindReferences(referenceIndicator,  resolvedIndicator, third, "file3.d")
        doCheck(succeed)
    }

    @Test
    fun `test C module foo case`() = doCheckByText2("""
                module A;
                void foo();
                void bar();
            """.trimIndent(),
            """
                module C;
                import A;
                void /*<resolved>*/foo();
                void test()
                {
                    /*<ref>*/foo(); // C.foo() is called
                }
            """.trimIndent())

    @Test
    fun `test C module bar case`() = doCheckByText2("""
                module A;
                void foo();
                void /*<resolved>*/bar();
            """.trimIndent(),
        """
                module C;
                import A;
                void foo();
                void test()
                {
                    /*<ref>*/bar(); // A.bar() is called
                }
            """.trimIndent())

    /* TODO enable once fixed.
        Needs to properly implement the lookup algorithm for that : https://dlang.org/spec/module.html#name_lookup
    @Test
    fun `test D module foo case`() = doCheckByText3("""
                module A;
                void foo();
                void bar();
            """.trimIndent(),
        """
                module B;
                void foo();
                void bar();
            """.trimIndent(),
        """
                module D;
                import A;
                import B;
                void test()
                {
                    /*<ref>*/foo(); // error, A.foo() or B.foo() ?
                }
            """.trimIndent(),
        false)*/

    @Test
    fun `test D module A_foo case`() = doCheckByText3("""
                module A;
                void /*<resolved>*/foo();
                void bar();
            """.trimIndent(),
        """
                module B;
                void foo();
                void bar();
            """.trimIndent(),
        """
                module D;
                import A;
                import B;
                void test()
                {
                    A./*<ref>*/foo(); // A.foo() is called
                }
            """.trimIndent())

    @Test
    fun `test D module B_foo case`() = doCheckByText3("""
                module A;
                void foo();
                void bar();
            """.trimIndent(),
        """
                module B;
                void /*<resolved>*/foo();
                void bar();
            """.trimIndent(),
        """
                module D;
                import A;
                import B;
                void test()
                {
                    B./*<ref>*/foo(); // B.foo() is called
                }
            """.trimIndent())

    @Test
    fun `test E module foo case`() = doCheckByText3("""
                module A;
                void foo();
                void bar();
            """.trimIndent(),
        """
                module B;
                void foo();
                void bar();
            """.trimIndent(),
        """
                module E;
                import A;
                import B;
                alias /*<resolved>*/foo = B.foo;
                void test()
                {
                    /*<ref>*/foo(); // call B.foo()
                }
            """.trimIndent())

    @Test
    fun `test E module A_foo case`() = doCheckByText3("""
                module A;
                void /*<resolved>*/foo();
                void bar();
            """.trimIndent(),
        """
                module B;
                void foo();
                void bar();
            """.trimIndent(),
        """
                module E;
                import A;
                import B;
                alias foo = B.foo;
                void test()
                {
                    A./*<ref>*/foo(); // A.foo() is called
                }
            """.trimIndent())

    @Test
    fun `test E module B_foo case`() = doCheckByText3("""
                module A;
                void foo();
                void bar();
            """.trimIndent(),
        """
                module B;
                void foo();
                void bar();
            """.trimIndent(),
        """
                module E;
                import A;
                import B;
                alias /*<resolved>*/foo = B.foo;
                void test()
                {
                    /*<ref>*/foo(); // B.foo() is called
                }
            """.trimIndent())

    // Note: this test is not technically in the spec files, but it is here to validate the resolve algorithm
    // AKA: if a package (of imported module) as the same as a symbol, then it resolve to the package and not to the symbol
    // The test ensure that we don’t resolve the symbol (it is not as simple to check that it resolve correctly to the package,
    // but this case is already covered by other tests)
    @Test
    fun `test package resolving has priority over symbol`() = doCheckByText3(
        """
            module principal;

            void main() {
                import std.stdio;
                import a;

                /*<ref>*/std;
            }
        """.trimIndent(),
        """
            module std.stdio;

            void writeln();
        """.trimIndent(),
        """
            module a;
            void /*<resolved>*/std();
        """.trimIndent(),
        succeed = false)
}
