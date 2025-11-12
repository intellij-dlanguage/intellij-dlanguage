package io.github.intellij.dlanguage.psi

import com.intellij.psi.PsiQualifiedReference
import com.intellij.psi.ResolveState
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.psi.resolve.processor.TypeProcessor
import io.github.intellij.dlanguage.psi.scope.PsiScopesUtil

object TypeResolveUtil {
    fun resolveType(reference: PsiQualifiedReference, forceTemplateSearch: Boolean, canSearchVariable: Boolean, topLevelOnly: Boolean = false): DNamedElement? {
        val userTypeName = reference.referenceName!!
        val processor = TypeProcessor(userTypeName, reference.element, forceTemplateSearch, canSearchVariable)
        if (topLevelOnly) {
            val containingFile = reference.element.containingFile
            containingFile.processDeclarations(processor, ResolveState.initial(), containingFile, reference.element)
        } else {
            PsiScopesUtil.resolveAndWalk(processor, reference, null)
        }
        if (processor.getResult().size == 1) {
            return processor.getResult()[0].element as DNamedElement
        }
        return null
    }
}
