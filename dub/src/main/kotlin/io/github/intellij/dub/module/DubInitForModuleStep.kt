package io.github.intellij.dub.module

import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.ui.ComboBox
import com.intellij.util.ui.JBUI
import io.github.intellij.dlanguage.DlangBundle.message
import io.github.intellij.dlanguage.module.DlangProjectFormatPanel
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*

class DubInitForModuleStep(protected val myWizardContext: WizardContext) : ModuleWizardStep() {
    private val myPanel: JPanel
    private val myFormatPanel = DlangProjectFormatPanel()
    private val dubFormat: ComboBox<String>
    private val dubType: ComboBox<String>
    private val dubParams: JTextField

    init {
        myPanel = JPanel(GridBagLayout())
        myPanel.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

        // Title label
        val titletextLabel = JLabel(message("d.ui.dub.config.label.choosedubinitoptions"))
        myPanel.add(titletextLabel, GridBagConstraints(0, -1, 1, 1, 1.0, 0.0, 18, 2, JBUI.insets(8, 10, 8, 10), 0, 0))

        // add combo to choose --format sdl/json for init
        dubFormat = ComboBox(arrayOf("sdl", "json"))
        dubFormat.selectedItem = "json"
        val dubFormatLabel = JLabel(message("d.ui.dub.config.label.dubformat"))
        dubFormatLabel.labelFor = dubFormat
        myPanel.add(dubFormatLabel, GridBagConstraints(0, -1, 1, 1, 0.0, 0.0, 17, 0, JBUI.insets(0, 0, 5, 4), 0, 0))
        myPanel.add(dubFormat, GridBagConstraints(0, -1, 1, 1, 1.0, 0.0, 18, 2, JBUI.insets(10, 0, 20, 0), 0, 0))

        // add combo to choose --type minimal, vibe.d, deimos
        dubType = ComboBox(arrayOf("minimal", "vibe.d", "deimos"))
        dubType.selectedItem = "minimal"
        val dubTypeLabel = JLabel(message("d.ui.dub.config.label.dubprojecttype"))
        dubTypeLabel.labelFor = dubType
        myPanel.add(dubTypeLabel, GridBagConstraints(0, -1, 1, 1, 0.0, 0.0, 17, 0, JBUI.insets(0, 0, 5, 4), 0, 0))
        myPanel.add(dubType, GridBagConstraints(0, -1, 1, 1, 1.0, 0.0, 18, 2, JBUI.insets(10, 0, 20, 0), 0, 0))

        // add text field to add params instead of using combo boxes as fallback
        dubParams = JTextField()
        val dubParamsLabel = JLabel(message("d.ui.dub.config.label.dubparamsoverride"))
        dubParamsLabel.labelFor = dubParams
        myPanel.add(dubParamsLabel, GridBagConstraints(0, -1, 1, 1, 1.0, 0.0, 18, 2, JBUI.insets(10, 0, 20, 0), 0, 0))
        myPanel.add(dubParams, GridBagConstraints(0, -1, 1, 1, 1.0, 0.0, 18, 2, JBUI.insets(10, 0, 20, 0), 0, 0))
    }

    override fun getComponent(): JComponent {
        return myPanel
    }

    override fun isStepVisible(): Boolean {
        return myWizardContext.project == null
    }

    override fun updateDataModel() {
        val moduleBuilder = myWizardContext.projectBuilder
        if (moduleBuilder != null) {
            myWizardContext.projectBuilder = moduleBuilder
            if (moduleBuilder is ModuleBuilder) {
                val builder = moduleBuilder
                if ("DLangDubApp" == builder.builderId) {
                    val dubBuilder = builder as DlangDubModuleBuilder
                    val options: MutableMap<String, String> = HashMap(3)
                    options["dubFormat"] = dubFormat.selectedItem.toString()
                    options["dubType"] = dubType.selectedItem.toString()
                    options["dubParams"] = dubParams.text
                    dubBuilder.setDubOptions(options)
                }
            }
        }
        myFormatPanel.updateData(myWizardContext)
    }

    override fun getIcon(): Icon? {
        return myWizardContext.stepIcon
    }
}
