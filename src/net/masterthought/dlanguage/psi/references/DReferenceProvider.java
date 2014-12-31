package net.masterthought.dlanguage.psi.references;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import net.masterthought.dlanguage.DLanguage;
import org.jetbrains.annotations.NotNull;

public class DReferenceProvider extends PsiReferenceProvider {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                 @NotNull ProcessingContext context) {
        if (!element.getLanguage().is(DLanguage.INSTANCE)) {
            return PsiReference.EMPTY_ARRAY;
        }

        if (element instanceof PsiNamedElement) {
            PsiNamedElement se = (PsiNamedElement) element;
            return new PsiReference[]{new DReference(se, se.getTextRange())};
        }
        return PsiReference.EMPTY_ARRAY;
    }
}
