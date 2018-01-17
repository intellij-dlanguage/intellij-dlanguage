package io.github.intellij.dlanguage.settings;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.RawCommandLineEditor;
import com.intellij.ui.TextAccessor;
import com.intellij.util.messages.Topic;
import io.github.intellij.dlanguage.utils.ExecUtil;
import io.github.intellij.dlanguage.utils.GuiUtil;
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

    public static boolean USE_NATIVE_CODE_COMPLETION = false;

    private static final Logger LOG = Logger.getInstance(DLanguageToolsConfigurable.class);
    private final PropertiesComponent propertiesComponent;
    private final List<Tool> properties;
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
    private TextFieldWithBrowseButton dFormatPath;
    private RawCommandLineEditor dFormatFlags;
    private JButton dFormatAutoFind;
    private JTextField dFormatVersion;
    private TextFieldWithBrowseButton dFixPath;
    private RawCommandLineEditor dFixFlags;
    private JButton dFixAutoFind;
    private JTextField dFixVersion;
    private TextFieldWithBrowseButton GDBPath;
    private JButton GDBAutoFind;
    private RawCommandLineEditor GDBFlags;
    private JTextField GDBVersion;
    private JTabbedPane tabbedPane1;
    private JCheckBox chkNativeCodeCompletion;

    public DLanguageToolsConfigurable(@NotNull final Project project) {
        this.propertiesComponent = PropertiesComponent.getInstance();
        properties = Arrays.asList(
            new Tool(project, "dub", ToolKey.DUB_KEY, dubPath, dubFlags,
                dubAutoFind, dubVersion),
            new Tool(project, "dscanner", ToolKey.DSCANNER_KEY, dscannerPath, dscannerFlags,
                dscannerAutoFind, dscannerVersion),
            new Tool(project, "dcd-server", ToolKey.DCD_SERVER_KEY, dcdPath, dcdFlags,
                dcdAutoFind, dcdVersion, "--version", SettingsChangeNotifier.DCD_TOPIC),
            new Tool(project, "dcd-client", ToolKey.DCD_CLIENT_KEY, dcdClientPath, dcdClientFlags,
                dcdClientAutoFind, dcdClientVersion),
            new Tool(project, "dfmt", ToolKey.DFORMAT_KEY, dFormatPath, dFormatFlags,
                dFormatAutoFind, dFormatVersion),
            new Tool(project, "dfix", ToolKey.DFIX_KEY, dFixPath, dFixFlags,
                dFixAutoFind, dFixVersion),
            new Tool(project, "gdb", ToolKey.GDB_KEY, GDBPath, GDBFlags,
                GDBAutoFind, GDBVersion)
        );
    }

    /**
     * Heuristically finds the version number. Current implementation is the
     * identity function since cabal plays nice.
     */
    public static String getVersion(final String cmd, final String versionFlag) {
        final @Nullable String versionOutput = ExecUtil.readCommandLine(null, cmd, versionFlag);

        if(StringUtil.isNotEmpty(versionOutput)) {
            final String version = versionOutput.split("\n")[0].trim();
            LOG.info(String.format("%s [%s]", cmd, version));
            return version;
        }
        return "";
    }

    @NotNull
    @Override
    public String getId() {
        return D_TOOLS_ID;
    }

    @Nullable
    @Override
    public Runnable enableSearch(final String s) {
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
        for (final Property property : properties) {
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
     * Updates the version info fields for all files configured.
     */
    private void updateVersionInfoFields() {
        for (final Property property : properties) {
            if (property instanceof Versioned) {
                ((Versioned) property).updateVersion();
            }
        }
    }

    /**
     * Persistent save of the current state.
     */
    private void saveState() {
        LOG.info("Saving D Tools Config");
        for (final Property property : properties) {
            property.saveState();
        }
        propertiesComponent.setValue("USE_NATIVE_CODE_COMPLETION", chkNativeCodeCompletion.isSelected());
    }

    /**
     * Restore components to the initial state.
     */
    private void restoreState() {
        LOG.info("Restore D Tools Config");
        for (final Property property : properties) {
            property.restoreState();
        }
    }

    interface Property {
        boolean isModified();

        void saveState();

        void restoreState();
    }

    interface Versioned {
        void updateVersion();
    }

    /**
     * Manages the state of a PropertyComponent and its respective field.
     */
    class PropertyField implements Property {
        public final TextAccessor field;
        public final String propertyKey;
        public String oldValue;

        PropertyField(@NotNull final String propertyKey, @NotNull final TextAccessor field) {
            this(propertyKey, field, "");
        }

        PropertyField(@NotNull final String propertyKey, @NotNull final TextAccessor field, @NotNull final String defaultValue) {
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

        Tool(final Project project, final String command, final ToolKey key, final TextFieldWithBrowseButton pathField,
             final RawCommandLineEditor flagsField, final JButton autoFindButton, final JTextField versionField) {
            this(project, command, key, pathField, flagsField, autoFindButton, versionField, "--version");
        }

        Tool(final Project project, final String command, final ToolKey key, final TextFieldWithBrowseButton pathField,
             final RawCommandLineEditor flagsField, final JButton autoFindButton, final JTextField versionField, final String versionParam) {
            this(project, command, key, pathField, flagsField, autoFindButton, versionField, versionParam, null);
        }

        Tool(final Project project, final String command, final ToolKey key, final TextFieldWithBrowseButton pathField,
             final RawCommandLineEditor flagsField, final JButton autoFindButton, final JTextField versionField, final String versionParam,
             @Nullable final Topic<SettingsChangeNotifier> topic) {
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
            final String pathText = pathField.getText();
            final String version = StringUtil.isEmpty(pathText) ? "" : getVersion(pathText, versionParam);
            versionField.setText(version);
        }

        public boolean isModified() {
            return propertyFields.parallelStream().anyMatch(PropertyField::isModified);
        }

        public void saveState() {
            if (isModified() && publisher != null) {
                publisher.onSettingsChanged(new ToolSettings(pathField.getText(), flagsField.getText()));
            }
            for (final PropertyField propertyField : propertyFields) {
                propertyField.saveState();
            }
        }

        public void restoreState() {
            for (final PropertyField propertyField : propertyFields) {
                propertyField.restoreState();
            }
        }
    }
}
