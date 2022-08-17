package io.github.intellij.dlanguage.utils;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.DumbAware;
import io.github.intellij.dlanguage.settings.DLanguageToolsConfigurable;
import org.jetbrains.annotations.NotNull;

public class DToolsNotificationAction extends AnAction implements DumbAware {
    public DToolsNotificationAction(String text) {
        super(text);
    }

    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        e.getPresentation().setEnabledAndVisible(e.getProject() != null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ShowSettingsUtil.getInstance().showSettingsDialog(e.getProject(), DLanguageToolsConfigurable.D_TOOLS_ID);
    }

}
