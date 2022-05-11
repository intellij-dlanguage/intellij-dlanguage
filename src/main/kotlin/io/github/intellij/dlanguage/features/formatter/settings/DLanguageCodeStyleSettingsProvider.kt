package io.github.intellij.dlanguage.features.formatter.settings

import com.intellij.application.options.IndentOptionsEditor
import com.intellij.application.options.SmartIndentOptionsEditor
import com.intellij.lang.Language
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider
import io.github.intellij.dlanguage.DLanguage

class DLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {


    companion object {
        private const val DEFAULT_CODE_SAMPLE =
            "module example;\n" +
                "\n" +
                "import std.stdio;\n" +
                "\n" +
                "void main() {\n" +
                "\twriteln(\"Hello\");\n" +
                "}"
    }

    override fun getLanguage(): Language {
        return DLanguage
    }

    override fun getCodeSample(settingsType: SettingsType): String {
        return DEFAULT_CODE_SAMPLE
    }

    override fun getIndentOptionsEditor(): IndentOptionsEditor {
        return SmartIndentOptionsEditor()
    }

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

}
