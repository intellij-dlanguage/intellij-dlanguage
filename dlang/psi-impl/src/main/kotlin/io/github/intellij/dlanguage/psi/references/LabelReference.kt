package io.github.intellij.dlanguage.psi.references

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.ResolveState
import com.intellij.util.IncorrectOperationException
import io.github.intellij.dlanguage.psi.impl.DElementFactory
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.psi.interfaces.LabelQualifier
import io.github.intellij.dlanguage.psi.resolve.processor.LabelProcessor
import io.github.intellij.dlanguage.psi.scope.PsiScopesUtil
import io.github.intellij.dlanguage.utils.FunctionDeclaration
import io.github.intellij.dlanguage.utils.TemplateDeclaration

class LabelReference(element: LabelQualifier,
                     textRange: TextRange,
): PsiReferenceBase<LabelQualifier>(element, textRange) {

    override fun resolve(): PsiElement? {
        val processor = LabelProcessor(element.identifier!!.text)
        var current = element.parent
        while ((current !is FunctionDeclaration || current !is TemplateDeclaration)&& current != null) {
            PsiScopesUtil.walkChildrenScopes(current, processor, ResolveState.initial(), null, element)
            if (processor.getResult().size == 1) {
                return processor.getResult()[0].element as DNamedElement
            }
            current = current.parent
        }
        return null
    }

    @Throws(IncorrectOperationException::class)
    override fun handleElementRename(newElementName: String): PsiElement {
        val element = DElementFactory.createDLanguageIdentifierFromText(
                myElement.project,
                newElementName
            )!!
            myElement.identifier!!.replace(
                element
            )
        return element
    }
}
