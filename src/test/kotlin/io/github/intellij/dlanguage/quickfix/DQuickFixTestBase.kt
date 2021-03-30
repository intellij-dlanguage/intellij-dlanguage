package io.github.intellij.dlanguage.quickfix

import com.intellij.codeInsight.daemon.quickFix.LightQuickFixTestCase

abstract class DQuickFixTestBase : LightQuickFixTestCase() {
    override fun getTestDataPath(): String {
        return this.javaClass.classLoader.getResource("gold/quickfix/")!!.path
    }
}

