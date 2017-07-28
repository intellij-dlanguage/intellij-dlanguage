package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageAutoDeclaration extends PsiElement {
    @Nullable
    public DLanguageStorageClass getStorageClass();

    @NotNull
    public List<PsiElement> getOP_COMMAs();

    @Nullable
    public PsiElement getOP_SCOLON();

    @NotNull
    public List<DLanguageAutoDeclarationPart> getAutoDeclarationParts();
}
