package net.masterthought.dlanguage.actions;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
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
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootModificationUtil;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.ui.components.JBList;
import net.masterthought.dlanguage.module.DLanguageModuleType;
import net.masterthought.dlanguage.settings.ToolKey;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Collection;
import java.util.Iterator;

public class ProcessDLibs extends AnAction implements DumbAware {
    private static final Logger LOG = Logger.getInstance(ProcessDLibs.class);

    public static final String MENU_PATH = "Tools > Process D Libs";

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabled(enabled(e));
    }

    private static boolean enabled(@NotNull AnActionEvent e) {
//        final Project project = getEventProject(e);
//        if (project == null) return false;
//        final String cdcServerPath = ToolKey.DCD_SERVER_KEY.getPath(project);
//        return cdcServerPath != null && !cdcServerPath.isEmpty() && DLanguageModuleType.findModules(project).size() > 0;
        return true;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final String prefix = "Unable to process D libraries - ";
        Project project = e.getProject();
        if (project == null) {
            displayError(e, prefix + "No active project.");
            return;
        }
        Collection<Module> modules = DLanguageModuleType.findModules(project);
        int size = modules.size();
        if (size == 0) displayError(e, prefix + "No DLanguage modules are used in this project.");
        else if (size == 1) processDLibs(e, modules.iterator().next());
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
        return new Runnable() {
            @Override
            public void run() {
                processDLibs(e, (Module) list.getSelectedValue());
            }
        };
    }

    private static void processDLibs(@NotNull AnActionEvent e, @NotNull final Module module) {

        Project project = getEventProject(e);
        final String dubPAth = ToolKey.DUB_KEY.getPath(project);

        GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setWorkDirectory(module.getProject().getBasePath());
        commandLine.setExePath(dubPAth);
        ParametersList parametersList = commandLine.getParametersList();
        parametersList.addParametersString("describe");

        try {
            OSProcessHandler process = new OSProcessHandler(commandLine.createProcess());

            final StringBuilder builder = new StringBuilder();
            process.addProcessListener(new ProcessAdapter() {
                @Override
                public void onTextAvailable(ProcessEvent event, Key outputType) {
                    builder.append(event.getText());
                }
            });

            process.startNotify();
            process.waitFor();

            // remove the warning line at the top if it exists
            String json = builder.toString().replaceAll("WARNING.+","").trim();
//            System.out.println(json);

            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            JsonArray packages = jsonObject.get("packages").getAsJsonArray();
            for (JsonElement pkg : packages) {
                String path = ((JsonObject) pkg).get("path").getAsString();
                String name = ((JsonObject) pkg).get("name").getAsString();
                String version = ((JsonObject) pkg).get("version").getAsString();
                String fullName = name + "-" + version;
                if (path.contains(".dub/packages")) {
                    createLibraryDependency(module, project, fullName, path);
                }
            }

        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }

    }

    private static void createLibraryDependency(final Module module, Project project, String libraryName, String libraryPath) {
        LibraryTable projectLibraryTable = LibraryTablesRegistrar.getInstance().getLibraryTable(project);
        final LibraryTable.ModifiableModel projectLibraryModel = projectLibraryTable.getModifiableModel();

        // remove existing libs
//        for(Library lib : projectLibraryModel.getLibraries()){
//            projectLibraryModel.removeLibrary(lib);
//        }

        final Library library = projectLibraryModel.createLibrary(libraryName);
        final Library.ModifiableModel libraryModel = library.getModifiableModel();
        String pathUrl = VirtualFileManager.constructUrl(LocalFileSystem.PROTOCOL, libraryPath);
        VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(pathUrl);

        if (file != null) {
            libraryModel.addRoot(file, OrderRootType.CLASSES);
            ApplicationManager.getApplication().runWriteAction(new Runnable() {
                @Override
                public void run() {
                    libraryModel.commit();
                    projectLibraryModel.commit();
                    ModuleRootModificationUtil.addDependency(module, library);
                }
            });
        }
    }

    private static void displayError(@NotNull AnActionEvent e, @NotNull String message) {
        final String groupId = e.getPresentation().getText();
        Notifications.Bus.notify(new Notification(
                groupId, "Restart dcd-server", message, NotificationType.ERROR), getEventProject(e));
        LOG.warn(message);
    }

}
