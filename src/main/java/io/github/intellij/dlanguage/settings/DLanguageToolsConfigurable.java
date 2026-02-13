package io.github.intellij.dlanguage.settings;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.*;
import com.intellij.ide.DataManager;
import com.intellij.ide.ui.search.SearchableOptionContributor;
import com.intellij.ide.ui.search.SearchableOptionProcessor;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.options.ex.Settings;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.RawCommandLineEditor;
import com.intellij.ui.TextAccessor;
import com.intellij.ui.components.ActionLink;
import com.intellij.util.messages.Topic;
import io.github.intellij.dlanguage.messagebus.ToolChangeListener;
import io.github.intellij.dlanguage.messagebus.Topics;
import io.github.intellij.dlanguage.tools.DtoolUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static io.github.intellij.dlanguage.tools.DtoolUtils.*;

/**
 * The "D Tools" option in: Settings -> Languages & Frameworks -> D Tools.
 */
public class DLanguageToolsConfigurable implements SearchableConfigurable {

    public static final String D_TOOLS_ID = "D Tools";
    public static final String D_TOOLS_DISPLAY_NAME = D_TOOLS_ID;

    private static final Logger LOG = Logger.getInstance(DLanguageToolsConfigurable.class);
    private final PropertiesComponent propertiesComponent;
    private final List<Tool> properties;
    // Swing components.
    private JPanel mainPanel;
    private ActionLink dubMovedLink;
    private JPanel dubPanel;
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
    private TextFieldWithBrowseButton GDBPath;
    private JButton GDBAutoFind;
    private RawCommandLineEditor GDBFlags;
    private JTextField GDBVersion;
    private JTabbedPane tabbedPane1;

    public static class SearchableDlangTools extends SearchableOptionContributor {
        @Override
        public void processOptions(@NotNull SearchableOptionProcessor processor) {
            processor.addOptions("dub", null, "dub", D_TOOLS_ID, D_TOOLS_DISPLAY_NAME, false);
            processor.addOptions("dscanner", null, "dscanner", D_TOOLS_ID, D_TOOLS_DISPLAY_NAME, true);
            processor.addOptions("dcd", "dcd", "dcd", D_TOOLS_ID, D_TOOLS_DISPLAY_NAME, false);
            processor.addOptions("dfmt", null, "dfmt", D_TOOLS_ID, D_TOOLS_DISPLAY_NAME, false);
            processor.addOptions("gdb", null, "gdb", D_TOOLS_ID, D_TOOLS_DISPLAY_NAME, false);
        }
    }

