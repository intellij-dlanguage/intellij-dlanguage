package io.github.intellij.dlanguage.types.infer

import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase
import io.github.intellij.dlanguage.psi.DLanguageDeclaration
import io.github.intellij.dlanguage.types.dtype
import org.intellij.lang.annotations.Language

class DNumericLiteralTypeInferenceTest : DLightPlatformCodeInsightFixtureTestCase("types/infer") {
    private fun doTest(@Language("D") code: String, description: String = "") {
        InlineFile(code)
        check(description)
    }

    private fun check(description: String) {
        val (expr, expectedType) = findElementAndDataInEditor<DLanguageDeclaration>()

        check(expr.dtype.toString() == expectedType) {
            "Type mismatch. Expected: $expectedType, found: ${expr.dtype}. $description"
        }
    }

    fun `test int literal`() = doTest("""
        auto a = 100;
           //^ int
    """)

    fun `test long literal`() = doTest("""
        auto a = 100L;
           //^ long
    """)

    fun `test uint literal`() = doTest("""
        auto a = 100U;
           //^ uint
    """)

    fun `test ulong literal`() = doTest("""
        auto a = 100UL;
           //^ ulong
    """)

    fun `test double literal`() = doTest("""
        auto a = 1.0;
           //^ double
    """)

    fun `test float literal`() = doTest("""
        auto a = 1.0f;
           //^ float
    """)

    fun `test real literal`() = doTest("""
        auto a = 1.0L;
           //^ real
    """)

    fun `test ifloat literal`() = doTest("""
        auto a = 1.0fi;
           //^ ifloat
    """)

    fun `test ireal literal`() = doTest("""
        auto a = 1.0Li;
           //^ ireal
    """)

    fun `test idouble literal`() = doTest("""
        auto a = 1.0i;
           //^ idouble
    """)

    fun `test boolean literal`() = doTest("""
        auto a = true;
           //^ bool
    """)

    fun `test char literal`() = doTest("""
        auto a = 'a';
           //^ char
    """)

    fun `test string literal`() = doTest("""
        auto a = "Hello world";
           //^ string
    """)

    fun `test dstring literal`() = doTest("""
        auto a = "Hello world"d;
           //^ dstring
    """)

    fun `test wstring literal`() = doTest("""
        auto a = "Hello world"w;
           //^ wstring
    """)

    fun `test delimiter string literal`() = doTest("""
        auto a = r"Hello world";
           //^ string
    """)

    fun `test const literal`() = doTest("""
        const a = 23;
            //^ const(int)
    """)

    fun `test immutable literal`() = doTest("""
        immutable a = 23;
                //^ immutable(int)
    """)

    fun `test enum literal`() = doTest("""
        enum a = 23;
           //^ int
    """)

    fun `test const auto literal`() = doTest("""
        const auto a = 23;
                 //^ const(int)
    """)

    fun `test immutable auto literal`() = doTest("""
        immutable auto a = 23;
                     //^ immutable(int)
    """)

    fun `test scope literal`() = doTest("""
        scope a = 23;
            //^ int
    """)

    fun `test delimiter 2 string literal`() = doTest("""
        auto a = q{Hello world};
           //^ string
    """)

    fun `test wysiwyg string literal`() = doTest("""
        auto a = `Hello world`;
           //^ string
    """)

    fun `test infer rvalue from lvalue integer`() = doTest("""
        auto byte a = 127;
                //^ byte
    """)

    fun `test infer rvalue from lvalue float`() = doTest("""
        auto float a = 1.0;
                 //^ float
    """)
}
