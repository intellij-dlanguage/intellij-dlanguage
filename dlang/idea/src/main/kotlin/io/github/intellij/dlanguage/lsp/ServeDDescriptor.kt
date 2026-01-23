package io.github.intellij.dlanguage.lsp

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.icons.AllIcons
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.Lsp4jClient
import com.intellij.platform.lsp.api.LspServerNotificationsHandler
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor
import com.intellij.platform.lsp.api.customization.LspCompletionCustomizer
import com.intellij.platform.lsp.api.customization.LspCompletionSupport
import com.intellij.platform.lsp.api.customization.LspCustomization
import com.intellij.platform.lsp.api.customization.LspDocumentHighlightsCustomizer
import com.intellij.platform.lsp.api.customization.LspDocumentHighlightsDisabled
import com.intellij.platform.lsp.api.customization.LspDocumentSymbolCustomizer
import com.intellij.platform.lsp.api.customization.LspDocumentSymbolDisabled
import com.intellij.platform.lsp.api.customization.LspFindReferencesDisabled
import com.intellij.platform.lsp.api.customization.LspFoldingRangeDisabled
import com.intellij.platform.lsp.api.customization.LspGoToDefinitionDisabled
import com.intellij.platform.lsp.api.customization.LspGoToTypeDefinitionDisabled
import com.intellij.platform.lsp.api.customization.LspHoverDisabled
import com.intellij.platform.lsp.api.customization.LspInlayHintCustomizer
import com.intellij.platform.lsp.api.customization.LspInlayHintDisabled
import com.intellij.platform.lsp.api.customization.LspSemanticTokensDisabled
import com.intellij.platform.lsp.api.customization.LspSignatureHelpCustomizer
import com.intellij.platform.lsp.api.customization.LspSignatureHelpDisabled
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.lsp.settings.ServeDSettingsState
import org.eclipse.lsp4j.CompletionItem
import org.eclipse.lsp4j.CompletionItemKind
import java.util.Locale

class ServeDDescriptor(project: Project, /* val cmdBuilder: ServeDCommandLineBuilder */) : ProjectWideLspServerDescriptor(project, "Serve-D") {

//        private val serveDCommands = ServeDCommands()
//
//        // todo: implement custom commands if needed, or get rid of this
//        private class ServeDCommands : LspCommandsSupport() {
//            override fun executeCommand(server: LspServer, contextFile: VirtualFile, command: Command) {
//                thisLogger().info("Serve-D command: ${command.command}")
//                super.executeCommand(server, contextFile, command)
//            }
//        }

    override fun isSupportedFile(file: VirtualFile) = ProjectRootManager.getInstance(this.project)
        .fileIndex.isInProject(file)
        .and(file.fileType == DlangFileType)

    // binary path for serve-d is configured in ServeDSettings.kt
    override fun createCommandLine() =
        // potentially use ServeDCommandLineBuilder:
        // this.cmdBuilder.withBinaryPath(
        //     "/opt/serve-d_0.8.0-beta.18-linux-x86_64/serve-d"
        // )
        //     .provides(
        //         "http",
        //         "implement-snippets",
        //         "context-snippets",
        //         "default-snippets",
        //         "tasks-current",
        //         "async-ask-load"
        //     )
        //     .build()
    //Paths.get("/opt/serve-d_0.8.0-beta.18-linux-x86_64/serve-d").toFile()
    // todo: decide which "--provide" options we want to enable
        GeneralCommandLine(
            ApplicationManager.getApplication().getService(ServeDSettingsState::class.java).state.binaryPath,
            "--require", "D",
            "--lang", (Locale.getDefault() ?: Locale.UK).language,
//                "--provide", "http",
//                "--provide", "implement-snippets",
//                "--provide", "context-snippets",
//                "--provide", "default-snippets",
//                "--provide", "tasks-current",
//                "--provide", "async-ask-load"
        )


    /**
     * Required to handle custom requests and notifications from the serve-d LSP server.
     */
    override fun createLsp4jClient(handler: LspServerNotificationsHandler): Lsp4jClient = ServeDLanguageClient(handler)

    // code highlighting, references resolution, code completion, and hover info are implemented without using the LSP server
    override val lspCustomization: LspCustomization = object : LspCustomization() {
        override val semanticTokensCustomizer = LspSemanticTokensDisabled
        override val goToDefinitionCustomizer = LspGoToDefinitionDisabled // done using PSI
        override val goToTypeDefinitionCustomizer = LspGoToTypeDefinitionDisabled // done using PSI

        override val completionCustomizer: LspCompletionCustomizer = object : LspCompletionSupport() {
            override fun shouldRunCodeCompletion(parameters: CompletionParameters): Boolean {
                //thisLogger().debug("LSP Serve-D completion check for ${parameters.completionType.name}")
                // parameters.originalFile.fileType == io.github.intellij.dlanguage.DlangFileType
                // and check settings to see if user has set up completion via serve-d or direct DCD integration

                return CompletionType.BASIC == parameters.completionType // support BASIC completion
            }

            override fun createLookupElement(
                parameters: CompletionParameters,
                item: CompletionItem
            ): LookupElement? {
                thisLogger().debug("${parameters.completionType.name} completion for ${item.kind.name} : ${item.label}")
                val type: String = item.kind.name
                val name: String = item.label

                // code take from DCompletionContributor
                val icon = if (CompletionItemKind.Function == item.kind)
                    AllIcons.Nodes.Function // should perhaps use DlangIcons.NODE_FUNCTION
                else
                    if (CompletionItemKind.Variable == item.kind) AllIcons.Nodes.Variable else DLanguage.Icons.FILE

                return LookupElementBuilder.create(name)
                    .withItemTextItalic("Keyword" == type)
                    //.withItemTextForeground(JBColor.ORANGE) // tmp: just for testing
                    .withIcon(icon)
                    .withTypeText(type, true)
                    //.withTailText(" (" + module + ')', true)
                    //.withRenderer()
                    //.withAutoCompletionPolicy(AutoCompletionPolicy.SETTINGS_DEPENDENT)
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

        // override val commandsCustomizer: LspCommandsCustomizer
        //     get() = serveDCommands
    }
}
