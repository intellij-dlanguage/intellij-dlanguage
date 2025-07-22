package io.github.intellij.dlanguage.resolve

import org.junit.Test

/**
 * Test import cases for scoped import.
 *
 * Test cases taken from specs: https://dlang.org/spec/module.html#scoped_imports
 */
class DScopedImportResolveTest : DResolveTestCase() {

    override fun getTestDataPath(): String = ""

    @Test
    fun test1() = doCheckByText2("""
        void main()
        {
            void /*<resolved>*/writeln(string) {}
            void foo()
            {
                /*<ref>*/writeln("bar");
                import std.stdio;
                void writeln(string) {}
            }
        }
    """.trimIndent(),
        """
            module std.stdio;

                void write(string a){}
                void writeln(string a){}
        """.trimIndent())

    @Test
    fun test2() = doCheckByText2("""
        void main()
        {
            void writeln(string) {}
            void foo()
            {
                import std.stdio;
                /*<ref>*/writeln("bar");
                void writeln(string) {}
            }
        }
    """.trimIndent(),
        """
            module std.stdio;

                void write(string a){}
                void /*<resolved>*/writeln(string a){}
        """.trimIndent())

    @Test
    fun test3() = doCheckByText2("""
        void main()
        {
            void writeln(string) {}
            void foo()
            {
                import std.stdio;
                void /*<resolved>*/writeln(string) {}
                /*<ref>*/writeln("bar");
            }
        }
    """.trimIndent(),
        """
            module std.stdio;

                void write(string a){}
                void writeln(string a){}
        """.trimIndent())

    @Test
    fun test4() = doCheckByText2("""
        void main()
        {
            void /*<resolved>*/writeln(string) {}
            void foo()
            {
                import std.stdio;
                void writeln(string) {}
            }
            /*<ref>*/writeln("bar");
        }
    """.trimIndent(),
        """
            module std.stdio;

                void write(string a){}
                void writeln(string a){}
        """.trimIndent())

    @Test
    fun test5() = doCheckByText2("""
        void main()
        {
            void writeln(string) {}
            void foo()
            {
                import std.stdio;
                void writeln(string) {}
            }
            std.stdio./*<ref>*/writeln("bar");
        }
    """.trimIndent(),
        """
            module std.stdio;

                void write(string a){}
                void writeln(string a){}
        """.trimIndent(),
        false)
}
