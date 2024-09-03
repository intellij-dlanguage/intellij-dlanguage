package io.github.intellij.dlanguage.psi.references

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiQualifiedReference
import com.intellij.psi.PsiReferenceBase
import com.intellij.util.IncorrectOperationException
import io.github.intellij.dlanguage.psi.impl.DElementFactory
import io.github.intellij.dlanguage.resolve.DResolveUtil
import io.github.intellij.dlanguage.utils.Constructor
import io.github.intellij.dlanguage.utils.ReferenceExpression

class GenericExpressionElementReference(element: ReferenceExpression,
                                        textRange: TextRange,
                                        private val qualifier: ReferenceExpression?,
                                        private val referenceName: String
) : PsiReferenceBase<ReferenceExpression>(element, textRange), PsiQualifiedReference {

    override fun resolve(): PsiElement? {
        var basicResolveResult = DResolveUtil.getInstance(myElement.project).findDefinitionNode(myElement)
        basicResolveResult = basicResolveResult.filter { it !is Constructor }.toSet()
        return basicResolveResult.singleOrNull()
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
