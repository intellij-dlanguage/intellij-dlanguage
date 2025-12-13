package io.github.intellij.dlanguage.lsp

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServer
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor
import com.intellij.platform.lsp.api.LspServerManager
import com.intellij.platform.lsp.api.customization.*
import com.intellij.platform.lsp.api.lsWidget.LspServerWidgetItem
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.settings.DLanguageToolsConfigurable

/**
 * Support for LSP using serve-d added for 2025.3 onward as Jetbrains now include LSP in the base platform.
 * See related IntelliJ Platform Plugin SDK docs: <a href="https://plugins.jetbrains.com/docs/intellij/language-server-protocol.html">Language Server Protocol (LSP)</a>.
 *
 * Development is done with serve-d v0.8.0-beta.18 (not going to test older versions)
 *
 */
class ServeDProvider() : LspServerSupportProvider {

    override fun fileOpened(
        project: Project,
        file: VirtualFile,
        serverStarter: LspServerSupportProvider.LspServerStarter
    ) {
        if (file.extension == "d") {
            serverStarter.ensureServerStarted(
                ServeDDescriptor(project)
            )
        }
    }

    override fun createLspServerWidgetItem(lspServer: LspServer, currentFile: VirtualFile?): LspServerWidgetItem? {
        return LspServerWidgetItem(
            lspServer,
            currentFile,
            DLanguage.Icons.SDK, // todo: Use the icon for Serve-D: https://code.dlang.org/packages/serve-d/logo?s=5af7654674f4ca845dbd3e01
            DLanguageToolsConfigurable::class.java // todo: make Serve-D configurable in D Tools
        )
    }

    private class ServeDDescriptor(project: Project) : ProjectWideLspServerDescriptor(project, "Serve-D") {

        override fun isSupportedFile(file: VirtualFile) = file.extension == "d"

        override fun createCommandLine() =
            GeneralCommandLine("/opt/serve-d-0.8.0-beta18/serve-d",
                "--require", "D",
                "--lang", "en", // todo: get this from the IDE environment
                "--provide", "http",
                "--provide", "implement-snippets",
                "--provide", "context-snippets",
                "--provide", "default-snippets",
                "--provide", "tasks-current",
                "--provide", "async-ask-load")

        // code highlighting, references resolution, code completion, and hover info are implemented without using the LSP server
        override val lspCustomization: LspCustomization = object : LspCustomization() {
            override val semanticTokensCustomizer = LspSemanticTokensDisabled
            override val goToDefinitionCustomizer = LspGoToDefinitionDisabled // done using PSI
            override val goToTypeDefinitionCustomizer = LspGoToTypeDefinitionDisabled // done using PSI

            override val completionCustomizer: LspCompletionCustomizer = object : LspCompletionSupport() {
                override fun shouldRunCodeCompletion(parameters: CompletionParameters): Boolean {
                    // parameters.originalFile.fileType == io.github.intellij.dlanguage.DlangFileType
                    // and check settings to see if use has set up completion via serve-d or direct DCD integration
                    return true
                }
            }

            override val hoverCustomizer = LspHoverDisabled // done using PSI
            override val findReferencesCustomizer = LspFindReferencesDisabled // done using PSI
            override val foldingRangeCustomizer = LspFoldingRangeDisabled // done using PSI
            override val inlayHintCustomizer: LspInlayHintCustomizer = LspInlayHintDisabled // done using PSI
            override val documentHighlightsCustomizer: LspDocumentHighlightsCustomizer = LspDocumentHighlightsDisabled // done using PSI
            override val documentSymbolCustomizer: LspDocumentSymbolCustomizer = LspDocumentSymbolDisabled // done using PSI
            override val signatureHelpCustomizer: LspSignatureHelpCustomizer = LspSignatureHelpDisabled // done using PSI

//            override val formattingCustomizer = object : LspFormattingSupport() {
//                override fun shouldFormatThisFileExclusivelyByServer(file: VirtualFile,
//                                                                     ideCanFormatThisFileItself: Boolean,
//                                                                     serverExplicitlyWantsToFormatThisFile: Boolean): Boolean {
//                    return file.fileType == io.github.intellij.dlanguage.DlangFileType // && check some option for formatting via serve-d
//                }
//            }
        }
    }


}
