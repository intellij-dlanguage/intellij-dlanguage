package io.github.intellij.dlanguage.lsp

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.*
import com.intellij.platform.lsp.api.lsWidget.LspServerWidgetItem
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.lsp.settings.ServeDSettings
import io.github.intellij.dlanguage.lsp.settings.ServeDSettingsState

/**
 * Support for LSP using serve-d added for 2025.3 onward as Jetbrains now include LSP in the base platform.
 * See related IntelliJ Platform Plugin SDK docs: <a href="https://plugins.jetbrains.com/docs/intellij/language-server-protocol.html">Language Server Protocol (LSP)</a>.
 *
 * Development is done with serve-d v0.8.0-beta.18 (not going to test older versions)
 *
 * This extension is defined in serve-d.xml in :plugin-impl
 */
class ServeDProvider : LspServerSupportProvider {

    override fun fileOpened(
        project: Project,
        file: VirtualFile,
        serverStarter: LspServerSupportProvider.LspServerStarter
    ) {
        val application = ApplicationManager.getApplication()

        if (application.isUnitTestMode) {
            return // don't start LSP servers during unit tests
        }
        val isDlangSourceFile = ProjectRootManager.getInstance(project)
            .fileIndex.isInProject(file)
            .and(file.fileType == DlangFileType)

        val settingsState: ServeDSettingsState = application.getService(ServeDSettingsState::class.java)
        val canStart = settingsState.state.acceptedPreviewFeature && settingsState.state.binaryPath.isNotEmpty()

        if (isDlangSourceFile && canStart) {
            serverStarter.ensureServerStarted(
                descriptor = ServeDDescriptor(project)
                //descriptor = ServeDDescriptor(project, ServeDCommandLineBuilder())
            )
        }
    }

    override fun createLspServerWidgetItem(lspServer: LspServer, currentFile: VirtualFile?): LspServerWidgetItem? {
        return LspServerWidgetItem(
            lspServer,
            currentFile,
            DLanguage.Icons.DTOOL_SERVE_D,
            ServeDSettings::class.java // todo: finish working on ServeDSettings
        )
    }

}
