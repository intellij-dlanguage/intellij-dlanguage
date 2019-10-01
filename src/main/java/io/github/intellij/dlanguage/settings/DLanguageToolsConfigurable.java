package io.github.intellij.dlanguage.settings;

import com.intellij.ide.ui.search.SearchableOptionsRegistrar;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.RawCommandLineEditor;
import com.intellij.ui.TextAccessor;
import com.intellij.util.messages.Topic;
import io.github.intellij.dlanguage.messagebus.ToolChangeListener;
import io.github.intellij.dlanguage.messagebus.Topics;
import io.github.intellij.dlanguage.tools.DtoolUtils;
import io.github.intellij.dlanguage.utils.ExecUtil;
import io.github.intellij.dlanguage.utils.GuiUtil;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.tools.DtoolUtils.*;

/**
 * The "D Tools" option in: Settings -> Languages & Frameworks -> D Tools.
 */
public class DLanguageToolsConfigurable implements SearchableConfigurable {

    public static final String D_TOOLS_ID = "D Tools";
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
    private JCheckBox disableFormatterSyntaxErrorCheckBox;

    public DLanguageToolsConfigurable(@NotNull final Project project) {
        this.propertiesComponent = PropertiesComponent.getInstance();

        final SearchableOptionsRegistrar sor = SearchableOptionsRegistrar.getInstance();
        sor.addOption("dub", null, "dub", D_TOOLS_ID, D_TOOLS_ID);
        sor.addOption("dscanner", null, "dscanner", D_TOOLS_ID, D_TOOLS_ID);
        sor.addOption("dcd", null, "dcd", D_TOOLS_ID, D_TOOLS_ID);
        sor.addOption("dfmt", null, "dfmt", D_TOOLS_ID, D_TOOLS_ID);
        sor.addOption("dfix", null, "dfix", D_TOOLS_ID, D_TOOLS_ID);
        sor.addOption("gdb", null, "gdb", D_TOOLS_ID, D_TOOLS_ID);

        properties = Arrays.asList(
            new Tool(project, "dub", ToolKey.DUB_KEY, dubPath, dubFlags,
                dubAutoFind, dubVersion, DUB_LATEST, Topics.DUB_TOOL_CHANGE),
            new Tool(project, "dscanner", ToolKey.DSCANNER_KEY, dscannerPath, dscannerFlags,
                dscannerAutoFind, dscannerVersion, DSCANNER_LATEST, Topics.DSCANNER_TOOL_CHANGE),
            new Tool(project, "dcd-server", ToolKey.DCD_SERVER_KEY, dcdPath, dcdFlags,
                dcdAutoFind, dcdVersion, DCD_LATEST, Topics.DCD_SERVER_TOOL_CHANGE),
            new Tool(project, "dcd-client", ToolKey.DCD_CLIENT_KEY, dcdClientPath, dcdClientFlags,
                dcdClientAutoFind, dcdClientVersion, DCD_LATEST, Topics.DCD_CLIENT_TOOL_CHANGE),
            new Tool(project, "dfmt", ToolKey.DFORMAT_KEY, dFormatPath, dFormatFlags,
                dFormatAutoFind, dFormatVersion, DFORMAT_LATEST, Topics.DFMT_TOOL_CHANGE),
            new Tool(project, "dfix", ToolKey.DFIX_KEY, dFixPath, dFixFlags,
                dFixAutoFind, dFixVersion, DFIX_LATEST, Topics.DFIX_TOOL_CHANGE),
            new Tool(project, "gdb", ToolKey.GDB_KEY, GDBPath, GDBFlags,
                GDBAutoFind, GDBVersion, null, Topics.GDB_TOOL_CHANGE)
        );
    }

