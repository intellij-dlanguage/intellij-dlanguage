package io.github.intellij.dlanguage.library;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.libraries.*;
import com.intellij.openapi.roots.libraries.ui.LibraryEditorComponent;
import com.intellij.openapi.roots.libraries.ui.LibraryPropertiesEditor;
import com.intellij.openapi.roots.libraries.ui.LibraryRootsComponentDescriptor;
import com.intellij.openapi.roots.ui.configuration.FacetsProvider;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.module.DlangModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DlangLibraryType extends LibraryType<DummyLibraryProperties> {

    public static final PersistentLibraryKind<DummyLibraryProperties> DLANG_LIBRARY =
        new PersistentLibraryKind<DummyLibraryProperties>("DLangLib") {
            @NotNull
            @Override
            public DummyLibraryProperties createDefaultProperties() {
                return new DummyLibraryProperties();
            }
        };

    public DlangLibraryType() {
        super(DLANG_LIBRARY);
    }

    public static DlangLibraryType getInstance() {
        return LibraryType.EP_NAME.findExtension(DlangLibraryType.class);
    }

    @NotNull
    @Override
    public String getCreateActionName() {
        return "D Library";
    }

    @Override
    public boolean isSuitableModule(@NotNull final Module module, @NotNull final FacetsProvider facetsProvider) {
        return ModuleType.get(module).equals(DlangModuleType.getInstance());
    }

    @Override
    public NewLibraryConfiguration createNewLibrary(@NotNull final JComponent parentComponent,
                                                    @Nullable final VirtualFile contextDirectory,
                                                    @NotNull final Project project) {

        return LibraryTypeService.getInstance()
            .createLibraryFromFiles(createLibraryRootsComponentDescriptor(), parentComponent, contextDirectory, this, project);
    }

    @NotNull
    @Override
    public LibraryRootsComponentDescriptor createLibraryRootsComponentDescriptor() {
        return new DLanguageLibraryRootsComponentDescriptor();
    }

    @Override
    public LibraryPropertiesEditor createPropertiesEditor(@NotNull final LibraryEditorComponent<DummyLibraryProperties> component) {
        return null;
    }

    @Override
    public Icon getIcon(@Nullable final DummyLibraryProperties properties) {
        return DLanguage.Icons.FILE;
    }
}
