package io.github.intellij.dlanguage.types.infer

import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase
import io.github.intellij.dlanguage.psi.DLanguagePrimaryExpression
import io.github.intellij.dlanguage.types.dtype
import org.intellij.lang.annotations.Language

class DArithmeticOpsTest : DLightPlatformCodeInsightFixtureTestCase("types/infer") {
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

    fun `test same type of lhs and rhs`() = doTest("""
        void foo(int lhs, int rhs) {
            const a = lhs + rhs;
                //^ const(int)
        }
    """)
}
