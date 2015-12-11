package net.masterthought.dlanguage.highlighting.annotation;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.highlighting.DHighlighter;
import net.masterthought.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;

/**
* Annotator that:
* adds extra syntax highlighting
*/
public class DAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull final AnnotationHolder holder) {
        element.accept(new DLanguageVisitor() {
            @Override
            public void visitModuleFullyQualifiedName(@NotNull DLanguageModuleFullyQualifiedName o) {
                super.visitModuleFullyQualifiedName(o);
                setHighlighting(o.getIdentifier(), holder, DHighlighter.REF_MODULE);
            }

            @Override
            public void visitFuncDeclarator(@NotNull DLanguageFuncDeclarator o) {
                super.visitFuncDeclarator(o);
                setHighlighting(o.getIdentifier(), holder, DHighlighter.VARIABLE);
            }

            @Override
            public void visitBasicType2(@NotNull DLanguageBasicType2 o) {
                super.visitBasicType2(o);
                setHighlighting(o, holder, DHighlighter.KEYWORD);
            }

        });
    }

    private static void setHighlighting(@NotNull PsiElement element, @NotNull AnnotationHolder holder,
                                        @NotNull TextAttributesKey key) {
        holder.createInfoAnnotation(element, null).setEnforcedTextAttributes(
                EditorColorsManager.getInstance().getGlobalScheme().getAttributes(key));
    }
}

