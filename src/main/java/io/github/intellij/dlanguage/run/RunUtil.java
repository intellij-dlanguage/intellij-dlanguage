package io.github.intellij.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.utils.DToolsNotificationAction;
import org.jetbrains.annotations.Nullable;

public class RunUtil {

    private static final String NOTIFICATION_GROUPID = "Debugger";
    private static final String NOTIFICATION_TITLE = "Debugging Error";

    @Nullable
    public static RunContentDescriptor startDebugger(ProgramRunner<?> buildRunner, RunProfileState state, ExecutionEnvironment env, Project project, Executor executor, String execName) throws ExecutionException {
        final String gdbPath = ToolKey.GDB_KEY.getPath();

        // check if path to debugger is defined
        if (gdbPath == null) {
            NotificationGroupManager.getInstance()
                .getNotificationGroup(NOTIFICATION_GROUPID)
                .createNotification(
                    NOTIFICATION_TITLE,
                    "GDB executable path is empty",
                    NotificationType.ERROR
                )
                .addAction(new DToolsNotificationAction("Configure"))
                .notify(project);

            return null;
        }
        return null;
    }
}
