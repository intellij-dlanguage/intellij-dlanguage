package io.github.intellij.dlanguage.codeinsight.linemarker

import com.intellij.codeInsight.daemon.impl.DaemonCodeAnalyzerImpl
import com.intellij.execution.lineMarker.RunLineMarkerProvider
import com.intellij.util.ThreeState
import io.github.intellij.dlanguage.DLightPlatformCodeInsightFixtureTestCase
import junit.framework.TestCase
import org.junit.Test


class DRunMainLineMarkerTest : DLightPlatformCodeInsightFixtureTestCase("codeinsight/linemarker", "codeinsight/linemarker") {

    fun doTest(fileContent: String, shouldFind: Boolean) {
        myFixture.configureByText("test.d", fileContent)
        myFixture.doHighlighting()
        val markers = DaemonCodeAnalyzerImpl.getLineMarkers(myFixture.editor.document, myFixture.project)
        if (shouldFind) {
            TestCase.assertEquals(1, markers.size)
            assertEquals(ThreeState.YES, RunLineMarkerProvider.hadAnythingRunnable(myFixture.file.virtualFile))
        } else {
            TestCase.assertEquals(0, markers.size)
        }
    }

    @Test
    fun `test should find basic main`() {
        doTest("""
            module test;

            void main() {}
        """, true)
    }

    @Test
    fun `test should find basic main no args return`() {
        doTest("""
            module test;

            int main() {}
        """, true)
    }

    @Test
    fun `test should find basic main args`() {
        doTest("""
            module test;

            void main(string[] args) {}
        """, true)
    }

    @Test
    fun `test should find basic main args auto return`() {
       doTest("""
            module test;

            auto main(string[] args) {}
        """, true)
    }

    // TODO enable when support of noreturn will be added
    /*@Test
    fun `test should find basic noreturn return`() {
        doTest("""
            module test;

            noreturn main() { while (true) ;}
        """, true)
    } */

    @Test
    fun `test should not find runable method`() {
        doTest("""
            module test;

            void foo() {}
        """, false)
    }

    @Test
    fun `test should not find invalid main`() {
        doTest("""
            module test;

            string main() {return "";}
        """, false)
    }

    @Test
    fun `test should not find c main`() {
        doTest("""
            module test;

            int main(int argc, char** argv) {return 0;}
        """, false)
    }

    @Test
    fun `test should not find invalid main casing`() {
        doTest("""
            module test;

            void Main() {}
        """, false)
    }

    @Test
    fun `test should not find inner main method`() {
        doTest("""
            module test;

            class A {
                void main(string args) {}
            }
        """, false)
    }

    @Test
    fun `test should not find inner main method 2`() {
        doTest("""
            module test;

            fun A() {
                void main(string args) {}
            }
        """, false)
    }

    @Test
    fun `test should not find invalid main argument type`() {
        doTest("""
            module test;

            void main(string args) {}
        """, false)
    }

    @Test
    fun `test should not find incomplete main`() {
        doTest("""
            module test;

            void main
        """, false)
    }
}
