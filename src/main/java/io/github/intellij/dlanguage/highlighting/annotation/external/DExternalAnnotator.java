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
import io.github.intellij.dlanguage.highlighting.annotation.DProblem;
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

    /**
     * Collects full information required for annotation. This method is intended for long-running activities
     * and will be called outside a read action; implementations should either avoid accessing indices and PSI or
     * perform needed checks and locks themselves.
     *
     * @param file initial information gathered by {@link ExternalAnnotator#collectInformation}
     * @return annotations to pass to {@link DExternalAnnotator#apply(PsiFile, State, AnnotationHolder)}
     */
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
            new DScanner().checkFileSyntax(file)
        );
    }

    /**
     * Applies collected annotations (state) to the given AnnotationHolder. This method is called within a read action.
     *
     * @param file             a file to annotate
     * @param state annotations collected in {@link DExternalAnnotator#doAnnotate(PsiFile)}
     * @param holder           a container for receiving annotations
     */
    @Override
    public void apply(@NotNull final PsiFile file, final State state, @NotNull final AnnotationHolder holder) {
        createAnnotations(file, state.getdScannerProblems(), holder);
    }

    private void createAnnotations(@NotNull final PsiFile file,
                                   @Nullable final DProblem[] problems,
                                   @NotNull final AnnotationHolder holder) {
        if (problems == null || problems.length == 0 || !file.isValid()) return;
        for (final DProblem problem : problems) {
            if(problem != null) {
                problem.createAnnotations(file, holder);
            }
        }
    }

    public static class State {
        private final DProblem[] dScannerProblems;

        public State(final DProblem[] dScannerProblems) {
            this.dScannerProblems = dScannerProblems;
        }

        public DProblem[] getdScannerProblems() {
            return dScannerProblems;
        }
    }
}
