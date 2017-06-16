package net.masterthought.dlanguage.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.stubs.StubElement;
import net.masterthought.dlanguage.DLanguage;
import net.masterthought.dlanguage.DLanguageFileType;
import net.masterthought.dlanguage.resolve.ScopeProcessorImpl;
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
        return "D Language File";
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
        final DLanguageModuleDeclaration module = findChildByClass(DLanguageModuleDeclaration.class);
        if (module == null) {
            return null;
        }
        return module.getText().replaceAll(";","").replaceAll("^module\\s+","");
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

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        for (PsiElement element : getChildren()) {
            if (element instanceof DLanguageDeclDefs) {
                if (!ScopeProcessorImpl.INSTANCE.processDeclarations((DLanguageDeclDefs) element, processor, state, lastParent, place)) {
                    return false;
                }
            }
            if (element instanceof DLanguageStatement) {
                if (!ScopeProcessorImpl.INSTANCE.processDeclarations((DLanguageStatement) element, processor, state, lastParent, place)) {
                    return false;
                }
            }
            if (element instanceof DLanguageModuleDeclaration) {
                if (!processor.execute(element, state)) {
                    return false;
                }
            }
        }
        return true;
    }
}
