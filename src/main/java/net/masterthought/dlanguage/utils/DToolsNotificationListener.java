package net.masterthought.dlanguage.utils;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationListener;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import net.masterthought.dlanguage.settings.DLanguageToolsConfigurable;
import org.jetbrains.annotations.NotNull;

import javax.swing.event.HyperlinkEvent;

/**
 * Object sent over the notification bus.
 */
public class DToolsNotificationListener implements NotificationListener {
    @NotNull
    private final Project myProject;

    public DToolsNotificationListener(@NotNull final Project project) {
        myProject = project;
    }

    /**
     * Shows the settings dialog when the user presses "configure" on a balloon.
     */
    @Override
    public void hyperlinkUpdate(@NotNull final Notification notification, @NotNull final HyperlinkEvent event) {
        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            if (event.getDescription().equals("configureDLanguageTools") && !myProject.isDisposed()) {
                ShowSettingsUtil.getInstance().showSettingsDialog(myProject, DLanguageToolsConfigurable.D_TOOLS_ID);
                notification.expire();
            }
        }
    }
}

