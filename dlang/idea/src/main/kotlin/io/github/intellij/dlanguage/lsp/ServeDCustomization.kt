package io.github.intellij.dlanguage.lsp

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.customization.LspCodeActionsCustomizer
import com.intellij.platform.lsp.api.customization.LspCodeActionsSupport
import com.intellij.platform.lsp.api.customization.LspCompletionCustomizer
import com.intellij.platform.lsp.api.customization.LspCompletionSupport
import com.intellij.platform.lsp.api.customization.LspCustomization
import com.intellij.platform.lsp.api.customization.LspDiagnosticsCustomizer
import com.intellij.platform.lsp.api.customization.LspDiagnosticsSupport
import com.intellij.platform.lsp.api.customization.LspDocumentHighlightsCustomizer
import com.intellij.platform.lsp.api.customization.LspDocumentHighlightsDisabled
import com.intellij.platform.lsp.api.customization.LspDocumentSymbolCustomizer
import com.intellij.platform.lsp.api.customization.LspDocumentSymbolDisabled
import com.intellij.platform.lsp.api.customization.LspFindReferencesDisabled
import com.intellij.platform.lsp.api.customization.LspFoldingRangeDisabled
import com.intellij.platform.lsp.api.customization.LspFormattingSupport
import com.intellij.platform.lsp.api.customization.LspGoToDefinitionDisabled
import com.intellij.platform.lsp.api.customization.LspGoToTypeDefinitionDisabled
import com.intellij.platform.lsp.api.customization.LspHoverDisabled
import com.intellij.platform.lsp.api.customization.LspInlayHintCustomizer
import com.intellij.platform.lsp.api.customization.LspInlayHintDisabled
import com.intellij.platform.lsp.api.customization.LspSemanticTokensDisabled
import com.intellij.platform.lsp.api.customization.LspSignatureHelpCustomizer
import com.intellij.platform.lsp.api.customization.LspSignatureHelpDisabled
import io.github.intellij.dlanguage.DlangFileType

/*
* Some features should still be handled within the plugin. This class allows us to control which features are
* enabled/disabled for the LSP provider (serve-d).
*
* @author Samael Bate (singingbush)
* created on 2026-04-06
*/
class ServeDCustomization : LspCustomization() {
    override val completionCustomizer: LspCompletionCustomizer = object : LspCompletionSupport() {
        override fun shouldRunCodeCompletion(parameters: CompletionParameters): Boolean {
            thisLogger().debug("LSP Serve-D ${parameters.completionType.name} completion check for ${parameters.originalFile.name}")
            // parameters.originalFile.fileType == io.github.intellij.dlanguage.DlangFileType
            // and check settings to see if user has set up completion via serve-d or direct DCD integration
            return CompletionType.BASIC == parameters.completionType // support BASIC completion
        }

        // Not essential but we should be able to override the way in which each LookupElement is displayed in the UI.
        // However, for some reason implementing this was preventing the completion results from working.
//            override fun createLookupElement(
//                parameters: CompletionParameters,
//                item: CompletionItem
//            ): LookupElement? {
//                thisLogger().debug("${parameters.completionType.name} completion for ${item.kind.name} : ${item.label}")
//
//                val icon: javax.swing.Icon = when (item.kind) {
//                    CompletionItemKind.Function -> AllIcons.Nodes.Function // should perhaps use DlangIcons.NODE_FUNCTION
//                    CompletionItemKind.Variable -> AllIcons.Nodes.Variable
//                    CompletionItemKind.Field -> AllIcons.Nodes.Field
//                    CompletionItemKind.Struct -> AllIcons.Nodes.Variable
//                    CompletionItemKind.Keyword -> AllIcons.Nodes.Constant
//                    //CompletionItemKind.EnumMember -> AllIcons.Nodes.Variable
//                    //CompletionItemKind.Reference -> AllIcons.Nodes.Variable
//                    //CompletionItemKind.Property -> AllIcons.Nodes.Variable
//                    else -> DLanguage.Icons.FILE
//                }
//
//                return LookupElementBuilder.create(item.label)
//                    .withItemTextItalic(CompletionItemKind.Keyword == item.kind)
//                    //.withItemTextForeground(JBColor.ORANGE) // tmp: just for testing
//                    .withIcon(icon)
//                    .withTypeText(item.kind.name, true)
//                    .withStrikeoutness(item.deprecated ?: false)
//                    .withAutoCompletionPolicy(AutoCompletionPolicy.SETTINGS_DEPENDENT)
//            }
    }

    override val semanticTokensCustomizer = LspSemanticTokensDisabled // done using PSI
    override val goToDefinitionCustomizer = LspGoToDefinitionDisabled // done using PSI
    override val goToTypeDefinitionCustomizer = LspGoToTypeDefinitionDisabled // done using PSI
    override val hoverCustomizer = LspHoverDisabled // done using PSI
    override val findReferencesCustomizer = LspFindReferencesDisabled // done using PSI
    override val foldingRangeCustomizer = LspFoldingRangeDisabled // done using PSI
    override val inlayHintCustomizer: LspInlayHintCustomizer = LspInlayHintDisabled // done using PSI
    override val documentHighlightsCustomizer: LspDocumentHighlightsCustomizer = LspDocumentHighlightsDisabled // done using PSI
    override val documentSymbolCustomizer: LspDocumentSymbolCustomizer = LspDocumentSymbolDisabled // done using PSI
    override val signatureHelpCustomizer: LspSignatureHelpCustomizer = LspSignatureHelpDisabled // done using PSI

    // Rather than using dfmt via D Tools, use it via Serve-D
    override val formattingCustomizer = object : LspFormattingSupport() {
        override fun shouldFormatThisFileExclusivelyByServer(file: VirtualFile,
                                                             ideCanFormatThisFileItself: Boolean,
                                                             serverExplicitlyWantsToFormatThisFile: Boolean): Boolean {
            return file.fileType == DlangFileType // && check some option for formatting via serve-d
        }
    }

    override val diagnosticsCustomizer: LspDiagnosticsCustomizer = object : LspDiagnosticsSupport() {
        override fun shouldAskServerForDiagnostics(file: VirtualFile): Boolean = file.fileType == DlangFileType
        //override fun getTooltip(diagnostic: Diagnostic): @NlsSafe String = diagnostic.message
    }

    // todo: implement custom commands from serve-d
//        override val commandsCustomizer: LspCommandsCustomizer = object : LspCommandsSupport() {
//            override fun executeCommand(
//                server: LspServer,
//                contextFile: VirtualFile,
//                command: Command
//            ) {
//                thisLogger().info("Serve-D command: ${command.command}")
//
//                if ("window/logMessage" == command.command) {
//                    NotificationGroupManager.getInstance()
//                        .getNotificationGroup("SERVED")
//                        .createNotification(
//                            "Serve-D",
//                            "$command",
//                            NotificationType.WARNING
//                        )
//                        //.addAction(DToolsNotificationAction("Configure"))
//                        .notify(project)
//                } else
//                    super.executeCommand(server, contextFile, command)
//            }
//        }

        override val codeActionsCustomizer: LspCodeActionsCustomizer = object : LspCodeActionsSupport() {
            override val quickFixesSupport: Boolean = true
            override val intentionActionsSupport: Boolean = true
        }
}
