package io.github.intellij.dlanguage.run;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import io.github.intellij.dlanguage.DlangWritingAccessProvider;
import io.github.intellij.dlanguage.psi.DlangFile;
import io.github.intellij.dlanguage.DlangWritingAccessProvider;
import io.github.intellij.dlanguage.psi.DlangFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.utils.DUtil.isDunitTestFile;

public class DlangRunDubConfigurationProducer extends RunConfigurationProducer<DlangRunDubConfiguration> {


    public DlangRunDubConfigurationProducer() {
        super(new DlangRunDubConfigurationType());
    }

    @Nullable
    public static VirtualFile getRunnableDFileFromContext(final @NotNull ConfigurationContext context) {
        final PsiElement psiLocation = context.getPsiLocation();
        final PsiFile psiFile = psiLocation == null ? null : psiLocation.getContainingFile();
        final VirtualFile virtualFile = getRealVirtualFile(psiFile);

        if ((psiFile instanceof DlangFile) &&
            virtualFile != null &&
            ProjectRootManager.getInstance(context.getProject()).getFileIndex().isInContent(virtualFile) &&
            !DlangWritingAccessProvider.isInDLanguageSdkOrDLanguagePackagesFolder(psiFile.getProject(), virtualFile)) {

            // dont try to run this producer if is a test file
            if (isDunitTestFile(psiFile)) {
                return null;
            } else {
                return virtualFile;
            }
        }

        return null;
    }

    @Nullable
    private static VirtualFile getDFileFromContext(final @NotNull ConfigurationContext context) {
        final PsiElement psiLocation = context.getPsiLocation();
        final PsiFile psiFile = psiLocation == null ? null : psiLocation.getContainingFile();
        final VirtualFile virtualFile = getRealVirtualFile(psiFile);
        return psiFile instanceof DlangFile && virtualFile != null ? virtualFile : null;
    }

    public static VirtualFile getRealVirtualFile(final PsiFile psiFile) {
        return psiFile != null ? psiFile.getOriginalFile().getVirtualFile() : null;
    }

    @Override
    protected boolean setupConfigurationFromContext(final DlangRunDubConfiguration configuration, final ConfigurationContext context, final Ref<PsiElement> sourceElement) {
        final VirtualFile dFile = getRunnableDFileFromContext(context);
        if (dFile != null) {
            final Module module = context.getModule();
            if (module != null) {
                configuration.setModule(module);
            }
            configuration.setName("Dub");
            return true;
        }
        return false;
    }

    @Override
    public boolean isConfigurationFromContext(final @NotNull DlangRunDubConfiguration configuration,
                                              final @NotNull ConfigurationContext context) {
        final VirtualFile dFile = getDFileFromContext(context);
        return dFile != null;
    }


}
