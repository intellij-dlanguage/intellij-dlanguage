package io.github.intellij.dlanguage.resolve

import org.intellij.lang.annotations.Language
import org.junit.Test

class DImportsUsageResolveTest : DResolveTestCase() {

    override fun getTestDataPath(): String = ""

    @Language("D") val content = """
                module resolve.to.include;

                void write(string a){}
                void /*<resolved>*/writeln(string a){}
            """.trimIndent()

    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
    }

    private fun doCheckByText(@Language("D") main: String) = doCheckByText2(main, content)

    @Test
    fun testLocalImport() {
        doCheckByText("""
            void main() {
                import resolve.to.include;
                /*<ref>*/writeln("local");
            }
        """.trimIndent())
    }

    @Test
    fun testSelectiveImportUseNotImportedMethod() {
        doCheckByText(
            """
                import resolve.to.include : writeln;

                void main() {
                    /*<ref>*/writeln("selective");
                }
            """)
    }

    // TODO need to handle the static imports
    /*@Test
    fun testStaticImport() {
        doCheckByText(
            """
                static import resolve.to.include;

                void main() {
                    resolve.to.include./*<ref>*/writeln("static");
                }
            """)
    }*/

    @Test
    fun testRenamedImport() {
        doCheckByText(
            """
                import io = resolve.to.include;

                void main() {
                    io./*<ref>*/writeln("renamed");
                }
            """)
    }

    @Test
    fun testRenamedImportResolveRename() {
        doCheckByText1(
            """
                import /*<resolved>*/io = resolve.to.include;

                void main() {
                    /*<ref>*/io.writeln("renamed");
                }
            """)
    }

    @Test
    fun testImportNamedBinding() {
        doCheckByText1(
            """
                import resolve.to.include : /*<resolved>*/foo = writeln;

                void main() {
                    /*<ref>*/foo("named selective");
                }
            """)
    }

    @Test
    fun testSelectiveAndRenamedImport() {
        doCheckByText1(
            """
                import io = resolve.to.include: /*<resolved>*/foo = writeln;

                void main() {
                    /*<ref>*/foo("renamed import named selective");
                }
            """)
    }

    @Test
    fun testSelectiveAndRenamedImport2() {
        doCheckByText(
            """
                import io = resolve.to.include: foo = write;

                void main() {
                    io./*<ref>*/writeln("renamed");
                }
            """)
    }

    @Test
    fun testSelectiveImportShouldNotFindNestedObject() {
        doCheckByText2(
            """
                import resolve.to.include: B;

                void main() {
                    A./*<ref>*/B variable;
                }
            """,
            """
                module resolve.to.include;
                struct A {
                    struct /*<resolved>*/B {}
                    void foo{}
                }

            """,
            false)
    }

    @Test
    fun testSelectiveImportShouldResolveSelectiveImportSection() {
        doCheckByText(
            """
                import resolve.to.include : /*<ref>*/writeln;
            """)
    }

    @Test
    fun testSelectiveRenamedImportShouldResolveSelectiveImportSection() {
        doCheckByText(
            """
                import resolve.to.include : foo = /*<ref>*/writeln;
            """)
    }

    @Test
    fun testSelectiveRenamedImportShouldResolveSelectiveImportSectionSameName() {
        doCheckByText(
            """
                import resolve.to.include : writeln = /*<ref>*/writeln;
            """)
    }

    @Test
    fun testResolveInAlias() {
        doCheckByText2(
            """
                import resolve.to.include;

                final class A {
                    alias B = /*<ref>*/Foo.Bar;
                }
            """,
            """
                module resolve.to.include;
                enum /*<resolved>*/Foo {
                    Bar
                }

            """)
    }

}
