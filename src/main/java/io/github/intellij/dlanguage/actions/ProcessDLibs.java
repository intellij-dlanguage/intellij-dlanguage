package io.github.intellij.dlanguage.actions;

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
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task.Backgroundable;
import com.intellij.openapi.progress.impl.BackgroundableProcessIndicator;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.LibraryOrderEntry;
import com.intellij.openapi.roots.ModifiableModelsProvider;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootModificationUtil;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.impl.OrderEntryUtil;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.ui.components.JBList;
import io.github.intellij.dlanguage.module.DlangModuleType;
import io.github.intellij.dlanguage.project.DubConfigurationParser;
import io.github.intellij.dlanguage.project.DubPackage;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.utils.DToolsNotificationListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProcessDLibs extends AnAction implements DumbAware {

    public static final String MENU_PATH = "Tools > Process D Libraries";
    private static final String NOTIFICATION_GROUPID = "Process D Libs";
    private static final Logger LOG = Logger.getInstance(ProcessDLibs.class);

    private static boolean enabled(@NotNull final AnActionEvent e) {
        final Project project = getEventProject(e);
        if (project == null) {
            return false;
        }
        final String dubPath = ToolKey.DUB_KEY.getPath();
        return dubPath != null && !dubPath.isEmpty()
            && DlangModuleType.findModules(project).size() > 0;
    }

    private static void showModuleChoicePopup(@NotNull final AnActionEvent e, final Project project,
        final Collection<Module> modules) {
        final JList list = new JBList(JBList.createDefaultListModel(modules.toArray()));
        final JBPopup popup = JBPopupFactory.getInstance()
            .createListPopupBuilder(list)
            .setTitle("Process D libraries for module")
            .setItemChoosenCallback(makeModuleChoiceCallback(e, list))
            .createPopup();
        popup.showCenteredInCurrentWindow(project);
    }

    private static Runnable makeModuleChoiceCallback(final @NotNull AnActionEvent event,
        final @NotNull JList list) {
        return () -> processDLibs(getEventProject(event), (Module) list.getSelectedValue());
    }

    private static boolean dubPathAlreadWarned = false;

    public static void processDLibs(final Project project, @NotNull final Module module) {
        processDLibs(project, module, false, false);
    }

    public static void processDLibs(final Project project,
        @NotNull final Module module, final boolean mostlySilentMode, final boolean buildBefore) {
        ApplicationManager.getApplication().invokeAndWait(new Runnable() {
            @Override
            public void run() {
                final Backgroundable task = new Backgroundable(project,
                    "Updating Dub Libraries") {
                    @Override
                    public void run(@NotNull final ProgressIndicator indicator) {
                        processDLibsImpl(project, module, mostlySilentMode, buildBefore);
                    }
                };
                ProgressManager.getInstance().runProcessWithProgressAsynchronously(
                    task, new BackgroundableProcessIndicator(task));
            }
        }, ModalityState.defaultModalityState());
    }

    public static void processDLibsImpl(final Project project, @NotNull final Module module,
        final boolean mostlySilentMode, final boolean buildBefore) {
        final String prefix = "Unable to process D libraries - ";
        if (project == null) {
            displayError(project, prefix + "No active project.");
            return;
        }
        //todo needs build/fetch before adding libs, also needs to keep track of libs

        // remove all existing libs
//        removeDLibs(module, project);//this is not necessary since intellij filters out duplicate libraries.

        // ask dub for required libs
        final String dubPath = ToolKey.DUB_KEY.getPath();

        //final String groupId = e.getPresentation().getText();
        if (dubPath == null) {
            if (!dubPathAlreadWarned) {
                Notifications.Bus.notify(
                    new Notification(NOTIFICATION_GROUPID, "Process D Libraries",
                        "DUB executable path is empty<br/><a href='configureDLanguageTools'>Configure</a>",
                        NotificationType.WARNING, new DToolsNotificationListener(project)),
                    project);
                dubPathAlreadWarned = true;
            }
            return;
        }

        final DubConfigurationParser dubParser = new DubConfigurationParser(project, dubPath,
            mostlySilentMode);
        if (dubParser.canUseDub()) {
            final List<DubPackage> dependencies = dubParser.getDubPackageDependencies();
            for (final DubPackage pkg : dependencies) {
                final String fullName = pkg.getName() + "-" + pkg.getVersion();
                createLibraryDependency(module, project, fullName, pkg);
            }
        } else {
            LOG.info("not possible to run 'dub describe'");
        }

        if (!mostlySilentMode) {
            Notifications.Bus.notify(
                new Notification(NOTIFICATION_GROUPID, "Process D Libraries",
                    "Added your dub dependency libraries",
                    NotificationType.INFORMATION, new DToolsNotificationListener(project)),
                project);
        }
    }

    private static void createLibraryDependency(final Module module, final Project project,
        final String libraryName, final DubPackage dubPackage) {
        final VirtualFile sources = getSourcesVirtualFile(dubPackage);
        final LibraryTable projectLibraryTable = LibraryTablesRegistrar.getInstance()
            .getLibraryTable(project);
        final LibraryTable.ModifiableModel projectLibraryModel = projectLibraryTable
            .getModifiableModel();

        final Library library = projectLibraryModel.createLibrary(libraryName);
        final Library.ModifiableModel libraryModel = library.getModifiableModel();

        if (sources != null) {
            libraryModel.addRoot(sources, OrderRootType.CLASSES);
            //todo add binary libs/dub.json as well
            ApplicationManager.getApplication()
                .invokeAndWait(() -> ApplicationManager.getApplication().runWriteAction(() -> {
                    libraryModel.commit();
                    projectLibraryModel.commit();
                    for (final Module projectModule : ModuleManager.getInstance(project)
                        .getModules()) {
                        ModuleRootModificationUtil.addDependency(projectModule, library);
                    }

                }));

        }
    }

    private static VirtualFile getSourcesVirtualFile(final DubPackage dubPackage) {
        final String sourcesPathUrl;
        if (SystemInfo.isWindows) {
            sourcesPathUrl = VirtualFileManager.constructUrl(LocalFileSystem.PROTOCOL,
                dubPackage.getPath() + dubPackage.getSourcesDir().replace("/", "\\"));
        } else {
            sourcesPathUrl = VirtualFileManager.constructUrl(LocalFileSystem.PROTOCOL,
                dubPackage.getPath() + dubPackage.getSourcesDir());
        }
        final VirtualFile sources = VirtualFileManager.getInstance()
            .refreshAndFindFileByUrl(sourcesPathUrl);
        if (sources == null) {
            LOG.info("sources not found, fetching them");
            final GeneralCommandLine commandLine = new GeneralCommandLine();
            final String dubBinaryPath = ToolKey.DUB_KEY.getPath();
            if (dubBinaryPath == null) {
                LOG.error("dub executable should be configured");
                return null;
            }
            commandLine.setExePath(dubBinaryPath);
            final ParametersList parametersList = commandLine.getParametersList();
            parametersList.addParametersString("fetch");
            parametersList.addParametersString(dubPackage.getName());
            parametersList.addParametersString("--version=" + dubPackage.getVersion());

            final String dubCommand = commandLine.getCommandLineString();
            final OSProcessHandler process;
            try {
                process = new OSProcessHandler(commandLine.createProcess(), dubCommand);
            } catch (final ExecutionException e) {
                LOG.error(e);
                return null;
            }

            final StringBuilder builder = new StringBuilder();
            final List<String> errors = new ArrayList<>();

            process.addProcessListener(new ProcessAdapter() {
                @Override
                public void onTextAvailable(final ProcessEvent event, final Key outputType) {
                    switch (outputType.toString()) {
                        case "stdout":
                            builder.append(event.getText());
                            break;
                        case "stderr":
                            errors.add(event.getText());
                            break;
                    }
                }
            });

            process.startNotify();
            process.waitFor();

            LOG.debug("output from fetching package:" + builder.toString());

            for (final String error : errors) {
                LOG.error("error while fetching:" + error);
            }

            final Integer exitCode = process.getExitCode();
            if (exitCode != null && exitCode == 0) {
                //try loading again
                return VirtualFileManager.getInstance().refreshAndFindFileByUrl(sourcesPathUrl);
            } else {
                LOG.error("exitcode was no zero for fetching package");
            }
        }
        return sources;
    }

    private static void removeDLibs(final Module module, final Project project) {
        final LibraryTable projectLibraryTable = LibraryTablesRegistrar.getInstance()
            .getLibraryTable(project);
        for (final Library lib : projectLibraryTable.getLibraries()) {
            removeLibraryIfNeeded(module, lib.getName());
        }
    }

    private static void removeLibraryIfNeeded(final Module module, final String libraryName) {
        ApplicationManager.getApplication().assertIsDispatchThread();

        final ModifiableModelsProvider modelsProvider = ModifiableModelsProvider.SERVICE
            .getInstance();
        final ModifiableRootModel model = modelsProvider.getModuleModifiableModel(module);
        final LibraryOrderEntry dLibraryEntry = OrderEntryUtil
            .findLibraryOrderEntry(model, libraryName);
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
                } else {
                    modelsProvider.disposeModuleModifiableModel(model);
                }
            });
        } else {
            ApplicationManager.getApplication()
                .runWriteAction(() -> modelsProvider.disposeModuleModifiableModel(model));
        }
    }

    private static void displayError(@NotNull final AnActionEvent e,
        @NotNull final String message) {
        displayError(getEventProject(e), message);
    }

    private static void displayError(final @Nullable Project project,
        @NotNull final String message) {
        //final String groupId = e.getPresentation().getText();
        Notifications.Bus.notify(
            new Notification(NOTIFICATION_GROUPID, "Process D libs", message,
                NotificationType.ERROR),
            project
        );
        LOG.warn(message);
    }

    @Override
    public void update(@NotNull final AnActionEvent e) {
        e.getPresentation().setEnabled(enabled(e));
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
        if (size == 0) {
            displayError(e, prefix + "No DLanguage modules are used in this project.");
        } else if (size == 1) {
            processDLibs(project, modules.iterator().next());
        } else {
            showModuleChoicePopup(e, project, modules);
        }
    }

}
