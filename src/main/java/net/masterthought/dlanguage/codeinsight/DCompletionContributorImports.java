package net.masterthought.dlanguage.codeinsight;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import net.masterthought.dlanguage.DLanguage;
import net.masterthought.dlanguage.psi.DLanguageImport;
import net.masterthought.dlanguage.resolve.DImportScopeProcessor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 6/19/2017.
 */
public class DCompletionContributorImports extends CompletionContributor {
    public DCompletionContributorImports() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement().withLanguage(DLanguage.INSTANCE), new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
                final PsiElement position = parameters.getPosition();
                final DImportScopeProcessor importScopeProcessor = new DImportScopeProcessor();
                PsiTreeUtil.treeWalkUp(importScopeProcessor, position, position.getContainingFile(), ResolveState.initial());
                for (DLanguageImport anImport : importScopeProcessor.getImports()) {

                }

            }
        });
    }
}
