package io.github.intellij.dlanguage.highlighting.annotation.external;

import com.intellij.psi.PsiFile;
import io.github.intellij.dlanguage.highlighting.annotation.DProblem;
import org.jetbrains.annotations.NotNull;

public interface DlangLinter {

    DProblem[] checkFileSyntax(@NotNull final PsiFile file);
}
