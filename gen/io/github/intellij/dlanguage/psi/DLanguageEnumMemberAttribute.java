package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.Nullable;

public interface DLanguageEnumMemberAttribute extends PsiElement {

    @Nullable
    DLanguageAtAttribute getAtAttribute();

    @Nullable
    DLanguageDeprecated getDeprecated();
}
