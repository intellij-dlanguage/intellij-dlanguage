package io.github.intellij.dlanguage.module

import com.intellij.ide.util.BrowseFilesListener
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.fileChooser.FileChooserFactory
import com.intellij.openapi.project.ProjectBundle
import com.intellij.openapi.roots.CompilerModuleExtension
import com.intellij.openapi.roots.ui.configuration.ModuleConfigurationState
import com.intellij.openapi.roots.ui.configuration.ModuleElementsEditor
import com.intellij.openapi.roots.ui.configuration.ProjectStructureConfigurable
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.ui.DocumentAdapter
import com.intellij.ui.FieldPanel
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.InsertPathAction
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import org.jetbrains.annotations.NonNls
import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.IOException
import javax.swing.*
import javax.swing.event.DocumentEvent

class OutputElementsEditor(state: ModuleConfigurationState) : ModuleElementsEditor(state) {
    private lateinit var myInheritCompilerOutput: JRadioButton
    private lateinit var myPerModuleCompilerOutput: JRadioButton

    private lateinit var myOutputPathPanel: CommitableFieldPanel
    private lateinit var myTestsOutputPathPanel: CommitableFieldPanel
    private lateinit var myCbExcludeOutput: JCheckBox
    private lateinit var myOutputLabel: JLabel
    private lateinit var myTestOutputLabel: JLabel

    public override fun createComponentImpl(): JComponent {
        myInheritCompilerOutput = JRadioButton(ProjectBundle.message("project.inherit.compile.output.path"))
        myPerModuleCompilerOutput = JRadioButton(ProjectBundle.message("project.module.compile.output.path"))

        val group = ButtonGroup()
        group.add(myInheritCompilerOutput)
        group.add(myPerModuleCompilerOutput)

        val listener =
            ActionListener { e: ActionEvent? -> enableCompilerSettings(!myInheritCompilerOutput.isSelected) }

        myInheritCompilerOutput.addActionListener(listener)
        myPerModuleCompilerOutput.addActionListener(listener)

        myOutputPathPanel = createOutputPathPanel(
            ProjectBundle.message("module.paths.output.title"),
            object : CommitPathRunnable {
                override fun saveUrl(url: String?) {
                    if (compilerExtension!!.isCompilerOutputPathInherited) return  //do not override settings if any
                    compilerExtension!!.setCompilerOutputPath(url)
                }
            }
        )

        myTestsOutputPathPanel = createOutputPathPanel(
            ProjectBundle.message("module.paths.test.output.title"),
            object : CommitPathRunnable {
                override fun saveUrl(url: String?) {
                    if (compilerExtension!!.isCompilerOutputPathInherited) return  //do not override settings if any
                    compilerExtension!!.setCompilerOutputPathForTests(url)
                }
            })

        myCbExcludeOutput = JCheckBox(
            ProjectBundle.message("module.paths.exclude.output.checkbox"),
            this.compilerExtension!!.isExcludeOutput
        )

        myCbExcludeOutput.addActionListener { e: ActionEvent? ->
            this.compilerExtension!!.isExcludeOutput = myCbExcludeOutput.isSelected
        }

        val outputPathsPanel = JPanel(GridBagLayout())


        outputPathsPanel.add(
            myInheritCompilerOutput, GridBagConstraints(
                0, GridBagConstraints.RELATIVE, 2, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                JBUI.insets(6, 0, 0, 4), 0, 0
            )
        )

        outputPathsPanel.add(
            myPerModuleCompilerOutput, GridBagConstraints(
                0, GridBagConstraints.RELATIVE, 2, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                JBUI.insets(6, 0, 0, 4), 0, 0
            )
        )

        myOutputLabel = JLabel(ProjectBundle.message("module.paths.output.label"))

        outputPathsPanel.add(
            myOutputLabel, GridBagConstraints(
                0, GridBagConstraints.RELATIVE, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
                GridBagConstraints.NONE, JBUI.insets(6, 12, 0, 4), 0, 0
            )
        )

        outputPathsPanel.add(
            myOutputPathPanel, GridBagConstraints(
                1, GridBagConstraints.RELATIVE, 2, 1, 1.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, JBUI.insets(6, 4, 0, 0), 0, 0
            )
        )

        myTestOutputLabel = JLabel(ProjectBundle.message("module.paths.test.output.label"))

        outputPathsPanel.add(
            myTestOutputLabel, GridBagConstraints(
                0, GridBagConstraints.RELATIVE, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
                GridBagConstraints.NONE, JBUI.insets(6, 16, 0, 4), 0, 0
            )
        )

        outputPathsPanel.add(
            myTestsOutputPathPanel, GridBagConstraints(
                1, GridBagConstraints.RELATIVE, 2, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                JBUI.insets(6, 4, 0, 0), 0, 0
            )
        )

        outputPathsPanel.add(
            myCbExcludeOutput, GridBagConstraints(
                0, GridBagConstraints.RELATIVE, 2, 1, 1.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, JBUI.insets(6, 16, 0, 0), 0, 0
            )
        )

        // fill with data
        updateOutputPathPresentation()

        //compiler settings
        val outputPathInherited = this.compilerExtension!!.isCompilerOutputPathInherited
        myInheritCompilerOutput.setSelected(outputPathInherited)
        myPerModuleCompilerOutput.setSelected(!outputPathInherited)
        enableCompilerSettings(!outputPathInherited)

        val panel = JPanel(BorderLayout())
        panel.setBorder(
            IdeBorderFactory.createTitledBorder(
                ProjectBundle.message("project.roots.output.compiler.title"),
                true
            )
        )
        panel.add(outputPathsPanel, BorderLayout.NORTH)
        return panel
    }

