package io.github.intellij.dlanguage.psi.scope.processor

import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import io.github.intellij.dlanguage.psi.named.DLanguagePackage
import io.github.intellij.dlanguage.psi.scope.ElementDeclarationHint
import io.github.intellij.dlanguage.utils.ImportDeclaration
import io.github.intellij.dlanguage.utils.getImportText

class PackageOrModuleProcessor(private val delegate: PsiScopeProcessor, private val parentPackage: DLanguagePackage?) : PsiScopeProcessor, ElementDeclarationHint {

    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        if (element is ImportDeclaration) {
            val packages = element.singleImports.filter { it.identifier == null }.mapNotNull {
                var id = it.identifierChain ?: return@mapNotNull null
                while(id.identifierChain != null && (parentPackage == null || getImportText(id.identifierChain!!) != parentPackage.qualifiedName))
                    id = id.identifierChain!!
                id
            }.toList()
            for (`package` in packages) {
                val resolvedPackage = `package`.reference?.resolve() ?:continue
                if (!delegate.execute(resolvedPackage, state))
                    return false
            }
        }
        return true
    }

    override fun shouldProcess(kind: ElementDeclarationHint.DeclarationKind): Boolean {
        return kind != ElementDeclarationHint.DeclarationKind.IMPORT
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any?> getHint(hintKey: Key<T?>): T? {
        if (hintKey == ElementDeclarationHint.KEY)
            return this as T
        return super.getHint(hintKey)
    }
}
