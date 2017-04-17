// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility;
import net.masterthought.dlanguage.psi.interfaces.Declaration;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageEnumDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;

public interface DLanguageEnumDeclaration extends DNamedElement, HasVisibility, Declaration, StubBasedPsiElement<DLanguageEnumDeclarationStub> {

  @Nullable
  DLanguageAnonymousEnumDeclaration getAnonymousEnumDeclaration();

  @Nullable
  DLanguageEnumBaseType getEnumBaseType();

  @Nullable
  DLanguageEnumBody getEnumBody();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  PsiElement getKwEnum();

  @Nullable
  PsiElement getOpColon();

  @NotNull
  String getName();

  String getFullName();

  @Nullable
  PsiElement getNameIdentifier();

  @NotNull
  PsiReference getReference();

  @Nullable
  PsiElement setName(String newName);

  @NotNull
  ItemPresentation getPresentation();

  boolean isSomeVisibility(Visibility visibility);

}
