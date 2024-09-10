package io.github.intellij.dlanguage.psi.interfaces;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

/**
 * Represent elements that has qualifier to a label (goto, break or continue)
 */
public interface LabelQualifier extends PsiElement {
    @Nullable
    PsiElement getIdentifier();
}
