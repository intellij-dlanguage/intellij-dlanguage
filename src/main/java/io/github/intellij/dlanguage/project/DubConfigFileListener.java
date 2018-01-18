package io.github.intellij.dlanguage.project;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.progress.DumbProgressIndicator;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task.Backgroundable;
import com.intellij.openapi.progress.impl.BackgroundableProcessIndicator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileCopyEvent;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileListener;
import com.intellij.openapi.vfs.VirtualFileMoveEvent;
import com.intellij.openapi.vfs.VirtualFilePropertyEvent;
import io.github.intellij.dlanguage.actions.ProcessDLibs;
import io.github.intellij.dlanguage.utils.DToolsNotificationListener;
import org.jetbrains.annotations.NotNull;

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

    /**
     * Fired when the contents of a virtual file is changed.
     *
     * @param event the event object containing information about the change.
     */
    @Override
    public void contentsChanged(@NotNull final VirtualFileEvent event) {
        if (event.getFile().equals(dubConfigFile)) {
            ApplicationManager.getApplication().invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    final Backgroundable task = new Backgroundable(project,
                        "Updating Dub Libraries") {
                        @Override
                        public void run(@NotNull final ProgressIndicator indicator) {
                            ProcessDLibs.processDLibs(project, module);
                        }
                    };
                    ProgressManager.getInstance().runProcessWithProgressAsynchronously(
                        task, new BackgroundableProcessIndicator(task));
                }
            }, ModalityState.defaultModalityState());
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
