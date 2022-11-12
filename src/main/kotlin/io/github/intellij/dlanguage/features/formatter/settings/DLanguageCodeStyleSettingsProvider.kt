package io.github.intellij.dlanguage.features.formatter.settings

import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.IndentOptionsEditor
import com.intellij.application.options.SmartIndentOptionsEditor
import com.intellij.lang.Language
import com.intellij.psi.codeStyle.*
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.features.formatter.DCodeStyleSettings

class DLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {

    companion object {
        @org.intellij.lang.annotations.Language("D")
        private const val DEFAULT_CODE_SAMPLE =
            """ module foo.bar.example;

                import std.stdio : writeln;

                void main() {
                   writeln("Hello");
                }
            """
    }

    override fun getLanguage(): Language = DLanguage

    override fun getCodeSample(settingsType: SettingsType): String = DEFAULT_CODE_SAMPLE.trimIndent()

    override fun getIndentOptionsEditor(): IndentOptionsEditor = SmartIndentOptionsEditor()

    override fun createConfigurable(baseSettings: CodeStyleSettings,
                                    modelSettings: CodeStyleSettings): CodeStyleConfigurable =
        object : CodeStyleAbstractConfigurable(baseSettings, modelSettings, configurableDisplayName) {
            override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel =
                DCodeStyleMainPanel(currentSettings, settings)
        }

    override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings = DCodeStyleSettings(settings)

    override fun customizeDefaults(commonSettings: CommonCodeStyleSettings,
                                   indentOptions: CommonCodeStyleSettings.IndentOptions) {
        indentOptions.INDENT_SIZE = 4
        indentOptions.CONTINUATION_INDENT_SIZE = 4
        indentOptions.TAB_SIZE = 4
        indentOptions.USE_TAB_CHARACTER = false

        commonSettings.BLOCK_COMMENT_AT_FIRST_COLUMN = false
        commonSettings.LINE_COMMENT_AT_FIRST_COLUMN = false

        super.customizeDefaults(commonSettings, indentOptions)
    }

    override fun customizeSettings(consumer: CodeStyleSettingsCustomizable, settingsType: SettingsType) {
        when(settingsType) {
            SettingsType.SPACING_SETTINGS -> {
                consumer.showCustomOption(DCodeStyleSettings::class.java,
                    "SPACE_BEFORE_IMPORT_BINDS_COLON",
                    "Space before import colon",
                    CodeStyleSettingsCustomizableOptions.getInstance().SPACES_OTHER)
            }
            SettingsType.WRAPPING_AND_BRACES_SETTINGS -> {
            }
            SettingsType.BLANK_LINES_SETTINGS -> {
            }
            SettingsType.COMMENTER_SETTINGS -> {
            }
            else -> consumer.showStandardOptions()
        }
    }

}
