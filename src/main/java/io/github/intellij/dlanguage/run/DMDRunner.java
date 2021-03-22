package io.github.intellij.dlanguage.run;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import io.github.intellij.dlanguage.DlangSdkType;
import org.jetbrains.annotations.Nullable;

public class DMDRunner {

    public boolean isValidModule(@Nullable final Module module) {
        if(module == null || module.isDisposed()) {
            return false;
        }
        @Nullable final Sdk sdk = ModuleRootManager.getInstance(module).getSdk();
        return sdk != null && (sdk.getSdkType() instanceof DlangSdkType);
    }

//    public boolean ensureRunnerConfigured(@Nullable final Module module,
//                                          final RunProfile profile,
//                                          final Executor executor, final Project project) throws ExecutionException {
//        return false;  //To change body of implemented methods use File | Settings | File Templates.
//    }
}

