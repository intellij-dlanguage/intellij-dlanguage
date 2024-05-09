package io.github.intellij.dlanguage.actions;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionHelper;
import com.intellij.execution.ExecutionModes;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.*;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.util.ExceptionUtil;
import io.github.intellij.dlanguage.psi.DlangPsiFile;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.utils.DToolsNotificationAction;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Paths;


/**
 * Action that calls Dfmt on the buffer it is invoked for.
 */
public class DFormatAction extends DumbAwareAction {
    private static final String NOTIFICATION_GROUPID = "Dfmt Action";
    private static final String NOTIFICATION_TITLE = "Reformat code with DFormat";
    private static final Logger LOG = Logger.getInstance(DFormatAction.class);

    /**
     * Enable the action for D files.
     */
    @Override
    public void update(final AnActionEvent e) {
        final PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        if (psiFile != null) {
            e.getPresentation().setEnabled(DlangPsiFile.class.isAssignableFrom(psiFile.getClass()));
        }
    }

    @Override
    public void actionPerformed(@NotNull final AnActionEvent event) {
        final PsiFile psiFile = event.getData(CommonDataKeys.PSI_FILE);
        final VirtualFile virtualFile = event.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
        final Project project = getEventProject(event);

        if (project == null || !(psiFile instanceof DlangPsiFile))
            return;

        if (!virtualFile.isValid()) {
            // if the virtual file is no longer valid (eg: it's been deleted or changed) then there's no point continuing
            return;
        }

        // if we tell the editor to save the file, we can avoid reading in its contents and all the thread stuff that goes with it.
        Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
        if (document == null)
            return;
        FileDocumentManager.getInstance().saveDocument(document);

        // This will execute dfmt on another background thread that shouldn't throw up BGT too slow errors
        // and the best part? It'll give us a progress indicator!
        ProgressManager.getInstance().run(new Task.Backgroundable(project, "DFormat formatting: " + ((DlangPsiFile) psiFile).getFullyQualifiedModuleName()) {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                final GeneralCommandLine commandLine = createCommandLine(project, virtualFile);

                try {
                    // This particular process handle is quite brilliant.
                    // It allows the user to kill dfmt gracefully, and if that fails forcefully.
                    final ProcessHandler processHandler = new KillableProcessHandler(commandLine);

                    // we need process output from dfmt to have an idea of if it was succesfull and if not notify the user of stderr.
                    final ProcessOutput processOutput = new ProcessOutput();
                    processHandler.addProcessListener(new CapturingProcessAdapter(processOutput) {
                        // when dfmt completes (for whatever reason), we'll learn of its error code and grab its stderr output
                        @Override
                        public void processTerminated(@NotNull ProcessEvent event) {
                            super.processTerminated(event);

                            // first lets check to see if dfmt executed correctly
                            final boolean success = event.getExitCode() == 0 && processOutput.getStderr().isEmpty();

                            if (!success) {
                                // this is the most interesting condition
                                // we need to log stderr
                                notifyOfError("Formatting " + psiFile.getName() + "  with DFormat failed.", processOutput.getStderr(), project);
                                return;
                            }

                            notifyOfInformation(psiFile.getName() + " formatted with DFormat.", project);
                        }
                    });

                    processHandler.startNotify();

                    final ExecutionModes.SameThreadMode sameThreadMode = new ExecutionModes.SameThreadMode("DFormat " + virtualFile.getCanonicalPath());
                    ExecutionHelper.executeExternalProcess(project, processHandler, sameThreadMode, commandLine);
                } catch (ExecutionException e) {
                    notifyOfError("Formatting " + psiFile.getName() + " with DFormat failed", ExceptionUtil.getUserStackTrace(e, LOG), project);
                    LOG.error(e);
                    return;
                }

                // this is expected to reload the editor automatically
                VfsUtil.markDirtyAndRefresh(true, true, true, virtualFile);
            }
        });
    }

    GeneralCommandLine createCommandLine(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        final String dfmtPath = ToolKey.DFORMAT_KEY.getPath();
        final String dfmtFlags = ToolKey.DFORMAT_KEY.getFlags();

        if (dfmtPath == null || dfmtPath.isEmpty()) {
            notifyOfWarning("DFormat executable path is empty", project);
            return null;
        }

        if (!Paths.get(dfmtPath).toFile().canExecute()) {
            notifyOfWarning("DFormat executable path is not valid", project);
            return null;
        }

        final GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setExePath(dfmtPath);
        commandLine.getParametersList().addParametersString(dfmtFlags);

        if (!(dfmtFlags.contains("-i") || dfmtFlags.contains("--inplace"))) {
            // Do the work in place of the file system, rather than handle it with stdio (which could be slow)
            commandLine.addParameter("--inplace");
        }

        commandLine.getParametersList().addParametersString(virtualFile.getCanonicalPath());

        // Given the directory that the file that we are formatting is in
        //  set the current working directory of the processing being executed to it
        // This will allow .editorconfig to be searched for and be found in relation to the file.
        commandLine.setWorkDirectory(virtualFile.getParent().getCanonicalPath());
        return commandLine;
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    private void notifyOfInformation(@NotNull String errorMessage, @NotNull Project project) {
        final Notification notification = new Notification(NOTIFICATION_GROUPID, NOTIFICATION_TITLE, errorMessage, NotificationType.INFORMATION);
        Notifications.Bus.notify(notification, project);
    }

    private void notifyOfWarning(@NotNull String errorMessage, @NotNull Project project) {
        final Notification notification = new Notification(NOTIFICATION_GROUPID, NOTIFICATION_TITLE, errorMessage, NotificationType.WARNING);
        notification.addAction(new DToolsNotificationAction("Configure"));
        Notifications.Bus.notify(notification, project);
    }

    private void notifyOfError(@NotNull String title, @NotNull String errorMessage, @NotNull Project project) {
        final Notification notification = new Notification(NOTIFICATION_GROUPID, title, errorMessage, NotificationType.ERROR);
        Notifications.Bus.notify(notification, project);
    }
}
