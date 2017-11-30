package io.github.intellij.dlanguage.types.infer

import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase
import io.github.intellij.dlanguage.psi.DLanguageAssignExpression
import io.github.intellij.dlanguage.psi.DLanguageInitializer
import io.github.intellij.dlanguage.psi.DLanguagePrimaryExpression
import io.github.intellij.dlanguage.types.dtype
import org.intellij.lang.annotations.Language

class DNumericLiteralTypeInferenceTest : DLightPlatformCodeInsightFixtureTestCase("types/infer") {
    private fun doTest(@Language("D") code: String, description: String = "") {
        InlineFile(code)
        check(description)
    }

    private fun check(description: String) {
        val (expr, expectedType) = findElementAndDataInEditor<DLanguagePrimaryExpression>()

        check(expr.dtype.toString() == expectedType) {
            "Type mismatch. Expected: $expectedType, found: ${expr.dtype}. $description"
        }
    }

    fun `test int literal`() = doTest("""
        const a = 100;
                  //^ int
    """)

    fun `test long literal`() = doTest("""
        const a = 100L;
                  //^ long
    """)

    fun `test uint literal`() = doTest("""
        const a = 100U;
                  //^ uint
    """)

    fun `test ulong literal`() = doTest("""
        const a = 100UL;
                  //^ ulong
    """)

    fun `test double literal`() = doTest("""
        const a = 1.0;
                  //^ double
    """)

    fun `test float literal`() = doTest("""
        const a = 1.0f;
                  //^ float
    """)

    fun `test real literal`() = doTest("""
        const a = 1.0L;
                  //^ real
    """)

    fun `test ifloat literal`() = doTest("""
        const a = 1.0fi;
                  //^ ifloat
    """)

    fun `test ireal literal`() = doTest("""
        const a = 1.0Li;
                  //^ ireal
    """)

    fun `test idouble literal`() = doTest("""
        const a = 1.0i;
                  //^ idouble
    """)

    fun `test infer rvalue from lvalue integer`() = doTest("""
        const byte a = 127;
                       //^ byte
    """)
}
