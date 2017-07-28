package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageTemplateTypeParameter extends PsiElement {
    @Nullable
    public DLanguageIdentifier getIdentifier();

    @NotNull
    public List<DLanguageType> getTypes();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public PsiElement getOP_EQ();

}
