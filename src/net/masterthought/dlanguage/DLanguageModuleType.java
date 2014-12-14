package net.masterthought.dlanguage;

import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class DLanguageModuleType extends ModuleType<DLanguageModuleBuilder> {

    public static final String MODULE_TYPE_ID = "DLANGUAGE_MODULE";

    public DLanguageModuleType() {
        super(MODULE_TYPE_ID);
    }

    public static DLanguageModuleType getInstance() {
        return (DLanguageModuleType) ModuleTypeManager.getInstance().findByID(MODULE_TYPE_ID);
    }

    @NotNull
    @Override
    public DLanguageModuleBuilder createModuleBuilder() {
        return new DLanguageModuleBuilder();
    }

    @NotNull
    @Override
    public String getName() {
        return "DLanguage Module";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "D Language modules are used for developing <b>D</b> applications.";
    }

    @Override
    public Icon getBigIcon() {
        return DLanguageIcons.FILE;
    }

    @Override
    public Icon getNodeIcon(@Deprecated boolean isOpened) {
        return DLanguageIcons.FILE;
    }
}
