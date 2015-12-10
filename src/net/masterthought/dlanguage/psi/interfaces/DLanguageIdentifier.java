// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DLanguageIdentifier extends DNamedElement {

  @NotNull
  PsiElement getId();

    @Nullable
    PsiReference getReference();

    @NotNull
    String getName();

    @NotNull
    PsiElement setName(String newName);

    @NotNull
    PsiElement getNameIdentifier();
}
