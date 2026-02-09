package io.github.intellij.dlanguage.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.codeinsight.dcd.DCDCompletionServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class StopDCDServer extends BaseDCDAction {

    // DO NOT CHANGE THE ID WITHOUT ALSO MAKING SURE THAT THE SAME STRING IS USED IN plugin.xml
    public static final String ID = "io.github.intellij.dlanguage.actions.StopDCDServer";

    public StopDCDServer() {
        super(
            i18n.getString("dcd.action.stop.text"),
            i18n.getString("dcd.action.stop.description"),
            AllIcons.Actions.Suspend
        );
    }

    @Override
    public void update(@NotNull final AnActionEvent e) {
        e.getPresentation().setEnabled(dcdServerRunning(e));
    }

    @Override
    void performDcdServerAction(@NotNull DCDCompletionServer dcdServer) {
        dcdServer.kill();
    }

    private boolean dcdServerRunning(@NotNull final AnActionEvent e) {
        @Nullable final Project project = getEventProject(e);
        if (project == null) return false;

        final Collection<Module> modules = ModuleUtil.getModulesOfType(project, ModuleTypeManager.getInstance().findByID(DLanguage.MODULE_TYPE_ID));
        if (modules.isEmpty()) return false;

        @Nullable final DCDCompletionServer dcd = super.getDCDCompletionServer(modules.iterator().next());
        if (dcd == null) return false;

        return super.dcdServerPathAvailable() && dcd.isRunning();
    }
}
