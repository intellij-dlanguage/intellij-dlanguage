package net.masterthought.dlanguage.run;

import com.intellij.execution.configurations.RuntimeConfigurationError;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.xmlb.annotations.MapAnnotation;
import net.masterthought.dlanguage.DLanguageBundle;
import net.masterthought.dlanguage.DLanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class DLanguageCommandLineRunnerParameters implements Cloneable {
    private
    @Nullable
    String myFilePath = null;
    private
    @Nullable
    String myVMOptions = null;
    private boolean myRunDmdMode = true;
    private
    @Nullable
    String myArguments = null;
    private
    @Nullable
    String myWorkingDirectory = null;
    private
    @NotNull
    Map<String, String> myEnvs = new LinkedHashMap<String, String>();
    private boolean myIncludeParentEnvs = true;

    @Nullable
    public String getFilePath() {
        return myFilePath;
    }

    public void setFilePath(final @Nullable String filePath) {
        myFilePath = filePath;
    }

    @Nullable
    public String getVMOptions() {
        return myVMOptions;
    }

    public void setVMOptions(final @Nullable String vmOptions) {
        myVMOptions = vmOptions;
    }

    public boolean isRunDmdMode() {
        return myRunDmdMode;
    }

    public void setRunDmdMode(final boolean runDmdMode) {
        myRunDmdMode = runDmdMode;
    }

    @Nullable
    public String getArguments() {
        return myArguments;
    }

    public void setArguments(final @Nullable String arguments) {
        myArguments = arguments;
    }

    @Nullable
    public String getWorkingDirectory() {
        return myWorkingDirectory;
    }

    public void setWorkingDirectory(final @Nullable String workingDirectory) {
        myWorkingDirectory = workingDirectory;
    }

    @NotNull
    @MapAnnotation(surroundWithTag = false, surroundKeyWithTag = false, surroundValueWithTag = false)
    public Map<String, String> getEnvs() {
        return myEnvs;
    }

    public void setEnvs(@SuppressWarnings("NullableProblems") final Map<String, String> envs) {
        if (envs != null) { // null comes from old projects or if storage corrupted
            myEnvs = envs;
        }
    }

    public boolean isIncludeParentEnvs() {
        return myIncludeParentEnvs;
    }

    public void setIncludeParentEnvs(final boolean includeParentEnvs) {
        myIncludeParentEnvs = includeParentEnvs;
    }

    @NotNull
    public VirtualFile getDFile() throws RuntimeConfigurationError {
        final String filePath = getFilePath();
        if (StringUtil.isEmptyOrSpaces(filePath)) {
            throw new RuntimeConfigurationError(DLanguageBundle.message("path.to.dlanguage.file.not.set"));
        }

        final VirtualFile dFile = LocalFileSystem.getInstance().findFileByPath(filePath);
        if (dFile == null || dFile.isDirectory()) {
            throw new RuntimeConfigurationError(DLanguageBundle.message("dlanguage.file.not.found", FileUtil.toSystemDependentName(filePath)));
        }

        if (dFile.getFileType() != DLanguageFileType.INSTANCE) {
            throw new RuntimeConfigurationError(DLanguageBundle.message("not.a.dlanguage.file", FileUtil.toSystemDependentName(filePath)));
        }

        return dFile;
    }


    @Override
    protected DLanguageCommandLineRunnerParameters clone() {
        try {
            final DLanguageCommandLineRunnerParameters clone = (DLanguageCommandLineRunnerParameters) super.clone();
            clone.myEnvs = new LinkedHashMap<String, String>();
            clone.myEnvs.putAll(myEnvs);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

