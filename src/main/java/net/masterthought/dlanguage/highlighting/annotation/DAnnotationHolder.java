package net.masterthought.dlanguage.highlighting.annotation;

import com.intellij.codeInsight.daemon.impl.AnnotationHolderImpl;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.util.TextRange;
import com.intellij.xml.util.XmlStringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

public class DAnnotationHolder {
    public final AnnotationHolder holder;

    public DAnnotationHolder(@NotNull final AnnotationHolder holder) {
        this.holder = holder;
    }

    public Annotation createInfoAnnotation(@NotNull final TextRange range, @Nullable final String message) {
        return holder instanceof AnnotationHolderImpl ?
                createAnnotation((AnnotationHolderImpl)holder, HighlightSeverity.INFORMATION, range, message)
                : holder.createInfoAnnotation(range, message);
    }

    public Annotation createWeakWarningAnnotation(@NotNull final TextRange range, @Nullable final String message) {
        return holder instanceof AnnotationHolderImpl ?
                createAnnotation((AnnotationHolderImpl)holder, HighlightSeverity.WEAK_WARNING, range, message)
                : holder.createWeakWarningAnnotation(range, message);
    }

    public Annotation createWarningAnnotation(@NotNull final TextRange range, @Nullable final String message) {
        return holder instanceof AnnotationHolderImpl ?
                createAnnotation((AnnotationHolderImpl)holder, HighlightSeverity.WARNING, range, message)
                : holder.createWarningAnnotation(range, message);
    }

    public Annotation createErrorAnnotation(@NotNull final TextRange range, @Nullable final String message) {
        return holder instanceof AnnotationHolderImpl ?
                createAnnotation((AnnotationHolderImpl)holder, HighlightSeverity.ERROR, range, message)
                : holder.createErrorAnnotation(range, message);
    }

    private static Annotation createAnnotation(@NotNull final AnnotationHolderImpl holder, @NotNull HighlightSeverity severity, @NotNull TextRange range, @Nullable String message) {
        final String tooltip = message == null ? null : XmlStringUtil.wrapInHtml(escapeSpacesForHtml(XmlStringUtil.escapeString(message)));
        final Annotation annotation = new Annotation(range.getStartOffset(), range.getEndOffset(), severity, message, tooltip);
        holder.add(annotation);
        return annotation;
    }

    private static final Pattern NEWLINE_REGEX = Pattern.compile("\n");
    private static final Pattern SPACE_REGEX = Pattern.compile(" ");

    public static String escapeSpacesForHtml(final String string) {
        return string == null ? null : SPACE_REGEX.matcher(NEWLINE_REGEX.matcher(string).replaceAll("<br/>")).replaceAll("&nbsp;");
    }
}
