package io.github.intellij.dlanguage.template

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider

class DlangLiveTemplatesProvider : DefaultLiveTemplatesProvider {
    override fun getDefaultLiveTemplateFiles(): Array<out String> = arrayOf(
        "/dlang/ide/liveTemplates/D"
    )

    override fun getHiddenLiveTemplateFiles(): Array<out String>? = null
}
