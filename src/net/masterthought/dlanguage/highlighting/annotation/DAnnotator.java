package net.masterthought.dlanguage.highlighting.annotation;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.highlighting.DHighlighter;
import net.masterthought.dlanguage.psi.DVisitor;
import net.masterthought.dlanguage.psi.interfaces.*;
import org.jetbrains.annotations.NotNull;

/**
 * Annotator that:
 * <p/>
 * 1) brushes up syntax highlighting issues from parsing a broken program.
 * 2) Registers quickfixes on broken nodes.
 */
public class DAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull final AnnotationHolder holder) {
        element.accept(new DVisitor() {
            @Override
            public void visitDDeclarationModule(@NotNull DDeclarationModule o) {
                super.visitDDeclarationModule(o);
                setHighlighting(o.getSymbol(), holder, DHighlighter.REF_MODULE);
            }

            @Override
            public void visitDRefModule(@NotNull DRefModule o) {
                super.visitDRefModule(o);
                setHighlighting(o, holder, DHighlighter.REF_MODULE);
            }

            @Override
            public void visitDDefinitionVariable(@NotNull DDefinitionVariable o) {
                super.visitDDefinitionVariable(o);
                setHighlighting(o.getSymbol(), holder, DHighlighter.VARIABLE);
            }

            @Override
            public void visitDDefinitionFunction(@NotNull DDefinitionFunction o) {
                super.visitDDefinitionFunction(o);
                setHighlighting(o.getSymbol(), holder, DHighlighter.FUNCTION_DEFINITION);
            }

            @Override
            public void visitDRefIdentifier(@NotNull DRefIdentifier o) {
                super.visitDRefIdentifier(o);
                if (o.getParent() instanceof DRefQualified) {
                    if (hasNextSibling(o)) {
                        if (o.getNextSibling().getText().equals(".")) {
                            setHighlighting(o, holder, DHighlighter.VARIABLE);
                        }
                    }
                } else if(o.getParent() instanceof DExpInfix){
                    setHighlighting(o, holder, DHighlighter.VARIABLE);
                } else if(o.getParent() instanceof DStatementReturn){
                    setHighlighting(o, holder, DHighlighter.VARIABLE);
                }
            }

            private boolean hasNextSibling(PsiElement element) {
                return element.getNextSibling() != null;
            }

        });
    }

    private static void setHighlighting(@NotNull PsiElement element, @NotNull AnnotationHolder holder,
                                        @NotNull TextAttributesKey key) {
        holder.createInfoAnnotation(element, null).setEnforcedTextAttributes(
                EditorColorsManager.getInstance().getGlobalScheme().getAttributes(key));
    }
}

