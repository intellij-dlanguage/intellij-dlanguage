package net.masterthought.dlanguage.run;

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
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.ui.RawCommandLineEditor;
import com.intellij.ui.components.JBCheckBox;
import net.masterthought.dlanguage.DLanguageBundle;
import net.masterthought.dlanguage.DLanguageFileType;
import net.masterthought.dlanguage.DLanguageWritingAccessProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DLanguageApplicationRunConfigurationEditorForm extends SettingsEditor<DLanguageApplicationRunConfiguration> {
    private JPanel myMainPanel;
    private JLabel myFileLabel;
    private TextFieldWithBrowseButton myFileField;
    private RawCommandLineEditor myVMOptions;
    private JBCheckBox myRunDmdModeCheckBox;
    private RawCommandLineEditor myArguments;
    private TextFieldWithBrowseButton myWorkingDirectory;
    private EnvironmentVariablesComponent myEnvironmentVariables;

    public DLanguageApplicationRunConfigurationEditorForm(final Project project) {
        initDartFileTextWithBrowse(project, myFileField);

        myWorkingDirectory.addBrowseFolderListener(ExecutionBundle.message("select.working.directory.message"), null, project,
                FileChooserDescriptorFactory.createSingleFolderDescriptor());

        myVMOptions.setDialogCaption(DLanguageBundle.message("config.vmoptions.caption"));
        myArguments.setDialogCaption(DLanguageBundle.message("config.progargs.caption"));

        // 'Environment variables' is the widest label, anchored by myFileLabel
        myFileLabel.setPreferredSize(myEnvironmentVariables.getLabel().getPreferredSize());
        myEnvironmentVariables.setAnchor(myFileLabel);
    }

    public static void initDartFileTextWithBrowse(final @NotNull Project project,
                                                  final @NotNull TextFieldWithBrowseButton textWithBrowse) {
        textWithBrowse.getButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final String initialPath = FileUtil.toSystemIndependentName(textWithBrowse.getText().trim());
                final VirtualFile initialFile = initialPath.isEmpty() ? null : LocalFileSystem.getInstance().findFileByPath(initialPath);
                final PsiFile initialPsiFile = initialFile == null ? null : PsiManager.getInstance(project).findFile(initialFile);

                TreeFileChooser fileChooser = TreeFileChooserFactory.getInstance(project).createFileChooser(
                        DLanguageBundle.message("choose.dlanguage.main.file"),
                        initialPsiFile,
                        DLanguageFileType.INSTANCE,
                        new TreeFileChooser.PsiFileFilter() {
                            public boolean accept(PsiFile file) {
                                return !DLanguageWritingAccessProvider.isInDLanguageSdkOrDLanguagePackagesFolder(file);
                            }
                        }
                );

                fileChooser.showDialog();

                final PsiFile selectedFile = fileChooser.getSelectedFile();
                final VirtualFile virtualFile = selectedFile == null ? null : selectedFile.getVirtualFile();
                if (virtualFile != null) {
                    final String path = FileUtil.toSystemDependentName(virtualFile.getPath());
                    textWithBrowse.setText(path);
                }
            }
        });
    }

    @Override
    protected void resetEditorFrom(final DLanguageApplicationRunConfiguration configuration) {
        final DLanguageCommandLineRunnerParameters parameters = configuration.getRunnerParameters();

        myFileField.setText(FileUtil.toSystemDependentName(StringUtil.notNullize(parameters.getFilePath())));
        myArguments.setText(StringUtil.notNullize(parameters.getArguments()));
        myVMOptions.setText(StringUtil.notNullize(parameters.getVMOptions()));
        myRunDmdModeCheckBox.setSelected(parameters.isRunDmdMode());
        myWorkingDirectory.setText(FileUtil.toSystemDependentName(StringUtil.notNullize(parameters.getWorkingDirectory())));
        myEnvironmentVariables.setEnvs(parameters.getEnvs());
        myEnvironmentVariables.setPassParentEnvs(parameters.isIncludeParentEnvs());
    }

    @Override
    protected void applyEditorTo(final DLanguageApplicationRunConfiguration configuration) throws ConfigurationException {
        final DLanguageCommandLineRunnerParameters parameters = configuration.getRunnerParameters();

        parameters.setFilePath(StringUtil.nullize(FileUtil.toSystemIndependentName(myFileField.getText().trim()), true));
        parameters.setArguments(StringUtil.nullize(myArguments.getText(), true));
        parameters.setVMOptions(StringUtil.nullize(myVMOptions.getText(), true));
        parameters.setRunDmdMode(myRunDmdModeCheckBox.isSelected());
        parameters.setWorkingDirectory(StringUtil.nullize(FileUtil.toSystemIndependentName(myWorkingDirectory.getText().trim()), true));
        parameters.setEnvs(myEnvironmentVariables.getEnvs());
        parameters.setIncludeParentEnvs(myEnvironmentVariables.isPassParentEnvs());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return myMainPanel;
    }
}




