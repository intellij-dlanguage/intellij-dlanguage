package net.masterthought.dlanguage.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectBuilder;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.projectImport.ProjectFormatPanel;
import net.masterthought.dlanguage.DLanguageBundle;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DubInitForModuleStep extends ModuleWizardStep {

    protected final WizardContext myWizardContext;
    private final JPanel myPanel;
    private final ProjectFormatPanel myFormatPanel = new ProjectFormatPanel();
    private final ComboBox dubFormat;
    private final ComboBox dubType;
    private final JTextField dubParams;

    public DubInitForModuleStep(final WizardContext wizardContext) {
        this.myWizardContext = wizardContext;
        this.myPanel = new JPanel(new GridBagLayout());
        this.myPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title label
        final JLabel titletextLabel = new JLabel(DLanguageBundle.INSTANCE.message("d.ui.dub.config.label.choosedubinitoptions"));
        this.myPanel.add(titletextLabel, new GridBagConstraints(0, -1, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(8, 10, 8, 10), 0, 0));

        // add combo to choose --format sdl/json for init
        this.dubFormat = new ComboBox<>(new String[] {"sdl", "json"});
        this.dubFormat.setSelectedItem("json");

        final JLabel dubFormatLabel = new JLabel(DLanguageBundle.INSTANCE.message("d.ui.dub.config.label.dubformat"));
        dubFormatLabel.setLabelFor(dubFormat);

        this.myPanel.add(dubFormatLabel, new GridBagConstraints(0, -1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 5, 4), 0, 0));
        this.myPanel.add(dubFormat, new GridBagConstraints(0, -1, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(10, 0, 20, 0), 0, 0));

        // add combo to choose --type minimal, vibe.d, deimos
        this.dubType = new ComboBox<>(new String[] {"minimal", "vibe.d", "deimos"});
        dubType.setSelectedItem("minimal");

        final JLabel dubTypeLabel = new JLabel(DLanguageBundle.INSTANCE.message("d.ui.dub.config.label.dubprojecttype"));
        dubTypeLabel.setLabelFor(dubType);

        this.myPanel.add(dubTypeLabel, new GridBagConstraints(0, -1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 5, 4), 0, 0));
        this.myPanel.add(dubType, new GridBagConstraints(0, -1, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(10, 0, 20, 0), 0, 0));

        // add text field to add params instead of using combo boxes as fallback
        this.dubParams = new JTextField();
        final JLabel dubParamsLabel = new JLabel(DLanguageBundle.INSTANCE.message("d.ui.dub.config.label.dubparamsoverride"));
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
        final ProjectBuilder moduleBuilder = this.myWizardContext.getProjectBuilder();
        if (moduleBuilder != null) {
            this.myWizardContext.setProjectBuilder(moduleBuilder);

            if (moduleBuilder instanceof ModuleBuilder) {
                final ModuleBuilder builder = (ModuleBuilder) moduleBuilder;

                if ("DLangDubApp".equals(builder.getBuilderId())) {
                    final DLanguageDubModuleBuilder dubBuilder = (DLanguageDubModuleBuilder) builder;

                    final Map<String, String> options = new HashMap<>(3);
                    options.put("dubFormat", this.dubFormat.getSelectedItem().toString());
                    options.put("dubType", this.dubType.getSelectedItem().toString());
                    options.put("dubParams", this.dubParams.getText());

                    dubBuilder.setDubOptions(options);
                }
            }
        }

        this.myFormatPanel.updateData(this.myWizardContext);
    }

    public Icon getIcon() {
        return this.myWizardContext.getStepIcon();
    }

}
