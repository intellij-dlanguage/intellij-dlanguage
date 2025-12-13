package io.github.intellij.dlanguage.lsp.dto

data class WorkspaceStateParams(
    /** URI to the workspace folder */
    val uri: String,

    /** name of the workspace folder (or internal placeholder) */
    val name: String,

    /** true if this instance has been initialized */
    val initialized: Boolean,

    /** true if this is the active instance */
    val selected: Boolean,

    /**
     * May contain errors that are pending and will be shown once the user with
     * this workspace.
     *
     * Key: URI folder or file in which startup issues occured. (compare with
     * startsWith)
     * Value: human-readable message.
     */
    val pendingErrors: Map<String, String>
)

data class UpdateSettingParams(
    /** The configuration section to update in (e.g. "d" or "dfmt") */
    val section: String,
    /** The value to set the configuration value to */
    val value: Any,
    /** `true` if this is a configuration change across all instances and not just the active one */
    val global: Boolean
)

data class InteractiveDownloadParams(
    /** The URL to download */
    val url: String,

    /** The title to show in the UI popup for this download */
    val title: String?,

    /** The file path to write the downloaded file to */
    val output: String
)
