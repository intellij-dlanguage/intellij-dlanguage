package io.github.intellij.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.DefaultProgramRunner;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.xdebugger.XDebugProcess;
import com.intellij.xdebugger.XDebugProcessStarter;
import com.intellij.xdebugger.XDebugSession;
import com.intellij.xdebugger.XDebuggerManager;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.utils.DToolsNotificationListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.co.cwspencer.gdb.Gdb;
import uk.co.cwspencer.gdb.messages.GdbEvent;
import uk.co.cwspencer.ideagdb.debug.GdbDebugProcess;
import uk.co.cwspencer.ideagdb.debug.utils.SdkUtil;

public class RunUtil {

    private static final String NOTIFICATION_GROUPID = "Debugger";
    private static final String NOTIFICATION_TITLE = "Debugging Error";

    @Nullable
    static RunContentDescriptor startDebugger(DefaultProgramRunner buildRunner, RunProfileState state, ExecutionEnvironment env, Project project, Executor executor, String execName) throws ExecutionException {
        final ExecutionResult result = state.execute(executor, buildRunner);
        if (result == null) {
            return null;
        }

//        GdbRunConfiguration configuration = ((GdbExecutionResult) result).m_configuration;

        // check if path to debugger is defined
        if (ToolKey.GDB_KEY.getPath() == null){
            Notifications.Bus.notify(
                new Notification(NOTIFICATION_GROUPID, NOTIFICATION_TITLE,
                    "GDB executable path is empty" +
                        "<br/><a href='configureDLanguageTools'>Configure</a>",
                    NotificationType.ERROR, new DToolsNotificationListener(project)), project);

            throw new ExecutionException("path to gdb is not defined");
        }

        if (SdkUtil.isHostOsWindows()) {
            execName = execName.concat(".exe");
        }

        final XDebugSession debugSession = XDebuggerManager.getInstance(project).startSession(env,
            new XDebugProcessStarter() {
                @NotNull
                @Override
                public XDebugProcess start(@NotNull XDebugSession session) throws ExecutionException {
                    return new GdbDebugProcess(project, session, result);
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

        gdbProcess.sendCommand("file " + execName);

        // Send startup commands
        String[] commandsArray = new String[0];//configuration.STARTUP_COMMANDS.split("\\r?\\n");
        for (String command : commandsArray) {
            command = command.trim();
            if (!command.isEmpty()) {
                gdbProcess.sendCommand(command);
            }
        }

//        if (configuration.autoStartGdb) {
        gdbProcess.sendCommand("run");
//        }

        return debugSession.getRunContentDescriptor();
    }
}
