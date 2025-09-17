package io.github.intellij.dlanguage

import com.intellij.codeInsight.daemon.ProjectSdkSetupValidator
import com.intellij.openapi.application.smartReadAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.module.ModuleUtilCore
import com.intellij.openapi.progress.runBlockingCancellable
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.roots.OrderRootType
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.roots.ui.configuration.ProjectSettingsService
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.search.GlobalSearchScope.allScope
import com.intellij.ui.EditorNotificationPanel
import io.github.intellij.dlanguage.index.DModuleIndex
import io.github.intellij.dlanguage.psi.DlangPsiFile
import javax.swing.event.HyperlinkEvent


/**
 * This class is used to inform users that DMD is not setup for the project
 */
class DLangProjectDmdSetupValidator : ProjectSdkSetupValidator {

    // basic set of packages in phobos. Due to packages being moved around in phobos we can't check all of them.
    // At some point, if we can support language versions (need to implement com.intellij.openapi.projectRoots.Sdk)
    // then perhaps we can verify phobos packages relevant to the version of D being used.
    private val expectedModules = setOf(
        //"std.algorithm", // directory
        "std.array",
        "std.ascii",
        "std.base64",
        "std.bigint",
        "std.bitmanip",
        //"std.checkedint", see #770: Don't check "std.checkedint" as before D 2.099 it was "std.experimental.checkedint"
        "std.compiler",
        "std.complex",
        "std.concurrency",
        //"std.container", // directory
        "std.conv",
        "std.csv",
        //"std.datetime", // directory
        "std.demangle",
        //"std.digest", // directory
        "std.encoding",
        "std.exception",
        //"std.experimental", // directory
        "std.file",
        //"std.format", // directory
        "std.functional",
        "std.getopt",
        //"std.int128", // signed 128 bit integer type was added in 2.100.0 (we should check for it in later plugin releases)
        //"std.internal", // directory
        "std.json",
        //"std.math", // directory
        "std.mathspecial",
        "std.meta",
        "std.mmfile",
        "std.numeric",
        "std.outbuffer",
        "std.parallelism",
        "std.path",
        "std.process",
        "std.random", // The random number generators and API provided in this module are not designed to be cryptographically secure
        "std.signals",
        "std.socket",
        "std.stdint",
        "std.stdio",
        "std.string",
        "std.sumtype",
        "std.system",
        "std.traits",
        "std.typecons",
        "std.typetuple",
        //"std.uni", // directory
        "std.uni",
        "std.uri",
        "std.utf",
        "std.uuid",
        "std.variant", // the Algebraic template in this package is outdated and not recommended for use in new code. Instead, use std.sumtype.SumType.
        //"std.windows", // directory
        //"std.xml", // This module is considered out-dated and not up to Phobos' current standards. It will be removed from Phobos in 2.101.0
        "std.zip",
        "std.zlib"
    )

    override fun isApplicableFor(project: Project, file: VirtualFile): Boolean {
        if (DlangFileType != file.fileType) return false
        val projectSdk = ProjectRootManager.getInstance(project).projectSdk ?: return true
        val sdkType = projectSdk.sdkType as? DlangSdkType ?: return true

        if (!sdkType.isValidSdkHome(projectSdk.homePath.toString())) {
            return true
        }

        // Don't check for object.d or phobos unless the index is ready
        if(!DumbService.isDumb(project)) {
            return runBlockingCancellable {
                if (cannotFindObjectDotD(project)) {
                    return@runBlockingCancellable true
                }
                if (missingAnyPhobosFiles(project)) {
                    return@runBlockingCancellable true
                }
                false
            }
        }

        return false
    }

    private suspend fun missingAnyPhobosFiles(project: Project): Boolean =
        missingPhobosModules(project).isNotEmpty()

    private suspend fun missingPhobosModules(project: Project): List<String> {
        return smartReadAction(project) {
            val scope = allScope(project)
            expectedModules.filter {
                DModuleIndex.getFilesByModuleName(project, it, scope).isEmpty()
            }
        }
    }

    private suspend fun cannotFindObjectDotD(project: Project): Boolean {
        return smartReadAction(project) {
            DModuleIndex.getFilesByModuleName(project, "object", allScope(project)).toSet().firstOrNull()?.containingFile as DlangPsiFile? == null
        }
    }

    /*
    * This function replaces the previously used doFix()
    * It was changed when working on https://github.com/intellij-dlanguage/intellij-dlanguage/issues/679
    */
    override fun getFixHandler(project: Project, file: VirtualFile): EditorNotificationPanel.ActionHandler {
        return object : EditorNotificationPanel.ActionHandler {

            // This entrypoint is when the user chooses to fix the issue by clicking on the panel over the editor pane
            override fun handlePanelActionClick(panel: EditorNotificationPanel, event: HyperlinkEvent) = configureSdkForProject()

            // This entrypoint is when the user does Alt+Enter and chooses to fix the issue via context menu
            override fun handleQuickFixClick(editor: Editor, psiFile: PsiFile) = configureSdkForProject()

            fun configureSdkForProject() {
                if(!project.isDisposed) {
                    ProjectSettingsService.getInstance(project).openProjectSettings()
                }
            }

//            fun configureSdkForCurrentModule() {
//                ModuleUtilCore.findModuleForFile(file, project)?.let { module ->
//                    if(!module.isDisposed) {
//                        ProjectSettingsService.getInstance(project)
//                            .showModuleConfigurationDialog(module.name, ClasspathEditor.getName())
//                    }
//                }
//            }
        }
    }

    override fun getErrorMessage(project: Project, file: VirtualFile): String? {
        val module = ModuleUtilCore.findModuleForFile(file, project)

        if (module != null && !module.isDisposed) {
            val sdk = ModuleRootManager.getInstance(module).sdk
            if (sdk == null) {
                return if (ModuleRootManager.getInstance(module).isSdkInherited) {
                    DlangBundle.message("d.ui.project.sdk.not.defined")
                } else {
                    DlangBundle.message("d.ui.module.sdk.not.defined")
                }
            } else if(sdk.sdkType == DlangSdkType.getInstance() && sdk.rootProvider.getFiles(OrderRootType.SOURCES).isEmpty()) {
                return DlangBundle.message("d.ui.compilerconfig.missing.sourcepaths")
            } else if(sdk.sdkType == DlangSdkType.getInstance() && !DumbService.isDumb(project)) {
                return runBlockingCancellable {
                    if (cannotFindObjectDotD(project)) {
                        return@runBlockingCancellable DlangBundle.message("d.ui.dmd.object.path.not.set")
                    }
                    if (missingAnyPhobosFiles(project)) {
                        return@runBlockingCancellable DlangBundle.message("d.ui.dmd.phobos.path.not.set") + missingPhobosModules(project).foldRight("") { s: String, acc: String -> "$acc $s" }
                    }
                    null
                }
            }
        }
        return null
    }
}
