package io.github.intellij.dlanguage.actions;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.popup.PopupFactoryImpl;
import com.intellij.util.Consumer;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.codeinsight.dcd.DCDCompletionServer;
import io.github.intellij.dlanguage.settings.ToolKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * All actions for controlling DCD Server will need to check that the server is configured and be aware of its current state.
 * Use this base class to share common code.
 */
public abstract class BaseDCDAction extends AnAction implements DumbAware { // todo: consider implementing LightEditCompatible

    private static final String NOTIFICATION_GROUP_ID = DCDCompletionServer.NOTIFICATION_GROUP_ID;

    protected static final String DCD_I18N_BUNDLE_NAME = "dcd-i18n";

    @NlsContexts.NotificationTitle
    protected static final String NOTIFICATION_TITLE = "DCD server";

    protected static final ResourceBundle i18n = ResourceBundle.getBundle(DCD_I18N_BUNDLE_NAME);

    protected BaseDCDAction(@NotNull String text, @NotNull String description, @NotNull Icon icon) {
        super(text, description, icon);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Nullable
    protected DCDCompletionServer getDCDCompletionServer(@NotNull Module m) {
        return m.getService(DCDCompletionServer.class);
    }

    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        final Project project = e.getProject();
        if (project == null) {
            displayNotification(e, i18n.getString("dcd.no-active-project"), NotificationType.ERROR);
            return;
        }

        final Collection<Module> modules = ModuleUtil.getModulesOfType(project, ModuleTypeManager.getInstance().findByID(DLanguage.MODULE_TYPE_ID));
        final int size = modules.size();
        if (size == 0) displayNotification(e, i18n.getString("dcd.no-active-modules"), NotificationType.ERROR);
        else if (size == 1) performActionWithModule(e, modules.iterator().next());
        else this.showModuleChoicePopup(e, project, modules, module -> performActionWithModule(e, module));
    }

    private void performActionWithModule(@NotNull final AnActionEvent e, @NotNull final Module module) {
        final DCDCompletionServer dcdServer = getDCDCompletionServer(module);
        if (dcdServer != null) {
            performDcdServerAction(dcdServer);
        } else {
            displayNotification(
                e,
                i18n.getString("dcd.no-module-component"),
                NotificationType.ERROR
            );
        }
    }

    /**
     * Perform an action on the dcd server. The server is guaranteed to be non-null and running when this method is called.
     * @param dcdServer invoke the required action on this server instance.
     */
    abstract void performDcdServerAction(@NotNull final DCDCompletionServer dcdServer);

    protected boolean dcdServerPathAvailable() {
        final String dcdServerPath = ToolKey.DCD_SERVER_KEY.getPath();
        return dcdServerPath != null && !dcdServerPath.isEmpty() && Paths.get(dcdServerPath).toFile().canExecute();
    }

    protected void showModuleChoicePopup(@NotNull final AnActionEvent e, final Project project, final Collection<Module> modules, final Consumer<Module> moduleChosenCallback) {
        PopupFactoryImpl.getInstance()
            .createPopupChooserBuilder(new ArrayList<>(modules))
            .setTitle(e.getPresentation().getText()) //.setAdText("Module selection")
            .setItemChosenCallback(moduleChosenCallback)
            .createPopup()
            .showCenteredInCurrentWindow(project);
    }

    protected void displayNotification(
        @NotNull final AnActionEvent e,
        @NlsContexts.NotificationContent final String message,
        @NotNull final NotificationType type
    ) {
        // Could potentially use e.getPresentation().getText() for notification title
        NotificationGroupManager.getInstance()
            .getNotificationGroup(NOTIFICATION_GROUP_ID)
            .createNotification(NOTIFICATION_TITLE, message, type)
            .notify(getEventProject(e));
    }
}
