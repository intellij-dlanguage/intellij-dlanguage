package io.github.intellij.dlanguage

import com.intellij.openapi.projectRoots.AdditionalDataConfigurable
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.SdkModel
import com.intellij.openapi.projectRoots.SdkModificator
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.NlsContexts.TabTitle
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.dsl.builder.BottomGap
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

/**
 * A configurable for setting additional options for the selected D compiler.
 * See: https://github.com/intellij-dlanguage/intellij-dlanguage/issues/1231
 *
 * @author Samael Bate (singingbush)
 * created on 18/05/2026
 * @since 1.40
 */
class DlangSdkAdditionalDataConfigurable(sdkModel: SdkModel, sdkModificator: SdkModificator) : AdditionalDataConfigurable {
    private companion object {
        @TabTitle const val TAB_NAME = "Additional Options"
    }
    private val BETTER_C = Key.create<Boolean?>("BETTER_C")
    private val DIP_1000 = Key.create<Boolean?>("DIP_1000")
    private var sdk: Sdk? = null
    private var enableBetterC = false
    private var enableDip1000 = false
    private lateinit var enableBetterCChecked: Cell<JBCheckBox>
    private lateinit var enableDip1000Checked: Cell<JBCheckBox>

    override fun setSdk(sdk: Sdk?) {
        this.sdk = sdk
        sdk?.let {
            enableBetterC = it.getUserData(BETTER_C) ?: false
            enableDip1000 = it.getUserData(DIP_1000) ?: false
        }
    }

    override fun createComponent(): JComponent = panel {
        group {
            row {
                text("These settings enable additional inspections")
            }.bottomGap(BottomGap.SMALL)

            // Option for enabling inspections for BetterC
            row("Better C") {
                enableBetterCChecked = checkBox("Enable Better C support")
                    .comment("This option will enable code inspections for Better C")
            }.browserLink("Visit Better C", "https://dlang.org/spec/betterc.html")

            // Option for enforcing use of safe code (dip1000)
            row("Memory Safety") {
                enableDip1000Checked = checkBox("Enable Memory Safe D support (dip1000)")
                    .comment("Add option for enforcing use of safe code (dip1000)")
            }.browserLink("Visit Memory-Safe D", "https://dlang.org/spec/memory-safe-d.html")
        }.bottomGap(BottomGap.MEDIUM)
    }

    override fun isModified(): Boolean {
        return true
    }

    override fun apply() {
        //sdkModificator.setSdkAdditionalData(SdkAdditionalDataBase());
        // should we save state to xml file?? eg: com.intellij.openapi.components.State
        sdk?.let {
            it.putUserData(BETTER_C, this.enableBetterC)
            it.putUserData(DIP_1000, this.enableDip1000)
        }
    }
}
