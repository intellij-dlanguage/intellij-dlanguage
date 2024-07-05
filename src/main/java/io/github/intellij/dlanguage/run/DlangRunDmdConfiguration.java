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

public class DlangRunDmdConfiguration extends ModuleBasedConfiguration<RunConfigurationModule, Module> {

    //Compiler settings properties
    private boolean betterC;
    private boolean release;
    private boolean debug;
    private boolean unitTest;
    private boolean link;
    private boolean coverageAnalysis;
    private boolean allowDeprecated;
    private boolean ignorePragmas;
    private boolean functionInlining;
    private boolean library;
    private boolean noArrayBoundsCheck;
    private boolean noFloatingPointReferences;
    private boolean optimize;
    private boolean enforcePropertySyntax;
    private boolean quiet;
    private boolean verbose;
    private boolean listThreadLocalStorage;
    private boolean warnings;
    private boolean infoWarnings;
    private String defaultLibrary;
    private String importsPath;
    private String stringImportsPath;
    private String linkerArgs;

    //Output settings properties
    private boolean generateDocs;
    private String docsFilename;
    private String docsPath;
    private String moduleDepsFilename;
    private boolean generateHeader;
    private String headerDir;
    private String headerFilename;
    private boolean generateMap;
    private boolean noObjectFiles;
    private boolean noStripPaths;
    private boolean generateJson;
    private String jsonFilename;

    //Debug settings properties
    private boolean addSymbolicDebugInfo;
    private boolean generateStandardStackFrame;
    private String symbolicLibrary;
    private boolean profile;


    public DlangRunDmdConfiguration(final String name, final Project project, final ConfigurationFactory factory) {
        super(name, new RunConfigurationModule(project), factory);

        final Collection<Module> modules = this.getValidModules();
        if (!modules.isEmpty()) {
            //Get first valid module and use it
            this.setModule(modules.iterator().next());
        }
    }

    @Override
    public Collection<Module> getValidModules() {
        final Module[] modules = ModuleManager.getInstance(getProject()).getModules();
        final DMDRunner appRunner = new DMDRunner();

        final ArrayList<Module> res = new ArrayList<>();
        for (final Module module : modules) {
            if (appRunner.isValidModule(module)) {
                res.add(module);
            }
        }
        return res;
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new DlangRunDmdConfigurationEditor();
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull final Executor executor, @NotNull final ExecutionEnvironment env) throws ExecutionException {
        return new DlangRunDmdState(env, this);
    }

    @Override
    public void writeExternal(@NotNull final Element element) throws WriteExternalException {
        super.writeExternal(element);
        XmlSerializer.serializeInto(this, element);
    }

    @Override
    public void readExternal(@NotNull final Element element) throws InvalidDataException {
        super.readExternal(element);
        readModule(element);
        XmlSerializer.deserializeInto(this, element);
    }

    public boolean isBetterC() {
        return this.betterC;
    }

    public void setBetterC(boolean betterC) {
        this.betterC = betterC;
    }

    public boolean isRelease() {
        return release;
    }

