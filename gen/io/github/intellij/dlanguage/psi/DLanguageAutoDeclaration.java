package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAutoDeclaration extends PsiElement {

    @NotNull
    public List<DLanguageStorageClass> getStorageClasss();

    @NotNull
    public List<PsiElement> getOP_COMMAs();

    @Nullable
    public PsiElement getOP_SCOLON();

    @NotNull
    public List<DLanguageAutoDeclarationPart> getAutoDeclarationParts();
}
