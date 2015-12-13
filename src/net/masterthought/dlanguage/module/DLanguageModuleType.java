package net.masterthought.dlanguage.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectJdkForModuleStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import net.masterthought.dlanguage.DLanguageBundle;
import net.masterthought.dlanguage.DLanguageIcons;
import net.masterthought.dlanguage.DLanguageSdkType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

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
        return new ModuleWizardStep[]{new ProjectJdkForModuleStep(wizardContext, DLanguageSdkType.getInstance()) {
            public void updateDataModel() {
                super.updateDataModel();
                moduleBuilder.setModuleJdk(getJdk());
            }
        }};
    }
}
