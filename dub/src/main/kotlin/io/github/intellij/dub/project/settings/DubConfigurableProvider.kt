package io.github.intellij.dub.project.settings

import com.intellij.icons.AllIcons
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.application.invokeLater
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.BoundConfigurable
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurableProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.NlsContexts.ConfigurableName
import com.intellij.ui.AnimatedIcon
import com.intellij.ui.DocumentAdapter
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.fields.ExtendableTextField
import com.intellij.ui.components.textFieldWithBrowseButton
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.Alarm
import io.github.intellij.dlanguage.settings.ToolKey
import io.github.intellij.dub.DubBundle
import io.github.intellij.dub.service.DubBinaryPathProvider
import javax.swing.SwingConstants
import javax.swing.event.DocumentEvent

class DubConfigurableProvider(private val project: Project) : ConfigurableProvider() {

    override fun createConfigurable(): Configurable = DubConfigurable(project, DubBundle.message("settings.dlang.dub.name"))
}

class DubConfigurable(
    private val project: Project,
    @ConfigurableName displayName: String
) : BoundConfigurable(displayName) {

    private val dubConfigurablePanel by lazy { DubConfigurablePanel() }
    override fun createPanel(): DialogPanel = panel {
        dubConfigurablePanel.attachTo(this, project)
    }

    override fun disposeUIResources() {
        super.disposeUIResources()
        Disposer.dispose(dubConfigurablePanel)
    }
}

interface ImprovedDisposable : Disposable {
    fun isDisposed(): Boolean
}

class DubConfigurablePanel() : ImprovedDisposable {

    private val uiDebouncer = UiDebouncer(this, 400)

    private val versionLabel = JBLabel(DubBundle.message("settings.dub.executable.detecting"), AnimatedIcon.Default(), SwingConstants.LEFT)

    private val executablePath = ExtendableTextField()

    private var isDisposed = false

    override fun dispose() {
        isDisposed = true
    }

    override fun isDisposed(): Boolean = isDisposed

    fun attachTo(panel: Panel, project: Project) = with(panel) {
        val executablePathSelector =
            textFieldWithBrowseButton(project, executablePath, FileChooserDescriptorFactory.createSingleFileOrExecutableAppDescriptor().withTitle("Select Dub Executable"))
        executablePath.text = ToolKey.DUB_KEY.path
        executablePath.emptyText.text = DubBundle.message("settings.dub.executable.detecting")
        executablePath.document.addDocumentListener(object : DocumentAdapter() {
            override fun textChanged(e: DocumentEvent) {
                executablePath.emptyText.text = DubBundle.message("settings.dub.executable.detecting")
                update()
            }
        })

        row(DubBundle.message("settings.dub.executable.location.label")) {
            cell(executablePathSelector).align(AlignX.FILL)
        }
        row("") {
            cell(versionLabel)
        }

        onApply {
            ToolKey.DUB_KEY.path = executablePath.text
        }
        onIsModified {
            executablePath.text != ToolKey.DUB_KEY.path
        }
        update()
    }

    fun update() {
        uiDebouncer.run(
            onPooledThread = {
                if (executablePath.text == null || executablePath.text.isEmpty())
                    DubBinaryPathProvider.getAutodetectPath()
                else
                    executablePath.text
            },
            onUiThread = { dubPath ->
                if (dubPath == null || dubPath.isEmpty()) {
                    // not provided and not found
                    versionLabel.icon = AllIcons.General.Error
                    versionLabel.text = DubBundle.message("settings.dub.executable.not-found")
                }
                else if (DubBinaryPathProvider.verifyDubBinary(dubPath)) {
                    val version = DubBinaryPathProvider.getVersionOutput(dubPath)
                    versionLabel.icon = AllIcons.General.GreenCheckmark
                    versionLabel.text = "Dub version $version"
                    executablePath.emptyText.text = "Detected: $dubPath"
                }
                else {
                    versionLabel.icon = AllIcons.General.Error
                    versionLabel.text = DubBundle.message("settings.dub.executable.invalid")
                }
            }
        )
    }

}

class UiDebouncer(
    private val parentDisposable: ImprovedDisposable,
    private val delayMillis: Int = 200
) {
    private val alarm = Alarm(Alarm.ThreadToUse.POOLED_THREAD, parentDisposable)

    /**
     * @param onUiThread: callback to be executed in EDT with **any** modality state.
     * Use it only for UI updates
     */
    fun <T> run(onPooledThread: () -> T, onUiThread: (T) -> Unit) {
        if (parentDisposable.isDisposed()) return
        alarm.cancelAllRequests()
        alarm.addRequest({
            val r = onPooledThread()
            invokeLater(ModalityState.any()) {
                if (!parentDisposable.isDisposed()) {
                    onUiThread(r)
                }
            }
        }, delayMillis)
    }
}
