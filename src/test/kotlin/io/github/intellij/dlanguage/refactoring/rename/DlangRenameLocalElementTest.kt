package io.github.intellij.dlanguage.refactoring.rename

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import org.junit.Test

class DlangRenameLocalElementTest : LightPlatformCodeInsightFixture4TestCase() {
    @Test
    fun `test rename class and usages including constructor call`() {
        myFixture.configureByText("main.d", """
            class A {
                this() {}
            }

            void foo() {
                <caret>A a = new A();
            }
            """.trimIndent())
        myFixture.renameElementAtCaret("B")
        myFixture.checkResult("""
            class B {
                this() {}
            }

            void foo() {
                <caret>B a = new B();
            }
            """.trimIndent())
    }
}
