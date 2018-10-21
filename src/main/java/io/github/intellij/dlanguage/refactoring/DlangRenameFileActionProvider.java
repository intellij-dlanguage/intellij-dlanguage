package io.github.intellij.dlanguage.refactoring;

import com.intellij.psi.PsiFile;
import com.intellij.refactoring.actions.RenameFileActionProvider;
import io.github.intellij.dlanguage.psi.DlangFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author Samael Bate (singingbush)
 * created on 21/10/18
 */
public class DlangRenameFileActionProvider implements RenameFileActionProvider {

    @Override
    public boolean enabledInProjectView(@NotNull final PsiFile file) {
        return DlangFile.class.isAssignableFrom(file.getClass());
    }

}
