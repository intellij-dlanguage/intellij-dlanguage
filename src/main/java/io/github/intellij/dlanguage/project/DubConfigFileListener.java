package io.github.intellij.dlanguage.project;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.ModuleListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileListener;
import com.intellij.openapi.vfs.VirtualFileManager;
import io.github.intellij.dlanguage.actions.ProcessDLibs;
import io.github.intellij.dlanguage.utils.DToolsNotificationListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by francis on 1/18/2018.
 */
public class DubConfigFileListener implements VirtualFileListener {


    private final VirtualFile dubConfigFile;
    private final Project project;
    private final Module module;

    public DubConfigFileListener(final VirtualFile dubConfigFile, final Project project,
        final Module module) {

        this.dubConfigFile = dubConfigFile;
        this.project = project;
        this.module = module;
    }

    static void addProcessDLibsListener(final VirtualFile dubConfigFile,
        final Project project,
        final Module module) {
        if (dubConfigFile == null) {
            return;
        }
        VirtualFileManager.getInstance()
            .addVirtualFileListener(new DubConfigFileListener(dubConfigFile, project, module));

    }

    @Nullable
    static VirtualFile getDubFileFromModule(final Module module) {
        for (final VirtualFile file : module.getProject().getBaseDir().getChildren()) {
            if (file.isValid() &&
                (file.getName().equalsIgnoreCase("dub.json") ||
                    file.getName().equalsIgnoreCase("dub.sdl"))) {
                return file;
            }
        }
        return null;
    }

    /**
     * Fired when the contents of a virtual file is changed.
     *
     * @param event the event object containing information about the change.
     */
    @Override
    public void contentsChanged(@NotNull final VirtualFileEvent event) {
        if (event.getFile().equals(dubConfigFile)) {
            ProcessDLibs.processDLibs(project, module, true, false);
        }
    }

    /**
     * Fired before the deletion of a file is processed.
     *
     * @param event the event object containing information about the change.
     */
    @Override
    public void beforeFileDeletion(@NotNull final VirtualFileEvent event) {
        if (event.getFile().equals(dubConfigFile)) {
            Notifications.Bus.notify(
                new Notification("Dub", "Dub",
                    "dub.json or dub.sdl file deleted. ",
                    NotificationType.WARNING, new DToolsNotificationListener(project)),
                project);
        }
    }


}
