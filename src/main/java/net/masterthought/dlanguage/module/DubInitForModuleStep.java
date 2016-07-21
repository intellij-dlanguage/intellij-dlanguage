package net.masterthought.dlanguage.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectBuilder;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.util.Pair;
import com.intellij.projectImport.ProjectFormatPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DubInitForModuleStep extends ModuleWizardStep {

    private final JPanel myPanel;
    protected final WizardContext myWizardContext;
    private final ProjectFormatPanel myFormatPanel = new ProjectFormatPanel();
    private final ComboBox dubFormat;
    private final ComboBox dubType;
    private final JTextField dubParams;

    public DubInitForModuleStep(WizardContext wizardContext) {
        this.myWizardContext = wizardContext;
        this.myPanel = new JPanel(new GridBagLayout());
        this.myPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title label
        JLabel titletextLabel = new JLabel("Please choose the dub init options you prefer");
        this.myPanel.add(titletextLabel, new GridBagConstraints(0, -1, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(8, 10, 8, 10), 0, 0));

        // add combo to choose --format sdl/json for init
        java.util.List<String> options = new ArrayList<>();
        options.add("sdl");
        options.add("json");
        String[] opts = options.toArray(new String[options.size()]);
        this.dubFormat = new ComboBox(opts);
        dubFormat.setSelectedItem("json");

        JLabel dubFormatLabel = new JLabel("Dub format");
        dubFormatLabel.setLabelFor(dubFormat);

        this.myPanel.add(dubFormatLabel, new GridBagConstraints(0, -1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 5, 4), 0, 0));
        this.myPanel.add(dubFormat, new GridBagConstraints(0, -1, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(10, 0, 20, 0), 0, 0));

        // add combo to choose --type minimal, vibe.d, deimos
        java.util.List<String> typeOptions = new ArrayList<>();
        typeOptions.add("minimal");
        typeOptions.add("vibe.d");
        typeOptions.add("deimos");
        String[] typeOpts = typeOptions.toArray(new String[typeOptions.size()]);
        this.dubType = new ComboBox(typeOpts);
        dubType.setSelectedItem("minimal");

        JLabel dubTypeLabel = new JLabel("Dub project type");
        dubTypeLabel.setLabelFor(dubType);

        this.myPanel.add(dubTypeLabel, new GridBagConstraints(0, -1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 5, 4), 0, 0));
        this.myPanel.add(dubType, new GridBagConstraints(0, -1, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(10, 0, 20, 0), 0, 0));

        // add text field to add params instead of using combo boxes as fallback
        this.dubParams = new JTextField();
        JLabel dubParamsLabel = new JLabel("Dub params override - supply space separated params here instead and it will use these e.g --format=json");
        dubParamsLabel.setLabelFor(dubParams);
        this.myPanel.add(dubParamsLabel, new GridBagConstraints(0, -1, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(10, 0, 20, 0), 0, 0));
        this.myPanel.add(dubParams, new GridBagConstraints(0, -1, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(10, 0, 20, 0), 0, 0));

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

                    List<Pair<String, String>> optionsList = new ArrayList<>();
                    optionsList.add(Pair.create("dubFormat", this.dubFormat.getSelectedItem().toString()));
                    optionsList.add(Pair.create("dubType", this.dubType.getSelectedItem().toString()));
                    optionsList.add(Pair.create("dubParams", this.dubParams.getText()));

                    dubBuilder.setDubInitOptions(optionsList);
                }
            }
        }

        this.myFormatPanel.updateData(this.myWizardContext);
    }

    public Icon getIcon() {
        return this.myWizardContext.getStepIcon();
    }

}
