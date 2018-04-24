package io.github.intellij.dlanguage.module;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.configurations.*;
import com.intellij.execution.impl.RunManagerImpl;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessOutputTypes;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.Pair;
import io.github.intellij.dlanguage.DlangBundle;
import io.github.intellij.dlanguage.run.DlangRunDubConfigurationType;
import io.github.intellij.dlanguage.settings.ToolKey;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class DlangDubModuleBuilder extends DlangModuleBuilder {

    private static final Logger LOG = Logger.getInstance(DlangDubModuleBuilder.class);

    private static final String RUN_DUB_CONFIG_NAME = "Run DUB";

    private List<Pair<String, String>> sourcePaths;
    private Map<String, String> dubOptions = new HashMap<>();
    private String dubBinary;

    public DlangDubModuleBuilder() {
        super("DLangDubApp", DlangBundle.INSTANCE.message("module.dub.title"), DlangBundle.INSTANCE.message("module.dub.description"));
    }

    @Override
    public void setupRootModel(final ModifiableRootModel rootModel) throws ConfigurationException {
        super.setupRootModel(rootModel);

        final Project project = rootModel.getProject();
        final RunManagerImpl runManager = RunManagerImpl.getInstanceImpl(project);

        if (dubBinary != null) {
            ToolKey.DUB_KEY.setPath(dubBinary);
        }

        //Create "Run dub" configuration
        RunnerAndConfigurationSettings runDubSettings = runManager.findConfigurationByName(RUN_DUB_CONFIG_NAME);
        if (runDubSettings == null) {
            final DlangRunDubConfigurationType configurationType
                = Extensions.findExtension(ConfigurationType.CONFIGURATION_TYPE_EP, DlangRunDubConfigurationType.class);
            final ConfigurationFactory factory = configurationType.getConfigurationFactories()[0];
            runDubSettings = runManager.createRunConfiguration(RUN_DUB_CONFIG_NAME, factory);
            ((ModuleBasedConfiguration) runDubSettings.getConfiguration()).setModule(rootModel.getModule());

            runManager.addConfiguration(runDubSettings, false);
        }
    }

    /* By default sources are located in {WORKING_DIR}/source folder. */
    @NotNull
    @Override
    public List<Pair<String, String>> getSourcePaths() {
        if (sourcePaths == null) {
            final List<Pair<String, String>> paths = new ArrayList<>();
            @NonNls final String path = getContentEntryPath() + File.separator + "source";
            try {
                createDub(getContentEntryPath());
            } catch (final Exception e) {
                new File(path).mkdirs();
            }
            paths.add(Pair.create(path, ""));
            sourcePaths = paths;
        }
        return sourcePaths;
    }

    public void addSourcePath(final Pair<String, String> sourcePathInfo) {
        if (sourcePaths == null) {
            sourcePaths = new ArrayList<>();
        }
        sourcePaths.add(sourcePathInfo);
    }

    public void setDubOptions(final Map<String, String> options) {
        this.dubOptions = options;
    }

    private void createDub(final String workingDirectory) {
        final ParametersList parametersList = new ParametersList();
        parametersList.addParametersString("init");
        parametersList.addParametersString("-n");

        if (dubOptions.getOrDefault("dubParams", "").isEmpty()) {
            parametersList.addParametersString("--format");
            parametersList.addParametersString(dubOptions.getOrDefault("dubFormat", "json"));
            parametersList.addParametersString("--type");
            parametersList.addParametersString(dubOptions.getOrDefault("dubType", "minimal"));
        } else {
            parametersList.addParametersString(dubOptions.get("dubParams"));
        }

        final GeneralCommandLine cmd = new GeneralCommandLine()
            .withWorkDirectory(workingDirectory)
            .withExePath(this.dubBinary)
            .withParameters(parametersList.getParameters());

        try {
            final OSProcessHandler process = new OSProcessHandler(cmd.createProcess(), cmd.getCommandLineString());
            final DubInitListener listener = new DubInitListener();
            process.addProcessListener(listener);
            process.startNotify();
            process.waitFor();

            // write out a log file with the dub init error if dub init doesn't make the project
            // would have been nice to log an event but the new project hasn't been loaded yet so this is the
            // only way I could think to notify the user that dub init failed.
            if (listener.hasErrors()) {
                writeErrorLog(listener.getOutput(), "dub_init_error_log.txt");
            }

        } catch (final ExecutionException e) {
            LOG.warn("There was a problem running 'dub init'", e);
        }
    }

    public void setDubBinary(final String dubBinary) {
        this.dubBinary = dubBinary;
    }

    private void writeErrorLog(final String content, @NotNull final String fileName) {
        final Path dubInitErrorLog = Paths.get(getContentEntryPath() + File.separator + fileName);
        try {
            Files.write(dubInitErrorLog, content.getBytes());
        } catch (final IOException e) {
            LOG.warn("Unable to write to " + fileName);
        }
    }

    private class DubInitListener extends ProcessAdapter {
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
