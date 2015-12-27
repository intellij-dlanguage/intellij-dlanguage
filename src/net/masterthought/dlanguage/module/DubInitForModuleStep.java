package net.masterthought.dlanguage.module;

import com.intellij.ide.IdeBundle;
import com.intellij.ide.util.newProjectWizard.modes.WizardMode;
import com.intellij.ide.util.projectWizard.*;
import com.intellij.ide.util.projectWizard.importSources.impl.ProjectFromSourcesBuilderImpl;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.projectImport.ProjectFormatPanel;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.*;

public class DubInitForModuleStep extends ModuleWizardStep {

    private final JPanel myPanel;
//    protected final JPanel myAdditionalContentPanel;
//    protected NamePathComponent myNamePathComponent;
    protected final WizardContext myWizardContext;
//    @Nullable
//    protected final WizardMode myMode;
    private final ProjectFormatPanel myFormatPanel = new ProjectFormatPanel();

    public DubInitForModuleStep(WizardContext wizardContext) {
        this.myWizardContext = wizardContext;
//        this.myMode = mode;
//        this.myNamePathComponent = new NamePathComponent(IdeBundle.message("label.project.name", new Object[0]), IdeBundle.message("label.project.files.location", new Object[0]), IdeBundle.message("title.select.project.file.directory", new Object[]{IdeBundle.message("project.new.wizard.project.identification", new Object[0])}), IdeBundle.message("description.select.project.file.directory", new Object[]{StringUtil.capitalize(IdeBundle.message("project.new.wizard.project.identification", new Object[0]))}), true, false);
//        String baseDir = this.myWizardContext.getProjectFileDirectory();
//        String projectName = this.myWizardContext.getProjectName();
//        String initialProjectName = projectName != null?projectName: ProjectWizardUtil.findNonExistingFileName(baseDir, "untitled", "");
//        this.myNamePathComponent.setPath(projectName == null?baseDir + File.separator + initialProjectName:baseDir);
//        this.myNamePathComponent.setNameValue(initialProjectName);
//        this.myNamePathComponent.getNameComponent().select(0, initialProjectName.length());
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
        ComboBox dubFormat = new ComboBox(opts);
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
        ComboBox dubType = new ComboBox(typeOpts);
        dubType.setSelectedItem("minimal");

        JLabel dubTypeLabel = new JLabel("Dub project type");
        dubTypeLabel.setLabelFor(dubType);

        this.myPanel.add(dubTypeLabel, new GridBagConstraints(0, -1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 5, 4), 0, 0));
        this.myPanel.add(dubType, new GridBagConstraints(0, -1, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(10, 0, 20, 0), 0, 0));
        
        // add text field to add params instead of using combo boxes as fallback
        JTextField dubParams = new JTextField();
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
//        this.myWizardContext.setProjectName(this.getProjectName());
//        String projectFileDirectory = this.getProjectFileDirectory();
//        this.myWizardContext.setProjectFileDirectory(projectFileDirectory);
//        ProjectBuilder moduleBuilder = this.myWizardContext.getProjectBuilder();
//        if(moduleBuilder != null) {
//            this.myWizardContext.setProjectBuilder(moduleBuilder);
//            if(moduleBuilder instanceof ModuleBuilder) {
//                ((ModuleBuilder)moduleBuilder).setContentEntryPath(projectFileDirectory);
//            } else if(moduleBuilder instanceof ProjectFromSourcesBuilderImpl) {
//                ((ProjectFromSourcesBuilderImpl)moduleBuilder).setBaseProjectPath(projectFileDirectory);
//            }
//        }
//
//        this.myFormatPanel.updateData(this.myWizardContext);
    }

    public Icon getIcon() {
        return this.myWizardContext.getStepIcon();
    }

//    public JComponent getPreferredFocusedComponent() {
//        return this.myNamePathComponent.getNameComponent();
//    }
//
//    public String getHelpId() {
//        return "reference.dialogs.new.project.fromCode.name";
//    }
//
//    public String getProjectFileDirectory() {
//        return this.myNamePathComponent.getPath();
//    }
//
//    public String getProjectName() {
//        return this.myNamePathComponent.getNameValue();
//    }
//
//    public boolean validate() throws ConfigurationException {
//        return this.myNamePathComponent.validateNameAndPath(this.myWizardContext, this.myFormatPanel.isDefault());
//    }

}
