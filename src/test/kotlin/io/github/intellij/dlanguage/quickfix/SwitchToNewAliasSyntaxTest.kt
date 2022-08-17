package io.github.intellij.dlanguage.quickfix

import io.github.intellij.dlanguage.inspections.OldAliasSyntax

class SwitchToNewAliasSyntaxTest : DQuickFixTestBase() {

    override fun setUp() {
        super.setUp()
        enableInspectionTools(OldAliasSyntax())
    }

    fun `test inspection warning for alias syntax`() {
        doTest("warningSwitchToNewAliasSyntax.d", true, true)
    }

    fun `test quickfix for alias syntax`() {
        doSingleTest("SwitchToNewAlias.d")
    }
}

