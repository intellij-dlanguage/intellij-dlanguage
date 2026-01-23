package io.github.intellij.dlanguage.lsp.settings

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.ui.validation.CHECK_NON_EMPTY
import com.intellij.openapi.ui.validation.DialogValidation
import com.intellij.openapi.util.NlsContexts
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.BottomGap
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.RightGap
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.selected
import com.intellij.ui.dsl.builder.trimmedTextValidation
import javax.swing.JComponent

/*
* For examples of using the DSL for ui, see NewProjectWizardBaseStep.kt
* https://github.com/JetBrains/intellij-community
*/
class ServeDSettings : SearchableConfigurable {

    private companion object {
        const val ID: String = "SERVE_D"
        const val DISPLAY_NAME: String = "D LSP (Serve-D)"
    }

    private lateinit var acceptRisk: Cell<JBCheckBox>
    private lateinit var serveDPath: Cell<TextFieldWithBrowseButton>

    private val CHECK_EXECUTABLE = DialogValidation.WithParameter<() -> String> { getStr ->
        DialogValidation { if (getStr().isEmpty()) error("Selected path is not executable") else null }
    }

    override fun getId() = ID

    override fun getDisplayName(): @NlsContexts.ConfigurableName String = DISPLAY_NAME

    override fun createComponent(): JComponent = panel {
            row {
                comment("LSP support for D is currently an experimental feature. ")
            }
            row {
                acceptRisk = checkBox("I understand this and accept the risk")
            }

            group {
                row("Serve D:") {
                    serveDPath = textFieldWithBrowseButton(
                        FileChooserDescriptorFactory.createSingleFileOrExecutableAppDescriptor().withTitle("Select Serve-D Executable"),
                        project=null,
                        fileChosen={ it.path },
                    )
                        .align(AlignX.FILL)
                        .trimmedTextValidation(
                            CHECK_NON_EMPTY,
                            CHECK_EXECUTABLE
                        )
                        //.focused()
                        .gap(RightGap.SMALL)
                        .comment("Select path to the Serve-D executable.")
                }.bottomGap(BottomGap.MEDIUM)

                row {
                    link("Download serve-d from GitHub") {
                        BrowserUtil.browse("https://github.com/Pure-D/serve-d/releases")
                    }
                }
            }.enabledIf(acceptRisk.selected)
        }

    override fun isModified(): Boolean {
        val settingsState: ServeDSettingsState = ApplicationManager.getApplication().getService(ServeDSettingsState::class.java)

        return settingsState.state != ServeDSettingsState.ServeDConfigSettings(
            binaryPath = this.serveDPath.component.text,
            acceptedPreviewFeature = this.acceptRisk.component.isSelected
        )
    }

    //override fun getHelpTopic(): @NonNls String? {
    //    return super.getHelpTopic()
    //}

    override fun reset() {
        val settingsState: ServeDSettingsState = ApplicationManager.getApplication().getService(ServeDSettingsState::class.java)

        this.acceptRisk.component.isSelected = settingsState.state.acceptedPreviewFeature
        this.serveDPath.component.text = settingsState.state.binaryPath
    }

    override fun apply() {
        val settingsState: ServeDSettingsState = ApplicationManager.getApplication().getService(ServeDSettingsState::class.java)

        settingsState.state.acceptedPreviewFeature = this.acceptRisk.component.isSelected
        settingsState.state.binaryPath = this.serveDPath.component.text

        // runWithModalProgressBlocking(ModalTaskOwner.guess(), "") {
        //     saveSettings(ApplicationManager.getApplication(), forceSavingAllSettings = true)
        // }
    }
}
