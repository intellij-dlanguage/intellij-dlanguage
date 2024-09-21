package io.github.intellij.dlanguage.psi.references

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiQualifiedReference
import com.intellij.psi.PsiReferenceBase
import com.intellij.util.IncorrectOperationException
import io.github.intellij.dlanguage.psi.TypeResolveUtil
import io.github.intellij.dlanguage.psi.impl.DElementFactory
import io.github.intellij.dlanguage.utils.TemplateSingleArgument

class TypeInstanceReference(element: TemplateSingleArgument,
                            textRange: TextRange
): PsiReferenceBase<TemplateSingleArgument>(element, textRange), PsiQualifiedReference {

    override fun resolve(): PsiElement? = TypeResolveUtil.resolveType(this, false)

    override fun getQualifier(): PsiElement? = null

    override fun getReferenceName() = element.identifier!!.text

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
        } else {
            throw IncorrectOperationException()
        }
        return myElement
    }

}
