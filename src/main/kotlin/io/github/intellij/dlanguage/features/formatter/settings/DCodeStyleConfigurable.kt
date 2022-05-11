package io.github.intellij.dlanguage.features.formatter.settings

import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.psi.codeStyle.CodeStyleSettings
import io.github.intellij.dlanguage.DLanguage

class DCodeStyleConfigurable(settings: CodeStyleSettings, cloneSettings: CodeStyleSettings?) :
    CodeStyleAbstractConfigurable(settings, cloneSettings, "D") {

    override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel {
        return DCodeStyleMainPanel(currentSettings, settings)
    }

    override fun getHelpTopic(): String? {
        return null
    }

    private class DCodeStyleMainPanel(currentSettings: CodeStyleSettings, settings: CodeStyleSettings) :
        TabbedLanguageCodeStylePanel(DLanguage, currentSettings, settings) {
        override fun addSpacesTab(settings: CodeStyleSettings) {}
        override fun addBlankLinesTab(settings: CodeStyleSettings) {}
        override fun addWrappingAndBracesTab(settings: CodeStyleSettings) {}
    }
}
