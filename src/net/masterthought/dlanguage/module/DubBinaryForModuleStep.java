package net.masterthought.dlanguage.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectBuilder;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.Pair;
import com.intellij.projectImport.ProjectFormatPanel;
import net.masterthought.dlanguage.utils.GuiUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DubBinaryForModuleStep extends ModuleWizardStep {

    private final JPanel myPanel;
    protected final WizardContext myWizardContext;
    private final ProjectFormatPanel myFormatPanel = new ProjectFormatPanel();
    private final TextFieldWithBrowseButton dubBinary;
    private final JButton autoFindButton;


    public DubBinaryForModuleStep(WizardContext wizardContext) {
        this.myWizardContext = wizardContext;
        this.myPanel = new JPanel(new GridBagLayout());
        this.myPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title label
        JLabel titletextLabel = new JLabel("Please choose the location of your dub binary");
        this.myPanel.add(titletextLabel, new GridBagConstraints(0, -1, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(8, 10, 8, 10), 0, 0));

        this.dubBinary = new TextFieldWithBrowseButton();
        this.autoFindButton = new JButton("Auto find");

        JLabel dubFormatLabel = new JLabel("Dub binary location (auto find if dub binary is on your path)");
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
        ProjectBuilder moduleBuilder = this.myWizardContext.getProjectBuilder();
        if (moduleBuilder != null) {
            this.myWizardContext.setProjectBuilder(moduleBuilder);
            if (moduleBuilder instanceof ModuleBuilder) {
                ModuleBuilder builder = (ModuleBuilder) moduleBuilder;

                if (builder.getBuilderId() != null && builder.getBuilderId().equals("DLangDubApp")) {
                    DLanguageDubModuleBuilder dubBuilder = (DLanguageDubModuleBuilder) builder;

                    dubBuilder.setDubBinary(this.dubBinary.getText());
                }
            }
        }

        this.myFormatPanel.updateData(this.myWizardContext);
    }

    public Icon getIcon() {
        return this.myWizardContext.getStepIcon();
    }

}
