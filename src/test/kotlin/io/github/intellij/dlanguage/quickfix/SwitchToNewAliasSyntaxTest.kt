package io.github.intellij.dlanguage.quickfix

import io.github.intellij.dlanguage.inspections.OldAliasSyntax

class SwitchToNewAliasSyntaxTest : DQuickFixTestBase() {
    override fun setUp() {
        super.setUp()
        enableInspectionTools(OldAliasSyntax())
    }

    fun test() {
        doSingleTest("SwitchToNewAlias.d")
    }
}

