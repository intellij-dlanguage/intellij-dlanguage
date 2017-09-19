package io.github.intellij.dlanguage.highlighting.annotation.external;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.psi.PsiFile;
import io.github.intellij.dlanguage.highlighting.annotation.DAnnotationHolder;
import io.github.intellij.dlanguage.highlighting.annotation.DProblem;
import io.github.intellij.dlanguage.highlighting.annotation.Problems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Single annotator that calls all external tools used for annotations.
 */
public class DExternalAnnotator extends ExternalAnnotator<PsiFile, DExternalAnnotator.State> {
    @SuppressWarnings("UnusedDeclaration")
    private static final Logger LOG = Logger.getInstance(DExternalAnnotator.class);

    /**
     * The default implementation here is to not annotate files that have lexer/parser errors.  This is kind
     * of lame since the error may be invalid.
     */
    @Nullable
    @Override
    public PsiFile collectInformation(@NotNull final PsiFile file, @NotNull final Editor editor, final boolean hasErrors) {
        return collectInformation(file);
    }

    @NotNull
    @Override
    public PsiFile collectInformation(@NotNull final PsiFile file) {
        return file;
    }

    @Nullable
    @Override
    public State doAnnotate(@NotNull final PsiFile file) {
        // Force all files to save to ensure annotations are in sync with the file system.
        if (FileDocumentManager.getInstance().getUnsavedDocuments().length != 0)
            ApplicationManager
                .getApplication()
                .invokeAndWait(() -> FileDocumentManager.getInstance().saveAllDocuments(), ApplicationManager.getApplication().getDefaultModalityState());

        final State state = new State();
        state.dScannerProblems = new DScanner().checkFileSyntax(file);
        state.syntaxProblems = new CompileCheck().checkFileSyntax(file);
        return state;
    }

    /**
     * Wrapper to simplify adding annotations to an AnnotationHolder.
     */
    @Override
    public void apply(@NotNull final PsiFile file, final State state, @NotNull final AnnotationHolder holder) {
        apply(file, state, new DAnnotationHolder(holder));
    }

    public static void apply(@NotNull final PsiFile file, final State state, @NotNull final DAnnotationHolder holder) {
        createAnnotations(file, state.syntaxProblems, holder);
        createAnnotations(file, state.dScannerProblems, holder);
    }

    public static void createAnnotations(@NotNull final PsiFile file, @Nullable final Problems problems,
                                         @NotNull final DAnnotationHolder holder) {
        if (problems == null || problems.isEmpty() || !file.isValid()) return;
        for (final DProblem problem : problems) {
            problem.createAnnotations(file, holder);
        }
    }

    public static class State {
        Problems syntaxProblems;
        Problems dScannerProblems;
    }
}
