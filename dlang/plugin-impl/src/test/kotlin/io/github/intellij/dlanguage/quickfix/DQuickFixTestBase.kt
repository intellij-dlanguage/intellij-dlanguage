package io.github.intellij.dlanguage.quickfix

import com.intellij.codeInsight.daemon.quickFix.LightQuickFixTestCase
import com.intellij.util.PathUtil

abstract class DQuickFixTestBase : LightQuickFixTestCase() {

    override fun getTestDataPath(): String = PathUtil
        .toPresentableUrl(this.javaClass.classLoader.getResource("gold/quickfix/")!!.path)
        .removePrefix("\\")
}

