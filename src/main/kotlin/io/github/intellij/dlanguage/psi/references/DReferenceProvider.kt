package io.github.intellij.dlanguage.psi.references

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceProvider
import com.intellij.util.ProcessingContext
import io.github.intellij.dlanguage.DLanguage

class DReferenceProvider : PsiReferenceProvider() {
    override fun getReferencesByElement(
        element: PsiElement,
        context: ProcessingContext
    ): Array<PsiReference> {
        if (!element.language.`is`(DLanguage)) {
            return PsiReference.EMPTY_ARRAY
        }

        if (element is PsiNamedElement) {
            return arrayOf(DReference(element, element.textRange))
        }
        return PsiReference.EMPTY_ARRAY
    }
}
