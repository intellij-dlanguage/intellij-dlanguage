package io.github.intellij.dlanguage.actions;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.ui.popup.PopupFactoryImpl;
import io.github.intellij.dlanguage.codeinsight.dcd.DCDCompletionServer;
import io.github.intellij.dlanguage.module.DlangModuleType;
import io.github.intellij.dlanguage.settings.ToolKey;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class RestartDCD extends AnAction implements DumbAware { // todo: consider implementing LightEditCompatible

    private static final Logger LOG = Logger.getInstance(RestartDCD.class);

    // DO NOT CHANGE THE ID WITHOUT ALSO MAKING SURE THAT THE SAME STRING IS USED IN plugin.xml
    public static final String ID = "io.github.intellij.dlanguage.actions.RestartDCD";

    public static final String MENU_PATH = "Tools > Restart DCD Server";

    // called frequently so needs to be fast! Can be x2 times per second
    @Override
    public void update(@NotNull final AnActionEvent e) {
        e.getPresentation().setEnabled(enabled(e));
    }

    private static boolean enabled(@NotNull final AnActionEvent e) {
        final Project project = getEventProject(e);
        if (project == null) return false;
        final String dcdServerPath = ToolKey.DCD_SERVER_KEY.getPath();
        return dcdServerPath != null && !dcdServerPath.isEmpty() && DlangModuleType.findModules(project).size() > 0;
    }

    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        final String prefix = "Unable to restart dcd-server - ";
        final Project project = e.getProject();
        if (project == null) { displayError(e, prefix + "No active project."); return; }
        final Collection<Module> modules = DlangModuleType.findModules(project);
        final int size = modules.size();
        if (size == 0) displayError(e, prefix + "No DLanguage modules are used in this project.");
        else if (size == 1) restartDcdServer(e, modules.iterator().next());
        else showModuleChoicePopup(e, project, modules);
    }

    private static void showModuleChoicePopup(@NotNull final AnActionEvent e, final Project project, final Collection<Module> modules) {
        PopupFactoryImpl.getInstance()
            .createPopupChooserBuilder(new ArrayList<>(modules))
            .setTitle("Restart dcd-server for module")
            .setItemChosenCallback(module -> restartDcdServer(e, module))
            .createPopup()
            .showCenteredInCurrentWindow(project);
    }

    private static void restartDcdServer(@NotNull final AnActionEvent e, @NotNull final Module module) {
        final DCDCompletionServer dcdServer = module.getComponent(DCDCompletionServer.class);
        if (dcdServer == null) displayError(e, "Could not find module component for dcd-server.");
        else dcdServer.restart();
    }

    private static void displayError(@NotNull final AnActionEvent e, @NotNull final String message) {
        final String groupId = e.getPresentation().getText();
        Notifications.Bus.notify(new Notification(
                groupId, "Restart dcd-server", message, NotificationType.ERROR), getEventProject(e));
        LOG.warn(message);
    }
}
