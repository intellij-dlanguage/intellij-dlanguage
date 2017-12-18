package io.github.intellij.dlanguage.inspections

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase
import com.intellij.util.containers.ContainerUtil
import junit.framework.TestCase

abstract class DInspectionTestBase : LightPlatformCodeInsightFixtureTestCase() {

    protected fun doTest(quickFixName: String, checkHighlighting: Boolean = false) {
        val testName = getTestName(true)
        configure(checkHighlighting, testName)
        applySingleQuickFix(quickFixName)
        myFixture.checkResultByFile(testName + "_after.d", true)
    }

    private fun applySingleQuickFix(quickFixName: String) {
        val availableIntentions = myFixture.filterAvailableIntentions(quickFixName)
        val action = ContainerUtil.getFirstItem(availableIntentions)
        TestCase.assertNotNull(action)
        myFixture.launchAction(action!!)
    }

    protected fun doTestNoFix(name: String, checkHighlighting: Boolean = false) {
        configure(checkHighlighting, getTestName(true))
        val availableIntentions = myFixture.filterAvailableIntentions(name)
        assertEmpty(availableIntentions)
    }

    private fun configure(checkHighlighting: Boolean, testName: String) {
        if (checkHighlighting) {
            myFixture.testHighlighting(testName + ".d")
        } else {
            myFixture.configureByFile(testName + ".d")
            myFixture.doHighlighting()
        }
    }
}
