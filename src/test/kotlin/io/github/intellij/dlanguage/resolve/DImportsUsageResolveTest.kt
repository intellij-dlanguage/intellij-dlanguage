package io.github.intellij.dlanguage.resolve

import org.intellij.lang.annotations.Language

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

    fun testSelectiveImportUseNotImportedMethod() {
        doCheckByText(
            """
                import resolve.to.include : writeln;

                void main() {
                    /*<ref>*/writeln("selective");
                }
            """)
    }

    fun testStaticImport() {
        doCheckByText(
            """
                static import resolve.to.include;

                void main() {
                    resolve.to.include./*<ref>*/writeln("static");
                }
            """)
    }

    fun testRenamedImport() {
        doCheckByText(
            """
                import io = resolve.to.include;

                void main() {
                    io./*<ref>*/writeln("renamed");
                }
            """)
    }

    fun testImportNamedBinding() {
        doCheckByText(
            """
                import resolve.to.include : foo = writeln;

                void main() {
                    /*<ref>*/foo("named selective");
                }
            """)
    }

    fun testSelectiveAndRenamedImport() {
        doCheckByText(
            """
                import io = resolve.to.include: foo = writeln;

                void main() {
                    /*<ref>*/foo("renamed import named selective");
                }
            """)
    }

    fun testSelectiveAndRenamedImport2() {
        doCheckByText(
            """
                import io = resolve.to.include: foo = write;

                void main() {
                    io./*<ref>*/writeln("renamed");
                }
            """)
    }

}
