package io.github.intellij.dlanguage.lsp

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.Lsp4jClient
import com.intellij.platform.lsp.api.LspServerNotificationsHandler
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor
import com.intellij.platform.lsp.api.customization.LspCustomization
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.lsp.settings.ServeDSettingsState
import java.util.Locale

/*
* Serve-D comes bundled with dub, DCD-Client, Dfmt and DScanner compiled in but DCD-Server is still stand-alone.
* However, Serve-D will download and use its own DCD-Server so we no longer need users to configure it manually.
*
* @author Samael Bate (singingbush)
* created on 2026-04-06
*/
class ServeDDescriptor(project: Project, /* val cmdBuilder: ServeDCommandLineBuilder */) : ProjectWideLspServerDescriptor(project, "Serve-D") {

    override fun isSupportedFile(file: VirtualFile) = ProjectRootManager.getInstance(this.project)
        .fileIndex.isInProject(file)
        .and(file.fileType == DlangFileType)

    // binary path for serve-d is configured in ServeDSettings.kt
    override fun createCommandLine() = GeneralCommandLine(
            ApplicationManager.getApplication().getService(ServeDSettingsState::class.java).state.binaryPath,
            "--require", "D",
            "--loglevel", "all", // can accept "all|trace|info|warning|error|critical|fatal|off"
            "--lang", (Locale.getDefault() ?: Locale.UK).language,
//                "--provide", "http",
//                "--provide", "implement-snippets",
//                "--provide", "context-snippets",
//                "--provide", "default-snippets",
//                "--provide", "tasks-current",
//                "--provide", "async-ask-load"
        )

    // potentially could have some LSP setting specific to the workspace
//    override fun getWorkspaceConfiguration(item: ConfigurationItem): Any? {
//        return super.getWorkspaceConfiguration(item)
//    }

    /**
     * Required to handle custom requests and notifications from the serve-d LSP server.
     */
    override fun createLsp4jClient(handler: LspServerNotificationsHandler): Lsp4jClient = ServeDLanguageClient(handler)

    // code highlighting, references resolution, and hover info are implemented without using the LSP server
    override val lspCustomization: LspCustomization = ServeDCustomization()

}
