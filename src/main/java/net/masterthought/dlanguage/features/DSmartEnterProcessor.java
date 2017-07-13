package net.masterthought.dlanguage.features;

import com.intellij.lang.SmartEnterProcessorWithFixers;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 6/11/2017.
 */
public class DSmartEnterProcessor extends SmartEnterProcessorWithFixers {
    @Override
    public boolean process(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile psiFile) {
        final PsiElement element = getStatementAtCaret(editor, psiFile);

        return true;
    }
}
