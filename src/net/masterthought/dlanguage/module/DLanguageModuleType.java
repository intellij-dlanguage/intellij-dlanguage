package net.masterthought.dlanguage.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectJdkForModuleStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import net.masterthought.dlanguage.DLanguageBundle;
import net.masterthought.dlanguage.icons.DLanguageIcons;
import net.masterthought.dlanguage.DLanguageSdkType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DLanguageModuleType extends ModuleType<DLanguageModuleBuilder> {
    @NonNls
    private static final String ID = "DLANGUAGE_MODULE";

    public DLanguageModuleType() {
        super(ID);
    }

    @NotNull
    @Override
    public DLanguageModuleBuilder createModuleBuilder() {
        return new DLanguageModuleBuilder();
    }

    @NotNull
    @Override
    public String getName() {
        return DLanguageBundle.message("module.title");
    }

    @NotNull
    @Override
    public String getDescription() {
        return DLanguageBundle.message("module.description");
    }

    @Override
    public Icon getBigIcon() {
        return DLanguageIcons.FILE;
    }

    @Override
    public Icon getNodeIcon(@Deprecated boolean b) {
        return DLanguageIcons.FILE;
    }

    public static DLanguageModuleType getInstance() {
        return (DLanguageModuleType) ModuleTypeManager.getInstance().findByID(ID);
    }

    @NotNull
    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext,
                                                @NotNull final DLanguageModuleBuilder moduleBuilder,
                                                @NotNull ModulesProvider modulesProvider) {
        
        List<ModuleWizardStep> steps = new ArrayList<>();
        
        ModuleWizardStep setCompiler = new ProjectJdkForModuleStep(wizardContext, DLanguageSdkType.getInstance()) {
            public void updateDataModel() {
                super.updateDataModel();
                moduleBuilder.setModuleJdk(getJdk());
            }
        };
        ModuleWizardStep setDubBinary = new DubBinaryForModuleStep(wizardContext);
        ModuleWizardStep setDubInit = new DubInitForModuleStep(wizardContext);

        steps.add(setCompiler);
        
        if((moduleBuilder.getBuilderId() != null && moduleBuilder.getBuilderId().equals("DLangDubApp"))){
            steps.add(setDubBinary);
            steps.add(setDubInit);
        }

        return steps.toArray(new ModuleWizardStep[steps.size()]);
    }

    public static Collection<Module> findModules(Project project){
      return ModuleUtil.getModulesOfType(project, DLanguageModuleType.getInstance());
    }
}
