package io.github.intellij.dlanguage.psi.references

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.util.IncorrectOperationException
import io.github.intellij.dlanguage.psi.impl.DElementFactory
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.psi.resolve.processor.GenericProcessor
import io.github.intellij.dlanguage.psi.scope.PsiScopesUtil
import io.github.intellij.dlanguage.utils.ImportBind

class ImportBindReference(element: ImportBind,
                          textRange: TextRange
) : PsiReferenceBase<ImportBind>(element, textRange)
{
    override fun resolve(): PsiElement? {
        val processor = GenericProcessor(element.identifier!!.text, myElement)
        PsiScopesUtil.treeWalkUp(processor, element, null)
        if (processor.getResult().size == 1) {
            return processor.getResult()[0].element as DNamedElement
        }
        return null
    }

    override fun handleElementRename(newElementName: String): PsiElement? {
        if (myElement.identifier == null)
            throw IncorrectOperationException()
        myElement.identifier!!.replace(
            DElementFactory.createDLanguageIdentifierFromText(myElement.project, newElementName)!!
        )
        return myElement
    }
}
