package net.masterthought.dlanguage.module;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.configurations.*;
import com.intellij.execution.impl.RunManagerImpl;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessOutputTypes;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.Pair;
import net.masterthought.dlanguage.DLanguageBundle;
import net.masterthought.dlanguage.run.DLanguageRunDubConfigurationType;
import net.masterthought.dlanguage.settings.ToolKey;
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

public class DLanguageDubModuleBuilder extends DLanguageModuleBuilder {

    private static final String RUN_DUB_CONFIG_NAME = "Run DUB";

    private List<Pair<String, String>> sourcePaths;
    private Map<String, String> dubOptions = new HashMap<>();
    private String dubBinary;

    public DLanguageDubModuleBuilder() {
        super("DLangDubApp", DLanguageBundle.INSTANCE.message("module.dub.title"), DLanguageBundle.INSTANCE.message("module.dub.description"), null);
    }

    @Override
    public void setupRootModel(final ModifiableRootModel rootModel) throws ConfigurationException {
        setJavaRootModel(rootModel);

        final Project project = rootModel.getProject();
        final RunManagerImpl runManager = RunManagerImpl.getInstanceImpl(project);

        if (dubBinary != null) {
            ToolKey.DUB_KEY.setPath(project, dubBinary);
        }

        //Create "Run dub" configuration
        RunnerAndConfigurationSettings runDubSettings = runManager.findConfigurationByName(RUN_DUB_CONFIG_NAME);
        if (runDubSettings == null) {
            final DLanguageRunDubConfigurationType configurationType
                = Extensions.findExtension(ConfigurationType.CONFIGURATION_TYPE_EP, DLanguageRunDubConfigurationType.class);
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

    public void setDubOptions(final Map<String, String> options) {
        this.dubOptions = options;
    }

    private void createDub(final String workingDirectory) {
        final GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setWorkDirectory(workingDirectory);
        commandLine.setExePath(this.dubBinary);
        final ParametersList parametersList = commandLine.getParametersList();
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

        try {
            final OSProcessHandler process = new OSProcessHandler(commandLine.createProcess(), commandLine.getCommandLineString());

            final StringBuilder builder = new StringBuilder();
            final AtomicBoolean errors = new AtomicBoolean();

            process.addProcessListener(new ProcessAdapter() {
                @Override
                public void onTextAvailable(final ProcessEvent event, final Key outputType) {
                    if (ProcessOutputTypes.STDERR.equals(outputType)) {
                        errors.set(true);
                    }
                    builder.append(LocalTime.now()).append(" [").append(outputType).append("] ").append(event.getText());
                }
            });

            process.startNotify();
            process.waitFor();

            // write out a log file with the dub init error if dub init doesn't make the project
            // would have been nice to log an event but the new project hasn't been loaded yet so this is the
            // only way I could think to notify the user that dub init failed.
            if (errors.get()) {
                final Path dubInitErrorLog = Paths.get(getContentEntryPath() + File.separator + "dub_init_error_log.txt");
                try {
                    Files.write(dubInitErrorLog, builder.toString().getBytes());
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (final ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void setDubBinary(final String dubBinary) {
        this.dubBinary = dubBinary;
    }
}
