
package io.github.intellij.dlanguage.psi.references

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiQualifiedReference
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.util.elementType
import com.intellij.util.IncorrectOperationException
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.psi.TypeResolveUtil
import io.github.intellij.dlanguage.psi.impl.DElementFactory
import io.github.intellij.dlanguage.utils.BasicType
import io.github.intellij.dlanguage.utils.QualifiedIdentifier

class TypeReference(element: QualifiedIdentifier,
                    textRange: TextRange,
                    private val qualifiedIdentifier: QualifiedIdentifier?,
                    private var referenceName: String,
                    private val inTypeSuffix: Boolean
) : PsiReferenceBase<QualifiedIdentifier>(element, textRange),
    PsiQualifiedReference {

    override fun resolve(): PsiElement? {
        return TypeResolveUtil.resolveType(this, myElement.templateInstance != null, inTypeSuffix, isModuleScopeOperator(element))
    }

    override fun getQualifier(): PsiElement? = qualifiedIdentifier

    override fun getReferenceName(): String = referenceName

    fun isModuleScopeOperator(e: QualifiedIdentifier): Boolean {
        if (e.qualifiedIdentifier != null)
            // not the innermost (most-left) element
            return false

        var current = e
        var parent = e.parent
        while (parent is QualifiedIdentifier) {
            if (parent.firstChild !== current)
                return false
            current = parent
            parent = current.parent
        }
        // `typeof.qualifiedIdentifier` case exists, so DOT can be here but not the first child
        return parent is BasicType && parent.firstChild.elementType === DlangTypes.OP_DOT
    }

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
