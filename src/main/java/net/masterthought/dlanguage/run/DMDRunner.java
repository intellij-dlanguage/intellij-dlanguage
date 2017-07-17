package net.masterthought.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import net.masterthought.dlanguage.DLanguageSdkType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DMDRunner {
    public boolean isValidModule(@NotNull Module module) {
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        Sdk sdk = moduleRootManager.getSdk();
        return sdk != null && (sdk.getSdkType() instanceof DLanguageSdkType);
    }

    public boolean ensureRunnerConfigured(@Nullable Module module, RunProfile profile, Executor executor, Project project) throws ExecutionException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

