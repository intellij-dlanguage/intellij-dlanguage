package net.masterthought.dlanguage.utils;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class NotificationUtil {
    public static void displayToolsNotification(@NotNull final NotificationType type,
                                                @NotNull final Project project,
                                                @NotNull final String title,
                                                @NotNull final String message) {
        Notifications.Bus.notify(new Notification(
            title, title,
            message.replace("\n", "<br/>") + "<br/><a href='configureDTools'>Configure</a>",
            type, new DToolsNotificationListener(project)), project);
    }
}
