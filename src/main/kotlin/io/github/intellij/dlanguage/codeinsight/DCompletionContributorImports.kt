package io.github.intellij.dlanguage.codeinsight

import com.intellij.codeInsight.completion.*
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.processors.DImportScopeProcessor

/**
 * Created by francis on 6/19/2017.
 */
class DCompletionContributorImports : CompletionContributor()
{
    init
    {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement().withLanguage(DLanguage), object : CompletionProvider<CompletionParameters>()
        {
            override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, result: CompletionResultSet)
            {
                val position = parameters.position
                val importScopeProcessor = DImportScopeProcessor()
                PsiTreeUtil.treeWalkUp(importScopeProcessor, position, position.containingFile, ResolveState.initial())
                //                for (DLanguageImport anImport : importScopeProcessor.getImports()) {
                //
                //                }
                //todo

            }
        })
    }
}
