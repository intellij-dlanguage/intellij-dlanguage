package net.masterthought.dlanguage.highlighting.annotation.external;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.psi.PsiFile;
import net.masterthought.dlanguage.highlighting.annotation.DAnnotationHolder;
import net.masterthought.dlanguage.highlighting.annotation.DProblem;
import net.masterthought.dlanguage.highlighting.annotation.Problems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Single annotator that calls all external tools used for annotations.
 */
public class DExternalAnnotator extends ExternalAnnotator<PsiFile, DExternalAnnotator.State> {
    @SuppressWarnings("UnusedDeclaration")
    private static final Logger LOG = Logger.getInstance(DExternalAnnotator.class);

    public static void apply(@NotNull PsiFile file, State state, @NotNull DAnnotationHolder holder) {
        createAnnotations(file, state.syntaxProblems, holder);
        createAnnotations(file, state.dScannerProblems, holder);
    }

    public static void createAnnotations(@NotNull PsiFile file, @Nullable Problems problems,
                                         @NotNull DAnnotationHolder holder) {
        if (problems == null || problems.isEmpty() || !file.isValid()) return;
        for (DProblem problem : problems) {
            problem.createAnnotations(file, holder);
        }
    }

    /**
     * The default implementation here is to not annotate files that have lexer/parser errors.  This is kind
     * of lame since the error may be invalid.
     */
    @Nullable
    @Override
    public PsiFile collectInformation(@NotNull PsiFile file, @NotNull Editor editor, boolean hasErrors) {
        return collectInformation(file);
    }

    @NotNull
    @Override
    public PsiFile collectInformation(@NotNull PsiFile file) {
        return file;
    }

    @Nullable
    @Override
    public State doAnnotate(@NotNull PsiFile file) {
        // Force all files to save to ensure annotations are in sync with the file system.
        ApplicationManager.getApplication().invokeAndWait(new Runnable() {
            @Override
            public void run() {
                FileDocumentManager.getInstance().saveAllDocuments();
            }
        }, ModalityState.NON_MODAL);

        State state = new State();
        state.dScannerProblems = new DScanner().checkFileSyntax(file);
        state.syntaxProblems = new CompileCheck().checkFileSyntax(file);
        return state;
    }

    /**
     * Wrapper to simplify adding annotations to an AnnotationHolder.
     */
    @Override
    public void apply(@NotNull PsiFile file, State state, @NotNull AnnotationHolder holder) {
        apply(file, state, new DAnnotationHolder(holder));
    }

    public static class State {
        Problems syntaxProblems;
        Problems dScannerProblems;
    }
}
