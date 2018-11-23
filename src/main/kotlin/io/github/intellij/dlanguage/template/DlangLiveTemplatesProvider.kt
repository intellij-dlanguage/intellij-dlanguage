package io.github.intellij.dlanguage.template

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider

class DlangLiveTemplatesProvider : DefaultLiveTemplatesProvider {
    override fun getDefaultLiveTemplateFiles(): Array<out String> = arrayOf(
        //"/org/rust/ide/liveTemplates/iterations",
        //"/org/rust/ide/liveTemplates/output",
        //"/org/rust/ide/liveTemplates/test",
        "/dlang/ide/liveTemplates/D",
        "/dlang/ide/liveTemplates/other"
    )

    override fun getHiddenLiveTemplateFiles(): Array<out String>? = null
}
