package net.masterthought.dlanguage.actions;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.components.JBList;
import net.masterthought.dlanguage.codeinsight.dcd.DCDCompletionServer;
import net.masterthought.dlanguage.module.DLanguageModuleType;
import net.masterthought.dlanguage.settings.ToolKey;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Collection;

public class RestartDCD extends AnAction implements DumbAware {
    private static final Logger LOG = Logger.getInstance(RestartDCD.class);

    public static final String MENU_PATH = "Tools > Restart DCD Server";

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabled(enabled(e));
    }

    private static boolean enabled(@NotNull AnActionEvent e) {
        final Project project = getEventProject(e);
        if (project == null) return false;
        final String cdcServerPath = ToolKey.DCD_SERVER_KEY.getPath(project);
        return cdcServerPath != null && !cdcServerPath.isEmpty() && DLanguageModuleType.findModules(project).size() > 0;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final String prefix = "Unable to restart dcd-server - ";
        Project project = e.getProject();
        if (project == null) { displayError(e, prefix + "No active project."); return; }
        Collection<Module> modules = DLanguageModuleType.findModules(project);
        int size = modules.size();
        if (size == 0) displayError(e, prefix + "No DLanguage modules are used in this project.");
        else if (size == 1) restartDcdServer(e, modules.iterator().next());
        else showModuleChoicePopup(e, project, modules);
    }

    private static void showModuleChoicePopup(@NotNull AnActionEvent e, Project project, Collection<Module> modules) {
        final JList list = new JBList(JBList.createDefaultListModel(modules.toArray()));
        JBPopup popup = JBPopupFactory.getInstance()
                .createListPopupBuilder(list)
                .setTitle("Restart dcd-server for module")
                .setItemChoosenCallback(makeModuleChoiceCallback(e, list))
                .createPopup();
        popup.showCenteredInCurrentWindow(project);
    }

    private static Runnable makeModuleChoiceCallback(final @NotNull AnActionEvent e, final @NotNull JList list) {
        return () -> restartDcdServer(e, (Module) list.getSelectedValue());
    }

    private static void restartDcdServer(@NotNull AnActionEvent e, @NotNull Module module) {
        DCDCompletionServer dcdServer = module.getComponent(DCDCompletionServer.class);
        if (dcdServer == null) displayError(e, "Could not find module component for dcd-server.");
        else dcdServer.restart();
    }

    private static void displayError(@NotNull AnActionEvent e, @NotNull String message) {
        final String groupId = e.getPresentation().getText();
        Notifications.Bus.notify(new Notification(
                groupId, "Restart dcd-server", message, NotificationType.ERROR), getEventProject(e));
        LOG.warn(message);
    }
}
