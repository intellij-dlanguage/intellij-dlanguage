package io.github.intellij.dlanguage.module;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.configurations.*;
import com.intellij.execution.impl.RunManagerImpl;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessOutputTypes;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.ModifiableModuleModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleWithNameAlreadyExists;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.text.StringUtil;
import io.github.intellij.dlanguage.DlangBundle;
import io.github.intellij.dlanguage.run.DlangRunDubConfigurationType;
import io.github.intellij.dlanguage.settings.ToolKey;
import org.jdom.JDOMException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DlangDubModuleBuilder extends DlangModuleBuilder {

    private static final Logger LOG = Logger.getInstance(DlangDubModuleBuilder.class);

    private static final String RUN_DUB_CONFIG_NAME = "Run DUB";

    static final String BUILDER_ID = "DLangDubApp";

    private List<String> sourcePaths;
    private Map<String, String> dubOptions = new HashMap<>();
    private String dubBinary;

    public DlangDubModuleBuilder() {
        super(BUILDER_ID, DlangBundle.INSTANCE.message("module.dub.title"), DlangBundle.INSTANCE.message("module.dub.description"));
    }

    /*
    * When creating a project the following are called in order:
    *   createProject()
    *   commitModule()
    *   createAndCommitIfNeeded()
    *   setupRootModel()
    *   getSourcePaths()
    *
    * The code for running "dub init" has been moved here so that it has access to the Project and the "source"
    * directory should be created prior to the call to getSourcePaths().
    */
    @Override
    public @NotNull Module createAndCommitIfNeeded(@NotNull Project project, @Nullable ModifiableModuleModel model, boolean runFromProjectWizard) throws InvalidDataException, ConfigurationException, IOException, JDOMException, ModuleWithNameAlreadyExists {
        if (StringUtil.isNotEmpty(dubBinary)) {
            ToolKey.DUB_KEY.setPath(dubBinary);
        }

        if(!this.dubOptions.isEmpty()) {
            try {
                final GeneralCommandLine cmd = createDubInitCommand(project.getBasePath());

                ApplicationManager.getApplication()
                    .executeOnPooledThread(() -> this.runDubCommand(project, cmd));
            } catch (final Exception e) {
                LOG.error("dub init failed!", e);
            }
        }

        return super.createAndCommitIfNeeded(project, model, runFromProjectWizard);
    }

    @Override
    public void setupRootModel(@NotNull final ModifiableRootModel rootModel) throws ConfigurationException {
        super.setupRootModel(rootModel);

        LOG.debug("Setting up dub-specific root model for Dlang project");

        final Project project = rootModel.getProject();
        final RunManagerImpl runManager = RunManagerImpl.getInstanceImpl(project);

        //Create "Run dub" configuration
        @Nullable final RunnerAndConfigurationSettings runDubSettings = runManager.findConfigurationByName(RUN_DUB_CONFIG_NAME);
        if (runDubSettings == null) {
            @NotNull final DlangRunDubConfigurationType configurationType = ConfigurationType.CONFIGURATION_TYPE_EP.findExtensionOrFail(DlangRunDubConfigurationType.class);

            final ConfigurationFactory factory = configurationType.getConfigurationFactories()[0];
            final RunnerAndConfigurationSettings settings = runManager.createConfiguration(RUN_DUB_CONFIG_NAME, factory);
            ((ModuleBasedConfiguration) settings.getConfiguration()).setModule(rootModel.getModule());

            settings.storeInLocalWorkspace();
            runManager.addConfiguration(settings);
            LOG.debug(String.format("Run Configuration added for '%s'", RUN_DUB_CONFIG_NAME));
        }
    }

    /*
     * By default dub init will create a directory named "source" so this method presumptuously
     * returns "{WORKING_DIR}/source" as the source path. This could result in errors if dub
     * where to start creating files using an alternative directory name.
     */
    @NotNull
    @Override
    public List<String> getSourcePaths() {
        if (sourcePaths == null || sourcePaths.isEmpty()) {
            @NonNls final String path = getContentEntryPath() + File.separator + "source";

            //noinspection ArraysAsListWithZeroOrOneArgument
            sourcePaths = Arrays.asList(path); // may need to add additional source paths later
        }
        return sourcePaths;
    }

    public void addSourcePath(final String sourcePathInfo) {
        if (sourcePaths == null) {
            sourcePaths = new ArrayList<>();
        }
        sourcePaths.add(sourcePathInfo);
    }

    public void setDubOptions(final Map<String, String> options) {
        this.dubOptions = options;
    }

    /*
    * Build up the "dub init" command based on the parameters defined during project creation
    */
    private GeneralCommandLine createDubInitCommand(final String workingDirectory) {
        final ParametersList params = new ParametersList();
        params.addParametersString("init");
        params.addParametersString("-n");

        if (dubOptions.getOrDefault("dubParams", "").isEmpty()) {
            params.addParametersString("--format");
            params.addParametersString(dubOptions.getOrDefault("dubFormat", "json"));
            params.addParametersString("--type");
            params.addParametersString(dubOptions.getOrDefault("dubType", "minimal"));
        } else {
            params.addParametersString(dubOptions.get("dubParams"));
        }

        return new GeneralCommandLine()
            .withWorkDirectory(workingDirectory)
            .withExePath(this.dubBinary)
            .withParameters(params.getParameters());
    }

    /*
    * Must not be run within the EDT
    */
    private void runDubCommand(final Project project, final GeneralCommandLine cmd) {
        LOG.debug(cmd.getCommandLineString());

        try {
            final OSProcessHandler process = new OSProcessHandler(cmd.createProcess(), cmd.getCommandLineString());
            final DubInitListener listener = new DubInitListener();
            process.addProcessListener(listener);
            process.startNotify();
            process.waitFor();

            // new way to send a notification (since 2020.3) via notification group defined in plugin.xml
            NotificationGroupManager.getInstance().getNotificationGroup("DLANG_POPUP")
                .createNotification(
                    "Dub init output",
                    listener.getOutput(),
                    listener.hasErrors() ? NotificationType.WARNING : NotificationType.INFORMATION
                )
                .notify(project);

            if (listener.hasErrors()) {
                LOG.warn(listener.getOutput());
            }
        } catch (final ExecutionException e) {
            LOG.warn("There was a problem running 'dub init'", e);
        }
    }

    public void setDubBinary(final String dubBinary) {
        this.dubBinary = StringUtil.trim(dubBinary);
    }


    private static class DubInitListener extends ProcessAdapter {
        private final StringBuilder builder = new StringBuilder();
        private final AtomicBoolean errors = new AtomicBoolean();

        String getOutput() {
            return builder.toString();
        }

        boolean hasErrors() {
            return errors.get();
        }

        @Override
        public void onTextAvailable(@NotNull final ProcessEvent event, @NotNull final Key outputType) {
            if (ProcessOutputTypes.STDERR.equals(outputType)) {
                errors.set(true);
            }
            builder.append(LocalTime.now()).append(" [").append(outputType).append("] ").append(event.getText());
        }
    }
}
