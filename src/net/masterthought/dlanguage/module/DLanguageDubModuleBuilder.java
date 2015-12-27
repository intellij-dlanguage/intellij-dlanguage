package net.masterthought.dlanguage.module;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.configurations.*;
import com.intellij.execution.impl.RunManagerImpl;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.Pair;
import net.masterthought.dlanguage.DLanguageBundle;
import net.masterthought.dlanguage.run.DLanguageRunDubConfigurationType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DLanguageDubModuleBuilder extends DLanguageModuleBuilder {

    public static final String RUN_DUB_CONFIG_NAME = "Run DUB";

    private List<Pair<String, String>> sourcePaths;
    private List<Pair<String, String>> dubInitOptions;

    public DLanguageDubModuleBuilder() {
        super("DLangDubApp", DLanguageBundle.message("module.dub.title"), DLanguageBundle.message("module.dub.description"), null);
    }

    @Override
    public void setupRootModel(ModifiableRootModel rootModel) throws ConfigurationException {
        setJavaRootModel(rootModel);

        Project project = rootModel.getProject();
        RunManagerImpl runManager = RunManagerImpl.getInstanceImpl(project);

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
            final List<Pair<String, String>> paths = new ArrayList<Pair<String, String>>();
            @NonNls final String path = getContentEntryPath() + File.separator + "source";
            try {
                createDub(getContentEntryPath());
            } catch (Exception e) {
                new File(path).mkdirs();
            }
            paths.add(Pair.create(path, ""));
            sourcePaths = paths;
        }
        return sourcePaths;
    }

    public void setDubInitOptions(List<Pair<String, String>> options) {
        this.dubInitOptions = options;
    }

    public List<Pair<String, String>> getDubInitOptions() {
        return dubInitOptions;
    }

    private void createDub(String workingDirectory) {

        List<Pair<String, String>> dubOptions = getDubInitOptions();
        String dubFormat = "json";
        String dubType = "minimal";
        String dubParams = "";
        for (Pair<String, String> pair : dubOptions) {
            if (pair.getFirst().equals("dubFormat")) {
                dubFormat = pair.getSecond();
            } else if (pair.getFirst().equals("dubType")) {
                dubType = pair.getSecond();
            } else if (pair.getFirst().equals("dubParams")) {
                dubParams = pair.getSecond();
            }
        }

        GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setWorkDirectory(workingDirectory);
        commandLine.setExePath("dub");
        ParametersList parametersList = commandLine.getParametersList();
        parametersList.addParametersString("init");

        if (dubParams.isEmpty()) {
            parametersList.addParametersString("--format");
            parametersList.addParametersString(dubFormat);
            parametersList.addParametersString("--type");
            parametersList.addParametersString(dubType);
        } else {
            parametersList.addParametersString(dubParams);
        }

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

        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
