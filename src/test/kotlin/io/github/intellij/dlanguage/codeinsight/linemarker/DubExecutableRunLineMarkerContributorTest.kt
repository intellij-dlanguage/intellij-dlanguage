package io.github.intellij.dlanguage.codeinsight.linemarker

import com.intellij.execution.lineMarker.RunLineMarkerProvider
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.util.ThreeState


/**
 * @author Samael Bate (singingbush)
 * created on 19/07/2022
 */
class DubExecutableRunLineMarkerContributorTest : BasePlatformTestCase() {

    //@Test
    fun `test line marker detects main function`() {
        myFixture.configureByText("app.d",
            """import std.stdio : writeln;
                |void <caret>main() {
                |    writeln("Hello World!");
                |}
            """.trimMargin()
        )

        val virtualFile = myFixture.file.virtualFile

        // state will be UNSURE until the gutters are found
        assertEquals(ThreeState.UNSURE, RunLineMarkerProvider.hadAnythingRunnable(virtualFile))

        // the <caret> is on the line that has "void main()" so a gutter should be found on that line
        assertEquals(1, myFixture.findGuttersAtCaret().size)

        // there should only be one entry point in the source, so only one gutter line marker in the file
        assertEquals(1, myFixture.findAllGutters().size)

        // now the gutter marker is found the state is YES
        assertEquals(ThreeState.YES, RunLineMarkerProvider.hadAnythingRunnable(virtualFile))
    }
}