    public void setRelease(final boolean release) {
        this.release = release;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(final boolean debug) {
        this.debug = debug;
    }

    public boolean isUnitTest() {
        return unitTest;
    }

    public void setUnitTest(final boolean unitTest) {
        this.unitTest = unitTest;
    }

    public boolean isLink() {
        return link;
    }

    public void setLink(final boolean link) {
        this.link = link;
    }

    public boolean isCoverageAnalysis() {
        return coverageAnalysis;
    }

    public void setCoverageAnalysis(final boolean coverageAnalysis) {
        this.coverageAnalysis = coverageAnalysis;
    }

    public boolean isAllowDeprecated() {
        return allowDeprecated;
    }

    public void setAllowDeprecated(final boolean allowDeprecated) {
        this.allowDeprecated = allowDeprecated;
    }

    public boolean isIgnorePragmas() {
        return ignorePragmas;
    }

    public void setIgnorePragmas(final boolean ignorePragmas) {
        this.ignorePragmas = ignorePragmas;
    }

    public boolean isFunctionInlining() {
        return functionInlining;
    }

    public void setFunctionInlining(final boolean functionInlining) {
        this.functionInlining = functionInlining;
    }

    public boolean isLibrary() {
        return library;
    }

    public void setLibrary(final boolean library) {
        this.library = library;
    }

    public boolean isNoArrayBoundsCheck() {
        return noArrayBoundsCheck;
    }

    public void setNoArrayBoundsCheck(final boolean noArrayBoundsCheck) {
        this.noArrayBoundsCheck = noArrayBoundsCheck;
    }

    public boolean isNoFloatingPointReferences() {
        return noFloatingPointReferences;
    }

    public void setNoFloatingPointReferences(final boolean noFloatingPointReferences) {
        this.noFloatingPointReferences = noFloatingPointReferences;
    }

    public boolean isOptimize() {
        return optimize;
    }

    public void setOptimize(final boolean optimize) {
        this.optimize = optimize;
    }

    public boolean isEnforcePropertySyntax() {
        return enforcePropertySyntax;
    }

    public void setEnforcePropertySyntax(final boolean enforcePropertySyntax) {
        this.enforcePropertySyntax = enforcePropertySyntax;
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

    public boolean isListThreadLocalStorage() {
        return listThreadLocalStorage;
    }

    public void setListThreadLocalStorage(final boolean listThreadLocalStorage) {
        this.listThreadLocalStorage = listThreadLocalStorage;
    }

    public boolean isWarnings() {
        return warnings;
    }

    public void setWarnings(final boolean warnings) {
        this.warnings = warnings;
    }

    public boolean isInfoWarnings() {
        return infoWarnings;
    }

    public void setInfoWarnings(final boolean infoWarnings) {
        this.infoWarnings = infoWarnings;
    }

    public String getDefaultLibrary() {
        return defaultLibrary;
    }

    public void setDefaultLibrary(final String defaultLibrary) {
        this.defaultLibrary = defaultLibrary;
    }

    public String getImportsPath() {
        return importsPath;
    }

    public void setImportsPath(final String importsPath) {
        this.importsPath = importsPath;
    }

    public String getStringImportsPath() {
        return stringImportsPath;
    }

    public void setStringImportsPath(final String stringImportsPath) {
        this.stringImportsPath = stringImportsPath;
    }

    public String getLinkerArgs() {
        return linkerArgs;
    }

    public void setLinkerArgs(final String linkerArgs) {
        this.linkerArgs = linkerArgs;
    }

    public String getJsonFilename() {
        return jsonFilename;
    }

    public void setJsonFilename(final String jsonFilename) {
        this.jsonFilename = jsonFilename;
    }

    public boolean isGenerateJson() {
        return generateJson;
    }

    public void setGenerateJson(final boolean generateJson) {
        this.generateJson = generateJson;
    }

    public boolean isNoStripPaths() {
        return noStripPaths;
    }

    public void setNoStripPaths(final boolean noStripPaths) {
        this.noStripPaths = noStripPaths;
    }

    public boolean isNoObjectFiles() {
        return noObjectFiles;
    }

    public void setNoObjectFiles(final boolean noObjectFiles) {
        this.noObjectFiles = noObjectFiles;
    }

    public boolean isGenerateMap() {
        return generateMap;
    }

    public void setGenerateMap(final boolean generateMap) {
        this.generateMap = generateMap;
    }

    public String getHeaderFilename() {
        return headerFilename;
    }

    public void setHeaderFilename(final String headerFilename) {
        this.headerFilename = headerFilename;
    }

    public String getHeaderDir() {
        return headerDir;
    }

    public void setHeaderDir(final String headerDir) {
        this.headerDir = headerDir;
    }

    public boolean isGenerateHeader() {
        return generateHeader;
    }

    public void setGenerateHeader(final boolean generateHeader) {
        this.generateHeader = generateHeader;
    }

    public String getModuleDepsFilename() {
        return moduleDepsFilename;
    }

    public void setModuleDepsFilename(final String moduleDepsFilename) {
        this.moduleDepsFilename = moduleDepsFilename;
    }

    public String getDocsPath() {
        return docsPath;
    }

    public void setDocsPath(final String docsPath) {
        this.docsPath = docsPath;
    }

    public String getDocsFilename() {
        return docsFilename;
    }

    public void setDocsFilename(final String docsFilename) {
        this.docsFilename = docsFilename;
    }

    public boolean isGenerateDocs() {
        return generateDocs;
    }

    public void setGenerateDocs(final boolean generateDocs) {
        this.generateDocs = generateDocs;
    }

    public boolean isAddSymbolicDebugInfo() {
        return addSymbolicDebugInfo;
    }

    public void setAddSymbolicDebugInfo(final boolean addSymbolicDebugInfo) {
        this.addSymbolicDebugInfo = addSymbolicDebugInfo;
    }

    public boolean isGenerateStandardStackFrame() {
        return generateStandardStackFrame;
    }

    public void setGenerateStandardStackFrame(final boolean generateStandardStackFrame) {
        this.generateStandardStackFrame = generateStandardStackFrame;
    }

    public String getSymbolicLibrary() {
        return symbolicLibrary;
    }

    public void setSymbolicLibrary(final String symbolicLibrary) {
        this.symbolicLibrary = symbolicLibrary;
    }

    public boolean isProfile() {
        return profile;
    }

    public void setProfile(final boolean profile) {
        this.profile = profile;
    }
}
