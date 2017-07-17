package net.masterthought.dlanguage.actions;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.roots.impl.OrderEntryUtil;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.ui.components.JBList;
import net.masterthought.dlanguage.module.DLanguageModuleType;
import net.masterthought.dlanguage.project.DubConfigurationParser;
import net.masterthought.dlanguage.project.DubPackage;
import net.masterthought.dlanguage.settings.ToolKey;
import net.masterthought.dlanguage.utils.DToolsNotificationListener;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Collection;
import java.util.List;

public class ProcessDLibs extends AnAction implements DumbAware {
    public static final String MENU_PATH = "Tools > Process D Libraries";
    private static final Logger LOG = Logger.getInstance(ProcessDLibs.class);

    private static boolean enabled(@NotNull AnActionEvent e) {
        final Project project = getEventProject(e);
        if (project == null) return false;
        final String dubPath = ToolKey.DUB_KEY.getPath(project);
        return dubPath != null && !dubPath.isEmpty() && DLanguageModuleType.findModules(project).size() > 0;
    }

    private static void showModuleChoicePopup(@NotNull AnActionEvent e, Project project, Collection<Module> modules) {
        final JList list = new JBList(JBList.createDefaultListModel(modules.toArray()));
        JBPopup popup = JBPopupFactory.getInstance()
            .createListPopupBuilder(list)
            .setTitle("Process D libraries for module")
            .setItemChoosenCallback(makeModuleChoiceCallback(e, list))
            .createPopup();
        popup.showCenteredInCurrentWindow(project);
    }

    private static Runnable makeModuleChoiceCallback(final @NotNull AnActionEvent event, final @NotNull JList list) {
        return () -> processDLibs(event, (Module) list.getSelectedValue());
    }

    private static void processDLibs(@NotNull AnActionEvent e, @NotNull final Module module) {
        Project project = getEventProject(e);
        final String prefix = "Unable to process D libraries - ";
        if (project == null) {
            displayError(e, prefix + "No active project.");
            return;
        }

        // remove all existing libs
        removeDLibs(module, project);

        // ask dub for required libs
        final String dubPath = ToolKey.DUB_KEY.getPath(project);

        if (dubPath == null) {
            Notifications.Bus.notify(
                new Notification(e.getPresentation().getText(), "Process D Libraries",
                    "DUB executable path is empty" +
                        "<br/><a href='configureDLanguageTools'>Configure</a>",
                    NotificationType.WARNING, new DToolsNotificationListener(project)), project);
            return;
        }

        final DubConfigurationParser dubParser = new DubConfigurationParser(project, dubPath);
        if (dubParser.canUseDub()) {
            final List<DubPackage> dependencies = dubParser.getDubPackageDependencies();
            for (final DubPackage pkg : dependencies) {
                final String fullName = pkg.getName() + "-" + pkg.getVersion();
                createLibraryDependency(module, project, fullName, pkg.getPath());
            }
        } else {
            LOG.info("not possible to run 'dub describe'");
        }

        Notifications.Bus.notify(
            new Notification(e.getPresentation().getText(), "Process D Libraries",
                "Added your dub dependency libraries",
                NotificationType.INFORMATION, new DToolsNotificationListener(project)), project);

    }

    private static void createLibraryDependency(final Module module, Project project, String libraryName, String libraryPath) {
        LibraryTable projectLibraryTable = LibraryTablesRegistrar.getInstance().getLibraryTable(project);
        final LibraryTable.ModifiableModel projectLibraryModel = projectLibraryTable.getModifiableModel();

        final Library library = projectLibraryModel.createLibrary(libraryName);
        final Library.ModifiableModel libraryModel = library.getModifiableModel();
        String pathUrl = VirtualFileManager.constructUrl(LocalFileSystem.PROTOCOL, libraryPath);
        VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(pathUrl);

        if (file != null) {
            libraryModel.addRoot(file, OrderRootType.CLASSES);
            ApplicationManager.getApplication().runWriteAction(() -> {
                libraryModel.commit();
                projectLibraryModel.commit();
                ModuleRootModificationUtil.addDependency(module, library);
            });
        }
    }

    private static void removeDLibs(Module module, Project project) {
        LibraryTable projectLibraryTable = LibraryTablesRegistrar.getInstance().getLibraryTable(project);
        for (Library lib : projectLibraryTable.getLibraries()) {
            removeLibraryIfNeeded(module, lib.getName());
        }
    }

    private static void removeLibraryIfNeeded(Module module, String libraryName) {
        ApplicationManager.getApplication().assertIsDispatchThread();

        final ModifiableModelsProvider modelsProvider = ModifiableModelsProvider.SERVICE.getInstance();
        final ModifiableRootModel model = modelsProvider.getModuleModifiableModel(module);
        final LibraryOrderEntry dLibraryEntry = OrderEntryUtil.findLibraryOrderEntry(model, libraryName);
        if (dLibraryEntry != null) {
            ApplicationManager.getApplication().runWriteAction(() -> {
                Library library = dLibraryEntry.getLibrary();
                if (library != null) {
                    LibraryTable table = library.getTable();
                    if (table != null) {
                        table.removeLibrary(library);
                        model.removeOrderEntry(dLibraryEntry);
                        modelsProvider.commitModuleModifiableModel(model);
                    }
                } else {
                    modelsProvider.disposeModuleModifiableModel(model);
                }
            });
        } else {
            ApplicationManager.getApplication().runWriteAction(() -> modelsProvider.disposeModuleModifiableModel(model));
        }
    }

    private static void displayError(@NotNull AnActionEvent e, @NotNull String message) {
        final String groupId = e.getPresentation().getText();
        Notifications.Bus.notify(new Notification(
            groupId, "Process D libs", message, NotificationType.ERROR), getEventProject(e));
        LOG.warn(message);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabled(enabled(e));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final String prefix = "Unable to process D libraries - ";
        final Project project = e.getProject();
        if (project == null) {
            displayError(e, prefix + "No active project.");
            return;
        }
        final Collection<Module> modules = DLanguageModuleType.findModules(project);
        int size = modules.size();
        if (size == 0) displayError(e, prefix + "No DLanguage modules are used in this project.");
        else if (size == 1) processDLibs(e, modules.iterator().next());
        else showModuleChoicePopup(e, project, modules);
    }

}
