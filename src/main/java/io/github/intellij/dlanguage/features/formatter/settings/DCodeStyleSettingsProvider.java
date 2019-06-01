package io.github.intellij.dlanguage.features.formatter.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.psi.codeStyle.CodeStyleConfigurable;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider;
import org.jetbrains.annotations.NotNull;

public class DCodeStyleSettingsProvider extends CodeStyleSettingsProvider {

    @Override
    public String getConfigurableDisplayName() {
        return "D";
    }

    @NotNull
    @Override
    public CodeStyleConfigurable createConfigurable(@NotNull final CodeStyleSettings settings, final @NotNull CodeStyleSettings originalSettings) {
        return new DCodeStyleConfigurable(settings, originalSettings);
    }
}
