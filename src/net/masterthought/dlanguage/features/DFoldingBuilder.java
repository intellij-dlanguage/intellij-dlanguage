package net.masterthought.dlanguage.features;

import com.google.common.collect.Lists;
import com.intellij.codeInsight.highlighting.BraceMatcher;
import com.intellij.lang.ASTNode;
import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.PsiElementProcessor;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import net.masterthought.dlanguage.psi.DLanguageFile;
import net.masterthought.dlanguage.psi.DLanguageTokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DFoldingBuilder extends FoldingBuilderEx implements DumbAware {
    @NotNull


    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        if (!(root instanceof DLanguageFile)) return FoldingDescriptor.EMPTY;
        DLanguageFile file = (DLanguageFile) root;

        final List<FoldingDescriptor> result = ContainerUtil.newArrayList();



        if (!quick) {
            PsiTreeUtil.processElements(file, new PsiElementProcessor() {
                @Override
                public boolean execute(@NotNull PsiElement element) {
                    if (DLanguageTokenType.COMMENTS.contains(element.getNode().getElementType()) && element.getTextRange().getLength() > 2) {
                        result.add(new FoldingDescriptor(element, element.getTextRange()));
                    }
                    return true;
                }
            });
        }

        return result.toArray(new FoldingDescriptor[result.size()]);
    }


    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        PsiElement psi = node.getPsi();
//        if (psi instanceof ErlangFunction) return ErlangPsiImplUtil.createFunctionPresentation((ErlangFunction) psi) + " -> ...";
//        IElementType type = node.getElementType();
//        if (ErlangParserDefinition.ERL_COMMENT == type) return "% ...";
//        if (ErlangParserDefinition.ERL_FUNCTION_DOC_COMMENT == type) return "%% ...";
//        if (ErlangParserDefinition.ERL_MODULE_DOC_COMMENT == type) return "%%% ...";
//        return null;
        return "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }
}