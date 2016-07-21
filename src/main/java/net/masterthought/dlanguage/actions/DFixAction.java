package net.masterthought.dlanguage.actions;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.util.ExceptionUtil;
import net.masterthought.dlanguage.psi.DLanguageFile;
import net.masterthought.dlanguage.settings.ToolKey;
import net.masterthought.dlanguage.utils.DToolsNotificationListener;


/**
 * Action that calls Dfmt on the buffer it is invoked for.
 */
public class DFixAction extends AnAction implements DumbAware {
    private static final String NOTIFICATION_TITLE = "Fix code with DFix";
    private static final Logger LOG = Logger.getInstance(DFixAction.class);

    /**
     * Enable the action for D files.
     */
    @Override
    public void update(AnActionEvent e) {
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        boolean isHaskell = psiFile instanceof DLanguageFile;
        e.getPresentation().setEnabled(isHaskell);
    }

    /**
     * Main entry point. Calls DFix.
     */
    @Override
    public void actionPerformed(AnActionEvent e) {
        final PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        final Project project = getEventProject(e);
        if (project == null) return;
        if (!(psiFile instanceof DLanguageFile)) return;
        VirtualFile virtualFile = psiFile.getVirtualFile();
        if (virtualFile == null) return;

        final String groupId = e.getPresentation().getText();
        try {
            GeneralCommandLine commandLine = new GeneralCommandLine();
            final String stylishPath = ToolKey.DFIX_KEY.getPath(project);
            final String stylishFlags = ToolKey.DFIX_KEY.getFlags(project);
            if (stylishPath == null || stylishPath.isEmpty()) {
                Notifications.Bus.notify(
                        new Notification(groupId, NOTIFICATION_TITLE,
                                "DFix executable path is empty" +
                                        "<br/><a href='configureDLanguageTools'>Configure</a>",
                                NotificationType.WARNING, new DToolsNotificationListener(project)), project);
                return;
            }
            commandLine.setExePath(stylishPath);
            commandLine.getParametersList().addParametersString(stylishFlags);

            final VirtualFile backingFile = psiFile.getVirtualFile();
            if (backingFile == null) return;
            String backingFilePath = backingFile.getCanonicalPath();
            if (backingFilePath == null) return;
            commandLine.addParameter(backingFilePath);
            // Set the work dir so stylish can pick up the user config, if it exists.
            commandLine.setWorkDirectory(backingFile.getParent().getCanonicalPath());

            ApplicationManager.getApplication().saveAll();

            try {
                OSProcessHandler process = new OSProcessHandler(commandLine.createProcess());

                process.startNotify();
                process.waitFor();
                backingFile.refresh(true, true);

                Notifications.Bus.notify(new Notification(groupId, NOTIFICATION_TITLE,
                        psiFile.getName() + " fixed with DFix.(Load filesystem changes)",
                        NotificationType.INFORMATION), project);

            } catch (ExecutionException ex) {
                ex.printStackTrace();
                Notifications.Bus.notify(new Notification(groupId,
                        "Fixing " + psiFile.getName() + "  with DFix failed.", ExceptionUtil.getUserStackTrace(ex, LOG),
                        NotificationType.ERROR), project);
                LOG.error(ex);
            }
        } catch (Exception ex) {
            Notifications.Bus.notify(new Notification(groupId,
                    "Fixing " + psiFile.getName() + " with DFix failed", ExceptionUtil.getUserStackTrace(ex, LOG),
                    NotificationType.ERROR), project);
            LOG.error(ex);
        }
    }
}
