package io.github.intellij.dlanguage.psi.references;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.psi.DlangFunctionDeclaration;
import io.github.intellij.dlanguage.psi.DlangIdentifier;

public class DReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(final PsiReferenceRegistrar registrar) {
        final PsiElementPattern.Capture<PsiNamedElement> variableCapture =
            PlatformPatterns.psiElement(PsiNamedElement.class).withParent(DlangIdentifier.class)
                .withParent(DlangFunctionDeclaration.class).withLanguage(DLanguage.INSTANCE);
        registrar.registerReferenceProvider(variableCapture,
            new DReferenceProvider());
    }
}

