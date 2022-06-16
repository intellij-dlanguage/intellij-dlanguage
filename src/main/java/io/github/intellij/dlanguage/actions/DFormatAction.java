package io.github.intellij.dlanguage.actions;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessAdapter;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.util.ExceptionUtil;
import io.github.intellij.dlanguage.psi.DlangFile;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.utils.DToolsNotificationListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Paths;
import java.util.List;


/**
 * Action that calls Dfmt on the buffer it is invoked for.
 */
public class DFormatAction extends AnAction implements DumbAware {
    private static final String NOTIFICATION_GROUPID = "Dfmt Action";
    private static final String NOTIFICATION_TITLE = "Reformat code with DFormat";
    private static final Logger LOG = Logger.getInstance(DFormatAction.class);

    /**
     * Enable the action for D files.
     */
    @Override
    public void update(final AnActionEvent e) {
        final PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        if(psiFile != null) {
            e.getPresentation().setEnabled(DlangFile.class.isAssignableFrom(psiFile.getClass()));
        }
    }

    /**
     * Main entry point. Calls Dfmt
     */
    @Override
    public void actionPerformed(final AnActionEvent e) {
        final PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        final Project project = getEventProject(e);
        if (project == null) return;
        if (!(psiFile instanceof DlangFile)) return;
        final VirtualFile virtualFile = psiFile.getVirtualFile();
        if (virtualFile == null) return;

        //final String groupId = e.getPresentation().getText();
        try {
            final GeneralCommandLine commandLine = new GeneralCommandLine();
            final String dfmtPath = ToolKey.DFORMAT_KEY.getPath();
            final String dfmtFlags = ToolKey.DFORMAT_KEY.getFlags();
            if (dfmtPath == null || dfmtPath.isEmpty()) {

                showNotification(NOTIFICATION_TITLE,
                    "DFormat executable path is empty<br/><a href='configureDLanguageTools'>Configure</a>",
                    NotificationType.WARNING,
                    new DToolsNotificationListener(project),
                    project);
                return;
            }

            if(!Paths.get(dfmtPath).toFile().canExecute()) {
                showNotification(NOTIFICATION_TITLE,
                    "DFormat executable path is not valid<br/><a href='configureDLanguageTools'>Configure</a>",
                    NotificationType.WARNING,
                    new DToolsNotificationListener(project),
                    project);

                return;
            }

            commandLine.setExePath(dfmtPath);
            commandLine.getParametersList().addParametersString(dfmtFlags);

            final VirtualFile backingFile = psiFile.getVirtualFile();
            if (backingFile == null) return;
            final String backingFilePath = backingFile.getCanonicalPath();
            if (backingFilePath == null) return;
            commandLine.addParameter(backingFilePath);
            // Set the work dir so dfmt can pick up the user config, if it exists.
            commandLine.setWorkDirectory(backingFile.getParent().getCanonicalPath());

            ApplicationManager.getApplication().saveAll();

            final String commandLineString = commandLine.getCommandLineString();
            final OSProcessHandler handler = new OSProcessHandler(commandLine.createProcess(), commandLineString);
            handler.addProcessListener(new CapturingProcessAdapter() {
                @Override
                public void processTerminated(@NotNull final ProcessEvent event) {
                    @NotNull final List<String> errors = getOutput().getStderrLines();

                    if (!errors.isEmpty()) {
                        final String[] parts = errors.get(0).split("\\[\\w+]:\\s");
                        final String output = parts.length == 2 ? parts[1] : errors.get(0);
                        showNotification("DFormat error.", output, NotificationType.ERROR, null, project);
                        return;
                    }

                    final String text = getOutput().getStdout();
                    ApplicationManager.getApplication().invokeLater(() -> {
                        try {
                            @Nullable final Document document = PsiDocumentManager.getInstance(project).getDocument(psiFile);
                            if (document == null) {
                                return;
                            }
                            CommandProcessor.getInstance()
                                .executeCommand(
                                    project,
                                    () -> ApplicationManager
                                        .getApplication()
                                        .runWriteAction(() -> document.setText(text)),
                                    NOTIFICATION_TITLE,
                                    "",
                                    document
                                );

                            showNotification(NOTIFICATION_TITLE,
                                psiFile.getName() + " formatted with DFormat.",
                                NotificationType.INFORMATION, null, project);

                        } catch (final Exception e) {
                            showNotification("Formatting " + psiFile.getName() + "  with DFormat failed.",
                                ExceptionUtil.getUserStackTrace(e, LOG),
                                NotificationType.ERROR, null, project);

                            //LOG.error(e);
                        }
                    });
                }
            });
            handler.startNotify();
        } catch (final Exception ex) {
            showNotification("Formatting " + psiFile.getName() + " with DFormat failed",
                ExceptionUtil.getUserStackTrace(ex, LOG),
                NotificationType.ERROR, null, project);

            LOG.error(ex);
        }
    }

    private void showNotification(@NotNull final String title,
                                  @NotNull final String content,
                                  @NotNull final NotificationType type,
                                  @Nullable final NotificationListener listener,
                                  @Nullable final Project project) {
        final Notification notification = new Notification(NOTIFICATION_GROUPID, title, content, type);

        if(listener != null) {
            notification.setListener(listener);
        }

        Notifications.Bus.notify(notification, project);
    }
}
