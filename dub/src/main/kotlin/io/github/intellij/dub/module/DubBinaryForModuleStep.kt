package io.github.intellij.dub.module

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.util.text.StringUtil
import com.intellij.projectImport.ProjectFormatPanel
import com.intellij.util.ui.JBUI
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangBundle.message
import io.github.intellij.dlanguage.settings.DLanguageToolsConfigurable
import io.github.intellij.dlanguage.settings.ToolKey
import io.github.intellij.dub.project.DubProjectImportBuilder
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.ActionEvent
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import javax.swing.*

class DubBinaryForModuleStep(private val myWizardContext: WizardContext) : ModuleWizardStep() {
    private val myPanel: JPanel
    private val myFormatPanel = ProjectFormatPanel()
    private val dubBinary: TextFieldWithBrowseButton
    override fun getPreferredFocusedComponent(): JComponent {
        return dubBinary
    }

    override fun getComponent(): JComponent {
        return myPanel
    }

    override fun isStepVisible(): Boolean {
        return myWizardContext.project == null
    }

    /**
     * Commits data from UI into ModuleBuilder and WizardContext
     */
    override fun updateDataModel() {
        val moduleBuilder = myWizardContext.projectBuilder
        val dubBinaryPath = dubBinary.text
        if (StringUtil.isEmpty(ToolKey.DUB_KEY.path) && StringUtil.isNotEmpty(dubBinaryPath)) {
            ToolKey.DUB_KEY.path = dubBinaryPath
        }
        if (moduleBuilder != null && !dubBinaryPath.isEmpty()) {
            myWizardContext.projectBuilder = moduleBuilder
            if (moduleBuilder is ModuleBuilder) {
                val builder = moduleBuilder
                if (builder.builderId != null && DlangDubModuleBuilder.BUILDER_ID == builder.builderId) {
                    val dubBuilder = builder as DlangDubModuleBuilder
                    dubBuilder.setDubBinary(dubBinaryPath)
                }
            } else if (moduleBuilder is DubProjectImportBuilder) {
                moduleBuilder.getParameters().dubBinary = dubBinaryPath
            }
        }
        myFormatPanel.updateData(myWizardContext)
    }

    /**
     * Update UI from ModuleBuilder and WizardContext
     */
    override fun updateStep() {
        super.updateStep()
    }

    /**
     * Validates user input before [.updateDataModel] is called.
     *
     * @return `true` if input is valid, `false` otherwise
     * @throws ConfigurationException if input is not valid and needs user attention. Exception message will be displayed to user
     */
    @Throws(ConfigurationException::class)
    override fun validate(): Boolean {
        val dubBinaryPath = StringUtil.trim(dubBinary.text)
        if (StringUtil.isEmpty(dubBinaryPath)) {
            throw ConfigurationException(
                message("d.ui.dub.config.error.path-not-set"),
                message("d.ui.dub.config.error.title")
            )
        } else if (!Files.isExecutable(Paths.get(dubBinaryPath))) {
            throw ConfigurationException(
                message("d.ui.dub.config.error.path-not-executable"),
                message("d.ui.dub.config.error.title")
            )
        } else if (StringUtil.isEmpty(DLanguageToolsConfigurable.getVersion(dubBinaryPath))) {
            throw ConfigurationException(
                message("d.ui.dub.config.error.path-not-valid"),
                message("d.ui.dub.config.error.title")
            )
        }
        return true
    }

    override fun getIcon(): Icon? {
        return myWizardContext.stepIcon
    }

    private fun autoFindDubBinary(event: ActionEvent) {
        val optionalPath = Arrays.stream(STANDARD_DUB_EXE_PATHS)
            .filter { p: Path? -> Files.exists(p) && Files.isExecutable(p) }
            .findFirst()
        if (optionalPath.isPresent) {
            dubBinary.text = optionalPath.get().toString()
        } else {
            val foundPath = locateViaCommandline()
            if (StringUtil.isEmpty(foundPath)) {
                Messages.showErrorDialog("Could not find 'dub'.", "DLanguage")
            } else if (!Files.isExecutable(Paths.get(foundPath))) {
                Messages.showErrorDialog("$foundPath is not executable.", "DLanguage")
            } else {
                dubBinary.text = StringUtil.trim(foundPath)
            }
        }
    }

