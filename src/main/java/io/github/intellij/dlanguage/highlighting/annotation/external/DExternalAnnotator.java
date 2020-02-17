package io.github.intellij.dlanguage.highlighting.annotation.external;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.progress.ProcessCanceledException;
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
        final FileDocumentManager fileDocumentManager = FileDocumentManager.getInstance();
        final Application application = ApplicationManager.getApplication();

        if (fileDocumentManager.getUnsavedDocuments().length > 0) {
            try {
                application.invokeAndWait(
                    fileDocumentManager::saveAllDocuments,
                    application.getDefaultModalityState()
                );
            } catch (final ProcessCanceledException e) {
                LOG.warn("problem saving files", e);
            }
        }

        return new State(
            new DScanner().checkFileSyntax(file),
            new CompileCheck().checkFileSyntax(file)
        );
    }

    /**
     * Wrapper to simplify adding annotations to an AnnotationHolder.
     */
    @Override
    public void apply(@NotNull final PsiFile file, final State state, @NotNull final AnnotationHolder holder) {
        apply(file, state, new DAnnotationHolder(holder));
    }

    public static void apply(@NotNull final PsiFile file, final State state, @NotNull final DAnnotationHolder holder) {
        createAnnotations(file, state.getSyntaxProblems(), holder);
        createAnnotations(file, state.getdScannerProblems(), holder);
    }

    public static void createAnnotations(@NotNull final PsiFile file, @Nullable final Problems problems,
                                         @NotNull final DAnnotationHolder holder) {
        if (problems == null || problems.isEmpty() || !file.isValid()) return;
        for (final DProblem problem : problems) {
            problem.createAnnotations(file, holder);
        }
    }

    public static class State {
        private final Problems syntaxProblems;
        private final Problems dScannerProblems;

        public State(final Problems syntaxProblems, final Problems dScannerProblems) {
            this.syntaxProblems = syntaxProblems;
            this.dScannerProblems = dScannerProblems;
        }

        public Problems getSyntaxProblems() {
            return syntaxProblems;
        }

        public Problems getdScannerProblems() {
            return dScannerProblems;
        }
    }
}
