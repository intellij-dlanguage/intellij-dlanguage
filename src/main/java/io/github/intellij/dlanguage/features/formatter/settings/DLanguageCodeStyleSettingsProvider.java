package io.github.intellij.dlanguage.features.formatter.settings;

import com.intellij.application.options.IndentOptionsEditor;
import com.intellij.application.options.SmartIndentOptionsEditor;
import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import io.github.intellij.dlanguage.DLanguage;
import org.jetbrains.annotations.NotNull;

public class DLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {

    private static final String DEFAULT_CODE_SAMPLE =
        "module example;\n" +
            "\n" +
            "import std.stdio;\n" +
            "\n" +
            "void main() {\n" +
            "\twriteln(\"Hello\");\n" +
            "}";

    @NotNull
    @Override
    public Language getLanguage() {
        return DLanguage.INSTANCE;
    }

    @NotNull
    @Override
    public String getCodeSample(@NotNull final SettingsType settingsType) {
        return DEFAULT_CODE_SAMPLE;
    }

    @Override
    public IndentOptionsEditor getIndentOptionsEditor() {
        return new SmartIndentOptionsEditor();
    }

    @Override
    public CommonCodeStyleSettings getDefaultCommonSettings() {
        final CommonCodeStyleSettings defaultSettings = new CommonCodeStyleSettings(getLanguage());
        final CommonCodeStyleSettings.IndentOptions indentOptions = defaultSettings.initIndentOptions();
        indentOptions.INDENT_SIZE = 4;
        indentOptions.CONTINUATION_INDENT_SIZE = 4;
        indentOptions.TAB_SIZE = 4;
        indentOptions.USE_TAB_CHARACTER = false;

        defaultSettings.BLOCK_COMMENT_AT_FIRST_COLUMN = false;
        defaultSettings.LINE_COMMENT_AT_FIRST_COLUMN = false;
        return defaultSettings;
    }
}
