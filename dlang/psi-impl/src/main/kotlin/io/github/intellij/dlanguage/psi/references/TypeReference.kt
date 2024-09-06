
package io.github.intellij.dlanguage.psi.references

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiQualifiedReference
import com.intellij.psi.PsiReferenceBase
import com.intellij.util.IncorrectOperationException
import io.github.intellij.dlanguage.psi.TypeResolveUtil
import io.github.intellij.dlanguage.psi.impl.DElementFactory
import io.github.intellij.dlanguage.utils.QualifiedIdentifier

class TypeReference(element: QualifiedIdentifier,
                    textRange: TextRange,
                    private val qualifiedIdentifier: QualifiedIdentifier?,
                    private var referenceName: String
) : PsiReferenceBase<QualifiedIdentifier>(element, textRange),
    PsiQualifiedReference {

    override fun resolve(): PsiElement? = TypeResolveUtil.resolveType(this, myElement.templateInstance != null)

    override fun getQualifier(): PsiElement? = qualifiedIdentifier

    override fun getReferenceName(): String = referenceName

    @Throws(IncorrectOperationException::class)
    override fun handleElementRename(newElementName: String): PsiElement {
        if (myElement.identifier != null) {
            val element = DElementFactory.createDLanguageIdentifierFromText(
                myElement.project,
                newElementName
            )!!
            myElement.identifier!!.replace(
                element
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
        referenceName = newElementName
        return myElement
    }
}
