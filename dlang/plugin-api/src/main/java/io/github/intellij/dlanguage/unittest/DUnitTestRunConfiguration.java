package io.github.intellij.dlanguage.unittest;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.PathUtil;
import com.intellij.util.xmlb.XmlSerializer;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.DlangBundle;
import io.github.intellij.dlanguage.DlangFileType;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DUnitTestRunConfiguration extends LocatableConfigurationBase {

    public String getdFilePath() {
        return dFilePath;
    }

    public void setdFilePath(final String dFilePath) {
        this.dFilePath = dFilePath;
    }

    public String getWorkingDir() {
        return workingDir;
    }

    public void setWorkingDir(final String workingDir) {
        this.workingDir = workingDir;
    }

    public Map<String, String> getEnvVars() {
        return envVars;
    }

    public void setEnvVars(final Map<String, String> envVars) {
        this.envVars = envVars;
    }

    private String dFilePath;
    private String workingDir;
    private Map<String, String> envVars;

    public DUnitTestRunConfiguration(@NotNull final Project project) {
        this(DLanguage.INSTANCE.getDisplayName(), project);
    }

    public DUnitTestRunConfiguration(@Nullable String name, @NotNull Project project) {
        super(project, new DUnitTestRunConfigurationFactory(DUnitTestRunConfigurationType.getInstance()), name);
        envVars = new HashMap<>();
    }

    @Override
    public void readExternal(final @NotNull Element element) throws InvalidDataException {
        super.readExternal(element);
        XmlSerializer.deserializeInto(this, element);
    }

    @Override
    public void writeExternal(final @NotNull Element element) throws WriteExternalException {
        super.writeExternal(element);
        XmlSerializer.serializeInto(this, element);
    }

    @NotNull
    @Override
    public SettingsEditor<DUnitTestRunConfiguration> getConfigurationEditor() {
        return new DUnitTestRunConfigurationEditor(getProject());
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException
    {
        // TODO: Fill this in
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull final Executor executor, @NotNull final ExecutionEnvironment environment) throws ExecutionException {
        return new DUnitTestRunProfileState(environment);
    }

    @NotNull
    public VirtualFile getDFile() throws RuntimeConfigurationError {
        final String filePath = getdFilePath();
        if (StringUtil.isEmptyOrSpaces(filePath)) {
            throw new RuntimeConfigurationError(DlangBundle.INSTANCE.message("path.to.dlanguage.file.not.set"));
        }

        final VirtualFile dFile = LocalFileSystem.getInstance().findFileByPath(filePath);
        if (dFile == null || dFile.isDirectory()) {
            throw new RuntimeConfigurationError(DlangBundle.INSTANCE.message("dlanguage.file.not.found", FileUtil.toSystemDependentName(filePath)));
        }

        if (dFile.getFileType() != DlangFileType.INSTANCE) {
            throw new RuntimeConfigurationError(DlangBundle.INSTANCE.message("not.a.dlanguage.file", FileUtil.toSystemDependentName(filePath)));
        }

        return dFile;
    }

    @Nullable
    public String suggestedName() {
        final String filePath = getdFilePath();
        return filePath == null ? null : PathUtil.getFileName(filePath);
    }
//
//    public DUnitTestRunConfiguration clone() {
//        final DUnitTestRunConfiguration clone = (DUnitTestRunConfiguration)super.clone();
//        clone.myRunnerParameters = myRunnerParameters.clone();
//        return clone;
//    }
}

