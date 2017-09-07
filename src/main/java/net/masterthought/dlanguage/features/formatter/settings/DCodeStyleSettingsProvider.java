package net.masterthought.dlanguage.features.formatter.settings;

import com.intellij.openapi.options.Configurable;
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
    public Configurable createSettingsPage(@NotNull final CodeStyleSettings settings, final CodeStyleSettings originalSettings) {
        return new DCodeStyleConfigurable(settings, originalSettings);
    }
}
