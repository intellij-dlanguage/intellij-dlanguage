package io.github.intellij.dlanguage.features.formatter.settings

import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.psi.codeStyle.CodeStyleSettings
import io.github.intellij.dlanguage.DLanguage

class DCodeStyleMainPanel (currentSettings: CodeStyleSettings, settings: CodeStyleSettings) :
    TabbedLanguageCodeStylePanel(DLanguage, currentSettings, settings) {

    override fun initTabs(settings: CodeStyleSettings) {
        super.initTabs(settings)
    }
}