    /**
     * Attempt to find the D Tool by looking on the PATH
     *
     * @return either the found tool path or null
     */
    private fun locateViaCommandline(): String? {
        val cmd = GeneralCommandLine()
            .withExePath(if (SystemInfo.isWindows) "cmd" else "/bin/sh")
            .withParameters(
                if (SystemInfo.isWindows) "/c" else "-c",
                if (SystemInfo.isWindows) "where" else "which",
                "dub"
            )
        try {
            val path = ApplicationManager.getApplication().executeOnPooledThread<String?> {
                try {
                    return@executeOnPooledThread CapturingProcessHandler(
                        cmd.createProcess(),
                        cmd.charset,
                        cmd.commandLineString
                    )
                        .runProcess()
                        .stdout
                } catch (e: ExecutionException) {
                    log.warn(String.format("Failed to run '%s'.", "dub"), e)
                }
                null
            }[500, TimeUnit.MILLISECONDS]
            if (path != null && SystemInfo.isWindows && path.contains("C:\\")) {
                // not sure if this is actually needed. Was moved over from ExecUtil
                val split = path.split("(?=C:\\\\)".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                log.info("Multiple paths found for " + "dub")
                return split[0] // if there are multiple results default to first one.
            }
            return path
        } catch (e: InterruptedException) {
            log.warn(String.format("Failed to run '%s'.", "dub"), e)
        } catch (e: java.util.concurrent.ExecutionException) {
            log.warn(String.format("Failed to run '%s'.", "dub"), e)
        } catch (e: TimeoutException) {
            log.warn(String.format("Failed to run '%s'.", "dub"), e)
        }
        return null
    }

    init {
        myPanel = JPanel(GridBagLayout())
        myPanel.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

        // Title label
        val titletextLabel = JLabel(message("d.ui.dub.config.label.choosedublocation"))
        myPanel.add(titletextLabel, GridBagConstraints(0, -1, 1, 1, 1.0, 0.0, 18, 2, JBUI.insets(8, 10), 0, 0))
        dubBinary = TextFieldWithBrowseButton()
        if (StringUtil.isNotEmpty(ToolKey.DUB_KEY.path)) {
            dubBinary.setText(ToolKey.DUB_KEY.path)
        }
        val autoFindButton = JButton(message("d.ui.dub.config.label.autofind"), DLanguage.Icons.SDK)
        val dubFormatLabel = JLabel(message("d.ui.dub.config.label.dubbinarylocation"))
        dubFormatLabel.labelFor = dubBinary
        dubBinary.addBrowseFolderListener(
            String.format("Select %s executable", "dub"),
            "",
            null,
            FileChooserDescriptorFactory.createSingleLocalFileDescriptor()
        )
        autoFindButton.addActionListener { event: ActionEvent -> autoFindDubBinary(event) }
        myPanel.add(dubFormatLabel, GridBagConstraints(0, -1, 1, 1, 0.0, 0.0, 17, 0, JBUI.insets(0, 0, 5, 4), 0, 0))
        myPanel.add(dubBinary, GridBagConstraints(0, -1, 1, 1, 1.0, 0.0, 18, 2, JBUI.insets(10, 0, 20, 0), 0, 0))
        myPanel.add(autoFindButton, GridBagConstraints(0, -1, 1, 1, 1.0, 0.0, 18, 2, JBUI.insets(10, 0, 20, 0), 0, 0))
    }

    companion object {
        private val log = Logger.getInstance(
            DubBinaryForModuleStep::class.java
        )
        private val STANDARD_DUB_EXE_PATHS: Array<Path>

        init {
            if (SystemInfo.isWindows) {
                STANDARD_DUB_EXE_PATHS = arrayOf(
                    Paths.get("\\D\\dmd2\\windows\\bin\\dub.exe")
                )
            } else if (SystemInfo.isMac) {
                STANDARD_DUB_EXE_PATHS = arrayOf(
                    Paths.get("/usr/local/opt/dub") // homebrew
                )
            } else if (SystemInfo.isUnix) {
                STANDARD_DUB_EXE_PATHS = arrayOf(
                    Paths.get("/usr/local/bin/dub"),
                    Paths.get("/usr/bin/dub"),
                    Paths.get("/snap/bin/dub"),  // #575 support snaps
                    Paths.get(System.getProperty("user.home") + "/bin/dub")
                )
            } else {
                log.warn(String.format("D language plugin does not support %s", SystemInfo.getOsNameAndVersion()))
                STANDARD_DUB_EXE_PATHS = arrayOf()
            }
        }
    }
}
