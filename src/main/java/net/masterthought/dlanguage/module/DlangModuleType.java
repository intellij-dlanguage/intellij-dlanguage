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
import net.masterthought.dlanguage.DlangBundle;
import net.masterthought.dlanguage.DlangSdkType;
import net.masterthought.dlanguage.icons.DlangIcons;
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

    //    @Override
    public Icon getBigIcon() {
        return DlangIcons.FILE;
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

        final ModuleWizardStep setCompiler = new ProjectJdkForModuleStep(wizardContext, DlangSdkType.getInstance()) {
            public void updateDataModel() {
                super.updateDataModel();
                moduleBuilder.setModuleJdk(getJdk());
            }
        };
        final ModuleWizardStep setDubBinary = new DubBinaryForModuleStep(wizardContext);
        final ModuleWizardStep setDubInit = new DubInitForModuleStep(wizardContext);

        steps.add(setCompiler);

        if((moduleBuilder.getBuilderId() != null && moduleBuilder.getBuilderId().equals("DLangDubApp"))) {
            steps.add(setDubBinary);
            steps.add(setDubInit);
        }

        return steps.toArray(new ModuleWizardStep[steps.size()]);
    }
}
