package io.github.intellij.dlanguage.features

import com.intellij.codeInsight.documentation.DocumentationManagerProtocol
import com.intellij.lang.documentation.psi.psiDocumentationTargets
import com.intellij.platform.backend.documentation.DocumentationLinkHandler
import com.intellij.platform.backend.documentation.DocumentationTarget
import com.intellij.platform.backend.documentation.LinkResolveResult
import com.intellij.psi.PsiElement

class DDocumentationLinkHandler : DocumentationLinkHandler {
    override fun resolveLink(target: DocumentationTarget, url: String): LinkResolveResult? {
        if (target !is DDocumentationTarget) return null

        val element = target.element
        if (url.startsWith(DocumentationManagerProtocol.PSI_ELEMENT_PROTOCOL)) {
            val names = url.substring(DocumentationManagerProtocol.PSI_ELEMENT_PROTOCOL.length).split('.')
            val target = resolveDDockLink(names, element) ?: return null
            return LinkResolveResult.resolvedTarget(psiDocumentationTargets(target, target).first())
        }
        return null
    }
}

fun resolveDDockLink(names: List<String>, element: PsiElement): PsiElement? {
    return null // TODO
}