    private fun updateOutputPathPresentation() {
        if (this.compilerExtension!!.isCompilerOutputPathInherited) {
            val baseUrl = ProjectStructureConfigurable.getInstance(myProject).projectConfig.compilerOutputUrl
            moduleCompileOutputChanged(baseUrl, model.module.name)
        } else {
            val compilerOutputPath = this.compilerExtension!!.compilerOutputPath
            if (compilerOutputPath != null) {
                myOutputPathPanel.text = FileUtil.toSystemDependentName(compilerOutputPath.path)
            } else {
                val compilerOutputUrl = this.compilerExtension!!.compilerOutputUrl
                if (compilerOutputUrl != null) {
                    myOutputPathPanel.text = FileUtil.toSystemDependentName(VfsUtilCore.urlToPath(compilerOutputUrl))
                }
            }
            val testsOutputPath = this.compilerExtension!!.compilerOutputPathForTests
            if (testsOutputPath != null) {
                myTestsOutputPathPanel.text = FileUtil.toSystemDependentName(testsOutputPath.path)
            } else {
                val testsOutputUrl = this.compilerExtension!!.compilerOutputUrlForTests
                if (testsOutputUrl != null) {
                    myTestsOutputPathPanel.text = FileUtil.toSystemDependentName(VfsUtilCore.urlToPath(testsOutputUrl))
                }
            }
        }
    }

    private fun enableCompilerSettings(enabled: Boolean) {
        UIUtil.setEnabled(myOutputPathPanel, enabled, true)
        UIUtil.setEnabled(myOutputLabel, enabled, true)
        UIUtil.setEnabled(myTestsOutputPathPanel, enabled, true)
        UIUtil.setEnabled(myTestOutputLabel, enabled, true)
        myCbExcludeOutput.setEnabled(enabled)
        this.compilerExtension!!.inheritCompilerOutputPath(!enabled)
        updateOutputPathPresentation()
    }

    private fun createOutputPathPanel(title: String?, commitPathRunnable: CommitPathRunnable): CommitableFieldPanel {
        val textField = JTextField()
        val outputPathsChooserDescriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor()
        outputPathsChooserDescriptor.isHideIgnored = false
        InsertPathAction.addTo(textField, outputPathsChooserDescriptor)
        FileChooserFactory.getInstance().installFileCompletion(textField, outputPathsChooserDescriptor, true, null)

        val commitRunnable = Runnable {
            if (!model.isWritable) {
                return@Runnable
            }
            val path = textField.getText().trim { it <= ' ' }
            if (path.isEmpty()) {
                commitPathRunnable.saveUrl(null)
            } else {
                // should set only absolute paths
                val canonicalPath = try {
                    FileUtil.resolveShortWindowsName(path)
                } catch (_: IOException) {
                    path
                }
                commitPathRunnable.saveUrl(VfsUtilCore.pathToUrl(FileUtil.toSystemIndependentName(canonicalPath)))
            }
        }

        textField.document.addDocumentListener(object : DocumentAdapter() {
            override fun textChanged(e: DocumentEvent) {
                commitRunnable.run()
            }
        })

        return CommitableFieldPanel(
            textField,
            null,
            null,
            object : BrowseFilesListener(textField, title, "", outputPathsChooserDescriptor) {
                override fun actionPerformed(e: ActionEvent?) {
                    super.actionPerformed(e)
                    commitRunnable.run()
                }
            },
            null,
            commitRunnable
        )
    }

    override fun saveData() {
        myOutputPathPanel.commit()
        myTestsOutputPathPanel.commit()
        this.compilerExtension!!.commit()
    }

    override fun getDisplayName(): String {
        return ProjectBundle.message("output.tab.title")
    }

    override fun getHelpTopic(): @NonNls String? {
        return "project.structureModulesPage.outputJavadoc"
    }


    override fun moduleStateChanged() {
        //if content enties tree was changed
        myCbExcludeOutput.setSelected(this.compilerExtension!!.isExcludeOutput)
    }

    override fun moduleCompileOutputChanged(baseUrl: String?, moduleName: String?) {
        if (this.compilerExtension!!.isCompilerOutputPathInherited) {
            if (baseUrl != null) {
                myOutputPathPanel.text = FileUtil.toSystemDependentName(
                    VfsUtilCore.urlToPath(
                        baseUrl + "/" + CompilerModuleExtension.PRODUCTION + "/" + moduleName
                    )
                )

                myTestsOutputPathPanel.text = FileUtil.toSystemDependentName(
                    VfsUtilCore.urlToPath(
                        baseUrl + "/" + CompilerModuleExtension.TEST + "/" + moduleName
                    )
                )
            } else {
                myOutputPathPanel.text = null
                myTestsOutputPathPanel.text = null
            }
        }
    }

    val compilerExtension: CompilerModuleExtension?
        get() = model.getModuleExtension(CompilerModuleExtension::class.java)

    private interface CommitPathRunnable {
        fun saveUrl(url: String?)
    }

    private class CommitableFieldPanel(
        textField: JTextField?,
        labelText: String?,
        viewerDialogTitle: String?,
        browseButtonActionListener: ActionListener?,
        documentListener: Runnable?,
        private val myCommitRunnable: Runnable
    ) : FieldPanel(textField, labelText, viewerDialogTitle, browseButtonActionListener, documentListener) {
        fun commit() {
            myCommitRunnable.run()
        }
    }
}
