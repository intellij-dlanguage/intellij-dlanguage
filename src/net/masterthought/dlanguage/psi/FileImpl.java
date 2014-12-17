package net.masterthought.dlanguage.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import net.masterthought.dlanguage.DLanguage;
import net.masterthought.dlanguage.DLanguageFileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class FileImpl extends PsiFileBase {
    public FileImpl(@NotNull FileViewProvider viewProvider) {
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
}
