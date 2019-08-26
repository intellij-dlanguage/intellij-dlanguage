package io.github.intellij.dlanguage.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectJdkForModuleStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import io.github.intellij.dlanguage.DlangBundle;
import io.github.intellij.dlanguage.DlangSdkType;
import io.github.intellij.dlanguage.icons.DlangIcons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DlangModuleType extends ModuleType<DlangModuleBuilder> {

    @NonNls
    private static final String ID = "DLANGUAGE_MODULE";

    public DlangModuleType() {
        super(ID);
    }

    public static DlangModuleType getInstance() {
        return (DlangModuleType) ModuleTypeManager.getInstance().findByID(ID);
    }

    public static Collection<Module> findModules(final Project project) {
        return ModuleUtil.getModulesOfType(project, DlangModuleType.getInstance());
    }

    @NotNull
    @Override
    public DlangModuleBuilder createModuleBuilder() {
        return new DlangModuleBuilder();
    }

    @NotNull
    @Override
    public String getName() {
        return DlangBundle.INSTANCE.message("module.title");
    }

    @NotNull
    @Override
    public String getDescription() {
        return DlangBundle.INSTANCE.message("module.description");
    }

    @Override
    public Icon getNodeIcon(@Deprecated final boolean b) {
        return DlangIcons.FILE;
    }

    @NotNull
    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull final WizardContext wizardContext,
                                                @NotNull final DlangModuleBuilder moduleBuilder,
                                                @NotNull final ModulesProvider modulesProvider) {
        final List<ModuleWizardStep> steps = new ArrayList<>();

        // todo: instead of using ProjectJdkForModuleStep, create a new ModuleWizardStep specific to configuring a D compiler
        // steps.add(new io.github.intellij.dlanguage.project.ui.DlangCompilerWizardStep(wizardContext, moduleBuilder));
        steps.add(new ProjectJdkForModuleStep(wizardContext, DlangSdkType.getInstance()) {
            public void updateDataModel() {
                super.updateDataModel();
                moduleBuilder.setModuleJdk(getJdk());
            }
        });

        if((moduleBuilder.getBuilderId() != null && moduleBuilder.getBuilderId().equals("DLangDubApp"))) {
            steps.add(new DubBinaryForModuleStep(wizardContext));
            steps.add(new DubInitForModuleStep(wizardContext));
        }

        return steps.toArray(new ModuleWizardStep[steps.size()]);
    }
}
