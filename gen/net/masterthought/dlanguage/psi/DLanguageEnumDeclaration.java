// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageEnumDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;

public interface DLanguageEnumDeclaration extends DNamedElement, HasVisibility, StubBasedPsiElement<DLanguageEnumDeclarationStub> {

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
