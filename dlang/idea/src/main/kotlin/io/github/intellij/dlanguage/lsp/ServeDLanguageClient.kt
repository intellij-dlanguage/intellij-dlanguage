package io.github.intellij.dlanguage.lsp

import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.platform.lsp.api.Lsp4jClient
import com.intellij.platform.lsp.api.LspServerNotificationsHandler
import io.github.intellij.dlanguage.lsp.dto.InteractiveDownloadParams
import io.github.intellij.dlanguage.lsp.dto.UpdateSettingParams
import io.github.intellij.dlanguage.lsp.dto.WorkspaceStateParams
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification
import java.util.concurrent.CompletableFuture


/**
 * Client to handle notifications from the Serve-D language server.
 *
 * We need to extend Lsp4jClient to add custom handlers as the serve-d LSP has custom requests and notifications
 * which are not part of the standard LSP specification. To handle these, we define methods in this class
 * annotated with @JsonRequest or @JsonNotification as appropriate.
 *
 * serve-d defines a variety of custom requests and notifications for better editor integration.
 * All requests starting with 'coded/' are sent from serve-d to the client and all requests starting
 * with 'served/' are sent from client to serve-d at any time.
 *
 * @author Samael Bate (singingbush)
 * created on 2024-06-10
 */
class ServeDLanguageClient(handler: LspServerNotificationsHandler) : Lsp4jClient(handler) {


    /*
    * todo: implement all the custom requests and notifications defined by serve-d.
    *
    * Some examples of which are:
    *
    * Requests:
    *  - "served/buildTasks" : returns Task[]
    *
    * Notifications:
    * - "served/doDscanner"
    * - "served/searchFile"
    * - "served/findFilesByModule"
    * - "served/getDscannerConfig"
    * - "served/getActiveDubConfig"
    * - "served/getProfileGCEntries"
    *
    * this is not a complete list, see serve-d source for all custom requests and notifications.
    */


    @JsonNotification("coded/changedSelectedWorkspace")
    fun changedSelectedWorkspace(params: WorkspaceStateParams) {
        thisLogger().info("Received coded/changedSelectedWorkspace notification with params: ${params}")
        // todo: complete implementation to handle workspace change
    }


    /**
     * Client request coded/interactiveDownload
     * Instructs the client to download a file into a given output path using download UI.
     *
     * Params: InteractiveDownload object:
     * {
     *   url: string;
     * 	 title?: string; The title to show in the UI popup for this download
     * 	 output: string; The file path to write the downloaded file to
     * }
     *
     * Returns: boolean; trust if the download was successful, false otherwise.
     *
     * This must be implemented if '--provide http' is given to serve-d in the command line, otherwise this is not called.
     */
    @JsonNotification("coded/interactiveDownload")
    fun interactiveDownload(params: InteractiveDownloadParams): CompletableFuture<Boolean> {
        // params will be json object like:
        // {
        //   url=https://github.com/dlang-community/DCD/releases/download/v0.15.2/dcd-v0.15.2-linux-x86_64.tar.gz,
        //   title=Downloading DCD...,
        //   output=/home/samael/.local/share/code-d/bin/dcd-v0.15.2-linux-x86_64.tar.gz
        // }
        thisLogger().info("Received coded/interactiveDownload notification with params: ${params}")

        // todo: manually handle downloading the file with progress UI and save to output path, then return true if successful
        // note that until we do this "--provide http" has been removed from the command line args in ServeDProvider
        return CompletableFuture.supplyAsync { false }
    }

    @JsonNotification("coded/logInstall")
    fun logInstall(message: String) {
        // params will be a string like:
        // "Installing DCD: DCD is outdated. Expected: 0.15.2, got none"
        // "Downloading from https://github.com/dlang-community/DCD/releases/download/v0.15.2/dcd-v0.15.2-linux-x86_64.tar.gz to /home/samael/.local/share/code-d/bin"
        // "Extracting download..."
        // "> tar xvfz dcd-v0.15.2-linux-x86_64.tar.gz"
        // "dcd-client"
        // "dcd-server"
        // "Successfully installed DCD"
        thisLogger().info("Received coded/logInstall notification: $message")
        // todo: complete implementation to show messages in UI
    }


    @JsonNotification("coded/updateSetting")
    fun updateSetting(params: UpdateSettingParams) {
        // {
        //   section=dcdClientPath,
        //   value=dcd-client,
        //   global=true
        // }
        // could be handy to have serve-d control the location of dcd-client and dcd-server but we'll
        // need to store these in D Tool settings and make sure it's only controlled by serve-d if the
        // user has opted to do so. Some users may want to use their own installation.
        thisLogger().info("Received coded/updateSetting notification with params: ${params}")
        // todo: complete implementation to update plugin settings accordingly
    }
}
