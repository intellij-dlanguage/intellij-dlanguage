package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAssocArrayLiteral extends PsiElement {

    @Nullable
    public DLanguageKeyValuePairs getKeyValuePairs();

    @Nullable
    public PsiElement getOP_BRACKET_RIGHT();

    @Nullable
    public PsiElement getOP_BRACKET_LEFT();

}
