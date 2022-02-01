package io.github.intellij.dlanguage.highlighting.annotation;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
* This class probably needs revisiting. No idea why it doesn't simply implement the AnnotationHolder interface but I
* suspect that's what it should do.
 */
@Deprecated // this class needs to be removed
public class DAnnotationHolder {

    public final AnnotationHolder holder;

    public DAnnotationHolder(@NotNull final AnnotationHolder holder) {
        this.holder = holder;
    }

    public void createInfoAnnotation(@NotNull final TextRange range, @Nullable final String message) {
        holder.newAnnotation(HighlightSeverity.INFORMATION, message).range(range).create();
    }

    public void createWarningAnnotation(@NotNull final TextRange range, @Nullable final String message) {
        holder.newAnnotation(HighlightSeverity.WARNING, message).range(range).create();
    }

    public void createErrorAnnotation(@NotNull final TextRange range, @Nullable final String message) {
        holder.newAnnotation(HighlightSeverity.ERROR, message).range(range).create();
    }

}
