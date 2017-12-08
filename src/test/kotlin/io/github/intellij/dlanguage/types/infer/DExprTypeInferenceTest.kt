package io.github.intellij.dlanguage.types.infer

import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase
import io.github.intellij.dlanguage.psi.DLanguageDeclaration
import io.github.intellij.dlanguage.psi.DLanguagePrimaryExpression
import io.github.intellij.dlanguage.types.dtype
import org.intellij.lang.annotations.Language

class DExprTypeInferenceTest : DLightPlatformCodeInsightFixtureTestCase("types/infer") {
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

    fun `test local variable`() = doTest("""
        void main() {
            const int x;
            auto y = x;
               //^ const(int)
        }
    """)

    fun `test function call`() = doTest("""
        struct S { int a; }

        S foo() { return S(10); }

        void main() {
            const x = foo();
                //^ const(S)
        }
    """)

    fun `test class field`() = doTest("""
        class Foo {
            int a;
        }

        void main() {
            const a = (new Foo()).a;
                //^ const(int)
        }
    """)
}
