package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageTemplateTypeParameter extends PsiElement {
    @Nullable
    DLanguageIdentifier getIdentifier();

    @NotNull
    List<DLanguageType> getTypes();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    PsiElement getOP_EQ();

}
