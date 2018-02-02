package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateTypeParameter extends PsiElement {

    @Nullable
    public DlangIdentifier getIdentifier();

    @NotNull
    public List<DLanguageType> getTypes();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public PsiElement getOP_EQ();

}
