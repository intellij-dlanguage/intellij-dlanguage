package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageAutoDeclaration extends PsiElement {
    @Nullable
    DLanguageStorageClass getStorageClass();

    @NotNull
    List<PsiElement> getOP_COMMAs();

    @Nullable
    PsiElement getOP_SCOLON();

    @NotNull
    List<DLanguageAutoDeclarationPart> getAutoDeclarationParts();
}
