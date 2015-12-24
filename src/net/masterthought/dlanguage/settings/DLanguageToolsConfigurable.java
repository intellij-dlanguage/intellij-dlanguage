package net.masterthought.dlanguage.settings;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.RawCommandLineEditor;
import com.intellij.ui.TextAccessor;
import com.intellij.util.messages.Topic;
import net.masterthought.dlanguage.utils.ExecUtil;
import net.masterthought.dlanguage.utils.GuiUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * The "D Tools" option in Preferences->Project Settings.
 */
public class DLanguageToolsConfigurable implements SearchableConfigurable {
    public static final String D_TOOLS_ID = "D Tools";

    private PropertiesComponent propertiesComponent;

    // Swing components.
    private JPanel mainPanel;
    private TextFieldWithBrowseButton dubPath;
    private RawCommandLineEditor dubFlags;
    private JButton dubAutoFind;
    private JTextField dubVersion;
    private TextFieldWithBrowseButton dscannerPath;
    private RawCommandLineEditor dscannerFlags;
    private JButton dscannerAutoFind;
    private JTextField dscannerVersion;
    private TextFieldWithBrowseButton dcdPath;
    private RawCommandLineEditor dcdFlags;
    private JButton dcdAutoFind;
    private JTextField dcdVersion;
    private TextFieldWithBrowseButton dcdClientPath;
    private RawCommandLineEditor dcdClientFlags;
    private JButton dcdClientAutoFind;
    private JTextField dcdClientVersion;

    private List<Tool> properties;

    public DLanguageToolsConfigurable(@NotNull Project project) {
        this.propertiesComponent = PropertiesComponent.getInstance(project);
        properties = Arrays.asList(
                new Tool(project, "dub", ToolKey.DUB_KEY, dubPath, dubFlags,
                        dubAutoFind, dubVersion),
                new Tool(project, "dscanner", ToolKey.DSCANNER_KEY, dscannerPath, dscannerFlags,
                        dscannerAutoFind, dscannerVersion),
                new Tool(project, "dcd-server", ToolKey.DCD_SERVER_KEY, dcdPath, dcdFlags,
                        dcdAutoFind, dcdVersion, "--version",SettingsChangeNotifier.DCD_TOPIC),
                new Tool(project, "dcd-client", ToolKey.DCD_CLIENT_KEY, dcdClientPath, dcdClientFlags,
                        dcdClientAutoFind, dcdClientVersion)
        );
    }

    interface Property {
        public boolean isModified();

        public void saveState();

        public void restoreState();
    }

    interface Versioned {
        public void updateVersion();
    }

    /**
     * Manages the state of a PropertyComponent and its respective field.
     */
    class PropertyField implements Property {
        public String oldValue;
        public String propertyKey;
        public final TextAccessor field;

        PropertyField(@NotNull String propertyKey, @NotNull TextAccessor field) {
            this(propertyKey, field, "");
        }

        PropertyField(@NotNull String propertyKey, @NotNull TextAccessor field, @NotNull String defaultValue) {
            this.propertyKey = propertyKey;
            this.field = field;
            this.oldValue = propertiesComponent.getValue(propertyKey, defaultValue);
            field.setText(oldValue);
        }

        public boolean isModified() {
            return !field.getText().equals(oldValue);
        }

        public void saveState() {
            propertiesComponent.setValue(propertyKey, oldValue = field.getText());
        }

        public void restoreState() {
            field.setText(oldValue);
        }
    }

    /**
     * Manages the group of fields which reside to a particular tool.
     */
    class Tool implements Property, Versioned {
        public final Project project;
        public final String command;
        public final ToolKey key;
        public final TextFieldWithBrowseButton pathField;
        public final RawCommandLineEditor flagsField;
        public final JTextField versionField;
        public final String versionParam;
        public final JButton autoFindButton;
        public final List<PropertyField> propertyFields;
        public final
        @Nullable
        Topic<SettingsChangeNotifier> topic;
        private final
        @Nullable
        SettingsChangeNotifier publisher;

