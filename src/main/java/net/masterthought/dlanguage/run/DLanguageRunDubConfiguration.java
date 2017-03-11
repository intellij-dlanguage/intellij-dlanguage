package net.masterthought.dlanguage.run;

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

public class DLanguageRunDubConfiguration extends ModuleBasedConfiguration<RunConfigurationModule>
        implements RunConfiguration {

    //General tab
    private int generalDubOptions = 1;

    public int getGeneralDubOptions() {
        return this.generalDubOptions;
    }

    public void setGeneralDubOptions(int generalDubOptions) {
        this.generalDubOptions = generalDubOptions;
    }

    //Build tab
    private boolean cbRdmd = false;

    public boolean isCbForce() {
        return cbForce;
    }

    public void setCbForce(boolean cbForce) {
        this.cbForce = cbForce;
    }

    public boolean isCbNoDeps() {
        return cbNoDeps;
    }

    public void setCbNoDeps(boolean cbNoDeps) {
        this.cbNoDeps = cbNoDeps;
    }

    public boolean isCbForceRemove() {
        return cbForceRemove;
    }

    public void setCbForceRemove(boolean cbForceRemove) {
        this.cbForceRemove = cbForceRemove;
    }

    public boolean isCbCombined() {
        return cbCombined;
    }

    public void setCbCombined(boolean cbCombined) {
        this.cbCombined = cbCombined;
    }

    public boolean isCbParallel() {
        return cbParallel;
    }

    public void setCbParallel(boolean cbParallel) {
        this.cbParallel = cbParallel;
    }

    private String tfBuild;

    public String getTfBuild() {
        return tfBuild;
    }

    public void setTfBuild(String tfBuild) {
        this.tfBuild = tfBuild;
    }

    private String tfCompiler;

    public String getTfCompiler() {
        return tfCompiler;
    }

    public void setTfCompiler(String tfCompiler) {
        this.tfCompiler = tfCompiler;
    }

    public String getTfConfig() {
        return tfConfig;
    }

    public void setTfConfig(String tfConfig) {
        this.tfConfig = tfConfig;
    }

    public String getTfArch() {
        return tfArch;
    }

    public void setTfArch(String tfArch) {
        this.tfArch = tfArch;
    }

    public String getTfDebug() {
        return tfDebug;
    }

    public void setTfDebug(String tfDebug) {
        this.tfDebug = tfDebug;
    }

    public int getBuildMode() {
        return buildMode;
    }

    public void setBuildMode(int buildMode) {
        this.buildMode = buildMode;
    }

    private String tfConfig;
    private String tfArch;
    private String tfDebug;
    private int buildMode = 0;

    private boolean cbForce = false;
    private boolean cbNoDeps = false;
    private boolean cbForceRemove = false;
    private boolean cbCombined = false;
    private boolean cbParallel = false;

    public boolean isCbRdmd() {
        return cbRdmd;
    }

    public void setCbRdmd(boolean cbRdmd) {
        this.cbRdmd = cbRdmd;
    }

    // Run tab

    public boolean isCbTempBuild() {
        return cbTempBuild;
    }

    public void setCbTempBuild(boolean cbTempBuild) {
        this.cbTempBuild = cbTempBuild;
    }

    public String getTfMainFile() {
        return tfMainFile;
    }

    public void setTfMainFile(String tfMainFile) {
        this.tfMainFile = tfMainFile;
    }

    public boolean isCbCoverage() {
        return cbCoverage;
    }

    public void setCbCoverage(boolean cbCoverage) {
        this.cbCoverage = cbCoverage;
    }

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


    public DLanguageRunDubConfiguration(String name, Project project, ConfigurationFactory factory) {
        super(name, new RunConfigurationModule(project), factory);

        Collection<Module> modules = this.getValidModules();
        if (!modules.isEmpty()) {
            //Get first valid module and use it
            this.setModule(modules.iterator().next());
        }
        envVars = new HashMap<String, String>();
    }

    @Override
    public Collection<Module> getValidModules() {
        Module[] modules = ModuleManager.getInstance(getProject()).getModules();
        final DMDRunner appRunner = new DMDRunner();

        ArrayList<Module> res = new ArrayList<Module>();
        for (Module module : modules) {
            if (appRunner.isValidModule(module)) {
                res.add(module);
            }
        }
        return res;
    }

    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new DLanguageRunDubConfigurationEditor();
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment env) throws ExecutionException {
        return new DLanguageRunDubState(env, this);
    }

    @Override
    public void writeExternal(@NotNull Element element) throws WriteExternalException {
        if (envVars == null) {
            envVars = new HashMap<String, String>();
        }

        super.writeExternal(element);
        writeModule(element);
        XmlSerializer.serializeInto(this, element);
    }

    @Override
    public void readExternal(@NotNull Element element) throws InvalidDataException {
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

    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }

    public boolean isQuiet() {
        return quiet;
    }

    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public String getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(String additionalParams) {
        this.additionalParams = additionalParams;
    }

    public Map<String, String> getEnvVars() {
        return envVars;
    }

    public void setEnvVars(Map<String, String> envVars) {
        this.envVars = envVars;
    }
}
