package net.masterthought.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.configurations.RuntimeConfigurationException;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.components.PathMacroManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizerUtil;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;


public class DLanguageApplicationRunConfiguration extends DLanguageRunConfigurationBase {

    // Element keys for readExternal and writeExternal to save configuration.
    public static final String PROGRAM_ARGUMENTS = "PROGRAM_ARGUMENTS";

    // Local configuration variables.
    public String programArguments;

    protected DLanguageApplicationRunConfiguration(@NotNull Project project, @NotNull ConfigurationFactory factory) {
        super(project, factory);
    }

    @NotNull
    @Override
    public DLanguageApplicationRunConfigurationEditorForm getConfigurationEditor() {
        return new DLanguageApplicationRunConfigurationEditorForm();
    }

    /**
     * If the user is not using dmd, an error is displayed in the run configuration dialog.
     */
    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {
        requireDmd();
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {
        return new DLanguageApplicationCommandLineState(executionEnvironment, this);
    }

    @Override
    public Collection<Module> getValidModules() {
        // TODO: Lookup modules with main defined.
        return null;
    }

    @Override
    public void readExternal(Element element) throws InvalidDataException {
        PathMacroManager.getInstance(getProject()).expandPaths(element);
        super.readExternal(element);
        programArguments = JDOMExternalizerUtil.readField(element, PROGRAM_ARGUMENTS);
    }

    @Override
    public void writeExternal(Element element) throws WriteExternalException {
        super.writeExternal(element);
        JDOMExternalizerUtil.writeField(element, PROGRAM_ARGUMENTS, programArguments);
        PathMacroManager.getInstance(getProject()).collapsePathsRecursively(element);
    }
}
