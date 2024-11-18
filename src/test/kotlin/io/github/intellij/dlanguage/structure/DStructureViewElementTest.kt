package io.github.intellij.dlanguage.structure

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import io.github.intellij.dlanguage.DlangFileType
import org.junit.Test

class DStructureViewElementTest : LightPlatformCodeInsightFixture4TestCase() {

    @Test
    fun `presentation for constructor`() {
        assertStructureViewText(
            """
                this() {
                    writeln("constructor");
                }
            """.trimIndent(),
            "this()"
        )

        assertStructureViewText(
            """
                this(int arg1, int arg2) {
                    writeln("constructor");
                }
            """.trimIndent(),
            "this(int, int)"
        )
    }

    /*
    * https://github.com/intellij-dlanguage/intellij-dlanguage/issues/1024
    */
    @Test
    fun `presentation for delegate`() {
        assertStructureViewText(
            "void delegate() @safe @nogc nothrow refAdd_;",
            "refAdd_ : void delegate() @safe @nogc nothrow"
        )

        assertStructureViewText(
            "void delegate() @safe @nogc nothrow refSub_;",
            "refSub_ : void delegate() @safe @nogc nothrow"
        )

        assertStructureViewText(
            "void[] delegate(size_t, TypeInfo ti = null) @safe @nogc nothrow allocate_;",
            "allocate_ : void [] delegate(size_t, TypeInfo ti = null) @safe @nogc nothrow"
        )

        assertStructureViewText(
            "bool delegate(scope void[]) @safe @nogc nothrow deallocate_;",
            "deallocate_ : bool delegate(scope void[]) @safe @nogc nothrow"
        )
    }

    @Test
    fun `presentation for function declaration`() {
        assertStructureViewText(
            """
                void hello() {
                    writeln("Hello World");
                }
            """.trimIndent(),
            "hello() : void"
        )
    }

    @Test
    fun `presentation for lambda declaration`() {
        assertStructureViewText(
            "auto hello = () => writeln(\"Hello World\");",
            "hello = () => writeln(\"Hello World\")"
        )
    }

    @Test
    fun `presentation for enum declaration`() {
        assertStructureViewText(
            """
                enum IntOps {
                    add = 0,
                    sub = 1,
                    mul = 2,
                    div = 3
                }
            """.trimIndent(),
            "IntOps"
        )
    }

    private fun assertStructureViewText(sourceCode: String, expectedPresentableText: String) {
        val psiFile = myFixture.configureByText(DlangFileType, sourceCode)
        val structureView = DStructureViewElement(psiFile.firstChild)
        assertEquals(expectedPresentableText, structureView.presentation.presentableText!!)
    }

}
