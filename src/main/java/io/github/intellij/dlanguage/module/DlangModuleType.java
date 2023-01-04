package io.github.intellij.dlanguage.module;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.DlangBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Collection;

public class DlangModuleType extends ModuleType<DlangModuleBuilder> {

    @NonNls
    private static final String ID = DLanguage.MODULE_TYPE_ID;

    public DlangModuleType() {
        super(ID);
    }

    public static DlangModuleType getInstance() {
        return (DlangModuleType) ModuleTypeManager.getInstance().findByID(ID);
    }

    @Deprecated // use ModuleUtil.getModulesOfType(project, ModuleTypeManager.getInstance().findByID(DLanguage.MODULE_TYPE_ID));
    @NotNull
    public static Collection<Module> findModules(@NotNull final Project project) {
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
    public @NotNull Icon getIcon() {
        return DLanguage.Icons.FILE;
    }

    //@Override // todo: remove this overidden getter when it's removed from Intellij platform
    public @NotNull Icon getNodeIcon(@Deprecated final boolean b) {
        return DLanguage.Icons.FILE;
    }

}
