package io.github.intellij.dlanguage.run;

import com.intellij.ide.actions.ShowPluginsWithSearchOptionAction;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.utils.DToolsNotificationAction;

public class RunUtil {

    private static final String NOTIFICATION_GROUPID = "Debugger";
    private static final String NOTIFICATION_TITLE = "Debugging Error";

    public static boolean checkDebuggerConfigured(Project project) {
        final String gdbPath = ToolKey.GDB_KEY.getPath();

        // check if path to debugger is defined
        if (gdbPath == null) {
            new Notification(NOTIFICATION_GROUPID, NOTIFICATION_TITLE, "GDB executable path is empty", NotificationType.ERROR)
                .addAction(new DToolsNotificationAction("Configure"))
                .notify(project);
            return false;
        }
        // Verify that plugin is available
        var pluginId = PluginId.getId("com.intellij.nativeDebug");
        if (!PluginManagerCore.isPluginInstalled(pluginId)) {
            new Notification(NOTIFICATION_GROUPID, NOTIFICATION_TITLE, "The plugin needs to be installed", NotificationType.ERROR)
                .addAction(new ShowPluginsWithSearchOptionAction("Install plugin", "com.intellij.nativeDebug"))
                .notify(project);
            return false;
        }
        // Check that it is enabled
        if (PluginManagerCore.isDisabled(pluginId)) {
            new Notification(NOTIFICATION_GROUPID, NOTIFICATION_TITLE, "The plugin needs to be enabled", NotificationType.ERROR)
                .addAction(new ShowPluginsWithSearchOptionAction("Enable plugin", "com.intellij.nativeDebug"))
                .notify(project);
            return false;
        }
        return true;
    }
}
