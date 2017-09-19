package io.github.intellij.dlanguage.unittest;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import io.github.intellij.dlanguage.DlangWritingAccessProvider;
import io.github.intellij.dlanguage.psi.DlangFile;
import io.github.intellij.dlanguage.utils.DUtil;
import io.github.intellij.dlanguage.DlangWritingAccessProvider;
import io.github.intellij.dlanguage.psi.DlangFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.utils.DUtil.*;

public class DUnitTestRunConfigurationProducer extends RunConfigurationProducer<DUnitTestRunConfiguration> {


    public DUnitTestRunConfigurationProducer() {
        super(DUnitTestRunConfigurationType.getInstance());
    }

    @Override
    protected boolean setupConfigurationFromContext(final DUnitTestRunConfiguration configuration,
                                                    final ConfigurationContext context,
                                                    final Ref<PsiElement> sourceElement) {
        final VirtualFile dFile = getRunnableDFileFromContext(context);
        if (dFile != null) {
            configuration.setdFilePath(dFile.getPath());
            configuration.setWorkingDir(dFile.getParent().getPath());
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

        if ((psiFile instanceof DlangFile) &&
                virtualFile != null &&
                ProjectRootManager.getInstance(context.getProject()).getFileIndex().isInContent(virtualFile) &&
                !DlangWritingAccessProvider.isInDLanguageSdkOrDLanguagePackagesFolder(psiFile.getProject(), virtualFile)) {

            // only run this producer if is test file
            if (DUtil.isDunitTestFile(psiFile)) {
                return virtualFile;
            } else {
                return null;
            }
        }

        return null;
    }

    @Override
    public boolean isConfigurationFromContext(final @NotNull DUnitTestRunConfiguration configuration,
                                              final @NotNull ConfigurationContext context) {
        final VirtualFile dartFile = getDFileFromContext(context);
        return dartFile != null && dartFile.getPath().equals(configuration.getdFilePath());
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


}
