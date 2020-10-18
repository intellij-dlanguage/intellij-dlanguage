package io.github.intellij.dlanguage.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectBuilder;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.projectImport.ProjectFormatPanel;
import com.intellij.util.concurrency.AppExecutorUtil;
import io.github.intellij.dlanguage.DlangBundle;
import io.github.intellij.dlanguage.icons.DlangIcons;
import io.github.intellij.dlanguage.project.DubProjectImportBuilder;
import io.github.intellij.dlanguage.settings.DLanguageToolsConfigurable;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.utils.ExecUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

public class DubBinaryForModuleStep extends ModuleWizardStep {

    private final static Logger log = Logger.getInstance(DubBinaryForModuleStep.class);

    private final WizardContext myWizardContext;
    private final JPanel myPanel;
    private final ProjectFormatPanel myFormatPanel = new ProjectFormatPanel();
    private final TextFieldWithBrowseButton dubBinary;

    public DubBinaryForModuleStep(final WizardContext wizardContext) {
        this.myWizardContext = wizardContext;
        this.myPanel = new JPanel(new GridBagLayout());
        this.myPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title label
        final JLabel titletextLabel = new JLabel(DlangBundle.INSTANCE.message("d.ui.dub.config.label.choosedublocation"));
        this.myPanel.add(titletextLabel, new GridBagConstraints(0, -1, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(8, 10, 8, 10), 0, 0));

        this.dubBinary = new TextFieldWithBrowseButton();
        if(StringUtil.isNotEmpty(ToolKey.DUB_KEY.getPath())) {
            this.dubBinary.setText(ToolKey.DUB_KEY.getPath());
        }

        final JButton autoFindButton = new JButton(DlangBundle.INSTANCE.message("d.ui.dub.config.label.autofind"), DlangIcons.SDK);

        final JLabel dubFormatLabel = new JLabel(DlangBundle.INSTANCE.message("d.ui.dub.config.label.dubbinarylocation"));
        dubFormatLabel.setLabelFor(dubBinary);

        dubBinary.addBrowseFolderListener(
            String.format("Select %s executable", "dub"),
            "",
            null,
            FileChooserDescriptorFactory.createSingleLocalFileDescriptor());

        autoFindButton.addActionListener(this::autoFindDubBinary);


        this.myPanel.add(dubFormatLabel, new GridBagConstraints(0, -1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 5, 4), 0, 0));
        this.myPanel.add(dubBinary, new GridBagConstraints(0, -1, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(10, 0, 20, 0), 0, 0));
        this.myPanel.add(autoFindButton, new GridBagConstraints(0, -1, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(10, 0, 20, 0), 0, 0));

    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return dubBinary;
    }

    @Override
    public JComponent getComponent() {
        return this.myPanel;
    }

    @Override
    public boolean isStepVisible() {
        return this.myWizardContext.getProject() == null;
    }

    @Override
    public void updateDataModel() {
        final ProjectBuilder moduleBuilder = this.myWizardContext.getProjectBuilder();
        final String dubBinaryPath = this.dubBinary.getText();

        if(StringUtil.isEmpty(ToolKey.DUB_KEY.getPath()) && StringUtil.isNotEmpty(dubBinaryPath)) {
            ToolKey.DUB_KEY.setPath(dubBinaryPath);
        }

        if (moduleBuilder != null  && !dubBinaryPath.isEmpty()) {
            this.myWizardContext.setProjectBuilder(moduleBuilder);
            if (moduleBuilder instanceof ModuleBuilder) {
                final ModuleBuilder builder = (ModuleBuilder) moduleBuilder;

                if (builder.getBuilderId() != null && DlangDubModuleBuilder.BUILDER_ID.equals(builder.getBuilderId())) {
                    final DlangDubModuleBuilder dubBuilder = (DlangDubModuleBuilder) builder;

                    dubBuilder.setDubBinary(dubBinaryPath);
                }
            } else if (moduleBuilder instanceof DubProjectImportBuilder) {
                ((DubProjectImportBuilder) moduleBuilder).getParameters().dubBinary = dubBinaryPath;
            }
        }

        this.myFormatPanel.updateData(this.myWizardContext);
    }

    @Override
    public boolean validate() throws ConfigurationException {
        if(StringUtil.isEmpty(this.dubBinary.getText())) {
            throw new ConfigurationException(
                DlangBundle.INSTANCE.message("d.ui.dub.config.error.path-not-set"),
                DlangBundle.INSTANCE.message("d.ui.dub.config.error.title")
            );
        } else if(StringUtil.isEmpty(DLanguageToolsConfigurable.getVersion(this.dubBinary.getText()))) {
            throw new ConfigurationException(
                DlangBundle.INSTANCE.message("d.ui.dub.config.error.path-not-valid"),
                DlangBundle.INSTANCE.message("d.ui.dub.config.error.title")
            );
        }

        return true;
    }

    public Icon getIcon() {
        return this.myWizardContext.getStepIcon();
    }

    private void autoFindDubBinary(final ActionEvent event) {
        @Nullable final Optional<Path> optionalPath = Arrays.stream(STANDARD_DUB_EXE_PATHS)
            .filter(p -> Files.exists(p) && Files.isExecutable(p))
            .findFirst();

        if(optionalPath.isPresent()) {
            dubBinary.setText(optionalPath.get().toString());
        } else {
            AppExecutorUtil.getAppExecutorService().submit(() -> {
                @Nullable final String foundPath = ExecUtil.locateExecutable("dub");
                if (StringUtil.isNotEmpty(foundPath)) {
                    dubBinary.setText(foundPath);
                } else {
                    Messages.showErrorDialog("Could not find 'dub'.", "DLanguage");
                }
            });
        }
    }

    private static final Path[] STANDARD_DUB_EXE_PATHS;

    static {
        if (SystemInfo.isWindows) {
            STANDARD_DUB_EXE_PATHS = new Path[] {
                Paths.get("\\D\\dmd2\\windows\\bin\\dub.exe")
            };
        } else if (SystemInfo.isMac) {
            STANDARD_DUB_EXE_PATHS = new Path[] {
                Paths.get("/usr/local/opt/dub") // homebrew
            };
        } else if (SystemInfo.isUnix) {
            STANDARD_DUB_EXE_PATHS = new Path[] {
                Paths.get("/usr/local/bin/dub"),
                Paths.get("/usr/bin/dub"),
                Paths.get("/snap/bin/dub"), // #575 support snaps
                Paths.get(System.getProperty("user.home") + "/bin/dub")
            };
        } else {
            log.warn(String.format("D language plugin does not support %s", SystemInfo.getOsNameAndVersion()));
            STANDARD_DUB_EXE_PATHS = new Path[]{};
        }
    }
}
