package io.github.intellij.dlanguage.module;

import com.intellij.execution.BeforeRunTaskProvider;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ModuleBasedConfiguration;
import com.intellij.execution.impl.RunConfigurationBeforeRunProvider;
import com.intellij.execution.impl.RunManagerImpl;
import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectJdkForModuleStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.SdkTypeId;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.DlangBundle;
import io.github.intellij.dlanguage.DlangSdkType;
import io.github.intellij.dlanguage.run.DlangRunAppConfigurationType;
import io.github.intellij.dlanguage.run.DlangRunDmdConfigurationType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class DlangModuleBuilder extends ModuleBuilder {

    private static final Logger LOG = Logger.getInstance(DlangModuleBuilder.class);

    private static final String DLANG_GROUP_NAME = "D Language";
    private static final String RUN_CONFIG_NAME = "Run D App";
    private static final String COMPILE_CONFIG_NAME = "Compile with DMD";

    private final String myBuilderId;
    private final String myPresentableName;
    private final String myDescription;

    @Nullable
    private List<String> sourcePaths;

    public DlangModuleBuilder() {
        this("DLangDmdApp", DlangBundle.INSTANCE.message("module.title"), DlangBundle.INSTANCE.message("module.description"));
    }

    protected DlangModuleBuilder(final String builderId,
                                 final String presentableName,
                                 final String description) {
        myBuilderId = builderId;
        myPresentableName = presentableName;
        myDescription = description;
    }

    @Override
    public String getBuilderId() {
        return myBuilderId;
    }

    @Override
    public Icon getNodeIcon() {
        return DLanguage.Icons.FILE;
    }

    @Override
    public String getDescription() {
        return myDescription;
    }

    @Override
    public String getPresentableName() {
        return myPresentableName;
    }

    @Override
    public String getGroupName() {
        return DLANG_GROUP_NAME;
    }

    @Override
    public String getParentGroup() {
        return DLANG_GROUP_NAME;
    }

    @Override
    public @Nullable Project createProject(String name, String path) {
        LOG.debug(String.format("Creating Dlang project '%s' in '%s'", name, path));
        return super.createProject(name, path);
    }

    @Override
    public void setupRootModel(@NotNull final ModifiableRootModel rootModel) throws ConfigurationException {
        rootModel.setSdk(myJdk);
        rootModel.inheritSdk(); // all modules should use the same dmd as the project
        LOG.debug(String.format("Dlang project SDK set as: %s", myJdk));

        final ContentEntry contentEntry = doAddContentEntry(rootModel);
        if (contentEntry != null) {
            for (final String sourcePath : getSourcePaths()) {
                final VirtualFile sourceRoot = LocalFileSystem.getInstance()
                            .refreshAndFindFileByPath(FileUtil.toSystemIndependentName(sourcePath));

                if (sourceRoot != null) {
                    contentEntry.addSourceFolder(sourceRoot, false);
                }
            }
        }

        final Project project = rootModel.getProject();
        final RunManagerImpl runManager = RunManagerImpl.getInstanceImpl(project);

        //Create "Compile with DMD" configuration
        @Nullable RunnerAndConfigurationSettings runDmdSettings = runManager.findConfigurationByName(COMPILE_CONFIG_NAME);
        if (runDmdSettings == null) {
            final DlangRunDmdConfigurationType configurationType
                = ConfigurationType.CONFIGURATION_TYPE_EP.findExtensionOrFail(DlangRunDmdConfigurationType.class);
            final ConfigurationFactory factory = configurationType.getConfigurationFactories()[0];
            runDmdSettings = runManager.createConfiguration(COMPILE_CONFIG_NAME, factory);
            ((ModuleBasedConfiguration) runDmdSettings.getConfiguration()).setModule(rootModel.getModule());

            runDmdSettings.storeInLocalWorkspace();
            runManager.addConfiguration(runDmdSettings);
            LOG.debug(String.format("Run Configuration added for '%s'", COMPILE_CONFIG_NAME));
        }

        //Create "Run D App" configuration
        @Nullable RunnerAndConfigurationSettings runAppSettings = runManager.findConfigurationByName(RUN_CONFIG_NAME);
        if (runAppSettings == null) {
            final DlangRunAppConfigurationType configurationType
                = ConfigurationType.CONFIGURATION_TYPE_EP.findExtensionOrFail(DlangRunAppConfigurationType.class);
            final ConfigurationFactory factory = configurationType.getConfigurationFactories()[0];
            runAppSettings = runManager.createConfiguration(RUN_CONFIG_NAME, factory);
            ((ModuleBasedConfiguration) runAppSettings.getConfiguration()).setModule(rootModel.getModule());

            runAppSettings.storeInLocalWorkspace();
            runManager.addConfiguration(runAppSettings);
            LOG.debug(String.format("Run Configuration added for '%s'", RUN_CONFIG_NAME));
        }

        //Add dependency to exec "runDmdSettings" before running "runAppSettings".
        //XXX: next code doesn't add BeforeRunTask. I don't know why.
        @Nullable final BeforeRunTaskProvider<RunConfigurationBeforeRunProvider.RunConfigurableBeforeRunTask> provider =
            RunConfigurationBeforeRunProvider.getProvider(project, RunConfigurationBeforeRunProvider.ID);

        if(provider != null) {
            final RunConfigurationBeforeRunProvider.RunConfigurableBeforeRunTask runDmdTask = provider.createTask(runDmdSettings.getConfiguration());

            final List<RunConfigurationBeforeRunProvider.RunConfigurableBeforeRunTask> beforeRunTasks = Collections.singletonList(runDmdTask);

            runManager.setBeforeRunTasks(runAppSettings.getConfiguration(), beforeRunTasks);
        }

        LOG.debug("Dlang project root configured");
    }

    /* By default, sources are located in {WORKING_DIR}/source folder. */
    @Nullable
    public List<String> getSourcePaths() {
        if (sourcePaths == null || sourcePaths.isEmpty()) {
            @NonNls final String path = getContentEntryPath() + File.separator + "source";

            if(createSourceDirIfNotExists(path)) {
                sourcePaths = Collections.singletonList(path);
            }
        }
        return sourcePaths;
    }

    @Override
    public boolean isSuitableSdkType(final SdkTypeId sdkType) {
        return sdkType == DlangSdkType.getInstance();
    }

    @Override
    public ModuleType<DlangModuleBuilder> getModuleType() {
        return DlangModuleType.getInstance();
    }

    @Nullable
    @Override
    public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        // todo: instead of using ProjectJdkForModuleStep, create a new ModuleWizardStep specific to D
        //  in which we can configure a compiler and optionally setup dub (so will no longer need DlangDubModuleBuilder).
        //  instead of the following code there would simply be one line, something like: return new DlangCompilerWizardStep(context, this);

        final DlangModuleBuilder moduleBuilder = this;
        return new ProjectJdkForModuleStep(context, DlangSdkType.getInstance()) {
            public void updateDataModel() {
                super.updateDataModel();
                moduleBuilder.setModuleJdk(getJdk());
            }
        };
    }

    private boolean createSourceDirIfNotExists(@NonNls final String path) {
        final File sourceDir = new File(path);

        if(sourceDir.exists()) {
            return true;
        } else {
            if(FileUtil.createDirectory(sourceDir)) {
                LOG.info("Create source folder: " + path);
                return true;
            } else {
                LOG.warn("Failed to create source folder: " + path);
                return false;
            }
        }
    }
}
