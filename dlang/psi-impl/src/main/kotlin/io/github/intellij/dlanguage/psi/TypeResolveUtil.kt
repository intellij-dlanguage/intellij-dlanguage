package io.github.intellij.dlanguage.psi

import com.intellij.psi.PsiQualifiedReference
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.psi.resolve.processor.TypeProcessor
import io.github.intellij.dlanguage.psi.scope.PsiScopesUtil

object TypeResolveUtil {
    fun resolveType(reference: PsiQualifiedReference, forceTemplateSearch: Boolean): DNamedElement? {
        val userTypeName = reference.referenceName!!
        val processor = TypeProcessor(userTypeName, reference.element, forceTemplateSearch)
        PsiScopesUtil.resolveAndWalk(processor, reference, null)
        if (processor.getResult().size == 1) {
            return processor.getResult()[0].getElement() as DNamedElement
        }
        return null
    }
}
