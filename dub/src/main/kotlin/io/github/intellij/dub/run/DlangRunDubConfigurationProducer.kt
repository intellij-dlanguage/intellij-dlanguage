package io.github.intellij.dub.run

import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.LazyRunConfigurationProducer
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.util.Ref
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import io.github.intellij.dlanguage.DlangWritingAccessProvider
import io.github.intellij.dlanguage.psi.DlangPsiFile
import io.github.intellij.dlanguage.utils.DUtil

class DlangRunDubConfigurationProducer : LazyRunConfigurationProducer<DlangRunDubConfiguration>() {
    private val runDubConfigurationType: DlangRunDubConfigurationType = DlangRunDubConfigurationType()

    private fun getRunnableDFileFromContext(context: ConfigurationContext): VirtualFile? {
        val psiLocation = context.psiLocation
        val psiFile = psiLocation?.containingFile
        val virtualFile = getRealVirtualFile(psiFile)
        return if (psiFile is DlangPsiFile && virtualFile != null &&
            ProjectRootManager.getInstance(context.project).fileIndex.isInContent(virtualFile) &&
            !DlangWritingAccessProvider.isInDLanguageSdkOrDLanguagePackagesFolder(psiFile.getProject(), virtualFile)
        ) {

            // don't try to run this producer if is a test file
            if (DUtil.isDunitTestFile(psiFile)) {
                log.debug(psiFile.getName() + " is a dunit test file")
                null
            } else {
                virtualFile
            }
        } else null
    }

    private fun getDFileFromContext(context: ConfigurationContext): VirtualFile? {
        val psiLocation = context.psiLocation
        val psiFile = psiLocation?.containingFile
        val virtualFile = getRealVirtualFile(psiFile)
        return if (psiFile is DlangPsiFile && virtualFile != null) virtualFile else null
    }

    private fun getRealVirtualFile(psiFile: PsiFile?): VirtualFile? {
        return psiFile?.originalFile?.virtualFile
    }

    override fun setupConfigurationFromContext(
        configuration: DlangRunDubConfiguration,
        context: ConfigurationContext,
        sourceElement: Ref<PsiElement>
    ): Boolean {
        val dFile = getRunnableDFileFromContext(context)
        if (dFile != null) {
            val module = context.module
            if (module != null) {
                configuration.setModule(module)
            }
            configuration.name = "Dub"
            return true
        }
        return false
    }

    override fun isConfigurationFromContext(
        configuration: DlangRunDubConfiguration,
        context: ConfigurationContext
    ): Boolean {
        val dFile = getDFileFromContext(context)
        return dFile != null
    }

    override fun getConfigurationFactory(): ConfigurationFactory {
        return runDubConfigurationType.configurationFactories[0]
    }

    companion object {
        private val log = Logger.getInstance(
            DlangRunDubConfigurationProducer::class.java
        )
    }
}
