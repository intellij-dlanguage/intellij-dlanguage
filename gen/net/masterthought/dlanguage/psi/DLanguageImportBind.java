package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageImportBind extends PsiElement {
    @NotNull
    public List<DLanguageIdentifier> getIdentifiers();

    @Nullable
    public PsiElement getOP_EQ();

}