    public DLanguageToolsConfigurable(@NotNull final Project project) {
        this.propertiesComponent = PropertiesComponent.getInstance();

        properties = Arrays.asList(
            new Tool(project, "dscanner", ToolKey.DSCANNER_KEY, dscannerPath, dscannerFlags,
                dscannerAutoFind, dscannerVersion, DSCANNER_LATEST, Topics.DSCANNER_TOOL_CHANGE),
            new Tool(project, "dcd-server", ToolKey.DCD_SERVER_KEY, dcdPath, dcdFlags,
                dcdAutoFind, dcdVersion, DCD_LATEST, Topics.DCD_SERVER_TOOL_CHANGE),
            new Tool(project, "dcd-client", ToolKey.DCD_CLIENT_KEY, dcdClientPath, dcdClientFlags,
                dcdClientAutoFind, dcdClientVersion, DCD_LATEST, Topics.DCD_CLIENT_TOOL_CHANGE),
            new Tool(project, "dfmt", ToolKey.DFORMAT_KEY, dFormatPath, dFormatFlags,
                dFormatAutoFind, dFormatVersion, DFORMAT_LATEST, Topics.DFMT_TOOL_CHANGE),
            new Tool(project, "gdb", ToolKey.GDB_KEY, GDBPath, GDBFlags,
                GDBAutoFind, GDBVersion, null, Topics.GDB_TOOL_CHANGE)
        );

        dubMovedLink.addActionListener(actionEvent -> {
            DataContext dataContext = DataManager.getInstance().getDataContext(dubPanel);
            Settings settings = Settings.KEY.getData(dataContext);
            if (settings != null) {
                settings.select(settings.find("language.d.build.tool.dub"));
            }
        });
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
        return D_TOOLS_DISPLAY_NAME;
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

                final String exePath = StringUtil.trim(t.pathField.getText());

                if(StringUtil.isNotEmpty(exePath)) {
                    final Path path = Paths.get(exePath);

                    if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
                        t.versionField.setText("Path does not exist");
                        t.versionField.setDisabledTextColor(UIManager.getColor("Component.warningFocusColor"));
                    } else if(!Files.isExecutable(path)) {
                        t.versionField.setText("Path is not executable");
                        t.versionField.setDisabledTextColor(UIManager.getColor("Component.warningFocusColor"));
//                    } else if(!StringUtil.containsIgnoreCase(exePath, t.command)) {
//                        t.versionField.setText(String.format("Not a valid %s binary", t.command));
//                        t.versionField.setDisabledTextColor(UIManager.getColor("Component.warningFocusColor"));
                    } else {
                        // then it's a valid path to the tool
                        t.updateVersion();
                        return true;
                    }
                } else {
                    // allow it to be set to a blank value
                    t.updateVersion();
                    return true;
                }
            } else if (t.isDirty()) {
                t.updateVersion();
                t.setDirty(false);
            }
        }
        return false;
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

        for (Tool tool: properties) {
            tool.cancelUpdate();
        }
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

