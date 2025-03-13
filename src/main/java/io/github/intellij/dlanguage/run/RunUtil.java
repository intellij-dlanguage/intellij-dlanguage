package io.github.intellij.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.intellij.xdebugger.XDebugProcess;
import com.intellij.xdebugger.XDebugProcessStarter;
import com.intellij.xdebugger.XDebugSession;
import com.intellij.xdebugger.XDebuggerManager;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.utils.DToolsNotificationAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.co.cwspencer.gdb.Gdb;
import uk.co.cwspencer.gdb.messages.GdbEvent;
import uk.co.cwspencer.ideagdb.debug.GdbDebugProcess;

public class RunUtil {

    private static final String NOTIFICATION_GROUPID = "Debugger";
    private static final String NOTIFICATION_TITLE = "Debugging Error";

    @Nullable
    public static RunContentDescriptor startDebugger(ProgramRunner<?> buildRunner, RunProfileState state, ExecutionEnvironment env, Project project, Executor executor, String execName) throws ExecutionException {
        final ExecutionResult result = state.execute(executor, buildRunner);
        if (result == null) {
            return null;
        }

//        GdbRunConfiguration configuration = ((GdbExecutionResult) result).m_configuration;

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

        final String executableFilePath = execName;
        final XDebugSession debugSession = XDebuggerManager.getInstance(project).startSession(env,
            new XDebugProcessStarter() {
                @NotNull
                @Override
                public XDebugProcess start(@NotNull XDebugSession session) throws ExecutionException {
                    return new GdbDebugProcess(gdbPath, session, result, executableFilePath);
                }
            });

        GdbDebugProcess debugProcess = ((GdbDebugProcess) debugSession.getDebugProcess());

        final Gdb gdbProcess = debugProcess.m_gdb;

        // Queue startup commands
        gdbProcess.sendCommand("-list-features", new Gdb.GdbEventCallback() {
            @Override
            public void onGdbCommandCompleted(GdbEvent event) {
                gdbProcess.onGdbCapabilitiesReady(event);
            }
        });

        // Send startup commands
        String[] commandsArray = new String[0];//configuration.STARTUP_COMMANDS.split("\\r?\\n");
        for (String command : commandsArray) {
            command = command.trim();
            if (!command.isEmpty()) {
                gdbProcess.sendCommand(command);
            }
        }

//        if (configuration.autoStartGdb) {
        gdbProcess.sendCommand("-exec-run");
//        }

        return debugSession.getRunContentDescriptor();
    }
}
