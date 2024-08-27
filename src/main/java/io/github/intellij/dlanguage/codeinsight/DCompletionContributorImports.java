package io.github.intellij.dlanguage.codeinsight;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.processors.DImportScopeProcessor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 6/19/2017.
 */
public class DCompletionContributorImports extends CompletionContributor {
    public DCompletionContributorImports() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement().withLanguage(DLanguage.INSTANCE), new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(@NotNull final CompletionParameters parameters, final ProcessingContext context, @NotNull final CompletionResultSet result) {
                final PsiElement position = parameters.getPosition();
                final DImportScopeProcessor importScopeProcessor = new DImportScopeProcessor();
                PsiTreeUtil.treeWalkUp(importScopeProcessor, position, position.getContainingFile(), ResolveState.initial());
//                for (DLanguageImport anImport : importScopeProcessor.getImports()) {
//
//                }
                //todo

            }
        });
    }
}
