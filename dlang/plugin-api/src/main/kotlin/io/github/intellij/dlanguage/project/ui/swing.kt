package io.github.intellij.dlanguage.project.ui

import com.intellij.openapi.projectRoots.ProjectJdkTable
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.ui.ComboBoxWithWidePopup
import com.intellij.ui.ColoredListCellRenderer
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.components.JBCheckBox
import io.github.intellij.dlanguage.DlangSdkType
import io.github.intellij.dlanguage.settings.ToolKey
import java.util.*
import javax.swing.DefaultComboBoxModel
import javax.swing.JList

// todo: consider some ordering/filtering for the model
class DmdCompilerComboBoxModel(sdks: List<Sdk>) : DefaultComboBoxModel<Sdk>(Vector<Sdk>(sdks))


/**
 * Displays a list of available D compilers (currently only dmd) to be used.
 *
 * use com.intellij.openapi.roots.ui.configuration.JdkComboBox as a reference.
 */
class DmdCompilerComboBox :
    ComboBoxWithWidePopup<Sdk>(
        DmdCompilerComboBoxModel(ProjectJdkTable.getInstance().getSdksOfType(DlangSdkType.getInstance()))
    ) {
    init {
        setRenderer(DmdCompilerCellRenderer())
    }
}

/**
 * Customise the layout of each cell in a DmdCompilerComboBox
 */
class DmdCompilerCellRenderer : ColoredListCellRenderer<Sdk>() {
    override fun customizeCellRenderer(list: JList<out Sdk>, value: Sdk?, index: Int, selected: Boolean, hasFocus: Boolean) {
        if(value == null) {
            append("no D compiler configured")
        } else {
            when (value.sdkType) {
                is DlangSdkType -> {
                    value.homePath?.let {
                        val type = DlangSdkType.getInstance()
                        icon = type.icon
                        val version = type.getVersionString(it)
                        append(version ?: type.presentableName)
                        append(" ($it)", SimpleTextAttributes.GRAYED_ATTRIBUTES)
                    }

                }
            }
        }
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