//    /**
//     * Ensures that the UI component for selecting a Dub Tool can only be used to select the correct binary
//     */
//    private class DubToolBinaryChooserDescriptor extends FileChooserDescriptor {
//        DubToolBinaryChooserDescriptor(@NotNull final String binaryName) {
//            super(true, false, false, false, false, false);
//            withFileFilter(vf -> vf.getNameWithoutExtension().equalsIgnoreCase(binaryName));
//        }
//    }

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

        private Future<?> updateInProgress;

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

            var chooserDescriptor = new FileChooserDescriptor(true, false, false, false, false, false)
                .withTitle(String.format("Select %s executable", command))
                .withDescription(SystemInfo.isWindows && "gdb".equals(command) ? "On Windows mago-mi.exe can be used" : "")
                .withShowHiddenFiles(false)
                .withFileFilter(SystemInfo.isWindows && "gdb".equals(command) ?
                    vf -> List.of(command, "mago-mi").contains(vf.getNameWithoutExtension()) :
                    vf -> vf.getNameWithoutExtension().equalsIgnoreCase(command)
                );

            pathField.addBrowseFolderListener(null, chooserDescriptor);

            autoFindButton.addActionListener(new LocateToolListener(pathField, command));

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
            updateVersionField(this);
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

        public void setUpdateInProgress(Future<?> future) {
            cancelUpdate();
            updateInProgress = future;
        }

        public void cancelUpdate() {
            if (updateInProgress != null) {
                updateInProgress.cancel(true);
            }
            updateInProgress = null;
        }
    }

    private void updateVersionField(@NotNull final Tool tool) {
        @Nullable final String pathText = StringUtil.trim(tool.pathField.getText());

        if(StringUtil.isEmptyOrSpaces(pathText)) {
            LOG.trace(String.format("unable to get %s version info as path is empty", tool.command));
            tool.versionField.setText("");
            tool.versionField.setDisabledTextColor(UIManager.getColor("ComboBox.disabledForeground"));
            tool.versionField.setToolTipText(null); // turns the tool tip off
        } else if(!Paths.get(pathText).toFile().canExecute()) {
            LOG.warn(String.format("unable to get %s version info for path: '%s'", tool.command, pathText));
            tool.versionField.setText(String.format("Path '%s' is not executable", pathText));
            tool.versionField.setDisabledTextColor(UIManager.getColor("Component.warningFocusColor"));
            tool.versionField.setToolTipText(null); // turns the tool tip off
        } else {
            final GeneralCommandLine cmd = new GeneralCommandLine(pathText)
                .withCharset(StandardCharsets.UTF_8)
                .withParameters("--version");

            tool.setUpdateInProgress(ApplicationManager.getApplication()
                .executeOnPooledThread(() -> {
                    try {
                        new DlangToolVersionProcessAdapter(tool, cmd)
                            .startNotify();
                    } catch (final ExecutionException e) {
                        LOG.error("Could not run: " + cmd.getCommandLineString(), e);
                    }
                }));
        }
    }

    private static class LocateToolListener implements ActionListener {
        private final TextFieldWithBrowseButton pathField;
        private final String command;

        public LocateToolListener(@NonNls final TextFieldWithBrowseButton pathField, @NonNls final String command) {
            this.pathField = pathField;
            this.command = command;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            @Nullable final String path = Optional.ofNullable(DtoolUtils.INSTANCE.lookInStandardDirectories(command)).orElseGet(this::locateViaCommandline);

            if (StringUtil.isNotEmpty(path)) {
                pathField.setText(StringUtil.trim(path));
            } else {
                Messages.showWarningDialog(String.format("Could not find '%s'.", command), "DLanguage");
            }
        }

        /**
         * Attempt to find the D Tool by looking on the PATH
         *
         * @return either the found tool path or null
         */
        @Nullable
        private String locateViaCommandline() {
            final GeneralCommandLine cmd = new GeneralCommandLine()
                .withExePath(SystemInfo.isWindows ? "cmd" : "/bin/sh")
                .withParameters(
                    SystemInfo.isWindows ? "/c" : "-c",
                    SystemInfo.isWindows ? "where" : "which",
                    command)
                ;

            try {
                final String path = ApplicationManager.getApplication().executeOnPooledThread(() -> {
                        try {
                            return new CapturingProcessHandler(
                                cmd.createProcess(),
                                cmd.getCharset(),
                                cmd.getCommandLineString()
                            )
                                .runProcess()
                                .getStdout();
                        } catch (final ExecutionException e) {
                            LOG.warn(String.format("Failed to run '%s'.", command), e);
                        }
                        return null;
                    })
                    .get(800, TimeUnit.MILLISECONDS);

                if (path != null && SystemInfo.isWindows && path.contains("C:\\")) {
                    // not sure if this is actually needed. Was moved over from ExecUtil
                    final String[] split = path.split("(?=C:\\\\)");
                    LOG.info("Multiple paths found for " + command);
                    return split[0]; // if there are multiple results default to first one.
                }
                return path;
            } catch (final InterruptedException | java.util.concurrent.ExecutionException | TimeoutException e) {
                LOG.warn(String.format("Failed to run '%s'.", command), e);
            }
            return null;
        }
    }

    // A process handler for assign version output from D Tools to the appropriate fields
    private class DlangToolVersionProcessAdapter extends OSProcessHandler {

        private boolean complete;

        public DlangToolVersionProcessAdapter(@NotNull final Tool tool,
                                              @NotNull final GeneralCommandLine commandLine) throws ExecutionException {
            super(commandLine);

            this.complete = false;

            this.addProcessListener(new ProcessAdapter() {
                @Override
                public void onTextAvailable(@NotNull ProcessEvent event, @NotNull Key outputType) {
                    if(!complete && ProcessOutputTypes.STDOUT.equals(outputType)) {
                        final String version = StringUtil.replace(event.getText(), "\n", "");
                        tool.versionField.setText(version);

                        if(DtoolUtils.versionPredates(version, tool.latestVersion)) {
                            tool.versionField.setToolTipText(String.format("A newer version of %s is available", tool.command));
                            tool.versionField.setDisabledTextColor(UIManager.getColor("Component.warningFocusColor"));
                        } else {
                            tool.versionField.setDisabledTextColor(UIManager.getColor("ComboBox.disabledForeground"));
                            tool.versionField.setToolTipText(null); // turns the tool tip off
                        }
                        complete = true;
                    }
                }
            });
        }
    }
}
