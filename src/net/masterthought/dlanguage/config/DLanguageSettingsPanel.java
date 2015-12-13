package net.masterthought.dlanguage.config;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import net.masterthought.dlanguage.DLanguageBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/* UI Panel with DLang plugin settings.
 * See "DLangSettingsPanel.form" for details.
 * It has a single edit field for DLangGeneralSettings.state.dubExecutablePath
 */
public class DLanguageSettingsPanel implements SearchableConfigurable {
    private final DLanguageGeneralSettings settings;
    private final Project project;
    private JPanel panel;
    private TextFieldWithBrowseButton pathDubExecutable;

    public DLanguageSettingsPanel(@NotNull Project project, @NotNull DLanguageGeneralSettings settings) {
        this.settings = settings;
        this.project = project;
    }

    @NotNull
    @Override
    public String getId() {
        return DLanguageSettingsPanel.class.getCanonicalName();
    }

    @Nullable
    @Override
    public Runnable enableSearch(String s) {
        return null;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return DLanguageBundle.message("settings.label");
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        FileChooserDescriptor fcd = FileChooserDescriptorFactory.createSingleFileOrExecutableAppDescriptor();
        fcd.setShowFileSystemRoots(true);
        fcd.setTitle(DLanguageBundle.message("dub.config.selectexecutable.title"));
        fcd.setDescription(DLanguageBundle.message("dub.config.selectexecutable.description"));
        fcd.setHideIgnored(false);

        pathDubExecutable.addBrowseFolderListener(null,
                new TextFieldWithBrowseButton.BrowseFolderActionListener<JTextField>(fcd.getTitle(), fcd.getDescription(),
                        pathDubExecutable, null, fcd, TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT) );

        return panel;
    }

    @Override
    public boolean isModified() {
        DLanguageGeneralSettings formData = new DLanguageGeneralSettings();
        readForm(formData);
        return !formData.equals(settings);
    }

    @Override
    public void apply() throws ConfigurationException {
        readForm(settings);
    }

    @Override
    public void reset() {
        updateForm(settings);
    }

    @Override
    public void disposeUIResources() {
    }

    private void readForm(DLanguageGeneralSettings settings) {
        settings.setDubExecutablePath(pathDubExecutable.getText());
    }

    private void updateForm(DLanguageGeneralSettings settings) {
        pathDubExecutable.setText(settings.getDubExecutablePath());
    }
}
