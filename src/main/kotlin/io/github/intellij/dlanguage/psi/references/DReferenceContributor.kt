package io.github.intellij.dlanguage.psi.references

import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.psi.named.DlangFunctionDeclaration

class DReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        val variableCapture =
            PlatformPatterns.psiElement(PsiNamedElement::class.java)
                .withParent(DlangFunctionDeclaration::class.java).withLanguage(DLanguage)
        registrar.registerReferenceProvider(
            variableCapture,
            DReferenceProvider()
        )
    }
}

