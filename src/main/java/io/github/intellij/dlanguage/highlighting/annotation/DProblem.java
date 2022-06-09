package io.github.intellij.dlanguage.highlighting.annotation;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public interface DProblem {
    void createAnnotations(@NotNull PsiFile file, @NotNull AnnotationHolder holder);

}

