package net.masterthought.dlanguage.features.formatter.settings;

import com.intellij.application.options.CodeStyleAbstractConfigurable;
import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.application.options.TabbedLanguageCodeStylePanel;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import net.masterthought.dlanguage.DLanguage;
import org.jetbrains.annotations.NotNull;


public class DCodeStyleConfigurable extends CodeStyleAbstractConfigurable {
    public DCodeStyleConfigurable(@NotNull CodeStyleSettings settings, CodeStyleSettings cloneSettings) {
        super(settings, cloneSettings, "D");
    }

    @NotNull
    @Override
    protected CodeStyleAbstractPanel createPanel(CodeStyleSettings settings) {
        return new GoCodeStyleMainPanel(getCurrentSettings(), settings);
    }

    @Override
    public String getHelpTopic() {
        return null;
    }

    private static class GoCodeStyleMainPanel extends TabbedLanguageCodeStylePanel {
        private GoCodeStyleMainPanel(CodeStyleSettings currentSettings, CodeStyleSettings settings) {
            super(DLanguage.INSTANCE, currentSettings, settings);
        }

        @Override
        protected void addSpacesTab(CodeStyleSettings settings) {
        }

        @Override
        protected void addBlankLinesTab(CodeStyleSettings settings) {
        }

        @Override
        protected void addWrappingAndBracesTab(CodeStyleSettings settings) {
        }
    }
}
