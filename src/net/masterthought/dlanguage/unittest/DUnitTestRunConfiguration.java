package net.masterthought.dlanguage.unittest;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.LocatableConfigurationBase;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.configurations.RuntimeConfigurationException;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.util.PathUtil;
import com.intellij.util.xmlb.XmlSerializer;
import net.masterthought.dlanguage.DLanguage;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DUnitTestRunConfiguration extends LocatableConfigurationBase {

    // Element keys for readExternal and writeExternal to save configuration.
//    public static final String PROGRAM_ARGUMENTS = "PROGRAM_ARGUMENTS";

    // Local configuration variables.
    private @NotNull
    DUnitTestParameters myRunnerParameters = new DUnitTestParameters();

    @NotNull
    public DUnitTestParameters getRunnerParameters() {
        return myRunnerParameters;
    }

    public DUnitTestRunConfiguration(Project project)
    {
        super(project, new DUnitTestRunConfigurationFactory(DUnitTestRunConfigurationType.getInstance()), DLanguage.INSTANCE.getDisplayName());
        envVars = new HashMap<>();
    }

    private Map<String, String> envVars;

    @Override
    public void readExternal(Element element) throws InvalidDataException
    {
        super.readExternal(element);
        XmlSerializer.deserializeInto(this, element);
    }

    @Override
    public void writeExternal(Element element) throws WriteExternalException
    {
        super.writeExternal(element);
        XmlSerializer.serializeInto(this, element);
    }

    @NotNull
    @Override
    public SettingsEditor<DUnitTestRunConfiguration> getConfigurationEditor()
    {
        return new DUnitTestRunConfigurationEditor(getProject());
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException
    {
        // TODO: Fill this in
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException
    {
        return new DUnitTestRunProfileState(environment);
    }

    @Nullable
    public String suggestedName() {
        final String filePath = myRunnerParameters.getFilePath();
        return filePath == null ? null : PathUtil.getFileName(filePath);
    }

    public DUnitTestRunConfiguration clone() {
        final DUnitTestRunConfiguration clone = (DUnitTestRunConfiguration)super.clone();
        clone.myRunnerParameters = myRunnerParameters.clone();
        return clone;
    }
}

