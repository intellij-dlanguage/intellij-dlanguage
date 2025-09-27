package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.DLanguageBlockStatement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.DTypedElement;
import org.jetbrains.annotations.Nullable;

public interface DLanguageOutStatement extends DNamedElement, DTypedElement {

    @Nullable
    PsiElement getIdentifier();

    @Nullable
    DLanguageBlockStatement getBlockStatement();

    @Nullable
    PsiElement getKW_OUT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();
}
