package io.github.intellij.dlanguage.types.infer

import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase
import io.github.intellij.dlanguage.psi.DLanguageDeclaration
import io.github.intellij.dlanguage.types.dtype
import org.intellij.lang.annotations.Language

class DFunctionTypeInferenceTest : DLightPlatformCodeInsightFixtureTestCase("types/infer") {
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

    fun `test function const parameter`() = doTest("""
        void foo(in int a, int b) {
            auto x = a;
              // ^ const(int)
        }
    """)

    fun `test function parameter`() = doTest("""
        void foo(in int a, int b) {
            auto x = b;
              // ^ int
        }
    """)
}
