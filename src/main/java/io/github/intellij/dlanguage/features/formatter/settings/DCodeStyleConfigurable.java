package io.github.intellij.dlanguage.features.formatter.settings;

import com.intellij.application.options.CodeStyleAbstractConfigurable;
import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.application.options.TabbedLanguageCodeStylePanel;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.DLanguage;
import org.jetbrains.annotations.NotNull;


public class DCodeStyleConfigurable extends CodeStyleAbstractConfigurable {
    public DCodeStyleConfigurable(@NotNull final CodeStyleSettings settings, final CodeStyleSettings cloneSettings) {
        super(settings, cloneSettings, "D");
    }

    @NotNull
    @Override
    protected CodeStyleAbstractPanel createPanel(final CodeStyleSettings settings) {
        return new DCodeStyleMainPanel(getCurrentSettings(), settings);
    }

    @Override
    public String getHelpTopic() {
        return null;
    }

    private static class DCodeStyleMainPanel extends TabbedLanguageCodeStylePanel {
        private DCodeStyleMainPanel(final CodeStyleSettings currentSettings, final CodeStyleSettings settings) {
            super(DLanguage.INSTANCE, currentSettings, settings);
        }

        @Override
        protected void addSpacesTab(final CodeStyleSettings settings) {
        }

        @Override
        protected void addBlankLinesTab(final CodeStyleSettings settings) {
        }

        @Override
        protected void addWrappingAndBracesTab(final CodeStyleSettings settings) {
        }
    }
}
