package io.github.intellij.dlanguage.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectBuilder;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.projectImport.ProjectFormatPanel;
import io.github.intellij.dlanguage.DlangBundle;
import io.github.intellij.dlanguage.project.DubProjectImportBuilder;
import io.github.intellij.dlanguage.utils.GuiUtil;

import javax.swing.*;
import java.awt.*;

public class DubBinaryForModuleStep extends ModuleWizardStep {

    protected final WizardContext myWizardContext;
    private final JPanel myPanel;
    private final ProjectFormatPanel myFormatPanel = new ProjectFormatPanel();
    private final TextFieldWithBrowseButton dubBinary;
    private final JButton autoFindButton;


    public DubBinaryForModuleStep(final WizardContext wizardContext) {
        this.myWizardContext = wizardContext;
        this.myPanel = new JPanel(new GridBagLayout());
        this.myPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title label
        final JLabel titletextLabel = new JLabel(DlangBundle.INSTANCE.message("d.ui.dub.config.label.choosedublocation"));
        this.myPanel.add(titletextLabel, new GridBagConstraints(0, -1, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(8, 10, 8, 10), 0, 0));

        this.dubBinary = new TextFieldWithBrowseButton();
        this.autoFindButton = new JButton(DlangBundle.INSTANCE.message("d.ui.dub.config.label.autofind"));

        final JLabel dubFormatLabel = new JLabel(DlangBundle.INSTANCE.message("d.ui.dub.config.label.dubbinarylocation"));
        dubFormatLabel.setLabelFor(dubBinary);

        GuiUtil.addFolderListener(dubBinary, "dub");
        GuiUtil.addApplyPathAction(autoFindButton, dubBinary, "dub");


        this.myPanel.add(dubFormatLabel, new GridBagConstraints(0, -1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 5, 4), 0, 0));
        this.myPanel.add(dubBinary, new GridBagConstraints(0, -1, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(10, 0, 20, 0), 0, 0));
        this.myPanel.add(autoFindButton, new GridBagConstraints(0, -1, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(10, 0, 20, 0), 0, 0));

    }

    public JComponent getComponent() {
        return this.myPanel;
    }

    public boolean isStepVisible() {
        return this.myWizardContext.getProject() == null;
    }

    public void updateDataModel() {
        final ProjectBuilder moduleBuilder = this.myWizardContext.getProjectBuilder();
        if (moduleBuilder != null) {
            this.myWizardContext.setProjectBuilder(moduleBuilder);
            if (moduleBuilder instanceof ModuleBuilder) {
                final ModuleBuilder builder = (ModuleBuilder) moduleBuilder;

                if (builder.getBuilderId() != null && builder.getBuilderId().equals("DLangDubApp")) {
                    final DlangDubModuleBuilder dubBuilder = (DlangDubModuleBuilder) builder;

                    dubBuilder.setDubBinary(this.dubBinary.getText());
                }
            } else if (moduleBuilder instanceof DubProjectImportBuilder) {
                ((DubProjectImportBuilder) moduleBuilder).getParameters().dubBinary = this.dubBinary.getText();
            }
        }

        this.myFormatPanel.updateData(this.myWizardContext);
    }

    public Icon getIcon() {
        return this.myWizardContext.getStepIcon();
    }

}
