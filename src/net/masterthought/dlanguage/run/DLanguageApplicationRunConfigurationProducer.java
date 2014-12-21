package net.masterthought.dlanguage.run;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import net.masterthought.dlanguage.DLanguageFileType;
import net.masterthought.dlanguage.DLanguageWritingAccessProvider;
import net.masterthought.dlanguage.psi.DLanguageFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DLanguageApplicationRunConfigurationProducer extends RunConfigurationProducer<DLanguageApplicationRunConfiguration> {


    public DLanguageApplicationRunConfigurationProducer() {
        super(DLanguageApplicationConfigurationType.getInstance());
    }

    @Override
    protected boolean setupConfigurationFromContext(DLanguageApplicationRunConfiguration configuration, ConfigurationContext context, Ref<PsiElement> sourceElement) {
        final VirtualFile dFile = getRunnableDFileFromContext(context);
        if (dFile != null) {
            configuration.getRunnerParameters().setFilePath(dFile.getPath());
            configuration.getRunnerParameters().setWorkingDirectory(dFile.getParent().getPath());
            configuration.setGeneratedName();

            sourceElement.set(sourceElement.isNull() ? null : sourceElement.get().getContainingFile());
            return true;
        }
        return false;
    }

    @Nullable
    public static VirtualFile getRunnableDFileFromContext(final @NotNull ConfigurationContext context) {
        final PsiElement psiLocation = context.getPsiLocation();
        final PsiFile psiFile = psiLocation == null ? null : psiLocation.getContainingFile();
        final VirtualFile virtualFile = getRealVirtualFile(psiFile);

        if ((psiFile instanceof DLanguageFile) &&
                virtualFile != null &&
                ProjectRootManager.getInstance(context.getProject()).getFileIndex().isInContent(virtualFile) &&
                !DLanguageWritingAccessProvider.isInDLanguageSdkOrDLanguagePackagesFolder(psiFile.getProject(), virtualFile)) {
            return virtualFile;
        }

        return null;
    }

    @Override
    public boolean isConfigurationFromContext(final @NotNull DLanguageApplicationRunConfiguration configuration,
                                              final @NotNull ConfigurationContext context) {
        final VirtualFile dartFile = getDFileFromContext(context);
        return dartFile != null && dartFile.getPath().equals(configuration.getRunnerParameters().getFilePath());
    }

    @Nullable
    private static VirtualFile getDFileFromContext(final @NotNull ConfigurationContext context) {
        final PsiElement psiLocation = context.getPsiLocation();
        final PsiFile psiFile = psiLocation == null ? null : psiLocation.getContainingFile();
        final VirtualFile virtualFile = getRealVirtualFile(psiFile);
        return psiFile instanceof DLanguageFile && virtualFile != null ? virtualFile : null;
    }

    public static VirtualFile getRealVirtualFile(PsiFile psiFile) {
        return psiFile != null ? psiFile.getOriginalFile().getVirtualFile() : null;
    }


}
