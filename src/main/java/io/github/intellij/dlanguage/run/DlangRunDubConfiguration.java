package io.github.intellij.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.util.xmlb.XmlSerializer;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DlangRunDubConfiguration extends ModuleBasedConfiguration<RunConfigurationModule, Module> {

    //General tab
    private int generalDubOptions = 1;
    //Build tab
    private boolean cbRdmd = false;
    private String tfBuild;
    private String tfCompiler;
    private String tfConfig;
    private String tfArch;
    private String tfDebug;
    private int buildMode = 0;
    private boolean cbForce = false;
    private boolean cbNoDeps = false;
    private boolean cbForceRemove = false;
    private boolean cbCombined = false;
    private boolean cbParallel = false;
    private boolean cbTempBuild;
    // Test tab
    private String tfMainFile;
    private boolean cbCoverage;
    //DUB properties
    private String workingDir;
    private boolean quiet = false;
    private boolean verbose = false;
    private String additionalParams;
    private Map<String, String> envVars;

    public DlangRunDubConfiguration(final String name, final Project project, final ConfigurationFactory factory) {
        super(name, new RunConfigurationModule(project), factory);

        final Collection<Module> modules = this.getValidModules();
        if (!modules.isEmpty()) {
            //Get first valid module and use it
            this.setModule(modules.iterator().next());
        }
        envVars = new HashMap<String, String>();
    }

    public int getGeneralDubOptions() {
        return this.generalDubOptions;
    }

    public void setGeneralDubOptions(final int generalDubOptions) {
        this.generalDubOptions = generalDubOptions;
    }

    public boolean isCbForce() {
        return cbForce;
    }

    public void setCbForce(final boolean cbForce) {
        this.cbForce = cbForce;
    }

    public boolean isCbNoDeps() {
        return cbNoDeps;
    }

    public void setCbNoDeps(final boolean cbNoDeps) {
        this.cbNoDeps = cbNoDeps;
    }

    public boolean isCbForceRemove() {
        return cbForceRemove;
    }

    public void setCbForceRemove(final boolean cbForceRemove) {
        this.cbForceRemove = cbForceRemove;
    }

    public boolean isCbCombined() {
        return cbCombined;
    }

    public void setCbCombined(final boolean cbCombined) {
        this.cbCombined = cbCombined;
    }

    public boolean isCbParallel() {
        return cbParallel;
    }

    public void setCbParallel(final boolean cbParallel) {
        this.cbParallel = cbParallel;
    }

    public String getTfBuild() {
        return tfBuild;
    }

    public void setTfBuild(final String tfBuild) {
        this.tfBuild = tfBuild;
    }

    public String getTfCompiler() {
        return tfCompiler;
    }

    public void setTfCompiler(final String tfCompiler) {
        this.tfCompiler = tfCompiler;
    }

    public String getTfConfig() {
        return tfConfig;
    }

    // Run tab

    public void setTfConfig(final String tfConfig) {
        this.tfConfig = tfConfig;
    }

    public String getTfArch() {
        return tfArch;
    }

    public void setTfArch(final String tfArch) {
        this.tfArch = tfArch;
    }

    public String getTfDebug() {
        return tfDebug;
    }

    public void setTfDebug(final String tfDebug) {
        this.tfDebug = tfDebug;
    }

    public int getBuildMode() {
        return buildMode;
    }

    public void setBuildMode(final int buildMode) {
        this.buildMode = buildMode;
    }

    public boolean isCbRdmd() {
        return cbRdmd;
    }

    public void setCbRdmd(final boolean cbRdmd) {
        this.cbRdmd = cbRdmd;
    }

    public boolean isCbTempBuild() {
        return cbTempBuild;
    }

    public void setCbTempBuild(final boolean cbTempBuild) {
        this.cbTempBuild = cbTempBuild;
    }

    public String getTfMainFile() {
        return tfMainFile;
    }

    public void setTfMainFile(final String tfMainFile) {
        this.tfMainFile = tfMainFile;
    }

    public boolean isCbCoverage() {
        return cbCoverage;
    }

    public void setCbCoverage(final boolean cbCoverage) {
        this.cbCoverage = cbCoverage;
    }

    @Override
    public Collection<Module> getValidModules() {
        final Module[] modules = ModuleManager.getInstance(getProject()).getModules();
        final DMDRunner appRunner = new DMDRunner();

        final ArrayList<Module> res = new ArrayList<Module>();
        for (final Module module : modules) {
            if (appRunner.isValidModule(module)) {
                res.add(module);
            }
        }
        return res;
    }

    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new DlangRunDubConfigurationEditor();
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull final Executor executor, @NotNull final ExecutionEnvironment env) throws ExecutionException {
        return new DlangRunDubState(env, this);
    }

    @Override
    public void writeExternal(@NotNull final Element element) throws WriteExternalException {
        if (envVars == null) {
            envVars = new HashMap<String, String>();
        }

        super.writeExternal(element);
        XmlSerializer.serializeInto(this, element);
    }

    @Override
    public void readExternal(@NotNull final Element element) throws InvalidDataException {
        super.readExternal(element);
        readModule(element);
        XmlSerializer.deserializeInto(this, element);
    }

    //    /**
//     * Getters and Setters. Autogenerated by IntelliJ IDEA *
//     */
    public String getWorkingDir() {
        return workingDir;
    }

    public void setWorkingDir(final String workingDir) {
        this.workingDir = workingDir;
    }

    public boolean isQuiet() {
        return quiet;
    }

    public void setQuiet(final boolean quiet) {
        this.quiet = quiet;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(final boolean verbose) {
        this.verbose = verbose;
    }

    public String getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(final String additionalParams) {
        this.additionalParams = additionalParams;
    }

    public Map<String, String> getEnvVars() {
        return envVars;
    }

    public void setEnvVars(final Map<String, String> envVars) {
        this.envVars = envVars;
    }
}