    /**
     * Heuristically finds the version number. Current implementation is the identity function since
     * cabal plays nice.
     */
    public static String getVersion(final String cmd) {
        final @Nullable String versionOutput = ExecUtil.readCommandLine(null, cmd, "--version");

        if (StringUtil.isNotEmpty(versionOutput)) {
            final String version = versionOutput.split("\n")[0].trim();
            LOG.debug(String.format("%s [%s]", cmd, version));
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
    public Runnable enableSearch(final String option) {
        return () -> {
            if(StringUtil.isNotEmpty(option)) {
                final int tabCount = tabbedPane1.getTabCount();

                for (int i = 0; i < tabCount-1; i++) {
                    final String title = tabbedPane1.getTitleAt(i);
                    if(title.toLowerCase().contains(option.toLowerCase())) {
                        tabbedPane1.setSelectedIndex(i);
                    }
                }
            }

        };
    }

    @Nls
    @Override
    public String getDisplayName() {
        return D_TOOLS_ID;
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
        for (final Tool t : properties) {
            if (t.isModified()) {
                t.setDirty(true);

                if(StringUtil.isNotEmpty(t.pathField.getText()) && !StringUtil.containsIgnoreCase(t.pathField.getText(), t.command)) {
                    t.versionField.setText(String.format("Not a valid %s binary", t.command));
                    t.versionField.setDisabledTextColor(UIManager.getColor("Focus.color"));
                } else {
                    t.updateVersion();
                    return true;
                }
            } else if (t.isDirty()) {
                t.updateVersion();
                t.setDirty(false);
            }
        }
        return
            propertiesComponent.getBoolean("USE_NATIVE_CODE_COMPLETION") != chkNativeCodeCompletion
                .isSelected()
                || propertiesComponent.getBoolean("DISABLE_SYNTAX_ERROR_FORMATTER_WARNING")
                != disableFormatterSyntaxErrorCheckBox.isSelected();
    }

    /**
     * Triggered when the user pushes the apply button.
     */
    @Override
    public void apply() {
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
        properties.forEach(Tool::updateVersion);
    }

    /**
     * Persistent save of the current state.
     */
    private void saveState() {
        LOG.info("Saving D Tools Config");
        for (final Property property : properties) {
            property.saveState();
        }
        propertiesComponent
            .setValue("USE_NATIVE_CODE_COMPLETION", chkNativeCodeCompletion.isSelected());
        propertiesComponent.setValue("DISABLE_SYNTAX_ERROR_FORMATTER_WARNING",
            disableFormatterSyntaxErrorCheckBox.isSelected());
    }

    /**
     * Restore components to the initial state.
     */
    private void restoreState() {
        LOG.info("Restore D Tools Config");
        for (final Property property : properties) {
            property.restoreState();
        }
        if (!propertiesComponent.isValueSet("USE_NATIVE_CODE_COMPLETION")) {
            propertiesComponent.setValue("USE_NATIVE_CODE_COMPLETION", false);
        }
        if (!propertiesComponent.isValueSet("DISABLE_SYNTAX_ERROR_FORMATTER_WARNING")) {
            propertiesComponent.setValue("DISABLE_SYNTAX_ERROR_FORMATTER_WARNING", false);
        }
        chkNativeCodeCompletion
            .setSelected(propertiesComponent.getBoolean("USE_NATIVE_CODE_COMPLETION"));
        disableFormatterSyntaxErrorCheckBox
            .setSelected(propertiesComponent.getBoolean("DISABLE_SYNTAX_ERROR_FORMATTER_WARNING"));
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
        private final String propertyKey;
        private String oldValue;

        PropertyField(@NotNull final String propertyKey, @NotNull final TextAccessor field) {
            this(propertyKey, field, "");
        }

        PropertyField(@NotNull final String propertyKey, @NotNull final TextAccessor field,
            @NotNull final String defaultValue) {
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
     * Ensures that the UI component for selecting a Dub Tool can only be used to select the correct binary
     */
    private class DubToolBinaryChooserDescriptor extends FileChooserDescriptor {
        DubToolBinaryChooserDescriptor(@NotNull final String binaryName) {
            super(true, false, false, false, false, false);
            withFileFilter(vf -> vf.getNameWithoutExtension().equalsIgnoreCase(binaryName));
        }
    }

    /**
     * Manages the group of fields which reside to a particular tool.
     */
    class Tool implements Property, Versioned {

        public final Project project;
        public final String command;
        public final ToolKey key;
        final TextFieldWithBrowseButton pathField;
        final RawCommandLineEditor flagsField;
        final JTextField versionField;
        final String latestVersion;
        final JButton autoFindButton;
        final List<PropertyField> propertyFields;
        final Topic<ToolChangeListener> topic;
        private final ToolChangeListener publisher;
        private boolean dirty = false;

        Tool(final Project project, final String command, final ToolKey key,
             final TextFieldWithBrowseButton pathField,
             final RawCommandLineEditor flagsField, final JButton autoFindButton,
             final JTextField versionField,
             final String latestVersion,
             final Topic<ToolChangeListener> topic) {
            this.project = project;
            this.command = command;
            this.key = key;
            this.pathField = pathField;
            this.flagsField = flagsField;
            this.versionField = versionField;
            this.latestVersion = latestVersion;
            this.autoFindButton = autoFindButton;
            this.topic = topic;
            this.publisher = topic == null ? null : project.getMessageBus().syncPublisher(topic);

            this.propertyFields = Arrays.asList(
                new PropertyField(key.getPathKey(), pathField),
                new PropertyField(key.getFlagsKey(), flagsField));

            GuiUtil.addFolderListener(pathField, command, "", new DubToolBinaryChooserDescriptor(command));
            GuiUtil.addApplyPathAction(autoFindButton, pathField, command);
            updateVersion();
        }

        public boolean isDirty() {
            return dirty;
        }

        void setDirty(final boolean dirty) {
            this.dirty = dirty;
        }

        @Override
        public void updateVersion() {
            @Nullable final String pathText = StringUtil.trim(pathField.getText());
            final String version = StringUtil.isEmpty(pathText) ? "" : getVersion(pathText);
            versionField.setText(version);

            if(DtoolUtils.versionPredates(version, this.latestVersion)) {
                versionField.setToolTipText(String.format("A newer version of %s is available", this.command));
                versionField.setDisabledTextColor(UIManager.getColor("Focus.color"));
            } else {
                versionField.setDisabledTextColor(UIManager.getColor("ComboBox.disabledForeground"));
                versionField.setToolTipText(null); // turns the tool tip off
            }
        }

        @Override
        public boolean isModified() {
            return propertyFields.parallelStream().anyMatch(PropertyField::isModified);
        }

        @Override
        public void saveState() {
            if (isModified() && publisher != null) {
                @Nullable final String pathText = StringUtil.trim(pathField.getText());
                publisher.onToolSettingsChanged(new ToolSettings(pathText, flagsField.getText()));
            }
            for (final PropertyField propertyField : propertyFields) {
                propertyField.saveState();
            }
        }

        @Override
        public void restoreState() {
            for (final PropertyField propertyField : propertyFields) {
                propertyField.restoreState();
            }
        }
    }
}
