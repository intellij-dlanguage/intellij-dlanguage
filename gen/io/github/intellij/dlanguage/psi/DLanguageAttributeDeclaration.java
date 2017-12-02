package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAttributeDeclaration extends PsiElement {

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public DLanguageAttribute getAttribute();
}
