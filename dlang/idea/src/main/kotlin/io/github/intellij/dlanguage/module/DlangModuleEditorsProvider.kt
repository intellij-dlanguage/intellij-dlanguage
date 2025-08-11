package io.github.intellij.dlanguage.module

import com.intellij.openapi.module.ModuleConfigurationEditor
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ui.configuration.CommonContentEntriesEditor
import com.intellij.openapi.roots.ui.configuration.DefaultModuleConfigurationEditorFactory
import com.intellij.openapi.roots.ui.configuration.ModuleConfigurationEditorProvider
import com.intellij.openapi.roots.ui.configuration.ModuleConfigurationState
import org.jetbrains.jps.model.java.JavaSourceRootType

/* DLang Module settings editor. It contains of multiple tabs: Common, Output, Libraries. */
class DlangModuleEditorsProvider : ModuleConfigurationEditorProvider {
    override fun createEditors(state: ModuleConfigurationState): Array<ModuleConfigurationEditor> {
        val module = state.modifiableRootModel.module
        if (ModuleType.get(module) !== DlangModuleType.getInstance()) {
            return ModuleConfigurationEditor.EMPTY
        }

        val editorFactory = DefaultModuleConfigurationEditorFactory.getInstance()
        return arrayOf(
            CommonContentEntriesEditor(
                module.name,
                state,
                JavaSourceRootType.SOURCE,
                JavaSourceRootType.TEST_SOURCE
            ),
            OutputElementsEditor(state),
            editorFactory.createClasspathEditor(state)
            //DLangModuleBuildConfEditor(state); //TODO: implement DLangModuleBuildConfEditor
        )
    }
}
