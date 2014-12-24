package net.masterthought.dlanguage.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.DLanguage;
import net.masterthought.dlanguage.DLanguageFileType;
import net.masterthought.dlanguage.lexer.PropertyImpl;
import net.masterthought.dlanguage.psi.interfaces.DDeclarationModule;
import net.masterthought.dlanguage.stubs.DLanguageFileStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DLanguageFile extends PsiFileBase {

    public DLanguageFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, DLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return DLanguageFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "D file";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }

    /**
     * Returns the module name defined in the file or null if it doesn't exist.
     */
    @Nullable
    public String getModuleName() {
        final DDeclarationModule module = findChildByClass(DDeclarationModule.class);
        if (module == null) {
            return null;
        }
        return module.getText().split(" ")[1].replaceAll(";", "");
    }

    /**
     * Returns the module name if it exists, otherwise returns the file name.
     */
    @NotNull
    public String getModuleOrFileName() {
        final String moduleName = getModuleName();
        return moduleName == null ? getName() : moduleName;
    }

    /**
     * Generates a stub for the current file, particularly so we can index names.
     */
    @Nullable
    @Override
    public DLanguageFileStub getStub() {
        final StubElement stub = super.getStub();
        if (stub == null) return null;
        return (DLanguageFileStub)stub;
    }
}
