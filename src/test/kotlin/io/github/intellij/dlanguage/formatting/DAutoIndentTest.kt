package io.github.intellij.dlanguage.formatting

import com.intellij.testFramework.PsiTestUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.intellij.lang.annotations.Language

class DAutoIndentTest : BasePlatformTestCase() {

    private fun sanitizeText(text: String): String {
        return text.replace("/*caret*/", "<caret>").trimIndent()
    }

    private fun doTestByText(
        @Language("D") before: String,
        @Language("D") after: String,
        c: Char = '\n',
    ) {
        myFixture.configureByText("main.d", sanitizeText(before))
        myFixture.type(c)
        PsiTestUtil.checkPsiStructureWithCommit(myFixture.file, PsiTestUtil::checkPsiMatchesTextIgnoringNonCode)
        myFixture.checkResult(sanitizeText(after))
    }

    fun testStruct() = doTestByText("""
        struct A {/*caret*/}
    """.trimIndent(), """
        struct A {
            /*caret*/
        }
    """.trimIndent()
    )

    fun testClass() = doTestByText("""
        class A {/*caret*/}
    """.trimIndent(), """
        class A {
            /*caret*/
        }
    """.trimIndent()
    )

    // https://github.com/intellij-dlanguage/intellij-dlanguage/issues/776
    fun testEnum() = doTestByText("""
        enum A {/*caret*/}
    """.trimIndent(), """
        enum A {
            /*caret*/
        }
    """.trimIndent()
    )

    fun testFunction() = doTestByText("""
        void main() {/*caret*/}
    """.trimIndent(), """
        void main() {
            /*caret*/
        }
    """.trimIndent()
    )

    fun testIf() = doTestByText("""
        void main() {
            if (true) {/*caret*/}
        }
    """.trimIndent(), """
        void main() {
            if (true) {
                /*caret*/
            }
        }
    """.trimIndent()
    )

    fun testWhile() = doTestByText("""
        void main() {
            while (true) {/*caret*/}
        }
    """.trimIndent(), """
        void main() {
            while (true) {
                /*caret*/
            }
        }
    """.trimIndent()
    )

    fun testFor() = doTestByText("""
        void main() {
            for (int i = 0; i < 10; i++) {/*caret*/}
        }
    """.trimIndent(), """
        void main() {
            for (int i = 0; i < 10; i++) {
                /*caret*/
            }
        }
    """.trimIndent()
    )

    fun testSwitch() = doTestByText("""
        void main() {
            int i;
            switch (i) {/*caret*/}
        }
    """.trimIndent(), """
        void main() {
            int i;
            switch (i) {
                /*caret*/
            }
        }
    """.trimIndent()
    )

    // TODO fix them
    /*fun testSwitchCase() = doTestByText("""
        void main() {
            int i;
            switch (i) {
                case 1:/*caret*/
            }
        }
    """.trimIndent(), """
        void main() {
            int i;
            switch (i) {
                case 1:
                    /*caret*/
            }
        }
    """.trimIndent()
    )

    fun testSwitchRangeCase() = doTestByText("""
        void main() {
            int i;
            switch (i) {
                case 1: .. case 5:/*caret*/
            }
        }
    """.trimIndent(), """
        void main() {
            int i;
            switch (i) {
                case 1: .. case 5:
                    /*caret*/
            }
        }
    """.trimIndent()
    )

    fun testSwitchDefault() = doTestByText("""
        void main() {
            int i;
            switch (i) {
                default:/*caret*/
            }
        }
    """.trimIndent(), """
        void main() {
            int i;
            switch (i) {
                default:
                    /*caret*/
            }
        }
    """.trimIndent()
    )*/

    // https://github.com/intellij-dlanguage/intellij-dlanguage/issues/780
    fun testFunctionCallChaining() = doTestByText("""
        void main() {
            Person person = new Person();
            person
	            .setName()/*caret*/
        }
    """.trimIndent(), """
        void main() {
            Person person = new Person();
            person
	            .setName()
                /*caret*/
        }
    """.trimIndent()
    )

    fun testVersion() = doTestByText("""
        version (A) {/*caret*/}
    """.trimIndent(), """
        version (A) {
            /*caret*/
        }
    """.trimIndent()
    )

    fun testUnittest() = doTestByText("""
        unittest {/*caret*/}
    """.trimIndent(), """
        unittest {
            /*caret*/
        }
    """.trimIndent()
    )

    fun testUnittestInVersion() = doTestByText("""
        version (A) {
            unittest {/*caret*/}
        }
    """.trimIndent(), """
        version (A) {
            unittest {
                /*caret*/
            }
        }
    """.trimIndent()
    )

    fun testSingleLineDoc() = doTestByText("""
        /// this is a line/*caret*/
        void main() {}
    ""","""
        /// this is a line
        /*caret*/
        void main() {}

    """.trimIndent())

    fun testMultiLineDoc() = doTestByText("""
        /**
        this is a line
        *//*caret*/
        void main() {}
    ""","""
        /**
        this is a line
        */
        /*caret*/
        void main() {}

    """.trimIndent())
    fun testNestedLineDoc() = doTestByText("""
        /++
        this is a line
        +//*caret*/
        void main() {}
    ""","""
        /++
        this is a line
        +/
        /*caret*/
        void main() {}

    """.trimIndent())
}
