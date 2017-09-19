package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageTemplateTypeParameter extends PsiElement {
    @Nullable
    DlangIdentifier getIdentifier();

    @NotNull
    List<DLanguageType> getTypes();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    PsiElement getOP_EQ();

}
