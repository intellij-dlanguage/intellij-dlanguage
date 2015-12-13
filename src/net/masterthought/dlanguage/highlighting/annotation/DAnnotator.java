package net.masterthought.dlanguage.highlighting.annotation;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.highlighting.DHighlighter;
import net.masterthought.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
                if(o.getIdentifier().getText().startsWith("std")) {
                    setHighlighting(o, holder, DHighlighter.STD_IMPORT);
                }
            }

            @Override
            public void visitModuleDeclaration(@NotNull DLanguageModuleDeclaration o) {
                super.visitModuleDeclaration(o);
                setHighlighting(o.getModuleFullyQualifiedName(), holder, DHighlighter.MODULE_DEFINITION);
            }

            @Override
            public void visitFuncDeclarator(@NotNull DLanguageFuncDeclarator o) {
                super.visitFuncDeclarator(o);
                setHighlighting(o.getIdentifier(), holder, DHighlighter.FUNCTION_DEFINITION);
            }

            @Override
            public void visitAggregateDeclaration(@NotNull DLanguageAggregateDeclaration o) {
                super.visitAggregateDeclaration(o);
                if(o.getClassDeclaration() != null){
                    if(o.getClassDeclaration().getIdentifier() != null) {
                        setHighlighting(o.getClassDeclaration().getIdentifier(), holder, DHighlighter.AGGREGATE_DEFINITION);
                    }
                }
                if(o.getStructDeclaration() != null){
                    if(o.getStructDeclaration().getIdentifier() != null) {
                        setHighlighting(o.getStructDeclaration().getIdentifier(), holder, DHighlighter.AGGREGATE_DEFINITION);
                    }
                }
                if(o.getUnionDeclaration() != null){
                    if(o.getUnionDeclaration().getIdentifier() != null) {
                        setHighlighting(o.getUnionDeclaration().getIdentifier(), holder, DHighlighter.AGGREGATE_DEFINITION);
                    }
                }
                if(o.getInterfaceDeclaration() != null){
                    if(o.getInterfaceDeclaration().getIdentifier() != null) {
                        setHighlighting(o.getInterfaceDeclaration().getIdentifier(), holder, DHighlighter.AGGREGATE_DEFINITION);
                    }
                }
            }

            @Override
            public void visitAutoFuncDeclaration(@NotNull DLanguageAutoFuncDeclaration o) {
                super.visitAutoFuncDeclaration(o);
                setHighlighting(o.getIdentifier(), holder, DHighlighter.FUNCTION_DEFINITION);
            }

            @Override
            public void visitBasicTypeX(@NotNull DLanguageBasicTypeX o) {
                super.visitBasicTypeX(o);
                    setHighlighting(o, holder, DHighlighter.BASIC_TYPE);
            }

        });
    }

    private static void setHighlighting(@NotNull PsiElement element, @NotNull AnnotationHolder holder,
                                        @NotNull TextAttributesKey key) {
        holder.createInfoAnnotation(element, null).setEnforcedTextAttributes(
                EditorColorsManager.getInstance().getGlobalScheme().getAttributes(key));
    }
}

