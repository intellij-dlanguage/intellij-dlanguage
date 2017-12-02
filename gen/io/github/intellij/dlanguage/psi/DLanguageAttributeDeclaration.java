package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAttributeDeclaration extends PsiElement {

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    DLanguageAttribute getAttribute();
}
