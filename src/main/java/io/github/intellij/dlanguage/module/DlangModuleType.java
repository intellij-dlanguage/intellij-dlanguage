package io.github.intellij.dlanguage.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import io.github.intellij.dlanguage.DlangBundle;
import io.github.intellij.dlanguage.icons.DlangIcons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Collection;

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

    /*
     * todo: Create some custom UI to be shown as the Custom Options Step in DlangModuleBuilder so that these additional wizard steps are no longer needed
     */
    @NotNull
    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull final WizardContext wizardContext,
                                                @NotNull final DlangModuleBuilder moduleBuilder,
                                                @NotNull final ModulesProvider modulesProvider) {
        if((moduleBuilder.getBuilderId() != null && moduleBuilder.getBuilderId().equals("DLangDubApp"))) {
            return new ModuleWizardStep[] {
                new DubBinaryForModuleStep(wizardContext),
                new DubInitForModuleStep(wizardContext)
            };
        }
        return ModuleWizardStep.EMPTY_ARRAY;
    }
}
