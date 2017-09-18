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
import net.masterthought.dlanguage.module.DlangModuleType;
import net.masterthought.dlanguage.project.DubConfigurationParser;
import net.masterthought.dlanguage.project.DubPackage;
import net.masterthought.dlanguage.settings.ToolKey;
import net.masterthought.dlanguage.utils.DToolsNotificationListener;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Collection;
import java.util.List;

public class ProcessDLibs extends AnAction implements DumbAware {
    private static final Logger LOG = Logger.getInstance(ProcessDLibs.class);

    public static final String MENU_PATH = "Tools > Process D Libraries";

    @Override
    public void update(@NotNull final AnActionEvent e) {
        e.getPresentation().setEnabled(enabled(e));
    }

    private static boolean enabled(@NotNull final AnActionEvent e) {
        final Project project = getEventProject(e);
        if (project == null) return false;
        final String dubPath = ToolKey.DUB_KEY.getPath(project);
        return dubPath != null && !dubPath.isEmpty() && DlangModuleType.findModules(project).size() > 0;
    }

    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        final String prefix = "Unable to process D libraries - ";
        final Project project = e.getProject();
        if (project == null) {
            displayError(e, prefix + "No active project.");
            return;
        }
        final Collection<Module> modules = DlangModuleType.findModules(project);
        final int size = modules.size();
        if (size == 0) displayError(e, prefix + "No DLanguage modules are used in this project.");
        else if (size == 1) processDLibs(e, modules.iterator().next());
        else showModuleChoicePopup(e, project, modules);
    }

    private static void showModuleChoicePopup(@NotNull final AnActionEvent e, final Project project, final Collection<Module> modules) {
        final JList list = new JBList(JBList.createDefaultListModel(modules.toArray()));
        final JBPopup popup = JBPopupFactory.getInstance()
                .createListPopupBuilder(list)
                .setTitle("Process D libraries for module")
                .setItemChoosenCallback(makeModuleChoiceCallback(e, list))
                .createPopup();
        popup.showCenteredInCurrentWindow(project);
    }

    private static Runnable makeModuleChoiceCallback(final @NotNull AnActionEvent event, final @NotNull JList list) {
        return () -> processDLibs(event, (Module) list.getSelectedValue());
    }

    private static void processDLibs(@NotNull final AnActionEvent e, @NotNull final Module module) {
        final Project project = getEventProject(e);
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
                            "DUB executable path is empty"+
                                    "<br/><a href='configureDLanguageTools'>Configure</a>",
                            NotificationType.WARNING, new DToolsNotificationListener(project)), project);
            return;
        }

        final DubConfigurationParser dubParser = new DubConfigurationParser(project, dubPath);
        if(dubParser.canUseDub()) {
            final List<DubPackage> dependencies = dubParser.getDubPackageDependencies();
            for(final DubPackage pkg : dependencies){
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

    private static void createLibraryDependency(final Module module, final Project project, final String libraryName, final String libraryPath) {
        final LibraryTable projectLibraryTable = LibraryTablesRegistrar.getInstance().getLibraryTable(project);
        final LibraryTable.ModifiableModel projectLibraryModel = projectLibraryTable.getModifiableModel();

        final Library library = projectLibraryModel.createLibrary(libraryName);
        final Library.ModifiableModel libraryModel = library.getModifiableModel();
        final String pathUrl = VirtualFileManager.constructUrl(LocalFileSystem.PROTOCOL, libraryPath);
        final VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(pathUrl);

        if (file != null) {
            libraryModel.addRoot(file, OrderRootType.CLASSES);
            ApplicationManager.getApplication().runWriteAction(() -> {
                libraryModel.commit();
                projectLibraryModel.commit();
                ModuleRootModificationUtil.addDependency(module, library);
            });
        }
    }

    private static void removeDLibs(final Module module, final Project project){
        final LibraryTable projectLibraryTable = LibraryTablesRegistrar.getInstance().getLibraryTable(project);
        for(final Library lib : projectLibraryTable.getLibraries()){
            removeLibraryIfNeeded(module,lib.getName());
        }
    }

    private static void removeLibraryIfNeeded(final Module module, final String libraryName) {
        ApplicationManager.getApplication().assertIsDispatchThread();

        final ModifiableModelsProvider modelsProvider = ModifiableModelsProvider.SERVICE.getInstance();
        final ModifiableRootModel model = modelsProvider.getModuleModifiableModel(module);
        final LibraryOrderEntry dLibraryEntry = OrderEntryUtil.findLibraryOrderEntry(model, libraryName);
        if (dLibraryEntry != null) {
            ApplicationManager.getApplication().runWriteAction(() -> {
                final Library library = dLibraryEntry.getLibrary();
                if (library != null) {
                    final LibraryTable table = library.getTable();
                    if (table != null) {
                        table.removeLibrary(library);
                        model.removeOrderEntry(dLibraryEntry);
                        modelsProvider.commitModuleModifiableModel(model);
                    }
                }
                else {
                    modelsProvider.disposeModuleModifiableModel(model);
                }
            });
        }
        else {
            ApplicationManager.getApplication().runWriteAction(() -> modelsProvider.disposeModuleModifiableModel(model));
        }
    }

    private static void displayError(@NotNull final AnActionEvent e, @NotNull final String message) {
        final String groupId = e.getPresentation().getText();
        Notifications.Bus.notify(new Notification(
                groupId, "Process D libs", message, NotificationType.ERROR), getEventProject(e));
        LOG.warn(message);
    }

}
