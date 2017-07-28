package net.masterthought.dlanguage.unittest;

import com.intellij.execution.ExecutionBundle;
import com.intellij.execution.configuration.EnvironmentVariablesComponent;
import com.intellij.ide.util.TreeFileChooser;
import com.intellij.ide.util.TreeFileChooserFactory;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import net.masterthought.dlanguage.DLanguageBundle;
import net.masterthought.dlanguage.DLanguageFileType;
import net.masterthought.dlanguage.DLanguageWritingAccessProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Map;

public class DUnitTestRunConfigurationEditor extends SettingsEditor<DUnitTestRunConfiguration> {
    private JPanel myMainPanel;
    private JLabel myFileLabel;
    private TextFieldWithBrowseButton myFileField;
    private TextFieldWithBrowseButton myWorkingDirectory;
    private EnvironmentVariablesComponent envVariables;

    public DUnitTestRunConfigurationEditor(final Project project) {
        initDFileTextWithBrowse(project, myFileField);

        myWorkingDirectory.addBrowseFolderListener(ExecutionBundle.message("select.working.directory.message"), null, project,
            FileChooserDescriptorFactory.createSingleFolderDescriptor());

    }

    public static void initDFileTextWithBrowse(final @NotNull Project project,
                                               final @NotNull TextFieldWithBrowseButton textWithBrowse) {
        textWithBrowse.getButton().addActionListener(event -> {
            final String initialPath = FileUtil.toSystemIndependentName(textWithBrowse.getText().trim());
            final VirtualFile initialFile = initialPath.isEmpty() ? null : LocalFileSystem.getInstance().findFileByPath(initialPath);
            final PsiFile initialPsiFile = initialFile == null ? null : PsiManager.getInstance(project).findFile(initialFile);

            final TreeFileChooser fileChooser = TreeFileChooserFactory.getInstance(project).createFileChooser(
                DLanguageBundle.INSTANCE.message("choose.dlanguage.main.file"),
                initialPsiFile,
                DLanguageFileType.INSTANCE,
                file -> !DLanguageWritingAccessProvider.isInDLanguageSdkOrDLanguagePackagesFolder(file)
            );

            fileChooser.showDialog();

            final PsiFile selectedFile = fileChooser.getSelectedFile();
            final VirtualFile virtualFile = selectedFile == null ? null : selectedFile.getVirtualFile();
            if (virtualFile != null) {
                final String path = FileUtil.toSystemDependentName(virtualFile.getPath());
                textWithBrowse.setText(path);
            }
        });
    }

    @Override
    protected void resetEditorFrom(@NotNull final DUnitTestRunConfiguration config) {
        myWorkingDirectory.setText(config.getWorkingDir());
        myFileField.setText(config.getdFilePath());

        final Map<String, String> envVars = config.getEnvVars();
        if (envVars != null) {
            envVariables.setEnvs(config.getEnvVars());
        }
    }

    @Override
    protected void applyEditorTo(@NotNull final DUnitTestRunConfiguration config) throws ConfigurationException {
        config.setEnvVars(envVariables.getEnvs());
        config.setWorkingDir(myWorkingDirectory.getText());
        config.setdFilePath(FileUtil.toSystemIndependentName(myFileField.getText().trim()));
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return myMainPanel;
    }
}




