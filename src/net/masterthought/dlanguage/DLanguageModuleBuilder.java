package net.masterthought.dlanguage;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.ide.util.projectWizard.*;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.projectRoots.SdkTypeId;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.text.StringUtil;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class DLanguageModuleBuilder extends JavaModuleBuilder implements SourcePathsBuilder, ModuleBuilderListener {

    @Override
    public void setupRootModel(ModifiableRootModel rootModel) throws ConfigurationException {
        addListener(this);
        super.setupRootModel(rootModel);
    }

    /**
     * Returns the D Language module type.
     */
    @Override
    public ModuleType getModuleType() {
        return DLanguageModuleType.getInstance();
    }

    /**
     * Ensures that SDK type is a D Language SDK.
     */
    @Override
    public boolean isSuitableSdkType(SdkTypeId sdkType) {
        return sdkType == DLanguageSdkType.getInstance();
    }

    /**
     * Called after module is created.
     */
    @Override
    public void moduleCreated(@NotNull Module module) {
        // create the dub project
        createDub(module.getProject().getBaseDir().getCanonicalPath());

        // Update the ignored files and folders to avoid file search showing compiled files.
        FileTypeManager fileTypeManager = FileTypeManager.getInstance();
        StringBuilder builder = new StringBuilder(fileTypeManager.getIgnoredFilesList());
        // Ensure the ignored file list ends with a semicolon.
        if (builder.charAt(builder.length() - 1) != ';') {
            builder.append(';');
        }
        for (String ignore : new String[]{"*.dyn_hi", "*.dyn_hi", "*.dyn_o", "*.hi", "*.o"}) {
            if (builder.indexOf(';' + ignore + ';') == -1) {
                builder.append(ignore).append(';');
            }
        }
        fileTypeManager.setIgnoredFilesList(builder.toString());
    }


    /**
     * Hook into the new project creation and set dist to the compiler output
     * directory.
     */
    @Override
    @Nullable
    public ModuleWizardStep modifySettingsStep(@NotNull SettingsStep settingsStep) {
        if (settingsStep.getContext().isCreatingNewProject() &&
                settingsStep.getContext().isProjectFileDirectorySet()) {
            WizardContext c = settingsStep.getContext();
            String path = c.getProjectFileDirectory();
            String out = StringUtil.endsWithChar(path, '/') ? path + "dist" : path + "/dist";
            c.setCompilerOutputDirectory(out);
        }
        return super.modifySettingsStep(settingsStep);
    }


    private void createDub(String workingDirectory) {
        GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setWorkDirectory(workingDirectory);
        commandLine.setExePath("dub");
        ParametersList parametersList = commandLine.getParametersList();
        parametersList.addParametersString("init");
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

            if (builder.toString().startsWith("Successfully created an empty project")) {
                // dub init creates a source folder with a starter d file inside - delete the original src folder that
                // intellij creates and rename source to src.
                File sourceDir = new File(workingDirectory + "/source");
                File srcDir = new File(workingDirectory + "/src");

                if (sourceDir.exists() && srcDir.exists()) {
                    srcDir.delete();
                    sourceDir.renameTo(srcDir);
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}

