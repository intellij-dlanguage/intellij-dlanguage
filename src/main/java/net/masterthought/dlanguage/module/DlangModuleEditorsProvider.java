package net.masterthought.dlanguage.module;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleConfigurationEditor;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.roots.ui.configuration.CommonContentEntriesEditor;
import com.intellij.openapi.roots.ui.configuration.DefaultModuleConfigurationEditorFactory;
import com.intellij.openapi.roots.ui.configuration.ModuleConfigurationEditorProvider;
import com.intellij.openapi.roots.ui.configuration.ModuleConfigurationState;
import org.jetbrains.jps.model.java.JavaSourceRootType;

import java.util.ArrayList;
import java.util.List;

/* DLang Module settings editor. It contains of multiple tabs: Common, Output, Libraries. */
public class DlangModuleEditorsProvider implements ModuleConfigurationEditorProvider {

    @Override
    public ModuleConfigurationEditor[] createEditors(final ModuleConfigurationState state) {
        final Module module = state.getRootModel().getModule();
        if (ModuleType.get(module) != DlangModuleType.getInstance()) {
            return ModuleConfigurationEditor.EMPTY;
        }

        final DefaultModuleConfigurationEditorFactory editorFactory = DefaultModuleConfigurationEditorFactory.getInstance();
        final List<ModuleConfigurationEditor> editors = new ArrayList<>();
        editors.add(new CommonContentEntriesEditor(module.getName(), state, JavaSourceRootType.SOURCE, JavaSourceRootType.TEST_SOURCE));
        editors.add(new OutputElementsEditor(state));
        editors.add(editorFactory.createClasspathEditor(state));
        //editors.add(new DLangModuleBuildConfEditor(state)); //TODO: implement DLangModuleBuildConfEditor
        return editors.toArray(new ModuleConfigurationEditor[editors.size()]);
    }
}
