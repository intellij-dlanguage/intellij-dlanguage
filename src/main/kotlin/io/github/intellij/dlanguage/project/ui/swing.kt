package io.github.intellij.dlanguage.project.ui

import com.intellij.openapi.projectRoots.ProjectJdkTable
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.impl.SdkListCellRenderer
import com.intellij.openapi.ui.ComboBoxWithWidePopup
import com.intellij.ui.components.JBCheckBox
import io.github.intellij.dlanguage.DlangSdkType
import io.github.intellij.dlanguage.settings.ToolKey
import java.util.*
import javax.swing.DefaultComboBoxModel

// todo: consider some ordering/filtering for the model
class DmdCompilerComboBoxModel(sdks: List<Sdk>) : DefaultComboBoxModel<Sdk>(Vector<Sdk>(sdks))


/**
 * Displays a list of available D compilers (currently only dmd) to be used.
 */
class DmdCompilerComboBox :
    ComboBoxWithWidePopup<Sdk>(
        DmdCompilerComboBoxModel(ProjectJdkTable.getInstance().getSdksOfType(DlangSdkType.getInstance()))
    ) {
    init {
        setRenderer(SdkListCellRenderer("no dmd configured", false))
    }
}

/**
 * To be used when creating a new project. Allows the user to run 'dub init' during project creation
 */
class DubInitCheckBox : JBCheckBox("dub init") {
    init {
        val dubPath = ToolKey.DUB_KEY.path
        isSelected = dubPath?.isNotEmpty() ?: false
        isEnabled = dubPath?.isNotEmpty() ?: false

        if(dubPath == null || dubPath.isEmpty()) {
            text = "dub needs to be configured"
        }
    }
}
