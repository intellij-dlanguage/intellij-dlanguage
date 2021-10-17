package io.github.intellij.dlanguage.psi;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiFile;
import io.github.intellij.dlanguage.DLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

/**
 * @author Samael Bate (singingbush)
 * created on 20/10/18
 */
public abstract class DlangItemPresentation implements ItemPresentation {

    private final PsiFile psiFile;

    protected DlangItemPresentation(final PsiFile psiFile) {
        this.psiFile = psiFile;
    }

    @Nullable
    @Override
    public String getLocationString() {
        return psiFile instanceof DlangFile ? ((DlangFile) psiFile).getModuleOrFileName() : null;
    }

    @NotNull
    @Override
    public Icon getIcon(boolean unused) {
        return DLanguage.Icons.FILE;
    }
}
