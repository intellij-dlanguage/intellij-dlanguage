package net.masterthought.dlanguage.run;

import com.intellij.application.options.ModulesComboBox;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.options.SettingsEditorListener;
import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.RawCommandLineEditor;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import net.masterthought.dlanguage.DLanguageBundle;
import net.masterthought.dlanguage.module.DLanguageModuleType;
import net.masterthought.dlanguage.run.exception.NoSourcesException;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DLanguageRunDmdConfigurationEditor extends SettingsEditor<DLanguageRunDmdConfiguration> {
    private JTabbedPane myMainPanel;
    private JPanel tabCompiler;
    private JPanel tabOutput;
    private JPanel tabDebug;
    private JPanel tabArguments;

    //"Compile" tab
    private ModulesComboBox comboModules;
    private JCheckBox cbRelease;
    private JCheckBox cbDebug;
    private JCheckBox cbUnitTest;
    private JCheckBox cbLink;
    private JCheckBox cbCoverageAnalysis;
    private JCheckBox cbAllowDeprecated;
    private JCheckBox cbIgnorePragmas;
    private JCheckBox cbFunctionInlining;
    private JCheckBox cbLibrary;
    private JCheckBox cbNoArrayBoundsCheck;
    private JCheckBox cbNoFloatingPointReferences;
    private JCheckBox cbOptimize;
    private JCheckBox cbEnforcePropertySyntax;
    private JCheckBox cbQuiet;
    private JCheckBox cbVerbose;
    private JCheckBox cbListThreadLocalStorage;
    private JCheckBox cbWarnings;
    private JCheckBox cbInfoWarnings;
    private JTextField textDefaultLibrary;
    private TextFieldWithBrowseButton pathImports;
    private TextFieldWithBrowseButton pathStringImports;
    private RawCommandLineEditor linkerArgs;

    //"Output" tab
    private JCheckBox cbGenerateDocumentation;
    private JTextField filenameDocumentation;
    private TextFieldWithBrowseButton pathDocumentation;
    private JTextField filenameModuleDependencies;
    private JCheckBox cbGenerateHeader;
    private TextFieldWithBrowseButton pathHeaderDir;
    private JTextField filenameHeader;
    private JCheckBox cbGenerateMap;
    private JCheckBox cbNoObjectFiles;
    private JCheckBox cbNoStripPaths;
    private JCheckBox cbGenerateJson;
    private JTextField filenameJson;

    //"Debug" tab
    private JCheckBox  cbAddSymbolicDebugInfo;
    private JCheckBox  cbAddSymbolicDebugInfoC;
    private JCheckBox  cbGenerateStandardStackFrame;
    private JTextField textSymbolicLibrary;
    private JCheckBox  cbProfile;

    //"Arguments" tab
    private JLabel linkDmdDoc;
    private JTextPane textArgsPane;
    private JBScrollPane textArgsScrollPane;

    /** Update editor UI with data of DLangRunDmdConfiguration.
     * All components must be changed according to "config" data. */
    @Override
    protected void resetEditorFrom(DLanguageRunDmdConfiguration config) {
        resetCompilerTabForm(config);
        resetOutputTabForm(config);
        resetDebugTabForm(config);
        fillArguments(config);
    }

    /** Save state of editor UI to DLangRunDmdConfiguration instance. */
    @Override
    protected void applyEditorTo(DLanguageRunDmdConfiguration config) throws ConfigurationException {
        applyCompilerTabForm(config);
        applyOutputTabForm(config);
        applyDebugTabForm(config);
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        FileChooserDescriptor fcd = FileChooserDescriptorFactory.createSingleFolderDescriptor();
        fcd.setShowFileSystemRoots(true);
        fcd.setTitle(DLanguageBundle.INSTANCE.message("dmd.run.config.selectimportfolder.title"));
        fcd.setDescription(DLanguageBundle.INSTANCE.message("dmd.run.config.selectimportfolder.description"));
        fcd.setHideIgnored(false);

        pathImports.addActionListener(new TextFieldWithBrowseButton.BrowseFolderActionListener<>(fcd.getTitle(), fcd.getDescription(),
                        pathImports, null, fcd, TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT) );

        //XXX: fix title and description
        pathStringImports.addActionListener(new TextFieldWithBrowseButton.BrowseFolderActionListener<>(fcd.getTitle(), fcd.getDescription(),
                        pathStringImports, null, fcd, TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT) );

        //XXX: fix title and description
        pathDocumentation.addActionListener(new TextFieldWithBrowseButton.BrowseFolderActionListener<>(fcd.getTitle(), fcd.getDescription(),
                        pathDocumentation, null, fcd, TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT) );

        //XXX: fix title and description
        pathHeaderDir.addActionListener(new TextFieldWithBrowseButton.BrowseFolderActionListener<>(fcd.getTitle(), fcd.getDescription(),
                        pathHeaderDir, null, fcd, TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT) );

        cbGenerateDocumentation.addChangeListener(e -> {
            final boolean enabled = cbGenerateDocumentation.isSelected();
            filenameDocumentation.setEnabled(enabled);
            pathDocumentation.setEnabled(enabled);
        });

        cbGenerateHeader.addChangeListener(e -> {
            final boolean enabled = cbGenerateHeader.isSelected();
            pathHeaderDir.setEnabled(enabled);
            filenameHeader.setEnabled(enabled);
        });

        cbGenerateJson.addChangeListener(e -> filenameJson.setEnabled(cbGenerateJson.isSelected()));


        setDocLink();

        /* Each time settings are changed "Arguments" tab must be updated.
         * Add listener here. */
        final DLanguageRunDmdConfigurationType configurationType
                = Extensions.findExtension(ConfigurationType.CONFIGURATION_TYPE_EP, DLanguageRunDmdConfigurationType.class);
        final ConfigurationFactory factory = configurationType.getConfigurationFactories()[0];

        addSettingsEditorListener(editor -> {
            try {
                Module module = comboModules.getSelectedModule();
                if(module!=null) {
                    DLanguageRunDmdConfiguration config = (DLanguageRunDmdConfiguration) factory.createTemplateConfiguration(module.getProject());
                    applyEditorTo(config); //Save current editor state to "config"
                    fillArguments(config); //Convert "config" to DMD arguments to display on "Arguments" tab.
                }
            }
            catch (Exception e) {
                //pass
            }
        });

        //wrap "textArgsPane" with JBScrollPane to allow scrolling.
        Container tabContent = textArgsPane.getParent();
        GridLayoutManager layout = (GridLayoutManager)tabContent.getLayout();
        GridConstraints constraints = layout.getConstraintsForComponent(textArgsPane);

        textArgsScrollPane = new JBScrollPane(textArgsPane);
        tabContent.add(textArgsScrollPane, constraints);
        tabContent.remove(textArgsPane);

        return myMainPanel;
    }

    @Override
    protected void disposeEditor() {
    }

    /* I hope there is no misprints in next methods :) */
    private void resetCompilerTabForm(DLanguageRunDmdConfiguration config) {
        comboModules.fillModules(config.getProject(), DLanguageModuleType.getInstance());
        comboModules.setSelectedModule(config.getConfigurationModule().getModule());
        cbRelease.setSelected(config.isRelease());
        cbDebug.setSelected(config.isDebug());
        cbUnitTest.setSelected(config.isUnitTest());
        cbLink.setSelected(config.isLink());
        cbCoverageAnalysis.setSelected(config.isCoverageAnalysis());
        cbAllowDeprecated.setSelected(config.isAllowDeprecated());
        cbIgnorePragmas.setSelected(config.isIgnorePragmas());
        cbFunctionInlining.setSelected(config.isFunctionInlining());
        cbLibrary.setSelected(config.isLibrary());
        cbNoArrayBoundsCheck.setSelected(config.isNoArrayBoundsCheck());
        cbNoFloatingPointReferences.setSelected(config.isNoFloatingPointReferences());
        cbOptimize.setSelected(config.isOptimize());
        cbEnforcePropertySyntax.setSelected(config.isEnforcePropertySyntax());
        cbQuiet.setSelected(config.isQuiet());
        cbVerbose.setSelected(config.isVerbose());
        cbListThreadLocalStorage.setSelected(config.isListThreadLocalStorage());
        cbWarnings.setSelected(config.isWarnings());
        cbInfoWarnings.setSelected(config.isInfoWarnings());
        textDefaultLibrary.setText(config.getDefaultLibrary());
        pathImports.setText(config.getImportsPath());
        pathStringImports.setText(config.getStringImportsPath());
        linkerArgs.setText(config.getLinkerArgs());
    }

    private void resetOutputTabForm(DLanguageRunDmdConfiguration config) {
        cbGenerateDocumentation.setSelected(config.isGenerateDocs());
        filenameDocumentation.setText(config.getDocsFilename());
        pathDocumentation.setText(config.getDocsPath());
        filenameModuleDependencies.setText(config.getModuleDepsFilename());
        cbGenerateHeader.setSelected(config.isGenerateHeader());
        pathHeaderDir.setText(config.getHeaderDir());
        filenameHeader.setText(config.getHeaderFilename());
        cbGenerateMap.setSelected(config.isGenerateMap());
        cbNoObjectFiles.setSelected(config.isNoObjectFiles());
        cbNoStripPaths.setSelected(config.isNoStripPaths());
        cbGenerateJson.setSelected(config.isGenerateJson());
        filenameJson.setText(config.getJsonFilename());
    }

    private void resetDebugTabForm(DLanguageRunDmdConfiguration config) {
        cbAddSymbolicDebugInfo.setSelected(config.isAddSymbolicDebugInfo());
        cbAddSymbolicDebugInfoC.setSelected(config.isAddSymbolicDebugInfoC());
        cbGenerateStandardStackFrame.setSelected(config.isGenerateStandardStackFrame());
        textSymbolicLibrary.setText(config.getSymbolicLibrary());
        cbProfile.setSelected(config.isProfile());
    }

    /* Make "linkDmdDoc" clickable. */
    private void setDocLink() {
        linkDmdDoc.setText("<html><a href=\"http://dlang.org/dmd-linux.html#switches\">Documentation at dlang.org</a></html>");
        linkDmdDoc.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkDmdDoc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI("http://dlang.org/dmd-linux.html#switches");
                            desktop.browse(uri);
                        } catch (IOException ex) {
                            //do nothing
                        } catch (URISyntaxException ex) {
                            //do nothing
                        }
                    }
                }
            }
        });
    }

    private void applyCompilerTabForm(DLanguageRunDmdConfiguration config) {
        config.setModule(comboModules.getSelectedModule());
        config.setRelease(cbRelease.isSelected());
        config.setDebug(cbDebug.isSelected());
        config.setUnitTest(cbUnitTest.isSelected());
        config.setLink(cbLink.isSelected());
        config.setCoverageAnalysis(cbCoverageAnalysis.isSelected());
        config.setAllowDeprecated(cbAllowDeprecated.isSelected());
        config.setIgnorePragmas(cbIgnorePragmas.isSelected());
        config.setFunctionInlining(cbFunctionInlining.isSelected());
        config.setLibrary(cbLibrary.isSelected());
        config.setNoArrayBoundsCheck(cbNoArrayBoundsCheck.isSelected());
        config.setNoFloatingPointReferences(cbNoFloatingPointReferences.isSelected());
        config.setOptimize(cbOptimize.isSelected());
        config.setEnforcePropertySyntax(cbEnforcePropertySyntax.isSelected());
        config.setQuiet(cbQuiet.isSelected());
        config.setVerbose(cbVerbose.isSelected());
        config.setListThreadLocalStorage(cbListThreadLocalStorage.isSelected());
        config.setWarnings(cbWarnings.isSelected());
        config.setInfoWarnings(cbInfoWarnings.isSelected());
        config.setDefaultLibrary(textDefaultLibrary.getText());
        config.setImportsPath(pathImports.getText());
        config.setStringImportsPath(pathStringImports.getText());
        config.setLinkerArgs(linkerArgs.getText());
    }

    private void applyOutputTabForm(DLanguageRunDmdConfiguration config) {
        config.setGenerateDocs(cbGenerateDocumentation.isSelected());
        config.setDocsFilename(filenameDocumentation.getText());
        config.setDocsPath(pathDocumentation.getText());
        config.setModuleDepsFilename(filenameModuleDependencies.getText());
        config.setGenerateHeader(cbGenerateHeader.isSelected());
        config.setHeaderDir(pathHeaderDir.getText());
        config.setHeaderFilename(filenameHeader.getText());
        config.setGenerateMap(cbGenerateMap.isSelected());
        config.setNoObjectFiles(cbNoObjectFiles.isSelected());
        config.setNoStripPaths(cbNoStripPaths.isSelected());
        config.setGenerateJson(cbGenerateJson.isSelected());
        config.setJsonFilename(filenameJson.getText());
    }

    private void applyDebugTabForm(DLanguageRunDmdConfiguration config) {
        config.setAddSymbolicDebugInfo(cbAddSymbolicDebugInfo.isSelected());
        config.setAddSymbolicDebugInfoC(cbAddSymbolicDebugInfoC.isSelected());
        config.setGenerateStandardStackFrame(cbGenerateStandardStackFrame.isSelected());
        config.setSymbolicLibrary(textSymbolicLibrary.getText());
        config.setProfile(cbProfile.isSelected());
    }

    /** Update "textArgsPane" text area with actual DMD command line arguments */
    private void fillArguments(DLanguageRunDmdConfiguration config) {
        Module module = comboModules.getSelectedModule();
        if(module == null) {
            return;
        }
        try {
            java.util.List<String> args = DLanguageDmdConfigToArgsConverter.getDmdParameters(config, module);
            textArgsPane.setText(StringUtils.join(args, "\n"));
        } catch (NoSourcesException | ExecutionException e) {
            textArgsPane.setText("*Exception*:\n" + e.getMessage());
        }
    }
}
