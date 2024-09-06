package io.github.intellij.dlanguage.psi.references

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiQualifiedReference
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.ResolveState
import com.intellij.util.IncorrectOperationException
import io.github.intellij.dlanguage.psi.impl.DElementFactory
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.psi.resolve.processor.GenericProcessor
import io.github.intellij.dlanguage.psi.scope.PsiScopesUtil
import io.github.intellij.dlanguage.utils.ReferenceExpression

class GenericExpressionElementReference(element: ReferenceExpression,
                                        textRange: TextRange,
                                        private val qualifier: ReferenceExpression?,
                                        private val referenceName: String
) : PsiReferenceBase<ReferenceExpression>(element, textRange), PsiQualifiedReference {

    override fun resolve(): PsiElement? {
        val processor = GenericProcessor(referenceName, myElement)
        if (isModuleScopeOperator(element)) {
            element.containingFile.processDeclarations(processor, ResolveState.initial(), null, element)
        } else {
            PsiScopesUtil.resolveAndWalk(processor, this, null)
        }
        if (processor.getResult().size == 1) {
            return processor.getResult()[0].element as DNamedElement
        }
        return null
    }

    fun isModuleScopeOperator(e: PsiElement): Boolean {
        return e is ReferenceExpression && e.oP_DOT != null && e.referenceExpression == null
    }

    override fun getQualifier(): PsiElement? = qualifier

    override fun getReferenceName(): String = referenceName

    @Throws(IncorrectOperationException::class)
    override fun handleElementRename(newElementName: String): PsiElement {
        if (myElement.identifier != null) {
            myElement.identifier!!.replace(
                DElementFactory.createDLanguageIdentifierFromText(
                    myElement.project,
                    newElementName
                )!!
            )
        } else if (myElement.templateInstance != null && myElement.templateInstance!!.identifier != null) {
            myElement.templateInstance!!.identifier!!.replace(
                DElementFactory.createDLanguageIdentifierFromText(
                    myElement.project,
                    newElementName
                )!!
            )
        } else {
            throw IncorrectOperationException()
        }
        return myElement
    }
}