        Tool(Project project, String command, ToolKey key, TextFieldWithBrowseButton pathField,
             RawCommandLineEditor flagsField, JButton autoFindButton, JTextField versionField) {
            this(project, command, key, pathField, flagsField, autoFindButton, versionField, "--version");
        }

        Tool(Project project, String command, ToolKey key, TextFieldWithBrowseButton pathField,
             RawCommandLineEditor flagsField, JButton autoFindButton, JTextField versionField, String versionParam) {
            this(project, command, key, pathField, flagsField, autoFindButton, versionField, versionParam, null);
        }

        Tool(Project project, String command, ToolKey key, TextFieldWithBrowseButton pathField,
             RawCommandLineEditor flagsField, JButton autoFindButton, JTextField versionField, String versionParam,
             @Nullable Topic<SettingsChangeNotifier> topic) {
            this.project = project;
            this.command = command;
            this.key = key;
            this.pathField = pathField;
            this.flagsField = flagsField;
            this.versionField = versionField;
            this.versionParam = versionParam;
            this.autoFindButton = autoFindButton;
            this.topic = topic;
            this.publisher = topic == null ? null : project.getMessageBus().syncPublisher(topic);

            this.propertyFields = Arrays.asList(
                    new PropertyField(key.pathKey, pathField),
                    new PropertyField(key.flagsKey, flagsField));

            GuiUtil.addFolderListener(pathField, command);
            GuiUtil.addApplyPathAction(autoFindButton, pathField, command);
            updateVersion();
        }

        public void updateVersion() {
            String pathText = pathField.getText();
            if (pathText.isEmpty()) {
                versionField.setText("");
            } else {
                versionField.setText(getVersion(pathText, versionParam));
            }
        }

        public boolean isModified() {
            for (PropertyField propertyField : propertyFields) {
                if (propertyField.isModified()) {
                    return true;
                }
            }
            return false;
        }

        public void saveState() {
            if (isModified() && publisher != null) {
                publisher.onSettingsChanged(new ToolSettings(pathField.getText(), flagsField.getText()));
            }
            for (PropertyField propertyField : propertyFields) {
                propertyField.saveState();
            }
        }

        public void restoreState() {
            for (PropertyField propertyField : propertyFields) {
                propertyField.restoreState();
            }
        }
    }

    @NotNull
    @Override
    public String getId() {
        return D_TOOLS_ID;
    }

    @Nullable
    @Override
    public Runnable enableSearch(String s) {
        // TODO
        return null;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return D_TOOLS_ID;
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return mainPanel;
    }

    /**
     * Enables the apply button if anything changed.
     */
    @Override
    public boolean isModified() {
        for (Property property : properties) {
            if (property.isModified()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Triggered when the user pushes the apply button.
     */
    @Override
    public void apply() throws ConfigurationException {
        updateVersionInfoFields();
        saveState();
    }

    /**
     * Triggered when the user pushes the cancel button.
     */
    @Override
    public void reset() {
        restoreState();
    }

    @Override
    public void disposeUIResources() {

    }

    /**
     * Heuristically finds the version number. Current implementation is the
     * identity function since cabal plays nice.
     */
    private static String getVersion(String cmd, String versionFlag) {
        return ExecUtil.readCommandLine(null, cmd, versionFlag);
    }

    /**
     * Updates the version info fields for all files configured.
     */
    private void updateVersionInfoFields() {
        for (Property property : properties) {
            if (property instanceof Versioned) {
                ((Versioned) property).updateVersion();
            }
        }
    }

    /**
     * Persistent save of the current state.
     */
    private void saveState() {
        for (Property property : properties) {
            property.saveState();
        }
    }

    /**
     * Restore components to the initial state.
     */
    private void restoreState() {
        for (Property property : properties) {
            property.restoreState();
        }
    }
}