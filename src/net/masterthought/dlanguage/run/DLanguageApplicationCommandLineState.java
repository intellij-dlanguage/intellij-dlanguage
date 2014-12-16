package net.masterthought.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.util.text.StringUtil;
import net.masterthought.dlanguage.settings.DLanguageBuildSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class DLanguageApplicationCommandLineState extends CommandLineState {

    private final DLanguageApplicationRunConfiguration myConfig;

    protected DLanguageApplicationCommandLineState(ExecutionEnvironment environment, DLanguageApplicationRunConfiguration runConfiguration) {
        super(environment);
        myConfig = runConfiguration;
    }

    @NotNull
    @Override
    protected OSProcessHandler startProcess() throws ExecutionException {
        ExecutionEnvironment env = getEnvironment();
        GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setWorkDirectory(env.getProject().getBasePath());
        // TODO: This should probably be a bit more generic than relying on `dmd -run`.

        final String dmdPath = DLanguageBuildSettings.getInstance(myConfig.getProject()).getDmdPath();
        final String rdmdPath = DLanguageBuildSettings.getInstance(myConfig.getProject()).getrDmdPath();


        ParametersList parametersList = commandLine.getParametersList();

        String args = myConfig.getRunnerParameters().getArguments();
        if (args != null && !args.isEmpty()) {
            parametersList.add(args);
        }

        Boolean withRun = myConfig.getRunnerParameters().isRunDmdMode();
        if(withRun){
            commandLine.setExePath(rdmdPath);
        } else {
            commandLine.setExePath(dmdPath);
        }

        parametersList.addParametersString(myConfig.getRunnerParameters().getFilePath());
        return new OSProcessHandler(commandLine.createProcess());
    }
}
