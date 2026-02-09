package io.github.intellij.dlanguage.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.codeinsight.dcd.DCDCompletionServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RestartDCD extends BaseDCDAction {

    // DO NOT CHANGE THE ID WITHOUT ALSO MAKING SURE THAT THE SAME STRING IS USED IN plugin.xml
    public static final String ID = "io.github.intellij.dlanguage.actions.RestartDCD";

    public RestartDCD() {
        super(
            i18n.getString("dcd.action.restart.text"),
            i18n.getString("dcd.action.restart.description"),
            AllIcons.Actions.Restart
        );
    }

    // called frequently so needs to be fast! Can be x2 times per second
    @Override
    public void update(@NotNull final AnActionEvent e) {
        e.getPresentation().setEnabled(restartEnabled(e));
    }

    private boolean restartEnabled(@NotNull final AnActionEvent e) {
        @Nullable final Project project = getEventProject(e);
        if (project == null) return false;
        return super.dcdServerPathAvailable() &&
            !ModuleUtil.getModulesOfType(project, ModuleTypeManager.getInstance().findByID(DLanguage.MODULE_TYPE_ID)).isEmpty();
    }

    @Override
    void performDcdServerAction(@NotNull DCDCompletionServer dcdServer) {
        dcdServer.restart();
    }

}
