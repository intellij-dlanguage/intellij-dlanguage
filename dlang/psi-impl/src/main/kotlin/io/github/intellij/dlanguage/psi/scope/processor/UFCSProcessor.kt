package io.github.intellij.dlanguage.psi.scope.processor

import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import io.github.intellij.dlanguage.psi.DlangPsiFile
import io.github.intellij.dlanguage.utils.*

class UFCSProcessor(private val delegate: PsiScopeProcessor) : PsiScopeProcessor {
    private var isCurrentModuleEval: Boolean = false

    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        if (element is ImportDeclaration || element is ImportBind || element is NamedImportBind)
            return delegate.execute(element, state)
        if (isCurrentModuleEval &&
            (element is FunctionDeclaration ||
                element is TemplateDeclaration ||
                element is AliasInitializer)) {
            return delegate.execute(element, state)
        }
        return true
    }

    override fun handleEvent(event: PsiScopeProcessor.Event, associated: Any?) {
        if (event === PsiScopeProcessor.Event.SET_DECLARATION_HOLDER) {
            isCurrentModuleEval = associated is DlangPsiFile
        }
    }

}
