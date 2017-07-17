package net.masterthought.dlanguage.features;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import net.masterthought.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DFoldingBuilder extends FoldingBuilderEx implements DumbAware {
    @NotNull

    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        if (!(root instanceof DLanguageFile)) return FoldingDescriptor.EMPTY;
        DLanguageFile file = (DLanguageFile) root;

        final List<FoldingDescriptor> result = ContainerUtil.newArrayList();

        // add all functions
        for (DLanguageFunctionDeclaration function : PsiTreeUtil.findChildrenOfType(file, DLanguageFunctionDeclaration.class)) {
            if (function.isPhysical() && function.isValid() && function.isWritable() && !function.getText().equals(""))//required in case the psi element has been deleted
                result.add(new FoldingDescriptor(function, function.getTextRange()));
        }

        // add all aggregates
        for (DLanguageInterfaceOrClass aggregateDefinition : PsiTreeUtil.findChildrenOfType(file, DLanguageInterfaceOrClass.class)) {
            if (aggregateDefinition.isPhysical() && aggregateDefinition.isValid() && aggregateDefinition.isWritable() && !aggregateDefinition.getText().equals(""))//required in case the psi element has been deleted
                result.add(new FoldingDescriptor(aggregateDefinition, aggregateDefinition.getTextRange()));
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

//        if (!quick) {
//            PsiTreeUtil.processElements(file, new PsiElementProcessor() {
//                @Override
//                public boolean execute(@NotNull PsiElement element) {
//                    if (DTokenSets.MULTI_LINE_COMMENTS.contains(element.getNode().getElementType()) && element.getTextRange().getLength() > 2) {
//                        result.add(new FoldingDescriptor(element, element.getTextRange()));
//                    }
//                    return true;
//                }
//            });
//        }

        return result.toArray(new FoldingDescriptor[result.size()]);
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        PsiElement psi = node.getPsi();

        if (psi instanceof DLanguageFunctionDeclaration) {
            return ((DLanguageFunctionDeclaration) psi).getName() + " (Function) ...";
        }
        if (psi instanceof DLanguageImportDeclaration) {
            return "import ...";
        }
        if (psi instanceof DLanguageInterfaceOrClass) {
            DLanguageInterfaceOrClass declaration = (DLanguageInterfaceOrClass) psi;
            if (declaration.getParent() instanceof DLanguageClassDeclaration) {
                return declaration.getName() + " (Class) ...";
            }
            if (declaration.getParent() instanceof DLanguageInterfaceDeclaration) {
                return declaration.getName() + " (Interface) ...";
            }

        }
        return "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        PsiElement psi = node.getPsi();
        return psi instanceof DLanguageImportDeclaration;
    }
}
