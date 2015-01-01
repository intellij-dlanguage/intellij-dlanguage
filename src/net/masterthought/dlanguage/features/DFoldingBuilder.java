package net.masterthought.dlanguage.features;

import com.google.common.collect.Lists;
import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.PsiElementProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import net.masterthought.dlanguage.psi.DLanguageFile;
import net.masterthought.dlanguage.psi.DTokenSets;
import net.masterthought.dlanguage.psi.interfaces.DDeclarationImport;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionClass;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionFunction;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionStruct;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DFoldingBuilder extends FoldingBuilderEx implements DumbAware {
    @NotNull

    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        if (!(root instanceof DLanguageFile)) return FoldingDescriptor.EMPTY;
        DLanguageFile file = (DLanguageFile) root;

        final List<FoldingDescriptor> result = ContainerUtil.newArrayList();

        // add all functions
        for (DDefinitionFunction function : PsiTreeUtil.findChildrenOfType(file, DDefinitionFunction.class)) {
            result.add(new FoldingDescriptor(function, function.getTextRange()));
        }

        // add all classes
        for (DDefinitionClass classDefinition : PsiTreeUtil.findChildrenOfType(file, DDefinitionClass.class)) {
            result.add(new FoldingDescriptor(classDefinition, classDefinition.getTextRange()));
        }

        // add all structs
        for (DDefinitionStruct struct : PsiTreeUtil.findChildrenOfType(file, DDefinitionStruct.class)) {
            result.add(new FoldingDescriptor(struct, struct.getTextRange()));
        }

// TODO - fix this - getting IndexOutOfBoundsException
//        // add import group
//        List<DDeclarationImport> imports = Lists.newArrayList();
//        int count = 0;
//        for (DDeclarationImport importer : PsiTreeUtil.findChildrenOfType(file, DDeclarationImport.class)) {
//            // add the first one
//            if (count == 0) {
//                imports.add(importer);
//            } else {
//                // only add the next one if the previous one was also an import
//                if (importer.getPrevSibling().getPrevSibling() instanceof DDeclarationImport) {
//                    imports.add(importer);
//                }
//            }
//            count++;
//        }
//        int first = imports.get(0).getTextRange().getStartOffset();
//        int last = imports.get(imports.size() - 1).getTextRange().getEndOffset();
//        result.add(new FoldingDescriptor(imports.get(0), new TextRange(first, last)));

        if (!quick) {
            PsiTreeUtil.processElements(file, new PsiElementProcessor() {
                @Override
                public boolean execute(@NotNull PsiElement element) {
                    if (DTokenSets.MULTI_LINE_COMMENTS.contains(element.getNode().getElementType()) && element.getTextRange().getLength() > 2) {
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

        if (psi instanceof DDefinitionFunction) {
            return ((DDefinitionFunction) psi).getName() + " (Function) ...";
        }
        if (psi instanceof DDeclarationImport) {
            return "import ...";
        }
        //TODO - add stubs for classes and structs
//        if (psi instanceof DDefinitionClass) {
//            return ((DDefinitionClass) psi).getName() + " (Class) ...";
//        }
//        if (psi instanceof DDefinitionStruct) {
//            return ((DDefinitionStruct) psi).getName() + " (Struct) ...";
//        }
        return "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        PsiElement psi = node.getPsi();
        return psi instanceof DDeclarationImport;
    }
}